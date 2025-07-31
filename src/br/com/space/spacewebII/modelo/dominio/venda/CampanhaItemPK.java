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
 * @author Renato
 */
@Embeddable
public class CampanhaItemPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cmi_cmpcodigo")
	private int codigo;

	@Column(name = "cmi_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "cmi_item")
	private int item;

	public CampanhaItemPK() {
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

	public int getItem() {
		return item;
	}

	public void setItem(int item) {
		this.item = item;
	}
}
