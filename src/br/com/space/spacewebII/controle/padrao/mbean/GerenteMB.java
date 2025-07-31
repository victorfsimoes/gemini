package br.com.space.spacewebII.controle.padrao.mbean;

import java.io.IOException;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;

import org.jboss.seam.security.AuthorizationException;

import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.controle.aplicacao.ParametroWebMB;
import br.com.space.spacewebII.controle.aplicacao.SessaoMB;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.controle.permissao.GerentePermissao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOCep;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.excecao.ErroGenericoExcecao;
import br.com.space.spacewebII.modelo.excecao.permissao.PermissaoExcecao;
import br.com.space.spacewebII.modelo.padrao.interfaces.IGerenteMB;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.Arquivo;
import br.com.space.spacewebII.modelo.util.FormatacaoMB;

import com.ocpsoft.pretty.faces.config.mapping.UrlMapping;

/**
 * Classe que padroniza as classes bean de Gerente e tem alguns métodos em comum
 * com elas
 * 
 * @author Desenvovimento
 * 
 */
public abstract class GerenteMB implements IGerenteMB {

	private static final long serialVersionUID = 1L;

	@Inject
	protected Propriedade propriedade;

	@Inject
	protected GenericoDAO dao;

	@Inject
	protected GenericoDAOCep daoCep;

	@Inject
	protected GenericoDAOLog daoLog;

	@Inject
	protected GerenteLogin gerenteLogin;

	@Inject
	protected GerentePermissao gerentePermissao = null;

	@Inject
	protected SessaoMB sessaoMB;

	@Inject
	protected FormatacaoMB formatacaoMB;

	@Inject
	protected ParametroWebMB parametroWebMB;

	@Inject
	protected Arquivo arquivo;

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.spacewebII.modelo.padrao.interfaces.IGerenteMB#
	 * verificarPermissao()
	 */
	@Override
	public void verificarPermissao() {

		if (getUsuario() == null
				&& (necessarioLogin() || !permiteAcessoVisitante())) {

			throw new AuthorizationException(
					propriedade.getMensagem("alerta.loginNecessario"));

		} else if (!temPermissao()) {

			throw new PermissaoExcecao(
					propriedade.getMensagem("mensagem.erropermissao"));
		}

	}

	/**
	 * Verifica se o usuario logado tem permissao para acessar este recurso
	 * 
	 * @return TRUE Caso o usuario tenha permissão, caso contrario FALSE
	 */
	public boolean temPermissao() {

		boolean temPermissao = !necessarioLogin()
				|| gerentePermissao.verificarPermissao(getNomePrograma())
				|| (permiteAcessoDeCliente() && gerenteLogin.isPerfilCliente())
				|| getNomePrograma() == null;

		return temPermissao;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.spacewebII.modelo.padrao.interfaces.CarregarListener#aoCarregar
	 * ()
	 */
	@Override
	public void aoCarregar() {
		verificarPermissao();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.IGerenteMB#carregarObjetos
	 * ()
	 */
	@Override
	public void carregarObjetos() {

		verificarPermissao();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.IGerenteMB#carregarView()
	 */
	@Override
	public String carregarView() {
		return sessaoMB.redirecionar(getUrlView());
	}

	public void redirecionar(String url) throws IOException {
		FacesContext fc = FacesContext.getCurrentInstance();
		ExternalContext ec = fc.getExternalContext();
		ec.redirect(url);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.IGerenteMB#getUrlViewVoltar
	 * ()
	 */
	@Override
	public String getUrlViewVoltar() {
		return sessaoMB.getUrlAnterior();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.IGerenteMB#getPropriedade()
	 */
	@Override
	public Propriedade getPropriedade() {
		return propriedade;
	}

	/**
	 * 
	 * @return Usuário atual da sessão
	 */
	public UsuarioWeb getUsuario() {
		return gerenteLogin.getUsuarioLogado();
	}

	/**
	 * Metodo que adiciona uma mensagem do tipo ALERTA ao JSF
	 * 
	 * @param titilo
	 *            O titulo da mensagem
	 * @param mensagem
	 *            A mensagem a ser exibida
	 */
	protected void exibirMensagemAlerta(String titilo, String mensagem) {

		exibirMensagem(TipoMensagem.Alerta, titilo, mensagem);
	}

	/**
	 * Metodo que adiciona uma mensagem do tipo ERRO ao JSF
	 * 
	 * @param titilo
	 *            O titulo da mensagem
	 * @param mensagem
	 *            A mensagem a ser exibida
	 */
	protected void exibirMensagemErro(String titilo, String mensagem) {

		exibirMensagem(TipoMensagem.Erro, titilo, mensagem);
	}

	/**
	 * Metodo que adiciona uma mensagem do tipo INFORMAÇÃO ao JSF
	 * 
	 * @param titilo
	 *            O titulo da mensagem
	 * @param mensagem
	 *            A mensagem a ser exibida
	 */
	protected void exibirMensagemInformacao(String titilo, String mensagem) {

		exibirMensagem(TipoMensagem.Info, titilo, mensagem);
	}

	/**
	 * Metodo que adiciona uma mensagem do tipo em parametro ao JSF
	 * 
	 * @param tipoMensagem
	 *            O tipo da Mensagem
	 * @see TipoMensagem
	 * 
	 * @param titilo
	 *            O titulo da mensagem
	 * @param mensagem
	 *            A mensagem a ser exibida
	 */
	protected void exibirMensagem(TipoMensagem tipoMensagem, String titilo,
			String mensagem) {

		sessaoMB.mensagem(tipoMensagem, titilo, mensagem);
	}

	/**
	 * 
	 * @return O codigo da filial
	 */
	public int getFilialCodigo() {
		return gerenteLogin.getCodigoFilialSelecionada();

	}

	/**
	 * 
	 * @return TRUE caso o bean necessite de login para exibir suas informações.
	 */
	public abstract boolean necessarioLogin();

	/**
	 * @return TRUE se o bean pode exibir informações para cliente
	 */
	public abstract boolean permiteAcessoDeCliente();

	/**
	 * 
	 * @return TRUE caso o parametro permite visitante esteja marcado
	 */
	public boolean permiteAcessoVisitante() {

		if (parametroWebMB != null && parametroWebMB.getParametroWeb() != null) {
			return parametroWebMB.getParametroWeb().isPermiteVisitantes();
		}

		return false;
	}

	/**
	 * Retorna a url que requisitou alguma acão
	 * 
	 * @return
	 */
	public String getURLRequisicao() {

		try {
			HttpServletRequest req = (HttpServletRequest) FacesContext
					.getCurrentInstance().getExternalContext().getRequest();

			return req.getRequestURI();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;
	}

	public String getURLRequisicaoSemRootPath() {

		String url = getURLRequisicao();

		if (StringUtil.isValida(url)) {

			return url.replace(arquivo.getRootPath(), "").trim();

		}
		return null;

	}

	protected void lancarExececaoGenerica(String msg) {
		throw new ErroGenericoExcecao(msg);
	}

	/**
	 * @return Retorna a pagina o xhtml, que é a representação do beam
	 */
	protected abstract String getUrlViewPage();

	/**
	 * 
	 * @return Retorna o ID , que é a representação do beam ou seja retorna o id
	 *         mapeado pelo {@link UrlMapping#getPattern()}
	 * 
	 */
	protected abstract String getUrlViewPattern();
}
