package com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Categoria;
import com.cursomc.repositories.CategoriaRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class CategoriaService {
	
	@Autowired
	private CategoriaRepository catRepo;

	public Categoria find(Integer id) {
		Optional<Categoria> obj = catRepo.findById(id);
		//return obj.orElse(null);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Categoria.class.getName()));
	}
	
	public Categoria insert(Categoria obj) {
		obj.setId(null); 	//auto-incremento
		return catRepo.save(obj);
	}
	
	public Categoria update(Categoria obj) { 	
		find(obj.getId());
		return catRepo.save(obj);
	}
}
