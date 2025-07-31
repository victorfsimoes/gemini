/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.endereco.Bairro;
import br.com.space.spacewebII.modelo.dominio.endereco.Cidade;
import br.com.space.spacewebII.modelo.dominio.pessoa.Colaborador;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "localest")
@XmlRootElement
public class LocalEstoque implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "lce_codigo")
	private int codigo;

	@Column(name = "lce_desc")
	private String descricao;

	@Column(name = "lce_ativo")
	private Integer flagAtivo;

	@Column(name = "lce_cnpj")
	private String cnpj;

	@Column(name = "lce_logradouro")
	private String logradouro;

	@Column(name = "lce_numero")
	private String numeroEndereco;

	@Column(name = "lce_ufsigla")
	private String ufSigla;

	@Column(name = "lce_cep")
	private String cep;

	@Column(name = "lce_fone")
	private String telefone;

	@Column(name = "lce_terceirizad")
	private Integer flagTerceirizado;

	@Column(name = "lce_bloqentrada")
	private Integer flagBloqueiaEntrada;

	@Column(name = "lce_inscest")
	private String inscricaoEstadual;

	@Column(name = "lce_enderecado")
	private Integer flagEnderecado;

	@Column(name = "lce_exigepermis")
	private Integer flagExigePermissao;

	@JoinColumn(name = "lce_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filial;

	@JoinColumn(name = "lce_clbcodigo", referencedColumnName = "clb_codigo")
	@ManyToOne
	private Colaborador colaboradorCodigo;

	@JoinColumn(name = "lce_cidcodigo", referencedColumnName = "cid_codigo")
	@ManyToOne
	private Cidade cidade;

	@JoinColumn(name = "lce_baicodigo", referencedColumnName = "bai_codigo")
	@ManyToOne
	private Bairro bairro;

	public LocalEstoque() {
	}

	public LocalEstoque(int lceCodigo) {
		this.codigo = lceCodigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getCnpj() {
		return cnpj;
	}

	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getUfSigla() {
		return ufSigla;
	}

	public void setUfSigla(String ufSigla) {
		this.ufSigla = ufSigla;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public Integer getFlagTerceirizado() {
		return flagTerceirizado;
	}

	public void setFlagTerceirizado(Integer flagTerceirizado) {
		this.flagTerceirizado = flagTerceirizado;
	}

	public Integer getFlagBloqueiaEntrada() {
		return flagBloqueiaEntrada;
	}

	public void setFlagBloqueiaEntrada(Integer flagBloqueiaEntrada) {
		this.flagBloqueiaEntrada = flagBloqueiaEntrada;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public Integer getFlagEnderecado() {
		return flagEnderecado;
	}

	public void setFlagEnderecado(Integer flagEnderecado) {
		this.flagEnderecado = flagEnderecado;
	}

	public Integer getFlagExigePermissao() {
		return flagExigePermissao;
	}

	public void setFlagExigePermissao(Integer flagExigePermissao) {
		this.flagExigePermissao = flagExigePermissao;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	public Colaborador getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(Colaborador colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
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

	public static List<LocalEstoque> recuperaLocaisEstoque(GenericoDAO dao,
			Integer codigoFilial, Integer codigoColaborador) {
		StringBuilder where = new StringBuilder();
		if (codigoFilial != null)
			where.append("lce_filcodigo = ").append(codigoFilial);
		if (!where.equals("") && codigoColaborador != null)
			where.append(" and ");
		if (codigoColaborador != null)
			where.append("lce_clbcodigo = ").append(codigoColaborador);

		return dao.list(LocalEstoque.class, where.toString(), null, null, null);
	}

}
