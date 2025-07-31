/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.endereco;

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
@Table(name = "uf")
@XmlRootElement
public class Uf implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_SIGLA = "uf_sigla";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_SIGLA)
	private String sigla;

	@Column(name = "uf_desc")
	private String descricao;

	@Column(name = "uf_aliqicms")
	private Double aliquotaIcms;

	@Column(name = "uf_aliqcalc")
	private Double aliquotaCalculoST;

	@Column(name = "uf_icmsvenda")
	private Double aliquotaIcmsVenda;

	@Column(name = "uf_nentrega")
	private int flagNaoEntrega;

	public Uf() {
	}

	public Uf(String ufSigla) {
		this.sigla = ufSigla;
	}

	public String getSigla() {
		return sigla;
	}

	public void setSigla(String sigla) {
		this.sigla = sigla;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getAliquotaIcms() {
		return aliquotaIcms;
	}

	public void setAliquotaIcms(Double aliquotaIcms) {
		this.aliquotaIcms = aliquotaIcms;
	}

	public Double getAliquotaCalculoST() {
		return aliquotaCalculoST;
	}

	public void setAliquotaCalculoST(Double aliquotaCalculoST) {
		this.aliquotaCalculoST = aliquotaCalculoST;
	}

	public Double getAliquotaIcmsVenda() {
		return aliquotaIcmsVenda;
	}

	public void setAliquotaIcmsVenda(Double aliquotaIcmsVenda) {
		this.aliquotaIcmsVenda = aliquotaIcmsVenda;
	}

	public int getFlagNaoEntrega() {
		return flagNaoEntrega;
	}

	public void setFlagNaoEntrega(int flagNaoEntrega) {
		this.flagNaoEntrega = flagNaoEntrega;
	}

	/**
	 * 
	 * @param dao
	 * @param ufSigla
	 * @return
	 */
	public static Uf recuperarUnico(GenericoDAO dao, String ufSigla) {
		String where = COLUNA_SIGLA + " = '" + ufSigla + "'";
		return dao.uniqueResult(Uf.class, where, null);
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
