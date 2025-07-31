package br.com.space.spacewebII.modelo.aplicacao;

import java.io.Serializable;

public class RegistroNavegacao implements Serializable {

	private static final long serialVersionUID = 1L;
	private String url;
	private long tempoMilesegundos = 0;

	public RegistroNavegacao() {

	}

	public RegistroNavegacao(String url, long tempoMilesegundos) {
		super();
		this.url = url;
		this.tempoMilesegundos = tempoMilesegundos;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public long getTempoMilesegundos() {
		return tempoMilesegundos;
	}

	public void setTempoMilesegundos(long tempoMilesegundos) {
		this.tempoMilesegundos = tempoMilesegundos;
	}

}
