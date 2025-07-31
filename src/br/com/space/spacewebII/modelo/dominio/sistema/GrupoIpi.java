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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "grupoipi")
@XmlRootElement
public class GrupoIpi implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;
	
	private static final String COLUNA_CODIGO = "gti_codigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Basic(optional = false)
	@Column(name = "gti_desc")
	private String descricao;

	@Column(name = "gti_csicodent")
	private String csiEntradaCodigo;

	@Column(name = "gti_csicodsai")
	private String csiSaidaCodigo;

	@Column(name = "gti_ativo")
	private Integer flagAtivo;

	public GrupoIpi() {
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

	public String getCsiEntradaCodigo() {
		return csiEntradaCodigo;
	}

	public void setCsiEntradaCodigo(String csiEntradaCodigo) {
		this.csiEntradaCodigo = csiEntradaCodigo;
	}

	public String getCsiSaidaCodigo() {
		return csiSaidaCodigo;
	}

	public void setCsiSaidaCodigo(String csiSaidaCodigo) {
		this.csiSaidaCodigo = csiSaidaCodigo;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
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
	
	public static GrupoIpi recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(GrupoIpi.class, COLUNA_CODIGO + " = "
				+ codigo, null);
	}
}
