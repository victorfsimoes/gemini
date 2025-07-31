/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Renato
 */
@Embeddable
public class DicionarioCamposPK implements Serializable {
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "DCP_CAMPO")
	private String nomeCampo;

	@Basic(optional = false)
	@Column(name = "DCP_DTBTAB")
	private String nomeTabela;

	public DicionarioCamposPK() {
	}

	public DicionarioCamposPK(String dcpCampo, String dcpDtbtab) {
		this.nomeCampo = dcpCampo;
		this.nomeTabela = dcpDtbtab;
	}

	public String getNomeCampo() {
		return nomeCampo;
	}

	public void setNomeCampo(String nomeCampo) {
		this.nomeCampo = nomeCampo;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (nomeCampo != null ? nomeCampo.hashCode() : 0);
		hash += (nomeTabela != null ? nomeTabela.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof DicionarioCamposPK)) {
			return false;
		}
		DicionarioCamposPK other = (DicionarioCamposPK) object;
		if ((this.nomeCampo == null && other.nomeCampo != null)
				|| (this.nomeCampo != null && !this.nomeCampo
						.equals(other.nomeCampo))) {
			return false;
		}
		
		if ((this.nomeTabela == null && other.nomeTabela != null)
				|| (this.nomeTabela != null && !this.nomeTabela
						.equals(other.nomeTabela))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "dominioGuardian.DicCamposPK[ dcpCampo=" + nomeCampo
				+ ", dcpDtbtab=" + nomeTabela + " ]";
	}

}
