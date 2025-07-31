/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.space.api.spa.annotations.SpaceColumn;

/**
 * 
 * @author Sandro
 */
@Embeddable
public class ProdutoMidiaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = ProdutoMidia.COLUNA_PRODUTO_CODIGO)
	@SpaceColumn(name = ProdutoMidia.COLUNA_PRODUTO_CODIGO)
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = ProdutoMidia.COLUNA_TIPO)
	@SpaceColumn(name = ProdutoMidia.COLUNA_TIPO)
	private int tipoMidia;

	@Basic(optional = false)
	@Column(name = ProdutoMidia.COLUNA_ORDEM)
	@SpaceColumn(name = ProdutoMidia.COLUNA_ORDEM)
	private int ordem;

	@Basic(optional = false)
	@Column(name = ProdutoMidia.COLUNA_KIT_CODIGO)
	@SpaceColumn(name = ProdutoMidia.COLUNA_KIT_CODIGO)
	private int kitCodigo;

	public ProdutoMidiaPK() {
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public int getTipoMidia() {
		return tipoMidia;
	}

	public void setTipoMidia(int tipoMidia) {
		this.tipoMidia = tipoMidia;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public int getKitCodigo() {
		return kitCodigo;
	}

	public void setKitCodigo(int kitCodigo) {
		this.kitCodigo = kitCodigo;
	}
}
