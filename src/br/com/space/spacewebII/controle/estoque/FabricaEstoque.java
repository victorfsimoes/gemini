package br.com.space.spacewebII.controle.estoque;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import br.com.space.api.dominiojee.dominio.estoque.LocalProduto;
import br.com.space.api.dominiojee.dominio.estoque.TipoMovimentoEstoque;
import br.com.space.api.negocio.modelo.dominio.IItemPedido;
import br.com.space.api.negocio.modelo.dominio.estoque.ILocalProduto;
import br.com.space.api.negocio.modelo.dominio.estoque.ITipoMovimentoEstoque;
import br.com.space.api.negocio.modelo.negocio.estoque.FabricaEstoqueImpl;
import br.com.space.api.spa.model.dao.GenericDAO;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;

public class FabricaEstoque extends FabricaEstoqueImpl implements Serializable {

	private static final long serialVersionUID = 1L;

	public FabricaEstoque() {
		super(false, false);
	}

	public FabricaEstoque(Parametros parametros) {
		super(parametros.getParametro1().getFlagControlaLote() == 1,
				parametros.getParametro2().getFlagControlaSaldoEstoqueLocalLote() == 1);
	}

	@Override
	public ITipoMovimentoEstoque criarTipoMovimentoEstoquePesquisa() {
		return new TipoMovimentoEstoque();
	}

	@Override
	public ILocalProduto criarLocalProduto(int filial, int local, int produto, double estoqueInicial, Date dataInicial,
			double estoqueFisico, double estoqueReservado, double estoqueUsado, Double estoquePendenteConfirmacao,
			Double estoquePendenteEntrega, Double estoqueMinimo) {

		return new LocalProduto(filial, local, produto, estoqueInicial, dataInicial, estoqueFisico, estoqueReservado,
				estoqueUsado, estoquePendenteConfirmacao, estoquePendenteEntrega, estoqueMinimo);
	}

	@Override
	public List<TipoMovimentoEstoque> recuperarTodosTipoMovimentacaoEstoque(GenericDAO<IPersistent> dao) {
		return dao.list(TipoMovimentoEstoque.class);
	}

	@Override
	public ResultSet recureparDados(GenericDAO<IPersistent> dao, String select)
			throws ClassNotFoundException, SQLException {
		if (dao instanceof GenericoDAO) {
			return ((GenericoDAO<IPersistent>) dao).listResultSet(select);
		}
		return null;
	}

	@Override
	public IItemPedido recuperarItemPedido(GenericDAO<IPersistent> dao, int numeroPedido, String seriePedido,
			int filialCodigo, int numeroItem) {
		return null;
	}
}
