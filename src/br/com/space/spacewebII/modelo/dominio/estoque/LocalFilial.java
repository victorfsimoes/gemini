/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "localfilial")
@XmlRootElement
public class LocalFilial implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "lcf_filcodigo";
	private static final String COLUNA_LOCAL = "lcf_lcecodigo";

	@EmbeddedId
	protected LocalFilialPK localFilialPK;

	@Column(name = "lcf_intext")
	private String flagInternoExterno;

	@Column(name = "lcf_permiteven")
	private Integer flagPermiteVenda;

	@Column(name = "lcf_requisicao")
	private Integer flagRequisicao;

	@Column(name = "lcf_estnegativo")
	private Integer flagEstoqueNegativo;

	@Column(name = "lcf_seqbaixaest")
	private Integer SequenciaBaixaEstoque;

	@Column(name = "lcf_bloqentrada")
	private Integer flagBlqueiaEntrada;

	public LocalFilial() {
	}

	public LocalFilial(LocalFilialPK localFilialPK) {
		this.localFilialPK = localFilialPK;
	}

	public LocalFilial(int lcfLcecodigo, int lcfFilcodigo) {
		this.localFilialPK = new LocalFilialPK(lcfLcecodigo, lcfFilcodigo);
	}

	public LocalFilial(int local, int filial, String flagInternoExterno,
			Integer flagPermiteVenda, Integer flagRequisicao,
			Integer flagEstoqueNegativo, Integer sequenciaBaixaEstoque,
			Integer flagBlqueiaEntrada) {
		super();
		this.localFilialPK = new LocalFilialPK(local, filial);
		this.flagInternoExterno = flagInternoExterno;
		this.flagPermiteVenda = flagPermiteVenda;
		this.flagRequisicao = flagRequisicao;
		this.flagEstoqueNegativo = flagEstoqueNegativo;
		SequenciaBaixaEstoque = sequenciaBaixaEstoque;
		this.flagBlqueiaEntrada = flagBlqueiaEntrada;
	}

	public LocalFilialPK getLocalFilialPK() {
		return localFilialPK;
	}

	public void setLocalFilialPK(LocalFilialPK localFilialPK) {
		this.localFilialPK = localFilialPK;
	}

	public String getFlagInternoExterno() {
		return flagInternoExterno;
	}

	public void setFlagInternoExterno(String flagInternoExterno) {
		this.flagInternoExterno = flagInternoExterno;
	}

	public Integer getFlagPermiteVenda() {
		return flagPermiteVenda;
	}

	public void setFlagPermiteVenda(Integer flagPermiteVenda) {
		this.flagPermiteVenda = flagPermiteVenda;
	}

	public Integer getFlagRequisicao() {
		return flagRequisicao;
	}

	public void setFlagRequisicao(Integer flagRequisicao) {
		this.flagRequisicao = flagRequisicao;
	}

	public Integer getFlagEstoqueNegativo() {
		return flagEstoqueNegativo;
	}

	public void setFlagEstoqueNegativo(Integer flagEstoqueNegativo) {
		this.flagEstoqueNegativo = flagEstoqueNegativo;
	}

	public Integer getSequenciaBaixaEstoque() {
		return SequenciaBaixaEstoque;
	}

	public void setSequenciaBaixaEstoque(Integer sequenciaBaixaEstoque) {
		SequenciaBaixaEstoque = sequenciaBaixaEstoque;
	}

	public Integer getFlagBlqueiaEntrada() {
		return flagBlqueiaEntrada;
	}

	public void setFlagBlqueiaEntrada(Integer flagBlqueiaEntrada) {
		this.flagBlqueiaEntrada = flagBlqueiaEntrada;
	}

	public int getLocalEstoqueCodigo() {
		return this.getLocalFilialPK().getLocalEstoqueCodigo();
	}

	public void setLocalEstoqueCodigo(int localEstoqueCodigo) {
		this.getLocalFilialPK().setLocalEstoqueCodigo(localEstoqueCodigo);
	}

	public int getFilialCodigo() {
		return this.getLocalFilialPK().getFilialCodigo();
	}

	public void setFilialCodigo(int filialCodigo) {
		this.getLocalFilialPK().setFilialCodigo(filialCodigo);
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

	public static LocalFilial recuperarUnico(GenericoDAO dao, int localCodigo,
			int filialCodigo) {

		return dao.uniqueResult(LocalFilial.class,
				getWhere(localCodigo, filialCodigo), null);
	}

	public static long count(GenericoDAO dao, int localCodigo, int filialCodigo) {
		return dao.count("localfilial", getWhere(localCodigo, filialCodigo));
	}

	private static String getWhere(int localCodigo, int filialCodigo) {

		return LocalFilial.COLUNA_FILIAL + " = " + filialCodigo + " AND "
				+ LocalFilial.COLUNA_LOCAL + " = " + localCodigo;
	}
}
