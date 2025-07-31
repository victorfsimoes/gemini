/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * 
 * @author Renato
 */
@Embeddable
public class CampanhaItemPeriodoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "cmd_cmpcodigo")
	private int cmdCmpcodigo;
	@Basic(optional = false)
	@Column(name = "cmd_filcodigo")
	private int cmdFilcodigo;
	@Basic(optional = false)
	@Column(name = "cmd_item")
	private int cmdItem;
	@Basic(optional = false)
	@Column(name = "cmd_dataini")
	@Temporal(TemporalType.DATE)
	private Date cmdDataini;

	public CampanhaItemPeriodoPK() {
	}

	public CampanhaItemPeriodoPK(int cmdCmpcodigo, int cmdFilcodigo, int cmdItem,
			Date cmdDataini) {
		this.cmdCmpcodigo = cmdCmpcodigo;
		this.cmdFilcodigo = cmdFilcodigo;
		this.cmdItem = cmdItem;
		this.cmdDataini = cmdDataini;
	}

	public int getCmdCmpcodigo() {
		return cmdCmpcodigo;
	}

	public void setCmdCmpcodigo(int cmdCmpcodigo) {
		this.cmdCmpcodigo = cmdCmpcodigo;
	}

	public int getCmdFilcodigo() {
		return cmdFilcodigo;
	}

	public void setCmdFilcodigo(int cmdFilcodigo) {
		this.cmdFilcodigo = cmdFilcodigo;
	}

	public int getCmdItem() {
		return cmdItem;
	}

	public void setCmdItem(int cmdItem) {
		this.cmdItem = cmdItem;
	}

	public Date getCmdDataini() {
		return cmdDataini;
	}

	public void setCmdDataini(Date cmdDataini) {
		this.cmdDataini = cmdDataini;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) cmdCmpcodigo;
		hash += (int) cmdFilcodigo;
		hash += (int) cmdItem;
		hash += (cmdDataini != null ? cmdDataini.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof CampanhaItemPeriodoPK)) {
			return false;
		}
		CampanhaItemPeriodoPK other = (CampanhaItemPeriodoPK) object;
		if (this.cmdCmpcodigo != other.cmdCmpcodigo) {
			return false;
		}
		if (this.cmdFilcodigo != other.cmdFilcodigo) {
			return false;
		}
		if (this.cmdItem != other.cmdItem) {
			return false;
		}
		if ((this.cmdDataini == null && other.cmdDataini != null)
				|| (this.cmdDataini != null && !this.cmdDataini
						.equals(other.cmdDataini))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "dominioGuardian.CampanhaitemperPK[ cmdCmpcodigo="
				+ cmdCmpcodigo + ", cmdFilcodigo=" + cmdFilcodigo
				+ ", cmdItem=" + cmdItem + ", cmdDataini=" + cmdDataini + " ]";
	}

}
