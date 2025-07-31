package br.com.space.spacewebII.modelo.excecao.pedido;

import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

public class PedidoCarregadoExcecao extends SpaceExcecao {
	
	private static final long serialVersionUID = 1L;

	public PedidoCarregadoExcecao(Propriedade propriedade) {
		super(propriedade.getMensagem("alerta.pedido.carregado"));
	}

}
