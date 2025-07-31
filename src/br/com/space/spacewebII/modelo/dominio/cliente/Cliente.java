/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.cliente;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.negocio.modelo.dominio.ICliente;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.endereco.Regiao;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.pessoa.CarteiraCliente;
import br.com.space.spacewebII.modelo.dominio.pessoa.Pessoa;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "cliente")
@XmlRootElement
public class Cliente implements Serializable, ICliente, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "CLI_PESCODIGO";
	public static final String COLUNA_RAZAOSOCIAL = "PES_RAZAOSOC";
	public static final String COLUNA_CARTEIRA_INTERNA = "cli_cclcodigoi";
	public static final String COLUNA_CARTEIRA_EXTERNA = "cli_cclcodigoe";
	public static final String COLUNA_CHAVENOVASENHA = "cli_chavenovsen";
	private static final String COLUNA_CHAVECONFIRMACAOEMAIL = "cli_confemail";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int pessoaCodigo;

	@Column(name = "cli_seqent")
	private Integer sequenciaEntrega;

	@Column(name = "cli_obsent")
	private String observacaoEntrega;

	@Column(name = "cli_debcre")
	private int flagDebitoCredito;

	@Column(name = "cli_bloqfin")
	private int flagBloqueadoFinanceiro;

	@Column(name = "cli_dtpricom")
	@Temporal(TemporalType.DATE)
	private Date dataPrimeiraCompra;

	@Column(name = "cli_dtultcom")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaCompra;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "cli_valultcom")
	private double valorUltimaCompra;

	@Column(name = "cli_prazoespe")
	private int prazoEspecial;

	// @Column(name = "cli_limitecre")
	// private double valorLimiteCredito;

	@Column(name = "cli_dtultalt")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaAlteracao;

	// @Column(name = "cli_valdebito")
	// private double valorDebitoCliente;

	@Column(name = "cli_dtultdeb")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaAlteracaoDebito;

	@Column(name = "cli_hrultdeb")
	private String horaUltimaAlteracaoDebito;

	// @Column(name = "cli_limitead")
	// private double valorLimiteAdicional;

	@Transient
	private double valorDebito;

	@Transient
	private double limiteCredito;

	@Transient
	private double limiteAdicional;

	@Transient
	private double valorDebitoPrevisto;

	@Column(name = "cli_grccodigo")
	private Integer GrupoClienteCodigo;

	@Column(name = "cli_dtultaltlim")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaAlteracaoLimite;

	@Column(name = "cli_dtbloqueio")
	@Temporal(TemporalType.DATE)
	private Date dataBloqueio;

	@Column(name = "cli_nacional")
	private String nacionalidade;

	@Column(name = "cli_natural")
	private String naturalidade;

	@Column(name = "cli_codbarras")
	private String codigoBarras;

	@Column(name = "cli_pontos")
	private Double pontos;

	@Column(name = "cli_numdepende")
	private Integer numeroDependentes;

	@Column(name = "cli_pai")
	private String nomePai;

	@Column(name = "cli_mae")
	private String nomeMae;

	@Column(name = "cli_razaoem")
	private String razaoSocialEmpregador;

	@Column(name = "cli_endem")
	private String enderecoEmpregador;

	@Column(name = "cli_numeroem")
	private String numeroEmpregador;

	@Column(name = "cli_baicodigoem")
	private Integer bairroEmpregadorCodigo;

	@Column(name = "cli_cidcodigoem")
	private Integer cidadeEmpregadorCodigo;

	@Column(name = "cli_ufsiglaem")
	private String ufEmpregador;

	@Column(name = "cli_cepem")
	private String cepEmpregador;

	@Column(name = "cli_cargoem")
	private String cargoEmprego;

	@Column(name = "cli_salarioem")
	private Double salarioEmprego;

	@Column(name = "cli_fone1em")
	private String fone1Empregador;

	@Column(name = "cli_fone2em")
	private String fone2Empregador;

	@Column(name = "cli_nomeco")
	private String nomeConjuge;

	@Column(name = "cli_razaoco")
	private String razaoSocialConjuge;

	@Column(name = "cli_endco")
	private String enderecoConjuge;

	@Column(name = "cli_numeroco")
	private String numeroConjuge;

	@Column(name = "cli_baicodigoco")
	private Integer bairroConjugeCodigo;

	@Column(name = "cli_cidcodigoco")
	private Integer cidadeConjugeCodigo;

	@Column(name = "cli_ufsiglaco")
	private String ufConjuge;

	@Column(name = "cli_fone1co")
	private String fone1Conjuge;

	@Column(name = "cli_fone2co")
	private String fone2Conjuge;

	@Column(name = "cli_celularco")
	private String celularConjuge;

	@Column(name = "cli_cargoco")
	private String cargoConjuge;

	@Column(name = "cli_cepco")
	private String cepConjuge;

	@Column(name = "cli_salarioco")
	private Double salarioConjuge;

	@Column(name = "cli_freq")
	private String frequenciaVisitacao;

	/*
	 * @Column(name = "CLI_FREQMES") private String frequenciaMensal;
	 */

	@Column(name = "cli_domvis")
	private Integer flagVisitaDomingo;

	@Column(name = "cli_segvis")
	private Integer flagVisitaSegunda;

	@Column(name = "cli_tervis")
	private Integer flagVisitaTerca;

	@Column(name = "cli_quavis")
	private Integer flagVisitaQuarta;

	@Column(name = "cli_quivis")
	private Integer flagVisitaQuinta;

	@Column(name = "cli_sexvis")
	private Integer flagVisitaSexta;

	@Column(name = "cli_sabvis")
	private Integer flagVisitaSabado;

	@Column(name = "cli_horinivis")
	private String horaInicialVisita;

	@Column(name = "cli_horfimvis")
	private String horaFinalVisita;

	@Column(name = "cli_datainivis")
	@Temporal(TemporalType.DATE)
	private Date dataInicioVisitacao;

	@Column(name = "cli_diamesvis")
	private Integer diaMesVisitacao;

	@Column(name = "cli_semmesvis")
	private String semanaMesVisitacao;

	@Column(name = "cli_freqsemvis")
	private Integer frequenciaSemanalVisitacao;

	@Column(name = "cli_descmaximo")
	private Double descontoMaximo;

	@Column(name = "cli_acrmaximo")
	private Double acrescimoMaximo;

	@Column(name = "cli_descesp")
	private Double descontoEspecial;

	@Column(name = "cli_acreesp")
	private Double acrescimoEspecial;

	@Column(name = "cli_situac1")
	private String situacao1;

	@Column(name = "cli_situac2")
	private String situacao2;

	@Column(name = "cli_situac3")
	private String situacao3;

	@Column(name = "cli_dtsituac")
	@Temporal(TemporalType.DATE)
	private Date dataAlteracaoSituacao;

	@Column(name = "cli_hrsituac")
	private String horaAlteracaoSituacao;

	@Column(name = "cli_tprcodigo")
	private int tabelaPrecoCodigoGuardian;

	@Column(name = "cli_tprcodigopr")
	private int tabelaPrecoPromocionalCodigoGuardian;

	@Column(name = "cli_tprpadgemin")
	private int tabelaPrecoPadraoGemini;

	// @Column(name = "cli_valdebitopr")
	// private double valorDebitoPrevistoCliente;

	@Column(name = "cli_situac4")
	private String situacao4;

	@Column(name = "cli_hrultalt")
	private String horaUltimaAlteracao;

	@Column(name = "cli_limitecrant")
	private Double limiteCreditoAnterior;

	@Column(name = "cli_opcesp")
	private Integer opcaoEspecialCodigo;

	@Column(name = "cli_cctcodigo")
	private Integer contaCorrenteCodigo;

	@Column(name = "cli_dtultconsin")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaConsultaSintegra;

	@Column(name = "cli_taxaboleta")
	private Integer flagTaxaBoleta;

	@Column(name = "cli_prazomax")
	private Integer prazoMaximo;

	@Column(name = "cli_bloqpend")
	private Integer flagBloqueioPedidoPendente;

	@Column(name = "cli_pescodigot")
	private Integer transportadorCodigo;

	@Column(name = "cli_pta")
	private String pta;

	@Column(name = "cli_ngpcodigo")
	private Integer NegociacaoPrecoCodigo;

	@Column(name = "cli_emailfinan")
	private String emailFinanceiro;

	@Column(name = "cli_ncalcipi")
	private Integer flagNaoCalculaIpi;

	@Column(name = "cli_obscobr")
	private String observacaoCobranca;

	@Column(name = "cli_natPadGemin")
	private String naturezaOperacao;

	@Column(name = "cli_ordcompnfe")
	private int flagInformaOrigemVendaNoXml = 0;

	@OneToOne(optional = false)
	@JoinColumn(name = "cli_pescodigo", referencedColumnName = "pes_codigo", insertable = false, updatable = false)
	private Pessoa pessoa;

	@ManyToOne(optional = false)
	@JoinColumn(name = "cli_fpgcodigo", referencedColumnName = "fpg_codigo")
	@NotFound(action = NotFoundAction.IGNORE)
	private FormaPagamento formaPagamento;

	@ManyToOne(optional = true)
	@JoinColumn(name = "cli_cpgcodigo", referencedColumnName = "cpg_codigo")
	@NotFound(action = NotFoundAction.IGNORE)
	private CondicaoPagamento condicaoPagamento;

	@ManyToOne(optional = true)
	@JoinColumn(name = "cli_fpgcodwebcl", referencedColumnName = "fpg_codigo")
	@NotFound(action = NotFoundAction.IGNORE)
	private FormaPagamento formaPagamentoModoCliente;

	@ManyToOne
	@JoinColumn(name = "cli_cpgcodwebcl", referencedColumnName = "cpg_codigo")
	@NotFound(action = NotFoundAction.IGNORE)
	private CondicaoPagamento condicaoPagamentoModoCliente;

	@ManyToOne
	@JoinColumn(name = "cli_cclcodigoi", referencedColumnName = "ccl_codigo")
	@NotFound(action = NotFoundAction.IGNORE)
	private CarteiraCliente carteiraClienteInterna;

	@ManyToOne
	@JoinColumn(name = "cli_cclcodigoe", referencedColumnName = "ccl_codigo")
	@NotFound(action = NotFoundAction.IGNORE)
	private CarteiraCliente carteiraClienteExterna;

	@ManyToOne
	@JoinColumn(name = "cli_regcodigo", referencedColumnName = "reg_codigo")
	@NotFound(action = NotFoundAction.IGNORE)
	private Regiao regiao;

	@Column(name = "cli_acessoweb")
	private Integer acessoWeb;

	@Column(name = "cli_loginweb")
	private String emailLoginWeb;

	@Column(name = "cli_senhaWeb")
	private String senhaWeb;

	@Column(name = COLUNA_CHAVECONFIRMACAOEMAIL)
	private String chaveConfirmacaoEmail;

	@Column(name = COLUNA_CHAVENOVASENHA)
	private String chaveNovaSenha;

	@Column(name = "cli_prepago")
	private int flagClientePrePago;

	public Cliente() {
	}

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public Regiao getRegiao() {
		return regiao;
	}

	public void setRegiao(Regiao regiao) {
		this.regiao = regiao;
	}

	public Integer getSequenciaEntrega() {
		return sequenciaEntrega;
	}

	public void setSequenciaEntrega(Integer sequenciaEntrega) {
		this.sequenciaEntrega = sequenciaEntrega;
	}

	public String getObservacaoEntrega() {
		return observacaoEntrega;
	}

	public void setObservacaoEntrega(String observacaoEntrega) {
		this.observacaoEntrega = observacaoEntrega;
	}

	public int getFlagDebitoCredito() {
		return flagDebitoCredito;
	}

	public void setFlagDebitoCredito(int flagDebitoCredito) {
		this.flagDebitoCredito = flagDebitoCredito;
	}

	public int getFlagBloqueadoFinanceiro() {
		return flagBloqueadoFinanceiro;
	}

	public void setFlagBloqueadoFinanceiro(int flagBloqueadoFinanceiro) {
		this.flagBloqueadoFinanceiro = flagBloqueadoFinanceiro;
	}

	@Override
	public Date getDataPrimeiraCompra() {
		return dataPrimeiraCompra;
	}

	@Override
	public void setDataPrimeiraCompra(Date dataPrimeiraCompra) {
		this.dataPrimeiraCompra = dataPrimeiraCompra;
	}

	@Override
	public Date getDataUltimaCompra() {
		return dataUltimaCompra;
	}

	@Override
	public void setDataUltimaCompra(Date dataUltimaCompra) {
		this.dataUltimaCompra = dataUltimaCompra;
	}

	@Override
	public double getValorUltimaCompra() {
		return valorUltimaCompra;
	}

	@Override
	public void setValorUltimaCompra(double valorUltimaCompra) {
		this.valorUltimaCompra = valorUltimaCompra;
	}

	public int getPrazoEspecial() {
		return prazoEspecial;
	}

	public void setPrazoEspecial(Integer prazoEspecial) {
		this.prazoEspecial = prazoEspecial;
	}

	public double getLimiteCredito() {
		return limiteCredito;
	}

	public void setLimiteCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public double getValorDebito() {
		return valorDebito;
	}

	public void setValorDebito(double valorDebito) {
		this.valorDebito = valorDebito;
	}

	public Date getDataUltimaAlteracaoDebito() {
		return dataUltimaAlteracaoDebito;
	}

	public void setDataUltimaAlteracaoDebito(Date dataUltimaAlteracaoDebito) {
		this.dataUltimaAlteracaoDebito = dataUltimaAlteracaoDebito;
	}

	public String getHoraUltimaAlteracaoDebito() {
		return horaUltimaAlteracaoDebito;
	}

	public void setHoraUltimaAlteracaoDebito(String horaUltimaAlteracaoDebito) {
		this.horaUltimaAlteracaoDebito = horaUltimaAlteracaoDebito;
	}

	public double getLimiteAdicional() {
		return limiteAdicional;
	}

	public void setLimiteAdicional(double limiteAdicional) {
		this.limiteAdicional = limiteAdicional;
	}

	public Integer getGrupoClienteCodigo() {
		return GrupoClienteCodigo;
	}

	public void setGrupoClienteCodigo(Integer grupoClienteCodigo) {
		GrupoClienteCodigo = grupoClienteCodigo;
	}

	public Date getDataUltimaAlteracaoLimite() {
		return dataUltimaAlteracaoLimite;
	}

	public void setDataUltimaAlteracaoLimite(Date dataUltimaAlteracaoLimite) {
		this.dataUltimaAlteracaoLimite = dataUltimaAlteracaoLimite;
	}

	public Date getDataBloqueio() {
		return dataBloqueio;
	}

	public void setDataBloqueio(Date dataBloqueio) {
		this.dataBloqueio = dataBloqueio;
	}

	public String getNacionalidade() {
		return nacionalidade;
	}

	public void setNacionalidade(String nacionalidade) {
		this.nacionalidade = nacionalidade;
	}

	public String getNaturalidade() {
		return naturalidade;
	}

	public void setNaturalidade(String naturalidade) {
		this.naturalidade = naturalidade;
	}

	public String getCodigoBarras() {
		return codigoBarras;
	}

	public void setCodigoBarras(String codigoBarras) {
		this.codigoBarras = codigoBarras;
	}

	public Double getPontos() {
		return pontos;
	}

	public void setPontos(Double pontos) {
		this.pontos = pontos;
	}

	public Integer getNumeroDependentes() {
		return numeroDependentes;
	}

	public void setNumeroDependentes(Integer numeroDependentes) {
		this.numeroDependentes = numeroDependentes;
	}

	public String getNomePai() {
		return nomePai;
	}

	public void setNomePai(String nomePai) {
		this.nomePai = nomePai;
	}

	public String getNomeMae() {
		return nomeMae;
	}

	public void setNomeMae(String nomeMae) {
		this.nomeMae = nomeMae;
	}

	public String getRazaoSocialEmpregador() {
		return razaoSocialEmpregador;
	}

	public void setRazaoSocialEmpregador(String razaoSocialEmpregador) {
		this.razaoSocialEmpregador = razaoSocialEmpregador;
	}

	public String getEnderecoEmpregador() {
		return enderecoEmpregador;
	}

	public void setEnderecoEmpregador(String enderecoEmpregador) {
		this.enderecoEmpregador = enderecoEmpregador;
	}

	public String getNumeroEmpregador() {
		return numeroEmpregador;
	}

	public void setNumeroEmpregador(String numeroEmpregador) {
		this.numeroEmpregador = numeroEmpregador;
	}

	public Integer getBairroEmpregadorCodigo() {
		return bairroEmpregadorCodigo;
	}

	public void setBairroEmpregadorCodigo(Integer bairroEmpregadorCodigo) {
		this.bairroEmpregadorCodigo = bairroEmpregadorCodigo;
	}

	public Integer getCidadeEmpregadorCodigo() {
		return cidadeEmpregadorCodigo;
	}

	public void setCidadeEmpregadorCodigo(Integer cidadeEmpregadorCodigo) {
		this.cidadeEmpregadorCodigo = cidadeEmpregadorCodigo;
	}

	public String getUfEmpregador() {
		return ufEmpregador;
	}

	public void setUfEmpregador(String ufEmpregador) {
		this.ufEmpregador = ufEmpregador;
	}

	public String getCepEmpregador() {
		return cepEmpregador;
	}

	public void setCepEmpregador(String cepEmpregador) {
		this.cepEmpregador = cepEmpregador;
	}

	public String getCargoEmprego() {
		return cargoEmprego;
	}

	public void setCargoEmprego(String cargoEmprego) {
		this.cargoEmprego = cargoEmprego;
	}

	public Double getSalarioEmprego() {
		return salarioEmprego;
	}

	public void setSalarioEmprego(Double salarioEmprego) {
		this.salarioEmprego = salarioEmprego;
	}

	public String getFone1Empregador() {
		return fone1Empregador;
	}

	public void setFone1Empregador(String fone1Empregador) {
		this.fone1Empregador = fone1Empregador;
	}

	public String getFone2Empregador() {
		return fone2Empregador;
	}

	public void setFone2Empregador(String fone2Empregador) {
		this.fone2Empregador = fone2Empregador;
	}

	public String getNomeConjuge() {
		return nomeConjuge;
	}

	public void setNomeConjuge(String nomeConjuge) {
		this.nomeConjuge = nomeConjuge;
	}

	public String getRazaoSocialConjuge() {
		return razaoSocialConjuge;
	}

	public void setRazaoSocialConjuge(String razaoSocialConjuge) {
		this.razaoSocialConjuge = razaoSocialConjuge;
	}

	public String getEnderecoConjuge() {
		return enderecoConjuge;
	}

	public void setEnderecoConjuge(String enderecoConjuge) {
		this.enderecoConjuge = enderecoConjuge;
	}

	public String getNumeroConjuge() {
		return numeroConjuge;
	}

	public void setNumeroConjuge(String numeroConjuge) {
		this.numeroConjuge = numeroConjuge;
	}

	public Integer getBairroConjugeCodigo() {
		return bairroConjugeCodigo;
	}

	public void setBairroConjugeCodigo(Integer bairroConjugeCodigo) {
		this.bairroConjugeCodigo = bairroConjugeCodigo;
	}

	public Integer getCidadeConjugeCodigo() {
		return cidadeConjugeCodigo;
	}

	public void setCidadeConjugeCodigo(Integer cidadeConjugeCodigo) {
		this.cidadeConjugeCodigo = cidadeConjugeCodigo;
	}

	public String getUfConjuge() {
		return ufConjuge;
	}

	public void setUfConjuge(String ufConjuge) {
		this.ufConjuge = ufConjuge;
	}

	public String getFone1Conjuge() {
		return fone1Conjuge;
	}

	public void setFone1Conjuge(String fone1Conjuge) {
		this.fone1Conjuge = fone1Conjuge;
	}

	public String getFone2Conjuge() {
		return fone2Conjuge;
	}

	public void setFone2Conjuge(String fone2Conjuge) {
		this.fone2Conjuge = fone2Conjuge;
	}

	public String getCelularConjuge() {
		return celularConjuge;
	}

	public void setCelularConjuge(String celularConjuge) {
		this.celularConjuge = celularConjuge;
	}

	public String getCargoConjuge() {
		return cargoConjuge;
	}

	public void setCargoConjuge(String cargoConjuge) {
		this.cargoConjuge = cargoConjuge;
	}

	public String getCepConjuge() {
		return cepConjuge;
	}

	public void setCepConjuge(String cepConjuge) {
		this.cepConjuge = cepConjuge;
	}

	public Double getSalarioConjuge() {
		return salarioConjuge;
	}

	public void setSalarioConjuge(Double salarioConjuge) {
		this.salarioConjuge = salarioConjuge;
	}

	public String getFrequenciaVisitacao() {
		return frequenciaVisitacao;
	}

	public void setFrequenciaVisitacao(String frequenciaVisitacao) {
		this.frequenciaVisitacao = frequenciaVisitacao;
	}

	public Integer getFlagVisitaDomingo() {
		return flagVisitaDomingo;
	}

	public void setFlagVisitaDomingo(Integer flagVisitaDomingo) {
		this.flagVisitaDomingo = flagVisitaDomingo;
	}

	public Integer getFlagVisitaSegunda() {
		return flagVisitaSegunda;
	}

	public void setFlagVisitaSegunda(Integer flagVisitaSegunda) {
		this.flagVisitaSegunda = flagVisitaSegunda;
	}

	public Integer getFlagVisitaTerca() {
		return flagVisitaTerca;
	}

	public void setFlagVisitaTerca(Integer flagVisitaTerca) {
		this.flagVisitaTerca = flagVisitaTerca;
	}

	public Integer getFlagVisitaQuarta() {
		return flagVisitaQuarta;
	}

	public void setFlagVisitaQuarta(Integer flagVisitaQuarta) {
		this.flagVisitaQuarta = flagVisitaQuarta;
	}

	public Integer getFlagVisitaQuinta() {
		return flagVisitaQuinta;
	}

	public void setFlagVisitaQuinta(Integer flagVisitaQuinta) {
		this.flagVisitaQuinta = flagVisitaQuinta;
	}

	public Integer getFlagVisitaSexta() {
		return flagVisitaSexta;
	}

	public void setFlagVisitaSexta(Integer flagVisitaSexta) {
		this.flagVisitaSexta = flagVisitaSexta;
	}

	public Integer getFlagVisitaSabado() {
		return flagVisitaSabado;
	}

	public void setFlagVisitaSabado(Integer flagVisitaSabado) {
		this.flagVisitaSabado = flagVisitaSabado;
	}

	public String getHoraInicialVisita() {
		return horaInicialVisita;
	}

	public void setHoraInicialVisita(String horaInicialVisita) {
		this.horaInicialVisita = horaInicialVisita;
	}

	public String getHoraFinalVisita() {
		return horaFinalVisita;
	}

	public void setHoraFinalVisita(String horaFinalVisita) {
		this.horaFinalVisita = horaFinalVisita;
	}

	public Date getDataInicioVisitacao() {
		return dataInicioVisitacao;
	}

	public void setDataInicioVisitacao(Date dataInicioVisitacao) {
		this.dataInicioVisitacao = dataInicioVisitacao;
	}

	public Integer getDiaMesVisitacao() {
		return diaMesVisitacao;
	}

	public void setDiaMesVisitacao(Integer diaMesVisitacao) {
		this.diaMesVisitacao = diaMesVisitacao;
	}

	public String getSemanaMesVisitacao() {
		return semanaMesVisitacao;
	}

	public void setSemanaMesVisitacao(String semanaMesVisitacao) {
		this.semanaMesVisitacao = semanaMesVisitacao;
	}

	public Integer getFrequenciaSemanalVisitacao() {
		return frequenciaSemanalVisitacao;
	}

	public void setFrequenciaSemanalVisitacao(Integer frequenciaSemanalVisitacao) {
		this.frequenciaSemanalVisitacao = frequenciaSemanalVisitacao;
	}

	public double getDescontoMaximo() {
		return descontoMaximo;
	}

	public double getAcrescimoMaximo() {
		return acrescimoMaximo;
	}

	public void setAcrescicmoMaximo(Double acrescicmoMaximo) {
		this.acrescimoMaximo = acrescicmoMaximo;
	}

	public Double getDescontoEspecial() {
		return descontoEspecial;
	}

	public void setDescontoEspecial(Double descontoEspecial) {
		this.descontoEspecial = descontoEspecial;
	}

	public Double getAcrescimoEspecial() {
		return acrescimoEspecial;
	}

	public void setAcrescimoEspecial(Double acrescimoEspecial) {
		this.acrescimoEspecial = acrescimoEspecial;
	}

	public String getSituacao1() {
		return situacao1;
	}

	public void setSituacao1(String situacao1) {
		this.situacao1 = situacao1;
	}

	public String getSituacao2() {
		return situacao2;
	}

	public void setSituacao2(String situacao2) {
		this.situacao2 = situacao2;
	}

	public String getSituacao3() {
		return situacao3;
	}

	public void setSituacao3(String situacao3) {
		this.situacao3 = situacao3;
	}

	public Date getDataAlteracaoSituacao() {
		return dataAlteracaoSituacao;
	}

	public void setDataAlteracaoSituacao(Date dataAlteracaoSituacao) {
		this.dataAlteracaoSituacao = dataAlteracaoSituacao;
	}

	public String getHoraAlteracaoSituacao() {
		return horaAlteracaoSituacao;
	}

	public void setHoraAlteracaoSituacao(String horaAlteracaoSituacao) {
		this.horaAlteracaoSituacao = horaAlteracaoSituacao;
	}

	@Override
	public int getTabelaPrecoCodigo() {

		if (getTabelaPrecoPadraoGemini() > 0) {
			return getTabelaPrecoPadraoGemini();
		}

		return getTabelaPrecoCodigoGuardian();
	}

	@Override
	public int getTabelaPrecoPromocionalCodigo() {

		if (getTabelaPrecoPadraoGemini() > 0) {
			return getTabelaPrecoPadraoGemini();
		}

		return getTabelaPrecoPromocionalCodigoGuardian();
	}

	public int getTabelaPrecoCodigoGuardian() {
		return tabelaPrecoCodigoGuardian;
	}

	public void setTabelaPrecoCodigoGuardian(int tabelaPrecoCodigoGuardian) {
		this.tabelaPrecoCodigoGuardian = tabelaPrecoCodigoGuardian;
	}

	public int getTabelaPrecoPromocionalCodigoGuardian() {
		return tabelaPrecoPromocionalCodigoGuardian;
	}

	public void setTabelaPrecoPromocionalCodigoGuardian(int tabelaPrecoPromocionalCodigoGuardian) {
		this.tabelaPrecoPromocionalCodigoGuardian = tabelaPrecoPromocionalCodigoGuardian;
	}

	public int getTabelaPrecoPadraoGemini() {
		return tabelaPrecoPadraoGemini;
	}

	public void setTabelaPrecoPadraoGemini(int tabelaPrecoPadraoGemini) {
		this.tabelaPrecoPadraoGemini = tabelaPrecoPadraoGemini;
	}

	public double getValorDebitoPrevisto() {
		return valorDebitoPrevisto;
	}

	public void setValorDebitoPrevisto(double valorDebitoPrevisto) {
		this.valorDebitoPrevisto = valorDebitoPrevisto;
	}

	public String getSituacao4() {
		return situacao4;
	}

	public void setSituacao4(String situacao4) {
		this.situacao4 = situacao4;
	}

	public String getHoraUltimaAlteracao() {
		return horaUltimaAlteracao;
	}

	public void setHoraUltimaAlteracao(String horaUltimaAlteracao) {
		this.horaUltimaAlteracao = horaUltimaAlteracao;
	}

	public Double getLimiteCreditoAnterior() {
		return limiteCreditoAnterior;
	}

	public void setLimiteCreditoAnterior(Double limiteCreditoAnterior) {
		this.limiteCreditoAnterior = limiteCreditoAnterior;
	}

	public int getOpcaoEspecialCodigo() {
		return opcaoEspecialCodigo;
	}

	public void setOpcaoEspecialCodigo(Integer opcaoEspecialCodigo) {
		this.opcaoEspecialCodigo = opcaoEspecialCodigo;
	}

	public Integer getContaCorrenteCodigo() {
		return contaCorrenteCodigo;
	}

	public void setContaCorrenteCodigo(Integer contaCorrenteCodigo) {
		this.contaCorrenteCodigo = contaCorrenteCodigo;
	}

	public Date getDataUltimaConsultaSintegra() {
		return dataUltimaConsultaSintegra;
	}

	public void setDataUltimaConsultaSintegra(Date dataUltimaConsultaSintegra) {
		this.dataUltimaConsultaSintegra = dataUltimaConsultaSintegra;
	}

	public Integer getFlagTaxaBoleta() {
		return flagTaxaBoleta;
	}

	public void setFlagTaxaBoleta(Integer flagTaxaBoleta) {
		this.flagTaxaBoleta = flagTaxaBoleta;
	}

	public int getPrazoMaximo() {
		if (prazoMaximo == null)
			return 0;
		return prazoMaximo;
	}

	public void setPrazoMaximo(Integer prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	public Integer getFlagBloqueioPedidoPendente() {
		return flagBloqueioPedidoPendente;
	}

	public void setFlagBloqueioPedidoPendente(Integer flagBloqueioPedidoPendente) {
		this.flagBloqueioPedidoPendente = flagBloqueioPedidoPendente;
	}

	public Integer getTransportadorCodigo() {
		return transportadorCodigo;
	}

	public void setTransportadorCodigo(Integer transportadorCodigo) {
		this.transportadorCodigo = transportadorCodigo;
	}

	public String getPta() {
		return pta;
	}

	public void setPta(String pta) {
		this.pta = pta;
	}

	public Integer getNegociacaoPrecoCodigo() {
		return NegociacaoPrecoCodigo;
	}

	public void setNegociacaoPrecoCodigo(Integer negociacaoPrecoCodigo) {
		NegociacaoPrecoCodigo = negociacaoPrecoCodigo;
	}

	public String getEmailFinanceiro() {
		return emailFinanceiro;
	}

	public void setEmailFinanceiro(String emailFinanceiro) {
		this.emailFinanceiro = emailFinanceiro;
	}

	public Integer getFlagNaoCalculaIpi() {
		return flagNaoCalculaIpi;
	}

	public void setFlagNaoCalculaIpi(Integer flagNaoCalculaIpi) {
		this.flagNaoCalculaIpi = flagNaoCalculaIpi;
	}

	public String getObservacaoCobranca() {
		return observacaoCobranca;
	}

	public void setObservacaoCobranca(String observacaoCobranca) {
		this.observacaoCobranca = observacaoCobranca;
	}

	public String getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(String naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	public int getFlagInformaOrigemVendaNoXml() {
		return flagInformaOrigemVendaNoXml;
	}

	public void setFlagInformaOrigemVendaNoXml(int flagInformaOrigemVendaNoXml) {
		this.flagInformaOrigemVendaNoXml = flagInformaOrigemVendaNoXml;
	}

	public static String getColunaCodigo() {
		return COLUNA_CODIGO;
	}

	public static String getColunaRazaosocial() {
		return COLUNA_RAZAOSOCIAL;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public FormaPagamento getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamento formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public CondicaoPagamento getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
	}

	public FormaPagamento getFormaPagamentoModoCliente() {
		return formaPagamentoModoCliente;
	}

	public void setFormaPagamentoModoCliente(FormaPagamento formaPagamentoModoCliente) {
		this.formaPagamentoModoCliente = formaPagamentoModoCliente;
	}

	public CondicaoPagamento getCondicaoPagamentoModoCliente() {
		return condicaoPagamentoModoCliente;
	}

	public void setCondicaoPagamentoModoCliente(CondicaoPagamento condicaoPagamentoModoCliente) {
		this.condicaoPagamentoModoCliente = condicaoPagamentoModoCliente;
	}

	public CarteiraCliente getCarteiraClienteInterna() {
		return carteiraClienteInterna;
	}

	public void setCarteiraClienteInterna(CarteiraCliente carteiraClienteInterna) {
		this.carteiraClienteInterna = carteiraClienteInterna;
	}

	public CarteiraCliente getCarteiraClienteExterna() {
		return carteiraClienteExterna;
	}

	public void setCarteiraClienteExterna(CarteiraCliente carteiraClienteExterna) {
		this.carteiraClienteExterna = carteiraClienteExterna;
	}

	public int getFlagClientePrePago() {
		return flagClientePrePago;
	}

	public void setFlagClientePrePago(int flagClientePrePago) {
		this.flagClientePrePago = flagClientePrePago;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	@Override
	public String getCnpjCpf() {
		return this.getPessoa().getCnpjCpf();
	}

	@Override
	public int getCodigo() {
		return this.getPessoaCodigo();
	}

	@Override
	public String getInscricaoEstadual() {
		return this.getPessoa().getInscricaoEstadual();
	}

	@Override
	public String getNomeFantasia() {
		return this.getPessoa().getFantasia();
	}

	@Override
	public double getPercentualAcrescimoEspecial() {
		return this.getAcrescimoEspecial();
	}

	@Override
	public double getPercentualDescontoEspecial() {
		return this.getDescontoEspecial();
	}

	@Override
	public String getRazao() {
		return this.getPessoa().getRazao();
	}

	public String getRazao(ParametroWeb parametroWeb) {
		if (parametroWeb.isFlagExibeFantasiaCliente()) {
			return getNomeFantasia();
		} else {
			return getRazao();
		}
	}

	@Override
	public String getRg() {
		return this.getPessoa().getRg();
	}

	@Override
	public String getTipoPessoa() {
		return this.getPessoa().getTipoPessoa();
	}

	@Override
	public String getUfSigla() {
		return null;
	}

	@Override
	public double getValorCredito() {
		return this.getLimiteCredito();
	}

	@Override
	public void setAcrescimoMaximo(double arg0) {
		this.acrescimoMaximo = arg0;
	}

	@Override
	public void setCnpjCpf(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setCodigo(int arg0) {
		this.pessoaCodigo = arg0;
	}

	@Override
	public void setDescontoMaximo(double arg0) {
		this.descontoMaximo = arg0;
	}

	@Override
	public void setInscricaoEstadual(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setNomeFantasia(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setOpcaoEspecialCodigo(int arg0) {
		this.opcaoEspecialCodigo = arg0;
	}

	@Override
	public void setPercentualAcrescimoEspecial(double arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setPercentualDescontoEspecial(double arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setRazao(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setRg(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setTipoPessoa(String arg0) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setValorCredito(double limiteCredito) {
		this.limiteCredito = limiteCredito;
	}

	public String getSenhaWeb() {
		return senhaWeb;
	}

	public void setSenhaWeb(String senhaWeb) {
		this.senhaWeb = senhaWeb;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public int getTabelaPrecoInternaCodigo() {
		if (this.getCarteiraClienteInterna() == null) {
			return 0;
		}
		return this.getCarteiraClienteInterna().getTabelaPrecoCodigo();
	}

	@Override
	public int getTabelaPrecoInternaPromocionalCodigo() {
		if (this.getCarteiraClienteInterna() == null) {
			return 0;
		}
		return this.getCarteiraClienteInterna().getTabelaPrecoPromocionalCodigo();
	}

	@Override
	public int getTabelaPrecoExternaCodigo() {
		if (this.getCarteiraClienteExterna() == null) {
			return 0;
		}
		return this.getCarteiraClienteExterna().getTabelaPrecoCodigo();
	}

	@Override
	public int getTabelaPrecoExternaPromocionalCodigo() {
		if (this.getCarteiraClienteExterna() == null) {
			return 0;
		}
		return this.getCarteiraClienteExterna().getTabelaPrecoCodigo();
	}

	public String getEmailLoginWeb() {
		return emailLoginWeb;
	}

	public void setEmailLoginWeb(String emailLoginWeb) {
		this.emailLoginWeb = emailLoginWeb;
	}

	public String getChaveNovaSenha() {
		return chaveNovaSenha;
	}

	public void setChaveNovaSenha(String chaveNovaSenha) {
		this.chaveNovaSenha = chaveNovaSenha;
	}

	public String getChaveConfirmacaoEmail() {
		return chaveConfirmacaoEmail;
	}

	public void setChaveConfirmacaoEmail(String chaveConfirmacaoEmail) {
		this.chaveConfirmacaoEmail = chaveConfirmacaoEmail;
	}

	public Integer getAcessoWeb() {

		if (acessoWeb == null)
			return 0;

		return acessoWeb;
	}

	public void setAcessoWeb(Integer acessoWeb) {
		this.acessoWeb = acessoWeb;
	}

	public static Cliente recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(Cliente.class, COLUNA_CODIGO + " = " + codigo, null);
	}

	public static List<Cliente> recuperarCliente(GenericoDAO dao, String login) {
		return dao.list(Cliente.class, "cli_loginweb like '" + login + "'", null, null, null);
	}

	public static List<Cliente> recuperarClienteNovaSenha(GenericoDAO dao, String chaveNovaSenha) {

		return dao.list(Cliente.class, COLUNA_CHAVENOVASENHA + " like '" + chaveNovaSenha + "'", null, null, null);
	}

	public static List<Cliente> recuperarChaveConfirmacaoEmailCliente(GenericoDAO dao, String chave) {

		return dao.list(Cliente.class, COLUNA_CHAVECONFIRMACAOEMAIL + " like '" + chave + "'", null, null, null);
	}

	public void recuperarLimite(GenericoDAO dao) {

		this.limiteAdicional = LimiteAdicional.recuperarLimiteAdicional(dao, pessoaCodigo);

		ResultSet limite = null;

		String sql = "select cli_limitecre, cli_valdebito, cli_valdebitopr from cliente where cli_pescodigo = "
				+ pessoaCodigo;

		try {

			limite = dao.listResultSet(sql, null, null);

			if (limite.first()) {

				this.limiteCredito = Conversao.nvlDouble(limite.getDouble("cli_limitecre"), 0.0);
				this.valorDebito = Conversao.nvlDouble(limite.getDouble("cli_valdebito"), 0.0);
				this.valorDebitoPrevisto = Conversao.nvlDouble(limite.getDouble("cli_valdebitopr"), 0.0);

			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(limite);
		}

		return;
	}

	public static boolean isExisteEmail(GenericoDAO dao, String email) {

		String where = "cli_loginweb = '" + email + "'";

		return dao.count("cliente", where) > 0;
	}

	@Override
	public String getSituacaoContribuinte() {
		return null;
	}

	@Override
	public void setSituacaoContribuinte(String situacaoContribuinte) {
	}

	public Integer getSegmentoCodigo() {
		return pessoa.getSegmentoCodigo();
	}

	@Override
	public int getRegiaoCodigo() {
		return regiao != null ? regiao.getCodigo() : 0;
	}

	@Override
	public Integer getAtividadeCodigo() {
		return pessoa != null ? pessoa.getAtividadeCodigo() : 0;
	}
}
