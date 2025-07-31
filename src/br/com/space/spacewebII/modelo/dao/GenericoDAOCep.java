package br.com.space.spacewebII.modelo.dao;

import java.io.Serializable;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

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
public class GenericoDAOCep extends br.com.space.api.web.modelo.dao.GenericoDAO<IPersistent> implements Serializable {

	private static GenericoDAOCep daoLeitura;
	private static final long serialVersionUID = 1L;
	
	private static SessionFactory sessionFactory = null;
	private static Configuration configuration = null;

	public GenericoDAOCep() {
	}

	@PostConstruct
	public void posConstrutor() {

		try {
			ExternalContext ec = FacesContext.getCurrentInstance().getExternalContext();

			if (ec != null) {

				HttpSession httpSession = (HttpSession) ec.getSession(false);
				httpSession.setAttribute(AtributoSessao.GENERICO_DAOCEP, this);
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
			configuration = new AnnotationConfiguration().configure(getClass().getResource("/hibernateCep.cfg.xml"));

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

		StringBuilder where = new StringBuilder();

		if (whereFragmentos.size() > 0) {

			for (String string : whereFragmentos) {

				if (where.toString().equals("")) {
					where.append(string);

				} else {
					where.append(" and ").append(string);
				}
			}
		}
		return where.toString();
	}

	public static GenericoDAOCep getDaoLeitura() {
		if (daoLeitura == null) {
			daoLeitura = new GenericoDAOCep();
		}
		return daoLeitura;
	}

	public static void fechar(HttpSession httpSession) {
		try {
			if (httpSession != null) {

				GenericoDAOCep dao = (GenericoDAOCep) httpSession.getAttribute(AtributoSessao.GENERICO_DAOCEP);

				if (dao != null) {
					dao.disconnect();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
