package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;
import java.util.List;

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
@Table(name = "log_bairro")
public class LogBairro implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "BAI_NU_SEQUENCIAL")
	private Integer baiNuSequencial;

	@Column(name = "UFE_SG")
	private String ufeSg;

	@Column(name = "LOC_NU_SEQUENCIAL")
	private int locNuSequencial;

	@Column(name = "BAI_NO")
	private String baiNo;

	@Column(name = "BAI_NO_ABREV")
	private String baiNoAbrev;

	public LogBairro() {
	}

	public LogBairro(Integer baiNuSequencial) {
		this.baiNuSequencial = baiNuSequencial;
	}

	public LogBairro(Integer baiNuSequencial, String ufeSg,
			int locNuSequencial, String baiNo) {
		this.baiNuSequencial = baiNuSequencial;
		this.ufeSg = ufeSg;
		this.locNuSequencial = locNuSequencial;
		this.baiNo = baiNo;
	}

	public Integer getBaiNuSequencial() {
		return baiNuSequencial;
	}

	public void setBaiNuSequencial(Integer baiNuSequencial) {
		this.baiNuSequencial = baiNuSequencial;
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

	public String getBaiNo() {
		return baiNo;
	}

	public void setBaiNo(String baiNo) {
		this.baiNo = baiNo;
	}

	public String getBaiNoAbrev() {
		return baiNoAbrev;
	}

	public void setBaiNoAbrev(String baiNoAbrev) {
		this.baiNoAbrev = baiNoAbrev;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (baiNuSequencial != null ? baiNuSequencial.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof LogBairro)) {
			return false;
		}
		LogBairro other = (LogBairro) object;
		if ((this.baiNuSequencial == null && other.baiNuSequencial != null)
				|| (this.baiNuSequencial != null && !this.baiNuSequencial
						.equals(other.baiNuSequencial))) {
			return false;
		}
		return true;
	}

	@Override
	public String toString() {
		return "entidades.LogBairro[ baiNuSequencial=" + baiNuSequencial + " ]";
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	@Override
	public LogBairro clone() throws CloneNotSupportedException {
		return (LogBairro) super.clone();
	}

	public static LogBairro recuperaUnicoBairroInicial(GenericoDAO<IPersistent> dao, String filtroBairroInicial){
		String where = "BAI_NU_SEQUENCIAL = " + filtroBairroInicial;
		return dao.uniqueResult(LogBairro.class, where, null);
	}
	
	public static LogBairro recuperaUnicoBairroFinal(GenericoDAO<IPersistent> dao, String filtroBairroFinal){
		String where = "BAI_NU_SEQUENCIAL = " + filtroBairroFinal;
		return dao.uniqueResult(LogBairro.class, where, null);
	}
	
	public static List<LogBairro> recuperaTodosBairros(GenericoDAO<IPersistent> dao, String ordem){
		return dao.list(LogBairro.class, "", null, ordem, null);
	}
}
