package br.com.space.spacewebII.modelo.excecao.permissao;

import org.jboss.seam.security.AuthorizationException;

public class PermissaoExcecao extends AuthorizationException {

	private static final long serialVersionUID = 1L;

	public PermissaoExcecao(String message) {
		super(message);
	}
}
