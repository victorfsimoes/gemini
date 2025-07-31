package br.com.space.spacewebII.controle.aplicacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.aplicacao.Tema;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;

@ManagedBeanSessionScoped
public class EscolhaTemaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private ParametroWebMB parametroWebMB;

	private ArrayList<Tema> temas = null;

	private Tema temaPadrao = null;
	private Tema temaEscolhido = null;

	public EscolhaTemaMB() {

		temaPadrao = new Tema("Azul", "redmond", "temaRedmond.png",
				"logo_azul.png", "azul_redmond.css");

		carregarListaTemas();

		/*
		 * Seta o tema padrao
		 */
		setTemaEscolhido(temaPadrao);

	}

	/**
	 * Carrega a lista de temas
	 */
	private void carregarListaTemas() {

		temas = new ArrayList<Tema>();

		temas.add(temaPadrao);

		temas.add(new Tema("Vermelho", "blitzer", "temaBlitzer.png",
				"logo_vermelho.png", "vermelho_blitzer.css"));
		temas.add(new Tema("Rosa", "rosa", "temaBlitzer.png", "logo_rosa.png",
				"rosa_custom.css"));
		temas.add(new Tema("Colorido", "hot-sneaks", "temaHot-sneaks.png",
				"logo_colorido.png", "colorido_hot-sneaks.css"));
		temas.add(new Tema("Laranja", "ui-lightness", "temaUi-lightness.png",
				"logo_laranja.png", "laranja_ui-lightness.css"));
		temas.add(new Tema("Verde", "south-street", "temaSouth-street.png",
				"logo_verde.png", "verde_south-street.css"));
		temas.add(new Tema("Preto", "black-tie", "temaBlack-tie.png",
				"logo_preto.png", "preto_black-tie.css"));
		temas.add(new Tema("Cinza Escuro", "home", "temaHome.png",
				"logo_cinza_escuro.png", "cinzaEscuro_home.css"));

		Collections.sort(temas);
	}

	@PostConstruct
	public void postConstruct() {
		try {
			carregarTemaPadrao();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String imagemLogo(String nomeTema) {
		nomeTema = nomeTema.trim();
		nomeTema = nomeTema.replaceAll(" ", "_");
		return "logo_" + nomeTema.toLowerCase() + ".png";
	}

	public static String corEmail(String nomeTema) {
		if (nomeTema.equals("Azul"))
			return "#1ba4e7";
		else if (nomeTema.equals("Vermelho"))
			return "#FF0000";
		else if (nomeTema.equals("Colorido"))
			return "rgb(100, 124, 132)";
		else if (nomeTema.equals("Laranja"))
			return "#FFA500";
		else if (nomeTema.equals("Verde"))
			return "#006200";
		else if (nomeTema.equals("Preto"))
			return "black";
		else if (nomeTema.equals("Cinza Escuro"))
			return "#555555";
		else
			return "silver";

	}

	/**
	 * Carrega no sistema o tema padrao
	 */
	private void carregarTemaPadrao() {

		ParametroWeb parametroWeb = parametroWebMB.getParametroWeb();

		if (parametroWeb != null
				&& StringUtil.isValida(parametroWeb.getTemaWebNome())) {

			setTemaEscolhido(parametroWeb.getTemaWebNome());

		}
	}

	/**
	 * 
	 * @return
	 */
	public String getIDTemaEscolhido() {
		return temaEscolhido.getIdTema();
	}

	/**
	 * 
	 * @return
	 */
	public String getArquivoCssCores() {
		return temaEscolhido.getArquivoCss();
	}

	public Tema getTemaEscolhido() {
		return temaEscolhido;
	}

	public void setTemaEscolhido(Tema temaEscolhido) {
		this.temaEscolhido = temaEscolhido;
	}

	/**
	 * 
	 * @param nomeTemaEscolhido
	 */
	public void setTemaEscolhido(String nomeTemaEscolhido) {
		if (nomeTemaEscolhido != null && !nomeTemaEscolhido.isEmpty()) {

			for (Tema tema : temas) {
				if (tema.getNomeExibicao().equals(nomeTemaEscolhido)) {

					setTemaEscolhido(tema);

					break;
				}
			}
		}
	}

	/**
	 * Escolha de tema
	 * 
	 * private String escolherTema(int idTema) {
	 * 
	 * setTemaEscolhido(temas.get(idTema));
	 * setTemaEscolhido(temaEscolhido.getNomeExibicao());
	 * 
	 * return "/pages/pesquisaProdutosHome.xhtml"; }
	 */
}
