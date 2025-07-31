package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioFilialPK implements Serializable {

	/**
		 * 
		 */
	private static final long serialVersionUID = 1L;

	public UsuarioFilialPK() {
		super();
	}

	public UsuarioFilialPK(int codigoFilial, String loginUsuario) {
		super();
		this.codigoFilial = codigoFilial;
		this.loginUsuario = loginUsuario;
	}

	@Column(name = "usf_filcodigo")
	private int codigoFilial;

	@Column(name = "usf_usrlogin")
	private String loginUsuario;

	/*
	 * Gets e Setters
	 */
	public int getCodigoFilial() {
		return codigoFilial;
	}

	public void setCodigoFilial(int codigoFilial) {
		this.codigoFilial = codigoFilial;
	}

	public String getLoginUsuario() {
		return loginUsuario;
	}

	public void setLoginUsuario(String loginUsuario) {
		this.loginUsuario = loginUsuario;
	}

}
