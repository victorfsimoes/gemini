package br.com.space.spacewebII.modelo.util;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.net.URLEncoder;

import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Named;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;

/**
 * Gerencia os caminhos das pastas.
 * 
 * @author Desenvolvimento
 * 
 */
@ManagedBeanSessionScoped
public class Arquivo implements Serializable {

	private static final long serialVersionUID = 1L;

	public static final String URL_DOWNLOAD = "/downloadServlet";
	public static final String URL_LOGIN = "/login";
	public static final String EXTENSAO_ARQUIVO_LICENCA = ".lic";
	public static final String ARQUIVO_LOG_ERRO = "/erro.log";
	public static final String QUEBRA_LINHA_ARQUIVO = "/r/n";
	public static final String ARQUIVO_CONFIGURACAO = "configuracao.xml";

	/**
	 * Retorna o caminho da pasta Root.
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getRootPath() {
		ServletContext s = (ServletContext) FacesContext.getCurrentInstance()
				.getExternalContext().getContext();

		return s.getContextPath();
	}

	/**
	 * Retorna url do site sem http ou https
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getRootURL() {

		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) ec.getRequest();

		String servidor = request.getServerName();
		int porta = request.getServerPort();

		String url = servidor;

		if (porta != 443 && porta != 80)
			url += ":" + porta;

		return url;
	}

	/**
	 * Retorna a url do site completa até o nome da aplicação;
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getCompleteRootURL() {

		ExternalContext ec = FacesContext.getCurrentInstance()
				.getExternalContext();

		HttpServletRequest request = (HttpServletRequest) ec.getRequest();

		String scheme = request.getScheme(); // http ou https
		String servidor = request.getServerName();
		String contextPath = request.getContextPath(); // /mywebapp
		int porta = request.getServerPort();

		StringBuilder url = new StringBuilder(scheme + "://" + servidor);

		if (porta != 443 && porta != 80)
			url.append(":" + porta);

		url.append(contextPath);

		return url.toString();
	}

	/**
	 * Retorna o caminho absoluto da pasta img, ou seja, a partir da pasta raiz.
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getRootImagePath() {
		return getRootPath() + "/resources/img/";
	}

	public File getConfiguracaoFilePath() {

		File file = new File(getRootPath(), "conf");

		if (!file.exists()) {
			file.mkdirs();
		}

		return file;
	}

	/**
	 * Retorna o caminho relativo da pasta img, ou seja, a partir da pasta
	 * Webcontent.
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getImagePath() {
		return "/resources/img/";
	}

	/**
	 * Retorna o endereço da aplicação onde estao as imagens
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getImageApplication() {
		return Fabrica.gerarEnderecoAplicacaoImagem();
	}

	/**
	 * Retorna o endereço da aplicação mais o diretorio de logo
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getImageApplicationLogo() {
		return getImageApplication() + "logo/";
	}

	/**
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getImagePathFormasPagamento() {
		return getImagePath() + "formasPagamento/";
	}

	/**
	 * 
	 * @param formaPagamento
	 * @return
	 */
	public String getImageFormaPagamento(FormaPagamento formaPagamento) {
		String arquivo = "";

		if (!StringUtil.isValida(formaPagamento.getImagem())) {
			String tipoDoc = formaPagamento.getClassificaDocumento().getTipo()
					.toUpperCase().trim();

			if (tipoDoc.equals("B"))
				arquivo = "boleto.png";
			else if (tipoDoc.equals("H"))
				arquivo = "dinheiro.png";
			else if (tipoDoc.equals("C"))
				arquivo = "cheque.png";
			else if (tipoDoc.equals("T"))
				arquivo = "cartao_credito.png";

			if (arquivo.isEmpty())
				return "";
			else
				arquivo = getImagePathFormasPagamento() + arquivo;
		} else
			arquivo = Fabrica.gerarEnderecoImagemSemPasta(formaPagamento
					.getImagem());

		return arquivo;
	}

	/**
	 * Retorna o caminho relativo da pasta Page, ou seja, a partir da pasta
	 * Webcontent.
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getPagePath() {
		return "/pages/";
	}

	/**
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getRootPagePath() {
		return getRootPath() + getPagePath();
	}

	/**
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getSafePath() {
		return "/safe/";
	}

	/**
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getRootSafePath() {
		return getRootPath() + getSafePath();
	}

	/**
	 * 
	 * @return
	 */
	@Produces
	@Named
	public String getTempPath() {
		return "/temp/";
	}

	/**
	 * 
	 * @return o caminho dos documentos
	 */
	@Produces
	@Named
	public String getDocPath() {
		return "/resources/doc/";
	}

	/**
	 * 
	 * @return o caminho das releases
	 */
	@Produces
	@Named
	public String getReleasePath() {
		return "/resources/release/";
	}

	/**
	 * 
	 * @return url de download
	 */
	@Produces
	@Named
	public String getUrlDownloadServlet() {
		return URL_DOWNLOAD;
	}

	/**
	 * 
	 * @param url
	 * @return
	 */
	public String getUrlEncoded(String url) {

		try {
			url = URLEncoder.encode(url, "UTF8");
		} catch (Exception e) {
			e.printStackTrace();
		}

		return url;
	}

	public static File getPastaTemporaria() {

		File fileTemp = new File(
				br.com.space.api.core.util.Arquivo.getCaminhoPastaTemporaria(),
				"Gemini");

		if (!fileTemp.exists()) {
			fileTemp.mkdirs();
		}

		return fileTemp;
	}

	public static File getArquivoLogErroAtualizacaoTransacaoCartao() {

		File fileErro = new File(getPastaTemporaria(),
				"logErroAtualizacaoTransacaoCartao.log");

		if (!fileErro.exists()) {
			try {
				fileErro.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileErro;
	}
	
	public static File getArquivoLogErroGerentePagamentoCartao() {

		File fileErro = new File(getPastaTemporaria(),
				"logErroGerentePagamentoCartao.log");

		if (!fileErro.exists()) {
			try {
				fileErro.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileErro;
	}

	public static File getArquivoLogVerificacaoTransacaoCartao() {

		File fileLogVeridicacao = new File(getPastaTemporaria(),
				"logVerificacaoTransacaoCartao.log");

		if (!fileLogVeridicacao.exists()) {
			try {
				fileLogVeridicacao.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return fileLogVeridicacao;
	}

}
