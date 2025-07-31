package br.com.space.spacewebII.controle.produto.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.primefaces.event.TabChangeEvent;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.ICondicaoPagamento;
import br.com.space.api.negocio.modelo.dominio.INaturezaOperacao;
import br.com.space.api.negocio.modelo.dominio.IOpcaoEspecialFilial;
import br.com.space.api.negocio.modelo.dominio.ITabelaPreco;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.spacewebII.controle.Licenca;
import br.com.space.spacewebII.controle.estoque.Estoque;
import br.com.space.spacewebII.controle.padrao.mbean.GerenteMB;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.controle.pedido.mbean.GerentePedidoMB;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidadePK;
import br.com.space.spacewebII.modelo.dominio.sistema.Galeria;
import br.com.space.spacewebII.modelo.dominio.sistema.GaleriaImagem;
import br.com.space.spacewebII.modelo.dominio.venda.Kit;
import br.com.space.spacewebII.modelo.padrao.interfaces.Listavel;
import br.com.space.spacewebII.modelo.padrao.interfaces.Pesquisavel;
import br.com.space.spacewebII.modelo.padrao.interfaces.UrlActionListener;
import br.com.space.spacewebII.modelo.util.CaixaProdutoUtil;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;
import br.com.space.spacewebII.modelo.widget.CaixaVisualizavelList;
import br.com.space.spacewebII.modelo.widget.Grupo;
import br.com.space.spacewebII.modelo.widget.GrupoWidget;
import br.com.space.spacewebII.modelo.widget.SubGrupo;

/**
 * 
 * @author Desenvolvimento
 * 
 */
@ManagedBeanSessionScoped
@URLMappings(mappings = {
		@URLMapping(id = "produtosBarra", pattern = "/produtos/", viewId = "/pages/pesquisaProdutosHome.xhtml", onPostback = false),
		@URLMapping(id = "produtos", pattern = "/produtos", viewId = "/pages/pesquisaProdutosHome.xhtml", onPostback = false),
		@URLMapping(id = "produtosG", pattern = "/produtos/#{produtoPesquisaMB.grupoURL}", viewId = "/pages/pesquisaProdutosHome.xhtml", onPostback = false),
		@URLMapping(id = "produtosGS", pattern = "/produtos/#{produtoPesquisaMB.grupoURL}/#{produtoPesquisaMB.subGrupoURL}", viewId = "/pages/pesquisaProdutosHome.xhtml", onPostback = false) })
@URLBeanName("produtoPesquisaMB")
public class ProdutoPesquisaMB extends GerenteMB implements Serializable, Pesquisavel,
		Listavel<CaixaProdutoVisualizavel>, UrlActionListener, GerentePedido.ComunicaoUsuarioListener {

	private static final long serialVersionUID = 1L;

	@Inject
	private GerentePedidoMB gerentePedidoMB;

	private List<Produto> produtosDestaque = null;
	private List<Produto> produtosOferta = null;

	private List<String> mensagensGenrentePedido = null;

	private CaixaVisualizavelList resultadosPesquisa;
	private String filtroCodigoOuDescricao = null;
	private String filtroGrupoGrupoClassisificacao = null;
	private List<Grupo> grupoClassificacaoTodos;
	private ArrayList<Grupo> grupoClassificacaoResultado;

	private ArrayList<Grupo> grupoClassificacaoFiltro;

	private Grupo grupoOferta = null;
	private Grupo grupoDestaque = null;

	private Grupo grupoKit = null;

	private SubGrupo subgrupoSelecionadoAnterior = null;

	private SubGrupo subGrupoSelecionado = null;
	private Grupo grupoSelecionado = null;

	private String indiceDoGrupoSelecionado = "0";

	private int grupoSelecionadoPrimeiroCodigo = 0;

	private int precoBaseSelecionado = 0;

	private List<Galeria> galeriasBannerTodos = null;
	private List<Galeria> galeriasBannerTodosNaoExclusivos = null;

	private ArrayList<GaleriaImagem> imagensBannerResultado = null;
	private List<GaleriaImagem> imagensBannerTodas = null;
	private List<GaleriaImagem> imagensBannerMaxi = null;
	private List<List<GaleriaImagem>> listaMiniBanners = null;

	private int precoBaseUtilizado = 0;
	private int filialUtilizada = 0;

	private boolean exibeProdutoSemEstoque = false;
	private boolean ordenarSemEstoque = false;

	private Comparator<CaixaProdutoVisualizavel> comparatorDescricaoEstoque = null;

	private String urlStaticaUtilizada = null;

	private String mensagemPesquisa = null;

	private String grupoURL = null;
	private String subGrupoURL = null;

	private int layoutListaProduto = 0;

	private int listaOuGridProduto = 0;

	private ICondicaoPagamento condicaoPagamentoConfiguracaoProduto;
	private IOpcaoEspecialFilial especialFilialConfiguracaoProduto;
	private ITabelaPreco tabelaPrecoConfiguracaoProduto;
	private INaturezaOperacao naturezaOperacaoConfiguracaoProduto;
	private boolean isNegociacaoDiferente = false;
	private int clienteCodigoConfiguracaoProduto = 0;
	private int vendedorCodigoConfiguracaoProduto = 0;

	private int numeroLinha = 16;

	/**
	 * Construtor Padrão
	 */
	public ProdutoPesquisaMB() {

		grupoClassificacaoTodos = new ArrayList<Grupo>();
		grupoClassificacaoResultado = new ArrayList<Grupo>();
		grupoClassificacaoFiltro = new ArrayList<Grupo>();
		imagensBannerResultado = new ArrayList<GaleriaImagem>();

		mensagensGenrentePedido = new ArrayList<String>();
	}

	/**
	 * Construtor padrão executado pelo CDI
	 */
	@PostConstruct
	public void posContrutor() {
		try {
			grupoOferta = new Grupo(Grupo.CODIGO_GRUPO_OFERTA, propriedade.getMensagem("texto.oferta"));

			grupoDestaque = new Grupo(Grupo.CODIGO_GRUPO_DESTAQUE, propriedade.getMensagem("texto.destaque"));

			grupoKit = Kit.getNewGrupoKit(propriedade.getMensagem("texto.kit"));

			layoutListaProduto = parametroWebMB.getParametroWeb().getLayoutListaProduto();

			listaOuGridProduto = parametroWebMB.getParametroWeb().getListaOuGridProduto();

			this.ordenarSemEstoque = parametroWebMB.getParametroWeb().isOrdenaProdutoSemEstoque();

			this.exibeProdutoSemEstoque = parametroWebMB.getParametroWeb().isExibeProdutoSemEstoque();

			comparatorDescricaoEstoque = CaixaProdutoUtil.getNewComparatorDescricaoEstoque(ordenarSemEstoque);

			resultadosPesquisa = new CaixaVisualizavelList() {

				private static final long serialVersionUID = 1L;

				@Override
				public void aoRetornarUmObjeto(CaixaProdutoVisualizavel produtoVisualizavel) {

					try {

						Precificacao precificacao = getPrecificacao();

						copiarNegociacaoConfiguracaoProduto(precificacao);

						preencherAtributosCaixa(produtoVisualizavel);
					} catch (Exception e) {

						e.printStackTrace();
					}
				}
			};

			copiarNegociacaoConfiguracaoProduto(getPrecificacao());
			carregarBanners();
			carregarPrimeiraVisualizacao();

			gerentePedidoMB.getGerentePedido().addComunicaoUsuario(this);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param produtoVisualizavel
	 * @param forcarRefreshPrecos
	 * @throws Exception
	 */
	public void preencherAtributosCaixa(CaixaProdutoVisualizavel produtoVisualizavel) throws Exception {

		try (GenericoDAO dao = new GenericoDAO()) {

			produtoVisualizavel.preencherAtributos(dao, gerentePedidoMB.getGerentePedido(), null, isNegociacaoDiferente,
					false, !produtoVisualizavel.eProduto());
		}
	}

	/**
	 * Verifica se negociação usada para configuara os preços dos produto teve
	 * alguma alteração utilizado o metodo
	 * 
	 * 
	 * @return
	 */
	private boolean isNegociacaoDiferente() {

		boolean trocouNatureza = (naturezaOperacaoConfiguracaoProduto == null
				&& getPrecificacao().getNaturezaOperacao() != null)
				|| (naturezaOperacaoConfiguracaoProduto != null && getPrecificacao().getNaturezaOperacao() == null)
				|| (naturezaOperacaoConfiguracaoProduto != null && getPrecificacao().getNaturezaOperacao() != null
						&& !getPrecificacao().getNaturezaOperacao().getCodigo()
								.equals(naturezaOperacaoConfiguracaoProduto.getCodigo()));

		boolean trocouCliente = gerentePedidoMB.getGerentePedido().getCliente() != null
				&& gerentePedidoMB.getGerentePedido().getCliente().getCodigo() != clienteCodigoConfiguracaoProduto;

		boolean trocouVendedor = gerentePedidoMB.getGerentePedido().getVendedor() != null && gerentePedidoMB
				.getGerentePedido().getVendedor().getColaboradorCodigo() != vendedorCodigoConfiguracaoProduto;

		this.isNegociacaoDiferente = trocouCliente || trocouVendedor
				|| gerentePedidoMB.getGerentePedido().isNegociacaoDiferente(tabelaPrecoConfiguracaoProduto,
						condicaoPagamentoConfiguracaoProduto, especialFilialConfiguracaoProduto,
						naturezaOperacaoConfiguracaoProduto)

				|| trocouNatureza;

		return this.isNegociacaoDiferente;

	}

	/**
	 * Evento disparado quando a unidade é alterada.
	 * 
	 * @param e
	 * @throws Exception
	 */
	public void unidadeChangeListener(ValueChangeEvent e) throws Exception {

		String idUnidade = e.getNewValue().toString();

		setCaixaVisualizavel(idUnidade);
	}

	private void setCaixaVisualizavel(String idUnidade) throws Exception {

		for (CaixaProdutoVisualizavel caixaVisualizavel : resultadosPesquisa) {

			ProdutoUnidade produtoUnidade = getUnidadeSelecionada(caixaVisualizavel, idUnidade);

			if (produtoUnidade != null) {

				caixaVisualizavel.preencherAtributos(dao, gerentePedidoMB.getGerentePedido(), produtoUnidade, true,
						true);

				break;
			}
		}
	}

	/**
	 * Pesquisa na lista de unidades qual é a unidade que corresponde com o id
	 * informado. <br/>
	 * Se encontrou recalcula o preço
	 * 
	 * @param id
	 *            O id do produto unidade em {@link ProdutoUnidadePK#getID()}
	 */
	public ProdutoUnidade getUnidadeSelecionada(CaixaProdutoVisualizavel caixaVisualizavel, String idUnidade) {
		for (ProdutoUnidade unidade : caixaVisualizavel.getUnidades()) {

			if (unidade.getProdutoUnidadePK().getID().equals(idUnidade)) {

				return unidade;
			}
		}

		return null;
	}

	/**
	 * 
	 * @param precificacao
	 */
	private void copiarNegociacaoConfiguracaoProduto(Precificacao precificacao) {
		if (precificacao == null) {
			return;
		}

		condicaoPagamentoConfiguracaoProduto = precificacao.getCondicaoPagamento();
		especialFilialConfiguracaoProduto = precificacao.getOpcaoEspecialFilial();

		tabelaPrecoConfiguracaoProduto = precificacao.getTabelaPreço();

		naturezaOperacaoConfiguracaoProduto = precificacao.getNaturezaOperacao();

		if (gerentePedidoMB.getGerentePedido().getCliente() != null) {
			clienteCodigoConfiguracaoProduto = gerentePedidoMB.getGerentePedido().getCliente().getCodigo();
		}

		if (gerentePedidoMB.getGerentePedido().getVendedor() != null) {
			vendedorCodigoConfiguracaoProduto = gerentePedidoMB.getGerentePedido().getVendedor().getColaboradorCodigo();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.padrao.mbean.GerenteMB#aoCarregar()
	 */
	@Override
	public void aoCarregar() {
		super.aoCarregar();
		exibirMensagensGerentePedido();

		boolean negociacaoMudou = isNegociacaoDiferente();

		// Carrega os produtos novamente caso o preço base mude ou a filial
		if (precoBaseUtilizado != getPrecificacao().getPrecoBaseCodigo()
				|| filialUtilizada != gerenteLogin.getFilialCodigo()) {

			limparFiltros();
			mensagemPesquisa = null;

			carregarPrimeiraVisualizacao();

		} else if (negociacaoMudou) {

			resultadosPesquisa.reconfigurarProdutos();
		}
	}

	/**
	 * Carrega os produtos para a primeira exibição. Ou seja carrega novamente todos
	 * os dados referente aos produtos em destaque grupos e subgrupos
	 */
	private void carregarPrimeiraVisualizacao() {
		precoBaseUtilizado = getPrecificacao().getPrecoBaseCodigo();
		filialUtilizada = gerenteLogin.getFilialCodigo();

		carregarProdutosEmDestaque();

		carregarGrupoESubGrupo();

		montarPrimeiraVisualizacao();
	}

	/**
	 * Carrega todos os grupos e subgupos Encluindo kit's
	 */
	private void carregarGrupoESubGrupo() {
		grupoClassificacaoTodos.clear();

		List<Grupo> grupos = Grupo.gerarGrupos(dao, gerenteLogin.getFilialCodigo(),
				getPrecificacao().getPrecoBaseCodigo(), exibeProdutoSemEstoque, true);

		grupoClassificacaoTodos.addAll(grupos);

		adcionarGrupoKit();

		// Adiciona todos os grupo nemos os gerados (Oferta e destaque)
		grupoClassificacaoFiltro.addAll(grupoClassificacaoTodos);

		classificarGrupoESubgrupoProdutoDestaque();

		Collections.sort(grupoClassificacaoTodos, Grupo.getComparadorNome());
	}

	/**
	 * Classifica os grupo e subgrupos dos produtos em destaque é em oferta
	 */
	private void classificarGrupoESubgrupoProdutoDestaque() {

		grupoDestaque.limparListas();

		if (ListUtil.isValida(produtosDestaque)) {
			adicionarProdutosAoGrupo(grupoClassificacaoTodos, grupoDestaque, produtosDestaque, true);
		}

		grupoOferta.limparListas();

		if (ListUtil.isValida(produtosOferta)) {

			adicionarProdutosAoGrupo(grupoClassificacaoTodos, grupoOferta, produtosOferta, true);
		}

	}

	/**
	 * Adciona o grupo do kit nos grupos de classificação caso tenha algum kit
	 * cadastrado
	 */
	private void adcionarGrupoKit() {

		int countKits = (int) Kit.count(dao);
		grupoKit.limparListas();

		if (countKits > 0) {

			grupoClassificacaoTodos.add(grupoKit);

			grupoKit.adicionarSubGrupoTodos(propriedade.getMensagem("texto.todos"));
			grupoKit.getSubGrupoTodos().setTotalizador(countKits);

		}
	}

	/**
	 * Busca no DB todos os produtos em destaque é em oferta
	 */
	private void carregarProdutosEmDestaque() {

		produtosDestaque = Produto.recuperarProdutosDestaqueVendaUsandoResultSet(dao, gerenteLogin.getFilialCodigo(),
				getPrecificacao().getPrecoBaseCodigo(), null, null, exibeProdutoSemEstoque,
				gerentePedidoMB.getGerentePedido(), false);

		produtosOferta = Produto.recuperarProdutosOfertaVendaUsandoResultSet(dao, gerenteLogin.getFilialCodigo(),
				getPrecificacao().getPrecoBaseCodigo(), false, new Date(), gerentePedidoMB.getGerentePedido(),
				exibeProdutoSemEstoque);

		preencherAtributosProdutoDestaque();

	}

	/**
	 * Carrega os atributos dos produtos em destaque é em oferta
	 */
	private void preencherAtributosProdutoDestaque() {

		List<Produto> produtosAux = new ArrayList<Produto>();

		if (ListUtil.isValida(produtosOferta)) {
			produtosAux.addAll(produtosOferta);
		}

		if (ListUtil.isValida(produtosDestaque)) {
			produtosAux.addAll(produtosDestaque);
		}

		/*
		 * Atualiza os produtos das listas adcionadas pois estamos tratando de objetos
		 */
		if (ListUtil.isValida(produtosAux)) {
			Produto.preencherAtributosProduto(dao, gerenteLogin.getFilialCodigo(), gerentePedidoMB.getGerentePedido(),
					produtosAux);
		}
	}

	/**
	 * Adciona os produtos ao grupo de classificão em seu respectivo grupo em
	 * parametro
	 * 
	 * @param grupoClassificacao
	 *            A lista de grupo no qual o grupo sera inserido
	 * @param grupo
	 *            O grupo que sera usuado para classifiacar os produtos
	 * @param produtos
	 *            Os produtos para serem classificados
	 */
	private void adicionarProdutosAoGrupo(List<Grupo> grupoClassificacao, Grupo grupo, List<Produto> produtos,
			boolean adcionaSubGrupoTodos) {

		for (Produto produto : produtos) {
			adicionarProdutoAoGrupo(grupoClassificacao, grupo, produto, adcionaSubGrupoTodos);
		}
	}

	/**
	 * Adicina o produto no grupo. Caso o grupo não exista na lista de grupo de
	 * classificação o mesmo e adcionado. Caso exista o produto e adcionado no grupo
	 * que esta dentro da lista de grupo de classificação
	 * 
	 * @param grupoClassificacao
	 *            Lista contendo onde será armazenado o grupo
	 * 
	 * @param grupo
	 *            O grupo que o produto será adicionado
	 * @param produto
	 *            O produto
	 */
	private void adicionarProdutoAoGrupo(List<Grupo> grupoClassificacao, Grupo grupo, CaixaProdutoVisualizavel produto,
			boolean adcionaSubGrupoTodos) {

		int indiceGrupo = Collections.binarySearch(grupoClassificacao, grupo);

		if (indiceGrupo >= 0) {
			grupo = grupoClassificacao.get(indiceGrupo);
		} else {
			grupoClassificacao.add(grupo);
			Collections.sort(grupoClassificacao);
		}

		grupo.addProdutoVizualizavel(produto, adcionaSubGrupoTodos);
	}

	/**
	 * Metodo responsavel por montar a primeira visualização dos produtos ou seja
	 * define qual será os produtos que estarão na pagina de HOME.
	 * 
	 * </br>
	 * </br>
	 * 
	 * Metodo acionado de forma automatica pelo UrlAction
	 * 
	 * 
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoPesquisaMB#getUrlView()
	 * 
	 * @return A url view
	 */
	// @URLActions(actions = { @URLAction(mappingId = "produtosHome", onPostback
	// = false) })
	public String montarPrimeiraVisualizacao() {

		Grupo grupoSelecionado = null;

		limparFiltros();
		mensagemPesquisa = null;

		montarListaGrupoResultado(grupoClassificacaoTodos);

		if (grupoDestaque.getSubGrupoTodos() != null && grupoDestaque.getSubGrupoTodos().isTemProdutos()) {

			grupoSelecionado = grupoDestaque;

		} else if (grupoOferta.getSubGrupoTodos() != null && grupoOferta.getSubGrupoTodos().isTemProdutos()) {

			grupoSelecionado = grupoOferta;

		} else if (grupoClassificacaoResultado != null && grupoClassificacaoResultado.size() > 0) {

			grupoSelecionado = grupoClassificacaoResultado.get(0);

			if (grupoSelecionado.getSubGrupoTodos() == null || !grupoSelecionado.getSubGrupoTodos().isTemProdutos()) {

				List<Produto> produtos = recuperarProdutosParaVenda(grupoSelecionado.getCodigo(), null, true);

				adicionarProdutosAoGrupo(grupoClassificacaoTodos, grupoSelecionado, produtos, true);
			}
		}

		if (grupoSelecionado != null) {
			grupoSelecionado.setVisualizarExclusiva(false);
			grupoSelecionadoPrimeiroCodigo = grupoSelecionado.getCodigo();
			informarGrupoSelecionado(grupoSelecionado);
		}

		Collections.sort(grupoClassificacaoResultado, Grupo.getComparadorNome());

		return getUrlView();
	}

	/**
	 * Metodo que monta a visualização da pagina de home
	 * 
	 * 1° Se a url de requisição for difirente de url view entao simplesmente
	 * retonar a mantem a visualização antiga. <br/>
	 * 2° Se a url de requisição for igual a url view então monta a primeira
	 * visualização
	 * 
	 * 
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoPesquisaMB#montarPrimeiraVisualizacao()
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoPesquisaMB#getUrlView()
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoPesquisaMB#getURLRequisicao()
	 * @see br.com.space.spacewebII.controle.produto.mbean.ProdutoPesquisaMB#montarPrimeiraVisualizacao()
	 * 
	 * @return
	 * 
	 */
	public String montarVisualizacaoHome() {
		if (isRequisicaoInterna()) {
			montarPrimeiraVisualizacao();
		}
		return getUrlView();
	}

	/**
	 * Carrega todos os banners
	 * 
	 * Max banner <br/>
	 * Banner Central <br/>
	 * Mini Banner
	 * 
	 */
	private void carregarBanners() {

		/*
		 * Somente carrega banners se cliente possuir módulo banners
		 */
		if (Licenca.isModuloBannerAtivo()) {
			carregarBannersMaxi();
			carregarBannersCentral();
			carregarBannersMini();
		}

	}

	public void produtoChangeListener(ValueChangeEvent event) {

	}

	/**
	 * Carrega todos os mini banners ativos </BR>
	 * </BR>
	 * So carrega quando o {@link #isPerfilUsuarioPermiteMiniBanner()} é TRUE
	 */
	private void carregarBannersMini() {

		if (isPerfilUsuarioPermiteMiniBanner()) {

			List<Galeria> galeriaMiniBannerTodos = Galeria.recuperarGaleriasAtivasTipo(dao, Galeria.BANNER_MINI);

			if (galeriaMiniBannerTodos != null) {

				listaMiniBanners = new ArrayList<>();

				for (Galeria galeria : galeriaMiniBannerTodos) {
					List<GaleriaImagem> imagens = GaleriaImagem.recuperarImagensGaleria(dao, galeria.getCodigo());

					if (imagens != null) {
						for (GaleriaImagem galeriaImagem : imagens) {
							galeriaImagem.setGaleria(galeria);
						}
						listaMiniBanners.add(imagens);
					}
				}
			}
		}
	}

	/**
	 * Carrega todos os banners centrais ativo e classifica os banner quanto a sua
	 * não exclusividade.
	 * 
	 * </BR>
	 * </BR>
	 * So carrega quando o {@link #isPerfilUsuarioPermiteBannerCentral()} é TRUE
	 * 
	 */
	private void carregarBannersCentral() {

		if (isPerfilUsuarioPermiteBannerCentral()) {

			galeriasBannerTodos = Galeria.recuperarGaleriasAtivasTipo(dao, Galeria.BANNER_CENTRAL);

			imagensBannerTodas = GaleriaImagem.recuperarImagensBannerAtivos(dao, Galeria.BANNER_CENTRAL);

			carregarBannerNaoExclusivos(galeriasBannerTodos);

			if (imagensBannerTodas != null && imagensBannerTodas.size() > 0) {
				for (GaleriaImagem imagem : imagensBannerTodas) {
					imagem.setGaleria(Galeria.pesquisarNaLista(galeriasBannerTodos, imagem.getGaleriaCodigo()));

					imagem.setNomeImagem(formatacaoMB.formatarEnderecoImagemSemPasta(imagem.getNomeImagem()));
				}
			}
		}
	}

	/**
	 * Carrega todos os Maxi banners </BR>
	 * </BR>
	 * So carrega quando o {@link #isPerfilUsuarioPermiteMaxiBanner()} é TRUE
	 */
	private void carregarBannersMaxi() {
		if (imagensBannerMaxi == null)
			imagensBannerMaxi = new ArrayList<>();

		imagensBannerMaxi.clear();

		if (isPerfilUsuarioPermiteMaxiBanner()) {

			List<Galeria> galeriaMaxiBannerTodos = Galeria.recuperarGaleriasAtivasTipo(dao, Galeria.BANNER_MAXI);

			if (ListUtil.isValida(galeriaMaxiBannerTodos)) {
				/*
				 * Pega somente as imagens do primeiro maxi banner.
				 */
				Galeria galeria = galeriaMaxiBannerTodos.get(0);

				List<GaleriaImagem> imagens = GaleriaImagem.recuperarImagensGaleria(dao, galeria.getCodigo());

				if (imagens != null) {
					for (GaleriaImagem galeriaImagem : imagens) {
						galeriaImagem.setGaleria(galeria);
						galeriaImagem.setNomeImagem(
								formatacaoMB.formatarEnderecoImagemSemPasta(galeriaImagem.getNomeImagem()));
					}
					imagensBannerMaxi.addAll(imagens);
				}
			}
		}
	}

	/**
	 * Carrega as galerias que não são excluisivas
	 * 
	 * @param galeriasBannerTodos
	 *            Lista de galeria contento todas as galerias
	 */
	private void carregarBannerNaoExclusivos(List<Galeria> galeriasBannerTodos) {

		if (ListUtil.isValida(galeriasBannerTodos)) {

			galeriasBannerTodosNaoExclusivos = new ArrayList<Galeria>();

			for (Galeria galeria : galeriasBannerTodos) {

				if (!galeria.eExclusiva()) {
					galeriasBannerTodosNaoExclusivos.add(galeria);
				}
			}
		}
	}

	/**
	 * Recupera produtos com os filtros especificados mais somente os que estiverem
	 * disponivel para venda
	 * 
	 * @param grupoCodigo
	 *            O codigo do grupo produto - Caso esteja null ou seu codigo for
	 *            nemor que zero sera desconsiderado
	 * @param subGrupoCodigo
	 *            O SubGrupo desejado = Caso esteja null ou seu codigo for nemor que
	 *            zero sera desconsiderado
	 * @param preencherAtributos
	 *            Caso TRUE preenche os atributos do produtos, FALSE não preenche os
	 *            dados do produto
	 * @return
	 */
	private List<Produto> recuperarProdutosParaVenda(Integer grupoCodigo, Integer subGrupoCodigo,
			boolean preencherAtributos) {

		try {

			boolean pesquisarSubGrupoNull = subGrupoCodigo != null && subGrupoCodigo < 0;

			return recuperProdutosParaVenda(grupoCodigo, subGrupoCodigo, null, pesquisarSubGrupoNull,
					preencherAtributos);

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Recupera os Kit's para a venda
	 * 
	 * @param filtroCodigoOuDescricao
	 * @return
	 */
	public List<Kit> recuperarKitParaVenda(String filtroCodigoOuDescricao) {

		List<Kit> kits = Kit.recuperarKitParaVenda(dao, filtroCodigoOuDescricao);

		return kits;
	}

	/**
	 * Recupera os produtos disponiveis para a venda com os filros em parametro
	 * 
	 * @param grupoCodigo
	 * @param subGrupoCodigo
	 * @param filtroCodigoOuDescricao
	 * @param pesquisarSubGrupoNull
	 * @param preencherAtributos
	 * @return
	 */
	public List<Produto> recuperProdutosParaVenda(Integer grupoCodigo, Integer subGrupoCodigo,
			String filtroCodigoOuDescricao, boolean pesquisarSubGrupoNull, boolean preencherAtributos) {

		String filtoDesc = null;
		Integer filtoCodigo = null;

		if (StringUtil.isValida(filtroCodigoOuDescricao)) {

			boolean pesquisaPorCodigo = filtroCodigoOuDescricao.matches("[0-9]+");

			if (pesquisaPorCodigo) {
				filtoCodigo = Integer.parseInt(filtroCodigoOuDescricao);
			} else {
				filtoDesc = filtroCodigoOuDescricao;
			}
		}

		try {
			return Produto.recuperarProdutosVendaUsandoResultSet(dao, gerenteLogin.getFilialCodigo(),
					getPrecificacao().getPrecoBaseCodigo(), grupoCodigo, subGrupoCodigo, filtoDesc, filtoCodigo,
					gerentePedidoMB.getGerentePedido(), pesquisarSubGrupoNull, exibeProdutoSemEstoque,
					preencherAtributos);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.modelo.padrao.interfaces.Pesquisavel#
	 * executarPesquisa ()
	 */
	@Override
	public void executarPesquisa() {

		Integer filtroGupo = StringUtil.isValida(filtroGrupoGrupoClassisificacao)
				? Integer.parseInt(filtroGrupoGrupoClassisificacao)
				: 0;

		if (!StringUtil.isValida(filtroCodigoOuDescricao)
				&& (filtroGupo == null || filtroGupo == Grupo.CODIGO_GRUPO_TODOS)) {

			montarPrimeiraVisualizacao();

			return;
		}

		List<CaixaProdutoVisualizavel> result = new ArrayList<CaixaProdutoVisualizavel>();

		if (filtroGupo != null && filtroGupo.intValue() == Grupo.CODIGO_GRUPO_KIT) {

			result.addAll(recuperarKitParaVenda(filtroCodigoOuDescricao));

			if (ListUtil.isValida(result)) {
				informarGrupoSelecionado(grupoKit);
			}

		} else {

			List<Produto> produtos = recuperProdutosParaVenda(filtroGupo, null, filtroCodigoOuDescricao, false, true);

			List<Kit> kits = null;

			if (filtroGupo == Grupo.CODIGO_GRUPO_TODOS) {
				kits = recuperarKitParaVenda(filtroCodigoOuDescricao);
			}

			if (ListUtil.isValida(produtos)) {
				result.addAll(produtos);
			}

			if (ListUtil.isValida(kits)) {
				result.addAll(kits);
			}
		}

		String busca = StringUtil.isValida(filtroCodigoOuDescricao)
				? " " + propriedade.getMensagem("texto.porminusculo") + " " + filtroCodigoOuDescricao.toUpperCase()
				: "";

		Grupo grupoSelecionado = getGrupoPeloCodigo(getGrupoClassificacaoFiltro(), filtroGupo);

		busca += grupoSelecionado != null && grupoSelecionado.getDescricao() != null
				? " " + propriedade.getMensagem("texto.em") + " " + grupoSelecionado.getDescricao().toUpperCase()
				: "";

		if (ListUtil.isValida(result)) {

			montarListaGrupoResultado(getGrupoClassificacao(result, true));

			informarGrupoSelecionado(getGrupoPeloCodigo(grupoClassificacaoResultado, Grupo.CODIGO_GRUPO_TODOS));

			montarListaProdutosResultado(result);

			String nomeString = "mensagem.resultadosobtidos";

			if (result.size() == 1) {
				nomeString = "mensagem.resultadoobtido";
			}

			mensagemPesquisa = propriedade.getMensagem(nomeString, result.size(), busca);

		} else {

			result = null;

			mensagemPesquisa = propriedade.getMensagem("mensagem.nenhumresultado", busca);
		}
	}

	/**
	 * Monta a exibição dos grupos que apareceram no canto esquerdo da tela de
	 * produto pesquisa
	 * 
	 * @see ProdutoPesquisaMB#montarListaGrupoResultado(Grupo)
	 * 
	 * @param grupos
	 *            Os grupos que serão exibidos.
	 */
	private void montarListaGrupoResultado(List<Grupo> grupos) {

		grupoClassificacaoResultado.clear();
		grupoClassificacaoResultado.addAll(grupos);

		Collections.sort(grupoClassificacaoResultado, Grupo.getComparadorNome());
	}

	/**
	 * 
	 * Monta a exibição do unico grupo que aparerá no canto esquerdo da tela de
	 * produto pesquisa
	 * 
	 * @see ProdutoPesquisaMB#montarListaGrupoResultado(List)
	 * 
	 * @param grupo
	 *            O grupo que será exibido.
	 */
	private void montarListaGrupoResultado(Grupo grupo) {

		grupoClassificacaoResultado.clear();
		grupoClassificacaoResultado.add(grupo);

	}

	/**
	 * Percorre a lista de produtos, classificando os pelo seu grupo. <br/>
	 * 
	 * Todos os produtos são adicionados ao grupo "TODOS" dependendo do parametro.
	 * 
	 * @param produtos
	 *            Os produtos que serão classificados
	 * @param gerarGrupoTodos
	 *            Caso TRUE cria o Grupo todos, Caso contrario não cria
	 * 
	 * @return A lista de Grupos classificada
	 */
	private List<Grupo> getGrupoClassificacao(List<CaixaProdutoVisualizavel> produtos, boolean gerarGrupoTodos) {

		List<Grupo> grupos = new ArrayList<Grupo>();

		Grupo grupoTodos = null;

		for (CaixaProdutoVisualizavel produto : produtos) {

			GrupoWidget grupoWidget = produto.getGrupoWidget();

			Grupo grupo = null;

			if (grupoWidget == null || !StringUtil.isValida(grupoWidget.getDescricao())) {

				grupo = new Grupo(Grupo.CODIGO_GRUPO_OUTRO, propriedade.getMensagem("texto.outro"));

			} else {
				grupo = new Grupo(grupoWidget);
			}

			adicionarProdutoAoGrupo(grupos, grupo, produto, true);

			if (gerarGrupoTodos) {

				if (grupoTodos == null) {
					grupoTodos = new Grupo(Grupo.CODIGO_GRUPO_TODOS, propriedade.getMensagem("texto.todos"));
				}

				adicionarProdutoAoGrupo(grupos, grupoTodos, produto, true);

			}
		}

		return grupos;
	}

	/**
	 * Executa a pesquisa com subgrupo informado mais somente se ele não tiver
	 * produtos.
	 * 
	 * Caso ja tenha produtos os produtos do grupo serão exibidos.<br/>
	 * Caso não tenha os produtos serão recuperados do banco de dados e adicionado
	 * ao sub-grupo
	 * 
	 * @param subGrupo
	 */
	private void executarPesquisa(SubGrupo subGrupo) {

		if (subGrupo == null) {
			return;
		}

		if (!ListUtil.isValida(subGrupo.getProdutos())) {

			List<? extends CaixaProdutoVisualizavel> produtos = null;

			if (subGrupo.getGrupoCodigo() == Grupo.CODIGO_GRUPO_KIT) {

				produtos = recuperarKitParaVenda(null);

			} else {

				Integer subGrupoCodigo = subGrupo.getCodigo() == SubGrupo.CODIGO_SUBGRUPO_TODOS ? null
						: subGrupo.getCodigo();

				produtos = recuperarProdutosParaVenda(subGrupo.getGrupoCodigo(), subGrupoCodigo, true);
			}

			subGrupo.setProdutos(produtos);
		}

		montarListaProdutosResultado(subGrupo.getProdutos());
	}

	/**
	 * Pesquina o codigo do grupo dentro da lista grupoClassificacao
	 * 
	 * @param codigoGrupo
	 *            O codigo do grupo no qual deseja obter o Grupo
	 * @return O objeto Grupo
	 */
	private Grupo getGrupoPeloCodigo(List<Grupo> grupos, int codigoGrupo) {

		for (Grupo grupo : grupos) {

			if (grupo.getCodigo() == codigoGrupo) {
				return grupo;
			}

		}
		return null;
	}

	/**
	 * 
	 * @param codigoGrupoOuDescricao
	 * @param subGrupoCodigocodigoOuDescricao
	 * @return
	 */
	public SubGrupo informarSubGrupoSelecionado(String codigoGrupoOuDescricao, String subGrupoCodigocodigoOuDescricao) {

		if (StringUtil.isValida(subGrupoCodigocodigoOuDescricao) && StringUtil.isValida(codigoGrupoOuDescricao)) {

			boolean digitoGrupo = StringUtil.isSomenteDigitos(codigoGrupoOuDescricao);

			boolean digitoSub = StringUtil.isSomenteDigitos(subGrupoCodigocodigoOuDescricao);

			if (digitoGrupo && digitoSub) {

				return informarSubGrupoSelecionado(Integer.parseInt(codigoGrupoOuDescricao),
						Integer.parseInt(subGrupoCodigocodigoOuDescricao));

			} else if (!digitoGrupo && !digitoSub) {

				Grupo grupo = informarGrupoSelecionado(codigoGrupoOuDescricao);

				if (grupo != null) {

					SubGrupo subGrupo = pesquisaSubGrupoNaLista(grupo.getSubGrupos(), subGrupoCodigocodigoOuDescricao,
							0);

					informarSubGrupoSelecionado(subGrupo);
					return subGrupo;
				}
			}
		}

		return null;
	}

	/**
	 * Metodo que recebe o evento de seleção de sub grupo conseguentente tambem
	 * realiza a seleção do grupo no qual o sub-grupo esta inserido.
	 * 
	 * @param codigoGrupo
	 *            O codigo do grupo no qual pertence o sub-grupo
	 * @param subGrupoCodigo
	 *            O codido do sub-grupo selecionado
	 * 
	 * @return Retorna o grupo que foi selecionado
	 * 
	 * 
	 */
	public SubGrupo informarSubGrupoSelecionado(Integer codigoGrupo, Integer subGrupoCodigo) {

		Grupo grupo = null;

		if (grupoSelecionado == null || grupoSelecionado.getCodigo() != codigoGrupo) {
			grupo = informarGrupoSelecionado(codigoGrupo);
		} else {
			grupo = grupoSelecionado;
		}

		if (grupo != null) {

			SubGrupo subGrupo = pesquisaSubGrupoNaLista(grupo.getSubGrupos(), null, subGrupoCodigo);

			informarSubGrupoSelecionado(subGrupo);

			return subGrupo;

		}

		return null;
	}

	/**
	 * Pesquisa dentor da lista de subGrupos um subgrupo que tenha o codigo ou a
	 * descrição igual a do parametro. <br/>
	 * 
	 * <strong> Pesquisa somente por um dos parametros não os dois. Se o codigo
	 * estiver maior que zero a ele será a chave</strong>
	 * 
	 * @param subGrupos
	 * @param descricao
	 * @param codigo
	 * @return
	 */
	private SubGrupo pesquisaSubGrupoNaLista(List<SubGrupo> subGrupos, String descricao, int codigo) {

		if (StringUtil.isValida(descricao) || codigo != 0) {

			boolean pesquisaCodigo = codigo != 0;

			for (SubGrupo subGrupo : subGrupos) {

				if (pesquisaCodigo && subGrupo.getCodigo() == codigo) {

					return subGrupo;

				} else if (!pesquisaCodigo && subGrupo.getDescricao().equalsIgnoreCase(descricao)) {
					return subGrupo;
				}

			}

		}
		return null;

	}

	/**
	 * Metodo que recebe o evento da seleção de um subGrupo
	 * 
	 * @param subGrupoSelecionado
	 *            O Objeto do grupo selecionado
	 */
	public void informarSubGrupoSelecionado(SubGrupo subGrupoSelecionado) {

		deselecionarSubGrupoAnterior();
		this.subGrupoSelecionado = subGrupoSelecionado;

		if (subGrupoSelecionado != null) {

			subGrupoSelecionado.setSelecionado(true);
			subgrupoSelecionadoAnterior = subGrupoSelecionado;

			executarPesquisa(subGrupoSelecionado);
		}
	}

	/**
	 * Seta false no atributo selecionado do subGrupo seleceionado anteriormente
	 * caso esteja diferente de null
	 */
	private void deselecionarSubGrupoAnterior() {

		if (subgrupoSelecionadoAnterior != null) {
			subgrupoSelecionadoAnterior.setSelecionado(false);
		}

		subGrupoSelecionado = null;
	}

	/**
	 * Recebe o evento de qual Grupo foi selecionado
	 * 
	 * @param codigoOuDescricao
	 *            O codigo ou a descrição do grupo que foi selecionado
	 * 
	 * @return O Grupo que foi selecionado
	 */
	public Grupo informarGrupoSelecionado(String codigoOuDescricao) {

		if (StringUtil.isValida(codigoOuDescricao)) {

			boolean pesquisaPorCodigo = StringUtil.isSomenteDigitos(codigoOuDescricao);

			if (pesquisaPorCodigo) {

				return informarGrupoSelecionado(Integer.parseInt(codigoOuDescricao));

			} else {

				Grupo grupo = pesquisarGrupoNalista(grupoClassificacaoTodos, codigoOuDescricao, 0);

				informarGrupoSelecionado(grupo);

				return grupo;
			}
		}
		return null;
	}

	/**
	 * Pesquisa dentor da lista de grupo um grupo que tenha o codigo ou a descrição
	 * igual a do parametro. <br/>
	 * 
	 * <strong> Pesquisa somente por um dos parametros não os dois. Se o codigo
	 * estiver maior que zero a ele será a chave</strong>
	 * 
	 * @param grupos
	 * @param descricao
	 * @param codigo
	 * @return
	 */
	private Grupo pesquisarGrupoNalista(List<Grupo> grupos, String descricao, int codigo) {

		if (ListUtil.isValida(grupos) && (StringUtil.isValida(descricao) || codigo != 0)) {

			boolean pesquisaPorCodigo = codigo != 0;

			for (Grupo grupo : grupos) {

				if (pesquisaPorCodigo && grupo.getCodigo() == codigo) {
					return grupo;
				} else if (!pesquisaPorCodigo && grupo.getDescricao().equalsIgnoreCase(descricao)) {

					return grupo;
				}
			}
		}

		return null;
	}

	/**
	 * Recebe o evento de qual Grupo foi selecionado
	 * 
	 * @param codigoGrupo
	 *            O codigo do grupo que foi selecionado
	 * 
	 * @return O Grupo que foi selecionado
	 * 
	 *         public Grupo informarGrupoSelecionado(int codigoGrupo, boolean
	 *         montaPrimeiraVisualizacao) {
	 * 
	 *         Grupo grupo = pesquisarGrupoNalista(grupoClassificacaoResultado,
	 *         null, codigoGrupo);
	 * 
	 *         informarGrupoSelecionado(grupo, montaPrimeiraVisualizacao);
	 * 
	 *         return grupo; }
	 */
	/**
	 * Recebe o evento de qual Grupo foi selecionado
	 * 
	 * @param codigoGrupo
	 *            O codigo do grupo que foi selecionado
	 * 
	 * @return O Grupo que foi selecionado
	 */
	public Grupo informarGrupoSelecionado(int codigoGrupo) {

		Grupo grupo = pesquisarGrupoNalista(grupoClassificacaoTodos, null, codigoGrupo);

		informarGrupoSelecionado(grupo);

		return grupo;
	}

	/**
	 * Recebe o evento de qual Grupo foi selecionado
	 * 
	 * @param grupoSelecionado
	 *            O Grupo selecionado
	 * 
	 *            public void informarGrupoSelecionado(Grupo grupoSelecionado) {
	 *            informarGrupoSelecionado(grupoSelecionado); }
	 */

	/**
	 * 
	 * Monta a exibição do grupo selecionado se o grupo ja tem produtos o metodo
	 * simplesmete os exibe, Caso cotrario monta a primeora visualização do grupo
	 * caso o parametro montaPrimeiraVisualizacao == TRUE.
	 * 
	 * @see ProdutoPesquisaMB#montarVisualizacaoSubGrupoParaOGrupo(Grupo)
	 * 
	 * @param grupoSelecionado
	 *            O grupo que fou selecionado
	 * @param montaPrimeiraVisualizacao
	 *            Se permite montar a primeira visualização do grupo caso o mesmo
	 *            nçao tenha produtos
	 */
	private void informarGrupoSelecionado(Grupo grupoSelecionado) {

		if (grupoSelecionado == null) {
			return;
		}

		this.grupoSelecionado = grupoSelecionado;

		deselecionarSubGrupoAnterior();

		montarVisualizacaoGrupo(grupoSelecionado);

		indiceDoGrupoSelecionado = Integer
				.toString(indiceGrupo(grupoClassificacaoResultado, grupoSelecionado.getCodigo()));
		ordenarSubGruposDoGrupo(grupoSelecionado);
	}

	/**
	 * 
	 * @param grupoSelecionado
	 */
	private void montarVisualizacaoGrupo(Grupo grupoSelecionado) {

		if (grupoSelecionado.isVisualizarExclusiva()) {
			montarListaGrupoResultado(grupoSelecionado);
		}

		montarVisualizacaoSubGrupoParaOGrupo(grupoSelecionado);
	}

	/**
	 * 
	 * @param grupo
	 */
	private void ordenarSubGruposDoGrupo(Grupo grupo) {

		if (ListUtil.isValida(grupo.getSubGrupos())) {

			Collections.sort(grupo.getSubGrupos(), SubGrupo.getComparadorNome());

		}
	}

	/**
	 * Verifica na lista de grupos qual é o grupo e reotorna seu Indice
	 * 
	 * @param grupos
	 *            A lista que será pesquisada
	 * @param grupoCodigo
	 *            O grupo que deseja obter o Indice
	 * @return O indice do do grupo em parametro em relação a lista informada; CASO
	 *         não encotre será retornado 0 ou seja o indice do primeiro grupo
	 */
	private int indiceGrupo(List<Grupo> grupos, int grupoCodigo) {

		for (int i = 0; i < grupos.size(); i++) {

			if (grupos.get(i).getCodigo() == grupoCodigo) {
				return i;
			}
		}
		return 0;
	}

	/**
	 * Monta a primeira visualização do grupo; são dois casos possiveis:
	 * 
	 * 1° Caso o grupo tenha produtos que estajam em destaque (Produto: Destaque +
	 * Oferta) será exibidos estes produtos inicialmente. <br/>
	 * 
	 * 2° Caso não tenha produtos em destaque será exibidos os produtos referente ao
	 * primeiro subgrupo.
	 * 
	 * @param grupoSelecionado
	 */
	private void montarVisualizacaoSubGrupoParaOGrupo(Grupo grupoSelecionado) {

		SubGrupo subGrupoExibicao = null;

		if (grupoSelecionado.getCodigo() == Grupo.CODIGO_GRUPO_KIT) {

			subGrupoExibicao = grupoSelecionado.getSubGrupoTodos();

		} else if (grupoSelecionado.getSubGrupoDestaque() != null
				&& grupoSelecionado.getSubGrupoDestaque().isTemProdutos()) {

			subGrupoExibicao = grupoSelecionado.getSubGrupoDestaque();

		} else {

			List<Produto> produtosAux = new ArrayList<Produto>();

			produtosAux.addAll(produtosDestaque);
			produtosAux.addAll(produtosOferta);

			List<Produto> produtos = Produto.pesquisarNaLista(null, Integer.toString(grupoSelecionado.getCodigo()),
					produtosAux, true);

			if (ListUtil.isValida(produtos)) {

				grupoSelecionado.adicionarSubgrupoDestaque(produtos, propriedade.getMensagem("texto.destaque"));

				subGrupoExibicao = grupoSelecionado.getSubGrupoDestaque();

			} else if (grupoSelecionado.getSubGrupoTodos() != null
					&& grupoSelecionado.getSubGrupoTodos().isTemProdutos()) {

				subGrupoExibicao = grupoSelecionado.getSubGrupoTodos();

			} else {
				ordenarSubGruposDoGrupo(grupoSelecionado);

				for (SubGrupo subGrupo : grupoSelecionado.getSubGrupos()) {

					if (subGrupo.getCodigo() != SubGrupo.CODIGO_SUBGRUPO_DESTAQUE
							&& subGrupo.getCodigo() != SubGrupo.CODIGO_SUBGRUPO_TODOS) {

						subGrupoExibicao = subGrupo;
						break;
					}
				}
			}
		}

		informarSubGrupoSelecionado(subGrupoExibicao);
	}

	/**
	 * Recebe o evento de alteração do Grupos no painel de seleção de Grupos e sub
	 * Grupos
	 * 
	 * @param event
	 */
	public void tabChange(TabChangeEvent event) {

		Object object = event.getData();

		if (object instanceof Grupo) {
			informarGrupoSelecionado(((Grupo) object));
		}
	}

	/**
	 * Prepara a lista em parametro para a visualização na tela;
	 * 
	 * @param produtos
	 */
	private void montarListaProdutosResultado(List<? extends CaixaProdutoVisualizavel> produtos) {
		try {

			this.resultadosPesquisa.clear();

			Collections.sort(produtos, comparatorDescricaoEstoque);

			montarListaBannerResultado(produtos);

			resultadosPesquisa.addAll(produtos);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Monta o banner cetral de acordo com os produtos informados. Ou seja classifia
	 * os banners de exibição de acordo com o grupo, sub-grupo e categorias dos
	 * produtos
	 * 
	 * @param produtos
	 */
	private void montarListaBannerResultado(List<? extends CaixaProdutoVisualizavel> produtos) {

		if (isPerfilUsuarioPermiteBannerCentral()) {

			StringBuilder grupo = new StringBuilder();
			StringBuilder subGrupo = new StringBuilder();
			StringBuilder categoria = new StringBuilder();
			StringBuilder subCategoria = new StringBuilder();

			for (CaixaProdutoVisualizavel caixaProduto : produtos) {

				if (caixaProduto.getGrupoWidget() != null && caixaProduto.getGrupoWidget().getCodigo() > 0) {

					String grupoPro = "," + Integer.toString(caixaProduto.getGrupoWidget().getCodigo()) + ",";

					if (!StringUtil.contains(grupo.toString(), grupoPro)) {
						grupo.append(grupoPro);
					}
				}

				if (caixaProduto.getSubGrupoWidget() != null && caixaProduto.getSubGrupoWidget().getCodigo() > 0) {

					String subGrupoPro = "," + Integer.toString(caixaProduto.getSubGrupoWidget().getCodigo()) + ",";

					if (!StringUtil.contains(subGrupo.toString(), subGrupoPro)) {
						subGrupo.append(subGrupoPro);
					}
				}

				if (caixaProduto.getCategoriaCodigo() != null && caixaProduto.getCategoriaCodigo() > 0) {

					String categoriaPro = "," + caixaProduto.getCategoriaCodigo().toString() + ",";

					if (!StringUtil.contains(categoria.toString(), categoriaPro)) {
						categoria.append(categoriaPro);
					}
				}

				if (caixaProduto.getSubCategoriaCodigo() != null && caixaProduto.getSubCategoriaCodigo() > 0) {

					String subCategoriaPro = "," + caixaProduto.getSubCategoriaCodigo().toString() + ",";

					if (!StringUtil.contains(subCategoria.toString(), subCategoriaPro)) {
						subCategoria.append(subCategoriaPro);
					}
				}
			}

			List<Galeria> galeriasAplicaveis = getGaleriasBannerAplicaveis(grupo.toString(), subGrupo.toString(),
					categoria.toString(), subCategoria.toString());

			montarImagensBannerResultado(galeriasAplicaveis);
		}
	}

	/**
	 * 
	 * 
	 * @param grupo
	 * @param subGrupo
	 * @param categoria
	 * @param subCategoria
	 * 
	 * @return Os banners que são aplicados aos filtro informados
	 */
	private List<Galeria> getGaleriasBannerAplicaveis(String grupo, String subGrupo, String categoria,
			String subCategoria) {

		if (!ListUtil.isValida(galeriasBannerTodos)) {
			return null;
		}

		boolean verificar = StringUtil.isValida(grupo) || StringUtil.isValida(subGrupo)
				|| StringUtil.isValida(categoria) || StringUtil.isValida(subCategoria);

		List<Galeria> galerias = new ArrayList<Galeria>();

		for (Galeria galeria : galeriasBannerTodos) {

			if (verificar && galeria.eExclusiva()) {

				boolean cadidato = (StringUtil.contains(grupo, "," + galeria.getGrupoCodigo() + ","))
						|| (StringUtil.contains(subGrupo, "," + galeria.getSubGrupoCodigo() + ","))
						|| (StringUtil.contains(categoria, "," + galeria.getCategoriaProdutoCodigo() + ","))
						|| (StringUtil.contains(subCategoria, "," + galeria.getSubCategoriaProdutoCodigo() + ","));

				if (cadidato) {
					galerias.add(galeria);
				}

			} else if (!verificar && !galeria.eExclusiva()) {
				galerias.add(galeria);
			}
		}

		return galerias;
	}

	/**
	 * Monta a lista de imagens banners que será exibido na tela de acordo com as
	 * galerias em parametro
	 * 
	 * @param galerias
	 */
	private void montarImagensBannerResultado(List<Galeria> galerias) {

		List<Galeria> galeriasAux = ListUtil.isValida(galerias) ? galerias : galeriasBannerTodosNaoExclusivos;

		galeriasAux = ListUtil.isValida(galeriasAux) && galeriasAux.size() > 1 ? galerias.subList(0, 1) : galeriasAux;

		List<GaleriaImagem> galeriaImagemsAux = null;

		if (ListUtil.isValida(galeriasAux)) {

			List<GaleriaImagem> imagemsTemp = GaleriaImagem.pesquisarNaLista(imagensBannerTodas, galeriasAux, null);

			if (imagemsTemp != null) {
				galeriaImagemsAux = getImagensBannerSemDuplicidade(imagemsTemp);
			}
		}

		if (ListUtil.isValida(galeriaImagemsAux)) {

			Collections.sort(galeriaImagemsAux, GaleriaImagem.getComparadorOrdem());

			imagensBannerResultado.clear();
			imagensBannerResultado.addAll(galeriaImagemsAux);
		}
	}

	/**
	 * @param galeriaImagems
	 *            Imagens galerias para serem tratadas.
	 * 
	 * @return Retorna a lista em paramentro retirando as imagens duplicadas
	 */
	private List<GaleriaImagem> getImagensBannerSemDuplicidade(List<GaleriaImagem> galeriaImagems) {

		if (!ListUtil.isValida(galeriaImagems)) {
			return null;
		}

		List<GaleriaImagem> imagems = new ArrayList<GaleriaImagem>();

		for (GaleriaImagem galeriaImagem : galeriaImagems) {

			if (!existeImagensNaLista(imagems, galeriaImagem.getNomeImagem())) {
				imagems.add(galeriaImagem);
			}
		}

		return imagems;

	}

	/**
	 * Verifica se a imagem em parametro ja existe na lista
	 * 
	 * @param galeriaImagems
	 * @param nomeImagem
	 * 
	 * @return Retorna TRUE caso a nomeImagem estajá na lista de galerias imagens,
	 *         CASO contrario true
	 */
	private boolean existeImagensNaLista(List<GaleriaImagem> galeriaImagems, String nomeImagem) {

		if (ListUtil.isValida(galeriaImagems)) {

			for (int i = 0; i < galeriaImagems.size(); i++) {
				GaleriaImagem galeriaImagemI = galeriaImagems.get(i);

				if (StringUtil.isValida(galeriaImagemI.getNomeImagem()) && StringUtil.isValida(nomeImagem)
						&& galeriaImagemI.getNomeImagem().equals(nomeImagem)) {

					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Completa a lista de banners com a quantidade em parametro
	 * 
	 * @param galeriaImagemsParaCompletar
	 *            A lista de imagens para completar.
	 * @param quantidade
	 *            A quantidade que esta faltando
	 */
	@SuppressWarnings("unused")
	private void completarImagensBanner(List<GaleriaImagem> galeriaImagemsParaCompletar, int quantidade) {

		if (ListUtil.isValida(galeriasBannerTodosNaoExclusivos)) {

			quantidade = Math.abs(quantidade);

			List<GaleriaImagem> galeriaImagems = GaleriaImagem.pesquisarNaLista(imagensBannerTodas,
					galeriasBannerTodosNaoExclusivos, null);

			if (ListUtil.isValida(galeriaImagems)) {

				Collections.shuffle(galeriaImagems);

				int indiceMaximo = galeriaImagems.size() < quantidade ? galeriaImagems.size() : quantidade;

				galeriaImagemsParaCompletar.addAll(galeriaImagems.subList(0, indiceMaximo));

			}
		}
	}

	/*
	 * . (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.modelo.padrao.interfaces.Listavel#
	 * setListaResultados (java.util.List)
	 */
	@Override
	public void setListaResultados(List<CaixaProdutoVisualizavel> list) {
		montarListaProdutosResultado(list);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.modelo.padrao.interfaces.Listavel#
	 * getListaResultados ()
	 */
	@Override
	public List<CaixaProdutoVisualizavel> getListaResultados() {
		return resultadosPesquisa;
	}

	/**
	 * 
	 * @return A precificação do gerente pedido
	 */
	public Precificacao getPrecificacao() {

		Precificacao precificacao = gerentePedidoMB.getPrecificacao();

		/*
		 * Para incrementar velocidade de pesquisa, carregar lista completa de preços. A
		 * lista é válida durante toda a sessão, e é preenchida apenas para o preço-base
		 * padrão selecionado. Ao se mudar o preço-base, a lista é gerada novamente.
		 */
		if (precificacao.getListaPrecos() == null || this.precoBaseSelecionado != precificacao.getPrecoBaseCodigo()) {

			this.precoBaseSelecionado = precificacao.getPrecoBaseCodigo();

			precificacao.carregarPrecos(0, "pro_ativo = 1", null, 0);
		}

		return precificacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.modelo.padrao.interfaces.Pesquisavel#
	 * limparFiltros ()
	 */
	@Override
	public void limparFiltros() {
		filtroCodigoOuDescricao = null;
		filtroGrupoGrupoClassisificacao = null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.modelo.padrao.interfaces.Listavel#
	 * getPaginaAtualScroller()
	 */
	@Override
	public int getPaginaAtualScroller() {
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.modelo.padrao.interfaces.Listavel#
	 * setPaginaAtualScroller(int)
	 */
	@Override
	public void setPaginaAtualScroller(int paginaAtualScroller) {
	}

	public String getFiltroCodigoOuDescricao() {
		return filtroCodigoOuDescricao;
	}

	public void setFiltroCodigoOuDescricao(String filtroCodigoOuDescricao) {
		if (StringUtil.isValida(filtroCodigoOuDescricao)) {
			filtroCodigoOuDescricao = filtroCodigoOuDescricao.trim();
		}

		this.filtroCodigoOuDescricao = filtroCodigoOuDescricao;
	}

	public String getFiltroGrupoGrupoClassisificacao() {
		return filtroGrupoGrupoClassisificacao;
	}

	public void setFiltroGrupoGrupoClassisificacao(String filtroGrupoGrupoClassisificacao) {
		this.filtroGrupoGrupoClassisificacao = filtroGrupoGrupoClassisificacao;
	}

	/**
	 * Captura o evento de modificação no capo de filtro nome ou codigo;
	 * 
	 * @param changeEvent
	 */
	public void changeGrupo(ValueChangeEvent changeEvent) {
		setFiltroCodigoOuDescricao(changeEvent.getNewValue().toString());
	}

	public String getIndiceDoGrupoSelecionado() {
		return indiceDoGrupoSelecionado;
	}

	public void setIndiceDoGrupoSelecionado(String indiceDoGrupoSelecionado) {
		try {
			this.indiceDoGrupoSelecionado = indiceDoGrupoSelecionado;
		} catch (Exception e) {
			this.indiceDoGrupoSelecionado = "0";
		}

	}

	public Estoque getEstoque() {
		return gerentePedidoMB.getEstoque();
	}

	public List<GaleriaImagem> getImagensBannerTodas() {
		return imagensBannerTodas;
	}

	public List<GaleriaImagem> getImagensBannerResultado() {
		return imagensBannerResultado;
	}

	public ArrayList<Grupo> getGrupoClassificacaoResultado() {
		return grupoClassificacaoResultado;
	}

	public List<Grupo> getGrupoClassificacaoTodos() {
		return grupoClassificacaoTodos;
	}

	public ArrayList<Grupo> getGrupoClassificacaoFiltro() {
		return grupoClassificacaoFiltro;
	}

	/**
	 * Mais ou menos a mesma quantidade dos principais sites de venda
	 * 
	 * @return A quantidade maxima de banners que será exibida na tela
	 */
	public int getQuantidadeMinimaBanner() {
		return 7;
	}

	/**
	 * Retorna o intervalo em segundos para a transição dos Banners
	 * 
	 * @return
	 */
	public long getIntervaloExibicaoBanner() {
		return 4;
	}

	/**
	 * 
	 * @return
	 */
	public boolean eMultiplo() {
		return grupoClassificacaoResultado.size() == 1 || getGrupoSelecionadoCodigo() == grupoDestaque.getCodigo()
				|| getGrupoSelecionadoCodigo() == grupoOferta.getCodigo()
				|| (getGrupoSelecionadoCodigo() == grupoSelecionadoPrimeiroCodigo && isPrimeiraVisualizacao());

	}

	private int getGrupoSelecionadoCodigo() {

		if (grupoSelecionado != null) {
			return grupoSelecionado.getCodigo();
		}
		return 0;
	}

	@Override
	public String getNomePrograma() {
		return null;
	}

	@Override
	public boolean necessarioLogin() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.modelo.padrao.interfaces.IGerenteMB#getUrlView()
	 */
	@Override
	public String getUrlView() {
		return getUrlViewPattern();
	}

	public String getUrlView(String grupo, String subGrupo) {

		StringBuilder url = new StringBuilder(getUrlViewPattern());

		if (StringUtil.isValida(grupo)) {

			url.append("/").append(grupo);

			if (StringUtil.isValida(subGrupo)) {
				url.append("/").append(subGrupo);
			}
		}

		return url.toString();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.padrao.mbean.GerenteMB#getUrlViewPage()
	 */
	protected String getUrlViewPage() {
		return "/pages/pesquisaProdutosHome.xhtml";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.controle.padrao.mbean.GerenteMB#getUrlViewID()
	 */
	protected String getUrlViewPattern() {
		return "/produtos";
	}

	private boolean isRequisicaoInterna() {

		String urlRequisicao = getURLRequisicao();

		return StringUtil.isValida(urlRequisicao) && urlRequisicao.contains(getUrlViewPage());
	}

	@Override
	@URLActions(actions = { @URLAction(mappingId = "produtosG", onPostback = false),
			@URLAction(mappingId = "produtosGS", onPostback = false) })
	public void urlAction() {

		String urlStaticaUtilizada = getURLStaticaUtilizada();

		if (!urlStaticaUtilizada.equalsIgnoreCase(this.urlStaticaUtilizada)
				|| grupoESubgrupoDiferente(urlStaticaUtilizada)) {

			if (StringUtil.isValida(grupoURL) && StringUtil.isValida(subGrupoURL)) {

				informarSubGrupoSelecionado(grupoURL, subGrupoURL);

			} else if (StringUtil.isValida(grupoURL)) {
				informarGrupoSelecionado(grupoURL);
			}

			/*
			 * grupoURL = null; subGrupoURL = null;
			 */

		}
		this.urlStaticaUtilizada = urlStaticaUtilizada;
	}

	private String getURLStaticaUtilizada() {
		String url = "";

		if (StringUtil.isValida(grupoURL) && StringUtil.isValida(subGrupoURL)) {

			url = grupoURL + "/" + subGrupoURL;

		} else if (StringUtil.isValida(grupoURL)) {
			url = grupoURL;
		}

		return url;

	}

	public String getGrupoSubGrupoSelecionadoURL() {
		return grupoURL;
	}

	public void setGrupoSubGrupoSelecionadoURL(String grupoSelecionadoURL) {

		if (StringUtil.isValida(grupoSelecionadoURL) && !grupoSelecionadoURL.equalsIgnoreCase(grupoURL)
				&& grupoESubgrupoDiferente(grupoSelecionadoURL)) {

			/*
			 * Grupo grupo = null; SubGrupo subGrupo = null;
			 */

			String[] slipt = grupoSelecionadoURL.split("-");

			if (slipt != null && slipt.length > 0) {

				String grupoStr = slipt[0];

				if (StringUtil.isValida(grupoStr)) {

					if (slipt.length == 1) {

						informarGrupoSelecionado(grupoStr);

					} else if (slipt.length == 2) {

						String subGrupoStr = slipt[1];

						if (StringUtil.isValida(subGrupoStr)) {
							informarSubGrupoSelecionado(grupoStr, subGrupoStr);
						}
					}
				}
			}
		}

		this.grupoURL = grupoSelecionadoURL;
	}

	public String getGrupoURL() {
		return grupoURL;
	}

	public void setGrupoURL(String grupoURL) {
		this.grupoURL = grupoURL;
		setSubGrupoURL(null);
	}

	public String getSubGrupoURL() {
		return subGrupoURL;
	}

	public void setSubGrupoURL(String subGrupoURL) {
		this.subGrupoURL = subGrupoURL;
	}

	/**
	 * Verifica se o grupo e subgrupo selecionado atual é diferente da URL Informada
	 * 
	 * @param grupoSubGrupoSelecionadoURL
	 * @return
	 */
	private boolean grupoESubgrupoDiferente(String grupoSubGrupoSelecionadoURL) {

		StringBuilder descricao = new StringBuilder();
		StringBuilder codigo = new StringBuilder();

		if (grupoSelecionado != null) {
			descricao.append(grupoSelecionado.getDescricao());
			codigo.append(Integer.toString(grupoSelecionado.getCodigo()));

			if (subGrupoSelecionado != null) {
				descricao.append("/").append(subGrupoSelecionado.getDescricao());
				codigo.append("/").append(Integer.toString(subGrupoSelecionado.getCodigo()));
			}

			String[] split = grupoSubGrupoSelecionadoURL.split("/");

			if (split != null && split.length > 0) {

				boolean porCodigo = StringUtil.isSomenteDigitos(split[0]);

				if (porCodigo) {
					return !grupoSubGrupoSelecionadoURL.equalsIgnoreCase(codigo.toString());
				} else {
					return !grupoSubGrupoSelecionadoURL.equalsIgnoreCase(descricao.toString());
				}
			}

		}

		return true;
	}

	@Override
	public boolean permiteAcessoDeCliente() {
		return true;
	}

	public List<List<GaleriaImagem>> getListaMiniBanners() {
		return listaMiniBanners;
	}

	public void setListaMiniBanners(List<List<GaleriaImagem>> listaMiniBanners) {
		this.listaMiniBanners = listaMiniBanners;
	}

	public List<GaleriaImagem> getImagensBannerMaxi() {
		return imagensBannerMaxi;
	}

	public String getMensagemPesquisa() {
		return mensagemPesquisa;
	}

	public int getListaOuGridProduto() {
		return listaOuGridProduto;
	}

	public void setListaOuGridProduto(int listaOuGridProduto) {
		this.listaOuGridProduto = listaOuGridProduto;
	}

	public Integer getLayoutListaProduto() {
		return layoutListaProduto;
	}

	public void setLayoutListaProduto(int layoutListaProduto) {
		this.layoutListaProduto = layoutListaProduto;
	}

	public boolean isPrimeiraVisualizacao() {

		boolean isPrimeira = ListUtil.isValida(grupoClassificacaoResultado)
				&& ListUtil.isValida(grupoClassificacaoTodos)
				&& grupoClassificacaoResultado.size() == grupoClassificacaoTodos.size();

		return isPrimeira;

	}

	public int getNumeroLinha() {
		if ((resultadosPesquisa.size()) < numeroLinha) {

			return resultadosPesquisa.size();

		}
		return numeroLinha;
	}

	public void setNumeroLinha(int numeroLinha) {
		this.numeroLinha = numeroLinha;
	}

	public boolean isGridDimamico() {
		return grupoSelecionado != null && !grupoSelecionado.isVisualizarExclusiva();
	}

	public boolean isExibeBannerCentral() {
		return isPerfilUsuarioPermiteBannerCentral() && ListUtil.isValida(imagensBannerResultado);
	}

	public boolean isExibeMiniBanner() {

		return isPerfilUsuarioPermiteMiniBanner() && ListUtil.isValida(getListaMiniBanners());
	}

	public boolean isExibeMaxiBanner() {
		return isPerfilUsuarioPermiteMaxiBanner() && ListUtil.isValida(imagensBannerMaxi) && isPrimeiraVisualizacao();
	}

	private boolean isPerfilUsuarioPermiteMaxiBanner() {

		return gerenteLogin.isPerfilCliente() || (gerenteLogin.isPerfilColaborador()
				&& parametroWebMB.getParametroWeb().isExibeMaxiBannerColaborador());
	}

	private boolean isPerfilUsuarioPermiteBannerCentral() {
		return gerenteLogin.isPerfilCliente() || (gerenteLogin.isPerfilColaborador()
				&& parametroWebMB.getParametroWeb().isExibeBannerCentralColaborador());

	}

	private boolean isPerfilUsuarioPermiteMiniBanner() {
		boolean perfilPermite = gerenteLogin.isPerfilCliente() || (gerenteLogin.isPerfilColaborador()
				&& parametroWebMB.getParametroWeb().isExibeMiniBannerColaborador());

		return perfilPermite;

	}

	public boolean isExibeProdutoSemEstoque() {
		return exibeProdutoSemEstoque;
	}

	public boolean habilitarCompraRapida() {
		return Licenca.isModuloCompraRapidaAtiva();
	}

	private void exibirMensagensGerentePedido() {
		if (ListUtil.isValida(mensagensGenrentePedido)) {
			for (String msg : mensagensGenrentePedido) {
				exibirMensagemInformacao(msg, null);
			}
			mensagensGenrentePedido.clear();
		}
	}

	@Override
	public void comunicarUsuario(String mensagem) {
		mensagensGenrentePedido.add(mensagem);
	}
}
