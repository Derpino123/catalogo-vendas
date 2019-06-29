package br.com.davsantos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davsantos.entities.Cidade;
import br.com.davsantos.repositories.CidadeRepository;

@Service
public class CidadeService {

	@Autowired
	private CidadeRepository cidadeRepository;
	
	public List<Cidade> findByEstado(Integer estadoId){
		return cidadeRepository.findCidades(estadoId);
	}
	
}
