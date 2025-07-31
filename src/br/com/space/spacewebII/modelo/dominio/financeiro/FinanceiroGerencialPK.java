package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class FinanceiroGerencialPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "fng_codigoseq")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigoSequencial = 0;

	@Column(name = "fng_filcodigo")
	private int filialCodigo = 0;

	public FinanceiroGerencialPK(int codigoSequencial, int filialcodigo) {
		this.filialCodigo = filialcodigo;
		this.codigoSequencial = codigoSequencial;
	}

	public int getCodigoSequencial() {
		return codigoSequencial;
	}

	public void setCodigoSequencial(int codigoSequencial) {
		this.codigoSequencial = codigoSequencial;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
