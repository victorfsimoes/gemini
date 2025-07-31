/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Sandro
 */
@Embeddable
public class CondicaoPagamentoNaoPermitidaFormaPagamentoPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "cnf_fpgcodigo")
    private String formaPagamentoCodigo;
	
    @Basic(optional = false)
    @Column(name = "cnf_cpgcodigo")
    private int condicaoPagamentoCodigo;

    public CondicaoPagamentoNaoPermitidaFormaPagamentoPK() {
    }

	public String getFormaPagamentoCodigo() {
		return formaPagamentoCodigo;
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		this.formaPagamentoCodigo = formaPagamentoCodigo;
	}

	public int getCondicaoPagamentoCodigo() {
		return condicaoPagamentoCodigo;
	}

	public void setCondicaoPagamentoCodigo(int condicaoPagamentoCodigo) {
		this.condicaoPagamentoCodigo = condicaoPagamentoCodigo;
	}


    
}
