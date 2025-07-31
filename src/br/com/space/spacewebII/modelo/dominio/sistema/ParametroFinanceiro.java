package br.com.space.spacewebII.modelo.dominio.sistema;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Id;

import br.com.space.api.negocio.modelo.dominio.parametro.IParametroFinanceiro;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.model.dao.db.Table;

public class ParametroFinanceiro implements IParametroFinanceiro {

	private static final String COLUNA_FILIAL = "par_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private int filialCodigo;

	@SpaceColumn(name = "par_tipost1")
	private int flagSituacaoCliente1;

	@SpaceColumn(name = "par_tipost2")
	private int flagSituacaoCliente2;

	@SpaceColumn(name = "par_tipost3")
	private int flagSituacaoCliente3;

	@SpaceColumn(name = "par_tipost4")
	private int flagSituacaoCliente4;

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public void setFlagSituacaoCliente1(int flagSituacaoCliente1) {
		this.flagSituacaoCliente1 = flagSituacaoCliente1;
	}

	public void setFlagSituacaoCliente2(int flagSituacaoCliente2) {
		this.flagSituacaoCliente2 = flagSituacaoCliente2;
	}

	public void setFlagSituacaoCliente3(int flagSituacaoCliente3) {
		this.flagSituacaoCliente3 = flagSituacaoCliente3;
	}

	public void setFlagSituacaoCliente4(int flagSituacaoCliente4) {
		this.flagSituacaoCliente4 = flagSituacaoCliente4;
	}

	@Override
	public int getFlagSituacaoCliente1() {
		return this.flagSituacaoCliente1;
	}

	@Override
	public int getFlagSituacaoCliente2() {
		return this.flagSituacaoCliente2;
	}

	@Override
	public int getFlagSituacaoCliente3() {
		return this.flagSituacaoCliente3;
	}

	@Override
	public int getFlagSituacaoCliente4() {
		return this.flagSituacaoCliente4;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(Table arg0) {
		// TODO Auto-generated method stub
	}

}
