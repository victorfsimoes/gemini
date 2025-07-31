package br.com.space.spacewebII.modelo.dominio.sistema.log;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.spacewebII.modelo.dominio.venda.TransacaoPagamentoCartao;

@Embeddable
public class LogTransacaoPagamentoCartaoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = "ltp_seq")
	private int sequencia = 0;

	@Column(name = "ltp_filcodigo")
	private int filialCodigo = 0;

	@Column(name = "ltp_dataverif")
	@Temporal(TemporalType.DATE)
	private Date dataVerificacao = null;

	@Column(name = "ltp_horaverif")
	private String horaVerificacao = null;

	public LogTransacaoPagamentoCartaoPK() {
	}

	public LogTransacaoPagamentoCartaoPK(
			TransacaoPagamentoCartao transacaoPagamentoCartao) {

		this.sequencia = transacaoPagamentoCartao
				.getTransacaoPagamentoCartaoPK().getSequencial();
		this.filialCodigo = transacaoPagamentoCartao
				.getTransacaoPagamentoCartaoPK().getFilialCodigo();

		this.dataVerificacao = new Date();
		this.horaVerificacao = Conversao.formatarDataHHMMSS(dataVerificacao);
	}

	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Date getDataVerificacao() {
		return dataVerificacao;
	}

	public void setDataVerificacao(Date dataVerificacao) {
		this.dataVerificacao = dataVerificacao;
	}

	public String getHoraVerificacao() {
		return horaVerificacao;
	}

	public void setHoraVerificacao(String horaVerificacao) {
		this.horaVerificacao = horaVerificacao;
	}
}
