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
@Table(name = "log_cpc")
public class LogCpc implements Serializable, IPersistent {
    private static final long serialVersionUID = 1L;
    
    @Id
    @Column(name = "CPC_NU_SEQUENCIAL")
    private Integer cpcNuSequencial;
    
    @Column(name = "UFE_SG")
    private String ufeSg;
    
    @Column(name = "LOC_NU_SEQUENCIAL")
    private int locNuSequencial;
    
    @Column(name = "CEP")
    private String cep;
    
    @Column(name = "CPC_NO")
    private String cpcNo;
    
    @Column(name = "CPC_ENDERECO")
    private String cpcEndereco;
    
    @Column(name = "CPC_TIPO")
    private String cpcTipo;
    
    @Column(name = "CPC_ABRANGENCIA")
    private String cpcAbrangencia;

    public LogCpc() {
    }

    public LogCpc(Integer cpcNuSequencial) {
        this.cpcNuSequencial = cpcNuSequencial;
    }

    public LogCpc(Integer cpcNuSequencial, String ufeSg, int locNuSequencial, String cep, String cpcNo, String cpcEndereco) {
        this.cpcNuSequencial = cpcNuSequencial;
        this.ufeSg = ufeSg;
        this.locNuSequencial = locNuSequencial;
        this.cep = cep;
        this.cpcNo = cpcNo;
        this.cpcEndereco = cpcEndereco;
    }

    public Integer getCpcNuSequencial() {
        return cpcNuSequencial;
    }

    public void setCpcNuSequencial(Integer cpcNuSequencial) {
        this.cpcNuSequencial = cpcNuSequencial;
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

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCpcNo() {
        return cpcNo;
    }

    public void setCpcNo(String cpcNo) {
        this.cpcNo = cpcNo;
    }

    public String getCpcEndereco() {
        return cpcEndereco;
    }

    public void setCpcEndereco(String cpcEndereco) {
        this.cpcEndereco = cpcEndereco;
    }

    public String getCpcTipo() {
        return cpcTipo;
    }

    public void setCpcTipo(String cpcTipo) {
        this.cpcTipo = cpcTipo;
    }

    public String getCpcAbrangencia() {
        return cpcAbrangencia;
    }

    public void setCpcAbrangencia(String cpcAbrangencia) {
        this.cpcAbrangencia = cpcAbrangencia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cpcNuSequencial != null ? cpcNuSequencial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogCpc)) {
            return false;
        }
        LogCpc other = (LogCpc) object;
        if ((this.cpcNuSequencial == null && other.cpcNuSequencial != null) || (this.cpcNuSequencial != null && !this.cpcNuSequencial.equals(other.cpcNuSequencial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.LogCpc[ cpcNuSequencial=" + cpcNuSequencial + " ]";
    }

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}
	
	@Override
	public LogCpc clone() throws CloneNotSupportedException{
		return (LogCpc) super.clone();
	}
	
	public static LogCpc recuperaUnico(GenericoDAO<IPersistent> dao, String filtroCep, String filtroNumCpc){
		String where = null;
		if(filtroCep != null && !filtroCep.isEmpty())
			where = "CEP = " + filtroCep;
		if(filtroNumCpc != null && !filtroNumCpc.isEmpty())
			where = "CPC_NU_SEQUENCIAL = " + filtroNumCpc;
		
		return dao.uniqueResult(LogCpc.class, where, null);
	}
    
}
