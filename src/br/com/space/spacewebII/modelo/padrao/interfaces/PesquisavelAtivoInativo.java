package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Responsável pelas telas de pesquisa.
 * 
 * @author Desenvolvimento
 * 
 */
public interface PesquisavelAtivoInativo {

	/**
	 * Retorna se a pesquisa por Ativo e Inativo está habilitada.
	 * 
	 * @return TRUE: pesquisa habilitada, caso contrário: FALSE.
	 */
	public boolean getHabilitarPesquisaAtivoEInativo();

	/**
	 * @return O valor do check de ativo.
	 */
	public boolean getValorCheckAtivo();

	/**
	 * Atribui o valor do check à variável correspondente.
	 * 
	 * @param checkAtivo
	 */
	public void setValorCheckAtivo(boolean checkAtivo);

	/**
	 * @return O valor do check de inativo
	 */
	public boolean getValorCheckInativo();

	/**
	 * Atribui o valor do check à variável correspondente
	 * 
	 * @param checkInativo
	 */
	public void setValorCheckInativo(boolean checkInativo);

}
