/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IGrupoVendaNaoPermitidoCliente;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "gruvenncli")
@XmlRootElement
public class GrupoVendaNaoPermitidoCliente implements Serializable,
		IPersistent, IGrupoVendaNaoPermitidoCliente {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected GrupoVendaNaoPermitidoClientePK grupoVendaNaoPermitidoClientePK;

	public GrupoVendaNaoPermitidoCliente() {
	}

	public GrupoVendaNaoPermitidoClientePK getGrupoVendaNaoPermitidoClientePK() {
		return grupoVendaNaoPermitidoClientePK;
	}

	public void setGrupoVendaNaoPermitidoClientePK(
			GrupoVendaNaoPermitidoClientePK grupoVendaNaoPermitidoClientePK) {
		this.grupoVendaNaoPermitidoClientePK = grupoVendaNaoPermitidoClientePK;
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

	public static List<GrupoVendaNaoPermitidoCliente> recuperar(
			GenericoDAO dao, int codigoPessoa) {
		return dao.list(GrupoVendaNaoPermitidoCliente.class, "gnc_pescodigo = "
				+ codigoPessoa, null, null, null);
	}

	@Override
	public int getPessoaCodigo() {

		if (grupoVendaNaoPermitidoClientePK != null) {
			return grupoVendaNaoPermitidoClientePK.getPessoaCodigo();
		}

		return 0;
	}

	@Override
	public int getGrupoVendaCodigo() {
		if (grupoVendaNaoPermitidoClientePK != null) {
			return grupoVendaNaoPermitidoClientePK.getGrupoVendaCodigo();
		}
		return 0;
	}
}
