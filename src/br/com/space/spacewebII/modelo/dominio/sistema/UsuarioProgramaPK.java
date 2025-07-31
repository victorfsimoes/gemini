package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioProgramaPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public UsuarioProgramaPK() {
		
	}
	
	public UsuarioProgramaPK(String login, String programa, int codigoPermissao) {
		super();
		this.login = login;
		this.programa = programa;
		this.codigoPermissao = codigoPermissao;
	}

	@Column(name = "upr_usrlogin")
	private String login;
	
	@Column(name = "upr_prgcodigo")
	private String programa;
	
	@Column(name = "upr_percodigo")
	private int codigoPermissao;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getPrograma() {
		return programa;
	}

	public void setCodigoPrograma(String programa) {
		this.programa = programa;
	}

	public int getCodigoPermissao() {
		return codigoPermissao;
	}

	public void setCodigoPermissao(int codigoPermissao) {
		this.codigoPermissao = codigoPermissao;
	}
	
	
}
