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
public class PedidoPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "ped_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "ped_spvcodigo")
	private String serieCodigo;

	@Basic(optional = false)
	@Column(name = "ped_numero")
	private int numero;

	public PedidoPK() {
		this(0, "", 0);
	}

	public PedidoPK(int pedFilCodigo, String pedSpvCodigo, int pedNumero) {
		this.filialCodigo = pedFilCodigo;
		this.serieCodigo = pedSpvCodigo;
		this.numero = pedNumero;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getSerieCodigo() {
		return serieCodigo;
	}

	public void setSerieCodigo(String serieCodigo) {
		this.serieCodigo = serieCodigo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
