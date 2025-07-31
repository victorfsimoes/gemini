package br.com.space.spacewebII.modelo.padrao.interfaces;

import java.io.Serializable;

import br.com.space.spacewebII.modelo.propriedade.Propriedade;

/**
 * Interface que padroniza um contrato para criação de Gerentes MB
 * 
 * @author Desenvolvimento
 * 
 */
public interface IGerenteMB extends Serializable, CarregarListener {

	/**
	 * Este método deve retornar o nome do programa para fins de verifcação de
	 * permissão.
	 * 
	 * @return Nome do programa, para a verificação de permissão.
	 */
	public String getNomePrograma();

	/**
	 * Retorna a URL da view.
	 * 
	 * @return A URL da página correspondente ao Bean
	 */
	public String getUrlView();

	/**
	 * Retorna a URL da view que deve ser exibida quando clicar no botão voltar.
	 * 
	 * @return URl de retorno
	 */
	public String getUrlViewVoltar();

	/**
	 * Método realiza a verificação da permissão baseado no nome do programa.
	 * 
	 * @return TRUE: caso o usuário tenha permissão de acessar o recurso, caso
	 *         contrário: FALSE.
	 */
	public void verificarPermissao();

	/**
	 * Este método é responsável por carregar/inicializar conteúdos que serão
	 * utilizados na página.
	 */
	public void carregarObjetos();

	/**
	 * Este método tem por finalidade retornar a instância usada para a
	 * internacionalização
	 * 
	 * @return Uma instância válida para internacionalização
	 */
	public Propriedade getPropriedade();

	/**
	 * Método que será chamado logo após que um método de carregamento tenha
	 * sido iniciado (carregarEdicao, carregarNovo...). Este método padroniza um
	 * carregamento de página por operação
	 */
	public String carregarView();
}
