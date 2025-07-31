package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Respons�vel pelas telas de pesquisa.
 * 
 * @author Desenvolvimento
 * 
 */
public interface PesquisavelAtivoInativo {

	/**
	 * Retorna se a pesquisa por Ativo e Inativo est� habilitada.
	 * 
	 * @return TRUE: pesquisa habilitada, caso contr�rio: FALSE.
	 */
	public boolean getHabilitarPesquisaAtivoEInativo();

	/**
	 * @return O valor do check de ativo.
	 */
	public boolean getValorCheckAtivo();

	/**
	 * Atribui o valor do check � vari�vel correspondente.
	 * 
	 * @param checkAtivo
	 */
	public void setValorCheckAtivo(boolean checkAtivo);

	/**
	 * @return O valor do check de inativo
	 */
	public boolean getValorCheckInativo();

	/**
	 * Atribui o valor do check � vari�vel correspondente
	 * 
	 * @param checkInativo
	 */
	public void setValorCheckInativo(boolean checkInativo);

}
