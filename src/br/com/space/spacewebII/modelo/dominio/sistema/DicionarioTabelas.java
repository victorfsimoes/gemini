/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 *
 * @author Renato
 */
@Entity
@Table(name = "dictabelas")
public class DicionarioTabelas implements Serializable, IPersistent {
	
	private static final String COLUNA_TABELA = "DTB_TABELA";
	
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = COLUNA_TABELA)
    private String tabela;
    @Column(name = "DTB_DESCRICAO")
    private String tituloTabela;

    public DicionarioTabelas() {
    }

    public DicionarioTabelas(String dtbTabela) {
        this.tabela = dtbTabela;
    }

    public String getDtbTabela() {
        return tabela;
    }

    public void setDtbTabela(String dtbTabela) {
        this.tabela = dtbTabela;
    }

    public String getDtbDescricao() {
        return tituloTabela;
    }

    public void setDtbDescricao(String dtbDescricao) {
        this.tituloTabela = dtbDescricao;
    }

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public static DicionarioTabelas recuperarUnico(br.com.space.api.web.modelo.dao.GenericoDAO<IPersistent> dao, String tabela) {
		return dao.uniqueResult(DicionarioTabelas.class, COLUNA_TABELA + " like '" + tabela + "'",
				null);
	}
    
}
