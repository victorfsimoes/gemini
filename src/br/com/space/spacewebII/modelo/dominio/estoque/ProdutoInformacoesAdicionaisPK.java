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
 * @author Desenvolvimento
 */
@Embeddable
public class ProdutoInformacoesAdicionaisPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = ProdutoInformacoesAdicionais.COLUNA_PRODUTO_CODIGO)
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = "pia_titulo")
	private String titulo;

	public ProdutoInformacoesAdicionaisPK() {
	}

	public ProdutoInformacoesAdicionaisPK(int piaProcodigo, String piaTitulo) {
		this.produtoCodigo = piaProcodigo;
		this.titulo = piaTitulo;
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
