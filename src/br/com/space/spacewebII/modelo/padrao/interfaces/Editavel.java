package br.com.space.spacewebII.modelo.padrao.interfaces;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 *
 * @param <T>
 * 		Tipo do objeto que ser� editado
 */
public interface Editavel<T> {

	/**
	 * Verifica se o usu�rio pode incluir um registro.
	 * 
	 * @param objetoSelecionado
	 *            O objeto que ser� editado
	 * 
	 * @return TRUE: caso o usu�rio tenha permiss�o, caso contr�rio: FALSE.
	 */
	public boolean verificarPermissaoAlterar();

	/**
	 * Carregamento padr�o de edic�o.
	 * 
	 * @param objetoSelecionado
	 *            O objeto que ser� editado
	 * 
	 * @return A URL de destino
	 */
	public String carregarEdicao(T objetoSelecionado);

	/**
	 * Edita o objeto em par�metro
	 * 
	 * @param t
	 *            Objeto a ser editado
	 * @param dao
	 *            GenericoDAO para a altera��o
	 */
	public void editar(T t, GenericoDAO<IPersistent> dao);
}
