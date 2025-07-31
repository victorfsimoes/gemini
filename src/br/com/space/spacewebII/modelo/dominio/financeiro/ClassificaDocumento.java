/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.financeiro;

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
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "classificadoc")
@XmlRootElement
public class ClassificaDocumento implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final String TIPO_OUTROS = "O";
	public static final String TIPO_DUPLICATA = "D";
	public static final String TIPO_BOLETA = "B";
	public static final String TIPO_DINHEIRO = "H";
	public static final String TIPO_CHEQUE = "C";
	public static final String TIPO_CREDITO = "R";
	public static final String TIPO_PROMISSORIA = "P";
	public static final String TIPO_LIQUIDA_CREDITO = "L";
	public static final String TIPO_CARTAO = "T";

	@Id
	@Basic(optional = false)
	@Column(name = "cld_codigo")
	private Integer codigo;

	@Column(name = "cld_desc")
	private String descricao;

	@Column(name = "cld_ativo")
	private Integer flagAtivo;

	/**
	 * O=Outros, D=Duplicata, B=Boleta, H=Dinheiro, C=Cheque, R=Crédito,
	 * P=Promissória , L=Liquida Crédito, T=Cartão
	 */
	@Column(name = "cld_tipo")
	private String tipo;

	@JoinColumn(name = "cld_filcodigo", referencedColumnName = "fil_codigo")
	@ManyToOne
	private Filial filial;

	public ClassificaDocumento() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public Filial getCldFilcodigo() {
		return filial;
	}

	public void setCldFilcodigo(Filial cldFilcodigo) {
		this.filial = cldFilcodigo;
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

	/**
	 * 
	 * @param classificacoes
	 * @param classificacaoCodigo
	 * @return
	 */
	public static ClassificaDocumento pesquisarClassificaDocumentoLista(
			List<ClassificaDocumento> classificacoes, int classificacaoCodigo) {

		for (ClassificaDocumento classificacao : classificacoes) {
			if (classificacao.getCodigo() == classificacaoCodigo) {
				return classificacao;
			}
		}

		return null;
	}

}
