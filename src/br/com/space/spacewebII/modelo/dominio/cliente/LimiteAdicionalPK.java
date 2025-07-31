/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.cliente;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Ronie
 */
@Embeddable
public class LimiteAdicionalPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "lad_codigo")
	private int codigo;

	@Basic(optional = false)
	@Column(name = "lad_pescodigo")
	private int pessoaCodigo;

	@Basic(optional = false)
	@Column(name = "lad_filcodigo")
	private int filialCodigo;

	public LimiteAdicionalPK() {
	}

	public LimiteAdicionalPK(int ladCodigo, int ladPescodigo, int ladFilcodigo) {
		this.codigo = ladCodigo;
		this.pessoaCodigo = ladPescodigo;
		this.filialCodigo = ladFilcodigo;
	}

	public int getLadCodigo() {
		return codigo;
	}

	public void setLadCodigo(int ladCodigo) {
		this.codigo = ladCodigo;
	}

	public int getLadPescodigo() {
		return pessoaCodigo;
	}

	public void setLadPescodigo(int ladPescodigo) {
		this.pessoaCodigo = ladPescodigo;
	}

	public int getLadFilcodigo() {
		return filialCodigo;
	}

	public void setLadFilcodigo(int ladFilcodigo) {
		this.filialCodigo = ladFilcodigo;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (int) codigo;
		hash += (int) pessoaCodigo;
		hash += (int) filialCodigo;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof LimiteAdicionalPK)) {
			return false;
		}
		LimiteAdicionalPK other = (LimiteAdicionalPK) object;
		if (this.codigo != other.codigo) {
			return false;
		}
		if (this.pessoaCodigo != other.pessoaCodigo) {
			return false;
		}
		if (this.filialCodigo != other.filialCodigo) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "reversa.LimiteAdicionalPK[ ladCodigo=" + codigo
				+ ", ladPescodigo=" + pessoaCodigo + ", ladFilcodigo="
				+ filialCodigo + " ]";
	}

}
