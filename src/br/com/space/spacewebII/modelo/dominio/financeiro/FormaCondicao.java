package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.util.Date;

/**
 * 
 * @author Ronie
 * 
 */
public class FormaCondicao {
	public String formaPagamentoCodigo;
	public int condicaoPagamentoCodigo;
	public Date data;
	public int transacaoSequencial;
	public String observacao;
	public double valor;

	public FormaCondicao(String formaPagamentoCodigo,
			int condicaoPagamentoCodigo, Date data, int transacaoSequencial,
			double valor, String observacao) {
		super();
		this.formaPagamentoCodigo = formaPagamentoCodigo;
		this.condicaoPagamentoCodigo = condicaoPagamentoCodigo;
		this.data = data;
		this.transacaoSequencial = transacaoSequencial;
		this.valor = valor;
		this.observacao = observacao;
	}
}
