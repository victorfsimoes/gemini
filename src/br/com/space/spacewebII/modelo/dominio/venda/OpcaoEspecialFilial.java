/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IOpcaoEspecialFilial;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "opcaoespfilial")
@XmlRootElement
public class OpcaoEspecialFilial implements Serializable, IOpcaoEspecialFilial {
	private static final long serialVersionUID = 1L;
	private static final String COLUNA_CODIGO = "oef_oepcodigo";
	private static final String COLUNA_FILIAL = "oef_filcodigo";

	@EmbeddedId
	protected OpcaoEspecialFilialPK opcaoEspecialFilialPK;

	@Basic(optional = false)
	@Column(name = "oef_inativo")
	private int flagInativo;

	@Basic(optional = false)
	@Column(name = "oef_indice")
	private double indice;

	@Basic(optional = false)
	@Column(name = "oef_indiceof")
	private double indiceOferta;

	@Basic(optional = false)
	@Column(name = "oef_nperdeb")
	private int flagNaoPermiteDebito;

	@Basic(optional = false)
	@Column(name = "oef_npercre")
	private int flagNaoPermiteCredito;

	public OpcaoEspecialFilial() {
	}

	public OpcaoEspecialFilial(OpcaoEspecialFilialPK opcaoEspecialFilialPK) {
		this.opcaoEspecialFilialPK = opcaoEspecialFilialPK;
	}

	public OpcaoEspecialFilial(OpcaoEspecialFilialPK opcaoEspecialFilialPK,
			int oefInativo, double oefIndice, double oefIndiceof,
			int oefNperdeb, int oefNpercre) {
		this.opcaoEspecialFilialPK = opcaoEspecialFilialPK;
		this.flagInativo = oefInativo;
		this.indice = oefIndice;
		this.indiceOferta = oefIndiceof;
		this.flagNaoPermiteDebito = oefNperdeb;
		this.flagNaoPermiteCredito = oefNpercre;
	}

	public OpcaoEspecialFilialPK getOpcaoEspecialFilialPK() {
		return opcaoEspecialFilialPK;
	}

	public void setOpcaoEspecialFilialPK(
			OpcaoEspecialFilialPK opcaoEspecialFilialPK) {
		this.opcaoEspecialFilialPK = opcaoEspecialFilialPK;
	}

	public boolean isInativo() {
		return flagInativo == 1;
	}
	
	public int getFlagInativo() {
		return flagInativo;
	}

	public void setFlagInativo(int flagInativo) {
		this.flagInativo = flagInativo;
	}

	public double getIndice() {
		return indice;
	}

	public void setIndice(double indice) {
		this.indice = indice;
	}

	public double getIndiceOferta() {
		return indiceOferta;
	}

	public void setIndiceOferta(double indiceOferta) {
		this.indiceOferta = indiceOferta;
	}

	public int getFlagNaoPermiteDebito() {
		return flagNaoPermiteDebito;
	}

	public void setFlagNaoPermiteDebito(int flagNaoPermiteDebito) {
		this.flagNaoPermiteDebito = flagNaoPermiteDebito;
	}

	public int getFlagNaoPermiteCredito() {
		return flagNaoPermiteCredito;
	}

	public void setFlagNaoPermiteCredito(int flagNaoPermiteCredito) {
		this.flagNaoPermiteCredito = flagNaoPermiteCredito;
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
	public int getNumeroOpcaoEspecial() {
		// TODO Auto-generated method stub
		return this.getOpcaoEspecialFilialPK().getOpcaoEspecialCodigo();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public static OpcaoEspecialFilial recuperarUnico(GenericoDAO dao,
			int codigo, int filialCodigo) {
		return dao.uniqueResult(OpcaoEspecialFilial.class, COLUNA_CODIGO
				+ " = " + codigo + " AND " + COLUNA_FILIAL + " = "
				+ filialCodigo, null);
	}
}
