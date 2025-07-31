/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "precobase")
@XmlRootElement
public class PrecoBase implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "prb_codigo")
	private int codigo;

	@Column(name = "prb_desc")
	private String descricao;

	@Column(name = "prb_formula")
	private String formula;

	@Column(name = "prb_ativo")
	private Integer flagAtivo;

	@Column(name = "prb_atuprcvenda")
	private Integer flagAtualizaPrecoCusto;

	@Column(name = "prb_nenviapalm")
	private Integer flagNaoEnviaPalm;

	@JoinColumn(name = "prb_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filialCodigo;

	public PrecoBase() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getFormula() {
		return formula;
	}

	public void setFormula(String formula) {
		this.formula = formula;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagAtualizaPrecoCusto() {
		return flagAtualizaPrecoCusto;
	}

	public void setFlagAtualizaPrecoCusto(Integer flagAtualizaPrecoCusto) {
		this.flagAtualizaPrecoCusto = flagAtualizaPrecoCusto;
	}

	public Integer getFlagNaoEnviaPalm() {
		return flagNaoEnviaPalm;
	}

	public void setFlagNaoEnviaPalm(Integer flagNaoEnviaPalm) {
		this.flagNaoEnviaPalm = flagNaoEnviaPalm;
	}

	public Filial getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Filial filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();

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
}
