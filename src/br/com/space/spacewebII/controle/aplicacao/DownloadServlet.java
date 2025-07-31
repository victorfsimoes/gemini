package br.com.space.spacewebII.controle.aplicacao;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class UploadServlet Classe respons�vel pelo download
 * de arquivos fornecidos pelo sistema
 * 
 * Par�metros: file: arquivo que ser� baixado delete (TRUE ou FALSE): depois do
 * download o arquivo ser� deletado
 * 
 * @author Desenvolvimento
 * 
 */
// @WebServlet(Arquivo.URL_DOWNLOAD)
public class DownloadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DownloadServlet() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			/*
			 * Obt�m os par�metros da requisi��o
			 */
			String fullFilePath = request.getParameter("file");
			String delete = request.getParameter("delete");
			//String gerenciador = request.getParameter("gerenciador");

			if (fullFilePath == null) {

				response.getWriter().append("Path n�o � valido");

				return;

			}

			// String basePath = Arquivo.getRootPath();

			/**
			 * Garante que o path sempre inicia com a pasta host da aplica��o.
			 * Dessa forma evita-se de ter acesso �s pastas externas ao contexto
			 * da aplica��o.
			 * 
			 * Troca todas as barras para o caracter separador do ambiente.
			 */
			fullFilePath = fullFilePath.replace("/", File.separator);
			fullFilePath = fullFilePath.replace("\\", File.separator);
			fullFilePath = fullFilePath.replace("..", "");
			if (!fullFilePath.startsWith(File.separator)) {
				fullFilePath = File.separator + fullFilePath;
			}
			// fullFilePath = arquivo.getBasePath(this.getServletContext())+
			// fullFilePath;

			// Cria uma inst�ncia do arquivo de origem.
			File sourceFile = new File(fullFilePath);

			// Prepara o stream somente se o arquivo existir.
			if (sourceFile.exists() && sourceFile.isFile()) {
				// Preenche o cabe�alho do response com as informa��es do
				// arquivo.
				ServletContext context = getServletConfig().getServletContext();
				String mimetype = context.getMimeType(fullFilePath);

				response.setContentType((mimetype != null) ? mimetype
						: "application/octet-stream");
				response.setContentLength((int) sourceFile.length());
				response.setHeader("Content-Disposition",
						"attachment; filename=\"" + sourceFile.getName() + "\"");

				// Cria o Stream para leitura do arquivo de origem.
				FileInputStream fileIn = new FileInputStream(sourceFile);

				// Cria o Stream para onde ser� enviado o arquivo.
				DataOutputStream dataOut = new DataOutputStream(
						response.getOutputStream());

				int c = 30720;

				byte b[] = new byte[c];

				try {
					for (int j = fileIn.read(b); j >= 0; j = fileIn.read(b)) {
						dataOut.write(b, 0, j);
						dataOut.flush();
					}

					dataOut.flush();

				} catch (Exception e) {
					throw e;
				} finally {
					dataOut.close();
					fileIn.close();
				}

				try {
					if (delete.equalsIgnoreCase("true")) {
						sourceFile.delete();
					}
				} catch (Exception e) {
				}

			} else {
				// response.sendError(HttpVikingResponse.SC_ARQUIVO_NAO_ENCONTRADO,"Not Found!");
				return;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			// response.sendError(HttpVikingResponse.SC_ERRO_INTERNO,ex.getMessage());
		}
	}
}
