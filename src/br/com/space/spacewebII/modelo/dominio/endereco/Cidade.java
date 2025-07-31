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

@Entity(name = "cidade")
@Table(name = "cidade")
@SpaceTable(name = "cidade")
public class Cidade implements IPersistent, Serializable {

	private static final long serialVersionUID = 1L;

	public static final String COLUNA_CODIGO = "cid_codigo";
	public static final String COLUNA_DESCRICAO = "cid_desc";

	@Id
	@SpaceId
	@Column(name = COLUNA_CODIGO)
	@SpaceColumn(name = COLUNA_CODIGO)
	private int codigoCidade;

	@Column(name = COLUNA_DESCRICAO, length = 35)
	@Length(max = 35)
	@SpaceColumn(name = COLUNA_DESCRICAO, length = 50)
	private String descricaoCidade;

	@Column(name = "cid_ativo")
	private int cidadeAtivo;

	@Column(name = "cid_filcodigo")
	private int filialCidade;

	public Cidade() {
	}

	public int getCodigoCidade() {
		return codigoCidade;
	}

	public void setCodigoCidade(int codigoCidade) {
		this.codigoCidade = codigoCidade;
	}

	public String getDescricaoCidade() {
		return descricaoCidade;
	}

	public void setDescricaoCidade(String descricaoCidade) {
		this.descricaoCidade = descricaoCidade;
	}

	public int getCidadeAtivo() {
		return cidadeAtivo;
	}

	public void setCidadeAtivo(int cidadeAtivo) {
		this.cidadeAtivo = cidadeAtivo;
	}

	public int getFilialCidade() {
		return filialCidade;
	}

	public void setFilialCidade(int filialCidade) {
		this.filialCidade = filialCidade;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
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

	public static List<Cidade> recuperaTodasCidades(GenericoDAO<IPersistent> dao, String ordem) {
		return dao.list(Cidade.class, "", null, ordem, null);
	}

	public static Cidade recuperaCidade(GenericoDAO<IPersistent> dao, String descricaoCidade) {

		List<Cidade> cidades = dao.list(Cidade.class, COLUNA_DESCRICAO + " = '" + descricaoCidade + "'", null, null,
				null);

		return ListUtil.isValida(cidades) ? cidades.get(0) : null;
	}

	public int maxCodigo(GenericoDAO<IPersistent> dao) {
		return dao.maxResult("cidade", COLUNA_CODIGO, null);
	}
}
