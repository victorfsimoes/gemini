package br.com.space.spacewebII.controle;

import java.util.ArrayList;

import br.com.space.api.core.email.EnvioEmail;
import br.com.space.api.core.email.modelo.EmailAnexo;
import br.com.space.api.core.sistema.Criptografia;

public class TesteEnvioEmail {

	public static void main(String[] args) {

		try {

			String senha = "/1!Q:8.J9W^\"1(*E<W1S5$%!";
			String login = "pedido.pacha@gmail.com";
			String servidor = "smtp.gmail.com";

			String descriptografarSenhaEmail = Criptografia.descriptografarSenhaEmail(senha);

			EnvioEmail email = new EnvioEmail(login, descriptografarSenhaEmail, servidor, 587);

			//String dest = "pedido.pacha@pachaalimentos.com";
			String dest = "renatoalves.infor@gmail.com";
			
			email.enviarEmail(login, "ARCOS COMERCIO IMPORTACAO", dest, "Envio de email",
					"Teste", new ArrayList<EmailAnexo>());
			
			System.out.println("fim");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
