package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.ForeignKey;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "ticket")
public class Ticket implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue
	@Column(name = "tic_protocolo")
	private int protocolo;
	
	@Column(name = "tic_filcodigo")
	@ForeignKey(name = "fil_codigo")
	private int filialCodigo;
	
	@Column(name = "tic_pednumero")
	private Integer numeroPedido;
	
	@Column(name = "tic_spvcodigo")
	private String seriePedido;
	
	@Column(name = "tic_pescodigo")
	private Integer clienteCodigo;
	
	@Column(name = "tic_totcodigo")
	private Integer topicoCodigo;
	
	@Column(name = "tic_astcodigo")
	private Integer assuntoCodigo;
	
	@Column(name = "tic_status")
	private String status;
	
	@Column(name = "tic_dtcadastro")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;
	
	@Column(name = "tic_hrcadastro")
	private String horaCadastro;
	
	
	public Ticket() {
	
	}
	
	public int getProtocolo() {
		return protocolo;
	}

	public void setProtocolo(int protocolo) {
		this.protocolo = protocolo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Integer getNumeroPedido() {
		return numeroPedido;
	}

	public void setNumeroPedido(Integer numeroPedido) {
		this.numeroPedido = numeroPedido;
	}

	public String getSeriePedido() {
		return seriePedido;
	}

	public void setSeriePedido(String seriePedido) {
		this.seriePedido = seriePedido;
	}

	public Integer getTopicoCodigo() {
		return topicoCodigo;
	}

	public void setTopicoCodigo(Integer topicoCodigo) {
		this.topicoCodigo = topicoCodigo;
	}

	public Integer getAssuntoCodigo() {
		return assuntoCodigo;
	}

	public void setAssuntoCodigo(Integer assuntoCodigo) {
		this.assuntoCodigo = assuntoCodigo;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getHoraCadastro() {
		return horaCadastro;
	}

	public void setHoraCadastro(String horaCadastro) {
		this.horaCadastro = horaCadastro;
	}

	public Integer getClienteCodigo() {
		return clienteCodigo;
	}

	public void setClienteCodigo(Integer clienteCodigo) {
		this.clienteCodigo = clienteCodigo;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}
	
	public static List<Ticket> recuperarTickets(GenericoDAO dao, int pessoaCodigo, int filialCodigo, Date dataInicio, Date dataFim){
		String where = "tic_pescodigo = " + pessoaCodigo + " and tic_filcodigo = " + filialCodigo;
		if(dataInicio != null){
			where += " and tic_datacadastro between " + dataInicio + " and " + dataFim;
		}
		
		return dao.list(Ticket.class, where, null, "dataCadastro, horaCadastro", null);
	}
	

}
