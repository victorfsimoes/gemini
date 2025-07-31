/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "statuspedidos")
@XmlRootElement
public class StatusPedido implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final int STATUS_EM_INCLUSAO = 1;
	public static final int STATUS_NAO_SEPARADO = 2;
	public static final int STATUS_ENVIADO_OUTRO_SETOR = 3;
	public static final int STATUS_EM_ALTERACAO = 4;
	public static final int STATUS_BLOQUEADO = 5;
	public static final int STATUS_SEPARADO = 6;
	public static final int STATUS_CANCELADO_INCLUSAO = 7;
	public static final int STATUS_SEPARADO_PARCIAL = 8;
	public static final int STATUS_AGUARDANDO_PAGAMENTO_WEB = 9;
	public static final int STATUS_CANCELADO = 13;
	private static final String COLUNA_ATIVO = "stp_ativo";
	public static final String COLUNA_CODIGO = "stp_codigo";
	public static final String COLUNA_BLOQUEIO = "stp_bloqpedido";

	@Id
	@Column(name = COLUNA_CODIGO)
	private Integer codigo;

	@Basic(optional = false)
	@Column(name = "stp_desc")
	private String descricao;

	@Basic(optional = false)
	@Column(name = "stp_grupo")
	private int grupo;

	@Column(name = "stp_bloqpedido")
	private Integer flagBloqueado;

	@Column(name = COLUNA_ATIVO)
	private Integer flagAtivo;

	@Column(name = "stp_cancpedido")
	private Integer flagCancelado;

	public StatusPedido() {
	}

	public StatusPedido(Integer stpCodigo) {
		this.codigo = stpCodigo;
	}

	public StatusPedido(Integer stpCodigo, String stpDesc, int stpGrupo) {
		this.codigo = stpCodigo;
		this.descricao = stpDesc;
		this.grupo = stpGrupo;
	}

	public Integer getCodigo() {
		if (codigo == null)
			return 0;
		else
			return codigo.intValue();
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getCodigoString() {

		if (codigo != null) {
			return codigo.toString();
		}

		return null;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getGrupo() {
		return grupo;
	}

	public void setGrupo(int grupo) {
		this.grupo = grupo;
	}

	public Integer getFlagBloqueado() {
		return flagBloqueado;
	}

	public void setFlagBloqueado(Integer flagBloqueado) {
		this.flagBloqueado = flagBloqueado;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getFlagCancelado() {
		return flagCancelado;
	}

	public void setFlagCancelado(Integer flagCancelado) {
		this.flagCancelado = flagCancelado;
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

	public static List<StatusPedido> recuperarTodosStatusAtivos(GenericoDAO dao) {
		return dao.list(StatusPedido.class, COLUNA_ATIVO + " = 1", null, null,
				null);
	}

	public static List<StatusPedido> recuperarTodosStatusPermitidos(
			GenericoDAO dao) {
		return dao.list(StatusPedido.class, COLUNA_BLOQUEIO + " = 0", null,
				null, null);
	}

}
