package br.com.space.spacewebII.modelo.padrao.interfaces;

import java.io.Serializable;

import org.picketlink.idm.api.User;

public interface UsuarioWeb extends User, Serializable {

	public String getLogin();

	public int getColaboradorCodigo();

	public Integer getCodigoGrupo();

	public String getNome();
}
