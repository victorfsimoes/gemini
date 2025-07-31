package br.com.space.spacewebII.controle.pedido.mbean;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.jboss.seam.security.annotations.LoggedIn;
import org.primefaces.event.TabChangeEvent;

import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IItemKit;
import br.com.space.api.negocio.modelo.dominio.IItemPedido;
import br.com.space.api.negocio.modelo.dominio.Ikit;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.api.negocio.modelo.dominio.produto.PrecoVenda;
import br.com.space.api.negocio.sistema.IDSistema;
import br.com.space.spacewebII.controle.aplicacao.GerenteEmail;
import br.com.space.spacewebII.controle.autorizacao.mbean.GerenteAutorizacaoMB;
import br.com.space.spacewebII.controle.padrao.mbean.GerenteMB;
import br.com.space.spacewebII.controle.pedido.FabricaGerentePedido;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.endereco.Enderecos;
import br.com.space.spacewebII.modelo.dominio.estoque.FabricaPrecificacao;
import br.com.space.spacewebII.modelo.dominio.estoque.NaturezaOperacao;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoFilial;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.pessoa.Pessoa;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.venda.ItemPedido;
import br.com.space.spacewebII.modelo.dominio.venda.Kit;
import br.com.space.spacewebII.modelo.dominio.venda.KitPedido;
import br.com.space.spacewebII.modelo.dominio.venda.LocalRetira;
import br.com.space.spacewebII.modelo.dominio.venda.LogPedido;
import br.com.space.spacewebII.modelo.dominio.venda.OpcaoEspecial;
import br.com.space.spacewebII.modelo.dominio.venda.OpcaoEspecialFilial;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;
import br.com.space.spacewebII.modelo.dominio.venda.StatusPedido;
import br.com.space.spacewebII.modelo.dominio.venda.TabelaPreco;
import br.com.space.spacewebII.modelo.dominio.venda.Transportadora;
import br.com.space.spacewebII.modelo.dominio.venda.Vendedor;
import br.com.space.spacewebII.modelo.excecao.EnvioEmailExcecao;

@ManagedBeanSessionScoped
@LoggedIn
@URLBeanName("historicoPedidosMB")
@URLMappings(mappings = {
		@URLMapping(id = "meusPedidos", pattern = "/meusPedidos/", viewId = "/pages/meusPedidos.xhtml", onPostback = false),
		@URLMapping(id = "resumoPedido", pattern = "/resumoPedido/", viewId = "/pages/pedido/resumoPedido.xhtml", onPostback = false) })
public class HistoricoPedidosMB extends GerenteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<LogPedido> logPedidoSelecionado;
	private List<String> logStatusString;
	private List<ItemPedido> itensPedidoSelecionado;
	private List<Produto> produtosItensPedidoSelecionado;

	private List<StatusPedido> logStatus;

	private List<StatusPedido> statusHistoricoPedidos;

	private Cliente clientePedidoSelecionado;
	private Map<String, Object> razaoPessoas = null;

	@Inject
	private Parametros parametros;

	@Inject
	private GerenteAutorizacaoMB gerenteAutorizacaoMB;

	private GerentePedido gerentePedido;

	private List<Pedido> historicoPedidos;

	/* FILTROS DE PESQUISA */
	private Date dataMin;
	private Date dataMax;
	private String[] statusPedidoCodigoSelecionados = null;

	private Cliente filtroCliente;

	/*
	 * Utilizados para impressão de pedido
	 */
	private List<ParcelaCondicaoPagamento> parcelasCondicaoPagamento;
	private Transportadora transportadoraSelecionada;
	private LocalRetira localRetiraSelecionada;
	private Filial filial;
	/*
	 * Configurações da impressão de pedido
	 */
	private boolean exibirTabelaPrecos = false;
	private boolean exibirColaboradores = false;
	private String notaRodape = null;
	private Integer modeloImpressaoKit = null;
	private boolean jaBuscouPedidosInicialmente = false;
	private boolean ePedidoHistorico = true;
	private boolean flagPrecoRevenda = false;
	private String estiloDescricao = "tabIColDesc displayInlineBlock";

	/**
	 * 
	 */
	public HistoricoPedidosMB() {
	}

	@Override
	public void aoCarregar() {
		super.aoCarregar();

		if (!ListUtil.isValida(logStatus)) {
			try {
				logStatus = StatusPedido.recuperarTodosStatusAtivos(dao);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (gerenteLogin.isPerfilCliente() && statusPedidoCodigoSelecionados == null) {
			preencherStatusPedidoSelecionadoComStatusParaCliente();
		} else if (statusPedidoCodigoSelecionados == null) {
			preencherStatusPedidoSelecionadoComStatusEditaveis();
		}

		if (!ListUtil.isValida(historicoPedidos) && !jaBuscouPedidosInicialmente) {
			preencherHistoricoPedidos();
		}

	}

	private void prepararVariaveis() {
		logPedidoSelecionado = null;
		logStatusString = null;
		itensPedidoSelecionado = null;
		produtosItensPedidoSelecionado = null;
		clientePedidoSelecionado = null;
		this.historicoPedidos = null;
		this.razaoPessoas = null;
	}

	/**
	 * Por padrão, carrega apenas status possíveis de serem editados
	 */
	private void preencherStatusPedidoSelecionadoComStatusEditaveis() {
		statusPedidoCodigoSelecionados = new String[] { Integer.toString(StatusPedido.STATUS_NAO_SEPARADO),
				Integer.toString(StatusPedido.STATUS_EM_ALTERACAO), Integer.toString(StatusPedido.STATUS_SEPARADO),
				Integer.toString(StatusPedido.STATUS_SEPARADO_PARCIAL) };
	}

	private void preencherStatusPedidoSelecionadoComStatusParaCliente() {
		statusPedidoCodigoSelecionados = new String[] { Integer.toString(StatusPedido.STATUS_NAO_SEPARADO),
				Integer.toString(StatusPedido.STATUS_SEPARADO), Integer.toString(StatusPedido.STATUS_SEPARADO_PARCIAL),
				Integer.toString(StatusPedido.STATUS_BLOQUEADO), Integer.toString(StatusPedido.STATUS_CANCELADO),
				Integer.toString(StatusPedido.STATUS_EM_ALTERACAO),
				Integer.toString(StatusPedido.STATUS_ENVIADO_OUTRO_SETOR),
				Integer.toString(StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB) };
	}

	/**
	 * Preenche os dados necessários sobre o pedido selecionado, que será detalhado
	 * na tela.
	 */
	public void selecionarPedido(Pedido pedido) {

		if (pedido != null) {

			logPedidoSelecionado = LogPedido.recuperarLogPedido(dao, pedido.getNumero(), pedido.getSerieCodigo(),
					pedido.getFilialCodigo());

			logStatusString = new ArrayList<>();

			for (LogPedido log : logPedidoSelecionado) {
				for (StatusPedido status : logStatus) {
					if (log.getStatusPedidoCodigo() == status.getCodigo())
						logStatusString.add(status.getDescricao());
				}
			}

			itensPedidoSelecionado = ItemPedido.recuperarItensPedidoApiSpace(dao, pedido.getNumero(),
					pedido.getSerieCodigo(), pedido.getFilialCodigo());

			produtosItensPedidoSelecionado = ItemPedido.recuperarProdutosPedido(dao, pedido.getNumero(),
					pedido.getSerieCodigo(), pedido.getFilialCodigo());

			clientePedidoSelecionado = Cliente.recuperarUnico(dao, pedido.getPessoaCodigo());

		} else {
			logPedidoSelecionado = null;
			itensPedidoSelecionado = null;
		}
	}

	/**
	 * Recupera todos os pedidos do usuário logado.
	 */
	private String preencherHistoricoPedidos() {

		String dias = String.valueOf(parametroWebMB.getParametroWeb().getDiasConsultaPedidos());

		long diasRetroagirPesquisa = Long.parseLong(dias) * (24L * 60L * 60L * 1000L);

		long dataInicialPesquisa = System.currentTimeMillis() - diasRetroagirPesquisa;

		Date dataini = new Date(dataInicialPesquisa);
		Date datahoje = new Date();

		String dataMinStr = Conversao.obterAno(dataini) + "/" + (Conversao.obterMes(dataini) + 1) + "/"
				+ Conversao.obterDiaMes(dataini);

		dataMin = Conversao.converterStringParaDate(dataMinStr, Conversao.FORMATO_DATA_YMD);
		dataMax = datahoje;

		pesquisarPedidosComFiltros();
		jaBuscouPedidosInicialmente = true;

		return "/pages/meusPedidos.xhtml";
	}

	/**
	 * Recebe o evento de mudança de pedidos, para carregar seu detalhamento.
	 * 
	 * @param event
	 */
	public void pedidosTabChange(TabChangeEvent event) {

		Object object = event.getData();

		if (object instanceof Pedido) {
			selecionarPedido(((Pedido) object));
		}
	}

	/**
	 * 
	 */
	public void pesquisarPedidosComFiltros() {

		if (gerenteLogin.isPerfilCliente()) {

			setHistoricoPedidos(Pedido.recuperarPedidosFiltradosCliente(dao, gerenteLogin.getClienteCodigo(),
					gerenteLogin.getFilialLogada().getCodigo(), dataMin, dataMax, statusPedidoCodigoSelecionados));

		} else {

			setHistoricoPedidos(Pedido.recuperarPedidosFiltradosUsuario(dao, gerenteLogin.getColaboradorCodigo(),
					gerenteLogin.getFilialLogada().getCodigo(), dataMin, dataMax, statusPedidoCodigoSelecionados,
					filtroCliente));
		}
	}

	/**
	 * envia email com dados de confirmação do pedido
	 * 
	 * @param pedido
	 */
	public void enviarEmailConfirmacaoPedido(IPedidoWeb pedido, List<? extends IItemPedidoWeb> itens,
			GerentePedido gerentePedido) {

		Enderecos enderecos = Enderecos.localizarEndereco(gerentePedido.getEnderecosCliente(),
				pedido.getEnderecoEntregaCodigo());

		String end = enderecos != null ? enderecos.toString() : "";

		String conteudo = GerenteEmail.gerarConteudoEmailPedido(propriedade, pedido, itens,
				formatacaoMB.getPrimeiroNome(gerentePedido.getCliente().getRazao()),
				propriedade.getMensagem("mensagem.foifinalizado"), gerentePedido.getFormaPagamento().getDescricao(),
				gerentePedido.getCondicaoPagamento().getDescricao(), end, parametros, dao) + "<br/>";

		String destinatario = Cliente.recuperarUnico(dao, gerentePedido.getCliente().getCodigo()).getEmailLoginWeb();

		String assunto = propriedade.getMensagem("texto.confirmacaoPedido", pedido.getSerieENumeroPedido("/"));

		GerenteEmail.enviarEmail(parametros.getParametro1(), destinatario, assunto, conteudo);
	}

	public String imprimirPedido(String pedidoSerie, int pedidoNumero, boolean enviarEmailConfirmacao) {

		try {
			Pedido pedido = Pedido.recuperarUnicoPedido(dao, pedidoNumero, pedidoSerie, getFilialCodigo());
			return imprimirPedido(pedido, enviarEmailConfirmacao);
		} catch (EnvioEmailExcecao e) {
			e.printStackTrace();
			return getUrlViewResumo();
		}
	}

	/**
	 * Imprime resumo de pedido
	 * 
	 * @param pedido
	 * @param enviarEmailConfirmacao FALSE:pedidos recuperados do histórico.
	 *                               TRUE:pedidos que acabaram de ser confirmados
	 * @return
	 * @throws EnvioEmailExcecao
	 */
	public String imprimirPedido(final IPedidoWeb pedido, boolean enviarEmailConfirmacao) throws EnvioEmailExcecao {

		double valorTotalItens = 0.0;

		// enviar email apenas para pedidos que acabaram de ser confirmados
		setePedidoHistorico(!enviarEmailConfirmacao);

		/*
		 * Preenche pedido do gerentePedido
		 */
		this.getGerentePedido().setPedido(pedido);

		/*
		 * Preenche lista de itens
		 */
		final List<ItemPedido> itensAuxiliar = ItemPedido.recuperarItensPedido(dao,
				this.getGerentePedido().getPedido().getNumero(), this.getGerentePedido().getPedido().getSerieCodigo(),
				this.getGerentePedido().getPedido().getFilialCodigo());

		this.getGerentePedido().getItensPedido().clear();
		this.getGerentePedido().getKitsPedido().clear();

		Ikit kitAux = null;
		IItemKit itemKitMaiorQuantidade = null;
		double quantidadeKit = 0;

		setFlagPrecoRevenda(parametros.getParametroWeb().getTabelaPrecoRevenda() != 0);

		for (IItemPedido itemPedido : itensAuxiliar) {
			Produto produto = Produto.recuperarCodigo(dao, itemPedido.getProdutoCodigo());
			ProdutoUnidade produtoUnidade = ProdutoUnidade.recuperar(dao, itemPedido.getProdutoCodigo(),
					itemPedido.getUnidade(), itemPedido.getQuantidadeUnidade());
			ProdutoFilial produtoFilial = ProdutoFilial.recuperarUnico(dao,
					this.getGerentePedido().getPedido().getFilialCodigo(), itemPedido.getProdutoCodigo());
			((ItemPedido) itemPedido).setProduto(produto);
			((ItemPedido) itemPedido).setProdutoUnidade(produtoUnidade);
			((ItemPedido) itemPedido).setProdutoFilial(produtoFilial);

			if (isFlagPrecoRevenda()) {
				preencherPrecoRevenda((ItemPedido) itemPedido);
			}
			// this.getGerentePedidogravarCustoPadraoItem((IItemPedidoWeb)
			// itemPedido);

			valorTotalItens += itemPedido.getPrecoVenda() * itemPedido.getQuantidade();

			this.getGerentePedido().getItensPedido().add(itemPedido);

			if (kitAux != null && kitAux.getCodigo() != itemPedido.getKitCodigo()) {
				adicionarKitPedido(kitAux, quantidadeKit);
				kitAux = null;
			}

			if (itemPedido.getKitCodigo() > 0) {

				if (kitAux == null) {
					kitAux = Kit.recuperarKitComItens(dao, itemPedido.getKitCodigo());

					itemKitMaiorQuantidade = kitAux.getItemKits().get(0);
					for (IItemKit ikit : kitAux.getItemKits()) {
						if (ikit.getQuantidade() > itemKitMaiorQuantidade.getQuantidade())
							itemKitMaiorQuantidade = ikit;
					}
				}

				if (itemKitMaiorQuantidade.getProdutoCodigo() == itemPedido.getProdutoCodigo()) {
					quantidadeKit = itemPedido.getQuantidade() / itemKitMaiorQuantidade.getQuantidade();
				}
			}
		}
		if (kitAux != null) {
			adicionarKitPedido(kitAux, quantidadeKit);
		}

		/*
		 * Preenche demais propriedades
		 */
		this.setFilial(Filial.recuperarUnico(dao, this.getGerentePedido().getPedido().getFilialCodigo()));

		this.getGerentePedido().setFilial(getFilial());

		/*
		 * Preenche propriedades de classe gerentepedido
		 */
		this.getGerentePedido().setNaturezaOperacao(
				NaturezaOperacao.recuperarUnico(dao, this.getGerentePedido().getPedido().getNaturezaOperacaoCodigo()));

		this.getGerentePedido()
				.setCliente(Cliente.recuperarUnico(dao, this.getGerentePedido().getPedido().getPessoaCodigo()));
		this.getGerentePedido()
				.setVendedor(Vendedor.recuperarUnico(dao, this.getGerentePedido().getPedido().getVendedorCodigo()));
		this.getGerentePedido().setTabelaPreco(
				TabelaPreco.recuperarUnico(dao, this.getGerentePedido().getPedido().getTabelaPrecoCodigo()));
		this.getGerentePedido().setFormaPagamento(
				FormaPagamento.recuperarUnico(dao, this.getGerentePedido().getPedido().getFormaPagamentoCodigo()));
		this.getGerentePedido().setCondicaoPagamento(CondicaoPagamento.recuperarUnico(dao,
				this.getGerentePedido().getPedido().getCondicaoPagamentoCodigo()));
		this.getGerentePedido().setOpcaoEspecial(
				OpcaoEspecial.recuperarUnico(dao, this.getGerentePedido().getPedido().getOpcaoEspecialCodigo()));
		this.getGerentePedido()
				.setOpcaoEspecialFilial(OpcaoEspecialFilial.recuperarUnico(dao,
						this.getGerentePedido().getPedido().getOpcaoEspecialCodigo(),
						this.getGerentePedido().getPedido().getFilialCodigo()));
		// Para impressão, não será setado o vendedorUsuario
		// this.getGerentePedido().setVendedorUsuario(Vendedor.recuperarUnico(dao,
		// this.getGerentePedido().getPedido().getColaboradorCodigo()));

		this.setTransportadoraSelecionada(
				Transportadora.recuperarUnico(dao, this.getGerentePedido().getPedido().getTransportadoraCodigo()));

		int localRetiraCodigo = ((Pedido) this.getGerentePedido().getPedido()).getLocalRetiraCodigo();

		if (localRetiraCodigo > 0) {
			this.setLocalRetiraSelecionada(LocalRetira.recuperar(dao, localRetiraCodigo));
		}

		preencheParcelasCondicaoPagamento();

		if (enviarEmailConfirmacao && parametros.getParametro1().getFlagPermiteEnvioEmail() == 1) {

			try {
				enviarEmailConfirmacaoPedido(pedido, itensAuxiliar, getGerentePedido());
			} catch (Exception e) {
				throw new EnvioEmailExcecao(e);
			}
		}

		return getUrlViewResumo();
	}

	private void preencherPrecoRevenda(ItemPedido itemPedido) {
		TabelaPreco tabPreco = TabelaPreco.recuperarUnico(dao, parametros.getParametroWeb().getTabelaPrecoRevenda());
		Precificacao precificacao = new Precificacao(
				FabricaPrecificacao.getInstancia(dao, gerenteLogin.getFilialLogada(), parametros,
						this.gerenteAutorizacaoMB.getGerenteAutorizacao()),
				gerenteAutorizacaoMB.getGerenteAutorizacao(),
				parametros.getParametroVenda().getCasasDecimaisPrecoVenda(), parametros);

		precificacao.alterarNegociacao(this.getPropriedade(), gerentePedido.getCliente(), tabPreco,
				gerentePedido.getCondicaoPagamento(), gerentePedido.getFormaPagamento(), gerentePedido.getVendedor(),
				gerentePedido.getOpcaoEspecialFilial(), 0, 0, gerentePedido.getNaturezaOperacao());

		PrecoVenda precoVenda = precificacao.obterPrecoVendaSugerido(itemPedido.getProdutoCodigo(),
				itemPedido.getProdutoUnidade(), null, true);

		itemPedido.setPrecoRevenda(precoVenda.getPrecoSugerido());
	}

	public void adicionarKitPedido(Ikit kitAux, double quantidade) {

		KitPedido kitPedidoAux = new KitPedido(kitAux);
		kitPedidoAux.setQuantidade(quantidade);
		kitPedidoAux.setPreco(kitAux.getValor() * quantidade);

		this.getGerentePedido().getKitsPedido().add(kitPedidoAux);
	}

	/**
	 * Incresmenta a quantidade de impressões do pedido. Ativado quando o botão de
	 * impressão é utilizado.
	 * 
	 * @param pedido
	 */
	public void incrementarQuantidadeImpressao(Pedido pedido) {
		try {
			dao.beginTransaction();
			pedido.setQuantidadeImpressao(pedido.getQuantidadeImpressao() + 1);
			dao.update(pedido);
			dao.commitTransaction();
		} catch (ClassNotFoundException e) {
			dao.rollBackTransaction();
			e.printStackTrace();
		} catch (SQLException e) {
			dao.rollBackTransaction();
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<Pedido> getHistoricoPedidos() {
		return historicoPedidos;
	}

	/**
	 * 
	 * @param historicoPedidos
	 */
	public void setHistoricoPedidos(List<Pedido> historicoPedidos) {

		prepararVariaveis();

		this.historicoPedidos = historicoPedidos;

		if (ListUtil.isValida(this.historicoPedidos)) {
			Collections.reverse(this.historicoPedidos);
			selecionarPedido(historicoPedidos.get(0));
			preencherNomesCliente();
		}
	}

	private void preencherNomesCliente() {
		this.razaoPessoas = Pessoa.recuperarNome(dao, getCodigosClienteHistoricoPedidos(),
				parametroWebMB.getParametroWeb().isFlagExibeFantasiaCliente());
	}

	private List<Integer> getCodigosClienteHistoricoPedidos() {
		List<Integer> codigo = new ArrayList<Integer>();
		for (Pedido pedido : historicoPedidos) {

			codigo.add(pedido.getPessoaCodigo());
		}

		return codigo;
	}

	public List<LogPedido> getLogPedidoSelecionado() {
		return logPedidoSelecionado;
	}

	public void setLogPedidoSelecionado(List<LogPedido> logPedidoSelecionado) {
		this.logPedidoSelecionado = logPedidoSelecionado;
	}

	public List<ItemPedido> getItensPedidoSelecionado() {
		return itensPedidoSelecionado;
	}

	public void setItensPedidoSelecionado(List<ItemPedido> itensPedidoSelecionado) {
		this.itensPedidoSelecionado = itensPedidoSelecionado;
	}

	public Date getDataMin() {
		return dataMin;
	}

	public void setDataMin(Date dataMin) {
		this.dataMin = dataMin;
	}

	public Date getDataMax() {
		return dataMax;
	}

	public void setDataMax(Date dataMax) {
		this.dataMax = dataMax;
	}

	public String[] getStatusPedidoCodigoSelecionados() {
		return statusPedidoCodigoSelecionados;
	}

	public void setStatusPedidoCodigoSelecionados(String[] statusPedidoCodigoSelecionados) {

		this.statusPedidoCodigoSelecionados = statusPedidoCodigoSelecionados;
	}

	public List<String> getLogStatusString() {
		return logStatusString;
	}

	public void setLogStatusString(List<String> logStatusString) {
		this.logStatusString = logStatusString;
	}

	public List<Produto> getProdutosItensPedidoSelecionado() {
		return produtosItensPedidoSelecionado;
	}

	public void setProdutosItensPedidoSelecionado(List<Produto> produtosItensPedidoSelecionado) {
		this.produtosItensPedidoSelecionado = produtosItensPedidoSelecionado;
	}

	public List<StatusPedido> getStatusHistoricoPedidos() {
		return statusHistoricoPedidos;
	}

	public void setStatusHistoricoPedidos(List<StatusPedido> statusHistoricoPedidos) {
		this.statusHistoricoPedidos = statusHistoricoPedidos;
	}

	public List<StatusPedido> getLogStatus() {
		return logStatus;
	}

	public void setLogStatus(List<StatusPedido> logStatus) {
		this.logStatus = logStatus;
	}

	public String getClienteNome(Cliente cliente) {

		if (cliente != null) {
			return cliente.getRazao(parametroWebMB.getParametroWeb());
		}

		return "";
	}

	public Cliente getClientePedidoSelecionado() {
		if (clientePedidoSelecionado == null && getGerentePedido() != null) {
			return (Cliente) getGerentePedido().getCliente();
		}
		return clientePedidoSelecionado;
	}

	public void setClientePedidoSelecionado(Cliente clientePedidoSelecionado) {
		this.clientePedidoSelecionado = clientePedidoSelecionado;
	}

	/**
	 * 
	 * @param clienteCodigo
	 * @return
	 */
	public String getRazaoCliente(int clienteCodigo) {

		String key = Integer.toString(clienteCodigo);

		if (razaoPessoas != null && razaoPessoas.containsKey(key)) {

			String razao = key + "-";

			razao += razaoPessoas.get(key) != null ? razaoPessoas.get(key).toString() : "";

			return StringUtil.subString(razao, 35);
		}

		return null;
	}

	public Cliente getFiltroCliente() {
		return filtroCliente;
	}

	public void setFiltroCliente(Cliente filtroCliente) {
		this.filtroCliente = filtroCliente;
	}

	public long getQuantidadePedidosUsuario() {
		return ListUtil.isValida(getHistoricoPedidos()) ? getHistoricoPedidos().size() : 0;
	}

	@Override
	public String getNomePrograma() {
		return GerentePedidoMB.NOME_PROGRAMA;
	}

	@Override
	public String getUrlView() {
		return getUrlViewPattern();
	}

	@Override
	protected String getUrlViewPage() {
		return "/pages/meusPedidos.xhtml";
	}

	@Override
	protected String getUrlViewPattern() {
		return "/meusPedidos/";
	}

	public String getUrlViewResumo() {
		return getUrlViewIDResumo();
	}

	private String getUrlViewIDResumo() {
		return "/resumoPedido/";
	}

	private String getUrlViewPageResumo() {
		return "/pedido/resumoPedido.xhtml";

	}

	@Override
	public boolean necessarioLogin() {
		return true;
	}

	public GerentePedido getGerentePedido() {
		if (this.gerentePedido == null) {
			this.gerentePedido = new GerentePedido(IDSistema.SpaceWeb, gerenteLogin, null, propriedade, this.parametros,
					null, dao, new FabricaGerentePedido(), null);

		}
		return gerentePedido;
	}

	public void setGerentePedido(GerentePedido gerentePedido) {
		this.gerentePedido = gerentePedido;
	}

	/**
	 * Preenche parcelas para impressão de pedido
	 */
	public void preencheParcelasCondicaoPagamento() {
		IPedidoWeb pedido = this.getGerentePedido().getPedido();

		int numeroParcelas = this.getGerentePedido().getCondicaoPagamento().getNumeroParcelas();

		parcelasCondicaoPagamento = new ArrayList<>();

		GregorianCalendar vencimento = new GregorianCalendar();
		vencimento.setTime(pedido.getDataEmissao());

		double valorParcela = pedido.getValor();

		vencimento.add(Calendar.DAY_OF_MONTH, this.getGerentePedido().getCondicaoPagamento().getDiasEntrada());

		/* ENTRADA Não Está Sendo Usada */
		/*
		 * if(this.getGerentePedido().getCondicaoPagamento(). getPercentualEntrada () >
		 * 0){
		 * 
		 * double entrada = pedido.getValor(); entrada *=
		 * (condicaoPagamentoSelecionada.getPercentualEntrada() / 100);
		 * parcelasCondicaoPagamento.add("Entrada", entrada, vencimento); valorParcela
		 * -= entrada; numeroParcelas--; }
		 */

		valorParcela /= numeroParcelas;

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
	 * Classe para impressão de parcelas
	 * 
	 * @author Sandro
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

	public Transportadora getTransportadoraSelecionada() {
		return transportadoraSelecionada;
	}

	public void setTransportadoraSelecionada(Transportadora transportadoraSelecionada) {
		IPedidoWeb pedido = this.getGerentePedido().getPedido();
		this.transportadoraSelecionada = transportadoraSelecionada;
		if (this.transportadoraSelecionada != null)
			pedido.setTransportadoraCodigo(transportadoraSelecionada.getPessoa().getCodigo());
	}

	public LocalRetira getLocalRetiraSelecionada() {
		return localRetiraSelecionada;
	}

	public void setLocalRetiraSelecionada(LocalRetira localRetiraSelecionada) {
		this.localRetiraSelecionada = localRetiraSelecionada;
	}

	/**
	 * 
	 * @return
	 */
	public Enderecos getEnderecoEntregaSelecionado() {

		Enderecos endereco = null;

		if (this.getGerentePedido().getCliente() != null
				&& this.getGerentePedido().getPedido().getEnderecoEntregaCodigo() != 0) {
			endereco = Enderecos.localizarEndereco(this.getGerentePedido().getEnderecosCliente(),
					this.getGerentePedido().getPedido().getEnderecoEntregaCodigo());
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

	public Filial getFilial() {
		return filial;
	}

	public void setFilial(Filial filial) {
		this.filial = filial;
	}

	@Override
	public boolean permiteAcessoDeCliente() {
		return true;
	}

	public boolean verificarPermissaoEditarPedido() {
		return (this.gerentePermissao.verificarPermissao("FRMCONSPEDIDO", 5));
	}

	/**
	 * Utilizado para imprimir o valor de desconto na impressão do pedido
	 * 
	 * @return
	 */
	public double getValorDescontoPedido() {

		return getValorDescontoPedidoRateadoItens();
	}

	/**
	 * Utilizado para imprimir o percentual de desconto na impressão do pedido
	 * 
	 * @return
	 */
	public double getPercentualDescontoPedido() {

		double valorBrutoPedido = getValorTotalItem();

		double percentualDesconto = Conversao.arredondar(this.getValorDescontoPedido() / valorBrutoPedido * 100, 2);

		return percentualDesconto;
	}

	/**
	 * 
	 * @return
	 */
	public double getValorTotalItem() {

		double valorTotal = 0.0;

		if (this.getGerentePedido() != null && this.getGerentePedido().getPedido() != null
				&& this.getGerentePedido().getItensPedido() != null) {

			for (IItemPedido ipd : this.getGerentePedido().getItensPedido()) {
				valorTotal += Conversao.arredondar(ipd.getQuantidade() * ipd.getPrecoVenda(), 2);

			}

		}
		return valorTotal;
	}

	public double getValorDescontoPedidoRateadoItens() {

		double valorDesconto = 0D;

		if (this.getGerentePedido() != null && this.getGerentePedido().getPedido() != null
				&& this.getGerentePedido().getItensPedido() != null) {

			for (IItemPedido ipd : this.getGerentePedido().getItensPedido()) {

				valorDesconto += Conversao.arredondar(ipd.getDescontoPedidoValor(), 2);
			}
		}

		return valorDesconto;
	}

	public boolean getExibirTabelaPrecos() {
		return exibirTabelaPrecos;
	}

	public void setExibirTabelaPrecos(boolean exibirTabelaPrecos) {
		this.exibirTabelaPrecos = exibirTabelaPrecos;
	}

	public boolean getExibirColaboradores() {
		return exibirColaboradores;
	}

	public void setExibirColaboradores(boolean exibirColaboradores) {
		this.exibirColaboradores = exibirColaboradores;
	}

	public String getNotaRodape() {
		return notaRodape;
	}

	public void setNotaRodape(String notaRodape) {
		this.notaRodape = notaRodape;
	}

	public boolean isExibeFiltroSituacao() {
		return ListUtil.isValida(logStatus) && gerenteLogin.isPerfilColaborador();

	}

	public Integer getModeloImpressaoKit() {
		if (modeloImpressaoKit == null)
			return 1; // default impressão kit

		return modeloImpressaoKit;
	}

	public void setModeloImpressaoKit(Integer modeloImpressaoKit) {
		this.modeloImpressaoKit = modeloImpressaoKit;
	}

	public boolean getePedidoHistorico() {
		return ePedidoHistorico;
	}

	public void setePedidoHistorico(boolean ePedidoHistorico) {
		this.ePedidoHistorico = ePedidoHistorico;
	}

	public boolean isPedidoConfirmado() {

		int statusPedido = getGerentePedido().getPedido().getStatusPedidoCodigo();

		return statusPedido != StatusPedido.STATUS_AGUARDANDO_PAGAMENTO_WEB
				&& statusPedido != StatusPedido.STATUS_CANCELADO
				&& statusPedido != StatusPedido.STATUS_CANCELADO_INCLUSAO
				&& statusPedido != StatusPedido.STATUS_BLOQUEADO && statusPedido != StatusPedido.STATUS_EM_INCLUSAO
				&& statusPedido != StatusPedido.STATUS_EM_ALTERACAO;
	}

	public boolean isPedidoComPagamentoProcessamentoAutomatico() {
		return true;
	}

	public boolean isFlagPrecoRevenda() {
		return flagPrecoRevenda;
	}

	public void setFlagPrecoRevenda(boolean precoRevenda) {
		this.flagPrecoRevenda = precoRevenda;
		setEstiloDescricao(precoRevenda ? "tabIColDescRevenda displayInlineBlock" : "tabIColDesc displayInlineBlock");
	}

	public String getEstiloDescricao() {
		return estiloDescricao;
	}

	public void setEstiloDescricao(String estiloDescricao) {
		this.estiloDescricao = estiloDescricao;
	}

	public boolean isRedirecionaAutomaticamenteHome() {
		return !getePedidoHistorico() &&  Conversao.nvlInteger(getIntervaloRedirecionamentoAutomaticamenteHome(), 0).intValue() > 0;
	}

	public Integer getIntervaloRedirecionamentoAutomaticamenteHome() {
		return parametroWebMB.getParametroWeb().getIntervaloRedirecionamentoAutomaticoHome();
	}
}