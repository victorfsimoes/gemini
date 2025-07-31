package br.com.space.spacewebII.controle;

import java.io.IOException;
import java.io.Serializable;
import java.util.Iterator;

import javax.faces.FacesException;
import javax.faces.FactoryFinder;
import javax.faces.application.ViewExpiredException;
import javax.faces.context.ExceptionHandler;
import javax.faces.context.ExceptionHandlerWrapper;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ExceptionQueuedEvent;
import javax.faces.event.ExceptionQueuedEventContext;
import javax.faces.render.RenderKit;
import javax.faces.render.RenderKitFactory;
import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.jboss.seam.security.AuthorizationException;
import org.primefaces.context.RequestContext;
import org.primefaces.util.Constants;

import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.api.negocio.modelo.excecao.autorizacao.IExcecaoAutorizavel;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.modelo.dominio.sistema.Usuario;
import br.com.space.spacewebII.modelo.excecao.ErroGenericoExcecao;
import br.com.space.spacewebII.modelo.excecao.permissao.PermissaoExcecao;
import br.com.space.spacewebII.modelo.padrao.interfaces.AtributoSessao;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;

public class AppExceptionHandler extends ExceptionHandlerWrapper implements
		Serializable {

	private static final long serialVersionUID = 1L;

	private ExceptionHandler wrapped = null;

	private String paginaRedirecionamentoErro;
	private String comandoAjax;

	/**
	 * 
	 * @param propriedade
	 */
	public AppExceptionHandler(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

	/**
	 * 
	 * @param exception
	 * @return
	 */
	private Throwable getCause(Throwable exception) {
		Throwable cause = exception;
		if (exception != null && exception.getCause() != null) {
			cause = this.getCause(exception.getCause());
		}

		return cause;
	}

	private Throwable getFirstCause(Throwable exception) {
		Throwable cause = exception;
		if (exception != null && exception.getCause() != null) {
			cause = exception.getCause();
		}

		return cause;
	}

	public static SpaceExcecao getPrimeiraSpaceExcecao(Throwable exception) {

		if (exception instanceof SpaceExcecao) {
			return (SpaceExcecao) exception;
		} else if (exception != null && exception.getCause() != null) {
			return getPrimeiraSpaceExcecao(exception.getCause());
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean tratarExcecao() {

		boolean temExcecao = false;

		this.paginaRedirecionamentoErro = null;
		this.comandoAjax = null;

		for (Iterator<ExceptionQueuedEvent> i = getUnhandledExceptionQueuedEvents()
				.iterator(); i.hasNext();) {

			ExceptionQueuedEvent event = i.next();

			ExceptionQueuedEventContext context = (ExceptionQueuedEventContext) event
					.getSource();

			Throwable excecao = getFirstCause(context.getException());
			if (excecao == null) {
				continue;
			}

			Throwable causa = getCause(excecao);

			SpaceExcecao spaceExcecaoCausa = getPrimeiraSpaceExcecao(excecao);

			temExcecao = true;

			FacesContext fc = FacesContext.getCurrentInstance();
			ExternalContext ec = fc.getExternalContext();
			HttpSession session = (HttpSession) fc.getExternalContext()
					.getSession(false);
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
			// RequestContext rc = RequestContext.getCurrentInstance();

			UsuarioWeb usuarioLogado = null;
			if (session != null) {
				Object usr = session
						.getAttribute(AtributoSessao.USUARIO_LOGADO);
				if (usr != null)
					usuarioLogado = (UsuarioWeb) usr;
			}

			/*
			 * Pega a url que originou o erro e retira o contextPath.
			 */
			String contextPath = ((ServletContext) ec.getContext())
					.getContextPath();
			String urlOrigemErro = request.getRequestURI().toString();

			String comandoVoltarErro = null;

			/*
			 * Verifica o tipo do erro e se vai redirecionar/exibir a mensagem
			 * para o usuário.
			 */
			StringBuilder pagRedirecionamentoErro = new StringBuilder();

			if (excecao instanceof PermissaoExcecao || causa != null
					&& causa instanceof PermissaoExcecao) {

				pagRedirecionamentoErro.append(contextPath).append(
						"/pages/erroPermissao.xhtml");

			} else if (excecao instanceof ErroGenericoExcecao) {

				pagRedirecionamentoErro.append(contextPath).append(
						"/pages/erro.xhtml");

				session.setAttribute(
						AtributoSessao.MENSAGEM_EXCECAO_ERROGENERICO,
						excecao.getMessage());

			} else if (excecao instanceof AuthorizationException
					|| causa != null && causa instanceof AuthorizationException) {

				pagRedirecionamentoErro.append(contextPath).append("/login");

			} else if (usuarioLogado instanceof Usuario
					&& (excecao instanceof IExcecaoAutorizavel || (causa != null && causa instanceof IExcecaoAutorizavel))) {
				/*
				 * Testa se é uma requisição ajax ou não. Se for ajax exibe o
				 * popup senão redireciona. if (rc.isAjaxRequest()) {
				 * this.comandoAjax = "pAutorizacao.show();"; comandoVoltarErro
				 * = "pAutorizacao.hide();"; } else {
				 */

				pagRedirecionamentoErro.append(contextPath).append(
						"/autorizacao");

				if (!urlOrigemErro.equals(paginaRedirecionamentoErro)) {
					comandoVoltarErro = "redirecionar('" + urlOrigemErro
							+ "');";
				}

			} else if ((excecao instanceof IExcecaoAutorizavel)
					|| (causa != null && causa instanceof IExcecaoAutorizavel)) {

				IExcecaoAutorizavel autorizavel = null;

				if (excecao instanceof IExcecaoAutorizavel) {
					autorizavel = (IExcecaoAutorizavel) excecao;

				} else {
					autorizavel = (IExcecaoAutorizavel) causa;
				}

				if (autorizavel != null) {

					String msg = autorizavel.getMensagemAmigavel();

					MensagemSistema.exibir(TipoMensagem.Erro, "", msg);
				}

			} else if (spaceExcecaoCausa instanceof SpaceExcecao) {
				MensagemSistema.exibir(TipoMensagem.Erro, "",
						spaceExcecaoCausa.getMessage());
			} else if (excecao instanceof ViewExpiredException) {

				pagRedirecionamentoErro.append(contextPath).append("/produtos");
			}

			excecao.printStackTrace();
			fc.validationFailed();

			/*
			 * Registra na sessao a pagina que originou o erro. Se a pagina que
			 * originou o erro é diferente da propria pagina de erro.
			 */
			paginaRedirecionamentoErro = pagRedirecionamentoErro.toString();
			if (!urlOrigemErro.equals(paginaRedirecionamentoErro)
					&& session != null) {
				session.setAttribute(AtributoSessao.URL_ORIGEM_ERRO,
						urlOrigemErro);
			}
			if (comandoVoltarErro != null && session != null)
				session.setAttribute(AtributoSessao.COMANDO_VOLTAR_ERRO,
						comandoVoltarErro);

			try {
				i.remove();
			} catch (Exception e) {

			}
		}
		return temExcecao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.context.ExceptionHandlerWrapper#handle()
	 */
	@Override
	public void handle() throws FacesException {

		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();

		if (!Licenca.isLicencaValida()) {
			String contextPath = ((ServletContext) ec.getContext())
					.getContextPath();
			HttpServletRequest request = (HttpServletRequest) ec.getRequest();
			String urlOrigemErro = request.getRequestURI().toString();

			paginaRedirecionamentoErro = contextPath
					+ "/pages/indisponivel.xhtml";

			if (!urlOrigemErro.equals(paginaRedirecionamentoErro)) {
				try {
					ec.redirect(this.paginaRedirecionamentoErro);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		} else {

			boolean temExcecao = tratarExcecao();

			if (temExcecao) {
				try {

					/*
					 * Verifica se é para executar algum comando ajax.
					 */
					if (this.comandoAjax != null
							&& this.comandoAjax.length() > 0) {
						RequestContext rc = RequestContext.getCurrentInstance();
						rc.execute(this.comandoAjax);
					}

					/*
					 * Verifica se é para redirecionar para alguma página.
					 */
					if (this.paginaRedirecionamentoErro != null
							&& this.paginaRedirecionamentoErro.length() > 0) {
						try {
							ec.redirect(this.paginaRedirecionamentoErro);
						} catch (Exception e2) {
							e2.printStackTrace();
						}
					}
				} finally {
				}
			}
		}

		getWrapped().handle();
	}

	/**
	 * Método copiado da web de uma solução para corrigir o problema do
	 * renderKit.
	 * 
	 * @param fc
	 * @param redirectPage
	 * @throws FacesException
	 */
	public void doRedirect(FacesContext fc, String redirectPage)
			throws FacesException {
		ExternalContext ec = fc.getExternalContext();

		try {
			if (ec.getRequestParameterMap().containsKey(
					Constants.PARTIAL_PROCESS_PARAM)
					&& !ec.getRequestParameterMap()
							.get(Constants.PARTIAL_PROCESS_PARAM)
							.equals("@all")) {
			}

			// fix for renderer kit (Mojarra's and PrimeFaces's ajax redirect)
			if ((RequestContext.getCurrentInstance().isAjaxRequest() || fc
					.getPartialViewContext().isPartialRequest())
					&& fc.getResponseWriter() == null
					&& fc.getRenderKit() == null) {
				ServletResponse response = (ServletResponse) ec.getResponse();
				ServletRequest request = (ServletRequest) ec.getRequest();
				response.setCharacterEncoding(request.getCharacterEncoding());

				RenderKitFactory factory = (RenderKitFactory) FactoryFinder
						.getFactory(FactoryFinder.RENDER_KIT_FACTORY);
				RenderKit renderKit = factory.getRenderKit(fc, fc
						.getApplication().getViewHandler()
						.calculateRenderKitId(fc));
				ResponseWriter responseWriter = renderKit.createResponseWriter(
						response.getWriter(), null,
						request.getCharacterEncoding());
				fc.setResponseWriter(responseWriter);
			}
			ec.redirect(ec.getRequestContextPath()
					+ (redirectPage != null ? redirectPage : ""));
		} catch (IOException e) {
			System.out.println(
					"Redirect to the specified page '"+redirectPage+"' failed");
			throw new FacesException(e);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.context.ExceptionHandlerWrapper#getWrapped()
	 */
	@Override
	public ExceptionHandler getWrapped() {
		return wrapped;
	}

	/**
	 * 
	 * @param wrapped
	 */
	public void setWrapped(ExceptionHandler wrapped) {
		this.wrapped = wrapped;
	}

}
