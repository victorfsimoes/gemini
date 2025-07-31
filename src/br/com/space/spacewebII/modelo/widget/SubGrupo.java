package br.com.space.spacewebII.modelo.widget;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.com.space.api.core.util.ListUtil;
import br.com.space.spacewebII.modelo.list.GrupoSubGrupoProduto;

/**
 * Classe de modelo a ao data grid da tela de pesquisa de produtos
 * 
 * @author Renato
 * 
 */

public class SubGrupo implements Comparable<SubGrupo>, Serializable,
		SubGrupoWidget {

	public static final int CODIGO_SUBGRUPO_TODOS = -1;
	public static final int CODIGO_SUBGRUPO_OUTROS = -2;
	public static final int CODIGO_SUBGRUPO_DESTAQUE = -3;

	private static final long serialVersionUID = 1L;
	private static Comparator<SubGrupo> comparadorNome = null;
	private int codigo = 0;
	private String descricao = null;
	private boolean selecionado = false;
	private int grupoCodigo = 0;
	private List<CaixaProdutoVisualizavel> produtos = null;
	private int totalizador = 0;

	public SubGrupo(GrupoSubGrupoProduto grupoProduto) {

		this(grupoProduto.getCodigoSubGrupo(), grupoProduto.getCodigoGrupo(),
				grupoProduto.getDescricaoSubGrupo(), false);

		this.totalizador = grupoProduto.getTotalizador();
	}

	public SubGrupo(int codigo, int grupoCodigo, String nome,
			boolean selecionado) {
		this(codigo, grupoCodigo);
		this.descricao = nome;
		this.setSelecionado(selecionado);
	}

	public SubGrupo(int codigo, int grupoCodigo) {
		this();
		this.codigo = codigo;
		this.grupoCodigo = grupoCodigo;
	}

	public SubGrupo() {
		produtos = new ArrayList<CaixaProdutoVisualizavel>();
	}

	/**
	 * Adiciona o produto
	 * 
	 * @param caixaProdutoVisualizavel
	 */
	public void addProduto(CaixaProdutoVisualizavel caixaProdutoVisualizavel) {
		produtos.add(caixaProdutoVisualizavel);
	}

	public void addProdutos(
			List<CaixaProdutoVisualizavel> caixaProdutoVisualizavel) {
		produtos.addAll(caixaProdutoVisualizavel);
	}

	@Override
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}

	/**
	 * @return Uma string contendo a descrição do sub-grupo mais o totalizador
	 */
	public String getDescricaoVisualizacao() {

		String desc = getDescricao();

		if (getTotalizador() > 0) {

			desc += " (" + getTotalizador() + ")";

		}
		return desc;
	}

	public void setDescricao(String nome) {
		this.descricao = nome;
	}

	public List<CaixaProdutoVisualizavel> getProdutos() {
		return produtos;
	}

	@SuppressWarnings("unchecked")
	public void setProdutos(List<? extends CaixaProdutoVisualizavel> produtos) {
		this.produtos = (List<CaixaProdutoVisualizavel>) produtos;

	}

	public boolean getSelecionado() {
		return selecionado;
	}

	public void setSelecionado(boolean selecionado) {
		this.selecionado = selecionado;
	}

	public static Comparator<SubGrupo> getComparadorNome() {

		if (comparadorNome == null) {

			comparadorNome = new Comparator<SubGrupo>() {
				@Override
				public int compare(SubGrupo o1, SubGrupo o2) {

					if (o1.getCodigo() == CODIGO_SUBGRUPO_DESTAQUE) {

						return -1;

					} else if (o2.getCodigo() == CODIGO_SUBGRUPO_DESTAQUE) {
						return 1;
					}

					if (o1.getCodigo() == CODIGO_SUBGRUPO_DESTAQUE) {
						return -1;
					}

					if (o1.getCodigo() == CODIGO_SUBGRUPO_TODOS
							&& o2.getCodigo() == CODIGO_SUBGRUPO_DESTAQUE) {

						return 1;
					} else if (o1.getCodigo() == CODIGO_SUBGRUPO_TODOS) {
						return -1;
					}

					if (o2.getCodigo() == CODIGO_SUBGRUPO_DESTAQUE
							|| o2.getCodigo() == CODIGO_SUBGRUPO_TODOS) {
						return 1;
					}

					if (o1.getDescricao() == null || o2.getDescricao() == null) {
						return 1;
					}
					return o1.getDescricao().compareTo(o2.getDescricao());
				}
			};

		}
		return comparadorNome;
	}

	@Override
	public int compareTo(SubGrupo o) {

		return Integer.compare(codigo, o.getCodigo());
	}

	@Override
	public int getGrupoCodigo() {
		return grupoCodigo;
	}

	public void setTotalizador(int totalizador) {
		this.totalizador = totalizador;
	}

	public int getTotalizador() {

		if (totalizador == 0 && ListUtil.isValida(produtos)) {
			totalizador = produtos.size();
		}

		return totalizador;
	}

	public boolean isTemProdutos() {
		return ListUtil.isValida(produtos);
	}

	public void clearProdutos() {
		produtos.clear();
		totalizador = 0;
	}

	public boolean isExibeEstrela() {
		return codigo == CODIGO_SUBGRUPO_DESTAQUE;
	}
}