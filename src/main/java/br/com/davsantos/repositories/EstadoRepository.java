package br.com.davsantos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.davsantos.entities.Estado;

@Repository
public interface EstadoRepository extends JpaRepository<Estado, Integer>{

}
