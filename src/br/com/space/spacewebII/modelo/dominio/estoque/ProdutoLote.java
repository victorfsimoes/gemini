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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "produtolote")
@XmlRootElement
public class ProdutoLote implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "ple_filcodigo";
	private static final String COLUNA_LOCAL = "ple_lcecodigo";
	private static final String COLUNA_PRODUTO = "ple_procodigo";
	private static final String COLUNA_LOTE = "ple_lote";

	@EmbeddedId
	protected ProdutoLotePK produtoLotePK;

	@Column(name = "ple_certclass")
	private String certificadoClassificacao;

	@Basic(optional = false)
	@Column(name = "ple_validade")
	@Temporal(TemporalType.DATE)
	private Date dataValidade;

	@Basic(optional = false)
	@Column(name = "ple_estfisico")
	private double estoqueFisico;

	@Column(name = "ple_datalote")
	@Temporal(TemporalType.DATE)
	private Date dataLote;

	@Basic(optional = false)
	@Column(name = "ple_estinicial")
	private double estoqueInicial;

	@Basic(optional = false)
	@Column(name = "ple_estreserv")
	private double estoqueReservado;

	@Basic(optional = false)
	@Column(name = "ple_estusado")
	private double estoqueUsado;

	@Column(name = "ple_estpenconfi")
	private Double estoquePendenteConfirmacao;

	@Column(name = "ple_estpenentre")
	private Double estoquePendenteEntrega;

	public ProdutoLote() {
	}

	public ProdutoLotePK getProdutoLotePK() {
		return produtoLotePK;
	}

	public void setProdutoLotePK(ProdutoLotePK produtoLotePK) {
		this.produtoLotePK = produtoLotePK;
	}

	public String getCertificadoClassificacao() {
		return certificadoClassificacao;
	}

	public void setCertificadoClassificacao(String certificadoClassificacao) {
		this.certificadoClassificacao = certificadoClassificacao;
	}

	public Date getDataValidade() {
		return dataValidade;
	}

	public void setDataValidade(Date dataValidade) {
		this.dataValidade = dataValidade;
	}

	public double getEstoqueFisico() {
		return estoqueFisico;
	}

	public void setEstoqueFisico(double estoqueFisico) {
		this.estoqueFisico = estoqueFisico;
	}

	public Date getDataLote() {
		return dataLote;
	}

	public void setDataLote(Date dataLote) {
		this.dataLote = dataLote;
	}

	public double getEstoqueInicial() {
		return estoqueInicial;
	}

	public void setEstoqueInicial(double estoqueInicial) {
		this.estoqueInicial = estoqueInicial;
	}

	public double getEstoqueReservado() {
		return estoqueReservado;
	}

	public void setEstoqueReservado(double estoqueReservado) {
		this.estoqueReservado = estoqueReservado;
	}

	public double getEstoqueUsado() {
		return estoqueUsado;
	}

	public void setEstoqueUsado(double estoqueUsado) {
		this.estoqueUsado = estoqueUsado;
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

	public static ProdutoLote recuperarUnico(GenericoDAO dao, int localCodigo,
			String lote, int filialCodigo, int produtoCodigo) {

		return dao.uniqueResult(ProdutoLote.class,
				getWhere(localCodigo, lote, filialCodigo, produtoCodigo), null);
	}

	public static long count(GenericoDAO dao, int localCodigo, String lote,
			int filialCodigo, int produtoCodigo) {

		return dao.count("produtolote",
				getWhere(localCodigo, lote, filialCodigo, produtoCodigo));
	}

	private static String getWhere(int localCodigo, String lote,
			int filialCodigo, int produtoCodigo) {

		return ProdutoLote.COLUNA_FILIAL + " = " + filialCodigo + " AND "
				+ ProdutoLote.COLUNA_LOCAL + " = " + localCodigo + " AND "
				+ ProdutoLote.COLUNA_LOTE + " = '" + lote + "' AND "
				+ ProdutoLote.COLUNA_PRODUTO + " = " + produtoCodigo;
	}

	public static List<ProdutoLote> recuperaLotes(GenericoDAO dao,
			Integer produtoCodigo, Integer filialCodigo) {
		StringBuilder where = new StringBuilder(COLUNA_PRODUTO).append(" = ")
				.append(produtoCodigo);

		if (filialCodigo != null)
			where.append(" and ").append(COLUNA_FILIAL).append(" = ")
					.append(filialCodigo);

		return dao.list(ProdutoLote.class, where.toString(), null, null, null);
	}
}
