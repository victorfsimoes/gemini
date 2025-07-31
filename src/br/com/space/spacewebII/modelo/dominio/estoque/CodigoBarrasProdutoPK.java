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
public class CodigoBarrasProdutoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "cbp_procodigo")
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = "cbp_unpunida")
	private String unidade;

	@Basic(optional = false)
	@Column(name = "cbp_unpquant")
	private int quantidadeUnidade;

	@Basic(optional = false)
	@Column(name = "cbp_codigo")
	private String codigoBarras;

	public CodigoBarrasProdutoPK() {
	}

	public CodigoBarrasProdutoPK(int cbpProCodigo, String cbpUnpUnida,
			int cbpUnpQuant, String cbpCodigo) {
		this.produtoCodigo = cbpProCodigo;
		this.unidade = cbpUnpUnida;
		this.quantidadeUnidade = cbpUnpQuant;
		this.codigoBarras = cbpCodigo;
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

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

}
