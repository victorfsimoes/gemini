package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.negocio.modelo.dominio.parametro.IParametroVenda4;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "parametro4")
public class ParametroVenda4 implements Serializable, IParametroVenda4 {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "pa4_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private int filialCodigo;

	@Column(name = "pa4_rtdespedoft")
	private int flagRateiaDescontoPedidoItensEmOferta;

	@Column(name = "pa4_prctabven")
	private int flagPrecoTabelaKitAssumePrecoVendaKit;

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	@Override
	public int getFlagRateiaDescontoPedidoItensEmOferta() {
		return flagRateiaDescontoPedidoItensEmOferta;
	}

	public void setFlagRateiaDescontoPedidoItensEmOferta(
			int flagRateiaDescontoPedidoItensEmOferta) {

		this.flagRateiaDescontoPedidoItensEmOferta = flagRateiaDescontoPedidoItensEmOferta;
	}

	@Override
	public boolean isRateiaDescontoPedidoItensEmOferta() {
		return flagRateiaDescontoPedidoItensEmOferta == 1;
	}

	@Override
	public int getFlagPrecoTabelaKitAssumePrecoVendaKit() {
		return flagPrecoTabelaKitAssumePrecoVendaKit;
	}

	public void setFlagPrecoTabelaKitAssumePrecoVendaKit(
			int flagPrecoTabelaKitAssumePrecoVendaKit) {
		this.flagPrecoTabelaKitAssumePrecoVendaKit = flagPrecoTabelaKitAssumePrecoVendaKit;
	}

	@Override
	public boolean isPrecoTabelaKitAssumePrecoVendaKit() {
		return flagPrecoTabelaKitAssumePrecoVendaKit == 1;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	/**
	 * 
	 * @param dao
	 * @param codigo
	 * @return
	 */
	public static ParametroVenda4 recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(ParametroVenda4.class, COLUNA_FILIAL + " = "
				+ codigo, null);
	}
}
