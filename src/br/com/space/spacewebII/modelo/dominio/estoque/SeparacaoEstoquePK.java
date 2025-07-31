/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Ronie
 */
@Embeddable
public class SeparacaoEstoquePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "sep_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "sep_spvcodigo")
	private String seriePedidoCodigo;

	@Basic(optional = false)
	@Column(name = "sep_pednumero")
	private int pedidoNumero;

	@Basic(optional = false)
	@Column(name = "sep_numitem")
	private int numeroItem;

	@Basic(optional = false)
	@Column(name = "sep_procodigo")
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = "sep_lcecodigo")
	private int localEstoqueCodigo;

	@Basic(optional = false)
	@Column(name = "sep_lote")
	private String lote;

	@Basic(optional = false)
	@Column(name = "sep_data")
	@Temporal(TemporalType.DATE)
	private Date data;

	@Basic(optional = false)
	@Column(name = "sep_hora")
	private String hora;

	@Basic(optional = false)
	@Column(name = "sep_codigo")
	private int codigo;

	public SeparacaoEstoquePK() {
	}

	public SeparacaoEstoquePK(int sepFilcodigo, String sepSpvCodigo,
			int sepPedNumero, int sepNumItem, int sepProCodigo,
			int sepLceCodigo, String sepLote, Date sepData, String sepHora,
			int sepCodigo) {
		this.filialCodigo = sepFilcodigo;
		this.seriePedidoCodigo = sepSpvCodigo;
		this.pedidoNumero = sepPedNumero;
		this.numeroItem = sepNumItem;
		this.produtoCodigo = sepProCodigo;
		this.localEstoqueCodigo = sepLceCodigo;
		this.lote = sepLote;
		this.data = sepData;
		this.hora = sepHora;
		this.codigo = sepCodigo;
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

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public int getLocalEstoqueCodigo() {
		return localEstoqueCodigo;
	}

	public void setLocalEstoqueCodigo(int localEstoqueCodigo) {
		this.localEstoqueCodigo = localEstoqueCodigo;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
