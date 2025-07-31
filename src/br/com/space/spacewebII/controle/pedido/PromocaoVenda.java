package br.com.space.spacewebII.controle.pedido;

import java.sql.ResultSet;
import java.util.Date;
import java.util.List;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.negocio.modelo.dominio.IPedido;
import br.com.space.api.negocio.modelo.dominio.IProduto;
import br.com.space.api.negocio.modelo.dominio.IPromocao;
import br.com.space.api.negocio.modelo.negocio.GerentePedido;
import br.com.space.api.spa.model.dao.IGenericDAO;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.venda.Promocao;

public class PromocaoVenda extends br.com.space.api.negocio.modelo.negocio.PromocaoVenda {

	private static final long serialVersionUID = 1L;

	/**
	 * 
	 * @param dao
	 * @param gerentePedido
	 */
	public PromocaoVenda(IGenericDAO<IPersistent> dao, GerentePedido<? extends IPedido> gerentePedido) {
		super(dao, gerentePedido);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.PromocaoVenda#
	 * recuperarPromocoesPedido(br.com.space.api.spa.model.dao.IGenericDAO, int,
	 * java.util.Date)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends IPromocao> List<E> recuperarPromocoesPedido(int filialCodigo, Date data) {

		return (List<E>) Promocao.recuperarPromocoesPedido((GenericoDAO) dao, data, gerentePedido);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.PromocaoVenda#
	 * recuperarPromocoesItem (br.com.space.api.spa.model.dao.IGenericDAO, int,
	 * java.util.Date, br.com.space.api.negocio.modelo.dominio.IItemPedido)
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends IPromocao> List<E> recuperarPromocoesItem(int filialCodigo, Date data, IProduto produto) {

		return (List<E>) Promocao.recuperarPromocoesItem((GenericoDAO) dao, data, gerentePedido, (Produto) produto);
	}

	@Override
	public double getFatorEstoquePadraoPromocao(IProduto produto, IPromocao promocao) {

		String select = getSelectFatorEstoqueUnidadePromocao(produto, promocao);

		GenericoDAO genericoDAO = (GenericoDAO) dao;

		Double fatorEstoque = null;

		try (ResultSet result = genericoDAO.listResultSet(select)) {

			if (result.first()) {

				fatorEstoque = (Double) genericoDAO.getValueResultSet(Double.class, result,
						result.findColumn("unp_fatestoque"));

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return Conversao.nvlDouble(fatorEstoque, 1.0);
	}

}
