package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.negocio.modelo.dominio.parametro.IParametroVenda3;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "parametro3")
public class ParametroVenda3 implements Serializable, IParametroVenda3 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "pa3_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private int filialCodigo;

	@Column(name = "pa3_precovarejo")
	private int flagUtilizaPrecoVarejo;

	@Column(name = "pa3_indvarejo")
	private double indiceVarejo;

	@Column(name = "pa3_calcipidav")
	private int flagCalculaIpiDav;

	@Column(name = "pa3_calcfrereg")
	private int flagCalculaFreteRegiao;

	@Column(name = "Pa3_ParcValMin")
	private int flagConsistenValorMinimoFormaPagamentoPorParcela;

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFlagUtilizaPrecoVarejo() {
		return flagUtilizaPrecoVarejo;
	}

	public void setFlagUtilizaPrecoVarejo(int flagUtilizaPrecoVarejo) {
		this.flagUtilizaPrecoVarejo = flagUtilizaPrecoVarejo;
	}

	public double getIndiceVarejo() {
		return indiceVarejo;
	}

	public void setIndiceVarejo(double indiceVarejo) {
		this.indiceVarejo = indiceVarejo;
	}

	public int getFlagCalculaIpiDav() {
		return flagCalculaIpiDav;
	}

	public void setFlagCalculaIpiDav(int flagCalculaIpiDav) {
		this.flagCalculaIpiDav = flagCalculaIpiDav;
	}

	public int getFlagCalculaFreteRegiao() {
		return flagCalculaFreteRegiao;
	}

	public void setFlagCalculaFreteRegiao(int flagCalculaFreteRegiao) {
		this.flagCalculaFreteRegiao = flagCalculaFreteRegiao;
	}

	@Override
	public int getFlagConsistenValorMinimoFormaPagamentoPorParcela() {
		return flagConsistenValorMinimoFormaPagamentoPorParcela;
	}

	public void setFlagConsistenValorMinimoFormaPagamentoPorParcela(
			int flagConsistenValorMinimoFormaPagamentoPorParcela) {
		this.flagConsistenValorMinimoFormaPagamentoPorParcela = flagConsistenValorMinimoFormaPagamentoPorParcela;
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

	/**
	 * 
	 * @param dao
	 * @param codigo
	 * @return
	 */
	public static ParametroVenda3 recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(ParametroVenda3.class, COLUNA_FILIAL + " = "
				+ codigo, null);
	}

	@Override
	public int getBloqueiaClienteNaoHabilitado() {
		return 0;
	}

}
