package br.com.space.spacewebII.modelo.excecao.pedido;

import br.com.space.api.core.propriedade.Propriedade;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;

public class PedidoEmEdicaoExcecao extends SpaceExcecao {

	private static final long serialVersionUID = 1L;

	public PedidoEmEdicaoExcecao(Propriedade propriedade) {
		super(propriedade.getMensagem("alerta.pedido.emedicao"));
	}
}
