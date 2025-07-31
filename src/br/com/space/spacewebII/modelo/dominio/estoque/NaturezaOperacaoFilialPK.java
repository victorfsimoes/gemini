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
public class NaturezaOperacaoFilialPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "nof_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "nof_natcodigo")
	private String naturezaCodigo;

	public NaturezaOperacaoFilialPK() {
	}

	public NaturezaOperacaoFilialPK(int nofFilcodigo, String nofNatcodigo) {
		this.filialCodigo = nofFilcodigo;
		this.naturezaCodigo = nofNatcodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getNaturezaCodigo() {
		return naturezaCodigo;
	}

	public void setNaturezaCodigo(String naturezaCodigo) {
		this.naturezaCodigo = naturezaCodigo;
	}

}
