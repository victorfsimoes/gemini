package br.com.space.spacewebII.controle.cliente;

import java.io.Serializable;
import java.util.List;

import javax.inject.Inject;

import br.com.space.api.negocio.modelo.dominio.ICliente;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.endereco.Enderecos;
import br.com.space.spacewebII.modelo.dominio.pessoa.Pessoa;

public class GerenteCliente extends
		br.com.space.api.negocio.modelo.negocio.GerenteCliente<ICliente>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	
	@Inject
	GenericoDAO dao;

	// private Cliente

	/**
	 * 
	 * @param propriedade
	 */
	public GerenteCliente(
			br.com.space.api.core.propriedade.Propriedade propriedade) {
		super(propriedade);
	}

	/**
	 * 
	 * @param pessoa
	 * @param cliente
	 * @param enderecos
	 */
	public void cadastrar(Pessoa pessoa, Cliente cliente,
			List<Enderecos> enderecos) {
	}

	/*
	 * public GerenteCliente(IDSistema idSistema, GerenteLogin gerenteLogin,
	 * Precificacao precificacao, Propriedade propriedade, Parametros
	 * parametros, Estoque estoque, GenericoDAO dao, FabricaGerentePedido
	 * fabricaGerentePedido, GerenteAutorizacao gerenteAutorizacao) {
	 * 
	 * super(idSistema, gerenteLogin.getSessaoCodigo(), precificacao,
	 * propriedade, parametros, fabricaGerentePedido, gerenteAutorizacao);
	 * 
	 * this.gerenteLogin = gerenteLogin; this.estoque = estoque; this.parametros
	 * = parametros;
	 * 
	 * this.setPromocaoVenda(new
	 * br.com.space.spacewebII.controle.pedido.PromocaoVenda( dao, this));
	 * 
	 * this.setDao(dao); this.gruposComissao = dao.list(GrupoComissao.class);
	 * this.gruposComissaoItem = dao.list(GrupoComissaoItem.class);
	 * this.grupoComissaoPesquisa = new GrupoComissao();
	 * 
	 * fabricaGerentePedido.setGerentePedido(this); }
	 */

}
