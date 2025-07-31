package br.com.space.spacewebII.modelo.excecao.travamento;

import br.com.space.api.core.propriedade.Propriedade;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;

public class RegistroAcessoExclusivoExcecao extends SpaceExcecao {

	private static final long serialVersionUID = 1L;

	public RegistroAcessoExclusivoExcecao(Propriedade propriedade) {
		super(propriedade.getMensagem("alerta.registro.exclusivo"));
	}

}
