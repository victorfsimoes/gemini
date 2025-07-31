/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Renato
 */
@Embeddable
public class CampanhaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cmp_codigo")
	private int codigo;

	@Column(name = "cml_filcodigo")
	private int filialCodigo;

	public CampanhaPK() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}
}
