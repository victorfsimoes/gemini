package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IProduto;
import br.com.space.api.negocio.modelo.dominio.IProdutoUnidade;
import br.com.space.api.negocio.modelo.dominio.IPromocao;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoFilial;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "itenspedidoaux")
@XmlRootElement
public class ItemPedidoAuxiliar extends ItemPedidoComum implements IPersistent, Serializable {
	private static final long serialVersionUID = 1L;

	protected static final String COLUNA_SERIEPEDIDO = "ipx_spvcodigo";
	protected static final String COLUNA_NUMEROPEDIDO = "ipx_pednumero";
	protected static final String COLUNA_FILIALPEDIDO = "ipx_filcodigo";

	@EmbeddedId
	protected ItemPedidoAuxiliarPK itemPedidoAuxiliarPK;

	@Basic(optional = false)
	@Column(name = "ipx_ProCodigo")
	private int produtoCodigo;

	@Basic(optional = false)
	@Column(name = "ipx_Quantidade")
	private double quantidade;

	/*
	 * Quantidade usada na tela de itens do carrinho para não alterar a
	 * quantidade do item diretamente.
	 */
	@Transient
	private double quantidadeCarrinho;

	@Basic(optional = false)
	@Column(name = "ipx_PprPrecoVen")
	private double precoVendaOriginal;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "ipx_DescPerc")
	private double descontoPercentual;

	@Column(name = "ipx_DescValor")
	private double descontoValor;

	@Column(name = "ipx_cdfcodigo")
	private String codigoFiscalCodigo;

	@Basic(optional = false)
	@Column(name = "ipx_PrecoSug")
	private double precoSugerido;

	@Column(name = "ipx_Ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "ipx_UnpUnidade")
	private String unidade;

	@Column(name = "ipx_CbpCodigo")
	private String codigoBarrasProdutoCodigo;

	@Basic(optional = false)
	@Column(name = "ipx_UnpQuant")
	private int quantidadeUnidade;

	@Basic(optional = false)
	@Column(name = "ipx_PrecoVenda")
	private double precoVenda;

	@Column(name = "ipx_AcresPerc")
	private double acrescimoPercentual;

	@Column(name = "ipx_AcresValor")
	private double acrescimoValor;

	@Column(name = "ipx_StatusBaixa")
	private String statusBaixa;

	@Column(name = "ipx_PfiCusto1")
	private double custo1;

	@Column(name = "ipx_PfiCusto2")
	private double custo2;

	@Column(name = "ipx_PfiCusto3")
	private double custo3;

	@Column(name = "ipx_PfiCusto4")
	private double custo4;

	@Column(name = "ipx_PfiCusto5")
	private double custo5;

	@Column(name = "ipx_PfiCusto6")
	private double custo6;

	@Column(name = "ipx_PfiCusto7")
	private double custo7;

	@Column(name = "ipx_PfiCusto8")
	private double custo8;

	@Column(name = "ipx_PfiCusto9")
	private double custo9;

	@Column(name = "ipx_PfiCusto10")
	private double custo10;

	@Column(name = "ipx_UnpFatVen")
	private double fatorVenda;

	@Column(name = "ipx_UnpFatEst")
	private double fatorEstoque;

	@Column(name = "ipx_OftNumero")
	private int ofertaNumero;

	@Column(name = "ipx_ProPbruto")
	private Double pesoBrutoProduto;

	@Column(name = "ipx_PedDscValor")
	private double descontoPedidoValor;

	@Column(name = "ipx_PedAcrValor")
	private double acrescimoPedidoValor;

	@Column(name = "iPx_PrecoLiq")
	private double precoLiquido;

	@Column(name = "ipx_ProPLiquido")
	private Double pesoLiquidoProduto;

	@Column(name = "ipx_GcoCodigo")
	private Integer grupoComissaoCodigo;

	@Column(name = "ipx_PerComis1")
	private double percentualComissao1;

	@Column(name = "ipx_PerComis2")
	private double percentualComissao2;

	@Column(name = "ipx_PerComis3")
	private double percentualComissao3;

	@Column(name = "ipx_PerComis4")
	private double percentualComissao4;

	@Column(name = "ipx_PerComis5")
	private double percentualComissao5;

	@Column(name = "ipx_DebCredIt")
	private int flagDebitoCredito;

	@Column(name = "ipx_ItMtCubico")
	private Double metroCubico;

	@Column(name = "ipx_QtdVol")
	private Double quantidadeVolumes;

	@Column(name = "ipx_ngpnumero")
	private Integer negociacaoProdutoNumero;

	@Column(name = "ipx_ipi")
	private Double percentualIpi;

	@Column(name = "ipx_valoripi")
	private Double valorIpi;

	@Column(name = "ipx_csicodigo")
	private String cstIpiCodigo;

	@Column(name = "ipx_lote")
	private String lote;

	@Column(name = "ipx_lcecodigo")
	private Integer localEstoqueCodigo;

	@Transient
	private int kitCodigo;

	@Transient
	private double custo;

	@Transient
	private Produto produto;

	@Transient
	private ProdutoUnidade produtoUnidade;

	@Transient
	private ProdutoFilial produtoFilial;

	@Transient
	private Promocao promocao;

	@Transient
	private Integer promocaoNumero;

	@Transient
	private Double valorSubstituicao;

	@Transient
	private Double valorSubstituicaoAvulso;

	@Transient
	private double valorFundoEstadualCombatePobreza = 0;

	@Transient
	private String certificadoClassificacao;

	@Transient
	private String descricaoNF1;

	@Transient
	private String descricaoNF2;

	@Transient
	private String descricaoNF3;

	@Transient
	private String descricaoNF4;

	@Transient
	private String descricaoNF5;

	@Transient
	private Integer parcelaOrdemServico;

	@Transient
	private double quantidadeAnterior;

	@Transient
	private String operacao;

	public ItemPedidoAuxiliar() {

	}

	public ItemPedidoAuxiliar(ItemPedidoAuxiliarPK itenspedidoauxPK) {
		this.itemPedidoAuxiliarPK = itenspedidoauxPK;
	}

	public ItemPedidoAuxiliar(String ipxSpvCodigo, int ipxPedNumero, int ipxNumItem, int ipxFilCodigo) {
		this.itemPedidoAuxiliarPK = new ItemPedidoAuxiliarPK(ipxSpvCodigo, ipxPedNumero, ipxNumItem, ipxFilCodigo);
	}

	public ItemPedidoAuxiliar(Produto produto, ProdutoUnidade produtoUnidade) {

		setProduto(produto);
		setProdutoCodigo(produto.getCodigo());
		setPesoBrutoProduto(produto.getPesoBruto());
		setPesoLiquidoProduto(produto.getPesoLiquido());

		setProdutoUnidade(produtoUnidade);
		setUnidade(produtoUnidade.getUnidade());
		setQuantidadeUnidade(produtoUnidade.getQuantidade());
		setFatorEstoque(produtoUnidade.getFatorEstoque());
		setFatorVenda(produtoUnidade.getFatorVenda());
	}

	public ItemPedidoAuxiliar(Produto produto, ProdutoUnidade produtoUnidade, ProdutoFilial produtoFilial) {
		this(produto, produtoUnidade);

		setProdutoFilial(produtoFilial);
		produto.setProdutoFilial(produtoFilial);
		setFlagDebitoCredito(produtoFilial.getFlagDebitoCreditoProduto());
		setCusto1(produtoFilial.getCusto1());
		setCusto2(produtoFilial.getCusto2());
		setCusto3(produtoFilial.getCusto3());
		setCusto4(produtoFilial.getCusto4());
		setCusto5(produtoFilial.getCusto5());
		setCusto6(produtoFilial.getCusto6());
		setCusto7(produtoFilial.getCusto7());
		setCusto8(produtoFilial.getCusto8());
		setCusto9(produtoFilial.getCusto9());
		setCusto10(produtoFilial.getCusto10());

	}

	public ItemPedidoAuxiliarPK getItemPedidoAuxiliarPK() {
		if (this.itemPedidoAuxiliarPK == null)
			this.itemPedidoAuxiliarPK = new ItemPedidoAuxiliarPK();
		return itemPedidoAuxiliarPK;
	}

	public void setItemPedidoAuxiliarPK(ItemPedidoAuxiliarPK itemPedidoAuxiliarPK) {
		this.itemPedidoAuxiliarPK = itemPedidoAuxiliarPK;
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
		this.setQuantidadeCarrinho(quantidade);
	}

	public double getQuantidadeCarrinho() {
		if (this.quantidadeCarrinho == 0)
			this.quantidadeCarrinho = this.quantidade;
		return quantidadeCarrinho;
	}

	public void setQuantidadeCarrinho(double quantidade) {
		this.quantidadeCarrinho = quantidade;
	}

	public double getPrecoVendaOriginal() {
		return precoVendaOriginal;
	}

	public void setPrecoVendaOriginal(double precoVendaOriginal) {
		this.precoVendaOriginal = precoVendaOriginal;
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

	public double getPrecoSugerido() {
		return precoSugerido;
	}

	public void setPrecoSugerido(double precoSugerido) {
		this.precoSugerido = precoSugerido;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public String getCodigoBarrasProdutoCodigo() {
		return codigoBarrasProdutoCodigo;
	}

	public void setCodigoBarrasProdutoCodigo(String codigoBarrasCodigo) {
		this.codigoBarrasProdutoCodigo = codigoBarrasCodigo;
	}

	public int getQuantidadeUnidade() {
		return quantidadeUnidade;
	}

	public void setQuantidadeUnidade(int quantidadeUnidade) {
		this.quantidadeUnidade = quantidadeUnidade;

	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
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

	public String getStatusBaixa() {
		return statusBaixa;
	}

	public void setStatusBaixa(String statusBaixa) {
		this.statusBaixa = statusBaixa;
	}

	public double getCusto1() {
		return custo1;
	}

	public void setCusto1(double custo1) {
		this.custo1 = custo1;
	}

	public double getCusto2() {
		return custo2;
	}

	public void setCusto2(double custo2) {
		this.custo2 = custo2;
	}

	public double getCusto3() {
		return custo3;
	}

	public void setCusto3(double custo3) {
		this.custo3 = custo3;
	}

	public double getCusto4() {
		return custo4;
	}

	public void setCusto4(double custo4) {
		this.custo4 = custo4;
	}

	public double getCusto5() {
		return custo5;
	}

	public void setCusto5(double custo5) {
		this.custo5 = custo5;
	}

	public double getCusto6() {
		return custo6;
	}

	public void setCusto6(double custo6) {
		this.custo6 = custo6;
	}

	public double getCusto7() {
		return custo7;
	}

	public void setCusto7(double custo7) {
		this.custo7 = custo7;
	}

	public double getCusto8() {
		return custo8;
	}

	public void setCusto8(double custo8) {
		this.custo8 = custo8;
	}

	public double getCusto9() {
		return custo9;
	}

	public void setCusto9(double custo9) {
		this.custo9 = custo9;
	}

	public double getCusto10() {
		return custo10;
	}

	public void setCusto10(double custo10) {
		this.custo10 = custo10;
	}

	public double getFatorVenda() {
		return fatorVenda;
	}

	public void setFatorVenda(double fatorVenda) {
		this.fatorVenda = fatorVenda;
	}

	public double getFatorEstoque() {
		return fatorEstoque;
	}

	public void setFatorEstoque(double fatorEstoque) {
		this.fatorEstoque = fatorEstoque;
	}

	public int getOfertaNumero() {
		return ofertaNumero;
	}

	public void setOfertaNumero(int ofertaNumero) {
		this.ofertaNumero = ofertaNumero;
	}

	public Double getPesoBrutoProduto() {
		return pesoBrutoProduto;
	}

	public void setPesoBrutoProduto(Double pesoBrutoProduto) {
		this.pesoBrutoProduto = pesoBrutoProduto;
	}

	public double getDescontoPedidoValor() {
		return descontoPedidoValor;
	}

	public void setDescontoPedidoValor(double descontoPedidoValor) {
		this.descontoPedidoValor = descontoPedidoValor;
	}

	public double getAcrescimoPedidoValor() {
		return acrescimoPedidoValor;
	}

	public void setAcrescimoPedidoValor(double acrescimoPedidoValor) {
		this.acrescimoPedidoValor = acrescimoPedidoValor;
	}

	public double getPrecoLiquido() {
		return precoLiquido;
	}

	public void setPrecoLiquido(double precoLiquido) {
		this.precoLiquido = precoLiquido;
	}

	public Double getPesoLiquidoProduto() {
		return pesoLiquidoProduto;
	}

	public void setPesoLiquidoProduto(Double pesoLiquidoProduto) {
		this.pesoLiquidoProduto = pesoLiquidoProduto;
	}

	public Integer getGrupoComissaoCodigo() {
		return grupoComissaoCodigo;
	}

	public void setGrupoComissaoCodigo(Integer grupoComissaoCodigo) {
		this.grupoComissaoCodigo = grupoComissaoCodigo;
	}

	public double getPercentualComissao1() {
		return percentualComissao1;
	}

	public void setPercentualComissao1(double percentualComissao1) {
		this.percentualComissao1 = percentualComissao1;
	}

	public double getPercentualComissao2() {
		return percentualComissao2;
	}

	public void setPercentualComissao2(double percentualComissao2) {
		this.percentualComissao2 = percentualComissao2;
	}

	public double getPercentualComissao3() {
		return percentualComissao3;
	}

	public void setPercentualComissao3(double percentualComissao3) {
		this.percentualComissao3 = percentualComissao3;
	}

	public double getPercentualComissao4() {
		return percentualComissao4;
	}

	public void setPercentualComissao4(double percentualComissao4) {
		this.percentualComissao4 = percentualComissao4;
	}

	public double getPercentualComissao5() {
		return percentualComissao5;
	}

	public void setPercentualComissao5(double percentualComissao5) {
		this.percentualComissao5 = percentualComissao5;
	}

	public int getFlagDebitoCredito() {
		return flagDebitoCredito;
	}

	public void setFlagDebitoCredito(int flagDebitoCredito) {
		this.flagDebitoCredito = flagDebitoCredito;
	}

	public Double getMetroCubico() {
		return metroCubico;
	}

	public void setMetroCubico(Double metroCubico) {
		this.metroCubico = metroCubico;
	}

	public Double getQuantidadeVolumes() {
		return quantidadeVolumes;
	}

	public void setQuantidadeVolumes(Double quantidadeVolumes) {
		this.quantidadeVolumes = quantidadeVolumes;
	}

	public Integer getNegociacaoProdutoNumero() {
		return negociacaoProdutoNumero;
	}

	public void setNegociacaoProdutoNumero(Integer negociacaoNumero) {
		this.negociacaoProdutoNumero = negociacaoNumero;
	}

	public Double getPercentualIpi() {
		return percentualIpi;
	}

	public void setPercentualIpi(Double ipi) {
		this.percentualIpi = ipi;
	}

	public Double getValorIpi() {
		return valorIpi;
	}

	public void setValorIpi(Double valorIpi) {
		this.valorIpi = valorIpi;
	}

	public String getCstIpiCodigo() {
		return cstIpiCodigo;
	}

	public void setCstIpiCodigo(String cstIpiCodigo) {
		this.cstIpiCodigo = cstIpiCodigo;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Integer getLocalEstoqueCodigo() {
		return localEstoqueCodigo;
	}

	public void setLocalEstoqueCodigo(Integer localEstoqueCodigo) {
		this.localEstoqueCodigo = localEstoqueCodigo;
	}

	public int getNumeroPedido() {
		return this.getItemPedidoAuxiliarPK().getNumero();
	}

	public void setNumeroPedido(int numerpPedido) {
		this.getItemPedidoAuxiliarPK().setNumero(numerpPedido);

	}

	public String getSeriePedidoCodigo() {
		return this.getItemPedidoAuxiliarPK().getSerieCodigo();
	}

	public void setSeriePedidoCodigo(String serieCodigo) {
		this.getItemPedidoAuxiliarPK().setSerieCodigo(serieCodigo);
	}

	public int getNumeroItem() {
		return this.getItemPedidoAuxiliarPK().getNumeroItem();
	}

	public void setNumeroItem(int numeroItem) {
		this.getItemPedidoAuxiliarPK().setNumeroItem(numeroItem);
	}

	public int getKitCodigo() {
		return this.kitCodigo;
	}

	public double getCusto() {
		return this.custo;
	}

	public void setCusto(double custo) {
		this.custo = custo;
	}

	public IProduto getProduto() {
		return this.produto;
	}

	public void setProduto(IProduto produto) {
		this.produto = (Produto) produto;
	}

	public IProdutoUnidade getProdutoUnidade() {
		return this.produtoUnidade;
	}

	public void setProdutoUnidade(IProdutoUnidade produtoUnidade) {
		this.produtoUnidade = (ProdutoUnidade) produtoUnidade;
	}

	public Integer getPromocaoNumero() {
		return promocaoNumero;
	}

	@Override
	public boolean isPermiteAlterarQuantidadeNoList() {
		return (getPromocaoNumero() == null || getPromocaoNumero() == 0) && getKitCodigo() == 0;
	}

	public IPromocao getPromocao() {
		return this.promocao;
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

	public String getCodigoFiscalCodigo() {
		return this.codigoFiscalCodigo;
	}

	public void setCodigoFiscalCodigo(String codigoFiscalCodigo) {
		this.codigoFiscalCodigo = codigoFiscalCodigo;
	}

	public double getPrecoTotal() {
		return precoVenda * quantidade;
	}

	public void setKitCodigo(Integer kitCodigo) {
		this.kitCodigo = kitCodigo;
	}

	public Double getValorSubstituicao() {
		return this.valorSubstituicao;
	}

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

	public String getCertificadoClassificacao() {
		return this.certificadoClassificacao;
	}

	public void setCertificadoClassificacao(String certificadoClassificacao) {
		this.certificadoClassificacao = certificadoClassificacao;
	}

	public String getDescricaoNF1() {
		return this.descricaoNF1;
	}

	public void setDescricaoNF1(String descricaoNF1) {
		this.descricaoNF1 = descricaoNF1;
	}

	public String getDescricaoNF2() {
		return this.descricaoNF2;
	}

	public void setDescricaoNF2(String descricaoNF2) {
		this.descricaoNF2 = descricaoNF2;
	}

	public String getDescricaoNF3() {
		return this.descricaoNF3;
	}

	public void setDescricaoNF3(String descricaoNF3) {
		this.descricaoNF3 = descricaoNF3;
	}

	public String getDescricaoNF4() {
		return this.descricaoNF4;
	}

	public void setDescricaoNF4(String descricaoNF4) {
		this.descricaoNF4 = descricaoNF4;
	}

	public String getDescricaoNF5() {
		return this.descricaoNF5;
	}

	public void setDescricaoNF5(String descricaoNF5) {
		this.descricaoNF5 = descricaoNF5;
	}

	public Integer getParcelaOrdemServico() {
		return this.parcelaOrdemServico;
	}

	public void setParcelaOrdemServico(Integer parcelaOrdemServico) {
		this.parcelaOrdemServico = parcelaOrdemServico;
	}

	public void setPromocaoNumero(Integer promocaoNumero) {
		this.promocaoNumero = promocaoNumero;
	}

	public int getPedidoNumero() {
		return this.getItemPedidoAuxiliarPK().getNumero();
	}

	public void setPedidoNumero(int pedidoNumero) {
		this.getItemPedidoAuxiliarPK().setNumero(pedidoNumero);
	}

	public int getFilialCodigo() {
		return this.getItemPedidoAuxiliarPK().getFilialCodigo();
	}

	public void setFilialCodigo(int filialCodigo) {
		this.getItemPedidoAuxiliarPK().setFilialCodigo(filialCodigo);
	}

	public double getQuantidadeAnterior() {
		return quantidadeAnterior;
	}

	public void setQuantidadeAnterior(double quantidadeAnterior) {
		this.quantidadeAnterior = quantidadeAnterior;

	}

	public ProdutoFilial getProdutoFilial() {
		return produtoFilial;
	}

	public void setProdutoFilial(ProdutoFilial produtoFilial) {
		this.produtoFilial = produtoFilial;
	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacao) {
		this.operacao = operacao;
	}

	public static void apagarPedidoAuxiliar(GenericoDAO dao, int pedidoNumero, String serie, int filialCodigo) {
	}

	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	private static String getWhereChavePedido(int pedidoCodigo, String serie, int filialCodigo) {
		String where = COLUNA_SERIEPEDIDO + " = '" + serie + "' AND " + COLUNA_NUMEROPEDIDO + " = " + pedidoCodigo
				+ " AND " + COLUNA_FILIALPEDIDO + " = " + filialCodigo;
		return where;
	}

	public static List<ItemPedidoAuxiliar> recuperarItensPedido(GenericoDAO dao, int pedidoCodigo, String serie,
			int filialCodigo) {

		if (pedidoCodigo <= 0 || filialCodigo <= 0 || serie == null) {
			return null;
		}

		return dao.list(ItemPedidoAuxiliar.class, getWhereChavePedido(pedidoCodigo, serie, filialCodigo), null, null,
				null);
	}

	@Override
	public void reiniciarValoresImpostos() {
		setValorIpi(0.0);
		setValorSubstituicao(0.0);
		setValorSubstituicaoAvulso(0.0);
	}

	@Override
	public String getProdutoDescricao() {

		if (getProduto() instanceof CaixaProdutoVisualizavel) {
			return ((CaixaProdutoVisualizavel) getProduto()).getDescricaoVisualizacao();
		} else if (getProduto() != null) {
			return getProduto().getDescricao();
		}

		return null;
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
	public int getGrupoProdutoCodigo() {
		if (getProduto() != null)
			return getProduto().getGrupoCodigo();

		return 0;
	}

	@Override
	public int getSubGrupoProdutoCodigo() {
		if (getProduto() != null)
			return ((Produto) getProduto()).getSubGrupoCodigo();

		return 0;
	}

	@Override
	public int getCategoriaProdutoCodigo() {
		if (getProduto() != null)
			return ((Produto) getProduto()).getCategoriaCodigo();

		return 0;
	}

	@Override
	public int getSubCategoriaProdutoCodigo() {
		if (getProduto() != null)
			return ((Produto) getProduto()).getSubCategoriaCodigo();

		return 0;
	}

	@Override
	public double getValorFrete() {
		return 0;
	}

	@Override
	public void setValorFrete(double valorFrete) {

	}

	@Override
	public void setPromocao(IPromocao promocao) {
		this.promocao = (Promocao) promocao;

	}
}