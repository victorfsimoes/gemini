package br.com.space.spacewebII.modelo.list;

import java.util.List;

import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.dao.db.Table;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.controle.estoque.Estoque;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;

/**
 * Classe que padronização de um resultset
 * 
 * @author Renato
 * 
 */
@SpaceTable(name = "gruposubgrupoproduto")
public class GrupoSubGrupoProduto implements IPersistent {

	private static Table table = null;

	@SpaceColumn(name = "grp_codigo")
	private int codigoGrupo = 0;

	@SpaceColumn(name = "grp_desc")
	private String descricaoGrupo;

	@SpaceColumn(name = "sgp_codigo")
	private int codigoSubGrupo = 0;

	@SpaceColumn(name = "sgp_desc")
	private String descricaoSubGrupo;

	@SpaceColumn(name = "tolizador")
	private int totalizador = 0;

	public GrupoSubGrupoProduto() {

	}

	public int getCodigoGrupo() {
		return codigoGrupo;
	}

	public void setCodigoGrupo(int codigoGrupo) {
		this.codigoGrupo = codigoGrupo;
	}

	public String getDescricaoGrupo() {
		return descricaoGrupo;
	}

	public void setDescricaoGrupo(String descricaoGrupo) {
		this.descricaoGrupo = descricaoGrupo;
	}

	public int getCodigoSubGrupo() {
		return codigoSubGrupo;
	}

	public void setCodigoSubGrupo(int codigoSubGrupo) {
		this.codigoSubGrupo = codigoSubGrupo;
	}

	public String getDescricaoSubGrupo() {
		return descricaoSubGrupo;
	}

	public void setDescricaoSubGrupo(String descricaoSubGrupo) {
		this.descricaoSubGrupo = descricaoSubGrupo;
	}

	public int getTotalizador() {
		return totalizador;
	}

	public void setTotalizador(int totalizador) {
		this.totalizador = totalizador;
	}

	/**
	 * Recupera os Grupos e seus sub-grupos a partir de um select personalizado
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param precoBase
	 * @param permiteProduoSemEstoque
	 * @return
	 */
	public static List<GrupoSubGrupoProduto> recuperarDisponiveisParaVenda(
			GenericoDAO dao, int filialCodigo, int precoBase,
			boolean permiteProduoSemEstoque) {

		String fromAdicional = " inner join"
				+ " grupopro ON grp_codigo = pro_grpcodigo" + " left join"
				+ " subgrupopro ON pro_sgpcodigo = sgp_codigo"
				+ " inner join parametro on par_filcodigo =  pfi_filcodigo";

		String colunas = "grp_codigo, grp_desc, sgp_codigo, sgp_desc, count(*) as tolizador ";

		String groupBy = " group by grp_codigo , grp_desc , sgp_codigo , sgp_desc ";

		String where = permiteProduoSemEstoque ? "" : Estoque
				.getSubSelectEstoqueProduto(filialCodigo) + " > 0 ";

		where = StringUtil.isValida(where) ? " where " + where : "";

		String select = "select "
				+ colunas
				+ " from "
				+ Produto.getFromProdutoPadraoVenda(filialCodigo, precoBase,
						fromAdicional) + where + groupBy
				+ " order by grp_desc , grp_codigo , sgp_desc , sgp_codigo";

		try {
			return dao.list(GrupoSubGrupoProduto.class, select, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return null;

	}

	@Override
	public Table getTable() {

		return table;
	}

	@Override
	public void setTable(Table table) {
		GrupoSubGrupoProduto.table = table;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
}
