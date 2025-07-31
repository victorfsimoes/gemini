package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FormaPagamentoCondicaoPagamentoTaxaPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "fct_fpgcodigo")
	private String formaPagamentoCodigo;

	@Basic
	@Column(name = "fct_cpgcodigo")
	private int condicaoPagamentoCodigo;

	public FormaPagamentoCondicaoPagamentoTaxaPK() {

	}

	public String getFormaPagamentoCodigo() {
		return formaPagamentoCodigo;
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		this.formaPagamentoCodigo = formaPagamentoCodigo;
	}

	public int getCondicaoPagamentoCodigo() {
		return condicaoPagamentoCodigo;
	}

	public void setCondicaoPagamentoCodigo(int condicaoPagamentoCodigo) {
		this.condicaoPagamentoCodigo = condicaoPagamentoCodigo;
	}

}
