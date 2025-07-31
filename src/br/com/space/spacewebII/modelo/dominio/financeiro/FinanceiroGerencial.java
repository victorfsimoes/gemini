package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;

@Entity
@Table(name = "finang")
public class FinanceiroGerencial implements IPersistent {

	public static final String SITUACAO_ESTORNADO = "E";

	private static final String NOME_TABELA = "finang";

	private static final String COLUNA_CODIGO_SEQUENCIAL = "fng_codigoseq";
	private static final String COLUNA_FILIAL_CODIGO = "fng_filcodigo";
	private static final String COLUNA_SESSAO_CODIGO = "fng_sescodigo";

	private static final String COLUNA_LOTE_CODIGO = "fng_llccodigo";
	private static final String COLUNA_SITUACAO = "fng_situacao";

	@EmbeddedId
	private FinanceiroGerencialPK financeiroGerencialPK = null;

	@Column(name = "fng_pescodigo")
	private int pessoaCodigo = 0;

	@Column(name = "fng_fpgcodigo")
	private String formaPagamentoCodigo = null;

	@Column(name = "fng_numero")
	private String numeroDocumento = null;

	@Column(name = "fng_dtvencto")
	@Temporal(TemporalType.DATE)
	private Date dataVencimento = null;

	@Column(name = "fng_dtemissao")
	@Temporal(TemporalType.DATE)
	private Date dataEmissao = null;

	@Column(name = "fng_situacao")
	private String situacao = null;

	@Column(name = "fng_valor")
	private double valor = 0;

	@Column(name = "fng_acrescimo")
	private double acrescimo = 0;

	@Column(name = "fng_desconto")
	private double desconto = 0;

	@Column(name = "fng_valorpago")
	private double valorPago = 0;

	@Column(name = "fng_tiporecpag")
	private String flagTipoReceberPagar;

	@Column(name = "fng_dtpagto")
	@Temporal(TemporalType.DATE)
	private Date dataPagamento;

	@Column(name = "fng_clbcodigo")
	private int colaboradorCodigo;

	@Column(name = "fng_bnccodigo")
	private String bancoCodigo;

	@Column(name = "fng_llccodigo")
	private int loteLancamentoCodigo;

	@Column(name = "fng_ccucodigo")
	private int centroCustoCodigo;

	@Column(name = "fng_histlanc")
	private String historicoLancamento;

	@Column(name = "fng_plccodigo")
	private int planoContasCodigo;

	@Column(name = "fng_cctcodigo")
	private int contaCorrenteCodigo;

	@Column(name = "fng_prdcodigo")
	private int portadorCodigo;

	@Column(name = COLUNA_SESSAO_CODIGO)
	private int sessaoCodigo;

	@Column(name = "fng_endcodigo")
	private int enderecoCobrancaCodigo;

	@Column(name = "fng_percomis")
	private double percentualComissao;

	@Column(name = "fng_trpseq")
	private int transacaoPagamentoCartaoSequencial;

	/**
	 * 
	 */
	public FinanceiroGerencial() {

	}

	/**
	 * 
	 * @param filialCodigo
	 * @param flagTipoReceberPagar
	 * @param formaPagamentoCodigo
	 * @param numeroDocumento
	 * @param dataEmissao
	 * @param dataVencimento
	 * @param situacao
	 * @param valor
	 * @param acrescimo
	 * @param desconto
	 * @param valorPago
	 * @param colaboradorCodigo
	 * @param pessoaCodigo
	 * @param bancoCodigo
	 * @param centroCustoCodigo
	 * @param planoContasCodigo
	 * @param contaCorrenteCodigo
	 * @param portadorCodigo
	 * @param enderecoCobrancaCodigo
	 * @param percentualComissao
	 * @param historicoLancamento
	 * @param transacaoPagamentoCartaoSequencial
	 * @param sessaoCodigo
	 */
	public FinanceiroGerencial(int filialCodigo, String flagTipoReceberPagar,
			String formaPagamentoCodigo, String numeroDocumento,
			Date dataEmissao, Date dataVencimento, String situacao,
			double valor, double acrescimo, double desconto, double valorPago,
			int colaboradorCodigo, int pessoaCodigo, String bancoCodigo,
			int centroCustoCodigo, int planoContasCodigo,
			int contaCorrenteCodigo, int portadorCodigo,
			int enderecoCobrancaCodigo, double percentualComissao,
			String historicoLancamento, int transacaoPagamentoCartaoSequencial,
			int sessaoCodigo) {
		super();
		this.financeiroGerencialPK = new FinanceiroGerencialPK(0, filialCodigo);

		this.pessoaCodigo = pessoaCodigo;
		this.formaPagamentoCodigo = formaPagamentoCodigo;
		this.numeroDocumento = numeroDocumento;
		this.dataVencimento = dataVencimento;
		this.dataEmissao = dataEmissao;
		this.situacao = situacao;
		this.valor = valor;
		this.acrescimo = acrescimo;
		this.desconto = desconto;
		this.valorPago = valorPago;
		this.flagTipoReceberPagar = flagTipoReceberPagar;
		this.colaboradorCodigo = colaboradorCodigo;
		this.bancoCodigo = bancoCodigo;
		this.centroCustoCodigo = centroCustoCodigo;
		this.historicoLancamento = historicoLancamento;
		this.planoContasCodigo = planoContasCodigo;
		this.contaCorrenteCodigo = contaCorrenteCodigo;
		this.portadorCodigo = portadorCodigo;
		this.sessaoCodigo = sessaoCodigo;
		this.enderecoCobrancaCodigo = enderecoCobrancaCodigo;
		this.percentualComissao = percentualComissao;
		this.transacaoPagamentoCartaoSequencial = transacaoPagamentoCartaoSequencial;
	}

	public FinanceiroGerencialPK getFinanceiroGerencialPK() {
		return financeiroGerencialPK;
	}

	public void setFinanceiroGerencialPK(
			FinanceiroGerencialPK financeiroGerencialPK) {
		this.financeiroGerencialPK = financeiroGerencialPK;
	}

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public String getFormaPagamentoCodigo() {
		return formaPagamentoCodigo;
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		this.formaPagamentoCodigo = formaPagamentoCodigo;
	}

	public String getNumeroDocumento() {
		return numeroDocumento;
	}

	public void setNumeroDocumento(String numeroDocumento) {
		this.numeroDocumento = numeroDocumento;
	}

	public Date getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public Date getDataEmissao() {
		return dataEmissao;
	}

	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getAcrescimo() {
		return acrescimo;
	}

	public void setAcrescimo(double acrescimo) {
		this.acrescimo = acrescimo;
	}

	public double getDesconto() {
		return desconto;
	}

	public void setDesconto(double desconto) {
		this.desconto = desconto;
	}

	public double getValorPago() {
		return valorPago;
	}

	public void setValorPago(double valorPago) {
		this.valorPago = valorPago;
	}

	public String getFlagTipoReceberPagar() {
		return flagTipoReceberPagar;
	}

	public void setFlagTipoReceberPagar(String flagTipoReceberPagar) {
		this.flagTipoReceberPagar = flagTipoReceberPagar;
	}

	public Date getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(Date dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public int getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(int colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public String getBancoCodigo() {
		return bancoCodigo;
	}

	public void setBancoCodigo(String bancoCodigo) {
		this.bancoCodigo = bancoCodigo;
	}

	public int getLoteLancamentoCodigo() {
		return loteLancamentoCodigo;
	}

	public void setLoteLancamentoCodigo(int loteLancamentoCodigo) {
		this.loteLancamentoCodigo = loteLancamentoCodigo;
	}

	public int getCentroCustoCodigo() {
		return centroCustoCodigo;
	}

	public void setCentroCustoCodigo(int centroCustoCodigo) {
		this.centroCustoCodigo = centroCustoCodigo;
	}

	public String getHistoricoLancamento() {
		return historicoLancamento;
	}

	public void setHistoricoLancamento(String historicoLancamento) {
		this.historicoLancamento = historicoLancamento;
	}

	public int getPlanoContasCodigo() {
		return planoContasCodigo;
	}

	public void setPlanoContasCodigo(int planoContasCodigo) {
		this.planoContasCodigo = planoContasCodigo;
	}

	public int getContaCorrenteCodigo() {
		return contaCorrenteCodigo;
	}

	public void setContaCorrenteCodigo(int contaCorrenteCodigo) {
		this.contaCorrenteCodigo = contaCorrenteCodigo;
	}

	public int getPortadorCodigo() {
		return portadorCodigo;
	}

	public void setPortadorCodigo(int portadorCodigo) {
		this.portadorCodigo = portadorCodigo;
	}

	public int getSessaoCodigo() {
		return sessaoCodigo;
	}

	public void setSessaoCodigo(int sessaoCodigo) {
		this.sessaoCodigo = sessaoCodigo;
	}

	public int getEnderecoCobrancaCodigo() {
		return enderecoCobrancaCodigo;
	}

	public void setEnderecoCobrancaCodigo(int enderecoCobrancaCodigo) {
		this.enderecoCobrancaCodigo = enderecoCobrancaCodigo;
	}

	public double getPercentualComissao() {
		return percentualComissao;
	}

	public void setPercentualComissao(double percentualComissao) {
		this.percentualComissao = percentualComissao;
	}

	public int getTransacaoPagamentoCartaoSequencial() {
		return transacaoPagamentoCartaoSequencial;
	}

	public void setTransacaoPagamentoCartaoSequencial(
			int transacaoPagamentoCartaoSequencial) {
		this.transacaoPagamentoCartaoSequencial = transacaoPagamentoCartaoSequencial;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		// TODO Auto-generated method stub
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param sessaoCodigo
	 * @return
	 */
	public static int recuperarUltimoCodigo(GenericoDAO dao, int filialCodigo,
			int sessaoCodigo) {

		String where = COLUNA_FILIAL_CODIGO + "=" + filialCodigo + " and "
				+ COLUNA_SESSAO_CODIGO + "=" + sessaoCodigo;

		return dao.maxResult(NOME_TABELA, COLUNA_CODIGO_SEQUENCIAL, where);

	}

	public static int estornarFinanceiroGerencial(GenericoDAO dao,
			IPedidoWeb pedido) {

		String updateFinanceiro = "update " + NOME_TABELA + " set "
				+ COLUNA_SITUACAO + " = '" + SITUACAO_ESTORNADO + "' where "
				+ COLUNA_LOTE_CODIGO + " = "
				+ pedido.getLoteLancamentoFinanceiro() + " and "
				+ COLUNA_FILIAL_CODIGO + " = " + pedido.getFilialCodigo();

		return dao.executeUpdate(updateFinanceiro);

	}
}
