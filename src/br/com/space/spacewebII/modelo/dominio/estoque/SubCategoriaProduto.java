/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "subcategpro")
@XmlRootElement
public class SubCategoriaProduto implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "scp_codigo")
	private Integer codigo;

	@Basic(optional = false)
	@Column(name = "scp_desc")
	private String descricao;

	@Column(name = "scp_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "scp_filcodigo")
	private int filialCodigo;

	@Column(name = "scp_clbcodigo")
	private Integer colaboradorCodigo;

	@Column(name = "scp_markup")
	private Double markup;

	@Column(name = "scp_diasestoque")
	private Integer diasEstoque;

	@Column(name = "scp_precomin")
	private Double precoMinimoPercentual;

	@Column(name = "scp_precomax")
	private Double precoMaximoPercentual;

	@Column(name = "scp_diasestmin")
	private Integer diasEstoqueMinimo;

	@Column(name = "scp_diasestmax")
	private Integer diasEstoqueMaximo;

	@Column(name = "scp_gcocodigoi")
	private Integer grupoCoissaoInterno;

	@Column(name = "scp_gcocodigoe")
	private Integer grupoComissaoExterno;

	@Column(name = "scp_gcocodigooi")
	private Integer grupoComissaoOfertaInterno;

	@Column(name = "scp_gcocodigooe")
	private Integer grupoComissaoOfertaExterno;

	@Column(name = "scp_precominct")
	private Double precoMinimoCustoPercentual;

	@Column(name = "scp_markup2")
	private Double markup2;

	public SubCategoriaProduto() {
	}

	public SubCategoriaProduto(Integer scpCodigo) {
		this.codigo = scpCodigo;
	}

	public SubCategoriaProduto(Integer scpCodigo, String scpDesc,
			int scpFilCodigo) {
		this.codigo = scpCodigo;
		this.descricao = scpDesc;
		this.filialCodigo = scpFilCodigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

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

	public Integer getGrupoCoissaoInterno() {
		return grupoCoissaoInterno;
	}

	public void setGrupoCoissaoInterno(Integer grupoCoissaoInterno) {
		this.grupoCoissaoInterno = grupoCoissaoInterno;
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
}
