package com.cursomc.services;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.cursomc.domain.Cidade;
import com.cursomc.domain.Cliente;
import com.cursomc.domain.Endereco;
import com.cursomc.domain.enums.Perfil;
import com.cursomc.domain.enums.TipoCliente;
import com.cursomc.dto.ClienteDTO;
import com.cursomc.dto.ClienteNewDTO;
import com.cursomc.repositories.CidadeRepository;
import com.cursomc.repositories.ClienteRepository;
import com.cursomc.repositories.EnderecoRepository;
import com.cursomc.security.UserSS;
import com.cursomc.services.exceptions.AuthorizationException;
import com.cursomc.services.exceptions.DataIntegrityException;
import com.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class ClienteService {
	
	@Autowired 
	private BCryptPasswordEncoder pe;	
	
	@Autowired
	private ClienteRepository repo;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private S3Service s3Service;

	public Cliente find(Integer id) {
		UserSS user = UserService.authenticated();
		if (user==null || !user.hasRole(Perfil.ADMIN) && !id.equals(user.getId())) {
			throw new AuthorizationException("Acesso negado");
		}
		
		Optional<Cliente> obj = repo.findById(id);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Cliente.class.getName()));
	}
	
	@Transactional
	public Cliente insert(Cliente obj) {
		obj.setId(null); 	//auto-incremento
		obj = repo.save(obj);
		enderecoRepository.saveAll(obj.getEnderecos());
		return obj;
	}
	
	public Cliente update(Cliente obj) { 	
		Cliente newObj = find(obj.getId());
		updateData(newObj, obj);
		return repo.save(newObj);
	}
	
	public void delete(Integer id) {
		find(id);
		try {
			repo.deleteById(id);
		} catch (DataIntegrityViolationException e) {
			//Aqui uma excecao personalizada
			throw new DataIntegrityException("Não é possível excluir um cliente que possui entidades relacionadas");
		}
	}
	
	public List<Cliente> findAll() {
		return repo.findAll();
	}
	
	public Page<Cliente> findPage (Integer page, Integer linesPerPage, String orderBy, String direction) {
		PageRequest pageRequest = PageRequest.of(page, linesPerPage, Direction.valueOf(direction), orderBy);
		return repo.findAll(pageRequest);
	}
	
	//Converter ClienteDTO em Cliente
	public Cliente fromDto(ClienteDTO objDto) {
		//throw new UnsupportedOperationException();
		return new Cliente(objDto.getId(), objDto.getNome(), objDto.getEmail(), null, null, null);
	}
	
	public Cliente fromDto(ClienteNewDTO objDto) {
		Cliente cli = new Cliente(null, objDto.getNome(), objDto.getEmail(), objDto.getCpfOuCnpj(), TipoCliente.toEnum(objDto.getTipo()), pe.encode(objDto.getSenha()));
		Optional<Cidade> cid = cidadeRepository.findById(objDto.getCidadeId());
		Endereco end = new Endereco(null, objDto.getLogradouro(), objDto.getNumero(), objDto.getComplemento(), objDto.getBairro(), objDto.getCep(), cli, cid.get());
		cli.getEnderecos().add(end);
		cli.getTelefones().add(objDto.getTelefone1());
		if (objDto.getTelefone2()!=null)
			cli.getTelefones().add(objDto.getTelefone2());
		if (objDto.getTelefone3()!=null)
			cli.getTelefones().add(objDto.getTelefone3());
		return cli;
	}
	
	//Fazer Update com dados do Cliente originais
	private void updateData(Cliente newObj, Cliente obj) {
		newObj.setNome(obj.getNome());
		newObj.setEmail(obj.getEmail());
	}
	
	public URI uploadProfilePicture(MultipartFile multipartFile) {
		//return s3Service.uploadFile(multipartFile);
		
		UserSS user = UserService.authenticated();
		if (user == null) {
			throw new AuthorizationException("Acesso negado");
		}
		
		URI uri = s3Service.uploadFile(multipartFile);		//upload da imagem e retorna URI
		
		Optional<Cliente> cli = repo.findById(user.getId());
		cli.get().setImageUrl(uri.toString());
		repo.save(cli.get());
		
		return uri;
		
		/*
		BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
		jpgImage = imageService.cropSquare(jpgImage);
		jpgImage = imageService.resize(jpgImage, size);
		
		String fileName = prefix + user.getId() + ".jpg";
		
		return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
		*/
	}
}
