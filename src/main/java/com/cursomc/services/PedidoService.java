package com.cursomc.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.cursomc.domain.Pedido;
import com.cursomc.repositories.PedidoRepository;
import com.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository catRepo;

	public Pedido find(Integer id) {
		Optional<Pedido> obj = catRepo.findById(id);
		//return obj.orElse(null);
		return obj.orElseThrow(() -> new ObjectNotFoundException("Objeto não encontrado! Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
