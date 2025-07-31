/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.ITabelaPreco;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "tabpreco")
@XmlRootElement
public class TabelaPreco implements Serializable, ITabelaPreco {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "tpr_codigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Column(name = "tpr_desc")
	private String descricao;

	@Column(name = "tpr_limitacond")
	private int flagLimitaCondicaoPagamento;

	@Column(name = "tpr_indicead")
	private double indiceAdicional;

	@Column(name = "tpr_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "tpr_interno")
	private int flagInterno;

	@Basic(optional = false)
	@Column(name = "tpr_externo")
	private int flagExterno;

	@Column(name = "tpr_indiceof")
	private double indiceAdicionalOferta;

	@Column(name = "tpr_gcocodigo")
	private Integer grupoComissao;

	@Column(name = "tpr_gcocodigoof")
	private Integer grupoComissaoOferta;

	@Column(name = "tpr_enviapalm")
	private Integer flagEnviaPalm;

	@Column(name = "tpr_npromovenda")
	private int flagNaoParticipaPromocaoVenda;

	@Column(name = "tpr_naplindvar")
	private int flagNaoAplicaIndiceUnidadeVarejo;

	@Basic(optional = false)
	@Column(name = "tpr_flagindoep")
	private int flagIndiceOpcaoEspecial;

	@Basic(optional = false)
	@Column(name = "tpr_indiceoep")
	private double indiceOpcaoEspecial;

	@Basic(optional = false)
	@Column(name = "tpr_indiceoepof")
	private double indiceOpcaoEspecialOferta;

	@Column(name = "tpr_prbcodigo")
	private int precoBaseCodigo;

	@Column(name = "tpr_filcodigo")
	private Integer filialCodigo;

	@Column(name = "tpr_descgem")
	private int flagNaoPermiteDescontoGemini;

	@Column(name = "tpr_acresgem")
	private int flagNaoPermiteAcrescimoGemini;
	
	@Column(name = "tpr_difprepro")
	private int flagParticipaAcrescimoDespesaEntrega;

	/*
	 * @OneToMany(cascade = CascadeType.ALL, mappedBy = "tabelaPreco") private
	 * Collection<TabelaPrecoCondicao> tabelaPrecoCondicaoCollection;
	 */

	public TabelaPreco() {
	}

	public TabelaPreco(Integer tprCodigo) {
		this.codigo = tprCodigo;
	}

	public TabelaPreco(Integer tprCodigo, int tprInterno, int tprExterno,
			int tprFlagindoep, double tprIndiceoep, double tprIndiceoepof) {
		this.codigo = tprCodigo;
		this.flagInterno = tprInterno;
		this.flagExterno = tprExterno;
		this.flagIndiceOpcaoEspecial = tprFlagindoep;
		this.indiceOpcaoEspecial = tprIndiceoep;
		this.indiceOpcaoEspecialOferta = tprIndiceoepof;
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

	public int getFlagLimitaCondicaoPagamento() {
		return flagLimitaCondicaoPagamento;
	}

	public void setFlagLimitaCondicaoPagamento(int flagLimitaCondicaoPagamento) {
		this.flagLimitaCondicaoPagamento = flagLimitaCondicaoPagamento;
	}

	public double getIndiceAdicional() {
		if (indiceAdicional <= 0)
			return 1;
		return indiceAdicional;
	}

	public void setIndiceAdicional(double indiceAdicional) {
		this.indiceAdicional = indiceAdicional;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFlagInterno() {
		return flagInterno;
	}

	public void setFlagInterno(int flagInterno) {
		this.flagInterno = flagInterno;
	}

	public int getFlagExterno() {
		return flagExterno;
	}

	public void setFlagExterno(int flagExterno) {
		this.flagExterno = flagExterno;
	}

	public double getIndiceAdicionalOferta() {
		if (indiceAdicionalOferta <= 0)
			return 1;
		return indiceAdicionalOferta;
	}

	public void setIndiceAdicionalOferta(double indiceAdicionalOferta) {
		this.indiceAdicionalOferta = indiceAdicionalOferta;
	}

	public Integer getGrupoComissao() {
		return grupoComissao;
	}

	public void setGrupoComissao(Integer grupoComissao) {
		this.grupoComissao = grupoComissao;
	}

	public Integer getGrupoComissaoOferta() {
		return grupoComissaoOferta;
	}

	public void setGrupoComissaoOferta(Integer grupoComissaoOferta) {
		this.grupoComissaoOferta = grupoComissaoOferta;
	}

	public Integer getFlagEnviaPalm() {
		return flagEnviaPalm;
	}

	public void setFlagEnviaPalm(Integer flagEnviaPalm) {
		this.flagEnviaPalm = flagEnviaPalm;
	}

	public int getFlagNaoParticipaPromocaoVenda() {
		return flagNaoParticipaPromocaoVenda;
	}

	public void setFlagNaoParticipaPromocaoVenda(
			int flagNaoParticipaPromocaoVenda) {
		this.flagNaoParticipaPromocaoVenda = flagNaoParticipaPromocaoVenda;
	}

	public int getFlagNaoAplicaIndiceUnidadeVarejo() {
		return flagNaoAplicaIndiceUnidadeVarejo;
	}

	public void setFlagNaoAplicaIndiceUnidadeVarejo(
			int flagNaoAplicaIndiceUnidadeVarejo) {
		this.flagNaoAplicaIndiceUnidadeVarejo = flagNaoAplicaIndiceUnidadeVarejo;
	}

	public int getFlagIndiceOpcaoEspecial() {
		return flagIndiceOpcaoEspecial;
	}

	public void setFlagIndiceOpcaoEspecial(int flagIndiceOpcaoEspecial) {
		this.flagIndiceOpcaoEspecial = flagIndiceOpcaoEspecial;
	}

	public double getIndiceOpcaoEspecial() {
		if (indiceOpcaoEspecial <= 0)
			return 1;
		return indiceOpcaoEspecial;
	}

	public void setIndiceOpcaoEspecial(double indiceOpcaoEspecial) {
		this.indiceOpcaoEspecial = indiceOpcaoEspecial;
	}

	public double getIndiceOpcaoEspecialOferta() {
		if (indiceOpcaoEspecialOferta <= 0)
			return 1;
		return indiceOpcaoEspecialOferta;
	}

	public void setIndiceOpcaoEspecialOferta(double indiceOpcaoEspecialOferta) {
		this.indiceOpcaoEspecialOferta = indiceOpcaoEspecialOferta;
	}

	public int getPrecoBaseCodigo() {
		return precoBaseCodigo;
	}

	public void setPrecoBaseCodigo(int precoBaseCodigo) {
		this.precoBaseCodigo = precoBaseCodigo;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFlagNaoPermiteDescontoGemini() {
		return flagNaoPermiteDescontoGemini;
	}

	public void setFlagNaoPermiteDescontoGemini(int flagNaoPermiteDescontoGemini) {
		this.flagNaoPermiteDescontoGemini = flagNaoPermiteDescontoGemini;
	}

	public int getFlagNaoPermiteAcrescimoGemini() {
		return flagNaoPermiteAcrescimoGemini;
	}

	public void setFlagNaoPermiteAcrescimoGemini(
			int flagNaoPermiteAcrescimoGemini) {
		this.flagNaoPermiteAcrescimoGemini = flagNaoPermiteAcrescimoGemini;
	}

	@Override
	public boolean isPermiteDesconto() {
		return getFlagNaoPermiteDescontoGemini() == 0;
	}

	@Override
	public boolean isPermiteAcrescimo() {
		return getFlagNaoPermiteAcrescimoGemini() == 0;
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

	/**
	 * 
	 * @param dao
	 * @param codigo
	 * @return
	 */
	public static TabelaPreco recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(TabelaPreco.class, COLUNA_CODIGO + " = "
				+ codigo, null);
	}
	
	public int getFlagParticipaAcrescimoDespesaEntrega() {
		return flagParticipaAcrescimoDespesaEntrega;
	}
	
	public void setFlagParticipaAcrescimoDespesaEntrega(int flagParticipaAcrescimoDespesaEntrega) {
		this.flagParticipaAcrescimoDespesaEntrega = flagParticipaAcrescimoDespesaEntrega;
	}

	@Override
	public boolean isParticipaAcrescimoDespesaEntrega() {
		return flagParticipaAcrescimoDespesaEntrega == 1;
	}
}
