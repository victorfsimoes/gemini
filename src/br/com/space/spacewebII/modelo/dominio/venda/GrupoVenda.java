/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IGrupoVenda;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "grupovenda")
@XmlRootElement
public class GrupoVenda implements Serializable, IGrupoVenda,
		Comparable<GrupoVenda> {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "grv_codigo")
	private int codigo;

	@Column(name = "grv_desc")
	private String descricao;

	@Column(name = "grv_exclusivo")
	private int flagExclusivo;

	@Column(name = "grv_oepcodigo")
	private int opcaoEspecialCodigo;

	@Column(name = "grv_permisentra")
	private Integer exigePermissao;

	@Column(name = "grv_ativo")
	private Integer flagAtivo;

	public GrupoVenda() {
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

	public int getFlagExclusivo() {
		return flagExclusivo;
	}

	public void setFlagExclusivo(int flagExclusivo) {
		this.flagExclusivo = flagExclusivo;
	}

	public int getOpcaoEspecialCodigo() {
		return opcaoEspecialCodigo;
	}

	public void setOpcaoEspecialCodigo(int opcaoEspecialCodigo) {
		this.opcaoEspecialCodigo = opcaoEspecialCodigo;
	}

	public Integer getExigePermissao() {
		return exigePermissao;
	}

	public void setExigePermissao(Integer exigePermissao) {
		this.exigePermissao = exigePermissao;
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

	public static List<GrupoVenda> recuperar(GenericoDAO dao,
			List<Produto> produtos) {

		return dao
				.list(GrupoVenda.class, "grv_ativo = 1", null, "codigo", null);

	}

	public static GrupoVenda recuperar(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(GrupoVenda.class, "grv_codigo = " + codigo,
				null);
	}

	@Override
	public int compareTo(GrupoVenda o) {
		return Integer.compare(codigo, o.getCodigo());
	}
}
