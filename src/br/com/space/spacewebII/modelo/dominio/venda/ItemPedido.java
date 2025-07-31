/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.sql.SQLException;
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
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoFilial;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = ItemPedido.NOME_TABELA)
@SpaceTable(name = ItemPedido.NOME_TABELA)
@XmlRootElement
public class ItemPedido extends ItemPedidoComum implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELA = "itenspedido";

	protected static final String COLUNA_SERIEPEDIDO = "ipv_spvcodigo";
	protected static final String COLUNA_NUMEROPEDIDO = "ipv_pednumero";
	protected static final String COLUNA_FILIALPEDIDO = "ipv_filcodigo";

	@EmbeddedId
	protected ItemPedidoPK itemPedidoPK;

	@Column(name = "ipv_cdfcodigo")
	private String codigoFiscalCodigo;

	@Basic(optional = false)
	@Column(name = "ipv_quantidade")
	@SpaceColumn(name = "ipv_quantidade")
	private double quantidade;

	/*
	 * Quantidade usada na tela de itens do carrinho para não alterar a
	 * quantidade do item diretamente.
	 */
	@Transient
	private double quantidadeCarrinho;

	@Basic(optional = false)
	@Column(name = "ipv_pprprecoven")
	private double precoVendaOriginal;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "ipv_descperc")
	private double descontoPercentual;

	@Column(name = "ipv_descvalor")
	private double descontoValor;

	@Basic(optional = false)
	@Column(name = "ipv_precosug")
	private double precoSugerido;

	@Column(name = "ipv_ativo")
	private Integer flagAtivo;

	@Column(name = "ipv_cbpcodigo")
	private String codigoBarrasProdutoCodigo;

	@Basic(optional = false)
	@SpaceColumn(name = "ipv_precovenda")
	@Column(name = "ipv_precovenda")
	private double precoVenda;

	@Column(name = "ipv_acresperc")
	private double acrescimoPercentual;

	@Column(name = "ipv_acresvalor")
	private double acrescimoValor;

	@Column(name = "ipv_statusbaixa")
	private String statusBaixa;

	@Column(name = "ipv_pficusto1")
	private double custo1;

	@Column(name = "ipv_pficusto2")
	private double custo2;

	@Column(name = "ipv_pficusto3")
	private double custo3;

	@Column(name = "ipv_pficusto4")
	private double custo4;

	@Column(name = "ipv_pficusto5")
	private double custo5;

	@Column(name = "ipv_pficusto6")
	private double custo6;

	@Column(name = "ipv_pficusto7")
	private double custo7;

	@Column(name = "ipv_pficusto8")
	private double custo8;

	@Column(name = "ipv_pficusto9")
	private double custo9;

	@Column(name = "ipv_pficusto10")
	private double custo10;

	@Column(name = "ipv_unpfatven")
	private double fatorVenda;

	@Column(name = "ipv_unpfatest")
	private double fatorEstoque;

	@Column(name = "ipv_oftnumero")
	private int ofertaNumero;

	@Column(name = "ipv_propbruto")
	private Double pesoBrutoProduto = 0D;

	@Column(name = "ipv_peddscvalor")
	private double descontoPedidoValor;

	@Column(name = "ipv_pedacrvalor")
	private double acrescimoPedidoValor;

	@Column(name = "ipv_precoliq")
	private Double precoLiquido = 0.0;

	@Column(name = "ipv_propliquido")
	private Double pesoLiquidoProduto = 0.0;

	@Column(name = "ipv_gcocodigo")
	private Integer grupoComissaoCodigo = 0;

	@Column(name = "ipv_percomis1")
	private double percentualComissao1;

	@Column(name = "ipv_percomis2")
	private double percentualComissao2;

	@Column(name = "ipv_percomis3")
	private double percentualComissao3;

	@Column(name = "ipv_percomis4")
	private double percentualComissao4;

	@Column(name = "ipv_percomis5")
	private double percentualComissao5;

	@Column(name = "ipv_debcredit")
	private int flagDebitoCredito;

	@Column(name = "ipv_itmtcubico")
	private Double metroCubico;

	@Column(name = "ipv_qtdvol")
	private Double quantidadeVolumes = 0.0;

	@Column(name = "ipv_kitcodigo")
	private Integer kitCodigo = 0;

	@Column(name = "ipv_valsubst")
	private Double valorSubstituicao = 0.0;

	@Column(name = "ipv_valsubstav")
	private Double valorSubstituicaoAvulso = 0.0;

	@Column(name = "ipv_valfecp")
	private double valorFundoEstadualCombatePobreza = 0;

	@Column(name = "ipv_lcecodigo")
	private Integer localEstoqueCodigo = 0;

	@Column(name = "ipv_lote")
	private String lote;

	@Column(name = "ipv_certclass")
	private String certificadoClassificacao;

	@Column(name = "ipv_ngpnumero")
	private Integer negociacaoProdutoNumero;

	@Column(name = "ipv_descnf1")
	private String descricaoNF1;

	@Column(name = "ipv_descnf2")
	private String descricaoNF2;

	@Column(name = "ipv_descnf3")
	private String descricaoNF3;

	@Column(name = "ipv_descnf4")
	private String descricaoNF4;

	@Column(name = "ipv_descnf5")
	private String descricaoNF5;

	@Column(name = "ipv_osiparcela")
	private Integer parcelaOrdemServico;

	@Column(name = "ipv_prmnumero")
	private Integer promocaoNumero = 0;

	@Column(name = "ipv_ipi")
	private Double percentualIpi = 0.0;

	@Column(name = "ipv_valoripi")
	private Double valorIpi = 0.0;

	@Column(name = "ipv_csicodigo")
	private String cstIpiCodigo;

	@Column(name = "ipv_procodigo")
	private int produtoCodigo;

	@Column(name = "ipv_unpunidade")
	@SpaceColumn(name = "ipv_unpunidade")
	private String unidade;

	@Column(name = "ipv_unpquant")
	@SpaceColumn(name = "ipv_unpquant")
	private int quantidadeUnidade;

	@Column(name = "ipv_valoricms")
	private double valorIcms;

	@Column(name = "ipv_valorpis")
	private double valorPis;

	@Column(name = "ipv_valorcofins")
	private double valorCofins;

	@Column(name = "ipv_valorfrete")
	private double valorFrete;

	@Transient
	private Produto produto;

	@Transient
	private ProdutoUnidade produtoUnidade;

	@Transient
	private ProdutoFilial produtoFilial;

	@Transient
	private Promocao promocao;

	@Transient
	@SpaceColumn(name = COLUNA_SERIEPEDIDO)
	private String seriePedidoCodigo;

	@Transient
	@SpaceColumn(name = COLUNA_NUMEROPEDIDO)
	private int pedidoNumero;

	@Transient
	@SpaceColumn(name = "ipv_numitem")
	private int numeroItem;

	@Transient
	@SpaceColumn(name = COLUNA_FILIALPEDIDO)
	private int filialCodigo;

	@Transient
	private double custo;

	@Transient
	private String operacao;
	
	@Transient
	private double precoRevenda;

	/*
	 * Usada para alteração de pedido quando tiver que estornar a quantidade
	 * anterior.
	 */
	@Transient
	private double quantidadeAnterior;

	/**
	 * 
	 */
	public ItemPedido() {
	}

	public ItemPedido(Produto produto, ProdutoUnidade produtoUnidade) {

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

	public ItemPedido(Produto produto, ProdutoUnidade produtoUnidade, ProdutoFilial produtoFilial) {
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

	/**
	 * 
	 * @return
	 */
	public ItemPedidoPK getItemPedidoPK() {
		if (this.itemPedidoPK == null) {
			this.itemPedidoPK = new ItemPedidoPK();
		}

		return itemPedidoPK;
	}

	public String getCodigoFiscalCodigo() {
		return codigoFiscalCodigo;
	}

	public void setCodigoFiscalCodigo(String codigoFiscalCodigo) {
		this.codigoFiscalCodigo = codigoFiscalCodigo;
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

	public String getCodigoBarrasProdutoCodigo() {
		return codigoBarrasProdutoCodigo;
	}

	public void setCodigoBarrasProdutoCodigo(String codigoBarrasProdutoCodigo) {
		this.codigoBarrasProdutoCodigo = codigoBarrasProdutoCodigo;
	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	/**
	 * Calcula e retorna o preço total do Item; Preço de Venda multiplicado pela
	 * quantidade
	 * 
	 * @return
	 */
	public double getPrecoTotal() {
		return precoVenda * quantidade;
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

	@Override
	public int getKitCodigo() {
		if (kitCodigo == null) {
			return 0;
		}
		return kitCodigo;
	}

	public void setKitCodigo(Integer kitCodigo) {
		this.kitCodigo = kitCodigo;
	}

	public Double getValorSubstituicao() {
		return valorSubstituicao;
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

	public Integer getLocalEstoqueCodigo() {
		return localEstoqueCodigo;
	}

	public void setLocalEstoqueCodigo(Integer localEstoqueCodigo) {
		this.localEstoqueCodigo = localEstoqueCodigo;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String getCertificadoClassificacao() {
		return certificadoClassificacao;
	}

	public void setCertificadoClassificacao(String certificadoClassificacao) {
		this.certificadoClassificacao = certificadoClassificacao;
	}

	public Integer getNegociacaoProdutoNumero() {
		return negociacaoProdutoNumero;
	}

	public void setNegociacaoProdutoNumero(Integer negociacaoProdutoNumero) {
		this.negociacaoProdutoNumero = negociacaoProdutoNumero;
	}

	public String getDescricaoNF1() {
		return descricaoNF1;
	}

	public void setDescricaoNF1(String descricaoNF1) {
		this.descricaoNF1 = descricaoNF1;
	}

	public String getDescricaoNF2() {
		return descricaoNF2;
	}

	public void setDescricaoNF2(String descricaoNF2) {
		this.descricaoNF2 = descricaoNF2;
	}

	public String getDescricaoNF3() {
		return descricaoNF3;
	}

	public void setDescricaoNF3(String descricaoNF3) {
		this.descricaoNF3 = descricaoNF3;
	}

	public String getDescricaoNF4() {
		return descricaoNF4;
	}

	public void setDescricaoNF4(String descricaoNF4) {
		this.descricaoNF4 = descricaoNF4;
	}

	public String getDescricaoNF5() {
		return descricaoNF5;
	}

	public void setDescricaoNF5(String descricaoNF5) {
		this.descricaoNF5 = descricaoNF5;
	}

	public Integer getParcelaOrdemServico() {
		return parcelaOrdemServico;
	}

	public void setParcelaOrdemServico(Integer parcelaOrdemServico) {
		this.parcelaOrdemServico = parcelaOrdemServico;
	}

	public Integer getPromocaoNumero() {
		return promocaoNumero;
	}

	@Override
	public boolean isPermiteAlterarQuantidadeNoList() {
		return (getPromocaoNumero() == null || getPromocaoNumero() == 0) && getKitCodigo() == 0;
	}

	public void setPromocaoNumero(Integer promocaoNumero) {
		this.promocaoNumero = promocaoNumero;
	}

	public Double getPercentualIpi() {
		return percentualIpi;
	}

	public void setPercentualIpi(Double percentualIpi) {
		this.percentualIpi = percentualIpi;
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

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public int getQuantidadeUnidade() {
		return quantidadeUnidade;
	}

	public void setQuantidadeUnidade(int quantidadeUnidade) {
		this.quantidadeUnidade = quantidadeUnidade;
	}

	public void setItemPedidoPK(ItemPedidoPK itemPedidoPK) {
		this.itemPedidoPK = itemPedidoPK;
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
	public double getCusto() {
		return this.custo;
	}

	@Override
	public int getNumeroItem() {
		return this.getItemPedidoPK().getNumeroItem();
	}

	@Override
	public int getNumeroPedido() {
		return this.getItemPedidoPK().getPedidoNumero();
	}

	@Override
	public IProduto getProduto() {
		return this.produto;
	}

	@Override
	public IProdutoUnidade getProdutoUnidade() {
		return this.produtoUnidade;
	}

	@Override
	public void setCusto(double arg0) {
		this.custo = arg0;
	}

	@Override
	public void setNumeroItem(int numeroItem) {
		this.getItemPedidoPK().setNumeroItem(numeroItem);
		this.numeroItem = numeroItem;

	}

	public int getPedidoNumero() {
		return this.getItemPedidoPK().getPedidoNumero();
	}

	public void setPedidoNumero(int pedidoNumero) {
		this.getItemPedidoPK().setPedidoNumero(pedidoNumero);
		this.pedidoNumero = pedidoNumero;
	}

	public int getFilialCodigo() {
		return this.getItemPedidoPK().getFilialCodigo();
	}

	public void setFilialCodigo(int filialCodigo) {
		this.getItemPedidoPK().setFilialCodigo(filialCodigo);
		this.filialCodigo = filialCodigo;
	}

	@Override
	public void setNumeroPedido(int arg0) {
		this.getItemPedidoPK().setPedidoNumero(arg0);

	}

	@Override
	public void setProduto(IProduto arg0) {
		this.produto = (Produto) arg0;
	}

	public ProdutoFilial getProdutoFilial() {
		return produtoFilial;
	}

	public void setProdutoFilial(ProdutoFilial produtoFilial) {
		this.produtoFilial = produtoFilial;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
		if (produto != null) {
			setProdutoCodigo(produto.getCodigo());
		}
	}

	public void setProdutoUnidade(ProdutoUnidade produtoUnidade) {
		this.produtoUnidade = produtoUnidade;
	}

	@Override
	public void setProdutoUnidade(IProdutoUnidade arg0) {
		this.produtoUnidade = (ProdutoUnidade) arg0;
	}

	public double getQuantidadeAnterior() {
		return quantidadeAnterior;
	}

	public void setQuantidadeAnterior(double quantidadeAnterior) {
		this.quantidadeAnterior = quantidadeAnterior;
	}

	public static ItemPedido recuperaItemPedido(GenericoDAO dao, int codigoProduto) {

		return dao.uniqueResult(ItemPedido.class, "ipv_procodigo" + " = " + codigoProduto, null);
	}

	@Override
	public String getSeriePedidoCodigo() {
		return this.itemPedidoPK.getSeriePedidoCodigo();
	}

	@Override
	public void setSeriePedidoCodigo(String serieCodigo) {
		this.itemPedidoPK.setSeriePedidoCodigo(serieCodigo);

	}

	public String getOperacao() {
		return operacao;
	}

	public void setOperacao(String operacaoItem) {
		this.operacao = operacaoItem;
	}

	/**
	 * 
	 * @param promocao
	 */
	@Override
	public void setPromocao(Promocao promocao) {
		this.promocao = promocao;
	}

	@Override
	public IPromocao getPromocao() {
		return this.promocao;
	}

	@Override
	public double getValorIcms() {
		return valorIcms;
	}

	@Override
	public void setValorIcms(double valorIcms) {
		this.valorIcms = valorIcms;
	}

	@Override
	public double getValorPis() {
		return valorPis;
	}

	@Override
	public void setValorPis(double valorPis) {
		this.valorPis = valorPis;
	}

	@Override
	public double getValorCofins() {
		return valorCofins;
	}

	@Override
	public void setValorCofins(double valorCofins) {
		this.valorCofins = valorCofins;
	}

	public double getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(double valorFrete) {
		this.valorFrete = valorFrete;
	}

	/**
	 * Monta a where com a chave do pedido.
	 * 
	 * @param pedidoCodigo
	 * @param serie
	 * @param filialCodigo
	 * @return
	 */
	private static String getWhereChavePedido(int pedidoCodigo, String serie, int filialCodigo) {
		String where = COLUNA_SERIEPEDIDO + " = '" + serie + "' AND " + COLUNA_NUMEROPEDIDO + " = " + pedidoCodigo
				+ " AND " + COLUNA_FILIALPEDIDO + " = " + filialCodigo;
		return where;
	}

	/**
	 * Recupera os itens do pedido por meio do Hibernate.
	 * 
	 * @param dao
	 * @param pedidoCodigo
	 * @param serie
	 * @param filialCodigo
	 * @return
	 */
	public static List<ItemPedido> recuperarItensPedido(GenericoDAO dao, int pedidoCodigo, String serie,
			int filialCodigo) {

		if (pedidoCodigo <= 0 || filialCodigo <= 0 || serie == null) {
			return null;
		}

		return dao.list(ItemPedido.class, getWhereChavePedido(pedidoCodigo, serie, filialCodigo), null, null, null);
	}

	/**
	 * Recupera os produtos do pedido, para preencher os dados dos itens.
	 * 
	 * @param dao
	 * @param pedidoCodigo
	 * @param serie
	 * @param filialCodigo
	 * @return
	 */
	public static List<Produto> recuperarProdutosPedido(GenericoDAO dao, int pedidoCodigo, String serie,
			int filialCodigo) {
		String sql = "select produto.* from produto  inner join itenspedido on "
				+ getWhereChavePedido(pedidoCodigo, serie, filialCodigo)
				+ " and ipv_procodigo = pro_codigo order by ipv_procodigo";

		try {
			return dao.list(Produto.class, sql, null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	/**
	 * Recupera os itens do pedido por meio da Api da Space
	 * 
	 * @param dao
	 * @param pedidoCodigo
	 * @param serie
	 * @param filialCodigo
	 * @return
	 */
	public static List<ItemPedido> recuperarItensPedidoApiSpace(GenericoDAO dao, int pedidoCodigo, String serie,
			int filialCodigo) {

		if (pedidoCodigo <= 0 || filialCodigo <= 0 || serie == null) {
			return null;
		}

		String sql = "select ipv_numitem, ipv_procodigo, ipv_quantidade, ipv_unpunidade, ipv_unpquant, ipv_precovenda from itenspedido where "
				+ getWhereChavePedido(pedidoCodigo, serie, filialCodigo) + " order by ipv_procodigo";

		try {
			return dao.list(ItemPedido.class, sql, null);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void reiniciarValoresImpostos() {
		setValorIpi(0.0);
		setValorSubstituicao(0.0);
		setValorSubstituicaoAvulso(0.0);
		setValorIcms(0.0);
		setValorPis(0.0);
		setValorCofins(0.0);
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
	public void setPromocao(IPromocao promocao) {
		this.promocao = (Promocao) promocao;

	}

	public double getPrecoRevenda() {
		return precoRevenda;
	}

	public void setPrecoRevenda(double precoRevenda) {
		this.precoRevenda = precoRevenda;
	}

	
}
