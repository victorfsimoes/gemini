package br.com.space.spacewebII.controle.autenticacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.jboss.seam.security.Authenticator.AuthenticationStatus;
import org.jboss.seam.security.Credentials;
import org.jboss.seam.security.IdentityImpl;
import org.picketlink.idm.impl.api.PasswordCredential;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoBalancoAtivoExcecao;
import br.com.space.spacewebII.controle.GerenteSessao;
import br.com.space.spacewebII.controle.Licenca;
import br.com.space.spacewebII.controle.aplicacao.HttpUtilMB;
import br.com.space.spacewebII.controle.pedido.mbean.GerentePedidoMB;
import br.com.space.spacewebII.controle.produto.mbean.ProdutoPesquisaMB;
import br.com.space.spacewebII.modelo.anotacao.CodigoFilialSelecionado;
import br.com.space.spacewebII.modelo.anotacao.SessaoAutenticada;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.aplicacao.Configuracao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.sistema.Sessao;
import br.com.space.spacewebII.modelo.dominio.sistema.UsuarioCliente;
import br.com.space.spacewebII.modelo.dominio.sistema.UsuarioFilial;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;
import br.com.space.spacewebII.modelo.dominio.venda.StatusPedido;
import br.com.space.spacewebII.modelo.excecao.pedido.PedidoEmEdicaoExcecao;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroAcessoExclusivoExcecao;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroTravamentoExcecao;
import br.com.space.spacewebII.modelo.padrao.interfaces.AtributoSessao;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

@ManagedBeanSessionScoped
@URLMappings(mappings = {
		@URLMapping(id = "loginBarra", pattern = "/login/", viewId = "/pages/login.xhtml", onPostback = false),
		@URLMapping(id = "login", pattern = "/login", viewId = "/pages/login.xhtml", onPostback = false) })
public class GerenteLogin implements Serializable, AtributoSessao {

	public static final String URL_VIEW = "/login";
	private static final long serialVersionUID = 1L;

	@Inject
	GenericoDAO dao;

	@Inject
	protected Propriedade propriedade;

	@Inject
	private GerentePedidoMB gerentePedidoMB;

	@Inject
	Parametros parametros;

	@Inject
	private GenericoDAOLog daoLog;

	@Inject
	HttpUtilMB sessionUtil = null;

	@Inject
	ProdutoPesquisaMB produtoPesquisaMB;

	@Inject
	@SessaoAutenticada
	private Event<UsuarioWeb> eventSessaoAutenticada;

	@Inject
	private Credentials credentials;

	/**
	 * Evento disparado quando troca a filial.
	 */
	@Inject
	@CodigoFilialSelecionado
	private Event<Integer> eventCodigoFilial;

	@Inject
	private Configuracao configuracao;

	@Inject
	private IdentityImpl identityImpl;

	private AuthenticationStatus authenticationStatus = AuthenticationStatus.FAILURE;

	/*
	 * Usado pelo popupFilia.
	 */
	private Integer codigoFilialSelecionada = 0;

	private List<UsuarioFilial> filiaisUsuario = null;
	private String perfil = "Colaborador";
	private String mensagem = null;
	private boolean permiteVisitante = true;

	private Filial filialLogada = null;
	private UsuarioWeb usuarioLogado = null;
	private Filial filialPadrao = null;

	private Sessao sessao = null;

	// colocar mensagem de usuário não encontrado no perfil.

	public GerenteLogin() {
		super();

		if (isDesmostracao()) {
			perfil = "Cliente";
		}
	}

	/**
	 * 
	 */
	@PostConstruct
	public void carregarParametros() {

		try {

			/*
			 * Carrega a filial padrao do site. Essa filial deve estar
			 * configurada no arquivo XML do site. Definir nome e local do
			 * arquivo.
			 */
			filialPadrao = Filial.recuperarUnico(dao, configuracao.getAtributos().getFilialPadraoCodigo());

			if (this.filialLogada == null && this.filialPadrao != null) {
				this.setFilialLogada(this.filialPadrao);
			}

			/*
			 * Carrega também a licenca
			 * 
			 * leitorLicenca = new LeitorLicenca(); Grds grds =
			 * Grds.recuperarUnico(dao, this.getFilialLogada() .getCodigo());
			 * 
			 * getLeitorLicenca().setParamCodigoSistema("8"); getLeitorLicenca()
			 * .setParamCnpjCpf(this.getFilialLogada().getCnpj());
			 * getLeitorLicenca().carregarLicencaString(grds.getLicenca());
			 */
			setCredencialDemo();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void limparDadosLogin() {
		this.filialLogada = null;
		this.filiaisUsuario = null;
		this.usuarioLogado = null;
	}

	/**
	 * 
	 */
	private void setCredencialDemo() {
		if (!isDesmostracao()) {
			return;
		}

		if (isPerfilSelecionadoCliente()) {
			credentials.setUsername("demo@spaceinformatica.com.br");

		} else {
			credentials.setUsername("demo");
		}

		credentials.setCredential(new PasswordCredential("demo"));
	}

	/**
	 * 
	 * @param userLogin
	 * @return
	 */
	public List<UsuarioFilial> recuperarFiliaisUsuario(String userLogin) {

		List<UsuarioFilial> listUsuarioFilial = null;

		if (userLogin != null && !userLogin.isEmpty()) {
			if (isPerfilColaborador()) {
				listUsuarioFilial = dao.list(UsuarioFilial.class, "usf_usrlogin = '" + userLogin.trim() + "'", null, "",
						"");

				if (listUsuarioFilial != null && listUsuarioFilial.size() > 0) {
					codigoFilialSelecionada = listUsuarioFilial.get(0).getFilial().getCodigo();
				}
			} else {
				if (filialLogada != null) {
					listUsuarioFilial = new ArrayList<UsuarioFilial>();
					listUsuarioFilial.add(new UsuarioFilial(filialLogada, userLogin));
					codigoFilialSelecionada = filialLogada.getCodigo();
				}
			}
		}

		return listUsuarioFilial;
	}

	/**
	 * 
	 * @param usrLogin
	 */
	private void preencherFiliaisUsuario() {
		if (this.usuarioLogado == null)
			filiaisUsuario = null;
		else
			filiaisUsuario = recuperarFiliaisUsuario(this.usuarioLogado.getLogin());
	}

	/**
	 * Confirma o codigo da filial selecionada e procura a mesma na lista de
	 * filiais do usuario.
	 */
	public void confirmarFilialSelecionada() {
		if (codigoFilialSelecionada != null && codigoFilialSelecionada > 0) {

			for (UsuarioFilial filial : filiaisUsuario) {
				if (filial.getFilial().getCodigo() == codigoFilialSelecionada) {
					this.setFilialLogada(filial.getFilial());
					break;
				}
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<UsuarioFilial> getFiliaisUsuario() {
		return filiaisUsuario;
	}

	/**
	 * 
	 * @return
	 */
	public String getUserLogin() {
		if (this.usuarioLogado != null)
			return this.usuarioLogado.getLogin();
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getCodigoFilialSelecionada() {
		return codigoFilialSelecionada;
	}

	/**
	 * 
	 * @param codigoFilialSelecionada
	 */
	public void setCodigoFilialSelecionada(Integer codigoFilialSelecionada) {
		this.codigoFilialSelecionada = codigoFilialSelecionada;
		eventCodigoFilial.fire(codigoFilialSelecionada);
	}

	/**
	 * 
	 * @return
	 */
	public Integer getColaboradorCodigo() {
		if (usuarioLogado == null)
			return 0;
		return usuarioLogado.getColaboradorCodigo();
	}

	/**
	 * 
	 * @return
	 */
	public Integer getClienteCodigo() {
		if (usuarioLogado == null || isPerfilColaborador())
			return 0;

		return ((UsuarioCliente) usuarioLogado).getPessoaCodigo();
	}

	/**
	 * 
	 * @return
	 */
	public Filial getFilialLogada() {
		return filialLogada;
	}

	/**
	 * 
	 * @return
	 */
	public Filial getFilialPadrao() {
		return this.filialPadrao;
	}

	/**
	 * 
	 * @return
	 */
	public int getFilialCodigo() {
		if (this.filialLogada != null)
			return this.filialLogada.getCodigo();
		return 0;
	}

	/**
	 * 
	 * @return
	 */
	public UsuarioWeb getUsuarioLogado() {
		return usuarioLogado;
	}

	/**
	 * 
	 * @return
	 */
	public String getPerfil() {
		return perfil;
	}

	/**
	 * 
	 * @param perfil
	 */
	public void setPerfil(String perfil) {
		this.perfil = perfil;
		setCredencialDemo();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPerfilColaborador() {
		boolean logado = isSessoaAutenticada();

		return logado && perfil != null && perfil.equals("Colaborador");
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPerfilCliente() {
		return (isSessoaAutenticada() && perfil != null && isPerfilSelecionadoCliente()) || !isSessoaAutenticada();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPerfilSelecionadoCliente() {
		return perfil.equals("Cliente");
	}

	/**
	 * 
	 * @return
	 */
	public String getMensagem() {
		return mensagem;
	}

	/**
	 * 
	 * @param mensagem
	 */
	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPermiteVisitante() {
		return permiteVisitante;
	}

	/**
	 * 
	 * @param filialLogada
	 */
	public void setFilialLogada(Filial filialLogada) {
		this.filialLogada = filialLogada;
		this.codigoFilialSelecionada = this.filialLogada.getCodigo();
	}

	/**
	 * 
	 * @param usuario
	 */
	public void setUsuarioLogado(UsuarioWeb usuario) {

		this.usuarioLogado = usuario;

		preencherFiliaisUsuario();

		if (usuarioLogado != null && authenticationStatus == AuthenticationStatus.SUCCESS) {

			eventSessaoAutenticada.fire(usuario);

			sessao = GerenteSessao.abrirSessao(daoLog, sessionUtil, usuarioLogado,
					"FILIAL = " + getFilialLogada().getCodigo());

			if (sessao != null) {
				sessionUtil.setAtribute(CODIGO_SESSAO_GUARDIAN, Integer.valueOf(sessao.getCodigo()));

				sessionUtil.setAtribute(SESSAO_GUARDIAN, sessao);
			}

			sessionUtil.setAtribute(USUARIO_LOGADO, usuario);
			sessionUtil.setAtribute(USER_TOMCAT, usuario.getLogin());

			/*
			 * 18/05/2017 - Sandro - Verificar login - verificar pedidos em
			 * inclusão há 7 dias; recuperar último pedido
			 */

			parametros = Parametros.getInstancia(dao, getFilialLogada());

			if (parametros.getParametroWeb().isFlagRecuperaPedido()) {

				carregarPedidoEmInclusao();

			}

		}
	}

	public void carregarPedidoEmInclusao() {
		long diasRetroagir = 7L * (24L * 60L * 60L * 1000L);

		long dataInicial = System.currentTimeMillis() - diasRetroagir;

		Date dataIni = new Date(dataInicial);
		Date dataMax = new Date();

		String[] statusEmInclusao = new String[] { ((Integer) StatusPedido.STATUS_EM_INCLUSAO).toString() };

		List<Pedido> listaPedidos = null;

		if (isPerfilSelecionadoCliente()) {

			listaPedidos = Pedido.recuperarPedidosFiltradosCliente(dao, getClienteCodigo(),
					getFilialLogada().getCodigo(), dataIni, dataMax, statusEmInclusao, true);

		} else {

			listaPedidos = Pedido.recuperarPedidosFiltradosUsuario(dao, getColaboradorCodigo(),
					getFilialLogada().getCodigo(), dataIni, dataMax, statusEmInclusao, null, true);
		}

		if (ListUtil.isValida(listaPedidos)) {
			Collections.reverse(listaPedidos);
			Pedido pedido = listaPedidos.get(0);

			try {
				gerentePedidoMB.editarPedidoEmInclusao(pedido);
			} catch (PedidoEmEdicaoExcecao | RegistroAcessoExclusivoExcecao | RegistroTravamentoExcecao
					| PedidoBalancoAtivoExcecao e) {
				e.printStackTrace();
			}

		}

	}

	public void setAuthenticationStatus(AuthenticationStatus authenticationStatus) {
		this.authenticationStatus = authenticationStatus;
	}

	public AuthenticationStatus getAuthenticationStatus() {
		return authenticationStatus;
	}

	public Sessao getSessao() {
		return sessao;
	}

	public int getSessaoCodigo() {
		if (sessao != null) {
			return sessao.getCodigo();
		}
		return 0;
	}

	public String logout() {

		identityImpl.logout();
		return produtoPesquisaMB.carregarView();
	}

	public String getUrlView() {
		return URL_VIEW;
	}

	public String carregarPaginaLogin() {
		return getUrlView();
	}

	public boolean isSessoaAutenticada() {
		return identityImpl.isLoggedIn();
	}

	/**
	 * Verifica se cliente possui os dois módulos; caso contrário, seta
	 * propriedade perfil de acordo o módulo adquirido
	 * 
	 * @return
	 */
	public boolean verificarModulosClienteColaborador() {
		boolean perfilCliente = Licenca.isModuloClienteAtivo();
		boolean perfilColaborador = Licenca.isModuloColaboradorAtivo();
		if (perfilCliente != perfilColaborador) {
			if (perfilCliente) {
				this.setPerfil("Cliente");
			} else if (perfilColaborador) {
				this.setPerfil("Colaborador");
			}
		}
		return (perfilCliente && perfilColaborador);
	}

	public void perfilChangeListener(ValueChangeEvent e) {
		setPerfil((String) e.getNewValue());
	}

	public boolean isDesmostracao() {
		return Configuracao.getAtributosConfiguracao().isDemostracao();
	}

}
