package br.com.space.spacewebII.modelo.dominio.logistica;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;

@Entity
@Table(name = "grupoenttipo")
public class GrupoEntTipo implements IPersistent {

	@EmbeddedId
	private GrupoEntTipoPK grupoEntTipoPK;

	public GrupoEntTipoPK getGrupoEntTipoPK() {
		return grupoEntTipoPK;
	}

	public void setGrupoEntTipoPK(GrupoEntTipoPK grupoEntTipoPK) {
		this.grupoEntTipoPK = grupoEntTipoPK;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
