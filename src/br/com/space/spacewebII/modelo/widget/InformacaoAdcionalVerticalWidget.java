package br.com.space.spacewebII.modelo.widget;

import java.util.List;

import br.com.space.api.core.util.ListUtil;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoInformacoesAdicionais;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoInformacoesAdicionaisPK;
import br.com.space.spacewebII.modelo.util.Fabrica;

/**
 * Classe que contem as informa��o na vertical e informa��es em tab's
 * 
 * @author Desenvolvimento
 * 
 */
public class InformacaoAdcionalVerticalWidget extends
		ProdutoInformacoesAdicionais {

	private static final long serialVersionUID = 1L;

	private List<ProdutoInformacoesAdicionais> informacoesEmTab = null;
	private String descricaoProduto = null;
	private boolean adicionarNomeProdutoTitulo = false;

	public InformacaoAdcionalVerticalWidget() {
	}

	/**
	 * Cria uma nova instancia
	 * 
	 * @param informacoesAdicionais
	 *            A informa��o de origem
	 * @param descricaoProduto
	 *            A descri��o do produto para qual pertence a informa��o
	 * @param adicionarNomeProdutoTitulo
	 *            TRUE para adcionar o nome do produto ao titulo da descri��o
	 */
	public InformacaoAdcionalVerticalWidget(
			ProdutoInformacoesAdicionais informacoesAdicionais,
			String descricaoProduto, boolean adicionarNomeProdutoTitulo) {

		setDados(informacoesAdicionais);
		this.descricaoProduto = descricaoProduto;
		this.adicionarNomeProdutoTitulo = adicionarNomeProdutoTitulo;
	}

	/**
	 * Cria um nova instancia contento a PK em parametro
	 * 
	 * @param informacoesAdicionaisPK
	 */
	public InformacaoAdcionalVerticalWidget(
			ProdutoInformacoesAdicionaisPK informacoesAdicionaisPK) {
		setProdutoinfoadicPK(informacoesAdicionaisPK);
	}

	/**
	 * Carrega as informa��es em Tab do produto em quest�o
	 * 
	 * @param dao
	 * @return TRUE se existir alguma informa��o em tab, caso contraio FALSE
	 */
	public boolean carregarInformacaoAdicionalTab(GenericoDAO dao) {

		informacoesEmTab = ProdutoInformacoesAdicionais
				.recuperarInformacaoesAdicionaisOrdenadasAtivas(dao,
						getProdutoCodigo(),
						ProdutoInformacoesAdicionais.TIPO_HORIZONTAL);

		if (ListUtil.isValida(informacoesEmTab)) {

			for (ProdutoInformacoesAdicionais info : informacoesEmTab) {

				String novoEndereco = Fabrica.formatarTextoComImagem(info
						.getInformacoesAdicionais());

				info.setInformacoesAdicionais(novoEndereco);

			}
		}
		return ListUtil.isValida(informacoesEmTab);
	}

	public List<ProdutoInformacoesAdicionais> getInformacoesEmTab() {
		return informacoesEmTab;
	}

	/**
	 * Copia os dados da "informacoesAdicionais" cuja sua instancia � a super
	 * classe para esta instancia
	 * 
	 * @param informacoesAdicionais
	 */
	public void setDados(ProdutoInformacoesAdicionais informacoesAdicionais) {

		setTitulo(informacoesAdicionais.getTitulo());

		informacoesAdicionais.setInformacoesAdicionais(Fabrica
				.formatarTextoComImagem(informacoesAdicionais
						.getInformacoesAdicionais()));

		setInformacoesAdicionais(informacoesAdicionais
				.getInformacoesAdicionais());
		setOrdem(informacoesAdicionais.getOrdem());

		setProdutoinfoadicPK(informacoesAdicionais.getProdutoinfoadicPK());
	}

	/**
	 * @return O titulo da informa��o <br/>
	 *         Se tiver halitado o nome do produto ser� retornado com a seguite
	 *         sintaxe:<br/>
	 *         titulo + " : " + descri��o do produto
	 * 
	 * @see br.com.space.spacewebII.modelo.dominio.estoque.ProdutoInformacoesAdicionais#getTitulo()
	 */
	@Override
	public String getTitulo() {
		String titulo = super.getTitulo();

		if (adicionarNomeProdutoTitulo && descricaoProduto != null
				&& !titulo.contains(descricaoProduto)) {
			titulo += " : " + descricaoProduto;

		}

		return titulo;

	}
}
