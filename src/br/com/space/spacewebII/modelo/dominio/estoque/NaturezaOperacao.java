/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.negocio.modelo.dominio.INaturezaOperacao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.ClassificacaoNaturezaOperacao;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "natoper")
@XmlRootElement
public class NaturezaOperacao implements Serializable, INaturezaOperacao {

	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "nat_codigo";
	public static final String COLUNA_CLASSIFICA_SAIDA_CODIGO = "nat_cntcodigos";

	public static final int LACAMENTO_VENDEDOR_DO_CLIENTE = 1;
	public static final int LACAMENTO_VENDEDOR_DO_USUARIO = 2;

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private String codigo;

	@Basic(optional = false)
	@Column(name = "nat_desc")
	private String descricao;

	@Column(name = "nat_descnfe")
	private String descricaoNFEntrada;

	@Column(name = "nat_atualizaest")
	private int flagAtualizaEstoque;

	@Column(name = "nat_tipopreco")
	private String tipoPreco;

	@Column(name = "nat_tipofinan")
	private String tipoFinan;

	@Column(name = "nat_conslimite")
	private int flagConsisteLimite;

	@Column(name = "nat_consestoq")
	private int flagConsisteEstoque;

	@Column(name = "nat_conspreco")
	private int flagConsistePreco;

	@Column(name = "nat_conssitcli")
	private int flagConsisteSituacaoCliente;

	@Column(name = "nat_cspcodigos")
	private Integer controleNumeracaoCodigo;

	@Column(name = "nat_sepestoque")
	private Integer flagSeparaEstoque;

	@Column(name = "nat_ativo")
	private Integer flagAtivo;

	@Column(name = "nat_cntcodigoe")
	private Integer classificacaoNaturezaEntradaCodigo;

	@Column(name = "nat_tipovenda")
	private String tipoVenda;

	@Basic(optional = false)
	@Column(name = "nat_filcodigo")
	private int filialCodigo;

	@Column(name = "nat_cdfcodigoe")
	private String codigoFiscalEntrada;

	@Column(name = "nat_cdfcodigos")
	private String codigoFiscalSaida;

	@Column(name = "nat_descnfs")
	private String descricaoNFSaida;

	@Column(name = "nat_repeteprod")
	private Integer flagPermiteRepeticaoProduto = null;

	/*
	 * @Column(name = "NAT_BXESTOQS") private Integer natBxestoqs;
	 */

	// @Column(name = COLUNA_CLASSIFICA_SAIDA_CODIGO)
	// private Integer classificacaoNaturezaSaidaCodigo;

	@Column(name = "nat_calccustoe")
	private Integer flagCalculaCustoEntrada;

	@Column(name = "nat_calcvendae")
	private Integer flagCalculaVendaEntrada;

	@Column(name = "nat_calcpis")
	private Integer flagCalculaPis;

	@Column(name = "nat_calccofins")
	private Integer flagCalculaCofins;

	@Column(name = "nat_calcicms")
	private Integer flagCalculaIcms;

	@Column(name = "nat_lancitem")
	private Integer flagLancaItem;

	@Column(name = "nat_calcimpdav")
	private Integer flagCalculoFiscal;

	@Column(name = "nat_lancespelho")
	private Integer flagLancamentoEspelho;

	@Column(name = "nat_nfechabase")
	private Integer flagNaoFechaResumoBases;

	@Column(name = "nat_entrada")
	private Integer flagEntrada;

	@Column(name = "nat_saida")
	private Integer flagSaida;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "nat_vlrminvenda")
	private double valorMinimoVenda;

	@Column(name = "nat_cfgcliente")
	private Integer configuracaoLancamentoCliente;

	@Column(name = "nat_cfgvendedor")
	private Integer configuracaoLancamentoVendedor;

	@Column(name = "nat_cfgtabela")
	private int configuracaoOrigemTabelaPreco;

	@Column(name = "nat_cfglucrativ")
	private Integer flagLucratividade;

	@Column(name = "nat_cfgprecouni")
	private Integer flagPrecoUnitario;

	@Column(name = "nat_cfgpagto")
	private Integer configuracaoLancamentoCondicaoPagamento;

	@Column(name = "nat_nummaxitens")
	private int numeroMaximoItens;

	@Column(name = "nat_cfgprecosug")
	private Integer configuracaoPrecoSugerido;

	@Column(name = "nat_vresultmin")
	private double resultadoMinimoValor;

	@Column(name = "nat_presultmin")
	private double resultadoMinimoPercentual;

	@Column(name = "nat_vressimpmin")
	private double resultadoMinimoSemImpostoValor;

	@Column(name = "nat_pressimpmin")
	private double resultadoMinimoSemImpostoPercentual;

	@Column(name = "nat_exibelucrat")
	private int flagExibeLucratividade;

	@Column(name = "nat_modifpreco")
	private int flagPermiteAlterarPreco;

	@Column(name = "nat_obrordcomp")
	private Integer flagOrdemCompraObrigatorio;

	@Column(name = "nat_laycodigoe")
	private Integer layoutEntradaCodigo;

	@Column(name = "nat_laycodigos")
	private Integer layoutSaidaCodigo;

	@Column(name = "nat_mensagem")
	private String mensagem;

	@Column(name = "nat_altimpostos")
	private Integer flagAlteraValoresImpostos;

	@Column(name = "nat_lanprevenda")
	private Integer flagLancamentoPreVenda;

	@Column(name = "nat_exigepednf")
	private Integer flagExigePedidoNF;

	@Column(name = "nat_altnfpedido")
	private Integer flagAlteraNfPedido;

	@Column(name = "nat_basecalcs")
	private Float baseCalculo;

	@Column(name = "nat_outrass")
	private Float outras;

	@Column(name = "nat_isentass")
	private Float isentas;

	@Column(name = "nat_cstcodigos")
	private String cstSaidaCodigo;

	@Column(name = "nat_calcipie")
	private Integer flagCalculaIpiEntrada;

	@Column(name = "nat_calcipis")
	private Integer flagCalculaIpiSaida;

	@Column(name = "nat_atufornepro")
	private Integer flagAtualizaFornecedorProduto;

	@Column(name = "nat_layprevenda")
	private Integer layoutPreVendaCodigo;

	@Column(name = "nat_laynf")
	private Integer layoutNFCodigo;

	@Column(name = "nat_impnfent")
	private Integer flagImprimeNFEntrada;

	@Column(name = "nat_carga")
	private Integer flagCarga;

	@Column(name = "nat_mdfcodigo")
	private String modeloFiscalCodigo;

	@Column(name = "nat_impped")
	private Integer flagImprimePedidoAutomatico;

	@Column(name = "nat_origvenped")
	private Integer flagUtilizaOrigemVenda;

	@Column(name = "nat_datemisped")
	private Integer flagDataEmissaoPedido;

	@Column(name = "nat_tprcodigonf")
	private Integer tabelaPrecoNFCodigo;

	@Column(name = "nat_plcpagar")
	private Integer planoContasPagarCodigo;

	@Column(name = "nat_plcreceber")
	private Integer planoContasReceberCodigo;

	@Column(name = "nat_plclucro")
	private Integer planoContasLucroCodigo;

	@Column(name = "nat_layorcam")
	private Integer layoutOrcamentoCodigo;

	@Column(name = "nat_plcfrete")
	private Integer planoContasFreteCodigo;

	@Column(name = "nat_lancfine")
	private Integer flagLancaFinanceiroEntrada;

	@Column(name = "nat_laypedpar")
	private Integer layoutPedidoParcialCodigo;

	@Column(name = "nat_atrefforpro")
	private Integer flagAtualizaReferenciaFornecedorProduto;

	@Column(name = "nat_tiposepara")
	private String tipoSeparacao;

	@Column(name = "nat_codpronfe")
	private String codigoProdutoNFE;

	@Column(name = "nat_descpronfe")
	private String descricaoProdutoNFE;

	@Column(name = "nat_nfcompl")
	private Integer flagNfcomplemento;

	@Column(name = "nat_cdfcodigod")
	private String codigoFiscalDevolucao;

	@Column(name = "nat_remretind")
	private Integer remessaRetorno;

	@Column(name = "nat_mensagem2")
	private String mensagem2;

	@Column(name = "nat_lcltpeds")
	private Integer flagLancaLocalLotePedidoSaida;

	@Column(name = "nat_tprcodigo")
	private Integer tabelaPrecoCodigo;

	@Column(name = "nat_ncalcstent")
	private Integer flagNaoCalculaStEntrada;

	@Column(name = "nat_formaprocst")
	private Integer flagFormaProcessoSt;

	@Column(name = "nat_nfserv")
	private Integer flagNfServico;

	@Column(name = "nat_agprodret")
	private Integer flagAgrupaProdutoRetorno;

	@Column(name = "nat_envpalm")
	private Integer flagEnviaPalm;

	@Column(name = "nat_csscodigo")
	private String csosnCodigo;

	@Column(name = "nat_nexpcupom")
	private Integer flagNaoExportaCupom;

	@Column(name = "nat_obriglce")
	private Integer flagObrigatorioLocalEstoque;

	@Column(name = "nat_suglcepad")
	private Integer flagSugereLocalEstoquePadrao;

	@Column(name = "nat_lcepadrao")
	private Integer localEstoquePadrao;

	@Column(name = "nat_nlancafin")
	private Integer flagNaoLancaFinanceiro;

	@Column(name = "nat_lcepadraoe")
	private Integer localEstoquePadraoEntrada;

	@Column(name = "nat_lancaprunit")
	private Integer flagLancaPrecoUnitario;

	@Column(name = "nat_canprosest")
	private Integer flagCancelaDigitacaoProdutoSemEstoque;

	@Column(name = "nat_blqfatscarg")
	private Integer flagBloqueiaFaturamentoSemCarga;

	@Column(name = "nat_selcustosai")
	private Integer flagSelecionaCustoSaida;

	@Column(name = "nat_custosaires")
	private Integer custoSaidaResultado;

	@Column(name = "nat_selcustoent")
	private Integer flagSelecionaCustoEntrada;

	@Column(name = "nat_custoentatu")
	private String custosAtualizadosEntrada;

	@Column(name = "nat_csmedlocest")
	private Integer custoMedioLocalEstoque;

	@Column(name = "nat_prbcodigoen")
	private Integer precoBasePadraoExibicaoCodigo;

	@Column(name = "nat_atprvecssel")
	private Integer flagAtualizaPrecoVendaCustosSelecionados;

	@Column(name = "nat_nenviasped")
	private Integer flagNaoEnviaSped;

	@Column(name = "nat_geranfcfdav")
	private Integer flagGeraNFCFAutomaticamente;

	@Column(name = "nat_fornverba")
	private Integer flagFornecimentoVerba;

	@Column(name = "nat_cntdescarga")
	private Integer flagControleDescarga;

	@Column(name = "nat_sugatincped")
	private Integer flagSugereAtendenteInclusaoPedido = 0;

	@Column(name = "nat_libweb")
	private int flagLiberaVendaWeb;

	@Column(name = "nat_liberavenda")
	private int flagLiberaVenda;

	@Column(name = "nat_difprepro")
	private int flagParticipaAcrescimoDespesaEntrega;

	@OneToOne
	@JoinColumn(name = COLUNA_CLASSIFICA_SAIDA_CODIGO, referencedColumnName = ClassificacaoNaturezaOperacao.COLUNA_CODIGO)
	@NotFound(action = NotFoundAction.IGNORE)
	private ClassificacaoNaturezaOperacao classificacaoNaturezaSaida;

	public NaturezaOperacao() {
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

	public String getDescricaoNFEntrada() {
		return descricaoNFEntrada;
	}

	public void setDescricaoNFEntrada(String descricaoNFEntrada) {
		this.descricaoNFEntrada = descricaoNFEntrada;
	}

	public int getFlagAtualizaEstoque() {
		return flagAtualizaEstoque;
	}

	public void setFlagAtualizaEstoque(int flagAtualizaEstoque) {
		this.flagAtualizaEstoque = flagAtualizaEstoque;
	}

	public String getTipoPreco() {
		return tipoPreco;
	}

	public void setTipoPreco(String tipoPreco) {
		this.tipoPreco = tipoPreco;
	}

	public String getTipoFinan() {
		return tipoFinan;
	}

	public void setTipoFinan(String tipoFinan) {
		this.tipoFinan = tipoFinan;
	}

	public int getFlagConsisteLimite() {
		return flagConsisteLimite;
	}

	public void setFlagConsisteLimite(int flagConsisteLimite) {
		this.flagConsisteLimite = flagConsisteLimite;
	}

	public int getFlagConsisteEstoque() {
		return flagConsisteEstoque;
	}

	public void setFlagConsisteEstoque(int flagConsisteEstoque) {
		this.flagConsisteEstoque = flagConsisteEstoque;
	}

	public int getFlagConsistePreco() {
		return flagConsistePreco;
	}

	public void setFlagConsistePreco(int flagConsistePreco) {
		this.flagConsistePreco = flagConsistePreco;
	}

	public int getFlagConsisteSituacaoCliente() {
		return flagConsisteSituacaoCliente;
	}

	public void setFlagConsisteSituacaoCliente(int flagConsisteSituacaoCliente) {
		this.flagConsisteSituacaoCliente = flagConsisteSituacaoCliente;
	}

	public Integer getControleNumeracaoCodigo() {
		return controleNumeracaoCodigo;
	}

	public void setControleNumeracaoCodigo(Integer controleNumeracaoCodigo) {
		this.controleNumeracaoCodigo = controleNumeracaoCodigo;
	}

	public Integer getFlagSeparaEstoque() {
		return flagSeparaEstoque;
	}

	public void setFlagSeparaEstoque(Integer flagSeparaEstoque) {
		this.flagSeparaEstoque = flagSeparaEstoque;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getClassificacaoNaturezaEntradaCodigo() {
		return classificacaoNaturezaEntradaCodigo;
	}

	public void setClassificacaoNaturezaEntradaCodigo(Integer classificacaoNaturezaEntradaCodigo) {
		this.classificacaoNaturezaEntradaCodigo = classificacaoNaturezaEntradaCodigo;
	}

	public String getTipoVenda() {
		return tipoVenda;
	}

	public void setTipoVenda(String tipoVenda) {
		this.tipoVenda = tipoVenda;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getCodigoFiscalEntrada() {
		return codigoFiscalEntrada;
	}

	public void setCodigoFiscalEntrada(String codigoFiscalEntrada) {
		this.codigoFiscalEntrada = codigoFiscalEntrada;
	}

	public String getCodigoFiscalSaida() {
		return codigoFiscalSaida;
	}

	public void setCodigoFiscalSaida(String codigoFiscalSaida) {
		this.codigoFiscalSaida = codigoFiscalSaida;
	}

	public String getDescricaoNFSaida() {
		return descricaoNFSaida;
	}

	public void setDescricaoNFSaida(String descricaoNFSaida) {
		this.descricaoNFSaida = descricaoNFSaida;
	}

	/*
	 * public Integer getClassificacaoNaturezaSaidaCodigo() { return
	 * classificacaoNaturezaSaidaCodigo; }
	 * 
	 * public void setClassificacaoNaturezaSaidaCodigo( Integer
	 * classificacaoNaturezaSaidaCodigo) { this.classificacaoNaturezaSaidaCodigo =
	 * classificacaoNaturezaSaidaCodigo; }
	 */

	public Integer getFlagCalculaCustoEntrada() {
		return flagCalculaCustoEntrada;
	}

	public void setFlagCalculaCustoEntrada(Integer flagCalculaCustoEntrada) {
		this.flagCalculaCustoEntrada = flagCalculaCustoEntrada;
	}

	public Integer getFlagCalculaVendaEntrada() {
		return flagCalculaVendaEntrada;
	}

	public void setFlagCalculaVendaEntrada(Integer flagCalculaVendaEntrada) {
		this.flagCalculaVendaEntrada = flagCalculaVendaEntrada;
	}

	public Integer getFlagCalculaPis() {
		return flagCalculaPis;
	}

	public void setFlagCalculaPis(Integer flagCalculaPis) {
		this.flagCalculaPis = flagCalculaPis;
	}

	public Integer getFlagCalculaCofins() {
		return flagCalculaCofins;
	}

	public void setFlagCalculaCofins(Integer flagCalculaCofins) {
		this.flagCalculaCofins = flagCalculaCofins;
	}

	public Integer getFlagCalculaIcms() {
		return flagCalculaIcms;
	}

	public void setFlagCalculaIcms(Integer flagCalculaIcms) {
		this.flagCalculaIcms = flagCalculaIcms;
	}

	public Integer getFlagLancaItem() {
		return flagLancaItem;
	}

	public void setFlagLancaItem(Integer flagLancaItem) {
		this.flagLancaItem = flagLancaItem;
	}

	public Integer getFlagCalculoFiscal() {
		return flagCalculoFiscal;
	}

	public void setFlagCalculoFiscal(Integer flagCalculoFiscal) {
		this.flagCalculoFiscal = flagCalculoFiscal;
	}

	public Integer getFlagLancamentoEspelho() {
		return flagLancamentoEspelho;
	}

	public void setFlagLancamentoEspelho(Integer flagLancamentoEspelho) {
		this.flagLancamentoEspelho = flagLancamentoEspelho;
	}

	public Integer getFlagNaoFechaResumoBases() {
		return flagNaoFechaResumoBases;
	}

	public void setFlagNaoFechaResumoBases(Integer flagNaoFechaResumoBases) {
		this.flagNaoFechaResumoBases = flagNaoFechaResumoBases;
	}

	public Integer getFlagEntrada() {
		return flagEntrada;
	}

	public void setFlagEntrada(Integer flagEntrada) {
		this.flagEntrada = flagEntrada;
	}

	public Integer getFlagSaida() {
		return flagSaida;
	}

	public void setFlagSaida(Integer flagSaida) {
		this.flagSaida = flagSaida;
	}

	public double getValorMinimoVenda() {
		return valorMinimoVenda;
	}

	public void setValorMinimoVenda(double valorMinimoVenda) {
		this.valorMinimoVenda = valorMinimoVenda;
	}

	public Integer getConfiguracaoLancamentoCliente() {
		return configuracaoLancamentoCliente;
	}

	public void setConfiguracaoLancamentoCliente(Integer configuracaoLancamentoCliente) {
		this.configuracaoLancamentoCliente = configuracaoLancamentoCliente;
	}

	public Integer getConfiguracaoLancamentoVendedor() {
		return configuracaoLancamentoVendedor;
	}

	public void setConfiguracaoLancamentoVendedor(Integer configuracaoLancamentoVendedor) {
		this.configuracaoLancamentoVendedor = configuracaoLancamentoVendedor;
	}

	public Integer getFlagLucratividade() {
		return flagLucratividade;
	}

	public void setFlagLucratividade(Integer flagLucratividade) {
		this.flagLucratividade = flagLucratividade;
	}

	public Integer getFlagPrecoUnitario() {
		return flagPrecoUnitario;
	}

	public void setFlagPrecoUnitario(Integer flagPrecoUnitario) {
		this.flagPrecoUnitario = flagPrecoUnitario;
	}

	public Integer getConfiguracaoLancamentoCondicaoPagamento() {
		return configuracaoLancamentoCondicaoPagamento;
	}

	public void setConfiguracaoLancamentoCondicaoPagamento(Integer configuracaoLancamentoCondicaoPagamento) {
		this.configuracaoLancamentoCondicaoPagamento = configuracaoLancamentoCondicaoPagamento;
	}

	public int getNumeroMaximoItens() {
		return numeroMaximoItens;
	}

	public void setNumeroMaximoItens(int numeroMaximoItens) {
		this.numeroMaximoItens = numeroMaximoItens;
	}

	@Override
	public int getConfiguracaoPrecoSugerido() {
		return configuracaoPrecoSugerido != null ? configuracaoPrecoSugerido.intValue() : 0;
	}

	public void setConfiguracaoPrecoSugerido(Integer configuracaoPrecoSugerido) {
		this.configuracaoPrecoSugerido = configuracaoPrecoSugerido;
	}

	@Override
	public double getResultadoMinimoValor() {
		return resultadoMinimoValor;
	}

	public void setResultadoMinimoValor(double valorResultadoMinimo) {
		this.resultadoMinimoValor = valorResultadoMinimo;
	}

	@Override
	public double getResultadoMinimoPercentual() {
		return resultadoMinimoPercentual;
	}

	public void setResultadoMinimoPercentual(double percentualResultadoMinimo) {
		this.resultadoMinimoPercentual = percentualResultadoMinimo;
	}

	public double getResultadoMinimoSemImpostoValor() {
		return resultadoMinimoSemImpostoValor;
	}

	public void setResultadoMinimoSemImpostoValor(double resultadoMinimoSemImpostoValor) {
		this.resultadoMinimoSemImpostoValor = resultadoMinimoSemImpostoValor;
	}

	public double getResultadoMinimoSemImpostoPercentual() {
		return resultadoMinimoSemImpostoPercentual;
	}

	public void setResultadoMinimoSemImpostoPercentual(double resultadoMinimoSemImpostoPercentual) {
		this.resultadoMinimoSemImpostoPercentual = resultadoMinimoSemImpostoPercentual;
	}

	public int getFlagExibeLucratividade() {
		return flagExibeLucratividade;
	}

	public void setFlagExibeLucratividade(int flagExibeLucratividade) {
		this.flagExibeLucratividade = flagExibeLucratividade;
	}

	public Integer getFlagOrdemCompraObrigatorio() {
		return flagOrdemCompraObrigatorio;
	}

	public void setFlagOrdemCompraObrigatorio(Integer flagOrdemCompraObrigatorio) {
		this.flagOrdemCompraObrigatorio = flagOrdemCompraObrigatorio;
	}

	public Integer getLayoutEntradaCodigo() {
		return layoutEntradaCodigo;
	}

	public void setLayoutEntradaCodigo(Integer layoutEntradaCodigo) {
		this.layoutEntradaCodigo = layoutEntradaCodigo;
	}

	public Integer getLayoutSaidaCodigo() {
		return layoutSaidaCodigo;
	}

	public void setLayoutSaidaCodigo(Integer layoutSaidaCodigo) {
		this.layoutSaidaCodigo = layoutSaidaCodigo;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public Integer getFlagAlteraValoresImpostos() {
		return flagAlteraValoresImpostos;
	}

	public void setFlagAlteraValoresImpostos(Integer flagAlteraValoresImpostos) {
		this.flagAlteraValoresImpostos = flagAlteraValoresImpostos;
	}

	public Integer getFlagLancamentoPreVenda() {
		return flagLancamentoPreVenda;
	}

	public void setFlagLancamentoPreVenda(Integer flagLancamentoPreVenda) {
		this.flagLancamentoPreVenda = flagLancamentoPreVenda;
	}

	public Integer getFlagExigePedidoNF() {
		return flagExigePedidoNF;
	}

	public void setFlagExigePedidoNF(Integer flagExigePedidoNF) {
		this.flagExigePedidoNF = flagExigePedidoNF;
	}

	public Integer getFlagAlteraNfPedido() {
		return flagAlteraNfPedido;
	}

	public void setFlagAlteraNfPedido(Integer flagAlteraNfPedido) {
		this.flagAlteraNfPedido = flagAlteraNfPedido;
	}

	public Float getBaseCalculo() {
		return baseCalculo;
	}

	public void setBaseCalculo(Float baseCalculo) {
		this.baseCalculo = baseCalculo;
	}

	public Float getOutras() {
		return outras;
	}

	public void setOutras(Float outras) {
		this.outras = outras;
	}

	public Float getIsentas() {
		return isentas;
	}

	public void setIsentas(Float isentas) {
		this.isentas = isentas;
	}

	public String getCstSaidaCodigo() {
		return cstSaidaCodigo;
	}

	public void setCstSaidaCodigo(String cstSaidaCodigo) {
		this.cstSaidaCodigo = cstSaidaCodigo;
	}

	public Integer getFlagCalculaIpiEntrada() {
		return flagCalculaIpiEntrada;
	}

	public void setFlagCalculaIpiEntrada(Integer flagCalculaIpiEntrada) {
		this.flagCalculaIpiEntrada = flagCalculaIpiEntrada;
	}

	public Integer getFlagCalculaIpiSaida() {
		return flagCalculaIpiSaida;
	}

	public void setFlagCalculaIpiSaida(Integer flagCalculaIpiSaida) {
		this.flagCalculaIpiSaida = flagCalculaIpiSaida;
	}

	public Integer getFlagAtualizaFornecedorProduto() {
		return flagAtualizaFornecedorProduto;
	}

	public void setFlagAtualizaFornecedorProduto(Integer flagAtualizaFornecedorProduto) {
		this.flagAtualizaFornecedorProduto = flagAtualizaFornecedorProduto;
	}

	public Integer getLayoutPreVendaCodigo() {
		return layoutPreVendaCodigo;
	}

	public void setLayoutPreVendaCodigo(Integer layoutPreVendaCodigo) {
		this.layoutPreVendaCodigo = layoutPreVendaCodigo;
	}

	public Integer getLayoutNFCodigo() {
		return layoutNFCodigo;
	}

	public void setLayoutNFCodigo(Integer layoutNFCodigo) {
		this.layoutNFCodigo = layoutNFCodigo;
	}

	public Integer getFlagImprimeNFEntrada() {
		return flagImprimeNFEntrada;
	}

	public void setFlagImprimeNFEntrada(Integer flagImprimeNFEntrada) {
		this.flagImprimeNFEntrada = flagImprimeNFEntrada;
	}

	public Integer getFlagCarga() {
		return flagCarga;
	}

	public void setFlagCarga(Integer flagCarga) {
		this.flagCarga = flagCarga;
	}

	public String getModeloFiscalCodigo() {
		return modeloFiscalCodigo;
	}

	public void setModeloFiscalCodigo(String modeloFiscalCodigo) {
		this.modeloFiscalCodigo = modeloFiscalCodigo;
	}

	public Integer getFlagImprimePedidoAutomatico() {
		return flagImprimePedidoAutomatico;
	}

	public void setFlagImprimePedidoAutomatico(Integer flagImprimePedidoAutomatico) {
		this.flagImprimePedidoAutomatico = flagImprimePedidoAutomatico;
	}

	public boolean isUtilizaOrigemVenda() {
		return flagUtilizaOrigemVenda == 1;
	}

	public Integer getFlagUtilizaOrigemVenda() {
		return flagUtilizaOrigemVenda;
	}

	public void setFlagUtilizaOrigemVenda(Integer flagUtilizaOrigemVenda) {
		this.flagUtilizaOrigemVenda = flagUtilizaOrigemVenda;
	}

	public Integer getFlagDataEmissaoPedido() {
		return flagDataEmissaoPedido;
	}

	public void setFlagDataEmissaoPedido(Integer flagDataEmissaoPedido) {
		this.flagDataEmissaoPedido = flagDataEmissaoPedido;
	}

	public Integer getTabelaPrecoNFCodigo() {
		return tabelaPrecoNFCodigo;
	}

	public void setTabelaPrecoNFCodigo(Integer tabelaPrecoNFCodigo) {
		this.tabelaPrecoNFCodigo = tabelaPrecoNFCodigo;
	}

	public Integer getPlanoContasPagarCodigo() {
		return planoContasPagarCodigo;
	}

	public void setPlanoContasPagarCodigo(Integer planoContasPagarCodigo) {
		this.planoContasPagarCodigo = planoContasPagarCodigo;
	}

	public Integer getPlanoContasReceberCodigo() {
		return planoContasReceberCodigo;
	}

	public void setPlanoContasReceberCodigo(Integer planoContasReceberCodigo) {
		this.planoContasReceberCodigo = planoContasReceberCodigo;
	}

	public Integer getPlanoContasLucroCodigo() {
		return planoContasLucroCodigo;
	}

	public void setPlanoContasLucroCodigo(Integer planoContasLucroCodigo) {
		this.planoContasLucroCodigo = planoContasLucroCodigo;
	}

	public Integer getLayoutOrcamentoCodigo() {
		return layoutOrcamentoCodigo;
	}

	public void setLayoutOrcamentoCodigo(Integer layoutOrcamentoCodigo) {
		this.layoutOrcamentoCodigo = layoutOrcamentoCodigo;
	}

	public Integer getPlanoContasFreteCodigo() {
		return planoContasFreteCodigo;
	}

	public void setPlanoContasFreteCodigo(Integer planoContasFreteCodigo) {
		this.planoContasFreteCodigo = planoContasFreteCodigo;
	}

	public Integer getFlagLancaFinanceiroEntrada() {
		return flagLancaFinanceiroEntrada;
	}

	public void setFlagLancaFinanceiroEntrada(Integer flagLancaFinanceiroEntrada) {
		this.flagLancaFinanceiroEntrada = flagLancaFinanceiroEntrada;
	}

	public Integer getLayoutPedidoParcialCodigo() {
		return layoutPedidoParcialCodigo;
	}

	public void setLayoutPedidoParcialCodigo(Integer layoutPedidoParcialCodigo) {
		this.layoutPedidoParcialCodigo = layoutPedidoParcialCodigo;
	}

	public Integer getFlagAtualizaReferenciaFornecedorProduto() {
		return flagAtualizaReferenciaFornecedorProduto;
	}

	public void setFlagAtualizaReferenciaFornecedorProduto(Integer flagAtualizaReferenciaFornecedorProduto) {
		this.flagAtualizaReferenciaFornecedorProduto = flagAtualizaReferenciaFornecedorProduto;
	}

	public String getTipoSeparacao() {
		return tipoSeparacao;
	}

	public void setTipoSeparacao(String tipoSeparacao) {
		this.tipoSeparacao = tipoSeparacao;
	}

	public String getCodigoProdutoNFE() {
		return codigoProdutoNFE;
	}

	public void setCodigoProdutoNFE(String codigoProdutoNFE) {
		this.codigoProdutoNFE = codigoProdutoNFE;
	}

	public String getDescricaoProdutoNFE() {
		return descricaoProdutoNFE;
	}

	public void setDescricaoProdutoNFE(String descricaoProdutoNFE) {
		this.descricaoProdutoNFE = descricaoProdutoNFE;
	}

	public Integer getFlagNfcomplemento() {
		return flagNfcomplemento;
	}

	public void setFlagNfcomplemento(Integer flagNfcomplemento) {
		this.flagNfcomplemento = flagNfcomplemento;
	}

	public String getCodigoFiscalDevolucao() {
		return codigoFiscalDevolucao;
	}

	public void setCodigoFiscalDevolucao(String codigoFiscalDevolucao) {
		this.codigoFiscalDevolucao = codigoFiscalDevolucao;
	}

	public Integer getRemessaRetorno() {
		return remessaRetorno;
	}

	public void setRemessaRetorno(Integer remessaRetorno) {
		this.remessaRetorno = remessaRetorno;
	}

	public String getMensagem2() {
		return mensagem2;
	}

	public void setMensagem2(String mensagem2) {
		this.mensagem2 = mensagem2;
	}

	public Integer getFlagLancaLocalLotePedidoSaida() {
		return flagLancaLocalLotePedidoSaida;
	}

	public void setFlagLancaLocalLotePedidoSaida(Integer flagLancaLocalLotePedidoSaida) {
		this.flagLancaLocalLotePedidoSaida = flagLancaLocalLotePedidoSaida;
	}

	public int getTabelaPrecoCodigo() {
		if (tabelaPrecoCodigo == null)
			return 0;
		return tabelaPrecoCodigo.intValue();
	}

	public void setTabelaPrecoCodigo(int tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	public Integer getFlagNaoCalculaStEntrada() {
		return flagNaoCalculaStEntrada;
	}

	public void setFlagNaoCalculaStEntrada(Integer flagNaoCalculaStEntrada) {
		this.flagNaoCalculaStEntrada = flagNaoCalculaStEntrada;
	}

	public Integer getFlagFormaProcessoSt() {
		return flagFormaProcessoSt;
	}

	public void setFlagFormaProcessoSt(Integer flagFormaProcessoSt) {
		this.flagFormaProcessoSt = flagFormaProcessoSt;
	}

	public Integer getFlagNfServico() {
		return flagNfServico;
	}

	public void setFlagNfServico(Integer flagNfServico) {
		this.flagNfServico = flagNfServico;
	}

	public Integer getFlagAgrupaProdutoRetorno() {
		return flagAgrupaProdutoRetorno;
	}

	public void setFlagAgrupaProdutoRetorno(Integer flagAgrupaProdutoRetorno) {
		this.flagAgrupaProdutoRetorno = flagAgrupaProdutoRetorno;
	}

	public Integer getFlagEnviaPalm() {
		return flagEnviaPalm;
	}

	public void setFlagEnviaPalm(Integer flagEnviaPalm) {
		this.flagEnviaPalm = flagEnviaPalm;
	}

	public String getCsosnCodigo() {
		return csosnCodigo;
	}

	public void setCsosnCodigo(String csosnCodigo) {
		this.csosnCodigo = csosnCodigo;
	}

	public Integer getFlagNaoExportaCupom() {
		return flagNaoExportaCupom;
	}

	public void setFlagNaoExportaCupom(Integer flagNaoExportaCupom) {
		this.flagNaoExportaCupom = flagNaoExportaCupom;
	}

	public Integer getFlagObrigatorioLocalEstoque() {
		return flagObrigatorioLocalEstoque;
	}

	public void setFlagObrigatorioLocalEstoque(Integer flagObrigatorioLocalEstoque) {
		this.flagObrigatorioLocalEstoque = flagObrigatorioLocalEstoque;
	}

	public Integer getFlagSugereLocalEstoquePadrao() {
		return flagSugereLocalEstoquePadrao;
	}

	public void setFlagSugereLocalEstoquePadrao(Integer flagSugereLocalEstoquePadrao) {
		this.flagSugereLocalEstoquePadrao = flagSugereLocalEstoquePadrao;
	}

	public Integer getLocalEstoquePadrao() {
		return localEstoquePadrao;
	}

	public void setLocalEstoquePadrao(Integer localEstoquePadrao) {
		this.localEstoquePadrao = localEstoquePadrao;
	}

	public Integer getFlagNaoLancaFinanceiro() {
		return flagNaoLancaFinanceiro;
	}

	public void setFlagNaoLancaFinanceiro(Integer flagNaoLancaFinanceiro) {
		this.flagNaoLancaFinanceiro = flagNaoLancaFinanceiro;
	}

	public Integer getLocalEstoquePadraoEntrada() {
		return localEstoquePadraoEntrada;
	}

	public void setLocalEstoquePadraoEntrada(Integer localEstoquePadraoEntrada) {
		this.localEstoquePadraoEntrada = localEstoquePadraoEntrada;
	}

	public Integer getFlagLancaPrecoUnitario() {
		return flagLancaPrecoUnitario;
	}

	public void setFlagLancaPrecoUnitario(Integer flagLancaPrecoUnitario) {
		this.flagLancaPrecoUnitario = flagLancaPrecoUnitario;
	}

	public Integer getFlagCancelaDigitacaoProdutoSemEstoque() {
		return flagCancelaDigitacaoProdutoSemEstoque;
	}

	public void setFlagCancelaDigitacaoProdutoSemEstoque(Integer flagCancelaDigitacaoProdutoSemEstoque) {
		this.flagCancelaDigitacaoProdutoSemEstoque = flagCancelaDigitacaoProdutoSemEstoque;
	}

	public Integer getFlagBloqueiaFaturamentoSemCarga() {
		return flagBloqueiaFaturamentoSemCarga;
	}

	public void setFlagBloqueiaFaturamentoSemCarga(Integer flagBloqueiaFaturamentoSemCarga) {
		this.flagBloqueiaFaturamentoSemCarga = flagBloqueiaFaturamentoSemCarga;
	}

	public Integer getFlagSelecionaCustoSaida() {
		return flagSelecionaCustoSaida;
	}

	public void setFlagSelecionaCustoSaida(Integer flagSelecionaCustoSaida) {
		this.flagSelecionaCustoSaida = flagSelecionaCustoSaida;
	}

	public Integer getCustoSaidaResultado() {
		return custoSaidaResultado;
	}

	public void setCustoSaidaResultado(Integer custoSaidaResultado) {
		this.custoSaidaResultado = custoSaidaResultado;
	}

	public Integer getFlagSelecionaCustoEntrada() {
		return flagSelecionaCustoEntrada;
	}

	public void setFlagSelecionaCustoEntrada(Integer flagSelecionaCustoEntrada) {
		this.flagSelecionaCustoEntrada = flagSelecionaCustoEntrada;
	}

	public String getCustosAtualizadosEntrada() {
		return custosAtualizadosEntrada;
	}

	public void setCustosAtualizadosEntrada(String custosAtualizadosEntrada) {
		this.custosAtualizadosEntrada = custosAtualizadosEntrada;
	}

	public Integer getCustoMedioLocalEstoque() {
		return custoMedioLocalEstoque;
	}

	public void setCustoMedioLocalEstoque(Integer custoMedioLocalEstoque) {
		this.custoMedioLocalEstoque = custoMedioLocalEstoque;
	}

	public Integer getPrecoBasePadraoExibicaoCodigo() {
		return precoBasePadraoExibicaoCodigo;
	}

	public void setPrecoBasePadraoExibicaoCodigo(Integer precoBasePadraoExibicaoCodigo) {
		this.precoBasePadraoExibicaoCodigo = precoBasePadraoExibicaoCodigo;
	}

	public Integer getFlagAtualizaPrecoVendaCustosSelecionados() {
		return flagAtualizaPrecoVendaCustosSelecionados;
	}

	public void setFlagAtualizaPrecoVendaCustosSelecionados(Integer flagAtualizaPrecoVendaCustosSelecionados) {
		this.flagAtualizaPrecoVendaCustosSelecionados = flagAtualizaPrecoVendaCustosSelecionados;
	}

	public Integer getFlagNaoEnviaSped() {
		return flagNaoEnviaSped;
	}

	public void setFlagNaoEnviaSped(Integer flagNaoEnviaSped) {
		this.flagNaoEnviaSped = flagNaoEnviaSped;
	}

	public Integer getFlagGeraNFCFAutomaticamente() {
		return flagGeraNFCFAutomaticamente;
	}

	public void setFlagGeraNFCFAutomaticamente(Integer flagGeraNFCFAutomaticamente) {
		this.flagGeraNFCFAutomaticamente = flagGeraNFCFAutomaticamente;
	}

	public Integer getFlagFornecimentoVerba() {
		return flagFornecimentoVerba;
	}

	public void setFlagFornecimentoVerba(Integer flagFornecimentoVerba) {
		this.flagFornecimentoVerba = flagFornecimentoVerba;
	}

	public Integer getFlagControleDescarga() {
		return flagControleDescarga;
	}

	public void setFlagControleDescarga(Integer flagControleDescarga) {
		this.flagControleDescarga = flagControleDescarga;
	}

	public boolean isSugereAtendenteInclusaoPedido() {
		return flagSugereAtendenteInclusaoPedido != null && flagSugereAtendenteInclusaoPedido == 1;
	}

	public Integer getFlagSugereAtendenteInclusaoPedido() {
		return flagSugereAtendenteInclusaoPedido;
	}

	public void setFlagSugereAtendenteInclusaoPedido(Integer flagSugereAtendenteInclusaoPedido) {
		this.flagSugereAtendenteInclusaoPedido = flagSugereAtendenteInclusaoPedido;
	}

	public int getConfiguracaoOrigemTabelaPreco() {
		return configuracaoOrigemTabelaPreco;
	}

	public void setConfiguracaoOrigemTabelaPreco(int configuracaoOrigemTabelaPreco) {
		this.configuracaoOrigemTabelaPreco = configuracaoOrigemTabelaPreco;
	}

	public int getFlagPermiteAlterarPreco() {
		return flagPermiteAlterarPreco;
	}

	public void setFlagPermiteAlterarPreco(int flagPermiteAlterarPreco) {
		this.flagPermiteAlterarPreco = flagPermiteAlterarPreco;
	}

	@Override
	public int getFlagPermiteRepeticaoProduto() {
		if (flagPermiteRepeticaoProduto == null) {
			return 0;
		}

		return flagPermiteRepeticaoProduto;
	}

	public void setFlagPermiteRepeticaoProduto(Integer flagPermiteRepeticaoProduto) {

		this.flagPermiteRepeticaoProduto = flagPermiteRepeticaoProduto;
	}

	public ClassificacaoNaturezaOperacao getClassificacaoNaturezaSaida() {
		return classificacaoNaturezaSaida;
	}

	public void setClassificacaoNaturezaSaida(ClassificacaoNaturezaOperacao classificaNaturezaOperacaoSaida) {
		this.classificacaoNaturezaSaida = classificaNaturezaOperacaoSaida;
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

	public void setTabelaPrecoCodigo(Integer tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	@Override
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

	/**
	 * 
	 * @param dao
	 * @param codigo
	 * @return
	 */
	public static NaturezaOperacao recuperarUnico(GenericoDAO dao, String codigo) {

		return dao.uniqueResult(NaturezaOperacao.class, COLUNA_CODIGO + " = '" + codigo + "'", null);
	}

	public boolean isInterna() {
		return tipoVenda.equals("I");
	}

	public boolean isExterna() {
		return tipoVenda.equals("E");
	}

	public int getFlagLiberaVendaWeb() {
		return flagLiberaVendaWeb;
	}

	public void setFlagLiberaVendaWeb(int flagLiberaVendaWeb) {
		this.flagLiberaVendaWeb = flagLiberaVendaWeb;
	}

	public int getFlagLiberaVenda() {
		return flagLiberaVenda;
	}

	public void setFlagLiberaVenda(int flagLiberaVenda) {
		this.flagLiberaVenda = flagLiberaVenda;
	}
}
