/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "estacao")
@XmlRootElement
public class Estacao implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "est_codigo")
	private String codigo;

	@Basic(optional = false)
	@Column(name = "est_desc")
	private String descricao;

	/*
	 * @Column(name = "EST_CHAVE") private String chave;
	 */

	@Column(name = "est_imppadnf")
	private String impressoraPadraoNf;

	@Column(name = "est_imppadped")
	private String impressoraPadraoPedido;

	@Column(name = "est_leitoradoc")
	private Integer flagLeitoraDocumento;

	@Column(name = "est_stringlei")
	private String stringLeitora;

	@Column(name = "est_posstrlei")
	private Integer posicaoStringLeitora;

	@Column(name = "est_tamstrlei")
	private Integer tamanhoStringLeitora;

	@Column(name = "est_intapollo")
	private Integer flagIntegracaoApollo;

	@Column(name = "est_impapollo")
	private Integer flagImportacaoApollo;

	@Column(name = "est_expapollo")
	private Integer flagExportacaoApollo;

	@Lob
	@Column(name = "est_conapollo")
	private String conexaoApollo;

	@JoinColumn(name = "est_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filial;

	public Estacao() {
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getImpressoraPadraoNf() {
		return impressoraPadraoNf;
	}

	public void setImpressoraPadraoNf(String impressoraPadraoNf) {
		this.impressoraPadraoNf = impressoraPadraoNf;
	}

	public String getImpressoraPadraoPedido() {
		return impressoraPadraoPedido;
	}

	public void setImpressoraPadraoPedido(String impressoraPadraoPedido) {
		this.impressoraPadraoPedido = impressoraPadraoPedido;
	}

	public Integer getFlagLeitoraDocumento() {
		return flagLeitoraDocumento;
	}

	public void setFlagLeitoraDocumento(Integer flagLeitoraDocumento) {
		this.flagLeitoraDocumento = flagLeitoraDocumento;
	}

	public String getStringLeitora() {
		return stringLeitora;
	}

	public void setStringLeitora(String stringLeitora) {
		this.stringLeitora = stringLeitora;
	}

	public Integer getPosicaoStringLeitora() {
		return posicaoStringLeitora;
	}

	public void setPosicaoStringLeitora(Integer posicaoStringLeitora) {
		this.posicaoStringLeitora = posicaoStringLeitora;
	}

	public Integer getTamanhoStringLeitora() {
		return tamanhoStringLeitora;
	}

	public void setTamanhoStringLeitora(Integer tamanhoStringLeitora) {
		this.tamanhoStringLeitora = tamanhoStringLeitora;
	}

	public Integer getFlagIntegracaoApollo() {
		return flagIntegracaoApollo;
	}

	public void setFlagIntegracaoApollo(Integer flagIntegracaoApollo) {
		this.flagIntegracaoApollo = flagIntegracaoApollo;
	}

	public Integer getFlagImportacaoApollo() {
		return flagImportacaoApollo;
	}

	public void setFlagImportacaoApollo(Integer flagImportacaoApollo) {
		this.flagImportacaoApollo = flagImportacaoApollo;
	}

	public Integer getFlagExportacaoApollo() {
		return flagExportacaoApollo;
	}

	public void setFlagExportacaoApollo(Integer flagExportacaoApollo) {
		this.flagExportacaoApollo = flagExportacaoApollo;
	}

	public String getConexaoApollo() {
		return conexaoApollo;
	}

	public void setConexaoApollo(String conexaoApollo) {
		this.conexaoApollo = conexaoApollo;
	}

	public Filial getFilialCodigo() {
		return filial;
	}

	public void setFilialCodigo(Filial filialCodigo) {
		this.filial = filialCodigo;
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
}
