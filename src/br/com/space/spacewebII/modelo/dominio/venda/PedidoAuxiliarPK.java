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
public class PedidoAuxiliarPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "pex_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "pex_spvcodigo")
	private String seriePedidoCodigo;

	@Basic(optional = false)
	@Column(name = "pex_numero")
	private int numero;

	public PedidoAuxiliarPK() {
	}

	public PedidoAuxiliarPK(int pexFilCodigo, String pexSpvCodigo,
			int pexNumero) {
		this.filialCodigo = pexFilCodigo;
		this.seriePedidoCodigo = pexSpvCodigo;
		this.numero = pexNumero;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getSeriePedidoCodigo() {
		return seriePedidoCodigo;
	}

	public void setSeriePedidoCodigo(String seriePedidoCodigo) {
		this.seriePedidoCodigo = seriePedidoCodigo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

}
