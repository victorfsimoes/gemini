package br.com.space.spacewebII.modelo.padrao.interfaces;

import java.io.Serializable;

import br.com.space.spacewebII.modelo.propriedade.Propriedade;

/**
 * Interface que padroniza um contrato para cria��o de Gerentes MB
 * 
 * @author Desenvolvimento
 * 
 */
public interface IGerenteMB extends Serializable, CarregarListener {

	/**
	 * Este m�todo deve retornar o nome do programa para fins de verifca��o de
	 * permiss�o.
	 * 
	 * @return Nome do programa, para a verifica��o de permiss�o.
	 */
	public String getNomePrograma();

	/**
	 * Retorna a URL da view.
	 * 
	 * @return A URL da p�gina correspondente ao Bean
	 */
	public String getUrlView();

	/**
	 * Retorna a URL da view que deve ser exibida quando clicar no bot�o voltar.
	 * 
	 * @return URl de retorno
	 */
	public String getUrlViewVoltar();

	/**
	 * M�todo realiza a verifica��o da permiss�o baseado no nome do programa.
	 * 
	 * @return TRUE: caso o usu�rio tenha permiss�o de acessar o recurso, caso
	 *         contr�rio: FALSE.
	 */
	public void verificarPermissao();

	/**
	 * Este m�todo � respons�vel por carregar/inicializar conte�dos que ser�o
	 * utilizados na p�gina.
	 */
	public void carregarObjetos();

	/**
	 * Este m�todo tem por finalidade retornar a inst�ncia usada para a
	 * internacionaliza��o
	 * 
	 * @return Uma inst�ncia v�lida para internacionaliza��o
	 */
	public Propriedade getPropriedade();

	/**
	 * M�todo que ser� chamado logo ap�s que um m�todo de carregamento tenha
	 * sido iniciado (carregarEdicao, carregarNovo...). Este m�todo padroniza um
	 * carregamento de p�gina por opera��o
	 */
	public String carregarView();
}
