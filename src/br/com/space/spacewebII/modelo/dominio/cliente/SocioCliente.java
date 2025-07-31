/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.cliente;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "sociocli")
@XmlRootElement
public class SocioCliente implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected SocioClientePK sociocliPK;

	@Column(name = "scl_nome")
	private String nome;

	@Column(name = "scl_cpf")
	private String cpf;

	@Column(name = "scl_datanasc")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "scl_participa")
	private Double participacao;

	public SocioCliente() {
	}

	public SocioClientePK getSociocliPK() {
		return sociocliPK;
	}

	public void setSociocliPK(SocioClientePK sociocliPK) {
		this.sociocliPK = sociocliPK;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Double getParticipacao() {
		return participacao;
	}

	public void setParticipacao(Double participacao) {
		this.participacao = participacao;
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
