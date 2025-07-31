package br.com.space.spacewebII.modelo.dominio.sistema;

import java.util.Comparator;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.space.api.spa.model.domain.IPersistent;

@Table(name = "usrprog")
@Entity(name = "usrprog")
@Embeddable
public class UsuarioPrograma implements Comparable<UsuarioPrograma>,
		IPersistent {

	@EmbeddedId
	private UsuarioProgramaPK chaveUsrProg;

	@Column(name = "upr_incexcl")
	private String incluirExcluirPermissaoGrupo = null;

	public UsuarioPrograma() {
	}

	public UsuarioPrograma(UsuarioProgramaPK chaveUsrProg) {
		super();
		this.chaveUsrProg = chaveUsrProg;
	}

	public UsuarioProgramaPK getChaveUsrProg() {
		return chaveUsrProg;
	}

	public void setChaveUsrProg(UsuarioProgramaPK chaveUsrProg) {
		this.chaveUsrProg = chaveUsrProg;
	}

	public String getIncluirExcluirPermissaoGrupo() {
		return incluirExcluirPermissaoGrupo;
	}

	public void setIncluirExcluirPermissaoGrupo(
			String incluirExcluirPermissaoGrupo) {
		this.incluirExcluirPermissaoGrupo = incluirExcluirPermissaoGrupo;
	}

	@Override
	public int compareTo(UsuarioPrograma o) {

		return getChaveUsrProg().getPrograma().compareTo(
				o.getChaveUsrProg().getPrograma());

	}

	@Transient
	public final Comparator<UsuarioPrograma> comparatorUsrProg = new Comparator<UsuarioPrograma>() {

		@Override
		public int compare(UsuarioPrograma o1, UsuarioPrograma o2) {
			int compareString = o1.getChaveUsrProg().getPrograma()
					.compareTo(o2.getChaveUsrProg().getPrograma());

			if (compareString == 0) {
				if (o1.getChaveUsrProg().getCodigoPermissao() < o2
						.getChaveUsrProg().getCodigoPermissao()) {
					return -1;
				} else if (o1.getChaveUsrProg().getCodigoPermissao() > o2
						.getChaveUsrProg().getCodigoPermissao()) {
					return 1;
				} else {
					return 0;
				}
			} else {
				return compareString;
			}
		}
	};

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
	}

	@Override
	public UsuarioPrograma clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return (UsuarioPrograma) super.clone();
	}

}
