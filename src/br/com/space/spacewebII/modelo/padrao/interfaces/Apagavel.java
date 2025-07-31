package br.com.space.spacewebII.modelo.padrao.interfaces;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 *
 * @param <T>
 * 		Tipo do objeto que será apagado.
 */
public interface Apagavel<T> {

	/**
	 * Verifica se o usuário pode excluir um registro.
	 * 
	 * @return TRUE caso o usuário tenha permissão, caso contrário FALSE
	 */
	public boolean verificarPermissaoExcluir();

	/**
	 * Carregamento padrão de exclusão.
	 * 
	 * @param objetoSelecionado
	 *            O objeto que será excluido
	 * 
	 * @return A URL de destino
	 */
	public String carregarExclusao(T objetoSelecionado);

	/**
	 * O método exclui o objeto em parâmetro
	 * 
	 * @param t
	 *            O objeto que será apagado
	 * @param dao
	 *            GenericoDAO para a exclusão
	 */
	public void apagar(T t, GenericoDAO<IPersistent> dao);

}
