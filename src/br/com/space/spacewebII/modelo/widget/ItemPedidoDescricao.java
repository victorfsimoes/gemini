package br.com.space.spacewebII.modelo.widget;

import java.sql.SQLException;
import java.util.List;

import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IPedido;
import br.com.space.api.negocio.modelo.dominio.IPrecoVenda;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.dao.db.Table;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoDescricao;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;

@SpaceTable(name = "itenspedido")
public class ItemPedidoDescricao implements IItemPedidoDescricao, IPersistent {

	@SpaceColumn(name = "pro_desc")
	private String produtoDescricao = null;

	@SpaceColumn(name = "pro_descdet")
	private String produtoDescricaoDetalhada = null;

	@SpaceColumn(name = "ipv_unpunidade")
	private String unidade = null;

	@SpaceColumn(name = "ipv_unpquant")
	private int quantidadeUnidade = 0;

	@SpaceColumn(name = "ipv_quantidade")
	private double quantidade = 0;

	@SpaceColumn(name = "ipv_precovenda")
	private double precoVenda = 0;

	@Override
	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	@Override
	public int getQuantidadeUnidade() {
		return quantidadeUnidade;
	}

	public void setQuantidadeUnidade(int quantidadeUnidade) {
		this.quantidadeUnidade = quantidadeUnidade;
	}

	@Override
	public String getProdutoDescricao() {

		if (StringUtil.isValida(produtoDescricaoDetalhada)) {
			return produtoDescricaoDetalhada;
		}

		return produtoDescricao;
	}

	@Override
	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	@Override
	public double getPrecoTotal() {
		return precoVenda * quantidade;
	}

	@Override
	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public String getProdutoDescricaoDetalhada() {
		return produtoDescricaoDetalhada;
	}

	public void setProdutoDescricaoDetalhada(String produtoDescricaoDetalhada) {
		this.produtoDescricaoDetalhada = produtoDescricaoDetalhada;
	}

	public void setProdutoDescricao(String produtoDescricao) {
		this.produtoDescricao = produtoDescricao;
	}

	@Override
	public Table getTable() {
		return null;
	}

	@Override
	public void setTable(Table arg0) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static List<ItemPedidoDescricao> recuperar(GenericoDAO dao,
			IPedidoWeb pedido) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {

		String select = "select pro_desc, pro_descdet, ipv_unpunidade, ipv_unpquant, ipv_quantidade, ipv_precovenda"
				+ " from itenspedido"
				+ " inner join produto on ipv_procodigo = pro_codigo"
				+ " where ipv_filcodigo = "
				+ pedido.getFilialCodigo()
				+ " and ipv_spvcodigo = '"
				+ pedido.getSerieCodigo()
				+ "' and ipv_pednumero = " + pedido.getNumero();

		return dao.list(ItemPedidoDescricao.class, select, null);
	}

}
