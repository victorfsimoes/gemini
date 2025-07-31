/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Renato
 */
@Entity
@Table(name = "campanhaitemper")
@XmlRootElement
public class CampanhaItemPeriodo implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected CampanhaItemPeriodoPK campanhaitemperPK;

	@Column(name = "cmd_datafim")
	@Temporal(TemporalType.DATE)
	private Date dataFim;

	@Column(name = "cmd_pontos")
	private double pontos;

	@ManyToOne(optional = false)
	@JoinColumns({
			@JoinColumn(name = "cmd_cmpcodigo", referencedColumnName = "CMI_CMPCODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "cmd_filcodigo", referencedColumnName = "CMI_FILCODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "cmd_item", referencedColumnName = "CMI_ITEM", insertable = false, updatable = false) })
	private CampanhaItem campanhaItem;

	@JoinColumns({
			@JoinColumn(name = "cmd_cmpcodigo", referencedColumnName = "CMP_CODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "cmd_filcodigo", referencedColumnName = "CMP_FILCODIGO", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private Campanha campanha;

	public CampanhaItemPeriodo() {
	}

	public CampanhaItemPeriodoPK getCampanhaitemperPK() {
		return campanhaitemperPK;
	}

	public void setCampanhaitemperPK(CampanhaItemPeriodoPK campanhaitemperPK) {
		this.campanhaitemperPK = campanhaitemperPK;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public double getPontos() {
		return pontos;
	}

	public void setPontos(double pontos) {
		this.pontos = pontos;
	}

	public CampanhaItem getCampanhaItem() {
		return campanhaItem;
	}

	public void setCampanhaItem(CampanhaItem campanhaItem) {
		this.campanhaItem = campanhaItem;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

}
