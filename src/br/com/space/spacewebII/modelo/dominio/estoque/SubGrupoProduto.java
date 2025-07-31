/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.model.dao.GenericDAO;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.widget.SubGrupoWidget;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "subgrupopro")
@XmlRootElement
public class SubGrupoProduto implements Serializable, IPersistent,
		SubGrupoWidget, Comparable<SubGrupoProduto> {

	private static final long serialVersionUID = 1L;
	public static String CAMPO_CODIGO = "codigo";

	@Id
	@Basic(optional = false)
	@Column(name = "sgp_codigo")
	private Integer codigo;

	@Column(name = "sgp_grpcodigo")
	private int grupoProdutoCodigo;

	@Basic(optional = false)
	@Column(name = "sgp_desc")
	private String descricao;

	@Column(name = "sgp_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "sgp_filcodigo")
	private int filialCodigo;

	@Column(name = "sgp_clbcodigo")
	private Integer colaboradorCodigo;

	@Column(name = "sgp_markup")
	private Double markup;

	@Column(name = "sgp_diasestoque")
	private Integer diasEstoque;

	@Column(name = "sgp_precomin")
	private Double precoMinimoPercentual;

	@Column(name = "sgp_precomax")
	private Double precoMaximoPercentual;

	@Column(name = "sgp_diasestmin")
	private Integer diasEstoqueMinimo;

	@Column(name = "sgp_diasestmax")
	private Integer diasEstoqueMaximo;

	@Column(name = "sgp_gcocodigoi")
	private Integer grupoComissaoInterno;

	@Column(name = "sgp_gcocodigoe")
	private Integer grupoComissaoExterno;

	@Column(name = "sgp_gcocodigooi")
	private Integer grupoComissaoOfertaInterno;

	@Column(name = "sgp_gcocodigooe")
	private Integer grupoComissaoOfertaExterno;

	@Column(name = "sgp_precominct")
	private Double precoMinimoCustoPercentual;

	@Column(name = "sgp_markup2")
	private Double markup2;

	public SubGrupoProduto() {
	}

	public SubGrupoProduto(Integer sgpCodigo) {
		this.codigo = sgpCodigo;
	}

	public SubGrupoProduto(Integer sgpCodigo, String sgpDesc, int sgpFilCodigo) {
		this.codigo = sgpCodigo;
		this.descricao = sgpDesc;
		this.filialCodigo = sgpFilCodigo;
	}

	@Override
	public int getCodigo() {
		if (codigo != null) {
			return codigo.intValue();
		}

		return 0;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public int getGrupoProdutoCodigo() {
		return grupoProdutoCodigo;
	}

	public void setGrupoProdutoCodigo(int grupoProdutoCodigo) {
		this.grupoProdutoCodigo = grupoProdutoCodigo;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
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

	public Double getPrecoMinimoPercentual() {
		return precoMinimoPercentual;
	}

	public void setPrecoMinimoPercentual(Double precoMinimoPercentual) {
		this.precoMinimoPercentual = precoMinimoPercentual;
	}

	public Double getPrecoMaximoPercentual() {
		return precoMaximoPercentual;
	}

	public void setPrecoMaximoPercentual(Double precoMaximoPercentual) {
		this.precoMaximoPercentual = precoMaximoPercentual;
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

	public Integer getGrupoComissaoInterno() {
		return grupoComissaoInterno;
	}

	public void setGrupoComissaoInterno(Integer grupoComissaoInterno) {
		this.grupoComissaoInterno = grupoComissaoInterno;
	}

	public Integer getGrupoComissaoExterno() {
		return grupoComissaoExterno;
	}

	public void setGrupoComissaoExterno(Integer grupoComissaoExterno) {
		this.grupoComissaoExterno = grupoComissaoExterno;
	}

	public Integer getGrupoComissaoOfertaInterno() {
		return grupoComissaoOfertaInterno;
	}

	public void setGrupoComissaoOfertaInterno(Integer grupoComissaoOfertaInterno) {
		this.grupoComissaoOfertaInterno = grupoComissaoOfertaInterno;
	}

	public Integer getGrupoComissaoOfertaExterno() {
		return grupoComissaoOfertaExterno;
	}

	public void setGrupoComissaoOfertaExterno(Integer grupoComissaoOfertaExterno) {
		this.grupoComissaoOfertaExterno = grupoComissaoOfertaExterno;
	}

	public Double getPrecoMinimoCustoPercentual() {
		return precoMinimoCustoPercentual;
	}

	public void setPrecoMinimoCustoPercentual(Double precoMinimoCustoPercentual) {
		this.precoMinimoCustoPercentual = precoMinimoCustoPercentual;
	}

	public Double getMarkup2() {
		return markup2;
	}

	public void setMarkup2(Double markup2) {
		this.markup2 = markup2;
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

	public static List<SubGrupoProduto> recuperar(GenericoDAO dao,
			String ordenacao) {

		String where = null;

		/*
		 * if (filialCodigo != null && !filialCodigo.trim().isEmpty()) { where =
		 * "sgp_filcodigo = " + filialCodigo; }
		 */

		return dao.list(SubGrupoProduto.class, where, null, ordenacao, null);
	}

	public static List<SubGrupoProduto> recuperar(GenericoDAO dao,
			List<Produto> produtos) {

		List<String> whereFragmentos = new ArrayList<String>();

		whereFragmentos.add("sgp_ativo = 1");

		/*
		 * if (filialCodigo != null && !filialCodigo.trim().isEmpty()) {
		 * whereFragmentos.add("sgp_filcodigo = " + filialCodigo); }
		 */

		if (ListUtil.isValida(produtos)) {

			whereFragmentos.add(Produto.COLUNA_CODIGO + " IN "
					+  GenericDAO.createValuesArgumentIn(produtos));
		}

		String where = GenericoDAO.criarWhere(whereFragmentos);

		String select = "select  subgrupopro.* from subgrupopro inner join produto on pro_sgpcodigo = sgp_codigo"
				+ (StringUtil.isValida(where) ? " and " + where : "")
				+ " group by sgp_codigo order by sgp_codigo";

		return dao.list(SubGrupoProduto.class, select);
	}

	@Override
	public int compareTo(SubGrupoProduto o) {
		return codigo.compareTo(o.getCodigo());
	}

	@Override
	public int getGrupoCodigo() {
		return grupoProdutoCodigo;
	}
}
