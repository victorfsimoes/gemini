package br.com.space.spacewebII.modelo.dominio.sistema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.pessoa.Pessoa;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;

@Entity(name = "cliente")
@Table(name = "cliente")
public class UsuarioCliente implements IPersistent, UsuarioWeb {

	private static final long serialVersionUID = 1L;
	public static final String COLUNA_LOGIN = "cli_loginweb";
	public static final String COLUNA_SENHA = "cli_senhaweb";

	@Id
	@Column(name = "cli_pescodigo")
	private int pessoaCodigo;

	@Column(name = COLUNA_LOGIN)
	private String login;

	@Column(name = COLUNA_SENHA)
	private String senha;

	@Column(name = "cli_confemail")
	private String chaveConfirmacaoEmail;

	@Column(name = "cli_acessoweb")
	private Integer acessoWeb;

	@Transient
	private String nome = null;

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getChaveConfirmacaoEmail() {
		return chaveConfirmacaoEmail;
	}

	public void setChaveConfirmacaoEmail(String chaveConfirmacaoEmail) {
		this.chaveConfirmacaoEmail = chaveConfirmacaoEmail;
	}

	public Integer getAcessoWeb() {
		return acessoWeb;
	}

	public void setAcessoWeb(Integer acessoWeb) {
		this.acessoWeb = acessoWeb;
	}

	@Override
	public String getId() {
		return login;
	}

	@Override
	public String getKey() {
		return login + senha;
	}

	@Override
	public int getColaboradorCodigo() {
		return 0;
	}

	@Override
	public Integer getCodigoGrupo() {
		return 0;
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

	/**
	 * 
	 * @param dao
	 * @param filtroLogin
	 * @param filtroSenha
	 * @return
	 */
	public static UsuarioCliente recuperarUsuarioLogin(GenericoDAO<IPersistent> dao, String filtroLogin,
			String filtroSenha, boolean isExibeNomeFantasia) {

		UsuarioCliente usr = dao.uniqueResult(UsuarioCliente.class, UsuarioCliente.COLUNA_LOGIN + " = '" + filtroLogin
				+ "' and " + UsuarioCliente.COLUNA_SENHA + " = '" + filtroSenha + "'", null);

		if (usr != null) {
			Pessoa pessoa = Pessoa.recuperar(dao, usr.getPessoaCodigo());

			if (pessoa != null) {
				if (isExibeNomeFantasia) {
					usr.setNome(pessoa.getFantasia());
				} else {
					usr.setNome(pessoa.getRazao());
				}
			}
		}

		return usr;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	@Override
	public String getNome() {
		return nome;
	}
}
