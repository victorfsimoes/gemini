package br.com.space.spacewebII.modelo.widget;

import java.io.Serializable;

public interface SubGrupoWidget extends Serializable {

	/**
	 * @return A descrição do sub-grupo
	 */
	public String getDescricao();

	/**
	 * @return O codigo do sub-grupo
	 */
	public int getCodigo();

	/**
	 * @return O codigo do grupo
	 */
	public int getGrupoCodigo();

}
