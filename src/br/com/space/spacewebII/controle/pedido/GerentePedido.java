package br.com.space.spacewebII.controle.pedido;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.space.api.core.sistema.CodigoSistema;
import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.sistema.NomeSistema;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.ICliente;
import br.com.space.api.negocio.modelo.dominio.ICondicaoPagamento;
import br.com.space.api.negocio.modelo.dominio.IFormaPagamento;
import br.com.space.api.negocio.modelo.dominio.IGrupoVendaNaoPermitidoCliente;
import br.com.space.api.negocio.modelo.dominio.IItemKit;
import br.com.space.api.negocio.modelo.dominio.IItemPedido;
import br.com.space.api.negocio.modelo.dominio.IKitPedido;
import br.com.space.api.negocio.modelo.dominio.INaturezaOperacao;
import br.com.space.api.negocio.modelo.dominio.INaturezaOperacaoFilial;
import br.com.space.api.negocio.modelo.dominio.IOpcaoEspecialFilial;
import br.com.space.api.negocio.modelo.dominio.IPedido;
import br.com.space.api.negocio.modelo.dominio.ITabelaPreco;
import br.com.space.api.negocio.modelo.dominio.IVendedor;
import br.com.space.api.negocio.modelo.dominio.Ikit;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.api.negocio.modelo.dominio.produto.PrecoVenda;
import br.com.space.api.negocio.modelo.excecao.autorizacao.AlertaExcecao;
import br.com.space.api.negocio.modelo.excecao.cliente.ClienteGrupoVendaNaoPermitidoExcecao;
import br.com.space.api.negocio.modelo.excecao.cliente.ClienteNaoEncontradoExcecao;
import br.com.space.api.negocio.modelo.excecao.cliente.ClientePrazoMaximoExcecao;
import br.com.space.api.negocio.modelo.excecao.cliente.ClienteSaldoInsuficienteExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoDuplicadoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoAbaixoCustoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoAbaixoMargemCustoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMaximoClienteExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMaximoCondicaoPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMaximoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMaximoFormaPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMaximoProdutoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMaximoPromocaoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMaximoVendedorExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMinimoClienteExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMinimoCondicaoPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMinimoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMinimoFormaPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMinimoProdutoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMinimoPromocaoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoPrecoMinimoVendedorExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoQuantidadeEstoqueExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoQuantidadeEstoqueMinimoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ItemPedidoQuantidadeEstoqueOutrosExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ProdutoFracionamentoMinimoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ProdutoPrazoMaximoExcecao;
import br.com.space.api.negocio.modelo.excecao.item.ProdutoQuantidadeMinimaExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoAcrescimoMaximoClienteExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoAcrescimoMaximoCondicaoPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoAcrescimoMaximoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoAcrescimoMaximoFormaPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoAcrescimoMaximoVendedorExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoBalancoAtivoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoCondicaoPagamentoClienteExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoCondicaoPagamentoInvalidaExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoCondicaoPagamentoMaximoParcelasExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoCondicaoPagamentoNaoPermitidaFormaPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoCondicaoPagamentoPrazoMaximoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoDescontoMaximoClienteExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoDescontoMaximoCondicaoPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoDescontoMaximoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoDescontoMaximoFormaPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoDescontoMaximoPromocaoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoDescontoMaximoVendedorExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoFormaPagamentoClienteExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoFormaPagamentoInvalidaExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoGrupoVendaExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoGrupoVendaNaoPermiteOpcaoEspecialExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoNaturezaOperacaoInvalidaExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoNumeroMaximoItensExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoOpcaoEspecialClienteExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoOpcaoEspecialNaoPermitidaFormaPagamentoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoPrazoEspecialPedidoAcimadoClienteExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoResultadoMinimoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoResultadoMinimoSemImpostosExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoStatusSessaoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoTabelaPrecoInvalidaExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoValorMinimoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.entrega.EntregaEnderecoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.entrega.EntregaExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.entrega.EntregaFreteMinimoRegiaoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.entrega.EntregaTurnoExcecao;
import br.com.space.api.negocio.modelo.excecao.produto.ProdutoExcecao;
import br.com.space.api.negocio.modelo.excecao.vendedor.VendedorCreditoInsuficienteExcecao;
import br.com.space.api.negocio.modelo.excecao.vendedor.VendedorDebitoInsuficienteExcecao;
import br.com.space.api.negocio.modelo.excecao.vendedor.VendedorNaoEncontradoExcecao;
import br.com.space.api.negocio.modelo.excecao.vendedor.VendedorTipoVendaExcecao;
import br.com.space.api.negocio.modelo.negocio.OperacaoItem;
import br.com.space.api.negocio.modelo.negocio.estoque.Estoque;
import br.com.space.api.negocio.modelo.negocio.estoque.FlagTipoEstoque;
import br.com.space.api.negocio.sistema.IDSistema;
import br.com.space.api.transportadora.TipoEmbalagem;
import br.com.space.spacewebII.controle.Sistema;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.controle.autorizacao.GerenteAutorizacao;
import br.com.space.spacewebII.controle.entrega.GerenteFrete;
import br.com.space.spacewebII.controle.financeiro.GerenteFinanceiro;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.endereco.EnderecoBase;
import br.com.space.spacewebII.modelo.dominio.endereco.Enderecos;
import br.com.space.spacewebII.modelo.dominio.endereco.Regiao;
import br.com.space.spacewebII.modelo.dominio.estoque.NaturezaOperacao;
import br.com.space.spacewebII.modelo.dominio.estoque.NaturezaOperacaoFilial;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoFilial;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.dominio.financeiro.ClassificaDocumento;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.pessoa.CarteiraCliente;
import br.com.space.spacewebII.modelo.dominio.pessoa.CarteiraVendedor;
import br.com.space.spacewebII.modelo.dominio.pessoa.Colaborador;
import br.com.space.spacewebII.modelo.dominio.pessoa.GrupoVendaNaoPermitidoCliente;
import br.com.space.spacewebII.modelo.dominio.sistema.ClassificacaoNaturezaOperacao;
import br.com.space.spacewebII.modelo.dominio.sistema.CodigoFiscal;
import br.com.space.spacewebII.modelo.dominio.sistema.CstCofins;
import br.com.space.spacewebII.modelo.dominio.sistema.CstIpi;
import br.com.space.spacewebII.modelo.dominio.sistema.CstPis;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;
import br.com.space.spacewebII.modelo.dominio.sistema.GrupoIpi;
import br.com.space.spacewebII.modelo.dominio.sistema.GrupoPisCofins;
import br.com.space.spacewebII.modelo.dominio.sistema.GrupoSt;
import br.com.space.spacewebII.modelo.dominio.sistema.GrupoTributacao;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroVenda;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroVenda2;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroVenda3;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.sistema.RegraPisCofins;
import br.com.space.spacewebII.modelo.dominio.sistema.RegraStAvulso;
import br.com.space.spacewebII.modelo.dominio.sistema.RegraTributacao;
import br.com.space.spacewebII.modelo.dominio.sistema.Sessao;
import br.com.space.spacewebII.modelo.dominio.sistema.UsuarioCliente;
import br.com.space.spacewebII.modelo.dominio.venda.ColaboradorPedido;
import br.com.space.spacewebII.modelo.dominio.venda.GrupoComissao;
import br.com.space.spacewebII.modelo.dominio.venda.GrupoComissaoItem;
import br.com.space.spacewebII.modelo.dominio.venda.ItemKit;
import br.com.space.spacewebII.modelo.dominio.venda.ItemPedido;
import br.com.space.spacewebII.modelo.dominio.venda.ItemPedidoAuxiliar;
import br.com.space.spacewebII.modelo.dominio.venda.KitPedido;
import br.com.space.spacewebII.modelo.dominio.venda.LogPedido;
import br.com.space.spacewebII.modelo.dominio.venda.Oferta;
import br.com.space.spacewebII.modelo.dominio.venda.OpcaoEspecial;
import br.com.space.spacewebII.modelo.dominio.venda.OpcaoEspecialFilial;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;
import br.com.space.spacewebII.modelo.dominio.venda.PedidoAuxiliar;
import br.com.space.spacewebII.modelo.dominio.venda.Promocao;
import br.com.space.spacewebII.modelo.dominio.venda.SeriePedido;
import br.com.space.spacewebII.modelo.dominio.venda.StatusPedido;
import br.com.space.spacewebII.modelo.dominio.venda.TabelaPreco;
import br.com.space.spacewebII.modelo.dominio.venda.TipoEntrega;
import br.com.space.spacewebII.modelo.dominio.venda.Transportadora;
import br.com.space.spacewebII.modelo.dominio.venda.Vendedor;
import br.com.space.spacewebII.modelo.excecao.pedido.PedidoCarteiraClienteVendedorExcecao;
import br.com.space.spacewebII.modelo.excecao.pedido.PedidoCarteiraSemClienteExcecao;
import br.com.space.spacewebII.modelo.excecao.produto.ProdutoLiberadoVendaExcecao;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.Fabrica;

/**
 * 
 * @see {@link ComunicaoUsuarioListener Comunicador de mensagens do Gerente
 *      Pedido}
 * 
 * @author Ronie
 * 
 */
public class GerentePedido extends br.com.space.api.negocio.modelo.negocio.GerentePedido<Pedido>
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private Filial filial;

	private Estoque estoque;

	private Parametros parametros;

	private GerenteLogin gerenteLogin = null;

	private List<ClassificaDocumento> classificacoesDocumentoPermitidas;

	private NaturezaOperacaoFilial naturezaOperacaoFilial;

	private GrupoComissao grupoComissaoPesquisa;
	private List<GrupoComissao> gruposComissao;
	private List<GrupoComissaoItem> gruposComissaoItem;
	private List<Enderecos> enderecosCliente;

	private List<GrupoVendaNaoPermitidoCliente> grupoVendaNaoPermitidoCliente;

	private Transportadora transportadora = null;

	/*
	 * Propriedades auxiliares para utilização em alteração de pedido
	 */
	private IPedidoWeb pedidoEmAlteracao;
	private ArrayList<IItemPedido> itensPedidoEmAlteracao = null;

	// StringBuffer mensagensUsuario = null;
	private List<ComunicaoUsuarioListener> comunicaoUsuarioOuvintes = null;

	private ArrayList<Colaborador> colaboradoresPedidos = null;

	private boolean freteCalculadoComSucesso = false;

	private String codigoFiscalOperacao;

	/**
	 * 
	 * @param idSistema
	 * @param usuarioLogado
	 * @param precificacao
	 * @param propriedade
	 * @param parametros
	 * @param estoque
	 * @param dao
	 * @param fabricaGerentePedido
	 * @param gerenteAutorizacao
	 */
	public GerentePedido(IDSistema idSistema, GerenteLogin gerenteLogin, Precificacao precificacao,
			Propriedade propriedade, Parametros parametros, Estoque estoque, GenericoDAO dao,
			FabricaGerentePedido fabricaGerentePedido, GerenteAutorizacao gerenteAutorizacao) {

		super(idSistema, gerenteLogin.getSessaoCodigo(), precificacao, propriedade, parametros, fabricaGerentePedido,
				gerenteAutorizacao);

		this.gerenteLogin = gerenteLogin;
		this.estoque = estoque;
		this.parametros = parametros;

		this.setPromocaoVenda(new br.com.space.spacewebII.controle.pedido.PromocaoVenda(dao, this));

		this.setDao(dao);
		this.gruposComissao = dao.list(GrupoComissao.class);
		this.gruposComissaoItem = dao.list(GrupoComissaoItem.class);
		this.grupoComissaoPesquisa = new GrupoComissao();
		this.comunicaoUsuarioOuvintes = new ArrayList<GerentePedido.ComunicaoUsuarioListener>();

		fabricaGerentePedido.setGerentePedido(this);

		// this.mensagensUsuario = new StringBuffer();
	}

	@Override
	public void adicionarItem(IItemPedido itemPedido)
			throws ProdutoFracionamentoMinimoExcecao, ProdutoQuantidadeMinimaExcecao, ProdutoPrazoMaximoExcecao,
			VendedorDebitoInsuficienteExcecao, VendedorCreditoInsuficienteExcecao, PedidoGrupoVendaExcecao,
			ItemPedidoExcecao, PedidoNaturezaOperacaoInvalidaExcecao, PedidoTabelaPrecoInvalidaExcecao,
			PedidoFormaPagamentoInvalidaExcecao, PedidoOpcaoEspecialNaoPermitidaFormaPagamentoExcecao,
			PedidoCondicaoPagamentoInvalidaExcecao, PedidoCondicaoPagamentoNaoPermitidaFormaPagamentoExcecao,
			VendedorNaoEncontradoExcecao, ClienteNaoEncontradoExcecao, ItemPedidoPrecoMinimoExcecao,
			ItemPedidoPrecoMaximoExcecao, ClienteSaldoInsuficienteExcecao, ClienteGrupoVendaNaoPermitidoExcecao,
			PedidoGrupoVendaNaoPermiteOpcaoEspecialExcecao, ProdutoExcecao, PedidoExcecao,
			ItemPedidoQuantidadeEstoqueExcecao, ItemPedidoQuantidadeEstoqueOutrosExcecao,
			PedidoFormaPagamentoClienteExcecao, PedidoCondicaoPagamentoClienteExcecao, ClientePrazoMaximoExcecao,
			PedidoStatusSessaoExcecao, PedidoBalancoAtivoExcecao, VendedorTipoVendaExcecao,
			PedidoOpcaoEspecialClienteExcecao, ItemPedidoQuantidadeEstoqueMinimoExcecao, SpaceExcecao {
		super.adicionarItem(itemPedido);

		this.getGerenteAutorizacao().persistirAutorizacoesUtilizadas();

		try {

			if (isModificarSaldoOferta(itemPedido)) {

				double quantidade = itemPedido.getQuantidade() * itemPedido.getFatorEstoque();

				atualizarOfertaSaldo(itemPedido.getOfertaNumero(), quantidade);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void alterarItem(IItemPedido itemPedido, OperacaoItem operacao)
			throws ProdutoFracionamentoMinimoExcecao, ProdutoQuantidadeMinimaExcecao, ProdutoPrazoMaximoExcecao,
			VendedorDebitoInsuficienteExcecao, VendedorCreditoInsuficienteExcecao, CloneNotSupportedException,
			PedidoGrupoVendaExcecao, ItemPedidoExcecao, PedidoNaturezaOperacaoInvalidaExcecao,
			PedidoTabelaPrecoInvalidaExcecao, PedidoFormaPagamentoInvalidaExcecao,
			PedidoOpcaoEspecialNaoPermitidaFormaPagamentoExcecao, PedidoCondicaoPagamentoInvalidaExcecao,
			PedidoCondicaoPagamentoNaoPermitidaFormaPagamentoExcecao, VendedorNaoEncontradoExcecao,
			ClienteNaoEncontradoExcecao, ClienteSaldoInsuficienteExcecao, ClienteGrupoVendaNaoPermitidoExcecao,
			PedidoGrupoVendaNaoPermiteOpcaoEspecialExcecao, ItemPedidoPrecoMinimoExcecao, ItemPedidoPrecoMaximoExcecao,
			ProdutoExcecao, PedidoNumeroMaximoItensExcecao, PedidoCondicaoPagamentoPrazoMaximoExcecao,
			PedidoCondicaoPagamentoMaximoParcelasExcecao, PedidoFormaPagamentoClienteExcecao,
			PedidoCondicaoPagamentoClienteExcecao, PedidoPrazoEspecialPedidoAcimadoClienteExcecao,
			ItemPedidoQuantidadeEstoqueExcecao, ItemPedidoQuantidadeEstoqueOutrosExcecao, ClientePrazoMaximoExcecao,
			PedidoStatusSessaoExcecao, PedidoBalancoAtivoExcecao, VendedorTipoVendaExcecao,
			PedidoOpcaoEspecialClienteExcecao, ItemPedidoQuantidadeEstoqueMinimoExcecao, SpaceExcecao {

		super.alterarItem(itemPedido, operacao);

		this.getGerenteAutorizacao().persistirAutorizacoesUtilizadas();

		try {

			if (isModificarSaldoOferta(itemPedido)) {

				IItemPedidoWeb itemPedidoWeb = (IItemPedidoWeb) pesquisarItemPedido(itemPedido);

				double quantidadeAlteracao = itemPedidoWeb.getQuantidade() - itemPedidoWeb.getQuantidadeAnterior();

				quantidadeAlteracao *= itemPedidoWeb.getFatorEstoque();

				if (quantidadeAlteracao != 0) {

					atualizarOfertaSaldo(itemPedidoWeb.getOfertaNumero(), quantidadeAlteracao);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void excluirItem(IItemPedido itemPedido) throws SpaceExcecao {
		super.excluirItem(itemPedido);

		try {

			if (isModificarSaldoOferta(itemPedido)) {

				double quantidadeDebitar = itemPedido.getQuantidade() * itemPedido.getFatorEstoque();

				quantidadeDebitar *= -1;

				atualizarOfertaSaldo(itemPedido.getOfertaNumero(), quantidadeDebitar);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isModificarSaldoOferta(IItemPedido itemPedido) {
		return itemPedido.getOfertaNumero() > 0 && itemPedido.getKitCodigo() == 0;
	}

	/**
	 * Atualiza o Saldo de Oferta utilizando o metodo
	 * {@link Oferta#atualizarSaldoOferta(GenericoDAO, int, int, double) presente na
	 * respectiva classe}
	 * 
	 * @param ofertaNumero
	 * @param quantidadeDebioCredito
	 */
	protected void atualizarOfertaSaldo(int ofertaNumero, double quantidadeDebioCredito) {

		Oferta.atualizarSaldoOferta((GenericoDAO) getDao(), ofertaNumero, getFilial().getCodigo(),
				quantidadeDebioCredito);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * atualizarDebitoCliente ()
	 */
	@Override
	@Deprecated
	protected void atualizarDebitoCliente() {

		// ((Cliente) this.getCliente()).recuperarLimite((GenericoDAO)
		// getDao());

		if ((getNaturezaOperacao().getFlagConsisteLimite() == 1)
				&& (getFormaPagamento().getFlagConsisteLimite() == 1)) {

			((GenericoDAO) getDao()).executeUpdate("update cliente set cli_valdebitopr = cli_valdebitopr + "
					+ getPedido().getValor() + " where cli_pescodigo = " + getPedido().getPessoaCodigo());

			// ((Cliente) this.getCliente()).setValorDebitoPrevisto(((Cliente)
			// this.getCliente()).getValorDebitoPrevisto() +
			// getPedido().getValor());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * atualizarDebitoCreditoVendedor()
	 */
	@Override
	@Deprecated
	protected void atualizarDebitoCreditoVendedor() {

		if (this.getVendedor().getFlagDebitoCredito() == 1 && this.getCliente().getFlagDebitoCredito() == 1) {

			double valorDebitoCredito = this.getPedido().getValorVendaDebitoCredito()
					- this.getPedido().getValorSugeridoDebitoCredito() - this.getValorDebitoCreditoPedidoAnterior();

			((GenericoDAO) getDao()).executeUpdate("update vendedor set ven_saldoatual = ven_saldoatual + "
					+ valorDebitoCredito + " where ven_clbcodigo = " + getPedido().getVendedorCodigo());

		}
	}

	/**
	 * Atualiza Status do Pedido, gerando LOG
	 */
	@Override
	@Deprecated
	protected void atualizarStatusPedido() {

		if (isFlagVendaConsumidor()) {
			this.getPedido().setStatusPedidoCodigo(StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB);
		} else {
			this.getPedido().setStatusPedidoCodigo(StatusPedido.STATUS_NAO_SEPARADO);
		}
	}

	@Deprecated
	public void inserirLogPedidoFechamento() {

		switch (getPedido().getStatusPedidoCodigo()) {
		case StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB:

			this.inserirLogPedido(getPropriedade().getMensagem("texto.logPedido.pedidoaguardandopagamento"));

			break;
		case StatusPedido.STATUS_NAO_SEPARADO:

			this.inserirLogPedido(getPropriedade().getMensagem("texto.logPedido.pedidoConfirmado"));
			break;
		}
	}

	public void fecharPedido(int statusPedidoCodigo, int statusPagamento) throws Exception {
		this.fecharPedido();

		getPedido().setStatusPedidoCodigo(statusPedidoCodigo);
		getPedido().setStatusPagamentoCartao(statusPagamento);

		salvarOuAtualizarPedido();

	}

	@Override
	public void fecharPedido() throws Exception {
		// Verifica se adiciona o colaborador antes de finalizar o pedido para
		// que o calculo de coissão seja feito corretamente
		adicionarColaboradorAtendenteCasoPermitido();

		super.fecharPedido();

		if (pedidoEmInclusao()) {
			inserirColaboradoresPedido((GenericoDAO) getDao());
		}

		/*
		 * Gravar autorizações utilizadas como "U"
		 */
		this.getGerenteAutorizacao().persistirAutorizacoesUtilizadas();

	}

	@Override
	@Deprecated
	protected void atualizarDadosItemPedidoFechamentoVenda(IItemPedido itemPedido) {
		super.atualizarDadosItemPedidoFechamentoVenda(itemPedido);

		ProdutoUnidade.atualizarDataUltimaVendaProduto(((GenericoDAO) getDao()), itemPedido.getProdutoCodigo(),
				filial.getCodigo(), getPedido().getDataEmissao());
	}

	@Override
	public boolean isPermiteReservarEstoqueEntregaAoFechar() {
		return super.isPermiteReservarEstoqueEntregaAoFechar()
				&& getPedido().getStatusPedidoCodigo() != StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB;
	}

	private void inserirColaboradoresPedido(GenericoDAO dao) {
		if (ListUtil.isValida(colaboradoresPedidos)) {

			for (Colaborador colaborador : colaboradoresPedidos) {

				ColaboradorPedido colaboradorPedido = new ColaboradorPedido(colaborador, getPedido());

				dao.insertObject(colaboradorPedido);
			}
		}
	}

	/**
	 * Rotina de cálculo do percentual da comissão do item e dos valores de comissão
	 * dos pedidos
	 */
	@Override
	@Deprecated
	public void calcularComissao() {

		if (this.getItensPedido().size() == 0)
			return;

		this.getPedido().setValorComissao(0.0);
		this.getPedido().setValorComissao2(0.0);
		this.getPedido().setValorComissao3(0.0);
		this.getPedido().setValorComissao4(0.0);
		this.getPedido().setValorComissao5(0.0);

		ParametroVenda paramVenda = (ParametroVenda) this.getParametroVenda();
		NaturezaOperacao natop = ((NaturezaOperacao) this.getNaturezaOperacao());

		/*
		 * Se a natureza de operação não for uma venda nao calcula comissao.
		 */
		if (!natop.getClassificacaoNaturezaSaida().getTipoNaturezaOperacao().equals("V")
				|| this.getItensPedido().size() == 0)
			return;

		String calculoResultado = this.parametros.getParametro1().getFlagLucroCustoVenda();
		double resultado = 0;
		double result = 0;

		if (paramVenda.getBaseComissao() != null && paramVenda.getBaseComissao().equals("P")) {

			/*
			 * Comissão calculada com base nos valores do pedido inteiro
			 */
			IItemPedidoWeb itemAux = (IItemPedidoWeb) this.getItensPedido().get(0);

			GrupoComissao grupoComissao = this.recuperarGrupoComissao(itemAux);

			if (grupoComissao != null) {

				if (grupoComissao.getTipoComissao().equals("L")) {
					/* Calcular o resultado */
					result = grupoComissao.isLucroSemImposto() ? this.getValorResultadoPedidoSemImpostos()
							: this.getValorResultadoPedido();
					resultado = result * 100
							/ (calculoResultado.equals("C") ? this.getValorTotalCusto() : this.getValorTotalSugerido());
				} else {
					/* Calcular o acréscimo / desconto */
					// Retira o valor do frete do pedido para realizar o calculo
					double valorTotalPedido = this.getValorTotalPedidoSemFrete();
					resultado = (valorTotalPedido - this.getValorTotalSugerido()) * 100 / this.getValorTotalSugerido();
				}

				GrupoComissaoItem grupoComissaoItem = this.verificarFaixaComissao(grupoComissao, resultado);

				if (grupoComissaoItem != null) {
					for (IItemPedido itemPedido : this.getItensPedido()) {
						itemAux = (IItemPedidoWeb) itemPedido;
						this.preencherComissoes(itemAux, grupoComissaoItem);
					}
				}
			}

		} else {
			/*
			 * Comissão calculada com base em cada item do pedido
			 */
			for (IItemPedido itemPedido : this.getItensPedido()) {

				IItemPedidoWeb itemAux = (IItemPedidoWeb) itemPedido;

				GrupoComissao grupoComissao = this.recuperarGrupoComissao(itemAux);

				if (grupoComissao != null) {
					double basePreco = itemAux.getPrecoVenda();
					if (((ParametroVenda2) this.getParametroVenda2()).getFlagBuscarFaixaComissaoPrecoBruto() == 0) {

						basePreco = (basePreco * itemAux.getQuantidade() + itemAux.getAcrescimoPedidoValor()
								- itemAux.getDescontoPedidoValor()) / itemAux.getQuantidade();
					}
					if (grupoComissao.getTipoComissao().equals("L")) {

						result = (basePreco - itemAux.getCusto());
						if (grupoComissao.isLucroSemImposto()) {
							result = result
									- (itemAux.getValorIcms() - itemAux.getValorPis() - itemAux.getValorCofins())
											/ itemAux.getQuantidade();
						}

						/* Calcular o resultado */
						resultado = result * 100 / (calculoResultado.equals("C") ? itemAux.getCusto() : basePreco);
					} else {
						/* Calcular o acréscimo / desconto */
						resultado = (basePreco - itemAux.getPrecoSugerido()) * 100 / itemAux.getPrecoSugerido();
					}

					GrupoComissaoItem grupoComissaoItem = this.verificarFaixaComissao(grupoComissao, resultado);

					if (grupoComissaoItem != null)
						this.preencherComissoes(itemAux, grupoComissaoItem);
				}
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.api.negocio.modelo.negocio.GerentePedido#getValorTotalPedido ()
	 */
	@Override
	public double getValorTotalPedido() {
		return this.getPedido().getValor();
	}

	public double getValorTotalPedidoSemFrete() {
		return this.getValorTotalPedido() - (getPedido().getFrete() == null ? 0 : getPedido().getFrete());
	}

	/**
	 * 
	 */
	private double calcularFreteTabela() {
		double valorFrete = 0;

		try {
			Enderecos endEntrega = Enderecos.recuperarUnico((GenericoDAO) this.getDao(),
					this.getPedido().getPessoaCodigo(), this.getPedido().getEnderecoEntregaCodigo());

			EnderecoBase endFilial = new EnderecoBase(this.filial.getCep(), this.filial.getLogradouro(),
					this.filial.getNumeroLogradouro(), this.filial.getUfSigla(), 0,
					this.filial.getCidade().getCodigoCidade(), this.filial.getBairro().getCodigoBairro());
			GerenteFrete gf = new GerenteFrete(this.filial.getCodigo(), (GenericoDAO) this.getDao(), parametros);

			gf.setEnderecoOrigem(endFilial);
			gf.setEnderecoDestino(endEntrega, ((Cliente) this.getCliente()).getRegiao());

			TipoEntrega tpe = TipoEntrega.recuperarCodigo((GenericoDAO) this.getDao(),
					this.getPedido().getTipoSeparacao());

			valorFrete = gf.calcularValorFrete(tpe, TipoEmbalagem.CAIXA_PACOTE, getPedido(), getItensPedido());

			freteCalculadoComSucesso = true;

		} catch (Exception e) {
			freteCalculadoComSucesso = false;
			e.printStackTrace();
			if (e instanceof SpaceExcecao) {
				dispararMensagemUsuario(e.getMessage());
			}
		}

		return valorFrete;
	}

	public boolean isFreteCalculadoComSucesso() {
		return freteCalculadoComSucesso;
	}

	/**
	 * 
	 * @return
	 */
	private double calcularFreteMinimo() {

		double valorFrete = 0;

		if (((ParametroVenda3) this.parametros.getParametroVenda3()).getFlagCalculaFreteRegiao() == 1) {

			valorFrete = this.calcularFreteTabela();
			/*
			 * if (false) { Regiao regiaoAux = ((Cliente) this.getCliente()).getRegiao(); if
			 * (regiaoAux != null) {
			 * 
			 * Verifica se o pedido atingiu o valor para isencao do frete, caso negativo,
			 * verifica se o frete é valor fixo ou percentual sobre a venda.
			 * 
			 * if (regiaoAux.getFlagValorMinimoIsencaoFrete() == 1 &&
			 * this.getPedido().getValor() >= regiaoAux .getValorMinimoIsencaoFrete()) {
			 * valorFrete = 0; } else if (regiaoAux.getTipoFrete().equals("V")) { valorFrete
			 * = regiaoAux.getValorFrete(); } else { valorFrete =
			 * Conversao.arredondar(this.getPedido() .getValor()
			 * regiaoAux.getPercentualFrete() / 100, 2); }
			 * 
			 * }
			 * 
			 * /* Verifica o tipo de entrega do pedido e se possui algum desconto no frete.
			 * 
			 * TipoEntrega tpe = TipoEntrega.recuperarCodigo( (GenericoDAO) this.getDao(),
			 * this.getPedido() .getTipoSeparacao()); if (tpe != null) { double desconto =
			 * Conversao .arredondar( valorFrete tpe.getDescontoFretePercentual() / 100, 2);
			 * valorFrete -= desconto; tpe = null; } }
			 */

		}
		return valorFrete;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.api.negocio.modelo.negocio.GerentePedido#calcularFretePedido ()
	 */
	@Override
	public void calcularFretePedido() {

		/*
		 * Frete calculado de acordo com parâmetros da região
		 */
		if (((ParametroVenda3) this.parametros.getParametroVenda3()).getFlagCalculaFreteRegiao() == 1) {

			double valorFrete = this.calcularFreteMinimo();

			this.getPedido().setFrete(valorFrete);
		}

		this.getPedido().setValor(this.getPedido().getValor() + this.getPedido().getFrete());

		/*
		 * Ratear valor do frete entre os itens, como no Guardian
		 */
		if (this.getPedido().getFrete() > 0) {
			double pesoItem = 0.0;
			for (IItemPedido itemPedido : this.getItensPedido()) {
				pesoItem = Conversao.arredondar(itemPedido.getPrecoVenda() * itemPedido.getQuantidade(), 2)
						/ this.getValorTotalBrutoItem();
				IItemPedidoWeb itemAux = (IItemPedidoWeb) itemPedido;
				itemAux.setValorFrete(Conversao.arredondar(this.getPedido().getFrete() * pesoItem, 2));
			}
		}

	}

	public void setFreteValor(Double frete) {
		this.getPedido().setFrete(frete);

		if (isPedidoSemItens()) {
			zerarTodosValoresPedido();
			calcularFretePedido();
		} else {
			calcularTotais();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * calcularImpostosItem (br.com.space.api.negocio.modelo.dominio.IItemPedido)
	 */
	@Override
	@Deprecated
	protected void calcularImpostosItem(IItemPedido item) {

		/*
		 * IPI e ST
		 */
		IItemPedidoWeb itemAux = (IItemPedidoWeb) item;

		itemAux.reiniciarValoresImpostos();

		if ((((NaturezaOperacao) this.getNaturezaOperacao()).getFlagCalculaIpiSaida() == 1)
				&& (((ParametroVenda3) this.parametros.getParametroVenda3()).getFlagCalculaIpiDav() == 1)) {
			this.calcularIpiItem(itemAux);
		}

		Enderecos endereco = Enderecos.recuperarEnderecoPadrao((GenericoDAO) this.getDao(),
				this.getCliente().getCodigo(), this.parametros.getParametro1().getClassificacaoEnderecoPadraoCodigo());

		if (endereco == null) {

			endereco = Enderecos.recuperarUnico((GenericoDAO) this.getDao(), getPedido().getPessoaCodigo(),
					getPedido().getEnderecoEntregaCodigo());
		}

		if (((ParametroVenda2) this.getParametroVenda2()).getFlagCalculaStPedido() == 1) {

			if (endereco != null && StringUtil.isValida(endereco.getUfsigla()) && !isNaturezaEParametroInvalidosSt()) {
				this.calcularStItem(itemAux, endereco.getUfsigla());
				this.calcularSTAvulsoItem(itemAux, endereco.getUfsigla());
			}

		}

		this.calcularIcmsItem(itemAux, endereco.getUfsigla());
		this.calcularPisCofinsItem(itemAux);

	}

	@Deprecated
	private void calcularPisCofinsItem(IItemPedidoWeb item) {
		item.setValorPis(0.0);
		item.setValorCofins(0.0);

		if (((NaturezaOperacao) this.getNaturezaOperacao()).getFlagCalculoFiscal() == 1) {
			GrupoPisCofins grupoPisCofins = GrupoPisCofins.recuperarUnico((GenericoDAO) this.getDao(),
					((Produto) item.getProduto()).getGrupoPisCofinsCodigo());

			RegraPisCofins regraPis = RegraPisCofins.recuperarRegraPisCofins((GenericoDAO) this.getDao(),
					this.getCodigoFiscalOperacao(), grupoPisCofins.getCstPis(), grupoPisCofins.getCodigo(),
					getNaturezaOperacao().getCodigo(), ((Cliente) getCliente()).getSegmentoCodigo(), item, "P");

			RegraPisCofins regraCofins = RegraPisCofins.recuperarRegraPisCofins((GenericoDAO) this.getDao(),
					this.getCodigoFiscalOperacao(), grupoPisCofins.getCstCofins(), grupoPisCofins.getCodigo(),
					getNaturezaOperacao().getCodigo(), ((Cliente) getCliente()).getSegmentoCodigo(), item, "C");

			CodigoFiscal codigoFiscal = CodigoFiscal.recuperarUnico((GenericoDAO) this.getDao(),
					this.getCodigoFiscalOperacao());

			String codigoCstCofins = "";
			String codigoCstPis = "";
			if (regraPis != null) {
				codigoCstPis = regraPis.getCstPisCofins();
			} else {
				if (codigoFiscal.isCalculaPis()) {
					codigoCstPis = grupoPisCofins.getCstPis();
				} else {
					codigoCstPis = codigoFiscal.getCstPis();
				}
			}

			if (regraCofins != null) {
				codigoCstCofins = regraCofins.getCstPisCofins();
			} else {
				if (codigoFiscal.isCalculaCofins()) {
					codigoCstCofins = grupoPisCofins.getCstCofins();
				} else {
					codigoCstCofins = codigoFiscal.getCstCofins();
				}
			}

			CstPis cstPis = CstPis.recuperarUnico((GenericoDAO) this.getDao(), codigoCstPis);
			CstCofins cstCofins = CstCofins.recuperarUnico((GenericoDAO) this.getDao(), codigoCstCofins);

			double aliquotaPis = 0;
			double aliquotaCofins = 0;
			if ((!this.parametros.getParametro1().getTipoTributacaoPis().equals("I"))
					&& (cstPis.getTipoTributacao().equals("T") || cstPis.getTipoTributacao().equals("D"))) {
				if (regraPis != null) {
					aliquotaPis = regraPis.getAliquotaPis();
				} else {
					if (cstPis.getTipoTributacao().equals("D") && grupoPisCofins.getAliquotaPis() > 0) {
						aliquotaPis = grupoPisCofins.getAliquotaPis();
					} else {
						aliquotaPis = this.parametros.getParametro1().getAliquotaPis();
					}
				}
			}

			if ((!this.parametros.getParametro1().getTipoTributacaoCofins().equals("I"))
					&& (cstCofins.getTipoTributacao().equals("T") || cstCofins.getTipoTributacao().equals("D"))) {
				if (regraCofins != null) {
					aliquotaCofins = regraCofins.getAliquotaCofins();
				} else {
					if (cstCofins.getTipoTributacao().equals("D") && grupoPisCofins.getAliquotaCofins() > 0) {
						aliquotaCofins = grupoPisCofins.getAliquotaCofins();
					} else {
						aliquotaCofins = this.parametros.getParametro1().getAliquotaCofins();
					}
				}
			}

			double basePisCofins = Conversao.arredondar(item.getQuantidade() * item.getPrecoVenda()
					+ (this.parametros.getParametro3().isConsideraAcrescimoBaseCalculoPisCofins()
							? item.getAcrescimoPedidoValor()
							: 0)
					- (this.parametros.getParametro3().isConsideraDescontoBaseCalculoPisCofins()
							? item.getDescontoPedidoValor()
							: 0)
					+ (this.parametros.getParametro3().isConsideraIpiBaseCalculoPisCofins() ? item.getValorIpi() : 0)
					+ (this.parametros.getParametro3().isConsideraStBaseCalculoPisCofins() ? item.getValorSubstituicao()
							: 0)
					+ (this.parametros.getParametro4().isConsideraFreteBaseCalculoPisCofins() ? item.getValorFrete()
							: 0),
					2);

			if (parametros.getParametro4().isDeduzValorIcmsPisCofins()) {
				basePisCofins = Conversao.arredondar(basePisCofins - item.getValorIcms(), 2);
				basePisCofins = basePisCofins > 0 ? basePisCofins : 0;
			}

			double valorPis = Conversao.arredondar(basePisCofins * aliquotaPis / 100, 2);
			double valorCofins = Conversao.arredondar(basePisCofins * aliquotaCofins / 100, 2);

			item.setValorPis(valorPis);
			item.setValorCofins(valorCofins);
		}
	}

	@Deprecated
	private void calcularIcmsItem(IItemPedidoWeb item, String ufsigla) {

		item.setValorIcms(0.0);

		if (((NaturezaOperacao) this.getNaturezaOperacao()).getFlagCalculoFiscal() == 1) {
			double baseIcmsPercentual = 0, aliquotaIcms = 0;

			GrupoTributacao grupoTributacao = this.obterGrupoTributacao(item);

			RegraTributacao regraTributacao = this.obterRegraTributacao(ufsigla, grupoTributacao.getCodigo());

			if (regraTributacao == null) {
				this.setCodigoFiscalOperacao(((NaturezaOperacao) getNaturezaOperacao()).getCodigoFiscalSaida());
				if (((NaturezaOperacao) getNaturezaOperacao()).getFlagCalculaIcms() == 1) {
					baseIcmsPercentual = grupoTributacao.getBaseCalculo();
					aliquotaIcms = grupoTributacao.getAliquotaIcms();
				}
			} else {
				this.setCodigoFiscalOperacao(regraTributacao.getCodigoFiscalCodigo());
				baseIcmsPercentual = regraTributacao.getBaseCalculo();
				aliquotaIcms = regraTributacao.getAliquotaIcms();

			}

			double baseItem = Conversao.arredondar(item.getQuantidade() * item.getPrecoVenda(), 2)
					+ item.getAcrescimoPedidoValor() - item.getDescontoPedidoValor() + item.getValorFrete();

			/*
			 * arredondar para 2 para compatibilidade de arredondamento com Foxpro e para
			 * "Limpar" sujeiras do double
			 */

			double baseIcm = Conversao.arredondar(baseIcmsPercentual * baseItem / 100, 2);

			double valorIcm = Conversao.arredondar(baseIcm * aliquotaIcms / 100, 2);

			item.setValorIcms(valorIcm);
		}

	}

	@Deprecated
	private boolean isNaturezaEParametroInvalidosSt() {

		return (((NaturezaOperacao) this.getNaturezaOperacao()).getFlagCalculaIcms() == 0)
				|| (((ParametroVenda2) this.getParametroVenda2()).getFlagCalculaStPedido() == 0)
				|| (this.parametros.getParametro1().getFlagRecolheSt() == 0)
				|| (this.parametros.getParametro1().getFlagRecolheStPessoaFisica() == 0
						&& this.getCliente().getTipoPessoa().equals("F"))
				|| (this.parametros.getParametro1().getFlagRecolheStPessoaJuridicaIsenta() == 0
						&& this.getCliente().getTipoPessoa().equals("J")
						&& this.getCliente().getInscricaoEstadual().equals("ISENTO"));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * calcularImpostosPedido ()
	 */
	@Override
	@Deprecated
	protected void calcularImpostosPedido() {

		if (this.getItensPedido().size() == 0)
			return;

		this.getPedido().setValorIpi(0.0);
		this.getPedido().setValorSubstituicao(0.0);
		this.getPedido().setValorSubstituicaoAvulso(0.0);
		this.getPedido().setValorFundoEstadualCombatePobreza(0.0);
		this.getPedido().setValorIcms(0.0);
		this.getPedido().setValorPis(0.0);
		this.getPedido().setValorCofins(0.0);

		for (IItemPedido iItemPedido : this.getItensPedido()) {

			IItemPedidoWeb itemPedido = (IItemPedidoWeb) iItemPedido;

			this.calcularImpostosItem(itemPedido);

			this.getPedido()
					.setValorSubstituicao(this.getPedido().getValorSubstituicao() + itemPedido.getValorSubstituicao());

			this.getPedido().setValorSubstituicaoAvulso(
					this.getPedido().getValorSubstituicaoAvulso() + itemPedido.getValorSubstituicaoAvulso());

			this.getPedido().setValorFundoEstadualCombatePobreza(this.getPedido().getValorFundoEstadualCombatePobreza()
					+ itemPedido.getValorFundoEstadualCombatePobreza());

			this.getPedido().setValorIpi(this.getPedido().getValorIpi() + itemPedido.getValorIpi());

			this.getPedido().setValorPis(this.getPedido().getValorPis() + itemPedido.getValorPis());

			this.getPedido().setValorCofins(this.getPedido().getValorCofins() + itemPedido.getValorCofins());

			this.getPedido().setValorIcms(this.getPedido().getValorIcms() + itemPedido.getValorIcms());

		}

		this.getPedido().setValor(
				this.getPedido().getValor() + this.getPedido().getValorIpi() + this.getPedido().getValorSubstituicao());
	}

	/**
	 * 
	 * @param item
	 */
	@Deprecated
	protected void calcularIpiItem(IItemPedidoWeb item) {

		item.setValorIpi(0.0);

		int grupoIpiCodigo = ((Produto) item.getProduto()).getGrupoTributacaoIpiCodigo();
		double aliquotaIpi = 0;
		CstIpi cstIpi = null;

		if (grupoIpiCodigo == 0) {
			aliquotaIpi = ((Produto) item.getProduto()).getAliquotaIpi();
		} else {
			GrupoIpi grupoIpi = GrupoIpi.recuperarUnico((GenericoDAO) this.getDao(), grupoIpiCodigo);
			cstIpi = CstIpi.recuperarUnico((GenericoDAO) this.getDao(), grupoIpi.getCsiSaidaCodigo());
			if (cstIpi.getTipoTributacao().equals("T")) {
				aliquotaIpi = ((Produto) item.getProduto()).getAliquotaIpi();
			}

		}

		item.setValorIpi(Conversao.arredondar((item.getQuantidade() * item.getPrecoVenda()
				+ item.getAcrescimoPedidoValor() - item.getDescontoPedidoValor()) * aliquotaIpi / 100, 2));

		if (cstIpi == null || aliquotaIpi == 0)
			item.setCstIpiCodigo("53");
		else
			item.setCstIpiCodigo(cstIpi.getCodigo());

		item.setPercentualIpi(aliquotaIpi);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * calcularPesoVolumeItem (br.com.space.api.negocio.modelo.dominio.IItemPedido)
	 */
	@Override
	@Deprecated // O Auxilar pedido realiza o calculo do peso
	protected void calcularPesoVolumeItem(IItemPedido itemPedido) {
		IItemPedidoWeb item = (IItemPedidoWeb) itemPedido;

		item.setPesoBrutoProduto(
				((Produto) item.getProduto()).getPesoBruto() * item.getQuantidade() * item.getFatorEstoque());

		item.setPesoLiquidoProduto(
				((Produto) item.getProduto()).getPesoLiquido() * item.getQuantidade() * item.getFatorEstoque());

		item.setMetroCubico(((ProdutoUnidade) item.getProdutoUnidade()).getMetroCubico() * item.getQuantidade());

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * calcularPesoVolumePedido()
	 */
	@Override
	@Deprecated
	protected void calcularPesoVolumePedido() {
		IPedidoWeb pedido = this.getPedido();
		pedido.setPesoBruto(0.0);
		pedido.setPesoLiquido(0.0);
		pedido.setMetrosCubicos(0.0);
		if (((ParametroVenda2) this.getParametroVenda2()).getFlagCalculaVolumePedido() == 1)
			pedido.setQuantidadeVolumes(0.0);

		for (IItemPedido itemPedido : this.getItensPedido()) {

			IItemPedidoWeb item = (IItemPedidoWeb) itemPedido;

			pedido.setPesoBruto(pedido.getPesoBruto() + item.getPesoBrutoProduto());
			pedido.setPesoLiquido(pedido.getPesoLiquido() + item.getPesoLiquidoProduto());
			pedido.setMetrosCubicos(pedido.getMetrosCubicos() + item.getMetroCubico());

			if (((ParametroVenda2) this.getParametroVenda2()).getFlagCalculaVolumePedido() == 1
					&& item.getKitCodigo() == 0)

				if (item.getQuantidadeVolumes() > 0)
					pedido.setQuantidadeVolumes(pedido.getQuantidadeVolumes() + item.getQuantidadeVolumes());
				else
					pedido.setQuantidadeVolumes(pedido.getQuantidadeVolumes() + item.getQuantidade());

		}

		/*
		 * Considera cada kit como sendo 1 volume
		 */
		if (((ParametroVenda2) this.getParametroVenda2()).getFlagCalculaVolumePedido() == 1)
			for (IKitPedido kitPedido : this.getKitsPedido()) {
				pedido.setQuantidadeVolumes(pedido.getQuantidadeVolumes() + kitPedido.getQuantidade());
			}
	}

	/**
	 * 
	 * @param item
	 */
	@Deprecated
	protected void calcularStItem(IItemPedidoWeb item, String ufDestino) {

		item.setValorSubstituicao(0.0);

		double valorPauta, baseIcmsPercentual, aliquotaIcms, aliquotaMVA, percentualReducaoMVA, aliquotaST = 0;

		GrupoTributacao grupoTributacao = obterGrupoTributacao(item);
		GrupoSt grupoSt = obterGrupoSt(item);
		valorPauta = obterValorPauta(item);
		RegraTributacao regraTributacao = this.obterRegraTributacao(ufDestino, grupoTributacao.getCodigo());

		if (regraTributacao == null) {
			baseIcmsPercentual = grupoTributacao.getBaseCalculo();
			aliquotaIcms = grupoTributacao.getAliquotaIcms();
		} else {
			baseIcmsPercentual = regraTributacao.getBaseCalculo();
			aliquotaIcms = regraTributacao.getAliquotaIcms();
			if (regraTributacao.getFlagMantemGrupoSt() == 0) {
				grupoSt = GrupoSt.recuperarUnico((GenericoDAO) this.getDao(), regraTributacao.getGrupoStCodigo());
				valorPauta = regraTributacao.getValorPauta();
			}
		}

		if (grupoSt == null)
			return;

		aliquotaMVA = grupoSt.getAliquotaMva();
		percentualReducaoMVA = grupoSt.getReducaoMva();
		aliquotaST = grupoSt.getAliquotaSt();

		/*
		 * Regime Especial
		 */
		if (this.parametros.getParametro2().getFlagRegimeEspecial() == 1 && grupoSt.getFlagMvaAjustavel() == 1) {

			aliquotaMVA = Conversao.arredondar(((1 + aliquotaMVA / 100)
					* (1 - this.parametros.getParametro2().getCreditoPresumidoRegimeEspecial() / 100)
					/ (1 - aliquotaST / 100) - 1) * 100, 2);

			percentualReducaoMVA = this.parametros.getParametro2().getReducaoBaseRegimeEspecial();

			baseIcmsPercentual = Conversao
					.arredondar(100 - this.parametros.getParametro2().getReducaoBaseRegimeEspecial(), 2);

			aliquotaIcms = this.parametros.getParametro2().getCreditoPresumidoRegimeEspecial();
		}

		double valorSt = calcularValorST(item, valorPauta, percentualReducaoMVA, aliquotaMVA, aliquotaST,
				baseIcmsPercentual, aliquotaIcms);

		item.setValorSubstituicao(valorSt);
	}

	@Deprecated
	private double obterValorPauta(IItemPedidoWeb item) {
		if (item.getProdutoFilial().getValorPauta() > 0) {
			return item.getProdutoFilial().getValorPauta();
		} else {
			return ((Produto) item.getProduto()).getValorPauta();
		}
	}

	private GrupoSt obterGrupoSt(IItemPedidoWeb item) {

		if (item.getProdutoFilial().getGrupoSTCodigo() > 0) {
			return GrupoSt.recuperarUnico((GenericoDAO) this.getDao(), item.getProdutoFilial().getGrupoSTCodigo());
		} else {
			return GrupoSt.recuperarUnico((GenericoDAO) this.getDao(),
					((Produto) item.getProduto()).getGrupoStCodigo());
		}
	}

	private GrupoTributacao obterGrupoTributacao(IItemPedidoWeb item) {
		if (item.getProdutoFilial().getGrupoTributacaoCodigo() > 0) {

			return GrupoTributacao.recuperarUnico((GenericoDAO) this.getDao(),
					item.getProdutoFilial().getGrupoTributacaoCodigo());

		} else {
			return ((Produto) item.getProduto()).getGrupoTributacao();
		}
	}

	@Deprecated
	private void calcularSTAvulsoItem(IItemPedidoWeb item, String ufDestino) {

		item.setValorSubstituicaoAvulso(0.00);
		item.setValorFundoEstadualCombatePobreza(0.00);

		int grupoTributacaoCodigo = item.getProdutoFilial().getGrupoTributacaoCodigo() > 0
				? item.getProdutoFilial().getGrupoTributacaoCodigo()
				: ((Produto) item.getProduto()).getGrupoTributacaoCodigo();

		RegraStAvulso rSTAvulso = RegraStAvulso.recuperarUnicoAtivo((GenericoDAO) getDao(), getFilial(),
				getNaturezaOperacao().getCodigo(), getTipoPessoa(),
				((Cliente) getCliente()).getPessoa().getAtividadeCodigo(), grupoTributacaoCodigo, ufDestino);

		if (rSTAvulso == null) {
			return;
		}

		double baseIcmsPercentual = 100 - rSTAvulso.getReducaoMVA();

		double stAvulso = calcularValorST(item, rSTAvulso.getValorPauta(), rSTAvulso.getReducaoMVA(),
				rSTAvulso.getAliquotaMVA(), rSTAvulso.getAliquotaST(), baseIcmsPercentual, rSTAvulso.getAliquotaICMS());

		item.setValorSubstituicaoAvulso(stAvulso);

		double valorFecp = calcularValorFundoEstadualCombatePobreza(item, rSTAvulso.getValorPauta(),
				rSTAvulso.getReducaoMVA(), rSTAvulso.getAliquotaMVA(),
				rSTAvulso.getAliquotaFundoEstadualCombatePobreza());

		item.setValorFundoEstadualCombatePobreza(valorFecp);
	}

	private double calcularValorFundoEstadualCombatePobreza(IItemPedidoWeb item, double valorPauta,
			double percentualReducaoMVA, double aliquotaMVA, double aliquotaFecp) {

		double baseST = calcularValorBaseST(item, valorPauta, percentualReducaoMVA, aliquotaMVA);

		double valorFecp = Conversao.arredondar(baseST * aliquotaFecp / 100, 2);

		return valorFecp;

	}

	@Deprecated
	private double calcularValorST(IItemPedidoWeb item, double valorPauta, double percentualReducaoMVA,
			double aliquotaMVA, double aliquotaST, double baseIcmsPercentual, double aliquotaIcms) {

		double baseItem = Conversao.arredondar(item.getQuantidade() * item.getPrecoVenda(), 2)
				+ item.getAcrescimoPedidoValor() - item.getDescontoPedidoValor();

		double baseST = calcularValorBaseST(item, valorPauta, percentualReducaoMVA, aliquotaMVA);

		/*
		 * arredondar para 2 para compatibilidade de arredondamento com Foxpro e para
		 * "Limpar" sujeiras do double
		 */

		double valorSTGlobal = Conversao.arredondar(Math.abs((baseST * aliquotaST / 100)), 2);

		double baseIcm = Conversao.arredondar(baseIcmsPercentual * baseItem / 100, 2);

		double valorIcm = Conversao.arredondar(baseIcm * aliquotaIcms / 100, 2);

		double valorSt = Conversao.arredondar(valorSTGlobal - valorIcm, 2);

		return valorSt;
	}

	@Deprecated
	private double calcularValorBaseST(IItemPedidoWeb item, double valorPauta, double percentualReducaoMVA,
			double aliquotaMVA) {

		double baseST = 0;

		if (valorPauta > 0) {
			baseST = Conversao.arredondar(valorPauta * item.getQuantidade(), 2);
		} else {
			baseST = Conversao.arredondar(item.getQuantidade() * item.getPrecoVenda(), 2)
					+ item.getAcrescimoPedidoValor() + item.getValorIpi();

			if (this.parametros.getParametro3().getFlagConsideraDescontoCalculoSt() == 1) {
				baseST -= item.getDescontoPedidoValor();
			}

			baseST = Conversao.arredondar(
					Conversao.arredondar(baseST * (1 - percentualReducaoMVA / 100), 2) * (1 + aliquotaMVA / 100), 2);
		}
		return baseST;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * carregarRegrasCliente ()
	 */
	@Override
	protected void carregarRegrasCliente() {

		this.carregarTabelaPrecoPadrao();

		super.carregarRegrasCliente();

		this.carregarCondicaoPagamentoPadrao();
		this.carregarFormaPagamentoPadrao();

		this.carregarVendedorPadrao();

		this.carregarOpcaoEspecialPadrao();

		/*
		 * Envia os dados do gerente pedido para a classe de precificacao.
		 */
		this.alterarNegociacaoPrecificacao();

		this.carregarObservacaoCliente((Cliente) getCliente());
		this.carregarTransportadoraPadrao((Cliente) getCliente());

		this.carregarGruposVendNaoPermitidos();
	}

	private void carregarGruposVendNaoPermitidos() {
		if (getCliente() != null) {
			this.grupoVendaNaoPermitidoCliente = GrupoVendaNaoPermitidoCliente.recuperar((GenericoDAO) getDao(),
					getCliente().getCodigo());
		}
	}

	private void carregarTransportadoraPadrao(Cliente cliente) {

		if (cliente != null && cliente.getTransportadorCodigo() > 0) {

			try {

				Transportadora transportadoraNova = Transportadora.recuperarUnicoAtivo((GenericoDAO) getDao(),
						cliente.getTransportadorCodigo());

				int codigoTransportadoraAnterior = this.transportadora == null ? 0
						: this.transportadora.getPessoaCodigo();

				if (transportadoraNova != null
						&& codigoTransportadoraAnterior != transportadoraNova.getPessoaCodigo()) {

					setTransportadora(transportadoraNova);

					if (codigoTransportadoraAnterior > 0) {

						String msg = getPropriedade().getMensagem("mensagem.alteracao.cliente.transportadora",
								transportadoraNova.getPessoaCodigo() + " - "
										+ transportadoraNova.getPessoa().getRazao());

						dispararMensagemUsuario(msg);
					}
				}

			} catch (SpaceExcecao e) {
				e.printStackTrace();
			}
		}

	}

	private void carregarObservacaoCliente(Cliente cliente) {

		if (cliente != null && StringUtil.isValida(cliente.getObservacaoEntrega())) {
			getPedido().setObservacao(cliente.getObservacaoEntrega());
		}
	}

	/**
	 * Busca opção especial padrão do cliente
	 */
	private void carregarOpcaoEspecialPadrao() {
		/*
		 * Carrega a opção especial do cliente
		 */
		if (this.getCliente() != null && this.operacaoPedido == OperacaoPedido.Inclusao) {

			OpcaoEspecial opcaoEspecial = OpcaoEspecial.recuperarUnico((GenericoDAO) this.getDao(),
					this.getCliente().getOpcaoEspecialCodigo());

			try {
				carregarOpcaoEspecial(opcaoEspecial, false);
			} catch (Exception e) {
				e.printStackTrace();

				dispararMensagemUsuario(
						getPropriedade().getMensagem("mensagem.cliente.opcaoespecial.invalida", e.getMessage()));
			}
		}

	}

	public void carregarOpcaoEspecial(OpcaoEspecial opcaoEspecial, boolean isAlterarNegociacao) throws Exception {

		/*
		 * if (opcaoEspecial == null) { this.setOpcaoEspecial(null);
		 * this.setOpcaoEspecialFilial(null);
		 * 
		 * 
		 * 
		 * return; }
		 */

		OpcaoEspecialFilial opcaoEspecialFilial = null;

		if (opcaoEspecial != null) {

			validarOpcaoEspecial(opcaoEspecial);

			opcaoEspecialFilial = OpcaoEspecialFilial.recuperarUnico((GenericoDAO) this.getDao(),
					opcaoEspecial.getCodigo(), this.filial.getCodigo());

			validarOpcaoEspecialFilia(opcaoEspecialFilial);
		}

		if (isAlterarNegociacao) {
			alterarNegociacao(getTabelaPreco(), getCondicaoPagamento(), opcaoEspecialFilial);
		} else {
			this.setOpcaoEspecialFilial(opcaoEspecialFilial);
		}

		this.setOpcaoEspecial(opcaoEspecial);
	}

	/**
	 * 
	 * @param tabelaPreco
	 * @param condicaoPagamento
	 * @param opcaoEspecialFilial
	 * @throws Exception
	 */
	public void alterarNegociacao(ITabelaPreco tabelaPreco, ICondicaoPagamento condicaoPagamento,
			IOpcaoEspecialFilial opcaoEspecialFilial) throws Exception {

		alterarNegociacao(tabelaPreco, condicaoPagamento, opcaoEspecialFilial, getNaturezaOperacao(),
				((ParametroVenda) getParametroVenda()).getAcaoAlteracaoNegociacaoParametro());

	}

	public void validarOpcaoEspecialFilia(OpcaoEspecialFilial opcaoEspecialFilial) throws SpaceExcecao {

		if (opcaoEspecialFilial != null && opcaoEspecialFilial.isInativo()) {

			throw new SpaceExcecao(getPropriedade().getMensagem("alerta.pedido.opcaoespecial"));

		}

	}

	/**
	 * Carrega a condicao de pagamento padrao do cliente.
	 */
	private void carregarCondicaoPagamentoPadrao() {
		/*
		 * Somente carrega a condicao de pagamento do cliente se o parametro não estiver
		 * marcado.
		 */
		if (parametros.getParametroVenda2().getNaoSugereFormaCondicaoCliente() == 0 && this.getCliente() != null) {

			Cliente cliente = (Cliente) getCliente();

			CondicaoPagamento condicaoPadrao = getCondicaoPagamentoPadrao(cliente);

			if (condicaoPadrao != null && (getCondicaoPagamento() == null
					|| condicaoPadrao.getCodigo() != getCondicaoPagamento().getCodigo())) {

				boolean condicaoPermitida = isCodicaoPagamentoPermitida(condicaoPadrao);

				if (condicaoPermitida) {

					this.setCondicaoPagamento(condicaoPadrao);

				} else if (ListUtil.isValida(getCondicoesPagamentoPermitidas())) {

					this.setCondicaoPagamento(getCondicoesPagamentoPermitidas().get(0));
				}
			}
		}
	}

	private CondicaoPagamento getCondicaoPagamentoPadrao(Cliente cliente) {

		if (gerenteLogin.isPerfilCliente()) {

			return cliente.getCondicaoPagamentoModoCliente();

		} else {
			return cliente.getCondicaoPagamento();
		}
	}

	/**
	 * Carrega Forma de Pagamento padrão do cliente
	 */
	private void carregarFormaPagamentoPadrao() {
		/*
		 * Somente carrega a forma de pagamento do cliente se o parametro não estiver
		 * marcado.
		 */
		if (parametros.getParametroVenda2().getNaoSugereFormaCondicaoCliente() == 0 && this.getCliente() != null) {

			Cliente cliente = (Cliente) getCliente();

			FormaPagamento formaPadrao = getFormaPagamentoPadraoCliente(cliente);

			if (formaPadrao != null) {

				boolean formaPermitida = isFormaPagamentoPermitida(formaPadrao);

				if (formaPermitida) {
					this.setFormaPagamento(formaPadrao);

				} else if (ListUtil.isValida(getFormasPagamentoPermitidas())) {

					this.setFormaPagamento(getFormasPagamentoPermitidas().get(0));
				}
			}
		}
	}

	private FormaPagamento getFormaPagamentoPadraoCliente(Cliente cliente) {

		if (gerenteLogin.isPerfilCliente()) {

			return cliente.getFormaPagamentoModoCliente();

		} else {
			return cliente.getFormaPagamento();
		}
	}

	/**
	 * Carrega vendedor padrão do cliente ou usuário
	 */
	public void carregarVendedorPadrao() {

		Cliente clienteSelecionado = (Cliente) this.getCliente();
		NaturezaOperacao natop = (NaturezaOperacao) this.getNaturezaOperacao();

		Vendedor vendedorSugerido = null;

		boolean isUsuarioCliente = getUsuarioLogado() instanceof UsuarioCliente;

		/*
		 * Sugere o vendedor do cliente. Se o usuario logado for um cliente ignora o
		 * parametro de onde buscar o vendedor e busca da carteira.
		 */
		if (clienteSelecionado != null && natop != null
				&& (natop.getConfiguracaoLancamentoVendedor() == NaturezaOperacao.LACAMENTO_VENDEDOR_DO_CLIENTE
						|| isUsuarioCliente)) {

			vendedorSugerido = getVendedorCliente(clienteSelecionado, natop, isUsuarioCliente);

		} else {

			vendedorSugerido = getVendedorColaborador(natop);

			if (vendedorSugerido != null) {
				this.setVendedorUsuario(vendedorSugerido);
			}

			/*
			 * Sugere o vendedor do usuario logado.
			 */
			if (natop != null
					&& natop.getConfiguracaoLancamentoVendedor() == NaturezaOperacao.LACAMENTO_VENDEDOR_DO_USUARIO
					&& getUsuarioLogado() != null) {

				if (getUsuarioLogado().getColaboradorCodigo() != 0) {

					vendedorSugerido = Vendedor.recuperarUnico((GenericoDAO) this.getDao(),
							getUsuarioLogado().getColaboradorCodigo());

				}
			}
		}

		if (vendedorSugerido != null) {
			setVendedor(vendedorSugerido);
		}
	}

	/**
	 * 
	 * @param natop
	 * @return O vendedo padrao para o usuario colaborador
	 */
	private Vendedor getVendedorColaborador(NaturezaOperacao natop) {
		/*
		 * Sugere o vendedor do usuario logado.
		 */
		if (natop != null && natop.getConfiguracaoLancamentoVendedor() == 2 && getUsuarioLogado() != null
				&& getUsuarioLogado().getColaboradorCodigo() != 0) {

			return Vendedor.recuperarUnico((GenericoDAO) this.getDao(), getUsuarioLogado().getColaboradorCodigo());

		}
		return null;
	}

	/**
	 * 
	 * @param clienteSelecionado
	 * @param natop
	 * @return O vendedor padrao para o cliente em parametro. Caso o cliente não
	 *         tenha carteira ele sugere o vendedor que esta no parametroWeb
	 *         dependendo da isSugereParamentro
	 */
	private Vendedor getVendedorCliente(Cliente clienteSelecionado, NaturezaOperacao natop,
			boolean isSugereVendedorParametro) {

		Vendedor vendedor = null;
		CarteiraCliente carteira = null;

		if (natop.getTipoVenda().equals("I")) {
			carteira = clienteSelecionado.getCarteiraClienteInterna();
		} else {
			carteira = clienteSelecionado.getCarteiraClienteExterna();
		}

		if (carteira != null) {
			List<CarteiraVendedor> carteirasVendedor = CarteiraVendedor
					.recuperarPorCarteiraCliente((GenericoDAO) this.getDao(), carteira.getCodigo());
			/*
			 * Se achou apenas 1 vendedor na cartera, sugere o mesmo.
			 */
			if (carteirasVendedor != null && carteirasVendedor.size() == 1) {
				vendedor = carteirasVendedor.get(0).getVendedor();
			}
		}
		if (vendedor == null && isSugereVendedorParametro) {
			vendedor = Vendedor.recuperarUnico((GenericoDAO) this.getDao(),
					parametros.getParametroWeb().getVendedorPadraoCodigo());
		}

		return vendedor;
	}

	/**
	 * Cria Item de Pedido, preenchendo suas principais propriedades Recuperando ate
	 * o preco
	 * 
	 * @param produto
	 * @param produtoUnidade
	 * @return
	 */
	public IItemPedidoWeb criarItemPedido(Produto produto, ProdutoUnidade produtoUnidade) {
		return this.criarItemPedido(produto, produtoUnidade, true);
	}

	/**
	 * 
	 * Cria Item de Pedido, preenchendo suas principais propriedades
	 * 
	 * @param produto
	 * @param produtoUnidade
	 * @return
	 */
	public IItemPedidoWeb criarItemPedido(Produto produto, ProdutoUnidade produtoUnidade, boolean obterPreco) {

		ProdutoFilial produtoFilial = ProdutoFilial.recuperarUnico((GenericoDAO) this.getDao(),
				this.getFilial().getCodigo(), produto.getCodigo());

		// Se não encontrou a oferta, carrega a lista novamente e faz
		// outra pesquisa.

		this.getPrecificacao().carregarPrecos(produto.getCodigo(), Produto.COLUNA_CODIGO + " = " + produto.getCodigo(),
				null, 0);

		/*
		 * Dependendo da operação, Inclusão ou Alteração, criar ItemPedido ou
		 * ItemPedidoAuxiliar
		 */
		IItemPedidoWeb item;
		if (this.operacaoPedido == OperacaoPedido.Inclusao) {
			item = new ItemPedido(produto, produtoUnidade, produtoFilial);
		} else {
			item = new ItemPedidoAuxiliar(produto, produtoUnidade, produtoFilial);
		}

		if (item.getFlagDebitoCredito() == 0 || (getCliente() != null && getCliente().getFlagDebitoCredito() == 0)
				|| (getVendedor() != null && getVendedor().getFlagDebitoCredito() == 0)) {

			item.setFlagDebitoCredito(0);
		}

		item.setFilialCodigo(this.getFilial().getCodigo());

		item.setNumeroItem(0);

		if (obterPreco) {
			PrecoVenda precoVenda = this.getPrecificacao().obterPrecoVendaSugerido(produto.getCodigo(), produtoUnidade,
					null, true);

			item.setPrecoLiquido(precoVenda.getPrecoSugerido());
			item.setPrecoSugerido(precoVenda.getPrecoSugerido());
			item.setPrecoVenda(precoVenda.getPrecoSugerido());

			item.setPrecoVendaOriginal(precoVenda.getPrecoSugerido());
		}

		this.gravarCustoPadraoItem(item);

		item.setLocalEstoqueCodigo(0);

		/*
		 * Caso cliente controle estoque por local, sugere local de estoque da natureza
		 * de estoque ou o local padrão de estoque da filial
		 */
		NaturezaOperacao naturezaAux = (NaturezaOperacao) this.getNaturezaOperacao();

		NaturezaOperacaoFilial naturezaFilialAux = (NaturezaOperacaoFilial) this.getNaturezaOperacaoFilial();

		if ((Conversao.nvlInteger(naturezaAux.getFlagSugereLocalEstoquePadrao(), 0) == 1
				|| Conversao.nvlInteger(naturezaFilialAux.getLocalEstoquePadraoSaida(), 0) != 0)
				&& (Conversao.nvlInteger(naturezaAux.getFlagLancaLocalLotePedidoSaida(), 0) == 1
						|| this.parametros.getParametro2().getFlagControlaSaldoEstoqueLocalLote() != 0)) {

			if (Conversao.nvlInteger(naturezaAux.getFlagSugereLocalEstoquePadrao(), 0) == 1) {
				// Busca Local Padrão de Estoque
				ResultSet resultLocal = null;
				try {
					String sql = "select lcf_lcecodigo from localfilial where lcf_filcodigo = "
							+ this.getFilial().getCodigo() + " and lcf_permiteven = 1 order by lcf_seqbaixaest ";

					resultLocal = ((GenericoDAO) this.getDao()).listResultSet(sql);
					if (resultLocal.first()) {
						item.setLocalEstoqueCodigo(resultLocal.getInt("lcf_lcecodigo"));
					}
				} catch (Exception ex) {
					ex.printStackTrace();
				} finally {
					GenericoDAO.closeResultSet(resultLocal);
				}

			} else {
				item.setLocalEstoqueCodigo(naturezaFilialAux.getLocalEstoquePadraoSaida());
			}
		}

		item.setLote("");
		item.setStatusBaixa("");

		item.setPromocaoNumero(0);
		item.setFlagAtivo(1);
		item.setCodigoFiscalCodigo("");
		item.setCodigoBarrasProdutoCodigo("");
		item.setMetroCubico(0.0);
		item.setQuantidadeVolumes(0.0);
		item.setKitCodigo(0);
		item.setNegociacaoProdutoNumero(0);
		item.setDescricaoNF1("");
		item.setDescricaoNF2("");
		item.setDescricaoNF3("");
		item.setDescricaoNF4("");
		item.setDescricaoNF5("");
		item.setParcelaOrdemServico(0);
		item.setCertificadoClassificacao("");

		// Isolado - vai ser feito pelo itemPedidoMB - IncluirProduto
		// Pois ainda não estão setadas oferta e promoção
		// item.setGrupoComissaoCodigo(this.obterGrupoComissao(item));

		item.setValorIpi(0.0);
		item.setValorSubstituicao(0.0);

		/*
		 * Sugere a quantidade minima da unidade padrao de venda.
		 */
		if (produtoUnidade.getQuantidadeMinimaVenda() > 0) {
			item.setQuantidade(produtoUnidade.getQuantidadeMinimaVenda());
		} else {
			item.setQuantidade(1);
		}

		if (getPedido() != null && getPedido().getNumero() > 0 && StringUtil.isValida(getPedido().getSerieCodigo())) {
			item.setNumeroPedido(getPedido().getNumero());
			item.setSeriePedidoCodigo(getPedido().getSerieCodigo());
		}
		return item;
	}

	/**
	 * Grava campos de custo do kit
	 * 
	 * @param kitPedido
	 * @param itemKits
	 */
	private void gravarCustoAoKitPedido(KitPedido kitPedido, List<IItemKit> itemKits) {

		for (IItemKit iitemKit : itemKits) {

			ItemKit itemKit = (ItemKit) iitemKit;

			kitPedido.setCusto1(kitPedido.getCusto1() + itemKit.getCusto1());
			kitPedido.setCusto2(kitPedido.getCusto2() + itemKit.getCusto2());
			kitPedido.setCusto3(kitPedido.getCusto3() + itemKit.getCusto3());
			kitPedido.setCusto4(kitPedido.getCusto4() + itemKit.getCusto4());
			kitPedido.setCusto5(kitPedido.getCusto5() + itemKit.getCusto5());
			kitPedido.setCusto6(kitPedido.getCusto6() + itemKit.getCusto6());
			kitPedido.setCusto7(kitPedido.getCusto7() + itemKit.getCusto7());
			kitPedido.setCusto8(kitPedido.getCusto8() + itemKit.getCusto8());
			kitPedido.setCusto9(kitPedido.getCusto9() + itemKit.getCusto9());
			kitPedido.setCusto10(kitPedido.getCusto10() + itemKit.getCusto10());
		}
	}

	/**
	 * Estorna o estoque pendente de entrega e reserva o estoque pendente de
	 * confirmação.
	 */
	@Override
	@Deprecated
	protected void estornarEstoqueEntrega(IItemPedido itemPedido) {
		this.gravarEstoque((IItemPedidoWeb) itemPedido, 10001, false);// Esta no
																		// auxiliar
		this.reservarEstoqueConfirmacao((IItemPedidoWeb) itemPedido, false);
	}

	/**
	 * Estorna o estoque pendente de entrega e o estoque físico.
	 */
	@Override
	protected void estornarSeparacaoEstoque(IItemPedido itemPedido) {
		this.gravarEstoque((IItemPedidoWeb) itemPedido, 17001, false);
	}

	/**
	 * Gera próxima numeração do pedido
	 * 
	 * @throws PedidoExcecao
	 */
	@Deprecated
	private void gerarSerieProximoPedido() throws PedidoExcecao {
		NaturezaOperacao nat = (NaturezaOperacao) this.getNaturezaOperacao();
		nat.getControleNumeracaoCodigo();

		SeriePedido novaSerie = NumeracaoPedido.gerarSerieProximoPedido((GenericoDAO) this.getDao(), filial.getCodigo(),
				nat.getControleNumeracaoCodigo());

		if (novaSerie == null) {
			throw new PedidoExcecao(this.getPropriedade().getMensagem("alerta.pedido.controlenumeracao.serie"));
		}

		IPedidoWeb ped = this.getPedido();
		ped.setFilialCodigo(novaSerie.getSeriePedidosPK().getFilialCodigo());
		ped.setNumero(novaSerie.getNumeroAtual());
		ped.setSerieCodigo(novaSerie.getSeriePedidosPK().getCodigo());
	}

	public Filial getFilial() {
		return filial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * getFlagAtualizaEstoque ()
	 */
	@Override
	public int getFlagAtualizaEstoque() {
		return getPedido().getFlagAtualizaEstoque();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * getGrupoVendaNaoPermitidoCliente()
	 */
	@Override
	protected List<? extends IGrupoVendaNaoPermitidoCliente> getGrupoVendaNaoPermitidoCliente() {
		return grupoVendaNaoPermitidoCliente;
	}

	/**
	 * 
	 * @return
	 */
	public UsuarioWeb getUsuarioLogado() {
		return gerenteLogin.getUsuarioLogado();
	}

	/**
	 * Grava o custo padrão do item, atribuindo à propriedade Custo, verificado
	 * através de parâmetro e natureza de operação o custo padrão, já multiplicado
	 * pelo fator de estoque, para facilitar cálculos
	 * 
	 * @param itemAux
	 */
	private void gravarCustoPadraoItem(IItemPedidoWeb itemAux) {

		double custoPadrao = 0;
		int custoPadraoCodigo = 0;

		if ((((ParametroVenda2) this.getParametroVenda2()).getFlagSelecionaCustoNaturezaOperacao() == 1)
				&& (((NaturezaOperacao) this.getNaturezaOperacao()).getFlagSelecionaCustoSaida() == 1) && (Conversao
						.nvlInteger(((NaturezaOperacao) this.getNaturezaOperacao()).getCustoSaidaResultado(), 0) > 0)) {

			custoPadraoCodigo = ((NaturezaOperacao) this.getNaturezaOperacao()).getCustoSaidaResultado();

		} else {
			custoPadraoCodigo = this.parametros.getParametro1().getCustoPadrao();
		}

		switch (custoPadraoCodigo) {
		case 1:
			custoPadrao = itemAux.getCusto1();
			break;
		case 2:
			custoPadrao = itemAux.getCusto2();
			break;
		case 3:
			custoPadrao = itemAux.getCusto3();
			break;
		case 4:
			custoPadrao = itemAux.getCusto4();
			break;
		case 5:
			custoPadrao = itemAux.getCusto5();
			break;
		case 6:
			custoPadrao = itemAux.getCusto6();
			break;
		case 7:
			custoPadrao = itemAux.getCusto7();
			break;
		case 8:
			custoPadrao = itemAux.getCusto8();
			break;
		case 9:
			custoPadrao = itemAux.getCusto9();
			break;
		case 10:
			custoPadrao = itemAux.getCusto10();
			break;
		}

		itemAux.setCusto(custoPadrao * itemAux.getFatorEstoque());

	}

	/**
	 * Grava estoque dos itens
	 * 
	 * @param itemPedido
	 * @param tipoMovimentacaoEstoqueCodigo
	 */
	private void gravarEstoque(IItemPedidoWeb itemPedido, int tipoMovimentacaoEstoqueCodigo,
			boolean usaQuantidadeAnterior) {

		gravarEstoque(this.estoque, itemPedido, tipoMovimentacaoEstoqueCodigo, usaQuantidadeAnterior);
	}

	/**
	 * 
	 * @return
	 */
	private double recuperarEstoqueDisponivelItem(IItemPedidoWeb itemPedido, FlagTipoEstoque flagTipoEstoque) {

		double estoqueDisponivel = 0;
		try {
			this.estoque.limpar();
			this.estoque.setFilialCodigo(itemPedido.getFilialCodigo());
			this.estoque.setProdutoCodigo(itemPedido.getProdutoCodigo());

			if (itemPedido.getLocalEstoqueCodigo() != null) {
				this.estoque.setLocalEstoqueCodigo(itemPedido.getLocalEstoqueCodigo());
				if (itemPedido.getLote() != null)
					this.estoque.setLote(itemPedido.getLote());
			}
			this.estoque.setFlagTipoEstoque(flagTipoEstoque);

			estoqueDisponivel = this.estoque.recuperarEstoqueDisponivel();

		} catch (Exception e) {
		}

		return estoqueDisponivel;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * inicializarEdicaoPedido ()
	 */
	@Override
	public void inicializarEdicaoPedido() {
		super.inicializarEdicaoPedido();

		this.carregarDadosEdicaoPedido();

		this.configurarEdicaoPedido();

		inserirLogPedido(getPropriedade().getMensagem("texto.logPedido.inicioPedidoAlteracao"));

		/**
		 * Percorre todos os itens e salva a quantidade anterior à alteração.
		 */
		for (IItemPedido itemPedido : this.getItensPedido()) {
			IItemPedidoWeb itemAux = (IItemPedidoWeb) itemPedido;
			itemAux.setQuantidadeAnterior(itemAux.getQuantidade());
		}

	}

	public void carregarPedidoEmInclusao() {
		this.carregarDadosEdicaoPedido();

	}

	/**
	 * Carregar dados para edição do pedido
	 */
	private void carregarDadosEdicaoPedido() {

		/*
		 * Preenche lista de itens
		 */
		List<ItemPedido> itensAuxiliar = ItemPedido.recuperarItensPedido((GenericoDAO) this.getDao(),
				this.getPedido().getNumero(), this.getPedido().getSerieCodigo(), this.getPedido().getFilialCodigo());

		this.getItensPedido().clear();

		int numeroItem = 0;
		for (IItemPedido itemPedido : itensAuxiliar) {

			Produto produto = Produto.recuperarCodigo((GenericoDAO) this.getDao(), itemPedido.getProdutoCodigo());
			ProdutoUnidade produtoUnidade = ProdutoUnidade.recuperar((GenericoDAO) this.getDao(),
					itemPedido.getProdutoCodigo(), itemPedido.getUnidade(), itemPedido.getQuantidadeUnidade());
			ProdutoFilial produtoFilial = ProdutoFilial.recuperarUnico((GenericoDAO) this.getDao(),
					this.getPedido().getFilialCodigo(), itemPedido.getProdutoCodigo());

			if (itemPedido.getPromocaoNumero() > 0) {
				Promocao promocao = Promocao.recuperar((GenericoDAO) this.getDao(), this.getPedido().getFilialCodigo(),
						itemPedido.getPromocaoNumero());

				((ItemPedido) itemPedido).setPromocao(promocao);
			}

			((ItemPedido) itemPedido).setProduto(produto);
			((ItemPedido) itemPedido).setProdutoUnidade(produtoUnidade);
			((ItemPedido) itemPedido).setProdutoFilial(produtoFilial);

			if (itemPedido.getNumeroItem() > numeroItem)
				numeroItem = itemPedido.getNumeroItem();

			this.gravarCustoPadraoItem((IItemPedidoWeb) itemPedido);

			this.getItensPedido().add(itemPedido);
		}
		numeroItem++;
		this.setNumeroItemPedidoProximo(numeroItem);

		/*
		 * Preenche propriedades de classe gerentepedido
		 */
		this.setNaturezaOperacao(NaturezaOperacao.recuperarUnico((GenericoDAO) this.getDao(),
				this.getPedido().getNaturezaOperacaoCodigo()));
		this.setCliente(Cliente.recuperarUnico((GenericoDAO) this.getDao(), this.getPedido().getPessoaCodigo()));
		this.setVendedor(Vendedor.recuperarUnico((GenericoDAO) this.getDao(), this.getPedido().getVendedorCodigo()));
		this.setTabelaPreco(
				TabelaPreco.recuperarUnico((GenericoDAO) this.getDao(), this.getPedido().getTabelaPrecoCodigo()));
		this.setFormaPagamento(
				FormaPagamento.recuperarUnico((GenericoDAO) this.getDao(), this.getPedido().getFormaPagamentoCodigo()));
		this.setCondicaoPagamento(CondicaoPagamento.recuperarUnico((GenericoDAO) this.getDao(),
				this.getPedido().getCondicaoPagamentoCodigo()));
		this.setOpcaoEspecial(
				OpcaoEspecial.recuperarUnico((GenericoDAO) this.getDao(), this.getPedido().getOpcaoEspecialCodigo()));
		this.setOpcaoEspecialFilial(OpcaoEspecialFilial.recuperarUnico((GenericoDAO) this.getDao(),
				this.getPedido().getOpcaoEspecialCodigo(), this.getPedido().getFilialCodigo()));
		this.setVendedorUsuario(
				Vendedor.recuperarUnico((GenericoDAO) this.getDao(), this.getPedido().getColaboradorCodigo()));

		this.alterarNegociacaoPrecificacao();
		this.calcularTotais();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * inicializarInclusaoPedido()
	 */
	@Override
	public void inicializarInclusaoPedido() {

		/*
		 * Desfaz inclusão ou alteração já iniciada no pedido, caso haja
		 */
		if (this.getPedido() != null && this.getPedido().getNumero() > 0) {
			if (this.operacaoPedido == OperacaoPedido.Inclusao) {
				// if (this.getPedido().getOrigem().equals("W"))
				if (this.verificarCancelamentoPedido()) {
					GerentePedido.cancelarPedidoEmInclusao((GenericoDAO) this.getDao(), (Pedido) this.getPedido(),
							this.estoque, this.gerenteLogin.getSessao(),
							getPropriedade().getMensagem("texto.logpedido.complemento.lognovopedido"));
				}
			} else if (this.operacaoPedido == OperacaoPedido.Alteracao) {
				GerentePedido.desfazerAlteracaoPedido((GenericoDAO) this.getDao(), (Pedido) this.pedidoEmAlteracao,
						this.estoque, this.gerenteLogin.getSessao(),
						getPropriedade().getMensagem("texto.logpedido.complemento.lognovopedido"));
			}
		}

		super.inicializarInclusaoPedido();

		Pedido pedidoAux = new Pedido(parametros.getParametroWeb().getAdicionarDiasDataEmissao());
		// /pedidoAux.setDataCadastro(new Date());

		pedidoAux.setFilialCodigo(filial.getCodigo());
		pedidoAux.setHoraCadastro("");
		pedidoAux.setStatusPedidoCodigo(StatusPedido.STATUS_EM_INCLUSAO);
		pedidoAux.setFormaPagamentoCodigo("");
		pedidoAux.setCondicaoPagamentoCodigo(0);
		pedidoAux.setOrigemVendaCodigo(0);

		pedidoAux.setCarga(0);
		pedidoAux.setFrete(0.0);
		pedidoAux.setQuantidadeImpressao(0);
		pedidoAux.setFlagAtivo(1);
		pedidoAux.setObservacao("");
		pedidoAux.setObservacao1("");
		pedidoAux.setObservacao2("");
		pedidoAux.setLoteLancamentoFinanceiro(0);
		pedidoAux.setLoteNotaPedidoCodigo(0);
		pedidoAux.setLotePedidoImportadoCodigo(0);
		pedidoAux.setLoteComissaoNumero(0);
		pedidoAux.setCampoUsuario1("");
		pedidoAux.setCampoUsuario2("");
		pedidoAux.setCampoUsuario3("");
		pedidoAux.setCampoUsuario4("");
		pedidoAux.setCampoUsuario5("");
		pedidoAux.setOrcamentoCodigo(0);
		pedidoAux.setNumeroSeparacoes(0);
		pedidoAux.setOrdemEntrega(0);
		pedidoAux.setTipoVendaCodigo(0);
		pedidoAux.setOrdemCliente(0);
		pedidoAux.setOrigem("W"); /* Web */
		pedidoAux.setCodigoOrigem("");
		pedidoAux.setSeriePedidoPromocao("");
		pedidoAux.setNumeroPedidoPromocao(0);
		pedidoAux.setFlagParticipaPromocao(0);
		pedidoAux.setPromocaoNumero(0);
		pedidoAux.setPrazoEspecial(0);
		pedidoAux.setPrazoPromocao(0);
		pedidoAux.setQuantidadeVolumes(0.0);
		pedidoAux.setLucratividade(0.0);
		pedidoAux.setPesoBruto(0.0);
		pedidoAux.setPesoLiquido(0.0);
		pedidoAux.setMetrosCubicos(0.0);

		if (this.parametros.getParametro1().getContaCorrenteCodigo() > 0)
			pedidoAux.setContaCorrenteCodigo(this.parametros.getParametro1().getContaCorrenteCodigo());
		else
			pedidoAux.setContaCorrenteCodigo(0);

		pedidoAux.setTransportadoraCodigo(0);

		pedidoAux.setPrecoBaseCodigo(0);
		pedidoAux.setEnderecoCobrancaCodigo(0);

		this.setPedido(pedidoAux);
		this.setNaturezaOperacao(null);
		this.setTabelaPreco(null);
		this.setCondicaoPagamento(null);
		this.setFormaPagamento(null);
		this.setCliente(null);
		this.setVendedor(null);
		this.setVendedorUsuario(null);
		this.setGrupoVenda(null);
		this.setOpcaoEspecial(null);
		this.setTurnoEntrega(null);
		try {
			this.setTransportadora(null);
		} catch (SpaceExcecao e) {
			// Quando seta null não da erro. Renato
		}
		// this.setEnderecos(null);
		this.getItensPedido().clear();
		this.getKitsPedido().clear();
		this.limparAlertas();
		this.calcularTotais();
	}

	/*
	 * Método criado para verificar status e valor do pedido a ser cancelado Para
	 * que não seja cancelado pedido já alterado no Guardian Indevidamente
	 */
	private boolean verificarCancelamentoPedido() {

		ResultSet resultPedido = null;
		boolean retorno = false;

		Pedido pedido = (Pedido) this.getPedido();

		try {
			String sql = "select ped_stpcodigo, ped_valor from pedidos where ped_filcodigo = "
					+ pedido.getFilialCodigo() + " and ped_numero = " + pedido.getNumero() + " and ped_spvcodigo = '"
					+ pedido.getSerieCodigo() + "'";

			resultPedido = ((GenericoDAO) this.getDao()).listResultSet(sql);
			if (resultPedido.first()) {
				if (pedido.getStatusPedidoCodigo() == resultPedido.getInt("ped_stpcodigo")
						|| Conversao.arredondar(pedido.getValor(), 2) == Conversao
								.arredondar(resultPedido.getDouble("ped_valor"), 2)) {
					retorno = true;
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(resultPedido);
		}

		return retorno;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#inserirItemPedido
	 * (br.com.space.api.negocio.modelo.dominio.IItemPedido)
	 */
	@Override
	public int inserirItemPedido(IItemPedido itemPedido) {

		if (this.getFlagAtualizaEstoque() == 1)
			((IItemPedidoWeb) itemPedido).setStatusBaixa("C");
		else
			((IItemPedidoWeb) itemPedido).setStatusBaixa("X");

		((GenericoDAO) this.getDao()).insertObject(itemPedido);

		return 0;
	}

	/**
	 * Insere Log do Pedido
	 */
	@Override
	@Deprecated
	protected void inserirLogPedido(String obs) {

		inserirLogPedido(((GenericoDAO) this.getDao()), getPedido(), this.getSessaoCodigo(),
				getUsuarioLogado().getLogin(), obs, null, null);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#inserirPedido(br
	 * .com.space.api.negocio.modelo.dominio.IPedido)
	 */
	@Override
	public int inserirPedido(IPedido iPedido) {
		((GenericoDAO) this.getDao()).insertObject(iPedido);
		return 0;
	}

	/**
	 * Obtém grupo de comissão do item de pedido, de acordo com parâmetros,
	 * retornando seu código
	 */
	@Deprecated
	public int obterGrupoComissao(IItemPedidoWeb item) {

		// Primeiro, verifica se existe regra;
		// Caso exista, não verifica demais condições

		int grupoComissao = this.obterRegraComissao(item);

		if (grupoComissao == 0) {

			String tipoComissao = ((ParametroVenda) this.getParametroVenda()).getTipoComissao();

			if (tipoComissao != null) {
				switch (tipoComissao) {

				case "V": {
					if (this.getVendedor() != null) {
						if (((NaturezaOperacao) this.getNaturezaOperacao()).getTipoVenda().equals("I"))
							grupoComissao = ((Vendedor) this.getVendedor()).getGrupoComissaoInterno();
						else
							grupoComissao = ((Vendedor) this.getVendedor()).getGrupoComissaoExterno();
					}
					break;
				}
				case "T": {
					if (item.getOfertaNumero() == 0)
						grupoComissao = ((TabelaPreco) this.getTabelaPreco()).getGrupoComissao();
					else
						grupoComissao = ((TabelaPreco) this.getTabelaPreco()).getGrupoComissaoOferta();
					break;
				}

				case "P": {
					if (item.getPromocaoNumero() != 0)
						grupoComissao = retornarGrupoComissaoPromocao(item);
					else
						grupoComissao = retornarGrupoComissaoProduto(item);
					break;

				}
				}
			}
		}
		return grupoComissao;
	}

	/**
	 * Método para obter código do grupo de tributação da regra de comissão
	 * 
	 * @param item
	 * @return
	 */
	@Deprecated
	private int obterRegraComissao(IItemPedidoWeb item) {

		if (this.getCliente() == null) {
			return 0;
		}

		Produto prodaux = Produto.recuperarCodigo((GenericoDAO) this.getDao(), item.getProdutoCodigo());

		Cliente cliaux = Cliente.recuperarUnico((GenericoDAO) this.getDao(), this.getCliente().getCodigo());

		int grupoComissaoCodigo = 0;

		String sql = "select rgc_codigo, rgc_dataini, rgc_datafim, rgc_gcocodigo from regracomissao "
				+ " where rgc_ativo = 1 and (rgc_natcodigo = '" + this.getPedido().getNaturezaOperacaoCodigo()
				+ "' or rgc_natcodigo = '') " + " and (rgc_clbcodigo = " + this.getVendedor().getColaboradorCodigo()
				+ " or rgc_clbcodigo = 0) " + " and (rgc_procodigo = " + item.getProdutoCodigo()
				+ " or rgc_procodigo = 0) " + " and (rgc_ctpcodigo = " + prodaux.getCategoriaCodigo()
				+ " or rgc_ctpcodigo = 0) " + " and (rgc_scpcodigo = " + prodaux.getSubCategoriaCodigo()
				+ " or rgc_scpcodigo = 0) " + " and (rgc_grpcodigo = " + prodaux.getGrupoCodigo()
				+ " or rgc_grpcodigo = 0) " + " and (rgc_sgpcodigo = " + prodaux.getSubGrupoCodigo()
				+ " or rgc_sgpcodigo = 0) " + " and (rgc_tprcodigo = " + this.getTabelaPreco().getCodigo()
				+ " or rgc_tprcodigo = 0) " + " and (rgc_grccodigo = " + cliaux.getGrupoClienteCodigo()
				+ " or rgc_grccodigo = 0) " + " and (rgc_clccodigo = "
				+ cliaux.getPessoa().getClassificacaoClienteCodigo() + " or rgc_clccodigo = 0 ) "
				+ " and (rgc_cpgcodigo = " + this.getCondicaoPagamento().getCodigo() + " or rgc_cpgcodigo = 0) "
				+ " and (rgc_oferta = 'T' or rgc_oferta = '" + (item.getOfertaNumero() == 0 ? "N" : "S") + "') "
				+ " and (rgc_promocao = 'T' or rgc_promocao = '" + (item.getPromocaoNumero() == 0 ? "N" : "S") + "') "
				+ " and (rgc_filcodigo = " + item.getFilialCodigo() + " or rgc_filcodigo = 0) "
				+ " and (rgc_datafim is null or (rgc_dataini <= '"
				+ Conversao.formatarDataAAAAMMDD(this.getPedido().getDataEmissao()) + "' and rgc_datafim >= '"
				+ Conversao.formatarDataAAAAMMDD(this.getPedido().getDataEmissao())
				+ "')) order by rgc_datafim desc, rgc_filcodigo desc, rgc_clbcodigo desc, "
				+ " rgc_natcodigo desc, rgc_procodigo desc, rgc_promocao, rgc_oferta, "
				+ "rgc_grccodigo desc, rgc_tprcodigo desc, "
				+ " rgc_scpcodigo desc, rgc_ctpcodigo desc, rgc_sgpcodigo desc, rgc_grpcodigo desc ";

		ResultSet resultComissao = null;

		try {
			resultComissao = ((GenericoDAO) this.getDao()).listResultSet(sql);
			if (resultComissao.first()) {
				grupoComissaoCodigo = resultComissao.getInt("rgc_gcocodigo");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(resultComissao);
		}

		return grupoComissaoCodigo;
	}

	@Deprecated
	private String getTipoPessoa() {
		return (this.getCliente().getTipoPessoa().equals("J")
				&& this.getCliente().getInscricaoEstadual().equals("ISENTO") ? "I" : this.getCliente().getTipoPessoa());
	}

	/**
	 * Obtém regra de tributação, caso haja
	 * 
	 * @param ufSigla
	 * @return
	 */
	protected RegraTributacao obterRegraTributacao(String ufSigla, int grupoTributacaoCodigo) {

		ResultSet resultRegra = null;

		RegraTributacao regraTributacao = null;

		String tipoPessoa = getTipoPessoa();

		String sql = "select rtb_atvcodigo, rtb_ufdestino, rtb_grtcodigo, rtb_desc, rtb_filcodigo, "
				+ "rtb_natcodigo, rtb_cst, rtb_aliqicms, rtb_basecalculo, rtb_isentas, rtb_outras, rtb_cdfcodigo, "
				+ "rtb_tipopessoa, rtb_obfcodigo, rtb_filial, rtb_uforigem, rtb_gstcodigo, rtb_valpauta, rtb_ativo,"
				+ "rtb_negpreco, rtb_csscodigo, rtb_mantgst from regratrib where rtb_ativo = 1 and rtb_natcodigo = '"
				+ this.getNaturezaOperacao().getCodigo() + "' and rtb_tipopessoa like '%" + tipoPessoa
				+ "%' and (rtb_filial = 0 or rtb_filial = " + this.getFilial().getCodigo()
				+ ") and (rtb_atvcodigo = 0 or rtb_atvcodigo = "
				+ ((Cliente) this.getCliente()).getPessoa().getAtividadeCodigo()
				+ ") and (rtb_grtcodigo = 0 or rtb_grtcodigo = " + grupoTributacaoCodigo
				+ ") and (rtb_uforigem = '' or rtb_uforigem ='" + ((Filial) this.getFilial()).getUfSigla()
				+ "') and (rtb_ufdestino = '" + ufSigla + "' or rtb_ufdestino = '"
				+ (((Filial) this.getFilial()).getUfSigla().equals(ufSigla) ? "=" : "#")
				+ "') order by rtb_grtcodigo desc, rtb_atvcodigo desc, rtb_filcodigo desc, rtb_uforigem desc, rtb_ufdestino desc";

		try {
			resultRegra = ((GenericoDAO) this.getDao()).listResultSet(sql);
			if (resultRegra.first()) {
				regraTributacao = new RegraTributacao();
				regraTributacao.setBaseCalculo(resultRegra.getDouble("rtb_basecalculo"));
				regraTributacao.setAliquotaIcms(resultRegra.getDouble("rtb_aliqicms"));
				regraTributacao.setFlagMantemGrupoSt(resultRegra.getInt("rtb_mantgst"));
				regraTributacao.setGrupoStCodigo(resultRegra.getInt("rtb_gstcodigo"));
				regraTributacao.setValorPauta(resultRegra.getDouble("rtb_valpauta"));
				regraTributacao.setCodigoFiscalCodigo(resultRegra.getString("rtb_cdfcodigo"));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(resultRegra);

		}

		return regraTributacao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.api.negocio.modelo.negocio.GerentePedido#pedidoExisteNoBanco ()
	 */
	@Override
	@Deprecated
	public boolean pedidoExisteNoBanco() {

		boolean existeItensGravados = getItensPedido().size() > 0 && getItensPedido().get(0).getNumeroPedido() > 0;

		if (!existeItensGravados && getPedido() != null) {
			IPedidoWeb pedidoAux = getPedido();

			long tot = ((GenericoDAO) getDao()).count(Pedido.NOME_TABELA,
					Pedido.COLUNA_NUMERO + " = " + pedidoAux.getNumero() + " and " + Pedido.COLUNA_SERIE_CODIGO + "= '"
							+ pedidoAux.getSerieCodigo() + "' and " + Pedido.COLUNA_FILIAL_CODIGO + "="
							+ pedidoAux.getFilialCodigo());

			return tot > 0;
		}

		return existeItensGravados;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * preencherAtributosPedido()
	 */
	@SuppressWarnings("unused")
	@Override
	public void preencherAtributosPedido() {
		// TODO Auto-generated method stub
		super.preencherAtributosPedido();

		IPedidoWeb pedidoAux = this.getPedido();

		if (this.getNaturezaOperacao() != null) {
			ClassificacaoNaturezaOperacao classificacao = ((NaturezaOperacao) this.getNaturezaOperacao())
					.getClassificacaoNaturezaSaida();

			pedidoAux.setFlagTipoNaturezaOperacao(classificacao.getTipoNaturezaOperacao());

			pedidoAux.setFlagAtualizaEstoque(this.getNaturezaOperacao().getFlagAtualizaEstoque());

			pedidoAux.setFlagConsisteLimite(this.getNaturezaOperacao().getFlagConsisteLimite());

			pedidoAux.setFlagDevolucao(classificacao.getFlagDevolucao());

			pedidoAux.setPrecoBaseCodigo(this.getTabelaPreco().getPrecoBaseCodigo());
		}

		/*
		 * Preenche o Colaborador/Vendedor do pedido.
		 * 
		 * Colaborador é quem emitiu o pedido e Vendedor é para quem será faturado.
		 */
		if (getVendedor() != null) {
			getPedido().setVendedorCodigo(getVendedor().getColaboradorCodigo());
			getPedido().setColaboradorCodigo(getVendedor().getColaboradorCodigo());
		}

		if (getCliente() != null)
			getPedido().setPessoaCodigo(getCliente().getCodigo());

		if (getTurnoEntrega() != null)
			getPedido().setTurnoEntregaCodigo(getTurnoEntrega().getCodigo());

		/*
		 * Preencher campos
		 */
		if (getCliente() != null && getVendedor() != null
				&& (this.getCliente().getFlagDebitoCredito() == 1 || this.getVendedor().getFlagDebitoCredito() == 1)) {
			getPedido().setFlagDebitoCredito(1);
		} else {
			getPedido().setFlagDebitoCredito(0);
		}

		preencherContaCorrentePedido();

		if (false) {
			((Pedido) getPedido()).setCarga(0);
			((Pedido) getPedido()).setFrete(0.0);
			((Pedido) getPedido()).setQuantidadeImpressao(0);
			((Pedido) getPedido()).setFlagAtivo(1);
			((Pedido) getPedido()).setObservacao("");
			((Pedido) getPedido()).setObservacao1("");
			((Pedido) getPedido()).setObservacao2("");
			((Pedido) getPedido()).setLoteLancamentoFinanceiro(0);
			((Pedido) getPedido()).setLoteNotaPedidoCodigo(0);
			((Pedido) getPedido()).setLotePedidoImportadoCodigo(0);
			((Pedido) getPedido()).setLoteComissaoNumero(0);
			((Pedido) getPedido()).setCampoUsuario1("");
			((Pedido) getPedido()).setCampoUsuario2("");
			((Pedido) getPedido()).setCampoUsuario3("");
			((Pedido) getPedido()).setCampoUsuario4("");
			((Pedido) getPedido()).setCampoUsuario5("");
			((Pedido) getPedido()).setOrcamentoCodigo(0);
			((Pedido) getPedido()).setNumeroSeparacoes(0);
			((Pedido) getPedido()).setOrdemEntrega(0);
			((Pedido) getPedido()).setTipoVendaCodigo(0);
			((Pedido) getPedido()).setOrdemCliente(0);
			((Pedido) getPedido()).setOrigem("W"); /* Web */
			((Pedido) getPedido()).setCodigoOrigem("");
			((Pedido) getPedido()).setSeriePedidoPromocao("");
			((Pedido) getPedido()).setNumeroPedidoPromocao(0);
			((Pedido) getPedido()).setFlagParticipaPromocao(0);
			((Pedido) getPedido()).setPromocaoNumero(0);

			if (((ParametroVenda) this.getParametroVenda()).getFlagSugerePrazoEspecial() == 1)
				((Pedido) getPedido()).setPrazoEspecial(((Cliente) this.getCliente()).getPrazoEspecial());

			((Pedido) getPedido()).setQuantidadeVolumes(0.0);
			((Pedido) getPedido()).setLucratividade(0.0);
			((Pedido) getPedido()).setPesoBruto(0.0);
			((Pedido) getPedido()).setPesoLiquido(0.0);
			((Pedido) getPedido()).setMetrosCubicos(0.0);

			((Pedido) getPedido()).setTransportadoraCodigo(0);
			if (((Cliente) this.getCliente()).getTransportadorCodigo() > 0)
				((Pedido) getPedido()).setTransportadoraCodigo(((Cliente) this.getCliente()).getTransportadorCodigo());

			((Pedido) getPedido()).setPrecoBaseCodigo(this.getTabelaPreco().getPrecoBaseCodigo());

			int enderecoEntregaCodigo = this.parametros.getParametro1().getClassificacaoEnderecoPadraoCodigo();
			int enderecoCobrancaCodigo = this.parametros.getParametro1().getClassificacaoEnderecoPadraoCobrancaCodigo();
			if (enderecoCobrancaCodigo == 0)
				enderecoCobrancaCodigo = enderecoEntregaCodigo;

			Enderecos endereco = Enderecos.recuperarEnderecoPadrao((GenericoDAO) this.getDao(),
					this.getCliente().getCodigo(), enderecoEntregaCodigo);

			if (endereco != null)
				((Pedido) getPedido()).setEnderecoEntregaCodigo(endereco.getEnderecosPK().getCodigo());

			/*
			 * Verifica se existe endereço de cobrança - caso seja nulo, buscar endereco de
			 * entrega
			 */
			int enderecoCodigo = endereco.getEnderecosPK().getCodigo();
			endereco = Enderecos.recuperarEnderecoPadrao((GenericoDAO) this.getDao(), this.getCliente().getCodigo(),
					enderecoCobrancaCodigo);
			if (endereco == null)
				((Pedido) getPedido()).setEnderecoCobrancaCodigo(enderecoCodigo);
			else
				((Pedido) getPedido()).setEnderecoCobrancaCodigo(endereco.getEnderecosPK().getCodigo());
			endereco = null;
		}
	}

	private void preencherContaCorrentePedido() {

		if (this.operacaoPedido == OperacaoPedido.Alteracao) {
			return;
		}

		if (((Cliente) this.getCliente()).getContaCorrenteCodigo() > 0) {
			((Pedido) getPedido()).setContaCorrenteCodigo(((Cliente) this.getCliente()).getContaCorrenteCodigo());
		} else if (this.parametros.getParametro1().getContaCorrenteCodigo() > 0) {
			((Pedido) getPedido()).setContaCorrenteCodigo(this.parametros.getParametro1().getContaCorrenteCodigo());
		} else {
			((Pedido) getPedido()).setContaCorrenteCodigo(0);
		}
	}

	/**
	 * Utilizada para preencher os dados de percentual (itens) e valor (pedido) das
	 * comissões apuradas
	 * 
	 * @param itemAux
	 * @param grupoComissaoItem
	 */
	@Deprecated
	private void preencherComissoes(IItemPedidoWeb itemAux, GrupoComissaoItem grupoComissaoItem) {

		double valorComissao1 = this.getPedido().getValorComissao();
		double valorComissao2 = this.getPedido().getValorComissao2();
		double valorComissao3 = this.getPedido().getValorComissao3();
		double valorComissao4 = this.getPedido().getValorComissao4();
		double valorComissao5 = this.getPedido().getValorComissao5();

		itemAux.setPercentualComissao1(grupoComissaoItem.getPercentualComissao1());
		itemAux.setPercentualComissao2(grupoComissaoItem.getPercentualComissao2());
		itemAux.setPercentualComissao3(grupoComissaoItem.getPercentualComissao3());
		itemAux.setPercentualComissao4(grupoComissaoItem.getPercentualComissao4());
		itemAux.setPercentualComissao5(grupoComissaoItem.getPercentualComissao5());

		double valorProduto = itemAux.getQuantidade() * itemAux.getPrecoVenda();

		if (((ParametroVenda2) this.getParametroVenda2()).getFlagComissaoPrecoBruto() == 0) {

			valorProduto += itemAux.getAcrescimoPedidoValor() - itemAux.getDescontoPedidoValor();
		}

		valorComissao1 += Conversao.arredondar(valorProduto * itemAux.getPercentualComissao1() / 100, 2);
		valorComissao2 += Conversao.arredondar(valorProduto * itemAux.getPercentualComissao2() / 100, 2);
		valorComissao3 += Conversao.arredondar(valorProduto * itemAux.getPercentualComissao3() / 100, 2);
		valorComissao4 += Conversao.arredondar(valorProduto * itemAux.getPercentualComissao4() / 100, 2);
		valorComissao5 += Conversao.arredondar(valorProduto * itemAux.getPercentualComissao5() / 100, 2);

		this.getPedido().setValorComissao(valorComissao1);
		this.getPedido().setValorComissao2(valorComissao2);
		this.getPedido().setValorComissao3(valorComissao3);
		this.getPedido().setValorComissao4(valorComissao4);
		this.getPedido().setValorComissao5(valorComissao5);
	}

	/**
	 * Recupera endereços ativos do cliente
	 */
	public void recuperarEnderecosCliente() {
		this.enderecosCliente = Enderecos.recuperarEnderecosAtivosPessoa((GenericoDAO) this.getDao(),
				this.getCliente().getCodigo());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * preencherFlagClientePrePago()
	 */
	@Override
	public void preencherFlagClientePrePago() {
		this.setFlagVendaConsumidor(false);
		if (this.getCliente() != null) {
			((Pedido) this.getPedido()).setFlagPedidoPrePago(((Cliente) this.getCliente()).getFlagClientePrePago());

			if (((Cliente) this.getCliente()).getFlagClientePrePago() == 1 && gerenteLogin.isPerfilCliente()) {
				this.setFlagVendaConsumidor(true);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * preencherEnderecosCliente()
	 */
	@Override
	public void preencherEnderecosCliente() {
		// TODO Auto-generated method stub
		super.preencherEnderecosCliente();

		if (this.getCliente() != null) {
			this.recuperarEnderecosCliente();
		} else {
			if (this.enderecosCliente != null)
				this.enderecosCliente.clear();
		}

		Enderecos endEntrega = Enderecos.localizarClassificacao(this.enderecosCliente,
				parametros.getParametro1().getClassificacaoEnderecoPadraoCodigo());

		/*
		 * Endereco de entrega padrão
		 */
		if (endEntrega != null)
			this.getPedido().setEnderecoEntregaCodigo(endEntrega.getCodigo());
		else if (ListUtil.isValida(this.enderecosCliente))
			this.getPedido().setEnderecoEntregaCodigo(this.enderecosCliente.get(0).getCodigo());

		/*
		 * Endereço de cobrança padrão
		 */
		Enderecos endCobranca = Enderecos.localizarClassificacao(this.enderecosCliente,
				parametros.getParametro1().getClassificacaoEnderecoPadraoCobrancaCodigo());

		if (endCobranca != null) {
			this.getPedido().setEnderecoCobrancaCodigo(endCobranca.getCodigo());
		} else if (endEntrega != null) {
			this.getPedido().setEnderecoCobrancaCodigo(endEntrega.getCodigo());
		} else if (ListUtil.isValida(this.enderecosCliente)) {
			this.getPedido().setEnderecoCobrancaCodigo(this.enderecosCliente.get(0).getCodigo());
		}
	}

	/*
	 * Recupera campos de limite de crédito
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * recuperarCamposLimiteCredito()
	 */
	@Override
	protected void recuperarCamposLimiteCredito() {

		Cliente cliente = (Cliente) this.getCliente();
		cliente.recuperarLimite((GenericoDAO) this.getDao());

		/*
		 * Ajusta os valores de limite de crédito e valor débito
		 */
		cliente.setLimiteCredito(cliente.getLimiteCredito() + cliente.getLimiteAdicional());
		cliente.setValorDebito(cliente.getValorDebito() + cliente.getValorDebitoPrevisto());

		/*
		 * Se for alteração, pedido anterior diminui o valor de débito previsto
		 */
		if (this.getOperacaoPedido() == OperacaoPedido.Alteracao) {
			if (this.pedidoEmAlteracao.getFlagConsisteLimite() == 1)
				cliente.setLimiteCredito(cliente.getLimiteCredito() + this.pedidoEmAlteracao.getValor());
		}
	}

	/**
	 * Recupera grupo de Comissão correspondente ao Item de Pedido, pesquisando na
	 * lista
	 * 
	 * @param itemPedido
	 * @return
	 */
	private GrupoComissao recuperarGrupoComissao(IItemPedidoWeb itemPedido) {
		GrupoComissao grupoComissao = null;

		if (itemPedido.getGrupoComissaoCodigo() != null && itemPedido.getGrupoComissaoCodigo() > 0) {

			this.grupoComissaoPesquisa.setCodigo(itemPedido.getGrupoComissaoCodigo());

			int indice = Collections.binarySearch(this.gruposComissao, this.grupoComissaoPesquisa,
					GrupoComissao.comparatorCodigo);

			if (indice > -1)
				grupoComissao = this.gruposComissao.get(indice);
		}

		return grupoComissao;
	}

	/**
	 * Na WEB não haverá renumeração de itens
	 */
	@Override
	protected void renumerarItens() {
		return;
	}

	/**
	 * Reserva estoque pendente de confirmação
	 * 
	 * @throws Exception
	 * 
	 */
	@Override
	protected void reservarEstoqueConfirmacao(IItemPedido itemPedido, boolean estornarAnterior) {
		if (estornarAnterior)
			estornarEstoqueConfirmacao(itemPedido, estornarAnterior);

		this.gravarEstoque((IItemPedidoWeb) itemPedido, 7001, false);
		((IItemPedidoWeb) itemPedido).setStatusBaixa("C");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * estornarEstoqueConfirmacao
	 * (br.com.space.api.negocio.modelo.dominio.IItemPedido, boolean)
	 */
	@Override
	protected void estornarEstoqueConfirmacao(IItemPedido itemPedido, boolean estornarAnterior) {
		this.gravarEstoque((IItemPedidoWeb) itemPedido, 12001, estornarAnterior);
	}

	/**
	 * Estorna o estoque pendente de confirmação e reserva o estoque pendente de
	 * entrega.
	 */
	@Override
	@Deprecated
	protected void reservarEstoqueEntrega(IItemPedido itemPedido) {

		/*
		 * this.gravarEstoque((IItemPedidoWeb) itemPedido, 11001, false);
		 * ((IItemPedidoWeb) itemPedido).setStatusBaixa("R");
		 */
		reservarEstoqueEntrega((IItemPedidoWeb) itemPedido, this.estoque);
	}

	public static void reservarEstoqueEntrega(IItemPedidoWeb itemPedido, Estoque estoque) {

		gravarEstoque(estoque, itemPedido, 11001, false);

		itemPedido.setStatusBaixa("R");
	}

	/**
	 * Retorna grupo de comissão referente ao produto -
	 * grupo/subgrupo/categoria/subcategoria/produtofilial
	 * 
	 * @param item
	 * @return
	 */
	@Deprecated
	protected int retornarGrupoComissaoProduto(IItemPedidoWeb item) {

		int grupoComissao = 0;
		ResultSet resultGrupo = null;
		String sql = "select ";
		String sufixo = "gcocodigo";

		if (item.getOfertaNumero() != 0)
			sufixo = sufixo + "o";

		sufixo = sufixo + ((NaturezaOperacao) this.getNaturezaOperacao()).getTipoVenda().toLowerCase();

		sql = sql + "pfi_" + sufixo + " as grupoproduto, grp_" + sufixo + " as grupogrupo, sgp_" + sufixo
				+ " as gruposubgrupo, ctp_" + sufixo + " as grupocategoria, scp_" + sufixo
				+ " as gruposubcategoria from produto "
				+ " left join produtofilial on pro_codigo = pfi_procodigo and pfi_filcodigo = " + item.getFilialCodigo()
				+ " left join grupopro on pro_grpcodigo = grp_codigo "
				+ " left join subgrupopro on pro_sgpcodigo = sgp_codigo "
				+ " left join categoriapro on pro_ctpcodigo = ctp_codigo "
				+ " left join subcategpro on pro_scpcodigo = scp_codigo " + "where pfi_procodigo = "
				+ item.getProdutoCodigo();

		try {
			resultGrupo = ((GenericoDAO) this.getDao()).listResultSet(sql, null);
			if (resultGrupo.first()) {
				if (Conversao.nvlInteger(resultGrupo.getInt("grupoproduto"), 0) > 0) {
					grupoComissao = resultGrupo.getInt("grupoproduto");
				} else if (Conversao.nvlInteger(resultGrupo.getInt("gruposubcategoria"), 0) > 0) {
					grupoComissao = resultGrupo.getInt("gruposubcategoria");
				} else if (Conversao.nvlInteger(resultGrupo.getInt("grupocategoria"), 0) > 0) {
					grupoComissao = resultGrupo.getInt("grupocategoria");
				} else if (Conversao.nvlInteger(resultGrupo.getInt("gruposubgrupo"), 0) > 0) {
					grupoComissao = resultGrupo.getInt("gruposubgrupo");
				} else if (Conversao.nvlInteger(resultGrupo.getInt("grupogrupo"), 0) > 0) {
					grupoComissao = resultGrupo.getInt("grupogrupo");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(resultGrupo);
		}

		return grupoComissao;

	}

	/**
	 * 
	 * Retorna grupo de comissão da regra de comissão
	 * 
	 * @param item
	 * @return
	 */
	@Deprecated
	protected int retornarGrupoComissaoPromocao(IItemPedidoWeb item) {

		int grupoComissao = 0;

		if (item.getOfertaNumero() != 0) {

			if (this.getNaturezaOperacao().getTipoVenda().toUpperCase().equals("I")) {

				grupoComissao = item.getPromocao().getGrupoComissaoOfertaInterna();
			} else if (this.getNaturezaOperacao().getTipoVenda().toUpperCase().equals("E")) {
				grupoComissao = item.getPromocao().getGrupoComissaoOfertaExterna();
			}

		} else {
			if (this.getNaturezaOperacao().getTipoVenda().toUpperCase().equals("I")) {
				grupoComissao = item.getPromocao().getGrupoComissaoNormalInterna();
			} else if (this.getNaturezaOperacao().getTipoVenda().toUpperCase().equals("E")) {
				grupoComissao = item.getPromocao().getGrupoComissaoNormalExterna();
			}
		}

		if (grupoComissao == 0) {
			grupoComissao = this.retornarGrupoComissaoProduto(item);
		}
		return grupoComissao;
	}

	/**
	 * Baixa o estoque pendente de entrega e o estoque físico.
	 */
	@Override
	protected void separarEstoqueEntrega(IItemPedido itemPedido) {
		this.gravarEstoque((IItemPedidoWeb) itemPedido, 1001, false);
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@Override
	public double getValorDebitoCreditoPedidoAnterior() {
		/*
		 * Retorna valor apenas para operação de alteração, desde que flag debito
		 * credito seja 1
		 */
		if (this.pedidoEmAlteracao == null || this.getOperacaoPedido() != OperacaoPedido.Alteracao
				|| this.pedidoEmAlteracao.getFlagDebitoCredito() == 0)
			return 0;
		else
			return this.pedidoEmAlteracao.getValorVendaDebitoCredito()
					- this.pedidoEmAlteracao.getValorSugeridoDebitoCredito();
	}

	/*
	 * 
	 */
	@Override
	public void setNaturezaOperacao(INaturezaOperacao naturezaOperacao) {
		if (getNaturezaOperacao() == null || (naturezaOperacao != null
				&& !naturezaOperacao.getCodigo().equals(getNaturezaOperacao().getCodigo()))) {

			super.setNaturezaOperacao(naturezaOperacao);

			if (getCliente() != null) {
				carregarRegrasCliente();
			}

			if (naturezaOperacao != null)
				this.getPedido().setNaturezaOperacaoCodigo(naturezaOperacao.getCodigo());
			else
				this.getPedido().setNaturezaOperacaoCodigo("");
		}
	}

	@Override
	public void setTabelaPreco(ITabelaPreco tabelaPreco) {
		super.setTabelaPreco(tabelaPreco);

		this.carregarCondicoesPagamento();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * validarAcrescimoDescontoPedido()
	 */
	@Override
	public void validarAcrescimoDescontoPedido() throws PedidoAcrescimoMaximoExcecao, PedidoDescontoMaximoExcecao {

		/*
		 * Calcular o acréscimo / desconto / Retirar o Valor do frete para realiza o
		 * calculo
		 */

		double acrescimoDesconto = Conversao.arredondar(
				(getValorTotalPedidoSemFrete() - this.getValorTotalSugerido()) * 100 / this.getValorTotalSugerido(), 2);

		if (acrescimoDesconto != 0)
			if (acrescimoDesconto > 0) {
				/* Acréscimo */
				if (acrescimoDesconto > this.getCliente().getAcrescimoMaximo()
						&& this.getCliente().getAcrescimoMaximo() > 0) {

					this.verificarAutorizacao(new PedidoAcrescimoMaximoClienteExcecao(getPropriedade(), getPedido(),
							getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
							this.getCliente().getAcrescimoMaximo()));

				}

				if (acrescimoDesconto > this.getFormaPagamento().getAcrescimoMaximo()
						&& this.getFormaPagamento().getAcrescimoMaximo() > 0) {

					this.verificarAutorizacao(new PedidoAcrescimoMaximoFormaPagamentoExcecao(getPropriedade(),
							getPedido(), getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
							this.getFormaPagamento().getAcrescimoMaximo()));
				}

				if (acrescimoDesconto > this.getCondicaoPagamento().getAcrescimoMaximo()
						&& this.getCondicaoPagamento().getAcrescimoMaximo() > 0) {

					this.verificarAutorizacao(new PedidoAcrescimoMaximoCondicaoPagamentoExcecao(getPropriedade(),
							getPedido(), getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
							this.getFormaPagamento().getAcrescimoMaximo()));

				}

				if (acrescimoDesconto > this.getVendedor().getMargemNegociacaoAcima()
						&& this.getVendedor().getMargemNegociacaoAcima() > 0) {

					this.verificarAutorizacao(new PedidoAcrescimoMaximoVendedorExcecao(getPropriedade(), getPedido(),
							getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
							this.getVendedor().getMargemNegociacaoAcima()));

				}
			} else {
				double descontoPromocao = this.verificarDescontoMaximoPromocao();

				if (descontoPromocao > 0 && Math.abs(acrescimoDesconto) > descontoPromocao) {

					this.verificarAutorizacao(new PedidoDescontoMaximoPromocaoExcecao(getPropriedade(), getPedido(),
							getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
							this.getCliente().getDescontoMaximo()));
				} else {
					/* Desconto */
					if (Math.abs(acrescimoDesconto) > this.getCliente().getDescontoMaximo()
							&& this.getCliente().getDescontoMaximo() > 0) {

						this.verificarAutorizacao(new PedidoDescontoMaximoClienteExcecao(getPropriedade(), getPedido(),
								getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
								this.getCliente().getDescontoMaximo()));
					}
					if (Math.abs(acrescimoDesconto) > this.getFormaPagamento().getDescontoMaximo()
							&& this.getFormaPagamento().getDescontoMaximo() > 0) {

						this.verificarAutorizacao(new PedidoDescontoMaximoFormaPagamentoExcecao(getPropriedade(),
								getPedido(), getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
								this.getFormaPagamento().getDescontoMaximo()));
					}
					if (Math.abs(acrescimoDesconto) > this.getCondicaoPagamento().getDescontoMaximo()
							&& this.getCondicaoPagamento().getDescontoMaximo() > 0) {

						this.verificarAutorizacao(new PedidoDescontoMaximoCondicaoPagamentoExcecao(getPropriedade(),
								getPedido(), getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
								this.getCondicaoPagamento().getDescontoMaximo()));

					}
					if (Math.abs(acrescimoDesconto) > this.getVendedor().getMargemNegociacaoAbaixo()
							&& this.getVendedor().getMargemNegociacaoAbaixo() > 0) {

						this.verificarAutorizacao(new PedidoDescontoMaximoVendedorExcecao(getPropriedade(), getPedido(),
								getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento(),
								this.getVendedor().getMargemNegociacaoAbaixo()));
					}
				}
			}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * validarCondicaoPagamento()
	 */
	@Override
	public void validarCondicaoPagamento(ICondicaoPagamento condicaoPagamento)
			throws PedidoCondicaoPagamentoInvalidaExcecao, PedidoCondicaoPagamentoNaoPermitidaFormaPagamentoExcecao,
			PedidoCondicaoPagamentoPrazoMaximoExcecao, PedidoCondicaoPagamentoMaximoParcelasExcecao,
			PedidoCondicaoPagamentoClienteExcecao, PedidoPrazoEspecialPedidoAcimadoClienteExcecao,
			ClientePrazoMaximoExcecao {

		super.validarCondicaoPagamento(condicaoPagamento);

		/*
		 * Verifica se o número de parcelas da condição de pagamento ultrapassou o
		 * número máximo de parcelas da forma de pagamento.
		 */
		FormaPagamento fpg = (FormaPagamento) this.getFormaPagamento();

		if (fpg != null) {

			CondicaoPagamento cpg = (CondicaoPagamento) condicaoPagamento;

			if (cpg != null && !fpg.isCondicaoPermitidaForma((GenericoDAO) getDao(), condicaoPagamento.getCodigo())) {
				throw new PedidoCondicaoPagamentoNaoPermitidaFormaPagamentoExcecao(getPropriedade());
			}

			if (fpg.getNumeroMaximoParcelas() > 0) {
				if (cpg != null && cpg.getNumeroParcelas() > fpg.getNumeroMaximoParcelas()) {

					throw new PedidoCondicaoPagamentoMaximoParcelasExcecao(this.getPropriedade(),
							fpg.getNumeroMaximoParcelas());
				}
			}
		}

		/*
		 * Veifica se a Condição de pagamento é permitida para o cliente.
		 */
		if (this.getCliente() != null && condicaoPagamento != null) {

			if (!isCodicaoPagamentoPermitida(condicaoPagamento)) {
				throw new PedidoCondicaoPagamentoClienteExcecao(this.getPropriedade());
			}
		}
	}

	private boolean isCodicaoPagamentoPermitida(ICondicaoPagamento condicaoPagamento) {

		return condicaoPagamento != null && isCodicaoPagamentoPermitida(condicaoPagamento.getCodigo());

	}

	/**
	 * Verifica se o codigo da condição de pagamento em parametro esta presente na
	 * {@link #getCondicoesPagamentoPermitidas() codiçoes permitidas}.
	 * 
	 * <br>
	 * Segue a mesma filosofia do validarCondicaoPagamento pois se a lista não tem
	 * registros a condição em parametro não é permitida Veja o historico da
	 * implementa no dia 10/07/2014 Renato em 11/07/2014
	 * 
	 * @param condicaoPagamentoCodigo
	 * @return
	 */
	private boolean isCodicaoPagamentoPermitida(int condicaoPagamentoCodigo) {

		if (!ListUtil.isValida(getCondicoesPagamentoPermitidas())) {
			return false;
		}

		return this.pesquisarCondicaoPagamentoLista(this.getCondicoesPagamentoPermitidas(),
				condicaoPagamentoCodigo) != null;

	}

	@Override
	public void validarPrazoCliente(ICliente cliente, ICondicaoPagamento condicaoPagamento)
			throws ClientePrazoMaximoExcecao, PedidoCondicaoPagamentoPrazoMaximoExcecao {

		/*
		 * Verifica se a natureza de operação valida prazo máximo do cliente e do
		 * parametro. Existe um parametro para identificar se valida o prazo do cliente
		 * ou do parametro.
		 */
		Cliente clienteAux = (Cliente) this.getCliente();
		ParametroVenda2 paramVenda2 = (ParametroVenda2) this.parametros.getParametroVenda2();
		if (paramVenda2.getFlagUtilizaPrazoMaximo() == 1) {
			int prazoMaximo = -1;

			if (paramVenda2.getFlagPrazoMaximoClienteParametro().equals("C") && clienteAux != null) {
				super.validarPrazoCliente(cliente, condicaoPagamento);
				// prazoMaximo = clienteAux.getPrazoMaximo();
			} else {
				if (paramVenda2.getFlagPrazoMaximoClienteParametro().equals("P"))
					prazoMaximo = paramVenda2.getPrazoMaximo();

				if (prazoMaximo > -1
						&& getPrazoPedido(condicaoPagamento) - this.getPedido().getPrazoPromocao() > prazoMaximo)
					throw new PedidoCondicaoPagamentoPrazoMaximoExcecao(this.getPropriedade(), prazoMaximo);
			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.api.negocio.modelo.negocio.GerentePedido#validarDadosEntrega ()
	 */
	@Override
	public void validarDadosEntrega() throws EntregaExcecao, EntregaTurnoExcecao {

		super.validarDadosEntrega();

		if (((ParametroVenda) this.getParametroVenda()).getFlagTipoVendaObrigatorio() == 1
				&& this.getPedido().getTipoVendaCodigo() == 0) {
			// TODO - Tipo de Venda Obrigatório
		}

		/*
		 * Verifica se é obrigatorio informar o endereco de entrega.
		 */
		TipoEntrega tpe = TipoEntrega.recuperarCodigo((GenericoDAO) this.getDao(), this.getPedido().getTipoSeparacao());
		if (tpe != null && tpe.getFlagInformaEnderecoEntrega() == 1
				&& this.getPedido().getEnderecoEntregaCodigo() == 0) {

			throw new EntregaEnderecoExcecao(getPropriedade());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * carregarFormasPagamento ()
	 */
	@SuppressWarnings("unchecked")
	@Override
	public <E extends IFormaPagamento> List<E> carregarFormasPagamento() {

		List<E> formasPermitidasSuper = super.carregarFormasPagamento();

		List<E> formasPermitidas = tratarFormasPermitidasModoAcessoCliente(formasPermitidasSuper);

		this.formasPagamentoPermitidas = (List<IFormaPagamento>) formasPermitidas;

		classificarDoucmentosPermitidos(this.getFormasPagamentoPermitidas());

		return formasPermitidas;
	}

	private <E extends IFormaPagamento> List<E> tratarFormasPermitidasModoAcessoCliente(List<E> formasAVerificar) {

		if (!gerenteLogin.isPerfilCliente() || getCliente() == null) {
			return formasAVerificar;
		}

		List<E> formasPermitidas = new ArrayList<E>();

		for (E forma : formasAVerificar) {

			if (!((FormaPagamento) forma).isBloqueadaModoCliente()) {
				formasPermitidas.add(forma);
			}
		}

		return formasPermitidas;
	}

	private <E extends IFormaPagamento> void classificarDoucmentosPermitidos(List<E> formasPagamentoPermitidas) {
		classificacoesDocumentoPermitidas = new ArrayList<ClassificaDocumento>();

		for (E forma : formasPagamentoPermitidas) {
			FormaPagamento fpgAux = (FormaPagamento) forma;

			if (ClassificaDocumento.pesquisarClassificaDocumentoLista(classificacoesDocumentoPermitidas,
					fpgAux.getClassificaDocumento().getCodigo()) == null) {
				classificacoesDocumentoPermitidas.add(fpgAux.getClassificaDocumento());
			}
		}

	}

	/**
	 * 
	 * @return
	 */
	public List<ClassificaDocumento> getclassificacoesDocumentoPermitidas() {
		return this.classificacoesDocumentoPermitidas;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * validarFormaPagamento ()
	 */
	@Override
	public void validarFormaPagamento(IFormaPagamento formaPagamento) throws PedidoFormaPagamentoInvalidaExcecao,
			PedidoOpcaoEspecialNaoPermitidaFormaPagamentoExcecao, PedidoFormaPagamentoClienteExcecao {

		super.validarFormaPagamento(formaPagamento);

		/*
		 * Veifica se a forma de pagamento é permitida para o cliente.
		 */
		if (this.getCliente() != null && formaPagamento != null) {

			FormaPagamento formaAux = null;

			if (this.getFormasPagamentoPermitidas() != null) {

				formaAux = (FormaPagamento) this.pesquisarFormaPagamentoLista(this.getFormasPagamentoPermitidas(),
						formaPagamento.getCodigo());
			}

			if (formaAux == null) {
				throw new PedidoFormaPagamentoClienteExcecao(this.getPropriedade());
			}

			/*
			 * FormaPagamentoPessoa fpgPessoa = FormaPagamentoPessoa
			 * .recuperarUnico((GenericoDAO) this.getDao(), this .getCliente().getCodigo(),
			 * formaPagamento.getCodigo());
			 * 
			 * if (fpgPessoa == null) { throw new PedidoFormaPagamentoClienteExcecao(
			 * this.getPropriedade()); }
			 */
		}
	}

	private boolean isFormaPagamentoPermitida(IFormaPagamento formaPagamento) {
		if (formaPagamento == null || !ListUtil.isValida(getFormasPagamentoPermitidas())) {
			return false;
		}

		return pesquisarFormaPagamentoLista(getFormasPagamentoPermitidas(), formaPagamento.getCodigo()) != null;
	}

	/*
	 * Verifica se forma de pagamento consiste limite e efetua a validação
	 */
	@Override
	public void validarLimiteCliente(boolean validandoItem) throws ClienteSaldoInsuficienteExcecao {

		try {
			super.validarLimiteCliente(validandoItem);
		} catch (ClienteSaldoInsuficienteExcecao e) {
			/*
			 * Verifica o parametro se bloqueia(0) ou somente avisa (1) se o cliente não
			 * possuir limite suficiente.
			 */
			if (!validandoItem
					|| ((ParametroVenda) parametros.getParametroVenda()).getFlagConsisteLimiteFinalPedido() == 0) {
				throw e;
			} else {
				this.adicionarAlerta(new AlertaExcecao(e));
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * validarPrecoVendaItem (br.com.space.api.negocio.modelo.dominio.IItemPedido)
	 */
	@Override
	public void validarPrecoVendaItem(IItemPedido itemPedido)
			throws ItemPedidoPrecoMinimoProdutoExcecao, ItemPedidoPrecoMaximoProdutoExcecao,
			ItemPedidoPrecoMinimoClienteExcecao, ItemPedidoPrecoMaximoClienteExcecao,
			ItemPedidoPrecoMinimoVendedorExcecao, ItemPedidoPrecoMaximoVendedorExcecao,
			ItemPedidoPrecoMinimoCondicaoPagamentoExcecao, ItemPedidoPrecoMaximoCondicaoPagamentoExcecao,
			ItemPedidoPrecoMinimoFormaPagamentoExcecao, ItemPedidoPrecoMaximoFormaPagamentoExcecao,
			ItemPedidoPrecoAbaixoCustoExcecao, ItemPedidoPrecoAbaixoMargemCustoExcecao,
			ItemPedidoPrecoMinimoPromocaoExcecao, ItemPedidoPrecoMaximoPromocaoExcecao {

		// TODO - Precificação
		super.validarPrecoVendaItem(itemPedido);

		IItemPedidoWeb item = (IItemPedidoWeb) itemPedido;

		/*
		 * Verifica item sem preço sugerido. JÁ VERIFICA NO VALIDARITEMPEDIDO, SÓ QUE
		 * NÃO UTILIZA PARAMETRO.
		 */
		if (((ParametroVenda2) this.getParametroVenda2()).getFlagBloqueiaItemSemPreco() == 1
				&& item.getPrecoSugerido() == 0) {
			// TODO - Produto sem preço de venda definido
		}

		/*
		 * verifica se a natureza de operação não permitir oferta abaixo do preço de
		 * custo.
		 */
		if (this.getParametroVenda2().getPermiteOfertaAbaixoCusto() == 0 || item.getOfertaNumero() == 0) {

			/*
			 * Verifica se a natureza de operação sugere o preço de venda (2) do
			 * produto.1=Custo, 2=Preco Venda, 3=Negociacao.
			 */
			if (((NaturezaOperacao) this.getNaturezaOperacao()).getConfiguracaoPrecoSugerido() == 2) {

				/*
				 * se o preco de venda está abaixo da margem mínima de custo.
				 */
				String sql = "select pro_codigo, pfi_precominct, scp_precominct, ctp_precominct, "
						+ "sgp_precominct, grp_precominct "
						+ "from produto left join produtofilial on pro_codigo = pfi_procodigo "
						+ "left join subcategpro on pro_scpcodigo = scp_codigo "
						+ "left join categoriapro on pro_ctpcodigo = ctp_codigo "
						+ "left join subgrupopro on pro_sgpcodigo = sgp_codigo "
						+ "left join grupopro on pro_grpcodigo = grp_codigo " + "where pro_codigo = "
						+ item.getProdutoCodigo() + " and pfi_filcodigo = " + this.getFilial().getCodigo();

				ResultSet resultMargem = null;
				double percentualMinimoCusto = 0;

				try {
					resultMargem = ((GenericoDAO) this.getDao()).listResultSet(sql);
					if (resultMargem.first()) {
						if (Conversao.nvlDouble(resultMargem.getDouble("pfi_precominct"), 0.0) > 0)
							percentualMinimoCusto = resultMargem.getDouble("pfi_precominct");
						else if (Conversao.nvlDouble(resultMargem.getDouble("scp_precominct"), 0.0) > 0)
							percentualMinimoCusto = resultMargem.getDouble("scp_precominct");
						else if (Conversao.nvlDouble(resultMargem.getDouble("ctp_precominct"), 0.0) > 0)
							percentualMinimoCusto = resultMargem.getDouble("ctp_precominct");
						else if (Conversao.nvlDouble(resultMargem.getDouble("sgp_precominct"), 0.0) > 0)
							percentualMinimoCusto = resultMargem.getDouble("sgp_precominct");
						else if (Conversao.nvlDouble(resultMargem.getDouble("grp_precominct"), 0.0) > 0)
							percentualMinimoCusto = resultMargem.getDouble("grp_precominct");
					}
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					GenericoDAO.closeResultSet(resultMargem);
				}

				double margem = 0;
				if (percentualMinimoCusto > 0) {
					margem = 1 + (percentualMinimoCusto - Conversao.nvlDouble(
							((CondicaoPagamento) this.getCondicaoPagamento()).getPercentualAbatimentoItem(), 0.0))
							/ 100;

					if (item.getPrecoVenda() < Conversao.arredondar(item.getCusto() * margem,
							this.getParametroVenda().getCasasDecimaisPrecoVenda())) {

						this.verificarAutorizacao(new ItemPedidoPrecoAbaixoMargemCustoExcecao(getPropriedade(),
								itemPedido, itemPedido.getProduto(), getCliente(), getVendedor(), getFormaPagamento(),
								getCondicaoPagamento()), item);
					}
				}
				// TODO - Exceção preço abaixo margem custo - Libera do débito /
				// crédito, se parâmetro Par_PsLibDbCrPr = 1

			}

			if (item.getPrecoVenda() < Conversao.arredondar(item.getCusto(),
					this.getParametroVenda().getCasasDecimaisPrecoVenda())) {
				// TODO - Exceção preço abaixo custo - Libera do débito /
				// crédito, se parâmetro Par_PsLibDbCrPr = 1

				this.verificarAutorizacao(
						new ItemPedidoPrecoAbaixoCustoExcecao(getPropriedade(), itemPedido, itemPedido.getProduto(),
								getCliente(), getVendedor(), getFormaPagamento(), getCondicaoPagamento()),
						item);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * validarPreRequisitosItens()
	 */
	@Override
	public void validarPreRequisitosItens() throws PedidoNaturezaOperacaoInvalidaExcecao,
			PedidoTabelaPrecoInvalidaExcecao, PedidoFormaPagamentoInvalidaExcecao,
			PedidoOpcaoEspecialNaoPermitidaFormaPagamentoExcecao, PedidoCondicaoPagamentoInvalidaExcecao,
			PedidoCondicaoPagamentoNaoPermitidaFormaPagamentoExcecao, PedidoNumeroMaximoItensExcecao,
			PedidoCondicaoPagamentoPrazoMaximoExcecao, PedidoCondicaoPagamentoMaximoParcelasExcecao,
			ClienteNaoEncontradoExcecao, PedidoFormaPagamentoClienteExcecao, PedidoCondicaoPagamentoClienteExcecao,
			PedidoPrazoEspecialPedidoAcimadoClienteExcecao, ClientePrazoMaximoExcecao, VendedorNaoEncontradoExcecao,
			VendedorTipoVendaExcecao, PedidoOpcaoEspecialClienteExcecao {

		super.validarPreRequisitosItens();

		/*
		 * Valida a quantidade máxima de itens da natureza de operação.
		 */

		this.validarNumeroMaximoItensPedido(this.getNaturezaOperacao().getNumeroMaximoItens());

		/*
		 * Cliente deve ser informado no início do pedido.
		 */
		if (this.getCliente() == null) {
			throw new ClienteNaoEncontradoExcecao(getPropriedade());
		}
	}

	@Override
	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * validarNumeroMaximoItensPedido(int)
	 */
	protected void validarNumeroMaximoItensPedido(int quantidadeMaximaItens) throws PedidoNumeroMaximoItensExcecao {

		if (quantidadeMaximaItens > 0 && this.getItensPedido().size() > quantidadeMaximaItens) {
			/*
			 * Exibe o número máximo de itens da natureza de operação, não o do parâmetro
			 * quantidadeMaximaItens (pode vir com 1 a menos)
			 */
			throw new PedidoNumeroMaximoItensExcecao(this.getPropriedade(),
					this.getNaturezaOperacao().getNumeroMaximoItens());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * validarProdutoItemPedido
	 * (br.com.space.api.negocio.modelo.dominio.IItemPedido)
	 */
	@Override
	public void validarProdutoItemPedido(IItemPedido item) throws ProdutoExcecao, ItemPedidoDuplicadoExcecao {

		super.validarProdutoItemPedido(item);

		/*
		 * Valida se o produto está liberado para venda.
		 */
		ProdutoFilial produtoFilial = ProdutoFilial.recuperarUnico((GenericoDAO) this.getDao(),
				this.getFilial().getCodigo(), item.getProdutoCodigo());
		if (produtoFilial.getFlagLiberadoVenda() == 0 || produtoFilial.getFlagInativo() == 1) {
			throw new ProdutoLiberadoVendaExcecao(this.getPropriedade());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#validarFrete()
	 */
	@Override
	public void validarFrete() throws EntregaFreteMinimoRegiaoExcecao {
		Regiao regiao = ((Cliente) this.getCliente()).getRegiao();

		if (((ParametroVenda3) this.parametros.getParametroVenda3()).getFlagCalculaFreteRegiao() == 1) {

			double freteMinimo = this.calcularFreteMinimo();

			if (getPedido().getFrete() < freteMinimo) {
				verificarAutorizacao(new EntregaFreteMinimoRegiaoExcecao(getPropriedade(), getPedido(), getCliente(),
						regiao, getVendedor(), getFormaPagamento(), getCondicaoPagamento(), 1, getPedido().getFrete(),
						freteMinimo));
			}
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#validarValorPedido
	 * ()
	 */
	@Override
	public void validarValorPedido()
			throws PedidoValorMinimoExcecao, PedidoResultadoMinimoExcecao, PedidoResultadoMinimoSemImpostosExcecao {

		super.validarValorPedido();

		if (((CondicaoPagamento) this.getCondicaoPagamento()).getPercentualResultadoMinimo() > 0) {

			double baseResultado = (this.getParametroCusto().getBaseCalculoResultado().equals("C")
					? this.getValorTotalCusto()
					: this.getValorTotalPedido());

			double resultadoPercentual = 0;

			if (baseResultado > 0)
				resultadoPercentual = Conversao.arredondar(this.getValorResultadoPedido() * 100 / baseResultado, 2);
			if (resultadoPercentual < ((CondicaoPagamento) this.getCondicaoPagamento())
					.getPercentualResultadoMinimo()) {
				throw new PedidoResultadoMinimoExcecao(getPropriedade());
			}
		}
	}

	/**
	 * Verifica se existe promoção por valor que aumente faixa de desconto,
	 * retornando faixa de desconto permitida
	 * 
	 * @return
	 */
	private double verificarDescontoMaximoPromocao() {

		ResultSet resultPromocao = null;

		double resultado = 0.0;

		String sqlPromo = "select prm_numero, prm_tprcodigo, prm_perdescit " + " from promocao, natoper, pessoa where "
				+ "(PRM_TPPEDITEM = 1 OR PRM_TPPEDITEM IS NULL) " + " AND prm_situacao <> 'C' and nat_codigo = '"
				+ this.getNaturezaOperacao().getCodigo() + "' and ('"
				+ Conversao.formatarDataAAAAMMDD(this.getPedido().getDataEmissao())
				+ "' between prm_dataini and prm_datafim) " + " and pes_codigo = " + this.getPedido().getPessoaCodigo()
				+ " and (" + Conversao.arredondar(this.getValorTotalSugerido(), 2)
				+ " between prm_faixaini and prm_faixafim)"
				+ " and (((prm_interna = 1 or prm_interna is null) and nat_tipovenda = 'I')"
				+ " or ((prm_externa = 1 or prm_externa is null) and nat_tipovenda = 'E'))"
				+ " and ((prm_fisico = 1 and pes_tipopessoa = 'F') or (prm_juridico = 1 and pes_tipopessoa = 'J'))"
				+ " and prm_filcodigo = " + this.getFilial().getCodigo() + " and prm_tipoprom = 'M' ";

		try {
			resultPromocao = ((GenericoDAO) this.getDao()).listResultSet(sqlPromo, null, null);

			while (resultPromocao.next()) {
				if (Conversao.nvlInteger(resultPromocao.getInt("prm_tprcodigo"), 0) == 0
						|| Conversao.nvlInteger(resultPromocao.getInt("prm_tprcodigo"), 0) == this.getPedido()
								.getTabelaPrecoCodigo()) {
					resultado = Conversao.nvlDouble(resultPromocao.getDouble("prm_perdescit"), 0.0);
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(resultPromocao);
		}

		return resultado;
	}

	/**
	 * Verifica faixa de comissão, dependendo do grupo de comissao, do resultado ou
	 * acréscimo / desconto
	 * 
	 * @param grupoComissao
	 * @param resultado     - lucro ou acréscimo / desconto
	 * @return
	 */
	private GrupoComissaoItem verificarFaixaComissao(GrupoComissao grupoComissao, double resultado) {

		for (GrupoComissaoItem grupoComissaoItem : this.gruposComissaoItem) {

			if (grupoComissaoItem.getGrupocomisitPK().getGrupoComissaoCodigo() == grupoComissao.getCodigo()) {

				if (grupoComissao.getTipoComissao().equals("L")) {

					if (resultado >= grupoComissaoItem.getFaixaInicial()
							&& resultado <= grupoComissaoItem.getFaixaFinal())

						return grupoComissaoItem;

				} else {
					if (resultado >= 0.01) {
						/* Acréscimo */
						if (resultado >= grupoComissaoItem.getFaixaInicial()
								&& resultado <= grupoComissaoItem.getFaixaFinal()
								&& grupoComissaoItem.getTipo().equals("A"))

							return grupoComissaoItem;

					} else {
						/* Desconto */
						if (Math.abs(resultado) >= grupoComissaoItem.getFaixaInicial()
								&& Math.abs(resultado) <= grupoComissaoItem.getFaixaFinal()
								&& grupoComissaoItem.getTipo().equals("D"))

							return grupoComissaoItem;
					}
				}
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * verificarNumeroPedido ()
	 */
	@Override
	@Deprecated
	public void verificarNumeroPedido() throws PedidoExcecao {
		if (this.getPedido().getNumero() == 0) {
			this.gerarSerieProximoPedido();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * verificarNumeroPedidoItem
	 * (br.com.space.api.negocio.modelo.dominio.IItemPedido)
	 */
	@Override
	protected void verificarNumeroPedidoItem(IItemPedido itemPedido) throws PedidoExcecao {
		super.verificarNumeroPedidoItem(itemPedido);

		IItemPedidoWeb itemAux = (IItemPedidoWeb) itemPedido;
		IPedidoWeb pedidoAux = this.getPedido();

		itemAux.setFilialCodigo(pedidoAux.getFilialCodigo());
		itemAux.setSeriePedidoCodigo(pedidoAux.getSerieCodigo());
		itemAux.setPedidoNumero(pedidoAux.getNumero());
	}

	public void voltarNumeracaoPedido() {

		if (getPedido().getNumero() > 0 && isPedidoSemItens()) {

			getPedido().setNumero(0);
			getPedido().setSerieCodigo(null);
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<Enderecos> getEnderecosCliente() {
		if (this.getCliente() != null) {
			this.recuperarEnderecosCliente();
		}

		return enderecosCliente;
	}

	/**
	 * 
	 * @return
	 */
	public INaturezaOperacaoFilial getNaturezaOperacaoFilial() {
		if (this.getNaturezaOperacao() == null) {
			this.naturezaOperacaoFilial = null;
			return null;
		}

		if (this.naturezaOperacaoFilial == null || !this.getNaturezaOperacao().getCodigo()
				.equals(this.naturezaOperacaoFilial.getNaturezaOperacao().getCodigo())) {

			this.naturezaOperacaoFilial = NaturezaOperacaoFilial.recuperarUnico((GenericoDAO) this.getDao(),
					this.getNaturezaOperacao().getCodigo(), this.getFilial().getCodigo());
		}

		return (INaturezaOperacaoFilial) this.naturezaOperacaoFilial;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * validarEstoqueProduto (br.com.space.api.negocio.modelo.dominio.IItemPedido)
	 */
	@Override
	public void validarEstoqueProduto(IItemPedido itemPedido) throws ItemPedidoQuantidadeEstoqueExcecao,
			ItemPedidoQuantidadeEstoqueOutrosExcecao, ItemPedidoQuantidadeEstoqueMinimoExcecao {
		super.validarEstoqueProduto(itemPedido);

		double quantidadeItensProduto = itemPedido.getQuantidade() * itemPedido.getFatorEstoque();

		double quantidadeAnterior = ((IItemPedidoWeb) itemPedido).getQuantidadeAnterior()
				* itemPedido.getFatorEstoque();

		/*
		 * Verifica a quantidade de estoque disponivel.
		 */
		double estoqueDisponivel = this.recuperarEstoqueDisponivelItem((IItemPedidoWeb) itemPedido,
				FlagTipoEstoque.DisponivelVendaGeral);

		if (quantidadeItensProduto > estoqueDisponivel + quantidadeAnterior) {
			try {
				this.verificarAutorizacao(
						new ItemPedidoQuantidadeEstoqueExcecao(getPropriedade(), this.getPedido(), itemPedido,
								itemPedido.getProduto(), this.getCliente(), this.getVendedor(), estoqueDisponivel),
						itemPedido);
			} catch (ItemPedidoQuantidadeEstoqueExcecao ex) {
				throw ex;
			}
		}

		/*
		 * Verifica a quantidade de estoque de outros disponivel.
		 */
		if (((NaturezaOperacao) this.getNaturezaOperacao()).getFlagCalculoFiscal() == 1) {
			double estoqueOutros = this.recuperarEstoqueDisponivelItem((IItemPedidoWeb) itemPedido,
					FlagTipoEstoque.Outros);

			if (quantidadeItensProduto > estoqueOutros + quantidadeAnterior) {
				try {
					this.verificarAutorizacao(
							new ItemPedidoQuantidadeEstoqueOutrosExcecao(getPropriedade(), this.getPedido(), itemPedido,
									itemPedido.getProduto(), this.getCliente(), this.getVendedor(), estoqueOutros),
							itemPedido,
							(((ItemPedido) itemPedido).getProdutoFilial().getFlagNaoFaturarSemEstoque() == 1));
				} catch (ItemPedidoQuantidadeEstoqueOutrosExcecao ex) {
					throw ex;
				}
			}
		}
	}

	@Override
	protected void validarEstoqueMinimoProduto(IItemPedido itemPedido) throws ItemPedidoQuantidadeEstoqueMinimoExcecao {

		Produto produto = (Produto) itemPedido.getProduto();

		double estoqueAtualizado = (produto.getEstoque() / itemPedido.getFatorEstoque());

		estoqueAtualizado -= (itemPedido.getQuantidade() * itemPedido.getFatorEstoque());

		double estoqueMinimo = produto.getProdutoFilial().getEstoqueMinimo() == null ? 0
				: produto.getProdutoFilial().getEstoqueMinimo() / itemPedido.getFatorEstoque();

		if (estoqueAtualizado < estoqueMinimo) {
			throw new ItemPedidoQuantidadeEstoqueMinimoExcecao(itemPedido, getPropriedade(), estoqueMinimo,
					produto.getEstoque(), this);
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#criarItemPedido
	 * (br.com.space.api.negocio.modelo.dominio.IItemKit, double)
	 */
	@Override
	public IItemPedido criarItemPedido(IItemKit itemKit, double quantidade) {

		IItemPedidoWeb itemPedido = criarItemPedido((Produto) itemKit.getProduto(),
				(ProdutoUnidade) itemKit.getProdutoUnidade());

		if (itemPedido == null) {
			return null;
		}

		itemPedido.setKitCodigo(itemKit.getKitCodigo());

		itemPedido.setPrecoLiquido(itemKit.getPreco());
		itemPedido.setPrecoSugerido(itemKit.getPrecoVenda().getPrecoSugerido());
		itemPedido.setPrecoVenda(itemKit.getPreco());
		itemPedido.setPrecoVendaOriginal(itemKit.getPrecoVenda().getPrecoOriginal());

		itemPedido.setQuantidade(itemKit.getQuantidade() * quantidade);

		return itemPedido;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#criarKitPedido(
	 * br.com.space.api.negocio.modelo.dominio.Ikit, double)
	 */
	@Override
	public IKitPedido criarKitPedido(Ikit kit, double quantidade) {

		KitPedido kitPedido = new KitPedido(kit);

		kitPedido.setFilialCodigo(this.getFilial().getCodigo());
		kitPedido.setPreco(kit.getValor());
		kitPedido.setPrecoTabela(kit.getValorTabela());
		kitPedido.setQuantidade(quantidade);

		gravarCustoAoKitPedido(kitPedido, kit.getItemKits());

		return kitPedido;

	}

	@Override
	public IPedidoWeb getPedido() {
		return (IPedidoWeb) super.getPedido();
	}

	/*
	 * public void cancelarPedidoEmInclusao(GenericoDAO dao, Pedido pedido) {
	 * cancelarPedidosEmInclusao(dao, gerenteLogin.getSessao(), filial.getCodigo());
	 * }
	 */

	private int getStatusPadraoOperacao() {

		Integer statusPedido = StatusPedido.STATUS_EM_INCLUSAO;

		if (this.operacaoPedido == OperacaoPedido.Alteracao) {
			statusPedido = StatusPedido.STATUS_EM_ALTERACAO;
		}

		return statusPedido;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.api.negocio.modelo.negocio.GerentePedido#validarStatusPedido ()
	 */
	@Override
	public void validarStatusPedido() throws PedidoStatusSessaoExcecao {
		super.validarStatusPedido();

		int statusPedido = getStatusPadraoOperacao();

		/*
		 * Somente verifica o status do pedido se o mesmo possui um número, esse número
		 * é adicionado quando inclui o primeiro item no pedido.
		 */
		if (getPedido().getNumero() != 0) {
			GenericoDAO dao = (GenericoDAO) this.getDao();
			ResultSet resultSet = null;

			try {
				String sql = "select " + Pedido.COLUNA_STATUS_PEDIDO + " from " + Pedido.NOME_TABELA + " where "
						+ Pedido.COLUNA_FILIAL_CODIGO + " = " + getFilial().getCodigo() + " and "
						+ Pedido.COLUNA_SERIE_CODIGO + " = '" + getPedido().getSerieCodigo() + "' and "
						+ Pedido.COLUNA_NUMERO + " = " + getPedido().getNumero();

				resultSet = dao.listResultSet(sql);

				if (resultSet.first()) {
					statusPedido = resultSet.getInt(Pedido.COLUNA_STATUS_PEDIDO);
				}
			} catch (Exception e) {
				statusPedido = getStatusPadraoOperacao();
				e.printStackTrace();
			} finally {
				if (resultSet != null)
					GenericoDAO.closeResultSet(resultSet);
			}
		}

		if (statusPedido > 0 && getPedido().getStatusPedidoCodigo() != statusPedido) {

			String msg = getPropriedade().getMensagem("alerta.pedido.status.sessao.status", statusPedido,
					getPedido().getStatusPedidoCodigo());
			throw new PedidoStatusSessaoExcecao(msg);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.api.negocio.modelo.negocio.GerentePedido#validarBalancoAtivo ()
	 */
	@Override
	public void validarBalancoAtivo() throws PedidoBalancoAtivoExcecao {

		/*
		 * Verificar Balanço total em Aberto
		 */
		ResultSet result = null;
		int balancoAtivo = 0;
		String sql = "select inv_codigo from inventario where inv_filcodigo = " + this.getFilial().getCodigo()
				+ " and inv_status = 'A' and inv_balparcial = 0";
		try {
			result = ((GenericoDAO) this.getDao()).listResultSet(sql);
			if (result.first()) {
				balancoAtivo = result.getInt("inv_codigo");
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(result);
		}

		if (balancoAtivo > 0) {
			throw new PedidoBalancoAtivoExcecao(this.getPropriedade());
		}

	}

	@Override
	public void validarVendedor(IVendedor iVendedor) throws VendedorNaoEncontradoExcecao, VendedorTipoVendaExcecao {

		Vendedor vendedor = (Vendedor) iVendedor;

		if (vendedor == null || (vendedor.getColaborador() != null && vendedor.getColaborador().getFlagAtivo() == 0)) {
			throw new VendedorNaoEncontradoExcecao(getPropriedade());
		}

		super.validarVendedor(vendedor);
	}

	/**
	 * Verifica pedidos em inclusão abandonados e os cancela
	 * 
	 * @param dao
	 * @param sessao
	 */
	public static void cancelarPedidosEmInclusao(GenericoDAO dao, Sessao sessao, int filialCodigo,
			String obsCancelamento) {

		List<Pedido> pedidosEmAberto = Pedido.recuperarPedidosEmInclusao(dao, sessao.getCodigo(), filialCodigo);

		Estoque estoque = null;

		if (pedidosEmAberto != null && pedidosEmAberto.size() > 0) {

			for (Pedido pedido : pedidosEmAberto) {

				if (estoque == null || (estoque.getFilialCodigo() != pedido.getFilialCodigo())) {

					estoque = Fabrica.gerarEstoque(dao, pedido.getFilialCodigo());

				}

				cancelarPedidoEmInclusao(dao, pedido, estoque, sessao, obsCancelamento);
			}
		}
	}

	/**
	 * Cancela pedido em inclusão
	 * 
	 * @param dao
	 * @param pedido
	 * @param estoque
	 * @param sessao
	 */
	public static void cancelarPedidoEmInclusao(GenericoDAO dao, Pedido pedido, Estoque estoque, Sessao sessao,
			String obsCancelamento) {

		if (pedido == null || pedido.getStatusPedidoCodigo() != StatusPedido.STATUS_EM_INCLUSAO) {
			return;
		}

		try {

			dao.beginTransaction();

			cancelarPedido(dao, pedido, estoque, StatusPedido.STATUS_CANCELADO_INCLUSAO);

			inserirLogPedido(dao, pedido, sessao,
					Propriedade.getMensagemUtilizadoSessao("texto.logPedido.pedidoCancelaInclusao"), null,
					obsCancelamento);

			dao.commitTransaction();

		} catch (Exception e) {

			dao.rollBackTransaction();
		}
	}

	public static void cancelarPedido(GenericoDAO dao, Pedido pedido, int novoStatusPedido) {

		cancelarPedido(dao, pedido, Fabrica.gerarEstoque(dao, pedido.getFilialCodigo()), novoStatusPedido);

	}

	public static void cancelarPedido(GenericoDAO dao, Pedido pedido, Estoque estoque, int novoStatusPedido) {

		atualizaStatusPedidoEItens(dao, pedido, estoque, novoStatusPedido, "X", 12001);

		/*
		 * List<ItemPedido> itemPedidos = ItemPedido.recuperarItensPedido(dao,
		 * pedido.getNumero(), pedido.getSerieCodigo(), pedido.getFilialCodigo());
		 * 
		 * for (ItemPedido itemPedido : itemPedidos) {
		 * 
		 * if (pedido.isAtualizaEstoque() && itemPedido.getStatusBaixa().equals("C")) {
		 * 
		 * gravarEstoque(estoque, itemPedido, 12001, false);
		 * 
		 * itemPedido.setStatusBaixa("X");
		 * 
		 * dao.update(itemPedido); } }
		 * 
		 * pedido.setStatusPedidoCodigo(novoStatusPedido);
		 * 
		 * dao.update(pedido);
		 */

		if (pedido.getLoteLancamentoFinanceiro() > 0) {
			GerenteFinanceiro.cancelarLoteFinanceiro(dao, pedido);
		}
	}

	public static void confirmarPedido(GenericoDAO dao, Pedido pedido) {
		confirmarPedido(dao, pedido, Fabrica.gerarEstoque(dao, pedido.getFilialCodigo()));
	}

	public static void confirmarPedido(GenericoDAO dao, Pedido pedido, Estoque estoque) {

		atualizaStatusPedidoEItens(dao, pedido, estoque, StatusPedido.STATUS_NAO_SEPARADO, "R", 11001);
	}

	public static void atualizaStatusPedidoEItens(GenericoDAO dao, Pedido pedido, Estoque estoque, int novoStatusPedido,
			String novoStatusBaixaItem, int tipoMovimentacaoEstoqueCodigo) {

		if (pedido.isAtualizaEstoque()) {

			List<ItemPedido> itemPedidos = ItemPedido.recuperarItensPedido(dao, pedido.getNumero(),
					pedido.getSerieCodigo(), pedido.getFilialCodigo());

			for (ItemPedido itemPedido : itemPedidos) {

				gravarEstoque(estoque, itemPedido, tipoMovimentacaoEstoqueCodigo, false);

				itemPedido.setStatusBaixa(novoStatusBaixaItem);

				dao.update(itemPedido);

			}
		}

		pedido.setStatusPedidoCodigo(novoStatusPedido);

		dao.update(pedido);
	}

	/**
	 * Insere log Pedido
	 * 
	 * @param dao
	 * @param pedido
	 * @param sessao
	 * @param obs
	 */
	public static void inserirLogPedido(GenericoDAO dao, IPedidoWeb pedido, Sessao sessao, String obs,
			String obsComplementar, String observacaoCancelamento) {

		inserirLogPedido(dao, pedido, sessao.getCodigo(), sessao.getUsuario(), obs, obsComplementar,
				observacaoCancelamento);
	}

	/**
	 * Insere log Pedido
	 * 
	 * @param dao
	 * @param pedido
	 * @param sessaoCodigo
	 * @param usuarioLogin
	 * @param obs
	 */
	@Deprecated
	public static void inserirLogPedido(GenericoDAO dao, IPedidoWeb pedido, int sessaoCodigo, String usuarioLogin,
			String obs, String obsComplementar, String observacaoCancelamento) {

		if (!StringUtil.isValida(obs)) {
			return;
		}

		String observacao = obs + " - " + NomeSistema.getNome(CodigoSistema.VENDA_WEB) + " -";

		if (StringUtil.isValida(obsComplementar)) {
			observacao += " " + obsComplementar;
		}

		LogPedido logPedido = new LogPedido(pedido.getSerieCodigo(), pedido.getNumero(), Sistema.obterData(),
				usuarioLogin, observacao.toUpperCase(), pedido.getFilialCodigo(), sessaoCodigo, Sistema.obterHora(),
				pedido.getStatusPedidoCodigo());

		logPedido.setObservacaoCancelamento(observacaoCancelamento);

		dao.insertObject(logPedido);

	}

	/**
	 * Método para relizar gravação de estoque
	 * 
	 * @param estoque
	 * @param itemPedido
	 * @param tipoMovimentacaoEstoqueCodigo
	 * @param usaQuantidadeAnterior
	 */
	private static void gravarEstoque(Estoque estoque, IItemPedidoWeb itemPedido, int tipoMovimentacaoEstoqueCodigo,
			boolean usaQuantidadeAnterior) {

		try {
			estoque.limpar();
			estoque.setFilialCodigo(itemPedido.getFilialCodigo());
			estoque.setProdutoCodigo(itemPedido.getProdutoCodigo());

			if (itemPedido.getLocalEstoqueCodigo() != null) {
				estoque.setLocalEstoqueCodigo(itemPedido.getLocalEstoqueCodigo());
				if (itemPedido.getLote() != null)
					estoque.setLote(itemPedido.getLote());
			}

			estoque.setTipoMovimentoEstoqueCodigo(tipoMovimentacaoEstoqueCodigo);

			double quantidade = usaQuantidadeAnterior ? itemPedido.getQuantidadeAnterior() : itemPedido.getQuantidade();

			estoque.setQuantidade(quantidade * itemPedido.getFatorEstoque());

			estoque.gravarEstoque();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage());
		}
	}

	/**
	 * 
	 */
	public void validarTravamentoPedido() {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerentePedido#
	 * finalizarAlteracaoPedido()
	 */
	@Override
	protected void finalizarAlteracaoPedido() {

		/*
		 * Clona objetos para depois serem apagados no banco
		 */
		PedidoAuxiliar pedidoAux = null;
		ArrayList<IItemPedido> itensPedidoAux = null;
		try {
			pedidoAux = (PedidoAuxiliar) this.getPedido().clone();
			itensPedidoAux = (ArrayList<IItemPedido>) this.getItensPedido().clone();
		} catch (CloneNotSupportedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		/*
		 * Retorna limite de crédito
		 */
		if (this.pedidoEmAlteracao.getFlagConsisteLimite() == 1) {
			((GenericoDAO) getDao()).executeUpdate(
					"update cliente set cli_valdebitopr = cli_valdebitopr - " + this.pedidoEmAlteracao.getValor()
							+ " where cli_pescodigo = " + this.pedidoEmAlteracao.getPessoaCodigo());

		}

		/*
		 * Apagar Itens anteriores no banco (ItemPedido)
		 */
		for (IItemPedido itemPedido : this.itensPedidoEmAlteracao) {
			this.deletarItemPedido(itemPedido);
		}

		/*
		 * Cria lista de Itenspedido através da lista itensPedido() (que possui
		 * elementos de ItensPedidoAuxiliar ) e grava no banco em ItensPedido
		 */
		ArrayList<IItemPedido> itensPedidoAuxiliar = new ArrayList<IItemPedido>();

		for (IItemPedido itemPedido : this.getItensPedido()) {
			ItemPedidoAuxiliar itemAux = (ItemPedidoAuxiliar) itemPedido;
			ItemPedido itemAux2 = new ItemPedido();

			copiarItemPedido(itemAux, itemAux2);

			((GenericoDAO) this.getDao()).insert(itemAux2);
			itensPedidoAuxiliar.add(itemAux2);
		}

		/*
		 * Zerar lista e preencher getItensPedido com itens ItemPedido
		 */
		this.getItensPedido().clear();
		for (IItemPedido itemPedido : itensPedidoAuxiliar) {
			this.getItensPedido().add(itemPedido);
		}

		/*
		 * Atualizar Pedido Original
		 */
		this.copiarPedido(this.getPedido(), this.pedidoEmAlteracao);
		((GenericoDAO) this.getDao()).update(this.pedidoEmAlteracao);

		/*
		 * Limpar dados auxiliares do banco de dados
		 */
		((GenericoDAO) this.getDao()).delete(this.getPedido());
		for (IItemPedido itemPedidoAux : itensPedidoAux) {
			((GenericoDAO) this.getDao()).delete(itemPedidoAux);
		}

		/*
		 * Preenche propriedade com Pedido Correto
		 */
		this.setPedido(this.pedidoEmAlteracao);

		/*
		 * Zera pedido em alteracao e itensEmAlteracao
		 */
		this.pedidoEmAlteracao = null;
		this.itensPedidoEmAlteracao.clear();

	}

	/**
	 * Copia duas classes de ItemPedido
	 * 
	 * @param itemOrigem
	 * @param itemDestino
	 */
	public void copiarItemPedido(IItemPedidoWeb itemOrigem, IItemPedidoWeb itemDestino) {
		itemDestino.setAcrescimoPedidoValor(itemOrigem.getAcrescimoPedidoValor());
		itemDestino.setAcrescimoPercentual(itemOrigem.getAcrescimoPercentual());
		itemDestino.setAcrescimoValor(itemOrigem.getAcrescimoValor());
		itemDestino.setCertificadoClassificacao(itemOrigem.getCertificadoClassificacao());
		itemDestino.setCodigoBarrasProdutoCodigo(itemOrigem.getCodigoBarrasProdutoCodigo());
		itemDestino.setCodigoFiscalCodigo(itemOrigem.getCodigoFiscalCodigo());
		itemDestino.setCstIpiCodigo(itemOrigem.getCstIpiCodigo());
		itemDestino.setCusto(itemOrigem.getCusto());
		itemDestino.setCusto1(itemOrigem.getCusto1());
		itemDestino.setCusto2(itemOrigem.getCusto2());
		itemDestino.setCusto3(itemOrigem.getCusto3());
		itemDestino.setCusto4(itemOrigem.getCusto4());
		itemDestino.setCusto5(itemOrigem.getCusto5());
		itemDestino.setCusto6(itemOrigem.getCusto6());
		itemDestino.setCusto7(itemOrigem.getCusto7());
		itemDestino.setCusto8(itemOrigem.getCusto8());
		itemDestino.setCusto9(itemOrigem.getCusto9());
		itemDestino.setCusto10(itemOrigem.getCusto10());
		itemDestino.setDescontoPedidoValor(itemOrigem.getDescontoPedidoValor());
		itemDestino.setDescontoPercentual(itemOrigem.getDescontoPercentual());
		itemDestino.setDescontoValor(itemOrigem.getDescontoValor());
		itemDestino.setDescricaoNF1(itemOrigem.getDescricaoNF1());
		itemDestino.setDescricaoNF2(itemOrigem.getDescricaoNF2());
		itemDestino.setDescricaoNF3(itemOrigem.getDescricaoNF3());
		itemDestino.setDescricaoNF4(itemOrigem.getDescricaoNF4());
		itemDestino.setDescricaoNF5(itemOrigem.getDescricaoNF5());
		itemDestino.setFatorEstoque(itemOrigem.getFatorEstoque());
		itemDestino.setFatorVenda(itemOrigem.getFatorVenda());
		itemDestino.setFilialCodigo(itemOrigem.getFilialCodigo());
		itemDestino.setFlagAtivo(itemOrigem.getFlagAtivo());
		itemDestino.setFlagDebitoCredito(itemOrigem.getFlagDebitoCredito());
		itemDestino.setGrupoComissaoCodigo(itemOrigem.getGrupoComissaoCodigo());
		itemDestino.setKitCodigo(itemOrigem.getKitCodigo());
		itemDestino.setLocalEstoqueCodigo(itemOrigem.getLocalEstoqueCodigo());
		itemDestino.setLote(itemOrigem.getLote());
		itemDestino.setMetroCubico(itemOrigem.getMetroCubico());
		itemDestino.setNegociacaoProdutoNumero(itemOrigem.getNegociacaoProdutoNumero());
		itemDestino.setNumeroItem(itemOrigem.getNumeroItem());
		itemDestino.setNumeroPedido(itemOrigem.getNumeroPedido());
		itemDestino.setOfertaNumero(itemOrigem.getOfertaNumero());
		itemDestino.setParcelaOrdemServico(itemOrigem.getParcelaOrdemServico());
		itemDestino.setPedidoNumero(itemOrigem.getPedidoNumero());
		itemDestino.setPercentualComissao1(itemOrigem.getPercentualComissao1());
		itemDestino.setPercentualComissao2(itemOrigem.getPercentualComissao2());
		itemDestino.setPercentualComissao1(itemOrigem.getPercentualComissao3());
		itemDestino.setPercentualComissao2(itemOrigem.getPercentualComissao4());
		itemDestino.setPercentualComissao1(itemOrigem.getPercentualComissao5());
		itemDestino.setPercentualIpi(itemOrigem.getPercentualIpi());
		itemDestino.setPesoBrutoProduto(itemOrigem.getPesoBrutoProduto());
		itemDestino.setPesoLiquidoProduto(itemOrigem.getPesoLiquidoProduto());
		itemDestino.setPrecoLiquido(itemOrigem.getPrecoLiquido());
		itemDestino.setPrecoSugerido(itemOrigem.getPrecoSugerido());
		itemDestino.setPrecoVenda(itemOrigem.getPrecoVenda());
		itemDestino.setPrecoVendaOriginal(itemOrigem.getPrecoVendaOriginal());
		itemDestino.setProduto(itemOrigem.getProduto());
		itemDestino.setProdutoCodigo(itemOrigem.getProdutoCodigo());
		itemDestino.setProdutoFilial(itemOrigem.getProdutoFilial());
		itemDestino.setProdutoUnidade(itemOrigem.getProdutoUnidade());
		itemDestino.setPromocaoNumero(itemOrigem.getPromocaoNumero());
		itemDestino.setQuantidade(itemOrigem.getQuantidade());
		itemDestino.setQuantidadeAnterior(itemOrigem.getQuantidadeAnterior());
		itemDestino.setQuantidadeUnidade(itemOrigem.getQuantidadeUnidade());
		itemDestino.setQuantidadeVolumes(itemOrigem.getQuantidadeVolumes());
		itemDestino.setSeriePedidoCodigo(itemOrigem.getSeriePedidoCodigo());
		itemDestino.setStatusBaixa(itemOrigem.getStatusBaixa());
		itemDestino.setUnidade(itemOrigem.getUnidade());
		itemDestino.setValorIpi(itemOrigem.getValorIpi());
		itemDestino.setValorSubstituicao(itemOrigem.getValorSubstituicao());
		itemDestino.setValorSubstituicaoAvulso(itemOrigem.getValorSubstituicaoAvulso());
		itemDestino.setValorFundoEstadualCombatePobreza(itemOrigem.getValorFundoEstadualCombatePobreza());
		itemDestino.setPromocao((Promocao) itemOrigem.getPromocao());

	}

	/**
	 * Copia duas classes de Pedido
	 * 
	 * @param pedidoOrigem
	 * @param pedidoDestino
	 */
	public void copiarPedido(IPedidoWeb pedidoOrigem, IPedidoWeb pedidoDestino) {

		pedidoDestino.setAcrescimoPercentual(pedidoOrigem.getAcrescimoPercentual());
		pedidoDestino.setAcrescimoValor(pedidoOrigem.getAcrescimoValor());
		pedidoDestino.setCampoUsuario1(pedidoOrigem.getCampoUsuario1());
		pedidoDestino.setCampoUsuario2(pedidoOrigem.getCampoUsuario2());
		pedidoDestino.setCampoUsuario3(pedidoOrigem.getCampoUsuario3());
		pedidoDestino.setCampoUsuario4(pedidoOrigem.getCampoUsuario4());
		pedidoDestino.setCampoUsuario5(pedidoOrigem.getCampoUsuario5());
		pedidoDestino.setCarga(pedidoOrigem.getCarga());
		pedidoDestino.setCodigoOrigem(pedidoOrigem.getCodigoOrigem());
		pedidoDestino.setColaboradorCodigo(pedidoOrigem.getColaboradorCodigo());
		pedidoDestino.setCondicaoPagamentoCodigo(pedidoOrigem.getCondicaoPagamentoCodigo());
		pedidoDestino.setContaCorrenteCodigo(pedidoOrigem.getContaCorrenteCodigo());
		pedidoDestino.setControleNumeroCodigo(pedidoOrigem.getControleNumeroCodigo());
		pedidoDestino.setDataCadastro(pedidoOrigem.getDataCadastro());
		pedidoDestino.setDataEmissao(pedidoOrigem.getDataEmissao());
		pedidoDestino.setDataEntrega(pedidoOrigem.getDataEntrega());
		pedidoDestino.setDescontoPercentual(pedidoOrigem.getDescontoPercentual());
		pedidoDestino.setDescontoValor(pedidoOrigem.getDescontoValor());
		pedidoDestino.setEnderecoCobrancaCodigo(pedidoOrigem.getEnderecoCobrancaCodigo());
		pedidoDestino.setEnderecoEntregaCodigo(pedidoOrigem.getEnderecoEntregaCodigo());
		pedidoDestino.setFilialCodigo(pedidoOrigem.getFilialCodigo());
		pedidoDestino.setFlagAtivo(pedidoOrigem.getFlagAtivo());
		pedidoDestino.setFlagAtualizaEstoque(pedidoOrigem.getFlagAtualizaEstoque());
		pedidoDestino.setFlagConsisteLimite(pedidoOrigem.getFlagConsisteLimite());
		pedidoDestino.setFlagDebitoCredito(pedidoOrigem.getFlagDebitoCredito());
		pedidoDestino.setFlagDevolucao(pedidoOrigem.getFlagDevolucao());
		pedidoDestino.setFlagParticipaPromocao(pedidoOrigem.getFlagParticipaPromocao());
		pedidoDestino.setFlagTipoNaturezaOperacao(pedidoOrigem.getFlagTipoNaturezaOperacao());
		pedidoDestino.setFormaPagamentoCodigo(pedidoOrigem.getFormaPagamentoCodigo());
		pedidoDestino.setFrete(pedidoOrigem.getFrete());
		pedidoDestino.setGrupoVendaCodigo(pedidoOrigem.getGrupoVendaCodigo());
		pedidoDestino.setHoraCadastro(pedidoOrigem.getHoraCadastro());
		pedidoDestino.setLoteComissaoNumero(pedidoOrigem.getLoteComissaoNumero());
		pedidoDestino.setLoteLancamentoFinanceiro(pedidoOrigem.getLoteLancamentoFinanceiro());
		pedidoDestino.setLoteNotaPedidoCodigo(pedidoOrigem.getLoteNotaPedidoCodigo());
		pedidoDestino.setLotePedidoImportadoCodigo(pedidoOrigem.getLotePedidoImportadoCodigo());
		pedidoDestino.setLucratividade(pedidoOrigem.getLucratividade());
		pedidoDestino.setMetrosCubicos(pedidoOrigem.getMetrosCubicos());
		pedidoDestino.setNaturezaOperacaoCodigo(pedidoOrigem.getNaturezaOperacaoCodigo());
		pedidoDestino.setNumero(pedidoOrigem.getNumero());
		pedidoDestino.setNumeroPedidoPromocao(pedidoOrigem.getNumeroPedidoPromocao());
		pedidoDestino.setNumeroSeparacoes(pedidoOrigem.getNumeroSeparacoes());
		pedidoDestino.setNumeroEntregas(pedidoOrigem.getNumeroEntregas());
		pedidoDestino.setObservacao(pedidoOrigem.getObservacao());
		pedidoDestino.setObservacao1(pedidoOrigem.getObservacao1());
		pedidoDestino.setObservacao2(pedidoOrigem.getObservacao2());
		pedidoDestino.setOpcaoEspecialCodigo(pedidoOrigem.getOpcaoEspecialCodigo());
		pedidoDestino.setOrcamentoCodigo(pedidoOrigem.getOrcamentoCodigo());
		pedidoDestino.setOrdemCliente(pedidoOrigem.getOrdemCliente());
		pedidoDestino.setOrdemEntrega(pedidoOrigem.getOrdemEntrega());
		pedidoDestino.setOrigem(pedidoOrigem.getOrigem());
		pedidoDestino.setOrigemVendaCodigo(pedidoOrigem.getOrigemVendaCodigo());
		pedidoDestino.setPesoBruto(pedidoOrigem.getPesoBruto());
		pedidoDestino.setPesoLiquido(pedidoOrigem.getPesoLiquido());
		pedidoDestino.setPessoaCodigo(pedidoOrigem.getPessoaCodigo());
		pedidoDestino.setPrazoEspecial(pedidoOrigem.getPrazoEspecial());
		pedidoDestino.setPrazoPromocao(pedidoOrigem.getPrazoPromocao());
		pedidoDestino.setPrecoBaseCodigo(pedidoOrigem.getPrecoBaseCodigo());
		pedidoDestino.setPromocaoNumero(pedidoOrigem.getPromocaoNumero());
		pedidoDestino.setQuantidadeImpressao(pedidoOrigem.getQuantidadeImpressao());
		pedidoDestino.setQuantidadeVolumes(pedidoOrigem.getQuantidadeVolumes());
		pedidoDestino.setSerieCodigo(pedidoOrigem.getSerieCodigo());
		pedidoDestino.setSeriePedidoPromocao(pedidoOrigem.getSeriePedidoPromocao());
		// pedidoDestino.setStatusPedido(pedidoOrigem.getStatusPedido());
		pedidoDestino.setStatusPedidoCodigo(pedidoOrigem.getStatusPedidoCodigo());
		pedidoDestino.setTabelaPrecoCodigo(pedidoOrigem.getTabelaPrecoCodigo());
		pedidoDestino.setTipoSeparacao(pedidoOrigem.getTipoSeparacao());
		pedidoDestino.setTipoVendaCodigo(pedidoOrigem.getTipoVendaCodigo());
		pedidoDestino.setTransportadoraCodigo(pedidoOrigem.getTransportadoraCodigo());
		pedidoDestino.setTurnoEntregaCodigo(pedidoOrigem.getTurnoEntregaCodigo());
		pedidoDestino.setValor(pedidoOrigem.getValor());
		pedidoDestino.setValorComissao(pedidoOrigem.getValorComissao());
		pedidoDestino.setValorComissao2(pedidoOrigem.getValorComissao2());
		pedidoDestino.setValorComissao2(pedidoOrigem.getValorComissao3());
		pedidoDestino.setValorComissao2(pedidoOrigem.getValorComissao4());
		pedidoDestino.setValorComissao2(pedidoOrigem.getValorComissao5());
		pedidoDestino.setValorIpi(pedidoOrigem.getValorIpi());
		pedidoDestino.setValorSubstituicao(pedidoOrigem.getValorSubstituicao());
		pedidoDestino.setValorSubstituicaoAvulso(pedidoOrigem.getValorSubstituicaoAvulso());
		pedidoDestino.setValorFundoEstadualCombatePobreza(pedidoOrigem.getValorFundoEstadualCombatePobreza());
		pedidoDestino.setValorSugeridoDebitoCredito(pedidoOrigem.getValorSugeridoDebitoCredito());
		pedidoDestino.setValorVendaDebitoCredito(pedidoOrigem.getValorVendaDebitoCredito());
		pedidoDestino.setVendedorCodigo(pedidoOrigem.getVendedorCodigo());
		pedidoDestino.setGrupoVendaCodigo(pedidoOrigem.getGrupoVendaCodigo());
		pedidoDestino.setGrupoVenda(pedidoOrigem.getGrupoVenda());

		pedidoDestino.setStatusPagamentoCartao(pedidoOrigem.getStatusPagamentoCartao());
	}

	/**
	 * Configuração de ambiente para edição de pedido
	 */
	public void configurarEdicaoPedido() {

		PedidoAuxiliar pedidoAuxiliar = new PedidoAuxiliar();

		ArrayList<IItemPedido> itensPedidoAuxiliar = new ArrayList<IItemPedido>();

		/*
		 * Setar status pedido no banco para em Alteração
		 */
		this.getPedido().setStatusPedidoCodigo(StatusPedido.STATUS_EM_ALTERACAO);
		((GenericoDAO) this.getDao()).update(this.getPedido());

		/*
		 * Criar Pedido Auxiliar
		 */
		this.copiarPedido(this.getPedido(), pedidoAuxiliar);
		((GenericoDAO) this.getDao()).insert(pedidoAuxiliar);

		/*
		 * Criar ItensPedidoAuxiliar
		 */
		for (IItemPedido itemPedido : this.getItensPedido()) {
			ItemPedido itemAux = (ItemPedido) itemPedido;
			ItemPedidoAuxiliar itemPedidoAuxiliar = new ItemPedidoAuxiliar();
			copiarItemPedido(itemAux, itemPedidoAuxiliar);
			this.estornarEstoqueEntrega(itemPedidoAuxiliar);
			((GenericoDAO) this.getDao()).insert(itemPedidoAuxiliar);

			itensPedidoAuxiliar.add(itemPedidoAuxiliar);
		}

		/*
		 * Salva pedidos anteriores nos objetos pedidoEmAlteracao e
		 * itensPedidoEmAlteracao
		 */
		try {
			pedidoEmAlteracao = (Pedido) this.getPedido().clone();
			itensPedidoEmAlteracao = (ArrayList<IItemPedido>) this.getItensPedido().clone();
		} catch (CloneNotSupportedException e) {
			e.printStackTrace();
		}

		/*
		 * Seta Pedido e ItensPedido com Pedido Auxiliar
		 */
		this.setPedido(pedidoAuxiliar);
		this.getItensPedido().clear();
		for (IItemPedido itemPedido : itensPedidoAuxiliar) {
			this.getItensPedido().add(itemPedido);
		}

	}

	/**
	 * Desfaz alterações de pedidos incompletas em sessões encerradas
	 * 
	 * @param dao
	 * @param sessao
	 */
	public static void desfazerAlteracoes(GenericoDAO dao, Sessao sessao, String obsComplementar) {

		List<Pedido> pedidosEmAberto = Pedido.recuperarPedidosEmAlteracao(dao, sessao.getCodigo());

		Estoque estoque = null;

		if (pedidosEmAberto != null && pedidosEmAberto.size() > 0) {

			for (Pedido pedido : pedidosEmAberto) {

				if (estoque == null || (estoque.getFilialCodigo() != pedido.getFilialCodigo())) {

					estoque = Fabrica.gerarEstoque(dao, pedido.getFilialCodigo());

				}

				desfazerAlteracaoPedido(dao, pedido, estoque, sessao, obsComplementar);
			}
		}
	}

	/**
	 * Desfaz alteração de pedido interrompida
	 * 
	 * @param dao
	 * @param pedido
	 * @param estoque
	 * @param sessao
	 */
	private static void desfazerAlteracaoPedido(GenericoDAO dao, Pedido pedido, Estoque estoque, Sessao sessao,
			String obsComplementar) {

		try {

			dao.beginTransaction();
			/*
			 * Destrava pedido
			 */

			/*
			 * Preenche lista de itens
			 */
			List<ItemPedido> itens = ItemPedido.recuperarItensPedido(dao, pedido.getNumero(), pedido.getSerieCodigo(),
					pedido.getFilialCodigo());
			/*
			 * Preenche lista de itens auxiliares
			 */
			List<ItemPedidoAuxiliar> itensAuxiliar = ItemPedidoAuxiliar.recuperarItensPedido(dao, pedido.getNumero(),
					pedido.getSerieCodigo(), pedido.getFilialCodigo());

			/*
			 * Estornar estoque pendente de confirmação - itens auxiliares
			 */
			for (ItemPedidoAuxiliar itemAux : itensAuxiliar) {
				dao.delete(itemAux);

				/*
				 * Estorna a quantidade atual.
				 */
				if (pedido.getFlagAtualizaEstoque() == 1) {
					gravarEstoque(estoque, itemAux, 12001, false);
				}
			}

			/*
			 * Soma estoque pendente de entrega - itens
			 */
			for (ItemPedido item : itens) {
				gravarEstoque(estoque, item, 24001, false);
			}

			/*
			 * Carrega PedidosAux e o exclui
			 */
			PedidoAuxiliar pedAux = PedidoAuxiliar.recuperarUnicoPedidoAuxiliar(dao, pedido.getNumero(),
					pedido.getSerieCodigo(), pedido.getFilialCodigo());
			dao.delete(pedAux);

			/*
			 * Atualiza status do pedido
			 */
			pedido.setStatusPedidoCodigo(StatusPedido.STATUS_NAO_SEPARADO);
			dao.update(pedido);

			/*
			 * Log - ALteração desfeita
			 */
			inserirLogPedido(dao, pedido, sessao, "ALTERAÇÃO NÃO CONFIRMADA", obsComplementar, null);

			dao.commitTransaction();

		} catch (Exception e) {
			dao.rollBackTransaction();
		}

	}

	/**
	 * Estorna o estoque pendente de confirmação e reserva o estoque pendente de
	 * entrega.
	 */

	protected void reservarEstoqueEntregaSemConfirmacao(IItemPedido itemPedido) {

		this.gravarEstoque((IItemPedidoWeb) itemPedido, 24001, false);

	}

	public void lancarExcecaoUsuarioSemCarteria() throws SpaceExcecao {
		throw new PedidoCarteiraSemClienteExcecao((Propriedade) getPropriedade());
	}

	public void lancarExcecaoNaturezaOperacaoNaoInformada() throws SpaceExcecao {
		throw new PedidoNaturezaOperacaoInvalidaExcecao(getPropriedade());
	}

	/**
	 * 
	 * @param cliente
	 * @throws SpaceExcecao
	 */
	public void validarCarteiraClienteVendedorUsuario(Cliente cliente, List<Integer> carteiraInternaCodigos,
			List<Integer> carteiraExternaCodigos, boolean permiteVendaEmOutrasCarteiras) throws SpaceExcecao {

		NaturezaOperacao natop = (NaturezaOperacao) this.getNaturezaOperacao();

		/*
		 * Verifica se o usuario tem permissao para vender para uma carteira diferente.
		 */
		if (cliente != null && !permiteVendaEmOutrasCarteiras) {

			if (natop == null) {
				lancarExcecaoNaturezaOperacaoNaoInformada();
			}

			List<Integer> carteirasParaValidacao = null;
			CarteiraCliente carteiraASerValidada = null;

			if (natop.isInterna()) {
				carteirasParaValidacao = carteiraInternaCodigos;
				carteiraASerValidada = cliente.getCarteiraClienteInterna();
			} else {
				carteirasParaValidacao = carteiraExternaCodigos;
				carteiraASerValidada = cliente.getCarteiraClienteExterna();
			}

			if (ListUtil.isValida(carteirasParaValidacao) && carteiraASerValidada != null) {

				boolean valida = false;

				for (Integer codigo : carteirasParaValidacao) {

					if (codigo.equals(carteiraASerValidada.getCodigo())) {
						valida = true;
						break;
					}
				}

				if (!valida) {
					throw new PedidoCarteiraClienteVendedorExcecao((Propriedade) this.getPropriedade());
				}
			} else {
				lancarExcecaoUsuarioSemCarteria();
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public Transportadora getTransportadora() {
		return transportadora;
	}

	/**
	 * 
	 * @param transportadoraSelecionada
	 * @throws SpaceExcecao
	 */
	public void setTransportadora(Transportadora transportadora) throws SpaceExcecao {

		this.transportadora = transportadora;

		if (transportadora == null) {
			getPedido().setTransportadoraCodigo(0);

		} else if (transportadora.getPessoa() == null || transportadora.getPessoa().getFlagAtivo() == 0) {

			throw new SpaceExcecao(getPropriedade().getMensagem("alerta.pedido.transportadora"));

		} else {
			getPedido().setTransportadoraCodigo(transportadora.getPessoaCodigo());
		}
	}

	@Override
	protected void adicionarAlerta(AlertaExcecao alerta) {
		super.adicionarAlerta(alerta);

		if (alerta != null) {

			if (alerta.getExcecao() instanceof SpaceExcecao) {
				dispararMensagemUsuario(alerta.getExcecao().getMessage());
			} else {
				dispararMensagemUsuario(alerta.getObservacao());
			}
		}
	}

	/**
	 * Dispara a mensagem em parametro para o Ouvinete do
	 * {@link ComunicaoUsuarioListener comunicador listener}
	 * 
	 * @param mensagem Mensagem a ser enviada
	 */
	private void dispararMensagemUsuario(String mensagem) {

		if (ListUtil.isValida(comunicaoUsuarioOuvintes)) {

			for (ComunicaoUsuarioListener listener : comunicaoUsuarioOuvintes) {
				listener.comunicarUsuario(mensagem);
			}
		}
	}

	public void addComunicaoUsuario(ComunicaoUsuarioListener comunicaoUsuario) {
		comunicaoUsuarioOuvintes.add(comunicaoUsuario);

	}

	/**
	 * Interface que serve para padronizar o envio de mensagem do Gerente Pedido ao
	 * usuario ja que o mesmo não apresenta esta comunicação
	 * 
	 * @author Renato
	 * 
	 */
	public interface ComunicaoUsuarioListener {

		/**
		 * Metodo que envia uma mensagem a seus escutadores.
		 * 
		 * @param mensagem A mensagem a ser exibida ao usuario;
		 */
		public void comunicarUsuario(String mensagem);

	}

	/**
	 * Adiciona o colaborador em parametro na lista de colaboradores que pertencem
	 * ao pedido
	 * 
	 * @param colaborador O colaborador a ser acresentado no pedido
	 * @return FALSE casao o colaborador em parametro seja null ou estaja inativo.
	 *         TRUE caso o colaborador seja adicionado com susesso
	 */
	public boolean adicionarColaborador(Colaborador colaborador) {

		if (colaborador == null || !colaborador.isAtivo()) {
			return false;
		}

		if (colaboradoresPedidos == null) {
			colaboradoresPedidos = new ArrayList<Colaborador>();
		}

		boolean colaboradorRepetido = isColaboradorIncluidoPedido(colaborador.getCodigo());

		if (!colaboradorRepetido) {
			colaboradoresPedidos.add(colaborador);
		}

		return true;
	}

	private boolean isColaboradorIncluidoPedido(int colaboradorCodigo) {

		for (Colaborador colaborador : colaboradoresPedidos) {
			if (colaborador.getCodigo() == colaboradorCodigo) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Adiciona o colaborador do usuario caso seja permitido pelo metodos
	 * {@link #isInsereColaboradorAtendente() permite inserir o colaborado do
	 * usuario} e se o {@link #pedidoEmInclusao() pedido esta em inclusão} para
	 * adicionar utiliza {@link #adicionarColaborador(Colaborador) o metodo padrao
	 * para este fim}.
	 */
	private void adicionarColaboradorAtendenteCasoPermitido() {

		if (isInsereColaboradorAtendente() && pedidoEmInclusao()) {
			Colaborador colaborador = getColaboradorPedidoUsuarioAtendente();

			adicionarColaborador(colaborador);
		}
	}

	private Colaborador getColaboradorPedidoUsuarioAtendente() {

		Colaborador colaborador = Colaborador.recuperarAtendente((GenericoDAO) getDao(),
				getUsuarioLogado().getColaboradorCodigo());

		return colaborador;
	}

	private boolean isInsereColaboradorAtendente() {
		return (((ParametroVenda) parametros.getParametroVenda()).isPermiteLancarColaboradorPedido()
				&& ((NaturezaOperacao) getNaturezaOperacao()).isSugereAtendenteInclusaoPedido()
				&& getUsuarioLogado().getColaboradorCodigo() > 0);
	}

	public boolean pedidoEmInclusao() {
		return (getOperacaoPedido() == OperacaoPedido.Inclusao);
	}

	public ArrayList<Colaborador> getColaboradoresPedidos() {
		return colaboradoresPedidos;
	}

	public String getCodigoFiscalOperacao() {
		return codigoFiscalOperacao;
	}

	public void setCodigoFiscalOperacao(String codigoFiscalOperacao) {
		this.codigoFiscalOperacao = codigoFiscalOperacao;
	}

	public Estoque getEstoque() {
		return estoque;
	}
}