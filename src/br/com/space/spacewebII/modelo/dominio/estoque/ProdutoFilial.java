/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.ICustos;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "produtofilial")
@XmlRootElement
public class ProdutoFilial implements Serializable, IPersistent, ICustos {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "pfi_filcodigo";
	private static final String COLUNA_PRODUTO = "pfi_procodigo";

	@EmbeddedId
	protected ProdutoFilialPK produtoFilialPK;

	@Basic(optional = false)
	@Column(name = "pfi_custo1")
	private double custo1;

	@Basic(optional = false)
	@Column(name = "pfi_custo2")
	private double custo2;

	@Basic(optional = false)
	@Column(name = "pfi_custo3")
	private double custo3;

	@Basic(optional = false)
	@Column(name = "pfi_custo4")
	private double custo4;

	@Basic(optional = false)
	@Column(name = "pfi_custo5")
	private double custo5;

	@Basic(optional = false)
	@Column(name = "pfi_custo6")
	private double custo6;

	@Basic(optional = false)
	@Column(name = "pfi_custo7")
	private double custo7;

	@Basic(optional = false)
	@Column(name = "pfi_custo8")
	private double custo8;

	@Basic(optional = false)
	@Column(name = "pfi_custo9")
	private double custo9;

	@Basic(optional = false)
	@Column(name = "pfi_custo10")
	private double custo10;

	@Column(name = "pfi_lcecodigo")
	private Integer localEstoquePadraoCodigo;

	@Basic(optional = false)
	@Column(name = "pfi_margemlucro")
	private float margemLucro;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "pfi_estpenconfi")
	private Double estoquePendenteConfirmacao;

	@Column(name = "pfi_estpenentre")
	private Double estoquePendenteEntrega;

	@Column(name = "pfi_estpenentra")
	private Double estoquePendenteEntrada;

	@Column(name = "pfi_przmaximo")
	private Integer prazoMaximo;

	@Column(name = "pfi_contprzmax")
	private Integer controlePrazoMaximo;

	@Column(name = "pfi_dbcrprod")
	private Integer flagDebitoCreditoProduto;

	@Basic(optional = false)
	@Column(name = "pfi_dtestoqini")
	@Temporal(TemporalType.DATE)
	private Date dataEstoqueInicial;

	@Basic(optional = false)
	@Column(name = "pfi_estoqini")
	private double estoqueInicial;

	@Basic(optional = false)
	@Column(name = "pfi_estoque")
	private double estoque;

	@Column(name = "pfi_estminimo")
	private Double estoqueMinimo;

	@Column(name = "pfi_estmaximo")
	private Double estoqueMaximo;

	@Column(name = "pfi_libvenda")
	private Integer flagLiberadoVenda;

	@Column(name = "pfi_libvendaweb")
	private int flagLiberadoVendaWeb;

	@Column(name = "pfi_libcompra")
	private Integer flagLiberadoCompra;

	@Column(name = "pfi_clbcodigo")
	private Integer colaboradorCodigo;

	@Column(name = "pfi_diasestoque")
	private Integer diasEstoque;

	@Column(name = "pfi_precomin")
	private Double precoMinimo;

	@Column(name = "pfi_precomax")
	private Double precoMaximo;

	@Column(name = "pfi_diasestmin")
	private Integer diasEstoqueMinimo;

	@Column(name = "pfi_diasestmax")
	private Integer diasEstoqueMaximo;

	@Column(name = "pfi_gcocodigoi")
	private Integer grupoComissaoInternaCodigo;

	@Column(name = "pfi_gcocodigoe")
	private Integer grupoComissaoExternaCodigo;

	@Column(name = "pfi_gcocodigooi")
	private Integer grupoComissaoOfertaInternaCodigo;

	@Column(name = "pfi_gcocodigooe")
	private Integer grupoComissaoOfertaExternaCodigo;

	@Column(name = "pfi_dtultvenda")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaVenda;

	@Column(name = "pfi_dtfalta")
	@Temporal(TemporalType.DATE)
	private Date dataFalta;

	@Column(name = "pfi_grtcodigo")
	private int grupoTributacaoCodigo;

	@Column(name = "pfi_gstcodigo")
	private int grupoSTCodigo;

	@Basic(optional = false)
	@Column(name = "pfi_valpauta")
	private double valorPauta;

	@Column(name = "pfi_precominct")
	private Double precoMinimoCusto;

	@Basic(optional = false)
	@Column(name = "pfi_margemlucr2")
	private float margemLucro2;

	@Column(name = "pfi_lopcodigo")
	private String localizacaoProdutoCodigo;

	@Column(name = "pfi_inativo")
	private Integer flagInativo;

	@Column(name = "pfi_dtcontrole")
	@Temporal(TemporalType.DATE)
	private Date dataControle;

	@Column(name = "pfi_lastro")
	private Integer lastro;

	@Column(name = "pfi_altura")
	private Integer altura;

	@Column(name = "pfi_naoexpcupom")
	private Integer flagNaoExportaCupom;

	/*
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @Basic(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "pfi_procodigo", referencedColumnName = "pro_codigo",
	 * insertable = false, updatable = false) private Produto produto;
	 * 
	 * @ManyToOne(fetch = FetchType.LAZY)
	 * 
	 * @Basic(fetch = FetchType.LAZY)
	 * 
	 * @JoinColumn(name = "pfi_filcodigo", referencedColumnName = "fil_codigo",
	 * insertable = false, updatable = false) private Filial filial;
	 */
	@Column(name = "pfi_exiprecovis")
	private int exibePrecoVisitante;

	@Column(name = "pfi_nfatsestoq")
	private int flagNaoFaturarSemEstoque;

	public ProdutoFilial() {
	}

	public ProdutoFilialPK getProdutoFilialPK() {
		return produtoFilialPK;
	}

	public void setProdutoFilialPK(ProdutoFilialPK produtoFilialPK) {
		this.produtoFilialPK = produtoFilialPK;
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

	public Integer getLocalEstoquePadraoCodigo() {
		return localEstoquePadraoCodigo;
	}

	public void setLocalEstoquePadraoCodigo(Integer localEstoquePadraoCodigo) {
		this.localEstoquePadraoCodigo = localEstoquePadraoCodigo;
	}

	public float getMargemLucro() {
		return margemLucro;
	}

	public void setMargemLucro(float margemLucro) {
		this.margemLucro = margemLucro;
	}

	public Double getEstoquePendenteConfirmacao() {
		return estoquePendenteConfirmacao;
	}

	public void setEstoquePendenteConfirmacao(Double estoquePendenteConfirmacao) {
		this.estoquePendenteConfirmacao = estoquePendenteConfirmacao;
	}

	public Double getEstoquePendenteEntrega() {
		return estoquePendenteEntrega;
	}

	public void setEstoquePendenteEntrega(Double estoquePendenteEntrega) {
		this.estoquePendenteEntrega = estoquePendenteEntrega;
	}

	public Double getEstoquePendenteEntrada() {
		return estoquePendenteEntrada;
	}

	public void setEstoquePendenteEntrada(Double estoquePendenteEntrada) {
		this.estoquePendenteEntrada = estoquePendenteEntrada;
	}

	public Integer getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(Integer prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	public boolean isControlePrazoMaximo() {
		return controlePrazoMaximo != null && controlePrazoMaximo == 1;
	}

	public Integer getControlePrazoMaximo() {
		return controlePrazoMaximo;
	}

	public void setControlePrazoMaximo(Integer controlePrazoMaximo) {
		this.controlePrazoMaximo = controlePrazoMaximo;
	}

	public Integer getFlagDebitoCreditoProduto() {
		return flagDebitoCreditoProduto;
	}

	public void setFlagDebitoCreditoProduto(Integer flagDebitoCreditoProduto) {
		this.flagDebitoCreditoProduto = flagDebitoCreditoProduto;
	}

	public Date getDataEstoqueInicial() {
		return dataEstoqueInicial;
	}

	public void setDataEstoqueInicial(Date dataEstoqueInicial) {
		this.dataEstoqueInicial = dataEstoqueInicial;
	}

	public double getEstoqueInicial() {
		return estoqueInicial;
	}

	public void setEstoqueInicial(double estoqueInicial) {
		this.estoqueInicial = estoqueInicial;
	}

	public double getEstoque() {
		return estoque;
	}

	public void setEstoque(double estoque) {
		this.estoque = estoque;
	}

	public Double getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public void setEstoqueMinimo(Double estoqueMinimo) {
		this.estoqueMinimo = estoqueMinimo;
	}

	public Double getEstoqueMaximo() {
		return estoqueMaximo;
	}

	public void setEstoqueMaximo(Double estoqueMaximo) {
		this.estoqueMaximo = estoqueMaximo;
	}

	public Integer getFlagLiberadoVenda() {
		return flagLiberadoVenda;
	}

	public void setFlagLiberadoVenda(Integer flagLiberadoVenda) {
		this.flagLiberadoVenda = flagLiberadoVenda;
	}

	public Integer getFlagLiberadoCompra() {
		return flagLiberadoCompra;
	}

	public void setFlagLiberadoCompra(Integer flagLiberadoCompra) {
		this.flagLiberadoCompra = flagLiberadoCompra;
	}

	public Integer getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(Integer colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public Integer getDiasEstoque() {
		return diasEstoque;
	}

	public void setDiasEstoque(Integer diasEstoque) {
		this.diasEstoque = diasEstoque;
	}

	public Double getPrecoMinimo() {
		return precoMinimo;
	}

	public void setPrecoMinimo(Double precoMinimo) {
		this.precoMinimo = precoMinimo;
	}

	public Double getPrecoMaximo() {
		return precoMaximo;
	}

	public void setPrecoMaximo(Double precoMaximo) {
		this.precoMaximo = precoMaximo;
	}

	public Integer getDiasEstoqueMinimo() {
		return diasEstoqueMinimo;
	}

	public void setDiasEstoqueMinimo(Integer diasEstoqueMinimo) {
		this.diasEstoqueMinimo = diasEstoqueMinimo;
	}

	public Integer getDiasEstoqueMaximo() {
		return diasEstoqueMaximo;
	}

	public void setDiasEstoqueMaximo(Integer diasEstoqueMaximo) {
		this.diasEstoqueMaximo = diasEstoqueMaximo;
	}

	public Integer getGrupoComissaoInternaCodigo() {
		return grupoComissaoInternaCodigo;
	}

	public void setGrupoComissaoInternaCodigo(Integer grupoComissaoInternaCodigo) {
		this.grupoComissaoInternaCodigo = grupoComissaoInternaCodigo;
	}

	public Integer getGrupoComissaoExternaCodigo() {
		return grupoComissaoExternaCodigo;
	}

	public void setGrupoComissaoExternaCodigo(Integer grupoComissaoExternaCodigo) {
		this.grupoComissaoExternaCodigo = grupoComissaoExternaCodigo;
	}

	public Integer getGrupoComissaoOfertaInternaCodigo() {
		return grupoComissaoOfertaInternaCodigo;
	}

	public void setGrupoComissaoOfertaInternaCodigo(Integer grupoComissaoOfertaInternaCodigo) {
		this.grupoComissaoOfertaInternaCodigo = grupoComissaoOfertaInternaCodigo;
	}

	public Integer getGrupoComissaoOfertaExternaCodigo() {
		return grupoComissaoOfertaExternaCodigo;
	}

	public void setGrupoComissaoOfertaExternaCodigo(Integer grupoComissaoOfertaExternaCodigo) {
		this.grupoComissaoOfertaExternaCodigo = grupoComissaoOfertaExternaCodigo;
	}

	public Date getDataUltimaVenda() {
		return dataUltimaVenda;
	}

	public void setDataUltimaVenda(Date dataUltimaVenda) {
		this.dataUltimaVenda = dataUltimaVenda;
	}

	public Date getDataFalta() {
		return dataFalta;
	}

	public void setDataFalta(Date dataFalta) {
		this.dataFalta = dataFalta;
	}

	public Integer getGrupoTributacaoCodigo() {
		return grupoTributacaoCodigo;
	}

	public void setGrupoTributacaoCodigo(Integer grupoTributacaoCodigo) {
		this.grupoTributacaoCodigo = grupoTributacaoCodigo;
	}

	public Integer getGrupoSTCodigo() {
		return grupoSTCodigo;
	}

	public void setGrupoSTCodigo(Integer grupoSTCodigo) {
		this.grupoSTCodigo = grupoSTCodigo;
	}

	public double getValorPauta() {
		return valorPauta;
	}

	public void setValorPauta(double valorPauta) {
		this.valorPauta = valorPauta;
	}

	public Double getPrecoMinimoCusto() {
		return precoMinimoCusto;
	}

	public void setPrecoMinimoCusto(Double precoMinimoCusto) {
		this.precoMinimoCusto = precoMinimoCusto;
	}

	public float getMargemLucro2() {
		return margemLucro2;
	}

	public void setMargemLucro2(float margemLucro2) {
		this.margemLucro2 = margemLucro2;
	}

	public String getLocalizacaoProdutoCodigo() {
		return localizacaoProdutoCodigo;
	}

	public void setLocalizacaoProdutoCodigo(String localizacaoProdutoCodigo) {
		this.localizacaoProdutoCodigo = localizacaoProdutoCodigo;
	}

	public Integer getFlagInativo() {
		return flagInativo;
	}

	public void setFlagInativo(Integer flagInativo) {
		this.flagInativo = flagInativo;
	}

	public Date getDataControle() {
		return dataControle;
	}

	public void setDataControle(Date dataControle) {
		this.dataControle = dataControle;
	}

	public Integer getLastro() {
		return lastro;
	}

	public void setLastro(Integer lastro) {
		this.lastro = lastro;
	}

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

	public Integer getFlagNaoExportaCupom() {
		return flagNaoExportaCupom;
	}

	public void setFlagNaoExportaCupom(Integer flagNaoExportaCupom) {
		this.flagNaoExportaCupom = flagNaoExportaCupom;
	}

	/*
	 * public Produto getProduto() { return produto; }
	 * 
	 * public void setProduto(Produto produto) { this.produto = produto; }
	 * 
	 * public Filial getFilial() { return filial; }
	 * 
	 * public void setFilial(Filial filial) { this.filial = filial; }
	 */

	public int getFlagLiberadoVendaWeb() {
		return flagLiberadoVendaWeb;
	}

	public void setFlagLiberadoVendaWeb(int flagLiberadoVendaWeb) {
		this.flagLiberadoVendaWeb = flagLiberadoVendaWeb;
	}

	public int getExibePrecoVisitante() {
		return exibePrecoVisitante;
	}

	public void setExibePrecoVisitante(int exibePrecoVisitante) {
		this.exibePrecoVisitante = exibePrecoVisitante;
	}
	
	public int getFlagNaoFaturarSemEstoque() {
		return flagNaoFaturarSemEstoque;
	}

	public void setFlagNaoFaturarSemEstoque(int flagNaoFaturarSemEstoque) {
		this.flagNaoFaturarSemEstoque = flagNaoFaturarSemEstoque;
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
	 * @param produtoCodigo
	 * @return
	 */
	public static ProdutoFilial recuperarUnico(GenericoDAO dao, int filialCodigo, int produtoCodigo) {

		return dao.uniqueResult(ProdutoFilial.class, getWhere(filialCodigo, produtoCodigo), null);
	}

	public static long count(GenericoDAO dao, int filialCodigo, int produtoCodigo) {

		return dao.count("produtofilial", getWhere(filialCodigo, produtoCodigo));
	}

	private static String getWhere(int filialCodigo, int produtoCodigo) {
		return ProdutoFilial.COLUNA_FILIAL + " = " + filialCodigo + " AND " + ProdutoFilial.COLUNA_PRODUTO + " = "
				+ produtoCodigo;
	}

	public static double recuperarPrecoCusto(GenericoDAO dao, int filialCodigo, int produtoCodigo, int qualCusto) {

		String colunaCusto = "pfi_custo" + qualCusto;

		String select = "select " + colunaCusto + " from produtofilial where " + getWhere(filialCodigo, produtoCodigo);

		ResultSet rs = null;

		double precoCusto = 0;

		try {

			rs = dao.listResultSet(select, null, null);

			while (rs.next()) {
				precoCusto = rs.getDouble(colunaCusto);
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			GenericoDAO.closeResultSet(rs);
		}

		return precoCusto;
	}

}
