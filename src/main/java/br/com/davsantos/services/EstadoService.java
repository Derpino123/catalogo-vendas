package br.com.davsantos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davsantos.entities.Estado;
import br.com.davsantos.repositories.EstadoRepository;

@Service
public class EstadoService {

	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<Estado> findAll(){
		return estadoRepository.findAllByOrderByNome();
	}
}
