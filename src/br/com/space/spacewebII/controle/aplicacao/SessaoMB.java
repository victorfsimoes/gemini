package br.com.space.spacewebII.controle.aplicacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.persistence.Table;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.primefaces.component.menuitem.MenuItem;
import org.primefaces.model.DefaultMenuModel;
import org.primefaces.model.MenuModel;

import br.com.space.api.core.licenca.LeitorLicenca;
import br.com.space.api.core.sistema.Conversao;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.aplicacao.RegistroNavegacao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.LockRegistro;
import br.com.space.spacewebII.modelo.dominio.sistema.LockTabela;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroAcessoExclusivoExcecao;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroTravamentoExcecao;
import br.com.space.spacewebII.modelo.padrao.interfaces.AtributoSessao;
import br.com.space.spacewebII.modelo.padrao.interfaces.Travavel;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.Fabrica;

@ManagedBeanSessionScoped
public class SessaoMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private final int quantidadeMaximaHistorico = 10;

	/*
	 * Os objetos injetdados que não estão sendo usados foram declarados para
	 * forcar a criação dos mesmos no inicio de cada sessao.
	 */

	@Inject
	private GenericoDAO dao;

	@Inject
	private GerenteLogin gerenteLogin;

	@Inject
	private Propriedade propriedade;

	@Inject
	private HttpUtilMB httpUtilMB;

	private ArrayList<RegistroNavegacao> historicoNavegacao = null;
	private ArrayList<String> mapaSiteNavegacao = null;

	private LeitorLicenca leitorLicencaSpaceWeb;
	private LeitorLicenca leitorLicencaSpaceViking;

	private String urlAnterior = "/pages/login.xhtml";

	public SessaoMB() {
		super();
	}

	/**
	 * 
	 */
	public void carregarNovaSessao() {
		FacesContext.getCurrentInstance().getExternalContext().getSession(true);
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public String redirecionar(String url) {

		try {
			HttpServletRequest r = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext()
					.getRequest();

			urlAnterior = r.getRequestURI();

			urlAnterior = urlAnterior.substring(r.getContextPath().length());

		} catch (Exception e) {
		}

		return url;
	}

	/*
	 * 
	 * BANCO DE DADOS
	 */

	private LockRegistro recuperarLockRegistro(Travavel entidade, int sessaoCodigo, String programa) {

		String valorChave = entidade.getChaveTrava();
		String nomeTabela = entidade.getNomeTabelaTrava();

		String where = "lcr_valor = '" + valorChave + "' and lcr_tabela = '" + nomeTabela + "'";

		LockRegistro lockRegistroAux = dao.uniqueResult(LockRegistro.class, where, null);

		return lockRegistroAux;

	}

	/**
	 * 
	 * @param entidade
	 * @throws RegistroAcessoExclusivoExcecao
	 */
	public void travarRegistro(Travavel entidade, int sessaoCodigo, String programa, boolean novaTransacao)
			throws RegistroAcessoExclusivoExcecao, RegistroTravamentoExcecao {

		LockRegistro lockRegistroAux = recuperarLockRegistro(entidade, sessaoCodigo, programa);

		/*
		 * Verifica se existe um registro com a chave e programa informado, se
		 * existir significa que alguem já travou.
		 */
		if (lockRegistroAux != null && lockRegistroAux.getSessaoCodigo() != sessaoCodigo) {
			throw new RegistroAcessoExclusivoExcecao(propriedade);
		}

		/*
		 * Insere o registro de travamento.
		 */
		if (lockRegistroAux == null) {
			String valorChave = entidade.getChaveTrava();
			String nomeTabela = entidade.getNomeTabelaTrava();

			Date data = new Date();

			LockRegistro lockRegistro = new LockRegistro(0, sessaoCodigo, data,
					Conversao.formatarData(data, Conversao.FORMATO_HORA), programa, valorChave, nomeTabela);

			try {
				if (novaTransacao) {
					dao.beginTransaction();
				}

				dao.insert(lockRegistro);

				if (novaTransacao) {
					dao.commitTransaction();
				}
			} catch (Exception e) {
				if (novaTransacao)
					dao.rollBackTransaction();
				e.printStackTrace();
				throw new RegistroTravamentoExcecao(propriedade);
			}
		}
	}

	public void destravarRegistro(Travavel entidade, int sessaoCodigo, String programa, boolean novaTransacao)
			throws RegistroAcessoExclusivoExcecao, RegistroTravamentoExcecao {
		destravarRegistro(entidade, sessaoCodigo, programa, novaTransacao, false);
	}

	/**
	 * 
	 * @param entidade
	 * @param sessaoCodigo
	 * @param programa
	 * @throws RegistroAcessoExclusivoExcecao
	 * @throws RegistroTravamentoExcecao
	 */
	public void destravarRegistro(Travavel entidade, int sessaoCodigo, String programa, boolean novaTransacao,
			boolean forcarDestrava) throws RegistroAcessoExclusivoExcecao, RegistroTravamentoExcecao {

		/*
		 * Verifica se o registro foi travado pela sessão atual.
		 */
		LockRegistro lockRegistroAux = recuperarLockRegistro(entidade, sessaoCodigo, programa);

		if (lockRegistroAux != null && lockRegistroAux.getSessaoCodigo() != sessaoCodigo && !forcarDestrava) {

			// Tentando destravar registro de outra sessão.
			throw new RegistroAcessoExclusivoExcecao(propriedade);
		} else {
			if (lockRegistroAux != null) {
				try {
					if (novaTransacao)
						dao.beginTransaction();
					dao.delete(lockRegistroAux);
					if (novaTransacao)
						dao.commitTransaction();
				} catch (Exception e) {
					if (novaTransacao)
						dao.rollBackTransaction();
					e.printStackTrace();
					throw new RegistroTravamentoExcecao(propriedade);
				}
			}
		}
	}

	/**
	 * 
	 * @param entidade
	 * @param sessaoCodigo
	 * @param programa
	 * @return
	 */
	public boolean verificarTravaRegistro(Travavel entidade, int sessaoCodigo, String programa) {

		boolean result = false;
		LockRegistro lockRegistro;
		try {
			lockRegistro = recuperarLockRegistro(entidade, sessaoCodigo, programa);
			result = lockRegistro != null;
		} finally {
			lockRegistro = null;
		}

		return result;
	}

	/**
	 * Trava registro no banco de dados com os dados da Entidade
	 * 
	 * @param codigoSessao
	 * @param programa
	 * @return
	 */
	public LockRegistro retornarChave(int codigoSessao, String programa, int codigoTabela) {

		String retorno = String.valueOf(codigoTabela);
		retorno = "          " + retorno.trim().substring(retorno.length() - 10, retorno.length());

		LockRegistro lockRegistro = new LockRegistro(0, codigoSessao, new Date(),
				Conversao.formatarData(new Date(), Conversao.FORMATO_HORA), programa, retorno,
				getClass().getAnnotation(Table.class).name().toUpperCase());

		return lockRegistro;
	}

	/**
	 * 
	 * @param tabelas
	 * @return
	 */
	public void travarTabelas(String... tabelas) {

		ArrayList<String> lstTabelas = null;
		try {
			lstTabelas = new ArrayList<>();

			for (String tabela : tabelas) {
				lstTabelas.add(tabela);
			}

			Collections.sort(lstTabelas);
			int tentativas = 1;
			boolean travouTabelas = false;

			while (tentativas <= 3 && !travouTabelas) {
				try {
					for (String tabela : lstTabelas) {
						LockTabela lockTabela = LockTabela.recuperaUnico(dao, tabela);

						if (lockTabela != null) {
							lockTabela.setCodigoSessao(getCodigoSessaoAtiva());
							dao.update(lockTabela);
						} else {
							lockTabela = new LockTabela();
							lockTabela.setCodigoSessao(getCodigoSessaoAtiva());
							lockTabela.setNomeTabela(tabela);
							dao.insert(lockTabela);
						}
					}
					travouTabelas = true;
				} catch (Exception e) {
					tentativas += 1;
				}
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (lstTabelas != null)
				lstTabelas.clear();
		}
	}

	public LeitorLicenca getLeitorLicenca() {
		return leitorLicencaSpaceWeb;
	}

	public LeitorLicenca getLeitorLicencaSpaceViking() {
		return leitorLicencaSpaceViking;
	}

	public int getCodigoSessaoAtiva() {
		if (gerenteLogin.getSessao() != null) {
			return gerenteLogin.getSessao().getCodigo();
		}

		return 0;
	}

	public String getUrlAnterior() {
		return urlAnterior;
	}

	public void setUrlAnterior(String urlAnterior) {
		this.urlAnterior = urlAnterior;
	}

	/**
	 * 
	 * @param tipo
	 *            = erro, fatal, info, alerta
	 * @param titulo
	 * @param mensagem
	 */
	public void mensagem(TipoMensagem tipo, String titulo, String mensagem) {
		MensagemSistema.exibir(tipo, titulo, mensagem);
	}

	/**
	 * 
	 * @return
	 */
	public String getMensagemErroGenerico() {

		Object object = httpUtilMB.getAtribute(AtributoSessao.MENSAGEM_EXCECAO_ERROGENERICO);

		if (object != null) {
			return object.toString();
		}

		return null;
	}

	/**
	 * 
	 * @param url
	 */
	private void adicionarMapasite(String url) {

		if (mapaSiteNavegacao == null) {
			mapaSiteNavegacao = new ArrayList<String>();
		}

		if (!url.endsWith("/")) {
			removerUrlMapaNavegacao(url);
			mapaSiteNavegacao.add(url);
		}
	}

	/**
	 * 
	 * @param url
	 */
	private void removerUrlMapaNavegacao(String url) {

		if (mapaSiteNavegacao != null && mapaSiteNavegacao.size() > 0) {
			@SuppressWarnings("unchecked")
			List<String> clone = (List<String>) mapaSiteNavegacao.clone();

			for (int i = 0; i < clone.size(); i++) {
				if (clone.get(i).equals(url) && i < mapaSiteNavegacao.size()) {
					mapaSiteNavegacao.remove(i);
				}
			}
		}
	}

	/**
	 * 
	 * @param url
	 */
	public void adicionarHistoricoNavegacao(String url) {

		if (historicoNavegacao == null) {
			historicoNavegacao = new ArrayList<RegistroNavegacao>();
		}

		historicoNavegacao.add(new RegistroNavegacao(url, System.currentTimeMillis()));

		adicionarMapasite(url);
	}

	/**
	 * 
	 * @return
	 */
	public MenuModel getMenuModel() {

		DefaultMenuModel menuModel = new DefaultMenuModel();

		MenuItem item = new MenuItem();
		item.setValue("Home");
		item.setUrl("/pages/home.xhtml");
		item.setId("mi_" + Fabrica.gerarIDRandomico());

		menuModel.addMenuItem(item);

		if (mapaSiteNavegacao != null && mapaSiteNavegacao.size() > 0) {

			List<String> mapaSite = mapaSiteNavegacao;

			if (mapaSiteNavegacao.size() > quantidadeMaximaHistorico) {
				mapaSite = mapaSiteNavegacao.subList(mapaSiteNavegacao.size() - quantidadeMaximaHistorico,
						mapaSiteNavegacao.size());
			}
			for (int i = 0; i < mapaSite.size(); i++) {

				String url = mapaSiteNavegacao.get(i);

				MenuItem menuItem = new MenuItem();
				menuItem.setValue(getTituiloPagina(url));
				menuItem.setUrl(url);
				menuItem.setId("mi_" + i + Fabrica.gerarIDRandomico());

				menuModel.addMenuItem(menuItem);
			}
		}
		return menuModel;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	private String getTituiloPagina(String url) {
		return Fabrica.gerarNomePagina(url);
	}

	/**
	 * Retorna a ultima pagina que gerou um erro.
	 * 
	 * @return
	 */
	public String getURLOrigemErro() {
		String url = null;

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		if (session != null) {
			Object attr = session.getAttribute(AtributoSessao.URL_ORIGEM_ERRO);
			if (attr != null) {
				url = (String) attr;
			}
		}

		return url;
	}

	/**
	 * 
	 * @return
	 */
	public String getComandoVoltarErro() {
		String url = null;

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance().getExternalContext().getSession(false);

		if (session != null) {
			Object attr = session.getAttribute(AtributoSessao.COMANDO_VOLTAR_ERRO);
			if (attr != null) {
				url = (String) attr;
			}
		}

		return url;
	}

	public GerenteLogin getGerenteLogin() {
		return gerenteLogin;
	}

	public Propriedade getPropriedade() {
		return propriedade;
	}
}
