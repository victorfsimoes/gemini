package br.com.space.spacewebII.modelo.excecao.produto;

import br.com.space.api.core.propriedade.Propriedade;
import br.com.space.api.negocio.modelo.excecao.produto.ProdutoExcecao;

public class ProdutoLiberadoVendaExcecao extends ProdutoExcecao {

	private static final long serialVersionUID = 1L;

	public ProdutoLiberadoVendaExcecao(Propriedade propriedade) {
		super(propriedade.getMensagem("alerta.produto.liberadovenda"));
	}

}
