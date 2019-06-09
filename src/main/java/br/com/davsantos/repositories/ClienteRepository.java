package br.com.davsantos.repositories;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.davsantos.entities.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

	@Transactional
	Cliente findByEmail(String email);
}
