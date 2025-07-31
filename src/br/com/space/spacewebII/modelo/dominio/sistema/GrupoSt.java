/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "grupost")
@XmlRootElement
public class GrupoSt implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_CODIGO = "gst_codigo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private Integer codigo;

	@Basic(optional = false)
	@Column(name = "gst_desc")
	private String descricao;

	@Basic(optional = false)
	@Column(name = "gst_aliqmva")
	private double aliquotaMva;

	@Basic(optional = false)
	@Column(name = "gst_redmva")
	private double reducaoMva;

	@Basic(optional = false)
	@Column(name = "gst_aliqst")
	private double aliquotaSt;

	@Column(name = "gst_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "gst_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "gst_mvaajust")
	private int flagMvaAjustavel;

	public GrupoSt() {
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

	public double getAliquotaMva() {
		return aliquotaMva;
	}

	public void setAliquotaMva(double aliquotaMva) {
		this.aliquotaMva = aliquotaMva;
	}

	public double getReducaoMva() {
		return reducaoMva;
	}

	public void setReducaoMva(double reducaoMva) {
		this.reducaoMva = reducaoMva;
	}

	public double getAliquotaSt() {
		return aliquotaSt;
	}

	public void setAliquotaSt(double aliquotaSt) {
		this.aliquotaSt = aliquotaSt;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFlagMvaAjustavel() {
		return flagMvaAjustavel;
	}

	public void setFlagMvaAjustavel(int flagMvaAjustavel) {
		this.flagMvaAjustavel = flagMvaAjustavel;
	}

	public static GrupoSt recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(GrupoSt.class, COLUNA_CODIGO + " = " + codigo,
				null);
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
