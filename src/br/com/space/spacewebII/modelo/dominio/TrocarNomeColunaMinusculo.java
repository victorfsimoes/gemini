package br.com.space.spacewebII.modelo.dominio;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.List;

public class TrocarNomeColunaMinusculo {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		trocarNomesColunas(new File(
				"D:\\Projetos\\javaspace\\CFSw\\SpaceWebII\\src\\br\\com\\space\\spacewebII\\modelo\\dominio\\temporario2\\"));

	}

	private static void trocarNomesColunas(File pastaBase) {

		File[] arquivos = pastaBase.listFiles();

		for (File file : arquivos) {

			try {
				List<String> linhas = Files.readAllLines(file.toPath(),
						StandardCharsets.UTF_8);

				for (String string : linhas) {
					if (string.trim().startsWith("@Column")
							|| string.trim().startsWith("@JoinColumn")
							|| string.trim().startsWith("@JoinTable")) {

						String s2 = "";
						boolean abriu = false;

						for (int pos = 0; pos < string.length(); pos++) {

							String s3 = string.substring(pos, pos + 1);

							if (s3.compareTo("\"") == 0) {
								abriu = !abriu;
							}

							if (abriu) {
								s3 = s3.toLowerCase();
							}

							s2 += s3;
						}

						linhas.set(linhas.indexOf(string), s2);
					}
				}

				Files.write(file.toPath(), linhas, StandardCharsets.UTF_8);

			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}
}
