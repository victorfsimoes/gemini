/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IFormaPagamento;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "formapagto")
@XmlRootElement
public class FormaPagamento implements Serializable, IFormaPagamento {
	private static final long serialVersionUID = 1L;

	/*
	 * public static final int BANDEIRA_VISA = 0; public static final int
	 * BANDEIRA_MASTERCARD = 1; public static final int BANDEIRA_DINNERS = 2;
	 * public static final int BANDEIRA_AMERICANEXPRESS = 3; public static final
	 * int BANDEIRA_DISCOVER = 4; public static final int BANDEIRA_ELO = 5;
	 * public static final int BANDEIRA_AMEX = 6; public static final int
	 * BANDEIRA_JCB = 7; public static final int BANDEIRA_AURA = 8; public
	 * static final int BANDEIRA_HIPERCARD = 9;
	 */

	public static final String COLUNA_CODIGO = "fpg_codigo";
	public static final String COLUNA_ATIVO = "fpg_ativo";
	public static final String COLUNA_HABILITADO_VENDA = "fpg_habvenda";

	public static final String COLUNA_FLAG_EXIBE_RODAPEWEB = "fpg_exiberodweb";

	public static final String CAMPO_CODIGO = "codigo";
	public static final String CAMPO_DESCRICAO = "descricao";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private String codigo;

	@Column(name = "fpg_desc")
	private String descricao;

	@Column(name = "fpg_envpalm")
	private Integer flagEnviaPalm;

	@Column(name = "fpg_tiponum")
	private String tipoNumeracao;

	@Column(name = "fpg_baixadireta")
	private Integer flagBaixaDireta;

	@Column(name = "fpg_baixaaut")
	private Integer flagBaixaAutomatica;

	@Column(name = "fpg_emitente")
	private Integer emitente;

	@Column(name = "fpg_fluxo")
	private Integer flagFluxoCaixa;

	@Column(name = "fpg_libsci")
	private Integer flagLiberaSci;

	@Column(name = "fpg_libserasa")
	private Integer flagLiberaSerasa;

	@Column(name = "fpg_valminimo")
	private double valorMinimo;

	@Column(name = "fpg_remessabco")
	private Integer flagRemessaBanco;

	@Column(name = "fpg_conslimite")
	private int flagConsisteLimite;

	@Column(name = "fpg_numcampos")
	private Integer numeroCampos;

	@Column(name = "fpg_campo1")
	private String campo1;

	@Column(name = "fpg_campo2")
	private String campo2;

	@Column(name = "fpg_campo3")
	private String campo3;

	@Column(name = "fpg_campo4")
	private String campo4;

	@Column(name = "fpg_campo5")
	private String campo5;

	@Column(name = "fpg_tam1")
	private Integer tamanho1;

	@Column(name = "fpg_tam2")
	private Integer tamanho2;

	@Column(name = "fpg_tam3")
	private Integer tamanho3;

	@Column(name = "fpg_tam4")
	private Integer tamanho4;

	@Column(name = "fpg_tam5")
	private Integer tamanho5;

	@Column(name = "fpg_mascara1")
	private String mascara1;

	@Column(name = "fpg_mascara2")
	private String mascara2;

	@Column(name = "fpg_mascara3")
	private String mascara3;

	@Column(name = "fpg_mascara4")
	private String mascara4;

	@Column(name = "fpg_mascara5")
	private String mascara5;

	@Column(name = "fpg_txadm")
	private Double taxaAdministracao;

	@Column(name = "fpg_jurosmensal")
	private Double jurosMensais;

	@Column(name = "fpg_txacresc")
	private Double taxaAcrescimo;

	@Column(name = "fpg_prazovenc")
	private int prazoMaximoVencimento;

	@Column(name = "fpg_ativo")
	private Integer flagAtivo;

	@Column(name = "fpg_retornobco")
	private Integer flagRetornoBancario;

	@Column(name = "fpg_acrmaximo")
	private double acrescimoMaximo;

	@Column(name = "fpg_descmaximo")
	private double descontoMaximo;

	@Column(name = "fpg_obrig1")
	private Integer flagObrigatoriedadeCampo1;

	@Column(name = "fpg_obrig2")
	private Integer flagObrigatoriedadeCampo2;

	@Column(name = "fpg_obrig3")
	private Integer flagObrigatoriedadeCampo3;

	@Column(name = "fpg_obrig4")
	private Integer flagObrigatoriedadeCampo4;

	@Column(name = "fpg_obrig5")
	private Integer flagObrigatoriedadeCampo5;

	@Column(name = "fpg_plccodigor")
	private Integer planoContasReceitaCodigo;

	@Column(name = "fpg_plccodigod")
	private Integer planoContasDespesaCodigo;

	@Column(name = "fpg_parcauto")
	private Integer flagParcelamentoAutomatico;

	@Column(name = "fpg_posinilei1")
	private Integer posicaoInicialLeitora1;

	@Column(name = "fpg_posinilei2")
	private Integer posicaoInicialLeitora2;

	@Column(name = "fpg_posinilei3")
	private Integer posicaoInicialLeitora3;

	@Column(name = "fpg_posinilei4")
	private Integer posicaoInicialLeitora4;

	@Column(name = "fpg_posinilei5")
	private Integer posicaoInicialLeitora5;

	@Column(name = "fpg_posfinlei1")
	private Integer posicaoFinalLeitora1;

	@Column(name = "fpg_posfinlei2")
	private Integer posicaoFinalLeitora2;

	@Column(name = "fpg_posfinlei3")
	private Integer posicaoFinalLeitora3;

	@Column(name = "fpg_posfinlei4")
	private Integer posicaoFinalLeitora4;

	@Column(name = "fpg_posfinlei5")
	private Integer posicaoFinalLeitora5;

	@Column(name = "fpg_leitoradoc")
	private Integer flagUtilizaLeitora;

	@Column(name = "fpg_plcjurrec")
	private Integer planoContasJurosRecebidosCodigo;

	@Column(name = "fpg_plcjurpag")
	private Integer planoContasJurosPagosCodigo;

	@Column(name = "fpg_plcdesrec")
	private Integer planoContasDescontosConcedidosCodigo;

	@Column(name = "fpg_plcdespag")
	private Integer planoContasDescontosObtidosCodigo;

	@Column(name = "fpg_plclanrec")
	private Integer planoContasLancamentoReceberCodigo;

	@Column(name = "fpg_plclanpag")
	private Integer planoContasLancamentoPagarCodigo;

	@Column(name = "fpg_plccanrec")
	private Integer planoContasCancelamentoReceberCodigo;

	@Column(name = "fpg_plccanpag")
	private Integer planoContasCancelamentoPagarCodigo;

	@Column(name = "fpg_bloqcli")
	private Integer flagBloqueiaCliente;

	@Column(name = "fpg_emiterecp")
	private Integer flagEmiteReciboPagar;

	@Column(name = "fpg_emiterecr")
	private Integer flagEmiteReciboReceber;

	@Column(name = "fpg_tipo1")
	private Integer tipo1;

	@Column(name = "fpg_tipo2")
	private Integer tipo2;

	@Column(name = "fpg_tipo3")
	private Integer tipo3;

	@Column(name = "fpg_tipo4")
	private Integer tipo4;

	@Column(name = "fpg_tipo5")
	private Integer tipo5;

	@Column(name = "fpg_utilizaprz")
	private int flagUtilizaPrazoMaximo;

	@Column(name = "fpg_prdcodigo")
	private Integer portadorCodigo;

	@Column(name = "fpg_valmincarga")
	private Double valorMinimoCarregamento;

	@Column(name = "fpg_avista")
	private Integer flagAVista;

	@Column(name = "fpg_maxparcela")
	private Integer numeroMaximoParcelas;

	@Column(name = "fpg_prazomin")
	private int prazoMinimo;

	@Column(name = "fpg_venctofixo")
	private Integer flagVencimentoFixo;

	@Column(name = "fpg_diasreceb")
	private Integer diasRecebimento;

	@Column(name = "fpg_txdesc")
	private Double taxaDesconto;

	@Column(name = "fpg_finpednsep")
	private Integer flagPermiteFinanceiroPedidoNaoSeparado;

	@Column(name = "fpg_valmaxtx")
	private Double valorMaximoTaxa;

	@Basic(optional = false)
	@Column(name = "fpg_diascomp")
	private int diasCompensacao;

	@Column(name = "fpg_permiteop")
	private int flagNaoPermiteOpcaoEspecial;

	@Column(name = "fpg_npermiteren")
	private Integer flagNaoPermiteRenegociacao;

	@Column(name = "fpg_envpalmcli")
	private Integer flagEnviaTodosClientesPalm;

	@Basic(optional = false)
	@Column(name = "fpg_diastoljuro")
	private int diasToleranciaJuro;

	@Column(name = "fpg_sepcamrel")
	private Integer flagSeparaCamposRelatorio;

	@Column(name = "fpg_pescodigoa")
	private Integer pessoaAdministradoraCodigo;

	/**
	 * D = 'DEBITO' c = 'CREDITO'
	 */
	@Column(name = "fpg_cartdebcred")
	private String tipoCartaoDebitoCredito;

	@Column(name = "fpg_habvenda")
	private int flagHabilitadoVenda;

	@Column(name = COLUNA_FLAG_EXIBE_RODAPEWEB)
	private int flagExibeRodapeWeb;

	@Column(name = "fpg_imagem")
	private String imagem;

	@Column(name = "fpg_infoadicweb")
	private String informacaoAdicionalWeb;

	@Column(name = "fpg_bandeira")
	private String bandeira;

	@JoinColumn(name = "fpg_cldcodigo", referencedColumnName = "cld_codigo")
	@ManyToOne
	private ClassificaDocumento classificaDocumento;

	@Column(name = "fpg_blqnamodcli")
	private int flagBloqueadaModoCliente = 0;

	@Transient
	private FormaPagamentoFilial formaPagamentoFilial;

	@Transient
	private List<FormaPagamentoCondicaoPagamentoTaxa> condicoesPagamentoTaxa;

	/**
	 * 
	 */
	public FormaPagamento() {
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFlagEnviaPalm() {
		return flagEnviaPalm;
	}

	public void setFlagEnviaPalm(Integer flagEnviaPalm) {
		this.flagEnviaPalm = flagEnviaPalm;
	}

	public String getTipoNumeracao() {
		return tipoNumeracao;
	}

	public void setTipoNumeracao(String tipoNumeracao) {
		this.tipoNumeracao = tipoNumeracao;
	}

	public Integer getFlagBaixaDireta() {
		return flagBaixaDireta;
	}

	public void setFlagBaixaDireta(Integer flagBaixaDireta) {
		this.flagBaixaDireta = flagBaixaDireta;
	}

	public Integer getFlagBaixaAutomatica() {
		return flagBaixaAutomatica;
	}

	public void setFlagBaixaAutomatica(Integer flagBaixaAutomatica) {
		this.flagBaixaAutomatica = flagBaixaAutomatica;
	}

	public Integer getEmitente() {
		return emitente;
	}

	public void setEmitente(Integer emitente) {
		this.emitente = emitente;
	}

	public Integer getFlagFluxoCaixa() {
		return flagFluxoCaixa;
	}

	public void setFlagFluxoCaixa(Integer flagFluxoCaixa) {
		this.flagFluxoCaixa = flagFluxoCaixa;
	}

	public Integer getFlagLiberaSci() {
		return flagLiberaSci;
	}

	public void setFlagLiberaSci(Integer flagLiberaSci) {
		this.flagLiberaSci = flagLiberaSci;
	}

	public Integer getFlagLiberaSerasa() {
		return flagLiberaSerasa;
	}

	public void setFlagLiberaSerasa(Integer flagLiberaSerasa) {
		this.flagLiberaSerasa = flagLiberaSerasa;
	}

	public double getValorMinimo() {
		return valorMinimo;
	}

	public void setValorMinimo(double valorMinimo) {
		this.valorMinimo = valorMinimo;
	}

	public Integer getFlagRemessaBanco() {
		return flagRemessaBanco;
	}

	public void setFlagRemessaBanco(Integer flagRemessaBanco) {
		this.flagRemessaBanco = flagRemessaBanco;
	}

	public int getFlagConsisteLimite() {
		return flagConsisteLimite;
	}

	public void setFlagConsisteLimite(int flagConsisteLimite) {
		this.flagConsisteLimite = flagConsisteLimite;
	}

	public Integer getNumeroCampos() {
		return numeroCampos;
	}

	public void setNumeroCampos(Integer numeroCampos) {
		this.numeroCampos = numeroCampos;
	}

	public String getCampo1() {
		return campo1;
	}

	public void setCampo1(String campo1) {
		this.campo1 = campo1;
	}

	public String getCampo2() {
		return campo2;
	}

	public void setCampo2(String campo2) {
		this.campo2 = campo2;
	}

	public String getCampo3() {
		return campo3;
	}

	public void setCampo3(String campo3) {
		this.campo3 = campo3;
	}

	public String getCampo4() {
		return campo4;
	}

	public void setCampo4(String campo4) {
		this.campo4 = campo4;
	}

	public String getCampo5() {
		return campo5;
	}

	public void setCampo5(String campo5) {
		this.campo5 = campo5;
	}

	public Integer getTamanho1() {
		return tamanho1;
	}

	public void setTamanho1(Integer tamanho1) {
		this.tamanho1 = tamanho1;
	}

	public Integer getTamanho2() {
		return tamanho2;
	}

	public void setTamanho2(Integer tamanho2) {
		this.tamanho2 = tamanho2;
	}

	public Integer getTamanho3() {
		return tamanho3;
	}

	public void setTamanho3(Integer tamanho3) {
		this.tamanho3 = tamanho3;
	}

	public Integer getTamanho4() {
		return tamanho4;
	}

	public void setTamanho4(Integer tamanho4) {
		this.tamanho4 = tamanho4;
	}

	public Integer getTamanho5() {
		return tamanho5;
	}

	public void setTamanho5(Integer tamanho5) {
		this.tamanho5 = tamanho5;
	}

	public String getMascara1() {
		return mascara1;
	}

	public void setMascara1(String mascara1) {
		this.mascara1 = mascara1;
	}

	public String getMascara2() {
		return mascara2;
	}

	public void setMascara2(String mascara2) {
		this.mascara2 = mascara2;
	}

	public String getMascara3() {
		return mascara3;
	}

	public void setMascara3(String mascara3) {
		this.mascara3 = mascara3;
	}

	public String getMascara4() {
		return mascara4;
	}

	public void setMascara4(String mascara4) {
		this.mascara4 = mascara4;
	}

	public String getMascara5() {
		return mascara5;
	}

	public void setMascara5(String mascara5) {
		this.mascara5 = mascara5;
	}

	public Double getTaxaAdministracao() {
		return taxaAdministracao;
	}

	public void setTaxaAdministracao(Double taxaAdministracao) {
		this.taxaAdministracao = taxaAdministracao;
	}

	public Double getJurosMensais() {
		return jurosMensais;
	}

	public void setJurosMensais(Double jurosMensais) {
		this.jurosMensais = jurosMensais;
	}

	public Double getTaxaAcrescimo() {
		return taxaAcrescimo;
	}

	public void setTaxaAcrescimo(Double taxaAcrescimo) {
		this.taxaAcrescimo = taxaAcrescimo;
	}

	public int getPrazoMaximoVencimento() {
		return prazoMaximoVencimento;
	}

	public void setPrazoMaximoVencimento(int prazoMaximoVencimento) {
		this.prazoMaximoVencimento = prazoMaximoVencimento;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagRetornoBancario() {
		return flagRetornoBancario;
	}

	public void setFlagRetornoBancario(Integer flagRetornoBancario) {
		this.flagRetornoBancario = flagRetornoBancario;
	}

	public double getAcrescimoMaximo() {
		return acrescimoMaximo;
	}

	public void setAcrescimoMaximo(double acrescimoMaximo) {
		this.acrescimoMaximo = acrescimoMaximo;
	}

	public double getDescontoMaximo() {
		return descontoMaximo;
	}

	public void setDescontoMaximo(double descontoMaximo) {
		this.descontoMaximo = descontoMaximo;
	}

	public Integer getFlagObrigatoriedadeCampo1() {
		return flagObrigatoriedadeCampo1;
	}

	public void setFlagObrigatoriedadeCampo1(Integer flagObrigatoriedadeCampo1) {
		this.flagObrigatoriedadeCampo1 = flagObrigatoriedadeCampo1;
	}

	public Integer getFlagObrigatoriedadeCampo2() {
		return flagObrigatoriedadeCampo2;
	}

	public void setFlagObrigatoriedadeCampo2(Integer flagObrigatoriedadeCampo2) {
		this.flagObrigatoriedadeCampo2 = flagObrigatoriedadeCampo2;
	}

	public Integer getFlagObrigatoriedadeCampo3() {
		return flagObrigatoriedadeCampo3;
	}

	public void setFlagObrigatoriedadeCampo3(Integer flagObrigatoriedadeCampo3) {
		this.flagObrigatoriedadeCampo3 = flagObrigatoriedadeCampo3;
	}

	public Integer getFlagObrigatoriedadeCampo4() {
		return flagObrigatoriedadeCampo4;
	}

	public void setFlagObrigatoriedadeCampo4(Integer flagObrigatoriedadeCampo4) {
		this.flagObrigatoriedadeCampo4 = flagObrigatoriedadeCampo4;
	}

	public Integer getFlagObrigatoriedadeCampo5() {
		return flagObrigatoriedadeCampo5;
	}

	public void setFlagObrigatoriedadeCampo5(Integer flagObrigatoriedadeCampo5) {
		this.flagObrigatoriedadeCampo5 = flagObrigatoriedadeCampo5;
	}

	public Integer getPlanoContasReceitaCodigo() {
		return planoContasReceitaCodigo;
	}

	public void setPlanoContasReceitaCodigo(Integer planoContasReceitaCodigo) {
		this.planoContasReceitaCodigo = planoContasReceitaCodigo;
	}

	public Integer getPlanoContasDespesaCodigo() {
		return planoContasDespesaCodigo;
	}

	public void setPlanoContasDespesaCodigo(Integer planoContasDespesaCodigo) {
		this.planoContasDespesaCodigo = planoContasDespesaCodigo;
	}

	public Integer getFlagParcelamentoAutomatico() {
		return flagParcelamentoAutomatico;
	}

	public void setFlagParcelamentoAutomatico(Integer flagParcelamentoAutomatico) {
		this.flagParcelamentoAutomatico = flagParcelamentoAutomatico;
	}

	public Integer getPosicaoInicialLeitora1() {
		return posicaoInicialLeitora1;
	}

	public void setPosicaoInicialLeitora1(Integer posicaoInicialLeitora1) {
		this.posicaoInicialLeitora1 = posicaoInicialLeitora1;
	}

	public Integer getPosicaoInicialLeitora2() {
		return posicaoInicialLeitora2;
	}

	public void setPosicaoInicialLeitora2(Integer posicaoInicialLeitora2) {
		this.posicaoInicialLeitora2 = posicaoInicialLeitora2;
	}

	public Integer getPosicaoInicialLeitora3() {
		return posicaoInicialLeitora3;
	}

	public void setPosicaoInicialLeitora3(Integer posicaoInicialLeitora3) {
		this.posicaoInicialLeitora3 = posicaoInicialLeitora3;
	}

	public Integer getPosicaoInicialLeitora4() {
		return posicaoInicialLeitora4;
	}

	public void setPosicaoInicialLeitora4(Integer posicaoInicialLeitora4) {
		this.posicaoInicialLeitora4 = posicaoInicialLeitora4;
	}

	public Integer getPosicaoInicialLeitora5() {
		return posicaoInicialLeitora5;
	}

	public void setPosicaoInicialLeitora5(Integer posicaoInicialLeitora5) {
		this.posicaoInicialLeitora5 = posicaoInicialLeitora5;
	}

	public Integer getPosicaoFinalLeitora1() {
		return posicaoFinalLeitora1;
	}

	public void setPosicaoFinalLeitora1(Integer posicaoFinalLeitora1) {
		this.posicaoFinalLeitora1 = posicaoFinalLeitora1;
	}

	public Integer getPosicaoFinalLeitora2() {
		return posicaoFinalLeitora2;
	}

	public void setPosicaoFinalLeitora2(Integer posicaoFinalLeitora2) {
		this.posicaoFinalLeitora2 = posicaoFinalLeitora2;
	}

	public Integer getPosicaoFinalLeitora3() {
		return posicaoFinalLeitora3;
	}

	public void setPosicaoFinalLeitora3(Integer posicaoFinalLeitora3) {
		this.posicaoFinalLeitora3 = posicaoFinalLeitora3;
	}

	public Integer getPosicaoFinalLeitora4() {
		return posicaoFinalLeitora4;
	}

	public void setPosicaoFinalLeitora4(Integer posicaoFinalLeitora4) {
		this.posicaoFinalLeitora4 = posicaoFinalLeitora4;
	}

	public Integer getPosicaoFinalLeitora5() {
		return posicaoFinalLeitora5;
	}

	public void setPosicaoFinalLeitora5(Integer posicaoFinalLeitora5) {
		this.posicaoFinalLeitora5 = posicaoFinalLeitora5;
	}

	public Integer getFlagUtilizaLeitora() {
		return flagUtilizaLeitora;
	}

	public void setFlagUtilizaLeitora(Integer flagUtilizaLeitora) {
		this.flagUtilizaLeitora = flagUtilizaLeitora;
	}

	public Integer getPlanoContasJurosRecebidosCodigo() {
		return planoContasJurosRecebidosCodigo;
	}

	public void setPlanoContasJurosRecebidosCodigo(Integer planoContasJurosRecebidosCodigo) {
		this.planoContasJurosRecebidosCodigo = planoContasJurosRecebidosCodigo;
	}

	public Integer getPlanoContasJurosPagosCodigo() {
		return planoContasJurosPagosCodigo;
	}

	public void setPlanoContasJurosPagosCodigo(Integer planoContasJurosPagosCodigo) {
		this.planoContasJurosPagosCodigo = planoContasJurosPagosCodigo;
	}

	public Integer getPlanoContasDescontosConcedidosCodigo() {
		return planoContasDescontosConcedidosCodigo;
	}

	public void setPlanoContasDescontosConcedidosCodigo(Integer planoContasDescontosConcedidosCodigo) {
		this.planoContasDescontosConcedidosCodigo = planoContasDescontosConcedidosCodigo;
	}

	public Integer getPlanoContasDescontosObtidosCodigo() {
		return planoContasDescontosObtidosCodigo;
	}

	public void setPlanoContasDescontosObtidosCodigo(Integer planoContasDescontosObtidosCodigo) {
		this.planoContasDescontosObtidosCodigo = planoContasDescontosObtidosCodigo;
	}

	public Integer getPlanoContasLancamentoReceberCodigo() {
		return planoContasLancamentoReceberCodigo;
	}

	public void setPlanoContasLancamentoReceberCodigo(Integer planoContasLancamentoReceberCodigo) {
		this.planoContasLancamentoReceberCodigo = planoContasLancamentoReceberCodigo;
	}

	public Integer getPlanoContasLancamentoPagarCodigo() {
		return planoContasLancamentoPagarCodigo;
	}

	public void setPlanoContasLancamentoPagarCodigo(Integer planoContasLancamentoPagarCodigo) {
		this.planoContasLancamentoPagarCodigo = planoContasLancamentoPagarCodigo;
	}

	public Integer getPlanoContasCancelamentoReceberCodigo() {
		return planoContasCancelamentoReceberCodigo;
	}

	public void setPlanoContasCancelamentoReceberCodigo(Integer planoContasCancelamentoReceberCodigo) {
		this.planoContasCancelamentoReceberCodigo = planoContasCancelamentoReceberCodigo;
	}

	public Integer getPlanoContasCancelamentoPagarCodigo() {
		return planoContasCancelamentoPagarCodigo;
	}

	public void setPlanoContasCancelamentoPagarCodigo(Integer planoContasCancelamentoPagarCodigo) {
		this.planoContasCancelamentoPagarCodigo = planoContasCancelamentoPagarCodigo;
	}

	public Integer getFlagBloqueiaCliente() {
		return flagBloqueiaCliente;
	}

	public void setFlagBloqueiaCliente(Integer flagBloqueiaCliente) {
		this.flagBloqueiaCliente = flagBloqueiaCliente;
	}

	public Integer getFlagEmiteReciboPagar() {
		return flagEmiteReciboPagar;
	}

	public void setFlagEmiteReciboPagar(Integer flagEmiteReciboPagar) {
		this.flagEmiteReciboPagar = flagEmiteReciboPagar;
	}

	public Integer getFlagEmiteReciboReceber() {
		return flagEmiteReciboReceber;
	}

	public void setFlagEmiteReciboReceber(Integer flagEmiteReciboReceber) {
		this.flagEmiteReciboReceber = flagEmiteReciboReceber;
	}

	public Integer getTipo1() {
		return tipo1;
	}

	public void setTipo1(Integer tipo1) {
		this.tipo1 = tipo1;
	}

	public Integer getTipo2() {
		return tipo2;
	}

	public void setTipo2(Integer tipo2) {
		this.tipo2 = tipo2;
	}

	public Integer getTipo3() {
		return tipo3;
	}

	public void setTipo3(Integer tipo3) {
		this.tipo3 = tipo3;
	}

	public Integer getTipo4() {
		return tipo4;
	}

	public void setTipo4(Integer tipo4) {
		this.tipo4 = tipo4;
	}

	public Integer getTipo5() {
		return tipo5;
	}

	public void setTipo5(Integer tipo5) {
		this.tipo5 = tipo5;
	}

	public int getFlagUtilizaPrazoMaximo() {
		return flagUtilizaPrazoMaximo;
	}

	public void setFlagUtilizaPrazoMaximo(int flagUtilizaPrazoMaximo) {
		this.flagUtilizaPrazoMaximo = flagUtilizaPrazoMaximo;
	}

	public Integer getPortadorCodigo() {
		return portadorCodigo;
	}

	public void setPortadorCodigo(Integer portadorCodigo) {
		this.portadorCodigo = portadorCodigo;
	}

	public Double getValorMinimoCarregamento() {
		return valorMinimoCarregamento;
	}

	public void setValorMinimoCarregamento(Double valorMinimoCarregamento) {
		this.valorMinimoCarregamento = valorMinimoCarregamento;
	}

	public Integer getFlagAVista() {
		return flagAVista;
	}

	public void setFlagAVista(Integer flagAVista) {
		this.flagAVista = flagAVista;
	}

	public Integer getNumeroMaximoParcelas() {
		return numeroMaximoParcelas;
	}

	public void setNumeroMaximoParcelas(Integer numeroMaximoParcelas) {
		this.numeroMaximoParcelas = numeroMaximoParcelas;
	}

	public int getPrazoMinimo() {
		return prazoMinimo;
	}

	public void setPrazoMinimo(int prazoMinimo) {
		this.prazoMinimo = prazoMinimo;
	}

	public Integer getFlagVencimentoFixo() {
		return flagVencimentoFixo;
	}

	public void setFlagVencimentoFixo(Integer flagVencimentoFixo) {
		this.flagVencimentoFixo = flagVencimentoFixo;
	}

	public Integer getDiasRecebimento() {
		return diasRecebimento;
	}

	public void setDiasRecebimento(Integer diasRecebimento) {
		this.diasRecebimento = diasRecebimento;
	}

	public Double getTaxaDesconto() {
		return taxaDesconto;
	}

	public void setTaxaDesconto(Double taxaDesconto) {
		this.taxaDesconto = taxaDesconto;
	}

	public Integer getFlagPermiteFinanceiroPedidoNaoSeparado() {
		return flagPermiteFinanceiroPedidoNaoSeparado;
	}

	public void setFlagPermiteFinanceiroPedidoNaoSeparado(Integer flagPermiteFinanceiroPedidoNaoSeparado) {
		this.flagPermiteFinanceiroPedidoNaoSeparado = flagPermiteFinanceiroPedidoNaoSeparado;
	}

	public Double getValorMaximoTaxa() {
		return valorMaximoTaxa;
	}

	public void setValorMaximoTaxa(Double valorMaximoTaxa) {
		this.valorMaximoTaxa = valorMaximoTaxa;
	}

	public int getDiasCompensacao() {
		return diasCompensacao;
	}

	public void setDiasCompensacao(int diasCompensacao) {
		this.diasCompensacao = diasCompensacao;
	}

	public int getFlagNaoPermiteOpcaoEspecial() {
		return flagNaoPermiteOpcaoEspecial;
	}

	public void setFlagNaoPermiteOpcaoEspecial(int flagNaoPermiteOpcaoEspecial) {
		this.flagNaoPermiteOpcaoEspecial = flagNaoPermiteOpcaoEspecial;
	}

	public Integer getFlagNaoPermiteRenegociacao() {
		return flagNaoPermiteRenegociacao;
	}

	public void setFlagNaoPermiteRenegociacao(Integer flagNaoPermiteRenegociacao) {
		this.flagNaoPermiteRenegociacao = flagNaoPermiteRenegociacao;
	}

	public Integer getFlagEnviaTodosClientesPalm() {
		return flagEnviaTodosClientesPalm;
	}

	public void setFlagEnviaTodosClientesPalm(Integer flagEnviaTodosClientesPalm) {
		this.flagEnviaTodosClientesPalm = flagEnviaTodosClientesPalm;
	}

	public int getDiasToleranciaJuro() {
		return diasToleranciaJuro;
	}

	public void setDiasToleranciaJuro(int diasToleranciaJuro) {
		this.diasToleranciaJuro = diasToleranciaJuro;
	}

	public Integer getFlagSeparaCamposRelatorio() {
		return flagSeparaCamposRelatorio;
	}

	public void setFlagSeparaCamposRelatorio(Integer flagSeparaCamposRelatorio) {
		this.flagSeparaCamposRelatorio = flagSeparaCamposRelatorio;
	}

	public Integer getPessoaAdministradoraCodigo() {
		return pessoaAdministradoraCodigo;
	}

	public void setPessoaAdministradoraCodigo(Integer pessoaAdministradoraCodigo) {
		this.pessoaAdministradoraCodigo = pessoaAdministradoraCodigo;
	}

	public String getTipoCartaoDebitoCredito() {
		return tipoCartaoDebitoCredito;
	}

	public void setTipoCartaoDebitoCredito(String tipoCargaoDebitoCredito) {
		this.tipoCartaoDebitoCredito = tipoCargaoDebitoCredito;
	}

	public ClassificaDocumento getClassificaDocumento() {
		return classificaDocumento;
	}

	public void setClassificaDocumento(ClassificaDocumento classificaDocumento) {
		this.classificaDocumento = classificaDocumento;
	}

	public boolean isHabilitadoVenda() {
		return flagHabilitadoVenda == 1;
	}

	public int getFlagHabilitadoVenda() {
		return flagHabilitadoVenda;
	}

	public void setFlagHabilitadoVenda(int flagHabilitadoVenda) {
		this.flagHabilitadoVenda = flagHabilitadoVenda;
	}

	public int getFlagExibeRodapeWeb() {
		return flagExibeRodapeWeb;
	}

	public void setFlagExibeRodapeWeb(int flagExibeRodapeWeb) {
		this.flagExibeRodapeWeb = flagExibeRodapeWeb;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getInformacaoAdicionalWeb() {
		return informacaoAdicionalWeb;
	}

	public void setInformacaoAdicionalWeb(String informacaoAdicionalWeb) {
		this.informacaoAdicionalWeb = informacaoAdicionalWeb;
	}

	public String getBandeira() {
		return bandeira;
	}

	public void setBandeira(String bandeira) {
		this.bandeira = bandeira;
	}

	public FormaPagamentoFilial getFormaPagamentoFilial() {
		return formaPagamentoFilial;
	}

	public void setFormaPagamentoFilial(FormaPagamentoFilial formaPagamentoFilial) {
		this.formaPagamentoFilial = formaPagamentoFilial;
	}

	public List<FormaPagamentoCondicaoPagamentoTaxa> getCondicoesPagamentoTaxa() {
		return condicoesPagamentoTaxa;
	}

	public void setCondicoesPagamentoTaxa(List<FormaPagamentoCondicaoPagamentoTaxa> condicoesPagamentoTaxa) {
		this.condicoesPagamentoTaxa = condicoesPagamentoTaxa;
	}

	public boolean isBloqueadaModoCliente() {
		return flagBloqueadaModoCliente == 1;
	}

	public int getFlagBloqueadaModoCliente() {
		return flagBloqueadaModoCliente;
	}

	public void setFlagBloqueadaModoCliente(int flagBloqueadaModoCliente) {
		this.flagBloqueadaModoCliente = flagBloqueadaModoCliente;
	}

	/**
	 * Indica se a forma de pagamento é um cartao de débito ou não
	 * 
	 * @return
	 */
	public boolean isCartaoDebito() {
		boolean retorno = false;
		if (this.classificaDocumento != null && this.classificaDocumento.getTipo() != null) {

			retorno = this.classificaDocumento.getTipo().toUpperCase().equals("T")
					&& this.tipoCartaoDebitoCredito.toUpperCase().equals("D");
		}
		return retorno;
	}

	public boolean isCartao() {
		boolean retorno = false;
		if (this.classificaDocumento != null && this.classificaDocumento.getTipo() != null)
			retorno = this.classificaDocumento.getTipo().toUpperCase().equals(ClassificaDocumento.TIPO_CARTAO);
		return retorno;
	}

	/**
	 * Indica se a forma de pagamento é um cartao de crédito ou não
	 * 
	 * @return
	 */
	public boolean isCartaoCredito() {
		boolean retorno = false;
		if (this.classificaDocumento != null && this.classificaDocumento.getTipo() != null)
			retorno = this.classificaDocumento.getTipo().toUpperCase().equals("T")
					&& this.tipoCartaoDebitoCredito.toUpperCase().equals("C");
		return retorno;
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
	 * @param filialCodigo
	 * @param forcarAtualizacao
	 */
	public void recuperarFormaPagamentoFilial(GenericoDAO dao, int filialCodigo, boolean forcarAtualizacao) {
		if (forcarAtualizacao || this.formaPagamentoFilial == null) {
			this.formaPagamentoFilial = FormaPagamentoFilial.recuperarUnico(dao, this.getCodigo(), filialCodigo);
		}
	}

	/**
	 * 
	 * @param dao
	 * @param forcarAtualizacao
	 */
	public void recuperarCondicoesPagamentoTaxa(GenericoDAO dao, boolean forcarAtualizacao) {
		if (forcarAtualizacao || this.condicoesPagamentoTaxa == null) {
			this.condicoesPagamentoTaxa = FormaPagamentoCondicaoPagamentoTaxa.recuperar(dao, this.getCodigo());
		}
	}

	/**
	 * 
	 * @param dao
	 * @param codigo
	 * @return
	 */
	public static FormaPagamento recuperarUnico(GenericoDAO dao, String codigo) {
		return dao.uniqueResult(FormaPagamento.class, COLUNA_CODIGO + " = '" + codigo + "'", null);
	}

	/**
	 * 
	 * @param dao
	 * @return
	 */
	public static List<FormaPagamento> recuperarVisiveisRodape(GenericoDAO dao) {
		return dao.list(FormaPagamento.class, COLUNA_FLAG_EXIBE_RODAPEWEB + " = 1", null, "classificaDocumento", null);
	}

	public boolean isCondicaoPermitidaForma(GenericoDAO dao, int condicao) {

		return CondicaoPagamentoNaoPermitidaFormaPagamento.recuperar(dao, getCodigo(), condicao) == null;
	}
}
