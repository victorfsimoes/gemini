package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.cartao.modelo.CartaoTipo;
import br.com.space.api.cartao.modelo.StatusTransacao;
import br.com.space.api.cartao.modelo.resposta.IRetornoTransacao;
import br.com.space.api.spa.model.dao.db.Table;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.financeiro.CredencialAdministradoraCartao;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;

@Entity
@javax.persistence.Table(name = "transpagcartao")
@XmlRootElement
public class TransacaoPagamentoCartao implements IPersistent, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String COLUNA_FILIAL_CODIGO = "trp_filcodigo";
	public static final String COLUNA_SERIE_PEDIDO = "trp_spvcodigo";
	public static final String COLUNA_NUMERO_PEDIDO = "trp_pednumero";
	public static final String COLUNA_FORMAPAGAMENTO_CODIGO = "trp_fpgcodigo";
	public static final String COLUNA_CONDICAOPAGAMENTO_CODIGO = "trp_cpgcodigo";
	public static final String COLUNA_STATUS = "trp_status";

	@EmbeddedId
	private TransacaoPagamentoCartaoPK transacaoPagamentoCartaoPK;

	@Column(name = COLUNA_SERIE_PEDIDO)
	private String pedidoSerie;

	@Column(name = COLUNA_NUMERO_PEDIDO)
	private int pedidoNumero;

	@Column(name = "trp_data")
	private Date data;

	@Column(name = "trp_hora")
	private String hora;

	@Column(name = COLUNA_FORMAPAGAMENTO_CODIGO)
	private String formaPagamentoCodigo;

	@Column(name = COLUNA_CONDICAOPAGAMENTO_CODIGO)
	private int condicaoPagamentoCodigo;

	/**
	 * D = 'DEBITO' c = 'CREDITO'
	 */
	@Column(name = "trp_cartdebcred")
	private String tipoCartaoDebitoCredito;

	@Column(name = "trp_idtrans")
	private String idTransacao;

	@Column(name = "trp_idtransloc")
	private String idTransacaoLocal;

	@Column(name = "trp_numeroaut")
	private String numeroAutorizacao;

	@Column(name = "trp_numeronsu")
	private String numeroComprovanteVenda;

	@Column(name = COLUNA_STATUS)
	private String status;

	@Column(name = "trp_statuscod")
	private String statusCodigo;

	@Column(name = "trp_statusmsg")
	private String statusMensagem;

	@Column(name = "trp_valor")
	private double valor;

	@Column(name = "trp_caccodigo")
	private int credencialCodigo;

	@Column(name = "trp_compvenda")
	private String comprovanteVenda;

	@Column(name = "trp_tentivcanc")
	private int tentativasCancelamento = 0;

	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "trp_caccodigo", referencedColumnName = CredencialAdministradoraCartao.COLUNA_CODIGO, insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private CredencialAdministradoraCartao credencialAdministradoraCartao;

	@Column(name = "trp_log")
	private String log;

	/**
	 * 
	 */
	public TransacaoPagamentoCartao() {
	}

	/**
	 * 
	 * @param filialCodigo
	 * @param pedidoSerie
	 * @param pedidoNumero
	 * @param data
	 * @param hora
	 * @param formaPagamentoCodigo
	 * @param credencialCodigo
	 * @param numeroTransacao
	 * @param status
	 * @param statusCodigo
	 * @param statusMensagem
	 * @param valor
	 * @param log
	 */
	public TransacaoPagamentoCartao(int filialCodigo, String pedidoSerie,
			int pedidoNumero, Date data, String hora,
			String formaPagamentoCodigo, int condicaoPagamentoCodigo,
			int credencialCodigo, String numeroTransacao, String status,
			String statusCodigo, String statusMensagem, double valor, String log) {
		super();
		this.transacaoPagamentoCartaoPK = new TransacaoPagamentoCartaoPK(0,
				filialCodigo);
		this.pedidoSerie = pedidoSerie;
		this.pedidoNumero = pedidoNumero;
		this.data = data;
		this.hora = hora;
		this.formaPagamentoCodigo = formaPagamentoCodigo;
		this.condicaoPagamentoCodigo = condicaoPagamentoCodigo;
		this.credencialCodigo = credencialCodigo;
		this.idTransacao = numeroTransacao;
		this.status = status;
		this.statusCodigo = statusCodigo;
		this.statusMensagem = statusMensagem;
		this.valor = valor;
		this.log = log;
	}

	public TransacaoPagamentoCartaoPK getTransacaoPagamentoCartaoPK() {
		return transacaoPagamentoCartaoPK;
	}

	public void setTransacaoPagamentoCartaoPK(
			TransacaoPagamentoCartaoPK transacaoPagamentoCartaoPK) {
		this.transacaoPagamentoCartaoPK = transacaoPagamentoCartaoPK;
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

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getFormaPagamentoCodigo() {
		return formaPagamentoCodigo;
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		this.formaPagamentoCodigo = formaPagamentoCodigo;
	}

	public void setCondicaoPagamentoCodigo(int condicaoPagamentoCodigo) {
		this.condicaoPagamentoCodigo = condicaoPagamentoCodigo;
	}

	public int getCondicaoPagamentoCodigo() {
		return condicaoPagamentoCodigo;
	}

	public String getTipoCartaoDebitoCredito() {
		return tipoCartaoDebitoCredito;
	}

	public CartaoTipo geCartaoTipo() {

		switch (tipoCartaoDebitoCredito) {
		case "D":
		case "d":
			return CartaoTipo.debit;
		case "C":
		case "c":
			return CartaoTipo.credit;
		}
		return null;
	}

	public void setTipoCartaoDebitoCredito(String tipoCartaoDebitoCredito) {
		this.tipoCartaoDebitoCredito = tipoCartaoDebitoCredito;
	}

	public String getIdTransacao() {
		return idTransacao;
	}

	public void setIdTransacao(String idTransacao) {
		this.idTransacao = idTransacao;
	}

	/**
	 * E o codigo que ira compor o codigo de referencia na transação com cartão
	 * de credito
	 * 
	 * @return
	 */
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

	public String getStatusMensagem() {
		return statusMensagem;
	}

	public void setStatusMensagem(String statusMensagem) {
		this.statusMensagem = statusMensagem;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getCredencialCodigo() {
		return credencialCodigo;
	}

	public void setCredencialCodigo(int credencialCodigo) {
		this.credencialCodigo = credencialCodigo;
	}

	public String getComprovanteVenda() {
		return comprovanteVenda;
	}

	public void setComprovanteVenda(String comprovanteVenda) {
		this.comprovanteVenda = comprovanteVenda;
	}

	public int getTentativasCancelamento() {
		return tentativasCancelamento;
	}

	public void setTentativasCancelamento(int tentativasCancelamento) {
		this.tentativasCancelamento = tentativasCancelamento;
	}

	public void incrementarTentativasCancelamento() {
		this.tentativasCancelamento++;
	}

	public CredencialAdministradoraCartao getCredencialAdministradoraCartao() {
		return credencialAdministradoraCartao;
	}

	public void setCredencialAdministradoraCartao(
			CredencialAdministradoraCartao credencialAdministradoraCartao) {
		this.credencialAdministradoraCartao = credencialAdministradoraCartao;
	}

	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}

	public String getNumeroAutorizacao() {
		return numeroAutorizacao;
	}

	public void setNumeroAutorizacao(String numeroAutorizacao) {
		this.numeroAutorizacao = numeroAutorizacao;
	}

	public String getNumeroComprovanteVenda() {
		return numeroComprovanteVenda;
	}

	public void setNumeroComprovanteVenda(String numeroComprovanteVenda) {
		this.numeroComprovanteVenda = numeroComprovanteVenda;
	}

	public boolean isCancelarPorTimeout() {

		return !getStatusTransacao().equals(StatusTransacao.Confirmada)
				&& !getStatusTransacao().equals(StatusTransacao.Cancelada)
				&& isTimeout();
	}

	/**
	 * 
	 * @param quantidadeMaximaTentativasCancelamento
	 *            este valor deve ser
	 *            {@link ParametroWeb#getQuantidadeMaximaTentativasCancelamento()
	 *            quantidade maxima de tentativa de cancelamento} definida no
	 *            parametro web
	 * @return TRUE caso o numero de tentativas de cancelamento seja menor que a
	 *         {@link ParametroWeb#getQuantidadeMaximaTentativasCancelamento()
	 *         quantidade maxima de tentativa de cancelamento} em parametro
	 */
	public boolean isPermiteTentarCancelar(
			int quantidadeMaximaTentativasCancelamento) {
		return getTentativasCancelamento() < quantidadeMaximaTentativasCancelamento;
	}

	public boolean isTimeout() {

		Calendar dataLimite = Calendar.getInstance();

		dataLimite.clear();

		dataLimite.setTime(getData());
		dataLimite.set(Calendar.HOUR_OF_DAY, 23);
		dataLimite.set(Calendar.MINUTE, 59);
		dataLimite.set(Calendar.SECOND, 59);

		dataLimite.add(Calendar.DAY_OF_MONTH,
				getCredencialAdministradoraCartao().getDiasExpiraTransacao());

		boolean timeOut = System.currentTimeMillis() > dataLimite
				.getTimeInMillis();

		return timeOut;
	}

	public void setDados(IRetornoTransacao retornoTransacao) {

		this.setStatusCodigo(retornoTransacao.getCodigoRetorno());

		this.setStatusMensagem(retornoTransacao.getMensagemRetorno());

		this.setStatus(retornoTransacao.getStatusTransacao() == null ? getStatus()
				: retornoTransacao.getStatusTransacao().toSigla());

		this.setNumeroAutorizacao(retornoTransacao.getNumeroAutorizacao());
		this.setNumeroComprovanteVenda(retornoTransacao
				.getNumeroComprovanteVenda());

		this.setLog(retornoTransacao.getConteudoRetorno());

		this.setComprovanteVenda(retornoTransacao.getComprovanteVenda());
	}

	@Override
	public Table getTable() {
		return null;
	}

	@Override
	public void setTable(Table table) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * 
	 * @return
	 */
	public StatusTransacao getStatusTransacao() {
		return StatusTransacao.parseSigla(this.getStatus());
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param seriePedido
	 * @param pedidoNumero
	 * @param formaPagamentoCodigo
	 * @param somenteAtivo
	 * @return
	 */
	public static List<TransacaoPagamentoCartao> recuperar(GenericoDAO dao,
			int filialCodigo, String seriePedido, int pedidoNumero,
			String formaPagamentoCodigo, boolean somenteAtivo) {

		/*
		 * Canceladas e não autorizadas não são pesquisadas.
		 */
		String[] status = null;

		if (somenteAtivo) {
			status = new String[] { "ABE", "AUT", "CNF" };
		}

		return recuperar(dao, filialCodigo, seriePedido, pedidoNumero,
				formaPagamentoCodigo, status);
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param seriePedido
	 * @param pedidoNumero
	 * @param formaPagamentoCodigo
	 * @param status
	 * @return
	 */
	public static List<TransacaoPagamentoCartao> recuperar(GenericoDAO dao,
			int filialCodigo, String seriePedido, int pedidoNumero,
			String formaPagamentoCodigo, String[] status) {

		StringBuilder where = new StringBuilder(COLUNA_FILIAL_CODIGO + " = "
				+ filialCodigo + " and " + COLUNA_SERIE_PEDIDO + "= '"
				+ seriePedido + "' and " + COLUNA_NUMERO_PEDIDO + " = "
				+ pedidoNumero);

		if (formaPagamentoCodigo != null && formaPagamentoCodigo.length() > 0) {
			where.append(" and " + COLUNA_FORMAPAGAMENTO_CODIGO + "'"
					+ formaPagamentoCodigo + "'");
		}

		/*
		 * Canceladas e não autorizadas não são pesquisadas. Comentado por
		 * Renato, são verificadas todas os status para que se tome um decisão
		 * 
		 * if (status != null && status.length > 0) {
		 * 
		 * StringBuilder filtroStatus = new StringBuilder(); for (String sts :
		 * status) { if (filtroStatus.length() > 0) filtroStatus.append(",");
		 * filtroStatus.append("'").append(sts).append("'"); }
		 * 
		 * where.append(" and " + COLUNA_STATUS + " IN (" + filtroStatus + ")");
		 * }
		 */

		return dao.list(TransacaoPagamentoCartao.class, where.toString(), null,
				null, null);
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param seriePedido
	 * @param pedidoNumero
	 * @param formaPagamentoCodigo
	 * @return
	 */
	public static List<TransacaoPagamentoCartao> recuperarConfirmadas(
			GenericoDAO dao, int filialCodigo, String seriePedido,
			int pedidoNumero, String formaPagamentoCodigo) {

		String[] status = new String[] { "CNF" };

		return recuperar(dao, filialCodigo, seriePedido, pedidoNumero,
				formaPagamentoCodigo, status);
	}

	/**
	 * 
	 * @param dao
	 * @param idTransacao
	 * @return
	 */
	public static TransacaoPagamentoCartao recuperarUnico(GenericoDAO dao,
			String idTransacaoLocal) {
		return dao.uniqueResult(TransacaoPagamentoCartao.class,
				"trp_idtransloc = '" + idTransacaoLocal + "'", null);
	}

	public static void atulizarIdTransacao(GenericoDAO dao, String novoId,
			String idTransacaoLocal) throws Exception {

		String update = "update transpagcartao set trp_idtrans = '" + novoId
				+ "' where trp_idtransloc = '" + idTransacaoLocal
				+ "' and trp_idtrans <> '" + novoId + "'";

		try {

			dao.beginTransaction();

			dao.executeUpdate(update);

			dao.commitTransaction();

		} catch (Exception e) {
			dao.rollBackTransaction();
			throw e;
		}
	}

	public boolean isPermiteAtualizarId() {
		return true;
	}

	@Override
	public String toString() {
		return "TransacaoPagamentoCartao [pedidoSerie="
				+ pedidoSerie
				+ ", pedidoNumero="
				+ pedidoNumero
				+ ", idTransacao="
				+ idTransacao
				+ ", idTransacaoLocal="
				+ idTransacaoLocal
				+ ", status="
				+ status
				+ ", statusCodigo="
				+ statusCodigo
				+ ", statusMensagem="
				+ statusMensagem
				+ ", credencialAdministradoraCartao="
				+ (credencialAdministradoraCartao != null ? credencialAdministradoraCartao
						: credencialCodigo) + "]";
	}

}
