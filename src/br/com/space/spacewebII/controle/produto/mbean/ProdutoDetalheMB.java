package br.com.space.spacewebII.controle.produto.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import org.primefaces.event.TabChangeEvent;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.negocio.modelo.dominio.IFormaPagamento;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.api.negocio.modelo.dominio.produto.PrecoVenda;
import br.com.space.spacewebII.controle.padrao.mbean.GerenteMB;
import br.com.space.spacewebII.controle.pedido.mbean.GerentePedidoMB;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dominio.estoque.MarcaProduto;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoInformacoesAdicionais;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoMidia;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.dominio.venda.Kit;
import br.com.space.spacewebII.modelo.util.Acao;
import br.com.space.spacewebII.modelo.util.CaixaProdutoUtil;
import br.com.space.spacewebII.modelo.util.Fabrica;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;
import br.com.space.spacewebII.modelo.widget.CaixaVisualizavelList;
import br.com.space.spacewebII.modelo.widget.ProdutoInformacaoAdcionalWidget;

@ManagedBeanSessionScoped
public class ProdutoDetalheMB extends GerenteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	protected GerentePedidoMB gerentePedidoMB;

	@Inject
	protected ProdutoPesquisaMB produtoPesquisaMB;

	protected Acao acao = null;

	protected CaixaProdutoVisualizavel caixaProdutoSelecionado = null;

	private List<ProdutoInformacaoAdcionalWidget> produtoInformacaoAdcionalWidgets;
	private CaixaVisualizavelList produtosSemelhantes;
	private List<ProdutoMidia> produtoImagens;
	private List<ProdutoMidia> produtoVideos;
	private MarcaProduto marca = null;
	private ProdutoInformacoesAdicionais descricaoAdicional;

	private boolean exibirVideosRelacionadosYoutube = false;
	private boolean exibirImagens = true;

	protected PrecoVenda precoVenda = null;

	private ProdutoUnidade unidadeEmbarque = null;

	private boolean exibeInformacaoAdcional = false;

	private Comparator<CaixaProdutoVisualizavel> comparatorDescricaoEstoque = null;

	public ProdutoDetalheMB() {
	}

	/**
	 * Construtor padrão executado pelo CDI
	 */
	@PostConstruct
	public void posContrutor() {

		try {
			produtosSemelhantes = new CaixaVisualizavelList() {

				private static final long serialVersionUID = 1L;

				@Override
				public void aoRetornarUmObjeto(CaixaProdutoVisualizavel produtoVisualizavel) {

					try {
						produtoVisualizavel.preencherAtributos(dao, gerentePedidoMB.getGerentePedido(), true, false);
					} catch (Exception e) {

						e.printStackTrace();
					}

				}
			};

			comparatorDescricaoEstoque = CaixaProdutoUtil
					.getNewComparatorDescricaoEstoque(!parametroWebMB.getParametroWeb().isOrdenaProdutoSemEstoque());

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Metodo chamado pela tela para informar qual é o produto cuja os detalhes
	 * serão exibidos. Recarregando novamente os dados do produto ou kit
	 * 
	 * @param caixaProdutoVisualizavel
	 */
	public void visualizarDetalhes(CaixaProdutoVisualizavel caixaProdutoVisualizavel) {

		visualizarDetalhes(caixaProdutoVisualizavel, true);

	}

	/**
	 * Metodo chamado pela tela para informar qual é o produto cuja os detalhes
	 * serão exibidos
	 * 
	 * @param caixaProdutoVisualizavel
	 * @param carregarCaixaNovamente
	 *            Se busca novamente o produto ou o Kit no banco de dados
	 */
	protected void visualizarDetalhes(CaixaProdutoVisualizavel caixaProdutoVisualizavel,
			boolean carregarCaixaNovamente) {
		try {
			acao = Acao.VISUALIZAR;

			carregarCaixaProdutoVisualizavel(caixaProdutoVisualizavel, carregarCaixaNovamente, true);

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * Carrega os dados da caixa de produto visualizavel
	 * 
	 * @param caixaProdutoVisualizavel
	 * @param carregarCaixaNovamente
	 *            Se busca novamente o produto ou o Kit no banco de dados
	 * @throws Exception
	 */

	protected void carregarCaixaProdutoVisualizavel(CaixaProdutoVisualizavel caixaProdutoVisualizavel,
			boolean carregarCaixaNovamente, boolean preencheDadosAdicionais) throws Exception {

		if (carregarCaixaNovamente) {

			carregarCaixaProdutoVisualizavel(caixaProdutoVisualizavel.getCodigo(), caixaProdutoVisualizavel.eKit(),
					preencheDadosAdicionais);

		} else {

			setCaixaProdutoSelecionado(caixaProdutoVisualizavel, false, preencheDadosAdicionais);
		}
	}

	/**
	 * Metodo que carrega as informações do codigo do produto ou kit conforme
	 * parametro
	 * 
	 * @param codigo
	 * @param eKit
	 * @throws Exception
	 */
	protected void carregarCaixaProdutoVisualizavel(int codigo, boolean eKit, boolean preencheDadosAdicionais)
			throws Exception {

		CaixaProdutoVisualizavel visualizavelCandidata = null;

		if (eKit) {
			visualizavelCandidata = Kit.recuperar(dao, codigo);

		} else {
			visualizavelCandidata = Produto.recuperarCodigo(dao, codigo);
		}

		setCaixaProdutoSelecionado(visualizavelCandidata, true, preencheDadosAdicionais);

	}

	/**
	 * Reinicia uma porção das variaveis globais da classe
	 */
	protected void prepararVariaveis() {

		if (produtoInformacaoAdcionalWidgets == null) {
			produtoInformacaoAdcionalWidgets = new ArrayList<ProdutoInformacaoAdcionalWidget>();
		} else {
			produtoInformacaoAdcionalWidgets.clear();
		}

		exibeInformacaoAdcional = false;

		if (produtoImagens == null) {
			produtoImagens = new ArrayList<ProdutoMidia>();
		} else {
			produtoImagens.clear();
		}

		if (produtoVideos == null) {
			produtoVideos = new ArrayList<ProdutoMidia>();
		} else {
			produtoVideos.clear();
		}

		this.produtosSemelhantes.clear();

		this.exibirImagens = true;

		this.marca = null;
		this.unidadeEmbarque = null;

		this.caixaProdutoSelecionado = null;
	}

	protected void preencherAtributos(CaixaProdutoVisualizavel caixaProdutoVisualizavel) throws Exception {

		caixaProdutoVisualizavel.preencherAtributos(dao, gerentePedidoMB.getGerentePedido(), true, true);
	}

	/**
	 * Carrega dados da caixa de produto visualizavel
	 * 
	 * @param preencherAtributosNovamente
	 *            Se preenche os atributos novamente do produto
	 * @param preencheDadosadicionais
	 *            Caso TRUE carrega os produtos similiar
	 *            {@link #recuperarCarregarProdutosSemelhantes(Produto)}, dados
	 *            do produto {@link #preencherDadosFixosProduto(Produto, int)},
	 *            descrição adcional para produto é Kit
	 *            {@link #carregarDescricoesAdicionais(List)} e as midias de
	 *            produto ou kit
	 *            {@link #carregarMidias(CaixaProdutoVisualizavel)}
	 * @throws Exception
	 */
	protected void carregarDadosDaCaixaProdutoSelecionado(boolean preencherAtributosNovamente,
			boolean preencheDadosadicionais) throws Exception {

		int codigoFilial = gerenteLogin.getCodigoFilialSelecionada();

		if (preencherAtributosNovamente) {
			preencherAtributos(caixaProdutoSelecionado);
		}

		List<Produto> produtos = null;

		PrecoVenda precoVenda = (PrecoVenda) caixaProdutoSelecionado.getPrecoVenda();

		if (caixaProdutoSelecionado.eProduto()) {

			Produto produto = getProdutoSelecionado();

			produtos = new ArrayList<Produto>();
			produtos.add(produto);

			if (preencheDadosadicionais) {
				preencherDadosFixosProduto(produto, codigoFilial);
				recuperarCarregarProdutosSemelhantes(produto);
			}

		} else if (caixaProdutoSelecionado.eKit()) {

			Kit kit = getKitSelecionado();
			produtos = kit.getProdutosKit();
		}

		if (preencheDadosadicionais) {
			carregarDescricoesAdicionais(produtos);
			carregarMidias(caixaProdutoSelecionado);
		}

		carregarValores(precoVenda);
	}

	/**
	 * Preenche dados fixos do produto que dependem de filial
	 * 
	 * @param produto
	 * @param codigoFilial
	 * @param preencheen
	 */
	private void preencherDadosFixosProduto(Produto produto, int codigoFilial) {

		if (produto != null) {
			setUnidadeEmbarque(ProdutoUnidade.recuperarPadraoEmbarque(dao, produto.getCodigo()));

			setMarca(MarcaProduto.recuperarUnico(dao, produto.getMarcaCodigo()));

			/*
			 * não está sendo usado. if (produto.getFabricanteCodigo() != 0)
			 * fabricante = Fabricante.recuperaFabricanteAtivo(dao,
			 * produto.getFabricanteCodigo(), codigoFilial);
			 */
		}
	}

	/**
	 * Carrega o valor de venda que será exibido apartir do PrecoVenda em
	 * parametro
	 * 
	 * @param precoVenda
	 */
	protected void carregarValores(PrecoVenda precoVenda) {
		this.precoVenda = precoVenda;
	}

	/**
	 * Carrega as descrições adicionais da lista de produtos informada.
	 * 
	 * @param produtos
	 *            A lista de produtos para que seja montada a descrição
	 *            adicional
	 */
	private void carregarDescricoesAdicionais(List<Produto> produtos) {

		if (produtos != null) {

			for (Produto produto : produtos) {

				ProdutoInformacaoAdcionalWidget informacaoAdcionalWidget = new ProdutoInformacaoAdcionalWidget(produto,
						propriedade.getMensagem("texto.descricao"), dao, produtos.size() > 1);

				if (informacaoAdcionalWidget.temInformacaoVertical()) {
					produtoInformacaoAdcionalWidgets.add(informacaoAdcionalWidget);

					exibeInformacaoAdcional = true;
				}
			}
		}
	}

	/**
	 * 
	 * @return TRUE para que a informação adcional seja exibida, caso contrario
	 *         FALSE
	 */
	public boolean isExibeInformacaoAdcional() {
		return exibeInformacaoAdcional && ListUtil.isValida(produtoInformacaoAdcionalWidgets);
	}

	/**
	 * 
	 * @param exibeInformacaoAdcional
	 */
	public void setExibeInformacaoAdcional(boolean exibeInformacaoAdcional) {
		this.exibeInformacaoAdcional = exibeInformacaoAdcional;
	}

	/**
	 * Preenche as descrições adicionais, tanto os boxes quanto as tabs
	 * 
	 * @param produtoCodigo
	 */
	public void recuperarCarregarDescricoesAdicionais(Produto produto) {

	}

	/**
	 * Preenche imagens e videos da caixa de produto indempendete se é produto
	 * ou kit.
	 * 
	 * @param caixaProdutoVisualizavel
	 */
	public void carregarMidias(CaixaProdutoVisualizavel caixaProdutoVisualizavel) {

		if (isPerfilUsuaioPermiteImagemProduto()) {

			List<ProdutoMidia> midias = null;

			if (caixaProdutoVisualizavel.eKit()) {

				midias = ProdutoMidia.recuperaMidiasAtivasDeKit(dao, caixaProdutoVisualizavel.getCodigo());

			} else if (caixaProdutoVisualizavel.eProduto()) {
				midias = ProdutoMidia.recuperaMidiasAtivasDeProduto(dao, caixaProdutoVisualizavel.getCodigo());
			}

			classificarMidiasEmImagemVideo(midias);

			if (ListUtil.isValida(produtoImagens)) {

				int indiceImagemCapa = -1;

				for (int i = 0; i < produtoImagens.size(); i++) {
					ProdutoMidia produtoMidia = produtoImagens.get(i);

					if (produtoMidia.getOrdem() == 0) {

						caixaProdutoVisualizavel.setImagemCapa(produtoMidia);
						indiceImagemCapa = i;
						break;
					}
				}

				if (produtoImagens.size() > 1 && indiceImagemCapa >= 0) {
					produtoImagens.remove(indiceImagemCapa);
				}
			}
		} else if (caixaProdutoVisualizavel.getImagemCapa() == null) {
			carregarImagemCapa(caixaProdutoVisualizavel);
		}
	}

	/**
	 * 
	 * @param caixaProdutoVisualizavel
	 */
	protected void carregarImagemCapa(CaixaProdutoVisualizavel caixaProdutoVisualizavel) {
		ProdutoMidia imagemCapa = null;

		if (caixaProdutoVisualizavel.eKit()) {
			imagemCapa = ProdutoMidia.recuperarImagemCapaKit(dao, caixaProdutoVisualizavel.getCodigo());
		} else {
			imagemCapa = ProdutoMidia.recuperarImagemCapaAtiva(dao, caixaProdutoVisualizavel.getCodigo());
		}

		if (imagemCapa != null) {
			imagemCapa.setEnderecoMidia(Fabrica.gerarEnderecoImagemSemPasta(imagemCapa.getEnderecoMidia()));
			caixaProdutoVisualizavel.setImagemCapa(imagemCapa);
		}

	}

	/**
	 * Classifica as midias em videos e fotos
	 * 
	 * @param midias
	 */
	private void classificarMidiasEmImagemVideo(List<ProdutoMidia> midias) {

		if (midias == null || midias.size() == 0) {
			return;
		}

		for (ProdutoMidia midia : midias) {
			if (midia.getTipoMidia() == 0) {

				midia.setEnderecoMidia(formatacaoMB.formatarEnderecoImagemSemPasta(midia.getEnderecoMidia()));

				produtoImagens.add(midia);

			} else if (midia.getTipoMidia() == 1) {
				midia.setEnderecoMidia(
						formatacaoMB.formatarUrlYoutube(midia.getEnderecoMidia(), exibirVideosRelacionadosYoutube));
				produtoVideos.add(midia);
			}
		}

	}

	/**
	 * Carrega os produtos similares do produto em parametro
	 * 
	 * @param produto
	 */
	public void recuperarCarregarProdutosSemelhantes(Produto produto) {

		try {

			List<Produto> todosProdutosSemelhantes = Produto.recuperarProdutosSimilaresVendaUsandoResultSet(dao,
					getFilialCodigo(), getPrecificacao().getPrecoBaseCodigo(), produto,
					parametroWebMB.getParametroWeb().isExibeProdutoSemEstoque());

			if (ListUtil.isValida(todosProdutosSemelhantes)) {

				Produto.preencherAtributosProduto(dao, gerenteLogin.getFilialCodigo(),
						gerentePedidoMB.getGerentePedido(), todosProdutosSemelhantes);

				produtosSemelhantes.addAll(todosProdutosSemelhantes);

				java.util.Collections.sort(produtosSemelhantes, comparatorDescricaoEstoque);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Evento que captura a mudança de tab entre imagem e video
	 * 
	 * @param event
	 */
	public void onTabGaleriaChange(TabChangeEvent event) {
		if (event.getTab().getId().equals("galeriaImagens")) {
			exibirImagens = true;
		} else if (event.getTab().getId().equals("galeriaVideos")) {
			exibirImagens = false;
		}
	}

	/**
	 * Retorna para a pagina de produto pesquisa informando qual o grupo
	 * selecionado
	 * 
	 * @return A url view de ProdutoPesquisa
	 *         {@link ProdutoPesquisaMB#getUrlView()}
	 */
	public String getUrlProdutoPesquisaInformandoGrupo() {

		int cod = caixaProdutoSelecionado.getGrupoWidget() != null
				? caixaProdutoSelecionado.getGrupoWidget().getCodigo() : 0;

		return produtoPesquisaMB.getUrlView() + "/" + cod;

	}

	/**
	 * Retorna para a pagina de produto pesquisa informando qual o grupo e o
	 * sub-grupo selecionado selecionado
	 * 
	 * @return
	 */
	public String getUrlPesquisaProdutoInformandoGrupoESubGrupo() {

		int codigoGrupo = caixaProdutoSelecionado.getGrupoWidget() != null
				? caixaProdutoSelecionado.getGrupoWidget().getCodigo() : 0;

		int codigoSubGrupo = caixaProdutoSelecionado.getSubGrupoWidget() != null
				? caixaProdutoSelecionado.getSubGrupoWidget().getCodigo() : 0;

		return produtoPesquisaMB.getUrlView() + "/" + codigoGrupo + "/" + codigoSubGrupo;

	}

	public String getUrlGrupo() {
		return produtoPesquisaMB.getUrlView() + "/" + caixaProdutoSelecionado.getGrupoWidget().getCodigo();

	}

	public CaixaProdutoVisualizavel getCaixaProdutoSelecionado() {
		return caixaProdutoSelecionado;
	}

	public void setCaixaProdutoSelecionado(CaixaProdutoVisualizavel caixaProdutoSelecionado,
			boolean preencherAtributosNovamente, boolean preencheDadosAdicionais) throws Exception {

		if (caixaProdutoSelecionado != null) {
			prepararVariaveis();
			this.caixaProdutoSelecionado = caixaProdutoSelecionado;
			carregarDadosDaCaixaProdutoSelecionado(preencherAtributosNovamente, preencheDadosAdicionais);

		} else {
			throw new Exception(propriedade.getMensagem("alerta.produto.informacoes"));
		}
	}

	public MarcaProduto getMarca() {
		return marca;
	}

	public void setMarca(MarcaProduto marca) {
		this.marca = marca;
	}

	public boolean getExibirImagens() {
		return exibirImagens;
	}

	public void setExibirImagens(boolean exibirImagens) {
		this.exibirImagens = exibirImagens;
	}

	public Precificacao getPrecificacao() {
		return gerentePedidoMB.getPrecificacao();
	}

	public ProdutoInformacoesAdicionais getDescricaoAdicional() {
		return descricaoAdicional;
	}

	public void setDescricaoAdicional(ProdutoInformacoesAdicionais descricaoAdicional) {
		this.descricaoAdicional = descricaoAdicional;
	}

	public List<ProdutoMidia> getProdutoImagens() {
		return produtoImagens;
	}

	public void setProdutoImagens(List<ProdutoMidia> produtoImagens) {
		this.produtoImagens = produtoImagens;
	}

	public List<ProdutoMidia> getProdutoVideos() {
		return produtoVideos;
	}

	public void setProdutoVideos(List<ProdutoMidia> produtoVideos) {
		this.produtoVideos = produtoVideos;
	}

	public CaixaVisualizavelList getProdutosSemelhantes() {
		return produtosSemelhantes;
	}

	public boolean eProduto() {

		return caixaProdutoSelecionado instanceof Produto;
	}

	public boolean ekit() {
		return caixaProdutoSelecionado instanceof Kit;
	}

	public Kit getKitSelecionado() {
		if (caixaProdutoSelecionado instanceof Kit) {
			return (Kit) caixaProdutoSelecionado;
		}
		return null;
	}

	public Produto getProdutoSelecionado() {
		if (caixaProdutoSelecionado instanceof Produto) {

			return (Produto) caixaProdutoSelecionado;

		}
		return null;
	}

	public List<ProdutoInformacaoAdcionalWidget> getProdutoInformacaoAdcionalWidgets() {
		return produtoInformacaoAdcionalWidgets;
	}

	public List<? extends IFormaPagamento> getFormasPagamentoPermitidas() {
		return gerentePedidoMB.getGerentePedido().getFormasPagamentoPermitidas();
	}

	@Override
	public String getNomePrograma() {

		return null;
	}

	@Override
	public String getUrlView() {
		return getUrlViewPage();
	}

	@Override
	protected String getUrlViewPage() {
		return "/pages/detalheProduto.xhtml";
	}

	@Override
	protected String getUrlViewPattern() {
		return null;
	}

	@Override
	public boolean necessarioLogin() {
		return false;
	}

	public void setUnidadeEmbarque(ProdutoUnidade unidadeEmbarque) {
		this.unidadeEmbarque = unidadeEmbarque;
	}

	public ProdutoUnidade getUnidadeEmbarque() {
		return unidadeEmbarque;
	}

	@Override
	public boolean permiteAcessoDeCliente() {
		return true;
	}

	public Acao getAcao() {
		return acao;
	}

	/**
	 * 
	 * @return TRUE se é possivel exibir o preço unitario, caso contrario FALSE
	 */
	public boolean isExibePrecoUnitario() {
		return isExibePrecoVisitante() && parametroWebMB.getParametroWeb().isExibePrecoUnitario();
	}

	/**
	 * 
	 * @return TRUE se exibir o preço para visitanre , caso contrario FALSE
	 */
	public boolean isExibePrecoVisitante() {
		return gerenteLogin.isSessoaAutenticada() || parametroWebMB.getParametroWeb().isExibePrecoVisitante();
		// || getCaixaProdutoSelecionado().isExibePrecoVisitante();
	}

	public boolean isExibeImagemProduto() {

		return (ListUtil.isValida(produtoImagens) || ListUtil.isValida(produtoVideos))
				&& isPerfilUsuaioPermiteImagemProduto();
	}

	private boolean isPerfilUsuaioPermiteImagemProduto() {
		return gerenteLogin.isPerfilCliente() || (gerenteLogin.isPerfilColaborador()
				&& parametroWebMB.getParametroWeb().isExibeImagemProdutoColaborador());
	}

	public boolean isExibeEstoque() {
		return gerenteLogin.isSessoaAutenticada()
				&& (getCaixaProdutoSelecionado() == null || !getCaixaProdutoSelecionado().isEmBalanco())
				&& (gerenteLogin.isPerfilColaborador() || parametroWebMB.getParametroWeb().isExibeEstoqueCliente());
	}

	public boolean isExibeUnidadeProdutoLista() {
		return gerenteLogin.isPerfilColaborador()
				|| parametroWebMB.getParametroWeb().getFlagExibeUnidadeProdutoLista() == 1;
	}

	public boolean isExibePesoProduto() {

		return parametroWebMB.getParametroWeb().isFlagExibePesoProduto();
	}

	public boolean isPermiteSelecionarUnidadeCompraRapida() {
		return parametroWebMB.getParametroWeb().getFlagPermiteSelecionarUnidadeCompraRapida() == 1;
	}

	public double getPeso() {
		return caixaProdutoSelecionado.getPeso();
	}
}
