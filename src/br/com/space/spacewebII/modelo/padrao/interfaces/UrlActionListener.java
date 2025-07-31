package br.com.space.spacewebII.modelo.padrao.interfaces;

import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;

public interface UrlActionListener {

	/**
	 * Método chamado durante a abertura da tela atraves a URL personilisadoa
	 * pelo pretty faces, ou seja quando uma URL cuja a anotação
	 * {@link UrlMapping#getPattern()} é inserida no navegador
	 * 
	 * 
	 */
	public void urlAction();

}
