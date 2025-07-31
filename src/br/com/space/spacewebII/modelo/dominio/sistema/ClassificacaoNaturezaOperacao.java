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

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "classificanatop")
@XmlRootElement
public class ClassificacaoNaturezaOperacao implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "cnt_codigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Column(name = "cnt_desc")
	private String descricao;

	@Column(name = "cnt_ativo")
	private Integer flagAtivo;

	@Column(name = "cnt_tiponatop")
	private String tipoNaturezaOperacao;

	@Column(name = "cnt_devolucao")
	private Integer flagDevolucao;

	@Column(name = "cnt_devolnf")
	private Integer flagDevolucaoFiscal;

	public ClassificacaoNaturezaOperacao() {
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

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getTipoNaturezaOperacao() {
		return tipoNaturezaOperacao;
	}

	public void setTipoNaturezaOperacao(String tipoNaturezaOperacao) {
		this.tipoNaturezaOperacao = tipoNaturezaOperacao;
	}

	public Integer getFlagDevolucao() {
		return flagDevolucao;
	}

	public void setFlagDevolucao(Integer flagDevolucao) {
		this.flagDevolucao = flagDevolucao;
	}

	public Integer getFlagDevolucaoFiscal() {
		return flagDevolucaoFiscal;
	}

	public void setFlagDevolucaoFiscal(Integer flagDevolucaoFiscal) {
		this.flagDevolucaoFiscal = flagDevolucaoFiscal;
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
