package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "assuntoticket")
public class AssuntoTicket implements Serializable, IPersistent{

	private static final long serialVersionUID = 1L;
	
	@EmbeddedId
	private AssuntoTicketPK assuntoTicketPk;
	
	@Column(name = "ast_descricao")
	private String descricao;
	
	@Column(name = "ast_ativo")
	private int ativo;
	
	public AssuntoTicket(){
		
	}
	
	public AssuntoTicketPK getAssuntoTicketPk() {
		if(assuntoTicketPk == null)
			assuntoTicketPk = new AssuntoTicketPK();
		
		return assuntoTicketPk;
	}

	public int getCodigo() {
		return getAssuntoTicketPk().getCodigo();
	}

	public void setCodigo(int codigo) {
		getAssuntoTicketPk().setCodigo(codigo);
	}

	public int getTopicoTicketCodigo() {
		return getAssuntoTicketPk().getTopicoTicketCodigo();
	}

	public void setTopicoTicketCodigo(int topicoTicketCodigo) {
		getAssuntoTicketPk().setTopicoTicketCodigo(topicoTicketCodigo);
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}
	
	@Override
	public AssuntoTicket clone() throws CloneNotSupportedException {
		return (AssuntoTicket) super.clone();
	}
	
	public static List<AssuntoTicket> recuperarAssuntosAtivos(GenericoDAO dao){
		return dao.list(AssuntoTicket.class, "ast_ativo = 1", null, "descricao", null);
	}
	
	public static List<AssuntoTicket> recuperarAssuntosAtivos(GenericoDAO dao, int topicoCodigo){
		return dao.list(AssuntoTicket.class, "ast_ativo = 1 and ast_totcodigo = " + topicoCodigo, null, "descricao", null);
	}
}
