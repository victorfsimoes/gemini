package br.com.space.spacewebII.controle.produto.mbean;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.space.spacewebII.controle.aplicacao.SessaoMB;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;

@ManagedBeanSessionScoped
public class AvisoEstoqueMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private SessaoMB sessaoMB;

	@Inject
	private GenericoDAO dao;

	private Cliente cliente = null;

	public AvisoEstoqueMB() {
	}

	/**
	 * Armazena a solicitação de aviso no estoque
	 * 
	 * @param produto
	 * @return
	 */
	public void gravarAvisoEstoque(Produto produto) {

		if (cliente == null)
			cliente = Cliente.recuperarUnico(dao, sessaoMB.getGerenteLogin()
					.getClienteCodigo());

		GerenteAvisoEstoque.gravarAvisoEstoque(dao, cliente.getCodigo(),
				produto.getCodigo(), sessaoMB.getGerenteLogin()
						.getFilialCodigo());

	}
}
