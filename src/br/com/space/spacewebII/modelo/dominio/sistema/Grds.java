package br.com.space.spacewebII.modelo.dominio.sistema;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * @author Desenvolvimento
 * 
 */

@Entity
@Table(name = "grds")
public class Grds implements IPersistent {

	public static final String COLUNA_CODIGO_FILIAL = "grd_filcodigo";
	public static final String COLUNA_LICENCA = "grd_licenca";
	public static final String COLUNA_WAY = "grd_way";

	@EmbeddedId
	private GrdsPK chaveGrds;

	@Column(name = COLUNA_LICENCA)
	String licenca = null;

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public GrdsPK getChaveGrds() {
		return chaveGrds;
	}

	public String getLicenca() {
		return licenca;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	public void setChaveGrds(GrdsPK chaveGrds) {
		this.chaveGrds = chaveGrds;
	}

	public void setLicenca(String licenca) {
		this.licenca = licenca;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {

	}

	/**
	 * 
	 * @param dao
	 * @param filtroCodigoFilial
	 * @return
	 */
	public static List<Grds> getLicencasFilial(GenericoDAO<IPersistent> dao,
			int filtroCodigoFilial) {

		List<String> whereFragmentos = new ArrayList<String>();

		if (filtroCodigoFilial > 0) {
			whereFragmentos.add(COLUNA_CODIGO_FILIAL + " = "
					+ filtroCodigoFilial);
		}

		StringBuilder where = new StringBuilder();
		for (int i = 0; i < whereFragmentos.size(); i++) {

			if (i > 0)
				where.append(" and ");

			where.append(whereFragmentos.get(i));
		}

		return dao.list(Grds.class, where.toString(), null, null, null);
	}

	/**
	 * 
	 * @param dao
	 * @param filtroCodigoFilial
	 * @return
	 */
	public static Grds recuperarUnico(GenericoDAO<IPersistent> dao,
			int filtroCodigoFilial) {

		String where = COLUNA_CODIGO_FILIAL + " = " + filtroCodigoFilial
				+ " and " + COLUNA_WAY + " = 0";

		return dao.uniqueResult(Grds.class, where, null);
	}
}
