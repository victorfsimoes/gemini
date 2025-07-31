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
@Table(name = "linhapro")
@XmlRootElement
public class LinhaProduto implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lpr_codigo")
	private Integer codigo;

	@Basic(optional = false)
	@Column(name = "lpr_desc")
	private String descricao;

	@Basic(optional = false)
	@Column(name = "lpr_ativo")
	private int flagAtivo;

	@Basic(optional = false)
	@Column(name = "lpr_filcodigo")
	private int filialCodigo;

	public LinhaProduto() {
	}

	public LinhaProduto(Integer lprCodigo) {
		this.codigo = lprCodigo;
	}

	public LinhaProduto(Integer lprCodigo, String lprDesc, int lprAtivo,
			int lprFilcodigo) {
		this.codigo = lprCodigo;
		this.descricao = lprDesc;
		this.flagAtivo = lprAtivo;
		this.filialCodigo = lprFilcodigo;
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
