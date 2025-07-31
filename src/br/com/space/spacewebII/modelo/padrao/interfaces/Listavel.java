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
	 * Atribui à lista de pesquisa a lista no parâmetro
	 * 
	 * @param list
	 *            Lista a ser atribuída à lista de pesquisa
	 */
	public void setListaResultados(List<T> list);

	/**
	 * 
	 * @return Uma lista contendo os objetos referidos na pesquisa
	 */
	public List<T> getListaResultados();

	/**
	 * Método de extrema importância. Seu objetivo é armazenar a posição do
	 * página Scroller
	 * 
	 * @return A página atual do scroller
	 */
	public int getPaginaAtualScroller();

	/**
	 * Associa a variálvel de armazenamento da página atual com a página no
	 * parâmetro.
	 * 
	 * Método que é chamado pelo DataScroller, ou seja, não existe necessidade
	 * de chamá-lo.
	 * 
	 * @param paginaAtualScroller
	 *            Página atual
	 */
	public void setPaginaAtualScroller(int paginaAtualScroller);
}
