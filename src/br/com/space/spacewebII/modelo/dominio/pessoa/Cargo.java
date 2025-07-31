/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Formula;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "cargo")
@XmlRootElement
public class Cargo implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;
	@Id
	@Basic(optional = false)
	@Column(name = "crg_codigo")
	private int codigo;

	@Column(name = "crg_desc")
	private String descricao;

	@Column(name = "crg_ativo")
	private int flagAtivo = 0;

	@Column(name = "crg_comprador")
	@Formula("coalesce(crg_comprador, 0)")
	private int flagComprador = 0;

	@Column(name = "crg_supvenda")
	@Formula("coalesce(crg_supvenda, 0)")
	private int flagSupervisorVenda;

	@Column(name = "crg_gervenda")
	@Formula("coalesce(crg_gervenda, 0)")
	private int flagGerenteVenda;

	@Column(name = "crg_valorhora")
	private Double valorHora;

	@Column(name = "crg_conferente")
	private int flagConferente;

	@Column(name = "crg_separador")
	private int flagSeparador;

	@Column(name = "crg_descarga")
	private int flagDescarga;

	@Column(name = "crg_cargueiro")
	private int flagCargueiro;

	@Column(name = "crg_filcodigo")
	private Integer filialCodigo;

	public Cargo() {
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

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFlagComprador() {
		return flagComprador;
	}

	public void setFlagComprador(int flagComprador) {
		this.flagComprador = flagComprador;
	}

	public int getFlagSupervisorVenda() {
		return flagSupervisorVenda;
	}

	public void setFlagSupervisorVenda(int flagSupervisorVenda) {
		this.flagSupervisorVenda = flagSupervisorVenda;
	}

	public int getFlagGerenteVenda() {
		return flagGerenteVenda;
	}

	public void setFlagGerenteVenda(int flagGerenteVenda) {
		this.flagGerenteVenda = flagGerenteVenda;
	}

	public Double getValorHora() {
		return valorHora;
	}

	public void setValorHora(Double valorHora) {
		this.valorHora = valorHora;
	}

	public int getFlagConferente() {
		return flagConferente;
	}

	public void setFlagConferente(int flagConferente) {
		this.flagConferente = flagConferente;
	}

	public int getFlagSeparador() {
		return flagSeparador;
	}

	public void setFlagSeparador(int flagSeparador) {
		this.flagSeparador = flagSeparador;
	}

	public int getFlagDescarga() {
		return flagDescarga;
	}

	public void setFlagDescarga(int flagDescarga) {
		this.flagDescarga = flagDescarga;
	}

	public int getFlagCargueiro() {
		return flagCargueiro;
	}

	public void setFlagCargueiro(int flagCargueiro) {
		this.flagCargueiro = flagCargueiro;
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
