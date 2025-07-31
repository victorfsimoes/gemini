package br.com.space.spacewebII.controle.pedido;

import java.util.List;

import br.com.space.api.negocio.modelo.dominio.ICondicaoPagamento;
import br.com.space.api.negocio.modelo.dominio.ICondicaoPagamentoPessoa;
import br.com.space.api.negocio.modelo.dominio.IFormaPagamento;
import br.com.space.api.negocio.modelo.dominio.IFormaPagamentoPessoa;
import br.com.space.api.negocio.modelo.dominio.IOpcaoEspecial;
import br.com.space.api.negocio.modelo.dominio.ITabelaPreco;
import br.com.space.api.negocio.modelo.dominio.ITabelaPrecoCondicao;
import br.com.space.api.negocio.modelo.dominio.IUsuarioTabela;
import br.com.space.api.negocio.modelo.negocio.IFabricaGerentePedido;
import br.com.space.api.spa.model.dao.IGenericDAO;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.pessoa.CondicaoPagamentoPessoa;
import br.com.space.spacewebII.modelo.dominio.pessoa.FormaPagamentoPessoa;
import br.com.space.spacewebII.modelo.dominio.sistema.Usuario;
import br.com.space.spacewebII.modelo.dominio.venda.OpcaoEspecial;
import br.com.space.spacewebII.modelo.dominio.venda.TabelaPreco;
import br.com.space.spacewebII.modelo.dominio.venda.TabelaPrecoCondicao;
import br.com.space.spacewebII.modelo.dominio.venda.UsuarioTabela;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;

public class FabricaGerentePedido implements IFabricaGerentePedido {

	GerentePedido gerentePedido = null;
	private OpcaoEspecial opcaoEspecial = null;

	/**
	 * 
	 * @param gerentePedido
	 */
	public void setGerentePedido(GerentePedido gerentePedido) {
		this.gerentePedido = gerentePedido;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * recuperarCondicoesPagamento()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends ICondicaoPagamento> List<E> recuperarCondicoesPagamento(
			IGenericDAO<IPersistent> dao) {
		return (List<E>) dao.list(CondicaoPagamento.class,
				CondicaoPagamento.COLUNA_ATIVO + " = 1", null,
				CondicaoPagamento.CAMPO_DESCRICAO + ", "
						+ CondicaoPagamento.CAMPO_CODIGO, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * recuperarCondicoesPagamentoPessoa(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends ICondicaoPagamentoPessoa> List<E> recuperarCondicoesPagamentoPessoa(
			IGenericDAO<IPersistent> dao, Integer pessoaCodigo) {

		return (List<E>) dao.list(CondicaoPagamentoPessoa.class,
				CondicaoPagamentoPessoa.COLUNA_PESSOA_CODIGO + "="
						+ pessoaCodigo, null, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * recuperarTabelaPrecoCondicoes(java.lang.Integer)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends ITabelaPrecoCondicao> List<E> recuperarTabelaPrecoCondicoes(
			IGenericDAO<IPersistent> dao, Integer tabelaPrecoCodigo) {

		return (List<E>) dao.list(TabelaPrecoCondicao.class,
				TabelaPrecoCondicao.COLUNA_TABELAPRECO_CODIGO + "="
						+ tabelaPrecoCodigo, null, null, null);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends IFormaPagamentoPessoa> List<E> recuperarFormasPagamentoPessoa(
			IGenericDAO<IPersistent> dao, Integer pessoaCodigo) {
		return (List<E>) dao.list(FormaPagamentoPessoa.class,
				FormaPagamentoPessoa.COLUNA_PESSOA_CODIGO + "=" + pessoaCodigo,
				null, null, null);

	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends IFormaPagamento> List<E> recuperarFormasPagamento(
			IGenericDAO<IPersistent> dao) {
		return (List<E>) dao.list(FormaPagamento.class,
				FormaPagamento.COLUNA_ATIVO + " = 1 and "
						+ FormaPagamento.COLUNA_HABILITADO_VENDA + " = 1",
				null, FormaPagamento.CAMPO_DESCRICAO + ", "
						+ FormaPagamento.CAMPO_CODIGO, null);
	}

	@Override
	public ITabelaPreco recuperarTabelaPreco(IGenericDAO<IPersistent> dao,
			Integer tabelaPrecoCodigo) {
		return TabelaPreco.recuperarUnico((GenericoDAO) dao, tabelaPrecoCodigo);
	}

	@Override
	public <E extends IUsuarioTabela> List<E> recuperarUsuarioTabela(
			IGenericDAO<IPersistent> dao) {

		return recuperarUsuarioTabela(dao, 0);
	}

	@SuppressWarnings("unchecked")
	@Override
	public <E extends IUsuarioTabela> List<E> recuperarUsuarioTabela(
			IGenericDAO<IPersistent> dao, int tabela) {

		UsuarioWeb usuarioWeb = gerentePedido.getUsuarioLogado();

		if (usuarioWeb instanceof Usuario) {
			return (List<E>) UsuarioTabela.recupera(dao, usuarioWeb.getLogin(),
					new Integer(tabela));
		}
		return null;

	}

	@Override
	public IOpcaoEspecial recuperarOpcaoEspecial(IGenericDAO<IPersistent> dao,
			Integer opcaoEspecialCodigo) {
		if (opcaoEspecial == null
				|| opcaoEspecial.getCodigo() != opcaoEspecialCodigo) {
			opcaoEspecial = OpcaoEspecial.recuperarUnico((GenericoDAO) dao,
					opcaoEspecialCodigo);
		}
		return opcaoEspecial;
	}
}
