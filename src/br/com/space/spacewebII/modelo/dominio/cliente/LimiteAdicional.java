/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.cliente;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Date;

import javax.persistence.Basic;
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
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.controle.Sistema;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.pessoa.Pessoa;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "limitead")
@XmlRootElement
public class LimiteAdicional implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected LimiteAdicionalPK limiteAdicionalPK;

	@Column(name = "lad_data")
	@Temporal(TemporalType.DATE)
	private Date data;

	@Basic(optional = false)
	@Column(name = "lad_hora")
	private String hora;

	@Basic(optional = false)
	@Column(name = "lad_sescodigo")
	private int sessaoCodigo;

	@Basic(optional = false)
	@Column(name = "lad_usrlogin")
	private String usuarioLogin;

	@Basic(optional = false)
	@Column(name = "lad_obs")
	private String observacao;

	@Basic(optional = false)
	@Column(name = "lad_valor")
	private double valor;

	@JoinColumn(name = "lad_pescodigo", referencedColumnName = "pes_codigo", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Pessoa pessoa;

	@JoinColumn(name = "lad_filcodigo", referencedColumnName = "fil_codigo", insertable = false, updatable = false)
	@ManyToOne(optional = false)
	private Filial filial;

	public LimiteAdicional() {
	}

	public LimiteAdicional(LimiteAdicionalPK limiteAdicionalPK) {
		this.limiteAdicionalPK = limiteAdicionalPK;
	}

	public LimiteAdicionalPK getLimiteAdicionalPK() {
		return limiteAdicionalPK;
	}

	public void setLimiteAdicionalPK(LimiteAdicionalPK limiteAdicionalPK) {
		this.limiteAdicionalPK = limiteAdicionalPK;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public int getSessaoCodigo() {
		return sessaoCodigo;
	}

	public void setSessaoCodigo(int sessaoCodigo) {
		this.sessaoCodigo = sessaoCodigo;
	}

	public String getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(String usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
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

	public static double recuperarLimiteAdicional(GenericoDAO dao,
			int codigoCliente) {

		ResultSet limite = null;
		double limitead = 0;

		String sql = "select sum(lad_valor) as limitead from limitead where lad_pescodigo = "
				+ codigoCliente
				+ " and lad_data = '"
				+ Conversao.formatarDataAAAAMMDD(Sistema.obterData()) + "'";

		try {

			limite = dao.listResultSet(sql, null, null);

			if (limite.first()) {
				limitead = Conversao.nvlDouble(limite.getDouble("limitead"),
						0.0);
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(limite);
		}

		return limitead;
	}

}
