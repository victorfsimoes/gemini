package br.com.space.spacewebII.modelo.propriedade;

import java.io.Serializable;
import java.util.Locale;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import br.com.space.spacewebII.controle.aplicacao.HttpUtilMB;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.padrao.interfaces.AtributoSessao;
import br.com.space.spacewebII.modelo.padrao.interfaces.GeradorMensagem;
import br.com.space.spacewebII.modelo.util.HttpUtil;

@ManagedBeanSessionScoped
public class Propriedade extends br.com.space.api.core.propriedade.Propriedade implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	HttpServletRequest request = null;

	@Inject
	HttpUtilMB httpUtilMB;

	/**
	 * 
	 */
	public Propriedade() {
	}

	@PostConstruct
	public void postConstruct() {
		httpUtilMB.setAtribute(AtributoSessao.PROPRIEDADE, this);
	}

	/**
	 * Retorna a mensagem que corresponde o Key. </br>
	 * Usando a propriedade que esta na sessao {@link #getInstanciaSessao()} com
	 * o metodo {@link #getMensagem(String)}. </br>
	 * 
	 * 
	 * @param key
	 * @return A mensagem correspondete a key. Caso a proriedade esteja null
	 *         retorna null
	 */
	public static String getMensagemUtilizadoSessao(String key) {
		Propriedade propriedade = Propriedade.getInstanciaSessao();

		if (propriedade != null) {
			return propriedade.getMensagem(key);
		}

		return null;

	}

	private static Propriedade instanciaAplicacao;

	/**
	 * 
	 * @return
	 */
	public static Propriedade getInstanciaSessao() {
		Object object = HttpUtil.getAtribute(HttpUtil.getHttpSessionFacesContext(), AtributoSessao.PROPRIEDADE);

		if (object != null && object instanceof Propriedade) {
			return (Propriedade) object;
		}

		if (instanciaAplicacao == null)
			instanciaAplicacao = new Propriedade();

		return instanciaAplicacao;
	}

	public Locale getClientLocale() {

		try {
			if (request == null) {
				request = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
			}
		} catch (Throwable e) {
		}

		if (request == null) {
			return Locale.getDefault();
		}

		return request.getLocale();
	}

	/**
	 * 
	 */
	@Override
	public String getMensagem(String nome) {

		String retorno = "";
		int contador = 0;

		/*
		 * tenta localizar a mensagem 3 vezes.
		 * 
		 * Na primeira, tenta localizar no local do cliente.
		 * 
		 * Na segunda, tenta localizar no local vazio (sem localizadade).
		 * 
		 * Na terceira, tenta localizar no local padrão do java.
		 */

		Locale loc = getClientLocale();
		do {
			try {
				if (contador == 1)
					loc = Locale.ROOT;
				else if (contador == 2)
					loc = Locale.getDefault();

				retorno = getPalavras(loc).getString(nome);
			} catch (MissingResourceException e) {
			} catch (Exception e) {
			}
			contador++;

		} while (retorno.trim().length() == 0 && contador < 3);

		if (retorno.trim().length() == 0)
			retorno = "#" + loc.toString();

		return retorno;
	}

	/**
	 * 
	 * @return
	 */
	public ResourceBundle getPalavras() {
		return getPalavras(getClientLocale());
	}

	/**
	 * 
	 * @param locale
	 * @return
	 */
	public ResourceBundle getPalavras(Locale locale) {

		ResourceBundle palavras = null;

		try {

			if (locale == null) {
				locale = Locale.getDefault();
			}

			palavras = ResourceBundle.getBundle("messages", locale);

		} catch (Exception e) {
		}

		return palavras;
	}

	/**
	 * 
	 * @param gerador
	 * @return
	 */
	public String gerarMensagem(GeradorMensagem gerador) {
		return gerador.gerarMensagem(this);
	}
}
