package br.com.space.spacewebII.modelo.padrao.interfaces;

import java.io.Serializable;

/**
 * Define atributos de sessão.
 * 
 * @author Desenvolvimento
 * 
 */
public interface AtributoSessao extends Serializable {

	public String USUARIO_LOGADO = "USUARIO_LOGADO";
	public String USER_TOMCAT = "user";
	public String CODIGO_SESSAO_GUARDIAN = "CODIGO_SESSAO_GUARDIAN";
	public String SESSAO_GUARDIAN = "SESSAO_GUARDIAN";

	public String PROPRIEDADE = "PROPRIEDADE";

	public String MENSAGEM_ERRO = "MENSAGEM_ERRO";
	public String MAPA_URL = "MAPA_URL";
	public String GENERICO_DAO = "GENERICO_DAO";
	public String GENERICO_DAOLOG = "GENERICO_DAOLOG";
	public String GENERICO_DAOCEP = "GENERICO_DAOCEP";
	public String BASE_PATH = "BASE_PATH";
	public String CONFIGURACAO = "CONFIGURACAO";

	public String URL_REDIRECIONAMENTO_ERRO = "URL_REDIRECIONAMENTO_ERRO";
	public String URL_ORIGEM_ERRO = "URL_ORIGEM_ERRO";
	public String COMANDO_VOLTAR_ERRO = "COMANDO_VOLTAR_ERRO";

	public String RODAPE_LINKS = "RODAPE_LINKS";
	public String RODAPE_TITULOS_GRUPOS = "RODAPE_TITULOS_GRUPOS";

	public String MENSAGEM_EXCECAO_ERROGENERICO = "MENSAGEM_EXCECAO_REDIRECIONATO";

}
