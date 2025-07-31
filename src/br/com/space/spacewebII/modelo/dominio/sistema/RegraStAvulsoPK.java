package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Renato
 */
@Embeddable
public class RegraStAvulsoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "rsa_atvcodigo")
	private int atividadeCodigo = 0;

	@Column(name = "rsa_ufdestino")
	private String ufDestino = null;

	@Column(name = "rsa_uforigem")
	private String ufOrigem = null;

	@Column(name = "rsa_grtcodigo")
	private int grupoTributacaoCodigo = 0;

	@Column(name = "rsa_filcodigo")
	private int filialCodigo = 0;

	@Column(name = "rsa_natcodigo")
	private String naturezaOperacaoCodigo = null;

	@Column(name = "rsa_tipopessoa")
	private String tipoPessoa;

	public RegraStAvulsoPK() {
	}

	public int getAtividadeCodigo() {
		return atividadeCodigo;
	}

	public void setAtividadeCodigo(int atividadeCodigo) {
		this.atividadeCodigo = atividadeCodigo;
	}

	public String getUfDestino() {
		return ufDestino;
	}

	public void setUfDestino(String ufDestino) {
		this.ufDestino = ufDestino;
	}

	public String getUfOrigem() {
		return ufOrigem;
	}

	public void setUfOrigem(String ufOrigem) {
		this.ufOrigem = ufOrigem;
	}

	public int getGrupoTributacaoCodigo() {
		return grupoTributacaoCodigo;
	}

	public void setGrupoTributacaoCodigo(int grupoTributacaoCodigo) {
		this.grupoTributacaoCodigo = grupoTributacaoCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getNaturezaOperacaoCodigo() {
		return naturezaOperacaoCodigo;
	}

	public void setNaturezaOperacaoCodigo(String naturezaOperacaoCodigo) {
		this.naturezaOperacaoCodigo = naturezaOperacaoCodigo;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@Override
	public String toString() {
		return "GrupoStAvulsoPK [atividadeCodigo=" + atividadeCodigo
				+ ", ufDestino=" + ufDestino + ", ufOrigem=" + ufOrigem
				+ ", grupoTributacaoCodigo=" + grupoTributacaoCodigo
				+ ", filialCodigo=" + filialCodigo
				+ ", naturezaOperacaoCodigo=" + naturezaOperacaoCodigo
				+ ", tipoPessoa=" + tipoPessoa + "]";
	}
}
