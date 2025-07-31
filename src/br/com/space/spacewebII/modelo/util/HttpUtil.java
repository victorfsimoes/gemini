package br.com.space.spacewebII.modelo.util;

import java.io.Serializable;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

public class HttpUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private HttpSession session = null;

	/**
	 * 
	 */
	public HttpUtil() {
	}

	/**
	 * 
	 * @param session
	 */
	public HttpUtil(HttpSession session) {
		this.session = session;
	}

	/**
	 * 
	 * @return
	 */
	public HttpServletRequest getHttpServletRequest() {

		ExternalContext ec = getExternalContext();

		if (ec != null) {
			return (HttpServletRequest) ec.getRequest();
		}

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public HttpSession getHttpSession() {

		if (session != null) {
			return session;
		}

		return getHttpSessionFacesContext();
	}

	/**
	 * 
	 * @param httpSession
	 * @param key
	 * @param atributo
	 */
	public void setAtribute(HttpSession httpSession, String key, Serializable atributo) {

		if (httpSession != null && key != null && atributo != null) {

			httpSession.setAttribute(key, atributo);
		}
	}

	/**
	 * 
	 * @param key
	 * @param atributo
	 */
	public void setAtribute(String key, Serializable atributo) {
		this.setAtribute(getHttpSession(), key, atributo);
	}

	/**
	 * 
	 * @param key
	 * @return
	 */
	public Object getAtribute(String key) {
		return HttpUtil.getAtribute(getHttpSession(), key);
	}

	/**
	 * 
	 * @return
	 */
	public String getIDSessao() {
		return getIDSessao(getHttpSession());
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	public String getIDSessao(HttpSession session) {

		if (session != null) {
			return session.getId();
		}
		return null;
	}

	/**
	 * 
	 * @param session
	 * @return
	 */
	public String getTodasSessoesAtivas(HttpSession session) {
		session.getValueNames();

		return null;
	}

	/**
	 * 
	 * @param httpSession
	 * @param key
	 * @return
	 */
	public static Object getAtribute(HttpSession httpSession, String key) {

		if (httpSession != null && key != null) {
			return httpSession.getAttribute(key);
		}

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static ExternalContext getExternalContext() {
		FacesContext fc = FacesContext.getCurrentInstance();

		if (fc != null) {
			return fc.getExternalContext();
		}

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static HttpSession getHttpSessionFacesContext() {

		ExternalContext ec = getExternalContext();

		if (ec != null) {
			return (HttpSession) ec.getSession(false);
		}

		return null;
	}

	private static String serverInfo = null;
	private static String serverName = null;
	private static String serverVersion = null;

	public static String getServerInfo() {
		if (serverInfo == null) {
			ExternalContext ec = getExternalContext();
			if (ec != null && ec.getContext() != null) {
				ServletContext s = (ServletContext) getExternalContext()
						.getContext();
				serverInfo = s.getServerInfo();

				/*
				 * The form of the returned string is servername/versionnumber.
				 * For example, the JavaServer Web Development Kit may return
				 * the string JavaServer Web Dev Kit/1.0.
				 * 
				 * The servlet container may return other optional information
				 * after the primary string in parentheses, for example,
				 * JavaServer Web Dev Kit/1.0 (JDK 1.1.6; Windows NT 4.0 x86).
				 */
				String[] infors = serverInfo.split("/");
				if (infors != null && infors.length > 1) {
					serverName = infors[0];
					serverVersion = infors[1];
				}
			}
		}
		return serverInfo;
	}

	public static String getServerName() {
		getServerInfo();
		return serverName;
	}

	public static String getServerVersion() {
		getServerInfo();
		return serverVersion;
	}

	/*
	 * Método deve ser finalizado de forma que retorne o tipo do servidor java,
	 * ou seja, Tomcat, Glassfish, JBoss, etc.
	 */
	public static void getTipoServletContainer() {
		ServletContext s = (ServletContext) getExternalContext().getContext();
		RequestDispatcher rd = s.getNamedDispatcher("default");
		if (rd != null) {
			/*
			 * Verifica se é um servidor apache.
			 */
			rd.getClass().getName().contains("apache");
		}
	}
}
