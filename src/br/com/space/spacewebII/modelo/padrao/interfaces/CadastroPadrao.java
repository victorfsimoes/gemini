package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Padronização dos cadastros.
 * 
 * @author Desenvolvimento
 * 
 * @param <T>
 *            Qual o objeto que será cadastrado
 */
public interface CadastroPadrao<T> extends IGerenteMB,
		Editavel<T>, Apagavel<T>, Incluivel<T>, Visualizavel<T>,
		AcaoCadastroPadrao {

	/**
	 * Retorna a mensagem identificando a operação que está sendo executada.
	 * 
	 * @return
	 */
	public String getMensagemOperacao();

	/**
	 * @return TRUE Caso a operação atual seja de exclusão, caso contrário, FALSE.
	 */
	public boolean getFlagExcluindo();

	/**
	 * @return TRUE Caso a operação atual seja inclusão, caso contrário, FALSE.
	 */
	public boolean getFlagNovo();

	/**
	 * @return TRUE Caso a operação atual seja de edição, caso contrário, FALSE.
	 */
	public boolean getFlagEdicao();

	/**
	 * @return TRUE Caso a opereção atual seja visualização, caso contrário,
	 *         FALSE.
	 */
	public boolean getFlagVisualizacao();
}
