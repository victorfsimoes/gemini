package br.com.space.spacewebII.modelo.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
import org.hibernate.cfg.Configuration;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.padrao.interfaces.AtributoSessao;

/**
 * Classe responsável por moldar a conexão com o banco de dados.
 * 
 * @author Desenvolvimento
 * 
 */
@SessionScoped
public class GenericoDAO extends br.com.space.api.web.modelo.dao.GenericoDAO<IPersistent>
		implements Serializable, AutoCloseable {
	// private static long count = 0;
	private static GenericoDAO daoLeitura;
	private static final long serialVersionUID = 1L;

	private static SessionFactory sessionFactory = null;
	private static Configuration configuration = null;

	public GenericoDAO() {
	}

	@PostConstruct
	public void posConstrutor() {

		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			if (ec != null) {

				HttpSession httpSession = (HttpSession) ec.getSession(false);
				httpSession.setAttribute(AtributoSessao.GENERICO_DAO, this);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	protected void finalize() throws Throwable {
		super.finalize();
	}

	/**
	 * Retorna as configurações da conexão a partir do arquivo HIBERNATE.CFG.XML
	 * do Hibernate.
	 */
	@Override
	public Configuration getConfiguration() {
		try {
			configuration = new AnnotationConfiguration().configure(getClass().getResource("/hibernate.cfg.xml"));

		} catch (Exception e) {
			e.printStackTrace();
		}

		return configuration;
	}

	/**
	 * Abre uma nova sessão do Hibernate para conexão com o Banco de Dados.
	 * 
	 */
	@Override
	public SessionFactory getSessionFactory() {

		if (sessionFactory == null) {

			try {
				sessionFactory = getConfiguration().buildSessionFactory();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		return sessionFactory;
	}

	/**
	 * Recupera do Banco de Dados uma lista com ordenação.
	 * 
	 * @param <E>
	 * @param classe
	 * @param orderBy
	 * @return
	 */
	public <E extends IPersistent> List<E> recuperar(Class<E> classe, String orderBy) {

		return super.list(classe, null, null, orderBy, null);
	}

	/**
	 * Cria a WHERE do SELECT, ou seja, os filtros de pesquisa.
	 * 
	 * @param whereFragmentos
	 * @return where
	 */
	public static String criarWhere(List<String> whereFragmentos) {

		if (whereFragmentos == null || whereFragmentos.size() == 0)
			return "";

		StringBuilder where = new StringBuilder();

		for (String string : whereFragmentos) {

			if (where.length() > 0)
				where.append(" and ");

			where.append(string);
		}

		return where.toString();
	}

	/**
	 * 
	 * @return
	 */
	public static GenericoDAO getDaoLeitura() {
		if (daoLeitura == null) {
			daoLeitura = new GenericoDAO();
		}
		return daoLeitura;
	}

	/**
	 * 
	 * @param context
	 * @return
	 */
	public static GenericoDAO getDao(FacesContext context) {

		if (context != null && context.getExternalContext() != null
				&& context.getExternalContext().getSession(false) != null) {

			return getDao(((HttpSession) context.getExternalContext().getSession(false)));

		}

		return getDaoLeitura();
	}

	public static void fechar(HttpSession httpSession) {
		try {
			if (httpSession != null) {

				GenericoDAO dao = (GenericoDAO) httpSession.getAttribute(AtributoSessao.GENERICO_DAO);

				if (dao != null) {
					dao.sessionClose();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retorna o dao que estiver na sessao caso não tenha nenhum retorna o dao
	 * Somente Leitura.
	 * 
	 * @param httpSession
	 * @return
	 */
	public static GenericoDAO getDao(HttpSession httpSession) {

		GenericoDAO dao = null;

		if (httpSession != null) {
			dao = (GenericoDAO) httpSession.getAttribute(AtributoSessao.GENERICO_DAO);
		}

		if (dao == null) {

			dao = getDaoLeitura();
		}
		return dao;
	}

	/**
	 * Fecha um resultSet aberto anteriormente.
	 * 
	 * @param resultSet
	 */
	public static void closeResultSet(ResultSet resultSet) {
		br.com.space.api.web.modelo.dao.GenericoDAO.closeResultSet(resultSet);
	}

	@Override
	public void close() throws Exception {
		disconnect();
	}
}
