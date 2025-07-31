/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "sepestoque")
@XmlRootElement
public class SeparacaoEstoque implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected SeparacaoEstoquePK separacaoEstoquePK;

	@Basic(optional = false)
	@Column(name = "sep_cnmcodigo")
	private int controleNumeracaoCodigo;

	@Basic(optional = false)
	@Column(name = "sep_unpfatest")
	private double fatorEstoqueUnidade;

	@Basic(optional = false)
	@Column(name = "sep_quantidade")
	private double quantidade;

	public SeparacaoEstoque() {
	}

	public SeparacaoEstoque(SeparacaoEstoquePK separacaoEstoquePK) {
		this.separacaoEstoquePK = separacaoEstoquePK;
	}

	public SeparacaoEstoque(SeparacaoEstoquePK separacaoEstoquePK,
			int sepCnmCodigo, double sepUnpfatest, double sepquantidade) {
		this.separacaoEstoquePK = separacaoEstoquePK;
		this.controleNumeracaoCodigo = sepCnmCodigo;
		this.fatorEstoqueUnidade = sepUnpfatest;
		this.quantidade = sepquantidade;
	}

	public SeparacaoEstoque(int sepFilcodigo, String sepSpvCodigo,
			int sepPedNumero, int sepNumItem, int sepProCodigo,
			int sepLceCodigo, String sepLote, Date sepData, String sepHora,
			int sepCodigo) {
		this.separacaoEstoquePK = new SeparacaoEstoquePK(sepFilcodigo,
				sepSpvCodigo, sepPedNumero, sepNumItem, sepProCodigo,
				sepLceCodigo, sepLote, sepData, sepHora, sepCodigo);
	}

	public SeparacaoEstoquePK getSeparacaoEstoquePK() {
		return separacaoEstoquePK;
	}

	public void setSeparacaoEstoquePK(SeparacaoEstoquePK separacaoEstoquePK) {
		this.separacaoEstoquePK = separacaoEstoquePK;
	}

	public int getControleNumeracaoCodigo() {
		return controleNumeracaoCodigo;
	}

	public void setControleNumeracaoCodigo(int controleNumeracaoCodigo) {
		this.controleNumeracaoCodigo = controleNumeracaoCodigo;
	}

	public double getFatorEstoqueUnidade() {
		return fatorEstoqueUnidade;
	}

	public void setFatorEstoqueUnidade(double fatorEstoqueUnidade) {
		this.fatorEstoqueUnidade = fatorEstoqueUnidade;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
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
