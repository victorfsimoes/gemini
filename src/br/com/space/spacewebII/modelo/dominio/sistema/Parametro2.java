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
 * @author Ronie
 */
@Entity
@Table(name = "parametro2")
@XmlRootElement
public class Parametro2 implements IPersistent, Serializable {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "pa2_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private int filialCodigo;

	@Column(name = "pa2_contsldlce")
	private int flagControlaSaldoEstoqueLocalLote;

	@Column(name = "pa2_regespecial")
	private int flagRegimeEspecial;

	@Column(name = "pa2_recredpres")
	private double creditoPresumidoRegimeEspecial;

	@Column(name = "pa2_reredutbas")
	private double reducaoBaseRegimeEspecial;

	@Column(name = "pa2_verpedcli")
	private int flagVerificaExixtenciaPedidoDia = 0;

	@Column(name = "pa2_vultpreco")
	private int flagVisualizaUltimoPrecoNegociado = 0;

	public Parametro2() {
	}

	public Parametro2(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFlagControlaSaldoEstoqueLocalLote() {
		return flagControlaSaldoEstoqueLocalLote;
	}

	public void setFlagControlaSaldoEstoqueLocalLote(
			int flagControlaSaldoEstoqueLocalLote) {
		this.flagControlaSaldoEstoqueLocalLote = flagControlaSaldoEstoqueLocalLote;
	}

	public int getFlagRegimeEspecial() {
		return flagRegimeEspecial;
	}

	public void setFlagRegimeEspecial(int flagRegimeEspecial) {
		this.flagRegimeEspecial = flagRegimeEspecial;
	}

	public double getCreditoPresumidoRegimeEspecial() {
		return creditoPresumidoRegimeEspecial;
	}

	public void setCreditoPresumidoRegimeEspecial(
			double creditoPresumidoRegimeEspecial) {
		this.creditoPresumidoRegimeEspecial = creditoPresumidoRegimeEspecial;
	}

	public double getReducaoBaseRegimeEspecial() {
		return reducaoBaseRegimeEspecial;
	}

	public void setReducaoBaseRegimeEspecial(double reducaoBaseRegimeEspecial) {
		this.reducaoBaseRegimeEspecial = reducaoBaseRegimeEspecial;
	}

	public boolean isVerificaExixtenciaPedidoDia() {
		return flagVerificaExixtenciaPedidoDia == 1;
	}

	public int getFlagVerificaExixtenciaPedidoDia() {
		return flagVerificaExixtenciaPedidoDia;
	}

	public void setFlagVerificaExixtenciaPedidoDia(
			int flagVerificaExixtenciaPedidoDia) {
		this.flagVerificaExixtenciaPedidoDia = flagVerificaExixtenciaPedidoDia;
	}

	public boolean isVisualizaUltimoPrecoNegociado() {
		return flagVisualizaUltimoPrecoNegociado == 1;
	}

	public int getFlagVisualizaUltimoPrecoNegociado() {
		return flagVisualizaUltimoPrecoNegociado;
	}

	public void setFlagVisualizaUltimoPrecoNegociado(
			int flagVisualizaUltimoPrecoNegociado) {
		this.flagVisualizaUltimoPrecoNegociado = flagVisualizaUltimoPrecoNegociado;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static Parametro2 recuperarUnico(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(Parametro2.class, COLUNA_FILIAL + " = "
				+ codigo, null);
	}
	
	
}
