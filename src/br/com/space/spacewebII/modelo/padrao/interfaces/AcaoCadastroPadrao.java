package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Controla as ações num cadastro padrão.
 * 
 * @author Desenvolvimento
 * 
 */
public interface AcaoCadastroPadrao {

	/**
	 * 
	 * Método responsável por executar as operações realizadas por esta classe.
	 * EX: Inclusão, exclusão...
	 * 
	 * @return A view de redirecionamento, return null caso aconteça algum erro.
	 * 
	 */
	public String confirmar();

	/**
	 * Ação acionada pela tela de voltar para a página anterior
	 * 
	 * @return A view de redirecionamento.
	 */
	public String voltar();

}
