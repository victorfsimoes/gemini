/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "movoferta")
@XmlRootElement
public class MovOferta implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected MovOfertaPK movOfertaPK;

	@Basic(optional = false)
	@Column(name = "mof_qtdevendida")
	private double quantidadeVendida;

	@Basic(optional = false)
	@Column(name = "mof_datavenda")
	@Temporal(TemporalType.DATE)
	private Date dataVenda;

	public MovOferta() {
	}

	public MovOfertaPK getMovOfertaPK() {
		return movOfertaPK;
	}

	public void setMovOfertaPK(MovOfertaPK movOfertaPK) {
		this.movOfertaPK = movOfertaPK;
	}

	public double getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public void setQuantidadeVendida(double quantidadeVendida) {
		this.quantidadeVendida = quantidadeVendida;
	}

	public Date getDataVenda() {
		return dataVenda;
	}

	public void setDataVenda(Date dataVenda) {
		this.dataVenda = dataVenda;
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
