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
 * @author Sandro
 */
@Entity
@Table(name = "categoriapro")
@XmlRootElement
public class CategoriaProduto implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "ctp_codigo")
	private int codigo;

	@Basic(optional = false)
	@Column(name = "ctp_desc")
	private String descricao;

	@Column(name = "ctp_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "ctp_filcodigo")
	private int filialCodigo;

	@Column(name = "ctp_clbcodigo")
	private Integer colaboradorCodigo;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "ctp_markup")
	private Double markup;

	@Column(name = "ctp_diasestoque")
	private Integer diasEstoque;

	@Column(name = "ctp_precomin")
	private Double precoMinimo;

	@Column(name = "ctp_precomax")
	private Double precoMaximo;

	@Column(name = "ctp_diasestmin")
	private Integer diasEstoqueMinimo;

	@Column(name = "ctp_diasestmax")
	private Integer diasEstoqueMaximo;

	@Column(name = "ctp_gcocodigoi")
	private Integer grupoComissaoInternoCodigo;

	@Column(name = "ctp_gcocodigoe")
	private Integer grupoComissaoExternoCodigo;

	@Column(name = "ctp_gcocodigooi")
	private Integer grupoComissaoOfertaInternoCodigo;

	@Column(name = "ctp_gcocodigooe")
	private Integer grupoComissaoOfertaExternoCodigo;

	@Column(name = "ctp_precominct")
	private Double precoMinimoCusto;

	@Column(name = "ctp_markup2")
	private Double markup2;

	public CategoriaProduto() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
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

	public Integer getGrupoComissaoInternoCodigo() {
		return grupoComissaoInternoCodigo;
	}

	public void setGrupoComissaoInternoCodigo(Integer grupoComissaoInternoCodigo) {
		this.grupoComissaoInternoCodigo = grupoComissaoInternoCodigo;
	}

	public Integer getGrupoComissaoExternoCodigo() {
		return grupoComissaoExternoCodigo;
	}

	public void setGrupoComissaoExternoCodigo(Integer grupoComissaoExternoCodigo) {
		this.grupoComissaoExternoCodigo = grupoComissaoExternoCodigo;
	}

	public Integer getGrupoComissaoOfertaInternoCodigo() {
		return grupoComissaoOfertaInternoCodigo;
	}

	public void setGrupoComissaoOfertaInternoCodigo(
			Integer grupoComissaoOfertaInternoCodigo) {
		this.grupoComissaoOfertaInternoCodigo = grupoComissaoOfertaInternoCodigo;
	}

	public Integer getGrupoComissaoOfertaExternoCodigo() {
		return grupoComissaoOfertaExternoCodigo;
	}

	public void setGrupoComissaoOfertaExternoCodigo(
			Integer grupoComissaoOfertaExternoCodigo) {
		this.grupoComissaoOfertaExternoCodigo = grupoComissaoOfertaExternoCodigo;
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
