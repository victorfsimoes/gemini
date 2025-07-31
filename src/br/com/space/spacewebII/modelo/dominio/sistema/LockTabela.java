package br.com.space.spacewebII.modelo.dominio.sistema;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

@Entity
@Table(name = "locktables")
public class LockTabela implements IPersistent {

	private static final String COLUNA_CODIGO_SESSAO = "lct_sessao";
	private static final String COLUNA_TABELA = "lct_tabela";

	@Id
	@Column(name = COLUNA_TABELA)
	String nomeTabela;

	@Column(name = COLUNA_CODIGO_SESSAO)
	Integer codigoSessao;

	@Column(name = "lct_seq")
	private int sequencia;

	public LockTabela() {

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

	public Integer getCodigoSessao() {
		return codigoSessao;
	}

	public void setCodigoSessao(Integer codigoSessao) {
		this.codigoSessao = codigoSessao;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	public int getSequencia() {
		return sequencia;
	}

	public void setSequencia(int sequencia) {
		this.sequencia = sequencia;
	}

	public static LockTabela recuperaUnico(GenericoDAO<IPersistent> dao,
			String nomeTabela) {
		return dao.uniqueResult(LockTabela.class, COLUNA_TABELA + " = '"
				+ nomeTabela + "'", null);
	}

}
