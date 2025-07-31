/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IAutorizacao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "autorizacao")
@XmlRootElement
public class Autorizacao implements Serializable, IAutorizacao {

	private static final long serialVersionUID = 1L;

	private static final String COLUNA_CHAVE = "aut_chave";
	private static final String COLUNA_PERMISSAO = "aut_percodigo";
	private static final String COLUNA_PROGRAMA = "aut_prgcodigo";
	private static final String COLUNA_SESSAO = "aut_sescodsolic";
	private static final String COLUNA_STATUS = "aut_status";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "aut_numero")
	private int numero;

	@Column(name = "aut_filcodigo")
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "aut_sescodsolic")
	private int sessaoCodigoSolicitacao;

	@Column(name = "aut_sescodliber")
	private int sessaoCodigoLiberacao;

	@Column(name = "aut_usrliberou")
	private String usuarioLiberou;

	@Basic(optional = false)
	@Column(name = "aut_datasolic")
	@Temporal(TemporalType.DATE)
	private Date dataSolicitacao;

	@Basic(optional = false)
	@Column(name = "aut_horasolic")
	private String horaSolicitacao;

	@Column(name = "aut_datalibera")
	@Temporal(TemporalType.DATE)
	private Date dataLiberacao;

	@Column(name = "aut_horalibera")
	private String horaLiberacao;

	@Column(name = "aut_datacancela")
	@Temporal(TemporalType.DATE)
	private Date dataCancelamento;

	@Column(name = "aut_horacancela")
	private String horaCancelamento;

	@Column(name = "aut_status")
	private String status;

	@Column(name = "aut_tipolibera")
	private String tipoLiberacao;

	@Basic(optional = false)
	@Column(name = "aut_prgcodigo")
	private String programaCodigo;

	@Basic(optional = false)
	@Column(name = "aut_valor")
	private double valor;

	@Basic(optional = false)
	@Column(name = "aut_chave")
	private String chave;

	@Basic(optional = false)
	@Lob
	@Column(name = "aut_observacao")
	private String observacao;

	@Column(name = "aut_datautiliza")
	@Temporal(TemporalType.DATE)
	private Date dataUtilizacao;

	@Column(name = "aut_horautiliza")
	private String horaUtilizacao;

	@Column(name = "aut_percodigo")
	private int permissaoCodigo;

	public Autorizacao() {
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getSessaoCodigoSolicitacao() {
		return sessaoCodigoSolicitacao;
	}

	public void setSessaoCodigoSolicitacao(int sessaoCodigoSolicitacao) {
		this.sessaoCodigoSolicitacao = sessaoCodigoSolicitacao;
	}

	public int getSessaoCodigoLiberacao() {
		return sessaoCodigoLiberacao;
	}

	public void setSessaoCodigoLiberacao(int sessaoCodigoLiberacao) {
		this.sessaoCodigoLiberacao = sessaoCodigoLiberacao;
	}

	public String getUsuarioLiberou() {
		return usuarioLiberou;
	}

	public void setUsuarioLiberou(String usuarioLiberou) {
		this.usuarioLiberou = usuarioLiberou;
	}

	public Date getDataSolicitacao() {
		return dataSolicitacao;
	}

	public void setDataSolicitacao(Date dataSolicitacao) {
		this.dataSolicitacao = dataSolicitacao;
	}

	public String getHoraSolicitacao() {
		return horaSolicitacao;
	}

	public void setHoraSolicitacao(String horaSolicitacao) {
		this.horaSolicitacao = horaSolicitacao;
	}

	public Date getDataLiberacao() {
		return dataLiberacao;
	}

	public void setDataLiberacao(Date dataLiberacao) {
		this.dataLiberacao = dataLiberacao;
	}

	public String getHoraLiberacao() {
		return horaLiberacao;
	}

	public void setHoraLiberacao(String horaLiberacao) {
		this.horaLiberacao = horaLiberacao;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getHoraCancelamento() {
		return horaCancelamento;
	}

	public void setHoraCancelamento(String horaCancelamento) {
		this.horaCancelamento = horaCancelamento;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getTipoLiberacao() {
		return tipoLiberacao;
	}

	public void setTipoLiberacao(String tipoLiberacao) {
		this.tipoLiberacao = tipoLiberacao;
	}

	public String getProgramaCodigo() {
		return programaCodigo;
	}

	public void setProgramaCodigo(String programaCodigo) {
		this.programaCodigo = programaCodigo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public String getChave() {
		return chave;
	}

	public void setChave(String chave) {
		this.chave = chave;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public Date getDataUtilizacao() {
		return dataUtilizacao;
	}

	public void setDataUtilizacao(Date dataUtilizacao) {
		this.dataUtilizacao = dataUtilizacao;
	}

	public String getHoraUtilizacao() {
		return horaUtilizacao;
	}

	public void setHoraUtilizacao(String horaUtilizacao) {
		this.horaUtilizacao = horaUtilizacao;
	}

	public int getPermissaoCodigo() {
		return permissaoCodigo;
	}

	public void setPermissaoCodigo(int permissaoCodigo) {
		this.permissaoCodigo = permissaoCodigo;
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

	public static Autorizacao recuperarAutorizacao(GenericoDAO dao,
			String chave, String programa, int permissao, int sessao) {

		return dao.uniqueResult(Autorizacao.class, COLUNA_CHAVE + " = '"
				+ chave + "' and " + COLUNA_PROGRAMA + " = '" + programa
				+ "' and " + COLUNA_PERMISSAO + " = " + permissao + " and "
				+ COLUNA_SESSAO + " = " + sessao + " and " + COLUNA_STATUS
				+ " <> 'U'", null);
	}

}
