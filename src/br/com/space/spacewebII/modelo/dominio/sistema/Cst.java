/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

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

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "cst")
@XmlRootElement
public class Cst implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "cst_codigo")
	private String codigo;

	@Column(name = "cst_desc")
	private String descricao;

	@Column(name = "cst_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "cst_tipotrib")
	private String tipoTributacao;

	@Column(name = "cst_calcst")
	private Integer flagCalculaSt;

	@JoinColumn(name = "cst_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filial;

	public Cst() {
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getTipoTributacao() {
		return tipoTributacao;
	}

	public void setTipoTributacao(String tipoTributacao) {
		this.tipoTributacao = tipoTributacao;
	}

	public Integer getFlagCalculaSt() {
		return flagCalculaSt;
	}

	public void setFlagCalculaSt(Integer flagCalculaSt) {
		this.flagCalculaSt = flagCalculaSt;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
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
