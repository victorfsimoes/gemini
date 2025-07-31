/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "seriepedidos")
@XmlRootElement
public class SeriePedido implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELA = "seriepedidos";

	public static final String COLUNA_CODIGO = "spv_codigo";
	public static final String COLUNA_CONTROLESERIEPEDIDO = "spv_cspcodigo";
	public static final String COLUNA_FILIAL = "spv_filcodigo";
	public static final String COLUNA_ATIVO = "spv_ativo";
	public static final String COLUNA_NUMEROATUAL = "spv_numatual";

	@EmbeddedId
	protected SeriePedidoPK seriePedidosPK;

	@Basic(optional = false)
	@Column(name = "spv_desc")
	private String descricao;

	@Column(name = "spv_dataserie")
	@Temporal(TemporalType.DATE)
	private Date dataSerie;

	@Column(name = "spv_numatual")
	private Integer numeroAtual;

	@Column(name = COLUNA_ATIVO)
	private Integer flagAtivo;

	@Column(name = "spv_ultutiliz")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaUtilizacao;

	@Column(name = "spv_cspcodigo")
	private Integer controleSeriePedidoCodigo;

	public SeriePedido() {
	}

	public SeriePedido(SeriePedidoPK seriePedidosPK) {
		this.seriePedidosPK = seriePedidosPK;
	}

	public SeriePedido(SeriePedidoPK seriePedidosPK, String spvDesc) {
		this.seriePedidosPK = seriePedidosPK;
		this.descricao = spvDesc;
	}

	public SeriePedido(String spvCodigo, int spvFilCodigo) {
		this.seriePedidosPK = new SeriePedidoPK(spvCodigo, spvFilCodigo);
	}

	/**
	 * 
	 * @param spvCodigo
	 * @param spvFilCodigo
	 * @param descricao
	 * @param dataSerie
	 * @param numeroAtual
	 * @param flagAtivo
	 * @param dataUltimaUtilizacao
	 * @param controleSeriePedidoCodigo
	 */
	public SeriePedido(String spvCodigo, int spvFilCodigo, String descricao,
			Date dataSerie, Integer numeroAtual, Integer flagAtivo,
			Date dataUltimaUtilizacao, Integer controleSeriePedidoCodigo) {
		super();
		this.seriePedidosPK = new SeriePedidoPK(spvCodigo, spvFilCodigo);
		this.descricao = descricao;
		this.dataSerie = dataSerie;
		this.numeroAtual = numeroAtual;
		this.flagAtivo = flagAtivo;
		this.dataUltimaUtilizacao = dataUltimaUtilizacao;
		this.controleSeriePedidoCodigo = controleSeriePedidoCodigo;
	}

	public SeriePedidoPK getSeriePedidosPK() {
		return seriePedidosPK;
	}

	public void setSeriePedidosPK(SeriePedidoPK seriePedidosPK) {
		this.seriePedidosPK = seriePedidosPK;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Date getDataSerie() {
		return dataSerie;
	}

	public void setDataSerie(Date dataSerie) {
		this.dataSerie = dataSerie;
	}

	public Integer getNumeroAtual() {
		return numeroAtual;
	}

	public void setNumeroAtual(Integer numeroAtual) {
		this.numeroAtual = numeroAtual;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Date getDataUltimaUtilizacao() {
		return dataUltimaUtilizacao;
	}

	public void setDataUltimaUtilizacao(Date dataUltimaUtilizacao) {
		this.dataUltimaUtilizacao = dataUltimaUtilizacao;
	}

	public Integer getControleSeriePedidoCodigo() {
		return controleSeriePedidoCodigo;
	}

	public void setControleSeriePedidoCodigo(Integer controleSeriePedidoCodigo) {
		this.controleSeriePedidoCodigo = controleSeriePedidoCodigo;
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
		SeriePedido result = (SeriePedido) super.clone();

		result.setSeriePedidosPK((SeriePedidoPK) this.getSeriePedidosPK()
				.clone());

		return result;
	}

	/**
	 * 
	 * @return
	 */
	public static SeriePedido recuperarControleSerieAtiva(GenericoDAO dao,
			int filialCodigo, int controleSerieNumero) {

		return dao.uniqueResult(SeriePedido.class, SeriePedido.COLUNA_FILIAL
				+ " = " + filialCodigo + " and " + COLUNA_CONTROLESERIEPEDIDO
				+ " = " + controleSerieNumero + " and " + COLUNA_ATIVO
				+ " = 1 ", null);
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param codigo
	 * @return
	 */
	public static SeriePedido recuperarCodigo(GenericoDAO dao,
			int filialCodigo, String codigo) {

		return dao.uniqueResult(SeriePedido.class, SeriePedido.COLUNA_FILIAL
				+ " = " + filialCodigo + " and " + COLUNA_CODIGO + " = '"
				+ codigo + "'", null);
	}

	/**
	 * 
	 * @param dao
	 * @param serieAtual
	 */
	@Deprecated
	public static void atualizarIncremento(GenericoDAO dao,
			ControleSeriePedido controleSerie, SeriePedido serieAtual) {
		
		String sql = "update " + SeriePedido.NOME_TABELA + " set "
				+ SeriePedido.COLUNA_NUMEROATUAL + " = "
				+ SeriePedido.COLUNA_NUMEROATUAL + " + "
				+ controleSerie.getIncremento() + " where " + COLUNA_FILIAL
				+ " = " + serieAtual.getSeriePedidosPK().getFilialCodigo()
				+ " and " + COLUNA_CODIGO + " = '"
				+ serieAtual.getSeriePedidosPK().getCodigo() + "'";
		dao.executeUpdate(sql);
	}
}
