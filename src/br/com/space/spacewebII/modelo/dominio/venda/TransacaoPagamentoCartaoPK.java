package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;

@Embeddable
public class TransacaoPagamentoCartaoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "trp_seq")
	private int sequencial;

	@Basic(optional = false)
	@Column(name = "trp_filcodigo")
	private int filialCodigo;

	public TransacaoPagamentoCartaoPK() {

	}

	public TransacaoPagamentoCartaoPK(int sequencial, int filialCodigo) {
		super();
		this.sequencial = sequencial;
		this.filialCodigo = filialCodigo;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
