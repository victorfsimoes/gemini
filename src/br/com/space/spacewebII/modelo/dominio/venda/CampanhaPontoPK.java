/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Renato
 */
@Embeddable
public class CampanhaPontoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "cpt_cmpcodigo")
	private int Campanhacodigo;

	@Column(name = "cpt_clbcodigo")
	private int colaboradorCodigo;

	@Basic(optional = false)
	@Column(name = "cpt_tipo")
	private String tipo;

	@Basic(optional = false)
	@Column(name = "cpt_mes")
	private int mes;

	@Basic(optional = false)
	@Column(name = "cpt_ano")
	private int ano;

	public CampanhaPontoPK() {
	}

	public int getCampanhacodigo() {
		return Campanhacodigo;
	}

	public void setCampanhacodigo(int campanhacodigo) {
		Campanhacodigo = campanhacodigo;
	}

	public int getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(int colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public int getAno() {
		return ano;
	}

	public void setAno(int ano) {
		this.ano = ano;
	}
}
