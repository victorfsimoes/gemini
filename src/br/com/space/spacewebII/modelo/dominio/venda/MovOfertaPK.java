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
public class MovOfertaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "mof_oftnumero")
	private int ofertaNumero;

	@Basic(optional = false)
	@Column(name = "mof_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "mof_pescodigo")
	private int pessoaCodigo;

	public MovOfertaPK() {
	}

	public MovOfertaPK(int mofOftNumero, int mofFilCodigo, int mofPesCodigo) {
		this.ofertaNumero = mofOftNumero;
		this.filialCodigo = mofFilCodigo;
		this.pessoaCodigo = mofPesCodigo;
	}

	public int getOfertaNumero() {
		return ofertaNumero;
	}

	public void setOfertaNumero(int ofertaNumero) {
		this.ofertaNumero = ofertaNumero;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

}
