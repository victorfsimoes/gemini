package br.com.space.spacewebII.controle.permissao;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

import javax.inject.Inject;

import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.PermissaoGrupo;
import br.com.space.spacewebII.modelo.dominio.sistema.UsuarioPrograma;

/**
 * Classe responsável pelo gerenciamento de permissões.
 * 
 * 
 * @author Desenvolvimento
 * 
 */
@ManagedBeanSessionScoped
public class GerentePermissao implements Serializable {

	private static final long serialVersionUID = 1L;

	private HashMap<String, Boolean> hashPermissoesUsuarioLogado;

	@Inject
	private GenericoDAO dao;

	@Inject
	private GerenteLogin autenticador;

	/**
	 * Construtor padrão
	 */
	public GerentePermissao() {
	}

	/**
	 * Recupera do banco de dados a lista de permissões do usuario logado.
	 */
	public void carregarListaPermissoes() {

		if (hashPermissoesUsuarioLogado == null) {
			hashPermissoesUsuarioLogado = new HashMap<String, Boolean>();
		}

		List<UsuarioPrograma> usuarioProgramas = dao.list(
				UsuarioPrograma.class, "upr_usrlogin = '"
						+ autenticador.getUsuarioLogado().getLogin() + "'",
				null, "", "");

		List<PermissaoGrupo> permissaoGrupos = dao.list(PermissaoGrupo.class,
				"psg_grucodigo = "
						+ autenticador.getUsuarioLogado().getCodigoGrupo(),
				null, null, null);

		Collections.sort(permissaoGrupos);

		removerPermissoesExcluidasUsuario(usuarioProgramas, permissaoGrupos);

		adicionarPermissoesDoGrupoUsuario(permissaoGrupos);

		adicionarPermissoesDoUsuario(usuarioProgramas);
	}

	private void adicionarPermissoesDoGrupoUsuario(
			List<PermissaoGrupo> permissaoGrupos) {
		for (PermissaoGrupo permissaoGrupo : permissaoGrupos) {

			adicionarPermissaoALista(permissaoGrupo.getChavePermissaoGrupo()
					.getCodigoPrograma(), permissaoGrupo
					.getChavePermissaoGrupo().getCodigoPermissao(), true);
		}
	}

	private void adicionarPermissoesDoUsuario(
			List<UsuarioPrograma> usuarioProgramas) {

		for (UsuarioPrograma usuarioPrograma : usuarioProgramas) {

			if (usuarioPrograma != null
					&& !usuarioPrograma.getIncluirExcluirPermissaoGrupo()
							.equals("E")) {

				adicionarPermissaoALista(usuarioPrograma.getChaveUsrProg()
						.getPrograma(), usuarioPrograma.getChaveUsrProg()
						.getCodigoPermissao(), true);
			}
		}
	}

	private void adicionarPermissaoALista(String codigoPrograma,
			int codigoPermissao, boolean isPermitida) {

		String key = codigoPrograma;

		if (!hashPermissoesUsuarioLogado.containsKey(key)) {
			hashPermissoesUsuarioLogado.put(key, isPermitida);
		}

		key = codigoPrograma + Integer.toString(codigoPermissao);

		if (!hashPermissoesUsuarioLogado.containsKey(key)) {
			hashPermissoesUsuarioLogado.put(key, isPermitida);
		}
	}

	/**
	 * 
	 * @param usuarioProgramas
	 * @param permissaoGrupos
	 */
	private void removerPermissoesExcluidasUsuario(
			List<UsuarioPrograma> usuarioProgramas,
			List<PermissaoGrupo> permissaoGrupos) {

		for (UsuarioPrograma usuarioPrograma : usuarioProgramas) {

			if (usuarioPrograma != null
					&& usuarioPrograma.getIncluirExcluirPermissaoGrupo()
							.equals("E")) {

				PermissaoGrupo key = new PermissaoGrupo(autenticador
						.getUsuarioLogado().getCodigoGrupo(), usuarioPrograma
						.getChaveUsrProg().getPrograma(), usuarioPrograma
						.getChaveUsrProg().getCodigoPermissao(), autenticador
						.getFilialLogada().getCodigo());

				int indice = Collections.binarySearch(permissaoGrupos, key);

				if (indice >= 0) {
					permissaoGrupos.remove(indice);
				}
			}
		}
	}

	/**
	 * Limpa listagem de permisões do usuário logado no Space Web.
	 */
	public void limparListaPermissoes() {
		this.hashPermissoesUsuarioLogado = null;
	}

	public boolean verificarPermissao(String programa) {
		return verificarPermissao(programa, -1);
	}

	/**
	 * Verifica se o usuario logado tem a permissão especficada para o programa
	 * recebido via parametro.
	 * 
	 * @param programa
	 * @param permissao
	 * @return
	 */
	public boolean verificarPermissao(String programa, int permissao) {

		Boolean isPermissao = false;

		if (hashPermissoesUsuarioLogado != null) {
			if (permissao <= 0) {

				isPermissao = hashPermissoesUsuarioLogado.get(programa);

			} else {

				isPermissao = hashPermissoesUsuarioLogado.get(programa
						+ permissao);

			}
		}
		return isPermissao == null ? false : isPermissao;
	}
}
