package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.parametro.IParametroVenda;
import br.com.space.api.negocio.modelo.negocio.GerentePedidoGenerico.AcaoAlteracaoNegociacao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "parametro")
@XmlRootElement
public class ParametroVenda implements Serializable, IParametroVenda {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "par_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private int filialCodigo;

	@Column(name = "par_decvrvenda")
	private int casasDecimaisPrecoVenda = 2;

	@Column(name = "par_decqtvenda")
	private int casasDecimaisQuantidade = 2;

	@Column(name = "par_consturno")
	private int utilizaTurnoEntrega = 0;

	@Column(name = "par_histcli")
	private int exibeHistoricoCliente;

	@Column(name = "par_exiprmaxmin")
	private int exibePrecoMaximoMinimo = 1;

	@Column(name = "par_natcodpalm")
	private String naturezaPadraoViking = null;

	@Column(name = "par_luccusven", length = 1)
	private String baseCalculoResultado = null;

	@Column(name = "par_tipocom")
	private String tipoComissao = null;

	@Column(name = "par_basecom")
	private String baseComissao = null;

	@Column(name = "par_sugprzesp")
	private int flagSugerePrazoEspecial;

	@Column(name = "par_tipovenda")
	private int flagTipoVendaObrigatorio;

	@Column(name = "par_conlimfimps")
	private int flagConsisteLimiteFinalPedido;

	@Column(name = "par_altcondpag")
	private int flagAlteracaoCondicaoPagamento;

	@Column(name = "par_pslibdbcrpr")
	private int flagLiberaDebitoCreditoCasoAutorizado = 0;

	@Column(name = "par_colabped")
	private int flagPermiteLancarColaboradorPedido = 0;

	@Column(name = "par_custopadg")
	private int custoPadrao;

	public ParametroVenda() {

	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getCasasDecimaisPrecoVenda() {
		return casasDecimaisPrecoVenda;
	}

	public void setCasasDecimaisPrecoVenda(int casasDecimaisPrecoVenda) {
		this.casasDecimaisPrecoVenda = casasDecimaisPrecoVenda;
	}

	public int getCasasDecimaisQuantidade() {
		return casasDecimaisQuantidade;
	}

	public void setCasasDecimaisQuantidade(int casasDecimaisQuantidade) {
		this.casasDecimaisQuantidade = casasDecimaisQuantidade;
	}

	public int getUtilizaTurnoEntrega() {
		return utilizaTurnoEntrega;
	}

	public void setUtilizaTurnoEntrega(int utilizaTurnoEntrega) {
		this.utilizaTurnoEntrega = utilizaTurnoEntrega;
	}

	public int getExibeHistoricoCliente() {
		return exibeHistoricoCliente;
	}

	public void setExibeHistoricoCliente(int exibeHistoricoCliente) {
		this.exibeHistoricoCliente = exibeHistoricoCliente;
	}

	public int getExibePrecoMaximoMinimo() {
		return exibePrecoMaximoMinimo;
	}

	public void setExibePrecoMaximoMinimo(int exibePrecoMaximoMinimo) {
		this.exibePrecoMaximoMinimo = exibePrecoMaximoMinimo;
	}

	public String getNaturezaPadraoViking() {
		return naturezaPadraoViking;
	}

	public void setNaturezaPadraoViking(String naturezaPadraoViking) {
		this.naturezaPadraoViking = naturezaPadraoViking;
	}

	public String getBaseCalculoResultado() {
		return baseCalculoResultado;
	}

	public void setBaseCalculoResultado(String baseCalculoResultado) {
		this.baseCalculoResultado = baseCalculoResultado;
	}

	public String getTipoComissao() {
		return tipoComissao;
	}

	public void setTipoComissao(String tipoComissao) {
		this.tipoComissao = tipoComissao;
	}

	public String getBaseComissao() {
		return baseComissao;
	}

	public void setBaseComissao(String baseComissao) {
		this.baseComissao = baseComissao;
	}

	public int getFlagSugerePrazoEspecial() {
		return flagSugerePrazoEspecial;
	}

	public void setFlagSugerePrazoEspecial(int flagSugerePrazoEspecial) {
		this.flagSugerePrazoEspecial = flagSugerePrazoEspecial;
	}

	public int getFlagTipoVendaObrigatorio() {
		return flagTipoVendaObrigatorio;
	}

	public void setFlagTipoVendaObrigatorio(int flagTipoVendaObrigatorio) {
		this.flagTipoVendaObrigatorio = flagTipoVendaObrigatorio;
	}

	public int getFlagConsisteLimiteFinalPedido() {
		return flagConsisteLimiteFinalPedido;
	}

	public void setFlagConsisteLimiteFinalPedido(int flagConsisteLimiteFinalPedido) {
		this.flagConsisteLimiteFinalPedido = flagConsisteLimiteFinalPedido;
	}

	public int getFlagAlteracaoCondicaoPagamento() {
		return flagAlteracaoCondicaoPagamento;
	}

	public void setFlagAlteracaoCondicaoPagamento(int flagAlteracaoCondicaoPagamento) {
		this.flagAlteracaoCondicaoPagamento = flagAlteracaoCondicaoPagamento;
	}

	@Override
	public int getFlagLiberaDebitoCreditoCasoAutorizado() {
		return flagLiberaDebitoCreditoCasoAutorizado;
	}

	public void setFlagLiberaDebitoCreditoCasoAutorizado(int flagLiberaDebitoCreditoCasoAutorizado) {
		this.flagLiberaDebitoCreditoCasoAutorizado = flagLiberaDebitoCreditoCasoAutorizado;
	}

	public boolean isPermiteLancarColaboradorPedido() {
		return flagPermiteLancarColaboradorPedido == 1;
	}

	public int getFlagPermiteLancarColaboradorPedido() {
		return flagPermiteLancarColaboradorPedido;
	}

	public void setFlagPermiteLancarColaboradorPedido(int flagPermiteLancarColaboradorPedido) {
		this.flagPermiteLancarColaboradorPedido = flagPermiteLancarColaboradorPedido;
	}

	public int getCustoPadrao() {
		return custoPadrao;
	}

	public void setCustoPadrao(int custoPadrao) {
		this.custoPadrao = custoPadrao;
	}

	public AcaoAlteracaoNegociacao getAcaoAlteracaoNegociacaoParametro() {

		AcaoAlteracaoNegociacao acao = AcaoAlteracaoNegociacao.ManterDiferencaPreco;

		if (getFlagAlteracaoCondicaoPagamento() == 1)
			acao = AcaoAlteracaoNegociacao.ManterPrecoNegociado;
		else if (getFlagAlteracaoCondicaoPagamento() == 2)
			acao = AcaoAlteracaoNegociacao.ManterDiferencaPercentual;
		else if (getFlagAlteracaoCondicaoPagamento() == 3)
			acao = AcaoAlteracaoNegociacao.AssumirPrecoTabela;

		return acao;
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

	public static ParametroVenda recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(ParametroVenda.class, COLUNA_FILIAL + " = " + codigo, null);

	}

}
