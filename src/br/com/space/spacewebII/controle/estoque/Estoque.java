package br.com.space.spacewebII.controle.estoque;

import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;

public class Estoque extends
		br.com.space.api.negocio.modelo.negocio.estoque.Estoque {

	private static final long serialVersionUID = 1L;

	public Estoque(GenericoDAO dao, Parametros parametros) {
		super(dao, new FabricaEstoque(parametros));
	}
}
