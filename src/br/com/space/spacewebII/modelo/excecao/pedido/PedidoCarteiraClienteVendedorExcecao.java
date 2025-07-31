package br.com.space.spacewebII.modelo.excecao.pedido;

import br.com.space.api.negocio.modelo.excecao.pedido.PedidoExcecao;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

public class PedidoCarteiraClienteVendedorExcecao extends PedidoExcecao {

	private static final long serialVersionUID = 1L;

	public PedidoCarteiraClienteVendedorExcecao(Propriedade propriedade) {
		super(propriedade.getMensagem("alerta.pedido.carteiraclientevendedor"));
	}

}
