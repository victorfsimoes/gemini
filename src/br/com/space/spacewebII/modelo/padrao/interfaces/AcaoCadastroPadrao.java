package br.com.space.spacewebII.modelo.padrao.interfaces;

/**
 * Controla as a��es num cadastro padr�o.
 * 
 * @author Desenvolvimento
 * 
 */
public interface AcaoCadastroPadrao {

	/**
	 * 
	 * M�todo respons�vel por executar as opera��es realizadas por esta classe.
	 * EX: Inclus�o, exclus�o...
	 * 
	 * @return A view de redirecionamento, return null caso aconte�a algum erro.
	 * 
	 */
	public String confirmar();

	/**
	 * A��o acionada pela tela de voltar para a p�gina anterior
	 * 
	 * @return A view de redirecionamento.
	 */
	public String voltar();

}
