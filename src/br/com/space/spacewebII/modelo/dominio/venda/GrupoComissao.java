/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Comparator;

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
@Table(name = "grupocomis")
@XmlRootElement
public class GrupoComissao implements Serializable, IPersistent,
		Comparable<GrupoComissao> {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "gco_codigo")
	private int codigo;

	@Basic(optional = false)
	@Column(name = "gco_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "gco_descricao")
	private String descricao;

	@Basic(optional = false)
	@Column(name = "gco_tipocom")
	private String tipoComissao;
	
	@Column(name = "gco_lucsimp")
	private int considerarLucroSemImposto;

	public GrupoComissao() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoComissao() {
		return tipoComissao;
	}

	public void setTipoComissao(String tipoComissao) {
		this.tipoComissao = tipoComissao;
	}

	public int getConsiderarLucroSemImposto() {
		return considerarLucroSemImposto;
	}

	public void setConsiderarLucroSemImposto(int considerarLucroSemImposto) {
		this.considerarLucroSemImposto = considerarLucroSemImposto;
	}

	public boolean isLucroSemImposto() {
		return (this.getConsiderarLucroSemImposto() == 1);
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

	public static final Comparator<GrupoComissao> comparatorCodigo = new Comparator<GrupoComissao>() {

		@Override
		public int compare(GrupoComissao arg0, GrupoComissao arg1) {
			return arg0.compareTo(arg1);
		}
	};

	public int compareTo(GrupoComissao arg0) {
		if (this.codigo > arg0.getCodigo()) {
			return 1;
		} else if (this.codigo < arg0.getCodigo()) {
			return -1;
		} else {
			return 0;
		}
	}
}
