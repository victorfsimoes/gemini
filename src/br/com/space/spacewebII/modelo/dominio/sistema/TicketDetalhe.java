package br.com.space.spacewebII.modelo.dominio.sistema;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "ticketdetalhe")
public class TicketDetalhe implements IPersistent {

	@Id
	@GeneratedValue
	@Column(name = "tde_numeroseq")
	private int numeroSequencial;

	@Column(name = "tde_ticprot")
	private int protocoloTicket;

	@Column(name = "tde_data")
	@Temporal(TemporalType.DATE)
	private Date data;

	@Column(name = "tde_hora")
	private String hora;

	@Column(name = "tde_mensagem")
	private String mensagem;

	@Column(name = "tde_dataLeitura")
	@Temporal(TemporalType.DATE)
	private Date dataLeitura;

	@Column(name = "tde_horaLeitura")
	private String horaLeitura;

	@Column(name = "tde_notificado")
	private int notificado;

	@Column(name = "tde_respondido")
	private int respondido;

	@Column(name = "tde_usrlogin")
	private String usuarioLogin;

	public TicketDetalhe() {
	}

	public int getNumeroSequencial() {
		return numeroSequencial;
	}

	public void setNumeroSequencial(int numeroSequencial) {
		this.numeroSequencial = numeroSequencial;
	}

	public int getProtocoloTicket() {
		return protocoloTicket;
	}

	public void setProtocoloTicket(int protocoloTicket) {
		this.protocoloTicket = protocoloTicket;
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

	public String getMensagem() {
		return mensagem;
	}

	public void setMensagem(String mensagem) {
		this.mensagem = mensagem;
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

	public int getNotificado() {
		return notificado;
	}

	public void setNotificado(int notificado) {
		this.notificado = notificado;
	}

	public int getRespondido() {
		return respondido;
	}

	public void setRespondido(int respondido) {
		this.respondido = respondido;
	}

	public String getUsuarioLogin() {
		return usuarioLogin;
	}

	public void setUsuarioLogin(String usuarioLogin) {
		this.usuarioLogin = usuarioLogin;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {

	}

	@Override
	public TicketDetalhe clone() throws CloneNotSupportedException {
		return (TicketDetalhe) super.clone();
	}

	public static List<TicketDetalhe> recuperarRespostasTicket(GenericoDAO dao,
			int protocolo) {
		return dao.list(TicketDetalhe.class, "tde_ticprot = " + protocolo, null, "numeroSequencial", null);
	}

}
