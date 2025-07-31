package br.com.space.spacewebII.modelo.aplicacao;

import java.io.Serializable;

/**
 * @author Renato
 * 
 */
public class Tema implements Serializable, Comparable<Tema> {

	private static final long serialVersionUID = 1L;
	private String idTema = null;
	private String nomeExibicao = null;
	private String imagem = null;
	private String imagemLogo = null;
	private String arquivoCss = null;

	public Tema() {

	}

	public Tema(String nomeExibicao, String idTema, String imagem,
			String imagemLogo, String arquivoCss) {
		super();
		this.idTema = idTema;
		this.nomeExibicao = nomeExibicao;
		this.imagem = imagem;
		this.imagemLogo = imagemLogo;
		this.arquivoCss = arquivoCss;
	}

	public String getIdTema() {
		return idTema;
	}

	public void setIdTema(String idTema) {
		this.idTema = idTema;
	}

	public String getNomeExibicao() {
		return nomeExibicao;
	}

	public void setNomeExibicao(String nomeExibicao) {
		this.nomeExibicao = nomeExibicao;
	}

	public String getImagem() {
		return imagem;
	}

	public void setImagem(String imagem) {
		this.imagem = imagem;
	}

	public String getImagemLogo() {
		return imagemLogo;
	}

	public void setImagemLogo(String imagemLogo) {
		this.imagemLogo = imagemLogo;
	}

	public String getArquivoCss() {
		return arquivoCss;
	}

	public void setArquivoCss(String arquivoCss) {
		this.arquivoCss = arquivoCss;
	}

	@Override
	public String toString() {
		return "Tema [nomeExibicao=" + nomeExibicao + " idTema= " + idTema
				+ " , imagem= " + imagem + "]";
	}

	@Override
	public int compareTo(Tema arg0) {
		return toString().compareTo(arg0.toString());
	}
}
