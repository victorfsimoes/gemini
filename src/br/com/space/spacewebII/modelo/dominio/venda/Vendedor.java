/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IVendedor;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.pessoa.Colaborador;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "vendedor")
@XmlRootElement
public class Vendedor implements Serializable, IVendedor {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_EXTERNO = "ven_externo";
	public static final String COLUNA_RAZAO = "ven_razao";
	public static final String COLUNA_CODIGO = "ven_clbcodigo";
	public static final String COLUNA_FILIAL_PALM = "ven_filcodpalm";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int colaboradorCodigo;

	@Column(name = "ven_saldoini")
	private double saldoInicial;

	@Column(name = "ven_maxdeb")
	private double maximoDebito;

	@Column(name = "ven_maxcred")
	private double maximoCredito;

	@Column(name = "ven_saldoatual")
	private double saldoAtual;

	@Column(name = "ven_debcred")
	private int flagDebitoCredito;

	@Column(name = "ven_negtbacima")
	private double margemNegociacaoAcima;

	@Column(name = "ven_negtbabaixo")
	private double margemNegociacaoAbaixo;

	@Column(name = "ven_tprcodigo")
	private int tabelaPrecoCodigo;

	@Basic(optional = false)
	@Column(name = "ven_interno")
	private int flagInterno;

	@Basic(optional = false)
	@Column(name = COLUNA_EXTERNO)
	private int flagExterno;

	@Column(name = "ven_perverbaemp")
	private Double verbaEmpresaPercentual;

	@Column(name = "ven_vlrverbaemp")
	private Double verbaEmpresaValor;

	@Column(name = "ven_dtsaldoini")
	@Temporal(TemporalType.DATE)
	private Date dataSaldoInicial;

	@Column(name = "ven_negofabaixo")
	private double margemNegociacaoAbaixoOferta;

	@Column(name = "ven_negofacima")
	private double margemNegociacaoAcimaOferta;

	@Column(name = "ven_gcocodigoi")
	private Integer grupoComissaoInterno;

	@Column(name = "ven_gcocodigoe")
	private Integer grupoComissaoExterno;

	@Column(name = "ven_palmtop")
	private Integer flagUtilizaPalm;

	@Column(name = "ven_pastapalm")
	private String pastaBasePalm;

	@Column(name = "ven_filcodpalm")
	private Integer filialCodigoPalm;

	@Column(name = "ven_covcodigo")
	private Integer cumprimentoObjetivoVendaCodigo;

	@Column(name = "ven_antisala")
	private Integer flagAntisala;

	@JoinColumn(name = "ven_clbcodigo", referencedColumnName = "clb_codigo", insertable = false, updatable = false)
	@OneToOne(optional = false)
	private Colaborador colaborador;

	public Vendedor() {
	}

	public Vendedor(Integer venClbcodigo) {
		this.colaboradorCodigo = venClbcodigo;
	}

	public Vendedor(Integer venClbcodigo, int venInterno, int venExterno) {
		this.colaboradorCodigo = venClbcodigo;
		this.flagInterno = venInterno;
		this.flagExterno = venExterno;
	}

	public int getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(int colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public double getSaldoInicial() {
		return saldoInicial;
	}

	public void setSaldoInicial(double saldoInicial) {
		this.saldoInicial = saldoInicial;
	}

	public double getMaximoDebito() {
		return maximoDebito;
	}

	public void setMaximoDebito(double maximoDebito) {
		this.maximoDebito = maximoDebito;
	}

	public double getMaximoCredito() {
		return maximoCredito;
	}

	public void setMaximoCredito(double maximoCredito) {
		this.maximoCredito = maximoCredito;
	}

	public double getSaldoAtual() {
		return saldoAtual;
	}

	public void setSaldoAtual(double saldoAtual) {
		this.saldoAtual = saldoAtual;
	}

	public int getFlagDebitoCredito() {
		return flagDebitoCredito;
	}

	public void setFlagDebitoCredito(int flagDebitoCredito) {
		this.flagDebitoCredito = flagDebitoCredito;
	}

	public double getMargemNegociacaoAcima() {
		return margemNegociacaoAcima;
	}

	public void setMargemNegociacaoAcima(double margemNegociacaoAcima) {
		this.margemNegociacaoAcima = margemNegociacaoAcima;
	}

	public double getMargemNegociacaoAbaixo() {
		return margemNegociacaoAbaixo;
	}

	public void setMargemNegociacaoAbaixo(double margemNegociacaoAbaixo) {
		this.margemNegociacaoAbaixo = margemNegociacaoAbaixo;
	}

	public int getTabelaPrecoCodigo() {
		return tabelaPrecoCodigo;
	}

	public void setTabelaPrecoCodigo(int tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	@Override
	public int getFlagInterno() {
		return flagInterno;
	}

	public void setFlagInterno(int flagInterno) {
		this.flagInterno = flagInterno;
	}

	@Override
	public int getFlagExterno() {
		return flagExterno;
	}

	public void setFlagExterno(int flagExterno) {
		this.flagExterno = flagExterno;
	}

	public Double getVerbaEmpresaPercentual() {
		return verbaEmpresaPercentual;
	}

	public void setVerbaEmpresaPercentual(Double verbaEmpresaPercentual) {
		this.verbaEmpresaPercentual = verbaEmpresaPercentual;
	}

	public Double getVerbaEmpresaValor() {
		return verbaEmpresaValor;
	}

	public void setVerbaEmpresaValor(Double verbaEmpresaValor) {
		this.verbaEmpresaValor = verbaEmpresaValor;
	}

	public Date getDataSaldoInicial() {
		return dataSaldoInicial;
	}

	public void setDataSaldoInicial(Date dataSaldoInicial) {
		this.dataSaldoInicial = dataSaldoInicial;
	}

	public double getMargemNegociacaoAbaixoOferta() {
		return margemNegociacaoAbaixoOferta;
	}

	public void setMargemNegociacaoAbaixoOferta(double margemNegociacaoAbaixoOferta) {
		this.margemNegociacaoAbaixoOferta = margemNegociacaoAbaixoOferta;
	}

	public double getMargemNegociacaoAcimaOferta() {
		return margemNegociacaoAcimaOferta;
	}

	public void setMargemNegociacaoAcimaOferta(double margemNegociacaoAcimaOferta) {
		this.margemNegociacaoAcimaOferta = margemNegociacaoAcimaOferta;
	}

	public Integer getGrupoComissaoInterno() {
		return grupoComissaoInterno;
	}

	public void setGrupoComissaoInterno(Integer grupoComissaoInterno) {
		this.grupoComissaoInterno = grupoComissaoInterno;
	}

	public Integer getGrupoComissaoExterno() {
		return grupoComissaoExterno;
	}

	public void setGrupoComissaoExterno(Integer grupoComissaoExterno) {
		this.grupoComissaoExterno = grupoComissaoExterno;
	}

	public Integer getFlagUtilizaPalm() {
		return flagUtilizaPalm;
	}

	public void setFlagUtilizaPalm(Integer flagUtilizaPalm) {
		this.flagUtilizaPalm = flagUtilizaPalm;
	}

	public String getPastaBasePalm() {
		return pastaBasePalm;
	}

	public void setPastaBasePalm(String pastaBasePalm) {
		this.pastaBasePalm = pastaBasePalm;
	}

	public Integer getFilialCodigoPalm() {
		return filialCodigoPalm;
	}

	public void setFilialCodigoPalm(Integer filialCodigoPalm) {
		this.filialCodigoPalm = filialCodigoPalm;
	}

	public Integer getCumprimentoObjetivoVendaCodigo() {
		return cumprimentoObjetivoVendaCodigo;
	}

	public void setCumprimentoObjetivoVendaCodigo(Integer cumprimentoObjetivoVendaCodigo) {
		this.cumprimentoObjetivoVendaCodigo = cumprimentoObjetivoVendaCodigo;
	}

	public Integer getFlagAntisala() {
		return flagAntisala;
	}

	public void setFlagAntisala(Integer flagAntisala) {
		this.flagAntisala = flagAntisala;
	}

	public Colaborador getColaborador() {
		return colaborador;
	}

	public void setColaborador(Colaborador colaborador) {
		this.colaborador = colaborador;
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
	public long getDataSaldoInicialLong() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getFlagGerente() {
		return this.getColaborador().getCargo().getFlagGerenteVenda();
	}

	@Override
	public int getFlagSupervisor() {
		return this.getColaborador().getCargo().getFlagSupervisorVenda();
	}

	@Override
	public String getRazaoSocial() {
		return this.getColaborador().getRazaoSocial();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * 
	 * @param dao
	 * @param codigo
	 * @return
	 */
	public static Vendedor recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(Vendedor.class, COLUNA_CODIGO + " = " + codigo, null);
	}

	public static Vendedor recuperarUnicoAtivo(GenericoDAO dao, int codigo) {

		String select = "select vendedor.* from vendedor inner join colaborador on ven_clbcodigo = clb_codigo and clb_ativo = 1 and "
				+ COLUNA_CODIGO + " = " + codigo;

		return dao.uniqueResult(Vendedor.class, select);
	}

	/**
	 * Recupera os vendedores que corresponde diretamente com o
	 * codigoDoVendedorOuSupervisor ou que o codigo do supervisor seja igual.
	 * 
	 * @param dao
	 * @param codigoDoVendedorOuSupervisor
	 * @return
	 */
	public static List<Vendedor> recuperar(GenericoDAO dao, int codigoDoVendedorOuSupervisor, boolean interno,
			boolean externo) {

		// List<String> whereFragmentos = new ArrayList<String>();
		StringBuilder where = new StringBuilder();

		where.append("(clb_codigo = " + codigoDoVendedorOuSupervisor).append(" or clb_clbcodigo = ")
				.append(codigoDoVendedorOuSupervisor).append(") and clb_ativo = 1");

		/*
		 * whereFragmentos.add("(clb_codigo = " + codigoDoVendedorOuSupervisor +
		 * " or clb_clbcodigo = " + codigoDoVendedorOuSupervisor + ")");
		 * whereFragmentos.add("clb_ativo = 1");
		 */

		if (interno != externo) {
			if (interno) {
				where.append(" and ven_interno = 1");
				// whereFragmentos.add("ven_interno = 1 ");
			} else if (externo) {
				where.append(" and ven_externo = 1 ");
				// whereFragmentos.add("ven_externo = 1 ");
			}
		}

		// String where = GenericoDAO.criarWhere(whereFragmentos);

		StringBuilder select = new StringBuilder()
				.append("select vendedor.* from vendedor inner join colaborador on ven_clbcodigo = clb_codigo ");

		if (where.length() > 0)
			select.append(" where ").append(where);

		// select += StringUtil.isValida(where) ? "where " + where : "";

		return dao.list(Vendedor.class, select.toString());
	}

	@Override
	public int getTabelaPrecoPromocionalCodigo() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getSupervisorCodigo() {
		return getColaborador() != null ? getColaborador().getColaboradorCodigoSupervisor() : 0;
	}

	@Override
	public int getGerenteCodigo() {
		return getColaborador() != null && getColaborador().getColaboradorSupervisor() != null
				? getColaborador().getColaboradorSupervisor().getColaboradorCodigoSupervisor() : 0;
	}
}
