package br.com.space.spacewebII.controle;

import java.io.Serializable;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.faces.context.FacesContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;
import javax.swing.Timer;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.space.api.negocio.versao.modelo.GerenteVersao;
import br.com.space.spacewebII.controle.aplicacao.VersaoMB;
import br.com.space.spacewebII.controle.financeiro.GerenteAtualizacaoPagamentoCartao;
import br.com.space.spacewebII.controle.produto.mbean.GerenteAvisoEstoque;
import br.com.space.spacewebII.modelo.aplicacao.Configuracao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOCep;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.dominio.sistema.Sessao;
import br.com.space.spacewebII.modelo.padrao.interfaces.AtributoSessao;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.HttpUtil;

public class ListenerSessao implements Serializable, HttpSessionListener, AtributoSessao, ServletContextListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ScheduledExecutorService schedulerAvisoEstoque;
	private ScheduledExecutorService schedulerCartao;
	private ScheduledExecutorService schedulerSessao;

	public ListenerSessao() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionCreated(javax.servlet.http
	 * .HttpSessionEvent)
	 */
	@Override
	public void sessionCreated(HttpSessionEvent arg0) {
		/*
		 * try {
		 * 
		 * long memoriaLivreEmKB = Runtime.getRuntime().freeMemory() / 1024; if
		 * (memoriaLivreEmKB < 200) { Runtime.getRuntime().gc(); } } catch (Exception e)
		 * { }
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.http.HttpSessionListener#sessionDestroyed(javax.servlet
	 * .http.HttpSessionEvent)
	 */
	@Override
	public void sessionDestroyed(HttpSessionEvent arg0) {

		HttpSession httpSession = arg0.getSession();

		HttpUtil httpSessionUtil = new HttpUtil(httpSession);

		try {
			Sessao sessao = (Sessao) httpSessionUtil.getAtribute(SESSAO_GUARDIAN);

			String obsComplementar = getObsComplementar(httpSession);

			if (sessao != null) {
				GerenteSessao.fecharSessao(GenericoDAO.getDao(httpSession), GenericoDAOLog.getDao(httpSession), sessao,
						true, obsComplementar);
			}

			try {
				// Runtime.getRuntime().gc();
			} catch (Exception e) {
				e.printStackTrace();
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {

			GenericoDAO.fechar(httpSession);
			GenericoDAOLog.fechar(httpSession);
			GenericoDAOCep.fechar(httpSession);
		}
	}

	private String getObsComplementar(HttpSession httpSession) {
		try {

			Object objPropriedade = httpSession.getAttribute(AtributoSessao.PROPRIEDADE);

			return ((Propriedade) objPropriedade).getMensagem("texto.logpedido.complemento.encerramentosessao");

		} catch (Throwable e) {
			return "Log Gerado pelo encerramento da sessão.";
		}
	}

	/**
	 * 
	 * @return
	 * 
	 * 		public GenericoDAO getDao() { return
	 *         GenericoDAO.getDao(FacesContext.getCurrentInstance()); }
	 */

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextDestroyed(javax.servlet.
	 * ServletContextEvent)
	 */
	@Override
	public void contextDestroyed(ServletContextEvent arg0) {

		if (schedulerAvisoEstoque != null) {
			schedulerAvisoEstoque.shutdownNow();

			if (!schedulerAvisoEstoque.isTerminated()) {
				schedulerAvisoEstoque.shutdown();
			}
		}

		if (schedulerCartao != null) {

			schedulerCartao.shutdownNow();

			if (!schedulerCartao.isTerminated()) {
				schedulerCartao.shutdownNow();
			}
		}

		if (schedulerSessao != null) {

			schedulerSessao.shutdownNow();

			if (!schedulerSessao.isTerminated()) {
				schedulerSessao.shutdownNow();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.servlet.ServletContextListener#contextInitialized(javax.servlet
	 * .ServletContextEvent)
	 */
	@Override
	public void contextInitialized(ServletContextEvent arg0) {
		try {
			Licenca.carregarLicenca();
		} catch (Exception e) {
			e.printStackTrace();
		}

		iniciarServicoAtualizarVersao();

		schedulerSessao = Executors.newSingleThreadScheduledExecutor();

		schedulerSessao.scheduleAtFixedRate(new MonitorSessao(), 1, 10, TimeUnit.HOURS);

		criarSchedulerAvisoEstoque();

		criarSchedulerCartao();
	}

	private void iniciarServicoAtualizarVersao() {

		ScheduledExecutorService schedulerVersao = Executors.newSingleThreadScheduledExecutor();

		schedulerVersao.schedule(new Runnable() {

			@Override
			public void run() {
				atualizarVersao();
			}

		}, 1, TimeUnit.MINUTES);
	}

	private void atualizarVersao() {

		GenericoDAO dao = new GenericoDAO();

		try {

			dao.beginTransaction();

			GerenteVersao.manipularVersaoGemini(VersaoMB.versaoSistema,
					Configuracao.getAtributosConfiguracao().getFilialPadraoCodigo(), dao);

			dao.commitTransaction();

		} catch (Throwable e) {
			dao.rollBackTransaction();
			e.printStackTrace();
		} finally {
			try {
				dao.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void criarSchedulerCartao() {

		if (Configuracao.getAtributosConfiguracao().isRealizaVerificacaoPagamentoCartao()) {

			schedulerCartao = Executors.newSingleThreadScheduledExecutor();
			schedulerCartao.scheduleAtFixedRate(new TimerTask() {

				@Override
				public void run() {
					GerenteAtualizacaoPagamentoCartao.atualizarPedidos();
				}
			}, 2, Configuracao.getAtributosConfiguracao().getIntervaloVerificacaoTransacaoCartaoMinutos(),
					TimeUnit.MINUTES);
		}
	}

	private void criarSchedulerAvisoEstoque() {

		if (Configuracao.getAtributosConfiguracao().isRealizaAvisoEstoque()) {

			schedulerAvisoEstoque = Executors.newSingleThreadScheduledExecutor();
			schedulerAvisoEstoque.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					GerenteAvisoEstoque.verificarSolicitacoesAvisos();
				}

			}, 10, Configuracao.getAtributosConfiguracao().getIntervaloVerificacaoAvisoEstoqueMinutos(),
					TimeUnit.MINUTES);
		}
	}
}
