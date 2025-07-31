package br.com.space.spacewebII.controle;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import br.com.space.api.core.sistema.CodigoSistema;
import br.com.space.api.core.sistema.Conversao;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.modelo.aplicacao.Configuracao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.Sessao;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;
import br.com.space.spacewebII.modelo.util.HttpUtil;

public class GerenteSessao {

	private static HashMap<Integer, UsuarioWeb> sessoesAtivas = null;

	/**
	 * Abre Sessao no Banco de Log do sistema Retaguarda.
	 * 
	 * @param daoLog
	 * @param httpUtil
	 * @param usuarioLogado
	 * @param observacao
	 * @return
	 */
	public static Sessao abrirSessao(GenericoDAOLog daoLog, HttpUtil httpUtil, UsuarioWeb usuarioLogado,
			String observacao) {

		HttpServletRequest request = httpUtil.getHttpServletRequest();

		String ip = request != null ? request.getRemoteAddr() : null;

		Date dataInicial = new Date();

		Sessao sessao = new Sessao(usuarioLogado.getLogin(), dataInicial, Conversao.formatarDataHHMMSS(dataInicial), ip,
				observacao, httpUtil.getIDSessao(), Configuracao.getAtributosConfiguracao().getFilialPadraoCodigo());

		try {
			daoLog.beginTransaction();

			daoLog.insert(sessao);

			addSessaoAtiva(sessao, usuarioLogado);

			daoLog.commitTransaction();

		} catch (Exception e) {
			e.printStackTrace();
			daoLog.rollBackTransaction();
			return null;
		}
		return sessao;
	}

	/**
	 * 
	 * @param dao
	 * @param daoLog
	 * @param sessao
	 * @param liberarTodosRegistros
	 */
	public static void fecharSessao(GenericoDAO dao, GenericoDAOLog daoLog, Sessao sessao,
			boolean liberarTodosRegistros, String obsCancelamento) {

		if (sessao != null) {

			Date dateFim = new Date();

			sessao.setDataFinal(dateFim);
			sessao.setHoraFinal(Conversao.formatarDataHHMMSS(dateFim));
			try {

				daoLog.beginTransaction();

				daoLog.update(sessao);

				removerSessaoAtiva(sessao);

				if (dao != null && liberarTodosRegistros) {

					String delete = new StringBuilder("delete from lockreg where lcr_sescodigo = ")
							.append(sessao.getCodigo()).toString();

					dao.beginTransaction();

					dao.executeUpdate(delete);

					dao.commitTransaction();
				}

				daoLog.commitTransaction();

				ParametroWeb parametroWeb = ParametroWeb.recuperarUnico(dao,
						Configuracao.getAtributosConfiguracao().getFilialPadraoCodigo());

				if (!parametroWeb.isFlagRecuperaPedido()) {
					GerentePedido.cancelarPedidosEmInclusao(dao, sessao,
							Configuracao.getAtributosConfiguracao().getFilialPadraoCodigo(), obsCancelamento);
				}

				GerentePedido.desfazerAlteracoes(dao, sessao, obsCancelamento);

			} catch (Exception e) {
				daoLog.rollBackTransaction();

				if (dao != null) {
					dao.rollBackTransaction();
				}
			}

			sessao = null;
		}
	}

	/**
	 * 
	 * @param sessao
	 * @param usuario
	 */
	public static void addSessaoAtiva(Sessao sessao, UsuarioWeb usuario) {

		if (sessoesAtivas == null) {
			sessoesAtivas = new HashMap<Integer, UsuarioWeb>();
		}

		if (sessao != null && usuario != null) {
			sessoesAtivas.put(Integer.valueOf(sessao.getCodigo()), usuario);
		}
	}

	/**
	 * 
	 * @param sessao
	 */
	private static void removerSessaoAtiva(Sessao sessao) {

		if (sessao == null) {
			return;
		}

		Integer key = Integer.valueOf(sessao.getCodigo());

		if (sessoesAtivas != null && sessoesAtivas.containsKey(key)) {
			sessoesAtivas.remove(key);
		}
	}

	/**
	 * 
	 * @param sessao
	 * @return
	 */
	public static boolean sessaoAtiva(Sessao sessao) {

		if (sessoesAtivas != null) {
			return sessoesAtivas.containsKey(Integer.valueOf(sessao.getCodigo()));
		}

		return false;
	}

	/**
	 * Fecha todas as sessoes ininativas no banco de dados.
	 * 
	 * @param dao
	 * @param daoLog
	 * @return Uma lista contento todas as sessoes que foram fechadas
	 */
	public static List<Sessao> fecharSessoesInativa(GenericoDAO dao, GenericoDAOLog daoLog,
			String obsComplementarLogPedidos) {

		List<Sessao> sessoesFechadas = null;

		List<Sessao> sessoesAbertas = Sessao.recuperarSessoesAbertas(daoLog, CodigoSistema.VENDA_WEB,
				Configuracao.getAtributosConfiguracao().getFilialPadraoCodigo());

		if (sessoesAbertas != null && sessoesAbertas.size() > 0) {

			sessoesFechadas = new ArrayList<Sessao>();

			for (Sessao sessao : sessoesAbertas) {

				boolean sessaoAtiva = sessaoAtiva(sessao);

				if (!sessaoAtiva) {
					fecharSessao(dao, daoLog, sessao, true, obsComplementarLogPedidos);
					sessoesFechadas.add(sessao);
				}
			}
		}

		return sessoesFechadas;
	}
}
