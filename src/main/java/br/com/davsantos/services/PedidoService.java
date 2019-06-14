package br.com.davsantos.services;

import java.util.Date;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.davsantos.entities.ItemPedido;
import br.com.davsantos.entities.PagamentoComBoleto;
import br.com.davsantos.entities.Pedido;
import br.com.davsantos.entities.enums.StatusPagamento;
import br.com.davsantos.repositories.ItemPedidoRepository;
import br.com.davsantos.repositories.PagamentoRepository;
import br.com.davsantos.repositories.PedidoRepository;
import br.com.davsantos.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {

	@Autowired
	private PedidoRepository pedidoRepository;
	
	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoRepository pagamentoRepository;
	
	@Autowired
	private ProdutoService produtoService;
	
	@Autowired
	private ItemPedidoRepository itemPedidoRepository;
	
	
	public Pedido findById(Integer id) {
		if (id == null)
			return null;
		Optional<Pedido> pedido = pedidoRepository.findById(id);
		
		return pedido.orElseThrow(() -> new ObjectNotFoundException(
				"Objeto não encontrado! ID : " + id + ", TIPO : " + Pedido.class.getName()));
	}
	
	@Transactional
	public Pedido insert (Pedido pedido) {
		pedido.setId(null);
		pedido.setInstante(new Date());
		pedido.getPagamento().setStatusPagamento(StatusPagamento.PENDENTE);
		pedido.getPagamento().setPedido(pedido);
		
		if (pedido.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) pedido.getPagamento();
			boletoService.preencherPagtoBoleto(pagto, pedido.getInstante());
		}
		
		pedido = pedidoRepository.save(pedido);
		pagamentoRepository.save(pedido.getPagamento());
		
		for (ItemPedido item : pedido.getItens()) {
			item.setDesconto(0.0);
			item.setPreco(produtoService.findById(item.getProduto().getId()).getPreco());
		}
		
		itemPedidoRepository.saveAll(pedido.getItens());
		
		return pedido;
	}
	
	
}
