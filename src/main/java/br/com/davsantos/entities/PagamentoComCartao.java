package br.com.davsantos.entities;

import javax.persistence.Entity;

import br.com.davsantos.entities.enums.StatusPagamento;

@Entity
public class PagamentoComCartao extends Pagamento {

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
