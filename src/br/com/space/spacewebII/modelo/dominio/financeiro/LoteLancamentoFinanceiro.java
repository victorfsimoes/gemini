package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;

@Entity
@Table(name = "lotelanc")
public class LoteLancamentoFinanceiro implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	public static final String SITUACAO_CANCELADO = "C";

	public static final String NOME_TABELA = "lotelanc";
	public static final String COLUNA_CODIGO_SEQUENCIAL = "llc_codigo";
	public static final String COLUNA_FILIAL_CODIGO = "llc_filcodigo";
	public static final String COLUNA_SEQUENCIAL_AUXILIAR = "llc_seqaux";

	@EmbeddedId
	private LoteLancamentoFinanceiroPK loteLancamentoFinanceiroPK;

	@Column(name = "llc_valor")
	private double valor;

	@Column(name = "llc_numped")
	private int numeroPedidos;

	@Column(name = "llc_numdoc")
	private int numeroDocumentos;

	@Column(name = "llc_clbcodigo")
	private int colaboradorCodigo;

	@Column(name = "llc_data")
	private Date data;

	@Column(name = "llc_hora")
	private String hora;

	@Column(name = "llc_tiporecpag")
	private String flagTipoReceberPagar;

	@Column(name = "llc_situacao")
	private String situacao;

	@Column(name = "llc_lfccodigo")
	private int loteFechamentoCaixa;

	@Column(name = "llc_motestorno")
	private String motivoEstorno;

	@Column(name = "llc_automatico")
	private int flagAutomatico;

	@Column(name = "llc_confirma")
	private String flagConfirma;

	@Column(name = COLUNA_SEQUENCIAL_AUXILIAR)
	private String sequencialAuxiliar;

	@Column(name = "llc_numcte")
	private int numeroConhecimentoTransporteEletronico;

	/**
	 * 
	 */
	public LoteLancamentoFinanceiro() {

	}

	/**
	 * 
	 * @param loteLancamentoFinanceiroPK
	 * @param valor
	 * @param numeroPedidos
	 * @param numeroDocumentos
	 * @param colaboradorCodigo
	 * @param data
	 * @param hora
	 * @param flagTipoReceberPagar
	 * @param situacao
	 * @param loteFechamentoCaixa
	 * @param motivoEstorno
	 * @param flagAutomatico
	 * @param flagConfirma
	 * @param sequencialAuxiliar
	 * @param numeroConhecimentoTransporteEletronico
	 */
	public LoteLancamentoFinanceiro(int filialCodigo, double valor,
			int numeroPedidos, int numeroDocumentos, int colaboradorCodigo,
			Date data, String hora, String flagTipoReceberPagar,
			String situacao, int loteFechamentoCaixa, String motivoEstorno,
			int flagAutomatico, String flagConfirma, String sequencialAuxiliar,
			int numeroConhecimentoTransporteEletronico) {
		super();
		this.loteLancamentoFinanceiroPK = new LoteLancamentoFinanceiroPK(0,
				filialCodigo);
		this.valor = valor;
		this.numeroPedidos = numeroPedidos;
		this.numeroDocumentos = numeroDocumentos;
		this.colaboradorCodigo = colaboradorCodigo;
		this.data = data;
		this.hora = hora;
		this.flagTipoReceberPagar = flagTipoReceberPagar;
		this.situacao = situacao;
		this.loteFechamentoCaixa = loteFechamentoCaixa;
		this.motivoEstorno = motivoEstorno;
		this.flagAutomatico = flagAutomatico;
		this.flagConfirma = flagConfirma;
		this.sequencialAuxiliar = sequencialAuxiliar;
		this.numeroConhecimentoTransporteEletronico = numeroConhecimentoTransporteEletronico;
	}

	public int getFilialCodigo() {
		return this.getLoteLancamentoFinanceiroPK().getFilialCodigo();
	}

	public int getCodigoSequencial() {
		return this.getLoteLancamentoFinanceiroPK().getCodigoSequencial();
	}

	public LoteLancamentoFinanceiroPK getLoteLancamentoFinanceiroPK() {
		return loteLancamentoFinanceiroPK;
	}

	public void setLoteLancamentoFinanceiroPK(
			LoteLancamentoFinanceiroPK loteLancamentoFinanceiroPK) {
		this.loteLancamentoFinanceiroPK = loteLancamentoFinanceiroPK;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getNumeroPedidos() {
		return numeroPedidos;
	}

	public void setNumeroPedidos(int numeroPedidos) {
		this.numeroPedidos = numeroPedidos;
	}

	public int getNumeroDocumentos() {
		return numeroDocumentos;
	}

	public void setNumeroDocumentos(int numeroDocumentos) {
		this.numeroDocumentos = numeroDocumentos;
	}

	public int getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(int colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
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

	public String getFlagTipoReceberPagar() {
		return flagTipoReceberPagar;
	}

	public void setFlagTipoReceberPagar(String flagTipoReceberPagar) {
		this.flagTipoReceberPagar = flagTipoReceberPagar;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public int getLoteFechamentoCaixa() {
		return loteFechamentoCaixa;
	}

	public void setLoteFechamentoCaixa(int loteFechamentoCaixa) {
		this.loteFechamentoCaixa = loteFechamentoCaixa;
	}

	public String getMotivoEstorno() {
		return motivoEstorno;
	}

	public void setMotivoEstorno(String motivoEstorno) {
		this.motivoEstorno = motivoEstorno;
	}

	public int getFlagAutomatico() {
		return flagAutomatico;
	}

	public void setFlagAutomatico(int flagAutomatico) {
		this.flagAutomatico = flagAutomatico;
	}

	public String getFlagConfirma() {
		return flagConfirma;
	}

	public void setFlagConfirma(String flagConfirma) {
		this.flagConfirma = flagConfirma;
	}

	public String getSequencialAuxiliar() {
		return sequencialAuxiliar;
	}

	public void setSequencialAuxiliar(String sequencialAuxiliar) {
		this.sequencialAuxiliar = sequencialAuxiliar;
	}

	public int getNumeroConhecimentoTransporteEletronico() {
		return numeroConhecimentoTransporteEletronico;
	}

	public void setNumeroConhecimentoTransporteEletronico(
			int numeroConhecimentoTransporteEletronico) {
		this.numeroConhecimentoTransporteEletronico = numeroConhecimentoTransporteEletronico;
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

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param sequencialAuxiliar
	 * @return
	 */
	public static int recuperarUltimoCodigo(GenericoDAO dao, int filialCodigo,
			String sequencialAuxiliar) {

		String where = COLUNA_FILIAL_CODIGO + "=" + filialCodigo + " and "
				+ COLUNA_SEQUENCIAL_AUXILIAR + "=" + sequencialAuxiliar;

		return dao.maxResult(NOME_TABELA, COLUNA_CODIGO_SEQUENCIAL, where);
	}

	public static int cancelarLoteFinanceiro(GenericoDAO dao, IPedidoWeb pedido) {

		String updateLote = "update " + NOME_TABELA + " set llc_situacao = '"
				+ SITUACAO_CANCELADO + "' where " + COLUNA_CODIGO_SEQUENCIAL
				+ "= " + pedido.getLoteLancamentoFinanceiro() + " and "
				+ COLUNA_FILIAL_CODIGO + " = " + pedido.getFilialCodigo();

		FinanceiroGerencial.estornarFinanceiroGerencial(dao, pedido);

		return dao.executeUpdate(updateLote);
	}
}
