package br.com.space.spacewebII.controle.aplicacao;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.mail.EmailException;

import br.com.space.api.core.email.EnvioEmail;
import br.com.space.api.core.email.modelo.EmailAnexo;
import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.sistema.Criptografia;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoDescricao;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametro;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

public class GerenteEmail {

	public GerenteEmail() {
	}

	/**
	 * 
	 * @param parametros
	 * @return
	 */
	public static EnvioEmail configurarEmail(Parametro parametro) {

		String descriptografarSenhaEmail = Criptografia.descriptografarSenhaEmail(parametro.getSenhaEmail());

		EnvioEmail email = new EnvioEmail(parametro.getLoginEmail(), descriptografarSenhaEmail,
				parametro.getServidorEmail(), parametro.getPortaEmail());
		return email;
	}

	public static boolean enviarEmail(Parametro parametro, String destinatario, String assunto, String conteudo) {
		try {

			EnvioEmail email = GerenteEmail.configurarEmail(parametro);

			email.enviarEmail(parametro.getEmailRemetente(), parametro.getNomeEmailRemetente(), destinatario, conteudo,
					assunto, new ArrayList<EmailAnexo>());

			return true;
		} catch (EmailException e) {
			e.printStackTrace();
		}

		return false;
	}

	public static boolean enviarEmailGenerico(final String destinatario, final GenericoDAO dao,
			final Parametros parametros, final String tituloSuperior, final String textoSuperior,
			final String textoInstrucao, final String link, final String assunto, boolean emBackgroud) {

		if (emBackgroud) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					enviarEmailGenerico(destinatario, dao, parametros, tituloSuperior, textoSuperior, textoInstrucao,
							link, assunto);

				}
			}).start();
		} else {
			return enviarEmailGenerico(destinatario, dao, parametros, tituloSuperior, textoSuperior, textoInstrucao,
					link, assunto);
		}
		return true;
	}

	/**
	 * Envia email em formato generico.
	 * 
	 * @param novaSenha
	 * @param destinatario
	 * @param dao
	 * @param parametros
	 * @return
	 */
	public static boolean enviarEmailGenerico(String destinatario, GenericoDAO dao, Parametros parametros,
			String tituloSuperior, String textoSuperior, String textoInstrucao, String link, String assunto) {

		EnvioEmail email = configurarEmail(parametros.getParametro1());

		try {
			email.enviarEmail(parametros.getParametro1().getEmailRemetente(),
					parametros.getParametro1().getNomeEmailRemetente(), destinatario,
					getHtmlEmailGenerico(parametros, dao, tituloSuperior, textoSuperior, textoInstrucao, link), assunto,
					new ArrayList<EmailAnexo>());

		} catch (EmailException e) {
			e.printStackTrace();
			return false;
		}

		return true;
	}

	/**
	 * Envia email com nova senha.
	 * 
	 * @param novaSenha
	 * @param destinatario
	 * @param dao
	 * @param parametros
	 * @return
	 */
	public static boolean enviarEmailNovaSenha(String chaveRequisicao, String destinatario, GenericoDAO dao,
			Parametros parametros) {

		return enviarEmailGenerico(destinatario, dao, parametros,
				Propriedade.getMensagemUtilizadoSessao("texto.novaSenha"),
				Propriedade.getMensagemUtilizadoSessao("mensagem.novasenha.saldacao"),
				Propriedade.getMensagemUtilizadoSessao("mensagem.novasenha.instrucao"),
				"/novaSenha/" + chaveRequisicao + "/", Propriedade.getMensagemUtilizadoSessao("texto.novaSenha"));
	}

	/**
	 * Envia email com nova senha.
	 * 
	 * @param novaSenha
	 * @param destinatario
	 * @param dao
	 * @param parametros
	 * @return
	 */
	public static boolean enviarEmailConfirmacao(String chave, String destinatario, GenericoDAO dao,
			Parametros parametros) {

		return enviarEmailGenerico(destinatario, dao, parametros,
				Propriedade.getMensagemUtilizadoSessao("acao.confirmarEmail"),
				Propriedade.getMensagemUtilizadoSessao("mensagem.confirmacaoEmail.saudacao"),
				Propriedade.getMensagemUtilizadoSessao("mensagem.confirmacaoEmail.instrucao"),
				"/cliente/" + chave + "/", Propriedade.getMensagemUtilizadoSessao("acao.confirmarEmail"));
	}

	/**
	 * Envia email em formato genérico, com direito a:
	 * 
	 * TITULO Superior. Texto Superior . ________________________
	 * 
	 * Texto de Instrução : http://site/link
	 * 
	 * @param parametros
	 * @param dao
	 * @param tituloSuperior
	 * @param textoSuperior
	 * @param textoInstrucao
	 * @param link
	 * @return
	 */
	private static String getHtmlEmailGenerico(Parametros parametros, GenericoDAO dao, String tituloSuperior,
			String textoSuperior, String textoInstrucao, String link) {

		String email = parametros.getParametroWeb().getEmailHTMLNovaSenha();

		email = getHtmlEstruturaEmail(email, parametros, dao);

		String urlSite = "http://" + parametros.getParametroWeb().getUrlSite();
		email = email.replaceAll("\\{linkSite\\}", urlSite);

		if (tituloSuperior == null)
			tituloSuperior = "";
		if (textoSuperior == null)
			textoSuperior = "";
		if (textoInstrucao == null)
			textoInstrucao = "";
		if (link == null)
			link = "";

		email = email.replaceAll("\\{textoTitulo\\}", tituloSuperior);
		email = email.replaceAll("\\{textoTexto\\}", textoSuperior);
		email = email.replaceAll("\\{textoSenha\\}", textoInstrucao);
		email = email.replaceAll("\\{linkNovasenha\\}", urlSite + link);

		return email;
	}

	/**
	 * Substitui imagens, cor, e nome da filial na estrutura do email.
	 * 
	 * @param email
	 * @param parametros
	 * @param dao
	 * @return
	 */
	private static String getHtmlEstruturaEmail(String email, Parametros parametros, GenericoDAO dao) {

		String urlImagem = "http://" + parametros.getParametroWeb().getUrlImagem() + "/imagem/";

		email = email.replaceAll("\\{corTema\\}",
				EscolhaTemaMB.corEmail(parametros.getParametroWeb().getTemaWebNome()));

		email = email.replaceAll("\\{imagemLogo\\}",
				urlImagem + "logo/" + EscolhaTemaMB.imagemLogo(parametros.getParametroWeb().getTemaWebNome()));

		email = email.replaceAll("\\{textoSite\\}", parametros.getParametroWeb().getSiteTitulo());

		String urlSite = "http://" + parametros.getParametroWeb().getUrlSite();

		email = email.replaceAll("\\{linkSite\\}", urlSite);

		email = email.replaceAll("\\{linkLogin\\}", urlSite + GerenteLogin.URL_VIEW);

		return email;
	}

	/**
	 * 
	 * @return
	 */
	public static String gerarCabecalhoEmail(String conteudo, Parametros parametros, GenericoDAO dao) {
		String email = "<div style='text-align:left; padding:10px; font-family:arial; background:{corTema}; height:50px; border-radius:5px 5px 0 0'>"
				+ "	<a href='{linkSite}'>"
				+ "		<img src='{imagemLogo}' width='200' height='50' alt='{textoSite}' />" + "	</a>" + "</div>"
				+ "<div style='text-align:left; border-color: silver; border-style: solid; border-width: 1px 20px 1px 20px; padding:5px; font-family:arial; font-size: small;'>"
				+ conteudo + "</div>";

		email = getHtmlEstruturaEmail(email, parametros, dao);

		return email;
	}

	public static String gerarConteudoEmailCabecalhoPedido(IPedidoWeb pedido, Propriedade propriedade,
			String nomeCliente, String mensagemComplementoPedido) {

		StringBuffer conteudo = new StringBuffer();
		conteudo.append("<div style='display: inline-block'>");

		nomeCliente = nomeCliente != null ? nomeCliente.toLowerCase() : "";

		conteudo.append("<div><h3 style='text-transform: capitalize;'>" + propriedade.getMensagem("texto.ola") + ", "
				+ nomeCliente + "</h3></div>");

		conteudo.append("<span style='font-weight: bold;'>")
				.append(propriedade.getMensagem("mensagem.seuPedidoRealizadoEm",
						pedido.getSerieCodigo() + "/" + pedido.getNumero(),
						Conversao.formatarDataDDMMAAAA(pedido.getDataEmissao())))
				.append(" " + mensagemComplementoPedido).append(".</span><br/> <br />");

		conteudo.append("<span style='font-style: italic;'>")
				.append(propriedade.getMensagem("mensagem.emailpedido.acompanhe",
						"<a href='{linkLogin}'> " + propriedade.getMensagem("texto.aqui") + "</a>"))
				.append(".</span>");

		conteudo.append(
				"<hr style='height: 1px; width: 100%; text-align: left; border-style: dotted groove double ridge;' /></div> <br/><br/>");

		return conteudo.toString();
	}

	/**
	 * 
	 * @return
	 */
	public static String gerarConteudoEmailItens(List<? extends IItemPedidoDescricao> itens, Propriedade propriedade) {

		StringBuffer conteudo = new StringBuffer();

		conteudo.append("<br/><strong>").append(propriedade.getMensagem("titulo.itens")).append("</strong><br/>")
				.append(propriedade.getMensagem("texto.quantidadeTotal")).append(": ").append(itens.size());

		for (IItemPedidoDescricao item : itens) {
			conteudo.append("<br/>").append(propriedade.getMensagem("texto.produto")).append(": ")
					.append(item.getProdutoDescricao()).append("<br/>").append(propriedade.getMensagem("texto.unidade"))
					.append(": ").append(item.getUnidade()).append("/").append(item.getQuantidadeUnidade())
					.append(" - ").append(propriedade.getMensagem("texto.quantidade")).append(":")
					.append(item.getQuantidade()).append(" - ").append(propriedade.getMensagem("texto.precoUnitario"))
					.append(": ").append(propriedade.getMensagem("texto.siglaDinheiro")).append(" ")
					.append(item.getPrecoVenda()).append("<br/>").append(propriedade.getMensagem("texto.precoTotal"))
					.append(": ").append(propriedade.getMensagem("texto.siglaDinheiro")).append(item.getPrecoTotal())
					.append("<br/>");
		}

		return conteudo.toString();
	}

	public static String gerarConteudoEmailPagamento(Propriedade propriedade, String formaPagamentoDescricao,
			String condicaoPagamentoDescricao, double valorTotalPedido) {
		StringBuffer conteudo = new StringBuffer();

		conteudo.append("<br/><strong>").append(propriedade.getMensagem("titulo.pagamento")).append("</strong><br/>")
				.append(propriedade.getMensagem("texto.valorTotal")).append(": ")
				.append(propriedade.getMensagem("texto.siglaDinheiro")).append(" ")
				.append(Conversao.formatarValor2(valorTotalPedido)).append("<br/>")
				.append(propriedade.getMensagem("texto.formaPagamento")).append(": ").append(formaPagamentoDescricao)
				.append(" - ").append(condicaoPagamentoDescricao).append("<br/>");

		return conteudo.toString();
	}

	public static String gerarConteudoEmailEntrega(Propriedade propriedade, String enderecoDescricao, double pedoBruto,
			double metroCubico, double valorFrete) {
		StringBuffer conteudo = new StringBuffer();

		conteudo.append("<br/><strong>").append(propriedade.getMensagem("texto.entrega")).append("</strong><br/>")
				.append(propriedade.getMensagem("titulo.endereco")).append(": ").append(enderecoDescricao)
				.append("<br/>");

		if (pedoBruto > 0.0) {
			conteudo.append(propriedade.getMensagem("texto.peso")).append(": ")
					.append(Conversao.formatarValor3(pedoBruto)).append("kg").append("<br/>");
		}

		if (metroCubico > 0.0) {
			conteudo.append(propriedade.getMensagem("texto.volume")).append(": ")
					.append(Conversao.formatarValor3(metroCubico)).append("m³").append("<br/>");
		}

		if (valorFrete > 0.0) {
			conteudo.append(propriedade.getMensagem("texto.frete")).append(": R$ ")
					.append(Conversao.formatarValor2(valorFrete));
		}

		return conteudo.toString();
	}

	public static String gerarConteudoEmailPedido(Propriedade propriedade, IPedidoWeb pedido,
			List<? extends IItemPedidoDescricao> itens, String nomeCliente, String mensagemComplementoPedido,
			String formaPagamentoDescricao, String condicaoPagamentoDescricao, String enderecoDescricao,
			Parametros parametros, GenericoDAO dao) {

		StringBuffer conteudo = new StringBuffer();

		conteudo.append(GerenteEmail.gerarConteudoEmailCabecalhoPedido(pedido, propriedade, nomeCliente,
				mensagemComplementoPedido));

		conteudo.append(GerenteEmail.gerarConteudoEmailItens(itens, propriedade));

		conteudo.append(GerenteEmail.gerarConteudoEmailPagamento(propriedade, formaPagamentoDescricao,
				condicaoPagamentoDescricao, pedido.getValor()));

		conteudo.append(GerenteEmail.gerarConteudoEmailEntrega(propriedade, enderecoDescricao, pedido.getPesoBruto(),
				pedido.getMetrosCubicos(), pedido.getFrete()));

		conteudo.append("<h4>" + propriedade.getMensagem("mensagem.obrigado") + ", {textoSite}</h4>");

		return GerenteEmail.gerarCabecalhoEmail(conteudo.toString(), parametros, dao);
	}
}
