package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table
public class Fabricante implements Serializable, IPersistent {
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getAtivo() {
		return ativo;
	}

	public void setAtivo(int ativo) {
		this.ativo = ativo;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	private static final long serialVersionUID = 1L;
	public static final String COLUNA_CODIGO = "fab_codigo";
	public static final String COLUNA_FILIAL_CODIGO = "fab_filcodigo";
	public static final String COLUNA_ATIVO = "fab_ativo";
	
	@Id
	@Column(name = COLUNA_CODIGO)
	private int codigo;
	
	@Column(name = COLUNA_FILIAL_CODIGO)
	private int filialCodigo;
	
	@Column(name = COLUNA_ATIVO)
	private int ativo;
	
	@Column(name = "fab_razao")
	private String razao;
	
	public Fabricante(){
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		
	}
	
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public static Fabricante recuperaFabricanteAtivo(GenericoDAO dao, int codigo, Integer codigoFilial){
		StringBuilder where = new StringBuilder(COLUNA_CODIGO).append(" = ").append(codigo).append(" and ").append(COLUNA_ATIVO).append(" = 1");
		if(codigoFilial != null)
			where.append(" and ").append(COLUNA_FILIAL_CODIGO).append(" = ").append(codigoFilial);
		
		return dao.uniqueResult(Fabricante.class, where.toString(), null);
	}

}
