package br.com.space.spacewebII.controle.aplicacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

import org.jboss.seam.security.annotations.LoggedIn;
import org.primefaces.event.TabChangeEvent;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.AssuntoTicket;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.sistema.Ticket;
import br.com.space.spacewebII.modelo.dominio.sistema.TicketDetalhe;
import br.com.space.spacewebII.modelo.dominio.sistema.TopicoTicket;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@LoggedIn
@ManagedBeanSessionScoped
@URLMapping(id = "ticketCliente", pattern = "/atendimento/", viewId = "/pages/ticket.xhtml", onPostback = false)
@URLBeanName("ticketMB")
public class TicketMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GenericoDAO dao;

	@Inject
	private GerenteLogin gerenteLogin;

	@Inject
	private Parametros parametros;

	private Ticket ticket;
	private TicketDetalhe ticketDetalhe;

	private List<Ticket> tickets;
	private List<TicketDetalhe> respostasTicket;

	private List<TopicoTicket> topicosTicket = null;
	private List<AssuntoTicket> assuntosTicket = null;

	private final String STATUS_AGUARDANDO_USUARIO = "Aguardando administrador";
	private final String STATUS_AGUARDANDO_CLIENTE = "Aguardando cliente";
	private final String STATUS_ENCERRADO = "Encerrado";
	private final String SIGLA_STATUS_AGUARDANDO_USUARIO = "U";
	private final String SIGLA_STATUS_AGUARDANDO_CLIENTE = "C";
	private final String SIGLA_STATUS_ENCERRADO = "E";

	@URLAction
	public void abrirNovoTicketURL() {
		abrirNovoTicket(null);
		recuperarTickets();
	}

	public void abrirNovoTicket(Pedido pedido) {
		ticket = new Ticket();
		ticket.setStatus("U");
		ticket.setFilialCodigo(gerenteLogin.getFilialCodigo());
		ticket.setClienteCodigo(gerenteLogin.getClienteCodigo());

		if (pedido != null) {
			ticket.setNumeroPedido(pedido.getNumero());
			ticket.setSeriePedido(pedido.getSerieCodigo());
		}

		abrirNovaResposta(ticket);

		// ticket.setAssuntoCodigo(assuntoSelecionado.getCodigo());
		// ticket.setTopicoCodigo(topicoSelecionado.getCodigo());
	}

	public void abrirNovaResposta(Ticket ticket) {
		ticketDetalhe = new TicketDetalhe();
		this.ticket = ticket;
	}

	public void recuperarTickets() {

		tickets = Ticket.recuperarTickets(dao, gerenteLogin.getClienteCodigo(),
				gerenteLogin.getFilialCodigo(), null, null);
		Collections.reverse(tickets);

		recuperarRespostasTicket(tickets.get(0));
	}

	public void recuperarRespostasTicket(Ticket ticket) {
		if (ticket != null) {
			respostasTicket = TicketDetalhe.recuperarRespostasTicket(dao,
					ticket.getProtocolo());
		}
	}

	public void historicoTicketsTabChange(TabChangeEvent event) {

		Object object = event.getData();

		if (object instanceof Ticket) {
			recuperarRespostasTicket(((Ticket) object));
		}
	}

	public void enviarTicket(Ticket ticket, boolean novoTicket) {
		Date dataHoje = new Date();

		if (novoTicket) {
			ticket.setDataCadastro(dataHoje);
			ticket.setHoraCadastro(Conversao.formatarDataHHMMSS(dataHoje));
		} else {
			abrirNovaResposta(ticket);
		}

		ticketDetalhe.setData(dataHoje);
		ticketDetalhe.setNotificado(0);
		ticketDetalhe.setRespondido(0);
		ticketDetalhe.setUsuarioLogin(null); // Setar o suario quando for a
												// resposa do colaborador.
		ticketDetalhe.setHora(Conversao.formatarDataHHMMSS(dataHoje));

		try {
			dao.beginTransaction();

			if (novoTicket)
				dao.insertObject(ticket);

			ticketDetalhe.setProtocoloTicket(ticket.getProtocolo());
			dao.insert(ticketDetalhe);

			dao.commitTransaction();

			tickets.add(ticket);

			enviarEmailTicket();

			abrirNovoTicket(null);

		} catch (Exception e) {
			dao.rollBackTransaction();
			e.printStackTrace();
		}
	}

	public void enviarEmailTicket() {
		GerenteEmail.enviarEmailGenerico(
				gerenteLogin.getUserLogin(),
				dao,
				parametros,
				"Novo Ticket ",
				"Ticket "
						+ ticket.getProtocolo()
						+ " - "
						+ Conversao.formatarDataDDMMAAAA(ticketDetalhe
								.getData()) + " " + ticketDetalhe.getHora(),
				ticketDetalhe.getMensagem(), "/atendimento/", "Ticket");
	}

	public String nomearStatus(String status) {
		if (status.equals(SIGLA_STATUS_AGUARDANDO_USUARIO))
			return STATUS_AGUARDANDO_USUARIO;
		else if (status.equals(SIGLA_STATUS_AGUARDANDO_CLIENTE))
			return STATUS_AGUARDANDO_CLIENTE;
		else if (status.equals(SIGLA_STATUS_ENCERRADO))
			return STATUS_ENCERRADO;
		else
			return null;
	}

	public boolean permiteResponderTicket(Ticket ticket) {

		if (ticket == null || respostasTicket == null)
			return false;

		if (respostasTicket.get(0).getProtocoloTicket() != ticket
				.getProtocolo())
			recuperarRespostasTicket(ticket);

		if (respostasTicket.get(respostasTicket.size() - 1).getUsuarioLogin() == null)
			return false;

		return true;
	}

	public String nomearTopico(Integer topicoCodigo) {

		for (TopicoTicket topico : getTopicosTicket()) {
			if (topico.getCodigo() == topicoCodigo)
				return topico.getDescricao();
		}

		return null;
	}

	public String nomearAssunto(Integer topicoCodigo, Integer assuntoCodigo) {

		for (AssuntoTicket assunto : getAssuntosTicket()) {
			if (assunto.getCodigo() == assuntoCodigo
					&& assunto.getTopicoTicketCodigo() == topicoCodigo)
				return assunto.getDescricao();
		}

		return null;
	}

	public void mudarTopicoListening() {
		assuntosTicket = AssuntoTicket.recuperarAssuntosAtivos(dao,
				ticket.getTopicoCodigo());
	}

	public Ticket getTicket() {
		return ticket;
	}

	public void setTicket(Ticket ticket) {
		this.ticket = ticket;
	}

	public List<TopicoTicket> getTopicosTicket() {
		if (topicosTicket == null)
			topicosTicket = TopicoTicket.recuperarTopicosAtivos(dao);
		return topicosTicket;
	}

	public List<AssuntoTicket> getAssuntosTicket() {
		if (assuntosTicket == null)
			assuntosTicket = AssuntoTicket.recuperarAssuntosAtivos(dao);
		return assuntosTicket;
	}

	public List<AssuntoTicket> getAssuntosPorTopico(Integer topicoCodigo) {

		List<AssuntoTicket> assuntosFiltrados = new ArrayList<>();

		for (AssuntoTicket assunto : getAssuntosTicket()) {
			if (assunto.getTopicoTicketCodigo() == topicoCodigo)
				assuntosFiltrados.add(assunto);
		}

		return assuntosFiltrados;
	}

	public TicketDetalhe getTicketDetalhe() {
		return ticketDetalhe;
	}

	public void setTicketDetalhe(TicketDetalhe ticketDetalhe) {
		this.ticketDetalhe = ticketDetalhe;
	}

	public List<Ticket> getTickets() {
		return tickets;
	}

	public List<TicketDetalhe> getRespostasTicket() {
		return respostasTicket;
	}

	public void setTopicosTicket(List<TopicoTicket> topicosTicket) {
		this.topicosTicket = topicosTicket;
	}

	public void setAssuntosTicket(List<AssuntoTicket> assuntosTicket) {
		this.assuntosTicket = assuntosTicket;
	}

	public String getUrlView() {
		return "/atendimento/";
	}

}
