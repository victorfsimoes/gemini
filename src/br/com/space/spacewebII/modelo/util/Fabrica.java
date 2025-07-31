package br.com.space.spacewebII.modelo.util;

import java.util.Date;
import java.util.UUID;

import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.Sistema;
import br.com.space.spacewebII.controle.estoque.Estoque;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;

/**
 * Classe Factory.
 * 
 * @author Desenvolvimento
 * 
 */
public class Fabrica {

	@Produces
	@Named
	public static Date dataHoje() {
		return Sistema.obterData();
	}

	/**
	 * Define as seglas das unidades federativas do Brasil
	 */
	public static final String[] ufs = new String[] { "AC", "AL", "AP", "AM",
			"BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB",
			"PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE",
			"TO" };

	/**
	 * Define os nomes das unidades federativas do Brasil
	 */
	public static final String[] estados = new String[] { "Acre", "Alagoas",
			"Amapá", "Amazonas", "Bahia", "Ceará", "Distrito Federal",
			"Espírito Santo", "Goiás", "Maranhão", "Mato Grosso",
			"Mato Grosso do Sul", "Minas Gerais", "Pará", "Paraíba", "Paraná",
			"Pernambuco", "Piauí", "Rio de Janeiro", "Rio Grande do Norte",
			"Rio Grande do Sul", "Rondônia", "Roraima", "Santa Catarina",
			"São Paulo", "Sergipe", "Tocantins" };

	public static final String[] mascarasUF = new String[] {
			"99.999.999/999-99", "999.99999-9", "99999999-9", "99.999.999-9",
			"999999-99", "99999999-9", "999 99999 999-99", "999.99999-9",
			"99.999.999-9", "99999999-9", "9999999999-9", "99999999-9",
			"999.999.999/9999", "99-999999-9", "99999999-9", "99999999-99",
			"9999999-99", "99999999-9", "99.999.99-9", "99.9.999.999-9",
			"999999999-9", "9999999999999-9", "99999999-9", "999.999.999",
			"999.999.999.999", "99999999-9", "99.99.999999-9" };

	public static String gerarIDRandomico() {

		String id1 = UUID.randomUUID().toString();
		String id2 = UUID.randomUUID().toString();

		String id = id1.substring(0, 6) + id2.substring(id2.length() - 6);

		return id;
	}

	/**
	 * Gera o nome da pagina apartir da url em parametro
	 * 
	 * @param url
	 * @return
	 */
	public static String gerarNomePagina(String url) {

		int inicio = url.lastIndexOf("/");

		int fim = url.lastIndexOf(".");

		if (inicio < 0 || fim < 0 || inicio > fim) {
			return null;
		}

		String nomePagina = url.substring(inicio + 1, fim);

		if (nomePagina != null && !nomePagina.isEmpty()) {

			char[] nomePaginaChar = nomePagina.toCharArray();

			if (nomePaginaChar != null && nomePaginaChar.length > 0) {

				StringBuilder saida = new StringBuilder();

				for (int i = 0; i < nomePaginaChar.length; i++) {

					char c = nomePaginaChar[i];

					if (Character.isUpperCase(c)) {
						saida.append(" ");
					}

					if (i == 0 && c == nomePagina.charAt(0)) {
						c = Character.toUpperCase(c);
					}

					saida.append(c);
				}
				if (saida != null && !saida.toString().isEmpty()) {
					return saida.toString().trim();
				}
			}
		}
		return null;
	}

	public static Estoque gerarEstoque(GenericoDAO dao, int filialCodigo) {
		return new Estoque(dao, new Parametros(dao, filialCodigo));
	}

	/**
	 * 
	 * @param texto
	 * @return
	 */
	public static String formatarTextoComImagem(String texto) {
		return texto.replace("{imagem}", gerarEnderecoAplicacaoImagem());
	}

	/**
	 * 
	 * @return
	 */
	public static String gerarEnderecoAplicacaoImagem() {
		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) ec.getRequest();

		String aplicacao = "/imagem/";

		String servidor = request.getServerName();
		int porta = request.getServerPort();

		String caminhoServelet = gerarURLComHTTP(servidor, porta, aplicacao,
				request.isSecure());

		return caminhoServelet;
	}

	/**
	 * 
	 * @param enderecoRelativo
	 * @return
	 */
	public static String gerarEnderecoImagemSemPasta(String enderecoRelativo) {

		if (StringUtil.isValida(enderecoRelativo)) {

			String caminhoServelet = gerarEnderecoAplicacaoImagem();

			if (!enderecoRelativo.startsWith(caminhoServelet)) {
				return caminhoServelet + enderecoRelativo.replace("\\", "/");
			}
		}

		return enderecoRelativo;
	}

	public static String gerarURLComHTTP(String servidor, int porta,
			String aplicacao, boolean seguro) {

		if (!aplicacao.startsWith("/")) {
			aplicacao = "/" + aplicacao;
		}

		return (seguro ? "https://" : "http://") + servidor + ":" + porta
				+ aplicacao;

	}
}
