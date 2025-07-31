/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.ITabelaPrecoCondicao;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "tabprecocond")
@XmlRootElement
public class TabelaPrecoCondicao implements Serializable, ITabelaPrecoCondicao {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_TABELAPRECO_CODIGO = "tpc_tprcodigo";
	public static final String COLUNA_CONDICAOPAGAMENTO_CODIGO = "tpc_cpgcodigo";

	@EmbeddedId
	protected TabelaPrecoCondicaoPK tabelaPrecoCondicaoPK;

	@Column(name = "tpc_filcodigo")
	private Integer filialCodigo;

	@Column(name = "tpc_indice")
	private double indice;

	@Basic(optional = false)
	@Column(name = "tpc_indiceof")
	private double indiceOferta;

	@JoinColumn(name = "tpc_cpgcodigo", referencedColumnName = "cpg_codigo", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private CondicaoPagamento condicaoPagamento;

	public TabelaPrecoCondicao() {
	}

	public TabelaPrecoCondicao(TabelaPrecoCondicaoPK tabelaPrecoCondicaoPK) {
		this.tabelaPrecoCondicaoPK = tabelaPrecoCondicaoPK;
	}

	public TabelaPrecoCondicaoPK getTabelaPrecoCondicaoPK() {
		return tabelaPrecoCondicaoPK;
	}

	public void setTabelaPrecoCondicaoPK(
			TabelaPrecoCondicaoPK tabelaPrecoCondicaoPK) {
		this.tabelaPrecoCondicaoPK = tabelaPrecoCondicaoPK;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public double getIndice() {
		if (indice <= 0)
			return 1;
		return indice;
	}

	public void setIndice(double indice) {
		this.indice = indice;
	}

	public double getIndiceOferta() {
		if (indiceOferta <= 0)
			return 1;
		return indiceOferta;
	}

	public void setIndiceOferta(double indiceOferta) {
		this.indiceOferta = indiceOferta;
	}

	public CondicaoPagamento getCondicaoPagamento() {
		return condicaoPagamento;
	}

	public void setCondicaoPagamento(CondicaoPagamento condicaoPagamento) {
		this.condicaoPagamento = condicaoPagamento;
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
	public int getCondicaoPagamentoCodigo() {
		// TODO Auto-generated method stub
		return this.getTabelaPrecoCondicaoPK().getCondicaoPagamentoCodigo();
	}

	@Override
	public long getTabelaPrecoCodigo() {
		// TODO Auto-generated method stub
		return this.getTabelaPrecoCondicaoPK().getTabelaPrecoCodigo();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}
}
