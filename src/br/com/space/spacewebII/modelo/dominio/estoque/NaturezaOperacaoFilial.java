/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.INaturezaOperacaoFilial;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "natoperfilial")
@XmlRootElement
public class NaturezaOperacaoFilial implements Serializable, IPersistent,
		INaturezaOperacaoFilial {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_FILIAL = "nof_filcodigo";
	public static final String COLUNA_NATUREZA_CODIGO = "nof_natcodigo";

	@EmbeddedId
	protected NaturezaOperacaoFilialPK naturezaOperacaoFilialPK;

	@Column(name = "nof_inativo")
	private Integer flagInativo;

	@Column(name = "nof_tprcodigo")
	private int tabelaPrecoCodigo;

	@Column(name = "nof_envpalm")
	private Integer flagEnviaPalm;
	
	@Column(name = "nof_lcecodigos")
	private Integer localEstoquePadraoSaida;

	@Column(name = "nof_tabclipri")
	private int flagTabelaClientePrioritaria = 0;

	@JoinColumn(name = "nof_natcodigo", referencedColumnName = "nat_codigo", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private NaturezaOperacao naturezaOperacao;

	public NaturezaOperacaoFilial() {

	}

	public NaturezaOperacaoFilialPK getNaturezaOperacaoFilialPK() {
		return naturezaOperacaoFilialPK;
	}

	public void setNaturezaOperacaoFilialPK(
			NaturezaOperacaoFilialPK naturezaOperacaoFilialPK) {
		this.naturezaOperacaoFilialPK = naturezaOperacaoFilialPK;
	}

	public Integer getFlagInativo() {
		return flagInativo;
	}

	public void setFlagInativo(Integer flagInativo) {
		this.flagInativo = flagInativo;
	}

	public int getTabelaPrecoCodigo() {
		return tabelaPrecoCodigo;
	}

	public void setTabelaPrecoCodigo(int tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	public Integer getFlagEnviaPalm() {
		return flagEnviaPalm;
	}

	public void setFlagEnviaPalm(Integer flagEnviaPalm) {
		this.flagEnviaPalm = flagEnviaPalm;
	}

	public Integer getLocalEstoquePadraoSaida() {
		return localEstoquePadraoSaida;
	}

	public void setLocalEstoquePadraoSaida(Integer localEstoquePadraoSaida) {
		this.localEstoquePadraoSaida = localEstoquePadraoSaida;
	}

	@Override
	public int getFlagTabelaClientePrioritaria() {
		return flagTabelaClientePrioritaria;
	}

	public void setFlagTabelaClientePrioritaria(int flagTabelaClientePrioritaria) {
		this.flagTabelaClientePrioritaria = flagTabelaClientePrioritaria;
	}

	@Override
	public boolean isTabelaClientePrioritaria() {
		return flagTabelaClientePrioritaria == 1;
	}

	public NaturezaOperacao getNaturezaOperacao() {
		return naturezaOperacao;
	}

	public void setNaturezaOperacao(NaturezaOperacao naturezaOperacao) {
		this.naturezaOperacao = naturezaOperacao;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
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

	/**
	 * 
	 * @param dao
	 * @param naturezaOperacaoCodigo
	 * @return
	 */
	public static NaturezaOperacaoFilial recuperarUnico(GenericoDAO dao,
			String naturezaOperacaoCodigo, int filialcodigo) {

		return dao.uniqueResult(NaturezaOperacaoFilial.class,
				COLUNA_NATUREZA_CODIGO + " = '" + naturezaOperacaoCodigo
						+ "' and " + COLUNA_FILIAL + "=" + filialcodigo, null);
	}

}
