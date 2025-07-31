package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "log_unid_oper")
public class LogUnidOper implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name = "UOP_NU_SEQUENCIAL")
	private Integer uopNuSequencial;
	@Column(name = "UFE_SG")
	private String ufeSg;
	@Column(name = "LOC_NU_SEQUENCIAL")
	private int locNuSequencial;
	@Column(name = "LOG_NU_SEQUENCIAL")
	private Integer logNuSequencial;
	@Column(name = "BAI_NU_SEQUENCIAL")
	private int baiNuSequencial;
	@Column(name = "UOP_NO")
	private String uopNo;
	@Column(name = "CEP")
	private String cep;
	@Column(name = "UOP_ENDERECO")
	private String uopEndereco;
	@Column(name = "UOP_IN_CP")
	private Character uopInCp;

	public LogUnidOper() {
	}

	public LogUnidOper(Integer uopNuSequencial) {
		this.uopNuSequencial = uopNuSequencial;
	}

	public LogUnidOper(Integer uopNuSequencial, String ufeSg,
			int locNuSequencial, int baiNuSequencial, String uopNo, String cep,
			String uopEndereco) {
		this.uopNuSequencial = uopNuSequencial;
		this.ufeSg = ufeSg;
		this.locNuSequencial = locNuSequencial;
		this.baiNuSequencial = baiNuSequencial;
		this.uopNo = uopNo;
		this.cep = cep;
		this.uopEndereco = uopEndereco;
	}

	public Integer getUopNuSequencial() {
		return uopNuSequencial;
	}

	public void setUopNuSequencial(Integer uopNuSequencial) {
		this.uopNuSequencial = uopNuSequencial;
	}

	public String getUfeSg() {
		return ufeSg;
	}

	public void setUfeSg(String ufeSg) {
		this.ufeSg = ufeSg;
	}

	public int getLocNuSequencial() {
		return locNuSequencial;
	}

	public void setLocNuSequencial(int locNuSequencial) {
		this.locNuSequencial = locNuSequencial;
	}

	public Integer getLogNuSequencial() {
		return logNuSequencial;
	}

	public void setLogNuSequencial(Integer logNuSequencial) {
		this.logNuSequencial = logNuSequencial;
	}

	public int getBaiNuSequencial() {
		return baiNuSequencial;
	}

	public void setBaiNuSequencial(int baiNuSequencial) {
		this.baiNuSequencial = baiNuSequencial;
	}

	public String getUopNo() {
		return uopNo;
	}

	public void setUopNo(String uopNo) {
		this.uopNo = uopNo;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getUopEndereco() {
		return uopEndereco;
	}

	public void setUopEndereco(String uopEndereco) {
		this.uopEndereco = uopEndereco;
	}

	public Character getUopInCp() {
		return uopInCp;
	}

	public void setUopInCp(Character uopInCp) {
		this.uopInCp = uopInCp;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (uopNuSequencial != null ? uopNuSequencial.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// TODO: Warning - this method won't work in the case the id fields are
		// not set
		if (!(object instanceof LogUnidOper)) {
			return false;
		}
		LogUnidOper other = (LogUnidOper) object;
		if ((this.uopNuSequencial == null && other.uopNuSequencial != null)
				|| (this.uopNuSequencial != null && !this.uopNuSequencial
						.equals(other.uopNuSequencial))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.LogUnidOper[ uopNuSequencial=" + uopNuSequencial
				+ " ]";
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	@Override
	public LogUnidOper clone() throws CloneNotSupportedException {
		return (LogUnidOper) super.clone();
	}

	public static LogUnidOper recuperaUnico(GenericoDAO<IPersistent> dao,
			String filtroCep, String filtroNumUniOpe) {
		String where = null;
		if(filtroCep != null && !filtroCep.isEmpty())
			where = "CEP = " + filtroCep;
		if(filtroNumUniOpe != null && !filtroNumUniOpe.isEmpty())
			where = "UOP_NU_SEQUENCIAL = " + filtroNumUniOpe;
		
		return dao.uniqueResult(LogUnidOper.class, where, null);
	}

}
