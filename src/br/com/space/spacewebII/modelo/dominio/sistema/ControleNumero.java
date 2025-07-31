/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "controlenumero")
@XmlRootElement
public class ControleNumero implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected ControleNumeroPK controleNumeroPK;

	@Basic(optional = false)
	@Column(name = "ctn_numero")
	private int numero;

	public ControleNumero() {
	}

	public ControleNumeroPK getControleNumeroPK() {
		return controleNumeroPK;
	}

	public void setControleNumeroPK(ControleNumeroPK controleNumeroPK) {
		this.controleNumeroPK = controleNumeroPK;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
