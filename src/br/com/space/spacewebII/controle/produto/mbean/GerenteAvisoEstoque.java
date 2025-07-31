package br.com.space.spacewebII.controle.produto.mbean;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.apache.commons.mail.EmailException;

import br.com.space.api.core.email.EnvioEmail;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.negocio.modelo.negocio.estoque.FlagTipoEstoque;
import br.com.space.spacewebII.controle.aplicacao.EscolhaTemaMB;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.controle.estoque.Estoque;
import br.com.space.spacewebII.modelo.aplicacao.Configuracao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.AvisoEstoque;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoMidia;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.FormatacaoMB;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;

public class GerenteAvisoEstoque {

	public static final int MAX_EMAILS = 50;
	private static final int PRAZO_EXPIRACAO_AVISO_DIAS = 30;

	private static GenericoDAO dao = null;

	public GerenteAvisoEstoque() {
	}

	public static void gravarAvisoEstoque(GenericoDAO dao, int clienteCodigo,
			int produtoCodigo, int filialCodigo) {

		if (!avisoRepetido(dao, clienteCodigo, produtoCodigo, filialCodigo)) {

			AvisoEstoque avisoEstoque = new AvisoEstoque(produtoCodigo,
					clienteCodigo, filialCodigo, new Date(), null, 1);

			try {
				dao.beginTransaction();

				dao.insertObject(avisoEstoque);

				dao.commitTransaction();

				MensagemSistema
						.exibir(TipoMensagem.Info,
								null,
								Propriedade
										.getMensagemUtilizadoSessao("mensagem.solicitacaoEnviadaSucesso"));

			} catch (ClassNotFoundException e) {
				dao.rollBackTransaction();
				MensagemSistema
						.exibir(TipoMensagem.Erro,
								Propriedade
										.getMensagemUtilizadoSessao("alerta.ocorreuProblema"),
								Propriedade
										.getMensagemUtilizadoSessao("alerta.solicitacaoNaoEnviada"));
				e.printStackTrace();
			} catch (SQLException e) {
				dao.rollBackTransaction();
				MensagemSistema
						.exibir(TipoMensagem.Erro,
								Propriedade
										.getMensagemUtilizadoSessao("alerta.ocorreuProblema"),
								Propriedade
										.getMensagemUtilizadoSessao("alerta.solicitacaoNaoEnviada"));
				e.printStackTrace();
			}
		} else {
			MensagemSistema
					.exibir(TipoMensagem.Alerta,
							null,
							Propriedade
									.getMensagemUtilizadoSessao("mensagem.produtoJaSolicitadoAvisarChegada"));
		}
	}

	public static boolean avisoRepetido(GenericoDAO dao, int clienteCodigo,
			int produtoCodigo, int filialCodigo) {
		List<AvisoEstoque> avisoEspecifico = AvisoEstoque
				.recuperarSolicitacaoEspecifica(dao, filialCodigo,
						clienteCodigo, produtoCodigo);
		return avisoEspecifico != null && avisoEspecifico.size() > 0;
	}

	/**
	 * Verifica as solicitações pendentes do banco, determinando as que precisam
	 * ser cancelandas e as que podem ser enviadas.
	 */
	public static void verificarSolicitacoesAvisos() {

		GenericoDAO dao = getDao();
		Parametros parametros = getParametros();

		int codFil = Configuracao.getAtributosConfiguracao()
				.getFilialPadraoCodigo();

		List<AvisoEstoque> solicitacoesPendentes = AvisoEstoque
				.recuperarSolicitacoesAviso(dao, codFil);

		if (ListUtil.isValida(solicitacoesPendentes)) {

			List<AvisoEstoque> enviarSolicitacoes = new ArrayList<AvisoEstoque>();
			List<AvisoEstoque> cancelarSolicitacoes = new ArrayList<AvisoEstoque>();

			int iGrupoProdutos = 0;
			boolean agrupar = false;

			EnvioEmail email = new EnvioEmail(parametros.getParametro1()
					.getLoginEmail(), parametros.getParametro1()
					.getSenhaEmail(), parametros.getParametro1()
					.getServidorEmail(), parametros.getParametro1()
					.getPortaEmail());

			Estoque estoque = new Estoque(dao, parametros);

			estoque.setFilialCodigo(codFil);

			for (int i = 0; i < solicitacoesPendentes.size(); i++) {

				if (!agrupar) {
					if (avisoExpirado(solicitacoesPendentes.get(i)))
						cancelarSolicitacoes.add(solicitacoesPendentes.get(i));

					else if (produtoRetornadoEstoque(
							solicitacoesPendentes.get(i), estoque)) {

						iGrupoProdutos = i;
						agrupar = true;
					}
				}

				/* Agrupa avisos de mesmo produto em lista */
				if (agrupar) {
					if ((solicitacoesPendentes.get(i).getProduto().getCodigo() == solicitacoesPendentes
							.get(iGrupoProdutos).getProduto().getCodigo())
							&& (solicitacoesPendentes.get(i).getFilialCodigo() == solicitacoesPendentes
									.get(iGrupoProdutos).getFilialCodigo())
							&& (i - iGrupoProdutos < MAX_EMAILS)) {

						enviarSolicitacoes.add(solicitacoesPendentes.get(i));
					} else {
						atualizarSolicitacoesEnviadasProduto(
								enviarSolicitacoes, email, dao, parametros);
						i--;
						agrupar = false;
					}

					if (i == solicitacoesPendentes.size() - 1) {
						atualizarSolicitacoesEnviadasProduto(
								enviarSolicitacoes, email, dao, parametros);
					}
				}
			}

			if (!cancelarSolicitacoes.isEmpty())
				atualizarSolicitacoesCanceladas(cancelarSolicitacoes, dao);

		}
	}

	/**
	 * Verifica se a solicitação está vencida
	 * 
	 * @param solicitacao
	 * @return
	 */
	private static boolean avisoExpirado(AvisoEstoque solicitacao) {
		Calendar dataExpiracao = new GregorianCalendar();

		dataExpiracao.setTime(solicitacao.getDataRequisicao());
		dataExpiracao.add(Calendar.DAY_OF_MONTH, PRAZO_EXPIRACAO_AVISO_DIAS);

		return dataExpiracao.before(new Date());
	}

	/**
	 * Verifica se o produto retornou ao estoque
	 * 
	 * @param solicitacao
	 * @return
	 */
	private static boolean produtoRetornadoEstoque(AvisoEstoque solicitacao,
			Estoque estoque) {

		boolean temEstoque = estoque.recuperarEstoqueDisponivel(solicitacao
				.getFilialCodigo(), solicitacao.getProduto().getCodigo(), 0,
				null, FlagTipoEstoque.DisponivelVendaGeral) > 0;

		boolean existeBalanco = estoque.produtoEmBalanco(solicitacao
				.getProdutoCodigo());

		return temEstoque && !existeBalanco;
	}

	/**
	 * envia emails e atualiza no banco
	 * 
	 * @param enviarSolicitacoes
	 */
	private static void atualizarSolicitacoesEnviadasProduto(
			List<AvisoEstoque> enviarSolicitacoes, EnvioEmail email,
			GenericoDAO dao, Parametros parametros) {

		List<String> destinararios = new ArrayList<String>();

		for (AvisoEstoque solicitacao : enviarSolicitacoes) {
			destinararios.add(solicitacao.getCliente().getEmailLoginWeb());
		}

		if (enviarEmails(email, destinararios, enviarSolicitacoes.get(0)
				.getProduto(), parametros, dao)) {
			try {
				dao.beginTransaction();

				for (AvisoEstoque solicitacao : enviarSolicitacoes) {

					solicitacao.setFlagEnviar(0);
					solicitacao.setDataAviso(new Date());

					dao.update(solicitacao);

					dao.commitTransaction();
				}

			} catch (ClassNotFoundException e) {
				dao.rollBackTransaction();
				e.printStackTrace();
			} catch (SQLException e) {
				dao.rollBackTransaction();
				e.printStackTrace();
			}
		}
	}

	/**
	 * Envio de emails.
	 * 
	 * @param email
	 * @param destinararios
	 * @return
	 */
	private static boolean enviarEmails(EnvioEmail email,
			List<String> destinararios, CaixaProdutoVisualizavel produto,
			Parametros parametros, GenericoDAO dao) {
		try {
			email.enviarEmailOculto(
					parametros.getParametro1().getEmailRemetente(),
					parametros.getParametro1().getNomeEmailRemetente(),
					destinararios,
					mensagemEmailHTML(produto, parametros, dao),
					Propriedade
							.getMensagemUtilizadoSessao("mensagem.avisoChegadaProduto"),
					null);
		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Desativa as solicitações canceladas no banco
	 * 
	 * @param cancelarSolicitacoes
	 */
	private static void atualizarSolicitacoesCanceladas(
			List<AvisoEstoque> cancelarSolicitacoes, GenericoDAO dao) {
		try {

			dao.beginTransaction();

			for (AvisoEstoque solicitacao : cancelarSolicitacoes) {
				solicitacao.setFlagEnviar(0);
				dao.update(solicitacao);
			}

			dao.commitTransaction();

		} catch (ClassNotFoundException e) {
			dao.rollBackTransaction();
			e.printStackTrace();
		} catch (SQLException e) {
			dao.rollBackTransaction();
			e.printStackTrace();
		}
	}

	private static String mensagemEmailHTML(CaixaProdutoVisualizavel produto,
			Parametros parametros, GenericoDAO dao) {
		String email = parametros.getParametroWeb().getEmailHTMLAvisoEstoque();

		String urlImagem = "http://"
				+ parametros.getParametroWeb().getUrlImagem() + "/imagem/";
		String urlSite = "http://" + parametros.getParametroWeb().getUrlSite();

		email = email.replaceAll("\\{corTema\\}", EscolhaTemaMB
				.corEmail(parametros.getParametroWeb().getTemaWebNome()));

		email = email.replaceAll(
				"\\{imagemLogo\\}",
				urlImagem
						+ "logo/"
						+ EscolhaTemaMB.imagemLogo(parametros.getParametroWeb()
								.getTemaWebNome()));

		String urlImgProd = ProdutoMidia.recuperarImagemCapaAtiva(dao,
				produto.getCodigo()).getEnderecoMidia();
		urlImgProd = urlImgProd.replaceAll("\\\\", "/");
		email = email.replaceAll("\\{imagemProduto\\}", urlImagem + urlImgProd);

		email = email.replaceAll("\\{textoTitulo\\}",
				Propriedade.getMensagemUtilizadoSessao("texto.boasNoticias"));
		email = email
				.replaceAll(
						"\\{textoTexto\\}",
						Propriedade
								.getMensagemUtilizadoSessao("mensagem.avisoProdutoSolicitadoDeVoltaEstoque"));
		email = email.replaceAll("\\{textoBotao\\}", "Conferir");
		email = email.replaceAll("\\{textoProduto\\}", produto
				.getDescricaoVisualizacao().toUpperCase());
		email = email.replaceAll("\\{textoSite\\}", parametros
				.getParametroWeb().getSiteTitulo());
		email = email
				.replaceAll(
						"\\{textoAviso\\}",
						Propriedade
								.getMensagemUtilizadoSessao("mensagem.permanenciaProdutoNaoGarantida"));

		email = email.replaceAll("\\{linkBotao\\}",
				urlSite + "/produto/" + produto.getCodigo() + "/"
						+ FormatacaoMB.formataUrlNomeProduto(produto) + "/");

		email = email.replaceAll("\\{linkSite\\}", urlSite + "/");

		return email;
	}

	public static GenericoDAO getDao() {
		if (dao == null) {
			dao = new GenericoDAO();
		}
		return dao;
	}

	/**
	 * 
	 * @return
	 */
	private static Parametros getParametros() {

		return new Parametros(getDao(), Configuracao.getAtributosConfiguracao()
				.getFilialPadraoCodigo());
	}
}
