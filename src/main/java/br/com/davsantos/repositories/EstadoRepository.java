package br.com.davsantos.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import br.com.davsantos.entities.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{

	@Transactional
	public List<Estado> findAllByOrderByNome();
	
}
