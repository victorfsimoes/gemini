/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "produtoinfoadic")
@XmlRootElement
public class ProdutoInformacoesAdicionais implements Serializable, IPersistent {

	public static final int TIPO_HORIZONTAL = 1;
	public static final int TIPO_VERTICAL = 0;
	private static final long serialVersionUID = 1L;
	protected static final String COLUNA_ATIVO = "pia_ativo";
	protected static final String COLUNA_PRODUTO_CODIGO = "pia_procodigo";

	@EmbeddedId
	protected ProdutoInformacoesAdicionaisPK produtoinfoadicPK;

	@Basic(optional = false)
	@Column(name = "pia_ordem")
	private int ordem;

	@Lob
	@Column(name = "pia_infoadic")
	private String informacoesAdicionais;

	@Column(name = "pia_leiaute")
	private int tipoLeiaute;

	@Column(name = COLUNA_ATIVO)
	private int flagAtivo;

	public ProdutoInformacoesAdicionais() {
	}

	public ProdutoInformacoesAdicionaisPK getProdutoinfoadicPK() {
		if (produtoinfoadicPK == null)
			produtoinfoadicPK = new ProdutoInformacoesAdicionaisPK();

		return produtoinfoadicPK;
	}

	public void setProdutoinfoadicPK(
			ProdutoInformacoesAdicionaisPK produtoinfoadicPK) {
		this.produtoinfoadicPK = produtoinfoadicPK;
	}

	public int getProdutoCodigo() {
		return getProdutoinfoadicPK().getProdutoCodigo();
	}

	public void setProdutoCodigo(int produtoCodigo) {
		getProdutoinfoadicPK().setProdutoCodigo(produtoCodigo);
	}

	public String getTitulo() {
		return getProdutoinfoadicPK().getTitulo();
	}

	public void setTitulo(String titulo) {
		getProdutoinfoadicPK().setTitulo(titulo);
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public String getInformacoesAdicionais() {
		return informacoesAdicionais;
	}

	public void setInformacoesAdicionais(String informacoesAdicionais) {
		this.informacoesAdicionais = informacoesAdicionais;
	}

	public int getTipoLeiaute() {
		return tipoLeiaute;
	}

	public void setTipoLeiaute(int tipoLeiaute) {
		this.tipoLeiaute = tipoLeiaute;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static List<ProdutoInformacoesAdicionais> recuperaInformacaoesAdicionaisOrdenadasAtivas(
			GenericoDAO dao, int produtoCodigo) {

		return dao.list(ProdutoInformacoesAdicionais.class, COLUNA_ATIVO
				+ " = 1 AND " + COLUNA_PRODUTO_CODIGO + " = " + produtoCodigo,
				null, "ordem", null);
	}

	public static List<ProdutoInformacoesAdicionais> recuperarInformacaoesAdicionaisOrdenadasAtivas(
			GenericoDAO dao, int produtoCodigo, int tipoLayout) {

		String where = COLUNA_ATIVO + " = 1 and " + COLUNA_PRODUTO_CODIGO
				+ " = " + produtoCodigo + " and pia_leiaute = " + tipoLayout;

		return dao.list(ProdutoInformacoesAdicionais.class, where, null,
				"ordem", null);
	}

}
