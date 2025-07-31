/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.dominiojee.dominio.estoque.TipoMovimentoEstoque;
import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "kardex")
@XmlRootElement
public class Kardex implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "kdx_codigo")
	private int codigo;

	@Basic(optional = false)
	@Column(name = "kdx_cnmcodigo")
	private int controleNumeroMovCodigo;

	@Basic(optional = false)
	@Column(name = "kdx_lcecodigo")
	private int localEstoqueCodigo;

	@Basic(optional = false)
	@Column(name = "kdx_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "kdx_lote")
	private String lote;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "kdx_estoqatual")
	private Double estoqueAtual;

	@Basic(optional = false)
	@Column(name = "kdx_quantidade")
	private double quantidade;

	@Basic(optional = false)
	@Column(name = "kdx_estoqmov")
	private String estoqueMovimentado;

	@Basic(optional = false)
	@Column(name = "kdx_data")
	@Temporal(TemporalType.DATE)
	private Date data;

	@Basic(optional = false)
	@Column(name = "kdx_hora")
	private String hora;

	@Basic(optional = false)
	@Column(name = "kdx_sescodigo")
	private int sessaoCodigo;

	@Column(name = "kdx_fatestoque")
	private Float fatorEstoque;

	@Column(name = "kdx_validade")
	@Temporal(TemporalType.DATE)
	private Date validade;

	@Basic(optional = false)
	@Column(name = "kdx_idmovimento")
	private String idMovimento;

	@Basic(optional = false)
	@Column(name = "kdx_valor")
	private double valor;

	@Column(name = "kdx_numeromov")
	private String numeroMovimento;

	@Column(name = "kdx_datamov")
	@Temporal(TemporalType.DATE)
	private Date dataMovimento;

	@JoinColumn(name = "kdx_tmecodigo", referencedColumnName = "tme_codigo")
	@ManyToOne(optional = false)
	private TipoMovimentoEstoque tipoMovimentoEstoque;

	@JoinColumn(name = "kdx_procodigo", referencedColumnName = "pro_codigo")
	@ManyToOne(optional = false)
	private Produto produtoCodigo;

	public Kardex() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getCnmCodigo() {
		return controleNumeroMovCodigo;
	}

	public void setCnmCodigo(int cnmCodigo) {
		this.controleNumeroMovCodigo = cnmCodigo;
	}

	public int getLocalEstoqueCodigo() {
		return localEstoqueCodigo;
	}

	public void setLocalEstoqueCodigo(int localEstoqueCodigo) {
		this.localEstoqueCodigo = localEstoqueCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public Double getEstoqueAtual() {
		return estoqueAtual;
	}

	public void setEstoqueAtual(Double estoqueAtual) {
		this.estoqueAtual = estoqueAtual;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public String getEstoqueMovimentado() {
		return estoqueMovimentado;
	}

	public void setEstoqueMovimentado(String estoqueMovimentado) {
		this.estoqueMovimentado = estoqueMovimentado;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getSessaoCodigo() {
		return sessaoCodigo;
	}

	public void setSessaoCodigo(int sessaoCodigo) {
		this.sessaoCodigo = sessaoCodigo;
	}

	public Float getFatorEstoque() {
		return fatorEstoque;
	}

	public void setFatorEstoque(Float fatorEstoque) {
		this.fatorEstoque = fatorEstoque;
	}

	public Date getValidade() {
		return validade;
	}

	public void setValidade(Date validade) {
		this.validade = validade;
	}

	public String getIdMovimento() {
		return idMovimento;
	}

	public void setIdMovimento(String idMovimento) {
		this.idMovimento = idMovimento;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getNumeroMovimento() {
		return numeroMovimento;
	}

	public void setNumeroMovimento(String numeroMovimento) {
		this.numeroMovimento = numeroMovimento;
	}

	public Date getDataMovimento() {
		return dataMovimento;
	}

	public void setDataMovimento(Date dataMovimento) {
		this.dataMovimento = dataMovimento;
	}

	public TipoMovimentoEstoque getTipoMovimentoEstoquecodigo() {
		return tipoMovimentoEstoque;
	}

	public void setTipoMovimentoEstoquecodigo(
			TipoMovimentoEstoque tipoMovimentoEstoquecodigo) {
		this.tipoMovimentoEstoque = tipoMovimentoEstoquecodigo;
	}

	public Produto getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(Produto produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
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

	
}
