package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class ProdutoDestaquePK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "pdq_procodigo")
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = "pdq_filcodigo")
	private int filialCodigo;

	public ProdutoDestaquePK() {

	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}
}
