/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "grupotrib")
@XmlRootElement
public class GrupoTributacao implements Serializable, IPersistent,
		Comparable<GrupoTributacao> {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_CODIGO = "grt_codigo";
	public static String CAMPO_CODIGO = "codigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Column(name = "grt_desc")
	private String descricao;

	@Column(name = "grt_ativo")
	private int flagAtivo;

	@Column(name = "grt_aliqicms")
	private Float aliquotaIcms;

	@Column(name = "grt_basecalc")
	private Float baseCalculo;

	@Column(name = "grt_isenta")
	private Float isenta;

	@Column(name = "grt_outras")
	private Float outras;

	@Column(name = "grt_obfcodigo")
	private Integer observacaoFiscalCodigo;

	@Column(name = "grt_filcodigo")
	private Integer filialCodigo;

	@Column(name = "grt_csscodigo")
	private String csosnCodigo;

	@JoinColumn(name = "grt_cstcodigo", referencedColumnName = "cst_codigo")
	@Fetch(FetchMode.JOIN)
	@ManyToOne
	private Cst cst;

	public Cst getCst() {
		return cst;
	}

	public void setCst(Cst cst) {
		this.cst = cst;
	}

	public GrupoTributacao() {
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

	public Float getAliquotaIcms() {
		return aliquotaIcms;
	}

	public void setAliquotaIcms(Float aliquotaIcms) {
		this.aliquotaIcms = aliquotaIcms;
	}

	public Float getBaseCalculo() {
		return baseCalculo;
	}

	public void setBaseCalculo(Float baseCalculo) {
		this.baseCalculo = baseCalculo;
	}

	public Float getIsenta() {
		return isenta;
	}

	public void setIsenta(Float isenta) {
		this.isenta = isenta;
	}

	public Float getOutras() {
		return outras;
	}

	public void setOutras(Float outras) {
		this.outras = outras;
	}

	public Integer getObservacaoFiscalCodigo() {
		return observacaoFiscalCodigo;
	}

	public void setObservacaoFiscalCodigo(Integer observacaoFiscalCodigo) {
		this.observacaoFiscalCodigo = observacaoFiscalCodigo;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getCsosnCodigo() {
		return csosnCodigo;
	}

	public void setCsosnCodigo(String csosnCodigo) {
		this.csosnCodigo = csosnCodigo;
	}

	public static GrupoTributacao recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(GrupoTributacao.class, COLUNA_CODIGO + " = "
				+ codigo, null);
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static List<GrupoTributacao> recuperar(GenericoDAO dao,
			String filialCodigo, String ordenacao) {

		String where = null;

		if (filialCodigo != null && !filialCodigo.trim().isEmpty()) {
			where = "grt_filcodigo = " + filialCodigo;
		}

		return dao.list(GrupoTributacao.class, where, null, ordenacao, null);
	}

	@Override
	public int compareTo(GrupoTributacao o) {
		return Integer.compare(codigo, o.getCodigo());
	}

}
