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

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "grupocomisit")
@XmlRootElement
public class GrupoComissaoItem implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected GrupoComissaoItemPK grupoComissaoItemPK;

	@Basic(optional = false)
	@Column(name = "gci_tipo")
	private String tipo;

	@Basic(optional = false)
	@Column(name = "gci_faixaini")
	private double faixaInicial;

	@Basic(optional = false)
	@Column(name = "gci_faixafim")
	private double faixaFinal;

	@Basic(optional = false)
	@Column(name = "gci_percomis1")
	private double percentualComissao1;

	@Basic(optional = false)
	@Column(name = "gci_percomis2")
	private double percentualComissao2;

	@Basic(optional = false)
	@Column(name = "gci_percomis3")
	private double percentualComissao3;

	@Basic(optional = false)
	@Column(name = "gci_percomis4")
	private double percentualComissao4;

	@Basic(optional = false)
	@Column(name = "gci_percomis5")
	private double percentualComissao5;

	public GrupoComissaoItem() {
	}

	public GrupoComissaoItemPK getGrupocomisitPK() {
		return grupoComissaoItemPK;
	}

	public void setGrupoComissaoItemPK(GrupoComissaoItemPK grupocomissaoitemPK) {
		this.grupoComissaoItemPK = grupocomissaoitemPK;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getFaixaInicial() {
		return faixaInicial;
	}

	public void setFaixaInicial(double faixaInicial) {
		this.faixaInicial = faixaInicial;
	}

	public double getFaixaFinal() {
		return faixaFinal;
	}

	public void setFaixaFinal(double faixaFinal) {
		this.faixaFinal = faixaFinal;
	}

	public double getPercentualComissao1() {
		return percentualComissao1;
	}

	public void setPercentualComissao1(double percentualComissao1) {
		this.percentualComissao1 = percentualComissao1;
	}

	public double getPercentualComissao2() {
		return percentualComissao2;
	}

	public void setPercentualComissao2(double percentualComissao2) {
		this.percentualComissao2 = percentualComissao2;
	}

	public double getPercentualComissao3() {
		return percentualComissao3;
	}

	public void setPercentualComissao3(double percentualComissao3) {
		this.percentualComissao3 = percentualComissao3;
	}

	public double getPercentualComissao4() {
		return percentualComissao4;
	}

	public void setPercentualComissao4(double percentualComissao4) {
		this.percentualComissao4 = percentualComissao4;
	}

	public double getPercentualComissao5() {
		return percentualComissao5;
	}

	public void setPercentualComissao5(double percentualComissao5) {
		this.percentualComissao5 = percentualComissao5;
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
