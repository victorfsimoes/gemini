package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "topicoticket")
public class TopicoTicket implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name = "tot_codigo")
	private int codigo;

	@Column(name = "tot_descricao")
	private String descricao;
	
	@Column(name = "tot_ativo")
	private int ativo;
	
	public TopicoTicket(){
		
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
	public TopicoTicket clone() throws CloneNotSupportedException {
		return (TopicoTicket) super.clone();
	}
	
	public static List<TopicoTicket> recuperarTopicosAtivos(GenericoDAO dao){
		return dao.list(TopicoTicket.class, "tot_ativo = 1", null, null, null);
	}
}
