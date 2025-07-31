package br.com.space.spacewebII.modelo.pesquisa;

import javax.faces.event.ValueChangeEvent;

public class Filtro {

	private String titulo;
	private int tamanhoCampo;
	private String value;
	private String campo;
	private String tipoCampo;

	public Filtro() {
		titulo = new String();
		value = null;
	}

	public Filtro(String titulo, int tamanhoCampo, String campo,
			String tipoCampo, String value) {
		super();
		this.titulo = titulo;
		this.tamanhoCampo = tamanhoCampo;
		this.campo = campo;
		this.tipoCampo = tipoCampo;
		this.value = value;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	public int getTamanhoCampo() {
		return tamanhoCampo;
	}

	public void setTamanhoCampo(int tamanhoCampo) {
		this.tamanhoCampo = tamanhoCampo;
	}

	public String getCampo() {
		return campo;
	}

	public void setCampo(String campo) {
		this.campo = campo;
	}

	public String getTipoCampo() {
		return tipoCampo;
	}

	public void setTipoCampo(String tipoCampo) {
		this.tipoCampo = tipoCampo;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public void valueChangeListener(ValueChangeEvent e) {
		value = (String) e.getNewValue();
	}

}
