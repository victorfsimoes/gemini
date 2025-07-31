package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Respons�vel pelas telas de pesquisa.
 * 
 * @author Desenvolvimento
 * 
 */
public interface Pesquisavel {

	/**
	 * M�todo respons�vel por executar a pesquisa.
	 * 
	 */
	public void executarPesquisa();

	/**
	 * Limpa os filtros de pesquisa.
	 */
	public void limparFiltros();
}
