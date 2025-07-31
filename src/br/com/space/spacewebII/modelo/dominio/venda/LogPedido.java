/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "logpedidos")
@XmlRootElement
public class LogPedido implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;
	
	public static final String USUARIO_ATUALIZACAO_CARTAO = "GEMINI - AUTO CARD"; 
	
	private static final String COLUNA_SERIEPEDIDO = "lgp_spvcodigo";
	private static final String COLUNA_NUMEROPEDIDO = "lgp_pednumero";
	private static final String COLUNA_FILIALPEDIDO = "lgp_filcodigo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "lgp_codigo")
	private Integer codigo;

	@Basic(optional = false)
	@Column(name = COLUNA_SERIEPEDIDO)
	private String seriePedidoCodigo;

	@Basic(optional = false)
	@Column(name = COLUNA_NUMEROPEDIDO)
	private int pedidoNumero;

	@Basic(optional = false)
	@Column(name = "lgp_data")
	@Temporal(TemporalType.DATE)
	private Date data;

	@Basic(optional = false)
	@Column(name = "lgp_usrlogin")
	private String usuarioLogin;

	@Basic(optional = false)
	@Column(name = "lgp_obs")
	private String observacao;

	@Basic(optional = false)
	@Column(name = COLUNA_FILIALPEDIDO)
	private int filialCodigo;

	@Basic(optional = false)
	@Column(name = "lgp_sescodigo")
	private int sessaoCodigo;

	@Column(name = "lgp_strcodigo")
	private Integer stringCodigo;

	@Basic(optional = false)
	@Column(name = "lgp_hora")
	private String hora;

	@Column(name = "lgp_obscanc")
	private String observacaoCancelamento;

	@Column(name = "lgp_envpalm")
	private Integer flagEnviouPalm;

	@Column(name = "lgp_codigoaux")
	private Integer codigoAuxiliar;

	@Column(name = "lgp_stpcodigo")
	private int statusPedidoCodigo;

	public LogPedido() {
	}

	public LogPedido(String seriePedidoCodigo, int pedidoNumero, Date data,
			String usuarioLogin, String observacao, int filialCodigo,
			int sessaoCodigo, String hora, int statusPedidoCodigo) {
		super();
		this.seriePedidoCodigo = seriePedidoCodigo;
		this.pedidoNumero = pedidoNumero;
		this.data = data;
		this.usuarioLogin = usuarioLogin;
		setObservacao(observacao);
		this.filialCodigo = filialCodigo;
		this.sessaoCodigo = sessaoCodigo;
		this.hora = hora;
		this.statusPedidoCodigo = statusPedidoCodigo;
	}

	public String getSeriePedidoCodigo() {
		return seriePedidoCodigo;
	}

	public void setSeriePedidoCodigo(String seriePedidoCodigo) {
		this.seriePedidoCodigo = seriePedidoCodigo;
	}

	public int getPedidoNumero() {
		return pedidoNumero;
	}

	public void setPedidoNumero(int pedidoNumero) {
		this.pedidoNumero = pedidoNumero;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
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
		if (StringUtil.isValida(observacao) && observacao.length() > 100) {
			observacao = observacao.substring(0, 100);
		}
		this.observacao = observacao;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getSessaoCodigo() {
		return sessaoCodigo;
	}

	public void setSessaoCodigo(int sessaoCodigo) {
		this.sessaoCodigo = sessaoCodigo;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Integer getStringCodigo() {
		return stringCodigo;
	}

	public void setStringCodigo(Integer stringCodigo) {
		this.stringCodigo = stringCodigo;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getObservacaoCancelamento() {
		return observacaoCancelamento;
	}

	public void setObservacaoCancelamento(String observacaoCancelamento) {
		this.observacaoCancelamento = observacaoCancelamento;
	}

	public Integer getFlagEnviouPalm() {
		return flagEnviouPalm;
	}

	public void setFlagEnviouPalm(Integer flagEnviouPalm) {
		this.flagEnviouPalm = flagEnviouPalm;
	}

	public Integer getCodigoAuxiliar() {
		return codigoAuxiliar;
	}

	public void setCodigoAuxiliar(Integer codigoAuxiliar) {
		this.codigoAuxiliar = codigoAuxiliar;
	}

	public int getStatusPedidoCodigo() {
		return statusPedidoCodigo;
	}

	public void setStatusPedidoCodigo(int statusPedidoCodigo) {
		this.statusPedidoCodigo = statusPedidoCodigo;
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

	public static List<LogPedido> recuperarLogPedido(GenericoDAO dao,
			int pedidoCodigo, String serie, int filialCodigo) {

		if (pedidoCodigo <= 0 || filialCodigo <= 0 || serie == null) {
			return null;
		}

		String where = COLUNA_SERIEPEDIDO + " = '" + serie + "' AND "
				+ COLUNA_NUMEROPEDIDO + " = " + pedidoCodigo + " AND "
				+ COLUNA_FILIALPEDIDO + " = " + filialCodigo;

		return dao.list(LogPedido.class, where, null, "data, hora", null);
	}
}
