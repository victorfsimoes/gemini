package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "log_cep")
public class LogCep implements IPersistent, Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected LogCepPK logCepPK;

	@Column(name = "CEP")
	private String cep;

	public LogCep() {
	}

	public LogCep(LogCepPK logCepPK) {
		this.logCepPK = logCepPK;
	}

	public LogCep(LogCepPK logCepPK, String cep) {
		this.logCepPK = logCepPK;
		this.cep = cep;
	}

	public LogCep(int logNuCep, int logInTabela) {
		this.logCepPK = new LogCepPK(logNuCep, logInTabela);
	}

	public LogCepPK getLogCepPK() {
		return logCepPK;
	}

	public void setLogCepPK(LogCepPK logCepPK) {
		this.logCepPK = logCepPK;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (logCepPK != null ? logCepPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LogCep)) {
			return false;
		}
		LogCep other = (LogCep) object;
		if ((this.logCepPK == null && other.logCepPK != null)
				|| (this.logCepPK != null && !this.logCepPK
						.equals(other.logCepPK))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.LogCep[ logCepPK=" + logCepPK + " ]";
	}

	@Override
	public LogCep clone() throws CloneNotSupportedException {
		return (LogCep) super.clone();
	}

	public static List<LogCep> recuperar(GenericoDAO<IPersistent> dao,
			String filtroCep) {
		String where;
		where = "CEP = '" + filtroCep + "'";
		return dao.list(LogCep.class, where, null, null, null);
	}
	
	public static LogCep recuperarUnico(GenericoDAO<IPersistent> dao, String filtroCep){
		String where = "CEP = " + filtroCep;
		return dao.uniqueResult(LogCep.class, where, null);
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		
	}

}
