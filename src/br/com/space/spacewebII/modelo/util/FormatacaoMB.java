package br.com.space.spacewebII.modelo.util;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.Date;

import javax.inject.Inject;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dominio.estoque.GrupoProduto;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.SubGrupoProduto;
import br.com.space.spacewebII.modelo.dominio.sistema.Galeria;
import br.com.space.spacewebII.modelo.dominio.sistema.GaleriaImagem;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;

@ManagedBeanSessionScoped
public class FormatacaoMB implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	Parametros parametros;

	/**
	 * Formata um número de acordo com as casas decimais
	 * 
	 * @param numero
	 * @param casasDecimais
	 * @return
	 */
	public String formataDouble(double numero, int casasDecimais) {
		return Conversao.formatarValor(numero, casasDecimais);
	}

	/**
	 * Formata a descrição
	 * 
	 * @param descricao
	 * @return
	 */
	public String formataDescricao(String descricao) {
		return Conversao.formatarDescricao(descricao);
	}

	/**
	 * 
	 * @param preco
	 * @param casasDecimais
	 * @return
	 */
	public String formataPreco(double preco, int casasDecimais) {
		if (Double.isInfinite(preco) || Double.isNaN(preco))
			preco = 0;

		String precoFormatado = Conversao.formatarValor(preco, casasDecimais);

		return precoFormatado;
	}

	public String formataPreco2(double preco) {
		return formataPreco(preco, 2);

	}

	public String formataPreco3(double preco) {
		return formataPreco(preco, 3);
	}

	public String formatarQuantidadeEstoque(double quantidade) {

		int casas = parametros.getParametro1().getCasasDecimaisParaEstoque();

		return Conversao.formatarValor(quantidade, casas);
	}

	/**
	 * Converte porcentagem em índice
	 * 
	 * @param indice
	 * @return
	 */
	public String convertePorcentagem(double indice) {
		return formataDouble((1 - indice) * 100, 2);
	}

	/**
	 * Formata data em DD/MM/AAAA
	 * 
	 * @param data
	 * @return
	 */
	public String formataData(Date data) {
		if (data != null)
			return Conversao.formatarDataDDMMAAAA(data);
		else
			return "";
	}

	/**
	 * Formata uma data em HH:MM:SS
	 * 
	 * @param data
	 * @return HH:MM:SS
	 */
	public String formatarHora(Date data) {
		return Conversao.formatarDataHHMMSS(data);
	}

	/**
	 * Formata textos e substitui as imagens pelo caminho correto O endereço das
	 * imagens têm a notação {{{pasta/imagem.png}}}
	 * 
	 * @param texto
	 *            Texto a ser formatado
	 * @return texto Texto formatado
	 */
	public String formatarTextoComImagem(String texto) {
		return Fabrica.formatarTextoComImagem(texto);
	}

	/**
	 * Adiciona o caminho completo para a imagem
	 * 
	 * @param enderecoRelativo
	 * @return endereço do servlet
	 */
	public String formatarEnderecoImagemSemPasta(String enderecoRelativo) {
		return Fabrica.gerarEnderecoImagemSemPasta(enderecoRelativo);
	}

	/**
	 * Preenche alt default em imagens de banner
	 */
	public void preencheDescricaoBanner(Galeria banner, GaleriaImagem imagem) {
		if (imagem.getDescricaoImagem() == null) {

			String desc = Propriedade
					.getMensagemUtilizadoSessao("texto.imagemDo");

			if (banner.getNome() != null)
				imagem.setDescricaoImagem(desc + banner.getNome());
			else
				imagem.setDescricaoImagem(desc
						+ Propriedade
								.getMensagemUtilizadoSessao("texto.banner"));
		}
	}

	/**
	 * Transforma a primeira letra de uma string em maiúscula e as demais em
	 * minúsculas.
	 * 
	 * @param string
	 * @return
	 */
	public static String capitalize(String s) {
		try {

			return s.substring(0, 1).toUpperCase()
					+ (s.length() > 1 ? s.substring(1).toLowerCase() : "");
		} catch (Exception x) {
			return s;
		}
	}

	/**
	 * Formata a url do produto, separando seu nome com traços e adicionando seu
	 * código no final
	 * 
	 * @param produto
	 * @return
	 */
	public String formataUrlProduto(Produto produto) {
		if (produto == null) {
			return "/erro";
		}

		String nomeProdutoFormatado = formatarStringURL(produto.getDescricao(),
				true, true, true);

		String nomeGrupoFormatado = "";
		if (produto.getGrupoProduto() != null) {
			nomeGrupoFormatado += "/"
					+ formatarStringURL(produto.getGrupoProduto()
							.getDescricao(), true, true, false);
		}

		String nomeSubGrupoFormatado = "";
		if (produto.getSubgrupoProduto() != null) {
			nomeSubGrupoFormatado += "/"
					+ formatarStringURL(produto.getSubgrupoProduto()
							.getDescricao(), true, true, false);
		}

		return nomeGrupoFormatado + nomeSubGrupoFormatado + "/"
				+ nomeProdutoFormatado + "-" + produto.getCodigo();
	}

	public static String retirarAcento(String string) {
		string = Normalizer.normalize(string, Normalizer.Form.NFD);
		string = string.replaceAll("[^\\p{ASCII}]", "");
		return string;
	}

	/**
	 * 
	 * @param string
	 * @return string sem acentos, capitalizada e/ou com espaços transformados
	 *         em -
	 */
	public String formatarStringURL(String string, boolean retirarAcentos,
			boolean capitalizar, boolean separarComTracos) {
		StringBuilder url = new StringBuilder();

		if (retirarAcentos)
			string = retirarAcento(string);

		String texto[] = string.split("\\s");
		for (String palavra : texto) {

			if (capitalizar)
				palavra = capitalize(palavra);

			if (separarComTracos)
				url.append("-");

			url.append(palavra);
		}
		if (separarComTracos)
			url.append(url.toString().replaceFirst("-", ""));

		return url.toString();
	}

	/**
	 * Formata a url do produto, separando seu nome com traços e adicionando seu
	 * código no final
	 * 
	 * @param produto
	 * @return
	 */
	public static String formataUrlNomeProduto(CaixaProdutoVisualizavel produto) {

		if (produto == null
				|| !StringUtil.isValida(produto.getDescricaoVisualizacao())) {
			return "/produtos/";
		}

		String descricaoProduto = produto.getDescricaoVisualizacao().trim();

		descricaoProduto = StringUtil.removerAcentos(descricaoProduto);
		descricaoProduto = StringUtil
				.removerCaracteresEspeciais(descricaoProduto);
		descricaoProduto = StringUtil
				.removerEspacosDuplicados(descricaoProduto);

		String palavrasProduto[] = descricaoProduto.split("\\s");
		StringBuilder nomeProdutoFormatado = new StringBuilder();
		for (int i = 0; i < palavrasProduto.length; i++) {
			nomeProdutoFormatado.append(capitalize(palavrasProduto[i]));
			if (i < palavrasProduto.length - 1)
				nomeProdutoFormatado.append("-");
		}

		return nomeProdutoFormatado.toString().replaceAll("/", "").replaceAll(",", "");
	}

	public String formataUrlPesquisaProdutos(GrupoProduto grupo,
			SubGrupoProduto subGrupo) {
		return "";
	}

	public boolean isValida(String string) {

		return StringUtil.isValida(string);

	}
	
	public boolean isURLValida(String string) {

		return isValida(string) && !string.trim().contains(" ");
	}

	public String getPrimeiroNome(String nome) {

		try {

			if (StringUtil.isValida(nome)) {

				String nomeOriginal = new String(nome);

				String primeiroNome = "";

				while (primeiroNome.trim().length() <= 6
						&& !primeiroNome.equalsIgnoreCase(nome)) {

					int indiceEspaco = nome.indexOf(" ");

					if (indiceEspaco >= 0) {

						String pedaco = nome.substring(0, indiceEspaco + 1);

						if (pedaco.length() > 0) {

							primeiroNome += pedaco;

							nome = nome.replace(pedaco, "");
						}
					} else {
						primeiroNome = nomeOriginal;
					}
				}

				if (StringUtil.isValida(primeiroNome)) {
					return primeiroNome.trim().toLowerCase();
				} else {
					return nomeOriginal;
				}

			}

		} catch (Exception e) {
			e.printStackTrace();

		}
		return nome;
	}

	/**
	 * formata a url do youtube, preparando-a para exibição embutida no site.
	 * 
	 * @param url
	 * @param mostrarVideosRelacionados
	 * @return
	 */
	public String formatarUrlYoutube(String url,
			boolean mostrarVideosRelacionados) {
		url = url.replaceAll("http://", "");
		url = url.replaceAll("https://", "");

		url = "//" + url;
		url = url.replaceAll("watch\\?v=", "embed/");

		url = url.replaceAll("\\?rel=0", "");
		if (!mostrarVideosRelacionados)
			url += "?rel=0";

		return url;
	}
}
