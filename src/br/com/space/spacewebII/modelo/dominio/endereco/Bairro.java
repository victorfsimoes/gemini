package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.validator.Length;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceId;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

@Entity(name = "bairro")
@Table(name = "bairro")
@SpaceTable(name = "bairro")
public class Bairro implements IPersistent, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "bai_codigo";
	public static final String COLUNA_DESCRICAO = "bai_desc";

	@Id
	@SpaceId
	@Column(name = COLUNA_CODIGO)
	@SpaceColumn(name = COLUNA_CODIGO)
	private int codigoBairro;

	@Column(name = COLUNA_DESCRICAO, length = 35)
	@Length(max = 35)
	@SpaceColumn(name = COLUNA_DESCRICAO, length = 45)
	private String descricaoBairro;

	@Column(name = "bai_ativo")
	private int bairroAtivo;

	@Column(name = "bai_filcodigo")
	private int filialBairro;

	public Bairro() {
	}

	public int getCodigoBairro() {
		return codigoBairro;
	}

	public void setCodigoBairro(int codigoBairro) {
		this.codigoBairro = codigoBairro;
	}

	public String getDescricaoBairro() {
		return descricaoBairro;
	}

	public void setDescricaoBairro(String descricaoBairro) {
		this.descricaoBairro = descricaoBairro;
	}

	public int getBairroAtivo() {
		return bairroAtivo;
	}

	public void setBairroAtivo(int bairroAtivo) {
		this.bairroAtivo = bairroAtivo;
	}

	public int getFilialBairro() {
		return filialBairro;
	}

	public void setFilialBairro(int filialBairro) {
		this.filialBairro = filialBairro;
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
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {

	}

	public static List<Bairro> recuperaTodosBairros(GenericoDAO<IPersistent> dao, String ordem) {
		return dao.list(Bairro.class, "", null, ordem, null);
	}

	public static Bairro recuperaBairro(GenericoDAO<IPersistent> dao, String descricaoBairro) {

		String where = COLUNA_DESCRICAO + " = '" + descricaoBairro + "'";
		List<Bairro> bairros = dao.list(Bairro.class, where, null, null, null);

		return ListUtil.isValida(bairros) ? bairros.get(0) : null;
	}

	public int maxCodigo(GenericoDAO<IPersistent> dao) {
		return dao.maxResult("bairro", COLUNA_CODIGO, null);
	}
}