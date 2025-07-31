/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IRegiao;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "regiao")
@XmlRootElement
public class Regiao implements Serializable, IRegiao {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "reg_codigo")
	private int codigo;

	@Basic(optional = false)
	@Column(name = "reg_desc")
	private String descricao;

	@Column(name = "reg_ativo")
	private int flagAtivo;

	@Column(name = "reg_vlrminimo")
	private double valorMinimo;

	@Column(name = "reg_pesominimo")
	private double pesoMinimo;

	@Column(name = "reg_volminimo")
	private double volumeMinimo;

	@Column(name = "reg_sequencia")
	private int sequencia;

	@Basic(optional = false)
	@Column(name = "reg_diasentrega")
	private int diasEntrega;

	@Column(name = "reg_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "reg_tipofrete")
	private String tipoFrete;

	@Basic(optional = false)
	@Column(name = "reg_valorfrete")
	private double valorFrete;

	@Basic(optional = false)
	@Column(name = "reg_percfrete")
	private double percentualFrete;

	@Basic(optional = false)
	@Column(name = "reg_isenfrete")
	private int flagValorMinimoIsencaoFrete;

	@Basic(optional = false)
	@Column(name = "reg_valisfrete")
	private double valorMinimoIsencaoFrete;

	@Column(name = "reg_local")
	private int local;

	@Column(name = "reg_nentrega")
	private int flagNaoEntrega;

	@Column(name = "reg_grecodigo")
	private int grupoEntregaCodigo;

	@Column(name = "reg_ufsigla")
	private String ufSigla;

	@Column(name = "reg_inddesppro")
	private double indiceAcrescimoDespesaEntregaProduto;

	public Regiao() {
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

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public double getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public double getPesoMinimo() {
		return pesoMinimo;
	}

	public void setPesoMinimo(double pesoMinimo) {
		this.pesoMinimo = pesoMinimo;
	}

	public double getVolumeMinimo() {
		return volumeMinimo;
	}

	public void setVolumeMinimo(double volumeMinimo) {
		this.volumeMinimo = volumeMinimo;
	}

	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}

	public int getDiasEntrega() {
		return diasEntrega;
	}

	public void setDiasEntrega(int diasEntrega) {
		this.diasEntrega = diasEntrega;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public boolean isTipoFreteTipoEntregaPeso() {
		return getTipoFrete().equals("T");
	}

	public String getTipoFrete() {
		return tipoFrete;
	}

	public void setTipoFrete(String tipoFrete) {
		this.tipoFrete = tipoFrete;
	}

	public double getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(double valorFrete) {
		this.valorFrete = valorFrete;
	}

	public double getPercentualFrete() {
		return percentualFrete;
	}

	public void setPercentualFrete(double percentualFrete) {
		this.percentualFrete = percentualFrete;
	}

	public int getFlagValorMinimoIsencaoFrete() {
		return flagValorMinimoIsencaoFrete;
	}

	public void setFlagValorMinimoIsencaoFrete(int flagValorMinimoIsencaoFrete) {
		this.flagValorMinimoIsencaoFrete = flagValorMinimoIsencaoFrete;
	}

	public double getValorMinimoIsencaoFrete() {
		return valorMinimoIsencaoFrete;
	}

	public void setValorMinimoIsencaoFrete(double valorMinimoIsencaoFrete) {
		this.valorMinimoIsencaoFrete = valorMinimoIsencaoFrete;
	}

	public int getLocal() {
		return local;
	}

	public void setLocal(int local) {
		this.local = local;
	}

	public int getFlagNaoEntrega() {
		return flagNaoEntrega;
	}

	public void setFlagNaoEntrega(int flagNaoEntrega) {
		this.flagNaoEntrega = flagNaoEntrega;
	}

	public int getGrupoEntregaCodigo() {
		return grupoEntregaCodigo;
	}

	public void setGrupoEntregaCodigo(int grupoEntregaCodigo) {
		this.grupoEntregaCodigo = grupoEntregaCodigo;
	}

	public String getUfSigla() {
		return ufSigla;
	}

	public void setUfSigla(String ufSigla) {
		this.ufSigla = ufSigla;
	}

	@Override
	public double getIndiceAcrescimoDespesaEntregaProduto() {
		return indiceAcrescimoDespesaEntregaProduto;
	}

	public void setIndiceAcrescimoDespesaEntregaProduto(double indiceAcrescimoDespesaEntregaProduto) {
		this.indiceAcrescimoDespesaEntregaProduto = indiceAcrescimoDespesaEntregaProduto;
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

}
