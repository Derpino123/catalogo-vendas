package br.com.davsantos.entities;

import javax.persistence.Entity;

import com.fasterxml.jackson.annotation.JsonTypeName;

import br.com.davsantos.entities.enums.StatusPagamento;

@Entity
@JsonTypeName("pagamentoComCartao")
public class PagamentoComCartao extends Pagamento {
	private static final long serialVersionUID = 1L;
	
	
	private Integer numeroParcela;

	public PagamentoComCartao() {

	}

	public PagamentoComCartao(Integer id, StatusPagamento statusPagamento, Pedido pedido, Integer numeroParcela) {
		super(id, statusPagamento, pedido);

		this.numeroParcela = numeroParcela;
	}

	public Integer getNumeroParcela() {
		return numeroParcela;
	}

	public void setNumeroParcela(Integer numeroParcela) {
		this.numeroParcela = numeroParcela;
	}

}
