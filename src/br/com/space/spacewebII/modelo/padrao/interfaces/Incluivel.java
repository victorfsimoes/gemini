package br.com.space.spacewebII.modelo.padrao.interfaces;

import java.io.Serializable;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 *
 * @param <T>
 * 		Tipo do objeto a ser inclu�do.
 */
public interface Incluivel<T> {

	/**
	 * Verifica se o usu�rio pode incluir um registro.
	 * 
	 * @return TRUE: caso o usu�rio tenha permiss�o, caso contr�rio: FALSE.
	 */
	public boolean verificarPermissaoIncluir();

	/**
	 * Carregamento padr�o de inclus�o.
	 * 
	 * @return A URL de destino
	 */
	public String carregarNovo();

	/**
	 * O m�todo inclui um registro no banco de dados e retorna sua chave.
	 * 
	 * @param t
	 *            Objeto no qual ser� inclu�do.
	 * @param dao
	 *            GenericoDAO para a inser��o
	 * @return Chave do objeto inserido
	 */
	public Serializable incluir(T t, GenericoDAO<IPersistent> dao);

}
