package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Ronie
 * 
 */
@Embeddable
public class FormaPagamentoFilialPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic
	@Column(name = "fpf_fpgcodigo")
	private String formaPagamentoCodigo;

	@Basic
	@Column(name = "fpf_filcodigo")
	private int filialCodigo;

	public FormaPagamentoFilialPK() {

	}

	public String getFormaPagamentoCodigo() {
		return formaPagamentoCodigo;
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		this.formaPagamentoCodigo = formaPagamentoCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
