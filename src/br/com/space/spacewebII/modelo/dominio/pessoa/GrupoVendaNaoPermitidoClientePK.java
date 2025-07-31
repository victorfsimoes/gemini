/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Sandro
 */
@Embeddable
public class GrupoVendaNaoPermitidoClientePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "gnc_pescodigo")
	private int pessoaCodigo;

	@Basic(optional = false)
	@Column(name = "gnc_grvcodigo")
	private int grupoVendaCodigo;

	public GrupoVendaNaoPermitidoClientePK() {
	}

	public GrupoVendaNaoPermitidoClientePK(int pessoaCodigo,
			int grupoVendaCodigo) {
		super();
		this.pessoaCodigo = pessoaCodigo;
		this.grupoVendaCodigo = grupoVendaCodigo;
	}

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public int getGrupoVendaCodigo() {
		return grupoVendaCodigo;
	}

	public void setGrupoVendaCodigo(int grupoVendaCodigo) {
		this.grupoVendaCodigo = grupoVendaCodigo;
	}

}
