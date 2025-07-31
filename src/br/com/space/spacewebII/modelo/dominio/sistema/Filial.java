package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.endereco.Bairro;
import br.com.space.spacewebII.modelo.dominio.endereco.Cidade;
import br.com.space.spacewebII.modelo.dominio.endereco.Uf;

@Table(name = "filial")
@Entity(name = "filial")
public class Filial implements IPersistent, Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_ATIVO = "fil_ativo";

	@Id
	@OneToMany(mappedBy = "usrfilial")
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "fil_codigo", insertable = false, updatable = false)
	@Column(name = "fil_codigo")
	private int codigo;

	@Column(name = "fil_razao", length = 45)
	private String razaoSocial;

	@Column(name = "fil_fantasia", length = 45)
	private String fantasia;

	@Column(name = COLUNA_ATIVO, length = 45)
	private int ativo = 0;

	@Column(name = "fil_cnpj")
	private String cnpj;

	@Column(name = "fil_ufsigla")
	private String ufSigla;
	
	@Column(name = "fil_email")
	private String email;
	
	@Column(name="fil_telefone1")
	private String telefone1;
	
	@Column(name="fil_telefone2")
	private String telefone2;

	@Column(name="fil_cep")
	private String cep;
	
	@Column(name="fil_end")
	private String logradouro;
	
	@Column(name="fil_numero")
	private String numeroLogradouro;
	
	@Column(name="fil_complemento")
	private String complemento;
	
	@Column(name = "fil_inscest")
	private String inscricaoEstadual;
	
	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "fil_baicodigo", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Bairro bairro;
	
	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "fil_cidcodigo", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Cidade cidade;

	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "fil_ufsigla", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Uf uf;

	
	public Filial() {
		super();
	}

	public static Filial recuperarUnico(GenericoDAO<IPersistent> dao, int codigo) {

		String where = "fil_codigo = " + codigo;

		return dao.uniqueResult(Filial.class, where, null);
	}

	/*
	 * Gets e Setters
	 */

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getUfSigla() {
		return ufSigla;
	}

	public void setUfSigla(String ufSigla) {
		this.ufSigla = ufSigla;
	}

	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumeroLogradouro() {
		return numeroLogradouro;
	}

	public void setNumeroLogradouro(String numeroLogradouro) {
		this.numeroLogradouro = numeroLogradouro;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Uf getUf() {
		return uf;
	}

	public void setUf(Uf uf) {
		this.uf = uf;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {

	}

}
