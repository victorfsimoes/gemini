/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

/**
 * 
 * @author Ronie
 */
@Embeddable
public class EnderecosPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "end_pescodigo")
	private int pessoaCodigo;

	@Basic(optional = false)
	@Column(name = "end_codigo")
	@GeneratedValue
	private int codigo;

	public EnderecosPK() {
	}

	public EnderecosPK(int endPescodigo, int endCodigo) {
		this.pessoaCodigo = endPescodigo;
		this.codigo = endCodigo;
	}

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
