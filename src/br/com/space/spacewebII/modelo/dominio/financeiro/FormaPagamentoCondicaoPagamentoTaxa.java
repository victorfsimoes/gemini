package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 * 
 */
@Entity
@Table(name = "formacondtaxa")
public class FormaPagamentoCondicaoPagamentoTaxa implements Serializable,
		IPersistent {

	private static final long serialVersionUID = 1L;

	public static final String COLUNA_FORMAPAGAMENTO_CODIGO = "fct_fpgcodigo";
	public static final String COLUNA_CONDICAOPAGAMENTO_CODIGO = "fct_cpgcodigo";

	@EmbeddedId
	private FormaPagamentoCondicaoPagamentoTaxaPK formaPagamentoCondicaoPagamentoTaxaPK;

	@Column(name = "fct_taxa")
	private double taxa;

	/**
	 * Campo descritivo para a tela de Juros Parcelamento.
	 */
	@Column(name = "fct_jurosparc")
	private double jurosParcelamentoPercentual;

	/**
	 * 1 = AVista, 2 = Parcelado Administradora, 3 = Parcelado Lojista;
	 */
	@Column(name = "fct_tipoparc")
	private int flagTipoParcelamento;

	public FormaPagamentoCondicaoPagamentoTaxa() {

	}

	public FormaPagamentoCondicaoPagamentoTaxaPK getFormaPagamentoCondicaoPagamentoTaxaPK() {
		return formaPagamentoCondicaoPagamentoTaxaPK;
	}

	public void setFormaPagamentoCondicaoPagamentoTaxaPK(
			FormaPagamentoCondicaoPagamentoTaxaPK formaPagamentoCondicaoPagamentoTaxaPK) {
		this.formaPagamentoCondicaoPagamentoTaxaPK = formaPagamentoCondicaoPagamentoTaxaPK;
	}

	public double getTaxa() {
		return taxa;
	}

	public void setTaxa(double taxa) {
		this.taxa = taxa;
	}

	public int getFlagTipoParcelamento() {
		return flagTipoParcelamento;
	}

	public void setFlagTipoParcelamento(int flagTipoParcelamento) {
		this.flagTipoParcelamento = flagTipoParcelamento;
	}

	public double getJurosParcelamentoPercentual() {
		return jurosParcelamentoPercentual;
	}

	public void setJurosParcelamentoPercentual(
			double jurosParcelamentoPercentual) {
		this.jurosParcelamentoPercentual = jurosParcelamentoPercentual;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
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
	 * @param formaPagamentoCodigo
	 * @return
	 */
	public static List<FormaPagamentoCondicaoPagamentoTaxa> recuperar(
			GenericoDAO dao, String formaPagamentoCodigo) {
		return dao.list(FormaPagamentoCondicaoPagamentoTaxa.class,
				COLUNA_FORMAPAGAMENTO_CODIGO + " = '" + formaPagamentoCodigo
						+ "'", null, null, null);
	}

	/**
	 * 
	 * @param dao
	 * @param formaPagamentoCodigo
	 * @param condicaoPagamentoCodigo
	 * @return
	 */
	public static FormaPagamentoCondicaoPagamentoTaxa recuperarUnico(
			GenericoDAO dao, String formaPagamentoCodigo,
			int condicaoPagamentoCodigo) {

		String where = COLUNA_FORMAPAGAMENTO_CODIGO + " = '"
				+ formaPagamentoCodigo + "' and "
				+ COLUNA_CONDICAOPAGAMENTO_CODIGO + "="
				+ condicaoPagamentoCodigo;
		return dao.uniqueResult(FormaPagamentoCondicaoPagamentoTaxa.class,
				where, null);
	}

	/**
	 * 
	 * @param condicoes
	 * @param condicaoPagamentoCodigo
	 * @return
	 */
	public static FormaPagamentoCondicaoPagamentoTaxa pesquisarLista(
			List<FormaPagamentoCondicaoPagamentoTaxa> condicoes,
			int condicaoPagamentoCodigo) {

		for (FormaPagamentoCondicaoPagamentoTaxa condicaoPagamento : condicoes) {
			if (condicaoPagamento.getFormaPagamentoCondicaoPagamentoTaxaPK()
					.getCondicaoPagamentoCodigo() == condicaoPagamentoCodigo) {
				return condicaoPagamento;
			}
		}

		return null;
	}

}
