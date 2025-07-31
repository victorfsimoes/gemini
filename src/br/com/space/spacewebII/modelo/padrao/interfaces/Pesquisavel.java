package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Responsável pelas telas de pesquisa.
 * 
 * @author Desenvolvimento
 * 
 */
public interface Pesquisavel {

	/**
	 * Método responsável por executar a pesquisa.
	 * 
	 */
	public void executarPesquisa();

	/**
	 * Limpa os filtros de pesquisa.
	 */
	public void limparFiltros();
}
