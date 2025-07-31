package br.com.space.spacewebII.controle.filtro;

import java.io.IOException;
import java.io.Serializable;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * Servlet Filter implementation class HistoricoNavegacao
 */
//@WebFilter("/pages/*")
public class HistoricoNavegacao implements Serializable, Filter {

	private static final long serialVersionUID = 1L;

	// @Inject
	// private SessaoMB sessaoMB;

	// @Inject
	// private Configuracao configuracao;

	/**
	 * Default constructor.
	 */
	public HistoricoNavegacao() {
		super();
	}

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		String urlRequisicao = req.getRequestURL().toString();
		// sessaoMB.adicionarHistoricoNavegacao(urlRequisicao);
		chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {

	}

}
