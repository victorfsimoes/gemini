package br.com.space.spacewebII.modelo.dominio.venda;

import java.util.List;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

@SpaceTable(name = "itensPedidos")
public class ItemPedidoPrecoNegociado implements IPersistent {

	@SpaceColumn(name = "quantidade")
	private double quantidade;

	@SpaceColumn(name = "precoVenda")
	private double precoVenda;

	@SpaceColumn(name = "unidadeDescricao")
	private String unidadeDescricao = null;

	@SpaceColumn(name = "unidade")
	private String unidade = null;

	@SpaceColumn(name = "quantidadeUnidade")
	private double quantidadeUnidade;

	public ItemPedidoPrecoNegociado() {
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public double getPrecoVenda() {
		return precoVenda;
	}

	public void setPrecoVenda(double precoVenda) {
		this.precoVenda = precoVenda;
	}

	public String getUnidadeDescricao() {
		return unidadeDescricao;
	}

	public void setUnidadeDescricao(String unidadeDescricao) {
		this.unidadeDescricao = unidadeDescricao;
	}

	public String getUnidade() {
		return unidade;
	}

	public void setUnidade(String unidade) {
		this.unidade = unidade;
	}

	public double getQuantidadeUnidade() {
		return quantidadeUnidade;
	}

	public void setQuantidadeUnidade(double quantidadeUnidade) {
		this.quantidadeUnidade = quantidadeUnidade;
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

	public String getMensagemUsuario(Propriedade propriedade, String quebraLinha) {

		StringBuffer buffer = new StringBuffer();

		buffer.append(propriedade.getMensagem("texto.preco",
				Conversao.formatarValor2(precoVenda)));

		buffer.append(quebraLinha);

		buffer.append(propriedade.getMensagem("texto.quantidade") + ": "
				+ quantidade);

		buffer.append(quebraLinha);

		buffer.append(propriedade.getMensagem("texto.unidade") + " : ");

		if (StringUtil.isValida(unidadeDescricao)) {

			buffer.append(unidadeDescricao);

		} else {
			buffer.append(unidade + "/" + quantidadeUnidade);
		}

		return buffer.toString();
	}

	public static ItemPedidoPrecoNegociado ultimoPrecoNegociado(
			GenericoDAO dao, int filialCodigo, int produtoCodigo,
			int clienteCodigo) throws Exception {

		String select = "select " + getCamposSelectsItemPedidoPrecoNegociado();

		select += " from "
				+ getFromItemPedidoPrecoNegociado(filialCodigo, produtoCodigo,
						clienteCodigo)
				+ " order by ped_dtemissao desc, ped_numero desc limit 1";

		List<ItemPedidoPrecoNegociado> negociados = dao.list(
				ItemPedidoPrecoNegociado.class, select, null);

		return ListUtil.isValida(negociados) ? negociados.get(0) : null;
	}

	public static String getCamposSelectsItemPedidoPrecoNegociado() {
		return "ipv_quantidade as quantidade, ipv_precovenda as precoVenda, unp_descemb as unidadeDescricao, ipv_unpunidade as unidade, ipv_unpquant as quantidadeUnidade";
	}

	public static String getFromItemPedidoPrecoNegociado(int filialCodigo,
			int produtoCodigo, int clienteCodigo) {
		return "pedidos inner join itenspedido on ipv_filcodigo = ped_filcodigo and ipv_spvcodigo = ped_spvcodigo"
				+ " and ipv_pednumero = ped_numero and ped_filcodigo = "
				+ filialCodigo
				+ " and ped_pescodigo = "
				+ clienteCodigo
				+ " and ipv_procodigo = "
				+ produtoCodigo
				+ " and ped_stpcodigo in (2,6) left join unidadePro on unp_procodigo = ipv_procodigo"
				+ " and unp_unidade = ipv_unpunidade and unp_quantidade = ipv_unpquant";
	}

}
