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
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "permissoes")
@XmlRootElement
public class Permissao implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	public static final int ACESSO = 0;
	public static final int INCLUIR = 4;
	public static final int ALTERAR = 5;
	public static final int EXCLUIR = 6;
	public static final int IMPRIMIR = 7;
	public static final int CONSULTTAR = 8;
	public static final int LANCA_NATUREZA_VENDA = 139;
	public static final int ALTERA_VENDEDOR_SUGERIDO = 50;
	public static final int ALTERA_TIPO_ENTREGA = 173;
	public static final int LANCA_NUMERO_ENTREGA = 172;

	private static final String COLUNA_CODIGO = "per_codigo";

	@Id
	@Basic(optional = false)
	@Column(name = "per_codigo")
	private Integer codigo;

	@Basic(optional = false)
	@Column(name = "per_opcao")
	private String opcao;

	@Column(name = "per_dtbtabela")
	private String tabelaDicionario;

	@Column(name = "per_dcpcampo")
	private String campoDicionario;

	@Column(name = "per_filcodigo")
	private Integer filialCodigo;

	@Column(name = "per_idregraneg")
	private String idRegraNegocio;

	@Column(name = "per_naplicausr")
	private Integer flagNaoAplicaUsuario;

	public Permissao() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}

	public String getTabelaDicionario() {
		return tabelaDicionario;
	}

	public void setTabelaDicionario(String tabelaDicionario) {
		this.tabelaDicionario = tabelaDicionario;
	}

	public String getCampoDicionario() {
		return campoDicionario;
	}

	public void setCampoDicionario(String campoDicionario) {
		this.campoDicionario = campoDicionario;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getIdRegraNegocio() {
		return idRegraNegocio;
	}

	public void setIdRegraNegocio(String idRegraNegocio) {
		this.idRegraNegocio = idRegraNegocio;
	}

	public Integer getFlagNaoAplicaUsuario() {
		return flagNaoAplicaUsuario;
	}

	public void setFlagNaoAplicaUsuario(Integer flagNaoAplicaUsuario) {
		this.flagNaoAplicaUsuario = flagNaoAplicaUsuario;
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

	public static Permissao recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(Permissao.class,
				COLUNA_CODIGO + " = " + codigo, null);
	}

}
