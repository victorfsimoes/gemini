/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Sandro
 */
@Embeddable
public class LinkPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "lnk_grupo")
	private String grupo;

	@Basic(optional = false)
	@Column(name = "lnk_titulo")
	private String titulo;

	public LinkPK() {
	}

	public LinkPK(String lnkGrupo, String lnkTitulo) {
		this.grupo = lnkGrupo;
		this.titulo = lnkTitulo;
	}

	public String getGrupo() {
		return grupo;
	}

	public void setGrupo(String grupo) {
		this.grupo = grupo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

}
