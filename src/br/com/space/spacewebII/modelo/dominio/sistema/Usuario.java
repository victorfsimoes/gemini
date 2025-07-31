package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;

@Entity(name = "usuario")
@Table(name = "usuario")
public class Usuario implements IPersistent, Serializable, UsuarioWeb {

	private static final long serialVersionUID = 1L;
	public static final String COLUNA_LOGIN = "usr_login";
	public static final String COLUNA_SENHA = "usr_senha";
	public static final String COLUNA_CLB_CODIGO = "usr_clbcodigo";

	@Id
	@Column(name = COLUNA_LOGIN)
	private String login;

	@Column(name = COLUNA_SENHA)
	private String senha;

	@Column(name = "usr_grucodigo")
	private Integer codigoGrupo;

	@Column(name = "usr_nome")
	private String nome;

	@Column(name = "usr_clbcodigo")
	private int colaboradorCodigo;

	@Column(name = "usr_ativo")
	private int ativo;

	@Column(name = "usr_altsenha")
	private int altSenha;

	@Column(name = "usr_sisguardian")
	private int sistemaGuardian;

	@Column(name = "usr_sisweb")
	private int sistemaWeb;

	@Column(name = "usr_sisviking")
	private int sistemaViking;

	@Column(name = "usr_senhavik")
	private String senhaViking;

	@Column(name = "usr_serialvik")
	private String serialViking;

	@Column(name = "usr_pastavik")
	private String pastaViking;

	@Column(name = "usr_tipoconvik")
	private Integer tipoConexao;

	@Column(name = "usr_libloginvik")
	private Integer flagLiberaPrimeiroLogin;

	public Usuario() {

	}

	/*
	 * Gets e Setters
	 */

	@Override
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	@Override
	public Integer getCodigoGrupo() {
		return codigoGrupo;
	}

	public void setCodigoGrupo(Integer codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}

	@Override
	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public int getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(int colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public int getAltSenha() {
		return altSenha;
	}

	public void setAltSenha(int altSenha) {
		this.altSenha = altSenha;
	}

	public int getSistemaViking() {
		return sistemaViking;
	}

	public void setSistemaViking(int sistemaViking) {
		this.sistemaViking = sistemaViking;
	}

	public String getSenhaViking() {
		return senhaViking;
	}

	public void setSenhaViking(String senhaViking) {
		this.senhaViking = senhaViking;
	}

	public String getSerialViking() {
		return serialViking;
	}

	public void setSerialViking(String serialViking) {
		this.serialViking = serialViking;
	}

	public String getPastaViking() {
		return pastaViking;
	}

	public void setPastaViking(String pastaViking) {
		this.pastaViking = pastaViking;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
	}

	@Override
	public Usuario clone() throws CloneNotSupportedException {
		return (Usuario) super.clone();
	}

	public Integer getTipoConexao() {
		if (tipoConexao == null) {
			tipoConexao = 0;
		}
		return tipoConexao;
	}

	public int getSistemaGuardian() {
		return sistemaGuardian;
	}

	public void setSistemaGuardian(int sistemaGuardian) {
		this.sistemaGuardian = sistemaGuardian;
	}

	public int getSistemaWeb() {
		return sistemaWeb;
	}

	public void setSistemaWeb(int sistemaWeb) {
		this.sistemaWeb = sistemaWeb;
	}

	public void setTipoConexao(Integer tipoConexao) {
		this.tipoConexao = tipoConexao;
	}

	public Integer getFlagLiberaPrimeiroLogin() {
		return flagLiberaPrimeiroLogin;
	}

	public void setFlagLiberaPrimeiroLogin(Integer flagLiberaPrimeiroLogin) {
		this.flagLiberaPrimeiroLogin = flagLiberaPrimeiroLogin;
	}

	/**
	 * Retorna um único usuário de acordo com seu login e senha.
	 * 
	 * @param dao
	 * @param filtroLogin
	 *            Login do usuário
	 * @param filtroSenha
	 *            Senha do usuário
	 * @return
	 */
	public static Usuario recuperarUsuarioLogin(GenericoDAO<IPersistent> dao,
			String filtroLogin, String filtroSenha) {

		Usuario usr = dao.uniqueResult(Usuario.class, Usuario.COLUNA_LOGIN
				+ " = '" + filtroLogin + "' and " + Usuario.COLUNA_SENHA
				+ " = '" + filtroSenha + "' and usr_ativo = 1", null);

		return usr;
	}

	public boolean isVendedor(GenericoDAO<IPersistent> dao,
			int colaboradorCodigo) {

		long count = dao.count("vendedor", "ven_clbcodigo = "
				+ colaboradorCodigo);

		return count >= 1;
	}

	public boolean isSupervisorDeVenda(GenericoDAO<IPersistent> dao,
			int colaboradorCodigo) {

		String from = "colaborador" + " inner join" + " cargo"
				+ " on clb_crgcodigo = crg_codigo" + " and crg_supvenda = 1 "
				+ "and clb_codigo = " + colaboradorCodigo;

		long count = dao.count(from, null);

		return count >= 1;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.picketlink.idm.api.IdentityType#getKey()
	 */
	@Override
	public String getKey() {
		return login + senha;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.picketlink.idm.api.User#getId()
	 */
	@Override
	public String getId() {
		return login;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return login;
	}
}
