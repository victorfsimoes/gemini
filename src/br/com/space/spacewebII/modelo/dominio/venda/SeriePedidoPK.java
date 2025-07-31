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
public class SeriePedidoPK implements Serializable, Cloneable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "spv_codigo")
	private String codigo;

	@Basic(optional = false)
	@Column(name = "spv_filcodigo")
	private int filialCodigo;

	public SeriePedidoPK() {
	}

	public SeriePedidoPK(String spvCodigo, int spvFilCodigo) {
		this.codigo = spvCodigo;
		this.filialCodigo = spvFilCodigo;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
