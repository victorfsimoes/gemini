package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * 
 * @author Desenvolvimento
 * 
 */
public interface ColunaEstiloGrid {

	/**
	 * @return Numero de colunas do grid para ordenação dos elementos na tela
	 */
	public int getNumeroColunasGrid();

	/**
	 * @return As classes de stilo que serao usadas no grid. As classes devem
	 *         ser separadas com virgula (,)
	 * 
	 */
	public String getClassesDeEstiloColunas();

}
