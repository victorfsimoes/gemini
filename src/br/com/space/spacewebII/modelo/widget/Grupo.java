package br.com.space.spacewebII.modelo.widget;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.enterprise.inject.Produces;
import javax.inject.Named;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.venda.Kit;
import br.com.space.spacewebII.modelo.list.GrupoSubGrupoProduto;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

/**
 * 
 * Classe de modelo a ao data grid da tela de pesquisa de produtos
 * 
 * @author Renato
 * 
 */
public class Grupo implements Comparable<Grupo>, GrupoWidget {

	private static final long serialVersionUID = 1L;

	public static final int CODIGO_GRUPO_TODOS = -1;
	public static final int CODIGO_GRUPO_OFERTA = -2;

	public static final int CODIGO_GRUPO_OUTRO = -3;
	public static final int CODIGO_GRUPO_CAMPANHA = -4;
	public static final int CODIGO_GRUPO_PROMOCAO = -5;
	public static final int CODIGO_GRUPO_KIT = -6;
	public static final int CODIGO_GRUPO_DESTAQUE = -7;

	private static Comparator<Grupo> comparadorNome = null;
	private int codigo = 0;
	private String descricao = null;
	private List<SubGrupo> subGrupos = null;

	private SubGrupo subGrupoDestaque = null;
	private SubGrupo subGrupoTodos = null;

	private boolean visualizarExclusiva = false;

	private String idII = null;

	/**
	 * Construtor padrao
	 */
	public Grupo() {
		subGrupos = new ArrayList<SubGrupo>();
	}

	/**
	 * Contrutor
	 * 
	 * @param codigo
	 *            Codigo do grupo
	 * @param nome
	 *            Nome do grupo
	 */
	public Grupo(int codigo, String nome) {
		this(codigo);
		this.descricao = nome;
	}

	public Grupo(int codigo, String nome, boolean visualizarExclusiva) {
		this(codigo, nome);
		this.visualizarExclusiva = visualizarExclusiva;
	}

	public Grupo(GrupoSubGrupoProduto grupoProduto) {
		this(grupoProduto.getCodigoGrupo(), grupoProduto.getDescricaoGrupo());
		this.visualizarExclusiva = true;
	}

	public Grupo(GrupoWidget grupoProduto) {
		this(grupoProduto.getCodigo(), grupoProduto.getDescricao());
	}

	/**
	 * Contrutor
	 * 
	 * @param codigo
	 *            Codigo do grupo
	 */
	public Grupo(int codigo) {
		this();
		this.codigo = codigo;
	}

	/**
	 * Adiciona um subGrupo a lista. Apos a inserção a lista sera oredenada
	 * 
	 * @param subGrupo
	 */
	public void addSubGrupo(SubGrupo subGrupo) {
		if (subGrupo != null) {

			int indice = Collections.binarySearch(subGrupos, subGrupo);
			if (indice < 0) {
				subGrupos.add(subGrupo);
				Collections.sort(subGrupos);
			}
		}
	}

	/**
	 * Limpa as listas de produtos e subgrupos
	 */
	public void limparListas() {
		subGrupos.clear();
		if (subGrupoDestaque != null) {
			subGrupoDestaque.clearProdutos();
		}

		if (subGrupoTodos != null) {
			subGrupoTodos.clearProdutos();
		}
	}

	/**
	 * Responsavel por adcionar o produto em sua lista tambem adiciona o subgrupo do
	 * produto
	 * 
	 * 
	 * @param produtoVisualizavel
	 */
	public void addProdutoVizualizavel(CaixaProdutoVisualizavel produtoVisualizavel, boolean gerarSubgrupoTodos) {

		if (produtoVisualizavel instanceof Produto || produtoVisualizavel instanceof Kit) {

			addProdutoESubGrupo(produtoVisualizavel);

		}

		adicionarSubGrupoTodos(getTextoTodos());
		subGrupoTodos.addProduto(produtoVisualizavel);
	}

	/**
	 * Monta o Objeto do SubGrupo do produto e o adiciona
	 * 
	 * @param produto
	 */
	private void addProdutoESubGrupo(CaixaProdutoVisualizavel produto) {

		SubGrupoWidget subGrupoWidget = produto.getSubGrupoWidget();

		SubGrupo subGrupo = null;

		if (subGrupoWidget != null && subGrupoWidget.getDescricao() != null
				&& !subGrupoWidget.getDescricao().trim().isEmpty()) {

			subGrupo = new SubGrupo(subGrupoWidget.getCodigo(), codigo, subGrupoWidget.getDescricao().trim(), false);

		} else {
			subGrupo = new SubGrupo(SubGrupo.CODIGO_SUBGRUPO_OUTROS, codigo, getTextoOutros(), false);
		}

		addProdutoAoSubGrupo(subGrupo, (CaixaProdutoVisualizavel) produto);

	}

	/**
	 * Adciona o produto no sub grupo se o sub-grupo ja existe o produto e inserido
	 * no mesmo, caso contrario e cadastrado um novo sub-grupo
	 * 
	 * @param subGrupo
	 * @param produtoVisualizavel
	 */
	private void addProdutoAoSubGrupo(SubGrupo subGrupo, CaixaProdutoVisualizavel produtoVisualizavel) {

		int indiceSub = Collections.binarySearch(subGrupos, subGrupo);

		if (indiceSub >= 0) {
			subGrupo = subGrupos.get(indiceSub);
		} else {
			addSubGrupo(subGrupo);
			Collections.sort(subGrupos);
		}

		subGrupo.addProduto(produtoVisualizavel);
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

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public List<SubGrupo> getSubGrupos() {
		return subGrupos;
	}

	public void setSubGrupos(List<SubGrupo> subGrupos) {
		this.subGrupos = subGrupos;
	}

	public String getIdII() {
		return idII;
	}

	public void setIdII(String idII) {
		this.idII = idII;
	}

	@Override
	public int compareTo(Grupo o) {

		return Integer.compare(codigo, o.getCodigo());
	}

	/**
	 * 
	 * @param subGrupoWidgets
	 */
	public void separarSubGrupo(List<? extends SubGrupoWidget> subGrupoWidgets) {

		for (SubGrupoWidget subGrupoWidget : subGrupoWidgets) {

			if (subGrupoWidget.getGrupoCodigo() == codigo) {
				addSubGrupo(new SubGrupo(subGrupoWidget.getCodigo(), subGrupoWidget.getGrupoCodigo(),
						subGrupoWidget.getDescricao(), false));
			}
		}
	}

	public static Comparator<Grupo> getComparadorNome() {
		if (comparadorNome == null) {

			comparadorNome = new Comparator<Grupo>() {
				@Override
				public int compare(Grupo o1, Grupo o2) {

					if (o1.getCodigo() == CODIGO_GRUPO_DESTAQUE) {
						return -1;
					}

					if (o1.getCodigo() == CODIGO_GRUPO_OFERTA && o2.getCodigo() == CODIGO_GRUPO_DESTAQUE) {

						return 1;
					} else if (o1.getCodigo() == CODIGO_GRUPO_OFERTA) {
						return -1;
					}

					if (o1.getCodigo() == CODIGO_GRUPO_TODOS
							&& (o2.getCodigo() == CODIGO_GRUPO_DESTAQUE || o2.getCodigo() == CODIGO_GRUPO_OFERTA)) {

						return 1;

					} else if (o1.getCodigo() == CODIGO_GRUPO_TODOS) {

						return -1;
					}

					if (o2.getCodigo() == CODIGO_GRUPO_DESTAQUE || o2.getCodigo() == CODIGO_GRUPO_OFERTA
							|| o2.getCodigo() == CODIGO_GRUPO_TODOS) {
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

	/**
	 * Gera os grupos e seus respctivos sub-grupos apartir da lista fornecida pelo
	 * {@link GrupoSubGrupoProduto#recuperarDisponiveisParaVenda(GenericoDAO, int, int, boolean)}
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param precoBase
	 * @param permiteProduoSemEstoque
	 * @return
	 */
	public static List<Grupo> gerarGrupos(GenericoDAO dao, int filialCodigo, int precoBase,
			boolean permiteProduoSemEstoque, boolean criarSubGrupotodos) {

		List<GrupoSubGrupoProduto> grupoProdutos = GrupoSubGrupoProduto.recuperarDisponiveisParaVenda(dao, filialCodigo,
				precoBase, permiteProduoSemEstoque);

		List<Grupo> grupos = new ArrayList<Grupo>();

		if (grupoProdutos != null && grupoProdutos.size() > 0) {

			int grupoCodigo = -1;
			Grupo grupo = null;

			for (GrupoSubGrupoProduto grupoSubGrupoProduto : grupoProdutos) {

				if (grupoCodigo != grupoSubGrupoProduto.getCodigoGrupo()) {

					grupo = new Grupo(grupoSubGrupoProduto);

					grupos.add(grupo);

					if (criarSubGrupotodos) {
						grupo.adicionarSubGrupoTodos(getTextoTodos());
					}

					grupoCodigo = grupo.getCodigo();

					if (grupoSubGrupoProduto.getCodigoSubGrupo() == 0) {

						SubGrupo subGrupo = new SubGrupo(grupoSubGrupoProduto);

						subGrupo.setDescricao(getTextoOutros());
						subGrupo.setCodigo(SubGrupo.CODIGO_SUBGRUPO_OUTROS);

						grupo.addSubGrupo(subGrupo);

						continue;
					}
				}

				grupo.addSubGrupo(new SubGrupo(grupoSubGrupoProduto));

			}
		}
		return grupos;
	}

	@SuppressWarnings("unchecked")
	public void adicionarSubgrupoDestaque(List<? extends CaixaProdutoVisualizavel> caixaProdutoVisualizavels,
			String nomeSubgrupo) {

		if (ListUtil.isValida(caixaProdutoVisualizavels)) {

			if (subGrupoDestaque == null) {

				subGrupoDestaque = new SubGrupo(SubGrupo.CODIGO_SUBGRUPO_DESTAQUE, codigo, nomeSubgrupo, false);

				subGrupos.add(subGrupoDestaque);

			}

			subGrupoDestaque.addProdutos((List<CaixaProdutoVisualizavel>) caixaProdutoVisualizavels);
		}
	}

	@SuppressWarnings("unchecked")
	public void adicionarSubGrupoTodos(List<? extends CaixaProdutoVisualizavel> caixaProdutoVisualizavels,
			String nomeSubgrupo) {

		adicionarSubGrupoTodos(nomeSubgrupo);

		if (ListUtil.isValida(caixaProdutoVisualizavels)) {

			subGrupoTodos.getProdutos().clear();

			subGrupoTodos.addProdutos((List<CaixaProdutoVisualizavel>) caixaProdutoVisualizavels);
		}

	}

	public void adicionarSubGrupoTodos(String nomeSubgrupo) {
		if (!subGrupos.contains(subGrupoTodos)) {

			if (subGrupoTodos == null) {
				subGrupoTodos = new SubGrupo(SubGrupo.CODIGO_SUBGRUPO_TODOS, codigo, nomeSubgrupo, false);
			}

			subGrupos.add(subGrupoTodos);
		}
	}

	public SubGrupo getSubGrupoDestaque() {
		return subGrupoDestaque;
	}

	public SubGrupo getSubGrupoTodos() {
		return subGrupoTodos;
	}

	@Produces
	@Named
	public static String getCodigoGrupoTodos() {
		return Integer.toString(CODIGO_GRUPO_TODOS);
	}

	public boolean isVisualizarExclusiva() {
		return visualizarExclusiva;
	}

	public void setVisualizarExclusiva(boolean visualizarExclusiva) {
		this.visualizarExclusiva = visualizarExclusiva;
	}

	public static String getTextoTodos() {
		String todos = Propriedade.getMensagemUtilizadoSessao("texto.todos");

		if (StringUtil.isValida(todos)) {
			return todos;
		}

		return "Todos";
	}

	public static String getTextoOutros() {

		String outros = Propriedade.getMensagemUtilizadoSessao("texto.outros");

		if (StringUtil.isValida(outros)) {
			return outros;
		}

		return "Outros";

	}
}