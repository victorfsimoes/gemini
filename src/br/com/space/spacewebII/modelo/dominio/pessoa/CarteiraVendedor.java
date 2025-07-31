/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.venda.Vendedor;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "carteiravend")
@XmlRootElement
public class CarteiraVendedor implements Serializable, IPersistent {

	public static final String COLUNA_CARTEIRACLIENTE_CODIGO = "ccv_cclcodigo";

	public static final String COLUNA_COLABORADOR_CODIGO = "ccv_clbcodigo";

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected ChaveCarteiraVendedor chaveCarteiraVendedor;

	@Column(name = "ccv_padcolab")
	private Integer flagColaboradorPadrao;

	@JoinColumn(name = "ccv_clbcodigo", referencedColumnName = "ven_clbcodigo", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Vendedor vendedor;

	@JoinColumn(name = "ccv_cclcodigo", referencedColumnName = "ccl_codigo", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private CarteiraCliente carteiraCliente;

	public CarteiraVendedor() {
	}

	public ChaveCarteiraVendedor getChaveCarteiraVendedor() {
		return chaveCarteiraVendedor;
	}

	public void setChaveCarteiraVendedor(
			ChaveCarteiraVendedor chaveCarteiraVendedor) {
		this.chaveCarteiraVendedor = chaveCarteiraVendedor;
	}

	public Integer getFlagColaboradorPadrao() {
		return flagColaboradorPadrao;
	}

	public void setFlagColaboradorPadrao(Integer flagColaboradorPadrao) {
		this.flagColaboradorPadrao = flagColaboradorPadrao;
	}

	public Vendedor getVendedor() {
		return vendedor;
	}

	public void setVendedor(Vendedor vendedor) {
		this.vendedor = vendedor;
	}

	public CarteiraCliente getCarteiraCliente() {
		return carteiraCliente;
	}

	public void setCarteiraCliente(CarteiraCliente carteiraCliente) {
		this.carteiraCliente = carteiraCliente;
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
	 * @param carteiraCliente
	 * @return
	 */
	public static List<CarteiraVendedor> recuperarPorCarteiraCliente(
			GenericoDAO dao, int carteiraCodigo) {

		return dao.list(CarteiraVendedor.class, COLUNA_CARTEIRACLIENTE_CODIGO
				+ "=" + carteiraCodigo, null, null, null);
	}

	/**
	 * 
	 * @param dao
	 * @param carteiraCodigo
	 * @param colaboradorCodigo
	 * @return
	 */
	public static List<CarteiraVendedor> recuperarPorCarteiraColaborador(
			GenericoDAO dao, int carteiraCodigo, int colaboradorCodigo) {

		StringBuilder where = new StringBuilder();

		if (colaboradorCodigo > 0) {
			where.append(COLUNA_COLABORADOR_CODIGO).append(" = ").append(colaboradorCodigo);
		}
		if (carteiraCodigo > 0) {
			where.append((where.length() > 0 ? " and " : ""))
					.append(COLUNA_CARTEIRACLIENTE_CODIGO).append(" = ").append(carteiraCodigo);
		}

		return dao.list(CarteiraVendedor.class, where.toString(), null, null, null);
	}

}
