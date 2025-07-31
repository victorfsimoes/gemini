/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IGrupoVenda;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.padrao.interfaces.Travavel;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "pedidosaux")
@XmlRootElement
public class PedidoAuxiliar implements Serializable, IPedidoWeb, Travavel, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_NUMERO = "pex_numero";
	public static final String COLUNA_SERIE_CODIGO = "pex_spvcodigo";
	public static final String COLUNA_FILIAL_CODIGO = "pex_filcodigo";

	/*
	 * Enviando nome da tabela como pedidos para que a rotina de trava de
	 * registros trave a tabela de pedidos
	 */
	public static final String NOME_TABELA = "pedidos";

	@EmbeddedId
	protected PedidoAuxiliarPK pedidoAuxiliarPK;

	@Basic(optional = false)
	@Column(name = "pex_dtemissao")
	@Temporal(TemporalType.DATE)
	private Date dataEmissao;

	@Basic(optional = false)
	@Column(name = "pex_pescodigo")
	private int pessoaCodigo;

	@Basic(optional = false)
	@Column(name = "pex_vencodigo")
	private int vendedorCodigo;

	@Basic(optional = false)
	@Column(name = "pex_natcodigo")
	private String naturezaOperacaoCodigo;

	@Basic(optional = false)
	@Column(name = "pex_fpgcodigo")
	private String formaPagamentoCodigo;

	@Basic(optional = false)
	@Column(name = "pex_cpgcodigo")
	private int condicaoPagamentoCodigo;

	@Column(name = "pex_carga")
	private Integer carga;

	@Column(name = "pex_dscperc")
	private double descontoPercentual;

	@Column(name = "pex_dscvalor")
	private double descontoValor;

	@Column(name = "pex_acrperc")
	private double acrescimoPercentual;

	@Column(name = "pex_acrvalor")
	private double acrescimoValor;

	@Column(name = "pex_tprcodigo")
	private int tabelaPrecoCodigo;

	@Column(name = "pex_debcred")
	private int flagDebitoCredito;

	@Column(name = "pex_frete")
	private Double frete;

	@Column(name = "pex_valor")
	private double valor;

	@Column(name = "pex_qtdimp")
	private Integer quantidadeImpressao;

	@Column(name = "pex_origem")
	private String origem;

	@Column(name = "pex_codorigem")
	private String codigoOrigem;

	@Column(name = "pex_endentrega")
	private Integer enderecoEntregaCodigo;

	@Column(name = "pex_endcobr")
	private Integer enderecoCobrancaCodigo;

	@Column(name = "pex_orccodigo")
	private Integer orcamentoCodigo;

	@Column(name = "pex_lucrativida")
	private Double lucratividade;

	@Basic(optional = false)
	@Column(name = "pex_orvcodigo")
	private int origemVendaCodigo;

	@Basic(optional = false)
	@Column(name = "pex_datacad")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(name = "pex_ativo")
	private int flagAtivo;

	@Basic(optional = false)
	@Column(name = "pex_prbcodigo")
	private int precoBaseCodigo;

	@Basic(optional = false)
	@Column(name = "pex_cnmcodigo")
	private int controleNumeroCodigo;

	@Basic(optional = false)
	@Column(name = "pex_horacad")
	private String horaCadastro;

	@Basic(optional = false)
	@Column(name = "pex_stpcodigo")
	private int statusPedidoCodigo;

	@Column(name = "pex_obs")
	private String observacao;

	@Column(name = "pex_pesobruto")
	private Double pesoBruto;

	@Column(name = "pex_dtentrega")
	@Temporal(TemporalType.DATE)
	private Date dataEntrega;

	@Column(name = "pex_obs1")
	private String observacao1;

	@Column(name = "pex_obs2")
	private String observacao2;

	@Column(name = "pex_valcomis")
	private Double valorComissao;

	@Column(name = "pex_lpicodigo")
	private Integer lotePedidoImportadoCodigo;

	@Column(name = "pex_vlsugdbcr")
	private double valorSugeridoDebitoCredito;

	@Column(name = "pex_vlvendbcr")
	private double valorVendaDebitoCredito;

	@Basic(optional = false)
	@Column(name = "pex_valcomis2")
	private double valorComissao2;

	@Basic(optional = false)
	@Column(name = "pex_valcomis3")
	private double valorComissao3;

	@Basic(optional = false)
	@Column(name = "pex_valcomis4")
	private double valorComissao4;

	@Basic(optional = false)
	@Column(name = "pex_valcomis5")
	private double valorComissao5;

	@Column(name = "pex_pescodigot")
	private Integer transportadoraCodigo;

	@Column(name = "pex_campousr1")
	private String campoUsuario1;

	@Column(name = "pex_campousr2")
	private String campoUsuario2;

	@Column(name = "pex_campousr3")
	private String campoUsuario3;

	@Column(name = "pex_campousr4")
	private String campoUsuario4;

	@Column(name = "pex_campousr5")
	private String campoUsuario5;

	@Column(name = "pex_qtdevol")
	private Double quantidadeVolumes;

	@Column(name = "pex_tiposepara")
	private String tipoSeparacao;

	@Column(name = "pex_oepcodigo")
	private int opcaoEspecialCodigo;

	@Column(name = "pex_mtcubico")
	private Double metrosCubicos;

	@Column(name = "pex_pesoliquido")
	private Double pesoLiquido;

	@Transient
	private int turnoEntregaCodigo;

	@Transient
	private int colaboradorCodigo;

	@Transient
	private int prazoEspecial;

	@Transient
	private int prazoPromocao;

	@Transient
	private int grupoVendaCodigo;

	@Transient
	private GrupoVenda grupoVenda;

	@Transient
	private Integer promocaoNumero;

	@Transient
	private int loteLancamentoFinanceiro;

	@Transient
	private Integer loteNotaPedidoCodigo;

	@Transient
	private Integer contaCorrenteCodigo;

	@Transient
	private Integer numeroSeparacoes;

	@Transient
	private int numeroEntregas;

	@Transient
	private Integer ordemEntrega;

	@Transient
	private Integer tipoVendaCodigo;

	@Transient
	private Integer ordemCliente;

	@Transient
	private Double valorSubstituicao;

	@Transient
	private Double valorSubstituicaoAvulso;

	@Transient
	private double valorFundoEstadualCombatePobreza = 0;

	@Transient
	private int flagParticipaPromocao;

	@Transient
	private int flagAtualizaEstoque;

	@Transient
	private String flagTipoNaturezaOperacao;

	@Transient
	private int flagDevolucao;

	@Transient
	private int flagConsisteLimite;

	@Transient
	private Integer numeroPedidoPromocao;

	@Transient
	private String seriePedidoPromocao;

	@Transient
	private Double valorIPI;

	@Transient
	private Integer loteComissaoNumero;

	@Transient
	private int statusPagamentoCartao;

	public PedidoAuxiliar() {
	}

	public PedidoAuxiliar(PedidoAuxiliarPK pedidosAuxiliarPK) {
		this.pedidoAuxiliarPK = pedidosAuxiliarPK;
	}

	public PedidoAuxiliar(int pexFilCodigo, String pexSpvCodigo, int pexNumero) {
		this.pedidoAuxiliarPK = new PedidoAuxiliarPK(pexFilCodigo, pexSpvCodigo, pexNumero);
	}

	public PedidoAuxiliarPK getPedidoAuxiliarPK() {
		if (this.pedidoAuxiliarPK == null)
			this.pedidoAuxiliarPK = new PedidoAuxiliarPK();
		return pedidoAuxiliarPK;
	}

	public void setPedidoAuxiliarPK(PedidoAuxiliarPK pedidoAuxiliarPK) {
		this.pedidoAuxiliarPK = pedidoAuxiliarPK;
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

	public String getNaturezaOperacaoCodigo() {
		return naturezaOperacaoCodigo;
	}

	public void setNaturezaOperacaoCodigo(String naturezaOperacaoCodigo) {
		this.naturezaOperacaoCodigo = naturezaOperacaoCodigo;
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
		return getValor() - getValorIcms() - getValorPis() - getValorCofins();
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

	public Double getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Double pesoBruto) {
		this.pesoBruto = pesoBruto;
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

	public Double getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(Double pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public int getNumero() {
		return this.getPedidoAuxiliarPK().getNumero();
	}

	public void setNumero(int numero) {
		this.getPedidoAuxiliarPK().setNumero(numero);
	}

	public int getFilialCodigo() {
		return this.getPedidoAuxiliarPK().getFilialCodigo();
	}

	public String getSerieCodigo() {
		return this.getPedidoAuxiliarPK().getSeriePedidoCodigo();
	}

	public void setSerieCodigo(String serieCodigo) {
		this.getPedidoAuxiliarPK().setSeriePedidoCodigo(serieCodigo);
	}

	public long getDataEmissaoLong() {
		return 0;
	}

	public void setDataEmissaoLong(long dataEmissaoLong) {
	}

	public long getDataEntregaLong() {
		return 0;
	}

	public void setDataEntregaLong(long dataEntregaLong) {
	}

	public int getTurnoEntregaCodigo() {
		return turnoEntregaCodigo;
	}

	public void setTurnoEntregaCodigo(int turnoEntregaCodigo) {
		this.turnoEntregaCodigo = turnoEntregaCodigo;
	}

	public int getColaboradorCodigo() {
		return this.colaboradorCodigo;
	}

	public void setColaboradorCodigo(int colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public int getPrazoEspecial() {
		return this.prazoEspecial;
	}

	public void setPrazoEspecial(int prazoEspecial) {
		this.prazoEspecial = prazoEspecial;
	}

	public int getPrazoPromocao() {
		return this.prazoPromocao;
	}

	public void setPrazoPromocao(int prazoPromocao) {
		this.prazoPromocao = prazoPromocao;
	}

	public int getGrupoVendaCodigo() {
		return this.grupoVendaCodigo;
	}

	public void setGrupoVendaCodigo(int grupoVendaCodigo) {
		this.grupoVendaCodigo = grupoVendaCodigo;
	}

	@Override
	public IGrupoVenda getGrupoVenda() {
		return grupoVenda;
	}

	@Override
	public void setGrupoVenda(IGrupoVenda grupoVenda) {
		this.grupoVenda = (GrupoVenda) grupoVenda;
	}

	public Integer getPromocaoNumero() {
		return this.promocaoNumero;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		// TODO Auto-generated method stub

	}

	public int getLoteLancamentoFinanceiro() {
		return this.loteLancamentoFinanceiro;
	}

	public void setLoteLancamentoFinanceiro(int loteLancamentoFinanceiro) {
		this.loteLancamentoFinanceiro = loteLancamentoFinanceiro;
	}

	public Integer getLoteNotaPedidoCodigo() {
		return this.loteNotaPedidoCodigo;
	}

	public void setLoteNotaPedidoCodigo(Integer loteNotaPedidoCodigo) {
		this.loteNotaPedidoCodigo = loteNotaPedidoCodigo;
	}

	public Integer getContaCorrenteCodigo() {
		return this.contaCorrenteCodigo;
	}

	public void setContaCorrenteCodigo(Integer contaCorrenteCodigo) {
		this.contaCorrenteCodigo = contaCorrenteCodigo;
	}

	public Integer getNumeroSeparacoes() {
		return numeroSeparacoes;
	}

	public void setNumeroSeparacoes(Integer numeroSequencial) {
		this.numeroSeparacoes = numeroSequencial;
	}

	public int getNumeroEntregas() {
		return numeroEntregas;
	}

	public void setNumeroEntregas(int numeroEntregas) {
		this.numeroEntregas = numeroEntregas;
	}

	public Integer getOrdemEntrega() {
		return this.ordemEntrega;
	}

	public void setOrdemEntrega(Integer ordemEntrega) {
		this.ordemEntrega = ordemEntrega;
	}

	public Integer getTipoVendaCodigo() {
		return this.tipoVendaCodigo;
	}

	public void setTipoVendaCodigo(Integer tipoVendaCodigo) {
		this.tipoVendaCodigo = tipoVendaCodigo;
	}

	public Integer getOrdemCliente() {
		return this.ordemCliente;
	}

	public void setOrdemCliente(Integer ordemCliente) {
		this.ordemCliente = ordemCliente;
	}

	public Double getValorSubstituicao() {
		return valorSubstituicao;
	}

	public void setValorSubstituicao(Double valorSubstituicao) {
		this.valorSubstituicao = valorSubstituicao;
	}

	public Double getValorSubstituicaoAvulso() {
		return valorSubstituicaoAvulso;
	}

	public void setValorSubstituicaoAvulso(Double valorSubstituicaoAvulso) {
		this.valorSubstituicaoAvulso = valorSubstituicaoAvulso;
	}

	public double getValorFundoEstadualCombatePobreza() {
		return valorFundoEstadualCombatePobreza;
	}

	public void setValorFundoEstadualCombatePobreza(double valorFundoEstadualCombatePobreza) {
		this.valorFundoEstadualCombatePobreza = valorFundoEstadualCombatePobreza;
	}

	public void setPromocaoNumero(Integer promocaoNumero) {
		this.promocaoNumero = promocaoNumero;
	}

	public int getFlagParticipaPromocao() {
		return this.flagParticipaPromocao;
	}

	public void setFlagParticipaPromocao(int flagParticipaPromocao) {
		this.flagParticipaPromocao = flagParticipaPromocao;
	}

	@Override
	public boolean isAtualizaEstoque() {
		return getFlagAtualizaEstoque() == 1;
	}

	public int getFlagAtualizaEstoque() {
		return this.flagAtualizaEstoque;
	}

	public void setFlagAtualizaEstoque(int flagAtualizaEstoque) {
		this.flagAtualizaEstoque = flagAtualizaEstoque;
	}

	public String getFlagTipoNaturezaOperacao() {
		return this.flagTipoNaturezaOperacao;
	}

	public void setFlagTipoNaturezaOperacao(String flagTipoNaturezaOperacao) {
		this.flagTipoNaturezaOperacao = flagTipoNaturezaOperacao;
	}

	public int getFlagDevolucao() {
		return this.flagDevolucao;
	}

	public void setFlagDevolucao(int flagDevolucao) {
		this.flagDevolucao = flagDevolucao;
	}

	public int getFlagConsisteLimite() {
		return this.flagConsisteLimite;
	}

	public void setFlagConsisteLimite(int flagConsisteLimite) {
		this.flagConsisteLimite = flagConsisteLimite;
	}

	public Integer getNumeroPedidoPromocao() {
		return this.numeroPedidoPromocao;
	}

	public void setNumeroPedidoPromocao(Integer numeroPedidoPromocao) {
		this.numeroPedidoPromocao = numeroPedidoPromocao;
	}

	public String getSeriePedidoPromocao() {
		return this.seriePedidoPromocao;
	}

	public void setSeriePedidoPromocao(String seriePedidoPromocao) {
		this.seriePedidoPromocao = seriePedidoPromocao;
	}

	public Double getValorIpi() {
		return this.valorIPI;
	}

	public void setValorIpi(Double valorIpi) {
		this.valorIPI = valorIpi;
	}

	public Integer getLoteComissaoNumero() {
		return this.loteComissaoNumero;
	}

	public void setLoteComissaoNumero(Integer loteComissaoNumero) {
		this.loteComissaoNumero = loteComissaoNumero;
	}

	@Override
	public String getSerieENumeroPedido(String separador) {
		return getSerieCodigo() + separador + getNumero();
	}

	public void setFilialCodigo(int filialCodigo) {
		this.getPedidoAuxiliarPK().setFilialCodigo(filialCodigo);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public String getChaveTrava() {
		return String.valueOf(this.getFilialCodigo()) + Conversao.formatarEspacoDireita(this.getSerieCodigo(), 3)
				+ String.valueOf(this.getNumero());
	}

	@Override
	public String getNomeTabelaTrava() {
		return PedidoAuxiliar.NOME_TABELA;
	}

	@Override
	public int getStatusPagamentoCartao() {
		return statusPagamentoCartao;
	}

	@Override
	public void setStatusPagamentoCartao(int statusPagamentoCartao) {
		this.statusPagamentoCartao = statusPagamentoCartao;
	}

	public static PedidoAuxiliar recuperarUnicoPedidoAuxiliar(GenericoDAO dao, int pedidoCodigo, String serie,
			int filialCodigo) {

		if (pedidoCodigo <= 0 || filialCodigo <= 0 || serie == null) {
			return null;
		}

		String where = COLUNA_NUMERO + " = " + pedidoCodigo + " AND " + COLUNA_SERIE_CODIGO + " = '" + serie + "' AND "
				+ COLUNA_FILIAL_CODIGO + " = " + filialCodigo;

		return dao.uniqueResult(PedidoAuxiliar.class, where, null);
	}

	@Override
	public double getValorPis() {
		return 0;
	}

	@Override
	public double getValorCofins() {
		return 0;
	}

	@Override
	public double getValorIcms() {
		return 0;
	}

	@Override
	public void setValorPis(double valorPis) {

	}

	@Override
	public void setValorCofins(double valorCofins) {

	}

	@Override
	public void setValorIcms(double valorIcms) {

	}

	@Override
	public String getClienteIdentificacao() {
		return null;
	}

}
