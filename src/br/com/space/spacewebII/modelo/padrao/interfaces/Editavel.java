package br.com.space.spacewebII.modelo.padrao.interfaces;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 *
 * @param <T>
 * 		Tipo do objeto que será editado
 */
public interface Editavel<T> {

	/**
	 * Verifica se o usuário pode incluir um registro.
	 * 
	 * @param objetoSelecionado
	 *            O objeto que será editado
	 * 
	 * @return TRUE: caso o usuário tenha permissão, caso contrário: FALSE.
	 */
	public boolean verificarPermissaoAlterar();

	/**
	 * Carregamento padrão de edicão.
	 * 
	 * @param objetoSelecionado
	 *            O objeto que será editado
	 * 
	 * @return A URL de destino
	 */
	public String carregarEdicao(T objetoSelecionado);

	/**
	 * Edita o objeto em parâmetro
	 * 
	 * @param t
	 *            Objeto a ser editado
	 * @param dao
	 *            GenericoDAO para a alteração
	 */
	public void editar(T t, GenericoDAO<IPersistent> dao);
}
