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
public class ProdutoPrecoPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
	@Column(name = ProdutoPreco.COLUNA_PRODUTO_CODIGO)
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = "ppr_prbcodigo")
	private int precoBaseCodigo;

	@Basic(optional = false)
	@Column(name = "ppr_filcodigo")
	private int filialCodigo;

	public ProdutoPrecoPK() {
	}

	public ProdutoPrecoPK(int pprProcodigo, int pprPrbcodigo, int pprFilcodigo) {
		this.produtoCodigo = pprProcodigo;
		this.precoBaseCodigo = pprPrbcodigo;
		this.filialCodigo = pprFilcodigo;
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public int getPrecoBaseCodigo() {
		return precoBaseCodigo;
	}

	public void setPrecoBaseCodigo(int precoBaseCodigo) {
		this.precoBaseCodigo = precoBaseCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
