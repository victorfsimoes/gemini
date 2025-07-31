package br.com.space.spacewebII.controle.financeiro;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.space.api.cartao.modelo.AdministradoraCartao;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.modelo.aplicacao.Configuracao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;

/**
 * Servlet implementation class NotificacaoTransacaoPagSeguro
 */
@WebServlet("/notificacaoTransacaoPagSeguro")
public class NotificacaoTransacaoPagSeguro extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String TIPO_NOTIFICACAO_TRANSACAO = "transaction";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public NotificacaoTransacaoPagSeguro() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		String notificationCode = request.getParameter("notificationCode");
		String notificationType = request.getParameter("notificationType");

		if (TIPO_NOTIFICACAO_TRANSACAO.equals(notificationType)
				&& StringUtil.isValida(notificationCode)) {

			GenericoDAO dao = new GenericoDAO();

			Parametros parametros = new Parametros(dao, Configuracao
					.getAtributosConfiguracao().getFilialPadraoCodigo());

			try {
				GerentePagamentoCartao.consultarEAtualizarTransacaoNotificada(
						new GenericoDAOLog(), dao, notificationCode,
						AdministradoraCartao.Pagseguro, parametros);

				response.addHeader("Access-Control-Allow-Origin",
						"https://sandbox.pagseguro.uol.com.br");
			} catch (Exception e) {
				e.printStackTrace();
			}
		}else{
			response.getWriter().print("Requisição invalida");
		}
	}
}
