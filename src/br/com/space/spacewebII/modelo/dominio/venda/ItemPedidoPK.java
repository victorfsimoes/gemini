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
public class ItemPedidoPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = ItemPedido.COLUNA_SERIEPEDIDO)
	private String seriePedidoCodigo;
	@Basic(optional = false)
	@Column(name = ItemPedido.COLUNA_NUMEROPEDIDO)
	private int pedidoNumero;
	@Basic(optional = false)
	@Column(name = "ipv_numitem")
	private int numeroItem;
	@Basic(optional = false)
	@Column(name = ItemPedido.COLUNA_FILIALPEDIDO)
	private int filialCodigo;

	public ItemPedidoPK() {
	}

	public String getSeriePedidoCodigo() {
		return seriePedidoCodigo;
	}

	public void setSeriePedidoCodigo(String seriePedidoCodigo) {
		this.seriePedidoCodigo = seriePedidoCodigo;
	}

	public int getPedidoNumero() {
		return pedidoNumero;
	}

	public void setPedidoNumero(int pedidoNumero) {
		this.pedidoNumero = pedidoNumero;
	}

	public int getNumeroItem() {
		return numeroItem;
	}

	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
