/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.negocio.modelo.dominio.IOferta;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = Oferta.NOME_TABELA)
@XmlRootElement
public class Oferta implements Serializable, IOferta {
	private static final long serialVersionUID = 1L;

	public static final String NOME_TABELA = "oferta";

	public static final String COLUNA_NUMERO = "oft_numero";
	public static final String COLUNA_PRODUTO_CODIGO = "oft_procodigo";
	public static final String COLUNA_FILIAL_CODIGO = "oft_filcodigo";
	public static final String COLUNA_DATA_INICIAL = "oft_dataini";
	public static final String COLUNA_DATA_FINAL = "oft_datafim";
	public static final String COLUNA_FLAG_VALIDA_HORA = "oft_valhora";
	public static final String COLUNA_HORA_INICIAL = "oft_horaini";
	public static final String COLUNA_HORA_FINAL = "oft_horafim";
	public static final String COLUNA_STATUS = "oft_status";

	@EmbeddedId
	protected OfertaPK ofertaPK;

	@Column(name = COLUNA_STATUS)
	private int status;

	@Column(name = COLUNA_DATA_INICIAL)
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(name = COLUNA_DATA_FINAL)
	@Temporal(TemporalType.DATE)
	private Date dataFim;

	@Column(name = COLUNA_FLAG_VALIDA_HORA)
	private int flagValidaHora;

	@Column(name = "oft_tipo")
	private String tipoOferta;

	@Column(name = "oft_mensagem")
	private String mensagem;

	@Column(name = "oft_descmax")
	private double descontoMaximo;

	@Column(name = "oft_acremax")
	private double acrescimoMaximo;

	@Column(name = "oft_precooferta")
	private double precoOferta;

	@Column(name = "oft_qtdeoferta")
	private double quantidadeOfertada;

	@Column(name = "oft_qtdemaxcli")
	private double quantidadeMaximaCliente;

	@Column(name = "oft_saldooferta")
	private double saldoOferta;

	@Column(name = "oft_usrlogin")
	private String usuarioLogin;

	@Column(name = "oft_datacanc")
	@Temporal(TemporalType.DATE)
	private Date dataCancelamento;

	@Column(name = "oft_horacanc")
	private String horaCancelamento;

	@Column(name = "oft_przmaximo")
	private int prazoMaximo;

	@Column(name = COLUNA_HORA_INICIAL)
	private String horaInicio;

	@Column(name = COLUNA_HORA_FINAL)
	private String horaFim;

	@Column(name = "oft_dataalt")
	@Temporal(TemporalType.DATE)
	private Date dataAlteracao;

	@Column(name = "oft_horaalt")
	private String horaAlteracao;

	@Column(name = "oft_perdesc")
	private Double percentualDesconto;

	@Column(name = COLUNA_PRODUTO_CODIGO)
	private int produtoCodigo;

	@Column(name = "oft_prbcodigo")
	private int precoBaseCodigo;

	public Oferta() {
	}

	public Oferta(OfertaPK ofertaPK) {
		this.ofertaPK = ofertaPK;
	}

	public OfertaPK getOfertaPK() {
		return ofertaPK;
	}

	public void setOfertaPK(OfertaPK ofertaPK) {
		this.ofertaPK = ofertaPK;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public int getFlagValidaHora() {
		return flagValidaHora;
	}

	public void setFlagValidaHora(int flagValidaHora) {
		this.flagValidaHora = flagValidaHora;
	}

	public String getTipoOferta() {
		return tipoOferta;
	}

	public void setTipoOferta(String tipoOferta) {
		this.tipoOferta = tipoOferta;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public double getDescontoMaximo() {
		return descontoMaximo;
	}

	public void setDescontoMaximo(double descontoMaximo) {
		this.descontoMaximo = descontoMaximo;
	}

	public double getAcrescimoMaximo() {
		return acrescimoMaximo;
	}

	public void setAcrescimoMaximo(double acrescimoMaximo) {
		this.acrescimoMaximo = acrescimoMaximo;
	}

	public double getPrecoOferta() {
		return precoOferta;
	}

	public void setPrecoOferta(double precoOferta) {
		this.precoOferta = precoOferta;
	}

	public double getQuantidadeOfertada() {
		return quantidadeOfertada;
	}

	public void setQuantidadeOfertada(double quantidadeOfertada) {
		this.quantidadeOfertada = quantidadeOfertada;
	}

	public double getQuantidadeMaximaCliente() {
		return quantidadeMaximaCliente;
	}

	public void setQuantidadeMaximaCliente(double quantidadeMaximaCliente) {
		this.quantidadeMaximaCliente = quantidadeMaximaCliente;
	}

	public double getSaldoOferta() {
		return saldoOferta;
	}

	public void setSaldoOferta(double saldoOferta) {
		this.saldoOferta = saldoOferta;
	}

	public String getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(String usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getHoraCancelamento() {
		return horaCancelamento;
	}

	public void setHoraCancelamento(String horaCancelamento) {
		this.horaCancelamento = horaCancelamento;
	}

	public int getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(int prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	public String getHoraInicio() {
		return horaInicio;
	}

	public void setHoraInicio(String horaInicio) {
		this.horaInicio = horaInicio;
	}

	public String getHoraFim() {
		return horaFim;
	}

	public void setHoraFim(String horaFim) {
		this.horaFim = horaFim;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public String getHoraAlteracao() {
		return horaAlteracao;
	}

	public void setHoraAlteracao(String horaAlteracao) {
		this.horaAlteracao = horaAlteracao;
	}

	public Double getPercentualDesconto() {
		return percentualDesconto;
	}

	public void setPercentualDesconto(Double percentualDesconto) {
		this.percentualDesconto = percentualDesconto;
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public int getPrecoBaseCodigo() {
		return precoBaseCodigo;
	}

	public void setPrecoBaseCodigo(int precoBaseCodigo) {
		this.precoBaseCodigo = precoBaseCodigo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public long getDataFimLong() {
		// TODO Auto-generated method stub
		// this.getDataFim()
		return 0;
	}

	@Override
	public long getDataInicioLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getDescricaoTipo() {
		return this.getTipoOferta();
	}

	@Override
	public int getNumero() {
		return this.getOfertaPK().getNumero();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static String getWhere(int filialCodigo, Date data,
			String produtoCodigo) {

		String dataStr = Conversao.formatarDataAAAAMMDD(data);
		String horaStr = Conversao.formatarData(data, Conversao.FORMATO_HORA);

		String wherePro = produtoCodigo != null
				&& !produtoCodigo.trim().isEmpty() ? " and oft_procodigo = "
				+ produtoCodigo : "";

		String whereOferta = " oft_datacanc is null and" + " oft_filcodigo = "
				+ filialCodigo
				+ " "
				+ wherePro
				+ " and oft_status = 1 and "
				+ " (oft_qtdeoferta = 0 or (oft_qtdeoferta > 0 and oft_saldooferta < oft_qtdeoferta)) and"
				+ " (oft_valhora = 0 and '" + dataStr
				+ "' between oft_dataini and oft_datafim or"
				+ " (oft_valhora = 1 and (oft_dataini < '" + dataStr
				+ "' or (oft_dataini = '" + dataStr + "' and"
				+ " oft_horaini <= '" + horaStr + "')) and oft_datafim > '"
				+ dataStr + "' or (oft_datafim = '" + dataStr + "' and"
				+ " oft_horafim >= '" + horaStr + "')))";

		return whereOferta;
	}

	public static String getWhereSemHora(int filialCodigo, Date data,
			String produtoCodigo) {

		String dataStr = Conversao.formatarDataAAAAMMDD(data);

		String wherePro = produtoCodigo != null
				&& !produtoCodigo.trim().isEmpty() ? " and oft_procodigo = "
				+ produtoCodigo : "";

		String whereOferta = " oft_datacanc is null and oft_status = 1 and"
				+ " oft_filcodigo = "
				+ filialCodigo
				+ " "
				+ wherePro
				+ " and"
				+ " (oft_qtdeoferta = 0 or (oft_qtdeoferta > 0 and oft_saldooferta < oft_qtdeoferta)) and '"
				+ dataStr + "' between oft_dataini and oft_datafim";

		return whereOferta;
	}

	public static List<Integer> recuperarCodigoProdutosEmOferta(
			GenericoDAO dao, int filialCodigo, Date data, boolean validarHora)
			throws Exception {

		String where = validarHora ? getWhere(filialCodigo, data, null)
				: getWhereSemHora(filialCodigo, data, null);

		String selectOferta = "select oft_procodigo from" + " oferta"
				+ " where " + where + " order by oft_procodigo";

		List<Integer> produtosOferta = new ArrayList<Integer>();
		ResultSet rs = null;

		try {

			rs = dao.listResultSet(selectOferta, null, null);

			while (rs.next()) {
				int proCodigo = rs.getInt("oft_procodigo");

				produtosOferta.add(Integer.valueOf(proCodigo));
			}

			return produtosOferta;

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public static boolean isProdutoEmOferta(GenericoDAO dao, int filialCodigo,
			Date data, int produtoCodigo, boolean validarHora) {

		String where = validarHora ? getWhere(filialCodigo, data,
				Integer.toString(produtoCodigo)) : getWhereSemHora(
				filialCodigo, data, Integer.toString(produtoCodigo));

		String select = "select count(*) from" + " oferta" + " where " + where
				+ " order by oft_procodigo";

		long count = dao.count(select);

		return count > 0;
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param ofertaNumero
	 * @return
	 */
	public static Oferta recuperarNumero(GenericoDAO dao, int ofertaNumero,
			int filialCodigo) {

		return dao.uniqueResult(Oferta.class, COLUNA_NUMERO
				+ " = "
				+ ofertaNumero
				+ (filialCodigo > 0 ? " and " + COLUNA_FILIAL_CODIGO + " = "
						+ filialCodigo : ""), null);
	}

	/**
	 * 
	 * @param ofertaNumero
	 *            O numero da oferta
	 * @param quantidadeDebioCredito
	 *            A quantidade que deve ser atulizada na Oferta. Caso negativo
	 *            serã debitado do saldo da oferta e positivo creditado.
	 */
	public static void atualizarSaldoOferta(GenericoDAO dao, int ofertaNumero,
			int filialCodigo, double quantidadeDebioCredito) {

		String updade = "update " + NOME_TABELA
				+ " set oft_saldooferta = oft_saldooferta + ("
				+ quantidadeDebioCredito + ") where " + COLUNA_NUMERO + " = "
				+ ofertaNumero + " and " + COLUNA_FILIAL_CODIGO + " = "
				+ filialCodigo;

		dao.executeUpdate(updade);
	}
}
