/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;


/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "contacorrente")
@XmlRootElement
public class ContaCorrente implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "cct_codigo")
	private int codigo;

	@Column(name = "cct_desc")
	private String descricao;

	@Column(name = "cct_agencia")
	private String agencia;

	@Column(name = "cct_digitoag")
	private String digitoAgencia;

	@Column(name = "cct_numconta")
	private String numeroConta;

	@Column(name = "cct_digitocon")
	private String digitoConta;

	@Column(name = "cct_cedente")
	private String cedente;

	@Column(name = "cct_carteira")
	private String carteira;

	@Column(name = "cct_especiedoc")
	private String especieDocumento;

	@Column(name = "cct_dataab")
	@Temporal(TemporalType.DATE)
	private Date dataAbertura;

	@Column(name = "cct_gerente")
	private String gerente;

	@Column(name = "cct_fone")
	private String fone;

	@Column(name = "cct_email")
	private String email;

	@Column(name = "cct_nossonum")
	private String nossoNumero;

	@Column(name = "cct_digitonum")
	private String digitoNossoNumero;

	@Column(name = "cct_codremessa")
	private String codigoRemessa;

	@Column(name = "cct_seqremessa")
	private Integer sequenciaRemessa;

	@Column(name = "cct_nomeremessa")
	private String nomeRemessa;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "cct_txjuros")
	private Double taxaJuros;

	@Column(name = "cct_localpg")
	private String localPagamento;

	@Column(name = "cct_instcobr1")
	private String instrucaoCobranca1;

	@Column(name = "cct_instcobr2")
	private String instrucaoCobranca2;

	@Column(name = "cct_instcobr3")
	private String instrucaoCobranca3;

	@Column(name = "cct_instcobr4")
	private String instrucaoCobranca4;

	@Column(name = "cct_direnv")
	private String diretorioEnvio;

	@Column(name = "cct_dirret")
	private String diretorioRetorno;

	@Column(name = "cct_diasprot")
	private Integer diasProtesto;

	@Column(name = "cct_txboleto")
	private Double taxaBoleto;

	@Column(name = "cct_valminbol")
	private Double valorMinimoBoleto;

	@Column(name = "cct_ativo")
	private Integer flagAtivo;

	@Column(name = "cct_tipocobr")
	private String tipoCobranca;

	@Column(name = "cct_nossonumf")
	private String nossoNumeroParteFixa;

	@Column(name = "cct_tipopessoa")
	private String tipoPessoa;

	@Column(name = "cct_cnpjcpf")
	private String cnpjCpf;

	@Column(name = "cct_codconvenio")
	private String convenio;

	@Column(name = "cct_tipodoc")
	private String tipoDocumento;

	@Column(name = "cct_formacad")
	private String formaCadastro;

	@Column(name = "cct_emissaobol")
	private String emissaoBoleto;

	@Column(name = "cct_distbol")
	private String distribuicaoBoleto;

	@Column(name = "cct_aceite")
	private String aceite;

	@Column(name = "cct_codjuromora")
	private String codigoJurosMora;

	@Column(name = "cct_codprotesto")
	private String codigoProtesto;

	@Column(name = "cct_codcobranca")
	private String codigoCobranca;

	@Column(name = "cct_tipocnab")
	private String tipoCnab;

	@Column(name = "cct_tipoimpbol")
	private String tipoImpressaoBoleto;

	@Column(name = "cct_laycodigo")
	private Integer layoutCodigo;

	@Column(name = "cct_contrato")
	private String contrato;

	@Column(name = "cct_seqchrem")
	private Integer sequencialRemessaCheque;

	@Column(name = "cct_arqchrem")
	private String arquivoRemessaCheque;

	@Column(name = "cct_direnvch")
	private String diretorioEnvioCheque;

	@Column(name = "cct_dirretch")
	private String diretorioRetornoCheque;

	@Column(name = "cct_convch")
	private String convenioCheque;

	@Column(name = "cct_agenciaent")
	private String agenciaEntrega;

	@Column(name = "cct_dgagenent")
	private String digitoAgenciaEntrega;

	@Column(name = "cct_tipoconta")
	private String tipoConta;

	@Column(name = "cct_espmoeda")
	private String especieMoeda;

	@Column(name = "cct_cip")
	private String cip;

	@Column(name = "cct_modlayout")
	private String modeloLayout;

	@Column(name = "cct_codcarteira")
	private String codigoCarteira;

	@Basic(optional = false)
	@Column(name = "cct_razsacador")
	private String razaoSacador;

	@Basic(optional = false)
	@Column(name = "cct_cnpjsacador")
	private String cnpjSacador;

	@Column(name = "cct_msgboleta")
	private String mensagemBoleta;

	@Column(name = "cct_tpcob")
	private String tipoCob;

	@Column(name = "cct_baidev")
	private Integer baixarDevolver;

	@Column(name = "cct_diasbaidev")
	private Integer diasBaixarDevolver;

	@Column(name = "cct_numdigseq")
	private Integer numeroDigitoSequencial;

	@Column(name = "cct_digcontrato")
	private Integer digitoContrato;

	@Column(name = "cct_bxdtpagto")
	private Integer flagBaixaDataPagamento;

	@JoinColumn(name = "cct_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filial;

	@JoinColumn(name = "cct_bnccodigo", referencedColumnName = "bnc_codigo")
	@ManyToOne(optional = false)
	private Banco cctBnccodigo;

	public ContaCorrente() {
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

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getDigitoAgencia() {
		return digitoAgencia;
	}

	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}

	public String getNumeroConta() {
		return numeroConta;
	}

	public void setNumeroConta(String numeroConta) {
		this.numeroConta = numeroConta;
	}

	public String getDigitoConta() {
		return digitoConta;
	}

	public void setDigitoConta(String digitoConta) {
		this.digitoConta = digitoConta;
	}

	public String getCedente() {
		return cedente;
	}

	public void setCedente(String cedente) {
		this.cedente = cedente;
	}

	public String getCarteira() {
		return carteira;
	}

	public void setCarteira(String carteira) {
		this.carteira = carteira;
	}

	public String getEspecieDocumento() {
		return especieDocumento;
	}

	public void setEspecieDocumento(String especieDocumento) {
		this.especieDocumento = especieDocumento;
	}

	public Date getDataAbertura() {
		return dataAbertura;
	}

	public void setDataAbertura(Date dataAbertura) {
		this.dataAbertura = dataAbertura;
	}

	public String getGerente() {
		return gerente;
	}

	public void setGerente(String gerente) {
		this.gerente = gerente;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getNossoNumero() {
		return nossoNumero;
	}

	public void setNossoNumero(String nossoNumero) {
		this.nossoNumero = nossoNumero;
	}

	public String getDigitoNossoNumero() {
		return digitoNossoNumero;
	}

	public void setDigitoNossoNumero(String digitoNossoNumero) {
		this.digitoNossoNumero = digitoNossoNumero;
	}

	public String getCodigoRemessa() {
		return codigoRemessa;
	}

	public void setCodigoRemessa(String codigoRemessa) {
		this.codigoRemessa = codigoRemessa;
	}

	public Integer getSequenciaRemessa() {
		return sequenciaRemessa;
	}

	public void setSequenciaRemessa(Integer sequenciaRemessa) {
		this.sequenciaRemessa = sequenciaRemessa;
	}

	public String getNomeRemessa() {
		return nomeRemessa;
	}

	public void setNomeRemessa(String nomeRemessa) {
		this.nomeRemessa = nomeRemessa;
	}

	public Double getTaxaJuros() {
		return taxaJuros;
	}

	public void setTaxaJuros(Double taxaJuros) {
		this.taxaJuros = taxaJuros;
	}

	public String getLocalPagamento() {
		return localPagamento;
	}

	public void setLocalPagamento(String localPagamento) {
		this.localPagamento = localPagamento;
	}

	public String getInstrucaoCobranca1() {
		return instrucaoCobranca1;
	}

	public void setInstrucaoCobranca1(String instrucaoCobranca1) {
		this.instrucaoCobranca1 = instrucaoCobranca1;
	}

	public String getInstrucaoCobranca2() {
		return instrucaoCobranca2;
	}

	public void setInstrucaoCobranca2(String instrucaoCobranca2) {
		this.instrucaoCobranca2 = instrucaoCobranca2;
	}

	public String getInstrucaoCobranca3() {
		return instrucaoCobranca3;
	}

	public void setInstrucaoCobranca3(String instrucaoCobranca3) {
		this.instrucaoCobranca3 = instrucaoCobranca3;
	}

	public String getInstrucaoCobranca4() {
		return instrucaoCobranca4;
	}

	public void setInstrucaoCobranca4(String instrucaoCobranca4) {
		this.instrucaoCobranca4 = instrucaoCobranca4;
	}

	public String getDiretorioEnvio() {
		return diretorioEnvio;
	}

	public void setDiretorioEnvio(String diretorioEnvio) {
		this.diretorioEnvio = diretorioEnvio;
	}

	public String getDiretorioRetorno() {
		return diretorioRetorno;
	}

	public void setDiretorioRetorno(String diretorioRetorno) {
		this.diretorioRetorno = diretorioRetorno;
	}

	public Integer getDiasProtesto() {
		return diasProtesto;
	}

	public void setDiasProtesto(Integer diasProtesto) {
		this.diasProtesto = diasProtesto;
	}

	public Double getTaxaBoleto() {
		return taxaBoleto;
	}

	public void setTaxaBoleto(Double taxaBoleto) {
		this.taxaBoleto = taxaBoleto;
	}

	public Double getValorMinimoBoleto() {
		return valorMinimoBoleto;
	}

	public void setValorMinimoBoleto(Double valorMinimoBoleto) {
		this.valorMinimoBoleto = valorMinimoBoleto;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getTipoCobranca() {
		return tipoCobranca;
	}

	public void setTipoCobranca(String tipoCobranca) {
		this.tipoCobranca = tipoCobranca;
	}

	public String getNossoNumeroParteFixa() {
		return nossoNumeroParteFixa;
	}

	public void setNossoNumeroParteFixa(String nossoNumeroParteFixa) {
		this.nossoNumeroParteFixa = nossoNumeroParteFixa;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getConvenio() {
		return convenio;
	}

	public void setConvenio(String convenio) {
		this.convenio = convenio;
	}

	public String getTipoDocumento() {
		return tipoDocumento;
	}

	public void setTipoDocumento(String tipoDocumento) {
		this.tipoDocumento = tipoDocumento;
	}

	public String getFormaCadastro() {
		return formaCadastro;
	}

	public void setFormaCadastro(String formaCadastro) {
		this.formaCadastro = formaCadastro;
	}

	public String getEmissaoBoleto() {
		return emissaoBoleto;
	}

	public void setEmissaoBoleto(String emissaoBoleto) {
		this.emissaoBoleto = emissaoBoleto;
	}

	public String getDistribuicaoBoleto() {
		return distribuicaoBoleto;
	}

	public void setDistribuicaoBoleto(String distribuicaoBoleto) {
		this.distribuicaoBoleto = distribuicaoBoleto;
	}

	public String getAceite() {
		return aceite;
	}

	public void setAceite(String aceite) {
		this.aceite = aceite;
	}

	public String getCodigoJurosMora() {
		return codigoJurosMora;
	}

	public void setCodigoJurosMora(String codigoJurosMora) {
		this.codigoJurosMora = codigoJurosMora;
	}

	public String getCodigoProtesto() {
		return codigoProtesto;
	}

	public void setCodigoProtesto(String codigoProtesto) {
		this.codigoProtesto = codigoProtesto;
	}

	public String getCodigoCobranca() {
		return codigoCobranca;
	}

	public void setCodigoCobranca(String codigoCobranca) {
		this.codigoCobranca = codigoCobranca;
	}

	public String getTipoCnab() {
		return tipoCnab;
	}

	public void setTipoCnab(String tipoCnab) {
		this.tipoCnab = tipoCnab;
	}

	public String getTipoImpressaoBoleto() {
		return tipoImpressaoBoleto;
	}

	public void setTipoImpressaoBoleto(String tipoImpressaoBoleto) {
		this.tipoImpressaoBoleto = tipoImpressaoBoleto;
	}

	public Integer getLayoutCodigo() {
		return layoutCodigo;
	}

	public void setLayoutCodigo(Integer layoutCodigo) {
		this.layoutCodigo = layoutCodigo;
	}

	public String getContrato() {
		return contrato;
	}

	public void setContrato(String contrato) {
		this.contrato = contrato;
	}

	public Integer getSequencialRemessaCheque() {
		return sequencialRemessaCheque;
	}

	public void setSequencialRemessaCheque(Integer sequencialRemessaCheque) {
		this.sequencialRemessaCheque = sequencialRemessaCheque;
	}

	public String getArquivoRemessaCheque() {
		return arquivoRemessaCheque;
	}

	public void setArquivoRemessaCheque(String arquivoRemessaCheque) {
		this.arquivoRemessaCheque = arquivoRemessaCheque;
	}

	public String getDiretorioEnvioCheque() {
		return diretorioEnvioCheque;
	}

	public void setDiretorioEnvioCheque(String diretorioEnvioCheque) {
		this.diretorioEnvioCheque = diretorioEnvioCheque;
	}

	public String getDiretorioRetornoCheque() {
		return diretorioRetornoCheque;
	}

	public void setDiretorioRetornoCheque(String diretorioRetornoCheque) {
		this.diretorioRetornoCheque = diretorioRetornoCheque;
	}

	public String getConvenioCheque() {
		return convenioCheque;
	}

	public void setConvenioCheque(String convenioCheque) {
		this.convenioCheque = convenioCheque;
	}

	public String getAgenciaEntrega() {
		return agenciaEntrega;
	}

	public void setAgenciaEntrega(String agenciaEntrega) {
		this.agenciaEntrega = agenciaEntrega;
	}

	public String getDigitoAgenciaEntrega() {
		return digitoAgenciaEntrega;
	}

	public void setDigitoAgenciaEntrega(String digitoAgenciaEntrega) {
		this.digitoAgenciaEntrega = digitoAgenciaEntrega;
	}

	public String getTipoConta() {
		return tipoConta;
	}

	public void setTipoConta(String tipoConta) {
		this.tipoConta = tipoConta;
	}

	public String getEspecieMoeda() {
		return especieMoeda;
	}

	public void setEspecieMoeda(String especieMoeda) {
		this.especieMoeda = especieMoeda;
	}

	public String getCip() {
		return cip;
	}

	public void setCip(String cip) {
		this.cip = cip;
	}

	public String getModeloLayout() {
		return modeloLayout;
	}

	public void setModeloLayout(String modeloLayout) {
		this.modeloLayout = modeloLayout;
	}

	public String getCodigoCarteira() {
		return codigoCarteira;
	}

	public void setCodigoCarteira(String codigoCarteira) {
		this.codigoCarteira = codigoCarteira;
	}

	public String getRazaoSacador() {
		return razaoSacador;
	}

	public void setRazaoSacador(String razaoSacador) {
		this.razaoSacador = razaoSacador;
	}

	public String getCnpjSacador() {
		return cnpjSacador;
	}

	public void setCnpjSacador(String cnpjSacador) {
		this.cnpjSacador = cnpjSacador;
	}

	public String getMensagemBoleta() {
		return mensagemBoleta;
	}

	public void setMensagemBoleta(String mensagemBoleta) {
		this.mensagemBoleta = mensagemBoleta;
	}

	public String getTipoCob() {
		return tipoCob;
	}

	public void setTipoCob(String tipoCob) {
		this.tipoCob = tipoCob;
	}

	public Integer getBaixarDevolver() {
		return baixarDevolver;
	}

	public void setBaixarDevolver(Integer baixarDevolver) {
		this.baixarDevolver = baixarDevolver;
	}

	public Integer getDiasBaixarDevolver() {
		return diasBaixarDevolver;
	}

	public void setDiasBaixarDevolver(Integer diasBaixarDevolver) {
		this.diasBaixarDevolver = diasBaixarDevolver;
	}

	public Integer getNumeroDigitoSequencial() {
		return numeroDigitoSequencial;
	}

	public void setNumeroDigitoSequencial(Integer numeroDigitoSequencial) {
		this.numeroDigitoSequencial = numeroDigitoSequencial;
	}

	public Integer getDigitoContrato() {
		return digitoContrato;
	}

	public void setDigitoContrato(Integer digitoContrato) {
		this.digitoContrato = digitoContrato;
	}

	public Integer getFlagBaixaDataPagamento() {
		return flagBaixaDataPagamento;
	}

	public void setFlagBaixaDataPagamento(Integer flagBaixaDataPagamento) {
		this.flagBaixaDataPagamento = flagBaixaDataPagamento;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Banco getCctBnccodigo() {
		return cctBnccodigo;
	}

	public void setCctBnccodigo(Banco cctBnccodigo) {
		this.cctBnccodigo = cctBnccodigo;
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
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
}
