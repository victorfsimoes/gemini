package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Padroniza��o dos cadastros.
 * 
 * @author Desenvolvimento
 * 
 * @param <T>
 *            Qual o objeto que ser� cadastrado
 */
public interface CadastroPadrao<T> extends IGerenteMB,
		Editavel<T>, Apagavel<T>, Incluivel<T>, Visualizavel<T>,
		AcaoCadastroPadrao {

	/**
	 * Retorna a mensagem identificando a opera��o que est� sendo executada.
	 * 
	 * @return
	 */
	public String getMensagemOperacao();

	/**
	 * @return TRUE Caso a opera��o atual seja de exclus�o, caso contr�rio, FALSE.
	 */
	public boolean getFlagExcluindo();

	/**
	 * @return TRUE Caso a opera��o atual seja inclus�o, caso contr�rio, FALSE.
	 */
	public boolean getFlagNovo();

	/**
	 * @return TRUE Caso a opera��o atual seja de edi��o, caso contr�rio, FALSE.
	 */
	public boolean getFlagEdicao();

	/**
	 * @return TRUE Caso a opere��o atual seja visualiza��o, caso contr�rio,
	 *         FALSE.
	 */
	public boolean getFlagVisualizacao();
}
