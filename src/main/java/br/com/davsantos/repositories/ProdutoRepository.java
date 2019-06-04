package br.com.davsantos.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.davsantos.entities.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer>{

}
