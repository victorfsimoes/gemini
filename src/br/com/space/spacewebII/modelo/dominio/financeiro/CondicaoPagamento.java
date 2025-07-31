/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.ICondicaoPagamento;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "condpagto")
@XmlRootElement
public class CondicaoPagamento implements Serializable, ICondicaoPagamento {

	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "cpg_codigo";
	public static final String COLUNA_DESCRICAO = "cpg_desc";
	public static final String COLUNA_ATIVO = "cpg_ativo";

	public static final String CAMPO_CODIGO = "codigo";
	public static final String CAMPO_DESCRICAO = "descricao";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Column(name = "cpg_desc")
	private String descricao;

	@Column(name = "cpg_entperc")
	private Float percentualEntrada;

	@Column(name = "cpg_numparc")
	private int numeroParcelas;

	@Column(name = "cpg_interparc")
	private int intervaloEntreParcelas;

	@Column(name = "cpg_vencfixo")
	private Integer flagVencimentoFixo;

	@Column(name = "cpg_fatorsaldo")
	private double fatorSaldo;

	@Column(name = "cpg_valminparc")
	private double valorMinimoParcela;

	@Column(name = "cpg_ativo")
	private int flagAtivo;

	@Column(name = "cpg_diasent")
	private int diasEntrada;

	@Basic(optional = false)
	@Column(name = "cpg_fatorsldof")
	private double fatorSaldoOferta;

	@Column(name = "cpg_descmaximo")
	private double descontoMaximo;

	@Column(name = "cpg_acrmaximo")
	private double acrescimoMaximo;

	@Column(name = "cpg_presultmin")
	private Float percentualResultadoMinimo;

	@Column(name = "cpg_nenvpalm")
	private Integer flagNaoEnviaPalm;

	@Column(name = "cpg_percabatit")
	private Double percentualAbatimentoItem;

	@Column(name = "cpg_nprazoesp")
	private int flagNaoAdicionaPrazoEspecial;

	@Column(name = "cpg_npromovenda")
	private int flagNaoParticipaPromocaoVenda;

	@Column(name = "cpg_tipopessoa")
	private String tipoPessoa;

	@JoinColumn(name = "cpg_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filial;

	/**
	 * Descricao da condicao de pagamento quando a forma de pagamento for cartão
	 * de crédito.
	 */
	@Transient
	private String descricaoCartao;

	public CondicaoPagamento() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getPercentualEntrada() {
		return percentualEntrada;
	}

	public void setPercentualEntrada(Float percentualEntrada) {
		this.percentualEntrada = percentualEntrada;
	}

	public int getNumeroParcelas() {
		return numeroParcelas;
	}

	public void setNumeroParcelas(int numeroParcelas) {
		this.numeroParcelas = numeroParcelas;
	}

	public int getIntervaloEntreParcelas() {
		return intervaloEntreParcelas;
	}

	public void setIntervaloEntreParcelas(int intervaloEntreParcelas) {
		this.intervaloEntreParcelas = intervaloEntreParcelas;
	}

	public Integer getFlagVencimentoFixo() {
		return flagVencimentoFixo;
	}

	public void setFlagVencimentoFixo(Integer flagVencimentoFixo) {
		this.flagVencimentoFixo = flagVencimentoFixo;
	}

	public double getFatorSaldo() {
		if (fatorSaldo <= 0)
			return 1;

		return fatorSaldo;
	}

	public void setFatorSaldo(double fatorSaldo) {
		this.fatorSaldo = fatorSaldo;
	}

	public double getValorMinimoParcela() {
		return valorMinimoParcela;
	}

	public void setValorMinimoParcela(double valorMinimoParcela) {
		this.valorMinimoParcela = valorMinimoParcela;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getDiasEntrada() {
		return diasEntrada;
	}

	public void setDiasEntrada(int diasEntrada) {
		this.diasEntrada = diasEntrada;
	}

	public double getFatorSaldoOferta() {
		if (fatorSaldoOferta <= 0)
			return 1;
		return fatorSaldoOferta;
	}

	public void setFatorSaldoOferta(double fatorSaldoOferta) {
		this.fatorSaldoOferta = fatorSaldoOferta;
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

	public Float getPercentualResultadoMinimo() {
		return percentualResultadoMinimo;
	}

	public void setPercentualResultadoMinimo(Float percentualResultadoMinimo) {
		this.percentualResultadoMinimo = percentualResultadoMinimo;
	}

	public Integer getFlagNaoEnviaPalm() {
		return flagNaoEnviaPalm;
	}

	public void setFlagNaoEnviaPalm(Integer flagNaoEnviaPalm) {
		this.flagNaoEnviaPalm = flagNaoEnviaPalm;
	}

	public Double getPercentualAbatimentoItem() {
		return percentualAbatimentoItem;
	}

	public void setPercentualAbatimentoItem(Double percentualAbatimentoItem) {
		this.percentualAbatimentoItem = percentualAbatimentoItem;
	}

	public int getFlagNaoAdicionaPrazoEspecial() {
		return flagNaoAdicionaPrazoEspecial;
	}

	public void setFlagNaoAdicionaPrazoEspecial(int flagNaoAdicionaPrazoEspecial) {
		this.flagNaoAdicionaPrazoEspecial = flagNaoAdicionaPrazoEspecial;
	}

	public int getFlagNaoParticipaPromocaoVenda() {
		return flagNaoParticipaPromocaoVenda;
	}

	public void setFlagNaoParticipaPromocaoVenda(
			int flagNaoParticipaPromocaoVenda) {
		this.flagNaoParticipaPromocaoVenda = flagNaoParticipaPromocaoVenda;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
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
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public String getDescricaoCartao() {
		if (descricaoCartao == null || descricaoCartao.trim().length() == 0)
			return this.descricao;

		return descricaoCartao;
	}

	public void setDescricaoCartao(String descricaoCartao) {
		this.descricaoCartao = descricaoCartao;
	}

	/**
	 * 
	 * @param dao
	 * @param codigo
	 * @return
	 */
	public static CondicaoPagamento recuperarUnico(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(CondicaoPagamento.class, COLUNA_CODIGO + " = "
				+ codigo, null);
	}

	@Override
	public String toString() {
		return super.toString(); // this.descricao;
	}

}
