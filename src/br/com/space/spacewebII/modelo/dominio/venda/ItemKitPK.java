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
public class ItemKitPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "ikt_kitcodigo")
	private int kitCodigo;

	@Basic(optional = false)
	@Column(name = "ikt_procodigo")
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = "ikt_unpunidade")
	private String unidade;

	@Basic(optional = false)
	@Column(name = "ikt_unpquant")
	private int quantidadeUnidade;

	public ItemKitPK() {
	}

	public int getKitCodigo() {
		return kitCodigo;
	}

	public void setKitCodigo(int kitCodigo) {
		this.kitCodigo = kitCodigo;
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

}
