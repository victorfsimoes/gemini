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
@Table(name = "log_logradouro")
public class LogLogradouro implements Serializable, IPersistent {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "LOG_NU_SEQUENCIAL")
    private Integer logNuSequencial;
    @Column(name = "UFE_SG")
    private String ufeSg;
    @Column(name = "LOC_NU_SEQUENCIAL")
    private int locNuSequencial;
    @Column(name = "LOG_NO")
    private String logNo;
    @Column(name = "LOG_NOME")
    private String logNome;
    @Column(name = "BAI_NU_SEQUENCIAL_INI")
    private Integer baiNuSequencialIni;
    @Column(name = "BAI_NU_SEQUENCIAL_FIM")
    private Integer baiNuSequencialFim;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "LOG_COMPLEMENTO")
    private String logComplemento;
    @Column(name = "LOG_TIPO_LOGRADOURO")
    private String logTipoLogradouro;
    @Column(name = "LOG_STATUS_TIPO_LOG")
    private char logStatusTipoLog;
    @Column(name = "LOG_NO_SEM_ACENTO")
    private String logNoSemAcento;

    public LogLogradouro() {
    }

    public LogLogradouro(Integer logNuSequencial) {
        this.logNuSequencial = logNuSequencial;
    }

    public LogLogradouro(Integer logNuSequencial, String ufeSg, int locNuSequencial, String logNo, String cep, String logTipoLogradouro, char logStatusTipoLog, String logNoSemAcento) {
        this.logNuSequencial = logNuSequencial;
        this.ufeSg = ufeSg;
        this.locNuSequencial = locNuSequencial;
        this.logNo = logNo;
        this.cep = cep;
        this.logTipoLogradouro = logTipoLogradouro;
        this.logStatusTipoLog = logStatusTipoLog;
        this.logNoSemAcento = logNoSemAcento;
    }

    public Integer getLogNuSequencial() {
        return logNuSequencial;
    }

    public void setLogNuSequencial(Integer logNuSequencial) {
        this.logNuSequencial = logNuSequencial;
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

    public String getLogNo() {
        return logNo;
    }

    public void setLogNo(String logNo) {
        this.logNo = logNo;
    }

    public String getLogNome() {
        return logNome;
    }

    public void setLogNome(String logNome) {
        this.logNome = logNome;
    }

    public Integer getBaiNuSequencialIni() {
        return baiNuSequencialIni;
    }

    public void setBaiNuSequencialIni(Integer baiNuSequencialIni) {
        this.baiNuSequencialIni = baiNuSequencialIni;
    }

    public Integer getBaiNuSequencialFim() {
        return baiNuSequencialFim;
    }

    public void setBaiNuSequencialFim(Integer baiNuSequencialFim) {
        this.baiNuSequencialFim = baiNuSequencialFim;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getLogComplemento() {
        return logComplemento;
    }

    public void setLogComplemento(String logComplemento) {
        this.logComplemento = logComplemento;
    }

    public String getLogTipoLogradouro() {
        return logTipoLogradouro;
    }

    public void setLogTipoLogradouro(String logTipoLogradouro) {
        this.logTipoLogradouro = logTipoLogradouro;
    }

    public char getLogStatusTipoLog() {
        return logStatusTipoLog;
    }

    public void setLogStatusTipoLog(char logStatusTipoLog) {
        this.logStatusTipoLog = logStatusTipoLog;
    }

    public String getLogNoSemAcento() {
        return logNoSemAcento;
    }

    public void setLogNoSemAcento(String logNoSemAcento) {
        this.logNoSemAcento = logNoSemAcento;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (logNuSequencial != null ? logNuSequencial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogLogradouro)) {
            return false;
        }
        LogLogradouro other = (LogLogradouro) object;
        if ((this.logNuSequencial == null && other.logNuSequencial != null) || (this.logNuSequencial != null && !this.logNuSequencial.equals(other.logNuSequencial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.LogLogradouro[ logNuSequencial=" + logNuSequencial + " ]";
    }
    
    public static LogLogradouro recuperaUnico(GenericoDAO<IPersistent> dao, String filtroCEP, String filtroNumLogradouro){
    	String where = null;
    	
    	if(filtroCEP != null && !filtroCEP.isEmpty())
    		where = "CEP = " + filtroCEP;
    	if(filtroNumLogradouro != null && !filtroNumLogradouro.isEmpty())
    		where = "LOG_NU_SEQUENCIAL = " + filtroNumLogradouro;
    	
    	return dao.uniqueResult(LogLogradouro.class, where, null);
    	
    }

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		
	}
	
	@Override
	public LogLogradouro clone() throws CloneNotSupportedException {
		return (LogLogradouro) super.clone();
	}
    
}
