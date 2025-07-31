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
public class KitPedidoPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
	@Column(name = "kpd_filcodigo")
	private int filialCodigo;
	
	@Basic(optional = false)
	@Column(name = "kpd_spvcodigo")
	private String seriePedidosCodigo;
	
	@Basic(optional = false)
	@Column(name = "kpd_pednumero")
	private int pedidoNumero;
	
	@Basic(optional = false)
	@Column(name = "kpd_kitcodigo")
	private int kitCodigo;

	public KitPedidoPK() {
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getSeriePedidosCodigo() {
		return seriePedidosCodigo;
	}

	public void setSeriePedidosCodigo(String seriePedidosCodigo) {
		this.seriePedidosCodigo = seriePedidosCodigo;
	}

	public int getPedidoNumero() {
		return pedidoNumero;
	}

	public void setPedidoNumero(int pedidoNumero) {
		this.pedidoNumero = pedidoNumero;
	}

	public int getKitCodigo() {
		return kitCodigo;
	}

	public void setKitCodigo(int kitCodigo) {
		this.kitCodigo = kitCodigo;
	}
}
