/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Ronie
 */
@Embeddable
public class ChaveCarteiraVendedor implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "ccv_cclcodigo")
	private int carteiraClienteCodigo;

	@Basic(optional = false)
	@Column(name = "ccv_clbcodigo")
	private int colaboradorCodigo;

	public ChaveCarteiraVendedor() {
	}

	public ChaveCarteiraVendedor(int ccvCclcodigo, int ccvClbcodigo) {
		this.carteiraClienteCodigo = ccvCclcodigo;
		this.colaboradorCodigo = ccvClbcodigo;
	}

	public int getCcvCclcodigo() {
		return carteiraClienteCodigo;
	}

	public void setCcvCclcodigo(int ccvCclcodigo) {
		this.carteiraClienteCodigo = ccvCclcodigo;
	}

	public int getCcvClbcodigo() {
		return colaboradorCodigo;
	}

	public void setCcvClbcodigo(int ccvClbcodigo) {
		this.colaboradorCodigo = ccvClbcodigo;
	}

}
