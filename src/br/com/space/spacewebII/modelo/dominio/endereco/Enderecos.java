/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.pessoa.Pessoa;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "enderecos")
@XmlRootElement
public class Enderecos implements Serializable, IPersistent, IEndereco {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_PESSOA = "end_pescodigo";
	private static final String COLUNA_CODIGO = "end_codigo";
	private static final String COLUNA_CLASSIFICACAO = "end_clecodigo";
	private static final String COLUNA_ATIVO = "end_ativo";

	@EmbeddedId
	protected EnderecosPK enderecosPK;

	@Column(name = "end_logradouro")
	private String logradouro;

	@Column(name = "end_ufsigla")
	private String ufsigla;

	@Column(name = "end_cep")
	private String cep;

	@Column(name = "end_fone1")
	private String telefone1;

	@Column(name = "end_fone2")
	private String telefone2;

	@Column(name = "end_email")
	private String email;

	@Column(name = "end_contato")
	private String contato;

	@Column(name = "end_celular")
	private String telefoneCelular;

	@Column(name = "end_fax")
	private String fax;

	@Column(name = "end_tipocontato")
	private String tipoContato;

	@Column(name = "end_pontoref")
	private String pontoReferencia;

	@Column(name = "end_datanasc")
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@Column(name = "end_numero")
	private String numero;

	@Column(name = COLUNA_ATIVO)
	private Integer flagAtivo;

	@Column(name = "end_complemento")
	private String complemento;

	@Column(name = "end_paicodigo")
	private Integer paisCodigo;

	@Column(name = "end_caixapostal")
	private String caixaPostal;

	@JoinColumn(name = "end_pescodigo", referencedColumnName = "pes_codigo", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Pessoa pessoa;

	@Column(name = "end_clecodigo")
	private Integer classificacaoEnderecoCodigo;

	@JoinColumn(name = "end_cidcodigo", referencedColumnName = "cid_codigo")
	@ManyToOne
	private Cidade cidade;

	@JoinColumn(name = "end_baicodigo", referencedColumnName = "bai_codigo")
	@ManyToOne
	private Bairro bairro;

	public Enderecos() {
	}

	public Enderecos(EnderecosPK enderecosPK) {
		this.enderecosPK = enderecosPK;
	}

	public Enderecos(int endPescodigo, int endCodigo) {
		this.enderecosPK = new EnderecosPK(endPescodigo, endCodigo);
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Bairro getBairro() {
		return bairro;
	}

	public String getCaixaPostal() {
		return caixaPostal;
	}

	public String getCep() {
		return cep;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public Integer getClassificacaoEnderecoCodigo() {
		return classificacaoEnderecoCodigo;
	}

	public int getCodigo() {
		return getEnderecosPK().getCodigo();
	}

	public String getComplemento() {
		return complemento;
	}

	public String getContato() {
		return contato;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public String getEmail() {
		return email;
	}

	public EnderecosPK getEnderecosPK() {
		if (enderecosPK == null) {
			enderecosPK = new EnderecosPK();
		}
		return enderecosPK;
	}

	public String getFax() {
		return fax;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public Integer getPaisCodigo() {
		return paisCodigo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public int getPessoaCodigo() {
		return getEnderecosPK().getPessoaCodigo();
	}

	public String getPontoReferencia() {
		return pontoReferencia;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	public String getTelefone1() {
		return telefone1;
	}

	public String getTelefone2() {
		return telefone2;
	}

	public String getTelefoneCelular() {
		return telefoneCelular;
	}

	public String getTipoContato() {
		return tipoContato;
	}

	public String getUfsigla() {
		return ufsigla;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public void setCaixaPostal(String caixaPostal) {
		this.caixaPostal = caixaPostal;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public void setClassificacaoEnderecoCodigo(Integer classificacaoEnderecoCodigo) {
		this.classificacaoEnderecoCodigo = classificacaoEnderecoCodigo;
	}

	public void setCodigo(int codigo) {
		getEnderecosPK().setCodigo(codigo);
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setEnderecosPK(EnderecosPK enderecosPK) {
		this.enderecosPK = enderecosPK;
	}

	public void setFax(String fax) {
		this.fax = fax;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public void setPaisCodigo(Integer paisCodigo) {
		this.paisCodigo = paisCodigo;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		getEnderecosPK().setPessoaCodigo(pessoaCodigo);
	}

	public void setPontoReferencia(String pontoReferencia) {
		this.pontoReferencia = pontoReferencia;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {

	}

	public void setTelefone1(String telefone1) {
		this.telefone1 = telefone1;
	}

	public void setTelefone2(String telefone2) {
		this.telefone2 = telefone2;
	}

	public void setTelefoneCelular(String telefoneCelular) {
		this.telefoneCelular = telefoneCelular;
	}

	public void setTipoContato(String tipoContato) {
		this.tipoContato = tipoContato;
	}

	public void setUfsigla(String ufsigla) {
		this.ufsigla = ufsigla;
	}

	/**
	 * 
	 */
	@Override
	public String toString() {
		return EnderecoUtil.toString(this);
	}

	/**
	 * 
	 * @param dao
	 * @param codigoPessoa
	 * @param codigoClassificacao
	 * @return
	 */
	public static Enderecos recuperarEnderecoPadrao(GenericoDAO dao, int codigoPessoa, int codigoClassificacao) {

		return dao.uniqueResult(Enderecos.class,
				COLUNA_PESSOA + " = " + codigoPessoa + " AND " + COLUNA_CLASSIFICACAO + " = " + codigoClassificacao,
				null);
	}

	/**
	 * 
	 * @param dao
	 * @param pessoaCodigo
	 * @return
	 */
	public static List<Enderecos> recuperarEnderecosAtivosPessoa(GenericoDAO dao, int pessoaCodigo) {
		String where = COLUNA_ATIVO + " = 1 AND " + COLUNA_PESSOA + " = " + pessoaCodigo;
		return dao.list(Enderecos.class, where, null, "classificacaoEnderecoCodigo", null);
	}

	/**
	 * 
	 * @param dao
	 * @param pessoaCodigo
	 * @param enderecoCodigo
	 * @return
	 */
	public static Enderecos recuperarUnico(GenericoDAO dao, int pessoaCodigo, int enderecoCodigo) {
		String where = COLUNA_PESSOA + " = " + pessoaCodigo + " and " + COLUNA_CODIGO + " = " + enderecoCodigo;
		return dao.uniqueResult(Enderecos.class, where, null);
	}

	/**
	 * 
	 * @param enderecos
	 * @param codigoClassificacao
	 * @return
	 */
	public static Enderecos localizarClassificacao(List<Enderecos> enderecos, int codigoClassificacao) {

		if (ListUtil.isValida(enderecos)) {
			for (Enderecos endereco : enderecos) {
				if (endereco.getClassificacaoEnderecoCodigo() == codigoClassificacao)
					return endereco;
			}
		}
		return null;
	}

	/**
	 * 
	 * @param enderecos
	 * @param codigoClassificacao
	 * @return
	 */
	public static Enderecos localizarEndereco(List<Enderecos> enderecos, int enderecoCodigo) {

		if (enderecos != null) {
			for (Enderecos endereco : enderecos) {
				if (endereco.getCodigo() == enderecoCodigo)
					return endereco;
			}
		}
		return null;

	}

	@Override
	public Integer getCidadeCodigo() {
		if (this.cidade != null)
			return this.cidade.getCodigoCidade();
		return 0;
	}

	@Override
	public Integer getBairroCodigo() {
		if (this.bairro != null)
			return this.bairro.getCodigoBairro();
		return 0;
	}
}
