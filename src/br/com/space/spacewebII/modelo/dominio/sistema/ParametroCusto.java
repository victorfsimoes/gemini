package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.parametro.IParametroCusto;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "parametro")
@XmlRootElement
public class ParametroCusto implements IPersistent, Serializable,
		IParametroCusto {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "par_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private int filialCodigo;

	@Column(name = "par_luccusven")
	private String baseCalculoResultado;

	public ParametroCusto() {
	}

	public ParametroCusto(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getBaseCalculoResultado() {
		return baseCalculoResultado;
	}

	public void setBaseCalculoResultado(String baseCalculoResultado) {
		this.baseCalculoResultado = baseCalculoResultado;
	}

	public static ParametroCusto recuperarUnico(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(ParametroCusto.class, COLUNA_FILIAL + " = "
				+ codigo, null);
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

}
