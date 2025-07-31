package br.com.space.spacewebII.modelo.widget;

import java.io.Serializable;
import java.util.List;

import br.com.space.api.negocio.modelo.dominio.ICliente;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.api.negocio.modelo.padrao.interfaces.Precificavel;
import br.com.space.spacewebII.controle.estoque.Estoque;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoMidia;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;

/**
 * Interface que padroniza a exibiçao dos produtos na tela
 * 
 * @author Desnvovimento
 */
public interface CaixaProdutoVisualizavel extends PrecoVisualizavel, Precificavel, Serializable {

	/**
	 * @return O Descrição que do produto que será visivil na tela
	 */
	public String getDescricaoVisualizacao();

	/**
	 * 
	 * @return O codigo do produto
	 */
	public int getCodigo();

	/**
	 * @return O peso do produto
	 */
	public double getPeso();

	/**
	 * @return A descriçao da unidade
	 */
	public String getUnidadeDescricao();

	/**
	 * 
	 * @return Se é permitido exibir preço unitário.
	 */
	public boolean exibirPrecoUnitario();

	/**
	 * @return Se for um kit a lista ira conter todos os produto que compoem o
	 *         Kit
	 */
	public List<Produto> getProdutosKit();

	public List<ProdutoUnidade> getUnidades();

	/**
	 * @return TRUE caso o produto esteje em promoção, caso contrario FALSE
	 */
	public boolean getEmPromocao();

	/**
	 * @return TRUE caso o produto esteje em campanha, caso contrario FALSE
	 */
	public boolean getEmCampanha();

	/**
	 * @return TRUE caso o produto esteje em oferta, caso contrario FALSE
	 */
	public boolean getEmOferta();

	/**
	 * @return A quantidade do produto no estoque dividido pelo fator de estoque
	 *         da unidade padrao de venda
	 */
	public double getEstoqueVisualizacao();

	/**
	 * @return A quantidade do produto no estoque da unidade padrao de estoque
	 */
	public double getEstoque();

	/**
	 * Metodo de ação definido para popular todos os atributos da classe.
	 * Somente os atributos que se jugar necessario.
	 * 
	 * @param dao
	 *            A instancia do banco onde os dados estará disponiveis
	 * @param filialCodigo
	 *            O codigo da filial
	 * @param precificacao
	 *            O intancia da precificalação
	 * @param parametros
	 *            A instancia dos parametros
	 * @throws Exception
	 *             Caso algum erro ocorra;
	 */
	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, boolean atualizarFlags,
			boolean forcarRefreshPrecos) throws Exception;

	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, ProdutoUnidade produtoUnidade,
			boolean atualizarFlags, boolean forcarRefreshPrecos) throws Exception;

	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, ProdutoUnidade produtoUnidade,
			boolean atualizarFlags, boolean forcarRefreshPrecos, boolean recuperaEstoque) throws Exception;

	public ProdutoMidia getImagemCapa();

	public void setImagemCapa(ProdutoMidia produtoMidia);

	public boolean eKit();

	public boolean eProduto();

	public GrupoWidget getGrupoWidget();

	public SubGrupoWidget getSubGrupoWidget();

	public Integer getCategoriaCodigo();

	public Integer getSubCategoriaCodigo();

	public void setQuantidadeCompraRapida(double quantidadeCompraRapida);

	public double getQuantidadeCompraRapida();

	public boolean isPermiteVenderSemEstoque();

	public boolean isExibePrecoVisitante();

	public boolean isEmBalanco();

	public boolean atulizarEmbalanco(int codigoFilial, GenericoDAO dao);
}
