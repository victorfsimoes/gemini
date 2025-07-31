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
@Table(name = "log_grande_usuario")
public class LogGrandeUsuario implements Serializable, IPersistent {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "GRU_NU_SEQUENCIAL")
    private Integer gruNuSequencial;
    @Column(name = "UFE_SG")
    private String ufeSg;
    @Column(name = "LOC_NU_SEQUENCIAL")
    private int locNuSequencial;
    @Column(name = "LOG_NU_SEQUENCIAL")
    private String logNuSequencial;
    @Column(name = "BAI_NU_SEQUENCIAL")
    private int baiNuSequencial;
    @Column(name = "GRU_NO")
    private String gruNo;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "GRU_ENDERECO")
    private String gruEndereco;

    public LogGrandeUsuario() {
    }

    public LogGrandeUsuario(Integer gruNuSequencial) {
        this.gruNuSequencial = gruNuSequencial;
    }

    public LogGrandeUsuario(Integer gruNuSequencial, String ufeSg, int locNuSequencial, int baiNuSequencial, String gruNo, String cep, String gruEndereco) {
        this.gruNuSequencial = gruNuSequencial;
        this.ufeSg = ufeSg;
        this.locNuSequencial = locNuSequencial;
        this.baiNuSequencial = baiNuSequencial;
        this.gruNo = gruNo;
        this.cep = cep;
        this.gruEndereco = gruEndereco;
    }

    public Integer getGruNuSequencial() {
        return gruNuSequencial;
    }

    public void setGruNuSequencial(Integer gruNuSequencial) {
        this.gruNuSequencial = gruNuSequencial;
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

    public String getLogNuSequencial() {
        return logNuSequencial;
    }

    public void setLogNuSequencial(String logNuSequencial) {
        this.logNuSequencial = logNuSequencial;
    }

    public int getBaiNuSequencial() {
        return baiNuSequencial;
    }

    public void setBaiNuSequencial(int baiNuSequencial) {
        this.baiNuSequencial = baiNuSequencial;
    }

    public String getGruNo() {
        return gruNo;
    }

    public void setGruNo(String gruNo) {
        this.gruNo = gruNo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getGruEndereco() {
        return gruEndereco;
    }

    public void setGruEndereco(String gruEndereco) {
        this.gruEndereco = gruEndereco;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gruNuSequencial != null ? gruNuSequencial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogGrandeUsuario)) {
            return false;
        }
        LogGrandeUsuario other = (LogGrandeUsuario) object;
        if ((this.gruNuSequencial == null && other.gruNuSequencial != null) || (this.gruNuSequencial != null && !this.gruNuSequencial.equals(other.gruNuSequencial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.LogGrandeUsuario[ gruNuSequencial=" + gruNuSequencial + " ]";
    }

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		
	}
	
	@Override
	public LogGrandeUsuario clone() throws CloneNotSupportedException {
		return (LogGrandeUsuario) super.clone();
	}
	
	public static LogGrandeUsuario recuperaUnico (GenericoDAO<IPersistent> dao, String filtroCep, String filtroNum){
		String where = null;
		
		if(filtroCep != null && !filtroCep.isEmpty()){
			where = "CEP = " + filtroCep;
		}
		if (filtroNum != null && !filtroNum.isEmpty()){
			where = "GRU_NU_SEQUENCIAL = " + filtroNum;
		}
		
		return dao.uniqueResult(LogGrandeUsuario.class, where, null);
	}
    
}
