/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Renato
 */
@Entity
@Table(name = "campanhapto")
@XmlRootElement
public class CampanhaPonto implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected CampanhaPontoPK campanhaPontoPK;

	@Column(name = "cpt_ponto")
	private double ponto;

	@Column(name = "cpt_obs")
	private String observacao;

	@Column(name = "cpt_status")
	private String status;

	@Column(name = "cpt_filcodigo")
	private int filialCodigo;

	public CampanhaPonto() {
	}

	public CampanhaPonto(CampanhaPontoPK campanhaptoPK) {
		this.campanhaPontoPK = campanhaptoPK;
	}

	public double getPonto() {
		return ponto;
	}

	public void setPonto(double ponto) {
		this.ponto = ponto;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public CampanhaPontoPK getCampanhaPontoPK() {
		return campanhaPontoPK;
	}

	public void setCampanhaPontoPK(CampanhaPontoPK campanhaPontoPK) {
		this.campanhaPontoPK = campanhaPontoPK;
	}
}
