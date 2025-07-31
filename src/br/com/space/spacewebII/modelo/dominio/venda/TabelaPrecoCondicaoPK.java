/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Ronie
 */
@Embeddable
public class TabelaPrecoCondicaoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = TabelaPrecoCondicao.COLUNA_TABELAPRECO_CODIGO)
	private int tabelaPrecoCodigo;

	@Basic(optional = false)
	@Column(name = TabelaPrecoCondicao.COLUNA_CONDICAOPAGAMENTO_CODIGO)
	private int condicaoPagamentoCodigo;

	public TabelaPrecoCondicaoPK() {
	}

	public TabelaPrecoCondicaoPK(int tpcTprcodigo, int tpcCpgcodigo) {
		this.tabelaPrecoCodigo = tpcTprcodigo;
		this.condicaoPagamentoCodigo = tpcCpgcodigo;
	}

	public int getTabelaPrecoCodigo() {
		return tabelaPrecoCodigo;
	}

	public void setTabelaPrecoCodigo(int tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	public int getCondicaoPagamentoCodigo() {
		return condicaoPagamentoCodigo;
	}

	public void setCondicaoPagamentoCodigo(int condicaoPagamentoCodigo) {
		this.condicaoPagamentoCodigo = condicaoPagamentoCodigo;
	}

}
