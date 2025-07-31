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
public class OpcaoEspecialFilialPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "oef_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "oef_oepcodigo")
	private int opcaoEspecialCodigo;

	public OpcaoEspecialFilialPK() {
	}

	public OpcaoEspecialFilialPK(int oefFilcodigo, int oefOepcodigo) {
		this.filialCodigo = oefFilcodigo;
		this.opcaoEspecialCodigo = oefOepcodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getOpcaoEspecialCodigo() {
		return opcaoEspecialCodigo;
	}

	public void setOpcaoEspecialCodigo(int opcaoEspecialCodigo) {
		this.opcaoEspecialCodigo = opcaoEspecialCodigo;
	}

}
