package br.com.space.spacewebII.modelo.dominio.sistema.log;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;
import br.com.space.spacewebII.modelo.dominio.venda.TransacaoPagamentoCartao;

@Entity
@Table(name = "logtranspagcartao")
public class LogTransacaoPagamentoCartao implements IPersistent {

	@EmbeddedId
	private LogTransacaoPagamentoCartaoPK logTransacaoPagamentoCartaoPK = null;

	@Column(name = "ltp_spvcodigo")
	private String pedidoSerie;

	@Column(name = "ltp_pednumero")
	private int pedidoNumero;

	@Column(name = "ltp_idtrans")
	private String idTransacao;

	@Column(name = "ltp_idtransloc")
	private String idTransacaoLocal;

	@Column(name = "ltp_status")
	private String status;

	@Column(name = "ltp_statuscod")
	private String statusCodigo;

	@Column(name = "ltp_obs")
	private String observacao = null;

	public LogTransacaoPagamentoCartao() {

	}

	public LogTransacaoPagamentoCartao(
			TransacaoPagamentoCartao transacaoPagamentoCartao, String observacao) {

		logTransacaoPagamentoCartaoPK = new LogTransacaoPagamentoCartaoPK(
				transacaoPagamentoCartao);

		this.pedidoNumero = transacaoPagamentoCartao.getPedidoNumero();
		this.pedidoSerie = transacaoPagamentoCartao.getPedidoSerie();

		this.idTransacao = transacaoPagamentoCartao.getIdTransacao();
		this.idTransacaoLocal = transacaoPagamentoCartao.getIdTransacaoLocal();

		this.status = transacaoPagamentoCartao.getStatus();
		this.statusCodigo = transacaoPagamentoCartao.getStatusCodigo();

		this.observacao = observacao;
	}

	public LogTransacaoPagamentoCartaoPK getLogTransacaoPagamentoCartaoPK() {
		return logTransacaoPagamentoCartaoPK;
	}

	public void setLogTransacaoPagamentoCartaoPK(
			LogTransacaoPagamentoCartaoPK logTransacaoPagamentoCartaoPK) {
		this.logTransacaoPagamentoCartaoPK = logTransacaoPagamentoCartaoPK;
	}

	public String getPedidoSerie() {
		return pedidoSerie;
	}

	public void setPedidoSerie(String pedidoSerie) {
		this.pedidoSerie = pedidoSerie;
	}

	public int getPedidoNumero() {
		return pedidoNumero;
	}

	public void setPedidoNumero(int pedidoNumero) {
		this.pedidoNumero = pedidoNumero;
	}

	public String getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(String idTransacao) {
		this.idTransacao = idTransacao;
	}

	public String getIdTransacaoLocal() {
		return idTransacaoLocal;
	}

	public void setIdTransacaoLocal(String idTransacaoLocal) {
		this.idTransacaoLocal = idTransacaoLocal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusCodigo() {
		return statusCodigo;
	}

	public void setStatusCodigo(String statusCodigo) {
		this.statusCodigo = statusCodigo;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	public static boolean gravarLog(GenericoDAOLog daoLog,
			TransacaoPagamentoCartao transacao, String observacao) {

		if (transacao != null) {
			try {

				daoLog.beginTransaction();

				Serializable serializable = daoLog
						.insertObject(new LogTransacaoPagamentoCartao(
								transacao, observacao));

				daoLog.commitTransaction();

				return serializable != null;

			} catch (Exception e) {
				daoLog.rollBackTransaction();
				e.printStackTrace();
			}
		}
		return false;
	}
}
