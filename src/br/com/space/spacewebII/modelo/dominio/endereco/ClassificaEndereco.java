/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "classificaend")
@XmlRootElement
public class ClassificaEndereco implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "cle_codigo")
	private int codigo;

	@Column(name = "cle_desc")
	private String descricao;

	@Column(name = "cle_unico")
	private Integer flagUnico;

	@Column(name = "cle_ativo")
	private Integer flagAtivo;

	@JoinColumn(name = "cle_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filial;

	public ClassificaEndereco() {
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

	public boolean isUnico() {
		return Integer.valueOf(1).equals(flagUnico);
	}

	public Integer getFlagUnico() {
		return flagUnico;
	}

	public void setFlagUnico(Integer flagUnico) {
		this.flagUnico = flagUnico;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Filial getCleFilcodigo() {
		return filial;
	}

	public void setCleFilcodigo(Filial cleFilcodigo) {
		this.filial = cleFilcodigo;
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

	public static List<ClassificaEndereco> recuperarClassificacoesAtivas(GenericoDAO dao, int codigoFilial) {

		return dao.list(ClassificaEndereco.class, "cle_ativo = 1 and cle_filcodigo = " + codigoFilial, null,
				"descricao", null);
	}
}
