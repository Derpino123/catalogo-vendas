package br.com.davsantos.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davsantos.entities.Pedido;
import br.com.davsantos.repositories.PedidoRepository;
import br.com.davsantos.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;

	public Pedido findById(Integer id) {
		if (id == null)
			return null;
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID : " + id + ", TIPO : " + Pedido.class.getName()));
	}
}
