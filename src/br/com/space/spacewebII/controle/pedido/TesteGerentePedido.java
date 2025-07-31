package br.com.space.spacewebII.controle.pedido;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.api.negocio.modelo.negocio.estoque.FlagTipoEstoque;
import br.com.space.api.negocio.sistema.IDSistema;
import br.com.space.spacewebII.controle.autorizacao.GerenteAutorizacao;
import br.com.space.spacewebII.controle.estoque.Estoque;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.estoque.FabricaPrecificacao;
import br.com.space.spacewebII.modelo.dominio.estoque.NaturezaOperacao;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.venda.TabelaPreco;
import br.com.space.spacewebII.modelo.dominio.venda.Vendedor;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

public class TesteGerentePedido {

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		testeGerentePedido();

	}

	private static void testeGerentePedido() {
		GerentePedido gerentePedido = null;
		try {
			GenericoDAO dao = new GenericoDAO();

			NaturezaOperacao naturezaOperacao = NaturezaOperacao
					.recuperarUnico(dao, "VE");
			FormaPagamento formaPagamento = FormaPagamento.recuperarUnico(dao,
					"DP");
			CondicaoPagamento condicaoPagamento = CondicaoPagamento
					.recuperarUnico(dao, 1);
			TabelaPreco tabelaPreco = TabelaPreco.recuperarUnico(dao, 1);
			Cliente cliente = Cliente.recuperarUnico(dao, 1);
			Vendedor vendedor = Vendedor.recuperarUnico(dao, 1);
			Filial filial = Filial.recuperarUnico(dao, 1);
			// ParametroVenda paramVenda = ParametroVenda.recuperarUnico(dao,
			// 1);
			Parametros paramAux = new Parametros(dao, filial);
			paramAux.recuperarUnico(dao, 1);

			Estoque estoque = new Estoque(dao, paramAux);

			br.com.space.spacewebII.controle.autorizacao.GerenteAutorizacao ga = new GerenteAutorizacao(
					dao, null, null);

			gerentePedido = new GerentePedido(IDSistema.SpaceWeb, null,
					new Precificacao(FabricaPrecificacao.getInstancia(dao,
							filial, paramAux, null), ga, 2, paramAux),
					Propriedade.getInstanciaSessao(), paramAux, estoque, dao,
					new FabricaGerentePedido(), ga);

			((GenericoDAO) gerentePedido.getDao()).beginTransaction();
			gerentePedido.setFilial(filial);
			gerentePedido.inicializarInclusaoPedido();
			gerentePedido.setNaturezaOperacao(naturezaOperacao);
			gerentePedido.setFormaPagamento(formaPagamento);
			gerentePedido.setCondicaoPagamento(condicaoPagamento);
			gerentePedido.setTabelaPreco(tabelaPreco);
			gerentePedido.setCliente(cliente);
			gerentePedido.setVendedor(vendedor);

			gerentePedido.alterarNegociacaoPrecificacao();
			gerentePedido.preencherAtributosPedido();

			estoque.limpar();
			estoque.setFilialCodigo(filial.getCodigo());
			estoque.setProdutoCodigo(1002);
			estoque.setFlagTipoEstoque(FlagTipoEstoque.DisponivelVendaGeral);

			estoque.recuperarEstoqueDisponivel();
			System.out.println("Antes:"
					+ estoque.getEstoquePendenteConfirmacao());

			/**
			 * 1002
			 */
			Produto produto = Produto.recuperarCodigo(dao, 1002);
			ProdutoUnidade produtoUnidade = ProdutoUnidade.recuperar(dao, 1002,
					"CX", 6);
			IItemPedidoWeb itemPedido = gerentePedido.criarItemPedido(produto,
					produtoUnidade);
			itemPedido.setQuantidade(3);
			gerentePedido.adicionarItem(itemPedido);
			// ((GenericoDAO) gerentePedido.getDao()).commitTransaction();

			estoque.recuperarEstoqueDisponivel();
			System.out.println("Item 1:"
					+ estoque.getEstoquePendenteConfirmacao());

			/**
			 * 1014
			 */
			// ((GenericoDAO) gerentePedido.getDao()).beginTransaction();
			produto = Produto.recuperarCodigo(dao, 1014);
			produtoUnidade = ProdutoUnidade.recuperar(dao, 1014, "DZ", 12);
			itemPedido = gerentePedido.criarItemPedido(produto, produtoUnidade);
			itemPedido.setQuantidade(1);
			gerentePedido.adicionarItem(itemPedido);
			itemPedido.setPrecoVenda(Conversao.arredondar(
					itemPedido.getPrecoVenda() * .99, 2));

			estoque.recuperarEstoqueDisponivel();
			System.out.println("Item 2:"
					+ estoque.getEstoquePendenteConfirmacao());

			/**
			 * 1021
			 */
			// ((GenericoDAO) gerentePedido.getDao()).beginTransaction();
			produto = Produto.recuperarCodigo(dao, 1021);
			produtoUnidade = ProdutoUnidade.recuperar(dao, 1021, "UN", 1);
			itemPedido = gerentePedido.criarItemPedido(produto, produtoUnidade);
			itemPedido.setQuantidade(2);
			gerentePedido.adicionarItem(itemPedido);
			itemPedido.setPrecoVenda(Conversao.arredondar(
					itemPedido.getPrecoVenda() * .98, 2));

			estoque.recuperarEstoqueDisponivel();
			System.out.println("Item 3:"
					+ estoque.getEstoquePendenteConfirmacao());

			/**
			 * 1034
			 */
			// ((GenericoDAO) gerentePedido.getDao()).beginTransaction();
			produto = Produto.recuperarCodigo(dao, 1034);
			produtoUnidade = ProdutoUnidade.recuperar(dao, 1034, "MC", 20);
			itemPedido = gerentePedido.criarItemPedido(produto, produtoUnidade);
			itemPedido.setQuantidade(4);
			gerentePedido.adicionarItem(itemPedido);
			itemPedido.setPrecoVenda(Conversao.arredondar(
					itemPedido.getPrecoVenda() * .97, 2));

			estoque.recuperarEstoqueDisponivel();
			System.out.println("Item 4:"
					+ estoque.getEstoquePendenteConfirmacao());

			gerentePedido.getPedido().setDescontoValor(20.00);
			gerentePedido.fecharPedido();

			estoque.recuperarEstoqueDisponivel();
			System.out.println("Depois:"
					+ estoque.getEstoquePendenteConfirmacao());
			System.out
					.println("Entrega:" + estoque.getEstoquePendenteEntrega());

			((GenericoDAO) gerentePedido.getDao()).commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
			if (gerentePedido != null) {
				((GenericoDAO) gerentePedido.getDao()).rollBackTransaction();
			}
		}
	}

}
