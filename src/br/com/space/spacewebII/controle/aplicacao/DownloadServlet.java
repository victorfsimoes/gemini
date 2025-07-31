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
 * Servlet implementation class UploadServlet Classe responsável pelo download
 * de arquivos fornecidos pelo sistema
 * 
 * Parâmetros: file: arquivo que será baixado delete (TRUE ou FALSE): depois do
 * download o arquivo será deletado
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
			 * Obtém os parâmetros da requisição
			 */
			String fullFilePath = request.getParameter("file");
			String delete = request.getParameter("delete");
			//String gerenciador = request.getParameter("gerenciador");

			if (fullFilePath == null) {

				response.getWriter().append("Path não é valido");

				return;

			}

			// String basePath = Arquivo.getRootPath();

			/**
			 * Garante que o path sempre inicia com a pasta host da aplicação.
			 * Dessa forma evita-se de ter acesso às pastas externas ao contexto
			 * da aplicação.
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

			// Cria uma instância do arquivo de origem.
			File sourceFile = new File(fullFilePath);

			// Prepara o stream somente se o arquivo existir.
			if (sourceFile.exists() && sourceFile.isFile()) {
				// Preenche o cabeçalho do response com as informações do
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

				// Cria o Stream para onde será enviado o arquivo.
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
