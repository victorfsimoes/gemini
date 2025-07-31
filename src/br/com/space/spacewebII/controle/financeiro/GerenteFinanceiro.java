package br.com.space.spacewebII.controle.financeiro;

import java.util.ArrayList;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.sistema.Matematica;
import br.com.space.api.core.sistema.Numeracao;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.spacewebII.controle.Sistema;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.financeiro.ClassificaDocumento;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FinanceiroGerencial;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaCondicao;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamentoCondicaoPagamentoTaxa;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamentoFilial;
import br.com.space.spacewebII.modelo.dominio.financeiro.LoteLancamentoFinanceiro;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;
import br.com.space.spacewebII.modelo.dominio.venda.TransacaoPagamentoCartao;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

public class GerenteFinanceiro {

	/**
	 * @throws Exception
	 * 
	 */
	public static List<FinanceiroGerencial> gerarParcelasFinanceiroPedido(
			GenericoDAO dao, int sessaoCodigo, Pedido pedido,
			FormaCondicao formaCondicao, Propriedade propriedade)
			throws Exception {

		List<FinanceiroGerencial> listaFinanceiro = null;

		FormaPagamento fpgPag = FormaPagamento.recuperarUnico(dao,
				formaCondicao.formaPagamentoCodigo);

		FormaPagamentoFilial fpgPagFilial = FormaPagamentoFilial
				.recuperarUnico(dao, formaCondicao.formaPagamentoCodigo,
						pedido.getFilialCodigo());

		CondicaoPagamento cpgPag = CondicaoPagamento.recuperarUnico(dao,
				formaCondicao.condicaoPagamentoCodigo);

		FormaPagamentoCondicaoPagamentoTaxa formaCondicaoTaxa = FormaPagamentoCondicaoPagamentoTaxa
				.recuperarUnico(dao, formaCondicao.formaPagamentoCodigo,
						formaCondicao.condicaoPagamentoCodigo);

		if (fpgPag != null && fpgPagFilial != null && cpgPag != null) {
			listaFinanceiro = new ArrayList<FinanceiroGerencial>();

			int numeroParacelas = cpgPag.getNumeroParcelas();
			int intervaloParcelas = cpgPag.getIntervaloEntreParcelas();
			int diasEntrada = cpgPag.getDiasEntrada();

			/*
			 * Existe um parametro para determinar qual é a data de referencia
			 * para parcelamento. No caso de cartão de crédito a data de
			 * referencia deve ser sempre a data da transação (emissao ou data
			 * do sistema).
			 */
			Date dataEmissao = formaCondicao.data;

			/*
			 * Cria uma instancia do calendar para calcular a data de vencimento
			 * de acordo com o numero de dias de entradae o intervalo entre as
			 * parcelas.
			 */
			GregorianCalendar gcVencimento = new GregorianCalendar();
			gcVencimento.setTime(dataEmissao);
			gcVencimento.add(GregorianCalendar.DAY_OF_MONTH, diasEntrada);

			for (int parcela = 0; parcela < numeroParacelas; parcela++) {
				/*
				 * Acrescenta ao vencimento o intervalo entre as parcelas. No
				 * caso da segunda parcela em diante o vencimento é acumulado.
				 */
				int diasVencimento = (parcela == 0 ? 0 : intervaloParcelas)
						+ pedido.getPrazoEspecial();
				gcVencimento
						.add(GregorianCalendar.DAY_OF_MONTH, diasVencimento);

				/*
				 * Calcula o valor de cada parcela. Se houver alguma diferenca
				 * de arredondamento das parcelas, essa diferenca é ajustada na
				 * primeira parcela.
				 */
				double valorParcela = Conversao.arredondar(formaCondicao.valor
						/ numeroParacelas, 2);

				double diferencaParcelas = Conversao.arredondar(
						formaCondicao.valor - (valorParcela * numeroParacelas),
						2);

				if (diferencaParcelas != 0 && parcela == 0) {
					valorParcela = valorParcela + diferencaParcelas;
				}

				double valorDescontoFormaCondTaxa = 0;
				if (formaCondicaoTaxa != null
						&& formaCondicaoTaxa.getTaxa() > 0) {
					valorDescontoFormaCondTaxa = Conversao
							.arredondar(
									((formaCondicaoTaxa.getTaxa() * valorParcela) / 100),
									2);
				}

				Date dataVencimento = gcVencimento.getTime();

				// TODO Gerar o número de acordo com o parametro.
				// Sequencial/Dependente.
				String numeroDocumento = Conversao.formatarZeroEsquerda(
						pedido.getNumero(), 9)
						+ "-" + (parcela + 1) + "/" + numeroParacelas;

				// TODO Buscar informacoes do banco/conta corrente/centro de
				// custo.
				double percentualComissao = Conversao.arredondar(
						pedido.getValorComissao() / pedido.getValor() * 100, 2);
				int enderecoCobrancaCodigo = pedido.getEnderecoCobrancaCodigo();
				int centroCustoCodigo = 0;
				int contaCorrenteCodigo = 0;
				String bancoCodigo = "";
				String historicoLancamento = formaCondicao.observacao;

				FinanceiroGerencial financeiro = new FinanceiroGerencial(
						pedido.getFilialCodigo(), "R", fpgPag.getCodigo(),
						numeroDocumento, dataEmissao, dataVencimento, "A",
						valorParcela, 0.0, valorDescontoFormaCondTaxa, 0.0,
						pedido.getVendedorCodigo(), pedido.getPessoaCodigo(),
						bancoCodigo, centroCustoCodigo,
						fpgPag.getPlanoContasReceitaCodigo(),
						contaCorrenteCodigo, fpgPag.getPortadorCodigo(),
						enderecoCobrancaCodigo, percentualComissao,
						historicoLancamento, formaCondicao.transacaoSequencial,
						sessaoCodigo);

				listaFinanceiro.add(financeiro);
			}

		} else {

			throw new SpaceExcecao(
					propriedade.getMensagem("alerta.formaCondicao.invalida"));
		}

		return listaFinanceiro;
	}

	/**
	 * 
	 * @param dao
	 * @param pedido
	 * @throws Exception
	 */
	public static void gerarFinanceiroPedido(GenericoDAO dao, int sessaoCodigo,
			Pedido pedido, Propriedade propriedade) throws Exception {

		/*
		 * Verifica se o pedido já possui algum lote de financeiro.
		 */
		if (pedido.getLoteLancamentoFinanceiro() > 0) {
			throw new SpaceExcecao(
					propriedade.getMensagem("alerta.pedido.financeirolancado"));
		}

		FormaPagamento fpgPedido = FormaPagamento.recuperarUnico(dao,
				pedido.getFormaPagamentoCodigo());

		/*
		 * FormaPagamentoFilial fpgPedidoFilial = FormaPagamentoFilial
		 * .recuperarUnico(dao, pedido.getFormaPagamentoCodigo(),
		 * pedido.getFilialCodigo());
		 */

		CondicaoPagamento cpgPedido = CondicaoPagamento.recuperarUnico(dao,
				pedido.getCondicaoPagamentoCodigo());

		/*
		 * Cria uma lista com todas as formas e condições usadas para pagamento
		 * no pedido. Diferenciado quando o pagamento foi feito através de
		 * vários cartões e existem várias transações.
		 */
		List<FormaCondicao> formasCondicoes = new ArrayList<FormaCondicao>();

		/*
		 * Implementar alguma forma de identificar se o cartão faz integração
		 * com a administradora ou se é pagamento com 2 ou mais cartões.
		 */
		if (fpgPedido.isCartao()) {
			// fpgFilial.getCredencialAdministradoraCartao();

			/*
			 * Recupera a lista com todas as transacoes de cartao confirmadas e
			 * alimenta a lista para geração do financeiro.
			 */
			List<TransacaoPagamentoCartao> transacoes = TransacaoPagamentoCartao
					.recuperarConfirmadas(dao, pedido.getFilialCodigo(),
							pedido.getSerieCodigo(), pedido.getNumero(), null);
			if (transacoes != null) {
				for (TransacaoPagamentoCartao transacaoPagamentoCartao : transacoes) {
					FormaCondicao fpgCpg = new FormaCondicao(
							transacaoPagamentoCartao.getFormaPagamentoCodigo(),
							transacaoPagamentoCartao
									.getCondicaoPagamentoCodigo(),
							transacaoPagamentoCartao.getData(),
							transacaoPagamentoCartao
									.getTransacaoPagamentoCartaoPK()
									.getSequencial(),
							transacaoPagamentoCartao.getValor(),
							"AUT:"
									+ transacaoPagamentoCartao
											.getNumeroAutorizacao()
									+ " - DOC:"
									+ transacaoPagamentoCartao
											.getNumeroComprovanteVenda());

					formasCondicoes.add(fpgCpg);
				}
			}
		}

		/*
		 * Quando não for cartão ou se o cartão não faz integração com a
		 * administradora a lista estará vazia. Nesse caso preenchemos com a
		 * forma de pagamento do pedido.
		 */
		if (formasCondicoes.size() == 0) {
			FormaCondicao fpgCpg = new FormaCondicao(fpgPedido.getCodigo(),
					cpgPedido.getCodigo(), pedido.getDataEmissao(), 0,
					pedido.getValor(), "");
			formasCondicoes.add(fpgCpg);
		}

		if (formasCondicoes != null && formasCondicoes.size() > 0) {
			/*
			 * Percorre todos as formas de pagamentos e gera o financeiro para
			 * cada uma delas.
			 */
			for (FormaCondicao formaCondicao : formasCondicoes) {
				List<FinanceiroGerencial> financeiros = gerarParcelasFinanceiroPedido(
						dao, sessaoCodigo, pedido, formaCondicao, propriedade);

				if (financeiros != null && financeiros.size() > 0) {
					/*
					 * Cria o lote de lancamento financeiro e recupera o código
					 * sequencial gerado.
					 */
					FormaPagamento fpgAux = FormaPagamento.recuperarUnico(dao,
							formaCondicao.formaPagamentoCodigo);

					String flagConfirma = "N";
					String tiposConfirma = ClassificaDocumento.TIPO_BOLETA
							+ ClassificaDocumento.TIPO_DUPLICATA
							+ ClassificaDocumento.TIPO_PROMISSORIA
							+ ClassificaDocumento.TIPO_OUTROS;
					if (tiposConfirma.contains(fpgAux.getClassificaDocumento()
							.getTipo()) && fpgAux.getFlagBaixaDireta() == 0) {
						flagConfirma = "S";
					}
					String seqAux = Numeracao.ObterNumeracaoUnica().toString();

					LoteLancamentoFinanceiro lotelanc = new LoteLancamentoFinanceiro(
							pedido.getFilialCodigo(), pedido.getValor(), 1,
							financeiros.size(), 0, Sistema.obterData(),
							Sistema.obterHora(), "R", "N", 0, "", 1,
							flagConfirma, seqAux, 0);
					dao.insert(lotelanc);

					/*
					 * Recupera o código gerado para anexar o financeiro com o
					 * pedido.
					 */
					int loteLancamentoCodigo = LoteLancamentoFinanceiro
							.recuperarUltimoCodigo(dao,
									lotelanc.getFilialCodigo(),
									lotelanc.getSequencialAuxiliar());
					lotelanc.getLoteLancamentoFinanceiroPK()
							.setCodigoSequencial(loteLancamentoCodigo);

					/**
					 * Grava os documentos gerados.
					 */
					for (FinanceiroGerencial financeiro : financeiros) {

						financeiro
								.setLoteLancamentoCodigo(loteLancamentoCodigo);

						// dao.insert(financeiro);
						dao.insert(financeiro);

						/*
						 * Recupera o ultimo codigo gerado.
						 */
						financeiro
								.getFinanceiroGerencialPK()
								.setCodigoSequencial(
										FinanceiroGerencial
												.recuperarUltimoCodigo(
														dao,
														financeiro
																.getFinanceiroGerencialPK()
																.getFilialCodigo(),
														financeiro
																.getSessaoCodigo()));

					}

					/*
					 * Atualiza o log no pedido
					 */
					pedido.setLoteLancamentoFinanceiro(loteLancamentoCodigo);
					dao.update(pedido);
				}
			}
		}
	}

	public static int cancelarLoteFinanceiro(GenericoDAO dao, IPedidoWeb pedido) {
		return LoteLancamentoFinanceiro.cancelarLoteFinanceiro(dao, pedido);

	}
}
