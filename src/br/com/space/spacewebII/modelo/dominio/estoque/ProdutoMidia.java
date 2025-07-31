/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.dao.GenericDAO;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@SpaceTable(name = "produtomidia")
@Table(name = "produtomidia")
@XmlRootElement
public class ProdutoMidia implements Serializable, IPersistent,
		Comparable<ProdutoMidia> {

	private static final long serialVersionUID = 1L;

	public static final int TIPO_VIDEO = 1;
	public static final int TIPO_IMAGEM = 0;

	protected static final String COLUNA_ATIVO = "pmi_ativo";
	protected static final String COLUNA_MIDIA = "pmi_midia";
	protected static final String COLUNA_PRODUTO_CODIGO = "pmi_procodigo";
	protected static final String COLUNA_KIT_CODIGO = "pmi_kitcodigo";
	protected static final String COLUNA_ORDEM = "pmi_ordem";
	protected static final String COLUNA_TIPO = "pmi_tipo";

	private static br.com.space.api.spa.model.dao.db.Table table;

	@EmbeddedId
	protected ProdutoMidiaPK produtoMidiaPK;

	@Column(name = COLUNA_ATIVO)
	@SpaceColumn(name = COLUNA_ATIVO)
	private int flagAtivo;

	@Column(name = COLUNA_MIDIA)
	@SpaceColumn(name = COLUNA_MIDIA)
	private String enderecoMidia;

	@Column(name = "pmi_descmidia")
	@SpaceColumn(name = "pmi_descmidia")
	private String descricaoMidia;

	public ProdutoMidia() {
	}

	public ProdutoMidiaPK getProdutoMidiaPK() {
		if (produtoMidiaPK == null)
			produtoMidiaPK = new ProdutoMidiaPK();
		return produtoMidiaPK;
	}

	public void setProdutoMidiaPK(ProdutoMidiaPK produtoMidiaPK) {
		this.produtoMidiaPK = produtoMidiaPK;
	}

	@SpaceColumn(name = COLUNA_ORDEM)
	public int getOrdem() {
		return this.getProdutoMidiaPK().getOrdem();
	}

	public void setOrdem(int ordem) {
		this.getProdutoMidiaPK().setOrdem(ordem);
	}

	@SpaceColumn(name = COLUNA_TIPO)
	public int getTipoMidia() {
		return this.getProdutoMidiaPK().getTipoMidia();
	}

	public void setTipoMidia(int tipoMidia) {
		this.getProdutoMidiaPK().setTipoMidia(tipoMidia);
	}

	@SpaceColumn(name = COLUNA_PRODUTO_CODIGO)
	public int getProdutoCodigo() {
		return getProdutoMidiaPK().getProdutoCodigo();
	}

	public void setProdutoCodigo(int produtoCodigo) {
		getProdutoMidiaPK().setProdutoCodigo(produtoCodigo);
	}

	@SpaceColumn(name = COLUNA_KIT_CODIGO)
	public int getKitCodigo() {
		return getProdutoMidiaPK().getKitCodigo();
	}

	public void setKitCodigo(int kitCodigo) {
		getProdutoMidiaPK().setKitCodigo(kitCodigo);
	}

	public String getEnderecoMidia() {
		if (StringUtil.isValida(enderecoMidia) && getProdutoMidiaPK() != null
				&& getProdutoMidiaPK().getTipoMidia() != TIPO_VIDEO) {
			return this.enderecoMidia.toLowerCase();
		}
		return this.enderecoMidia;
	}

	public void setEnderecoMidia(String enderecoMidia) {
		this.enderecoMidia = enderecoMidia;
	}

	public String getDescricaoMidia() {
		return descricaoMidia;
	}

	public void setDescricaoMidia(String descricaoMidia) {
		this.descricaoMidia = descricaoMidia;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return ProdutoMidia.table;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		ProdutoMidia.table = table;
	}

	@Override
	public ProdutoMidia clone() {
		try {
			return (ProdutoMidia) super.clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}
		return null;
	}

	private static String getWherePadraoAtivo() {
		return COLUNA_ATIVO + " = 1 and " + COLUNA_MIDIA
				+ " is not null and pmi_midia <> ''";
	}

	public static List<ProdutoMidia> recuperaMidiasAtivasDeProduto(
			GenericoDAO dao, int produtoCodigo) {
		List<ProdutoMidia> midias = dao.list(ProdutoMidia.class,
				getWherePadraoAtivo() + " and " + COLUNA_PRODUTO_CODIGO + " = "
						+ produtoCodigo, null, "produtoMidiaPK.ordem", null);

		return midias;
	}

	public static List<ProdutoMidia> recuperaMidiasAtivasDeKit(GenericoDAO dao,
			int kitCodigo) {

		List<ProdutoMidia> midias = dao.list(ProdutoMidia.class,
				getWherePadraoAtivo() + " and " + COLUNA_KIT_CODIGO + " = "
						+ kitCodigo, null, "produtoMidiaPK.ordem", null);

		return midias;
	}

	public static ProdutoMidia recuperarImagemCapaKit(GenericoDAO dao,
			int kitCodigo) {

		String select = "select * from produtomidia where pmi_kitcodigo = "
				+ kitCodigo + " and pmi_ordem = 0 and " + getWherePadraoAtivo();

		List<ProdutoMidia> midias = dao.list(ProdutoMidia.class, select);

		if (ListUtil.isValida(midias)) {
			return midias.get(0);
		}

		return null;
	}

	public static List<ProdutoMidia> recuperarImagensAtivasCapa(
			GenericoDAO dao, List<Produto> produtos) {

		List<ProdutoMidia> lista = null;

		/*
		 * select produtomidia.* from produtomidia inner join produto on
		 * pmi_procodigo = pro_codigo group by pmi_procodigo order by
		 * pmi_procodigo
		 */

		String select = "select produtomidia.* from produtomidia inner join produto on pmi_procodigo = pro_codigo and pro_codigo in "
				+ GenericDAO.createValuesArgumentIn(produtos)
				+ " and "
				+ getWherePadraoAtivo()
				+ " and "
				+ COLUNA_TIPO
				+ " = 0 and "
				+ COLUNA_ORDEM + " = 0";

		try {
			lista = dao.list(ProdutoMidia.class, select, null);

		} catch (Exception e) {

			e.printStackTrace();
		}

		return lista;

	}

	public static ProdutoMidia recuperarImagemCapaAtiva(GenericoDAO dao,
			int produtoCodigo) {
		return dao.uniqueResult(ProdutoMidia.class, getWherePadraoAtivo()
				+ " AND " + COLUNA_TIPO + " = 0 AND " + COLUNA_ORDEM
				+ " = 0 AND " + COLUNA_PRODUTO_CODIGO + " = " + produtoCodigo,
				null);
	}

	@Override
	public int compareTo(ProdutoMidia o) {
		return Integer.compare(getProdutoCodigo(), o.getProdutoCodigo());
	}
}
