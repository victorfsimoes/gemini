package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@XmlRootElement
@Table(name = "codfiscal")
public class CodigoFiscal implements Serializable, IPersistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "CDF_CODIGO")
	private String codigo;

	@Column(name = "cdf_ncalcpis")
	private int flagNaoCalculaPis;

	@Column(name = "cdf_ncalccofins")
	private int flagNaoCalculaCofins;

	@Column(name = "cdf_cspcodigo")
	private String cstPis;

	@Column(name = "cdf_csccodigo")
	private String cstCofins;

	public boolean isCalculaPis() {
		return (flagNaoCalculaPis == 0);
	}

	public boolean isCalculaCofins() {
		return (flagNaoCalculaCofins == 0);
	}

	public String getCodigo() {
		return codigo;
	}

	public String getCstCofins() {
		return cstCofins;
	}

	public String getCstPis() {
		return cstPis;
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
		return super.clone();
	}

	public static CodigoFiscal recuperarUnico(GenericoDAO dao, String cdfCodigo) {

		return dao.uniqueResult(CodigoFiscal.class, "cdf_codigo = '" + cdfCodigo + "'", null);

	}
}
