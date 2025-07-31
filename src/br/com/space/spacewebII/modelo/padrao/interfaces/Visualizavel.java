package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Gerencia tela de visualiza��o.
 * 
 * @author Desenvolvimento
 *
 * @param <T>
 * 		Tipo do objeto que ser� visualizado
 */
public interface Visualizavel<T> {

	/**
	 * Verifica se o usu�rio tem permiss�o para visualiza��o do dado
	 * 
	 * @return TRUE: caso o usu�rio tenha permiss�o, caso contr�rio: FALSE.
	 */
	public boolean verificarPermissaoVisualizar();

	/**
	 * Carregamento padr�o de visualiza��o.
	 * 
	 * @param objetoSelecionado
	 *            O objeto que ser� visualizado
	 * 
	 * @return A URL de destino
	 */
	public String carregarVisualizacao(T objetoSelecionado);

}
