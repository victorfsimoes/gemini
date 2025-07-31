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
 * @author Sandro
 */
@Embeddable
public class GrupoComissaoItemPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "gci_gcocodigo")
	private int grupoComissaoCodigo;

	@Basic(optional = false)
	@Column(name = "gci_numseq")
	private int numeroSequencial;

	public GrupoComissaoItemPK() {
	}

	public GrupoComissaoItemPK(int grupoComissaoCodigo, int numeroSequencial) {
		super();
		this.grupoComissaoCodigo = grupoComissaoCodigo;
		this.numeroSequencial = numeroSequencial;
	}

	public int getGrupoComissaoCodigo() {
		return grupoComissaoCodigo;
	}

	public void setGrupoComissaoCodigo(int grupoComissaoCodigo) {
		this.grupoComissaoCodigo = grupoComissaoCodigo;
	}

	public int getNumeroSequencial() {
		return numeroSequencial;
	}

	public void setNumeroSequencial(int numeroSequencial) {
		this.numeroSequencial = numeroSequencial;
	}

}
