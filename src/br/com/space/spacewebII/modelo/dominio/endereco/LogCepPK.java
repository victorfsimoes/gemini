package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Desenvolvimento
 */
@Embeddable
public class LogCepPK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@Column(name = "LOG_NU_CEP")
    private int logNuCep;
    
	@Column(name = "LOG_IN_TABELA")
    private int logInTabela;

    public LogCepPK() {
    }

    public LogCepPK(int logNuCep, int logInTabela) {
        this.logNuCep = logNuCep;
        this.logInTabela = logInTabela;
    }

    public int getLogNuCep() {
        return logNuCep;
    }

    public void setLogNuCep(int logNuCep) {
        this.logNuCep = logNuCep;
    }

    public int getLogInTabela() {
        return logInTabela;
    }

    public void setLogInTabela(int logInTabela) {
        this.logInTabela = logInTabela;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) logNuCep;
        hash += (int) logInTabela;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LogCepPK)) {
            return false;
        }
        LogCepPK other = (LogCepPK) object;
        if (this.logNuCep != other.logNuCep) {
            return false;
        }
        if (this.logInTabela != other.logInTabela) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entidades.LogCepPK[ logNuCep=" + logNuCep + ", logInTabela=" + logInTabela + " ]";
    }
    
}
