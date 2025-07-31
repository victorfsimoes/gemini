package br.com.space.spacewebII.modelo.dominio.sistema;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;

@Table(name = "permisgrupo")
@Entity(name = "permisgrupo")
@Embeddable
public class PermissaoGrupo implements IPersistent, Comparable<PermissaoGrupo> {

	

	@EmbeddedId
	private PermissaoGrupoPK chavePermissaoGrupo = null;

	@Column(name = "psg_filcodigo")
	private int codigoFilial = 0;

	public PermissaoGrupo() {
	}

	public PermissaoGrupo(int codigoGrupo, String codigoPrograma,
			int codigoPermissao, int codigoFilial) {
		chavePermissaoGrupo = new PermissaoGrupoPK(codigoGrupo,
				codigoPrograma, codigoPermissao);

		this.codigoFilial = codigoFilial;
	}

	public PermissaoGrupoPK getChavePermissaoGrupo() {
		return chavePermissaoGrupo;
	}

	public void setChavePermissaoGrupo(PermissaoGrupoPK chavePermissaoGrupo) {
		this.chavePermissaoGrupo = chavePermissaoGrupo;
	}

	public int getCodigoFilial() {
		return codigoFilial;
	}

	public void setCodigoFilial(int codigoFilial) {
		this.codigoFilial = codigoFilial;
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

	@Override
	public int compareTo(PermissaoGrupo arg0) {

		if (chavePermissaoGrupo == null
				&& arg0.getChavePermissaoGrupo() != null) {
			return 0;
		}

		String key1 = chavePermissaoGrupo.getCodigoPrograma()
				+ Integer.toString(chavePermissaoGrupo.getCodigoGrupo())
				+ Integer.toString(chavePermissaoGrupo.getCodigoPermissao());

		String key2 = arg0.getChavePermissaoGrupo().getCodigoPrograma()
				+ Integer.toString(arg0.getChavePermissaoGrupo()
						.getCodigoGrupo())
				+ Integer.toString(arg0.getChavePermissaoGrupo()
						.getCodigoPermissao());

		return key1.compareTo(key2);
	}
}
