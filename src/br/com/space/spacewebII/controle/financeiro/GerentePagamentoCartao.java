package br.com.space.spacewebII.controle.financeiro;

import static br.com.space.spacewebII.controle.financeiro.GerentePagamentoCartaoUtil.*;

import java.util.List;
import java.util.UUID;

import br.com.space.api.cartao.modelo.AdministradoraCartao;
import br.com.space.api.cartao.modelo.BandeiraCartao;
import br.com.space.api.cartao.modelo.StatusTransacao;
import br.com.space.api.cartao.modelo.TipoParcelamentoCartao;
import br.com.space.api.cartao.modelo.excecao.ConexaoServidorExcecao;
import br.com.space.api.cartao.modelo.excecao.FabricaNulaExcecao;
import br.com.space.api.cartao.modelo.requisicao.ICredencial;
import br.com.space.api.cartao.modelo.requisicao.Requisicao;
import br.com.space.api.cartao.modelo.resposta.IRetorno;
import br.com.space.api.cartao.modelo.resposta.IRetornoTransacao;
import br.com.space.api.core.propriedade.Propriedade;
import br.com.space.api.core.sistema.LogErro;
import br.com.space.api.core.sistema.Numeracao;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IPedido;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.dominio.financeiro.CredencialAdministradoraCartao;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamentoCondicaoPagamentoTaxa;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamentoFilial;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.venda.TransacaoPagamentoCartao;
import br.com.space.spacewebII.modelo.util.Arquivo;

public class GerentePagamentoCartao {

	public static void consultarEAtualizarTransacaoNotificada(
			GenericoDAOLog daoLog, GenericoDAO dao, String codigoNotificacao,
			AdministradoraCartao administradoraCartao, Parametros parametros)
			throws Exception {

		if (dao == null) {
			return;
		}

		ICredencial cred = CredencialAdministradoraCartao.recuperar(dao,
				administradoraCartao);

		Requisicao requisicao = obterRequisicaoCartao(cred);

		IRetorno retorno = requisicao
				.consultarTransacaoPorNotificacao(codigoNotificacao);

		if (verificarETratarInsucessoTransacao(daoLog, dao, retorno, null,
				parametros)) {

			IRetornoTransacao retornoTransacao = (IRetornoTransacao) retorno;

			TransacaoPagamentoCartao transacaoCartao = recuperarTransacaoBanco(
					dao, retornoTransacao);

			if (transacaoCartao != null) {

				gravarLogTransacao(
						daoLog,
						transacaoCartao,
						"Inicio de consulta de transação iniciada por notificação da Administradora de Cartão");

				boolean idTrocado = verificarETrocarIDTransacao(
						transacaoCartao, retornoTransacao, administradoraCartao);

				transacaoCartao.setDados(retornoTransacao);

				try {
					dao.beginTransaction();
					dao.update(transacaoCartao);
					dao.commitTransaction();

					gravarLogTransacao(
							daoLog,
							transacaoCartao,
							"Fim da consulta de transação iniciada por notificação da Administradora de Cartão. O id da transação foi atualizado"
									+ (idTrocado ? "Sim" : "Não"));
				} catch (Exception e) {
					dao.rollBackTransaction();
					throw e;
				}
			}
		}
	}

	private static boolean verificarETrocarIDTransacao(
			TransacaoPagamentoCartao transacaoCartao,
			IRetornoTransacao retornoTransacao,
			AdministradoraCartao administradoraCartao) {

		if (administradoraCartao.equals(AdministradoraCartao.Pagseguro)
				&& transacaoCartao.isPermiteAtualizarId()
				&& !transacaoCartao.getIdTransacao().equals(
						retornoTransacao.getIdTransacao())) {

			transacaoCartao.setIdTransacao(retornoTransacao.getIdTransacao());

			return true;
		}
		return false;
	}

	private static TransacaoPagamentoCartao recuperarTransacaoBanco(
			GenericoDAO dao, IRetornoTransacao retorno) {

		return TransacaoPagamentoCartao.recuperarUnico(dao,
				retorno.getIdReferenciaTransacao());
	}

	/**
	 * E a sobrecarga do
	 * {@link #tratarTransacoesPedido(GenericoDAO, Propriedade, boolean, String, int, int)
	 * metodo} a finalide dos parametros é a mesma, o unico que muda é o
	 * parametro pedido que repassa as informações para o metodo retornado.
	 * 
	 * @return {@link #tratarTransacoesPedido(GenericoDAO, Propriedade, boolean, String, int, int)}
	 * 
	 */
	public static synchronized List<TransacaoPagamentoCartao> tratarTransacoesPedido(
			GenericoDAO dao, GenericoDAOLog daoLog, Parametros parametros,
			Propriedade propriedade, boolean confirmarAutorizadas,
			boolean cancelarPorTimeout, IPedido pedido) throws SpaceExcecao,
			FabricaNulaExcecao {

		return tratarTransacoesPedido(dao, daoLog, parametros, propriedade,
				confirmarAutorizadas, cancelarPorTimeout,
				pedido.getSerieCodigo(), pedido.getNumero(),
				pedido.getFilialCodigo());
	}

	/**
	 * Este medoto busca as transação do pedido em parametro as consulta e caso
	 * as
	 * 
	 * @param dao
	 *            Uma instancia do dao para recuperar as transaçoes
	 * @param propriedade
	 *            Para criação de mensagens
	 * @param confirmarAutorizadas
	 *            Caso True se tiver alguma transação que estaja autorizada será
	 *            realizada a tentativa de confirma-la
	 * @param seriePedidoCodigo
	 *            A serie do pedido em questão
	 * @param numeroPedido
	 *            O numero do pedido para analise
	 * @param filialCodigo
	 *            A filial do pedio
	 * @return A lista de transaçoes atulizadas
	 * @throws SpaceExcecao
	 * @throws FabricaNulaExcecao
	 */
	public static synchronized List<TransacaoPagamentoCartao> tratarTransacoesPedido(
			GenericoDAO dao, GenericoDAOLog daoLog, Parametros parametros,
			Propriedade propriedade, boolean confirmarAutorizadas,
			boolean cancelarPorTimeout, String seriePedidoCodigo,
			int numeroPedido, int filialCodigo) throws SpaceExcecao,
			FabricaNulaExcecao {

		/*
		 * Verifica se existe um pedido criado. Evitar erro quando digitar a url
		 * diretamente pelo browser.
		 */

		List<TransacaoPagamentoCartao> trans = TransacaoPagamentoCartao
				.recuperar(dao, filialCodigo, seriePedidoCodigo, numeroPedido,
						null, true);

		if (!ListUtil.isValida(trans)) {
			throw new SpaceExcecao(
					propriedade
							.getMensagem("alerta.nenhumaTransacaoProcessamento"));
		}

		atualizarTransacoesCasoConfirmalasOuCanacelas(daoLog, dao, parametros,
				trans, confirmarAutorizadas, cancelarPorTimeout,
				parametros.getParametroWeb());

		return trans;
	}

	/**
	 * Percorre todas as transações e verifica o seu status junto à
	 * administradora. </br><strong> A lista em paramentro tem seus dados
	 * atulizado de acordo como o novo retorno obtido da consulta</strong>
	 * 
	 * @param transacoes
	 *            Todas as transações para a serem atulizadas com seu Estatus
	 *            junto a administradora
	 * 
	 * @param confirmarAutorizadas
	 *            Caso a transsacoa Esteja Autorizada é enviada um requisição
	 *            para confirmar liquidar a transação
	 * 
	 * @param cancelarPorTimeout
	 *            Caso a transação tenha seu timeout atingido é não estaja
	 *            cancelada ou confirmada será cancelada
	 * 
	 * @throws FabricaNulaExcecao
	 * @throws ConexaoServidorExcecao
	 */
	private static void atualizarTransacoesCasoConfirmalasOuCanacelas(
			GenericoDAOLog daoLog, GenericoDAO dao, Parametros parametros,
			List<TransacaoPagamentoCartao> transacoes,
			boolean confirmarAutorizadas, boolean cancelarPorTimeout,
			ParametroWeb parametroWeb) throws FabricaNulaExcecao,
			ConexaoServidorExcecao {

		if (!ListUtil.isValida(transacoes)) {
			return;
		}

		for (TransacaoPagamentoCartao transacaoPagamentoPedido : transacoes) {
			try {

				Requisicao req = obterRequisicaoCartao(transacaoPagamentoPedido
						.getCredencialAdministradoraCartao());

				IRetorno retornoConsulta = req
						.consultarTransacao(transacaoPagamentoPedido
								.getIdTransacao());

				if (verificarETratarInsucessoTransacao(daoLog, dao,
						retornoConsulta, transacaoPagamentoPedido, parametros)) {

					IRetornoTransacao retornoTransacaoConsulta = (IRetornoTransacao) retornoConsulta;

					/*
					 * Verifica se a transacao foi autorizada e se o parametro
					 * indica que é para confirmar. Se sim, envia a confirmação
					 * automaticamente para a administradora.
					 */
					if (confirmarAutorizadas
							&& (retornoTransacaoConsulta.getStatusTransacao() == StatusTransacao.Autorizada || (transacaoPagamentoPedido
									.getStatusTransacao() == StatusTransacao.Autorizada && retornoTransacaoConsulta
									.isReenviarConfirmacao()))) {

						IRetorno retConfimada = confirmarTransacao(daoLog, req,
								transacaoPagamentoPedido);

						if (verificarETratarInsucessoTransacao(daoLog, dao,
								retConfimada, transacaoPagamentoPedido,
								parametros)) {
							retornoTransacaoConsulta = (IRetornoTransacao) retConfimada;
						}
					}

					/*
					 * Sempre atualiza os dados da transação isto fará o metodo
					 * abaixo funcionar e outra se der um erro na rotina abaixo
					 * pelo menos a transação é atualizada com os dados da
					 * consulta e confirmação
					 */
					transacaoPagamentoPedido.setDados(retornoTransacaoConsulta);

					gravarLogTransacao(
							daoLog,
							transacaoPagamentoPedido,
							"Fim da consulta e atualização da transação. Processos executados: consulta e confirmação ou cancelamento da transação");
				}

				if (cancelarPorTimeout) {
					verificarECancelarPorTimeOut(transacaoPagamentoPedido,
							parametros, req, daoLog, dao);
				}

			} catch (Exception e) {
				LogErro.gravarErroEmLog(Arquivo
						.getArquivoLogErroGerentePagamentoCartao()
						.getAbsolutePath(), e, "Gerente pagamento Cartão",
						"Erro na verificação da transação: "
								+ transacaoPagamentoPedido.toString());
				e.printStackTrace();
			}
		}
	}

	private static void verificarECancelarPorTimeOut(
			TransacaoPagamentoCartao transacaoPagamentoPedido,
			Parametros parametros, Requisicao req, GenericoDAOLog daoLog,
			GenericoDAO dao) throws ConexaoServidorExcecao {
		
		boolean cancelarTransacao = transacaoPagamentoPedido
				.isCancelarPorTimeout()
				&& transacaoPagamentoPedido.isPermiteTentarCancelar(parametros
						.getParametroWeb()
						.getQuantidadeMaximaTentativasCancelamento());

		if (cancelarTransacao) {

			IRetorno retCancelada = cancelarTransacao(daoLog, req,
					transacaoPagamentoPedido);

			if (verificarETratarInsucessoTransacao(daoLog, dao, retCancelada,
					transacaoPagamentoPedido, parametros)) {

				transacaoPagamentoPedido
						.setDados((IRetornoTransacao) retCancelada);
			}
		}
	}

	public static IRetorno cancelarTransacao(GenericoDAOLog daoLog,
			TransacaoPagamentoCartao transacao) throws ConexaoServidorExcecao,
			FabricaNulaExcecao {

		Requisicao req = obterRequisicaoCartao(transacao
				.getCredencialAdministradoraCartao());

		return cancelarTransacao(daoLog, req, transacao);
	}

	/**
	 * Cancela as transação da lista que ainda não foram canceladas utilizado
	 * {@link #cancelarTransacao(TransacaoPagamentoCartao)}
	 * 
	 * @param transacoes
	 *            As transação a serem verificadas e canceladas
	 * @throws Exception
	 */
	public static void cancelarTransacoesNaoCanceladas(GenericoDAOLog daoLog,
			GenericoDAO dao, Parametros parametros,
			List<TransacaoPagamentoCartao> transacoes) throws Exception {

		for (TransacaoPagamentoCartao transacaoPagamentoCartao : transacoes) {

			try {

				if (!transacaoPagamentoCartao.getStatusTransacao().equals(
						StatusTransacao.Cancelada)) {

					IRetorno retorno = cancelarTransacao(daoLog,
							transacaoPagamentoCartao);

					if (verificarETratarInsucessoTransacao(daoLog, dao,
							retorno, transacaoPagamentoCartao, parametros)) {
						transacaoPagamentoCartao
								.setDados((IRetornoTransacao) retorno);
					}
				}

			} catch (Exception e) {
				e.printStackTrace();
				throw e;
			}
		}
	}

	/**
	 * Cancela a transação com o id respectivo utilizado a requisição informada
	 * 
	 * @param daoLog
	 * 
	 * @param req
	 *            A {@link Requisicao requisição} par realizar o cancelamento
	 * @param transacao
	 *            A transação a ser cancelada.
	 * @return
	 * @throws ConexaoServidorExcecao
	 */
	private static IRetorno cancelarTransacao(GenericoDAOLog daoLog,
			Requisicao req, TransacaoPagamentoCartao transacao)
			throws ConexaoServidorExcecao {

		gravarLogTransacao(daoLog, transacao,
				"Tentativa de CANCELAMENTO da transação");

		transacao.incrementarTentativasCancelamento();

		IRetorno retornoCancelamento = req.cancelarTransacao(transacao
				.getIdTransacao());

		return consultarRetornoTransacaoAposRequisicao(retornoCancelamento,
				transacao, req);
	}

	/**
	 * Consulta a trasacao em parametro caso o retornoUltimaRequisicao tenho
	 * obtido sucesso, caso contrario retorna a propria transação.
	 * 
	 * @param retornoUltimaRequisicao
	 * @param transacao
	 * @param req
	 * @return
	 * @throws ConexaoServidorExcecao
	 */
	private static IRetorno consultarRetornoTransacaoAposRequisicao(
			IRetorno retornoUltimaRequisicao,
			TransacaoPagamentoCartao transacao, Requisicao req)
			throws ConexaoServidorExcecao {

		if (verificaSucessoTransacao(retornoUltimaRequisicao)) {

			// if (req.getCredencial().getAdministradoraCartao() ==
			// AdministradoraCartao.Rececard) {

			IRetorno retornoConsultaAposRequisicao = req
					.consultarTransacao(transacao.getIdTransacao());

			if (retornoConsultaAposRequisicao instanceof IRetornoTransacao) {
				return (IRetornoTransacao) retornoConsultaAposRequisicao;
			}
		}

		return retornoUltimaRequisicao;
	}

	/**
	 * Confirma a transação com o id respectivo utilizado a requisição informada
	 * 
	 * @param daoLog
	 * 
	 * @param req
	 *            A {@link Requisicao requisição} par realizar a consulta
	 * 
	 * @param transacao
	 *            A transação a ser consultada.
	 * @return O retorno obtido atravez do processo de confirmação
	 * 
	 * @throws ConexaoServidorExcecao
	 */
	private static IRetorno confirmarTransacao(GenericoDAOLog daoLog,
			Requisicao req, TransacaoPagamentoCartao transacao)
			throws ConexaoServidorExcecao {

		gravarLogTransacao(daoLog, transacao,
				"Tentativa de CONFIRMAÇÃO da transação");

		IRetorno retornoConfirmacao = req.confirmarTransacao(
				transacao.getIdTransacao(), transacao.getValor(),
				transacao.geCartaoTipo());

		return consultarRetornoTransacaoAposRequisicao(retornoConfirmacao,
				transacao, req);

	}

	/**
	 * Retorna uma requição da administrador representada pela
	 * {@link ICredencial credencial}
	 * 
	 * 
	 * @param obterNova
	 * @param cred
	 * @return
	 * @throws FabricaNulaExcecao
	 */
	private static Requisicao obterRequisicaoCartao(ICredencial cred)
			throws FabricaNulaExcecao {

		return new Requisicao(cred.getAmbienteTrabalhoChave(), cred);
	}

	/**
	 * @param pedido
	 * @param itensPedido
	 * @param numeroParcelas
	 * @param cpgAdm
	 * @param fpg
	 * @param urlRetorno
	 * @return
	 * @throws ConexaoServidorExcecao
	 * @throws FabricaNulaExcecao
	 */
	public static IRetorno criarTransacaoCartaoCredito(IPedidoWeb pedido,
			String referenciaLocal, List<? extends IItemPedidoWeb> itensPedido,
			int numeroParcelas, FormaPagamentoCondicaoPagamentoTaxa cpgAdm,
			FormaPagamento fpg, String urlRetorno)
			throws ConexaoServidorExcecao, FabricaNulaExcecao {

		/*
		 * Verifica se o parcelamento é a vista, debito ou parcelado. Se for
		 * debito o numero de parcelas deve ser 1 obrigatoriamente. Se o numero
		 * de parcelas for maior que 1 o tipo de parcelamento deve ser
		 * Administrador ou Lojista.
		 */

		TipoParcelamentoCartao tipoParcelamentoCartao = GerentePagamentoCartao
				.getTipoParcelamento(cpgAdm, fpg, numeroParcelas);

		if (tipoParcelamentoCartao == TipoParcelamentoCartao.CreditoAVista
				|| tipoParcelamentoCartao == TipoParcelamentoCartao.Debito) {
			numeroParcelas = 1;
		}

		/*
		 * Envia uma requisição para a administradora.
		 */
		Requisicao requisicao = obterRequisicaoCartao(fpg
				.getFormaPagamentoFilial().getCredencialAdministradoraCartao());

		IRetorno retorno = requisicao.criarTransacao(referenciaLocal,
				pedido.getDataCadastro(), pedido.getValor(), "",
				BandeiraCartao.parseBandeira(fpg.getBandeira()),
				tipoParcelamentoCartao, numeroParcelas, urlRetorno,
				itensPedido, pedido.getFrete());

		return retorno;
	}

	public static TipoParcelamentoCartao getTipoParcelamento(
			FormaPagamentoCondicaoPagamentoTaxa cpgAdm, FormaPagamento fpg,
			int numeroParcelas) {

		TipoParcelamentoCartao tipoParcelamentoCartao = TipoParcelamentoCartao.CreditoAVista;

		if (fpg.isCartaoDebito())
			tipoParcelamentoCartao = TipoParcelamentoCartao.Debito;
		else if (numeroParcelas > 0) {

			if (cpgAdm.getFlagTipoParcelamento() == 2)
				tipoParcelamentoCartao = TipoParcelamentoCartao.ParceladoAdministradora;
			else if (cpgAdm.getFlagTipoParcelamento() == 3)
				tipoParcelamentoCartao = TipoParcelamentoCartao.ParceladoLoja;
		}

		return tipoParcelamentoCartao;
	}

	public static boolean isIntegraCartaoAdministradora(FormaPagamento fpg,
			FormaPagamentoFilial fpgFilial) {

		return fpg != null && fpg.isCartao() && fpgFilial != null
				&& fpgFilial.getFlagIntegraPagamentoAdministradora() == 1
				&& fpgFilial.getCredencialAdministradoraCartao() != null;
	}

	/**
	 * Monta o número do pedido a ser enviado para a administradora. <br/>
	 * Contém a série "s" e o número do pedido "n". </br>
	 * 
	 * Utiliza o metodo {@link #gerarCodigoReferencia(IPedido, int) } informando
	 * no parametro numeroTransacao zero
	 * 
	 * @see #gerarCodigoReferencia(IPedido, int)
	 * @see #extrairDadosCodigoReferencia(String)
	 * 
	 * @param pedido
	 *            O pedido para ser gerado o codigo
	 * @return Uma String no formato sSeriePedidonNumeroPedido
	 */
	public static String gerarCodigoReferencia(IPedido pedido,
			AdministradoraCartao administradoraCartao) {

		String idUnico = UUID.randomUUID().toString();

		if (StringUtil.isValida(idUnico)) {
			idUnico = idUnico.replaceAll("-", "").trim().toUpperCase();
		} else {
			idUnico = Numeracao.ObterNumeracaoUnica().toString();
		}

		String codigo = gerarCodigoReferencia(pedido);

		int tamanhoMaximo = administradoraCartao.getTamanhoMaximoIdReferencia() > 100 ? 100
				: administradoraCartao.getTamanhoMaximoIdReferencia();

		if (codigo.length() < tamanhoMaximo) {

			int tamanhoComplemento = Math.abs(tamanhoMaximo
					- (codigo + "t").length());

			String complemento = StringUtil.subStringDireitaEsquerda(idUnico,
					tamanhoComplemento);

			codigo += "t" + complemento;
		}

		return codigo;
	}

	/**
	 * Monta o número do pedido a ser enviado para a administradora. <br/>
	 * Contém a série "s", número do pedido "n e o codigo da transação.
	 * 
	 * 
	 * 
	 * @see #gerarCodigoReferencia(IPedido, AdministradoraCartao)
	 * @see #extrairDadosCodigoReferencia(String)
	 * @param pedido
	 *            O pedido para ser gerado o codigo
	 * 
	 * @return Uma String no formato sSeriePedidonNumeroPedido
	 */
	private static String gerarCodigoReferencia(IPedido pedido) {

		return ("s" + pedido.getSerieCodigo().trim() + "n" + pedido.getNumero())
				.trim();
	}

	/**
	 * Decofica o codigo de referencia em parametro
	 * 
	 * @param codigoReferencia
	 *            A representação usavel do codigo de referencia
	 * 
	 * @return Os dados do odigo de referencia no tipo {@link CodigoReferencia}
	 */
	public static CodigoReferencia extrairDadosCodigoReferencia(
			String codigoReferencia) {
		return new CodigoReferencia(codigoReferencia);
	}

	public static class CodigoReferencia {
		private String seriePedido = null;
		private int numeroPedido = 0;
		private String transacaoIdentificacao = null;

		public CodigoReferencia(String seriePedido, int numeroPedido,
				String transacaoIdentificacao) {

			this.seriePedido = seriePedido;
			this.numeroPedido = numeroPedido;
			this.transacaoIdentificacao = transacaoIdentificacao;
		}

		public CodigoReferencia(String codigoReferencia) {
			decodificarCodigoReferencia(codigoReferencia);
		}

		public void decodificarCodigoReferencia(String codigoReferencia) {
			String[] s = codigoReferencia.split("n");

			this.seriePedido = s[0].replaceFirst("s", "").trim();

			String[] ss = s[1].split("t");

			this.numeroPedido = Integer.parseInt(ss[0].trim());

			if (ss.length > 1) {
				this.transacaoIdentificacao = ss[1].trim();
			}
		}

		public String getSeriePedido() {
			return seriePedido;
		}

		public int getNumeroPedido() {
			return numeroPedido;
		}

		public String getTransacaoIdentificacao() {
			return transacaoIdentificacao;
		}

		public void setTransacaoIdentificacao(String transacaoIdentificacao) {
			this.transacaoIdentificacao = transacaoIdentificacao;
		}
	}
}