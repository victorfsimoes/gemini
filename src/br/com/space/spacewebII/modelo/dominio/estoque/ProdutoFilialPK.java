/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Ronie
 */
@Embeddable
public class ProdutoFilialPK implements Serializable {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Basic(optional = false)
    @Column(name = "pfi_procodigo")
    private int produtoCodigo;
	
    @Basic(optional = false)
    @Column(name = "pfi_filcodigo")
    private int filialCodigo;

    public ProdutoFilialPK() {
    }

    public ProdutoFilialPK(int pfiProcodigo, int pfiFilcodigo) {
        this.produtoCodigo = pfiProcodigo;
        this.filialCodigo = pfiFilcodigo;
    }

    public int getPfiProcodigo() {
        return produtoCodigo;
    }

    public void setPfiProcodigo(int pfiProcodigo) {
        this.produtoCodigo = pfiProcodigo;
    }

    public int getPfiFilcodigo() {
        return filialCodigo;
    }

    public void setPfiFilcodigo(int pfiFilcodigo) {
        this.filialCodigo = pfiFilcodigo;
    }

}
