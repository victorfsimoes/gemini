package br.com.space.spacewebII.controle.entrega;

import java.util.List;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IItemPedido;
import br.com.space.api.transportadora.CalculoFrete;
import br.com.space.api.transportadora.IRetornoCalculoFrete;
import br.com.space.api.transportadora.TipoEmbalagem;
import br.com.space.api.transportadora.Transportadora;
import br.com.space.api.transportadora.UnidadeMediaDimensoes;
import br.com.space.spacewebII.controle.aplicacao.GerenteEmail;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.endereco.IEndereco;
import br.com.space.spacewebII.modelo.dominio.endereco.Regiao;
import br.com.space.spacewebII.modelo.dominio.endereco.Uf;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoUnidade;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.interfaces.IPedidoWeb;
import br.com.space.spacewebII.modelo.dominio.logistica.Frete;
import br.com.space.spacewebII.modelo.dominio.logistica.FreteFaixa;
import br.com.space.spacewebII.modelo.dominio.logistica.FreteFaixaPeso;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.venda.TipoEntrega;
import br.com.space.spacewebII.modelo.dominio.venda.TipoVeiculo;

public class GerenteFrete {

	private TipoEntrega tipoEntrega;

	private IEndereco enderecoOrigem;

	private IEndereco enderecoDestino;

	private Regiao regiaoOrigem;

	private Regiao regiaoDestino;

	private Uf ufDestino;

	private double valorTotalPedido;
	// private double valorMercadorias;
	private double valorDeclarado;
	private boolean flagAvisoRecebimento = false;
	private boolean flagMaoPropria = false;

	private TipoEmbalagem tipoEmbalagem;
	private double comprimento;
	private double altura;
	private double largura;
	private double diametro;
	private double cubagem;
	private double peso;

	/*
	 * Valores calculados
	 */
	private double valorFrete;
	private boolean flagIsencao;
	private double valorIsencao;
	private int prazoEntrega;
	private double pesoCubico;

	private GenericoDAO dao;
	private Parametros parametros = null;
	private int filialCodigo;

	/**
	 * 
	 */
	public GerenteFrete(int filialCodigo, GenericoDAO dao, Parametros parametros) {
		this.filialCodigo = filialCodigo;
		this.parametros = parametros;
		this.dao = dao;
	}

	/**
	 * 
	 * @param enderecoOrigem
	 */
	public void setEnderecoOrigem(IEndereco enderecoOrigem) {
		this.enderecoOrigem = enderecoOrigem;

		/*
		 * Verifica se a regiao de origem é a mesma do endereco e evita de
		 * buscar novamente caso positivo.
		 */
		if (this.regiaoOrigem == null
				|| !this.regiaoOrigem.getUfSigla().equals(
						enderecoOrigem.getUfsigla())) {
			this.regiaoOrigem = obterRegiao(enderecoOrigem.getCidadeCodigo(),
					enderecoOrigem.getUfsigla(),
					enderecoOrigem.getBairroCodigo());
		}
	}

	/**
	 * 
	 * @param enderecoDestino
	 * @param regiaoDestino
	 */
	public void setEnderecoDestino(IEndereco enderecoDestino,
			Regiao regiaoDestino) {
		this.enderecoDestino = enderecoDestino;
		this.regiaoDestino = regiaoDestino;

		/*
		 * Localiza uma regiao de acordo com o endereco, caso não encontre
		 * nenhuma mantem a regiao passada como parametro. No Guardian existem a
		 * maioria dos clientes não cadastra Cidade na região.
		 */
		Regiao regAux = obterRegiao(enderecoDestino.getCidadeCodigo(),
				enderecoDestino.getUfsigla(), enderecoDestino.getBairroCodigo());
		if (regAux != null) {
			this.regiaoDestino = regAux;
		}

		ufDestino = Uf.recuperarUnico(dao, enderecoDestino.getUfsigla());
	}

	/**
	 * 
	 * @param tipoVeiculo
	 * @throws Exception
	 */
	private void validarDados(TipoVeiculo tipoVeiculo) throws Exception {
		if (this.enderecoOrigem == null
				|| this.enderecoOrigem.getCidadeCodigo() == 0
				|| !StringUtil.isValida(this.enderecoOrigem.getUfsigla())
				|| !StringUtil.isValida(this.enderecoOrigem.getCep())) {
			throw new SpaceExcecao("Endereço de Origem Inválido !");
		}

		if (this.enderecoDestino == null
				|| this.enderecoDestino.getCidadeCodigo() == 0
				|| !StringUtil.isValida(this.enderecoDestino.getUfsigla())
				|| !StringUtil.isValida(this.enderecoDestino.getCep())) {
			throw new SpaceExcecao("Endereço de Destino Inválido !");
		}

		/*
		 * Verifica se possui alguma região cadastrada para o endereco de
		 * entrega e se não entrega.
		 */
		if (this.regiaoDestino != null
				&& this.regiaoDestino.getFlagNaoEntrega() == 1) {
			throw new SpaceExcecao("Entrega indisponível para a Região !");
		}

		/*
		 * Verifica se não entrega na UF somente não encontrou uma região para o
		 * endereço.
		 */
		if (ufDestino != null && ufDestino.getFlagNaoEntrega() == 1
				&& this.regiaoDestino == null) {
			throw new SpaceExcecao("Entrega indisponível para a UF !");
		}

		if (tipoEntrega == null) {
			throw new SpaceExcecao("Nenhum Tipo de Entrega informado !");
		}

		if (tipoEmbalagem == null) {
			throw new SpaceExcecao("Tipo da Embalagem Inválido !");
		}

		if (this.peso <= 0) {
			throw new SpaceExcecao("Peso Inválido !");
		}

		validarTipoVeiculo(tipoVeiculo);
	}

	private void validarTipoVeiculo(TipoVeiculo tipoVeiculo)
			throws SpaceExcecao {

		if (tipoVeiculo != null
				&& (tipoVeiculo.isLancaErroCargaMaximaInvalida(altura,
						comprimento, largura, peso) || tipoVeiculo
						.isLancaErroCargaMinimaInvalida(altura, comprimento,
								largura, peso))) {

			throw new SpaceExcecao("O veiculo, para o tipo de entrega "
					+ tipoEntrega.getCodigo()
					+ " não suporta o envio deste pedido!");
		}
	}

	/**
	 * Limpa os valores calculados por alguma chamada anterior. Evitar conflito
	 * de valores.
	 */
	private void limparValoresCalculados() {
		this.flagIsencao = false;
		this.valorIsencao = 0;
		this.prazoEntrega = 0;
		this.valorFrete = 0;
		this.pesoCubico = 0;
	}

	public double calcularValorFrete(TipoEntrega tipoEntrega,
			TipoEmbalagem tipoEmbalagem, IPedidoWeb pedido,
			List<IItemPedido> itensPedido) throws Exception {

		return calcularValorFrete(tipoEntrega, tipoEmbalagem,
				pedido.getMetrosCubicos(), pedido.getPesoBruto(),
				pedido.getValor(), itensPedido);
	}

	public double calcularValorFrete(TipoEntrega tipoEntrega,
			TipoEmbalagem tipoEmbalagem, double cubagem, double peso,
			double valorTotalPedido, List<IItemPedido> itensPedido)
			throws Exception {

		double valorFreteFinal = 0;
		double prazoEntrega = 0;

		if (regiaoDestino == null) {
			throw new SpaceExcecao(
					"Entrega indisponível para o Endereço de Destino!");
		}

		boolean isFreteTipoEntregaPeso = regiaoDestino
				.isTipoFreteTipoEntregaPeso();

		if (isFreteTipoEntregaPeso && tipoEntrega.isTipoCalculoFretePorVolume()
				&& ListUtil.isValida(itensPedido)) {

			for (IItemPedido itemPedido : itensPedido) {

				IItemPedidoWeb item = (IItemPedidoWeb) itemPedido;

				double quantidadeVendida = item.getQuantidade();

				ProdutoUnidade unidade = (ProdutoUnidade) item
						.getProdutoUnidade();

				double valorFrete = calcularValorFrete(
						tipoEntrega,
						tipoEmbalagem,
						unidade.getComprimento(),
						unidade.getAltura(),
						unidade.getLargura(),
						unidade.getDiametro(),
						0,
						(item.getPesoBrutoProduto() / quantidadeVendida),
						valorTotalPedido,
						calcularValorSeguro(item.calcularPrecoLiquido(),
								tipoEntrega));

				valorFreteFinal += Conversao.arredondar(
						(valorFrete * quantidadeVendida), 2);

				if (this.prazoEntrega > prazoEntrega) {
					prazoEntrega = this.prazoEntrega;
				}
			}

		} else {
			valorFreteFinal = calcularValorFrete(tipoEntrega, tipoEmbalagem, 0,
					0, 0, 0, cubagem, peso, valorTotalPedido,
					calcularValorSeguro(valorTotalPedido, tipoEntrega));
		}

		return Conversao.arredondar(valorFreteFinal, 2);
	}

	/**
	 * 
	 * @param tipoEntrega
	 * @param tipoEmbalagem
	 * @param comprimento
	 * @param altura
	 * @param largura
	 * @param diametro
	 * @param cubagem
	 *            Cubagem já calculada, será descartado caso alguma dimensão
	 *            (comprimento,altura,largura,diametro) for passado.
	 * @param peso
	 *            Peso bruto da mercadoria incluindo o peso da embalagem.
	 * @param valorTotalPedido
	 *            Valor total do pedido, usado para saber se atingiu alguma
	 *            regra de isenção.
	 * @param valorMercadorias
	 *            Valor das mercadorias Entrega, pode ser diferente do valor
	 *            total do pedido quando o frete é calculado por
	 *            volume/emgalagem
	 * @param valorDeclarado
	 * @param avisoRecebimento
	 * @return
	 * @throws Exception
	 */
	private double calcularValorFrete(TipoEntrega tipoEntrega,
			TipoEmbalagem tipoEmbalagem, double comprimento, double altura,
			double largura, double diametro, double cubagem, double peso,
			double valorTotalPedido, double valorDeclarado) throws Exception {

		this.tipoEntrega = tipoEntrega;
		this.tipoEmbalagem = tipoEmbalagem;
		this.comprimento = comprimento;
		this.altura = altura;
		this.largura = largura;
		this.diametro = diametro;
		this.cubagem = cubagem;
		this.peso = peso;
		this.valorTotalPedido = valorTotalPedido;
		// this.valorMercadorias = valorMercadorias;
		this.valorDeclarado = valorDeclarado;
		this.flagAvisoRecebimento = tipoEntrega.isAvisoRecebimento();
		this.flagMaoPropria = tipoEntrega.isMaoPropria();

		TipoVeiculo tipoVeiculo = TipoVeiculo.recuperarTipoVeiculo(dao,
				tipoEntrega.getTipoVeiculoCodigo());

		this.limparValoresCalculados();

		this.validarDados(tipoVeiculo);

		this.verificarAssumirDimensoesMinimas(tipoVeiculo);

		if (this.regiaoDestino != null) {

			this.flagIsencao = this.regiaoDestino
					.getFlagValorMinimoIsencaoFrete() == 1;

			this.valorIsencao = this.regiaoDestino.getValorMinimoIsencaoFrete();

			if ("V".equals(this.regiaoDestino.getTipoFrete())) {
				/*
				 * Valor fixo por região.
				 */
				this.valorFrete = this.regiaoDestino.getValorFrete();

			} else if ("P".equals(this.regiaoDestino.getTipoFrete())) {
				/*
				 * Percentual sobre o valor do pedido
				 */
				this.valorFrete = Conversao.arredondar(this.valorTotalPedido
						* this.regiaoDestino.getPercentualFrete() / 100, 2);

			} else if ("T".equals(this.regiaoDestino.getTipoFrete())) {
				/*
				 * Verifica se o frete é obtido através de um WebService ou
				 * através de uma tabela Frete por Faixa de pesos.
				 */
				if (this.tipoEntrega.getFlagIntegraWebService() == 1) {
					this.calcularFreteWebService();
				} else {
					this.calcularFreteTabelaFrete();
				}

				if (valorFrete > 0
						&& tipoEntrega.getDescontoFretePercentual() > 0) {
					valorFrete -= (valorFrete * (tipoEntrega
							.getDescontoFretePercentual() / 100D));
				}
			}
		}

		/*
		 * Verifica se não encontrou alguma regra de frete para o destino;
		 */
		if (this.valorFrete < 0) {
			this.valorFrete = 0;
			throw new SpaceExcecao(
					"Entrega indisponível para o Endereço de Destino!");
		}

		/*
		 * Verifica se possue alguma isencao de frete e se atingiu o valor
		 * minimo.
		 */
		if (this.flagIsencao && this.valorTotalPedido >= this.valorIsencao) {
			this.valorFrete = 0;
		}

		return this.valorFrete;
	}

	private void verificarAssumirDimensoesMinimas(TipoVeiculo tipoVeiculo) {

		if (tipoVeiculo != null && tipoVeiculo.isAssumeCargaMinimaInvalida()) {

			if (altura < tipoVeiculo.getAlturaMinima()) {
				altura = tipoVeiculo.getAlturaMinima();
			}

			if (largura < tipoVeiculo.getLarguraMinima()) {
				largura = tipoVeiculo.getLarguraMinima();
			}

			if (comprimento < tipoVeiculo.getComprimentoMinima()) {
				comprimento = tipoVeiculo.getComprimentoMinima();
			}

			if (peso < tipoVeiculo.getCargaMinima()) {
				peso = tipoVeiculo.getCargaMinima();
			}
		}
	}

	/**
	 * 
	 * @param cidadeCodigo
	 * @param ufSigla
	 * @param bairroCodigo
	 * @return
	 */
	private Regiao obterRegiao(int cidadeCodigo, String ufSigla,
			int bairroCodigo) {

		Regiao regiao = null;
		String sql = "select * from regiao "
				+ "inner join regcidbai on reg_codigo = rbc_regcodigo "
				+ " and reg_ufsigla = '" + ufSigla + "'"
				+ " and ((rbc_cidcodigo = " + cidadeCodigo
				+ " and rbc_todbairro = 1)" + " or " + "(rbc_cidcodigo = "
				+ cidadeCodigo + " and rbc_todbairro = 0 and rbc_baicodigo="
				+ bairroCodigo + ")) order by rbc_baicodigo desc";

		List<Regiao> regioes = dao.list(Regiao.class, sql);
		if (regioes != null && regioes.size() > 0) {
			regiao = regioes.get(0);
			regioes.clear();
		}

		return regiao;
	}

	/**
	 * 
	 */
	private void calcularFreteWebService() {

		try {
			List<IRetornoCalculoFrete> calculoFretes = CalculoFrete
					.calcularFrete(Transportadora.CORREIOS,
							tipoEntrega.getUsuarioWebService(),
							tipoEntrega.getSenhaWebService(),
							tipoEntrega.getCodigoServicoWebService(),
							enderecoOrigem.getCep(), enderecoDestino.getCep(),
							tipoEmbalagem, peso, comprimento, altura, largura,
							diametro, valorDeclarado, flagAvisoRecebimento,
							flagMaoPropria, UnidadeMediaDimensoes.M);

			IRetornoCalculoFrete retornoFrete = calculoFretes.get(0);

			if (retornoFrete.isSucesso()) {
				this.prazoEntrega = retornoFrete.getPrazoEntregaDias();
				this.valorFrete = retornoFrete.getValorFrete();
			} else {
				throw new SpaceExcecao(retornoFrete.getMensagemErro());
			}
		} catch (Exception e) {

			GerenteEmail.enviarEmailGenerico("renatoalves.infor@gmail.com",
					dao, parametros, "Problema na integração",
					"A integração com os correios falhou",
					"A mensagem obtida foi " + e.getMessage(), null,
					"Falha no calculo de frete");

			/*
			 * Se ocorrer um erro durante a conexao calcular o frete pela tabela
			 * de fretes.
			 */
			this.calcularFreteTabelaFrete();
		}
	}

	/**
	 * 
	 * @return
	 */
	private Frete obterRegraFrete() {
		Frete freteCandidato = null;

		/*
		 * Recupera todas as possíveis regras de frete candidatas a atender aos
		 * parametros de entrega. Leva em consideração a regra mais especifica
		 * para a mais generica.
		 * 
		 * Filial, UF Origem, UF Destino, Origem Capital/Interior, Destino
		 * Capital/Interior/Local, Tipo de Entrega, Regiao
		 */
		String whereDestLocal = "frt_destlocal in ("
				+ (isDestinoCapital() ? "1" : "0")
				+ (isDestinoLocal() ? ", 2" : "") + ")";

		String whereRegiaoDest = "(frt_regcodigo = 0 or frt_regcodigo = "
				+ (this.regiaoDestino == null ? 0 : this.regiaoDestino
						.getCodigo()) + ")";

		String whereTipoVeiculo = "(frt_tvecodigo = 0 or frt_tvecodigo = "
				+ this.tipoEntrega.getTipoVeiculoCodigo() + ")";

		String whereTipoEntrega = "(frt_tpecodigo is null or frt_tpecodigo = '' or frt_tpecodigo = '"
				+ this.tipoEntrega.getCodigo() + "')";

		String sql = "select * from frete where"
				+ " frt_uforigem = '"
				+ this.enderecoOrigem.getUfsigla()
				+ "'"
				+ " and frt_ufdestino = '"
				+ this.enderecoDestino.getUfsigla()
				+ "'"
				+ " and frt_origlocal = "
				+ (isOrigemCapital() ? "1" : "0")
				+ " and "
				+ whereDestLocal
				+ " and "
				+ whereTipoVeiculo
				+ " and "
				+ whereRegiaoDest
				+ " and "
				+ whereTipoEntrega
				+ " and frt_ativo = 1 and frt_filcodigo = "
				+ this.filialCodigo
				+ " order by frt_destlocal, frt_tpecodigo, frt_regcodigo, frt_tvecodigo desc";

		List<Frete> fretes = dao.list(Frete.class, sql);
		if (fretes != null && fretes.size() > 0) {
			freteCandidato = fretes.get(0);
			fretes.clear();
		}

		return freteCandidato;
	}

	/**
	 * 
	 */
	private void obterValorFretePeso(Frete regraFrete) {
		this.calcularPesoCubico();

		double pesoAux = Math.max(this.peso, this.pesoCubico);

		/*
		 * Recupera a faixa de peso na faixa do peso
		 */
		FreteFaixaPeso ffp = FreteFaixaPeso.recuperarPeso(dao,
				regraFrete.getFreteFaixaCodigo(), pesoAux);

		if (ffp != null) {
			this.valorFrete = ffp.getValorFrete();
		} else {
			/*
			 * Se não encontrou uma faixa de peso, busca o maior peso cadastrado
			 * e aplica o valor do kg adicional.
			 */

			/*
			 * Recupera o FreteFaixa pois precisaemos do valor adicional do
			 * kilo.
			 */
			FreteFaixa freteFaixa = FreteFaixa.recuperarPeso(dao,
					regraFrete.getFreteFaixaCodigo());

			if (freteFaixa != null) {

				FreteFaixaPeso ffpMaior = FreteFaixaPeso.recuperarMaiorPeso(
						dao, regraFrete.getFreteFaixaCodigo());

				if (ffpMaior != null) {

					this.valorFrete = ffpMaior.getValorFrete();
					/*
					 * Calcula a diferenca para o maior peso e aplica o valor do
					 * kg.
					 */
					double difPeso = pesoAux
							- ffpMaior.getFreteFaixaPesoPK().getPesoFinal();
					if (difPeso > 0) {

						/*
						 * Calcula o maior valor inteiro proximo à diferenca de
						 * peso.
						 */
						double difArrdondado = Conversao.arredondar(difPeso, 0);
						if (difPeso > difArrdondado)
							difPeso = difArrdondado + 1;
						else
							difPeso = difArrdondado;

						this.valorFrete = Conversao.arredondar(this.valorFrete
								+ difPeso * freteFaixa.getValorKiloAdicional(),
								2);
					}
				}
			}
		}
	}

	/**
	 * 
	 */
	private void calcularFreteTabelaFrete() {
		Frete regraFrete = this.obterRegraFrete();

		if (regraFrete != null) {
			/*
			 * Verifica se obtem o valor do frete de acordo com o peso ou um
			 * valor fixo.
			 */
			if (regraFrete.getFlagUsaFaixaPeso() == 1
					&& regraFrete.getFreteFaixaCodigo() > 0) {

				this.obterValorFretePeso(regraFrete);

			} else {
				this.valorFrete = regraFrete.getValor();
			}

			/*
			 * Verifica se possui algum tipo de isenção e se atingiu o valor
			 * mínimo.
			 */
			this.flagIsencao = regraFrete.getFlagIsencao() == 1;
			this.valorIsencao = regraFrete.getValorIsencao();
		} else
			this.valorFrete = -1;
	}

	private double calcularValorSeguro(double valorBase, TipoEntrega tipoEntrega) {
		if (tipoEntrega.getPercentualSeguroPrecoLiquido() > 0) {
			return valorBase
					* (tipoEntrega.getPercentualSeguroPrecoLiquido() / 100D);

		}
		return 0;
	}

	/**
	 * 
	 * @return
	 * @throws Exception
	 */
	private void calcularPesoCubico() {

		double cubagemAux = 0;

		if ("C".equals(tipoEmbalagem) && this.altura > 0
				&& this.comprimento > 0 && this.largura > 0) {
			// *** Caixa - Comprimento x Altura x Largura ***
			cubagemAux = this.altura * this.comprimento * this.largura;
		} else if ("R".equals(tipoEmbalagem) && this.diametro > 0
				&& this.comprimento > 0) {
			// *** Rolo/Prisma - PI x r2 x Comprimento
			cubagemAux = 3.1416 * Math.pow(this.diametro / 2, 2)
					* this.comprimento;
		} else
			cubagemAux = this.cubagem;

		this.pesoCubico = cubagemAux * this.tipoEntrega.getFatorPesoCubico();
	}

	/**
	 * Retorna se a Origem faz parte da região metropolitana da capital do
	 * estado.
	 * 
	 * @return
	 */
	private boolean isOrigemCapital() {
		return this.regiaoOrigem != null && this.regiaoOrigem.getLocal() == 1;
	}

	/**
	 * Retorna se o Destino faz parte da região metropolitana da capital do
	 * estado.
	 * 
	 * @return
	 */
	private boolean isDestinoCapital() {
		return this.regiaoDestino != null && this.regiaoDestino.getLocal() == 1;
	}

	/**
	 * Retorna se o destino é a mesma cidade da origem.
	 * 
	 * @return
	 */
	private boolean isDestinoLocal() {
		return this.enderecoOrigem != null
				&& this.enderecoDestino != null
				&& this.enderecoDestino.getCidadeCodigo() == this.enderecoOrigem
						.getCidadeCodigo();
	}

}
