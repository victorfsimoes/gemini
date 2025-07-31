package br.com.space.spacewebII.modelo.dominio.cliente;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.validator.Length;

import br.com.space.api.negocio.modelo.dominio.ICliente;
import br.com.space.api.negocio.modelo.dominio.IRegiao;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.padrao.interfaces.Travavel;

@Entity(name = ClienteImp.NOME_TABELA)
@Table(name = ClienteImp.NOME_TABELA)
@SpaceTable(name = ClienteImp.NOME_TABELA)
public class ClienteImp implements ICliente, IPersistent, Serializable, Travavel {

	private static final long serialVersionUID = 1L;
	private static br.com.space.api.spa.model.dao.db.Table TABLE = null;
	public static final String NOME_TABELA = "clienteemp";
	public static final String COLUNA_CODIGO = "cim_codigoseq";
	public static final String COLUNA_RAZAOSOC = "cim_razao";
	public static final String COLUNA_TIPOPESSOA = "cim_tipopessoa";

	@Id
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Column(name = "cim_codigoext")
	private int codigoExterno;

	@Column(name = "cim_codigoint")
	private int codigoInterno;

	@Column(name = "cim_filcodigo")
	private int codigoFilial;

	@Column(name = COLUNA_TIPOPESSOA, length = 1)
	@Length(max = 1)
	private String tipoPessoa = "J";

	@Column(name = "cim_cnpjcpf", length = 20)
	@Length(max = 20)
	private String cnpjCpf;

	@Column(name = "cim_rg", length = 15)
	@Length(max = 15)
	private String rg;

	@Column(name = "cim_insestadual", length = 20)
	@Length(max = 20)
	private String inscricaoEstadual;

	@Column(name = COLUNA_RAZAOSOC, length = 45)
	@Length(max = 45)
	private String razaoSocial;

	@Column(name = "cim_fantasia", length = 45)
	@Length(max = 45)
	private String nomeFantasia;

	@Column(name = "cim_logradouro", length = 45)
	@Length(max = 45)
	private String logradouro;

	@Column(name = "cim_numero", length = 10)
	@Length(max = 10)
	private String numero;

	@Column(name = "cim_baicodigo")
	private int codigoBairro;

	@Column(name = "cim_baidesc", length = 35)
	@Length(max = 35)
	private String descricaoBairro;

	@Column(name = "cim_cidcodigo")
	private int codigoCidade;

	@Column(name = "cim_ciddesc", length = 35)
	@Length(max = 35)
	private String descricaoCidade;

	@Column(name = "cim_atvcodigo")
	private int codigoAtividade;

	@Column(name = "cim_atvdesc", length = 35)
	@Length(max = 35)
	private String descricaoAtividade;

	@Column(name = "cim_cep", length = 10)
	@Length(max = 10)
	private String cep;

	@Column(name = "cim_ufsigla", length = 2)
	@Length(max = 2)
	private String ufSigla;

	@Column(name = "cim_fone1", length = 15)
	@Length(max = 15)
	private String fone1;

	@Column(name = "cim_fone2", length = 15)
	@Length(max = 15)
	private String fone2;

	@Column(name = "cim_contato", length = 30)
	@Length(max = 30)
	private String contato;

	@Column(name = "cim_pontoref", length = 40)
	@Length(max = 40)
	private String pontoreferencia;

	@Column(name = "cim_clbcodigo")
	private int codigoColaborador;

	@Temporal(TemporalType.DATE)
	@Column(name = "cim_datapre", length = 10)
	private Date dataPreCadastro;

	@Temporal(TemporalType.DATE)
	@Column(name = "cim_dataefe", length = 10)
	private Date dataEfetivacao;

	@Column(name = "cim_status", length = 1)
	@Length(max = 1)
	private String status;

	@Column(name = "cim_ref1", length = 100)
	@Length(max = 100)
	private String referenciaComercial;

	@Column(name = "cim_horapre", length = 8)
	@Length(max = 8)
	private String horaPreCadastro;

	@Column(name = "cim_horaefe", length = 8)
	@Length(max = 8)
	private String horaEfetivacao;

	@Column(name = "cim_motrejeita", length = 35)
	@Length(max = 35)
	private String motivoRejeicao;

	@Column(name = "cim_email", length = 100)
	@Length(max = 100)
	private String email;

	@Column(name = "cim_compl", length = 20)
	@Length(max = 20)
	private String complemento;

	public ClienteImp() {

	}

	public int getCodigoExterno() {
		return codigoExterno;
	}

	public void setCodigoExterno(int codigoExterno) {
		this.codigoExterno = codigoExterno;
	}

	public int getCodigoInterno() {
		return codigoInterno;
	}

	public void setCodigoInterno(int codigoInterno) {
		this.codigoInterno = codigoInterno;
	}

	public int getCodigoFilial() {
		return codigoFilial;
	}

	public void setCodigoFilial(int codigoFilial) {
		this.codigoFilial = codigoFilial;
	}

	@Override
	public String getTipoPessoa() {
		return tipoPessoa;
	}

	@Override
	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	@Override
	public String getCnpjCpf() {
		return cnpjCpf;
	}

	@Override
	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	@Override
	public String getRg() {
		return rg;
	}

	@Override
	public void setRg(String rg) {
		this.rg = rg;
	}

	@Override
	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	@Override
	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	@Override
	public String getNomeFantasia() {
		return nomeFantasia;
	}

	@Override
	public void setNomeFantasia(String nomeFantasia) {
		this.nomeFantasia = nomeFantasia;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public int getCodigoBairro() {
		return codigoBairro;
	}

	public void setCodigoBairro(int codigoBairro) {
		this.codigoBairro = codigoBairro;
	}

	public String getDescricaoBairro() {
		return descricaoBairro;
	}

	public void setDescricaoBairro(String descricaoBairro) {
		this.descricaoBairro = descricaoBairro;
	}

	public int getCodigoCidade() {
		return codigoCidade;
	}

	public void setCodigoCidade(int codigoCidade) {
		this.codigoCidade = codigoCidade;
	}

	public String getDescricaoCidade() {
		return descricaoCidade;
	}

	public void setDescricaoCidade(String descricaoCidade) {
		this.descricaoCidade = descricaoCidade;
	}

	public int getCodigoAtividade() {
		return codigoAtividade;
	}

	public void setCodigoAtividade(int codigoAtividade) {
		this.codigoAtividade = codigoAtividade;
	}

	public String getDescricaoAtividade() {
		return descricaoAtividade;
	}

	public void setDescricaoAtividade(String descricaoAtividade) {
		this.descricaoAtividade = descricaoAtividade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public String getUfSigla() {
		return ufSigla;
	}

	public void setUfSigla(String ufSigla) {
		this.ufSigla = ufSigla;
	}

	public String getFone1() {
		return fone1;
	}

	public void setFone1(String fone1) {
		this.fone1 = fone1;
	}

	public String getFone2() {
		return fone2;
	}

	public void setFone2(String fone2) {
		this.fone2 = fone2;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getPontoreferencia() {
		return pontoreferencia;
	}

	public void setPontoreferencia(String pontoreferencia) {
		this.pontoreferencia = pontoreferencia;
	}

	public int getCodigoColaborador() {
		return codigoColaborador;
	}

	public void setCodigoColaborador(int codigoColaborador) {
		this.codigoColaborador = codigoColaborador;
	}

	public Date getDataPreCadastro() {
		return dataPreCadastro;
	}

	public void setDataPreCadastro(Date dataPreCadastro) {
		this.dataPreCadastro = dataPreCadastro;
	}

	public Date getDataEfetivacao() {
		return dataEfetivacao;
	}

	public void setDataEfetivacao(Date dataEfetivacao) {
		this.dataEfetivacao = dataEfetivacao;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getReferenciaComercial() {
		return referenciaComercial;
	}

	public void setReferenciaComercial(String referenciaComercial) {
		this.referenciaComercial = referenciaComercial;
	}

	public String getHoraPreCadastro() {
		return horaPreCadastro;
	}

	public void setHoraPreCadastro(String horaPreCadastro) {
		this.horaPreCadastro = horaPreCadastro;
	}

	public String getHoraEfetivacao() {
		return horaEfetivacao;
	}

	public void setHoraEfetivacao(String horaEfetivacao) {
		this.horaEfetivacao = horaEfetivacao;
	}

	public String getMotivoRejeicao() {
		return motivoRejeicao;
	}

	public void setMotivoRejeicao(String motivoRejeicao) {
		this.motivoRejeicao = motivoRejeicao;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public int getCodigo() {
		return this.codigo;
	}

	@Override
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getRazao() {
		return this.razaoSocial;
	}

	@Override
	public void setRazao(String razaoSocial) {
		this.razaoSocial = razaoSocial;
	}

	@Override
	public String getSituacao1() {
		return null;
	}

	@Override
	public void setSituacao1(String situacao1) {

	}

	@Override
	public String getSituacao2() {

		return null;
	}

	@Override
	public void setSituacao2(String situacao2) {

	}

	@Override
	public String getSituacao3() {

		return null;
	}

	@Override
	public void setSituacao3(String situacao3) {

	}

	@Override
	public String getSituacao4() {

		return null;
	}

	@Override
	public void setSituacao4(String situacao4) {

	}

	@Override
	public int getFlagDebitoCredito() {

		return 0;
	}

	@Override
	public void setFlagDebitoCredito(int flagDebitoCredito) {

	}

	@Override
	public Object clone() throws CloneNotSupportedException {

		return super.clone();
	}

	@Override
	public double getValorCredito() {

		return 0;
	}

	@Override
	public void setValorCredito(double credito) {

	}

	@Override
	public double getValorDebito() {

		return 0;
	}

	@Override
	public void setValorDebito(double debito) {

	}

	@Override
	public int getOpcaoEspecialCodigo() {

		return 0;
	}

	@Override
	public void setOpcaoEspecialCodigo(int opcaoEspecialCodigo) {

	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return TABLE;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		TABLE = table;

	}

	@Override
	public double getDescontoMaximo() {

		return 0;
	}

	@Override
	public void setDescontoMaximo(double descontoMaximo) {

	}

	@Override
	public double getAcrescimoMaximo() {

		return 0;
	}

	@Override
	public void setAcrescimoMaximo(double acrescimoMaximo) {

	}

	@Override
	public double getPercentualDescontoEspecial() {

		return 0;
	}

	@Override
	public void setPercentualDescontoEspecial(double percentualDescontoEspecial) {

	}

	@Override
	public double getPercentualAcrescimoEspecial() {

		return 0;
	}

	@Override
	public void setPercentualAcrescimoEspecial(double percentualAcrescimoEspecial) {

	}

	@Override
	public int getPrazoEspecial() {
		return 0;
	}

	@Override
	public int getPrazoMaximo() {

		return 0;
	}

	@Override
	public int getTabelaPrecoCodigo() {

		return 0;
	}

	@Override
	public int getTabelaPrecoPromocionalCodigo() {

		return 0;
	}

	@Override
	public int getTabelaPrecoInternaCodigo() {

		return 0;
	}

	@Override
	public int getTabelaPrecoInternaPromocionalCodigo() {

		return 0;
	}

	@Override
	public int getTabelaPrecoExternaCodigo() {

		return 0;
	}

	@Override
	public int getTabelaPrecoExternaPromocionalCodigo() {

		return 0;
	}

	@Override
	public String getChaveTrava() {
		return String.valueOf(this.codigo);
	}

	@Override
	public String getNomeTabelaTrava() {
		return ClienteImp.NOME_TABELA;
	}

	@Override
	public String getSituacaoContribuinte() {
		return null;
	}

	@Override
	public void setSituacaoContribuinte(String situacaoContribuinte) {

	}

	@Override
	public Date getDataUltimaCompra() {

		return null;
	}

	@Override
	public double getValorUltimaCompra() {

		return 0;
	}

	@Override
	public void setDataUltimaCompra(Date arg0) {

	}

	@Override
	public void setValorUltimaCompra(double arg0) {

	}

	@Override
	public Date getDataPrimeiraCompra() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setDataPrimeiraCompra(Date arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public IRegiao getRegiao() {
		return null;
	}

	@Override
	public int getRegiaoCodigo() {
		return 0;
	}

	@Override
	public Integer getAtividadeCodigo() {
		return null;
	}
}
