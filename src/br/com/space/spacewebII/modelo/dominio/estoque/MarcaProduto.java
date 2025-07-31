package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity(name = "marcapro")
@Table(name = "marcapro")
public class MarcaProduto implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "mrc_codigo";

	@Id
	@Column(name = COLUNA_CODIGO)
	private int codigo = 0;
	@Column(name = "mrc_desc")
	private String descricao = null;
	@Column(name = "mrc_ativo")
	private int flagAtivo = 0;
	@Column(name = "mrc_filcodigo")
	private int filiaCodigo = 0;

	public MarcaProduto() {
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

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFiliaCodigo() {
		return filiaCodigo;
	}

	public void setFiliaCodigo(int filiaCodigo) {
		this.filiaCodigo = filiaCodigo;
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

	/**
	 * Recupera uma marca de acordo com seu código.
	 * 
	 * @param dao
	 * @param codigoMarca
	 * @return
	 */
	public static MarcaProduto recuperarUnico(GenericoDAO dao, int codigoMarca) {
		return dao.uniqueResult(MarcaProduto.class, COLUNA_CODIGO + " = "
				+ codigoMarca, null);
	}
}
