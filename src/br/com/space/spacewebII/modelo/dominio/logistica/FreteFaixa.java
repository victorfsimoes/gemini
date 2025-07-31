package br.com.space.spacewebII.modelo.dominio.logistica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "fretefaixa")
public class FreteFaixa implements IPersistent {

	public static final String COLUNA_CODIGO = "frf_codigo";

	@Id
	@GeneratedValue
	@Column(name = "frf_codigo")
	private int codigo;

	@Column(name = "frf_desc")
	private String descricao;

	@Column(name = "frf_valkgadic")
	private double valorKiloAdicional;

	@Column(name = "frf_ativo")
	private int flagAtivo;

	public FreteFaixa() {

	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValorKiloAdicional() {
		return valorKiloAdicional;
	}

	public void setValorKiloAdicional(double valorKiloAdicional) {
		this.valorKiloAdicional = valorKiloAdicional;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * 
	 * @param dao
	 * @param freteFaixaCodigo
	 * @return
	 */
	public static FreteFaixa recuperarPeso(GenericoDAO dao, int freteFaixaCodigo) {
		String where = COLUNA_CODIGO + " = " + freteFaixaCodigo;
		return dao.uniqueResult(FreteFaixa.class, where, null);
	}

}
