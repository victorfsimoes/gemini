package br.com.space.spacewebII.modelo.aplicacao;

import java.io.File;
import java.io.Serializable;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.util.Arquivo;

@ManagedBeanSessionScoped
public class Configuracao implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	Parametros parametros;

	@Inject
	GenericoDAO dao;

	private static AtributoConfiguracao atributos = null;

	/**
	 * 
	 * @return
	 */
	public static AtributoConfiguracao getAtributosConfiguracao() {
		if (atributos == null) {
			try {
				File file = null;
				file = new File(Configuracao.class.getResource(
						"/" + Arquivo.ARQUIVO_CONFIGURACAO).getFile());
				atributos = AtributoConfiguracao.carregarXml(file);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if (atributos == null) {
			atributos = new AtributoConfiguracao();
		}

		return atributos;
	}

	/**
	 * 
	 */
	public Configuracao() {
		getAtributosConfiguracao();
	}

	/**
	 * 
	 */
	@PostConstruct
	public void posConstrutor() {
		try {
			parametros.recuperarUnico(dao, atributos.getFilialPadraoCodigo());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public AtributoConfiguracao getAtributos() {
		return atributos;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		File file = null;
		file = new File("d:/temp/" + Arquivo.ARQUIVO_CONFIGURACAO);

		if (file != null && !file.exists()) {

			AtributoConfiguracao atributos = new AtributoConfiguracao();
			atributos.gravarEmXml(file);
		}
	}

}
