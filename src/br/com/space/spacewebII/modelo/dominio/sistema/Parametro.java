/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "parametro")
@XmlRootElement
public class Parametro implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "par_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private int filialCodigo;

	@Column(name = "par_contlote")
	private int flagControlaLote;

	@Column(name = "par_perenvemail")
	private int flagPermiteEnvioEmail;

	@Column(name = "par_custopadg")
	private int custoPadrao;

	@Column(name = "par_luccusven")
	private String flagLucroCustoVenda;

	@Column(name = "par_recolhest")
	private int flagRecolheSt;

	@Column(name = "par_recstpf")
	private int flagRecolheStPessoaFisica;

	@Column(name = "par_recstpi")
	private int flagRecolheStPessoaJuridicaIsenta;

	@Column(name = "par_recstuf")
	private int flagRecolheStUfDistinta;

	@Column(name = "par_clecodigopa")
	private int classificacaoEnderecoPadraoCodigo;

	@Column(name = "par_clecodigocb")
	private int classificacaoEnderecoPadraoCobrancaCodigo;

	@Column(name = "par_cctcodigo")
	private int contaCorrenteCodigo;

	@Column(name = "par_emailremete")
	private String emailRemetente;

	@Column(name = "par_nomeremete")
	private String nomeEmailRemetente;

	@Column(name = "par_serversmtp")
	private String servidorEmail;

	@Column(name = "par_loginemail")
	private String loginEmail;

	@Column(name = "par_senhaemail")
	private String senhaEmail;

	@Column(name = "par_portasmtp")
	private int portaEmail;

	@Column(name = "par_decqtvenda")
	private int casasDecimaisParaQuantidade = 0;

	@Column(name = "par_decest")
	private int casasDecimaisParaEstoque = 0;

	@Column(name = "par_tribpis")
	private String tipoTributacaoPis;

	@Column(name = "par_tribcofins")
	private String tipoTributacaoCofins;

	@Column(name = "par_aliqpis")
	private double aliquotaPis;

	@Column(name = "par_aliqcofins")
	private double aliquotaCofins;

	public Parametro() {
	}

	public Parametro(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFlagControlaLote() {
		return flagControlaLote;
	}

	public void setFlagControlaLote(int flagControlaLote) {
		this.flagControlaLote = flagControlaLote;
	}

	public int getCustoPadrao() {
		return custoPadrao;
	}

	public void setCustoPadrao(int custoPadrao) {
		this.custoPadrao = custoPadrao;
	}

	public String getFlagLucroCustoVenda() {
		return flagLucroCustoVenda;
	}

	public void setFlagLucroCustoVenda(String flagLucroCustoVenda) {
		this.flagLucroCustoVenda = flagLucroCustoVenda;
	}

	public int getFlagRecolheSt() {
		return flagRecolheSt;
	}

	public void setFlagRecolheSt(int flagRecolheSt) {
		this.flagRecolheSt = flagRecolheSt;
	}

	public int getFlagRecolheStPessoaFisica() {
		return flagRecolheStPessoaFisica;
	}

	public void setFlagRecolheStPessoaFisica(int flagRecolheStPessoaFisica) {
		this.flagRecolheStPessoaFisica = flagRecolheStPessoaFisica;
	}

	public int getFlagRecolheStPessoaJuridicaIsenta() {
		return flagRecolheStPessoaJuridicaIsenta;
	}

	public void setFlagRecolheStPessoaJuridicaIsenta(int flagRecolheStPessoaJuridicaIsenta) {
		this.flagRecolheStPessoaJuridicaIsenta = flagRecolheStPessoaJuridicaIsenta;
	}

	public int getFlagRecolheStUfDistinta() {
		return flagRecolheStUfDistinta;
	}

	public void setFlagRecolheStUfDistinta(int flagRecolheStUfDistinta) {
		this.flagRecolheStUfDistinta = flagRecolheStUfDistinta;
	}

	public int getClassificacaoEnderecoPadraoCodigo() {
		return classificacaoEnderecoPadraoCodigo;
	}

	public void setClassificacaoEnderecoPadraoCodigo(int classificacaoEnderecoPadraoCodigo) {
		this.classificacaoEnderecoPadraoCodigo = classificacaoEnderecoPadraoCodigo;
	}

	public int getContaCorrenteCodigo() {
		return contaCorrenteCodigo;
	}

	public void setContaCorrenteCodigo(int contaCorrenteCodigo) {
		this.contaCorrenteCodigo = contaCorrenteCodigo;
	}

	public int getClassificacaoEnderecoPadraoCobrancaCodigo() {
		return classificacaoEnderecoPadraoCobrancaCodigo;
	}

	public void setClassificacaoEnderecoPadraoCobrancaCodigo(int classificacaoEnderecoPadraoCobrancaCodigo) {
		this.classificacaoEnderecoPadraoCobrancaCodigo = classificacaoEnderecoPadraoCobrancaCodigo;
	}

	public String getEmailRemetente() {
		return emailRemetente;
	}

	public void setEmailRemetente(String emailRemetente) {
		this.emailRemetente = emailRemetente;
	}

	public String getNomeEmailRemetente() {
		return nomeEmailRemetente;
	}

	public void setNomeEmailRemetente(String nomeEmailRemetente) {
		this.nomeEmailRemetente = nomeEmailRemetente;
	}

	public String getServidorEmail() {
		return servidorEmail;
	}

	public void setServidorEmail(String servidorEmail) {
		this.servidorEmail = servidorEmail;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getSenhaEmail() {
		return senhaEmail;
	}

	public void setSenhaEmail(String senhaEmail) {
		this.senhaEmail = senhaEmail;
	}

	public int getPortaEmail() {
		return portaEmail;
	}

	public void setPortaEmail(int portaEmail) {
		this.portaEmail = portaEmail;
	}

	public int getCasasDecimaisParaQuantidade() {
		return casasDecimaisParaQuantidade;
	}

	public void setCasasDecimaisParaQuantidade(int casasDecimaisParaQuantidade) {
		this.casasDecimaisParaQuantidade = casasDecimaisParaQuantidade;
	}

	public int getCasasDecimaisParaEstoque() {
		return casasDecimaisParaEstoque;
	}

	public void setCasasDecimaisParaEstoque(int casasDecimaisParaEstoque) {
		this.casasDecimaisParaEstoque = casasDecimaisParaEstoque;
	}

	public int getFlagPermiteEnvioEmail() {
		return flagPermiteEnvioEmail;
	}

	public void setFlagPermiteEnvioEmail(int flagPermiteEnvioEmail) {
		this.flagPermiteEnvioEmail = flagPermiteEnvioEmail;
	}

	public String getTipoTributacaoPis() {
		return tipoTributacaoPis;
	}

	public void setTipoTributacaoPis(String tipoTributacaoPis) {
		this.tipoTributacaoPis = tipoTributacaoPis;
	}

	public String getTipoTributacaoCofins() {
		return tipoTributacaoCofins;
	}

	public void setTipoTributacaoCofins(String tipoTributacaoCofins) {
		this.tipoTributacaoCofins = tipoTributacaoCofins;
	}

	public double getAliquotaPis() {
		return aliquotaPis;
	}

	public void setAliquotaPis(double aliquotaPis) {
		this.aliquotaPis = aliquotaPis;
	}

	public double getAliquotaCofins() {
		return aliquotaCofins;
	}

	public void setAliquotaCofins(double aliquotaCofins) {
		this.aliquotaCofins = aliquotaCofins;
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
	public static Parametro recuperarUnico(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(Parametro.class, COLUNA_FILIAL + " = " + codigo, null);
	}

}
