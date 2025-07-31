package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Gerencia tela de visualização.
 * 
 * @author Desenvolvimento
 *
 * @param <T>
 * 		Tipo do objeto que será visualizado
 */
public interface Visualizavel<T> {

	/**
	 * Verifica se o usuário tem permissão para visualização do dado
	 * 
	 * @return TRUE: caso o usuário tenha permissão, caso contrário: FALSE.
	 */
	public boolean verificarPermissaoVisualizar();

	/**
	 * Carregamento padrão de visualização.
	 * 
	 * @param objetoSelecionado
	 *            O objeto que será visualizado
	 * 
	 * @return A URL de destino
	 */
	public String carregarVisualizacao(T objetoSelecionado);

}
