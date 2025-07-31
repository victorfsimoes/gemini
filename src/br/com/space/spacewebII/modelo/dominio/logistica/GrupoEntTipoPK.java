package br.com.space.spacewebII.modelo.dominio.logistica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class GrupoEntTipoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "get_grecodigo")
	private int grupoEntregaCodigo;

	@Column(name = "get_tpecodigo")
	private String tipoEntregaTipo;

	public int getGrupoEntregaCodigo() {
		return grupoEntregaCodigo;
	}

	public void setGrupoEntregaCodigo(int grupoEntregaCodigo) {
		this.grupoEntregaCodigo = grupoEntregaCodigo;
	}

	public String getTipoEntregaTipo() {
		return tipoEntregaTipo;
	}

	public void setTipoEntregaTipo(String tipoEntregaTipo) {
		this.tipoEntregaTipo = tipoEntregaTipo;
	}

}
