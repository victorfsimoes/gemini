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
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "cstipi")
@XmlRootElement
public class CstIpi implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_CODIGO = "csi_codigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private String codigo;

	@Column(name = "csi_desc")
	private String descricao;

	@Column(name = "csi_ativo")
	private Integer flagtivo;

	@Column(name = "csi_entrada")
	private Integer flagEntrada;

	@Column(name = "csi_saida")
	private Integer flagSaida;

	@Column(name = "csi_tipotrib")
	private String tipoTributacao;

	public CstIpi() {
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFlagtivo() {
		return flagtivo;
	}

	public void setFlagtivo(Integer flagtivo) {
		this.flagtivo = flagtivo;
	}

	public Integer getFlagEntrada() {
		return flagEntrada;
	}

	public void setFlagEntrada(Integer flagEntrada) {
		this.flagEntrada = flagEntrada;
	}

	public Integer getFlagSaida() {
		return flagSaida;
	}

	public void setFlagSaida(Integer flagSaida) {
		this.flagSaida = flagSaida;
	}

	public String getTipoTributacao() {
		return tipoTributacao;
	}

	public void setTipoTributacao(String tipoTributacao) {
		this.tipoTributacao = tipoTributacao;
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

	public static CstIpi recuperarUnico(GenericoDAO dao, String codigo) {

		return dao.uniqueResult(CstIpi.class, COLUNA_CODIGO + " = '" + codigo
				+ "'", null);
	}
}
