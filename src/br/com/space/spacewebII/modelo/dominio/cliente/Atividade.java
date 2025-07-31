package br.com.space.spacewebII.modelo.dominio.cliente;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.Length;

import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceId;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

@Entity(name="atividade")
@Table(name = "atividade")
@SpaceTable(name="atividade")
public class Atividade implements Comparable<Atividade>, IPersistent {
	
	public static final String COLUNA_CODIGO = "atv_codigo";
	public static final String CAMPO_CODIGO = "codigoAtividade";
	public static final String COLUNA_DESCRICAO = "atv_desc";
	public static final String COLUNA_ATIVO = "atv_ativo";

	@Id
	@SpaceId
	@Column(name = COLUNA_CODIGO)
	@SpaceColumn(name = COLUNA_CODIGO)
	private int codigoAtividade;

	@Column(name = COLUNA_DESCRICAO, length = 35)
	@Length(max = 35)
	@SpaceColumn(name = COLUNA_DESCRICAO, length = 50)
	private String descricaoAtividade;

	@Column(name = COLUNA_ATIVO)
	@Formula("coalesce(" +COLUNA_ATIVO + ", 0)")
	private int flagAtivo = 0;

	@Column(name = "atv_filcodigo")
	private int filialAtividade;

	public Atividade() {
	}

	public int getCodigoAtividade() {
		return codigoAtividade;
	}

	public void setCodigoAtividade(int codigoAtividade) {
		this.codigoAtividade = codigoAtividade;
	}

	public String getDescricaoAtividade() {
		return descricaoAtividade;
	}

	public void setDescricaoAtividade(String descricaoAtividade) {
		this.descricaoAtividade = descricaoAtividade.trim();
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFilialAtividade() {
		return filialAtividade;
	}

	public void setFilialAtividade(int filialAtividade) {
		this.filialAtividade = filialAtividade;
	}

	@Override
	public int compareTo(Atividade o) {
		return this.descricaoAtividade.toLowerCase().trim().compareTo(o.getDescricaoAtividade().toLowerCase().trim());
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {		
	}
	
	public static List<Atividade> recuperaTodasAtividades(GenericoDAO<IPersistent> dao, String ordem){
		return dao.list(Atividade.class, "", null, ordem, null);
	}
	
	public static Atividade recuperaAtividade(GenericoDAO<IPersistent> dao, String descricaoAtividade){
		return dao.uniqueResult(Atividade.class, COLUNA_DESCRICAO + " like '" + descricaoAtividade + "'", null);
	}
	
	public static Atividade recuperaAtividade(GenericoDAO<IPersistent> dao, int codigo){
		return dao.uniqueResult(Atividade.class, COLUNA_CODIGO + " = " + codigo, null);
	}
	
	@Override
	public String toString() {
		return descricaoAtividade;
	}
}
