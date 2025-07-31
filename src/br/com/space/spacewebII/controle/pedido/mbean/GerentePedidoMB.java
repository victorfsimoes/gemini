package br.com.space.spacewebII.controle.pedido.mbean;

import static br.com.space.spacewebII.controle.financeiro.GerentePagamentoCartao.criarTransacaoCartaoCredito;
import static br.com.space.spacewebII.controle.financeiro.GerentePagamentoCartao.gerarCodigoReferencia;
import static br.com.space.spacewebII.controle.financeiro.GerentePagamentoCartao.isIntegraCartaoAdministradora;
import static br.com.space.spacewebII.controle.financeiro.GerentePagamentoCartao.tratarTransacoesPedido;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Observes;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLActions;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.com.space.api.cartao.modelo.StatusTransacao;
import br.com.space.api.cartao.modelo.excecao.FabricaNulaExcecao;
import br.com.space.api.cartao.modelo.excecao.TransacaoConfirmadaExcecao;
import br.com.space.api.cartao.modelo.excecao.TransacaoNaoAutorizadaExcecao;
import br.com.space.api.cartao.modelo.requisicao.ICredencial;
import br.com.space.api.cartao.modelo.requisicao.Requisicao;
import br.com.space.api.cartao.modelo.resposta.IRetorno;
import br.com.space.api.cartao.modelo.resposta.IRetornoTransacao;
import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.sistema.Matematica;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IAutorizacao;
import br.com.space.api.negocio.modelo.dominio.ICondicaoPagamento;
import br.com.space.api.negocio.modelo.dominio.IFormaPagamento;
import br.com.space.api.negocio.modelo.dominio.IOpcaoEspecialFilial;
import br.com.space.api.negocio.modelo.dominio.ITabelaPreco;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.api.negocio.modelo.excecao.cliente.ClienteNaoEncontradoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoBalancoAtivoExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoGrupoVendaNaoPermiteOpcaoEspecialExcecao;
import br.com.space.api.negocio.modelo.excecao.pedido.PedidoPagamentoCartaoJaAutorizadoExcecao;
import br.com.space.api.negocio.modelo.negocio.GerentePedidoGenerico.OperacaoPedido;
import br.com.space.api.negocio.sistema.IDSistema;
import br.com.space.spacewebII.controle.AppExceptionHandler;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.controle.autorizacao.mbean.GerenteAutorizacaoMB;
import br.com.space.spacewebII.controle.estoque.Estoque;
import br.com.space.spacewebII.controle.financeiro.GerenteFinanceiro;
import br.com.space.spacewebII.controle.financeiro.GerentePagamentoCartao.CodigoReferencia;
import br.com.space.spacewebII.controle.padrao.mbean.GerenteMB;
import br.com.space.spacewebII.controle.pedido.FabricaGerentePedido;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.controle.pedido.NumeracaoPedido;
import br.com.space.spacewebII.controle.pesquisa.PesquisaMB;
import br.com.space.spacewebII.modelo.anotacao.ObjetoPesquisaSelecionado;
import br.com.space.spacewebII.modelo.anotacao.SessaoAutenticada;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.endereco.Enderecos;
import br.com.space.spacewebII.modelo.dominio.estoque.FabricaPrecificacao;
import br.com.space.spacewebII.modelo.dominio.estoque.NaturezaOperacao;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.CredencialAdministradoraCartao;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamentoCondicaoPagamentoTaxa;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamentoFilial;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.pessoa.CarteiraCliente;
import br.com.space.spacewebII.modelo.dominio.pessoa.Colaborador;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroVenda3;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.sistema.Permissao;
import br.com.space.spacewebII.modelo.dominio.sistema.Usuario;
import br.com.space.spacewebII.modelo.dominio.sistema.UsuarioCliente;
import br.com.space.spacewebII.modelo.dominio.sistema.log.LogTransacaoPagamentoCartao;
import br.com.space.spacewebII.modelo.dominio.venda.LocalRetira;
import br.com.space.spacewebII.modelo.dominio.venda.OpcaoEspecial;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;
import br.com.space.spacewebII.modelo.dominio.venda.Promocao;
import br.com.space.spacewebII.modelo.dominio.venda.StatusPedido;
import br.com.space.spacewebII.modelo.dominio.venda.TabelaPreco;
import br.com.space.spacewebII.modelo.dominio.venda.TipoEntrega;
import br.com.space.spacewebII.modelo.dominio.venda.TipoVenda;
import br.com.space.spacewebII.modelo.dominio.venda.TransacaoPagamentoCartao;
import br.com.space.spacewebII.modelo.dominio.venda.Transportadora;
import br.com.space.spacewebII.modelo.dominio.venda.TurnoEntrega;
import br.com.space.spacewebII.modelo.dominio.venda.Vendedor;
import br.com.space.spacewebII.modelo.excecao.EnvioEmailExcecao;
import br.com.space.spacewebII.modelo.excecao.pedido.PedidoCarregadoExcecao;
import br.com.space.spacewebII.modelo.excecao.pedido.PedidoEmEdicaoExcecao;
import br.com.space.spacewebII.modelo.excecao.pedido.PedidoFaturadoExcecao;
import br.com.space.spacewebII.modelo.excecao.pedido.PedidoFinanceiroLancadoExcecao;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroAcessoExclusivoExcecao;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroTravamentoExcecao;
import br.com.space.spacewebII.modelo.padrao.interfaces.CarregarListener;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;
import br.com.space.spacewebII.modelo.util.Fabrica;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;

@ManagedBeanSessionScoped
@URLMappings(mappings = {
		@URLMapping(id = "carrinhoBarra", pattern = "/carrinho/", viewId = "/pages/carrinho.xhtml", onPostback = false),
		@URLMapping(id = "carrinho", pattern = "/carrinho", viewId = "/pages/carrinho.xhtml", onPostback = false),
		@URLMapping(id = "pagamentoBarra", pattern = "/pagamento/", viewId = "/pages/checkout.xhtml", onPostback = false),
		@URLMapping(id = "pagamento", pattern = "/pagamento", viewId = "/pages/checkout.xhtml", onPostback = false),
		@URLMapping(id = "pagamentoConfirmacaoBarra", pattern = "/pagamento/confirmacao/", viewId = "/pages/confirmacaoPagamento.xhtml", onPostback = false),
		@URLMapping(id = "pagamentoConfirmacao", pattern = "/pagamento/confirmacao", viewId = "/pages/confirmacaoPagamento.xhtml", onPostback = false),
		@URLMapping(id = "pedidoBarra", pattern = "/pedido/", viewId = "/pages/pedido.xhtml", onPostback = false),
		@URLMapping(id = "pedido", pattern = "/pedido", viewId = "/pages/pedido.xhtml", onPostback = false) })
@URLBeanName("gerentePedidoMB")
public class GerentePedidoMB extends GerenteMB
		implements Serializable, CarregarListener, GerentePedido.ComunicaoUsuarioListener {

	private static final long serialVersionUID = 1L;
	public static final String FLAG_DESCONTO = "D";
	public static final String FLAG_ACRESCIMO = "A";
	public static final String NOME_PROGRAMA = "FRMCONSPEDIDO";

	private static final String PARAMETRO_ID_REFERENCIA = "referencia_id";
	private static final String PARAMETRO_ID_TRANSACAO = "transacao_id";
	private static final String URL_RETORNO_PAGAMENTO_CARTAO = "/pagamento/confirmacao";

	@Inject
	private Parametros parametros;

	@Inject
	private GerenteAutorizacaoMB gerenteAutorizacaoMB;

	@Inject
	private HistoricoPedidosMB historicoPedidosMB;

	@Inject
	private PesquisaMB pesquisaMB;

	private GerentePedido gerentePedido;

	private Estoque estoque;

	private Precificacao precificacao;

	private int tabAtual = 0;

	private double valorDoPedidoConfiguracaoParcelas = 0;

	private IPedidoWeb pedidoImpressao;

	private Colaborador colaboradorSelecionado;

	private Promocao promocaoSelecionada;
	private List<List<Promocao>> listasPromocoes;

	private List<TabelaPrecosFinal> precosFinal;
	private List<TipoVenda> tiposVenda;
	private List<OpcaoEspecial> opcoesEspeciais;
	private List<String> tiposEntrega;
	private List<TurnoEntrega> turnosEntrega;
	private List<ParcelaCondicaoPagamento> parcelasCondicaoPagamento;
	private List<LocalRetira> locaisRetirada;

	private Integer codClassificacaoPagtoSelecionada = null;
	private List<FormaPagamento> formasPagtoCassificadas = null;

	NumeracaoPedido numeracao;

	private String flagAcrescDesc = FLAG_DESCONTO;

	private double descontoAcrescimoPercentual = 0;
	private double descontoAcrescimoValor = 0;

	private List<Integer> carteirasClienteInternaUsuario = null;
	private List<Integer> carteirasClienteExternaUsuario = null;
	private boolean jaBuscouCarteirasDoUsuario = false;

	private boolean jaVerificouCargoDeVenda = false;
	private boolean cargoRelacionadoAVenda = false;

	private String htmlPedidoConfirmado = "não tem código.";

	private double saldoCreditoCliente = 0;

	private HashMap<ICredencial, Requisicao> hashMapRequisicao = null;

	private String idReferenciaPedido = null;

	/**
	 * Após a classe ser carregada, busca os parâmetros inicias da venda WEB.
	 * 
	 * @throws Exception
	 */
	@PostConstruct
	public void inicializarPadrao() throws Exception {
		try {

			this.hashMapRequisicao = new HashMap<ICredencial, Requisicao>();

			this.getGerentePedido().setFilial(gerenteLogin.getFilialPadrao());

			this.getGerentePedido().inicializarInclusaoPedido();

			ParametroWeb parametroWeb = parametros.getParametroWeb();

			/*
			 * Tabela de Preco Padrao.
			 */
			TabelaPreco tabelaPadrao = TabelaPreco.recuperarUnico(dao, parametroWeb.getTabelaPrecoCodigo());
			/*
			 * Condição de Pagamento padrão
			 */
			CondicaoPagamento condicaoPadrao = CondicaoPagamento.recuperarUnico(dao,
					parametroWeb.getCondicaoPagamentoCodigo());

			if (tabelaPadrao != null && condicaoPadrao != null)
				alterarNegociacao(tabelaPadrao, condicaoPadrao, this.getGerentePedido().getOpcaoEspecialFilial());

			/*
			 * Forma de pagamento padrão
			 */
			FormaPagamento formaPadrao = FormaPagamento.recuperarUnico(dao, parametroWeb.getFormaPagamentoCodigo());

			if (formaPadrao != null)
				setFormaPagamentoSelecionada(formaPadrao);

			/*
			 * Cliente do usuário.
			 */
			/*
			 * if (gerenteLogin.isPerfilCliente() && gerenteLogin.getClienteCodigo() !=
			 * null) {
			 * 
			 * setClienteCodigo(gerenteLogin.getClienteCodigo()); }
			 */

			NaturezaOperacao naturezaPadrao = carregarNaturezaOperacaoPadrao(parametroWeb);

			if (naturezaPadrao != null) {
				setNaturezaOperacaoSelecionada(naturezaPadrao);
			}

			carregarDadosUsuaio(gerenteLogin.getUsuarioLogado());

			tiposVenda = TipoVenda.recuperaTiposVendaAtivos(dao, gerenteLogin.getFilialCodigo());
			opcoesEspeciais = OpcaoEspecial.recuperaOpcoesEspeciais(dao, gerenteLogin.getFilialCodigo());

			turnosEntrega = TurnoEntrega.recuperaTurnosEntregaAtivos(dao, gerenteLogin.getFilialCodigo());

			// Cria um turno em branco pois não existe parametro que sugere um
			if (ListUtil.isValida(turnosEntrega)) {
				turnosEntrega.add(0, new TurnoEntrega("", "", 0));
			}

			// endereco entrega em set cliente
			preencherTiposEntrega();
			preencherLocaisRetira();

			if (naturezaPadrao != null) {
				carregarTipoEntregaPadraPedido(naturezaPadrao);
			}

			/*
			 * Preenche os dados da negociação com o padrão do sistema.
			 */
			this.getGerentePedido().getPrecificacao().alterarNegociacao(null, getClienteSelecionado(),
					getTabelaPrecoSelecionada(), getCondicaoPagamentoSelecionada(), getFormaPagamentoSelecionada(),
					null, null, 0, 0, getNaturezaOperacaoSelecionada());

			/*
			 * Preenche as listas de promoções
			 */
			List<Promocao> todasPromocoes = Promocao.recuperarPromocoesPedido(dao, new Date(), getGerentePedido());
			classificarPromocoes(todasPromocoes);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private NaturezaOperacao carregarNaturezaOperacaoPadrao(ParametroWeb parametroWeb) {
		/*
		 * Natureza de Operação Padrão
		 */

		NaturezaOperacao naturezaPadrao = null;

		if (getClienteSelecionado() != null && StringUtil.isValida(getClienteSelecionado().getNaturezaOperacao())
				&& gerenteLogin.isPerfilSelecionadoCliente()) {
			naturezaPadrao = NaturezaOperacao.recuperarUnico(dao, getClienteSelecionado().getNaturezaOperacao());
		} else {
			naturezaPadrao = NaturezaOperacao.recuperarUnico(dao, parametroWeb.getNaturezaOperacaoCodigo());
		}

		if (naturezaPadrao != null) {
			setNaturezaOperacaoSelecionada(naturezaPadrao);
		}
		return naturezaPadrao;
	}

	private void carregarTipoEntregaPadraPedido(NaturezaOperacao naturezaPadrao) {
		/*
		 * Verifica se o tipo de separacao do parametro é valido, se nao pega o primeira
		 * da lista.
		 */
		if (StringUtil.isValida(naturezaPadrao.getTipoSeparacao())) {
			getPedido().setTipoSeparacao(naturezaPadrao.getTipoSeparacao());
		} else if (ListUtil.isValida(getTiposEntrega())) {
			getPedido().setTipoSeparacao(getTiposEntrega().get(0));
		}
	}

	/**
	 * 
	 * @param usuarioWeb
	 * @throws Exception
	 */
	public void carregarDadosUsuaio(UsuarioWeb usuarioWeb) throws Exception {

		if (usuarioWeb == null) {
			return;
		}

		if (usuarioWeb instanceof Usuario) {
			carregarVendedorPadrao(usuarioWeb.getColaboradorCodigo());
		} else if (usuarioWeb instanceof UsuarioCliente) {
			setClienteCodigo(gerenteLogin.getClienteCodigo());
		}
	}

	/**
	 * 
	 * @param usuarioWeb
	 * @throws Exception
	 */
	public void perceberSessaoAutenticada(@Observes @SessaoAutenticada UsuarioWeb usuarioWeb) throws Exception {

		carregarDadosUsuaio(usuarioWeb);
	}

	/**
	 * Carrega o vendedor do usuario.
	 * 
	 * </br>
	 * Se a natureza de operação ja estiver definida entao ele recupera somente os
	 * vendedores que forem correspondente ao tipo de venda da natureza.
	 * 
	 * </br>
	 * Caso contrario busca os vendedores idependente de tipo de venda.
	 * 
	 * </br>
	 * </br>
	 * <strong> Este metodo busca o vendedor do usuario ou caso o usuario seja um
	 * supervisor busca todos os vendedores dele e escolhe o primeiro da lista ou
	 * entao se ele form um vendedor será ele mesmo </strong>
	 * 
	 * @param colaboradorCodigo O codigo do vendedor ou de um supervisor.
	 * @throws Exception
	 */
	private void carregarVendedorPadrao(int colaboradorCodigo) throws Exception {

		boolean interno = false;
		boolean externo = false;

		if (getNaturezaOperacaoSelecionada() != null) {
			interno = getNaturezaOperacaoSelecionada().isInterna();
			externo = getNaturezaOperacaoSelecionada().isExterna();
		}

		List<Vendedor> vendedors = Vendedor.recuperar(dao, colaboradorCodigo, interno, externo);

		if (ListUtil.isValida(vendedors)) {

			Vendedor vendedorSugerido = vendedors.get(0);

			if (vendedors.size() > 1) {
				for (Vendedor vendedor : vendedors) {
					if (vendedor.getColaboradorCodigo() == colaboradorCodigo) {
						vendedorSugerido = vendedor;
						break;
					}
				}
			}
			setVendedorSelecionado(vendedorSugerido);
		}
	}

	/**
	 * @throws Exception
	 * 
	 */
	public void inicializarNovoPedido() throws Exception {
		System.out.println("inicializarNovoPedido");
		this.inicializarPadrao();
	}

	/**
	 * 
	 * @param produto
	 */
	public void atualizaPedido(IItemPedidoWeb produto) {

		IPedidoWeb pedido = this.getGerentePedido().getPedido();

		double volume = 0;
		if (pedido.getMetrosCubicos() != null && pedido.getMetrosCubicos() > 0)
			volume = pedido.getMetrosCubicos();

		if (produto.getMetroCubico() != null)
			volume += produto.getMetroCubico();

		pedido.setMetrosCubicos(volume);

		double pesoBruto = 0;
		if (pedido.getPesoBruto() != null && pedido.getPesoBruto() > 0)
			pesoBruto = pedido.getPesoBruto();
		if (produto.getPesoBrutoProduto() != null)
			pesoBruto += produto.getPesoBrutoProduto();
		pedido.setPesoBruto(pesoBruto);
	}

	/**
	 * 
	 * @return
	 */
	public String carregarDadosPedido() {

		return "/pages/pedido.xhtml";
	}

	/**
	 * 
	 * @return
	 */
	public String editarPedido(Pedido pedido)
			throws PedidoEmEdicaoExcecao, RegistroAcessoExclusivoExcecao, RegistroTravamentoExcecao,
			PedidoFinanceiroLancadoExcecao, PedidoFaturadoExcecao, PedidoCarregadoExcecao, PedidoBalancoAtivoExcecao {

		try {
			/*
			 * Testa se existe pedido em edição
			 */
			if (this.getGerentePedido().getPedido().getNumero() > 0) {
				throw new PedidoEmEdicaoExcecao(this.getPropriedade());
			}

			/*
			 * Testa balanco em aberto
			 */
			this.gerentePedido.validarBalancoAtivo();
			/*
			 * Recarrega pedido, buscando informações atualizadas
			 */
			pedido = Pedido.recuperarUnicoPedido(dao, pedido.getNumero(), pedido.getSerieCodigo(),
					pedido.getFilialCodigo());

			/*
			 * Testa se existe financeiro lançado
			 */
			if (pedido.getLoteLancamentoFinanceiro() > 0) {
				throw new PedidoFinanceiroLancadoExcecao(this.getPropriedade());
			}

			/*
			 * Testa se pedido já foi faturado
			 */
			if (pedido.getLoteNotaPedidoCodigo() > 0) {
				throw new PedidoFaturadoExcecao(this.getPropriedade());
			}
			/*
			 * Testa se pedido já foi carregado
			 */
			if (pedido.getCarga() > 0) {
				throw new PedidoCarregadoExcecao(this.getPropriedade());
			}
		} catch (PedidoEmEdicaoExcecao | PedidoFinanceiroLancadoExcecao | PedidoFaturadoExcecao
				| PedidoCarregadoExcecao e2) {
			e2.printStackTrace();
			throw e2;
		}

		IPedidoWeb pedidoAux = this.getGerentePedido().getPedido();
		this.getGerentePedido().setPedido(pedido);

		try {
			this.travarPedido(true);
		} catch (RegistroAcessoExclusivoExcecao | RegistroTravamentoExcecao e1) {

			e1.printStackTrace();
			/*
			 * Se não conseguir travar, retorna o pedido anterior
			 */
			this.getGerentePedido().setPedido(pedidoAux);
			throw e1;
		}

		try {
			this.dao.beginTransaction();

			this.getGerentePedido().inicializarEdicaoPedido();

			this.dao.commitTransaction();

		} catch (Exception e) {
			this.dao.rollBackTransaction();
			e.printStackTrace();
		} finally {

		}
		return "/pages/pedido.xhtml";
	}

	public void editarPedidoEmInclusao(Pedido pedido) throws PedidoEmEdicaoExcecao, RegistroAcessoExclusivoExcecao,
			RegistroTravamentoExcecao, PedidoBalancoAtivoExcecao {

		try {
			/*
			 * Testa se existe pedido em edição
			 */
			if (this.getGerentePedido().getPedido().getNumero() > 0) {
				throw new PedidoEmEdicaoExcecao(this.getPropriedade());
			}

			/*
			 * Testa balanco em aberto
			 */
			this.gerentePedido.validarBalancoAtivo();

		} catch (PedidoEmEdicaoExcecao e2) {
			e2.printStackTrace();
			throw e2;
		}

		// if (pedido == null)
		// return "/pages/pedido.xhtml";

		this.getGerentePedido().setPedido(pedido);

		try {
			this.travarPedidoInclusao(true);
		} catch (RegistroAcessoExclusivoExcecao | RegistroTravamentoExcecao e1) {

			e1.printStackTrace();
			throw e1;
		}

		try {
			this.dao.beginTransaction();

			this.getGerentePedido().carregarPedidoEmInclusao();

			this.dao.commitTransaction();

		} catch (Exception e) {
			this.dao.rollBackTransaction();
			e.printStackTrace();
		} finally {

		}
		// return "/pages/pedido.xhtml";
	}

	/**
	 * Preenche tipos de entrega
	 */
	public void preencherTiposEntrega() {

		List<TipoEntrega> lista = TipoEntrega.recuperaAtivos(dao);

		if (tiposEntrega == null) {
			tiposEntrega = new ArrayList<String>();
		} else {
			tiposEntrega.clear();
		}

		for (TipoEntrega tipoEntrega : lista) {
			tiposEntrega.add(tipoEntrega.getCodigo());
		}
	}

	private void preencherLocaisRetira() {
		/* List<LocalRetira> */ locaisRetirada = LocalRetira.recuperar(dao);

		if (ListUtil.isValida(locaisRetirada)) {
			locaisRetirada.add(0, new LocalRetira());
		}
	}

	/**
	 * Classifica as promoções recuperadas em listas de acordo com seu tipo
	 * 
	 * @param todasPromocoes
	 */
	public void classificarPromocoes(List<Promocao> todasPromocoes) {
		listasPromocoes = new ArrayList<>();

		if (todasPromocoes != null && !todasPromocoes.isEmpty()) {

			List<Promocao> promocaoPrazoVenda = new ArrayList<>();
			List<Promocao> promocaoPrazoFinalVenda = new ArrayList<>();
			List<Promocao> promocaoVencimentoFixo = new ArrayList<>();
			List<Promocao> promocaoDescontoMaximo = new ArrayList<>();

			for (Promocao promocao : todasPromocoes) {
				if (promocao.getTipoPromocao().toLowerCase().trim().equals("a")) {
					promocaoPrazoVenda.add(promocao);
				}
				if (promocao.getTipoPromocao().toLowerCase().trim().equals("p")) {
					promocaoPrazoFinalVenda.add(promocao);
				}
				if (promocao.getTipoPromocao().toLowerCase().trim().equals("v")) {
					promocaoVencimentoFixo.add(promocao);
				}
				if (promocao.getTipoPromocao().toLowerCase().trim().equals("m")) {
					promocaoDescontoMaximo.add(promocao);
				}
			}

			listasPromocoes.add(promocaoPrazoVenda);
			listasPromocoes.add(promocaoPrazoFinalVenda);
			listasPromocoes.add(promocaoVencimentoFixo);
			listasPromocoes.add(promocaoDescontoMaximo);
		}
	}

	/**
	 * Preenche string de descrição da promoção selecionada
	 */
	public String preencherDescricaoPromocaoSelecionada(Promocao promo) {
		String descricao = null;

		// descricao = "De R$ " + promo.getFaixaValorInicio() + " à R$" +
		// promo.getFaixaValorFim() +;
		// descricao += propriedade.getMensagem("texto.voceGanha");

		gerentePedido.getPromocaoVenda().verificarPromocaoAtingidaPedido(promocaoSelecionada);

		return descricao;
	}

	/**
	 * 
	 */
	public void preencheParcelasCondicaoPagamento() {

		IPedidoWeb pedido = this.getGerentePedido().getPedido();

		if (this.getGerentePedido().getCondicaoPagamento() == null || pedido == null) {
			return;
		}

		if (parcelasCondicaoPagamento == null) {
			parcelasCondicaoPagamento = new ArrayList<ParcelaCondicaoPagamento>();
		} else {
			parcelasCondicaoPagamento.clear();
		}

		GregorianCalendar vencimento = new GregorianCalendar();
		vencimento.setTime(pedido.getDataEmissao());

		this.valorDoPedidoConfiguracaoParcelas = pedido.getValor();

		double valorParcela = pedido.getValor();

		vencimento.add(Calendar.DAY_OF_MONTH, this.getGerentePedido().getCondicaoPagamento().getDiasEntrada());

		valorParcela = pedido.getValor() / this.getGerentePedido().getCondicaoPagamento().getNumeroParcelas();

		for (int i = 1; i <= this.getGerentePedido().getCondicaoPagamento().getNumeroParcelas(); i++) {

			if (i > 1) {
				vencimento.add(Calendar.DAY_OF_MONTH,
						this.getGerentePedido().getCondicaoPagamento().getIntervaloEntreParcelas());
			}

			parcelasCondicaoPagamento
					.add(new ParcelaCondicaoPagamento(Integer.toString(i), valorParcela, vencimento.getTime()));
		}
	}

	/**
	 * 
	 * @author Ronie
	 * 
	 */
	public class ParcelaCondicaoPagamento {
		private String descParcela;
		private double preco;
		private Date vencimento;

		public ParcelaCondicaoPagamento(String descParcela, double preco, Date vencimento) {
			super();
			this.descParcela = descParcela;
			this.preco = preco;
			this.vencimento = vencimento;
		}

		public String getDescParcela() {
			return descParcela;
		}

		public void setDescParcela(String descParcela) {
			this.descParcela = descParcela;
		}

		public double getPreco() {
			return preco;
		}

		public void setPreco(double preco) {
			this.preco = preco;
		}

		public Date getVencimento() {
			return vencimento;
		}

		public void setVencimento(Date vencimento) {
			this.vencimento = vencimento;
		}
	}

	public List<ParcelaCondicaoPagamento> getParcelasCondicaoPagamento() {
		return parcelasCondicaoPagamento;
	}

	public void setParcelasCondicaoPagamento(List<ParcelaCondicaoPagamento> parcelasCondicaoPagamento) {
		this.parcelasCondicaoPagamento = parcelasCondicaoPagamento;
	}

	/**
	 * 
	 * @param object
	 * @throws Exception
	 */
	public void percebeValorPesquisa(@Observes @ObjetoPesquisaSelecionado Object object) throws Exception {

		if (object == null) {
			return;
		}

		if (object instanceof Cliente) {
			setClienteSelecionado((Cliente) object);
		} else if (object instanceof Colaborador) {
			setColaboradorSelecionado((Colaborador) object);
		} else if (object instanceof NaturezaOperacao) {
			setNaturezaOperacaoSelecionada((NaturezaOperacao) object);
		} else if (object instanceof Vendedor) {
			setVendedorSelecionado((Vendedor) object);
		} else if (object instanceof CondicaoPagamento) {
			setCondicaoPagamentoSelecionada((CondicaoPagamento) object);
		} else if (object instanceof FormaPagamento) {
			setFormaPagamentoSelecionada((FormaPagamento) object);
		} else if (object instanceof TabelaPreco) {
			setTabelaPrecoSelecionada((TabelaPreco) object);
		} else if (object instanceof Transportadora) {
			setTransportadora((Transportadora) object);
		} else if (object instanceof OpcaoEspecial) {
			setOpcaoEspecialSelecionada((OpcaoEspecial) object);
		}
	}

	/**
	 * 
	 * @throws SpaceExcecao
	 */
	public void pesquisarCliente() throws SpaceExcecao {

		boolean permiteOutraCarteira = isUsuarioPermiteVenderOutraCarteira();

		// Verificar se a natureza existe ou nao

		if (!permiteOutraCarteira) {

			if (getNaturezaOperacaoSelecionada() != null) {

				String in = null;
				String campo = null;

				if (getNaturezaOperacaoSelecionada().isExterna()) {

					campo = Cliente.COLUNA_CARTEIRA_EXTERNA;
					in = GenericoDAO.criarIN(getCarteirasClienteExternaUsuario());
				} else if (getNaturezaOperacaoSelecionada().isInterna()) {

					in = GenericoDAO.criarIN(getCarteirasClienteInternaUsuario());
					campo = Cliente.COLUNA_CARTEIRA_INTERNA;
				}

				if (StringUtil.isValida(in)) {

					String whereAdicional = campo + " in" + in;

					pesquisaMB.exibirPesquisa(Cliente.class, whereAdicional, getUrlView());
				} else {
					gerentePedido.lancarExcecaoUsuarioSemCarteria();
				}
			} else {
				gerentePedido.lancarExcecaoNaturezaOperacaoNaoInformada();
			}
		} else {
			pesquisaMB.exibirPesquisa(Cliente.class, null, getUrlView());
		}
	}

	public List<Integer> getCarteirasClienteExternaUsuario() {
		if (carteirasClienteExternaUsuario == null) {
			carregarCarteirasUsuario();
		}

		return carteirasClienteExternaUsuario;
	}

	public List<Integer> getCarteirasClienteInternaUsuario() {
		if (carteirasClienteInternaUsuario == null) {
			carregarCarteirasUsuario();
		}

		return carteirasClienteInternaUsuario;
	}

	private void carregarCarteirasUsuario() {

		if (!jaBuscouCarteirasDoUsuario && getUsuario() instanceof Usuario) {

			try {

				int colaboradorCodigo = ((Usuario) getUsuario()).getColaboradorCodigo();

				carteirasClienteInternaUsuario = CarteiraCliente.recuperarCodigosCarteira(dao, colaboradorCodigo,
						CarteiraCliente.TIPO_INTERNA);

				carteirasClienteExternaUsuario = CarteiraCliente.recuperarCodigosCarteira(dao, colaboradorCodigo,
						CarteiraCliente.TIPO_EXTERNA);

				jaBuscouCarteirasDoUsuario = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public Cliente getClienteSelecionado() {
		if (this.getGerentePedido().getCliente() == null)
			return null;

		return (Cliente) this.getGerentePedido().getCliente();
	}

	/**
	 * 
	 * @param clienteSelecionado
	 */
	public void setClienteSelecionado(Cliente clienteSelecionado) {

		if (clienteSelecionado == null)
			return;

		try {

			/*
			 * Somente altera o cliente quando realmente for um cliente diferente do atual.
			 */
			if (this.getGerentePedido().getCliente() == null
					|| this.getGerentePedido().getCliente().getCodigo() != clienteSelecionado.getCodigo()) {

				if (clienteSelecionado.getPessoa().getFlagAtivo() == 0) {
					throw new ClienteNaoEncontradoExcecao(getPropriedade());
				}

				getGerentePedido().getPromocaoVenda().validarPromocaoItens(clienteSelecionado);

				/*
				 * Verifica se o usuario pode vender para carteira diferente.
				 */
				gerentePedido.validarCarteiraClienteVendedorUsuario(clienteSelecionado,
						getCarteirasClienteInternaUsuario(), getCarteirasClienteExternaUsuario(),
						isUsuarioPermiteVenderOutraCarteira());

				this.getGerentePedido().setCliente(clienteSelecionado);

				carregarNaturezaOperacaoPadrao(parametros.getParametroWeb());

				saldoCreditoCliente = getGerentePedido().getSaldoCliente();

				this.getGerentePedido().validarNegociacao();

				gerentePedido.validarCliente(clienteSelecionado);

				notificarExixtenciaDePedidos(clienteSelecionado);
			}
		} catch (Exception e) {
			if (e instanceof SpaceExcecao) {
				exibirMensagemAlerta(null, e.getMessage());
			}
		}
	}

	private void notificarExixtenciaDePedidos(Cliente clienteSelecionado) {

		if (gerenteLogin.isPerfilColaborador() && parametros.getParametro2().isVerificaExixtenciaPedidoDia()) {

			Date datedia = new Date();

			long pedidoEfetivadosDia = Pedido.recuperarQuantidadePedidosEfetivadosDia(dao,
					clienteSelecionado.getCodigo(), getFilialCodigo(), datedia);

			if (pedidoEfetivadosDia > 0) {
				comunicarUsuario(getPropriedade().getMensagem("mensagem.cliente.existepedidos.hoje",
						Conversao.formatarDataDDMMAAAA(datedia)));
			}

			long pedidosSeparadosOuParcialmente = Pedido.recuperarQuantidadePedidos(dao, clienteSelecionado.getCodigo(),
					getFilialCodigo(), null, null, new String[] { Integer.toString(StatusPedido.STATUS_NAO_SEPARADO),
							Integer.toString(StatusPedido.STATUS_SEPARADO_PARCIAL) });

			if (pedidosSeparadosOuParcialmente > 0) {
				comunicarUsuario(getPropriedade().getMensagem("mensagem.cliente.existepedidos.separacaoparcialenao"));
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	private boolean isUsuarioPermiteVenderOutraCarteira() {
		return gerentePermissao.verificarPermissao(NOME_PROGRAMA, 161) || !isCargoRelacionadoAVenda();
	}

	/**
	 * 
	 * @return
	 */
	public boolean isCargoRelacionadoAVenda() {
		if (!jaVerificouCargoDeVenda && gerenteLogin.isSessoaAutenticada() && getUsuario() instanceof Usuario) {

			jaVerificouCargoDeVenda = true;

			Usuario usuario = (Usuario) getUsuario();

			if (usuario.getColaboradorCodigo() > 0) {

				cargoRelacionadoAVenda = usuario.isVendedor(dao, usuario.getColaboradorCodigo());

				if (!cargoRelacionadoAVenda) {
					cargoRelacionadoAVenda = usuario.isSupervisorDeVenda(dao, usuario.getColaboradorCodigo());
				}
			}
		}

		return cargoRelacionadoAVenda;
	}

	/**
	 * Provisório
	 */
	public void validarCodigoCliente() {

	}

	/**
	 * 
	 * @return
	 */
	public Colaborador getColaboradorSelecionado() {
		this.getGerentePedido().getVendedor();

		return colaboradorSelecionado;
	}

	/**
	 * 
	 * @param colaboradorSelecionado
	 */
	public void setColaboradorSelecionado(Colaborador colaboradorSelecionado) {
		IPedidoWeb pedido = this.getGerentePedido().getPedido();
		this.colaboradorSelecionado = colaboradorSelecionado;

		if (this.colaboradorSelecionado != null) {
			pedido.setColaboradorCodigo(colaboradorSelecionado.getCodigo());
		}
	}

	public Integer getTransportadoraCodigo() {
		if (getGerentePedido().getTransportadora() == null)
			return null;

		return getGerentePedido().getTransportadora().getPessoaCodigo();
	}

	public void setTransportadoraCodigo(Integer transportadoraCodigo) throws Exception {

		Transportadora transportadora = Transportadora.recuperarUnico(dao, transportadoraCodigo);

		this.setTransportadora(transportadora);

	}

	/**
	 * 
	 * @return
	 */
	public Transportadora getTransportadora() {
		return getGerentePedido().getTransportadora();
	}

	/**
	 * 
	 * @param transportadoraSelecionada
	 * @throws Exception
	 */
	public void setTransportadora(Transportadora transportadoraSelecionada) throws Exception {

		try {
			getGerentePedido().setTransportadora(transportadoraSelecionada);
		} catch (Exception e) {
			tratarException(e);
		}

	}

	/**
	 * 
	 * @return
	 */
	public NaturezaOperacao getNaturezaOperacaoSelecionada() {
		if (this.getGerentePedido().getNaturezaOperacao() == null)
			return null;
		return (NaturezaOperacao) this.getGerentePedido().getNaturezaOperacao();
	}

	/**
	 * 
	 * @param naturezaOperacaoSelecionada
	 */
	public void setNaturezaOperacaoSelecionada(NaturezaOperacao naturezaOperacaoSelecionada) {

		if (naturezaOperacaoSelecionada.getFlagSaida() != 1
				|| naturezaOperacaoSelecionada.getFlagLancamentoPreVenda() != 1
				|| naturezaOperacaoSelecionada.getClassificacaoNaturezaSaida() == null
				|| naturezaOperacaoSelecionada.getClassificacaoNaturezaSaida().getFlagDevolucao() == 1
				|| !naturezaOperacaoSelecionada.getClassificacaoNaturezaSaida().getTipoNaturezaOperacao().equals("V")
				|| naturezaOperacaoSelecionada.getFlagLiberaVenda() == 0
				|| naturezaOperacaoSelecionada.getFlagLiberaVendaWeb() == 0) {
			sessaoMB.mensagem(TipoMensagem.Alerta, "", propriedade.getMensagem("alerta.pedido.naturezaoperacao"));
			return;
		}

		/*
		 * Verifica se trocou de natureza de operação.
		 */

		boolean trocouNatureza = naturezaOperacaoSelecionada != null
				&& (this.getGerentePedido().getNaturezaOperacao() == null || !this.getGerentePedido()
						.getNaturezaOperacao().getCodigo().equals(naturezaOperacaoSelecionada.getCodigo()));

		if (trocouNatureza) {

			this.getGerentePedido().setNaturezaOperacao(naturezaOperacaoSelecionada);

			this.getGerentePedido().carregarVendedorPadrao();

			carregarTipoEntregaPadraPedido(naturezaOperacaoSelecionada);
		}
	}

	/**
	 * 
	 * @return
	 */
	public CondicaoPagamento getCondicaoPagamentoSelecionada() {
		if (this.getGerentePedido().getCondicaoPagamento() == null)
			return null;
		return (CondicaoPagamento) this.getGerentePedido().getCondicaoPagamento();
	}

	/**
	 * 
	 * @param condicaoPagamentoSelecionada
	 * @throws Exception
	 */
	public void setCondicaoPagamentoSelecionada(CondicaoPagamento condicaoPagamentoSelecionada) throws Exception {

		try {

			this.getGerentePedido().validarCondicaoPagamento(condicaoPagamentoSelecionada);

			alterarNegociacao(this.getGerentePedido().getTabelaPreco(), condicaoPagamentoSelecionada,
					this.getGerentePedido().getOpcaoEspecialFilial());

		} catch (Exception e) {
			tratarException(e);
		}
	}

	/**
	 * 
	 * @return
	 */
	public OpcaoEspecial getOpcaoEspecialSelecionada() {
		if (this.getGerentePedido().getOpcaoEspecial() == null)
			return null;
		return (OpcaoEspecial) this.getGerentePedido().getOpcaoEspecial();
	}

	/**
	 * 
	 * @param opcaoEspecialSelecionada
	 * @throws Exception
	 */
	public void setOpcaoEspecialSelecionada(OpcaoEspecial opcaoEspecialSelecionada) throws Exception {

		OpcaoEspecial opcaoEspecialAnt = (OpcaoEspecial) gerentePedido.getOpcaoEspecial();
		try {
			// Valida Grupo de Venda
			this.gerentePedido.validarGrupoVendaItensComOpcaoEspecial(opcaoEspecialSelecionada);

			// Valida Forma de Pagamento
			this.gerentePedido.validarFormaPagametoOpcaoEspecial(this.gerentePedido.getFormaPagamento(),
					opcaoEspecialSelecionada);

			// Valida a opcao especial
			this.gerentePedido.validarOpcaoEspecial(opcaoEspecialSelecionada);

			if (opcaoEspecialSelecionada == null
					|| (opcaoEspecialSelecionada != null && getGerentePedido().getOpcaoEspecialFilial() == null)
					|| (this.getGerentePedido().getOpcaoEspecialFilial() != null
							&& opcaoEspecialSelecionada.getCodigo() != this.getGerentePedido().getOpcaoEspecialFilial()
									.getNumeroOpcaoEspecial())) {

				this.gerentePedido.carregarOpcaoEspecial(opcaoEspecialSelecionada, true);

			}

		} catch (Exception e) {
			// Volta a opção especial anterior caso aconteça algum erro
			this.getGerentePedido().setOpcaoEspecial(opcaoEspecialAnt);

			if (e instanceof PedidoGrupoVendaNaoPermiteOpcaoEspecialExcecao) {
				((PedidoGrupoVendaNaoPermiteOpcaoEspecialExcecao) e).atribuirDescricaoItemAMensagem();
			}

			tratarException(e);
		}
	}

	public void tratarException(Exception e) throws Exception {

		if (e != null) {
			SpaceExcecao spaceExcecao = AppExceptionHandler.getPrimeiraSpaceExcecao(e);

			if (!(e instanceof IAutorizacao || spaceExcecao instanceof IAutorizacao) && spaceExcecao != null) {
				exibirMensagemErro(null, spaceExcecao.getMessage());
			} else {
				throw e;
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public FormaPagamento getFormaPagamentoSelecionada() {
		if (this.getGerentePedido().getFormaPagamento() == null)
			return null;
		return (FormaPagamento) this.getGerentePedido().getFormaPagamento();
	}

	/**
	 * 
	 * @param formaPagamentoSelecionada
	 * @throws Exception
	 */
	public void setFormaPagamentoSelecionada(FormaPagamento formaPagamentoSelecionada) throws Exception {

		try {
			this.getGerentePedido().validarFormaPagamento(formaPagamentoSelecionada);

			this.getGerentePedido().setFormaPagamento(formaPagamentoSelecionada);

		} catch (Exception e) {
			tratarException(e);
		}
	}

	public ArrayList<Colaborador> getColaboradores() {
		return getGerentePedido().getColaboradoresPedidos();
	}

	/**
	 * 
	 * @return
	 */
	public Vendedor getVendedorSelecionado() {
		if (this.getGerentePedido().getVendedor() == null)
			return null;
		return (Vendedor) this.getGerentePedido().getVendedor();
	}

	/**
	 * 
	 * @param vendedorSelecionado
	 * @throws Exception
	 */
	public void setVendedorSelecionado(Vendedor vendedorSelecionado) throws Exception {

		try {

			this.getGerentePedido().validarVendedor(vendedorSelecionado);
			getGerentePedido().getPromocaoVenda().validarPromocaoItens(vendedorSelecionado);

			this.getGerentePedido().setVendedor(vendedorSelecionado);

		} catch (Exception e) {
			tratarException(e);
		}
	}

	public void validarCodigoVendedor() {

	}

	public List<List<Promocao>> getListasPromocoes() {
		return listasPromocoes;
	}

	public void setListasPromocoes(List<List<Promocao>> listasPromocoes) {
		this.listasPromocoes = listasPromocoes;
	}

	public Promocao getPromocaoSelecionada() {
		return promocaoSelecionada;
	}

	/**
	 * 
	 * @param promocaoSelecionada
	 */
	public void setPromocaoSelecionada(Promocao promocaoSelecionada) {
		if (promocaoSelecionada != null && !promocaoSelecionada.equals(this.promocaoSelecionada)) {
			this.promocaoSelecionada = promocaoSelecionada;
			this.verificarPromocaoSelecionada();
		}
	}

	/**
	 * 
	 */
	public void verificarPromocaoSelecionada() {
		if (verificarPromocaoValida())
			getGerentePedido().aplicarPromocaoPedido(this.promocaoSelecionada);
		else
			this.promocaoSelecionada = null;
	}

	/**
	 * 
	 * @return
	 */
	private boolean verificarPromocaoValida() {
		return this.verificarPromocaoValida(promocaoSelecionada);
	}

	/**
	 * 
	 * @param promocao
	 * @return
	 */
	public boolean verificarPromocaoValida(Promocao promocao) {

		if (promocao != null) {
			return this.getGerentePedido().getPromocaoVenda().verificarPromocaoAtingidaPedido(promocao);
		}
		return false;
	}

	/**
	 * @return the itensPedido
	 */
	@SuppressWarnings("unchecked")
	public List<? extends IItemPedidoWeb> getItensPedido() {
		return (List<? extends IItemPedidoWeb>) this.getGerentePedido().getItensPedido();
	}

	public TabelaPreco getTabelaPrecoSelecionada() {
		if (this.getGerentePedido().getTabelaPreco() == null)
			return null;
		return (TabelaPreco) this.getGerentePedido().getTabelaPreco();
		// return tabelaPrecoSelecionada;
	}

	/**
	 * 
	 * @return
	 * 
	 * 		private AcaoAlteracaoNegociacao
	 *         obterAcaoAlteracaoNegociacaoParametro() { ParametroVenda paramVenda =
	 *         (ParametroVenda) parametros .getParametroVenda();
	 * 
	 *         AcaoAlteracaoNegociacao acao =
	 *         AcaoAlteracaoNegociacao.ManterDiferencaPreco;
	 * 
	 *         if (paramVenda.getFlagAlteracaoCondicaoPagamento() == 1) acao =
	 *         AcaoAlteracaoNegociacao.ManterPrecoNegociado; else if
	 *         (paramVenda.getFlagAlteracaoCondicaoPagamento() == 2) acao =
	 *         AcaoAlteracaoNegociacao.ManterDiferencaPercentual; else if
	 *         (paramVenda.getFlagAlteracaoCondicaoPagamento() == 3) acao =
	 *         AcaoAlteracaoNegociacao.AssumirPrecoTabela;
	 * 
	 *         return acao; }
	 */

	/**
	 * 
	 * @param tabelaPreco
	 * @param condicaoPagamento
	 * @param opcaoEspecialFilial
	 * @throws Exception
	 */
	private void alterarNegociacao(ITabelaPreco tabelaPreco, ICondicaoPagamento condicaoPagamento,
			IOpcaoEspecialFilial opcaoEspecialFilial) throws Exception {

		try {
			getGerentePedido().alterarNegociacao(tabelaPreco, condicaoPagamento, opcaoEspecialFilial);
		} catch (Exception e) {
			e.printStackTrace();

			tratarException(e);
		}

		preencheParcelasCondicaoPagamento();
	}

	/**
	 * 
	 * @param tabelaPrecoSelecionada
	 * @throws Exception
	 */
	public void setTabelaPrecoSelecionada(TabelaPreco tabelaPrecoSelecionada) throws Exception {

		alterarNegociacao(tabelaPrecoSelecionada, this.getGerentePedido().getCondicaoPagamento(),
				this.getGerentePedido().getOpcaoEspecialFilial());
	}

	public int getTabAtual() {
		return tabAtual;
	}

	public void setTabAtual(int tabAtual) {
		this.tabAtual = tabAtual;
	}

	public void tabChange(Integer i) {
		tabAtual = i;
	}

	public class TabelaPrecosFinal {
		private String descricao;
		private double valor;

		public TabelaPrecosFinal(String descricao, double valor) {
			super();
			this.setDescricao(descricao);
			this.setValor(valor);
		}

		public String getDescricao() {
			return descricao;
		}

		public void setDescricao(String descricao) {
			this.descricao = descricao;
		}

		public double getValor() {
			return valor;
		}

		public void setValor(double valor) {
			this.valor = valor;
		}

	}

	public double getValorImpostos() {

		if (getPedido() == null) {
			return 0;
		}

		double valorImpostos = Conversao.nvlDouble(getPedido().getValorSubstituicao(), 0D)
				+ Conversao.nvlDouble(getPedido().getValorSubstituicaoAvulso(), 0D)
				+ Conversao.nvlDouble(getPedido().getValorIpi(), 0D);

		return valorImpostos;
	}

	/**
	 * 
	 * @return
	 */
	public List<TabelaPrecosFinal> getPrecosFinal() {
		precosFinal = new ArrayList<TabelaPrecosFinal>();
		/*
		 * Preço bruto de itens
		 */
		if (this.getGerentePedido().getValorTotalBrutoItem() > 0) {

			precosFinal.add(new TabelaPrecosFinal(propriedade.getMensagem("texto.valorBrutoItens") + ":",
					this.getGerentePedido().getValorTotalBrutoItem()));
		}

		if (this.getGerentePedido().getValorTotalBrutoItemEmOferta() > 0) {
			precosFinal.add(new TabelaPrecosFinal(propriedade.getMensagem("texto.valorBrutoItens.oferta") + ":",
					this.getGerentePedido().getValorTotalBrutoItemEmOferta()));
		}

		/*
		 * Valor Acréscimo / Desconto
		 */
		if (this.getGerentePedido().getValorTotalBrutoItem() != this.getGerentePedido().getValorTotalItem()) {

			if (this.getGerentePedido().getValorTotalBrutoItem() > this.getGerentePedido().getValorTotalItem()) {

				precosFinal.add(new TabelaPrecosFinal(
						propriedade.getMensagem("texto.valorBrutoItens.calculo.desconto") + ":",
						Conversao.arredondar(this.getGerentePedido().getValorTotalBrutoItemParaCalculoDesconto(), 2)));

				precosFinal.add(new TabelaPrecosFinal("- " + propriedade.getMensagem("texto.valorDesconto") + ":",
						Conversao.arredondar(this.getGerentePedido().getValorTotalBrutoItem()
								- this.getGerentePedido().getValorTotalItem(), 2)));
			} else {
				precosFinal.add(new TabelaPrecosFinal("+ " + propriedade.getMensagem("texto.valorAcrescimo") + ":",
						Conversao.arredondar(Math.abs(this.getGerentePedido().getValorTotalBrutoItem()
								- this.getGerentePedido().getValorTotalItem()), 2)));
			}
		}

		/*
		 * Frete
		 */
		if (this.getGerentePedido().getPedido().getFrete() > 0) {
			precosFinal.add(new TabelaPrecosFinal("+ " + propriedade.getMensagem("texto.valorFrete") + ":",
					this.getGerentePedido().getPedido().getFrete()));
		}

		/*
		 * IPI
		 */
		if (this.getGerentePedido().getPedido().getValorIpi() != null
				&& this.getGerentePedido().getPedido().getValorIpi() > 0) {
			precosFinal.add(new TabelaPrecosFinal("+ " + propriedade.getMensagem("texto.valorIpi") + ":",
					this.getGerentePedido().getPedido().getValorIpi()));
		}

		/*
		 * ST
		 */
		if (this.getGerentePedido().getPedido().getValorSubstituicao() != null
				&& this.getGerentePedido().getPedido().getValorSubstituicao() > 0) {
			precosFinal.add(
					new TabelaPrecosFinal("+ " + propriedade.getMensagem("texto.valorSubstituicaoTributaria") + ":",
							this.getGerentePedido().getPedido().getValorSubstituicao()));
		}

		/*
		 * ST AVULSA
		 */
		if (this.getGerentePedido().getPedido().getValorSubstituicaoAvulso() != null
				&& this.getGerentePedido().getPedido().getValorSubstituicaoAvulso() > 0) {
			precosFinal
					.add(new TabelaPrecosFinal(propriedade.getMensagem("texto.valorSubstituicaoTributariaAvulsa") + ":",
							this.getGerentePedido().getPedido().getValorSubstituicaoAvulso()));
		}

		/*
		 * Valor Total
		 */
		precosFinal.add(new TabelaPrecosFinal("= " + propriedade.getMensagem("texto.valorTotal") + ":",
				this.getPedido().getValor()));

		return precosFinal;
	}

	/**
	 * 
	 * @param precosFinal
	 */
	public void setPrecosFinal(List<TabelaPrecosFinal> precosFinal) {
		this.precosFinal = precosFinal;
	}

	/**
	 * Calcula o valor total dos itens, sem acréscimos e descontos, ipi, st e frete.
	 * 
	 * @return the valorTotalItens
	 */
	public double getValorTotalItens() {

		return getGerentePedido().getValorTotalBrutoItem();
	}

	public double getValorTotalPedido() {
		return gerentePedido.getValorTotalPedido();
	}

	/**
	 * Valor do pedido sem acréscimos e descontos.
	 * 
	 * @return
	 */
	public double getValorOriginalPedido() {
		double valor = this.getGerentePedido().getPedido().getValor()
				- getGerentePedido().getPedido().getAcrescimoValor()
				+ getGerentePedido().getPedido().getDescontoValor();

		if (getGerentePedido().getPedido().getAcrescimoPercentual() > 0.0)
			valor -= valor / (1 + getPedido().getAcrescimoPercentual());
		if (getGerentePedido().getPedido().getDescontoPercentual() > 0.0)
			valor += valor / (1 - getPedido().getDescontoPercentual());

		return valor;
	}

	/**
	 * 
	 * @param e
	 */
	public void acrescDescPercentualChange(ValueChangeEvent e) {

		if (e.getNewValue() != null && ((double) e.getNewValue()) != 0) {

			this.setDescontoAcrescimoValor(0);
		}
	}

	/**
	 * 
	 * @param e
	 */
	public void acrescDescValorChange(ValueChangeEvent e) {

		if (e.getNewValue() != null && ((double) e.getNewValue()) != 0) {

			this.setDescontoAcrescimoPercentual(0);
		}
	}

	/**
	 * 
	 * @param e
	 */
	public void tipoEntregaChange(ValueChangeEvent e) {
		if (e.getNewValue() != null) {

			int indiceTipoEntrega = getTiposEntrega().indexOf(e.getNewValue().toString());

			if (indiceTipoEntrega >= 0) {
				calcularValorTotalPedido(getTiposEntrega().get(indiceTipoEntrega), true);
			}
		}
	}

	private void calcularValorTotalPedido(String tipoSeparacao, boolean tratarErroCalculoFrete) {

		String tipoSeparacaoAtual = getPedido().getTipoSeparacao();

		calcularValorTotalPedido(tipoSeparacao);

		if (!gerentePedido.isFreteCalculadoComSucesso() && tratarErroCalculoFrete) {
			calcularValorTotalPedido(tipoSeparacaoAtual);
		}
	}

	private void calcularValorTotalPedido(String tipoSeparacao) {

		getPedido().setTipoSeparacao(tipoSeparacao);

		if (!gerentePedido.isPedidoSemItens()) {
			gerentePedido.calcularTotais();
		}

		if (desabilitarDigitacaoNumeroEntregas()) {
			getPedido().setNumeroEntregas(0);
		}
	}

	public String getTipoEntregaSelecionado() {
		return getGerentePedido().getPedido().getTipoSeparacao();
	}

	public void setTipoEntregaSelecionado(String tipoEntrega) {
		System.out.println("tentou inserir " + tipoEntrega);
		/*
		 * Não é necessario nem deve ser implementado nada neste metodo deixa por conta
		 * do tipoEntregaChange
		 */
	}

	/**
	 * 
	 * @param e
	 */
	public void turnoEntregaChange(ValueChangeEvent e) {
		if (e.getNewValue() != null && ListUtil.isValida(turnosEntrega)) {

			try {
				int turnoCodigo = Integer.parseInt(e.getNewValue().toString());

				TurnoEntrega turnoEntrega = null;

				if (turnoCodigo != 0) {
					turnoEntrega = TurnoEntrega.pesquisaNaLista(turnosEntrega, turnoCodigo);
				}

				getGerentePedido().setTurnoEntrega(turnoEntrega);

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	/**
	 * 
	 * @param e
	 */
	public void enderecoEntregaChange(ValueChangeEvent e) {
		if (e.getNewValue() != null) {

			try {

				int codigoEndereco = Integer.parseInt(e.getNewValue().toString());

				if (codigoEndereco > 0) {
					getGerentePedido().getPedido().setEnderecoEntregaCodigo(codigoEndereco);
				}

			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
	}

	public int getEnderecoEntregaSelecionadoCodigo() {
		return getGerentePedido().getPedido().getEnderecoEntregaCodigo();
	}

	public void setEnderecoEntregaSelecionadoCodigo(int enderecoEntregaSelecionadoCodigo) {
		/*
		 * Não deve conter implementação deixar a cargo do enderecoEntregaChange
		 */
	}

	/**
	 * Retorna o valor do resultado.
	 */
	public double getResultado() {
		return gerentePedido.getValorResultadoPedido();
	}

	/**
	 * @return the tiposVenda
	 */
	public List<TipoVenda> getTiposVenda() {
		return tiposVenda;
	}

	/**
	 * @param tiposVenda the tiposVenda to set
	 */
	public void setTiposVenda(List<TipoVenda> tiposVenda) {
		this.tiposVenda = tiposVenda;
	}

	public List<OpcaoEspecial> getOpcoesEspeciais() {
		return opcoesEspeciais;
	}

	public List<String> getTiposEntrega() {
		if (!ListUtil.isValida(tiposEntrega)) {
			preencherTiposEntrega();
		}
		return tiposEntrega;
	}

	public List<LocalRetira> getLocaisRetirada() {
		if (!ListUtil.isValida(locaisRetirada)) {
			preencherLocaisRetira();
		}
		return locaisRetirada;
	}

	public int getLocalRetiraSelecionado() {

		int localRetiraCodigo = ((Pedido) getPedido()).getLocalRetiraCodigo();
		return localRetiraCodigo;
	}

	public void localRetiradaChange(ValueChangeEvent e) {
		if (e.getNewValue() != null && ListUtil.isValida(locaisRetirada)) {

			int localRetiraCodigo = Integer.parseInt(e.getNewValue().toString());
			((Pedido) getPedido()).setLocalRetiraCodigo(localRetiraCodigo);
		}
	}

	public void setLocalRetiraSelecionado(int localRetiraCodigo) {
		// Implementado no changeListener
	}

	public List<TurnoEntrega> getTurnosEntrega() {
		return turnosEntrega;
	}

	/**
	 * 
	 * @return
	 */
	public Enderecos getEnderecoEntregaSelecionado() {

		Enderecos endereco = null;

		if (this.getClienteSelecionado() != null && this.getPedido().getEnderecoEntregaCodigo() != 0) {
			endereco = Enderecos.localizarEndereco(this.getEnderecos(), this.getPedido().getEnderecoEntregaCodigo());
		}
		return endereco;

	}

	/**
	 * 
	 * @param enderecoEntregaSelecionado
	 */
	public void setEnderecoEntregaSelecionado(Enderecos enderecoEntregaSelecionado) {
		IPedidoWeb pedido = this.getGerentePedido().getPedido();
		pedido.setEnderecoEntregaCodigo(enderecoEntregaSelecionado.getCodigo());
	}

	/**
	 * 
	 * @param itemPedido
	 */
	public void excluirItem(IItemPedidoWeb itemPedido) {
		try {
			this.dao.beginTransaction();

			this.getGerentePedido().excluirItem(itemPedido);

			this.dao.commitTransaction();
		} catch (Exception e) {
			this.dao.rollBackTransaction();
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<Enderecos> getEnderecos() {
		return this.getGerentePedido().getEnderecosCliente();
	}

	/**
	 * @throws SpaceExcecao
	 * 
	 */
	private void popularPedido() throws SpaceExcecao {
		getGerentePedido().aplicarPromocaoPedido(this.promocaoSelecionada);

		setDataEntregaPerfilCliente();

		if (!StringUtil.isValida(getPedido().getTipoSeparacao())) {
			carregarTipoEntregaPadraPedido(getNaturezaOperacaoSelecionada());
		}
	}

	private void setDataEntregaPerfilCliente() throws SpaceExcecao {
		if (gerenteLogin.isPerfilCliente()) {

			Calendar calendar = Calendar.getInstance();

			calendar.setTime(Fabrica.dataHoje());
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);

			if (calendar.getTime().after(getPedido().getDataEmissao())) {
				throw new SpaceExcecao(propriedade.getMensagem("alerta.pedido.dataemissao.invalida"));
			}

			getPedido().setDataEntrega(getPedido().getDataEmissao());
		}
	}

	/**
	 * 
	 * @param verificarIntegracaoCartao flag indicando se é para verificar se o
	 *                                  pedido é pagamento com cartão e se a forma
	 *                                  de pagamento possui integração com uma
	 *                                  administradora de cartão.
	 * @return
	 * @throws Exception
	 */
	public String finalizarPedido(boolean verificarIntegracaoCartao) throws Exception {

		pedidoImpressao = null;
		idReferenciaPedido = null;

		String urlLocalRedirecionar = null;

		int statusAtualPedido = getPedido().getStatusPedidoCodigo();

		try {

			verificarTravaPedido();

			popularPedido();

			this.dao.beginTransaction();

			this.getGerentePedido().calcularTotais();
			this.getGerentePedido().validarPedido();

			RetornoIntegracaoCartao retornoIntegracaoCartao = new RetornoIntegracaoCartao(false, null);

			// NO futuro haverá esta integração com boleto.
			if (verificarIntegracaoCartao) {
				retornoIntegracaoCartao = verificarEIntegrarPagamentoComCartao();
			}

			retornoIntegracaoCartao.validarPagamento();

			boolean exibirResumoPedido = true;

			if (retornoIntegracaoCartao.isIntegracaoValida()) {
				fecharPedidoPagamentoCartao(retornoIntegracaoCartao.urlRedirecionamento);
				exibirResumoPedido = false;
			} else {
				this.getGerentePedido().fecharPedido();
			}

			destravarPedido(false);

			getGerentePedido().salvarOuAtualizarPedido();

			getGerentePedido().inserirLogPedidoFechamento();

			this.dao.commitTransaction();

			if (retornoIntegracaoCartao.integraComCartao) {
				try {
					LogTransacaoPagamentoCartao.gravarLog(daoLog, retornoIntegracaoCartao.transacaoPagamentoCartao,
							"Criação da Transação");
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			if (exibirResumoPedido) {

				pedidoImpressao = (IPedidoWeb) this.getPedido().clone();

				sessaoMB.mensagem(TipoMensagem.Info, "", propriedade.getMensagem("mensagem.pedidoFinalizadoSucesso"));

				try {
					urlLocalRedirecionar = historicoPedidosMB.imprimirPedido(pedidoImpressao, true);

				} catch (EnvioEmailExcecao e) {
					urlLocalRedirecionar = historicoPedidosMB.getUrlViewResumo();
					FacesContext fc = FacesContext.getCurrentInstance();
					ExternalContext ec = fc.getExternalContext();
					String contextPath = ((ServletContext) ec.getContext()).getContextPath();
					ec.redirect(contextPath + urlLocalRedirecionar);
					e.printStackTrace();
				}
			}

			this.inicializarNovoPedido();

		} catch (Exception e) {

			if (!(e instanceof SpaceExcecao)) {
				sessaoMB.mensagem(TipoMensagem.Erro, propriedade.getMensagem("alerta.ocorreuUmErro"),
						propriedade.getMensagem("alerta.tenteNovamenteMaisTarde") + " " + e.getMessage());
			}

			this.dao.rollBackTransaction();
			// Volta o status do objeto pois do banco o roll back garante
			getPedido().setStatusPedidoCodigo(statusAtualPedido);

			e.printStackTrace();

			throw e;
		}

		return urlLocalRedirecionar;
	}

	private void fecharPedidoPagamentoCartao(String urlRedirecionamento) throws Exception {

		// Seta a flag pois se trata de um dedido pre pago
		this.gerentePedido.setFlagVendaConsumidor(true);

		getGerentePedido().fecharPedido(StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB,
				Pedido.STATUS_PAGAMENTO_CARTAO_AGUARDANDO_PAGAMENTO);

		// Gera o financeiro automaticamente
		GerenteFinanceiro.gerarFinanceiroPedido(dao, sessaoMB.getCodigoSessaoAtiva(), (Pedido) this.getPedido(),
				getPropriedade());

		redirecionar(urlRedirecionamento);
	}

	/**
	 * 
	 * @throws RegistroAcessoExclusivoExcecao
	 * @throws RegistroTravamentoExcecao
	 */
	public void travarPedido(boolean novaTransacao) throws RegistroAcessoExclusivoExcecao, RegistroTravamentoExcecao {
		sessaoMB.travarRegistro(getPedido(), getGerentePedido().getSessaoCodigo(), GerentePedidoMB.NOME_PROGRAMA,
				novaTransacao);
	}

	/**
	 * 
	 * @throws RegistroAcessoExclusivoExcecao
	 * @throws RegistroTravamentoExcecao
	 */
	public void destravarPedido(boolean novaTransacao)
			throws RegistroAcessoExclusivoExcecao, RegistroTravamentoExcecao {
		sessaoMB.destravarRegistro(getPedido(), getGerentePedido().getSessaoCodigo(), GerentePedidoMB.NOME_PROGRAMA,
				novaTransacao);
	}

	public void travarPedidoInclusao(boolean novaTransacao)
			throws RegistroAcessoExclusivoExcecao, RegistroTravamentoExcecao {
		sessaoMB.destravarRegistro(getPedido(), getGerentePedido().getSessaoCodigo(), GerentePedidoMB.NOME_PROGRAMA,
				novaTransacao, true);
		this.travarPedido(novaTransacao);
	}

	/**
	 * 
	 * @throws RegistroAcessoExclusivoExcecao
	 */
	public void verificarTravaPedido() throws RegistroAcessoExclusivoExcecao {

		/*
		 * Se ainda não gravou o pedido não existirá trava.
		 */
		if (getGerentePedido().getPedido().getNumero() != 0) {
			if (!sessaoMB.verificarTravaRegistro(getPedido(), getGerentePedido().getSessaoCodigo(),
					GerentePedidoMB.NOME_PROGRAMA)) {
				throw new RegistroAcessoExclusivoExcecao(getGerentePedido().getPropriedade());
			}
		}
	}

	/**
	 * @return the pedido
	 */
	public IPedidoWeb getPedido() {
		return this.getGerentePedido().getPedido();
	}

	/*
	 * Retorna se pedido está em alteração
	 */
	public boolean pedidoEmAlteracao() {
		return (getGerentePedido().getOperacaoPedido() == OperacaoPedido.Alteracao);
	}

	/**
	 * 
	 * @return
	 */
	public Parametros getParametros() {
		return parametros;
	}

	/**
	 * 
	 * @return
	 */
	public Precificacao getPrecificacao() {

		if (this.precificacao == null)
			this.precificacao = new Precificacao(
					FabricaPrecificacao.getInstancia(dao, gerenteLogin.getFilialLogada(), parametros,
							this.gerenteAutorizacaoMB.getGerenteAutorizacao()),
					gerenteAutorizacaoMB.getGerenteAutorizacao(),
					this.getParametros().getParametroVenda().getCasasDecimaisPrecoVenda(), this.getParametros());

		return this.precificacao;
	}

	/**
	 * 
	 * @return
	 */
	public Estoque getEstoque() {
		if (this.estoque == null)
			this.estoque = new Estoque(dao, this.getParametros());
		return this.estoque;
	}

	/**
	 * 
	 * @return
	 */
	public GerentePedido getGerentePedido() {
		if (this.gerentePedido == null) {
			this.gerentePedido = new GerentePedido(IDSistema.SpaceWeb, gerenteLogin, this.getPrecificacao(),
					propriedade, this.getParametros(), this.getEstoque(), dao, new FabricaGerentePedido(),
					gerenteAutorizacaoMB.getGerenteAutorizacao());

			gerentePedido.addComunicaoUsuario(this);
		}

		this.gerentePedido.setSessaoCodigo(sessaoMB.getCodigoSessaoAtiva());

		return this.gerentePedido;
	}

	/**
	 * 
	 * @return
	 */
	public Integer getClienteCodigo() {
		if (getClienteSelecionado() == null)
			return null;
		return getClienteSelecionado().getPessoaCodigo();
	}

	public String getClienteNome() {
		if (getClienteSelecionado() == null) {
			return null;
		} else {
			return getClienteSelecionado().getRazao(parametroWebMB.getParametroWeb());
		}
	}

	/**
	 * 
	 * @param clienteCodigo
	 */
	public void setClienteCodigo(Integer clienteCodigo) {

		Cliente cliente = Cliente.recuperarUnico(dao, clienteCodigo);
		this.setClienteSelecionado(cliente);
	}

	public Integer getVendedorCodigo() {
		if (getVendedorSelecionado() == null)
			return null;
		return getVendedorSelecionado().getColaboradorCodigo();
	}

	public void setVendedorCodigo(Integer vendedorCodigo) throws Exception {
		Vendedor vendedor = Vendedor.recuperarUnicoAtivo(dao, vendedorCodigo);
		this.setVendedorSelecionado(vendedor);
	}

	public String getNaturezaOperacaoCodigo() {
		if (getNaturezaOperacaoSelecionada() == null)
			return null;
		return getNaturezaOperacaoSelecionada().getCodigo();
	}

	public void setNaturezaOperacaoCodigo(String naturezaOperacaoCodigo) {
		NaturezaOperacao naturezaOperacao = NaturezaOperacao.recuperarUnico(dao, naturezaOperacaoCodigo);
		this.setNaturezaOperacaoSelecionada(naturezaOperacao);
	}

	public Integer getCondicaoPagamentoCodigo() {
		if (getCondicaoPagamentoSelecionada() == null)
			return null;
		return getCondicaoPagamentoSelecionada().getCodigo();
	}

	public void setCondicaoPagamentoCodigo(Integer condicaoPagamentoCodigo) throws Exception {
		CondicaoPagamento condicaoPagamento = CondicaoPagamento.recuperarUnico(dao, condicaoPagamentoCodigo);
		this.setCondicaoPagamentoSelecionada(condicaoPagamento);
	}

	public String getFormaPagamentoCodigo() {
		if (getFormaPagamentoSelecionada() == null)
			return null;
		return getFormaPagamentoSelecionada().getCodigo();
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) throws Exception {
		FormaPagamento formaPagamento = FormaPagamento.recuperarUnico(dao, formaPagamentoCodigo);

		if (formaPagamento != null) {
			this.setFormaPagamentoSelecionada(formaPagamento);
		}
	}

	public Integer getTabelaPrecoCodigo() {
		if (getTabelaPrecoSelecionada() == null)
			return null;
		return getTabelaPrecoSelecionada().getCodigo();
	}

	public void setTabelaPrecoCodigo(Integer tabelaPrecoCodigo) throws Exception {

		TabelaPreco tabelaPreco = TabelaPreco.recuperarUnico(dao, tabelaPrecoCodigo);
		this.setTabelaPrecoSelecionada(tabelaPreco);
	}

	public Integer getColaboradorCodigo() {
		if (getColaboradorSelecionado() == null)
			return null;
		return getColaboradorSelecionado().getCodigo();
	}

	public void setColaboradorCodigo(Integer colaboradorCodigo) {
		Colaborador colaborador = Colaborador.recuperarUnico(dao, colaboradorCodigo);
		this.setColaboradorSelecionado(colaborador);
	}

	public Integer getOpcaoEspecialCodigo() {
		if (getOpcaoEspecialSelecionada() == null)
			return null;
		return getOpcaoEspecialSelecionada().getCodigo();
	}

	public void setOpcaoEspecialCodigo(Integer opcaoEspecialCodigo) throws Exception {
		OpcaoEspecial opcaoEspecial = OpcaoEspecial.recuperarUnico(dao, opcaoEspecialCodigo);
		this.setOpcaoEspecialSelecionada(opcaoEspecial);
	}

	public String getFlagAcrescDesc() {
		return flagAcrescDesc;
	}

	public void setFlagAcrescDesc(String flagAcrescDesc) {
		this.flagAcrescDesc = flagAcrescDesc;
	}

	public double getDescontoAcrescimoPercentual() {
		if (this.flagAcrescDesc.equals(FLAG_DESCONTO))
			descontoAcrescimoPercentual = this.getGerentePedido().getPedido().getDescontoPercentual();
		else if (this.flagAcrescDesc.equals(FLAG_ACRESCIMO))
			descontoAcrescimoPercentual = this.getGerentePedido().getPedido().getAcrescimoPercentual();

		return descontoAcrescimoPercentual;
	}

	public void setDescontoAcrescimoPercentual(double descontoAcrescimoPercentual) {
		this.descontoAcrescimoPercentual = descontoAcrescimoPercentual;
		if (this.flagAcrescDesc.equals(FLAG_DESCONTO)) {
			this.getGerentePedido().getPedido().setDescontoPercentual(descontoAcrescimoPercentual);
			if (descontoAcrescimoPercentual != 0) {
				this.getGerentePedido().getPedido().setAcrescimoPercentual(0);
				this.getGerentePedido().getPedido().setAcrescimoValor(0);
				this.setDescontoAcrescimoValor(0);
			}

		} else if (this.flagAcrescDesc.equals(FLAG_ACRESCIMO)) {
			this.getGerentePedido().getPedido().setAcrescimoPercentual(descontoAcrescimoPercentual);
			if (descontoAcrescimoPercentual != 0) {
				this.getGerentePedido().getPedido().setDescontoPercentual(0);
				this.getGerentePedido().getPedido().setDescontoValor(0);
				this.setDescontoAcrescimoValor(0);
			}
		}
		this.getGerentePedido().calcularTotais();

	}

	public double getDescontoAcrescimoValor() {
		if (this.flagAcrescDesc.equals(FLAG_DESCONTO))
			descontoAcrescimoValor = this.getGerentePedido().getPedido().getDescontoValor();
		else if (this.flagAcrescDesc.equals(FLAG_ACRESCIMO))
			descontoAcrescimoValor = this.getGerentePedido().getPedido().getAcrescimoValor();
		return descontoAcrescimoValor;
	}

	public void setDescontoAcrescimoValor(double descontoAcrescimoValor) {
		this.descontoAcrescimoValor = descontoAcrescimoValor;
		if (this.flagAcrescDesc.equals(FLAG_DESCONTO)) {
			this.getGerentePedido().getPedido().setDescontoValor(descontoAcrescimoValor);
			if (descontoAcrescimoValor != 0) {
				this.getGerentePedido().getPedido().setAcrescimoValor(0);
				this.getGerentePedido().getPedido().setAcrescimoPercentual(0);
				this.setDescontoAcrescimoPercentual(0);
			}
		} else if (this.flagAcrescDesc.equals(FLAG_ACRESCIMO)) {
			this.getGerentePedido().getPedido().setAcrescimoValor(descontoAcrescimoValor);
			if (descontoAcrescimoValor != 0) {
				this.getGerentePedido().getPedido().setDescontoValor(0);
				this.getGerentePedido().getPedido().setDescontoPercentual(0);
				this.setDescontoAcrescimoPercentual(0);
			}
		}

		this.getGerentePedido().calcularTotais();
	}

	public double getAcrescimoDescontoTotalPercentual() {
		if (this.getGerentePedido().getValorTotalSugerido() == 0)
			return 0;
		return Conversao.arredondar(
				this.getAcrescimoDescontoTotalValor() / this.getGerentePedido().getValorTotalSugerido() * 100, 2);
	}

	public double getAcrescimoDescontoTotalValor() {
		return Conversao.arredondar(this.getValorTotalItens() - this.getGerentePedido().getValorTotalSugerido(), 2);
	}

	public void setAcrescimoDescontoTotalPercentual() {
	}

	public void setAcrescimoDescontoTotalValor() {
	}

	public Double getFreteValor() {
		return getGerentePedido().getPedido().getFrete();
	}

	public void setFreteValor(Double frete) {
		getGerentePedido().setFreteValor(frete);
	}

	/**
	 * 
	 * @param e
	 */
	public void acrescDescFlagChangeListener(ValueChangeEvent e) {

		String newValue = (String) e.getNewValue();

		if (StringUtil.isValida(newValue)) {
			flagAcrescDesc = newValue;
			setDescontoAcrescimoPercentual(0);
			setDescontoAcrescimoValor(0);
		}
	}

	@Override
	public void aoCarregar() {
		super.aoCarregar();

		carregarDadosPedido();

		if (getPedido() != null && getPedido().getValor() != valorDoPedidoConfiguracaoParcelas) {
			preencheParcelasCondicaoPagamento();
		}
	}

	@Override
	public String getNomePrograma() {
		return NOME_PROGRAMA;
	}

	@Override
	public String getUrlView() {

		if (gerenteLogin.isPerfilColaborador()) {
			return getUrlViewColaborador();
		} else {
			return getUrlViewCliente();
		}
	}

	@Override
	protected String getUrlViewPage() {
		return "/pages/pedido.xhtml";
	}

	@Override
	protected String getUrlViewPattern() {
		return "/pedido";
	}

	protected String getUrlViewPatternCarrinho() {
		return "/carrinho";
	}

	protected String getUrlViewPageCarrinho() {
		return "/pages/carrinho.xhtml";
	}

	public String getUrlViewCliente() {
		return getUrlViewPatternCarrinho();
	}

	public String getUrlViewColaborador() {
		return getUrlViewPattern();
	}

	@Override
	public boolean necessarioLogin() {
		return true;
	}

	/**
	 * Lista contendo todas as formas de pagamento permitidas de acordo com a
	 * configuração do pedido.
	 * 
	 * @return
	 */
	public List<? extends IFormaPagamento> getFormasPagamentoPermitidas() {
		return getGerentePedido().getFormasPagamentoPermitidas();
	}

	List<CondicaoPagamento> listaCondicoesPermitidas = null;

	/**
	 * Lista contendo todas as condições de pagamento permitidas de acordo com a
	 * configuração do pedido.
	 * 
	 * @return
	 * @throws Exception
	 */
	public List<CondicaoPagamento> getCondicoesPagamentoPermitidas() throws Exception {
		listaCondicoesPermitidas = getGerentePedido().getCondicoesPagamentoPermitidas();

		/*
		 * Se a forma de pagamento selecionada for um cartão retorna somente as formas
		 * cadastradas na administradora.
		 */
		if (listaCondicoesPermitidas != null && this.getFormaPagamentoSelecionada() != null
				&& this.getFormaPagamentoSelecionada().isCartao()) {

			FormaPagamento fpg = this.getFormaPagamentoSelecionada();
			fpg.recuperarFormaPagamentoFilial(dao, gerenteLogin.getFilialLogada().getCodigo(), false);

			/*
			 * Verifica se a forma de pagamento limita as condições de pagamento cadastradas
			 * para a administradora.
			 */
			if (fpg.getFormaPagamentoFilial() != null
					&& fpg.getFormaPagamentoFilial().getFlagLimitaCondicaoPagamentoAdministradora() == 1) {
				List<CondicaoPagamento> lista2 = new ArrayList<CondicaoPagamento>();

				fpg.recuperarCondicoesPagamentoTaxa(dao, false);

				List<FormaPagamentoCondicaoPagamentoTaxa> condsTaxa = fpg.getCondicoesPagamentoTaxa();

				if (condsTaxa != null) {
					/*
					 * Adiciona na lista somente as condicoes cadastradas para a administradora.
					 */
					for (CondicaoPagamento cond : listaCondicoesPermitidas) {

						FormaPagamentoCondicaoPagamentoTaxa cpgTaxa = FormaPagamentoCondicaoPagamentoTaxa
								.pesquisarLista(condsTaxa, cond.getCodigo());

						if (cpgTaxa != null) {

							this.gerarDescricaoCartaoCondicaoPagamento(cond, fpg, cpgTaxa);

							lista2.add(cond);
						}
					}
					listaCondicoesPermitidas = lista2;
				} else {
					listaCondicoesPermitidas = null;
				}
			}
		}

		verificarCondicaoPagamentoSelecioda();

		return listaCondicoesPermitidas;
	}

	private void verificarCondicaoPagamentoSelecioda() throws Exception {
		if (ListUtil.isValida(listaCondicoesPermitidas)) {

			boolean condicaoNaLista = isCondicaoPagamentoNaLista(listaCondicoesPermitidas,
					getCondicaoPagamentoSelecionada());

			if (!condicaoNaLista) {

				for (int i = 0; i < listaCondicoesPermitidas.size(); i++) {
					CondicaoPagamento cp = listaCondicoesPermitidas.get(i);

					try {
						setCondicaoPagamentoSelecionada(cp);
						break;
					} catch (Exception e) {

						if (i + 1 < listaCondicoesPermitidas.size()) {
							continue;
						} else {
							sessaoMB.mensagem(TipoMensagem.Erro, null, e.getMessage());
						}
					}
				}
			}

		} else {
			setCondicaoPagamentoSelecionada(null);
		}
	}

	private boolean isCondicaoPagamentoNaLista(List<CondicaoPagamento> condicoesPagamentos,
			CondicaoPagamento condicaoPagamento) {

		if (condicaoPagamento == null) {
			return false;
		}

		for (CondicaoPagamento cp : condicoesPagamentos) {

			if (condicaoPagamento.getCodigo() == cp.getCodigo()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 
	 */
	@Override
	public boolean permiteAcessoDeCliente() {

		String uri = getURLRequisicao();

		boolean permite = StringUtil.isValida(uri) && (!(uri.endsWith(getUrlViewPage()))
				&& !(uri.endsWith(getUrlViewPattern())) && !(uri.endsWith("/pedido/")));

		return permite;
	}

	public boolean desabilitarDigitacaoTipoEntrega() {
		boolean temPermissao = gerentePermissao.verificarPermissao(NOME_PROGRAMA, Permissao.ALTERA_TIPO_ENTREGA);

		return !temPermissao;
	}

	/**
	 * Condições para habilitar digitação de tabela de preços
	 * 
	 * @return
	 */
	public boolean desabilitarDigitacaoTabela() {
		if (this.getNaturezaOperacaoSelecionada() != null
				&& this.getNaturezaOperacaoSelecionada().getConfiguracaoOrigemTabelaPreco() == 1)
			return true;
		else
			return (false || this.pedidoEmAlteracao());

	}

	/**
	 * Condições para habilitar digitação de natureza de operação
	 * 
	 * @return
	 */
	public boolean desabilitarDigitacaoNaturezaOperacao() {

		boolean naoTemPermissao = !(gerentePermissao.verificarPermissao(NOME_PROGRAMA, Permissao.LANCA_NATUREZA_VENDA));

		return naoTemPermissao || this.getItensPedido().size() > 0 || this.pedidoEmAlteracao();

	}

	/**
	 * Habilitar Digitação desconto de pedido, de acordo com permissão Retorna
	 * negativo (Utilizar em disabled)
	 * 
	 * @return
	 */
	public boolean desabilitarDigitacaoAcrescimoDescontoPedido() {
		if (this.flagAcrescDesc.equals("D"))
			return !gerentePermissao.verificarPermissao(NOME_PROGRAMA, 118);
		else
			return !gerentePermissao.verificarPermissao(NOME_PROGRAMA, 154);
	}

	/**
	 * Habilitar Digitação frete, de acordo com permissão. Retorna negativo
	 * (Utilizar em disabled)
	 * 
	 * @return
	 */
	public boolean desabilitarDigitacaoFrete() {
		return !gerentePermissao.verificarPermissao(NOME_PROGRAMA, 150)
				|| ((ParametroVenda3) getParametros().getParametroVenda3()).getFlagCalculaFreteRegiao() == 1;
	}

	/**
	 * Habilita o campo de numero de entregas somente se o tipo da entrega for para
	 * entregar.
	 * 
	 * @return
	 */
	public boolean desabilitarDigitacaoNumeroEntregas() {

		boolean temPermissao = gerentePermissao.verificarPermissao(NOME_PROGRAMA, Permissao.LANCA_NUMERO_ENTREGA);

		boolean tipoPermiteDigitacao = !StringUtil.isValida(getPedido().getTipoSeparacao())
				|| "ENTREGA^TRANSPORTADOR CIF^TRANSPORTADOR FOB".contains(getPedido().getTipoSeparacao());

		return !(temPermissao && tipoPermiteDigitacao);
	}

	/**
	 * Habilitar digitação vendedor, de acordo com permissão. Retorna negativo
	 * (utilizar em disabled)
	 * 
	 * @return
	 */
	public boolean desabilitarDigitacaoVendedor() {

		boolean naoTemPermissao = (!gerentePermissao.verificarPermissao(NOME_PROGRAMA,
				Permissao.ALTERA_VENDEDOR_SUGERIDO));

		/*
		 * return (naoTemPermissao && (this.getVendedorSelecionado() != null) || (this
		 * .pedidoEmAlteracao()));
		 */

		boolean desabilitarCampo = (naoTemPermissao || pedidoEmAlteracao()
				|| (getNaturezaOperacaoSelecionada() != null && getNaturezaOperacaoSelecionada()
						.getConfiguracaoLancamentoVendedor() == NaturezaOperacao.LACAMENTO_VENDEDOR_DO_USUARIO));

		return desabilitarCampo && getVendedorSelecionado() != null;
	}

	/**
	 * Habilitar Exibição de Comissão no Pedido Retorna positivo (Utitlizar em
	 * RENDERED)
	 * 
	 * @return
	 */
	public boolean habilitarExibicaoComissaoPedido() {
		return gerentePermissao.verificarPermissao(NOME_PROGRAMA, 111);
	}

	public boolean desabilitarDigitacaoOrigem() {
		return getNaturezaOperacaoSelecionada() != null && !getNaturezaOperacaoSelecionada().isUtilizaOrigemVenda();

	}

	/**
	 * 
	 * @return
	 */
	public boolean isNaturezaOperacaoConsisteEstoque() {

		if (getNaturezaOperacaoSelecionada() != null) {
			return getNaturezaOperacaoSelecionada().getFlagConsisteEstoque() == 1;
		}

		return false;
	}

	public void receberHtmlPedido(String html) {
		setHtmlPedidoConfirmado(html);
	}

	/**
	 * Metodo responsavel pela verificação se a caixa informada esta disponivel para
	 * a venda: </br>
	 * </br>
	 * <strong> Regra </strong>
	 * <ul>
	 * <li>Se o perfil de login é colaborador: TRUE</li>
	 * <li>Ou se a caixa permite venda sem estoque ou se tem estoque ou se a
	 * natureza de operação não consiste estoque: TRUE</li>
	 * <li>Caso contrario: FALSE</li>
	 * </ul>
	 * 
	 * 
	 * 
	 * @param produtoVisualizavel Caixa para verificação
	 * @return
	 */
	public boolean isPermiteVenda(CaixaProdutoVisualizavel produtoVisualizavel) {

		return !produtoVisualizavel.isEmBalanco()
				&& (gerenteLogin.isPerfilColaborador() || ((produtoVisualizavel.getEstoqueVisualizacao() > 0
						|| (!isNaturezaOperacaoConsisteEstoque()) || produtoVisualizavel.isPermiteVenderSemEstoque())));
	}

	/**
	 * Registra o retorno da requisição no banco de dados.
	 * 
	 * @param idTransacaoBanco
	 */
	private TransacaoPagamentoCartao registrarRetornoPedido(String idTransacaoLocal, IRetorno retorno) {

		if (retorno == null)
			return null;

		Date dataTransacao = new Date();
		String horaTransacao = Conversao.formatarDataHHMMSS(dataTransacao);
		String idTransacao = "";
		String status = "";
		String statusCodigo = "";
		String statusMensagem = "";
		String comprovanteVenda = "";

		String formaPagamentoCodigo = this.getFormaPagamentoSelecionada().getCodigo();
		int condicaoPagamentoCodigo = this.getCondicaoPagamentoSelecionada().getCodigo();
		String log = null;

		CredencialAdministradoraCartao cred = (CredencialAdministradoraCartao) retorno.getRequisicao().getCredencial();

		if (retorno instanceof IRetornoTransacao && retorno.isSucesso()) {
			IRetornoTransacao rt = (IRetornoTransacao) retorno;

			idTransacao = rt.getIdTransacao();
			status = rt.getStatusTransacao().toSigla();
			statusCodigo = rt.getStatus();
			statusMensagem = rt.getMensagemRetorno();
			log = rt.getConteudoRetorno();
			comprovanteVenda = rt.getComprovanteVenda();
		}

		TransacaoPagamentoCartao transacao = new TransacaoPagamentoCartao(this.getPedido().getFilialCodigo(),
				this.getPedido().getSerieCodigo(), this.getPedido().getNumero(), dataTransacao, horaTransacao,
				formaPagamentoCodigo, condicaoPagamentoCodigo, cred.getCodigo(), idTransacao, status, statusCodigo,
				statusMensagem, this.getPedido().getValor(), log);

		transacao.setComprovanteVenda(comprovanteVenda);

		transacao.setTipoCartaoDebitoCredito(getFormaPagamentoSelecionada().getTipoCartaoDebitoCredito());

		transacao.setIdTransacaoLocal(idTransacaoLocal);

		dao.insertObject(transacao);

		return transacao;
	}

	/**
	 * 
	 * @param transacaoPagamentoCartao
	 * @param ret
	 */
	private void atualizarStatusTransacaoPagamento(TransacaoPagamentoCartao transacaoPagamentoCartao,
			IRetornoTransacao ret) {

		transacaoPagamentoCartao.setDados(ret);
	}

	/**
	 * @param novoIdTransacao Este id será utilizado para atulizar todos os ids de
	 *                        transacao
	 */
	private void consultarTransacoesPedido(boolean atualizarStatusPedido, boolean confirmarAutorizadas) {

		mensagemPagamentoCartao = "";
		try {

			/*
			 * Verifica se existe um pedido criado. Evitar erro quando digitar a url
			 * diretamente pelo browser.
			 */
			if (this.getPedido() != null && this.getPedido().getNumero() > 0) {

				List<TransacaoPagamentoCartao> trans = tratarTransacoesPedido(dao, daoLog, getParametros(), propriedade,
						confirmarAutorizadas, false, getPedido());

				dao.beginTransaction();

				/*
				 * List<TransacaoPagamentoCartao> trans = TransacaoPagamentoCartao
				 * .recuperar(dao, this.getPedido().getFilialCodigo(),
				 * this.getPedido().getSerieCodigo(), this .getPedido().getNumero(), null,
				 * true);
				 */

				int contAutorizada = 0;
				int contConfirmada = 0;

				/*
				 * Percorre todas as transações criadas para o pedido e verifica o seu status
				 * junto à administradora.
				 */

				for (TransacaoPagamentoCartao transacaoPagamentoPedido : trans) {

					if (transacaoPagamentoPedido.getStatusTransacao() == StatusTransacao.Autorizada
							|| transacaoPagamentoPedido.getStatusTransacao() == StatusTransacao.Confirmada) {
						mensagemPagamentoCartao = transacaoPagamentoPedido.getComprovanteVenda();
					} else {
						mensagemPagamentoCartao = transacaoPagamentoPedido.getStatusMensagem();
					}

					/*
					 * Atualiza o status da transacao no bando de dados.
					 */
					dao.update(transacaoPagamentoPedido);

					if (transacaoPagamentoPedido.getStatusTransacao() == StatusTransacao.Autorizada) {
						contAutorizada++;
					} else if (transacaoPagamentoPedido.getStatusTransacao() == StatusTransacao.Confirmada) {
						contConfirmada++;
					}
				}

				if (atualizarStatusPedido) {
					/*
					 * Atualiza o status de pagamento do pedido de acordo com o status das
					 * transacoes.
					 */
					int statusPagamento = Pedido.STATUS_PAGAMENTO_CARTAO_SEM_PAGAMENTO;

					if (contAutorizada > 0)
						statusPagamento = Pedido.STATUS_PAGAMENTO_CARTAO_AGUARDANDO_PAGAMENTO;
					else if (contConfirmada > 0)
						statusPagamento = Pedido.STATUS_PAGAMENTO_CARTAO_CONFIRMADO;

					((Pedido) this.getPedido()).setStatusPagamentoCartao(statusPagamento);

					dao.update(this.getPedido());
				}

				dao.commitTransaction();
			}
		} catch (Exception e) {
			e.printStackTrace();
			dao.rollBackTransaction();
		}
	}

	private void cancelarTransacoesPedido(int filialCodigo, String pedidoSerie, int pedidoNumero,
			boolean atualizarStatusPedido, boolean confirmarAutorizadas) {
		mensagemPagamentoCartao = "";
		try {
			/*
			 * Verifica se existe um pedido criado. Evitar erro quando digitar a url
			 * diretamente pelo browser.
			 */
			if (this.getPedido() != null && this.getPedido().getNumero() > 0) {
				dao.beginTransaction();

				List<TransacaoPagamentoCartao> trans = TransacaoPagamentoCartao.recuperar(dao, filialCodigo,
						pedidoSerie, pedidoNumero, null, true);

				int contCancelada = 0;
				int totalTransacoes = 0;

				if (trans != null) {
					totalTransacoes = trans.size();
				}

				/*
				 * Percorre todas as transações criadas para o pedido e verifica o seu status
				 * junto à administradora. Se não estiver cancelada, envia a mensagem de
				 * cancelamento.
				 */
				if (totalTransacoes == 0) {
					mensagemPagamentoCartao = propriedade.getMensagem("alerta.nenhumaTransacaoProcessamento");
				} else {
					for (TransacaoPagamentoCartao transacaoPagamentoPedido : trans) {
						Requisicao req = obterRequisicaoCartao(false);

						IRetorno retorno = req.consultarTransacao(transacaoPagamentoPedido.getIdTransacao());

						if (retorno instanceof IRetornoTransacao) {
							IRetornoTransacao ret = (IRetornoTransacao) retorno;

							/*
							 * Verifica se a transacao ainda não está cancelada e envia a mensagem de
							 * cancelamento.
							 */
							if (confirmarAutorizadas && ret.getStatusTransacao() != StatusTransacao.Cancelada) {
								IRetorno ret2 = req.cancelarTransacao(transacaoPagamentoPedido.getIdTransacao());
								if (ret2 instanceof IRetornoTransacao)
									ret = (IRetornoTransacao) ret2;
							}

							atualizarStatusTransacaoPagamento(transacaoPagamentoPedido, ret);

							/*
							 * Atualiza o status da transacao no bando de dados.
							 */
							dao.update(transacaoPagamentoPedido);

							if (transacaoPagamentoPedido.getStatusTransacao() == StatusTransacao.Cancelada) {
								contCancelada++;
							}
						}
					}
				}

				if (atualizarStatusPedido) {
					/*
					 * Atualiza o status de pagamento do pedido de acordo com o status das
					 * transacoes.
					 */
					int statusPagamento = 0;
					if (contCancelada == totalTransacoes || totalTransacoes == 0) {
						statusPagamento = 0;

						((Pedido) this.getPedido()).setStatusPagamentoCartao(statusPagamento);
						dao.update(this.getPedido());
					}
				}
				dao.commitTransaction();
			}
		} catch (Exception e) {
			e.printStackTrace();
			dao.rollBackTransaction();
		}
	}

	@URLActions(actions = { @URLAction(mappingId = "pagamentoConfirmacao", onPostback = false),
			@URLAction(mappingId = "pagamentoConfirmacaoBarra", onPostback = false) })
	public void urlActionConfirmacaoPagamento() throws Exception {

		ParametroRetornoUrlPagamentoCartao parametroRetorno = new ParametroRetornoUrlPagamentoCartao(true);

		tratarIDTRansacaoRequisicao(parametroRetorno);

		CodigoReferencia codigoReferencia = new CodigoReferencia(parametroRetorno.idReferencia);

		String urlRedirecionar = arquivo.getRootPath() + historicoPedidosMB
				.imprimirPedido(codigoReferencia.getSeriePedido(), codigoReferencia.getNumeroPedido(), true);

		redirecionar(urlRedirecionar);
		// confirmarPagamentoPedidoComCartão();
	}

	private void tratarIDTRansacaoRequisicao(ParametroRetornoUrlPagamentoCartao parametroPagamentoCartao) {

		try {

			if (StringUtil.isValida(parametroPagamentoCartao.idTransacao)) {

				TransacaoPagamentoCartao.atulizarIdTransacao(dao, parametroPagamentoCartao.idTransacao,
						parametroPagamentoCartao.idReferencia);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	String mensagemPagamentoCartao;

	public String getMensagemPagamentoCartao() {
		return mensagemPagamentoCartao;
	}

	/**
	 * 
	 * @return
	 * @throws FabricaNulaExcecao
	 */
	private Requisicao obterRequisicaoCartao(boolean obterNova) throws FabricaNulaExcecao {

		/*
		 * Pega a credencial da forma de pagamento.
		 */
		ICredencial cred = this.getFormaPagamentoSelecionada().getFormaPagamentoFilial()
				.getCredencialAdministradoraCartao();

		boolean jaExiste = hashMapRequisicao.containsKey(cred);
		// Verifica se quer uma nova instancia ou caso não exista cria
		if (obterNova || !jaExiste) {

			/*
			 * Cria-se a requisição de acordo com a credencial.
			 */
			Requisicao requisicao = new Requisicao(cred.getAmbienteTrabalhoChave(), cred);

			hashMapRequisicao.put(cred, requisicao);
		}

		return hashMapRequisicao.get(cred);
	}

	/**
	 * 
	 * @throws TransacaoNaoAutorizadaExcecao
	 */
	private void verificarTransacoesCartaoConfirmadas()
			throws TransacaoConfirmadaExcecao, TransacaoNaoAutorizadaExcecao {

		if (this.getPedido() != null && this.getPedido().getNumero() > 0) {

			Pedido ped = (Pedido) this.getPedido();
			if (ped.getStatusPagamentoCartao() == Pedido.STATUS_PAGAMENTO_CARTAO_SEM_PAGAMENTO) {

				throw new TransacaoNaoAutorizadaExcecao();
			}
		}
	}

	/**
	 * 
	 * @param novoIdTransacao Este id será utilizado para atulizar todos os ids de
	 *                        transacao para este pedido
	 * @return
	 * @throws Exception
	 */
	private String confirmarPagamentoPedidoComCartão() throws Exception {
		if (this.getPedido() != null && this.getPedido().getNumero() > 0) {

			verificarTravaPedido();

			this.consultarTransacoesPedido(true, true);

			this.verificarTransacoesCartaoConfirmadas();

			/*
			 * Seta flag para venda de consumidor final, para fechamento normal de pedido
			 * (baixando estoque e gravando status = 2)
			 */
			this.getGerentePedido().setFlagVendaConsumidor(false);

			return this.finalizarPedido(false);
		} else
			return null;
	}

	/**
	 * Retorna se o pagamento com cartão foi autorizado ou confirmado.
	 * 
	 * @return
	 */
	public boolean isPagamentoCartaoAutorizado() {
		if (this.getPedido() != null)
			return ((Pedido) this.getPedido()).getStatusPagamentoCartao() > 0;
		else
			return false;
	}

	/**
	 * 
	 * @return
	 */
	public boolean isPedidoImpressaoPagamentoCartaoAutorizado() {
		if (pedidoImpressao != null)
			return ((Pedido) pedidoImpressao).getStatusPagamentoCartao() > 0;
		else
			return false;
	}

	/**
	 * 
	 * @param condicao
	 * @return
	 */

	public void gerarDescricaoCartaoCondicaoPagamento(CondicaoPagamento cpg, FormaPagamento fpg,
			FormaPagamentoCondicaoPagamentoTaxa cpgTaxa) {

		StringBuilder descricaoCartao = new StringBuilder(cpg.getDescricao());

		fpg.recuperarFormaPagamentoFilial(dao, this.getPedido().getFilialCodigo(), false);

		if (fpg.isCartao()) {
			descricaoCartao.append(" (").append(cpg.getNumeroParcelas()).append("x ");

			double valorParcela = Matematica.calcularValorParcelaPrice(this.getPedido().getValor(),
					cpgTaxa.getJurosParcelamentoPercentual(), cpg.getNumeroParcelas());

			descricaoCartao.append(Conversao.formatarValor2(valorParcela));

			if (cpgTaxa.getJurosParcelamentoPercentual() > 0)
				descricaoCartao.append(" ").append(propriedade.getMensagem("texto.comJuros")).append("*");
			else
				descricaoCartao.append(" ").append(propriedade.getMensagem("texto.semJuros"));
			descricaoCartao.append(")");
		}

		cpg.setDescricaoCartao(descricaoCartao.toString());
	}

	/**
	 * 
	 * @return
	 * 
	 */

	/**
	 * 
	 * @throws Exception
	 */
	public RetornoIntegracaoCartao verificarEIntegrarPagamentoComCartao() throws Exception {
		boolean integraComCartao = false;

		this.pedidoImpressao = null;

		FormaPagamento fpg = this.getFormaPagamentoSelecionada();

		fpg.recuperarFormaPagamentoFilial(dao, this.getPedido().getFilialCodigo(), true);

		FormaPagamentoFilial fpgFilial = fpg.getFormaPagamentoFilial();

		if (!fpg.isCartao())
			return new RetornoIntegracaoCartao(false, null);

		/*
		 * Verifica se o cadastro de forma de pagamento está completo. Se estiver
		 * definido que é para fazer integração e sem a credencial está configurada.
		 */
		if (fpgFilial == null || (fpgFilial.getFlagIntegraPagamentoAdministradora() == 1
				&& fpgFilial.getCredencialAdministradoraCartao() == null)) {

			throw new SpaceExcecao(propriedade.getMensagem("alerta.cadastroFormaPagamentoIncompleto"));
		}

		integraComCartao = isIntegraCartaoAdministradora(fpg, fpgFilial);

		if (integraComCartao) {
			return integrarComCartaoCredito(fpg);
		}

		return new RetornoIntegracaoCartao(integraComCartao, null);
	}

	/**
	 * Realiza a integração com o cartao de credito
	 * 
	 * @param fpg A forma de pagamento utilizada
	 * @return A Url para redirecionar o fluxo de pagamento
	 * 
	 * @throws Exception Caso algum problema aconteça
	 */
	private RetornoIntegracaoCartao integrarComCartaoCredito(FormaPagamento fpg) throws Exception {

		CondicaoPagamento cpg = this.getCondicaoPagamentoSelecionada();

		validarPedidoParaPagamentoCartao();

		/*
		 * Pega a condicao de pagamento cadastrada na administradora.
		 */
		FormaPagamentoCondicaoPagamentoTaxa cpgAdm = FormaPagamentoCondicaoPagamentoTaxa.recuperarUnico(dao,
				fpg.getCodigo(), cpg.getCodigo());

		if (cpgAdm == null) {
			throw new SpaceExcecao(
					this.getPropriedade().getMensagem("alerta.pedido.condicaopagamento.formapagento.naocompativeis"));
		}

		IRetorno retorno = null;

		idReferenciaPedido = gerarCodigoReferencia(getPedido(),
				fpg.getFormaPagamentoFilial().getCredencialAdministradoraCartao().getAdministradoraCartao());

		String urlRetorno = arquivo.getCompleteRootURL() + URL_RETORNO_PAGAMENTO_CARTAO + "?" + PARAMETRO_ID_REFERENCIA
				+ "=" + idReferenciaPedido;

		retorno = criarTransacaoCartaoCredito(getPedido(), idReferenciaPedido, getItensPedido(),
				cpg.getNumeroParcelas(), cpgAdm, fpg, urlRetorno);

		TransacaoPagamentoCartao transacaoPagamentoCartao = registrarRetornoPedido(idReferenciaPedido, retorno);

		/*
		 * Se o retorno é uma transacao, significa que existe uma url para onde o fluxo
		 * deve ser redirecionado. Caso contrário ocorreu algum erro.
		 */
		if (retorno instanceof IRetornoTransacao
				&& StringUtil.isValida(((IRetornoTransacao) retorno).getUrlAutenticacao())) {

			return new RetornoIntegracaoCartao(true, ((IRetornoTransacao) retorno).getUrlAutenticacao(),
					transacaoPagamentoCartao);

		} else {
			throw new SpaceExcecao(retorno.getMensagemRetorno());
		}
	}

	/**
	 * Verifica se o pedido ja teve alguma solicitação de pagamento com cartão
	 * 
	 * @throws Exception Caso algum incomformidade exista no pedido
	 */
	private void validarPedidoParaPagamentoCartao() throws Exception {

		/*
		 * Verifica se o pedido já possui algum pagamento autorizado/confirmado.
		 */
		Pedido ped = (Pedido) this.getGerentePedido().getPedido();

		if (ped.getStatusPagamentoCartao() >= Pedido.STATUS_PAGAMENTO_CARTAO_AGUARDANDO_PAGAMENTO) {
			throw new PedidoPagamentoCartaoJaAutorizadoExcecao(this.getPropriedade());
		}
	}

	public void classificarFormasPagto(Integer codigoClassificacao) {
		setCodClassificacaoPagtoSelecionada(codigoClassificacao);

		formasPagtoCassificadas = new ArrayList<>();

		for (IFormaPagamento formPag : gerentePedido.getFormasPagamentoPermitidas()) {

			if (((FormaPagamento) formPag).getClassificaDocumento().getCodigo()
					.equals(codClassificacaoPagtoSelecionada))

				formasPagtoCassificadas.add((FormaPagamento) formPag);
		}
	}

	public Integer getCodClassificacaoPagtoSelecionada() {
		if (codClassificacaoPagtoSelecionada == null) {
			int classificacaoPadrao = ((FormaPagamento) gerentePedido.getFormaPagamento()).getClassificaDocumento()
					.getCodigo();
			setCodClassificacaoPagtoSelecionada(classificacaoPadrao);
			classificarFormasPagto(classificacaoPadrao);
		}
		return codClassificacaoPagtoSelecionada;
	}

	public void setCodClassificacaoPagtoSelecionada(Integer codClassificacaoPagtoSelecionada) {
		this.codClassificacaoPagtoSelecionada = codClassificacaoPagtoSelecionada;
	}

	public List<FormaPagamento> getFormasPagtoCassificadas() {
		return formasPagtoCassificadas;
	}

	public void setFormasPagtoCassificadas(List<FormaPagamento> formasPagtoCassificadas) {
		this.formasPagtoCassificadas = formasPagtoCassificadas;
	}

	public String getHtmlPedidoConfirmado() {
		return htmlPedidoConfirmado;
	}

	public void setHtmlPedidoConfirmado(String htmlPedidoConfirmado) {
		this.htmlPedidoConfirmado = htmlPedidoConfirmado;
	}

	public double getSaldoCreditoCliente() {
		return saldoCreditoCliente;
	}

	public boolean isExibeLimiteCreditoCliente() {
		return getGerentePedido().isConsisteLimite() && parametroWebMB.getParametroWeb().isExibeLimiteCliente();
	}

	public boolean isExibeInformacaoDescontoOferta() {
		return !getGerentePedido().isRateiaDescontoPedidoItensEmOferta() && flagAcrescDesc.equals(FLAG_DESCONTO);
	}

	@Override
	public void comunicarUsuario(String mensagem) {

		if (StringUtil.isValida(mensagem)) {
			exibirMensagemInformacao(mensagem, null);
		}
	}

	class RetornoIntegracaoCartao {
		boolean integraComCartao = false;
		String urlRedirecionamento = null;
		TransacaoPagamentoCartao transacaoPagamentoCartao = null;

		public RetornoIntegracaoCartao(boolean integraComCartao, String urlRedirecionamento) {
			super();
			this.integraComCartao = integraComCartao;
			this.urlRedirecionamento = urlRedirecionamento;
		}

		public RetornoIntegracaoCartao(boolean integraComCartao, String urlRedirecionamento,
				TransacaoPagamentoCartao transacaoPagamentoCartao) {
			this(integraComCartao, urlRedirecionamento);
			this.transacaoPagamentoCartao = transacaoPagamentoCartao;
		}

		public boolean isIntegracaoValida() {
			return integraComCartao && StringUtil.isValida(urlRedirecionamento);
		}

		public void validarPagamento() throws SpaceExcecao {
			if (integraComCartao && !StringUtil.isValida(urlRedirecionamento)) {
				throw new SpaceExcecao(getPropriedade().getMensagem("alerta.pedido.pagamento.cartao.malsucedido"));

			}

		}
	}

	class ParametroRetornoUrlPagamentoCartao {

		String idTransacao = null;
		String idReferencia = null;

		public ParametroRetornoUrlPagamentoCartao(boolean isRecuperarParametros) {

			if (isRecuperarParametros) {

				FacesContext context = FacesContext.getCurrentInstance();

				HttpServletRequest request = (HttpServletRequest) context.getExternalContext().getRequest();

				idTransacao = request.getParameter(PARAMETRO_ID_TRANSACAO);

				idReferencia = request.getParameter(PARAMETRO_ID_REFERENCIA);
			}
		}
	}

	public boolean isRequerIdentificacaoCliente() {
		return parametroWebMB.getParametroWeb().isRequerIdentificacaoCliente();
	}

	public String validarIrPagamento() throws SpaceExcecao {
		
		if (getPedido() instanceof Pedido && isRequerIdentificacaoCliente()
				&& !StringUtil.isValida(getPedido().getClienteIdentificacao())) {
			throw new SpaceExcecao(propriedade.getMensagem("texto.idetificacao.cliente"));
		}
		
		return "/pagamento/";
	}
}
