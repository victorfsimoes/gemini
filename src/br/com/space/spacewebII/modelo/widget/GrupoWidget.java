package br.com.space.spacewebII.modelo.widget;

import java.io.Serializable;

public interface GrupoWidget extends Serializable {

	/**
	 * @return A descrição do grupo
	 */
	public String getDescricao();

	/**
	 * @return O codigo do grupo
	 */
	public int getCodigo();

}
