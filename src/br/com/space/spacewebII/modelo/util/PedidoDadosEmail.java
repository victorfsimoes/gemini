package br.com.space.spacewebII.modelo.util;

import java.sql.SQLException;
import java.util.List;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;

@SpaceTable(name = "pedidos")
public class PedidoDadosEmail implements IPersistent {

	@SpaceColumn(name = "cli_loginweb")
	private String clienteEmail = null;

	@SpaceColumn(name = "pes_razao")
	private String pessoaRazao = null;

	@SpaceColumn(name = "cpg_desc")
	private String condicaoPagamentoDescricao = null;

	@SpaceColumn(name = "fpg_desc")
	private String formaPagamentoDescricao = null;

	public PedidoDadosEmail() {
	}

	public String getClienteEmail() {
		return clienteEmail;
	}

	public void setClienteEmail(String clienteEmail) {
		this.clienteEmail = clienteEmail;
	}

	public String getPessoaRazao() {
		return pessoaRazao;
	}

	public void setPessoaRazao(String pessoaRazao) {
		this.pessoaRazao = pessoaRazao;
	}

	public String getCondicaoPagamentoDescricao() {
		return condicaoPagamentoDescricao;
	}

	public void setCondicaoPagamentoDescricao(String condicaoPagamentoDescricao) {
		this.condicaoPagamentoDescricao = condicaoPagamentoDescricao;
	}

	public String getFormaPagamentoDescricao() {
		return formaPagamentoDescricao;
	}

	public void setFormaPagamentoDescricao(String formaPagamentoDescricao) {
		this.formaPagamentoDescricao = formaPagamentoDescricao;
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
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	public static PedidoDadosEmail recuperarDadosEmailPedido(GenericoDAO dao,
			Pedido pedido) throws ClassNotFoundException,
			InstantiationException, IllegalAccessException, SQLException {

		String select = "select cli_loginweb, pes_razao, cpg_desc, fpg_desc"
				+ " from pedidos"
				+ " inner join pessoa on ped_pescodigo = pes_codigo"
				+ " inner join cliente on ped_pescodigo = cli_pescodigo"
				+ " inner join formapagto on ped_fpgcodigo = fpg_codigo"
				+ " inner join condpagto on ped_cpgcodigo = cpg_codigo"
				+ " where ped_filcodigo = " + pedido.getFilialCodigo()
				+ " and ped_spvcodigo = '" + pedido.getSerieCodigo()
				+ "' and ped_numero = " + pedido.getNumero();

		List<PedidoDadosEmail> dadosEmails = dao.list(PedidoDadosEmail.class,
				select, null);

		if (ListUtil.isValida(dadosEmails)) {
			return dadosEmails.get(0);
		}

		return null;

	}
}
