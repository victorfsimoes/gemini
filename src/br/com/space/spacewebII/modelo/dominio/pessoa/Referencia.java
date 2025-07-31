/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "referencia")
@XmlRootElement
public class Referencia implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected ReferenciaPK referenciaPK;

	@Column(name = "ref_tipo")
	private String tipo;

	@Column(name = "ref_nome")
	private String nome;

	@Column(name = "ref_endereco")
	private String endereco;

	@Column(name = "ref_fone")
	private String fone;

	@Column(name = "ref_celular")
	private String celular;

	@Lob
	@Column(name = "ref_informa")
	private String informa;

	@Column(name = "ref_filcodigo")
	private Integer filialCodigo;

	public Referencia() {
	}

	public ReferenciaPK getReferenciaPK() {
		return referenciaPK;
	}

	public void setReferenciaPK(ReferenciaPK referenciaPK) {
		this.referenciaPK = referenciaPK;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getFone() {
		return fone;
	}

	public void setFone(String fone) {
		this.fone = fone;
	}

	public String getCelular() {
		return celular;
	}

	public void setCelular(String celular) {
		this.celular = celular;
	}

	public String getInforma() {
		return informa;
	}

	public void setInforma(String informa) {
		this.informa = informa;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
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
