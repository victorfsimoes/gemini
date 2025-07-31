/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IProdutoPreco;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceId;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = ProdutoPreco.NOME_TABELA)
@SpaceTable(name = ProdutoPreco.NOME_TABELA)
@XmlRootElement
public class ProdutoPreco implements Serializable, IPersistent, IProdutoPreco {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELA = "produtopreco";

	public static final String COLUNA_PRODUTO_CODIGO = "ppr_procodigo";
	public static final String COLRUNA_FILIAL_CODIGO = "ppr_filcodigo";

	public static final String CAMPO_PK = "produtoPrecoPK";

	@Transient
	private br.com.space.api.spa.model.dao.db.Table table;

	@EmbeddedId
	protected ProdutoPrecoPK produtoPrecoPK;

	@Column(name = "ppr_prcvendaant")
	@SpaceColumn(name = "ppr_prcvendaant")
	private double precoVendaAnterior;

	@Column(name = "ppr_precovenda")
	@SpaceColumn(name = "ppr_precovenda")
	private double precoVenda;

	@Column(name = "ppr_precominp")
	@SpaceColumn(name = "ppr_precominp")
	private double precoMinimoPercentual;

	@Column(name = "ppr_precominv")
	@SpaceColumn(name = "ppr_precominv")
	private double precoMinimoValor;

	@Column(name = "ppr_precomaxp")
	@SpaceColumn(name = "ppr_precomaxp")
	private double precoMaximoPercentual;

	@Column(name = "ppr_precomaxv")
	@SpaceColumn(name = "ppr_precomaxv")
	private double precoMaximoValor;

	@Column(name = "ppr_dtultalt")
	@SpaceColumn(name = "ppr_dtultalt")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaAlteracao;

	@Column(name = "ppr_hrultalt")
	@SpaceColumn(name = "ppr_hrultalt")
	private String horaUltimaAlteracao;

	public ProdutoPreco() {
	}

	/**
	 * 
	 * @param produtoCodigo
	 * @param precoBaseCodigo
	 * @param filialCodigo
	 */
	public ProdutoPreco(int produtoCodigo, int precoBaseCodigo, int filialCodigo) {
		this();
		this.getProdutoPrecoPK().setProdutoCodigo(produtoCodigo);
		this.getProdutoPrecoPK().setPrecoBaseCodigo(precoBaseCodigo);
		this.getProdutoPrecoPK().setFilialCodigo(filialCodigo);
	}

	/**
	 * 
	 * @return
	 */
	public ProdutoPrecoPK getProdutoPrecoPK() {
		if (produtoPrecoPK == null)
			produtoPrecoPK = new ProdutoPrecoPK();

		return produtoPrecoPK;
	}

	public double getPrecoVendaAnterior() {
		return precoVendaAnterior;
	}

	public void setPrecoVendaAnterior(double precoVendaAnterior) {
		this.precoVendaAnterior = precoVendaAnterior;
	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public double getPrecoMinimoPercentual() {
		return precoMinimoPercentual;
	}

	public void setPrecoMinimoPercentual(double precoMinimoPercentual) {
		this.precoMinimoPercentual = precoMinimoPercentual;
	}

	public double getPrecoMinimoValor() {
		return precoMinimoValor;
	}

	public void setPrecoMinimoValor(double precoMinimoValor) {
		this.precoMinimoValor = precoMinimoValor;
	}

	public double getPrecoMaximoPercentual() {
		return precoMaximoPercentual;
	}

	public void setPrecoMaximoPercentual(double precoMaximoPercentual) {
		this.precoMaximoPercentual = precoMaximoPercentual;
	}

	public double getPrecoMaximoValor() {
		return precoMaximoValor;
	}

	public void setPrecoMaximoValor(double precoMaximoValor) {
		this.precoMaximoValor = precoMaximoValor;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getHoraUltimaAlteracao() {
		return horaUltimaAlteracao;
	}

	public void setHoraUltimaAlteracao(String horaUltimaAlteracao) {
		this.horaUltimaAlteracao = horaUltimaAlteracao;
	}

	public void setProdutoPrecoPK(ProdutoPrecoPK produtoPrecoPK) {
		this.produtoPrecoPK = produtoPrecoPK;
	}

	@SpaceColumn(name = "ppr_filcodigo")
	@SpaceId(hierarchy=1)
	public int getFilialCodigo() {
		return this.produtoPrecoPK.getFilialCodigo();
	}

	public void setFilialCodigo(int filialCodigo) {
		if (this.produtoPrecoPK == null)
			this.produtoPrecoPK = new ProdutoPrecoPK();
		this.produtoPrecoPK.setFilialCodigo(filialCodigo);
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return table;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	@SpaceColumn(name = "ppr_prbcodigo")
	@SpaceId(hierarchy=2)
	public int getPrecoBaseCodigo() {
		return this.getProdutoPrecoPK().getPrecoBaseCodigo();
	}

	@Override
	@SpaceColumn(name = "ppr_procodigo")
	@SpaceId(hierarchy=3)
	public int getProdutoCodigo() {
		return this.getProdutoPrecoPK().getProdutoCodigo();
	}

	@Override
	public void setPrecoBaseCodigo(int arg0) {
		this.getProdutoPrecoPK().setPrecoBaseCodigo(arg0);
	}

	@Override
	public void setProdutoCodigo(int arg0) {
		this.getProdutoPrecoPK().setProdutoCodigo(arg0);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
