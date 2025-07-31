package br.com.space.spacewebII.controle.venda.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.jboss.seam.security.AuthorizationException;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IFormaPagamento;
import br.com.space.api.negocio.modelo.dominio.IKitPedido;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.api.negocio.modelo.dominio.produto.PrecoVenda;
import br.com.space.api.negocio.modelo.excecao.autorizacao.IExcecaoAutorizavel;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoBalancoAtivoExececao;
import br.com.space.api.negocio.modelo.excecao.kit.KitNaoEncotradoExcecao;
import br.com.space.api.negocio.modelo.negocio.OperacaoItem;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.controle.autorizacao.mbean.GerenteAutorizacaoMB;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.controle.pedido.mbean.GerentePedidoMB;
import br.com.space.spacewebII.controle.produto.mbean.ProdutoDetalheMB;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.LocalEstoque;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoLote;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoLotePK;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoMidia;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidadePK;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroVenda2;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.venda.ItemKit;
import br.com.space.spacewebII.modelo.dominio.venda.ItemPedidoPrecoNegociado;
import br.com.space.spacewebII.modelo.dominio.venda.Kit;
import br.com.space.spacewebII.modelo.dominio.venda.Oferta;
import br.com.space.spacewebII.modelo.dominio.venda.Promocao;
import br.com.space.spacewebII.modelo.util.Acao;
import br.com.space.spacewebII.modelo.util.FormatacaoMB;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBeanSessionScoped
@URLBeanName("itemPedidoMB")
@URLMappings(mappings = {
		@URLMapping(id = "produto", pattern = "/produto/", viewId = "/pages/itemPedido3.xhtml", onPostback = false),
		@URLMapping(id = "produtoCodigob", pattern = "/produto/#{itemPedidoMB.codigoItemURL}", viewId = "/pages/itemPedido3.xhtml", onPostback = false),
		@URLMapping(id = "produtoCodigo", pattern = "/produto/#{itemPedidoMB.codigoItemURL}/", viewId = "/pages/itemPedido3.xhtml", onPostback = false),
		@URLMapping(id = "produtoCompleto", pattern = "/produto/#{itemPedidoMB.codigoItemURL}/#{itemPedidoMB.nomeItemURL}/", viewId = "/pages/itemPedido3.xhtml", onPostback = false) })
public class ItemPedidoMB extends ProdutoDetalheMB implements Serializable {

	private static final long serialVersionUID = 1L;;
	private static final String FLAG_DESCONTO = "D";
	private static final String FLAG_ACRESCIMO = "A";
	private static final String IDENTIFICADOR_KIT_URL = "K";

	@Inject
	GerenteAutorizacaoMB gerenteAutorizacaoMB;

	@Inject
	private Parametros parametros;

	private String codigoItemURL;
	private String nomeItemURL;

	private String fbNomeProduto;
	private String fbFotoCapa;

	private double quantidade = 1;
	private double quantidadeVolumes = 0;
	private double descontoAcrescimoValor = 0;
	private double descontoAcrescimoPorcentagem = 0;

	private double precoVendaValor = 0;
	private double precoOriginalProduto = 0;
	private double precoUnitarioValor = 0;
	private double precoTabelaValor = 0;
	private double precoSugeridoValor = 0;

	private ProdutoLote loteSelecionado;
	private LocalEstoque localEstoqueSelecionado;
	private ProdutoUnidade unidadeSelecionada = null;

	private String descricaoPromocaoSelecionada = null;
	private Promocao promocaoSelecionada = null;

	private List<ProdutoUnidade> unidades = null;
	private List<ProdutoLote> lotes = null;
	private List<LocalEstoque> locaisEstoque = null;
	private List<List<Promocao>> listasPromocoes = null;

	private String flagAcrescDesc = FLAG_DESCONTO;

	private IItemPedidoWeb itemPedidoErro = null;

	private IItemPedidoWeb itemPedidoProdutoSelecionado = null;

	private boolean exibirImagens = true;

	public ItemPedidoMB() {
	}

	@Override
	public void aoCarregar() {
		super.aoCarregar();
	}

	public String compraRapida(CaixaProdutoVisualizavel produto)
			throws SpaceExcecao, CloneNotSupportedException, Exception {

		ProdutoMidia imagemCapa = produto.getImagemCapa();

		boolean foiInserido = inserirOuVisualizarCaixaProduto(produto, true, false);

		if (foiInserido) {
			setQuantidade(produto.getQuantidadeCompraRapida());

			ProdutoUnidade produtoUnidade = ((Produto) produto).getProdutoUnidadeVenda();
			if (produtoUnidade == null) {
				produtoUnidade = ProdutoUnidade.recuperar(dao, produto.getCodigo(), 1);
			}

			setUnidadeSelecionada(produtoUnidade);

			getCaixaProdutoSelecionado().setImagemCapa(imagemCapa);

			recalcularPreco();

			confirmar();
		} else {
			exibirMensagemInformacao(produto.getDescricaoVisualizacao(),
					propriedade.getMensagem("mensagem.impossivelinserproduto"));
		}

		return "#";
	}

	/**
	 * Metodo chamado pela tela quando o Usuario clica no botão de confirmar
	 * 
	 * @throws CloneNotSupportedException
	 * @throws SpaceExcecao
	 */
	public void confirmar() throws SpaceExcecao, CloneNotSupportedException, Exception {

		if (acao == null) {
			return;
		}

		if (!gerenteLogin.isSessoaAutenticada()) {

			throw new AuthorizationException(propriedade.getMensagem("alerta.loginNecessario"));
		}

		GerentePedido gerentePedido = gerentePedidoMB.getGerentePedido();
		GenericoDAO dao = (GenericoDAO) gerentePedido.getDao();

		String mensagem = propriedade.getMensagem("mensagem.foiAdicionadoNoPedido");
		String titulo = caixaProdutoSelecionado.getDescricaoVisualizacao();
		TipoMensagem tipoMensagem = TipoMensagem.Info;

		try {

			gerentePedidoMB.verificarTravaPedido();

			if (getCaixaProdutoSelecionado().atulizarEmbalanco(getFilialCodigo(), dao)) {

				throw new ItemPedidoBalancoAtivoExececao(getPropriedade());

			}

			/*
			 * Inicia uma transação nova para a operação do item e atualização
			 * do pedido.
			 */
			dao.beginTransaction();

			if (acao == Acao.INCLUIR) {
				incluirItem(gerentePedido);
			} else if (acao == Acao.EDITAR) {
				alterarItem(gerentePedido);
				mensagem = propriedade.getMensagem("mensagem.foiAlteradoNoPedido");
				sessaoMB.mensagem(tipoMensagem, titulo, mensagem);
			} else if (acao == Acao.EXCLUIR) {
				excluirItem(gerentePedido);
				mensagem = propriedade.getMensagem("mensagem.foiExcluidoDoPedido");
				sessaoMB.mensagem(tipoMensagem, titulo, mensagem);
			}

			dao.commitTransaction();

			if (acao == Acao.INCLUIR) {
				gerentePedidoMB.travarPedido(true);
			}

		} catch (Exception e) {
			dao.rollBackTransaction();
			gerentePedidoMB.getGerentePedido().voltarNumeracaoPedido();

			if (e instanceof IExcecaoAutorizavel) {
				throw e;
			} else {
				tipoMensagem = TipoMensagem.Erro;
				mensagem = e.getMessage();
				sessaoMB.mensagem(tipoMensagem, titulo, mensagem);
				e.printStackTrace();
			}
		}
	}

	@URLActions(actions = { @URLAction(mappingId = "produtoCodigo", onPostback = false),
			@URLAction(mappingId = "produtoCompleto", onPostback = false) })
	public void inserirOuVisualizarCaixaProduto() {

		if (StringUtil.isValida(codigoItemURL)) {

			boolean urlKit = codigoItemURL.contains(IDENTIFICADOR_KIT_URL)
					|| codigoItemURL.contains(IDENTIFICADOR_KIT_URL.toLowerCase());

			int codigo = Integer.parseInt(
					codigoItemURL.replace(IDENTIFICADOR_KIT_URL, "").replace(IDENTIFICADOR_KIT_URL.toLowerCase(), ""));

			boolean podeBuscar = true;

			CaixaProdutoVisualizavel caixaProdutoVisualizavel = null;

			if (podeBuscar) {

				if (urlKit) {

					caixaProdutoVisualizavel = Kit.recuperarKitParaVenda(dao, codigo);

				} else if (!urlKit
						&& (podeBuscar || (caixaProdutoSelecionado != null && caixaProdutoSelecionado.eKit()))) {

					caixaProdutoVisualizavel = Produto.recuperarProdutoParaVenda(dao, codigo,
							gerenteLogin.getFilialCodigo(), getPrecificacao().getPrecoBaseCodigo(),
							parametroWebMB.getParametroWeb().isExibeProdutoSemEstoque());
				}

				if (caixaProdutoVisualizavel != null) {

					try {

						preencherAtributos(caixaProdutoVisualizavel);

						inserirOuVisualizarCaixaProduto(caixaProdutoVisualizavel, false, true);

					} catch (Exception e) {
						e.printStackTrace();
					}
				} else {
					lancarExececaoGenerica(propriedade.getMensagem("mensagem.nenhumProdutoEncontrado"));
				}
			}
		}
	}

	/**
	 * Insere ou Visualiza a caixa utilizando a seguinte regra: </br>
	 * </br>
	 * 
	 * <ul>
	 * <li>Caso a caixa permita a venda : Insere</li>
	 * <li>Caso a caixa não permita a venda : Visualiza</li>
	 * </ul>
	 * 
	 * <hr/>
	 * <br/>
	 * 
	 * <strong>O metodo utilizado para validar se a caixa permite a venda:<br/>
	 * </strong>
	 * {@link GerentePedidoMB#isPermiteVenda(CaixaProdutoVisualizavel)} <br/>
	 * 
	 * <br/>
	 * <strong> Metodos chamados para a inserção e visualização: <br/>
	 * </strong>
	 * 
	 * {@link ItemPedidoMB#inserirCaixaProduto(CaixaProdutoVisualizavel, boolean)}
	 * <br/>
	 * {@link ItemPedidoMB#visualizarDetalhes(CaixaProdutoVisualizavel, boolean)}
	 * <br/>
	 * <br/>
	 * 
	 * @param caixaProdutoVisualizavel
	 * @param carregarCaixaNovamente
	 *            Se busca novamente o produto ou o Kit no banco de dados
	 * @param preencheDadosAdicionais
	 *            Se vai carregar os produtos simililares, midias, informaçoes
	 *            para o produto...
	 *            {@link #carregarDadosDaCaixaProdutoSelecionado(boolean, boolean)}
	 * 
	 * 
	 * @return TRUE caso o produto tenha permita ser inserido, caso contrario
	 *         FALSE
	 */
	private boolean inserirOuVisualizarCaixaProduto(CaixaProdutoVisualizavel caixaProdutoVisualizavel,
			boolean carregarCaixaNovamente, boolean preencheDadosAdicionais) {

		if (caixaProdutoVisualizavel == null) {
			return false;
		}

		if (gerentePedidoMB.isPermiteVenda(caixaProdutoVisualizavel)) {

			inserirCaixaProduto(caixaProdutoVisualizavel, carregarCaixaNovamente, preencheDadosAdicionais);
			return true;
		} else {
			visualizarDetalhes(caixaProdutoVisualizavel, carregarCaixaNovamente);
			return false;
		}
	}

	/**
	 * Insere a caixa de produto opedendo ou não carregalo novamente via
	 * parametro
	 * 
	 * @param caixaProdutoVisualizavel
	 *            O produto ou o kit a ser inserido
	 * @param carregarCaixaNovamente
	 *            Se busca novamente o produto ou o Kit no banco de dados
	 * 
	 *            *
	 * @see ItemPedidoMB#inserirOuVisualizarCaixaProduto(CaixaProdutoVisualizavel)
	 */
	private void inserirCaixaProduto(CaixaProdutoVisualizavel caixaProdutoVisualizavel, boolean carregarCaixaNovamente,
			boolean preencheDadosAdicionais) {

		this.acao = Acao.INCLUIR;

		try {
			carregarCaixaProdutoVisualizavel(caixaProdutoVisualizavel, carregarCaixaNovamente, preencheDadosAdicionais);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void carregarCaixaProdutoVisualizavel(CaixaProdutoVisualizavel caixaProdutoVisualizavel,
			boolean carregarCaixaNovamente, boolean preencheDadosAdicionais) throws Exception {
		super.carregarCaixaProdutoVisualizavel(caixaProdutoVisualizavel, carregarCaixaNovamente,
				preencheDadosAdicionais);

		if (gerenteLogin.isPerfilColaborador() && parametros.getParametro2().isVisualizaUltimoPrecoNegociado()
				&& gerentePedidoMB.getClienteCodigo() != null && gerentePedidoMB.getClienteCodigo() > 0
				&& caixaProdutoVisualizavel.eProduto()) {

			recuperarENotificarUltimoPrecoPraticado();

		}
	}

	private void recuperarENotificarUltimoPrecoPraticado() {

		try {
			ItemPedidoPrecoNegociado ultimoPreco = ItemPedidoPrecoNegociado.ultimoPrecoNegociado(dao, getFilialCodigo(),
					getCaixaProdutoSelecionado().getCodigo(), gerentePedidoMB.getClienteCodigo());

			if (ultimoPreco != null) {

				String msg = ultimoPreco.getMensagemUsuario(getPropriedade(), "<br>");

				exibirMensagemInformacao(getPropriedade().getMensagem("titulo.ultimanegociacao"), msg);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Realiza a chamada dos metodos em comum da edição e exclusao que sao
	 * {@link #carregarCaixaProdutoVisualizavel(IItemPedidoWeb, boolean)} e
	 * {@link #carregarDadosItemPedidoEdicao(IItemPedidoWeb)}
	 * 
	 * @param itemPedido
	 * @param preencheDadosAdicionais
	 * @throws Exception
	 */
	private void carregarCaixaProdutoVisualizavelEdicaoOuExclusao(IItemPedidoWeb itemPedido,
			boolean preencheDadosAdicionais) throws Exception {

		try {

			IItemPedidoWeb itemClone = (IItemPedidoWeb) itemPedido.clone();

			IItemPedidoWeb itemAlvo = itemClone == null ? itemPedido : itemClone;

			carregarCaixaProdutoVisualizavel(itemAlvo, preencheDadosAdicionais);
			carregarDadosItemPedidoEdicao(itemAlvo);

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * 
	 * Carrega os dados do item pedido com quantidade e preço
	 * 
	 * @param itemPedido
	 * @throws KitNaoEncotradoExcecao
	 */
	private void carregarDadosItemPedidoEdicao(IItemPedidoWeb itemPedido) throws KitNaoEncotradoExcecao {

		if (ekit()) {
			IKitPedido kitPedido = gerentePedidoMB.getGerentePedido().getKitPedido(getKitSelecionado());

			setPrecoVendaValor(kitPedido.getPreco());
			setQuantidade(kitPedido.getQuantidade());

		} else {

			if (itemPedido.getLocalEstoqueCodigo() != null && itemPedido.getLocalEstoqueCodigo() > 0) {

				setLocalEstoqueSelecionado(itemPedido.getLocalEstoqueCodigo());
			}

			if (itemPedido.getLote() != null && itemPedido.getLote().trim().isEmpty()) {
				setLoteSelecionado(itemPedido.getLote());
			}

			if (itemPedido.getPromocaoNumero() != null && itemPedido.getPromocaoNumero() > 0) {

				setPromocaoSelecionada(itemPedido.getPromocaoNumero());
			}

			if (itemPedido.getProdutoUnidade() != null) {
				unidadeSelecionada = (ProdutoUnidade) itemPedido.getProdutoUnidade();
			}

			itemPedido.setQuantidadeAnterior(itemPedido.getQuantidade());

			setQuantidade(itemPedido.getQuantidade());

			carregarValores(itemPedido);

		}
	}

	/**
	 * Carrega os valores que o item foi vendido levando em consideração o
	 * acrescimo e desconto
	 * 
	 * @param itemPedido
	 */
	private void carregarValores(IItemPedidoWeb itemPedido) {

		precoVenda.setPrecoVenda(itemPedido.getPrecoVenda());
		precoVenda.setPrecoSugerido(itemPedido.getPrecoSugerido());
		precoVenda.setPrecoOriginal(itemPedido.getPrecoVendaOriginal());

		carregarValores(precoVenda);

		setPrecoVendaValor(itemPedido.getPrecoVenda());
	}

	/**
	 * Recebe o item pedido a ser alterado
	 * 
	 * @param itemPedido
	 * @throws Exception
	 */
	public void alterarItem(IItemPedidoWeb itemPedido) throws Exception {

		alterarItem(itemPedido, true);

	}

	/**
	 * Item inicia a alteração do Item de pedido
	 * 
	 * @param itemPedido
	 * @param preencheDadosAdicionais
	 * @throws Exception
	 */
	private void alterarItem(IItemPedidoWeb itemPedido, boolean preencheDadosAdicionais) throws Exception {
		acao = Acao.EDITAR;
		carregarCaixaProdutoVisualizavelEdicaoOuExclusao(itemPedido, preencheDadosAdicionais);
	}

	/**
	 * Recebe o item pedido para a exclusao
	 * 
	 * @param itemPedido
	 * @throws Exception
	 */
	public void excluirItem(IItemPedidoWeb itemPedido) throws Exception {
		acao = Acao.EXCLUIR;
		carregarCaixaProdutoVisualizavelEdicaoOuExclusao(itemPedido, true);
	}

	/**
	 * Inclue o item no banco de dados
	 * 
	 * @param gerentePedido
	 * 
	 * @param item
	 * @throws SpaceExcecao
	 * @throws Exception
	 */
	private void incluirItem(GerentePedido gerentePedido) throws SpaceExcecao {
		if (eProduto()) {
			incluirProduto(gerentePedido);
		} else if (ekit()) {
			incluirKit(gerentePedido);
		}
	}

	/**
	 * Altera o item no banco de dados.
	 * 
	 * @param gerentePedido
	 * @throws SpaceExcecao
	 * @throws CloneNotSupportedException
	 */
	private void alterarItem(GerentePedido gerentePedido) throws SpaceExcecao, CloneNotSupportedException {

		if (ekit()) {
			alterarKit(gerentePedido);

		} else if (eProduto()) {
			alterarProduto(gerentePedido);
		}

	}

	/**
	 * Inclui o item pedido de um produto no banco de dados
	 * 
	 * @param gerentePedido
	 * @throws SpaceExcecao
	 */
	private void incluirProduto(GerentePedido gerentePedido) throws SpaceExcecao {

		IItemPedidoWeb item = gerentePedido.criarItemPedido(getProdutoSelecionado(), unidadeSelecionada, false);

		popularItemPedidoProduto(item);

		item.setGrupoComissaoCodigo(gerentePedido.obterGrupoComissao(item));

		try {
			gerentePedido.adicionarItem(item);

		} catch (Exception e) {
			itemPedidoErro = item;
			throw e;
		}
	}

	/**
	 * Altera o item pedido no banco de dados
	 * 
	 * @param gerentePedido
	 * @throws SpaceExcecao
	 * @throws CloneNotSupportedException
	 */
	private void alterarProduto(GerentePedido gerentePedido) throws SpaceExcecao, CloneNotSupportedException {

		popularItemPedidoProduto(itemPedidoProdutoSelecionado);

		try {

			gerentePedido.alterarItem(itemPedidoProdutoSelecionado, OperacaoItem.ALTERAR);

		} catch (SpaceExcecao | CloneNotSupportedException e) {
			itemPedidoErro = itemPedidoProdutoSelecionado;
			throw e;
		}

	}

	/**
	 * Inclui um kit no banco de dados ou seja o kitpedido e os itens de pedido
	 * do kit
	 * 
	 * @param gerentePedido
	 * @throws SpaceExcecao
	 */
	private void incluirKit(GerentePedido gerentePedido) throws SpaceExcecao {

		Kit kit = getKitSelecionado();

		try {
			gerentePedido.adicionarKit(kit, quantidade);

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Aletra no banco o kit
	 * 
	 * @param gerentePedido
	 * @throws SpaceExcecao
	 */
	private void alterarKit(GerentePedido gerentePedido) throws SpaceExcecao {

		Kit kit = getKitSelecionado();

		try {
			gerentePedido.alterarKit(kit, quantidade);

		} catch (Exception e) {
			throw e;
		}
	}

	/**
	 * Exclui o item no banco de dados
	 * 
	 * @param gerentePedido
	 * @throws SpaceExcecao
	 */
	private void excluirItem(GerentePedido gerentePedido) throws SpaceExcecao {

		if (ekit()) {
			gerentePedido.excluirKit(getKitSelecionado());
		} else if (eProduto() && itemPedidoProdutoSelecionado != null) {
			gerentePedido.excluirItem(itemPedidoProdutoSelecionado);
		}
	}

	/**
	 * Preenche os dados do item pedido em parametro como preço, oferta, lote,
	 * local entre outros.
	 * 
	 * @param item
	 *            O item que sera populado
	 */
	private void popularItemPedidoProduto(IItemPedidoWeb item) {

		if (item == null) {
			return;
		}

		item.setPrecoSugerido(precoSugeridoValor);
		item.setPrecoLiquido(precoVendaValor);
		item.setPrecoVenda(precoVendaValor);
		item.setPrecoVendaOriginal(precoOriginalProduto);
		item.setQuantidade(quantidade);
		item.setQuantidadeVolumes(quantidadeVolumes);

		if (precoVenda.getNumeroOferta() > 0) {
			item.setOfertaNumero(precoVenda.getNumeroOferta());
		} else {
			item.setOfertaNumero(0);
		}

		if (loteSelecionado != null) {
			item.setLote(loteSelecionado.getProdutoLotePK().getPleLote());
		} else {
			item.setLote(null);
		}

		/*
		 * Por enquanto, localestoqueselecionado está desabilidado
		 */
		// if (localEstoqueSelecionado != null) {
		// item.setLocalEstoqueCodigo(localEstoqueSelecionado.getCodigo());
		// } else {
		// item.setLocalEstoqueCodigo(0);
		// }

		if (promocaoSelecionada != null) {
			item.setPromocao(promocaoSelecionada);
			item.setPromocaoNumero(promocaoSelecionada.getNumero());
		} else {
			item.setPromocao(null);
			item.setPromocaoNumero(0);
		}
	}

	/**
	 * Carrega a caixa de produto a partir de um item pedido
	 * 
	 * @param itemPedido
	 * @throws Exception
	 */
	private void carregarCaixaProdutoVisualizavel(IItemPedidoWeb itemPedido, boolean preencheDadosAdicionais)
			throws Exception {

		boolean eKit = itemPedido.getKitCodigo() > 0;

		int codigoCaixa = eKit ? itemPedido.getKitCodigo() : itemPedido.getProdutoCodigo();

		carregarCaixaProdutoVisualizavel(codigoCaixa, eKit, preencheDadosAdicionais);

		if (eProduto()) {
			this.itemPedidoProdutoSelecionado = itemPedido;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoDetalheMB#
	 * prepararVariaveis()
	 */
	@Override
	protected void prepararVariaveis() {

		super.prepararVariaveis();

		this.loteSelecionado = null;
		this.lotes = null;
		this.localEstoqueSelecionado = null;
		this.locaisEstoque = null;
		this.promocaoSelecionada = null;
		this.listasPromocoes = null;

		this.itemPedidoProdutoSelecionado = null;
		this.itemPedidoErro = null;

		this.unidades = null;
		this.unidadeSelecionada = null;

		this.quantidade = 1;
		this.quantidadeVolumes = 0;
		this.descontoAcrescimoValor = 0;
		this.descontoAcrescimoPorcentagem = 0;

		this.flagAcrescDesc = FLAG_DESCONTO;
		this.exibirImagens = true;
		this.descricaoPromocaoSelecionada = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoDetalheMB#
	 * carregarDadosDaCaixaProdutoSelecionado()
	 */
	@Override
	protected void carregarDadosDaCaixaProdutoSelecionado(boolean preencherAtributosNovamente,
			boolean preencheDadosadicionais) throws Exception {

		super.carregarDadosDaCaixaProdutoSelecionado(preencherAtributosNovamente, preencheDadosadicionais);

		localEstoqueSelecionado = null;
		loteSelecionado = null;

		setLotes(null);
		setLocaisEstoque(null);

		if (caixaProdutoSelecionado.eProduto()) {

			Produto produto = getProdutoSelecionado();

			setUnidades(ProdutoUnidade.recuperarUnidadesProdutoParaVenda(dao, produto.getCodigo()));

			setUnidadeSelecionada(produto.getProdutoUnidadeVenda());

			/*
			 * Local e lote no pedido - por enquanto, não habilitado na venda
			 * WEB
			 * 
			 * setLotes(ProdutoLote.recuperaLotes(dao, produto.getCodigo(),
			 * codigoFilial));
			 * 
			 * setLocaisEstoque(LocalEstoque.recuperaLocaisEstoque(dao,
			 * codigoFilial, codigoColaborador));
			 */

			if (locaisEstoque != null && locaisEstoque.size() > 0) {
				localEstoqueSelecionado = locaisEstoque.get(0);
			}

			if (lotes != null && lotes.size() > 0) {
				loteSelecionado = lotes.get(0);
			}

			if (produto.getEmPromocao()) {
				recuperarCarregarListaPromocoes(produto);
			}
		}
		carregarValores(precoVenda);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoDetalheMB#
	 * carregarValores
	 * (br.com.space.api.negocio.modelo.dominio.produto.PrecoVenda)
	 */
	@Override
	protected void carregarValores(PrecoVenda precoVenda) {

		super.carregarValores(precoVenda);

		this.precoTabelaValor = precoVenda.getPrecoTabela();
		this.precoVendaValor = precoVenda.getPrecoVenda();
		this.precoOriginalProduto = precoVenda.getPrecoOriginal();
		this.precoSugeridoValor = precoVenda.getPrecoSugerido();

		zerarDescontoAcrescimo();
		carregarPrecoUnitario(precoVendaValor);
	}

	/**
	 * Recupera do banco de dados todas as promoçoes disponiveis para o produto
	 * em parametro. <br/>
	 * 
	 * Carrega as lista de promocão atraves do metodo
	 * {@link ItemPedidoMB#classificarListasPromocao(List)}
	 * 
	 * @param produto
	 */
	private void recuperarCarregarListaPromocoes(Produto produto) {

		System.out.println(this.gerentePedidoMB.getGerentePedido().getPromocaoVenda());

		List<Promocao> promocoes = this.gerentePedidoMB.getGerentePedido().getPromocaoVenda()
				.recuperarPromocoesItem(produto);

		classificarListasPromocao(promocoes);

	}

	/**
	 * Classifica a lista de promocoes em parametro de acondo com seu tipo sendo
	 * por desconto, produto bonificado e linha
	 * 
	 * @param promocoes
	 *            Uma lista de promoção a serem classificadas
	 */
	private void classificarListasPromocao(List<Promocao> promocoes) {

		if (ListUtil.isValida(promocoes)) {
			listasPromocoes = new ArrayList<>();
			List<Promocao> promocaoDesconto = new ArrayList<>();
			List<Promocao> promocaoProBonificado = new ArrayList<>();
			List<Promocao> promocaoLinha = new ArrayList<>();

			for (Promocao promocao : promocoes) {

				if (promocao.getTipoPromocaoItem() == 1) {
					promocaoDesconto.add(promocao);

				} else if (promocao.getTipoPromocaoItem() == 2) {
					promocaoProBonificado.add(promocao);

				} else if (promocao.getTipoPromocaoItem() == 3)
					promocaoLinha.add(promocao);
			}

			listasPromocoes.add(promocaoDesconto);
			listasPromocoes.add(promocaoProBonificado);
			listasPromocoes.add(promocaoLinha);
		}

	}

	/**
	 * Verifica se a promoção seleciona é valida
	 * 
	 * @return TRUE caso a promoção esteja valida
	 */
	public boolean verificarPromocaoValida() {
		return this.verificarPromocaoValida(this.promocaoSelecionada);
	}

	/**
	 * Verifica se a promoção em parametro é válida. <br/>
	 * 
	 * <br/>
	 * <ul>
	 * <li>Verifica se a quantidade atende à promoção</li>
	 * <li>Verifica se a quantidade não excede ao estoque destinado para a
	 * promoção</li>
	 * <li>Se a quantidade máxima for 0 (zero), subentende-se que a promoção
	 * possui quantidade ilimitada</li>
	 * </ul>
	 * 
	 * @param promocao
	 * @return TRUE caso a promoção esteja valida
	 */
	public boolean verificarPromocaoValida(Promocao promocao) {

		if (promocao != null && caixaProdutoSelecionado.eProduto()) {
			return this.gerentePedidoMB.getGerentePedido().getPromocaoVenda().verificarPromocaoAtingidaItem(
					(Produto) caixaProdutoSelecionado, quantidade, unidadeSelecionada.getFatorEstoque(), promocao);
		}
		return false;
	}

	/**
	 * Retorna a melhor promoção de acordo com a quantidade solicitada.
	 * 
	 * 
	 * @deprecated substituir pelos metodos da gerentePedidoMB
	 *             .getGerentePedido().getPromocaoVenda().obterMelhorPromocaoItem
	 * @return A melhor promoção
	 */
	@Deprecated
	private Promocao obterMelhorPromocaoItem() {
		Promocao melhor = null;

		if (listasPromocoes == null)
			return null;

		for (List<Promocao> lista : listasPromocoes) {
			for (Promocao promocao : lista) {
				/*
				 * Verifica se a quantidade da promocao é superior a anterior
				 * selecionada. Normalmente quanto maior a quantidade melhor a
				 * promoção.
				 */
				if (((melhor == null)
						|| (this.gerentePedidoMB.getGerentePedido().getPromocaoVenda().isMelhor(melhor, promocao)))
						&& this.verificarPromocaoValida(promocao)) {
					melhor = promocao;
				}
			}
		}

		return melhor;
	}

	/**
	 * Implementa promoção selecionada de acordo com seu tipo
	 */
	private void aplicarPromocaoSelecionada() {
		if (promocaoSelecionada.getTipoPromocaoItem() == 1) {// desconto
			/*
			 * O Desconto já é aplicado diretamente no preço de tabela.
			 */
		} else if (promocaoSelecionada.getTipoPromocaoItem() == 2) {// bonificado
			/*
			 * adicionar item bonificado na lista
			 */
		} else if (promocaoSelecionada.getTipoPromocaoItem() == 3) {// linha

		}
	}

	/**
	 * Preenche promocaoSelecionada.
	 * 
	 * @param promocao
	 */
	public void selecionarPromocao(Promocao promocao) {
		setPromocaoSelecionada(promocao);
	}

	/**
	 * Apaga promoção selecionada e sua descrição.
	 */
	public void limparPromocaoSelecionada() {
		setPromocaoSelecionada(null);
	}

	/**
	 * @param promocao
	 * @return Uma String que corresponde a descrição textual da promoção em
	 *         parametro
	 */
	public String obterDescricaoPromocao(CaixaProdutoVisualizavel caixaProdutoVisualizavel, Promocao promocao) {
		String desc = null;

		if (promocao != null) {
			StringBuilder descricao = new StringBuilder();
			if (promocao.getTipoPromocaoItem() == 1) {
				descricao.append(propriedade.getMensagem("texto.descontoDe"));
				if (caixaProdutoVisualizavel != null && caixaProdutoVisualizavel.getEmOferta())
					descricao.append(formatacaoMB.convertePorcentagem(promocao.getIndiceOferta()));
				else
					descricao.append(formatacaoMB.convertePorcentagem(promocao.getIndiceNormal()));
				descricao.append("%");
			} else if (promocao.getTipoPromocaoItem() == 2) {
				descricao.append(
						propriedade.getMensagem("titulo.produtoBonificado") + " - " + promocao.getQuantidadeBonificada()
								+ " " + Conversao.nvl(promocao.getProdutoBonificado().getUnidadeDescricao(), "") + " "
								+ propriedade.getMensagem("texto.de") + promocao.getProdutoBonificado().getDescricao()
								+ " (" + promocao.getProdutoBonificado().getCodigo() + ")");
			} else if (promocao.getTipoPromocaoItem() == 3) {
				descricao.append(propriedade.getMensagem("texto.codigoDaLinha") + promocao.getLinhaProdutoCodigo());
			}
			desc = descricao.toString();
		}

		return desc;
	}

	/**
	 * @return Uma String que corresponde a descrição textual da promoção
	 *         selecionda
	 */
	public String obterDescricaoPromocaoSelecionada() {
		return obterDescricaoPromocao(caixaProdutoSelecionado, this.promocaoSelecionada);
	}

	/**
	 * Carrega novamente o preço de venda
	 */
	private void recalcularPreco() {

		PrecoVenda precoVenda = null;

		if (eProduto()) {
			precoVenda = getPrecificacao().obterPrecoVendaSugerido(getProdutoSelecionado(), unidadeSelecionada,
					promocaoSelecionada, true);
		}

		if (precoVenda != null) {
			carregarValores(precoVenda);
		}

	}

	/**
	 * Calcula o preço de venda levando em consideração a promoção em parametro
	 * <br/>
	 * 
	 * Metodo utilizado pela tela
	 * 
	 * @param promocao
	 * 
	 * @return O preço sugerido para o produto com a promoção
	 */
	public double calcularPrecoPromocao(Promocao promocao) {

		PrecoVenda precoVenda = getPrecificacao().obterPrecoVendaSugerido(getProdutoSelecionado(), unidadeSelecionada,
				promocao, true);

		if (precoVenda != null)
			return precoVenda.getPrecoSugerido();
		else
			return 0.0;
	}

	public List<ProdutoUnidade> getUnidades() {
		return unidades;
	}

	public void setUnidades(List<ProdutoUnidade> unidades) {
		this.unidades = unidades;
	}

	public ProdutoUnidade getUnidadeSelecionada() {
		return unidadeSelecionada;
	}

	public void setUnidadeSelecionada(ProdutoUnidade unidadeSelecionada) {
		this.unidadeSelecionada = unidadeSelecionada;
	}

	public String getUnidadeSelecionadaID() {
		return unidadeSelecionada.getProdutoUnidadePK().getID();
	}

	/**
	 * Pesquisa na lista de unidades qual é a unidade que corresponde com o id
	 * informado. <br/>
	 * Se encontrou recalcula o preço
	 * 
	 * @param id
	 *            O id do produto unidade em {@link ProdutoUnidadePK#getID()}
	 */
	public void setUnidadeSelecionadaID(String id) {

		for (ProdutoUnidade unidade : unidades) {
			if (unidade.getProdutoUnidadePK().getID().equals(id)) {

				unidadeSelecionada = unidade;
				atualizarPromocao();
				recalcularPreco();
				break;
			}
		}
	}

	public String getFlagAcrescDesc() {
		return flagAcrescDesc;
	}

	public void setFlagAcrescDesc(String flagAcrescDesc) {
		this.flagAcrescDesc = flagAcrescDesc;
	}

	public List<ProdutoLote> getLotes() {
		return lotes;
	}

	public void setLotes(List<ProdutoLote> lotes) {
		this.lotes = lotes;
	}

	public List<List<Promocao>> getListasPromocoes() {
		return listasPromocoes;
	}

	public void setListasPromocoes(List<List<Promocao>> listasPromocoes) {
		this.listasPromocoes = listasPromocoes;
	}

	public ProdutoLote getLoteSelecionado() {
		return loteSelecionado;
	}

	public void setLoteSelecionado(ProdutoLote loteSelecionado) {
		this.loteSelecionado = loteSelecionado;
	}

	public String getLoteSelecionadoID() {
		return loteSelecionado.getProdutoLotePK().getID();

	}

	/**
	 * Seta o lote selecionado a partir do codigo do lote representado por uma
	 * String
	 * 
	 * @param loteCodigo
	 */
	public void setLoteSelecionado(String loteCodigo) {

		if (lotes != null && lotes.size() > 0) {

			for (ProdutoLote lote : lotes) {

				if (lote.getProdutoLotePK().getPleLote().equals(loteCodigo)) {
					setLoteSelecionado(lote);
					break;
				}
			}
		}
	}

	/**
	 * Seta o lote seleciodo através di ID
	 * 
	 * @param loteSelecionadoID
	 *            o ID do lote {@link ProdutoLotePK#getID()}
	 */
	public void setLoteSelecionadoID(String loteSelecionadoID) {

		if (lotes != null && lotes.size() > 0) {

			for (ProdutoLote lote : lotes) {

				if (lote.getProdutoLotePK().getID().equals(loteSelecionadoID)) {
					setLoteSelecionado(lote);
					break;
				}
			}
		}
	}

	public List<LocalEstoque> getLocaisEstoque() {
		return locaisEstoque;
	}

	public void setLocaisEstoque(List<LocalEstoque> locaisEstoque) {
		this.locaisEstoque = locaisEstoque;
	}

	public LocalEstoque getLocalEstoqueSelecionado() {
		return localEstoqueSelecionado;
	}

	/**
	 * Seta o local de estoque a partir do codigo
	 * 
	 * @param codigo
	 */
	public void setLocalEstoqueSelecionado(int codigo) {

		if (locaisEstoque != null && locaisEstoque.size() > 0 && codigo > 0) {

			for (LocalEstoque local : locaisEstoque) {
				if (local.getCodigo() == codigo) {
					setLocalEstoqueSelecionado(local);
					break;
				}
			}
		}
	}

	public void setLocalEstoqueSelecionado(LocalEstoque localEstoqueSelecionado) {
		this.localEstoqueSelecionado = localEstoqueSelecionado;
	}

	public String getLocalEstoqueSelecionadoID() {
		return Integer.toString(localEstoqueSelecionado.getCodigo());
	}

	/**
	 * Seta o local de estoque a partir do ID
	 * 
	 * @param localEstoqueSelecionadoID
	 *            O ID do local de estoque {@link LocalEstoque#getCodigo()}
	 */
	public void setLocalEstoqueSelecionadoID(String localEstoqueSelecionadoID) {

		if (localEstoqueSelecionadoID != null) {

			int codigoLocal = Integer.parseInt(localEstoqueSelecionadoID);

			for (LocalEstoque local : locaisEstoque) {

				if (local.getCodigo() == codigoLocal) {
					localEstoqueSelecionado = local;
					break;
				}

			}
		}
	}

	public double getPrecoFinalValor() {
		return precoVendaValor * quantidade;
	}

	public Promocao getPromocaoSelecionada() {
		return promocaoSelecionada;
	}

	/**
	 * Verifica se a promoção selecionada é valida. e modifica a descrição da
	 * promoção selecionda
	 * 
	 * <ul>
	 * <li>Caso Valida Aplica a promoção selecionado <strong>(Não esta sendo
	 * feito nada)</strong></li>
	 * <li>Caso invalida atribui a promoção selecionada NULL
	 * </ul>
	 */
	private void verificarPromocaoSelecionada() {

		if (verificarPromocaoValida())
			aplicarPromocaoSelecionada();
		else
			this.promocaoSelecionada = null;

		setDescricaoPromocaoSelecionada(obterDescricaoPromocaoSelecionada());
	}

	/**
	 * 
	 * @param promocaoSelecionada
	 */
	public void setPromocaoSelecionada(Promocao promocaoSelecionada) {

		int promocaoAnteriorNumero = 0;
		int promocaoSelecionadaNumero = 0;

		if (this.promocaoSelecionada != null)
			promocaoAnteriorNumero = this.promocaoSelecionada.getNumero();

		if (promocaoSelecionada != null)
			promocaoSelecionadaNumero = promocaoSelecionada.getNumero();

		this.promocaoSelecionada = promocaoSelecionada;
		atualizarQuantidadeParaAtenderPromocao();

		this.verificarPromocaoSelecionada();

		if (promocaoAnteriorNumero != promocaoSelecionadaNumero)
			recalcularPreco();
	}

	private void atualizarQuantidadeParaAtenderPromocao() {

		// Quando a unidade selecionada é null por que a caixa de produto
		// seleciona é KIT
		if (unidadeSelecionada == null) {
			return;
		}

		double quantidadeProduto = quantidade * unidadeSelecionada.getFatorEstoque();

		if (promocaoSelecionada != null && promocaoSelecionada.getQuantidadeInicio() > quantidadeProduto) {

			double novaQuantidade = promocaoSelecionada.getQuantidadeInicio() / unidadeSelecionada.getFatorEstoque();

			novaQuantidade = arredondarQuantidade(novaQuantidade);

			if (unidadeSelecionada.getFlagPermiteFracionamento() == 0) {

				int inteiro = (int) novaQuantidade;

				if (novaQuantidade > inteiro) {
					novaQuantidade = Conversao.arredondarProximoInteiro(novaQuantidade);
				}

			} else {

				boolean fracaoCorreta = verificarFracaoCorreta(novaQuantidade);

				double fracaoMinima = unidadeSelecionada.getFracionamentoMinimo();

				if (!fracaoCorreta) {
					do {
						novaQuantidade = arredondarQuantidade(Conversao.proximoMultiplo(novaQuantidade, fracaoMinima));

					} while (!verificarFracionamentoEConformidadeComPromocao(novaQuantidade));
				}
			}
			setQuantidade(novaQuantidade, false);
		}
	}

	private boolean verificarFracionamentoEConformidadeComPromocao(double quantidade) {

		boolean isMultiplo = Conversao.isMultiplo(quantidade, unidadeSelecionada.getFracionamentoMinimo(),
				gerentePedidoMB.getParametros().getParametro1().getCasasDecimaisParaQuantidade());

		boolean isValidoPromocao = gerentePedidoMB.getGerentePedido().getPromocaoVenda().verificarPromocaoAtingidaItem(
				getProdutoSelecionado(), quantidade, unidadeSelecionada.getFatorEstoque(), promocaoSelecionada);

		return isMultiplo && isValidoPromocao;
	}

	private boolean verificarFracaoCorreta(double quatidade) {
		return Conversao.isMultiplo(quatidade, unidadeSelecionada.getFracionamentoMinimo(),
				gerentePedidoMB.getParametros().getParametro1().getCasasDecimaisParaQuantidade());
	}

	private double arredondarQuantidade(double quantidade) {
		return Conversao.arredondar(quantidade,
				gerentePedidoMB.getParametros().getParametro1().getCasasDecimaisParaQuantidade());
	}

	/**
	 * 
	 * @param numeroPromocao
	 */
	private void setPromocaoSelecionada(int numeroPromocao) {

		if (listasPromocoes != null && listasPromocoes.size() > 0) {

			boolean achou = false;

			for (List<Promocao> promocoes : listasPromocoes) {

				if (promocoes != null && promocoes.size() > 0) {

					for (Promocao promocao2 : promocoes) {

						achou = promocao2.getNumero() == numeroPromocao;

						if (achou) {
							setPromocaoSelecionada(promocao2);
							break;
						}
					}
				}

				if (achou) {
					break;
				}
			}
		}

	}

	/**
	 * @return A descrição da promoção caso NULL é exibida uma mensagem (Clique
	 *         em uma promoção para selecioná-la.)
	 */
	public String getDescricaoPromocaoSelecionada() {
		if (descricaoPromocaoSelecionada == null) {

			return propriedade.getMensagem("mensagem.promocao.cliqueselecionar");
		}
		return descricaoPromocaoSelecionada;
	}

	public void setDescricaoPromocaoSelecionada(String descricaoPromocaoSelecionada) {
		this.descricaoPromocaoSelecionada = descricaoPromocaoSelecionada;
	}

	public boolean getExibirImagens() {
		return exibirImagens;
	}

	public void setExibirImagens(boolean exibirImagens) {
		this.exibirImagens = exibirImagens;
	}

	public boolean isDesabilitarCampos() {
		return acao == Acao.EXCLUIR || acao == Acao.VISUALIZAR;
	}

	public boolean isIncluindoItem() {
		return acao == Acao.INCLUIR;
	}

	/**
	 * Evento disparado quando a quantidade é alterada.
	 * 
	 * @param e
	 */
	public void quantidadeValueChange(ValueChangeEvent e) {

		if (e.getNewValue() != null && e.getNewValue() != "") {

			double quantidadeNova = Double.parseDouble(e.getNewValue().toString());

			this.quantidade = quantidadeNova;
		}
	}

	/**
	 * Altera o item no banco de dados.
	 * 
	 * Metodo usado pelo carrinho
	 * 
	 * @throws Exception
	 * 
	 * 
	 */
	public void alterarQuantidadeCarrinho(IItemPedidoWeb item) throws Exception {

		if (item.isPermiteAlterarQuantidadeNoList()) {

			double qtdeAnt = item.getQuantidade();

			try {
				if (item.getKitCodigo() == 0) {
					alterarItem(item, true);
					setQuantidade(item.getQuantidadeCarrinho());
					confirmar();
				}
			} catch (Exception e) {
				item.setQuantidade(qtdeAnt);
				item.setQuantidadeAnterior(qtdeAnt);

				if (e instanceof IExcecaoAutorizavel) {
					throw e;
				} else {
					e.printStackTrace();
				}
			}
		}
	}

	public void loteValueChange(ValueChangeEvent e) {

		if (e.getNewValue() != null) {

			setLoteSelecionadoID(e.getNewValue().toString());
		}
	}

	public void localValueChange(ValueChangeEvent e) {

		if (e.getNewValue() != null) {

			setLocalEstoqueSelecionadoID(e.getNewValue().toString());
		}
	}

	/**
	 * Evento disparado quando a unidade é alterada.
	 * 
	 * @param e
	 */
	public void unidadeChangeListener(ValueChangeEvent e) {

		String valor = e.getNewValue().toString();

		setUnidadeSelecionadaID(valor);

	}

	public void precoVendaChange(ValueChangeEvent e) {

		if (e.getNewValue() != null) {

			setPrecoVendaValor((double) e.getNewValue());

		}
	}

	public void precoUnitarioChange(ValueChangeEvent e) {
		if (e.getNewValue() != null) {

			setPrecoUnitarioValor(precoUnitarioValor);
		}
	}

	public void acrescDescPercentualChange(ValueChangeEvent e) {

		if (e.getNewValue() != null) {

			descontoAcrescimoPorcentagem = (double) e.getNewValue();

			if (flagAcrescDesc.equals(FLAG_DESCONTO))

				descontoAcrescimoPorcentagem = getPrecificacao().calcularPercentualMaximo((double) precoSugeridoValor,
						descontoAcrescimoPorcentagem, 2);

			double descontoAcrescimoValorAux = (double) precoSugeridoValor * (descontoAcrescimoPorcentagem / 100);

			descontoAcrescimoValorAux = Conversao.arredondar(descontoAcrescimoValorAux, 2);

			setDescontoAcrescimoValor(descontoAcrescimoValorAux);
		}
	}

	/**
	 * 
	 * @param e
	 */
	public void acrescDescValorChange(ValueChangeEvent e) {

		if (e.getNewValue() != null) {

			setDescontoAcrescimoValor((double) e.getNewValue());

			double descontoAcrescimoPorcentagem = Conversao
					.arredondar((double) (descontoAcrescimoValor / precoSugeridoValor) * 100, 2);

			setDescontoAcrescimoPorcentagem(descontoAcrescimoPorcentagem);
		}
	}

	/**
	 * Calcula o preço unitario a partir do preço de venda <br/>
	 * precoUnitarioValor = precoVendaValor / unidadeSelecionada.getQuantidade()
	 * 
	 */
	private double calcularPrecoUnitario(double precoDeVenda) {
		double precoUnitarioValor = 0;

		if (unidadeSelecionada != null && unidadeSelecionada.getQuantidade() > 0)
			precoUnitarioValor = precoVendaValor / unidadeSelecionada.getQuantidade();

		return precoUnitarioValor;
	}

	/**
	 * Carrega o preço unitario a partir do preço de venda
	 * 
	 * @param precoDeVenda
	 */
	private void carregarPrecoUnitario(double precoDeVenda) {
		precoUnitarioValor = calcularPrecoUnitario(precoDeVenda);
	}

	/**
	 * Calcula o preço de venda a partir do preço unitario <br/>
	 * 
	 * setPrecoVendaValor(precoUnitario unidadeSelecionada.getQuantidade());
	 * <br/>
	 * 
	 * 
	 * @param precoUnitario
	 */
	private void carregarPrecoVendaEDescontoAcrescimo(double precoUnitario) {

		if (unidadeSelecionada != null) {

			setPrecoVendaValor(precoUnitario * unidadeSelecionada.getQuantidade());
		}
	}

	/**
	 * Calcula o preço de venda a partir do acrescimo ou desconto
	 * 
	 * @param descontoAcrescimoValor
	 */
	private void carregarPrecoVendaEValorUnitario(double descontoAcrescimoValor) {

		if (flagAcrescDesc.equals(FLAG_DESCONTO)) {

			setPrecoVendaValor(precoSugeridoValor - descontoAcrescimoValor);

		} else if (flagAcrescDesc.equals(FLAG_ACRESCIMO)) {
			setPrecoVendaValor(precoSugeridoValor + descontoAcrescimoValor);
		}
	}

	/**
	 * Carrega o desconto, acrescimo e preço unitario em função do preço de
	 * venda
	 * 
	 * @param precoDeVenda
	 */
	private void carregarDescontoAcrescimoEPrecoUnitario(double precoDeVenda) {

		carregarDescontoAcrescimo(precoDeVenda);
		carregarPrecoUnitario(precoDeVenda);

	}

	/**
	 * Carrega o desconto, acrescimo em função do preço de venda
	 * 
	 * @param precoDeVenda
	 */
	private void carregarDescontoAcrescimo(double precoDeVenda) {

		descontoAcrescimoValor = Conversao.arredondar(Math.abs(precoSugeridoValor - precoDeVenda), 2);
		descontoAcrescimoPorcentagem = Conversao.arredondar((descontoAcrescimoValor / precoSugeridoValor) * 100, 2);

		if (precoDeVenda > precoSugeridoValor) {
			flagAcrescDesc = FLAG_ACRESCIMO;

		} else {
			flagAcrescDesc = FLAG_DESCONTO;
		}
	}

	/**
	 * 
	 * @param e
	 */
	public void acrescDescFlagChangeListener(ValueChangeEvent e) {

		flagAcrescDesc = (String) e.getNewValue();

		zerarDescontoAcrescimo();
		carregarPrecoVendaEValorUnitario(descontoAcrescimoValor);
	}

	/**
	 * Atribui zero ao desconto valor e percentual <br/>
	 * descontoAcrescimoPorcentagem = 0; <br/>
	 * descontoAcrescimoValor = 0;
	 */
	private void zerarDescontoAcrescimo() {
		descontoAcrescimoPorcentagem = 0;
		descontoAcrescimoValor = 0;
	}

	/**
	 * 
	 * @return
	 */
	public Precificacao getPrecificacao() {
		return gerentePedidoMB.getPrecificacao();
	}

	public double getDescontoAcrescimoValor() {
		return descontoAcrescimoValor;
	}

	public void setDescontoAcrescimoValor(double descontoAcrescimoValor) {
		this.descontoAcrescimoValor = descontoAcrescimoValor;

		carregarPrecoVendaEValorUnitario(descontoAcrescimoValor);
	}

	public double getDescontoAcrescimoPorcentagem() {
		return Conversao.arredondar(descontoAcrescimoPorcentagem, 2);
	}

	public void setDescontoAcrescimoPorcentagem(double descontoAcrescimoPorcentagem) {

		this.descontoAcrescimoPorcentagem = descontoAcrescimoPorcentagem;
	}

	public double getPrecoVendaValor() {
		return Conversao.arredondar(precoVendaValor, 2);
	}

	/**
	 * seta o preço de venda.
	 * <ul>
	 * <li>Se for kit executa o {@link Kit#setAcrescimoDesconto(double, double)}
	 * e obtem o novo preço o kit</li>
	 * </ul>
	 * 
	 * Atribui o novo preço ao preço de venda e calcula o acrescimo e desconto e
	 * preço unitario
	 * 
	 * @param precoVendaValor
	 */
	public void setPrecoVendaValor(double precoVendaValor) {

		if (ekit()) {

			getKitSelecionado().setAcrescimoDesconto(precoVendaValor, precoSugeridoValor);

			precoVendaValor = getKitSelecionado().getValor();
		}

		this.precoVendaValor = precoVendaValor;
		carregarDescontoAcrescimoEPrecoUnitario(precoVendaValor);
	}

	public double getPrecoUnitarioValor() {
		return Conversao.arredondar(precoUnitarioValor, 3);
	}

	public void setPrecoUnitarioValor(double precoUnitarioValor) {

		this.precoUnitarioValor = precoUnitarioValor;
		carregarPrecoVendaEDescontoAcrescimo(precoUnitarioValor);
	}

	public double getPrecoTabelaValor() {
		return Conversao.arredondar(precoTabelaValor, 2);
	}

	public double getQuantidade() {
		return quantidade;
	}

	/**
	 * Atribui a quantidade pela quantidade em paramentro e tenta localizar a
	 * melhor promoção
	 * 
	 * @param quantidade
	 */
	public void setQuantidade(double quantidade) {
		setQuantidade(quantidade, true);
	}

	public void setQuantidade(double quantidade, boolean atualizaPromocao) {
		this.quantidade = quantidade;

		if (atualizaPromocao) {
			atualizarPromocao();
		}
		if (ekit()) {
			Kit kit = getKitSelecionado();

			for (ItemKit itemKit : kit.getItemKits()) {
				itemKit.atualizarQuantidadeVisualizacao(quantidade);
			}
		}
	}

	private void atualizarPromocao() {
		/*
		 * Atribui a promocao novamente para ser validada com a quantidade. Se
		 * não existe melhor promocao significa que nenhuma foi atingida.
		 */
		Promocao melhor = obterMelhorPromocaoItem();
		if (melhor == null || !this.verificarPromocaoValida(this.promocaoSelecionada) || this.gerentePedidoMB
				.getGerentePedido().getPromocaoVenda().isMelhor(this.promocaoSelecionada, melhor)) {
			setPromocaoSelecionada(melhor);
		} else
			this.verificarPromocaoSelecionada();

	}

	public double getQuantidadeVolumes() {
		return quantidadeVolumes;
	}

	public void setQuantidadeVolumes(double quantidadeVolumes) {
		this.quantidadeVolumes = quantidadeVolumes;
	}

	public double getPrecoSugeridoValor() {
		return precoSugeridoValor;
	}

	public boolean isPermiteSelecionarUnidade() {
		return eProduto() && unidades != null && unidades.size() > 0;
	}

	/**
	 * 
	 * @return Uma Strin contendo o texto do botao confirmar dependendo a ação
	 *         que esta sendo executa
	 */
	public String getTextoBotaoConfirma() {

		String oque = ekit() ? "texto.kit" : "texto.produto";
		String texto = "acao.confirmar";

		if (acao == Acao.INCLUIR) {
			texto = "acao.adicionar";
		} else if (acao == Acao.EXCLUIR) {
			texto = "acao.excluir";
		} else if (acao == Acao.EDITAR) {
			texto = "acao.alterar";
		}

		texto = gerentePedidoMB.getGerentePedido().getPropriedade().getMensagem(texto) + " "
				+ gerentePedidoMB.getGerentePedido().getPropriedade().getMensagem(oque);

		return texto;
	}

	public List<? extends IFormaPagamento> getFormasPagamentoPermitidas() {
		return gerentePedidoMB.getGerentePedido().getFormasPagamentoPermitidas();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoDetalheMB#
	 * getNomePrograma()
	 */
	@Override
	public String getNomePrograma() {
		return GerentePedidoMB.NOME_PROGRAMA;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoDetalheMB#
	 * getUrlView ()
	 */
	@Override
	public String getUrlView() {
		/*
		 * if (caixaProdutoSelecionado != null) return "/produtos/" +
		 * caixaProdutoSelecionado.getCodigo() + "/"; else return "/produtos/";
		 */
		return getUrlViewPattern();
	}

	@Override
	protected String getUrlViewPattern() {
		return "/produto/";
	}

	@Override
	protected String getUrlViewPage() {
		return "/pages/itemPedido3.xhtml";
	}

	public String getUrlView(CaixaProdutoVisualizavel produto) {

		String id = getUrlViewPattern();

		if (produto != null) {

			return id + produto.getCodigo() + (produto.eKit() ? IDENTIFICADOR_KIT_URL : "") + "/"
					+ FormatacaoMB.formataUrlNomeProduto(produto) + "/";
		}

		return id;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoDetalheMB#
	 * necessarioLogin()
	 */
	@Override
	public boolean necessarioLogin() {
		return false; // pode ver alguns detalhes;
	}

	/**
	 * Desabilitar digitação de quantidade de volumes
	 * 
	 * @return
	 */
	public boolean desabilitarDigitacaoVolumeItens() {
		return (((ParametroVenda2) this.gerentePedidoMB.getGerentePedido().getParametroVenda2())
				.getFlagLancaVolumeItemPedido() == 0)
				|| (!this.gerentePermissao.verificarPermissao("FRMCONSPEDIDO", 147));
	}

	/**
	 * @return O estoque disponivel do produto ou kit levando em consideração o
	 *         fator de estoque da da unidade selecionada
	 */
	public double getEstoqueItem() {

		double estoqueDisp = 0;

		// double est = caixaProdutoSelecionado.getEstoqueVisualizacao();

		try {

			estoqueDisp = caixaProdutoSelecionado.getEstoque();

			if (unidadeSelecionada != null) {

				estoqueDisp /= unidadeSelecionada.getFatorEstoque();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return estoqueDisp;
	}

	/**
	 * 
	 * @return O saldo da oferta levando em consideração o fator de estoque da
	 *         da unidade selecionada
	 */
	public double getSaldoOferta() {

		double saldoOferta = 0;

		try {

			if (getOferta() != null && unidadeSelecionada != null && getOferta().getQuantidadeOfertada() > 0) {

				saldoOferta = Math.abs(getOferta().getQuantidadeOfertada() - getOferta().getSaldoOferta());

				saldoOferta /= unidadeSelecionada.getFatorEstoque();

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return saldoOferta;

	}

	/**
	 * 
	 * @return A quantidade maxima que o cliente pode comprar deste produto em
	 *         oferta
	 */
	public double getQuantidadeMaximaClienteOferta() {

		double quantidadeMaxima = 0;

		try {

			if (getOferta() != null && unidadeSelecionada != null && getOferta().getQuantidadeMaximaCliente() > 0) {

				quantidadeMaxima = getOferta().getQuantidadeMaximaCliente();

				quantidadeMaxima = Conversao.arredondar(quantidadeMaxima / unidadeSelecionada.getFatorEstoque(), 3);

			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return quantidadeMaxima;
	}

	public Oferta getOferta() {
		return this.precoVenda.getOferta() != null ? (Oferta) this.precoVenda.getOferta() : null;
	}

	public String getCodigoItemURL() {
		return codigoItemURL;
	}

	public void setCodigoItemURL(String codigoItemURL) {
		this.codigoItemURL = codigoItemURL;
	}

	public String getNomeItemURL() {

		return nomeItemURL;
	}

	public void setNomeItemURL(String nomeItemURL) {
		this.nomeItemURL = nomeItemURL;
	}

	public boolean isHabilitaBotaoConfirmar() {
		return gerenteLogin.isSessoaAutenticada() && acao != Acao.VISUALIZAR;
	}

	public String getFbNomeProduto() {
		return fbNomeProduto;
	}

	public void setFbNomeProduto(String fbNomeProduto) {
		this.fbNomeProduto = fbNomeProduto;
	}

	public String getFbFotoCapa() {
		return fbFotoCapa;
	}

	public void setFbFotoCapa(String fbFotoCapa) {
		this.fbFotoCapa = fbFotoCapa;
	}

	@Override
	public double getPeso() {
		if (eProduto() && unidadeSelecionada != null) {
			return ((Produto) caixaProdutoSelecionado).getPesoBruto() * unidadeSelecionada.getFatorEstoque();
		}
		return super.getPeso();
	}
}
