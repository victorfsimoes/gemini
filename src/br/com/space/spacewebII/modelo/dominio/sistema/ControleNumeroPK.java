/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Ronie
 */
@Embeddable
public class ControleNumeroPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "ctn_tabela")
	private String tabela;

	@Basic(optional = false)
	@Column(name = "ctn_filial")
	private int filialCodigo;

	public ControleNumeroPK() {
	}

	public ControleNumeroPK(String ctnTabela, int ctnFilial) {
		this.tabela = ctnTabela;
		this.filialCodigo = ctnFilial;
	}

	public String getTabela() {
		return tabela;
	}

	public void setTabela(String tabela) {
		this.tabela = tabela;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
