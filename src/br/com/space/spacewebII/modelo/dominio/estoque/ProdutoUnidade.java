/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.negocio.modelo.dominio.IProdutoUnidade;
import br.com.space.api.spa.model.dao.db.Dictionary;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "unidadepro")
@XmlRootElement
public class ProdutoUnidade implements Serializable, IProdutoUnidade, Comparable<ProdutoUnidade> {

	private static final long serialVersionUID = 1L;
	private static br.com.space.api.spa.model.dao.db.Table table;

	public static final String COLUNA_PRODUTO_CODIGO = "unp_procodigo";
	public static final String COLUNA_LIBERADO_VENDA = "unp_libvenda";
	public static final String COLUNA_UNIDADE = "unp_unidade";
	public static final String COLUNA_QUANTIDADE = "unp_quantidade";

	@EmbeddedId
	protected ProdutoUnidadePK produtoUnidadePK;

	@Column(name = "unp_descemb")
	private String unidadeDescricao;

	@Basic(optional = false)
	@Column(name = "unp_fatvenda")
	private double fatorVenda;

	@Basic(optional = false)
	@Column(name = "unp_fatestoque")
	private double fatorEstoque;

	@Column(name = "unp_padvenda")
	private int flagUnidadePadraoVenda;

	@Column(name = "unp_padestoque")
	private int flagUnidadePadraoEstoque;

	@Column(name = "unp_libvenda")
	private int flagLiberadoVenda;

	@Column(name = "unp_fraciona")
	private int flagPermiteFracionamento;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "unp_fracminima")
	private double fracionamentoMinimo;

	@Column(name = "unp_qminvenda")
	private double quantidadeMinimaVenda;

	@Column(name = "unp_ativo")
	private int flagAtivo;

	@Basic(optional = false)
	@Column(name = "unp_filcodigo")
	private int filialCodigo;

	@Column(name = "unp_padcompra")
	private int flagUnidadePadraoCompra;

	@Column(name = "unp_mtcubico")
	private Double metroCubico;

	@Column(name = "unp_referencia")
	private Integer referencia;

	@Column(name = "unp_altura")
	private Double altura;

	@Column(name = "unp_largura")
	private Double largura;

	@Column(name = "unp_compri")
	private Double comprimento;

	@Column(name = "unp_diametro")
	private Double diametro;

	@Column(name = "unp_lastroest")
	private Integer lastroEstoque;

	@Column(name = "unp_alturaest")
	private Integer alturaEstoque;

	@Column(name = "unp_padarm")
	private int flagUnidadePadraoArmazenamento;

	@Basic(optional = false)
	@Column(name = "unp_fracmincomp")
	private double fracaoMinimaCompra;

	@Column(name = "unp_varejo")
	private int flagVarejo;

	@Column(name = "unp_przmaximo")
	private Integer prazoMaximo = 0;

	@Column(name = "unp_contprzmax")
	private Integer flagControlaPrazoMaximo = 0;

	public ProdutoUnidade() {
	}

	public ProdutoUnidade(int produtoCodigo, String unidade, int quantidadeUnidade) {
		this();
		this.getProdutoUnidadePK().setProdutoCodigo(produtoCodigo);
		this.getProdutoUnidadePK().setUnidade(unidade);
		this.getProdutoUnidadePK().setQuantidadeUnidade(quantidadeUnidade);
	}

	public ProdutoUnidadePK getProdutoUnidadePK() {
		if (produtoUnidadePK == null)
			produtoUnidadePK = new ProdutoUnidadePK();

		return produtoUnidadePK;
	}

	public void setProdutoUnidadePK(ProdutoUnidadePK produtoUnidadePK) {
		this.produtoUnidadePK = produtoUnidadePK;
	}

	public String getUnidadeDescricao() {
		return unidadeDescricao;
	}

	public void setUnidadeDescricao(String unidadeDescricao) {
		this.unidadeDescricao = unidadeDescricao;
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

	public int getFlagUnidadePadraoVenda() {
		return flagUnidadePadraoVenda;
	}

	public void setFlagUnidadePadraoVenda(int flagUnidadePadraoVenda) {
		this.flagUnidadePadraoVenda = flagUnidadePadraoVenda;
	}

	public int getFlagUnidadePadraoEstoque() {
		return flagUnidadePadraoEstoque;
	}

	public void setFlagUnidadePadraoEstoque(int flagUnidadePadraoEstoque) {
		this.flagUnidadePadraoEstoque = flagUnidadePadraoEstoque;
	}

	public int getFlagLiberadoVenda() {
		return flagLiberadoVenda;
	}

	public void setFlagLiberadoVenda(int flagLiberadoVenda) {
		this.flagLiberadoVenda = flagLiberadoVenda;
	}

	public int getFlagPermiteFracionamento() {
		return flagPermiteFracionamento;
	}

	public void setFlagPermiteFracionamento(int flagPermiteFracionamento) {
		this.flagPermiteFracionamento = flagPermiteFracionamento;
	}

	public double getFracionamentoMinimo() {
		return fracionamentoMinimo;
	}

	public void setFracionamentoMinimo(double fracionamentoMinimo) {
		this.fracionamentoMinimo = fracionamentoMinimo;
	}

	public double getQuantidadeMinimaVenda() {
		return quantidadeMinimaVenda;
	}

	public void setQuantidadeMinimaVenda(double quantidadeMinimaVenda) {
		this.quantidadeMinimaVenda = quantidadeMinimaVenda;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	@Override
	public int getFlagUnidadePadraoCompra() {
		return flagUnidadePadraoCompra;
	}

	public void setFlagUnidadePadraoCompra(int flagUnidadePadraoCompra) {
		this.flagUnidadePadraoCompra = flagUnidadePadraoCompra;
	}

	public Double getMetroCubico() {
		return metroCubico;
	}

	public void setMetroCubico(Double metroCubico) {
		this.metroCubico = metroCubico;
	}

	public Integer getReferencia() {
		return referencia;
	}

	public void setReferencia(Integer referencia) {
		this.referencia = referencia;
	}

	public Double getAltura() {
		return altura;
	}

	public void setAltura(Double altura) {
		this.altura = altura;
	}

	public Double getLargura() {
		return largura;
	}

	public void setLargura(Double largura) {
		this.largura = largura;
	}

	public Double getComprimento() {
		return comprimento;
	}

	public void setComprimento(Double comprimento) {
		this.comprimento = comprimento;
	}

	public Double getDiametro() {
		return diametro;
	}

	public void setDiametro(Double diametro) {
		this.diametro = diametro;
	}

	public Integer getLastroEstoque() {
		return lastroEstoque;
	}

	public void setLastroEstoque(Integer lastroEstoque) {
		this.lastroEstoque = lastroEstoque;
	}

	public Integer getAlturaEstoque() {
		return alturaEstoque;
	}

	public void setAlturaEstoque(Integer alturaEstoque) {
		this.alturaEstoque = alturaEstoque;
	}

	public int getFlagUnidadePadraoArmazenamento() {
		return flagUnidadePadraoArmazenamento;
	}

	public void setFlagUnidadePadraoArmazenamento(int flagUnidadePadraoArmazenamento) {
		this.flagUnidadePadraoArmazenamento = flagUnidadePadraoArmazenamento;
	}

	public double getFracaoMinimaCompra() {
		return fracaoMinimaCompra;
	}

	public void setFracaoMinimaCompra(double fracaoMinimaCompra) {
		this.fracaoMinimaCompra = fracaoMinimaCompra;
	}

	public int getFlagVarejo() {
		return flagVarejo;
	}

	public void setFlagVarejo(int flagVarejo) {
		this.flagVarejo = flagVarejo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return ProdutoUnidade.table;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		ProdutoUnidade.table = arg0;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	@Override
	public long getProdutoCodigo() {
		return this.getProdutoUnidadePK().getProdutoCodigo();
	}

	@Override
	public int getQuantidade() {
		return this.getProdutoUnidadePK().getQuantidadeUnidade();
	}

	@Override
	public String getUnidade() {
		return this.getProdutoUnidadePK().getUnidade();
	}

	@Override
	public int getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(Integer prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	@Override
	public int getFlagControlaPrazoMaximo() {
		return flagControlaPrazoMaximo;
	}

	public void setFlagControlaPrazoMaximo(Integer flagControlaPrazoMaximo) {
		this.flagControlaPrazoMaximo = flagControlaPrazoMaximo;
	}

	/**
	 * 
	 */
	@Override
	public int compareTo(ProdutoUnidade o) {
		int result = 0;
		if (this.produtoUnidadePK != null && o.getProdutoUnidadePK() != null) {
			result = Integer.compare(produtoUnidadePK.getProdutoCodigo(), o.getProdutoUnidadePK().getProdutoCodigo());
			if (result == 0) {
				result = this.produtoUnidadePK.getUnidade().compareTo(o.getUnidade());
				if (result == 0) {
					Integer.compare(this.produtoUnidadePK.getQuantidadeUnidade(),
							o.getProdutoUnidadePK().getQuantidadeUnidade());
				}
			}
		}

		return result;
	}

	/**
	 * Localiza uma unidade na lista.
	 * 
	 * @param produtoCodigo
	 * @param unidade
	 * @param quantidadeUnidade
	 * @return unidade encontrado.
	 */
	public static ProdutoUnidade pessquisarUnidadeLista(List<ProdutoUnidade> listaUnidade, int produtoCodigo,
			String unidade, int quantidadeUnidade) {

		ProdutoUnidade result = null;

		for (ProdutoUnidade produtoUnidade : listaUnidade) {
			if (produtoUnidade.getProdutoCodigo() == produtoCodigo && produtoUnidade.getUnidade().equals(unidade)
					&& produtoUnidade.getQuantidade() == quantidadeUnidade) {
				result = produtoUnidade;
				break;
			}

		}

		return result;
	}

	/**
	 * 
	 * @param dao
	 * @param produtoCodigo
	 * @param unidade
	 * @param quantidadeUnidade
	 * @return
	 */
	public static ProdutoUnidade recuperar(GenericoDAO dao, int produtoCodigo, String unidade, int quantidadeUnidade) {

		return dao.uniqueResult(ProdutoUnidade.class,
				ProdutoUnidade.COLUNA_PRODUTO_CODIGO + "=" + produtoCodigo + " and " + ProdutoUnidade.COLUNA_UNIDADE
						+ "='" + unidade + "' and " + ProdutoUnidade.COLUNA_QUANTIDADE + "=" + quantidadeUnidade,
				null);
	}

	/**
	 * 
	 * @param dao
	 * @param produtoCodigo
	 * @param padraoVenda
	 * @return
	 */
	public static ProdutoUnidade recuperar(GenericoDAO dao, int produtoCodigo, int padraoVenda) {

		return dao.uniqueResult(ProdutoUnidade.class, ProdutoUnidade.COLUNA_PRODUTO_CODIGO + "=" + produtoCodigo
				+ " and unp_padvenda = " + padraoVenda + " and unp_ativo = 1", null);
	}

	/**
	 * 
	 * @param dao
	 * @param padraoDeVenda
	 * @return
	 */
	public static List<ProdutoUnidade> recuperar(GenericoDAO dao, int padraoDeVenda) {

		String where = (padraoDeVenda == 1 ? "unp_padvenda = 1 and unp_ativo = 1" : "");

		return dao.list(ProdutoUnidade.class, where, null, "produtoUnidadePK", null);
	}

	/**
	 * 
	 * @param dao
	 * @param codigoProduto
	 * @return
	 */
	public static List<ProdutoUnidade> recuperarUnidadesProdutoParaVenda(GenericoDAO dao, int codigoProduto) {

		String where = COLUNA_PRODUTO_CODIGO + " = " + codigoProduto + " and unp_ativo = 1 and " + COLUNA_LIBERADO_VENDA
				+ " = 1";

		return dao.list(ProdutoUnidade.class, where, null, "flagUnidadePadraoVenda", null);
	}

	/**
	 * 
	 * @param dao
	 * @param produtoCodigo
	 * @return
	 */
	public static ProdutoUnidade recuperarPadraoEmbarque(GenericoDAO dao, int produtoCodigo) {
		return dao.uniqueResult(ProdutoUnidade.class,
				COLUNA_PRODUTO_CODIGO + " = " + produtoCodigo + " and unp_padarm = 1 and unp_ativo = 1", null);
	}

	/**
	 * 
	 * @param dao
	 * @param produtoCodigo
	 * @return
	 */
	public static ProdutoUnidade recuperarPadraoVenda(GenericoDAO dao, int produtoCodigo) {
		return dao.uniqueResult(ProdutoUnidade.class,
				COLUNA_PRODUTO_CODIGO + " = " + produtoCodigo + " and unp_padvenda = 1 and unp_ativo = 1", null);
	}

	public static List<ProdutoUnidade> recuperarPadraoVendaResultSet(GenericoDAO dao) throws Exception {

		if (ProdutoUnidade.table == null)
			ProdutoUnidade.table = Dictionary.getTable(Produto.class);

		String colunas = ProdutoUnidade.table.getColumnsNamesWithCommas();

		String select = "select " + colunas + " from unidadepro " + " where unp_padvenda = 1 and unp_ativo = 1";

		return dao.list(ProdutoUnidade.class, select, null);
	}

	public static void atualizarDataUltimaVendaProduto(GenericoDAO dao, int produtoCodigo, int filialCodigo,
			Date dataVenda) {

		String stringData = Conversao.formatarData(dataVenda, Conversao.FORMATO_DATA_DB_YMD);

		String update = "update produtofilial set pfi_dtultvenda = '" + stringData + "' where pfi_procodigo = "
				+ produtoCodigo + " and pfi_filcodigo = " + filialCodigo;

		dao.executeUpdate(update);

	}

}