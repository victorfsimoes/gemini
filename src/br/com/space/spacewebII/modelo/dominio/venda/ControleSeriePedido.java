/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "contserieped")
@XmlRootElement
public class ControleSeriePedido implements Serializable, IPersistent {

	public static final String COLUNA_CODIGO = "csp_codigo";

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "csp_codigo")
	private int codigo;

	@Basic(optional = false)
	@Column(name = "csp_desc")
	private String descricao;

	@Basic(optional = false)
	@Column(name = "csp_frequencia")
	private String frequencia;

	@Column(name = "csp_numfinal")
	private Integer numeracaoFinal;

	@Basic(optional = false)
	@Column(name = "csp_incremento")
	private int incremento;

	@Basic(optional = false)
	@Column(name = "csp_filcodigo")
	private int filialCodigo;

	@Column(name = "csp_ativo")
	private Integer ativo;

	public ControleSeriePedido() {
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

	public String getFrequencia() {
		return frequencia;
	}

	public void setFrequencia(String frequencia) {
		this.frequencia = frequencia;
	}

	public Integer getNumeracaoFinal() {
		return numeracaoFinal;
	}

	public void setNumeracaoFinal(Integer numeracaoFinal) {
		this.numeracaoFinal = numeracaoFinal;
	}

	public int getIncremento() {
		return incremento;
	}

	public void setIncremento(int incremento) {
		this.incremento = incremento;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Integer getAtivo() {
		return ativo;
	}

	public void setAtivo(Integer ativo) {
		this.ativo = ativo;
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

	/*
	 * 
	 */
	public static ControleSeriePedido recuperarCodigo(GenericoDAO dao,
			int controleSerieNumero) {
		return dao
				.uniqueResult(ControleSeriePedido.class,
						ControleSeriePedido.COLUNA_CODIGO + " = "
								+ controleSerieNumero, null);
	}

}
