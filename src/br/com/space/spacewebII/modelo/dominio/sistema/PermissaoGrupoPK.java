package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class PermissaoGrupoPK implements Serializable {

	/**
	 * psg_grucodigo int(11) no pri psg_prgcodigo varchar(30) no pri
	 * psg_percodigo int(10) unsigned no pri
	 */

	private static final long serialVersionUID = 1L;

	@Column(name = "psg_grucodigo")
	private int codigoGrupo;

	@Column(name = "psg_prgcodigo")
	private String codigoPrograma;

	@Column(name = "psg_percodigo")
	private int codigoPermissao;

	public PermissaoGrupoPK() {
	}

	public PermissaoGrupoPK(int codigoGrupo, String codigoPrograma,
			int codigoPermissao) {
		super();
		this.codigoGrupo = codigoGrupo;
		this.codigoPrograma = codigoPrograma;
		this.codigoPermissao = codigoPermissao;
	}

	public int getCodigoGrupo() {
		return codigoGrupo;
	}

	public void setCodigoGrupo(int codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}

	public String getCodigoPrograma() {
		return codigoPrograma;
	}

	public void setCodigoPrograma(String codigoPrograma) {
		this.codigoPrograma = codigoPrograma;
	}

	public int getCodigoPermissao() {
		return codigoPermissao;
	}

	public void setCodigoPermissao(int codigoPermissao) {
		this.codigoPermissao = codigoPermissao;
	}

}
