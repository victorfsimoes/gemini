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

import br.com.space.api.negocio.modelo.dominio.IMensagem;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "mensagem")
@XmlRootElement
public class Mensagem implements Serializable, IPersistent, IMensagem {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_CODIGO = "msg_codigo";

	@Id
	@Basic(optional = false)
	@Column(name = "msg_codigo")
	private String codigo;
	

	@Column(name = "msg_desc")
	private String descricao;

	@Column(name = "msg_typedb")
	private String tipo;

	@Column(name = "msg_classifica")
	private Integer classificacao;

	@Column(name = "msg_solucao")
	private String solucao;

	@Column(name = "msg_percodigo")
	private Integer permissaoCodigo;

	@Column(name = "msg_nivel")
	private Integer nivel;

	@Column(name = "msg_modregraneg")
	private String moduloRegraNegocio;

	public Mensagem() {
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getClassificacao() {
		return classificacao;
	}

	public void setClassificacao(Integer classificacao) {
		this.classificacao = classificacao;
	}

	public String getSolucao() {
		return solucao;
	}

	public void setSolucao(String solucao) {
		this.solucao = solucao;
	}

	public Integer getPermissaoCodigo() {
		return permissaoCodigo;
	}

	public void setPermissaoCodigo(Integer permissaoCodigo) {
		this.permissaoCodigo = permissaoCodigo;
	}

	@Override
	public Integer getNivel() {
		return nivel;
	}

	public void setNivel(Integer nivel) {
		this.nivel = nivel;
	}

	public String getModuloRegraNegocio() {
		return moduloRegraNegocio;
	}

	public void setModuloRegraNegocio(String moduloRegraNegocio) {
		this.moduloRegraNegocio = moduloRegraNegocio;
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

	public static Mensagem recuperarUnico(GenericoDAO dao, String codigo) {

		return dao.uniqueResult(Mensagem.class, COLUNA_CODIGO + " = '" + codigo
				+ "'", null);
	}
}
