package br.com.space.spacewebII.controle.financeiro;

import java.util.List;

import br.com.space.api.cartao.modelo.resposta.IRetorno;
import br.com.space.api.cartao.modelo.resposta.IRetornoErro;
import br.com.space.api.cartao.modelo.resposta.IRetornoTransacao;
import br.com.space.api.core.sistema.LogErro;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.aplicacao.GerenteEmail;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.dominio.sistema.MensagemCorreio;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.sistema.log.LogTransacaoPagamentoCartao;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;
import br.com.space.spacewebII.modelo.dominio.venda.TransacaoPagamentoCartao;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.Arquivo;

public class GerentePagamentoCartaoUtil {

	private static Propriedade propriedade = null;

	public enum TipoNotificacao {
		CANCELAMENTO, ERRO_TRANSACAO;
	}

	/**
	 * Grava o no arquivo o log de quando a rotina foi iniciada
	 * 
	 * @param pedidos
	 *            Usada para definir a quantidade de pedidos verificada
	 */
	public static void gravarLogVerificacao(List<Pedido> pedidos) {
		try {

			int quantPedidos = pedidos != null ? pedidos.size() : 0;

			String mensagem = "Verificação de transação com cartão de credito avaliação de "
					+ quantPedidos + " pedido(s)";

			LogErro.gravarErroEmLog(Arquivo
					.getArquivoLogVerificacaoTransacaoCartao()
					.getAbsolutePath(), null, "Ataulizacao Transacao Cartão",
					mensagem);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Enviar a notificação para o responsavel definidos no
	 * {@link ParametroWeb#getEmailAvisoTransacao()} e
	 * {@link ParametroWeb#getUsuarioAvisoTransacao()} A notificações consistes
	 * no envio de um email e mensagem via correio interno.
	 * 
	 * @param dao
	 *            Para gravação do mensagem correio interno
	 * @param transacao
	 *            A transação que esta com problema
	 * @param parametros
	 *            Gerais
	 */
	public static void notificarResponsaveisProblemaTransacao(GenericoDAO dao,
			TransacaoPagamentoCartao transacao, Parametros parametros,
			String informacaoAdcional, TipoNotificacao tipoNotificacao) {

		String mensagem = propriedade
				.getMensagem("mensagem.email.avisotransacao.cancelamento");

		if (tipoNotificacao == TipoNotificacao.ERRO_TRANSACAO) {
			mensagem = propriedade
					.getMensagem("mensagem.email.avisotransacao.errotransacao");
		}

		String mensagemDetalhe = getMensagemDetalheTransacao(getPropriedade(),
				transacao);

		String mensagemDetalheCredencial = getMensagemDetalheCredencialAdm(
				getPropriedade(), transacao);

		String assunto = propriedade
				.getMensagem("titulo.processamento.cartao.problema")
				+ " "
				+ propriedade.getMensagem("titulo.pedido")
				+ " S"
				+ transacao.getPedidoSerie()
				+ "N"
				+ transacao.getPedidoNumero();

		enviarEmailAvisoTransacao(dao, transacao, parametros, mensagem,
				mensagemDetalhe, assunto, mensagemDetalheCredencial,
				informacaoAdcional);

		enviarMsgCorreioTransacao(dao, transacao, parametros, mensagem,
				mensagemDetalhe, assunto, mensagemDetalheCredencial,
				informacaoAdcional);
	}

	/**
	 * 
	 * @param transacao
	 * @return Retorna uma string contendo a descrição da administradora
	 */
	private static String getMensagemDetalheCredencialAdm(
			Propriedade propriedade, TransacaoPagamentoCartao transacao) {

		if (transacao == null
				|| transacao.getCredencialAdministradoraCartao() == null) {
			return "";
		}

		return propriedade.getMensagem(
				"mensagem.email.avisotransacao.detalhesadm", transacao
						.getCredencialAdministradoraCartao().getDescricao(),
				transacao.getCredencialAdministradoraCartao().getCodigo());

	}

	/**
	 * Envia o email com a notificação para os destinatirios presentes no
	 * {@link ParametroWeb#getEmailAvisoTransacao()}
	 * 
	 * @param dao
	 * @param transacao
	 * @param parametros
	 * @param mensagemcancelamento
	 * @param mensagemDetalhe
	 * @param assunto
	 * @param mensagemDetalheCredencial
	 * @param informacaoAdcional
	 */
	private static void enviarEmailAvisoTransacao(GenericoDAO dao,
			TransacaoPagamentoCartao transacao, Parametros parametros,
			String mensagem, String mensagemDetalhe, String assunto,
			String mensagemDetalheCredencial, String informacaoAdcional) {

		if (StringUtil.isValida(mensagemDetalheCredencial)) {
			mensagemDetalhe += " </br><p style='font-weight: bold;' >"
					+ mensagemDetalheCredencial + "</p>";
		}

		if (StringUtil.isValida(informacaoAdcional)) {
			mensagem += "</br>" + informacaoAdcional;
		}

		boolean enviou = GerenteEmail.enviarEmailGenerico(parametros
				.getParametroWeb().getEmailAvisoTransacao(), dao, parametros,
				assunto, mensagem, mensagemDetalhe, null, assunto);

		if (!enviou) {
			LogErro.gravarErroEmLog(Arquivo
					.getArquivoLogErroAtualizacaoTransacaoCartao()
					.getAbsolutePath(), null, "Ataulizacao Transacao Cartão",
					" Erro ao enviar um email para interferencia humana na transação "
							+ mensagemDetalhe
							+ " os emals destinatarios são "
							+ parametros.getParametroWeb()
									.getEmailAvisoTransacao());
		}
	}

	/**
	 * Fornece a descrição da transação
	 * 
	 * @param transacao
	 * @return
	 */
	private static String getMensagemDetalheTransacao(Propriedade propriedade,
			TransacaoPagamentoCartao transacao) {

		if (transacao == null) {
			return "";
		}

		return propriedade.getMensagem(
				"mensagem.email.avisotransacao.detalhes",
				transacao.getPedidoSerie(),
				Integer.toString(transacao.getPedidoNumero()),
				transacao.getIdTransacao(), transacao.getIdTransacaoLocal(),
				transacao.getStatusTransacao());
	}

	/**
	 * Grava as mensagens no correio interno para os usuario do
	 * {@link ParametroWeb#getUsuarioAvisoTransacao()}
	 * 
	 * @param dao
	 * @param transacao
	 * @param parametros
	 * @param mensagem
	 * @param mensagemDetalhe
	 * @param assunto
	 * @param mensagemDetalheCredencial
	 * @param informacaoAdcional
	 */
	private static void enviarMsgCorreioTransacao(GenericoDAO dao,
			TransacaoPagamentoCartao transacao, Parametros parametros,
			String mensagem, String mensagemDetalhe, String assunto,
			String mensagemDetalheCredencial, String informacaoAdcional) {

		if (StringUtil.isValida(mensagemDetalheCredencial)) {
			mensagemDetalhe += System.lineSeparator() + System.lineSeparator()
					+ mensagemDetalheCredencial;
		}

		mensagem += System.lineSeparator() + mensagemDetalhe;

		if (StringUtil.isValida(informacaoAdcional)) {
			mensagem += System.lineSeparator() + informacaoAdcional;
		}

		MensagemCorreio mensagemCorreio = new MensagemCorreio("manutmac",
				parametros.getParametroWeb().getUsuarioAvisoTransacao(),
				assunto, mensagem);

		try {
			List<MensagemCorreio> mensagemCorreios = MensagemCorreio
					.gerarMensagensEntradaSaida(mensagemCorreio);

			dao.beginTransaction();

			for (MensagemCorreio mensagemCorreio2 : mensagemCorreios) {
				dao.insertObject(mensagemCorreio2);
			}

			dao.commitTransaction();

		} catch (Exception e) {
			dao.rollBackTransaction();
			e.printStackTrace();

			LogErro.gravarErroEmLog(Arquivo
					.getArquivoLogErroAtualizacaoTransacaoCartao()
					.getAbsolutePath(), e, "Ataulizacao Transacao Cartão",
					" Erro ao enviar correio interno para interferencia humana na transação "
							+ mensagemDetalhe
							+ " os usuarios destinatarios são "
							+ parametros.getParametroWeb()
									.getUsuarioAvisoTransacao());
		}
	}

	public static boolean verificarETratarInsucessoTransacao(
			GenericoDAOLog daoLog, GenericoDAO dao, IRetorno retornoConsulta,
			TransacaoPagamentoCartao pagamentoCartao, Parametros parametros) {

		boolean sucesso = verificaSucessoTransacao(retornoConsulta);

		if (!sucesso) {

			tratarInsucessoTransacao(daoLog, dao, retornoConsulta,
					pagamentoCartao, parametros);

			return false;
		}

		return sucesso;
	}

	public static boolean verificaSucessoTransacao(IRetorno retornoConsulta) {
		return retornoConsulta instanceof IRetornoTransacao
				&& retornoConsulta.isSucesso();
	}

	public static void tratarInsucessoTransacao(GenericoDAOLog daoLog,
			GenericoDAO dao, IRetorno retornoConsulta,
			TransacaoPagamentoCartao pagamentoCartao, Parametros parametros) {

		String infoAdcional = "";

		if (retornoConsulta instanceof IRetornoErro) {

			IRetornoErro erro = (IRetornoErro) retornoConsulta;

			infoAdcional = System.lineSeparator()
					+ " Informação adicional: Código da divergência: "
					+ erro.getCodigo() + " Mensagem da divergência: "
					+ erro.getMensagem();
		}

		if (pagamentoCartao != null) {

			gravarLogTransacao(
					daoLog,
					pagamentoCartao,
					"A manipulação da transação foi mal sucedida. Retorno – Código: "
							+ retornoConsulta.getCodigoRetorno()
							+ " - Mensagem: "
							+ retornoConsulta.getMensagemRetorno()
							+ infoAdcional);

		} else if (retornoConsulta instanceof IRetornoTransacao) {

			IRetornoTransacao transacao = (IRetornoTransacao) retornoConsulta;

			infoAdcional += " Id transacao: " + transacao.getIdTransacao()
					+ " ID local: " + transacao.getIdReferenciaTransacao();

		} else {
			infoAdcional += " " + retornoConsulta.getConteudoRetorno();
		}

		notificarResponsaveisProblemaTransacao(dao, pagamentoCartao,
				parametros, infoAdcional, TipoNotificacao.ERRO_TRANSACAO);

	}

	public static boolean gravarLogTransacao(GenericoDAOLog daoLog,
			TransacaoPagamentoCartao transacao, String observacao) {

		try {
			return LogTransacaoPagamentoCartao.gravarLog(daoLog, transacao,
					observacao);
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public static Propriedade getPropriedade() {
		if (propriedade == null) {
			propriedade = new Propriedade();
		}
		return propriedade;
	}
}
