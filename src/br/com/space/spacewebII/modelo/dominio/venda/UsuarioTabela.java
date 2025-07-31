package br.com.space.spacewebII.modelo.dominio.venda;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceId;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.dao.IGenericDAO;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity(name = "usuariotabela")
@Table(name = "usuariotabela")
@SpaceTable(name = "usuariotabela")
public class UsuarioTabela implements IPersistent {

	public final static String COLUNA_USUARIO_LOGIN = "utp_usrlogin";
	public static final String COLUNA_TABELA_CODIGO = "utp_tprcodigo";

	@EmbeddedId
	private UsuarioTabelaPK chaveUsuarioTabela;

	@Transient
	@SpaceId
	@SpaceColumn(name = COLUNA_USUARIO_LOGIN)
	private String usuarioLogin = null;

	@Transient
	@SpaceId(hierarchy = 2)
	@SpaceColumn(name = COLUNA_TABELA_CODIGO)
	private int codigoTabela = 0;

	public String getUsuarioLogin() {
		if (chaveUsuarioTabela != null
				&& StringUtil.isValida(chaveUsuarioTabela.getUsuarioLogin())) {
			return chaveUsuarioTabela.getUsuarioLogin();
		}

		return usuarioLogin;
	}

	public void setUsuarioLogin(String usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public int getCodigoTabela() {
		if (chaveUsuarioTabela != null
				&& chaveUsuarioTabela.getCodigoTabela() > 0) {
			return chaveUsuarioTabela.getCodigoTabela();
		}

		return codigoTabela;
	}

	public void setCodigoTabela(int codigoTabela) {
		this.codigoTabela = codigoTabela;
	}

	public UsuarioTabelaPK getChaveUsuarioTabela() {
		return chaveUsuarioTabela;
	}

	public void setChaveUsuarioTabela(UsuarioTabelaPK chaveUsuarioTabela) {
		this.chaveUsuarioTabela = chaveUsuarioTabela;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	public static List<UsuarioTabela> recupera(IGenericDAO<IPersistent> dao,
			String usuario, Integer tabelaCodigo) {

		List<String> whereFragmentos = new ArrayList<String>();

		if (StringUtil.isValida(usuario)) {
			whereFragmentos.add(COLUNA_USUARIO_LOGIN + " = " + usuario);
		}

		if (tabelaCodigo != null && tabelaCodigo > 0) {
			whereFragmentos.add(COLUNA_TABELA_CODIGO + " = " + tabelaCodigo);
		}

		String where = GenericoDAO.criarWhere(whereFragmentos);

		return dao.list(UsuarioTabela.class, where, null, null, null);
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
