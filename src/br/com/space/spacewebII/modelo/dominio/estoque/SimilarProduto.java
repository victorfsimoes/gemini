/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "similarpro")
@XmlRootElement
public class SimilarProduto implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "spr_codigo")
	private Integer codigo;

	@Basic(optional = false)
	@Column(name = "spr_desc")
	private String descricao;

	@Column(name = "spr_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "spr_filcodigo")
	private int filialCodigo;

	public SimilarProduto() {
	}

	public SimilarProduto(Integer sprCodigo) {
		this.codigo = sprCodigo;
	}

	public SimilarProduto(Integer sprCodigo, String sprDesc, int sprFilcodigo) {
		this.codigo = sprCodigo;
		this.descricao = sprDesc;
		this.filialCodigo = sprFilcodigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
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

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
