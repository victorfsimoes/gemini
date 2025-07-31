/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.widget;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "grupopro")
@XmlRootElement
public class GrupoProdutoWidget implements IPersistent, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String CAMPO_DESCRICAO = "descricao";
	public static final String CAMPO_CODIGO = "codigo";

	@Id
	@Basic(optional = false)
	@Column(name = "grp_codigo")
	private int codigo;

	@Column(name = "grp_desc")
	private String descricao;

	/**
	 * Construtor padrao
	 */
	public GrupoProdutoWidget() {
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		// TODO Auto-generated method stub
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
