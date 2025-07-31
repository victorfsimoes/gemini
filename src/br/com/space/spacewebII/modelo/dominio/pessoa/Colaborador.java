/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "colaborador")
@XmlRootElement
public class Colaborador implements Serializable, IPersistent, Comparable<Colaborador> {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_ATIVO = "clb_ativo";

	private static final String COLUNA_CODIGO = "clb_codigo";

	@Id
	@Column(name = "clb_codigo")
	private Integer codigo;

	@Column(name = "clb_razao")
	private String razaoSocial;

	@Column(name = "clb_tipopessoa")
	private String tipoPessoa;

	@Column(name = "clb_cnpjcpf")
	private String cnpjCpf;

	@Column(name = "clb_inscest")
	private String inscricaoEstadual;

	@Column(name = "clb_inscmun")
	private String inscricaoMunicipal;

	@Column(name = "clb_rg")
	private String rg;

	@Column(name = "clb_cep")
	private String enderecoCep;

	@Column(name = "clb_logradouro")
	private String logradouroEndereco;

	@Column(name = "clb_numero")
	private String numeroEndereco;

	@Column(name = "clb_baicodigo")
	private Integer bairroCodigo;

	@Column(name = "clb_cidcodigo")
	private Integer cidadeCodigo;

	@Column(name = "clb_ufsigla")
	private String ufSigla;

	@Column(name = "clb_fone1")
	private String telefone1;

	@Column(name = "clb_fone2")
	private String telefone2;

	@Column(name = "clb_fax")
	private String fax;

	@Column(name = "clb_celular")
	private String telefoneCelular;

	@Column(name = "clb_homepage")
	private String homePage;

	@Column(name = "clb_email")
	private String email;

	@Column(name = "clb_sexo")
	private String sexo;

	@Column(name = "clb_datanasc")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(name = "clb_datainicio")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(name = "clb_datacad")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(name = "clb_clbcodigo")
	private Integer colaboradorCodigoSupervisor;

	@JoinColumn(name = "clb_clbcodigo", referencedColumnName = "clb_codigo", insertable = false, updatable = false)
	@NotFound(action=NotFoundAction.IGNORE)
	@OneToOne(optional = true)
	private Colaborador colaboradorSupervisor;

	@Column(name = "clb_estadocivil")
	private String estadoCivil;

	@Lob
	@Column(name = "clb_obs")
	private String observacao;

	@Column(name = "clb_terceiriza")
	private Integer flagTerceirizado;

	@Column(name = "clb_ctps")
	private String carteiraTrabalhoProfissional;

	@Column(name = COLUNA_ATIVO)
	private Integer flagAtivo;

	@Column(name = "clb_horasemanal")
	private String horasSemanais;

	@Column(name = "clb_datadeslig")
	@Temporal(TemporalType.DATE)
	private Date dataDesligamento;

	@Column(name = "clb_numcontrato")
	private String numeroContrato;

	@Column(name = "clb_complemento")
	private String complementoEndereco;

	@Column(name = "clb_crc")
	private String codigoRegistroContador;

	@Column(name = "clb_banco")
	private String banco;

	@Column(name = "clb_digbanco")
	private String digitoBanco;

	@Column(name = "clb_agencia")
	private String agencia;

	@Column(name = "clb_digagencia")
	private String digitoAgencia;

	@Column(name = "clb_conta")
	private String contaBanco;

	@Column(name = "clb_digconta")
	private String digitoContaBanco;

	@Column(name = "clb_tipoconta")
	private String tipoContaBanco;

	@Column(name = "clb_matricula")
	private String matricula;

	@Column(name = "clb_titularcc")
	private String titularContaBanco;

	@Column(name = "clb_cpfcc")
	private String cpfTitularContaBanco;

	@JoinColumn(name = "clb_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filial;

	@JoinColumn(name = "clb_crgcodigo", referencedColumnName = "crg_codigo")
	@ManyToOne
	private Cargo cargo;

	public Colaborador() {
	}

	public Colaborador(Integer clbCodigo) {
		this.codigo = clbCodigo;
	}

	public Colaborador(Integer clbCodigo, String clbRazao) {
		this.codigo = clbCodigo;
		this.razaoSocial = clbRazao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getRazaoSocial() {
		return razaoSocial;
	}

	public void setRazaoSocial(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getEnderecoCep() {
		return enderecoCep;
	}

	public void setEnderecoCep(String enderecoCep) {
		this.enderecoCep = enderecoCep;
	}

	public String getLogradouroEndereco() {
		return logradouroEndereco;
	}

	public void setLogradouroEndereco(String logradouroEndereco) {
		this.logradouroEndereco = logradouroEndereco;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public Integer getBairroCodigo() {
		return bairroCodigo;
	}

	public void setBairroCodigo(Integer bairroCodigo) {
		this.bairroCodigo = bairroCodigo;
	}

	public Integer getCidadeCodigo() {
		return cidadeCodigo;
	}

	public void setCidadeCodigo(Integer cidadeCodigo) {
		this.cidadeCodigo = cidadeCodigo;
	}

	public String getUfSigla() {
		return ufSigla;
	}

	public void setUfSigla(String ufSigla) {
		this.ufSigla = ufSigla;
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

	public String getFax() {
		return fax;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Integer getColaboradorCodigoSupervisor() {
		return colaboradorCodigoSupervisor;
	}

	public void setColaboradorCodigoSupervisor(Integer colaboradorCodigoSupervisor) {
		this.colaboradorCodigoSupervisor = colaboradorCodigoSupervisor;
	}
	
	public Colaborador getColaboradorSupervisor() {
		return colaboradorSupervisor;
	}

	public void setColaboradorSupervisor(Colaborador colaboradorSupervisor) {
		this.colaboradorSupervisor = colaboradorSupervisor;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Integer getFlagTerceirizado() {
		return flagTerceirizado;
	}

	public void setFlagTerceirizado(Integer flagTerceirizado) {
		this.flagTerceirizado = flagTerceirizado;
	}

	public String getCarteiraTrabalhoProfissional() {
		return carteiraTrabalhoProfissional;
	}

	public void setCarteiraTrabalhoProfissional(String carteiraTrabalhoProfissional) {
		this.carteiraTrabalhoProfissional = carteiraTrabalhoProfissional;
	}

	public boolean isAtivo() {
		return flagAtivo != null && flagAtivo == 1;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getHorasSemanais() {
		return horasSemanais;
	}

	public void setHorasSemanais(String horasSemanais) {
		this.horasSemanais = horasSemanais;
	}

	public Date getDataDesligamento() {
		return dataDesligamento;
	}

	public void setDataDesligamento(Date dataDesligamento) {
		this.dataDesligamento = dataDesligamento;
	}

	public String getNumeroContrato() {
		return numeroContrato;
	}

	public void setNumeroContrato(String numeroContrato) {
		this.numeroContrato = numeroContrato;
	}

	public String getComplementoEndereco() {
		return complementoEndereco;
	}

	public void setComplementoEndereco(String complementoEndereco) {
		this.complementoEndereco = complementoEndereco;
	}

	public String getCodigoRegistroContador() {
		return codigoRegistroContador;
	}

	public void setCodigoRegistroContador(String codigoRegistroContador) {
		this.codigoRegistroContador = codigoRegistroContador;
	}

	public String getBanco() {
		return banco;
	}

	public void setBanco(String banco) {
		this.banco = banco;
	}

	public String getDigitoBanco() {
		return digitoBanco;
	}

	public void setDigitoBanco(String digitoBanco) {
		this.digitoBanco = digitoBanco;
	}

	public String getAgencia() {
		return agencia;
	}

	public void setAgencia(String agencia) {
		this.agencia = agencia;
	}

	public String getDigitoAgencia() {
		return digitoAgencia;
	}

	public void setDigitoAgencia(String digitoAgencia) {
		this.digitoAgencia = digitoAgencia;
	}

	public String getContaBanco() {
		return contaBanco;
	}

	public void setContaBanco(String contaBanco) {
		this.contaBanco = contaBanco;
	}

	public String getDigitoContaBanco() {
		return digitoContaBanco;
	}

	public void setDigitoContaBanco(String digitoContaBanco) {
		this.digitoContaBanco = digitoContaBanco;
	}

	public String getTipoContaBanco() {
		return tipoContaBanco;
	}

	public void setTipoContaBanco(String tipoContaBanco) {
		this.tipoContaBanco = tipoContaBanco;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setMatricula(String matricula) {
		this.matricula = matricula;
	}

	public String getTitularContaBanco() {
		return titularContaBanco;
	}

	public void setTitularContaBanco(String titularContaBanco) {
		this.titularContaBanco = titularContaBanco;
	}

	public String getCpfTitularContaBanco() {
		return cpfTitularContaBanco;
	}

	public void setCpfTitularContaBanco(String cpfTitularContaBanco) {
		this.cpfTitularContaBanco = cpfTitularContaBanco;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	@Override
	public int compareTo(Colaborador o) {
		if (getCodigo() > o.getCodigo()) {
			return -1;
		} else if (getCodigo() == o.getCodigo()) {
			return 0;
		} else {
			return 1;
		}
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static Colaborador recuperarUnico(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(Colaborador.class, COLUNA_CODIGO + " = " + codigo, null);
	}

	public static Colaborador recuperarAtendente(GenericoDAO dao, int codigo) {
		String select = "select colaborador.* from colaborador inner join cargo on clb_codigo = " + codigo
				+ " and clb_crgcodigo = crg_codigo and crg_atendente = 1";

		return dao.uniqueResult(Colaborador.class, select);
	}
}
