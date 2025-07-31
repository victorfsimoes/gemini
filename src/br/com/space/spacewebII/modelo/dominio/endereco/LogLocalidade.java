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
@Table(name = "log_localidade")
public class LogLocalidade implements Serializable, IPersistent {
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "LOC_NU_SEQUENCIAL")
    private Integer locNuSequencial;
    @Column(name = "LOC_NOSUB")
    private String locNosub;
    @Column(name = "LOC_NO")
    private String locNo;
    @Column(name = "CEP")
    private String cep;
    @Column(name = "UFE_SG")
    private String ufeSg;
    @Column(name = "LOC_IN_SITUACAO")
    private int locInSituacao;
    @Column(name = "LOC_IN_TIPO_LOCALIDADE")
    private char locInTipoLocalidade;
    @Column(name = "LOC_NU_SEQUENCIAL_SUB")
    private Integer locNuSequencialSub;

    public LogLocalidade() {
    }

    public LogLocalidade(Integer locNuSequencial) {
        this.locNuSequencial = locNuSequencial;
    }

    public LogLocalidade(Integer locNuSequencial, String locNosub, String ufeSg, int locInSituacao, char locInTipoLocalidade) {
        this.locNuSequencial = locNuSequencial;
        this.locNosub = locNosub;
        this.ufeSg = ufeSg;
        this.locInSituacao = locInSituacao;
        this.locInTipoLocalidade = locInTipoLocalidade;
    }

    public Integer getLocNuSequencial() {
        return locNuSequencial;
    }

    public void setLocNuSequencial(Integer locNuSequencial) {
        this.locNuSequencial = locNuSequencial;
    }

    public String getLocNosub() {
        return locNosub;
    }

    public void setLocNosub(String locNosub) {
        this.locNosub = locNosub;
    }

    public String getLocNo() {
        return locNo;
    }

    public void setLocNo(String locNo) {
        this.locNo = locNo;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getUfeSg() {
        return ufeSg;
    }

    public void setUfeSg(String ufeSg) {
        this.ufeSg = ufeSg;
    }

    public int getLocInSituacao() {
        return locInSituacao;
    }

    public void setLocInSituacao(int locInSituacao) {
        this.locInSituacao = locInSituacao;
    }

    public char getLocInTipoLocalidade() {
        return locInTipoLocalidade;
    }

    public void setLocInTipoLocalidade(char locInTipoLocalidade) {
        this.locInTipoLocalidade = locInTipoLocalidade;
    }

    public Integer getLocNuSequencialSub() {
        return locNuSequencialSub;
    }

    public void setLocNuSequencialSub(Integer locNuSequencialSub) {
        this.locNuSequencialSub = locNuSequencialSub;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (locNuSequencial != null ? locNuSequencial.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogLocalidade)) {
            return false;
        }
        LogLocalidade other = (LogLocalidade) object;
        if ((this.locNuSequencial == null && other.locNuSequencial != null) || (this.locNuSequencial != null && !this.locNuSequencial.equals(other.locNuSequencial))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.LogLocalidade[ locNuSequencial=" + locNuSequencial + " ]";
    }

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}
	
	@Override
	public LogLocalidade clone() throws CloneNotSupportedException {
		return (LogLocalidade) super.clone();
	}
	
	public static LogLocalidade recuperaUnico(GenericoDAO<IPersistent> dao, String filtroCep, String filtroLocNum){
		String where = null;
		if(filtroCep != null && !filtroCep.isEmpty())
			where = "CEP = " + filtroCep;
		if(filtroLocNum != null && !filtroLocNum.isEmpty())
			where = "LOC_NU_SEQUENCIAL = " + filtroLocNum;
		
		return dao.uniqueResult(LogLocalidade.class, where, null);
	}
	
	public static List<LogLocalidade> recuperaTodasCidades(GenericoDAO<IPersistent> dao, String ordem){
		return dao.list(LogLocalidade.class, "", null, ordem, null);
	}
}
