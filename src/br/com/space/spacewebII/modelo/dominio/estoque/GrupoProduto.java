/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.spa.model.dao.GenericDAO;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.widget.GrupoWidget;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "grupopro")
@XmlRootElement
public class GrupoProduto implements Serializable, IPersistent, GrupoWidget,
		Comparable<GrupoProduto> {

	private static final long serialVersionUID = 1L;
	public static final String CAMPO_DESCRICAO = "descricao";
	public static final String CAMPO_CODIGO = "codigo";

	@Id
	@Basic(optional = false)
	@Column(name = "grp_codigo")
	private int codigo;

	@Column(name = "grp_desc")
	private String descricao;

	@Column(name = "grp_dptcodigo")
	private Integer departamentoCodigo;

	@Column(name = "grp_funcodigo")
	private Integer funcaoCodigo;

	@Column(name = "grp_markup")
	private Double markup;

	@Column(name = "grp_diasestoque")
	private Integer diasEstoque;

	@Column(name = "grp_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "grp_filcodigo")
	private int filialCodigo;

	@Column(name = "grp_clbcodigo")
	private Integer colaboradorCodigo;

	@Column(name = "grp_precomin")
	private Double precoMinimo;

	@Column(name = "grp_precomax")
	private Double precoMaximo;

	@Column(name = "grp_diasestmin")
	private Integer diasEstoqueMinimo;

	@Column(name = "grp_diasestmax")
	private Integer diasEstoqueMaximo;

	@Column(name = "grp_gcocodigoi")
	private Integer grupoComissaoInternaCodigo;

	@Column(name = "grp_gcocodigoe")
	private Integer grupoComissaoExternaCodigo;

	@Column(name = "grp_gcocodigooi")
	private Integer grupoComissaoOfertaInternaCodigo;

	@Column(name = "grp_gcocodigooe")
	private Integer grupoComissaoOfertaExternaCodigo;

	@Column(name = "grp_precominct")
	private Double precoMinimoCusto;

	@Column(name = "grp_markup2")
	private Double markup2;

	@Basic(optional = false)
	@Column(name = "grp_lopcodigo")
	private String localizacaoProdutoCodigo;

	public GrupoProduto() {
	}

	@Override
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getDescricao() {
		if (descricao != null) {
			return descricao.trim();
		}

		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getDepartamentoCodigo() {
		return departamentoCodigo;
	}

	public void setDepartamentoCodigo(Integer departamentoCodigo) {
		this.departamentoCodigo = departamentoCodigo;
	}

	public Integer getFuncaoCodigo() {
		return funcaoCodigo;
	}

	public void setFuncaoCodigo(Integer funcaoCodigo) {
		this.funcaoCodigo = funcaoCodigo;
	}

	public Double getMarkup() {
		return markup;
	}

	public void setMarkup(Double markup) {
		this.markup = markup;
	}

	public Integer getDiasEstoque() {
		return diasEstoque;
	}

	public void setDiasEstoque(Integer diasEstoque) {
		this.diasEstoque = diasEstoque;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Integer getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(Integer colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public Double getPrecoMinimo() {
		return precoMinimo;
	}

	public void setPrecoMinimo(Double precoMinimo) {
		this.precoMinimo = precoMinimo;
	}

	public Double getPrecoMaximo() {
		return precoMaximo;
	}

	public void setPrecoMaximo(Double precoMaximo) {
		this.precoMaximo = precoMaximo;
	}

	public Integer getDiasEstoqueMinimo() {
		return diasEstoqueMinimo;
	}

	public void setDiasEstoqueMinimo(Integer diasEstoqueMinimo) {
		this.diasEstoqueMinimo = diasEstoqueMinimo;
	}

	public Integer getDiasEstoqueMaximo() {
		return diasEstoqueMaximo;
	}

	public void setDiasEstoqueMaximo(Integer diasEstoqueMaximo) {
		this.diasEstoqueMaximo = diasEstoqueMaximo;
	}

	public Integer getGrupoComissaoInternaCodigo() {
		return grupoComissaoInternaCodigo;
	}

	public void setGrupoComissaoInternaCodigo(Integer grupoComissaoInternaCodigo) {
		this.grupoComissaoInternaCodigo = grupoComissaoInternaCodigo;
	}

	public Integer getGrupoComissaoExternaCodigo() {
		return grupoComissaoExternaCodigo;
	}

	public void setGrupoComissaoExternaCodigo(Integer grupoComissaoExternaCodigo) {
		this.grupoComissaoExternaCodigo = grupoComissaoExternaCodigo;
	}

	public Integer getGrupoComissaoOfertaInternaCodigo() {
		return grupoComissaoOfertaInternaCodigo;
	}

	public void setGrupoComissaoOfertaInternaCodigo(
			Integer grupoComissaoOfertaInternaCodigo) {
		this.grupoComissaoOfertaInternaCodigo = grupoComissaoOfertaInternaCodigo;
	}

	public Integer getGrupoComissaoOfertaExternaCodigo() {
		return grupoComissaoOfertaExternaCodigo;
	}

	public void setGrupoComissaoOfertaExternaCodigo(
			Integer grupoComissaoOfertaExternaCodigo) {
		this.grupoComissaoOfertaExternaCodigo = grupoComissaoOfertaExternaCodigo;
	}

	public Double getPrecoMinimoCusto() {
		return precoMinimoCusto;
	}

	public void setPrecoMinimoCusto(Double precoMinimoCusto) {
		this.precoMinimoCusto = precoMinimoCusto;
	}

	public Double getMarkup2() {
		return markup2;
	}

	public void setMarkup2(Double markup2) {
		this.markup2 = markup2;
	}

	public String getLocalizacaoProdutoCodigo() {
		return localizacaoProdutoCodigo;
	}

	public void setLocalizacaoProdutoCodigo(String localizacaoProdutoCodigo) {
		this.localizacaoProdutoCodigo = localizacaoProdutoCodigo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		return getDescricao() + " " + codigo;
	}

	/**
	 * 
	 * @param dao
	 * @param ordenacao
	 * @return
	 */
	public static List<GrupoProduto> recuperar(GenericoDAO dao, String ordenacao) {
		return dao.list(GrupoProduto.class, "grp_ativo = 1", null, ordenacao,
				null);
	}

	/**
	 * 
	 * @param dao
	 * @param produtos
	 * @return
	 */
	public static List<GrupoProduto> recuperar(GenericoDAO dao,
			List<Produto> produtos) {

		StringBuilder where = new StringBuilder();
		where.append("grp_ativo = 1");

		if (ListUtil.isValida(produtos)) {
			where.append(" and ").append(Produto.COLUNA_CODIGO + " IN ")
					.append(GenericDAO.createValuesArgumentIn(produtos));
		}

		StringBuilder select = new StringBuilder()
				.append("select grupopro.* from grupopro inner join produto on pro_grpcodigo = grp_codigo");

		if (where.length() > 0)
			select.append(" and ").append(where);

		select.append(" group by grp_codigo order by grp_codigo");

		return dao.list(GrupoProduto.class, select.toString());
	}

	@Override
	public int compareTo(GrupoProduto o) {
		return Integer.compare(codigo, o.getCodigo());
	}
}
