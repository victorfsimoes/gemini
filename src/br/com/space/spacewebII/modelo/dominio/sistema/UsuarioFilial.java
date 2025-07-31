package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

@Table(name = "usrfilial")
@Entity(name = "usrfilial")
@Embeddable
public class UsuarioFilial implements IPersistent, Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UsuarioFilialPK chaveUsrFilial;

	@ManyToOne()
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "usf_filcodigo", insertable = false, updatable = false, nullable = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private Filial filial;

	public UsuarioFilial() {
		super();
	}

	/**
	 * 
	 * @param chaveUsrFilial
	 * @param filial
	 */
	public UsuarioFilial(
			br.com.space.spacewebII.modelo.dominio.sistema.UsuarioFilialPK chaveUsrFilial,
			Filial filial) {
		super();
		this.chaveUsrFilial = chaveUsrFilial;
		this.filial = filial;
	}

	/**
	 * 
	 * @param filial
	 * @param loginUsuario
	 */
	public UsuarioFilial(Filial filial, String loginUsuario) {
		super();
		this.chaveUsrFilial = new UsuarioFilialPK(filial.getCodigo(),
				loginUsuario);
		this.filial = filial;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public UsuarioFilialPK getChaveUsrFilial() {
		return chaveUsrFilial;
	}

	public void setChaveUsrFilial(UsuarioFilialPK chaveUsrFilial) {
		this.chaveUsrFilial = chaveUsrFilial;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
	}

	@Override
	public UsuarioFilial clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (UsuarioFilial) super.clone();
	}

	public static UsuarioFilial recuperaUnico(GenericoDAO<IPersistent> dao,
			String userLogin, String filialCodigo) {
		String where = "usf_filcodigo = " + filialCodigo
				+ " and usf_usrlogin like '" + userLogin.toUpperCase() + "'";
		return dao.uniqueResult(UsuarioFilial.class, where, null);
	}
}
