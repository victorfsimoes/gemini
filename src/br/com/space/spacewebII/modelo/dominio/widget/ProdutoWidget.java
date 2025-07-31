package br.com.space.spacewebII.modelo.dominio.widget;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.negocio.modelo.dominio.produto.PrecoVenda;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceId;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.dao.db.Dictionary;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoMidia;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;

@Entity
@SpaceTable(name = ProdutoWidget.NOME_TABELA)
@Table(name = ProdutoWidget.NOME_TABELA)
@XmlRootElement
public class ProdutoWidget implements IPersistent, Serializable {

	private static br.com.space.api.spa.model.dao.db.Table table;
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public static final String NOME_TABELA = "produto";
	public static final String COLUNA_DESCRICAO = "pro_desc";
	public static final String COLUNA_GRUPOPRODUTO_CODIGO = "pro_grpcodigo";
	public static final String COLUNA_CODIGO = "pro_codigo";
	public static final String COLUNA_ATIVO = "pro_ativo";
	public static final String COLUNA_PRODUTOSIMILAR_CODIGO = "pro_sprcodigo";

	@Id
	@Column(name = COLUNA_CODIGO)
	@SpaceId
	@SpaceColumn(name = "pro_codigo")
	private int codigo;

	@Column(name = COLUNA_DESCRICAO)
	@SpaceColumn(name = COLUNA_DESCRICAO)
	private String descricao;

	@Column(name = "pro_pbruto")
	@SpaceColumn(name = "pro_pbruto")
	private Double pesoBruto;

	@Transient
	private GrupoProdutoWidget grupoProdutoWidget;

	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "pro_sgpcodigo", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private SubGrupoProdutoWidget subgrupoProdutoWidget;

	@Transient
	private ProdutoUnidade unidadeVenda = null;

	@Transient
	private boolean emCampanha = false;

	@Transient
	private boolean emOferta = false;

	@Transient
	private boolean emPromocao = false;

	@Transient
	private PrecoVenda precoVenda = null;

	@Transient
	private double quantidadeEstoque = 0;

	@Transient
	private ProdutoMidia imagemCapa = null;

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

	public Double getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Double pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public GrupoProdutoWidget getGrupoProdutoWidget() {
		return grupoProdutoWidget;
	}

	public void setGrupoProdutoWidget(GrupoProdutoWidget grupoProdutoWidget) {
		this.grupoProdutoWidget = grupoProdutoWidget;
	}

	public SubGrupoProdutoWidget getSubgrupoProdutoWidget() {
		return subgrupoProdutoWidget;
	}

	public void setSubgrupoProdutoWidget(
			SubGrupoProdutoWidget subgrupoProdutoWidget) {
		this.subgrupoProdutoWidget = subgrupoProdutoWidget;
	}

	public ProdutoUnidade getUnidadeVenda() {
		return unidadeVenda;
	}

	public void setUnidadeVenda(ProdutoUnidade unidadeVenda) {
		this.unidadeVenda = unidadeVenda;
	}

	public boolean isEmCampanha() {
		return emCampanha;
	}

	public void setEmCampanha(boolean emCampanha) {
		this.emCampanha = emCampanha;
	}

	public boolean isEmOferta() {
		return emOferta;
	}

	public void setEmOferta(boolean emOferta) {
		this.emOferta = emOferta;
	}

	public boolean isEmPromocao() {
		return emPromocao;
	}

	public void setEmPromocao(boolean emPromocao) {
		this.emPromocao = emPromocao;
	}

	public PrecoVenda getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(PrecoVenda precoVenda) {
		this.precoVenda = precoVenda;
	}

	public double getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(double quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public ProdutoMidia getImagemCapa() {
		return imagemCapa;
	}

	public void setImagemCapa(ProdutoMidia imagemCapa) {
		this.imagemCapa = imagemCapa;
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

	public static List<ProdutoWidget> recuperarProdutosVendaUsandoResultSet(
			GenericoDAO dao, int filialCodigo, int precoBase) throws Exception {

		if (ProdutoWidget.table == null)
			ProdutoWidget.table = Dictionary.getTable(ProdutoWidget.class);

		String colunas = "";
		for (String coluna : ProdutoWidget.table.getColumnsNames()) {
			colunas += (colunas.length() > 0 ? "," : "") + coluna;
		}

		String select = "select pro_codigo, pro_desc, pro_grpcodigo, pro_sgpcodigo, pro_pbruto "
				+ " from produto "
				+ " inner join produtofilial"
				+ " ON pro_codigo = pfi_procodigo and"
				+ " pfi_filcodigo = "
				+ filialCodigo
				+ " and pfi_libvenda = 1 "
				+ " and pfi_libvendaweb = 1 "
				+ " and pro_ativo = 1 "
				+ (precoBase > 0 ? " inner join produtopreco on pro_codigo = ppr_procodigo"
						+ " and ppr_filcodigo = pfi_filcodigo and ppr_prbcodigo = "
						+ precoBase + " and ppr_precovenda > 0"
						: "");

		return dao.list(ProdutoWidget.class, select, null);
	}
}
