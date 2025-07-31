/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "carteiracli")
@XmlRootElement
public class CarteiraCliente implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	public static String TIPO_INTERNA = "I";
	public static String TIPO_EXTERNA = "E";

	@Id
	@Basic(optional = false)
	@Column(name = "ccl_codigo")
	private Integer codigo;

	@Column(name = "ccl_desc")
	private String descricao;

	@Column(name = "ccl_tipo")
	private String tipo;

	@Column(name = "ccl_ativo")
	private Integer flagAtivo = 0;

	@Basic(optional = false)
	@Column(name = "ccl_tprcodigo")
	private int tabelaPrecoCodigo;

	@Column(name = "ccl_tprcodigopr")
	private Integer tabelaPrecoPromocionalCodigo;

	@Column(name = "ccl_filcodigo")
	private Integer filialCodigo;

	@Column(name = "ccl_clbcodigo")
	private Integer colaboradorCodigo;

	public CarteiraCliente() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getTabelaPrecoCodigo() {
		return tabelaPrecoCodigo;
	}

	public void setTabelaPrecoCodigo(int tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	public Integer getTabelaPrecoPromocionalCodigo() {
		return tabelaPrecoPromocionalCodigo;
	}

	public void setTabelaPrecoPromocionalCodigo(
			Integer tabelaPrecoPromocionalCodigo) {
		this.tabelaPrecoPromocionalCodigo = tabelaPrecoPromocionalCodigo;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Integer getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(Integer colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public static List<Integer> recuperarCodigosCarteira(GenericoDAO dao,
			int colaboradorCodigo, String tipo) {

		String select = "select ccl_codigo from" + " colaborador"
				+ " inner join carteiravend" + " on clb_codigo = ccv_clbcodigo"
				+ "	inner join carteiracli on"
				+ " ccv_cclcodigo = ccl_codigo and ccl_tipo = '" + tipo + "'"
				+ " where clb_codigo = " + colaboradorCodigo
				+ " or clb_clbcodigo = " + colaboradorCodigo;

		return dao.listUniqueField(select, "ccl_codigo");
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
