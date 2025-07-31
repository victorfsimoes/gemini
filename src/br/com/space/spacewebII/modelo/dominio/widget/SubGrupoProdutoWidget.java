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
@Table(name = "subgrupopro")
@XmlRootElement
public class SubGrupoProdutoWidget implements IPersistent, Serializable {

	private static final long serialVersionUID = 1L;

	public static String CAMPO_CODIGO = "codigo";

	@Id
	@Basic(optional = false)
	@Column(name = "sgp_codigo")
	private Integer codigo;

	@Basic(optional = false)
	@Column(name = "sgp_desc")
	private String descricao;

	public SubGrupoProdutoWidget() {
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
