package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class AssuntoTicketPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "ast_codigo")
	private int codigo;
	
	@Column(name = "ast_totcodigo")
	private int topicoTicketCodigo;
	
	public AssuntoTicketPK(){
		
	}
	
	public AssuntoTicketPK(int codigo, int topicoTicketCodigo){
		this.codigo = codigo;
		this.topicoTicketCodigo = topicoTicketCodigo;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getTopicoTicketCodigo() {
		return topicoTicketCodigo;
	}

	public void setTopicoTicketCodigo(int topicoTicketCodigo) {
		this.topicoTicketCodigo = topicoTicketCodigo;
	}

}
