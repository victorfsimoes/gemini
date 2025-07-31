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
@Table(name = "cstcofins")
public class CstCofins implements Serializable, IPersistent {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "csc_codigo")
	private String codigo;

	@Column(name = "csc_tipotrib")
	private String tipoTributacao;

	public String getCodigo() {
		return codigo;
	}

	public String getTipoTributacao() {
		return tipoTributacao;
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

	public static CstCofins recuperarUnico(GenericoDAO dao, String cstCodigo) {

		return dao.uniqueResult(CstCofins.class, "csc_codigo = '" + cstCodigo + "'", null);

	}

}
