package br.com.space.spacewebII.controle.pedido;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.Sistema;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.venda.ControleSeriePedido;
import br.com.space.spacewebII.modelo.dominio.venda.SeriePedido;

/**
 * Classe para controlar numeração de Pedido de Saída
 * 
 * @author Desenvolvimento
 * 
 */
public class NumeracaoPedido {

	public static void recupearSerieAtiva(int ControleSerieNumero) {

	}

	/**
	 * 
	 * Gera série do próximo pedido, através do controle de numeração
	 * 
	 * @param dao
	 * @param controleSerieNumero
	 * @param filialCodigo
	 */
	public static SeriePedido gerarSerieProximoPedido(GenericoDAO dao,
			int filialCodigo, int controleSerieNumero) {

		SeriePedido serieNova = null;
		try {

			/*
			 * Recupera o controle atual e a serie ativa do mesmo para servir
			 * como referencia para o proximo numero.
			 */
			ControleSeriePedido controle = ControleSeriePedido.recuperarCodigo(
					dao, controleSerieNumero);

			SeriePedido serie = SeriePedido.recuperarControleSerieAtiva(dao,
					filialCodigo, controleSerieNumero);

			String expressao1 = "";
			String expressao2 = "";

			/*
			 * Testa se a numeracao já estourou de acordo com a sua frequencia.
			 */

			switch (controle.getFrequencia()) {
			case "D":
				expressao1 = Conversao.formatarDataAAAAMMDD(serie
						.getDataSerie());
				expressao2 = Conversao
						.formatarDataAAAAMMDD(Sistema.obterData());
				break;

			case "S":
				expressao1 = String.valueOf(Conversao.obterAno(serie
						.getDataSerie()))
						+ String.valueOf(Conversao.obterSemanaAno(serie
								.getDataSerie()));
				expressao2 = String.valueOf(Conversao.obterAno(Sistema
						.obterData()))
						+ String.valueOf(Conversao.obterSemanaAno(Sistema
								.obterData()));
				break;

			case "M":
				expressao1 = String.valueOf(Conversao.obterAno(serie
						.getDataSerie()))
						+ String.valueOf(Conversao.obterSemanaMes(serie
								.getDataSerie()));
				expressao2 = String.valueOf(Conversao.obterAno(Sistema
						.obterData()))
						+ String.valueOf(Conversao.obterSemanaMes(Sistema
								.obterData()));
				break;
			case "A":
				expressao1 = String.valueOf(Conversao.obterAno(serie
						.getDataSerie()));
				expressao2 = String.valueOf(Conversao.obterAno(Sistema
						.obterData()));
				break;

			case "E":
				int numeroAtual = serie.getNumeroAtual();
				// + controle.getIncremento();
				int numeroFinal = controle.getNumeracaoFinal();

				expressao1 = String.valueOf(numeroAtual);
				expressao2 = String.valueOf(numeroFinal);

				/*
				 * Se o numero atual não atingiu a numeracao final ou se a
				 * numeracao final não limitada, considera que não há
				 * necessidade de gerar uma nova serie.
				 */
				if (numeroAtual <= numeroFinal || numeroFinal == 0) {
					expressao2 = expressao1;
				}

				break;

			default:
				break;
			}

			/*
			 * Verifica se as expressoes indicam que é para gerar uma nova
			 * serie. Senão retorna a serie atual.
			 */
			if (expressao1.compareTo(expressao2) != 0) {
				String novaSerieString = NumeracaoPedido.gerarNovaSerie(dao,
						serie);

				if (!StringUtil.isValida(novaSerieString)) {
					throw new IllegalArgumentException("serie vazia");
				}

				/*
				 * Cria uma nova serie com a numeracao iniciando em 1.
				 */
				serieNova = new SeriePedido(novaSerieString, serie
						.getSeriePedidosPK().getFilialCodigo(),
						serie.getDescricao(), Sistema.obterData(), 1, 1,
						Sistema.obterData(),
						serie.getControleSeriePedidoCodigo());
				dao.insertObject(serieNova);

				/*
				 * Inativa a serie anterior.
				 */
				serie.setFlagAtivo(0);
				dao.update(serie);
			} else {
				serieNova = serie;
			}

			/*
			 * Acrescenta ao numero atual o valor do incremento. Atualiza
			 * somente o numero no banco de dados pelo motivo que o numero
			 * gravado é o numero do proximo pedido. Assim a nova serie será
			 * retornada corretamente sem esse incremento.
			 */
			SeriePedido.atualizarIncremento(dao, controle, serieNova);

			return serieNova;

		} catch (Exception e) {

			e.printStackTrace();
			return null;
		}
	}

	/**
	 * Gera série de acordo com parâmetros
	 * 
	 * @param dao
	 * @param serieAtual
	 * @return
	 */
	public static String gerarNovaSerie(GenericoDAO dao, SeriePedido serieAtual) {
		String novaSerie = "";

		try {

			if (serieAtual != null) {

				String codigoAtual = serieAtual.getSeriePedidosPK().getCodigo()
						.toUpperCase();

				char[] novaSerieChar = codigoAtual.toCharArray();

				int posicao = novaSerieChar.length - 1;
				while (posicao >= 0) {

					char letra = novaSerieChar[posicao];

					/*
					 * Verifica se a letra ultrapassou a letra "Z".
					 */
					if (letra > 90) {
						novaSerieChar[posicao] = 'A';
					} else {
						/*
						 * Avança para a proxima letra na posição atual.
						 */
						novaSerieChar[posicao] = (char) (letra + 1);

						/*
						 * Verifica se a serie já existe até não encontrar.
						 */
						String novaSerieAux = String.valueOf(novaSerieChar);
						SeriePedido serieExiste = SeriePedido.recuperarCodigo(
								dao, serieAtual.getSeriePedidosPK()
										.getFilialCodigo(), novaSerieAux);

						if (serieExiste != null)
							continue;
						else
							break;
					}

					posicao--;
				}
				novaSerie = String.valueOf(novaSerieChar);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return novaSerie;

	}

	public static void main(String[] args) {
		GenericoDAO dao = new GenericoDAO();

		// NumeracaoPedido.gerarNovaSerie(dao, serieAtual)
		try {
			dao.beginTransaction();
			SeriePedido serie = NumeracaoPedido.gerarSerieProximoPedido(dao, 1,
					1);
			System.out.println(serie.getSeriePedidosPK().getCodigo() + "-"
					+ serie.getNumeroAtual());

			dao.commitTransaction();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

}
