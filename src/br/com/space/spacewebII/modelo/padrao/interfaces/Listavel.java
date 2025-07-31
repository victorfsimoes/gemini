package br.com.space.spacewebII.modelo.padrao.interfaces;

import java.util.List;

/**
 * 
 * @author Desenvolvimento
 * 
 * @param <T>
 *            Tipo da lista
 */
public interface Listavel<T> {

	/**
	 * Atribui � lista de pesquisa a lista no par�metro
	 * 
	 * @param list
	 *            Lista a ser atribu�da � lista de pesquisa
	 */
	public void setListaResultados(List<T> list);

	/**
	 * 
	 * @return Uma lista contendo os objetos referidos na pesquisa
	 */
	public List<T> getListaResultados();

	/**
	 * M�todo de extrema import�ncia. Seu objetivo � armazenar a posi��o do
	 * p�gina Scroller
	 * 
	 * @return A p�gina atual do scroller
	 */
	public int getPaginaAtualScroller();

	/**
	 * Associa a vari�lvel de armazenamento da p�gina atual com a p�gina no
	 * par�metro.
	 * 
	 * M�todo que � chamado pelo DataScroller, ou seja, n�o existe necessidade
	 * de cham�-lo.
	 * 
	 * @param paginaAtualScroller
	 *            P�gina atual
	 */
	public void setPaginaAtualScroller(int paginaAtualScroller);
}
