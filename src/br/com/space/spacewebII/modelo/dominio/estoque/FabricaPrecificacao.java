package br.com.space.spacewebII.modelo.dominio.estoque;

import java.util.Date;
import java.util.List;

import br.com.space.api.negocio.modelo.dominio.IOferta;
import br.com.space.api.negocio.modelo.dominio.IProduto;
import br.com.space.api.negocio.modelo.dominio.IProdutoPreco;
import br.com.space.api.negocio.modelo.dominio.IProdutoUnidade;
import br.com.space.api.negocio.modelo.dominio.ITabelaPrecoCondicao;
import br.com.space.api.negocio.modelo.dominio.Ikit;
import br.com.space.api.negocio.modelo.dominio.produto.IFabricaPrecificacao;
import br.com.space.api.negocio.modelo.negocio.GerenteAutorizacao;
import br.com.space.api.spa.model.dao.db.Dictionary;
import br.com.space.api.spa.model.dao.db.Table;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.venda.Kit;
import br.com.space.spacewebII.modelo.dominio.venda.Oferta;
import br.com.space.spacewebII.modelo.dominio.venda.TabelaPrecoCondicao;

public class FabricaPrecificacao implements IFabricaPrecificacao {

	GenericoDAO dao;
	Filial filial;
	Parametros parametros;

	private static FabricaPrecificacao instancia = null;

	/**
	 * Responsavel pelo SingleTom
	 * 
	 * @return
	 */
	public static FabricaPrecificacao getInstancia(GenericoDAO dao, Filial filial, Parametros parametros,
			GerenteAutorizacao gerenteAutorizacao) {
		if (instancia == null) {
			instancia = new FabricaPrecificacao(dao, filial, parametros, gerenteAutorizacao);
		}

		instancia.dao = dao;
		instancia.filial = filial;
		instancia.parametros = parametros;

		return instancia;
	}

	private br.com.space.api.negocio.modelo.dominio.produto.Precificacao precificacao = null;

	/**
	 * Construtora padrao.
	 */
	protected FabricaPrecificacao(GenericoDAO dao, Filial filial, Parametros parametros,
			GerenteAutorizacao gerenteAutorizacao) {
		super();
		precificacao = new br.com.space.api.negocio.modelo.dominio.produto.Precificacao(this, gerenteAutorizacao, 2,
				parametros);
	}

	/**
	 * 
	 * @return
	 */
	public br.com.space.api.negocio.modelo.dominio.produto.Precificacao getPrecificacao() {
		return precificacao;
	}

	/**
	 * 
	 * @param tabelaPrecoCodigo
	 * @param condicaoCodigo
	 * @return
	 */
	@Override
	public ITabelaPrecoCondicao obterTabelaPrecoCondicao(int tabelaPrecoCodigo, int condicaoCodigo) {

		return dao.uniqueResult(TabelaPrecoCondicao.class,
				TabelaPrecoCondicao.COLUNA_TABELAPRECO_CODIGO + " = " + tabelaPrecoCodigo + " AND "
						+ TabelaPrecoCondicao.COLUNA_CONDICAOPAGAMENTO_CODIGO + " = " + condicaoCodigo,
				null);
	}

	/**
	 * 
	 * @param where
	 * @param parametros
	 * @param tipoPreco
	 * @return
	 */
	@Override
	public List<? extends IProdutoPreco> obterListaPrecos(String where, String[] parametros, int tipoPreco) {

		Table tabelaProdutoPreco = null;
		try {
			tabelaProdutoPreco = Dictionary.getTable(ProdutoPreco.class);
		} catch (Exception e) {

		} finally {

		}

		String colunas = tabelaProdutoPreco.getColumnsNamesWithCommas();
		// Dictionary.extractColumnFromField(ProdutoPreco.class, "");

		String sql = "select " + colunas + " from produtopreco " + " inner join produto on ppr_procodigo = pro_codigo "
				+ " and ppr_filcodigo = " + this.filial.getCodigo() + " where " + where
				+ " order by ppr_procodigo, ppr_prbcodigo";

		/*
		 * return dao.list(ProdutoPreco.class, ProdutoPreco.NOME_TABELA + " inner join "
		 * + Produto.NOME_TABELA + " on " + ProdutoPreco.COLUNA_PRODUTO_CODIGO + " = " +
		 * Produto.COLUNA_CODIGO + " and " + ProdutoPreco.COLUNA_FILIAL_CODIGO + " = " +
		 * this.filial.getCodigo(), where, parametros,
		 * ProdutoPreco.COLUNA_PRODUTO_CODIGO + ", " +
		 * ProdutoPreco.COLUNA_PRECO_BASE_CODIGO, null);
		 */

		List<ProdutoPreco> produtos = null;
		try {
			produtos = dao.list(ProdutoPreco.class, sql, null);
		} catch (Exception e) {
		} finally {
		}
		return produtos;
	}

	/**
	 * 
	 * @param where
	 * @param parametros
	 * @param tipoPreco
	 * @return
	 */
	@Override
	public List<? extends IOferta> obterListaOfertas(String where, String[] parametros, int tipoPreco) {

		Date dataAux = new Date();

		String whereOferta = Oferta.getWhere(this.filial.getCodigo(), dataAux, null);

		return dao.list(Oferta.class,
				Oferta.NOME_TABELA + " inner join " + Produto.NOME_TABELA + " on " + Oferta.COLUNA_PRODUTO_CODIGO
						+ " = " + Produto.COLUNA_CODIGO + " and " + whereOferta,
				where, parametros, Oferta.COLUNA_PRODUTO_CODIGO + "," + Oferta.COLUNA_PRECO_BASE_CODIGO, null);
	}

	/**
	 * 
	 */
	@Override
	public IProdutoUnidade obterProdutoUnidade(int produtoCodigo, String unidade, int quantidadeUnidade) {

		return dao.uniqueResult(ProdutoUnidade.class,
				ProdutoUnidade.COLUNA_PRODUTO_CODIGO + "=" + produtoCodigo + " and " + ProdutoUnidade.COLUNA_UNIDADE
						+ "='" + unidade + "' and " + ProdutoUnidade.COLUNA_QUANTIDADE + "=" + quantidadeUnidade,
				null);

	}

	/**
	 * 
	 */
	@Override
	public IProdutoPreco criarProdutoPreco() {
		return new ProdutoPreco();
	}

	/**
	 * 
	 */
	@Override
	public IOferta criarOferta() {
		return new Oferta();
	}

	/**
	 * 
	 */
	@Override
	public Oferta recuperarOferta(int ofertaNumero) {

		return Oferta.recuperarNumero(dao, ofertaNumero, (filial != null ? filial.getCodigo() : 0));
	}

	@Override
	public Ikit obterKit(int kitCodigo) {
		return Kit.recuperar(dao, kitCodigo);
	}

	@Override
	public double getValorCustoPadraoProduto(int produtoCodigo, int custoPadraoCodigo) {
		return ProdutoFilial.recuperarPrecoCusto(dao, filial.getCodigo(), produtoCodigo, custoPadraoCodigo);
	}

	@Override
	public IProduto obterProduto(int arg0) {
		return Produto.recuperarCodigo(dao, arg0);
	}
}
