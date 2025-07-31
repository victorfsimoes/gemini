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
 * @author Sandro
 */
@Embeddable
public class SocioClientePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "scl_pescodigo")
	private int pessoaCodigo;

	@Basic(optional = false)
	@Column(name = "scl_seq")
	private int sequencial;

	public SocioClientePK() {
	}

	public SocioClientePK(int sclPescodigo, int sclSeq) {
		this.pessoaCodigo = sclPescodigo;
		this.sequencial = sclSeq;
	}

	public int getSclPescodigo() {
		return pessoaCodigo;
	}

	public void setSclPescodigo(int sclPescodigo) {
		this.pessoaCodigo = sclPescodigo;
	}

	public int getSclSeq() {
		return sequencial;
	}

	public void setSclSeq(int sclSeq) {
		this.sequencial = sclSeq;
	}

}
