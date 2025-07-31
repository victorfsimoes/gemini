/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "diccampos")
public class DicionarioCampos implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_TABELA = "DCP_DTBTAB";
	public static final String COLUNA_FLAGEXIBIDOGRID = "DCP_GRID";
	public static final String COLUNA_FLAGCONSULTA = "DCP_CONSULTA";
	public static final String COLUNA_CAMPO = "DCP_CAMPO";

	// public static final String COLUNA_POSICAOGRID = "DCP_POSICAOGRID";
	// public static final String COLUNA_ORDENAGRID = "DCP_ORDPADRAO";

	// public static final String VAR_ORDENAGRID = "ordenaGrid";

	@EmbeddedId
	protected DicionarioCamposPK dicCamposPK;

	@JoinColumn(name = "DCP_DTBTAB", referencedColumnName = "DTB_TABELA", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private DicionarioTabelas dictabelas;

	@Column(name = "DCP_DESC")
	private String tituloCampo;

	@Column(name = COLUNA_FLAGEXIBIDOGRID)
	private Integer flagExibidoNoGrid;

	@Column(name = COLUNA_FLAGCONSULTA)
	private Integer flagConsulta;

	@Column(name = "DCP_POSICAOGRID")
	private Integer posicaoGrid;

	@Column(name = "DCP_TAMGRID")
	private Integer tamanhoGrid;

	@Column(name = "DCP_ORDPADRAO")
	private Integer ordemPadraoGrid;

	@Column(name = "DCP_DTBTABPAI")
	private String tabelaPai;

	@Column(name = "DCP_CHAVEPAI")
	private String chavePai;

	@Column(name = "DCP_CHAVEFILHO")
	private String chaveFilho;

	@Column(name = "DCP_TIPO")
	private String tipoCampo;

	@Column(name = "dcp_picgrid")
	private String mascara;

	@Transient
	private boolean flagIndice;

	public DicionarioCampos() {
	}

	public DicionarioCampos(DicionarioCamposPK diccamposPK) {
		this.dicCamposPK = diccamposPK;
	}

	public DicionarioCampos(String dcpCampo, String dcpDtbtab) {
		this.dicCamposPK = new DicionarioCamposPK(dcpCampo, dcpDtbtab);
	}

	public DicionarioCamposPK getDicCamposPK() {
		return dicCamposPK;
	}

	public void setDicCamposPK(DicionarioCamposPK diccamposPK) {
		this.dicCamposPK = diccamposPK;
	}

	public String getTituloCampo() {
		return tituloCampo;
	}

	public void setTituloCampo(String tituloCampo) {
		this.tituloCampo = tituloCampo;
	}

	public Integer getFlagExibidoNoGrid() {
		return flagExibidoNoGrid;
	}

	public void setFlagExibidoNoGrid(Integer flagExibidoNoGrid) {
		this.flagExibidoNoGrid = flagExibidoNoGrid;
	}

	public Integer getPosicaoGrid() {
		return posicaoGrid;
	}

	public void setposicaoGrid(Integer posicaoGrid) {
		this.posicaoGrid = posicaoGrid;
	}

	public Integer getTamanhoGrid() {
		return tamanhoGrid;
	}

	public void setTamanhoGrid(Integer tamanhoGrid) {
		this.tamanhoGrid = tamanhoGrid;
	}

	public Integer getFlagConsulta() {
		return flagConsulta;
	}

	public void setFlagConsulta(Integer flagConsulta) {
		this.flagConsulta = flagConsulta;
	}

	public Integer getOrdemPadraoGrid() {
		return ordemPadraoGrid;
	}

	public void setordemPadraoGrid(Integer ordemPadraoGrid) {
		this.ordemPadraoGrid = ordemPadraoGrid;
	}

	public String getTabelaPai() {
		return tabelaPai;
	}

	public void setTabelaPai(String tabelaPai) {
		this.tabelaPai = tabelaPai;
	}

	public String getChavePai() {
		return chavePai;
	}

	public void setChavePai(String chavePai) {
		this.chavePai = chavePai;
	}

	public String getChaveFilho() {
		return chaveFilho;
	}

	public void setChaveFilho(String chaveFilho) {
		this.chaveFilho = chaveFilho;
	}

	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}
	
	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	public DicionarioTabelas getDictabelas() {
		return dictabelas;
	}

	public void setDictabelas(DicionarioTabelas dictabelas) {
		this.dictabelas = dictabelas;
	}

	public boolean isFlagIndice() {
		return flagIndice;
	}

	public void setFlagIndice(boolean flagIndice) {
		this.flagIndice = flagIndice;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {

	}

	@Override
	public DicionarioCampos clone() throws CloneNotSupportedException {
		return (DicionarioCampos) super.clone();
	}

	/**
	 * 
	 * @param dao
	 * @param nomeTabela
	 * @return
	 */
	public static List<DicionarioCampos> recuperaCamposGridConsulta(GenericoDAO dao, String nomeTabela) {
		String where = COLUNA_TABELA + " like '" + nomeTabela + "' AND ( " + COLUNA_FLAGCONSULTA + " = 1 OR "
				+ COLUNA_FLAGEXIBIDOGRID + " = 1 )";

		return dao.list(DicionarioCampos.class, where, null, "posicaoGrid", null);
	}

	/**
	 * 
	 * @param dao
	 * @param nomeTabela
	 * @return
	 */
	public static List<DicionarioCampos> recuperaCamposIndice(GenericoDAO dao, String nomeTabela) {

		String sql = "select diccampos.* " + " from diccampos inner join dicindices"
				+ " on dcp_campo = did_dcpcampo and dcp_dtbtab = did_dtbtab" + " where did_primary = 1 and "
				+ COLUNA_TABELA + " = '" + nomeTabela + "' order by did_ordem";

		return dao.list(DicionarioCampos.class, sql);
	}

	/**
	 * 
	 * @param dao
	 * @param tabela
	 * @param campo
	 * @return
	 */
	public static DicionarioCampos recuperarCampoTabela(GenericoDAO dao, String tabela, String campo) {

		return dao.uniqueResult(DicionarioCampos.class,
				COLUNA_CAMPO + " = '" + campo + "' and " + COLUNA_TABELA + " = '" + tabela + "'", null);
	}

	/**
	 * 
	 * @param campos
	 * @param indices
	 */
	public static void atualizarIndices(List<DicionarioCampos> campos, List<DicionarioCampos> indices) {

		for (DicionarioCampos indice : indices) {
			indice.setFlagIndice(true);
			boolean achou = false;

			for (DicionarioCampos campo : campos) {
				achou = (campo.getDicCamposPK().getNomeCampo().equals(indice.getDicCamposPK().getNomeCampo()));
				if (achou) {
					campo.setFlagIndice(true);
					break;
				}
			}
			if (!achou) {
				campos.add(indice);
			}
		}

	}

}
