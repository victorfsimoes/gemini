package br.com.space.spacewebII.modelo.dominio;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

import br.com.space.api.spa.model.domain.IPersistent;

public class GeradorMapeamentoHibernate {

	public static void main(String[] args) {

		GeradorMapeamentoHibernate gmh = new GeradorMapeamentoHibernate();

		gmh.gerarMapeamentoPasta(new File(
				"D:\\Projetos\\javaspace\\CFSw\\SpaceWebII\\src\\"
						+ "br\\com\\space\\spacewebII\\modelo\\dominio\\"),
				true);

	}

	List<String> listaClasses = new ArrayList<String>();

	/**
	 * 
	 * @param pastaBase
	 */
	private void gerarMapeamentoPasta(File pastaBase, boolean gerarArquivo) {
		try {
			listaClasses.add("");
			listaClasses.add("<!-- Mapeamento de: "
					+ pastaBase.getAbsolutePath() + " -->");

			/*
			 * Gera o mapeamento das classes existentes na pasta.
			 */
			gerarMapeamentoClasses(pastaBase);

			File[] arquivos = pastaBase.listFiles();

			for (File file : arquivos) {

				try {
					if (file.isDirectory()) {
						gerarMapeamentoPasta(file, false);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			if (gerarArquivo) {
				File file = new File(pastaBase.getAbsolutePath() + "\\"
						+ "mapeamentoHibernate.txt");

				Files.write(file.toPath(), listaClasses,
						StandardCharsets.US_ASCII);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param pastaBase
	 * @param listaClasses
	 */
	private void gerarMapeamentoClasses(File pastaBase) {
		File[] arquivos = pastaBase.listFiles();

		for (File file : arquivos) {

			BufferedReader in = null;
			try {

				if (file.isFile() && file.getName().endsWith(".java")) {

					in = new BufferedReader(new FileReader(file));
					String string;
					while ((string = in.readLine()) != null) {
						if (string.trim().startsWith("package")) {

							string = string.substring(7, string.length() - 1)
									.trim()
									+ "."
									+ file.getName().substring(0,
											file.getName().length() - 5);

							Class<?> c = Class.forName(string);

							try {
								/*
								 * Verifica se é possivel fazer um cast para
								 * IPersistent.
								 */
								c.asSubclass(IPersistent.class);
								listaClasses.add("<mapping class=\"" + string
										+ "\" />");
							} catch (Exception e) {
								// TODO: handle exception
							}
							break;
						}
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (in != null) {
					try {
						in.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static void gerarClasse(Class<?> classe) {

	}

}
