/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FormaPagamentoPessoaPK implements Serializable {

	private static final long serialVersionUID = 1L;

	public final static String COLUNA_FORMA_PAGAMENTO_CODIGO = "fpe_fpgcodigo";
	public static final String COLUNA_PESSOA_CODIGO = "fpe_pescodigo";

	@Column(name = COLUNA_PESSOA_CODIGO)
	private int pessoaCodigo = 0;

	@Column(name = COLUNA_FORMA_PAGAMENTO_CODIGO, length = 3)
	private String formaPagamentoCodigo = null;

	public FormaPagamentoPessoaPK() {

	}

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public String getFormaPagamentoCodigo() {
		return formaPagamentoCodigo;
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		this.formaPagamentoCodigo = formaPagamentoCodigo;
	}

}
