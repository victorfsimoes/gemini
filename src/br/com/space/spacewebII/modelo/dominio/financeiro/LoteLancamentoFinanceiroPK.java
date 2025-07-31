package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class LoteLancamentoFinanceiroPK implements Serializable{

	private static final long serialVersionUID = 1L;

	@Column(name = "llc_codigo")
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int codigoSequencial;

	@Column(name = "llc_filcodigo")
	private int filialCodigo;

	public LoteLancamentoFinanceiroPK(int codigoSequencial, int filialCodigo) {
		super();
		this.codigoSequencial = codigoSequencial;
		this.filialCodigo = filialCodigo;
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
