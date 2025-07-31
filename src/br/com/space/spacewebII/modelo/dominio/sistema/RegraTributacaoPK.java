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
public class RegraTributacaoPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "rtb_atvcodigo")
	private int atividadeCodigo;

	@Basic(optional = false)
	@Column(name = "rtb_ufdestino")
	private String ufDestino;

	@Basic(optional = false)
	@Column(name = "rtb_grtcodigo")
	private int grupoTributacaoCodigo;

	@Basic(optional = false)
	@Column(name = "rtb_natcodigo")
	private String naturezaOperacaoCodigo;

	@Basic(optional = false)
	@Column(name = "rtb_tipopessoa")
	private String tipoPessoa;

	@Basic(optional = false)
	@Column(name = "rtb_filial")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "rtb_uforigem")
	private String ufOrigem;

	public RegraTributacaoPK() {
	}

	public RegraTributacaoPK(int rtbAtvcodigo, String rtbUfdestino,
			int rtbGrtcodigo, String rtbNatcodigo, String rtbTipopessoa,
			int rtbFilial, String rtbUforigem) {
		this.atividadeCodigo = rtbAtvcodigo;
		this.ufDestino = rtbUfdestino;
		this.grupoTributacaoCodigo = rtbGrtcodigo;
		this.naturezaOperacaoCodigo = rtbNatcodigo;
		this.tipoPessoa = rtbTipopessoa;
		this.filialCodigo = rtbFilial;
		this.ufOrigem = rtbUforigem;
	}

	public int getRtbAtvcodigo() {
		return atividadeCodigo;
	}

	public void setRtbAtvcodigo(int rtbAtvcodigo) {
		this.atividadeCodigo = rtbAtvcodigo;
	}

	public String getRtbUfdestino() {
		return ufDestino;
	}

	public void setRtbUfdestino(String rtbUfdestino) {
		this.ufDestino = rtbUfdestino;
	}

	public int getRtbGrtcodigo() {
		return grupoTributacaoCodigo;
	}

	public void setRtbGrtcodigo(int rtbGrtcodigo) {
		this.grupoTributacaoCodigo = rtbGrtcodigo;
	}

	public String getRtbNatcodigo() {
		return naturezaOperacaoCodigo;
	}

	public void setRtbNatcodigo(String rtbNatcodigo) {
		this.naturezaOperacaoCodigo = rtbNatcodigo;
	}

	public String getRtbTipopessoa() {
		return tipoPessoa;
	}

	public void setRtbTipopessoa(String rtbTipopessoa) {
		this.tipoPessoa = rtbTipopessoa;
	}

	public int getRtbFilial() {
		return filialCodigo;
	}

	public void setRtbFilial(int rtbFilial) {
		this.filialCodigo = rtbFilial;
	}

	public String getRtbUforigem() {
		return ufOrigem;
	}

	public void setRtbUforigem(String rtbUforigem) {
		this.ufOrigem = rtbUforigem;
	}

}
