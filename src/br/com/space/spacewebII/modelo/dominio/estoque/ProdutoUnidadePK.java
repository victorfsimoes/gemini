/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Ronie
 */
@Embeddable
public class ProdutoUnidadePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = ProdutoUnidade.COLUNA_PRODUTO_CODIGO)
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = ProdutoUnidade.COLUNA_UNIDADE)
	private String unidade;

	@Basic(optional = false)
	@Column(name = ProdutoUnidade.COLUNA_QUANTIDADE)
	private int quantidadeUnidade;

	public ProdutoUnidadePK() {
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public int getQuantidadeUnidade() {
		return quantidadeUnidade;
	}

	public void setQuantidadeUnidade(int quantidadeUnidade) {
		this.quantidadeUnidade = quantidadeUnidade;
	}

	/**
	 * 
	 * @return O indentificador da unidade contento a <strong> UNIDADE +
	 *         QUANTIDADE + PRODUTO CODIGO </strong>
	 */
	public String getID() {

		return getUnidade() + getQuantidadeUnidade() + getProdutoCodigo();

	}

}
