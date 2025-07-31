package br.com.space.spacewebII.modelo.widget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import br.com.space.api.core.util.ListUtil;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoInformacoesAdicionais;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoInformacoesAdicionaisPK;

/**
 * Classe destinada a recuperar classificar e montar todas as informa��es
 * adcionais de um produto
 * 
 * @author Desenvolvimento
 * 
 */
public class ProdutoInformacaoAdcionalWidget implements Serializable {

	private static final long serialVersionUID = 1L;

	private Produto produto = null;
	private String tituloPadrao = null;

	private boolean adicionarNomeProdutoTitulo = false;

	private List<InformacaoAdcionalVerticalWidget> informacoesAdcionaisVerticalWidget = null;

	/**
	 * 
	 * Construtor padrao. <br/>
	 * Constroi a classe e recupera todos as informa��es adcional (Vertical e
	 * Horizontal) do produto em parametro
	 * 
	 * @param produto
	 *            O Produto que sera trabalhado
	 * @param tituloPadrao
	 *            Caso n�o seja encontrado nenhum informa��o adicional vertical
	 *            � criada uma instancia com este titulo somente para carregar
	 *            as informa��es na horizontal
	 * @param dao
	 * @param adicionarNomeProdutoTitulo
	 *            TRUE caso queira que seje adicionado o nome do produto ao
	 *            titulo da informa�ao vertical
	 */
	public ProdutoInformacaoAdcionalWidget(Produto produto,
			String tituloPadrao, GenericoDAO dao,
			boolean adicionarNomeProdutoTitulo) {

		super();
		this.produto = produto;
		this.tituloPadrao = tituloPadrao;
		this.adicionarNomeProdutoTitulo = adicionarNomeProdutoTitulo;

		informacoesAdcionaisVerticalWidget = new ArrayList<InformacaoAdcionalVerticalWidget>();

		if (this.produto != null) {
			carregarInformacoesAdicionais(dao);
		}
	}

	/**
	 * @return TRUE caso exista informa��es para o produto, caso contrario FALSE
	 */
	public boolean temInformacaoVertical() {

		return ListUtil.isValida(informacoesAdcionaisVerticalWidget);

	}

	/**
	 * Carregas as informa��oes adcionais para o produto
	 * 
	 * @param dao
	 */
	public void carregarInformacoesAdicionais(GenericoDAO dao) {

		List<ProdutoInformacoesAdicionais> informacoesAdicionais = InformacaoAdcionalVerticalWidget
				.recuperarInformacaoesAdicionaisOrdenadasAtivas(dao,
						produto.getCodigo(),
						ProdutoInformacoesAdicionais.TIPO_VERTICAL);

		converterListaProdutoInformacaoAdicional(informacoesAdicionais,
				informacoesAdcionaisVerticalWidget);

		carregarInformacoesHorizontais(dao, informacoesAdcionaisVerticalWidget);
	}

	/**
	 * Carrega as informa�oes do tipo horizontal seguindo a logica abaixo
	 * 
	 * <br/>
	 * <ol>
	 * <li>Carregar as informa��es em tab na informa�ao vertical que tiver com o
	 * tiulo igual ao titulo padrao informado</li>
	 * 
	 * <li>Caso n�o encontre carrena na primeira da lista em parametro
	 * (informacoesAdcionaisVerticalWidget).</li>
	 * 
	 * <li>Caso n�o exista informa��o vertical na lista: � criado uma informa��o
	 * vertical com o titulo padrao e carrega as informa��es em tab mais so
	 * adciona na lista (informacoesAdcionaisVerticalWidget) caso exista
	 * informa��es em tab</li>
	 * </ol>
	 * 
	 * @param dao
	 * @param informacoesAdcionaisVerticalWidget
	 *            Todas as informa��es na vertical
	 */
	private void carregarInformacoesHorizontais(
			GenericoDAO dao,
			List<InformacaoAdcionalVerticalWidget> informacoesAdcionaisVerticalWidget) {

		InformacaoAdcionalVerticalWidget informacaoVerticalWidgetDescricao = null;

		if (ListUtil.isValida(informacoesAdcionaisVerticalWidget)) {

			informacaoVerticalWidgetDescricao = informacoesAdcionaisVerticalWidget
					.get(0);

			int indiceDescricao = getIndiceInformacaoTituloPadrao();

			if (indiceDescricao >= 0) {

				informacaoVerticalWidgetDescricao = informacoesAdcionaisVerticalWidget
						.get(indiceDescricao);
			}

			informacaoVerticalWidgetDescricao
					.carregarInformacaoAdicionalTab(dao);
		} else {

			ProdutoInformacoesAdicionaisPK adicionaisPK = new ProdutoInformacoesAdicionaisPK(
					produto.getCodigo(), tituloPadrao);

			informacaoVerticalWidgetDescricao = new InformacaoAdcionalVerticalWidget(
					adicionaisPK);

			boolean exiteInformacoo = informacaoVerticalWidgetDescricao
					.carregarInformacaoAdicionalTab(dao);

			if (exiteInformacoo) {

				informacoesAdcionaisVerticalWidget
						.add(informacaoVerticalWidgetDescricao);
			}
		}
	}

	/**
	 * Percore a lista de "informacoesAdcionaisVerticalWidget" ate que seja
	 * encontrada uma informa��o que inicie com o mesmo titulo que o
	 * "tituloPadrao"
	 * 
	 * @return O indice da primeira ocorrencia que inicie com o titulo padr�o.
	 *         Caso N�o exista retorna -1
	 */
	private int getIndiceInformacaoTituloPadrao() {

		if (informacoesAdcionaisVerticalWidget != null) {

			for (int i = 0; i < informacoesAdcionaisVerticalWidget.size(); i++) {

				ProdutoInformacoesAdicionais adicionais = informacoesAdcionaisVerticalWidget
						.get(i);

				if (adicionais.getTitulo() != null
						&& tituloPadrao != null
						&& adicionais.getTitulo().toLowerCase()
								.startsWith(tituloPadrao.toLowerCase())) {

					return i;
				}
			}
		}

		return -1;

	}

	/**
	 * Converte os objetos ProdutoInformacoesAdicionais em
	 * InformacaoAdcionalVerticalWidget e os adciona na lista
	 * "adcionalVerticalWidgets"
	 * 
	 * @param informacoesAdicionais
	 *            Objetos a serem converdidos
	 * @param adcionalVerticalWidgets
	 *            Lista que ser�o arqmazenados os objetos convertidos
	 */
	private void converterListaProdutoInformacaoAdicional(
			List<ProdutoInformacoesAdicionais> informacoesAdicionais,
			List<InformacaoAdcionalVerticalWidget> adcionalVerticalWidgets) {

		if (!ListUtil.isValida(informacoesAdicionais)) {
			return;
		}

		for (ProdutoInformacoesAdicionais produtoInformacoesAdicionais : informacoesAdicionais) {

			InformacaoAdcionalVerticalWidget adcionalVerticalWidget = new InformacaoAdcionalVerticalWidget(
					produtoInformacoesAdicionais, produto.getDescricao(),
					adicionarNomeProdutoTitulo);

			adcionalVerticalWidgets.add(adcionalVerticalWidget);
		}
	}

	public List<InformacaoAdcionalVerticalWidget> getInformacoesAdcionaisVerticalWidget() {
		return informacoesAdcionaisVerticalWidget;
	}

	public Produto getProduto() {
		return produto;
	}
}
