/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IPedido;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dominio.pessoa.Colaborador;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "colabpedidos")
@XmlRootElement
public class ColaboradorPedido implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected ColaboradorPedidoPK colabpedidosPK;

	@Column(name = "clp_ativo")
	private Integer flagAtivo;

	@Basic(optional = false)
	@Column(name = "clp_crgcodigo")
	private int cargoCodigo;

	public ColaboradorPedido() {
	}
	
	public ColaboradorPedido(Colaborador colaborador, IPedido pedido) {
		if (colabpedidosPK == null) {
			colabpedidosPK = new ColaboradorPedidoPK(colaborador, pedido);
		}
		
		this.flagAtivo = 1;
		this.cargoCodigo = colaborador.getCargo() != null?colaborador.getCargo().getCodigo():0;

	}

	public ColaboradorPedidoPK getColabpedidosPK() {
		return colabpedidosPK;
	}

	public void setColabpedidosPK(ColaboradorPedidoPK colabpedidosPK) {
		this.colabpedidosPK = colabpedidosPK;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getCargoCodigo() {
		return cargoCodigo;
	}

	public void setCargoCodigo(int cargoCodigo) {
		this.cargoCodigo = cargoCodigo;
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
