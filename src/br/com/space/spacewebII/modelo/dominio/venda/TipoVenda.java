/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

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
 * @author Desenvolvimento
 */
@Entity
@Table(name = "tipovenda")
@XmlRootElement
public class TipoVenda implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;
	private static final String COLUNA_FILIAL_CODIGO = "tpv_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = "tpv_codigo")
	private Integer codigo;

	@Column(name = "tpv_desc")
	private String descricao;

	@Column(name = "tpv_ativo")
	private Integer flagAtivo;

	@Column(name = COLUNA_FILIAL_CODIGO)
	private Integer filialCodigo;

	public TipoVenda() {
	}

	public TipoVenda(Integer tpvCodigo) {
		this.codigo = tpvCodigo;
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

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public static List<TipoVenda> recuperaTiposVendaAtivos(GenericoDAO dao, Integer filialCodigo){
		StringBuilder where = new StringBuilder("tpv_ativo = 1");
		if(filialCodigo != null)
			where.append(" and ").append(COLUNA_FILIAL_CODIGO).append(" = ").append(filialCodigo);
		return dao.list(TipoVenda.class, where.toString(), null, "descricao", null);
	}
}
