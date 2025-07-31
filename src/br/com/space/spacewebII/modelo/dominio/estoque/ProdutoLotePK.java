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
public class ProdutoLotePK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "ple_procodigo")
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = "ple_lcecodigo")
	private int localEstoqueCodigo;

	@Basic(optional = false)
	@Column(name = "ple_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "ple_lote")
	private String lote;

	public ProdutoLotePK() {
	}

	public ProdutoLotePK(int pleProcodigo, int pleLcecodigo, int pleFilcodigo,
			String pleLote) {
		this.produtoCodigo = pleProcodigo;
		this.localEstoqueCodigo = pleLcecodigo;
		this.filialCodigo = pleFilcodigo;
		this.lote = pleLote;
	}

	public int getPleProcodigo() {
		return produtoCodigo;
	}

	public void setPleProcodigo(int pleProcodigo) {
		this.produtoCodigo = pleProcodigo;
	}

	public int getPleLcecodigo() {
		return localEstoqueCodigo;
	}

	public void setPleLcecodigo(int pleLcecodigo) {
		this.localEstoqueCodigo = pleLcecodigo;
	}

	public int getPleFilcodigo() {
		return filialCodigo;
	}

	public void setPleFilcodigo(int pleFilcodigo) {
		this.filialCodigo = pleFilcodigo;
	}

	public String getPleLote() {
		return lote;
	}

	public void setPleLote(String pleLote) {
		this.lote = pleLote;
	}

	/**
	 * 
	 * @return O ID composto por <strong> filialCodigo + localEstoqueCodigo +
	 *         lote + produtoCodigo </strong>
	 */
	public String getID() {
		return filialCodigo + localEstoqueCodigo + lote + produtoCodigo;
	}

}
