package br.com.space.spacewebII.controle.filtro;

import java.io.IOException;
import java.io.Serializable;
import java.lang.reflect.Field;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.Connector;
import org.apache.catalina.connector.Request;

/**
 * Servlet Filter implementation class HistoricoNavegacao
 */
@WebFilter("/*")
public class NavegacaoHttp implements Serializable, Filter {

	private static final long serialVersionUID = 1L;

	private Integer portaHttps = -1;
	private Integer portaHttp = -1;
	private Boolean flagUsaPorta = null;

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * Recupera as portas dos connectors existentes.
	 * 
	 * @param request
	 */
	private void recuperarPortas(ServletRequest request) {

		if ((portaHttp == -1 || portaHttps == -1)
				&& request instanceof org.apache.catalina.connector.RequestFacade) {
			try {
				Field field = request.getClass().getDeclaredField("request");
				field.setAccessible(true);

				Request req2 = (Request) field.get(request);

				Connector[] conns = req2.getConnector().getService()
						.findConnectors();

				for (Connector conn : conns) {
					if (conn.getScheme().equals("http") && portaHttp == -1) {
						portaHttp = conn.getPort();
					} else if (conn.getSecure()
							&& conn.getScheme().equals("https")
							&& portaHttps == -1) {
						portaHttps = conn.getPort();
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		/*
		 * ApplicationFilterConfig ap = (ApplicationFilterConfig) fConfig; try {
		 * Field fld = ap.getClass().getDeclaredField("context");
		 * fld.setAccessible(true);
		 * 
		 * Object obj = fld.get(ap); obj.toString();
		 * 
		 * } catch (Exception e) { e.printStackTrace(); }
		 */

		HttpServletRequest req = (HttpServletRequest) request;
		String urlRequisicao = req.getRequestURL().toString();

		/*
		 * Pega a pagina chamada e o context para verificar se é para ser http
		 * ou https.
		 */
		String contextPath = req.getContextPath();
		String uri = req.getRequestURI();

		/*
		 * Verifica se o primeiro acesso ao sistema foi feito atraves de uma url
		 * que espeficou a porta de acesso. Quando o acesso é feito através de
		 * um redirecionamento de porta pelo firewall pode ocorrer.
		 */
		if (flagUsaPorta == null) {
			String urlAux = "";
			if (request.isSecure())
				urlAux = "https://" + req.getServerName() + ":";
			else
				urlAux = "http://" + req.getServerName() + ":";
			flagUsaPorta = urlRequisicao.startsWith(urlAux);
		}

		if (request.isSecure() && !uri.contains("javax.faces.resource")) {
			/*
			 * o contextPath pode ser seguro ou não.
			 */
			boolean paginaSegura = uri.equals(contextPath + "/");

			if (!paginaSegura) {
				/*
				 * Lista das paginas (/pages) excluidas do http, ou seja, que
				 * permitem https.
				 */
				String[] exclusoes = { contextPath + "/pages/login.xhtml",
						contextPath + "/login", contextPath + "/login/",
						contextPath + "/pages/erroAutorizavel.xhtml",
						contextPath + "/autorizacao",
						contextPath + "/autorizacao/",
						contextPath + "/pagamento",
						contextPath + "/pagamento/",
						contextPath + "/pages/checkout",
						contextPath + "/pages/checkout/" };

				for (String string : exclusoes) {
					paginaSegura = uri.startsWith(string);
					if (paginaSegura)
						break;
				}
				recuperarPortas(request);
			}

			if (!paginaSegura && portaHttp != -1 && portaHttps != -1) {
				String redirectTarget = urlRequisicao;
				redirectTarget = redirectTarget.replaceFirst("https:", "http:");
				redirectTarget = redirectTarget.replaceFirst(":" + portaHttps,
						(flagUsaPorta ? ":" + portaHttp : ""));

				/*
				 * Verifica se a porta http é diferente da padrão 80. se sim a
				 * mesma deve existir na requisição. Se a porta https for a
				 * padrão 443 não vai existir a porta na url, sendo assim deve
				 * acrescentar novamente.
				 */
				if (portaHttp != 80 && flagUsaPorta) {
					String t = "http://" + req.getServerName() + ":"
							+ portaHttp;
					if (!redirectTarget.contains(t)) {
						redirectTarget = redirectTarget.replaceFirst("http://"
								+ req.getServerName(), t);
					}
				}

				((HttpServletResponse) response).sendRedirect(redirectTarget);
			} else {
				chain.doFilter(request, response);
			}
		} else
			chain.doFilter(request, response);
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// FilterConfig fc = fConfig;
	}
}
