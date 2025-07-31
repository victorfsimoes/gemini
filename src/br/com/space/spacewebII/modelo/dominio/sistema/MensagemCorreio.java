/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.integracao.servidorviking.viking.modelo.IMensagem;
import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Renato
 */
@Entity
@Table(name = "msgcorreio")
public class MensagemCorreio implements Serializable, IPersistent, IMensagem {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "msc_seq")
	private int sequencial;

	@Column(name = "msc_usrloginenv")
	private String usuarioEnvioLogin;

	@Column(name = "msc_usrlogindes")
	private String usuarioDestinoLogin;

	@Column(name = "msc_dataenv")
	@Temporal(TemporalType.DATE)
	private Date dataEnvio;

	@Column(name = "msc_horaenv")
	private String horaEnvio;

	@Column(name = "msc_datalei")
	@Temporal(TemporalType.DATE)
	private Date dataLeitura;

	@Column(name = "msc_horalei")
	private String horaLeitura;

	@Column(name = "msc_assunto")
	private String assunto;

	@Column(name = "msc_mensagem")
	private String mensagem;

	@Column(name = "msc_automatica")
	private int flagMensagemAutomatica;

	@Column(name = "msc_status")
	private String statusMensagem;

	@Column(name = "msc_codaut")
	private int autorizacaoCodigo;

	@Column(name = "msc_tipoaut")
	private String tipoAutorizacao;

	@Column(name = "msc_enviouvik")
	private int flagEnviouViking;

	public MensagemCorreio() {
		this.dataEnvio = new Date();
		this.horaEnvio = Conversao.formatarDataHHMMSS(dataEnvio);
	}

	public MensagemCorreio(String usuarioEnvioLogin,
			String usuarioDestinoLogin, String assunto, String mensagem) {
		this();
		this.usuarioEnvioLogin = usuarioEnvioLogin;
		this.usuarioDestinoLogin = usuarioDestinoLogin;
		this.assunto = assunto;
		this.mensagem = mensagem;
	}

	public int getSequencial() {
		return sequencial;
	}

	public void setSequencial(int sequencial) {
		this.sequencial = sequencial;
	}

	public String getUsuarioEnvioLogin() {
		return usuarioEnvioLogin;
	}

	public void setUsuarioEnvioLogin(String usuarioEnvioLogin) {
		this.usuarioEnvioLogin = usuarioEnvioLogin;
	}

	public String getUsuarioDestinoLogin() {
		return usuarioDestinoLogin;
	}

	public void setUsuarioDestinoLogin(String usuarioDestinoLogin) {
		this.usuarioDestinoLogin = usuarioDestinoLogin;
	}

	public Date getDataEnvio() {
		return dataEnvio;
	}

	public void setDataEnvio(Date dataEnvio) {
		this.dataEnvio = dataEnvio;
	}

	public String getHoraEnvio() {
		return horaEnvio;
	}

	public void setHoraEnvio(String horaEnvio) {
		this.horaEnvio = horaEnvio;
	}

	public Date getDataLeitura() {
		return dataLeitura;
	}

	public void setDataLeitura(Date dataLeitura) {
		this.dataLeitura = dataLeitura;
	}

	public String getHoraLeitura() {
		return horaLeitura;
	}

	public void setHoraLeitura(String horaLeitura) {
		this.horaLeitura = horaLeitura;
	}

	public String getAssunto() {
		return assunto;
	}

	public void setAssunto(String assunto) {
		this.assunto = assunto;
	}

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
	}

	public int getFlagMensagemAutomatica() {
		return flagMensagemAutomatica;
	}

	public void setFlagMensagemAutomatica(int flagMensagemAutomatica) {
		this.flagMensagemAutomatica = flagMensagemAutomatica;
	}

	public String getStatusMensagem() {
		return statusMensagem;
	}

	public void setStatusMensagem(String statusMensagem) {
		this.statusMensagem = statusMensagem;
	}

	public int getAutorizacaoCodigo() {
		return autorizacaoCodigo;
	}

	public void setAutorizacaoCodigo(int autorizacaoCodigo) {
		this.autorizacaoCodigo = autorizacaoCodigo;
	}

	public String getTipoAutorizacao() {
		return tipoAutorizacao;
	}

	public void setTipoAutorizacao(String tipoAutorizacao) {
		this.tipoAutorizacao = tipoAutorizacao;
	}

	public int getFlagEnviouViking() {
		return flagEnviouViking;
	}

	public void setFlagEnviouViking(int flagEnviouViking) {
		this.flagEnviouViking = flagEnviouViking;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static List<MensagemCorreio> gerarMensagensEntradaSaida(
			MensagemCorreio mensagem) throws CloneNotSupportedException {

		if (mensagem == null || !StringUtil.isValida(mensagem.getOrigem())
				|| !StringUtil.isValida(mensagem.getDestino())
				|| !StringUtil.isValida(mensagem.getMensagem())) {
			return null;
		}

		List<MensagemCorreio> mensagems = new ArrayList<MensagemCorreio>();

		String[] strings = mensagem.getDestino().split(SEPARADOR_CONTATO);

		for (String destino : strings) {

			MensagemCorreio msgEntrada = (MensagemCorreio) mensagem.clone();
			MensagemCorreio msgSaida = (MensagemCorreio) mensagem.clone();

			setPropriedadesComunsEntraSaida(msgEntrada, destino);
			msgEntrada.setStatusMensagem(STATUS_ENTRADA);

			setPropriedadesComunsEntraSaida(msgSaida, destino);
			msgSaida.setStatusMensagem(STATUS_ENVIADO);

			mensagems.add(msgEntrada);
			mensagems.add(msgSaida);
		}

		return mensagems;
	}

	private static void setPropriedadesComunsEntraSaida(MensagemCorreio msg,
			String destino) {
		msg.setSequencia(0);
		msg.setDestino(destino);
		msg.setFlagMensagemAutomatica(0);
	}

	@Override
	public int getSequencia() {
		return getSequencial();
	}

	@Override
	public void setSequencia(int sequencia) {
		setSequencial(sequencia);

	}

	@Override
	public String getOrigem() {
		return getUsuarioEnvioLogin();
	}

	@Override
	public void setOrigem(String origem) {
		setUsuarioEnvioLogin(origem);
	}

	@Override
	public String getDestino() {
		return getUsuarioDestinoLogin();
	}

	@Override
	public void setDestino(String destino) {
		setUsuarioDestinoLogin(destino);
	}
}
