/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "regratrib")
@XmlRootElement
public class RegraTributacao implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_ATIVIDADE = "rtb_atvcodigo";
	private static final String COLUNA_UFDESTINO = "rtb_ufdestino";
	private static final String COLUNA_GRUPOTRIBUTACAO = "rtb_grtcodigo";
	private static final String COLUNA_NATUREZAOPERACAO = "rtb_natcodigo";
	private static final String COLUNA_TIPOPESSOA = "rtb_tipopessoa";
	private static final String COLUNA_FILIAL = "rtb_filial";
	private static final String COLUNA_UFORIGEM = "rtb_uforigem";

	@EmbeddedId
	protected RegraTributacaoPK regratribPK;

	@Column(name = "rtb_desc")
	private String descricao;

	@Column(name = "rtb_filcodigo")
	private Integer filialCodigo;

	@Basic(optional = false)
	@Column(name = "rtb_cst")
	private String cstCodigo;

	@Basic(optional = false)
	@Column(name = "rtb_aliqicms")
	private double aliquotaIcms;

	@Basic(optional = false)
	@Column(name = "rtb_basecalculo")
	private double baseCalculo;

	@Basic(optional = false)
	@Column(name = "rtb_isentas")
	private double isentas;

	@Basic(optional = false)
	@Column(name = "rtb_outras")
	private double outras;

	@Basic(optional = false)
	@Column(name = "rtb_cdfcodigo")
	private String codigoFiscalCodigo;

	@Column(name = "rtb_obfcodigo")
	private Integer observacaoFiscalCodigo;

	@Basic(optional = false)
	@Column(name = "rtb_gstcodigo")
	private int grupoStCodigo;

	@Basic(optional = false)
	@Column(name = "rtb_valpauta")
	private double valorPauta;

	@Basic(optional = false)
	@Column(name = "rtb_ativo")
	private int flagAtivo;

	@Basic(optional = false)
	@Column(name = "rtb_negpreco")
	private int negociacaoPrecoCodigo;

	@Column(name = "rtb_csscodigo")
	private String csosnCodigo;

	@Column(name = "rtb_mantgst")
	private int flagMantemGrupoSt;

	public RegraTributacao() {
	}

	public RegraTributacaoPK getRegratribPK() {
		return regratribPK;
	}

	public void setRegratribPK(RegraTributacaoPK regratribPK) {
		this.regratribPK = regratribPK;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getCstCodigo() {
		return cstCodigo;
	}

	public void setCstCodigo(String cstCodigo) {
		this.cstCodigo = cstCodigo;
	}

	public double getAliquotaIcms() {
		return aliquotaIcms;
	}

	public void setAliquotaIcms(double aliquotaIcms) {
		this.aliquotaIcms = aliquotaIcms;
	}

	public double getBaseCalculo() {
		return baseCalculo;
	}

	public void setBaseCalculo(double baseCalculo) {
		this.baseCalculo = baseCalculo;
	}

	public double getIsentas() {
		return isentas;
	}

	public void setIsentas(double isentas) {
		this.isentas = isentas;
	}

	public double getOutras() {
		return outras;
	}

	public void setOutras(double outras) {
		this.outras = outras;
	}

	public String getCodigoFiscalCodigo() {
		return codigoFiscalCodigo;
	}

	public void setCodigoFiscalCodigo(String codigoFiscalCodigo) {
		this.codigoFiscalCodigo = codigoFiscalCodigo;
	}

	public Integer getObservacaoFiscalCodigo() {
		return observacaoFiscalCodigo;
	}

	public void setObservacaoFiscalCodigo(Integer observacaoFiscalCodigo) {
		this.observacaoFiscalCodigo = observacaoFiscalCodigo;
	}

	public int getGrupoStCodigo() {
		return grupoStCodigo;
	}

	public void setGrupoStCodigo(int grupoStCodigo) {
		this.grupoStCodigo = grupoStCodigo;
	}

	public double getValorPauta() {
		return valorPauta;
	}

	public void setValorPauta(double valorPauta) {
		this.valorPauta = valorPauta;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getNegociacaoPrecoCodigo() {
		return negociacaoPrecoCodigo;
	}

	public void setNegociacaoPrecoCodigo(int negociacaoPrecoCodigo) {
		this.negociacaoPrecoCodigo = negociacaoPrecoCodigo;
	}

	public String getCsosnCodigo() {
		return csosnCodigo;
	}

	public void setCsosnCodigo(String csosnCodigo) {
		this.csosnCodigo = csosnCodigo;
	}

	public Integer getFlagMantemGrupoSt() {
		return flagMantemGrupoSt;
	}

	public void setFlagMantemGrupoSt(int flagmantemGrupoSt) {
		flagMantemGrupoSt = flagmantemGrupoSt;
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
	public static RegraTributacao recuperarUnico(GenericoDAO<IPersistent> dao,
			int codigoNatureza, int filial, int codigoAtividade,
			int codigoGrupoTributacao, String tipoPessoa, String ufOrigem,
			String ufDestino) {

		String where = COLUNA_NATUREZAOPERACAO + " = '" + codigoNatureza
				+ "' AND " + COLUNA_GRUPOTRIBUTACAO + " = "
				+ codigoGrupoTributacao + " AND " + COLUNA_ATIVIDADE + " = "
				+ codigoAtividade + " AND " + COLUNA_TIPOPESSOA + " = '"
				+ tipoPessoa + "' AND " + COLUNA_FILIAL + " = " + filial
				+ " AND " + COLUNA_UFORIGEM + " = '" + ufOrigem + "' AND "
				+ COLUNA_UFDESTINO + " = '" + ufDestino + "'";

		return dao.uniqueResult(RegraTributacao.class, where, null);
	}
}
