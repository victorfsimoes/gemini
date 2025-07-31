/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.negocio.modelo.dominio.impl.ItemKitImpl;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.widget.PrecoVisualizavel;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "itenskit")
@XmlRootElement
public class ItemKit extends ItemKitImpl implements Serializable, IPersistent,
		PrecoVisualizavel {

	private static final long serialVersionUID = 1L;
	private static Comparator<ItemKit> comparadorProduto = null;

	@EmbeddedId
	protected ItemKitPK itensKitPK;

	@Basic(optional = false)
	@Column(name = "ikt_quantidade")
	private double quantidade;

	@Transient
	private double quantidadeVisualizacao = 0;

	@Column(name = "ikt_precotab")
	private Double precoTabela;

	@Column(name = "ikt_preco")
	private Double preco;

	@Column(name = "ikt_persimilar")
	private Integer permiteLancamentoSimilar;

	@Column(name = "ikt_embalagem")
	private Integer flagEmbalagem;

	@Column(name = "ikt_filcodigo")
	private Integer filialCodigo;

	@Column(name = "ikt_pficusto1")
	private Double custo1;

	@Column(name = "ikt_pficusto2")
	private Double custo2;

	@Column(name = "ikt_pficusto3")
	private Double custo3;

	@Column(name = "ikt_pficusto4")
	private Double custo4;

	@Column(name = "ikt_pficusto5")
	private Double custo5;

	@Column(name = "ikt_pficusto6")
	private Double custo6;

	@Column(name = "ikt_pficusto7")
	private Double custo7;

	@Column(name = "ikt_pficusto8")
	private Double custo8;

	@Column(name = "ikt_pficusto9")
	private Double custo9;

	@Column(name = "ikt_pficusto10")
	private Double custo10;

	@ManyToOne(optional = false)
	@Fetch(FetchMode.JOIN)
	@JoinColumns({
			@JoinColumn(name = "ikt_procodigo", referencedColumnName = ProdutoUnidade.COLUNA_PRODUTO_CODIGO, insertable = false, updatable = false),
			@JoinColumn(name = "ikt_unpquant", referencedColumnName = ProdutoUnidade.COLUNA_QUANTIDADE, insertable = false, updatable = false),
			@JoinColumn(name = "ikt_unpunidade", referencedColumnName = ProdutoUnidade.COLUNA_UNIDADE, insertable = false, updatable = false) })
	@NotFound(action = NotFoundAction.IGNORE)
	private ProdutoUnidade produtoUnidade = null;

	@ManyToOne(optional = false)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "ikt_procodigo", referencedColumnName = Produto.COLUNA_CODIGO, insertable = false, updatable = false)
	private Produto produto;

	public ItemKit() {
	}

	public ItemKitPK getItensKitPK() {
		return itensKitPK;
	}

	public void setItensKitPK(ItemKitPK itensKitPK) {
		this.itensKitPK = itensKitPK;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
		this.quantidadeVisualizacao = quantidade;
	}

	public double getQuantidadeVisualizacao() {

		if (quantidadeVisualizacao == 0) {
			return quantidade;
		}

		return quantidadeVisualizacao;
	}

	public void atualizarQuantidadeVisualizacao(double quantidadeDoKit) {
		this.quantidadeVisualizacao = quantidade * quantidadeDoKit;
	}

	public Double getPrecoTabela() {
		return precoTabela;
	}

	public void setPrecoTabela(Double precoTabela) {
		this.precoTabela = precoTabela;

		if (precoVenda != null) {
			precoVenda.setPrecoTabela(precoTabela);
		}
	}

	public Double getPrecoOriginal() {
		return precoOriginal;
	}

	public Double getPreco() {
		return preco;
	}

	public void setPreco(Double preco) {
		this.preco = preco;

		if (precoVenda != null) {
			precoVenda.setPrecoVenda(preco);
		}
	}

	public Integer getPermiteLancamentoSimilar() {
		return permiteLancamentoSimilar;
	}

	public void setPermiteLancamentoSimilar(Integer permiteLancamentoSimilar) {
		this.permiteLancamentoSimilar = permiteLancamentoSimilar;
	}

	public Integer getFlagEmbalagem() {
		return flagEmbalagem;
	}

	public void setFlagEmbalagem(Integer flagEmbalagem) {
		this.flagEmbalagem = flagEmbalagem;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public double getCusto1() {
		if (custo1 == null) {
			return 0;
		}
		return custo1;
	}

	public void setCusto1(Double custo1) {
		this.custo1 = custo1;
	}

	public double getCusto2() {
		if (custo2 == null) {
			return 0;
		}
		return custo2;
	}

	public void setCusto2(Double custo2) {
		this.custo2 = custo2;
	}

	public double getCusto3() {
		if (custo3 == null) {
			return 0;
		}
		return custo3;
	}

	public void setCusto3(Double custo3) {
		this.custo3 = custo3;
	}

	public double getCusto4() {
		if (custo4 == null) {
			return 0;
		}
		return custo4;
	}

	public void setCusto4(Double custo4) {
		this.custo4 = custo4;
	}

	public double getCusto5() {
		if (custo5 == null) {
			return 0;
		}
		return custo5;
	}

	public void setCusto5(Double custo5) {
		this.custo5 = custo5;
	}

	public double getCusto6() {
		if (custo6 == null) {
			return 0;
		}
		return custo6;
	}

	public void setCusto6(Double custo6) {
		this.custo6 = custo6;
	}

	public double getCusto7() {
		if (custo7 == null) {
			return 0;
		}
		return custo7;
	}

	public void setCusto7(Double custo7) {
		this.custo7 = custo7;
	}

	public double getCusto8() {
		if (custo8 == null) {
			return 0;
		}
		return custo8;
	}

	public void setCusto8(Double custo8) {
		this.custo8 = custo8;
	}

	public double getCusto9() {
		if (custo9 == null) {
			return 0;
		}
		return custo9;
	}

	public void setCusto9(Double custo9) {
		this.custo9 = custo9;
	}

	public double getCusto10() {
		if (custo10 == null) {
			return 0;
		}
		return custo10;
	}

	public void setCusto10(Double custo10) {
		this.custo10 = custo10;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProduto(Produto produto) {
		this.produto = produto;
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

	public ProdutoUnidade getProdutoUnidade() {
		return produtoUnidade;
	}

	public void setProdutoUnidade(ProdutoUnidade produtoUnidade) {
		this.produtoUnidade = produtoUnidade;
	}

	public static List<ItemKit> recuperar(GenericoDAO dao, String kitNumero,
			String produtoCodigo) {

		List<String> whereFragmentos = new ArrayList<String>();

		if (kitNumero != null && !kitNumero.trim().isEmpty()) {
			whereFragmentos.add("ikt_kitcodigo = " + kitNumero);
		}

		if (produtoCodigo != null && !produtoCodigo.trim().isEmpty()) {
			whereFragmentos.add("ikt_procodigo = " + produtoCodigo);
		}

		String where = GenericoDAO.criarWhere(whereFragmentos);

		return dao.list(ItemKit.class, where, null, null, null);

	}

	public static Comparator<ItemKit> getComparadorProduto() {

		if (comparadorProduto == null) {
			comparadorProduto = new Comparator<ItemKit>() {

				@Override
				public int compare(ItemKit o1, ItemKit o2) {

					if (o1.getItensKitPK() == null
							|| o2.getItensKitPK() == null) {
						return -1;

					}

					return Integer.compare(o1.getItensKitPK()
							.getProdutoCodigo(), o2.getItensKitPK()
							.getProdutoCodigo());

				}
			};

		}

		return comparadorProduto;
	}

	@Override
	public int getKitCodigo() {

		if (itensKitPK != null) {
			return itensKitPK.getKitCodigo();
		}

		return 0;
	}

	@Override
	public int getProdutoCodigo() {

		if (itensKitPK != null) {
			return itensKitPK.getProdutoCodigo();
		}

		return 0;
	}

	@Override
	public String getUnidade() {
		if (itensKitPK != null) {
			return itensKitPK.getUnidade();
		}
		return null;
	}

	@Override
	public int getQuantidadeUnidade() {
		if (itensKitPK != null) {
			return itensKitPK.getQuantidadeUnidade();
		}
		return 0;
	}

	@Override
	public String getPrecoVendaVisualizacao() {

		if (getPrecoVenda() != null) {
			return Conversao.formatarValor2(getPrecoVenda().getPrecoVenda());
		}

		return null;
	}

	@Override
	public String getPrecoTabelaVisualizacao() {

		if (getPrecoVenda() != null) {
			return Conversao.formatarValor2(getPrecoVenda().getPrecoTabela());
		}

		return null;
	}

	public String getPrecoTotalVisualizacao() {

		if (getPrecoVenda() != null) {
			return Conversao.formatarValor2(getPrecoVenda().getPrecoVenda()
					* getQuantidadeVisualizacao());
		}

		return null;
	}

	@Override
	public String getPrecoUnitarioVisualizacao() {
		if (getPrecoVenda() != null && getQuantidadeUnidade() > 0) {

			double valorUnitario = Conversao.arredondar(getPrecoVenda()
					.getPrecoSugerido() / getQuantidadeUnidade(), 3);

			return Conversao.formatarValor2(valorUnitario);
		}

		return null;
	}
}
