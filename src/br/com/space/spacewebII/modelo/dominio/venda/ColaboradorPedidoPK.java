/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.space.api.negocio.modelo.dominio.IPedido;
import br.com.space.spacewebII.modelo.dominio.pessoa.Colaborador;

/**
 * 
 * @author Sandro
 */
@Embeddable
public class ColaboradorPedidoPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "clp_spvcodigo")
	private String seriePedidosCodigo;

	@Basic(optional = false)
	@Column(name = "clp_pednumero")
	private int pedidoNumero;

	@Basic(optional = false)
	@Column(name = "clp_clbcodigo")
	private int colaboradorCodigo;

	@Basic(optional = false)
	@Column(name = "clp_filcodigo")
	private int filialCodigo;

	public ColaboradorPedidoPK() {
	}

	public ColaboradorPedidoPK(Colaborador colaborador, IPedido pedido) {

		this.seriePedidosCodigo = pedido.getSerieCodigo();
		this.pedidoNumero = pedido.getNumero();
		this.filialCodigo = pedido.getFilialCodigo();
		this.colaboradorCodigo = colaborador.getCodigo();
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

	public int getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(int colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
