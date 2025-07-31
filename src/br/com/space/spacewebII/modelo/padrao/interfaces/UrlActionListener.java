package br.com.space.spacewebII.modelo.padrao.interfaces;

import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;

public interface UrlActionListener {

	/**
	 * M�todo chamado durante a abertura da tela atraves a URL personilisadoa
	 * pelo pretty faces, ou seja quando uma URL cuja a anota��o
	 * {@link UrlMapping#getPattern()} � inserida no navegador
	 * 
	 * 
	 */
	public void urlAction();

}
