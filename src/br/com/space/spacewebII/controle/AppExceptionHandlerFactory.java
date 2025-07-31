package br.com.space.spacewebII.controle;

import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerFactory;

public class AppExceptionHandlerFactory extends ExceptionHandlerFactory {

	private ExceptionHandlerFactory parent;

	/*
	 * Manter apenas uma instancia, pois a fabrica � chamada a todo momento e
	 * n�o somente quando ocorre uma exce��o.
	 */
	private AppExceptionHandler appExceptionHandler;

	/**
	 * 
	 * @param parent
	 */
	public AppExceptionHandlerFactory(ExceptionHandlerFactory parent) {
		this.parent = parent;

		this.appExceptionHandler = new AppExceptionHandler(null);
	}

	/**
	 * 
	 */
	@Override
	public ExceptionHandler getExceptionHandler() {

		this.appExceptionHandler.setWrapped(parent.getExceptionHandler());

		return this.appExceptionHandler;
	}
}
