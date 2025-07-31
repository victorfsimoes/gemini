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
 * @author Sandro
 */
@Embeddable
public class ReferenciaPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Basic(optional = false)
    @Column(name = "ref_pescodigo")
    private int pessoaCodigo;
	
    @Basic(optional = false)
    @Column(name = "ref_seq")
    private int sequencial;

    public ReferenciaPK() {
    }

    public ReferenciaPK(int refPescodigo, int refSeq) {
        this.pessoaCodigo = refPescodigo;
        this.sequencial   = refSeq;
    }

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

    
}
