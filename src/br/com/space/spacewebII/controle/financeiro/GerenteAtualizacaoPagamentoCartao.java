package br.com.space.spacewebII.controle.financeiro;

import static br.com.space.spacewebII.controle.financeiro.GerentePagamentoCartaoUtil.*;

import java.util.List;

import br.com.space.api.cartao.modelo.StatusTransacao;
import br.com.space.api.core.sistema.LogErro;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.aplicacao.GerenteEmail;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.modelo.aplicacao.Configuracao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.dominio.endereco.Enderecos;
import br.com.space.spacewebII.modelo.dominio.sistema.MensagemCorreio;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.venda.LogPedido;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;
import br.com.space.spacewebII.modelo.dominio.venda.StatusPedido;
import br.com.space.spacewebII.modelo.dominio.venda.TransacaoPagamentoCartao;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.Arquivo;
import br.com.space.spacewebII.modelo.util.PedidoDadosEmail;
import br.com.space.spacewebII.modelo.widget.ItemPedidoDescricao;

public class GerenteAtualizacaoPagamentoCartao {

	public static void main(String[] args) {
		atualizarPedidos();
	}

	private static GenericoDAO dao = null;
	private static GenericoDAOLog daoLog = null;

	/**
	 * Atualiza as transação e o pedido da filial definida na configuração.
	 * Neste metodo realiza todas as açoes para efetivar ou cancelar o pedido
	 * pago com cartão de credito.
	 * 
	 * @see #atualizarPedidos(GenericoDAO, GenericoDAOLog)
	 *
	 */
	public static void atualizarPedidos() {
		atualizarPedidos(getDao(), getDaoLog());
	}

	/**
	 * Atualiza as transação e o pedido da filial definida na configuração.
	 * Neste metodo realiza todas as açoes para efetivar ou cancelar o pedido
	 * pago com cartão de credito.
	 * 
	 * @param dao
	 *            O dao principal para recuperar e atulizar os dados
	 * @param daoLog
	 *            O dao de representação do log para inserção do log de
	 *            acompanhamento
	 * 
	 * @see #atualizarPedidos()
	 */
	public static void atualizarPedidos(GenericoDAO dao, GenericoDAOLog daoLog) {

		int filialCodigo = Configuracao.getAtributosConfiguracao()
				.getFilialPadraoCodigo();

		List<Pedido> pedidos = Pedido.recuperarPedidosAguradandoPagamentoWeb(
				dao, filialCodigo);

		gravarLogVerificacao(pedidos);

		if (!ListUtil.isValida(pedidos)) {
			return;
		}

		Parametros parametros = new Parametros(dao, filialCodigo);

		for (Pedido pedido : pedidos) {
			try {

				List<TransacaoPagamentoCartao> transacoes = GerentePagamentoCartao
						.tratarTransacoesPedido(dao, daoLog, parametros,
								getPropriedade(), true, true, pedido);

				if (!ListUtil.isValida(transacoes)) {
					continue;
				}

				boolean foiAtualizado = tratarPedidoETransacoes(dao, pedido,
						transacoes, parametros);

				if (foiAtualizado) {
					enviarEmailCliente(dao, pedido, parametros);
				}

			} catch (Exception e) {
				e.printStackTrace();

				LogErro.gravarErroEmLog(Arquivo
						.getArquivoLogErroAtualizacaoTransacaoCartao()
						.getAbsolutePath(), e, "Ataulizacao Transacao Cartão",
						"Erro na verificação das transacoes para o pedido "
								+ pedido.getSerieENumeroPedido("/"));
			}
		}
	}

	/**
	 * Envia o email para o cliento do pedido informado-o sobre a situação atual
	 * 
	 * @param dao
	 *            O Dao par ser recuperado os dados
	 * @param pedido
	 *            O pedido para o emial
	 * @param parametros
	 *            Parametros gerais do sistema
	 */
	private static void enviarEmailCliente(GenericoDAO dao, Pedido pedido,
			Parametros parametros) {

		try {

			PedidoDadosEmail pedidoDadosEmail = PedidoDadosEmail
					.recuperarDadosEmailPedido(dao, pedido);

			if (pedidoDadosEmail == null
					|| !StringUtil.isValida(pedidoDadosEmail.getClienteEmail())) {
				return;
			}

			// Vou ter que fazer uma interface que é o descritivo do item de
			// pedido similar a caixa de produto visualizavel
			List<ItemPedidoDescricao> itens = ItemPedidoDescricao.recuperar(
					dao, pedido);

			Enderecos enderecos = Enderecos
					.recuperarUnico(dao, pedido.getPessoaCodigo(),
							pedido.getEnderecoEntregaCodigo());

			String mensagemComplementoPedido = getPropriedade().getMensagem(
					"mensagem.foiatualizado",
					pedido.getDescricaoLog(getPropriedade()));

			String conteudo = GerenteEmail.gerarConteudoEmailPedido(
					getPropriedade(), pedido, itens,
					pedidoDadosEmail.getPessoaRazao(),
					mensagemComplementoPedido,
					pedidoDadosEmail.getFormaPagamentoDescricao(),
					pedidoDadosEmail.getCondicaoPagamentoDescricao(),
					enderecos.toString(), parametros, dao);

			String assunto = getPropriedade().getMensagem(
					"texto.atualizacaoPedido",
					pedido.getSerieENumeroPedido("/"));

			GerenteEmail.enviarEmail(parametros.getParametro1(),
					pedidoDadosEmail.getClienteEmail(), assunto, conteudo);

		} catch (Exception e) {
			e.printStackTrace();

			LogErro.gravarErroEmLog(Arquivo
					.getArquivoLogErroAtualizacaoTransacaoCartao()
					.getAbsolutePath(), e, "Ataulizacao Transacao Cartão",
					" Erro no envio de email com a notificação de mudança de staus do pedido "
							+ pedido.getSerieENumeroPedido("/"));
		}
	}

	/**
	 * Este metodo é responsavel por:
	 * <ol>
	 * <li>
	 * Gerar o {@link RelatorioTransacao relatorios das transações} e o
	 * {@link StatusPedido status pedido} são classes auxiliares cuja a
	 * responsabilidade é interpretar o status de cada transação e fornecer qual
	 * é o fututro status para o pedido.</li>
	 * <li>Se o status do pedido indicar o
	 * {@link #cancelarTransacoesNaoCanceladas(List) cancelamento das trasação}
	 * então o realiza</li>
	 * <li>Atualiza as transação no banco de dados usando o dao em parametro
	 * atravez do metodo {@link #atualizarTransacoes(GenericoDAO, List)
	 * atualizar transacoes}</li>
	 * <li>Chama o metodo
	 * {@link #verificarCasoExecutarPlanoContigencia(GenericoDAO, List, Parametros)
	 * verificar contigencia}</li>
	 * <li>Atualiza o Status do pedido atravez do metodo {@link
	 * #atualizarStatusPedido(GenericoDAO, Pedido, StatusPedidoGerador atualizar
	 * Status Pedido)}</li>
	 * </ol>
	 *
	 * 
	 * @param dao
	 *            Para atualização dos dados
	 * @param pedido
	 *            O pedido para manipulação
	 * @param transacoes
	 *            As transaçãoe do pedido ja atualizaddas na adm e confirmadas
	 *            ou canceladas
	 * @param parametros
	 *            Parametros gerais do sistema
	 * @return TRUE Se o status do pedido sofreu alteração, FALSE caso contrario
	 * @throws Exception
	 *             Erro generico em caso de alguma falha
	 */
	private static boolean tratarPedidoETransacoes(GenericoDAO dao,
			Pedido pedido, List<TransacaoPagamentoCartao> transacoes,
			Parametros parametros) throws Exception {

		RelatorioTransacao relatorioTransacao = new RelatorioTransacao(
				transacoes);

		StatusPedidoGerador statusPedidoGerador = new StatusPedidoGerador(
				relatorioTransacao, pedido);

		if (statusPedidoGerador.isCancelarOutrasTransacoes()) {
			GerentePagamentoCartao.cancelarTransacoesNaoCanceladas(getDaoLog(),
					getDao(), parametros, transacoes);
		}

		atualizarTransacoes(dao, transacoes);

		verificarCasoExecutarPlanoContigencia(dao, transacoes, parametros);

		boolean foiAtualizado = atualizarStatusPedido(dao, pedido,
				statusPedidoGerador);

		return foiAtualizado;
	}

	/**
	 * Percorre todas as transação em busca de uma transação que não seja mais
	 * {@link TransacaoPagamentoCartao#isPermiteTentarCancelar(int) permitida
	 * seu cancelamento} encontrando,
	 * {@link #notificarResponsaveisTentativasCancelamento(GenericoDAO, TransacaoPagamentoCartao, Parametros)
	 * notifica os usuarios responsaveis} que a transação não pode ser mais
	 * cancelada.
	 * 
	 * @param dao
	 *            Gravação de mensagens
	 * @param transacoes
	 *            As transações a serem verificadas
	 * @param parametros
	 *            Gerais
	 */
	private static void verificarCasoExecutarPlanoContigencia(GenericoDAO dao,
			List<TransacaoPagamentoCartao> transacoes, Parametros parametros) {

		for (TransacaoPagamentoCartao transacao : transacoes) {

			if (!transacao.isPermiteTentarCancelar(parametros.getParametroWeb()
					.getQuantidadeMaximaTentativasCancelamento())) {

				notificarResponsaveisProblemaTransacao(dao, transacao,
						parametros, null, TipoNotificacao.CANCELAMENTO);
			}
		}
	}

	/**
	 * Atualiza o status do pedido mais somente se tiver sofrido alteração. A
	 * decisação fica por conta do metodo
	 * {@link StatusPedidoGerador#isAtualizarStatusPedido(Pedido)} </br></br>
	 * Caso o status deva ser modificado e verificado o status se é Cancelado
	 * executa {@link GerentePedido#cancelarPedido(GenericoDAO, Pedido, int) o
	 * cancelamento do gerente de pedido} caso Confirmado executa
	 * {@link GerentePedido#confirmarPedido(GenericoDAO, Pedido) confirmação de
	 * pedido do gerente de pedido} se não for simplesmente atualiza o status do
	 * objeto.</br> Apos o pedido é atualizado na base de dados
	 * 
	 * 
	 * 
	 * @param dao
	 *            Para atualiza o pedido
	 * @param pedido
	 *            Pedido a ser verificado e atualizado
	 * @param statusPedidoGerador
	 *            O novo status para o pedido
	 * @return TRUE caso o pedido tenha sido modificado, caso contrario FALSE
	 * @throws Exception
	 *             Erro diversos
	 */
	private static boolean atualizarStatusPedido(GenericoDAO dao,
			Pedido pedido, StatusPedidoGerador statusPedidoGerador)
			throws Exception {

		boolean atualizarStatusPedido = statusPedidoGerador
				.isAtualizarStatusPedido(pedido);

		if (atualizarStatusPedido) {

			try {

				dao.beginTransaction();

				pedido.setStatusPagamentoCartao(statusPedidoGerador
						.getStatusPagamentoCodigo());

				String observacaoCancelamento = null;

				if (statusPedidoGerador.getStatusPedidoCodigo() == StatusPedido.STATUS_CANCELADO) {

					GerentePedido.cancelarPedido(dao, pedido,
							statusPedidoGerador.getStatusPedidoCodigo());

					observacaoCancelamento = getPropriedade().getMensagem(
							"texto.logPedido.obscancelamento.canceladocartao");

				} else if (statusPedidoGerador.getStatusPedidoCodigo() == StatusPedido.STATUS_NAO_SEPARADO) {

					GerentePedido.confirmarPedido(dao, pedido);

				} else {

					pedido.setStatusPedidoCodigo(statusPedidoGerador
							.getStatusPedidoCodigo());

					dao.update(pedido);
				}

				GerentePedido.inserirLogPedido(dao, pedido, 0,
						LogPedido.USUARIO_ATUALIZACAO_CARTAO,
						pedido.getDescricaoLog(getPropriedade()), null,
						observacaoCancelamento);

				dao.commitTransaction();
			} catch (Exception e) {

				e.printStackTrace();
				dao.rollBackTransaction();

				throw e;
			}
		}

		return atualizarStatusPedido;
	}

	/**
	 * Atualiza a lista de transação na base de dados
	 * 
	 * @param dao
	 *            Para atualização das transações
	 * @param transacoes
	 *            A lista de transaçõs a serem atualizadas
	 * @throws Exception
	 *             Possiveis erros
	 */
	private static void atualizarTransacoes(GenericoDAO dao,
			List<TransacaoPagamentoCartao> transacoes) throws Exception {

		try {
			dao.beginTransaction();

			for (TransacaoPagamentoCartao transacaoPagamentoCartao : transacoes) {
				dao.update(transacaoPagamentoCartao);
			}

			dao.commitTransaction();
		} catch (Exception e) {
			dao.rollBackTransaction();
			throw e;
		}
	}

	public static GenericoDAO getDao() {
		if (dao == null) {
			dao = new GenericoDAO();
		}

		return dao;
	}

	public static GenericoDAOLog getDaoLog() {
		if (daoLog == null) {
			daoLog = new GenericoDAOLog();
		}

		return daoLog;
	}

	/**
	 * Agrupa as transaçoes em dados unicos
	 * 
	 * @author Renato
	 *
	 */
	public static class RelatorioTransacao {
		// Aberta, Autorizada, NaoAutorizada, Confirmada, Cancelada;

		int quantidadeAbertas = 0;
		double valorTotalAbertas = 0;

		int quantidadeAutorizadas = 0;
		double valorTotalAutorizadas = 0;

		int quantidadeNaoAutorizadas = 0;
		double valorTotalNaoAutorizadas = 0;

		int quantidadeConfirmadas = 0;
		double valorTotalConfirmadas = 0;

		int quantidadeCanceladas = 0;
		double valorTotalCanceladas = 0;

		public RelatorioTransacao(List<TransacaoPagamentoCartao> transacoes) {
			for (TransacaoPagamentoCartao transacaoPagamentoCartao : transacoes) {
				gerar(transacaoPagamentoCartao);
			}
		}

		public void gerar(TransacaoPagamentoCartao trans) {

			switch (trans.getStatusTransacao()) {

			case Aberta:

				quantidadeAbertas++;
				valorTotalAbertas += trans.getValor();
				break;
			case Autorizada:

				quantidadeAutorizadas++;
				valorTotalAutorizadas += trans.getValor();
				break;

			case NaoAutorizada:

				quantidadeNaoAutorizadas++;
				valorTotalNaoAutorizadas += trans.getValor();
				break;

			case Confirmada:

				quantidadeConfirmadas++;
				valorTotalConfirmadas += trans.getValor();
				break;

			case Cancelada:

				quantidadeCanceladas++;
				valorTotalCanceladas += trans.getValor();
				break;
			}
		}

		public StatusTransacao getStatusTransacaoUnico(Pedido pedido) {

			boolean valorDasPositivasIqualPedido = getValorTotalPositivas() == pedido
					.getValor();

			if (quantidadeConfirmadas > 0 && valorDasPositivasIqualPedido) {

				return StatusTransacao.Confirmada;

			} else if (quantidadeAutorizadas > 0
					&& valorDasPositivasIqualPedido) {

				return StatusTransacao.Autorizada;

			} else if (quantidadeAbertas > 0 && valorDasPositivasIqualPedido) {

				return StatusTransacao.Aberta;

			} else if (quantidadeNaoAutorizadas > 0
					&& !valorDasPositivasIqualPedido) {

				return StatusTransacao.NaoAutorizada;
			}
			return StatusTransacao.Cancelada;
		}

		public int getQuantidadeAbertas() {
			return quantidadeAbertas;
		}

		public double getValorTotalAbertas() {
			return valorTotalAbertas;
		}

		public int getQuantidadeAutorizadas() {
			return quantidadeAutorizadas;
		}

		public double getValorTotalAutorizadas() {
			return valorTotalAutorizadas;
		}

		public int getQuantidadeNaoAutorizadas() {
			return quantidadeNaoAutorizadas;
		}

		public double getValorTotalNaoAutorizadas() {
			return valorTotalNaoAutorizadas;
		}

		public int getQuantidadeConfirmadas() {
			return quantidadeConfirmadas;
		}

		public double getValorTotalConfirmadas() {
			return valorTotalConfirmadas;
		}

		public int getQuantidadeCanceladas() {
			return quantidadeCanceladas;
		}

		public double getValorTotalCanceladas() {
			return valorTotalCanceladas;
		}

		public boolean isExistePositivas() {
			return (quantidadeAbertas + quantidadeAutorizadas + quantidadeConfirmadas) > 0;
		}

		public double getValorTotalPositivas() {
			return valorTotalAbertas + valorTotalAutorizadas
					+ valorTotalConfirmadas;
		}

		public double getValorTotalNegativas() {
			return valorTotalCanceladas + valorTotalNaoAutorizadas;
		}
	}

	/**
	 * Com base no {@link RelatorioTransacao} gerar o status do pedido e de
	 * pagamento
	 * 
	 * @author Renato
	 *
	 */
	public static class StatusPedidoGerador {

		private int statusPedidoCodigo = 0;
		private int statusPagamentoCodigo = 0;
		private boolean cancelarOutrasTransacoes = false;

		StatusPedidoGerador(RelatorioTransacao relatorioTransacao, Pedido pedido) {
			gerarStatus(relatorioTransacao, pedido);
		}

		public void gerarStatus(RelatorioTransacao relatorioTransacao,
				Pedido pedido) {

			switch (relatorioTransacao.getStatusTransacaoUnico(pedido)) {
			case Confirmada:
				statusPedidoCodigo = StatusPedido.STATUS_NAO_SEPARADO;
				statusPagamentoCodigo = Pedido.STATUS_PAGAMENTO_CARTAO_CONFIRMADO;
				break;

			case Autorizada:
			case Aberta:
				statusPedidoCodigo = StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB;
				statusPagamentoCodigo = Pedido.STATUS_PAGAMENTO_CARTAO_AGUARDANDO_PAGAMENTO;
				break;
			case Cancelada:
			case NaoAutorizada:

				if (relatorioTransacao.isExistePositivas()) {
					statusPedidoCodigo = StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB;
					statusPagamentoCodigo = Pedido.STATUS_PAGAMENTO_CARTAO_AGUARDANDO_PAGAMENTO;
					cancelarOutrasTransacoes = true;
				} else {
					statusPedidoCodigo = StatusPedido.STATUS_CANCELADO;
					statusPagamentoCodigo = Pedido.STATUS_PAGAMENTO_CARTAO_SEM_PAGAMENTO;
				}
				break;

			default:
				break;
			}
		}

		public int getStatusPedidoCodigo() {
			return statusPedidoCodigo;
		}

		public int getStatusPagamentoCodigo() {
			return statusPagamentoCodigo;
		}

		/**
		 * @return TRUE Caso exista um transação canceladas e outras que estão
		 *         positivas, caso contrario FALSE
		 */
		public boolean isCancelarOutrasTransacoes() {
			return cancelarOutrasTransacoes;
		}

		/**
		 * 
		 * @param pedido
		 *            O pedido cujo o status será validado
		 * @return TRUE caso o status do pedido ou status do pagamento estaja
		 *         diferente do {@link #getStatusPedidoCodigo()} e
		 *         {@link #getStatusPagamentoCodigo()}
		 */
		public boolean isAtualizarStatusPedido(Pedido pedido) {
			return pedido.getStatusPedidoCodigo() != statusPedidoCodigo
					|| pedido.getStatusPagamentoCartao() != statusPagamentoCodigo;
		}
	}
}
