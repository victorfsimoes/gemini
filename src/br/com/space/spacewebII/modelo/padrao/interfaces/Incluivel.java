package br.com.space.spacewebII.modelo.padrao.interfaces;

import java.io.Serializable;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 *
 * @param <T>
 * 		Tipo do objeto a ser incluído.
 */
public interface Incluivel<T> {

	/**
	 * Verifica se o usuário pode incluir um registro.
	 * 
	 * @return TRUE: caso o usuário tenha permissão, caso contrário: FALSE.
	 */
	public boolean verificarPermissaoIncluir();

	/**
	 * Carregamento padrão de inclusão.
	 * 
	 * @return A URL de destino
	 */
	public String carregarNovo();

	/**
	 * O método inclui um registro no banco de dados e retorna sua chave.
	 * 
	 * @param t
	 *            Objeto no qual será incluído.
	 * @param dao
	 *            GenericoDAO para a inserção
	 * @return Chave do objeto inserido
	 */
	public Serializable incluir(T t, GenericoDAO<IPersistent> dao);

}
