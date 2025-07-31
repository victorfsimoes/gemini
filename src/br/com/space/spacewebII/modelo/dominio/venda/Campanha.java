/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Renato
 */
@Entity
@Table(name = "campanha")
@XmlRootElement
public class Campanha implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected CampanhaPK campanhaPK;

	@Column(name = "cmp_nome")
	private String nome = null;

	@Column(name = "cmp_dataini")
	@Temporal(TemporalType.DATE)
	private Date dataInicial = null;

	@Column(name = "cmp_datafim")
	@Temporal(TemporalType.DATE)
	private Date dataFim = null;

	@Column(name = "cmp_obs")
	private String observacao;

	@Column(name = "cmp_pontoval")
	private String pontoOuValor;

	@Column(name = "cmp_valormin")
	private double valorMinimo;

	@Column(name = "cmp_climin")
	private int clienteMinimo;

	@Column(name = "cmp_tipopremio")
	private String tipoPremiacao;

	@Column(name = "cmp_situacao")
	private String situacao;

	@Column(name = "cmp_valorprim")
	private double valorPremioColocado;

	@Column(name = "cmp_valordem")
	private double valorDemaisColocado;

	@Basic(optional = false)
	@Column(name = "cmp_casada")
	private int vendaCasada;

	@Column(name = "cmp_datagrv")
	@Temporal(TemporalType.DATE)
	private Date dataGravacao;

	@Basic(optional = false)
	@Column(name = "cmp_horagrv")
	private String horaGravacao = null;

	@Basic(optional = false)
	@Column(name = "cmp_sescodigo")
	private int sessaoCodigo = 0;

	@Basic(optional = false)
	@Column(name = "cmp_alvo")
	private String publicoAlvo;

	@Basic(optional = false)
	@Column(name = "cmp_numpremio")
	private int numeroPremiacoes;

	@Column(name = "cmp_objvalor")
	private Integer objetivoValor;

	@Column(name = "cmp_objcli")
	private Integer objetivoCliente;

	@Column(name = "cmp_bonusvalor")
	private Integer bonusValor;

	@Column(name = "cmp_bonuscli")
	private Integer bonusCliente;

	@Column(name = "cmp_objvlrponto")
	private Double objetivoValorPonto;

	@Column(name = "cmp_objcliponto")
	private Double objetivoClientePonto;

	@Column(name = "cmp_bonvlrponto")
	private Double bonificacaoValorPonto;

	@Column(name = "cmp_boncliponto")
	private Double bonificaoPontoCliente;

	@Column(name = "cmp_bonacimavlr")
	private Double bomificacaoAcimaValor;

	@Column(name = "cmp_bonacimacli")
	private Double bonificacaoAcimaCliente;

	@Column(name = "cmp_abadev")
	private Integer abaDevolucao;

	@Column(name = "cmp_devperc")
	private Double devolucaoPercentual;

	@Column(name = "cmp_devponto")
	private Double devolucaoPonto;

	@Column(name = "cmp_devpontoper")
	private Double devolucaoPontoPercentual;

	@Column(name = "cmp_codigoori")
	private Integer codigoCampanhaCadastrato;

	@Column(name = "cmp_filcodori")
	private Integer codigoFilialCadastro;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "campanha")
	private List<CampanhaItemPeriodo> campanhaItensPeriodo;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "campanha")
	private List<CampanhaItem> campanhaItens;

	public Campanha() {
	}

	public CampanhaPK getCampanhaPK() {
		return campanhaPK;
	}

	public void setCampanhaPK(CampanhaPK campanhaPK) {
		this.campanhaPK = campanhaPK;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getPontoOuValor() {
		return pontoOuValor;
	}

	public void setPontoOuValor(String pontoOuValor) {
		this.pontoOuValor = pontoOuValor;
	}

	public double getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public int getClienteMinimo() {
		return clienteMinimo;
	}

	public void setClienteMinimo(int clienteMinimo) {
		this.clienteMinimo = clienteMinimo;
	}

	public String getTipoPremiacao() {
		return tipoPremiacao;
	}

	public void setTipoPremiacao(String tipoPremiacao) {
		this.tipoPremiacao = tipoPremiacao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public double getValorPremioColocado() {
		return valorPremioColocado;
	}

	public void setValorPremioColocado(double valorPremioColocado) {
		this.valorPremioColocado = valorPremioColocado;
	}

	public double getValorDemaisColocado() {
		return valorDemaisColocado;
	}

	public void setValorDemaisColocado(double valorDemaisColocado) {
		this.valorDemaisColocado = valorDemaisColocado;
	}

	public int getVendaCasada() {
		return vendaCasada;
	}

	public void setVendaCasada(int vendaCasada) {
		this.vendaCasada = vendaCasada;
	}

	public Date getDataGravacao() {
		return dataGravacao;
	}

	public void setDataGravacao(Date dataGravacao) {
		this.dataGravacao = dataGravacao;
	}

	public String getHoraGravacao() {
		return horaGravacao;
	}

	public void setHoraGravacao(String horaGravacao) {
		this.horaGravacao = horaGravacao;
	}

	public int getSessaoCodigo() {
		return sessaoCodigo;
	}

	public void setSessaoCodigo(int sessaoCodigo) {
		this.sessaoCodigo = sessaoCodigo;
	}

	public String getPublicoAlvo() {
		return publicoAlvo;
	}

	public void setPublicoAlvo(String publicoAlvo) {
		this.publicoAlvo = publicoAlvo;
	}

	public int getNumeroPremiacoes() {
		return numeroPremiacoes;
	}

	public void setNumeroPremiacoes(int numeroPremiacoes) {
		this.numeroPremiacoes = numeroPremiacoes;
	}

	public Integer getObjetivoValor() {
		return objetivoValor;
	}

	public void setObjetivoValor(Integer objetivoValor) {
		this.objetivoValor = objetivoValor;
	}

	public Integer getObjetivoCliente() {
		return objetivoCliente;
	}

	public void setObjetivoCliente(Integer objetivoCliente) {
		this.objetivoCliente = objetivoCliente;
	}

	public Integer getBonusValor() {
		return bonusValor;
	}

	public void setBonusValor(Integer bonusValor) {
		this.bonusValor = bonusValor;
	}

	public Integer getBonusCliente() {
		return bonusCliente;
	}

	public void setBonusCliente(Integer bonusCliente) {
		this.bonusCliente = bonusCliente;
	}

	public Double getObjetivoValorPonto() {
		return objetivoValorPonto;
	}

	public void setObjetivoValorPonto(Double objetivoValorPonto) {
		this.objetivoValorPonto = objetivoValorPonto;
	}

	public Double getObjetivoClientePonto() {
		return objetivoClientePonto;
	}

	public void setObjetivoClientePonto(Double objetivoClientePonto) {
		this.objetivoClientePonto = objetivoClientePonto;
	}

	public Double getBonificacaoValorPonto() {
		return bonificacaoValorPonto;
	}

	public void setBonificacaoValorPonto(Double bonificacaoValorPonto) {
		this.bonificacaoValorPonto = bonificacaoValorPonto;
	}

	public Double getBonificaoPontoCliente() {
		return bonificaoPontoCliente;
	}

	public void setBonificaoPontoCliente(Double bonificaoPontoCliente) {
		this.bonificaoPontoCliente = bonificaoPontoCliente;
	}

	public Double getBomificacaoAcimaValor() {
		return bomificacaoAcimaValor;
	}

	public void setBomificacaoAcimaValor(Double bomificacaoAcimaValor) {
		this.bomificacaoAcimaValor = bomificacaoAcimaValor;
	}

	public Double getBonificacaoAcimaCliente() {
		return bonificacaoAcimaCliente;
	}

	public void setBonificacaoAcimaCliente(Double bonificacaoAcimaCliente) {
		this.bonificacaoAcimaCliente = bonificacaoAcimaCliente;
	}

	public Integer getAbaDevolucao() {
		return abaDevolucao;
	}

	public void setAbaDevolucao(Integer abaDevolucao) {
		this.abaDevolucao = abaDevolucao;
	}

	public Double getDevolucaoPercentual() {
		return devolucaoPercentual;
	}

	public void setDevolucaoPercentual(Double devolucaoPercentual) {
		this.devolucaoPercentual = devolucaoPercentual;
	}

	public Double getDevolucaoPonto() {
		return devolucaoPonto;
	}

	public void setDevolucaoPonto(Double devolucaoPonto) {
		this.devolucaoPonto = devolucaoPonto;
	}

	public Double getDevolucaoPontoPercentual() {
		return devolucaoPontoPercentual;
	}

	public void setDevolucaoPontoPercentual(Double devolucaoPontoPercentual) {
		this.devolucaoPontoPercentual = devolucaoPontoPercentual;
	}

	public Integer getCodigoCampanhaCadastrato() {
		return codigoCampanhaCadastrato;
	}

	public void setCodigoCampanhaCadastrato(Integer codigoCampanhaCadastrato) {
		this.codigoCampanhaCadastrato = codigoCampanhaCadastrato;
	}

	public Integer getCodigoFilialCadastro() {
		return codigoFilialCadastro;
	}

	public void setCodigoFilialCadastro(Integer codigoFilialCadastro) {
		this.codigoFilialCadastro = codigoFilialCadastro;
	}

	public List<CampanhaItemPeriodo> getCampanhaItensPeriodo() {
		return campanhaItensPeriodo;
	}

	public void setCampanhaItensPeriodo(
			List<CampanhaItemPeriodo> campanhaItensPeriodo) {
		this.campanhaItensPeriodo = campanhaItensPeriodo;
	}

	public List<CampanhaItem> getCampanhaItens() {
		return campanhaItens;
	}

	public void setCampanhaItens(List<CampanhaItem> campanhaItens) {
		this.campanhaItens = campanhaItens;
	}
}
