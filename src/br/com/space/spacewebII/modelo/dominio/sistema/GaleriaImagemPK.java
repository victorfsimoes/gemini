/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

import br.com.space.api.core.util.StringUtil;

/**
 * 
 * @author Sandro
 */
@Embeddable
public class GaleriaImagemPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = GaleriaImagem.COLUNA_GALERIACODIGO)
	private int galeriaCodigo;

	@Basic(optional = false)
	@Column(name = "gim_nomeimagem")
	private String nomeImagem;

	public GaleriaImagemPK() {
	}

	public GaleriaImagemPK(int gimGalcodigo, String gimNomeimagem) {
		this.galeriaCodigo = gimGalcodigo;
		this.nomeImagem = gimNomeimagem;
	}

	public int getGaleriaCodigo() {
		return galeriaCodigo;
	}

	public void setGaleriaCodigo(int galeriaCodigo) {
		this.galeriaCodigo = galeriaCodigo;
	}

	public String getNomeImagem() {
		if (StringUtil.isValida(nomeImagem)) {

			return nomeImagem.toLowerCase();
		}
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

}
