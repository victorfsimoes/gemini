package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

import br.com.space.api.spa.model.dao.db.Table;
import br.com.space.api.spa.model.domain.IPersistent;

@Entity
@javax.persistence.Table(name = "produtodestaque")
public class ProdutoDestaque implements IPersistent, Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProdutoDestaquePK produtoDestaquePK = null;

	public ProdutoDestaque() {

	}

	public ProdutoDestaquePK getProdutoDestaquePK() {
		if (produtoDestaquePK == null) {
			produtoDestaquePK = new ProdutoDestaquePK();

		}
		return produtoDestaquePK;
	}

	public void setProdutoDestaquePK(ProdutoDestaquePK produtoDestaquePK) {
		this.produtoDestaquePK = produtoDestaquePK;
	}

	public int getProdutoCodigo() {

		return getProdutoDestaquePK().getProdutoCodigo();
	}

	public void setProdutoCodigo(int produtoCodigo) {

		getProdutoDestaquePK().setProdutoCodigo(produtoCodigo);

	}

	public int getFilialCodigo() {
		return getProdutoDestaquePK().getFilialCodigo();
	}

	public void setFilialCodigo(int filialCodigo) {
		getProdutoDestaquePK().setFilialCodigo(filialCodigo);
	}

	@Override
	public Table getTable() {

		return null;
	}

	@Override
	public void setTable(Table table) {

	}

	@Override
	public ProdutoDestaque clone() throws CloneNotSupportedException {
		return (ProdutoDestaque) super.clone();
	}
}
