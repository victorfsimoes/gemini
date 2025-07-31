package br.com.space.spacewebII.modelo.widget;

import java.io.Serializable;

public interface PrecoVisualizavel extends Serializable {

	/**
	 * @return O preço que produto sera vendido
	 */
	public String getPrecoVendaVisualizacao();

	/**
	 * @return O peço de tabela do produto
	 */
	public String getPrecoTabelaVisualizacao();

	/**
	 * @return O valor unitario do produto
	 */
	public String getPrecoUnitarioVisualizacao();

}
