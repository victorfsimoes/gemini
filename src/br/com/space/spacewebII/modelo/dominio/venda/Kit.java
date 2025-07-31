/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.impl.KitImpl;
import br.com.space.api.negocio.modelo.negocio.estoque.FlagTipoEstoque;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.controle.estoque.Estoque;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoMidia;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.Fabrica;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;
import br.com.space.spacewebII.modelo.widget.Grupo;
import br.com.space.spacewebII.modelo.widget.GrupoWidget;
import br.com.space.spacewebII.modelo.widget.SubGrupo;
import br.com.space.spacewebII.modelo.widget.SubGrupoWidget;

/**
 * 
 * @author Desnvolvimnto
 */
@SuppressWarnings("unchecked")
@Entity
@Table(name = "kit")
@XmlRootElement
public class Kit extends KitImpl implements Serializable, IPersistent, CaixaProdutoVisualizavel {

	private static final long serialVersionUID = 1L;
	private static Grupo grupo;
	private static SubGrupo subGrupo;

	@Id
	@Basic(optional = false)
	@Column(name = "kit_codigo")
	private int codigo;

	@Basic(optional = false)
	@Column(name = "kit_desc")
	private String descricao;

	@Column(name = "kit_descdet")
	private String descricaoDetalhada;

	@Column(name = "kit_dtcad")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(name = "kit_dtultatu")
	@Temporal(TemporalType.DATE)
	private Date dataUltimaAtualizacao;

	@Column(name = "kit_listaped")
	private Integer flagListaPedidos;

	@Column(name = "kit_valortab")
	private double valorTabela;

	@Column(name = "kit_valor")
	private double valor;

	@Column(name = "kit_valoremb")
	private Double valorEmbalagem;

	@Column(name = "kit_filcodigo")
	private Integer filialCodigo;

	@Transient
	private double estoqueQuantidade = 0;

	@Transient
	private double peso = 0;

	@Transient
	private List<ItemKit> itemKits = null;

	@Transient
	private List<Produto> produtos = null;

	@Transient
	private ProdutoMidia imagemCapa = null;

	@Transient
	private double quantidadeCompraRapida = 1;

	@Transient
	private boolean emBalanco = false;

	/*
	 * @Transient private SubGrupo subGrupoWidget;
	 * 
	 * @Transient private Grupo grupoWidget = null;
	 */

	public Kit() {
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

	public String getDescricaoDetalhada() {
		return descricaoDetalhada;
	}

	public void setDescricaoDetalhada(String descricaoDetalhada) {
		this.descricaoDetalhada = descricaoDetalhada;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataUltimaAtualizacao() {
		return dataUltimaAtualizacao;
	}

	public void setDataUltimaAtualizacao(Date dataUltimaAtualizacao) {
		this.dataUltimaAtualizacao = dataUltimaAtualizacao;
	}

	@Override
	public double getValorTabela() {
		return valorTabela;
	}

	@Override
	public void setValorTabela(double valorTabela) {
		this.valorTabela = valorTabela;

		if (precoVenda != null) {
			precoVenda.setPrecoTabela(valorTabela);
		}
	}

	@Override
	public double getValor() {
		return valor;
	}

	@Override
	public void setValor(double valor) {
		this.valor = valor;

		if (precoVenda != null) {
			precoVenda.setPrecoVenda(valor);
		}
	}

	public Double getValorEmbalagem() {
		return valorEmbalagem;
	}

	public void setValorEmbalagem(Double valorEmbalagem) {
		this.valorEmbalagem = valorEmbalagem;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
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

	@Override
	public String getUnidadeDescricao() {
		return null;
	}

	@Override
	public boolean getEmPromocao() {
		return false;
	}

	@Override
	public boolean getEmCampanha() {
		return false;
	}

	@Override
	public boolean getEmOferta() {
		return false;
	}

	@Override
	public List<Produto> getProdutosKit() {

		if (itemKits != null && itemKits.size() > 0 && produtos == null) {
			produtos = new ArrayList<Produto>();

			for (ItemKit itemKit : itemKits) {
				produtos.add(itemKit.getProduto());
			}
		}

		return produtos;
	}

	@Override
	public double getEstoqueVisualizacao() {
		return getEstoque();
	}

	@Override
	public double getEstoque() {
		return estoqueQuantidade;
	}

	@Override
	public ProdutoMidia getImagemCapa() {
		return imagemCapa;
	}

	@Override
	public void setImagemCapa(ProdutoMidia imagemCapa) {
		this.imagemCapa = imagemCapa;
	}

	public static List<Kit> recuperarKitParaVenda(GenericoDAO dao) {
		return recuperarKitParaVenda(dao, null, null);
	}

	public static Kit recuperarKitParaVenda(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(Kit.class, "kit_codigo = " + codigo, null);
	}

	public static List<Kit> recuperarKitParaVenda(GenericoDAO dao, String descricao, Integer codigo) {

		List<String> whereFragmentos = new ArrayList<String>();

		if (descricao != null && !descricao.trim().isEmpty()) {
			whereFragmentos.add("(kit_desc like '%" + descricao + "%' or kit_descdet like '%" + descricao + "%')");
		}

		if (codigo != null && codigo > 0) {
			whereFragmentos.add("kit_codigo = " + codigo);
		}

		String where = GenericoDAO.criarWhere(whereFragmentos);

		return dao.list(Kit.class, where, null, null, null);
	}

	public static List<Kit> recuperarKitParaVenda(GenericoDAO dao, String filtroCodigoOuDescricao) {

		String filtoDesc = null;
		Integer filtoCodigo = null;

		if (filtroCodigoOuDescricao != null && !filtroCodigoOuDescricao.trim().isEmpty()) {

			boolean pesquisaPorCodigo = filtroCodigoOuDescricao.matches("[0-9]+");

			if (pesquisaPorCodigo) {
				filtoCodigo = Integer.parseInt(filtroCodigoOuDescricao);
			} else {
				filtoDesc = filtroCodigoOuDescricao;
			}
		}

		return recuperarKitParaVenda(dao, filtoDesc, filtoCodigo);
	}

	public static long count(GenericoDAO dao) {
		return dao.count("kit", null);
	}

	public static Kit recuperar(GenericoDAO dao, Integer codigo) {

		String where = null;

		if (codigo != null) {

			where = "kit_codigo = " + codigo.toString();
		}

		return dao.uniqueResult(Kit.class, where, null);
	}

	@Override
	public List<ItemKit> getItemKits() {
		return itemKits;
	}

	@Override
	public List<ItemKit> getItemKitsEstoque() {
		return getItemKits();
	}

	public void setItemKits(List<ItemKit> itemKits) {
		this.itemKits = itemKits;
	}

	@Override
	public boolean eKit() {
		return true;
	}

	@Override
	public boolean eProduto() {
		return false;
	}

	@Override
	public GrupoWidget getGrupoWidget() {
		return getGrupoKit();
	}

	/*
	 * public void setGrupoWidget(Grupo grupoWidget) { this.grupoWidget =
	 * grupoWidget; }
	 */

	@Override
	public SubGrupoWidget getSubGrupoWidget() {
		return getSubGrupoKitTodos();
	}

	/*
	 * public void setSubGrupoWidget(SubGrupo subGrupoWidget) {
	 * this.subGrupoWidget = subGrupoWidget; }
	 */

	@Override
	public String getPrecoVendaVisualizacao() {

		if (getPrecoVenda() != null) {
			return Conversao.formatarValor2(getPrecoVenda().getPrecoVenda());
		}

		return Conversao.formatarValor2(valor);
	}

	@Override
	public String getPrecoTabelaVisualizacao() {

		if (getPrecoVenda() != null) {
			return Conversao.formatarValor2(getPrecoVenda().getPrecoTabela());
		}
		return Conversao.formatarValor2(valorTabela);
	}

	@Override
	public String getPrecoUnitarioVisualizacao() {
		return getPrecoVendaVisualizacao();
	}

	/*
	 * @Override public double getPeso() {
	 * 
	 * this.peso = 0;
	 * 
	 * if (itemKits != null) { for (ItemKit item : itemKits) {
	 * 
	 * double pesoItem = item.getPeso();
	 * 
	 * if (pesoItem > 0) { this.peso += pesoItem; } } }
	 * 
	 * return peso; }
	 */

	public static Kit recuperarKitComItens(GenericoDAO dao, int codigo) {
		Kit kit = dao.uniqueResult(Kit.class, "kit_codigo = " + codigo, null);

		List<ItemKit> itensKit = ItemKit.recuperar(dao, String.valueOf(codigo), null);
		kit.setItemKits(itensKit);

		return kit;
	}

	@Override
	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, boolean atualizarFlags,
			boolean forcarRefreshPrecos) throws Exception {

		preencherAtributos(dao, gerentePedido, null, atualizarFlags, forcarRefreshPrecos);
	}

	@Override
	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, ProdutoUnidade produtoUnidade,
			boolean atualizarFlags, boolean forcarRefreshPrecos) throws Exception {

		preencherAtributos(dao, gerentePedido, produtoUnidade, atualizarFlags, forcarRefreshPrecos, true);
	}

	@Override
	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, ProdutoUnidade produtoUnidade,
			boolean atualizarFlags, boolean forcarRefreshPrecos, boolean recuperarEstoque) throws Exception {

		if (itemKits == null || itemKits.size() == 0) {
			this.setItemKits(ItemKit.recuperar(dao, Integer.toString(codigo), null));
		}

		int filialCodigo = gerentePedido.getFilial().getCodigo();

		gerentePedido.getPrecificacao().obterPrecoVendaSugeridoKit(this);

		if (recuperarEstoque) {
			estoqueQuantidade = gerentePedido.getEstoque().recuperarEstoqueDisponivelKit(filialCodigo, this,
					FlagTipoEstoque.DisponivelVendaInterno);
		}

		if (imagemCapa == null) {
			imagemCapa = ProdutoMidia.recuperarImagemCapaKit(dao, codigo);
			if (imagemCapa != null) {
				String endImagem = Fabrica.gerarEnderecoImagemSemPasta(imagemCapa.getEnderecoMidia());

				imagemCapa.setEnderecoMidia(endImagem);
			}

			atulizarEmbalanco(filialCodigo, dao);

			List<Produto> produtos = getProdutosKit();

			List<ProdutoMidia> produtoMidias = ProdutoMidia.recuperarImagensAtivasCapa(dao, produtos);

			boolean possueMidias = ListUtil.isValida(produtoMidias);

			if (possueMidias) {
				Collections.sort(produtoMidias);
			}

			for (Produto produto : produtos) {

				if (possueMidias) {
					Produto.montarImagemCapaProduto(produto, produtoMidias);
				}

				produto.setQuantidadeEstoque(
						produto.recuperarEstoqueDisponivel(gerentePedido.getEstoque(), filialCodigo));
			}
		}
	}

	/*
	 * public void montarGrupoESubgrupo() {
	 * 
	 * setGrupoWidget(getGrupoKit()); setSubGrupoWidget(getSubGrupoKitTodos());
	 * }
	 */

	public static Grupo getGrupoKit() {

		if (grupo == null) {
			String nome = Propriedade.getMensagemUtilizadoSessao("texto.kit");

			if (!StringUtil.isValida(nome)) {
				nome = "Kit";
			}

			grupo = getNewGrupoKit(nome);

		}

		return grupo;
	}

	public static Grupo getNewGrupoKit(String nomeGrupo) {
		return grupo = new Grupo(Grupo.CODIGO_GRUPO_KIT, nomeGrupo, true);
	}

	public static SubGrupo getNewSubGrupoKitTodos(String nomeSubGrupo) {
		return subGrupo = new SubGrupo(SubGrupo.CODIGO_SUBGRUPO_TODOS, Grupo.CODIGO_GRUPO_KIT, nomeSubGrupo, false);
	}

	public static SubGrupo getSubGrupoKitTodos() {

		if (subGrupo == null) {
			subGrupo = getNewSubGrupoKitTodos(Grupo.getTextoTodos());
		}

		return subGrupo;
	}

	@Override
	public Integer getCategoriaCodigo() {
		return null;
	}

	@Override
	public Integer getSubCategoriaCodigo() {
		return null;
	}

	@Override
	public boolean isPermiteVenderSemEstoque() {
		return false;
	}

	@Override
	public String getDescricaoVisualizacao() {

		if (StringUtil.isValida(descricaoDetalhada)) {

			return descricaoDetalhada;

		} else {
			return descricao;
		}
	}

	@Override
	public boolean exibirPrecoUnitario() {
		return false;
	}

	@Override
	public void setQuantidadeCompraRapida(double quantidadeCompraRapida) {
		this.quantidadeCompraRapida = quantidadeCompraRapida;
	}

	@Override
	public double getQuantidadeCompraRapida() {
		return quantidadeCompraRapida;
	}

	@Override
	public boolean isExibePrecoVisitante() {
		return false;
	}

	@Override
	public boolean isEmBalanco() {
		return emBalanco;
	}

	@Override
	public boolean atulizarEmbalanco(int codigoFilial, GenericoDAO dao) {
		emBalanco = Estoque.algumProdutoEmBalanco(getProdutosKit(), codigoFilial, dao);
		return emBalanco;
	}

	@Override
	public List<ProdutoUnidade> getUnidades() {
		return null;
	}

}
