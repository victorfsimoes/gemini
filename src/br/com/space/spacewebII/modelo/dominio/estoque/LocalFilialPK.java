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
public class LocalFilialPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "lcf_lcecodigo")
	private int localEstoqueCodigo;

	@Basic(optional = false)
	@Column(name = "lcf_filcodigo")
	private int filialCodigo;

	public LocalFilialPK() {
	}

	public LocalFilialPK(int localEstoqueCodigo, int filialCodigo) {
		super();
		this.localEstoqueCodigo = localEstoqueCodigo;
		this.filialCodigo = filialCodigo;
	}

	public int getLocalEstoqueCodigo() {
		return localEstoqueCodigo;
	}

	public void setLocalEstoqueCodigo(int localEstoqueCodigo) {
		this.localEstoqueCodigo = localEstoqueCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
