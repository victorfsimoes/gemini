package br.com.space.spacewebII.modelo.padrao.interfaces;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 *
 * @param <T>
 * 		Tipo do objeto que ser� apagado.
 */
public interface Apagavel<T> {

	/**
	 * Verifica se o usu�rio pode excluir um registro.
	 * 
	 * @return TRUE caso o usu�rio tenha permiss�o, caso contr�rio FALSE
	 */
	public boolean verificarPermissaoExcluir();

	/**
	 * Carregamento padr�o de exclus�o.
	 * 
	 * @param objetoSelecionado
	 *            O objeto que ser� excluido
	 * 
	 * @return A URL de destino
	 */
	public String carregarExclusao(T objetoSelecionado);

	/**
	 * O m�todo exclui o objeto em par�metro
	 * 
	 * @param t
	 *            O objeto que ser� apagado
	 * @param dao
	 *            GenericoDAO para a exclus�o
	 */
	public void apagar(T t, GenericoDAO<IPersistent> dao);

}
