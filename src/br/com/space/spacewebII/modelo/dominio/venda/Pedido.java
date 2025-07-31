/*
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.core.propriedade.Propriedade;
import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IGrupoVenda;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.padrao.interfaces.Travavel;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = Pedido.NOME_TABELA)
@XmlRootElement
public class Pedido implements Serializable, IPedidoWeb, Travavel {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELA = "pedidos";

	public static final String COLUNA_NUMERO = "ped_numero";
	public static final String COLUNA_SERIE_CODIGO = "ped_spvcodigo";
	public static final String COLUNA_FILIAL_CODIGO = "ped_filcodigo";
	public static final String COLUNA_VENDEDOR_CODIGO = "ped_vencodigo";
	public static final String COLUNA_DATA_EMISSAO = "ped_dtemissao";
	public static final String COLUNA_STATUS_PEDIDO = "ped_stpcodigo";
	public static final String COLUNA_CLIENTE_CODIGO = "ped_pescodigo";

	/**
	 * Status de pagamento com cartão.
	 * 
	 * 0 = Sem Pagamento, 1 = Aguardando Confirmação, 2 = Confirmado
	 */

	public static final int STATUS_PAGAMENTO_CARTAO_SEM_PAGAMENTO = 0;
	public static final int STATUS_PAGAMENTO_CARTAO_AGUARDANDO_PAGAMENTO = 1;
	public static final int STATUS_PAGAMENTO_CARTAO_CONFIRMADO = 2;

	@EmbeddedId
	protected PedidoPK pedidoPK;

	@Basic(optional = false)
	@Column(name = COLUNA_STATUS_PEDIDO)
	private int statusPedidoCodigo;

	@JoinColumn(name = COLUNA_STATUS_PEDIDO, referencedColumnName = StatusPedido.COLUNA_CODIGO, insertable = false, updatable = false)
	@ManyToOne
	private StatusPedido statusPedido;

	/**
	 * Status de pagamento com cartão.
	 * 
	 * 0 = Sem Pagamento, 1 = Aguardando Confirmação, 2 = Confirmado
	 */
	@Column(name = "ped_stpgct")
	private int statusPagamentoCartao;

	@Basic(optional = false)
	@Column(name = COLUNA_DATA_EMISSAO)
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;

	@Basic(optional = false)
	@Column(name = COLUNA_CLIENTE_CODIGO)
	private int pessoaCodigo;

	@Basic(optional = false)
	@Column(name = COLUNA_VENDEDOR_CODIGO)
	private int vendedorCodigo;

	@Basic(optional = false)
	@Column(name = "ped_fpgcodigo")
	private String formaPagamentoCodigo;

	@Basic(optional = false)
	@Column(name = "ped_cpgcodigo")
	private int condicaoPagamentoCodigo;

	@Column(name = "ped_carga")
	private Integer carga;

	@Column(name = "ped_dscperc")
	private double descontoPercentual;

	@Column(name = "ped_dscvalor")
	private double descontoValor;

	@Column(name = "ped_acrperc")
	private double acrescimoPercentual;

	@Column(name = "ped_acrvalor")
	private double acrescimoValor;

	@Column(name = "ped_tprcodigo")
	private int tabelaPrecoCodigo;

	@Column(name = "ped_debcred")
	private int flagDebitoCredito;

	@Column(name = "ped_frete")
	private Double frete;

	@Column(name = "ped_qtdimp")
	private Integer quantidadeImpressao;

	@Column(name = "ped_origem")
	private String origem;

	@Column(name = "ped_codorigem")
	private String codigoOrigem;

	@Column(name = "ped_endentrega")
	private Integer enderecoEntregaCodigo;

	@Column(name = "ped_endcobr")
	private Integer enderecoCobrancaCodigo;

	@Column(name = "ped_orccodigo")
	private Integer orcamentoCodigo;

	@Column(name = "ped_lucrativida")
	private Double lucratividade;

	@Basic(optional = false)
	@Column(name = "ped_orvcodigo")
	private int origemVendaCodigo;

	@Basic(optional = false)
	@Column(name = "ped_datacad")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(name = "ped_ativo")
	private int flagAtivo;

	@Basic(optional = false)
	@Column(name = "ped_prbcodigo")
	private int precoBaseCodigo;

	@Basic(optional = false)
	@Column(name = "ped_cnmcodigo")
	private int controleNumeroCodigo;

	@Basic(optional = false)
	@Column(name = "ped_horacad")
	private String horaCadastro;

	@Column(name = "ped_obs")
	private String observacao;

	@Basic(optional = false)
	@Column(name = "ped_llccodigo")
	private int loteLancamentoFinanceiro;

	@Column(name = "ped_pesobruto")
	private Double pesoBruto;

	@Column(name = "ped_lnpcodigo")
	private Integer loteNotaPedidoCodigo;

	@Column(name = "ped_dtentrega")
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;

	@Column(name = "ped_obs1")
	private String observacao1;

	@Column(name = "ped_obs2")
	private String observacao2;

	@Column(name = "ped_valcomis")
	private Double valorComissao;

	@Column(name = "ped_lpicodigo")
	private Integer lotePedidoImportadoCodigo;

	@Column(name = "ped_valor")
	private double valor;

	@Column(name = "ped_vlsugdbcr")
	private double valorSugeridoDebitoCredito;

	@Column(name = "ped_vlvendbcr")
	private double valorVendaDebitoCredito;

	@Basic(optional = false)
	@Column(name = "ped_valcomis2")
	private double valorComissao2;

	@Basic(optional = false)
	@Column(name = "ped_valcomis3")
	private double valorComissao3;

	@Basic(optional = false)
	@Column(name = "ped_valcomis4")
	private double valorComissao4;

	@Basic(optional = false)
	@Column(name = "ped_valcomis5")
	private double valorComissao5;

	@Column(name = "ped_pescodigot")
	private Integer transportadoraCodigo;

	@Column(name = "ped_campousr1")
	private String campoUsuario1;

	@Column(name = "ped_campousr2")
	private String campoUsuario2;

	@Column(name = "ped_campousr3")
	private String campoUsuario3;

	@Column(name = "ped_campousr4")
	private String campoUsuario4;

	@Column(name = "ped_campousr5")
	private String campoUsuario5;

	@Column(name = "ped_qtdevol")
	private Double quantidadeVolumes;

	@Column(name = "ped_tiposepara")
	private String tipoSeparacao;

	@Column(name = "ped_numentrega")
	private int numeroEntregas;

	@Column(name = "ped_oepcodigo")
	private int opcaoEspecialCodigo;

	@Column(name = "ped_mtcubico")
	private Double metrosCubicos;

	@Column(name = "ped_cctcodigo")
	private Integer contaCorrenteCodigo;

	@Column(name = "ped_trecodigo")
	private int turnoEntregaCodigo;

	@Column(name = "ped_numsep")
	private Integer numeroSeparacoes;

	@Column(name = "ped_ordement")
	private Integer ordemEntrega;

	@Column(name = "ped_tpvcodigo")
	private Integer tipoVendaCodigo;

	@Column(name = "ped_prazoesp")
	private Integer prazoEspecial;

	@Column(name = "ped_prazopromo")
	private Integer prazoPromocao;

	@Column(name = "ped_ordemcli")
	private Integer ordemCliente;

	@Column(name = "ped_valsubst")
	private Double valorSubstituicao;

	@Column(name = "ped_valsubstav")
	private Double valorSubstituicaoAvulso;

	@Column(name = "ped_valfecp")
	private double valorFundoEstadualCombatePobreza;

	@Column(name = "ped_prmnumero")
	private Integer promocaoNumero;

	@Column(name = "ped_partpromo")
	private Integer flagParticipaPromocao;

	@Column(name = "ped_pesoliquido")
	private Double pesoLiquido;

	@Column(name = "ped_clbusr")
	private Integer colaboradorCodigo;

	@Column(name = "ped_atualizaest")
	private int flagAtualizaEstoque;

	@Column(name = "ped_tiponatop")
	private String flagTipoNaturezaOperacao;

	@Column(name = "ped_devolucao")
	private int flagDevolucao;

	@Column(name = "ped_conslimite")
	private int flagConsisteLimite;

	@Column(name = "ped_grvcodigo")
	private Integer grupoVendaCodigo;

	@Column(name = "ped_pednumpromo")
	private Integer numeroPedidoPromocao;

	@Column(name = "ped_pedspvpromo")
	private String seriePedidoPromocao;

	@Column(name = "ped_valoripi")
	private Double valorIpi;

	@Column(name = "ped_lcpnumero")
	private Integer loteComissaoNumero;

	@Column(name = "ped_natcodigo")
	private String naturezaOperacaoCodigo;

	@Column(name = "ped_prepago")
	private int flagPedidoPrePago;

	@Column(name = "ped_valoricms")
	private double valorIcms;

	@Column(name = "ped_valorpis")
	private double valorPis;

	@Column(name = "ped_valorcofins")
	private double valorCofins;

	@Column(name = "ped_lrrcodigo")
	private int localRetiraCodigo = 0;

	@Column(name = "ped_cliidentif")
	private String clienteIdentificacao;

	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ped_grvcodigo", referencedColumnName = "grv_codigo", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private GrupoVenda grupoVenda;

	private Pedido() {
		super();
		clienteIdentificacao = "";
	}

	/**
	 * 
	 */
	public Pedido(int adicionarDiasData) {

		this();

		this.dataCadastro = Conversao.somarDiasDataAtual(adicionarDiasData);
		this.dataEmissao = Conversao.somarDiasDataAtual(adicionarDiasData);
		this.dataEntrega = Conversao.somarDiasDataAtual(adicionarDiasData);
	}

	/**
	 * 
	 * @param pedidoPK
	 */
	public Pedido(PedidoPK pedidoPK, int adicionarDiasData) {
		this(adicionarDiasData);
		this.pedidoPK = pedidoPK;
	}

	public PedidoPK getPedidoPK() {
		if (this.pedidoPK == null) {
			this.pedidoPK = new PedidoPK(0, "", 0);
		}

		return pedidoPK;
	}

	public void setPedidoPK(PedidoPK pedidoPK) {
		this.pedidoPK = pedidoPK;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public int getVendedorCodigo() {
		return vendedorCodigo;
	}

	public void setVendedorCodigo(int vendedorCodigo) {
		this.vendedorCodigo = vendedorCodigo;
	}

	public String getFormaPagamentoCodigo() {
		return formaPagamentoCodigo;
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		this.formaPagamentoCodigo = formaPagamentoCodigo;
	}

	public int getCondicaoPagamentoCodigo() {
		return condicaoPagamentoCodigo;
	}

	public void setCondicaoPagamentoCodigo(int condicaoPagamentoCodigo) {
		this.condicaoPagamentoCodigo = condicaoPagamentoCodigo;
	}

	public Integer getCarga() {
		return carga;
	}

	public void setCarga(Integer carga) {
		this.carga = carga;
	}

	public double getDescontoPercentual() {
		return descontoPercentual;
	}

	public void setDescontoPercentual(double descontoPercentual) {
		this.descontoPercentual = descontoPercentual;
	}

	public double getDescontoValor() {
		return descontoValor;
	}

	public void setDescontoValor(double descontoValor) {
		this.descontoValor = descontoValor;
	}

	public double getAcrescimoPercentual() {
		return acrescimoPercentual;
	}

	public void setAcrescimoPercentual(double acrescimoPercentual) {
		this.acrescimoPercentual = acrescimoPercentual;
	}

	public double getAcrescimoValor() {
		return acrescimoValor;
	}

	public void setAcrescimoValor(double acrescimoValor) {
		this.acrescimoValor = acrescimoValor;
	}

	public int getTabelaPrecoCodigo() {
		return tabelaPrecoCodigo;
	}

	public void setTabelaPrecoCodigo(int tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	public int getFlagDebitoCredito() {
		return flagDebitoCredito;
	}

	public void setFlagDebitoCredito(int flagDebitoCredito) {
		this.flagDebitoCredito = flagDebitoCredito;
	}

	public Double getFrete() {
		return frete;
	}

	public void setFrete(Double frete) {
		this.frete = frete;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	@Override
	public double getValorSemImpostos() {

		return getValor() - getValorCofins() - getValorPis() - getValorIcms();
	}

	public Integer getQuantidadeImpressao() {
		return quantidadeImpressao;
	}

	public void setQuantidadeImpressao(Integer quantidadeImpressao) {
		this.quantidadeImpressao = quantidadeImpressao;
	}

	public String getOrigem() {
		return origem;
	}

	public void setOrigem(String origem) {
		this.origem = origem;
	}

	public String getCodigoOrigem() {
		return codigoOrigem;
	}

	public void setCodigoOrigem(String codigoOrigem) {
		this.codigoOrigem = codigoOrigem;
	}

	public int getEnderecoEntregaCodigo() {
		if (this.enderecoEntregaCodigo == null)
			return 0;
		return enderecoEntregaCodigo;
	}

	public void setEnderecoEntregaCodigo(int enderecoEntregaCodigo) {
		this.enderecoEntregaCodigo = enderecoEntregaCodigo;
	}

	public Integer getEnderecoCobrancaCodigo() {
		return enderecoCobrancaCodigo;
	}

	public void setEnderecoCobrancaCodigo(Integer enderecoCobrancaCodigo) {
		this.enderecoCobrancaCodigo = enderecoCobrancaCodigo;
	}

	public Integer getOrcamentoCodigo() {
		return orcamentoCodigo;
	}

	public void setOrcamentoCodigo(Integer orcamentoCodigo) {
		this.orcamentoCodigo = orcamentoCodigo;
	}

	public Double getLucratividade() {
		return lucratividade;
	}

	public void setLucratividade(Double lucratividade) {
		this.lucratividade = lucratividade;
	}

	public int getOrigemVendaCodigo() {
		return origemVendaCodigo;
	}

	public void setOrigemVendaCodigo(int origemVendaCodigo) {
		this.origemVendaCodigo = origemVendaCodigo;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getPrecoBaseCodigo() {
		return precoBaseCodigo;
	}

	public void setPrecoBaseCodigo(int precoBaseCodigo) {
		this.precoBaseCodigo = precoBaseCodigo;
	}

	public int getControleNumeroCodigo() {
		return controleNumeroCodigo;
	}

	public void setControleNumeroCodigo(int controleNumeroCodigo) {
		this.controleNumeroCodigo = controleNumeroCodigo;
	}

	public String getHoraCadastro() {
		return horaCadastro;
	}

	public void setHoraCadastro(String horaCadastro) {
		this.horaCadastro = horaCadastro;
	}

	public int getStatusPedidoCodigo() {
		return statusPedidoCodigo;
	}

	public void setStatusPedidoCodigo(int statusPedidoCodigo) {
		this.statusPedidoCodigo = statusPedidoCodigo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {

		this.observacao = StringUtil.removerQuebraLinha(observacao);
	}

	public int getLoteLancamentoFinanceiro() {
		return loteLancamentoFinanceiro;
	}

	public void setLoteLancamentoFinanceiro(int loteLancamentoFinanceiro) {
		this.loteLancamentoFinanceiro = loteLancamentoFinanceiro;
	}

	public Double getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Double pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public Integer getLoteNotaPedidoCodigo() {
		return loteNotaPedidoCodigo;
	}

	public void setLoteNotaPedidoCodigo(Integer loteNotaPedidoCodigo) {
		this.loteNotaPedidoCodigo = loteNotaPedidoCodigo;
	}

	public Date getDataEntrega() {
		return dataEntrega;
	}

	public void setDataEntrega(Date dataEntrega) {
		this.dataEntrega = dataEntrega;
	}

	public String getObservacao1() {
		return observacao1;
	}

	public void setObservacao1(String observacao1) {

		this.observacao1 = StringUtil.removerQuebraLinha(observacao1);

	}

	public String getObservacao2() {
		return observacao2;
	}

	public void setObservacao2(String observacao2) {

		this.observacao2 = StringUtil.removerQuebraLinha(observacao2);
	}

	public Double getValorComissao() {
		return valorComissao;
	}

	public void setValorComissao(Double valorComissao) {
		this.valorComissao = valorComissao;
	}

	public Integer getLotePedidoImportadoCodigo() {
		return lotePedidoImportadoCodigo;
	}

	public void setLotePedidoImportadoCodigo(Integer lotePedidoImportadoCodigo) {
		this.lotePedidoImportadoCodigo = lotePedidoImportadoCodigo;
	}

	public double getValorSugeridoDebitoCredito() {
		return valorSugeridoDebitoCredito;
	}

	public void setValorSugeridoDebitoCredito(double valorSugeridoDebitoCredito) {
		this.valorSugeridoDebitoCredito = valorSugeridoDebitoCredito;
	}

	public double getValorVendaDebitoCredito() {
		return valorVendaDebitoCredito;
	}

	public void setValorVendaDebitoCredito(double valorVendaDebitoCredito) {
		this.valorVendaDebitoCredito = valorVendaDebitoCredito;
	}

	public double getValorComissao2() {
		return valorComissao2;
	}

	public void setValorComissao2(double valorComissao2) {
		this.valorComissao2 = valorComissao2;
	}

	public double getValorComissao3() {
		return valorComissao3;
	}

	public void setValorComissao3(double valorComissao3) {
		this.valorComissao3 = valorComissao3;
	}

	public double getValorComissao4() {
		return valorComissao4;
	}

	public void setValorComissao4(double valorComissao4) {
		this.valorComissao4 = valorComissao4;
	}

	public double getValorComissao5() {
		return valorComissao5;
	}

	public void setValorComissao5(double valorComissao5) {
		this.valorComissao5 = valorComissao5;
	}

	public Integer getTransportadoraCodigo() {
		return transportadoraCodigo;
	}

	public void setTransportadoraCodigo(Integer transportadoraCodigo) {
		this.transportadoraCodigo = transportadoraCodigo;
	}

	public String getCampoUsuario1() {
		return campoUsuario1;
	}

	public void setCampoUsuario1(String campoUsuario1) {
		this.campoUsuario1 = campoUsuario1;
	}

	public String getCampoUsuario2() {
		return campoUsuario2;
	}

	public void setCampoUsuario2(String campoUsuario2) {
		this.campoUsuario2 = campoUsuario2;
	}

	public String getCampoUsuario3() {
		return campoUsuario3;
	}

	public void setCampoUsuario3(String campoUsuario3) {
		this.campoUsuario3 = campoUsuario3;
	}

	public String getCampoUsuario4() {
		return campoUsuario4;
	}

	public void setCampoUsuario4(String campoUsuario4) {
		this.campoUsuario4 = campoUsuario4;
	}

	public String getCampoUsuario5() {
		return campoUsuario5;
	}

	public void setCampoUsuario5(String campoUsuario5) {
		this.campoUsuario5 = campoUsuario5;
	}

	public Double getQuantidadeVolumes() {
		return quantidadeVolumes;
	}

	public void setQuantidadeVolumes(Double quantidadeVolumes) {
		this.quantidadeVolumes = quantidadeVolumes;
	}

	public String getTipoSeparacao() {
		return tipoSeparacao;
	}

	public void setTipoSeparacao(String tipoSeparacao) {
		this.tipoSeparacao = tipoSeparacao;
	}

	public int getNumeroEntregas() {
		return numeroEntregas;
	}

	public void setNumeroEntregas(int numeroEntregas) {
		this.numeroEntregas = numeroEntregas;
	}

	public int getOpcaoEspecialCodigo() {
		return opcaoEspecialCodigo;
	}

	public void setOpcaoEspecialCodigo(int opcaoEspecialCodigo) {
		this.opcaoEspecialCodigo = opcaoEspecialCodigo;
	}

	public Double getMetrosCubicos() {
		return metrosCubicos;
	}

	public void setMetrosCubicos(Double metrosCubicos) {
		this.metrosCubicos = metrosCubicos;
	}

	public Integer getContaCorrenteCodigo() {
		return contaCorrenteCodigo;
	}

	public void setContaCorrenteCodigo(Integer contaCorrenteCodigo) {
		this.contaCorrenteCodigo = contaCorrenteCodigo;
	}

	public int getTurnoEntregaCodigo() {
		return turnoEntregaCodigo;
	}

	public void setTurnoEntregaCodigo(int turnoEntregaCodigo) {
		this.turnoEntregaCodigo = turnoEntregaCodigo;
	}

	public Integer getNumeroSeparacoes() {
		return numeroSeparacoes;
	}

	public void setNumeroSeparacoes(Integer numeroSequencial) {
		this.numeroSeparacoes = numeroSequencial;
	}

	public Integer getOrdemEntrega() {
		return ordemEntrega;
	}

	public void setOrdemEntrega(Integer ordemEntrega) {
		this.ordemEntrega = ordemEntrega;
	}

	public Integer getTipoVendaCodigo() {
		return tipoVendaCodigo;
	}

	public void setTipoVendaCodigo(Integer tipoVendaCodigo) {
		this.tipoVendaCodigo = tipoVendaCodigo;
	}

	public int getPrazoEspecial() {
		if (prazoEspecial == null)
			return 0;
		else
			return prazoEspecial.intValue();
	}

	public void setPrazoEspecial(int prazoEspecial) {
		this.prazoEspecial = Integer.valueOf(prazoEspecial);
	}

	public Integer getOrdemCliente() {
		return ordemCliente;
	}

	public void setOrdemCliente(Integer ordemCliente) {
		this.ordemCliente = ordemCliente;
	}

	@Override
	public Double getValorSubstituicao() {
		return valorSubstituicao;
	}

	@Override
	public void setValorSubstituicao(Double valorSubstituicao) {
		this.valorSubstituicao = valorSubstituicao;
	}

	@Override
	public Double getValorSubstituicaoAvulso() {
		return valorSubstituicaoAvulso;
	}

	@Override
	public void setValorSubstituicaoAvulso(Double valorSubstituicaoAvulso) {
		this.valorSubstituicaoAvulso = valorSubstituicaoAvulso;
	}

	public double getValorFundoEstadualCombatePobreza() {
		return valorFundoEstadualCombatePobreza;
	}

	public void setValorFundoEstadualCombatePobreza(double valorFundoEstadualCombatePobreza) {
		this.valorFundoEstadualCombatePobreza = valorFundoEstadualCombatePobreza;
	}

	@Override
	public Integer getPromocaoNumero() {
		return promocaoNumero;
	}

	public void setPromocaoNumero(Integer promocaoNumero) {
		this.promocaoNumero = promocaoNumero;
	}

	public int getFlagParticipaPromocao() {
		if (flagParticipaPromocao == null)
			return 0;
		else
			return flagParticipaPromocao.intValue();
	}

	public void setFlagParticipaPromocao(int flagParticipaPromocao) {
		this.flagParticipaPromocao = Integer.valueOf(flagParticipaPromocao);
	}

	public Double getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(Double pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public int getColaboradorCodigo() {

		if (colaboradorCodigo == null)
			return 0;
		else
			return colaboradorCodigo.intValue();
	}

	public void setColaboradorCodigo(int colaboradorCodigo) {
		this.colaboradorCodigo = Integer.valueOf(colaboradorCodigo);
	}

	@Override
	public boolean isAtualizaEstoque() {
		return getFlagAtualizaEstoque() == 1;
	}

	public int getFlagAtualizaEstoque() {
		return flagAtualizaEstoque;
	}

	public void setFlagAtualizaEstoque(int flagAtualizaEstoque) {
		this.flagAtualizaEstoque = flagAtualizaEstoque;
	}

	public String getFlagTipoNaturezaOperacao() {
		return flagTipoNaturezaOperacao;
	}

	public void setFlagTipoNaturezaOperacao(String flagTipoNaturezaOperacao) {
		this.flagTipoNaturezaOperacao = flagTipoNaturezaOperacao;
	}

	public int getFlagDevolucao() {
		return flagDevolucao;
	}

	public void setFlagDevolucao(int flagDevolucao) {
		this.flagDevolucao = flagDevolucao;
	}

	public int getFlagConsisteLimite() {
		return flagConsisteLimite;
	}

	public void setFlagConsisteLimite(int flagConsisteLimite) {
		this.flagConsisteLimite = flagConsisteLimite;
	}

	public int getGrupoVendaCodigo() {
		if (grupoVendaCodigo == null)
			return 0;
		else
			return grupoVendaCodigo.intValue();
	}

	public void setGrupoVendaCodigo(int grupoVendaCodigo) {
		this.grupoVendaCodigo = Integer.valueOf(grupoVendaCodigo);
	}

	public Integer getNumeroPedidoPromocao() {
		return numeroPedidoPromocao;
	}

	public void setNumeroPedidoPromocao(Integer numeroPedidoPromocao) {
		this.numeroPedidoPromocao = numeroPedidoPromocao;
	}

	public String getSeriePedidoPromocao() {
		return seriePedidoPromocao;
	}

	public void setSeriePedidoPromocao(String seriePedidoPromocao) {
		this.seriePedidoPromocao = seriePedidoPromocao;
	}

	public Double getValorIpi() {
		return valorIpi;
	}

	public void setValorIpi(Double valorIpi) {
		this.valorIpi = valorIpi;
	}

	public Integer getLoteComissaoNumero() {
		return loteComissaoNumero;
	}

	public void setLoteComissaoNumero(Integer loteComissaoNumero) {
		this.loteComissaoNumero = loteComissaoNumero;
	}

	public String getNaturezaOperacaoCodigo() {
		return naturezaOperacaoCodigo;
	}

	public void setNaturezaOperacaoCodigo(String naturezaOperacaoCodigo) {
		this.naturezaOperacaoCodigo = naturezaOperacaoCodigo;
	}

	public int getFilialCodigo() {
		return this.getPedidoPK().getFilialCodigo();
	}

	public void setFilialCodigo(int filialCodigo) {
		this.getPedidoPK().setFilialCodigo(filialCodigo);
	}

	public String getSerieCodigo() {
		return this.getPedidoPK().getSerieCodigo();
	}

	public void setSerieCodigo(String serieCodigo) {
		this.getPedidoPK().setSerieCodigo(serieCodigo);
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {

	}

	@Override
	public long getDataEmissaoLong() {
		return 0;
	}

	@Override
	public long getDataEntregaLong() {
		return 0;
	}

	@Override
	public int getNumero() {
		return this.getPedidoPK().getNumero();
	}

	@Override
	public void setDataEmissaoLong(long arg0) {

	}

	@Override
	public void setDataEntregaLong(long arg0) {

	}

	@Override
	public void setNumero(int arg0) {
		this.getPedidoPK().setNumero(arg0);
	}

	public StatusPedido getStatusPedido() {
		return statusPedido;
	}

	public void setStatusPedido(StatusPedido statusPedido) {
		this.statusPedido = statusPedido;
	}

	@Override
	public int getStatusPagamentoCartao() {
		return statusPagamentoCartao;
	}

	@Override
	public void setStatusPagamentoCartao(int statusPagamentoCartao) {
		this.statusPagamentoCartao = statusPagamentoCartao;
	}

	@Override
	public String getSerieENumeroPedido(String separador) {
		return getSerieCodigo() + separador + getNumero();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public IGrupoVenda getGrupoVenda() {
		return grupoVenda;
	}

	@Override
	public void setGrupoVenda(IGrupoVenda grupoVenda) {
		this.grupoVenda = (GrupoVenda) grupoVenda;
	}

	public static List<Pedido> recuperarPedidosAguradandoPagamentoWeb(GenericoDAO dao, int filialCodigo) {
		String where = COLUNA_STATUS_PEDIDO + " = " + StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB + " and "
				+ COLUNA_FILIAL_CODIGO + " = " + filialCodigo;

		return dao.list(Pedido.class, where, null, null, null);
	}

	public static Pedido recuperarUnicoPedido(GenericoDAO dao, int pedidoCodigo, String serie, int filialCodigo) {

		if (pedidoCodigo <= 0 || filialCodigo <= 0 || serie == null) {
			return null;
		}

		String where = COLUNA_NUMERO + " = " + pedidoCodigo + " AND " + COLUNA_SERIE_CODIGO + " = '" + serie + "' AND "
				+ COLUNA_FILIAL_CODIGO + " = " + filialCodigo;

		return dao.uniqueResult(Pedido.class, where, null);
	}

	public static List<Pedido> recuperarPedidosUsuario(GenericoDAO dao, int vendedorCodigo, int filialCodigo) {

		if (vendedorCodigo <= 0 || filialCodigo <= 0) {
			return null;
		}

		return dao.list(Pedido.class, getWherePedidosUsuario(vendedorCodigo, filialCodigo), null, "dataEmissao", null);
	}

	/**
	 * 
	 * @param vendedorCodigo
	 * @param filialCodigo
	 * @return
	 */
	private static String getWherePedidosUsuario(int vendedorCodigo, int filialCodigo) {
		String where = COLUNA_VENDEDOR_CODIGO + " = " + vendedorCodigo + " AND " + COLUNA_FILIAL_CODIGO + " = "
				+ filialCodigo;
		return where;
	}

	/**
	 * 
	 * @param vendedorCodigo
	 * @param filialCodigo
	 * @param dataMin
	 * @param dataMax
	 * @param codigoStatus
	 * @return
	 */
	private static String getWhereFiltrosPedido(Date dataMin, Date dataMax, String[] codigosStatus,
			Integer clienteCodigo) {

		return getWhereFiltrosPedido(dataMin, dataMax, codigosStatus, clienteCodigo, false);

	}

	private static String getWhereFiltrosPedido(Date dataMin, Date dataMax, String[] codigosStatus,
			Integer clienteCodigo, boolean somenteDoGemini) {

		StringBuilder where = new StringBuilder();

		if (dataMin != null) {
			String dataMinStr = Conversao.formatarDataAAAAMMDD(dataMin);
			where.append(" AND ").append(COLUNA_DATA_EMISSAO).append(" >= '").append(dataMinStr).append("'");
		}

		if (dataMax != null) {
			String dataMaxStr = Conversao.formatarDataAAAAMMDD(dataMax);
			where.append(" AND ").append(COLUNA_DATA_EMISSAO).append(" <= '").append(dataMaxStr).append("'");
		}

		if (clienteCodigo != null) {
			where.append(" AND ").append(COLUNA_CLIENTE_CODIGO).append(" = ").append(clienteCodigo);
		}

		if (somenteDoGemini) {
			where.append(" AND ped_origem = 'W' ");
		}

		if (codigosStatus != null && codigosStatus.length > 0) {
			where.append(" AND ( ");

			boolean primeiro = true;
			for (String codigo : codigosStatus) {
				if (!primeiro)
					where.append(" OR ");
				else
					primeiro = false;

				where.append(COLUNA_STATUS_PEDIDO).append(" = ").append(codigo);
			}
			where.append(" )");
		}

		return where.toString();
	}

	/**
	 * Recupera os pedidos de acordo com os filtros preenchidos pelo usuário
	 * 
	 * @param dao
	 * @param vendedorCodigo
	 * @param filialCodigo
	 * @param dataMin
	 * @param dataMax
	 * @param codigoStatus
	 * @return
	 */
	public static List<Pedido> recuperarPedidosFiltradosUsuario(GenericoDAO dao, int vendedorCodigo, int filialCodigo,
			Date dataMin, Date dataMax, String[] codigosStatus, Cliente cliente) {

		return recuperarPedidosFiltradosUsuario(dao, vendedorCodigo, filialCodigo, dataMin, dataMax, codigosStatus,
				cliente, false);
	}

	public static List<Pedido> recuperarPedidosFiltradosUsuario(GenericoDAO dao, int vendedorCodigo, int filialCodigo,
			Date dataMin, Date dataMax, String[] codigosStatus, Cliente cliente, boolean somenteDoGemini) {

		if (vendedorCodigo <= 0 || filialCodigo <= 0) {
			return null;
		}

		return dao.list(Pedido.class,
				getWherePedidosUsuario(vendedorCodigo, filialCodigo) + getWhereFiltrosPedido(dataMin, dataMax,
						codigosStatus, (cliente == null ? null : cliente.getCodigo()), somenteDoGemini),
				null, "dataEmissao", null);
	}

	/**
	 * 
	 * @param dao
	 * @param clienteCodigo
	 * @param filialCodigo
	 * @param dataMin
	 * @param dataMax
	 * @param codigosStatus
	 * @param cliente
	 * @return
	 */
	public static List<Pedido> recuperarPedidosFiltradosCliente(GenericoDAO dao, int clienteCodigo, int filialCodigo,
			Date dataMin, Date dataMax, String[] codigosStatus) {

		return recuperarPedidosFiltradosCliente(dao, clienteCodigo, filialCodigo, dataMin, dataMax, codigosStatus,
				false);
	}

	public static List<Pedido> recuperarPedidosFiltradosCliente(GenericoDAO dao, int clienteCodigo, int filialCodigo,
			Date dataMin, Date dataMax, String[] codigosStatus, boolean somenteDoGemini) {

		if (clienteCodigo <= 0 || filialCodigo <= 0) {
			return null;
		}

		StringBuilder where = new StringBuilder();

		where.append(COLUNA_FILIAL_CODIGO).append(" = ").append(filialCodigo);

		where.append(getWhereFiltrosPedido(dataMin, dataMax, codigosStatus, clienteCodigo, somenteDoGemini));

		return dao.list(Pedido.class, where.toString(), null, "dataEmissao", null);
	}

	public static long recuperarQuantidadePedidosEfetivadosDia(GenericoDAO dao, int clienteCodigo, int filialCodigo,
			Date datedia) {

		String select = "select count(*) from pedidos inner join statuspedidos on stp_codigo = ped_stpcodigo and stp_bloqpedido = 0 and stp_cancpedido = 0 and stp_ativo = 1 where";

		StringBuilder count = new StringBuilder();

		count.append(select).append(" ");
		count.append(COLUNA_FILIAL_CODIGO).append(" = ").append(filialCodigo);
		count.append(getWhereFiltrosPedido(datedia, datedia, null, clienteCodigo));

		return dao.count(count.toString());

	}

	public static long recuperarQuantidadePedidos(GenericoDAO dao, int clienteCodigo, int filialCodigo, Date dataMin,
			Date dataMax, String[] codigosStatus) {

		if (clienteCodigo <= 0 || filialCodigo <= 0) {
			return 0;
		}

		StringBuilder where = new StringBuilder().append(COLUNA_FILIAL_CODIGO).append(" = ").append(filialCodigo);

		where.append(getWhereFiltrosPedido(dataMin, dataMax, codigosStatus, clienteCodigo));

		return dao.count("pedidos", where.toString());
	}

	/**
	 * 
	 * @param dao
	 * @param vendedorCodigo
	 * @param filialCodigo
	 * @return
	 */
	public static long recuperarQuantidadePedidosUsuario(GenericoDAO dao, int vendedorCodigo, int filialCodigo) {
		return dao.count("pedidos", getWherePedidosUsuario(vendedorCodigo, filialCodigo));
	}

	/**
	 * 
	 * @param dao
	 * @param codigoSessao
	 * @return
	 */
	public static List<Pedido> recuperarPedidosEmInclusao(GenericoDAO dao, int codigoSessao, int filialCodigo) {

		StringBuilder whereSessao = new StringBuilder();

		if (codigoSessao > 0) {
			whereSessao.append(" and lgp_sescodigo = ").append(codigoSessao);
		}

		String select = "select pedidos.* from pedidos inner join logpedidos on ped_numero = lgp_pednumero and ped_filcodigo = "
				+ filialCodigo + " and ped_stpcodigo = " + StatusPedido.STATUS_EM_INCLUSAO + whereSessao;

		List<Pedido> pedidos = null;
		try {
			pedidos = dao.list(Pedido.class, select);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pedidos;
	}

	/**
	 * 
	 * @param dao
	 * @param codigoSessao
	 * @return
	 */
	public static List<Pedido> recuperarPedidosEmAlteracao(GenericoDAO dao, int codigoSessao) {

		StringBuilder whereSessao = new StringBuilder();

		if (codigoSessao > 0) {
			whereSessao.append(" and lgp_sescodigo = ").append(codigoSessao);
		}

		String select = "select pedidos.* from pedidos inner join logpedidos on ped_numero = lgp_pednumero and ped_stpcodigo = "
				+ StatusPedido.STATUS_EM_ALTERACAO + whereSessao;

		List<Pedido> pedidos = null;
		try {
			pedidos = dao.list(Pedido.class, select);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return pedidos;
	}

	@Override
	public String getChaveTrava() {
		return String.valueOf(this.getFilialCodigo()) + Conversao.formatarEspacoDireita(this.getSerieCodigo(), 3)
				+ String.valueOf(this.getNumero());
	}

	@Override
	public String getNomeTabelaTrava() {
		return Pedido.NOME_TABELA;
	}

	@Override
	public int getPrazoPromocao() {
		return prazoPromocao;
	}

	@Override
	public void setPrazoPromocao(int prazoPromocao) {
		this.prazoPromocao = prazoPromocao;
	}

	public double getValorIcms() {
		return valorIcms;
	}

	public void setValorIcms(double valorIcms) {
		this.valorIcms = valorIcms;
	}

	public double getValorPis() {
		return valorPis;
	}

	public void setValorPis(double valorPis) {
		this.valorPis = valorPis;
	}

	public double getValorCofins() {
		return valorCofins;
	}

	public void setValorCofins(double valorCofins) {
		this.valorCofins = valorCofins;
	}

	public int getLocalRetiraCodigo() {
		return localRetiraCodigo;
	}

	public void setLocalRetiraCodigo(int localRetiraCodigo) {
		this.localRetiraCodigo = localRetiraCodigo;
	}

	public int getFlagPedidoPrePago() {
		return flagPedidoPrePago;
	}

	public void setFlagPedidoPrePago(int flagPedidoPrePago) {
		this.flagPedidoPrePago = flagPedidoPrePago;
	}

	@Override
	public String getClienteIdentificacao() {
		return clienteIdentificacao;
	}

	public void setClienteIdentificacao(String clienteIdentificacao) {
		this.clienteIdentificacao = clienteIdentificacao;
	}

	public String getDescricaoLog(Propriedade propriedade) {

		return getDescricaoLog(getStatusPedidoCodigo(), propriedade);

	}

	public static String getDescricaoLog(int statusPedidoCodigo, Propriedade propriedade) {

		switch (statusPedidoCodigo) {
		case StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB:

			return propriedade.getMensagem("texto.logPedido.pedidoaguardandopagamento");

		case StatusPedido.STATUS_NAO_SEPARADO:

			return propriedade.getMensagem("texto.logPedido.pedidoConfirmado");

		case StatusPedido.STATUS_EM_ALTERACAO:

			return propriedade.getMensagem("texto.logPedido.inicioPedidoAlteracao");

		case StatusPedido.STATUS_CANCELADO:

			return propriedade.getMensagem("texto.logPedido.pedidoCancelado");

		case StatusPedido.STATUS_CANCELADO_INCLUSAO:

			return propriedade.getMensagem("texto.logPedido.pedidoCancelaInclusao");

		case StatusPedido.STATUS_EM_INCLUSAO:

			return propriedade.getMensagem("texto.logPedido.pedidoInclusao");

		case StatusPedido.STATUS_SEPARADO:

			return propriedade.getMensagem("texto.logPedido.separado");

		case StatusPedido.STATUS_SEPARADO_PARCIAL:

			return propriedade.getMensagem("texto.logPedido.separadoparcialmente");

		case StatusPedido.STATUS_ENVIADO_OUTRO_SETOR:

			return propriedade.getMensagem("texto.logPedido.enviadooutrosetor");

		case StatusPedido.STATUS_BLOQUEADO:

			return propriedade.getMensagem("texto.logPedido.pedidobloqueado");

		}

		return null;
	}
}
