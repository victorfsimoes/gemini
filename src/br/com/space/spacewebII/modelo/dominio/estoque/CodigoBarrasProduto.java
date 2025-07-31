/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "codbarraspro")
@XmlRootElement
public class CodigoBarrasProduto implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected CodigoBarrasProdutoPK codigoBarrasProdutoPK;

	@Basic(optional = false)
	@Column(name = "cbp_filcodigo")
	private int filialCodigo;

	@Column(name = "cbp_ativo")
	private int flagAtivo;

	public CodigoBarrasProduto() {
	}

	public CodigoBarrasProduto(CodigoBarrasProdutoPK codigoBarrasProdutoPK) {
		this.codigoBarrasProdutoPK = codigoBarrasProdutoPK;
	}

	public CodigoBarrasProduto(CodigoBarrasProdutoPK codigoBarrasProdutoPK,
			int cbpFilCodigo) {
		this.codigoBarrasProdutoPK = codigoBarrasProdutoPK;
		this.filialCodigo = cbpFilCodigo;
	}

	public CodigoBarrasProduto(int cbpProCodigo, String cbpUnpUnida,
			int cbpUnpQuant, String cbpCodigo) {
		this.codigoBarrasProdutoPK = new CodigoBarrasProdutoPK(cbpProCodigo,
				cbpUnpUnida, cbpUnpQuant, cbpCodigo);
	}

	public CodigoBarrasProdutoPK getCodigoBarrasProdutoPK() {
		return codigoBarrasProdutoPK;
	}

	public void setCodigoBarrasProdutoPK(
			CodigoBarrasProdutoPK codigoBarrasProdutoPK) {
		this.codigoBarrasProdutoPK = codigoBarrasProdutoPK;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
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
