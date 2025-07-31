package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@XmlRootElement
@Table(name = "grupopiscof")
public class GrupoPisCofins implements Serializable, IPersistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "gpc_codigo")
	private int codigo;

	@Column(name = "gpc_cspcodsai")
	private String cstPis;

	@Column(name = "gpc_csccodsai")
	private String cstCofins;

	@Column(name = "gpc_aliqpis")
	private double aliquotaPis;

	@Column(name = "gpc_aliqcofins")
	private double aliquotaCofins;

	public int getCodigo() {
		return codigo;
	}

	public String getCstPis() {
		return cstPis;
	}

	public String getCstCofins() {
		return cstCofins;
	}

	public double getAliquotaPis() {
		return aliquotaPis;
	}

	public double getAliquotaCofins() {
		return aliquotaCofins;
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
		// TODO Auto-generated method stub
		return super.clone();
	}

	public static GrupoPisCofins recuperarUnico(GenericoDAO dao, int grupoPisCofinsCodigo) {

		return dao.uniqueResult(GrupoPisCofins.class, "gpc_codigo = " + grupoPisCofinsCodigo, null);

	}

}
