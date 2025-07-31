package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.space.api.core.sistema.CodigoSistema;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;

@Table(name = "sessao")
@Entity(name = "sessao")
public class Sessao implements IPersistent, Serializable {

	private static final long serialVersionUID = 1L;
	public static int CODIGO_SISTEMA_GUARDIAN = 0;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	/*
	 * Cuidado ao mudar @GeneratedValue pois se remover esta anotação o codigo
	 * tera que ser setado manualmente
	 */
	@Column(name = "ses_codigo", columnDefinition = "serial")
	private int codigo = 0;

	@Column(name = "ses_usuario")
	private String usuario;

	@Column(name = "ses_dataini")
	@Temporal(TemporalType.DATE)
	private Date dataInicial;

	@Column(name = "ses_datafim")
	@Temporal(TemporalType.DATE)
	private Date dataFinal;

	@Column(name = "ses_horaini", length = 10)
	private String horaInicial;

	@Column(name = "ses_horafim", length = 10)
	private String horaFinal;

	@Column(name = "ses_estacao", length = 30)
	private String estacao;

	@Column(name = "ses_obs", length = 200)
	private String observacao;

	@Column(name = "ses_sessaohttp", length = 100)
	private String sessaoHttp = null;

	@Column(name = "ses_sistema")
	private int sistema = CodigoSistema.VENDA_WEB;

	@Column(name = "ses_filcodigo")
	private int filialCodigo = 0;

	public Sessao() {

		super();
	}

	public Sessao(String usuario, Date dataInicial, String horaInicial,
			String estacao, String observacao) {
		this();

		this.usuario = usuario;
		this.dataInicial = dataInicial;
		this.horaInicial = horaInicial;
		this.estacao = estacao;
		this.observacao = observacao;
	}

	public Sessao(String usuario, Date dataInicial, String horaInicial,
			String estacao, String observacao, String sessaoHttp,
			int filialCodigo) {

		this(usuario, dataInicial, horaInicial, estacao, observacao);

		this.sessaoHttp = sessaoHttp;
		this.filialCodigo = filialCodigo;

	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public Date getDataInicial() {
		return dataInicial;
	}

	public void setDataInicial(Date dataInicial) {
		this.dataInicial = dataInicial;
	}

	public Date getDataFinal() {
		return dataFinal;
	}

	public void setDataFinal(Date dataFinal) {
		this.dataFinal = dataFinal;
	}

	public String getHoraInicial() {
		return horaInicial;
	}

	public void setHoraInicial(String horaInicial) {
		this.horaInicial = horaInicial;
	}

	public String getHoraFinal() {
		return horaFinal;
	}

	public void setHoraFinal(String horaFinal) {
		this.horaFinal = horaFinal;
	}

	public String getEstacao() {
		return estacao;
	}

	public void setEstacao(String estacao) {
		this.estacao = estacao;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public String getSessaoHttp() {
		return sessaoHttp;
	}

	public void setSessaoHttp(String sessaoHttp) {
		this.sessaoHttp = sessaoHttp;
	}

	public int getSistema() {
		return sistema;
	}

	public void setSistema(int sistema) {
		this.sistema = sistema;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public static List<Sessao> recuperarSessoesAbertas(GenericoDAOLog dao,
			int codigoSistema, int filialCodigo) {

		String where = "ses_sistema = "
				+ codigoSistema
				+ " and ses_filcodigo = "
				+ filialCodigo
				+ " and ses_datafim is null and (ses_horafim is null or ses_horafim = '')";

		return dao.list(Sessao.class, where, null, null, null);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {

	}

}
