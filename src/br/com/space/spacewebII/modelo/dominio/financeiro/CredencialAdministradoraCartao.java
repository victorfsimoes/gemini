package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.cartao.modelo.AdministradoraCartao;
import br.com.space.api.cartao.modelo.AmbienteTrabalho;
import br.com.space.api.cartao.modelo.requisicao.ICredencial;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 * 
 */
@Entity
@Table(name = "credadmcartao")
public class CredencialAdministradoraCartao implements Serializable,
		ICredencial, IPersistent {

	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "cac_codigo";
	public static final int AMBIENTE_TESTE = 0;
	public static final int AMBIENTE_PRODUCAO = 1;

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Column(name = "cac_desc")
	private String descricao;

	/**
	 * Flag indicando a administradora de cartão. 0 = Cielo, 1 = Redecard, 2
	 * Pagseguro
	 */
	@Column(name = "cac_adm")
	private int flagAdministradora;

	@Column(name = "cac_chave1")
	private String chave1;

	@Column(name = "cac_chave2")
	private String chave2;

	@Column(name = "cac_chave3")
	private String chave3;

	@Column(name = "cac_idpageset")
	private int idPageSet = 0;

	@Column(name = "cac_expreturl")
	private String sessaoExpiradaRetornoUrl = null;

	@Column(name = "cac_ativo")
	private int flagAtivo;

	@Column(name = "cac_ambiente")
	private int ambiente;

	@Column(name = "cac_diasexptran")
	private int diasExpiraTransacao = 0;

	public CredencialAdministradoraCartao() {
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

	public int getFlagAdministradora() {
		return flagAdministradora;
	}

	public void setFlagAdministradora(int flagAdministradora) {
		this.flagAdministradora = flagAdministradora;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.api.cartao.modelo.requisicao.ICredencial#getAdministradoraCartao
	 * ()
	 */
	@Override
	public AdministradoraCartao getAdministradoraCartao() {
		switch (this.flagAdministradora) {
		case 0:
			return AdministradoraCartao.Cielo;
		case 1:
			return AdministradoraCartao.Rececard;
		case 2:
			return AdministradoraCartao.Pagseguro;
		default:
			break;
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.cartao.modelo.requisicao.ICredencial#getChave1()
	 */
	@Override
	public String getChave1() {
		return chave1;
	}

	public void setChave1(String chave1) {
		this.chave1 = chave1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.cartao.modelo.requisicao.ICredencial#getChave2()
	 */
	@Override
	public String getChave2() {
		return chave2;
	}

	public void setChave2(String chave2) {
		this.chave2 = chave2;
	}

	@Override
	public String getChave3() {
		return chave3;
	}

	public void setChave3(String chave3) {
		this.chave3 = chave3;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	@Override
	public AmbienteTrabalho getAmbienteTrabalhoChave() {

		if (getAmbiente() == AMBIENTE_TESTE) {
			return AmbienteTrabalho.Teste;
		} else {
			return AmbienteTrabalho.Producao;
		}

		/*
		 * if (this.getAdministradoraCartao() == AdministradoraCartao.Cielo &&
		 * ((this.chave1 != null && this.chave1.toLowerCase().equals(
		 * Credencial.CHAVE1_BYPAGECIELO_TESTE)) || (this.chave2 != null &&
		 * this.chave2 .toLowerCase().equals(
		 * Credencial.CHAVE2_BYPAGECIELO_TESTE)))) { return
		 * AmbienteTrabalho.Teste; }
		 * 
		 * return AmbienteTrabalho.Producao;
		 */
	}

	@Override
	public int getIdPageSet() {
		return idPageSet;
	}

	public void setIdPageSet(int idPageSet) {
		this.idPageSet = idPageSet;
	}

	@Override
	public String getSessaoExpiradaRetornoUrl() {
		return sessaoExpiradaRetornoUrl;
	}

	public void setSessaoExpiradaRetornoUrl(String sessaoExpiradaRetornoUrl) {
		this.sessaoExpiradaRetornoUrl = sessaoExpiradaRetornoUrl;
	}

	public int getAmbiente() {
		return ambiente;
	}

	public void setAmbiente(int ambiente) {
		this.ambiente = ambiente;
	}

	public int getDiasExpiraTransacao() {
		return diasExpiraTransacao;
	}

	public void setDiasExpiraTransacao(int diasExpiraTransacao) {
		this.diasExpiraTransacao = diasExpiraTransacao;
	}

	@Override
	public boolean isAdministradoraPagSeguro() {
		return getAdministradoraCartao() == AdministradoraCartao.Pagseguro;
	}

	@Override
	public boolean isAdministradoraCielo() {
		return getAdministradoraCartao() == AdministradoraCartao.Cielo;
	}

	@Override
	public boolean isAdministradoraRedeCard() {
		return getAdministradoraCartao() == AdministradoraCartao.Rececard;
	}

	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	public static CredencialAdministradoraCartao recuperar(GenericoDAO dao,
			AdministradoraCartao administradoraCartao) {

		return recuperar(dao, administradoraCartao.getCodigo());
	}

	public static CredencialAdministradoraCartao recuperar(GenericoDAO dao,
			int codigoAdministradora) {

		String where = "cac_adm = " + codigoAdministradora;
		return dao.uniqueResult(CredencialAdministradoraCartao.class, where,
				null);
	}

	@Override
	public String toString() {
		return "CredencialAdministradoraCartao [codigo=" + codigo
				+ ", descricao=" + descricao + ", flagAdministradora="
				+ flagAdministradora + "]";
	}

}
