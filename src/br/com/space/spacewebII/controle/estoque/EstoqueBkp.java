package br.com.space.spacewebII.controle.estoque;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.dominiojee.dominio.estoque.LocalProduto;
import br.com.space.api.dominiojee.dominio.estoque.TipoMovimentoEstoque;
import br.com.space.api.negocio.modelo.dominio.IItemKit;
import br.com.space.api.spa.model.dao.GenericDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.LocalFilial;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoFilial;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoLote;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.venda.Kit;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

/**
 * 
 * Classe controle de estoque
 * 
 * @author Desenvolvimento
 * 
 */
public class EstoqueBkp implements Serializable {

	private static final long serialVersionUID = 1L;

	public enum FlagTipoEstoque {
		Geral, Outros, DisponivelInterno, DisponivelExterno, DisponivelVendaInterno, DisponivelVendaGeral
	}

	GenericoDAO dao;

	Parametros parametros;

	Propriedade propriedade;

	/*
	 * Propriedades de estoque
	 */
	private double estoquePendenteConfirmacao;
	private double estoquePendenteConfirmacaoLocal;
	private double estoquePendenteEntrega;
	private double estoquePendenteEntregaLocal;
	private double estoqueReservado;
	private double estoquePendenteEntrada;
	private double estoqueFisico;
	private double estoqueDisponivel;
	private double estoqueOutros;
	private double estoqueMinimo;
	private double estoqueMaximo;
	private List<TipoMovimentoEstoque> tiposMovimentoEstoque;
	private int tipoMovimentoEstoqueCodigo;
	private TipoMovimentoEstoque tipoMovimentoEstoque;
	private TipoMovimentoEstoque tipoMovimentoEstoquePesquisa;

	/*
	 * Quantidade deve já vir multiplicada pelo fator de estoque
	 */
	private double quantidade;

	/*
	 * Propriedades usadas para as consultas e atualizações
	 */
	private int filialCodigo;
	private int produtoCodigo;
	private Produto produto;

	private int localEstoqueCodigo;
	private Integer[] locaisEstoque;

	private String lote;
	private String[] lotes;

	private int flagControlaLote;
	private int flagControlaCertificado;
	private FlagTipoEstoque flagTipoEstoque;

	/*
	 * Strings utilizadas para baixa de estoque
	 */
	private String sqlProdutoFilial;
	private String sqlLocalProduto;
	private String sqlProdutoLote;

	/**
	 * 
	 * @param dao
	 * @param parametros
	 */
	public EstoqueBkp(GenericoDAO dao, Parametros parametros) {
		this.dao = dao;
		this.parametros = parametros;
		this.tiposMovimentoEstoque = dao.list(TipoMovimentoEstoque.class);
		this.tipoMovimentoEstoquePesquisa = new TipoMovimentoEstoque();
	}

	/**
	 * Limpa propriedades relativas a estoque
	 */
	public void limparEstoque() {
		this.estoquePendenteConfirmacao = 0;
		this.estoquePendenteConfirmacaoLocal = 0;
		this.estoquePendenteEntrega = 0;
		this.estoquePendenteEntregaLocal = 0;
		this.estoqueReservado = 0;
		this.estoquePendenteEntrada = 0;
		this.estoqueFisico = 0;
		this.estoqueDisponivel = 0;
		this.estoqueOutros = 0;
		this.estoqueMinimo = 0;
		this.estoqueMaximo = 0;
	}

	/**
	 * Limpa propriedades
	 */
	public void limpar() {
		this.limparEstoque();

		this.filialCodigo = 0;
		this.flagControlaLote = 0;
		this.flagControlaCertificado = 0;
		this.flagTipoEstoque = FlagTipoEstoque.Geral;
		this.locaisEstoque = null;
		this.localEstoqueCodigo = 0;
		this.lote = "";
		this.lotes = null;
		this.produtoCodigo = 0;
		this.produto = null;
		this.tipoMovimentoEstoqueCodigo = 0;
		this.tipoMovimentoEstoque = null;
		this.sqlProdutoFilial = "";
		this.sqlLocalProduto = "";
		this.sqlProdutoLote = "";
	}

	/**
	 * 
	 * Recupera se produto e parâmetro controle lote e validade
	 * 
	 * Retorna:
	 * 
	 * 0 - Controle Lote não habilitado no sistema
	 * 
	 * 1 - Parâmetro marcado, item não controla lote
	 * 
	 * 2 - Controla Lote
	 * 
	 * 3 - Controla Lote e Validade
	 * 
	 * @param produtoCodigo
	 * @param flagProdutoControlaLote
	 * @param flagProdutoControlaValidade
	 * @return
	 */
	private int recuperarFlagControleLoteProduto(int produtoCodigo,
			int flagProdutoControlaLote, int flagProdutoControlaValidade) {

		int result = 0;
		if (parametros.getParametro1().getFlagControlaLote() == 1) {
			result = 1 + flagProdutoControlaLote + flagProdutoControlaValidade;
		}
		return result;

	}

	/**
	 * Verifica se produto controla lote e se produto controla validade
	 * 
	 * @param produtoCodigo
	 * @return
	 */

	private int recuperarFlagControleLoteProduto(int produtoCodigo) {
		int flagProdutoControlaLote = 0;
		int flagProdutoControlaValidade = 0;

		ResultSet resultFlag = null;
		try {

			if (this.produto != null
					&& this.produto.getCodigo() == this.produtoCodigo) {

				flagProdutoControlaLote = Conversao.nvlInteger(
						this.produto.getFlagControlaLote(), 0);

				flagProdutoControlaValidade = Conversao.nvlInteger(
						this.produto.getFlagControlaValidade(), 0);
			} else {

				String sqlFlag = "select pro_contlote, pro_contvalida "
						+ "from produto where pro_codigo = " + produtoCodigo;

				resultFlag = dao.listResultSet(sqlFlag);
				if (resultFlag.first()) {
					flagProdutoControlaLote = Conversao.nvlInteger(
							resultFlag.getInt("pro_contlote"), 0);

					flagProdutoControlaValidade = Conversao.nvlInteger(
							resultFlag.getInt("pro_contvalida"), 0);
				}
			}
		} catch (Exception e) {
		} finally {
			GenericoDAO.closeResultSet(resultFlag);
		}

		return recuperarFlagControleLoteProduto(produtoCodigo,
				flagProdutoControlaLote, flagProdutoControlaValidade);
	}

	/**
	 * Recupera se produto e parâmetro controla lote e certificado
	 * 
	 * Retorna:
	 * 
	 * 0 - Controle Lote não habilitado no sistema
	 * 
	 * 1 - Parâmetro marcado, item não controla lote
	 * 
	 * 2 - Controla Lote
	 * 
	 * 3 - Controla Lote e Certificado
	 * 
	 * @param produtoCodigo
	 * @param flagProdutoControlaLote
	 * @param flagProdutoControlaCertificado
	 * @return
	 */
	private int recuperarFlagControleLoteCertificadoProduto(int produtoCodigo,
			int flagProdutoControlaLote, int flagProdutoControlaCertificado) {
		int result = 0;
		if (parametros.getParametro1().getFlagControlaLote() == 1) {
			result = 1 + flagProdutoControlaLote
					+ flagProdutoControlaCertificado;
		}
		return result;
	}

	/**
	 * Verifica se produto controla lote e se produto controla certificado
	 * 
	 * @param produtoCodigo
	 */
	private int recuperarFlagControleLoteCertificadoProduto(int produtoCodigo) {
		int flagProdutoControlaLote = 0;
		int flagProdutoControlaCertificado = 0;

		ResultSet resultFlag = null;
		try {
			String sqlFlag = "select pro_contlote, pro_usacertclas "
					+ "from produto where pro_codigo = " + produtoCodigo;

			resultFlag = dao.listResultSet(sqlFlag);
			this.estoqueReservado = 0;
			if (resultFlag.first()) {
				flagProdutoControlaLote = Conversao.nvlInteger(
						resultFlag.getInt("pro_contlote"), 0);

				flagProdutoControlaCertificado = Conversao.nvlInteger(
						resultFlag.getInt("pro_usacertclas"), 0);
			}
		} catch (Exception e) {
		} finally {
			GenericoDAO.closeResultSet(resultFlag);
		}

		return recuperarFlagControleLoteCertificadoProduto(produtoCodigo,
				flagProdutoControlaLote, flagProdutoControlaCertificado);
	}

	/**
	 * Recupera estoque reservado de outros (produção)
	 */
	private void recuperarEstoqueReservadoOutros() {
		/*
		 * Se usa produção, pega o estoque reservado para empenho
		 */
		ResultSet resultReserv = null;
		try {
			String vSqlReserv = "select "
					+ " sum((opm_qtdereserv - opm_qtdebaixa) * opm_unpfatest) as estreserv "
					+ " from ordprodempenho inner join ordemproducao "
					+ " on opm_oppnumero = opp_numero and opm_filcodigo = opp_filcodigo"
					+ " and opm_filcodigo = "
					+ this.filialCodigo
					+ " and opm_procodigo = "
					+ this.produtoCodigo
					+ " and opm_qtdereserv > opm_qtdebaixa "
					+ " and opp_status = 'E' and (opp_digito = 0 or opp_digito = 2)";

			resultReserv = dao.listResultSet(vSqlReserv);
			this.estoqueReservado = 0;
			if (resultReserv.first()) {
				this.estoqueReservado = Conversao.nvlDouble(
						resultReserv.getDouble("estreserv"), 0.0);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			GenericoDAO.closeResultSet(resultReserv);
		}
	}

	/**
	 * Monta SQL responsável por recuperar estoque do produto
	 * 
	 * @return
	 */
	private String montarSqlRecuperarEstoque() {
		StringBuilder camposSaldo = new StringBuilder();
		String tabelaSaldo = "";
		StringBuilder condicaoProduto = new StringBuilder();
		String condicaoTipoEstoque = "";
		StringBuilder condicaoFilial = new StringBuilder(
				this.filialCodigo == 0 ? "" : " and pfi_filcodigo = ")
				.append(this.filialCodigo);

		/*
		 * outros (desconsidera Lote ou Locais)
		 */
		if (this.flagTipoEstoque == FlagTipoEstoque.Outros) {
			camposSaldo
					.append(", pfi_estoque as estoutros, sum(pfi_estoque) as estfisico, ")
					.append("0 as estreserv, 0 as estpenconfilce, 0 as estpenentrelce ");

			tabelaSaldo = " ";
			condicaoProduto.append(" and lcf_filcodigo = pfi_filcodigo ");
		} else {
			/*
			 * fisico = considera Lote ou Local, dependendo da configuracao do
			 * produto
			 */
			if (this.flagControlaLote >= 2) {

				camposSaldo.append(", pfi_estoque as estoutros, ")
						.append(" sum(ple_estfisico) as estfisico, ")
						.append(" sum(ple_estreserv) as estreserv, ")
						.append(" sum(ple_estpenconfi) as estpenconfilce, ")
						.append(" sum(ple_estpenentre) as estpenentrelce ")
						.toString();

				tabelaSaldo = " produtolote, ";
				condicaoProduto
						.append(" and pfi_procodigo = ple_procodigo and lcf_filcodigo = ple_filcodigo ")
						.append(" and lcf_lcecodigo = ple_lcecodigo and pfi_filcodigo = ple_filcodigo ");

				if (this.lote != null && this.lote.trim().length() > 0)
					condicaoProduto.append(condicaoProduto)
							.append(" and ple_lote = '")
							.append(new StringBuilder(this.lote).append("' "));
				else if (this.lotes != null && this.lotes.length > 0) {
					StringBuilder lotesValidos = new StringBuilder();
					for (String string : this.lotes) {
						lotesValidos.append(
								lotesValidos.length() > 0 ? ", " : "").append(
								string);
					}

					condicaoProduto.append(condicaoProduto)
							.append(" AND ple_Lote in (")
							.append(lotesValidos.toString()).append(") ");
				}
			}

			else {
				camposSaldo.append(", pfi_estoque as estoutros, ")
						.append(" sum(lpd_estfisico) as estfisico, ")
						.append(" sum(lpd_estreserv) as estreserv, ")
						.append(" sum(lpd_estpenconfi) as estpenconfilce, ")
						.append(" sum(lpd_estpenentre) as estpenentrelce ");

				tabelaSaldo = " localprod, ";
				condicaoProduto
						.append(" and pfi_procodigo = lpd_procodigo and lcf_filcodigo=lpd_filcodigo ")
						.append(" and lcf_lcecodigo = lpd_lcecodigo and pfi_filcodigo=lpd_filcodigo ");
			}
		}

		// Local específico.
		if (this.localEstoqueCodigo > 0)
			condicaoProduto.append(condicaoProduto)
					.append(" and lcf_lcecodigo = ")
					.append(this.localEstoqueCodigo);

		// Lista de Locais permitidos.
		if (this.locaisEstoque != null && this.locaisEstoque.length > 0) {
			StringBuilder locaisValidos = new StringBuilder();
			for (Integer local : this.locaisEstoque) {
				locaisValidos.append(locaisValidos.length() > 0 ? ", " : "")
						.append(local.toString());
			}

			condicaoProduto.append(condicaoProduto)
					.append(" and Lcf_LceCodigo in (")
					.append(locaisValidos.toString()).append(")");
		}

		/*
		 * Filtros para estoque.
		 */

		switch (this.flagTipoEstoque) {

		case DisponivelVendaInterno:
			condicaoTipoEstoque = " and lcf_permiteven = 1 and lcf_intext = 'I' ";
			break;

		case DisponivelInterno:
			condicaoTipoEstoque = " and lcf_intext = 'I' ";
			break;

		case DisponivelExterno:
			condicaoTipoEstoque = " and lcf_intext = 'E' ";
			condicaoFilial.append(" and pfi_filcodigo <> ")
					.append(this.filialCodigo).append(" and lcf_intext = 'E' ");
			break;

		case DisponivelVendaGeral:
			condicaoTipoEstoque = " and lcf_permiteven = 1 ";
			break;

		default:
			condicaoTipoEstoque = "";
			break;
		}

		String vSqlEstoque = "select pfi_procodigo, pfi_filcodigo, "
				+ " pfi_estpenconfi as estpenconfi, pfi_estpenentre as estpenentre, "
				+ " pfi_estpenentra as estpenentra, pfi_estminimo as estminimo, "
				+ " pfi_estmaximo as estmaximo, lcf_intext, lcf_permiteven "
				+ camposSaldo.toString()
				+ " from "
				+ tabelaSaldo
				+ " produtofilial, localfilial "
				+ " where pfi_procodigo = "
				+ this.produtoCodigo
				+ condicaoFilial.toString()
				+ condicaoProduto.toString()
				+ condicaoTipoEstoque
				+ " group by "
				+ " pfi_procodigo, pfi_filcodigo, pfi_estpenconfi, "
				+ " pfi_estpenentre, pfi_estpenentra, pfi_estminimo, pfi_estmaximo, "
				+ " pfi_estoque, lcf_intext, lcf_permiteven";

		return vSqlEstoque;

	}

	/**
	 * 
	 * @param filialCodigo
	 * @param produtoCodigo
	 * @param localEstoqueCodigo
	 * @param lote
	 * @param flagTipoEstoque
	 * @return
	 */
	public double recuperarEstoqueDisponivel(int filialCodigo,
			int produtoCodigo, int localEstoqueCodigo, String lote,
			FlagTipoEstoque flagTipoEstoque) {

		this.produtoCodigo = produtoCodigo;
		if (this.produto != null && produtoCodigo != this.produto.getCodigo()) {
			this.produto = null;
		}
		this.filialCodigo = filialCodigo;
		this.flagTipoEstoque = flagTipoEstoque;
		this.localEstoqueCodigo = localEstoqueCodigo;
		this.lote = lote == null ? "" : lote;

		return recuperarEstoqueDisponivel();
	}

	/**
	 * 
	 * @param filialCodigo
	 * @param produto
	 * @param localEstoqueCodigo
	 * @param lote
	 * @param flagTipoEstoque
	 * @return
	 */
	public double recuperarEstoqueDisponivel(int filialCodigo, Produto produto,
			int localEstoqueCodigo, String lote, FlagTipoEstoque flagTipoEstoque) {
		this.produto = produto;
		return this.recuperarEstoqueDisponivel(filialCodigo,
				produto.getCodigo(), localEstoqueCodigo, lote, flagTipoEstoque);
	}

	/**
	 * 
	 * @param filialCodigo
	 * @param kit
	 * @param flagTipoEstoque
	 * @return
	 */
	public double recuperarEstoqueDisponivelKit(int filialCodigo, Kit kit,
			FlagTipoEstoque flagTipoEstoque) {

		boolean estoquePopulado = false;

		double estoqueQuantidade = Integer.MAX_VALUE;

		for (IItemKit item : kit.getItemKits()) {

			double estoqueAux = recuperarEstoqueDisponivel(filialCodigo,
					item.getProdutoCodigo(), 0, null, flagTipoEstoque);

			estoqueAux = (estoqueAux / item.getQuantidade())
					/ item.getProdutoUnidade().getFatorEstoque();

			if (estoqueAux > 0 && estoqueAux < estoqueQuantidade) {
				estoqueQuantidade = estoqueAux;
				estoquePopulado = true;
			} else if (estoqueAux <= 0) {
				estoquePopulado = false;
				break;
			}
		}

		if (!estoquePopulado) {
			estoqueQuantidade = 0;
		}

		return estoqueQuantidade;
	}

	/**
	 * Recupera estoque disponível do produto e preenche as propriedades
	 * (Estoque Pendente de Entrega, Estoque Pendente de Confirmação, etc...
	 * 
	 * @return
	 */
	public double recuperarEstoqueDisponivel() {

		ResultSet resultEstoque = null;

		try {
			this.limparEstoque();

			this.flagControlaLote = this
					.recuperarFlagControleLoteProduto(this.produtoCodigo);

			String vSqlEstoque = this.montarSqlRecuperarEstoque();

			resultEstoque = dao.listResultSet(vSqlEstoque);

			int a = resultEstoque.getFetchSize();

			while (resultEstoque.next()) {

				/*
				 * Obtem os estoque pendentes soment do primeiro registro.
				 */
				if (resultEstoque.isFirst()) {
					this.estoqueOutros = Conversao.nvlDouble(
							resultEstoque.getDouble("estoutros"), 0.0);

					this.estoquePendenteConfirmacao = Conversao.nvlDouble(
							resultEstoque.getDouble("estpenconfi"), 0.0);

					this.estoquePendenteEntrega = Conversao.nvlDouble(
							resultEstoque.getDouble("estpenentre"), 0.0);

					this.estoquePendenteEntrada = Conversao.nvlDouble(
							resultEstoque.getDouble("estpenentra"), 0.0);

					this.estoqueMinimo = Conversao.nvlDouble(
							resultEstoque.getDouble("estminimo"), 0.0);

					this.estoqueMaximo = Conversao.nvlDouble(
							resultEstoque.getDouble("estmaximo"), 0.0);

					this.estoquePendenteConfirmacaoLocal = Conversao.nvlDouble(
							resultEstoque.getDouble("estpenconfilce"), 0.0);

					this.estoquePendenteEntregaLocal = Conversao.nvlDouble(
							resultEstoque.getDouble("estpenentrelce"), 0.0);

					this.estoqueReservado = Conversao.nvlDouble(
							resultEstoque.getDouble("estreserv"), 0.0);
				}

				/*
				 * Calcula o estoque fisico.
				 */
				this.estoqueFisico += (Conversao.nvlDouble(
						resultEstoque.getDouble("estfisico"), 0.0));

				this.estoqueDisponivel += (Conversao.nvlDouble(
						resultEstoque.getDouble("estfisico"), 0.0))
						- (Conversao.nvlDouble(
								resultEstoque.getDouble("estreserv"), 0.0));
			}

			/*
			 * Se usa produção, pega o estoque reservado para empenho
			 */
			if (this.flagTipoEstoque == FlagTipoEstoque.Outros) {
				this.recuperarEstoqueReservadoOutros();
			}

			if (this.flagTipoEstoque == FlagTipoEstoque.Outros) {
				/*
				 * Reserva para Empenho
				 */
				this.estoqueDisponivel = this.estoqueOutros
						- this.estoqueReservado;
			} else {
				/*
				 * Se não solicitou o estoque por local, calcula o estoque
				 * fisico subtraindo os pendentes do produtoFilial.
				 */

				if ((this.localEstoqueCodigo == 0 && (this.locaisEstoque == null || this.locaisEstoque.length == 0))
						|| (this.parametros.getParametro2()
								.getFlagControlaSaldoEstoqueLocalLote() == 0))
					this.estoqueDisponivel = (this.localEstoqueCodigo > 0 ? this.estoqueFisico
							: this.estoqueFisico
									- (this.estoquePendenteConfirmacao + this.estoquePendenteEntrega));
				else
					this.estoqueDisponivel = (this.localEstoqueCodigo > 0 ? this.estoqueFisico
							: this.estoqueFisico
									- (this.estoquePendenteConfirmacaoLocal + this.estoquePendenteEntregaLocal));
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(resultEstoque);
		}

		return this.estoqueDisponivel;
	}

	/**
	 * Rotina gravar estoque de acordo com tipo de movimento
	 * 
	 * @throws Exception
	 */
	public void gravarEstoque() throws Exception {

		this.pesquisarTipoMovimentoEstoque();
		this.verificarExistenciaLocalFilial();
		this.verificarExistenciaProdutoFilial();
		this.verificarExistenciaLocalProduto();
		this.verificarExistenciaProdutoLote();

		this.montarSqlsGravarEstoque();

		if (!this.sqlProdutoFilial.trim().isEmpty()) {
			dao.executeUpdate(this.sqlProdutoFilial);
		}

		if (!this.sqlLocalProduto.trim().isEmpty()) {
			dao.executeUpdate(this.sqlLocalProduto);
		}

		if (!this.sqlProdutoLote.trim().isEmpty()) {
			dao.executeUpdate(this.sqlProdutoLote);
		}

	}

	/**
	 * 
	 * @throws Exception
	 */
	private void pesquisarTipoMovimentoEstoque() throws Exception {
		this.tipoMovimentoEstoque = null;

		this.tipoMovimentoEstoquePesquisa
				.setCodigo(this.tipoMovimentoEstoqueCodigo);

		int indice = Collections.binarySearch(tiposMovimentoEstoque,
				this.tipoMovimentoEstoquePesquisa,
				TipoMovimentoEstoque.comparatorCodigo);

		if (indice < 0) {

			throw new Exception(
					this.propriedade
							.getMensagem("alerta.estoque.tipoMovimentoEstoque"));
		}

		this.tipoMovimentoEstoque = tiposMovimentoEstoque.get(indice);
	}

	/**
	 * Verifica existencia local filial caso não exista lança exceção
	 * 
	 */
	private void verificarExistenciaLocalFilial() throws Exception {
		if (this.getLocalEstoqueCodigo() == 0)
			return;

		LocalFilial localFilial = LocalFilial.recuperarUnico(dao,
				this.getLocalEstoqueCodigo(), this.getFilialCodigo());
		
		
		if (localFilial == null) {
			throw new Exception(
					this.propriedade.getMensagem("alerta.estoque.localFilial"));

		}

	}

	/**
	 * Verifica existencia produto na filial, caso não exista insere um registro
	 * na tabela ProdutoFilial
	 */
	private void verificarExistenciaProdutoFilial() {
		ProdutoFilial produtoFilial = ProdutoFilial.recuperarUnico(
				this.getDao(), this.getFilialCodigo(), this.getProdutoCodigo());

		if (produtoFilial == null) {
			inserirProdutoFilial();
		}
	}

	/**
	 * Verifica existencia de localproduto caso haja local preenchido - caso não
	 * exista, insere registro na tabela LocalProd
	 * 
	 */
	private void verificarExistenciaLocalProduto() {

		if (this.getLocalEstoqueCodigo() == 0)
			return;

		LocalProduto localProduto = LocalProduto.recuperarUnico(this.getDao(),
				this.getLocalEstoqueCodigo(), this.getFilialCodigo(),
				this.getProdutoCodigo());

		if (localProduto == null) {
			inserirLocalProduto();
		}
	}

	/**
	 * Verifica existencia de ProdutoLote caso haja lote preenchido - caso não
	 * exista, insere registro na tabela ProdutoLote
	 * 
	 */
	private void verificarExistenciaProdutoLote() {

		if (this.getLote() == null || this.getLote().trim().isEmpty())
			return;

		ProdutoLote produtoLote = ProdutoLote.recuperarUnico(this.getDao(),
				this.getLocalEstoqueCodigo(), this.getLote(),
				this.getFilialCodigo(), this.getProdutoCodigo());

		if (produtoLote == null)
			inserirProdutoLote();

	}

	/**
	 * Inserir produto Filial
	 */
	private void inserirProdutoFilial() {

	}

	/**
	 * Inserir localProd
	 */
	private void inserirLocalProduto() {
		try {

			Date data = Conversao.converterStringParaDate("01/01/2012",
					Conversao.FORMATO_DATA);

			LocalProduto localProduto = new LocalProduto(
					this.getFilialCodigo(), this.getLocalEstoqueCodigo(),
					this.getProdutoCodigo(), 0.0, data, 0.0, 0.0, 0.0, 0.0,
					0.0, 0.0);

			dao.insertObject(localProduto);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * Inserir ProdutoLote
	 */
	private void inserirProdutoLote() {

	}

	/*
	 * Verifica se o operação é valido, ou seja, + ou -;
	 */
	private boolean verificarOperacao(String operacao) {
		if (operacao.trim().length() == 0)
			return false;

		return "+-".contains(operacao.trim());
	}

	/**
	 * Monta SQL para gravar nas tabelas de estoque, atribuindo valores às
	 * propriedades SqlProdutoFilial, SqlLocalProduto e SqlProdutoLote
	 * 
	 */
	private void montarSqlsGravarEstoque() {
		this.setSqlLocalProduto("");
		// this.sqlLocalProduto = "";
		StringBuilder sbLocalProduto = new StringBuilder();

		this.setSqlProdutoFilial("");
		// this.sqlProdutoFilial = "";
		StringBuilder sbProdutoFilial = new StringBuilder();

		this.setSqlProdutoLote("");
		// this.sqlProdutoLote = "";
		StringBuilder sbProdutoLote = new StringBuilder();

		int controleLote;

		controleLote = this.recuperarFlagControleLoteProduto(this
				.getProdutoCodigo());

		/*
		 * Estoque fisico.
		 */
		if (verificarOperacao(tipoMovimentoEstoque
				.getMovimentacaoEstoqueFisico())) {
			/*
			 * Verifica se controla lote ou local.
			 */
			if (controleLote >= 2) {
				sbProdutoLote
						.append("ple_estfisco = ple_estfisico ")
						.append(tipoMovimentoEstoque
								.getMovimentacaoEstoqueFisico())
						.append(this.getQuantidade());

				/*
				 * this.sqlProdutoLote = "ple_estfisco = ple_estfisico " +
				 * tipoMovimentoEstoque.getMovimentacaoEstoqueFisico() +
				 * this.getQuantidade();
				 */
			} else {
				sbLocalProduto
						.append("lpd_estfisco = lpd_estfisico ")
						.append(tipoMovimentoEstoque
								.getMovimentacaoEstoqueFisico())
						.append(this.getQuantidade());

				/*
				 * this.sqlLocalProduto = "lpd_estfisco = lpd_estfisico " +
				 * tipoMovimentoEstoque.getMovimentacaoEstoqueFisico() +
				 * this.getQuantidade();
				 */
			}
		}

		/*
		 * Estoques pendentes e reservado por local.
		 */

		if ((this.localEstoqueCodigo > 0)
				&& (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoqueReservado())
						|| verificarOperacao(tipoMovimentoEstoque
								.getMovimentacaoEstoqueUsado())
						|| verificarOperacao(tipoMovimentoEstoque
								.getMovimentacaoEstoquePendenteConfirmacao()) || verificarOperacao(tipoMovimentoEstoque
							.getMovimentacaoEstoquePendenteEntrega()))) {

			if (controleLote >= 2) {
				if (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoqueReservado())) {

					if (sbProdutoLote.length() > 0)
						sbProdutoLote.append(",");

					sbProdutoLote
							.append("ple_estreserv = ple_estreserv ")
							.append(tipoMovimentoEstoque
									.getMovimentacaoEstoqueReservado())
							.append(this.quantidade);

					/*
					 * this.sqlProdutoLote += (this.sqlProdutoLote.trim()
					 * .isEmpty() ? "" : ",") + "ple_estreserv = ple_estreserv "
					 * + tipoMovimentoEstoque .getMovimentacaoEstoqueReservado()
					 * + this.quantidade;
					 */
				}

				if (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoqueUsado())) {

					if (sbProdutoLote.length() > 0)
						sbProdutoLote.append(",");

					sbProdutoLote
							.append("ple_estusado = ple_estusado ")
							.append(tipoMovimentoEstoque
									.getMovimentacaoEstoqueUsado())
							.append(this.quantidade);

					/*
					 * this.sqlProdutoLote += (this.sqlProdutoLote.trim()
					 * .isEmpty() ? "" : ",") + "ple_estusado = ple_estusado " +
					 * tipoMovimentoEstoque .getMovimentacaoEstoqueUsado() +
					 * this.quantidade;
					 */
				}

				if (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoquePendenteConfirmacao())) {

					if (sbProdutoLote.length() > 0)
						sbProdutoLote.append(",");

					sbProdutoLote
							.append("ple_estpencofin = ple_estpenconfi ")
							.append(tipoMovimentoEstoque
									.getMovimentacaoEstoquePendenteConfirmacao())
							.append(this.quantidade);

					/*
					 * this.sqlProdutoLote += (this.sqlProdutoLote.trim()
					 * .isEmpty() ? "" : ",") +
					 * "ple_estpencofin = ple_estpenconfi " +
					 * tipoMovimentoEstoque
					 * .getMovimentacaoEstoquePendenteConfirmacao() +
					 * this.quantidade;
					 */
				}

				if (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoquePendenteEntrega())) {

					if (sbProdutoLote.length() > 0)
						sbProdutoLote.append(",");

					sbProdutoLote
							.append("ple_estpenentre = ple_estpenentre ")
							.append(tipoMovimentoEstoque
									.getMovimentacaoEstoquePendenteEntrega())
							.append(this.quantidade);

					/*
					 * this.sqlProdutoLote += (this.sqlProdutoLote.trim()
					 * .isEmpty() ? "" : ",") +
					 * "ple_estpenentre = ple_estpenentre " +
					 * tipoMovimentoEstoque
					 * .getMovimentacaoEstoquePendenteEntrega() +
					 * this.quantidade;
					 */
				}

			} else {
				if (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoqueReservado())) {

					if (sbLocalProduto.length() > 0)
						sbLocalProduto.append(",");

					sbLocalProduto
							.append("lpd_estreserv = lpd_estreserv ")
							.append(tipoMovimentoEstoque
									.getMovimentacaoEstoqueReservado())
							.append(this.quantidade);

					/*
					 * this.sqlLocalProduto += (this.sqlLocalProduto.trim()
					 * .isEmpty() ? "" : ",") + "lpd_estreserv = lpd_estreserv "
					 * + tipoMovimentoEstoque .getMovimentacaoEstoqueReservado()
					 * + this.quantidade;
					 */
				}

				if (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoqueUsado())) {

					if (sbLocalProduto.length() > 0)
						sbLocalProduto.append(",");

					sbLocalProduto
							.append("lpd_estusado = lpd_estusado ")
							.append(tipoMovimentoEstoque
									.getMovimentacaoEstoqueUsado())
							.append(this.quantidade);

					/*
					 * this.sqlLocalProduto += (this.sqlLocalProduto.trim()
					 * .isEmpty() ? "" : ",") + "lpd_estusado = lpd_estusado " +
					 * tipoMovimentoEstoque .getMovimentacaoEstoqueUsado() +
					 * this.quantidade;
					 */
				}

				if (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoquePendenteConfirmacao())) {

					if (sbLocalProduto.length() > 0)
						sbLocalProduto.append(",");

					sbLocalProduto
							.append("lpd_estpenconfi = lpd_estpenconfi ")
							.append(tipoMovimentoEstoque
									.getMovimentacaoEstoquePendenteConfirmacao())
							.append(this.quantidade);

					/*
					 * this.sqlLocalProduto += (this.sqlLocalProduto.trim()
					 * .isEmpty() ? "" : ",") +
					 * "lpd_estpenconfi = lpd_estpenconfi " +
					 * tipoMovimentoEstoque
					 * .getMovimentacaoEstoquePendenteConfirmacao() +
					 * this.quantidade;
					 */
				}

				if (verificarOperacao(tipoMovimentoEstoque
						.getMovimentacaoEstoquePendenteEntrega())) {

					if (sbLocalProduto.length() > 0)
						sbLocalProduto.append(",");

					sbLocalProduto
							.append("lpd_estpenentre = lpd_estpenentre ")
							.append(tipoMovimentoEstoque
									.getMovimentacaoEstoquePendenteEntrega())
							.append(this.quantidade);

					/*
					 * this.sqlLocalProduto += (this.sqlLocalProduto.trim()
					 * .isEmpty() ? "" : ",") +
					 * "lpd_estpenentre = lpd_estpenentre " +
					 * tipoMovimentoEstoque
					 * .getMovimentacaoEstoquePendenteEntrega() +
					 * this.quantidade;
					 */
				}
			}
		}

		/*
		 * Pendente de confirmação.
		 */
		if (verificarOperacao(tipoMovimentoEstoque
				.getMovimentacaoEstoquePendenteConfirmacao())) {

			sbProdutoFilial
					.append("pfi_estpenconfi = pfi_estpenconfi ")
					.append(tipoMovimentoEstoque
							.getMovimentacaoEstoquePendenteConfirmacao())
					.append(this.quantidade);

			/*
			 * this.sqlProdutoFilial = "pfi_estpenconfi = pfi_estpenconfi " +
			 * tipoMovimentoEstoque .getMovimentacaoEstoquePendenteConfirmacao()
			 * + this.quantidade;
			 */
		}

		/*
		 * Pendente de Entrega.
		 */
		if (verificarOperacao(tipoMovimentoEstoque
				.getMovimentacaoEstoquePendenteEntrega())) {

			if (sbProdutoFilial.length() > 0)
				sbProdutoFilial.append(",");

			sbProdutoFilial
					.append("pfi_estpenentre = pfi_estpenentre ")
					.append(tipoMovimentoEstoque
							.getMovimentacaoEstoquePendenteEntrega())
					.append(this.quantidade);

			/*
			 * this.sqlProdutoFilial += (this.sqlProdutoFilial.trim().isEmpty()
			 * ? "" : ",") + "pfi_estpenentre = pfi_estpenentre " +
			 * tipoMovimentoEstoque .getMovimentacaoEstoquePendenteEntrega() +
			 * this.quantidade;
			 */
		}

		/*
		 * Pendente de Entrada.
		 */
		if (verificarOperacao(tipoMovimentoEstoque
				.getMovimentacaoEstoquePendenteEntrada())) {

			if (sbProdutoFilial.length() > 0)
				sbProdutoFilial.append(",");

			sbProdutoFilial
					.append("pfi_estpenentra = pfi_estpenentra ")
					.append(tipoMovimentoEstoque
							.getMovimentacaoEstoquePendenteEntrada())
					.append(this.quantidade);

			/*
			 * this.sqlProdutoFilial += (this.sqlProdutoFilial.trim().isEmpty()
			 * ? "" : ",") + "pfi_estpenentra = pfi_estpenentra " +
			 * tipoMovimentoEstoque .getMovimentacaoEstoquePendenteEntrada() +
			 * this.quantidade;
			 */
		}

		/*
		 * Outros.
		 */
		if (verificarOperacao(tipoMovimentoEstoque
				.getMovimentacaoEstoqueOutros())) {

			if (sbProdutoFilial.length() > 0)
				sbProdutoFilial.append(",");

			sbProdutoFilial
					.append("pfi_estoque = pfi_estoque ")
					.append(tipoMovimentoEstoque.getMovimentacaoEstoqueOutros())
					.append(this.quantidade);

			/*
			 * this.sqlProdutoFilial += (this.sqlProdutoFilial.trim().isEmpty()
			 * ? "" : ",") + "pfi_estoque = pfi_estoque " +
			 * tipoMovimentoEstoque.getMovimentacaoEstoqueOutros() +
			 * this.quantidade;
			 */
		}

		/*
		 * Finaliza a montagem dos comandos sql.
		 */

		if (sbProdutoFilial.length() > 0) {
			sbProdutoFilial.insert(0, "update produtofilial set ");
			sbProdutoFilial.append(" where pfi_procodigo = ")
					.append(this.produtoCodigo).append(" and pfi_filcodigo = ")
					.append(this.filialCodigo);

			/*
			 * if (!this.sqlProdutoFilial.trim().isEmpty()) {
			 * this.sqlProdutoFilial = "update produtofilial set " +
			 * this.sqlProdutoFilial + " where pfi_procodigo = " +
			 * this.produtoCodigo + " and pfi_filcodigo = " + this.filialCodigo;
			 */
		}

		if (sbLocalProduto.length() > 0) {
			sbLocalProduto.insert(0, "update localprod set ");
			sbLocalProduto.append(" where lpd_procodigo = ")
					.append(this.produtoCodigo).append(" and lpd_filcodigo = ")
					.append(this.filialCodigo).append(" and lpd_lcecodigo = ")
					.append(this.localEstoqueCodigo);

			/*
			 * if (!this.sqlLocalProduto.trim().isEmpty()) {
			 * this.sqlLocalProduto = "update localprod set " +
			 * this.sqlLocalProduto + " where lpd_procodigo = " +
			 * this.produtoCodigo + " and lpd_filcodigo = " + this.filialCodigo
			 * + " and lpd_lcecodigo = " + this.localEstoqueCodigo;
			 */
		}

		if (sbProdutoLote.length() > 0) {
			sbProdutoLote.insert(0, "update produtolote set ");
			sbProdutoLote.append(" where ple_procodigo = ")
					.append(this.produtoCodigo).append(" and ple_filcodigo = ")
					.append(this.filialCodigo).append(" and ple_lcecodigo = ")
					.append(this.localEstoqueCodigo)
					.append(" and ple_lote = '").append(this.lote).append("'");

			/*
			 * if (!this.sqlProdutoLote.trim().isEmpty()) { this.sqlProdutoLote
			 * = "update produtolote set " + this.sqlProdutoLote +
			 * " where ple_procodigo = " + this.produtoCodigo +
			 * " and ple_filcodigo = " + this.filialCodigo +
			 * " and ple_lcecodigo = " + this.localEstoqueCodigo +
			 * " and ple_lote = '" + this.lote + "'";
			 */
		}

		this.sqlProdutoFilial = sbProdutoFilial.toString();
		this.sqlProdutoLote = sbProdutoLote.toString();
		this.sqlLocalProduto = sbLocalProduto.toString();
	}

	public double getEstoquePendenteConfirmacao() {
		return estoquePendenteConfirmacao;
	}

	public double getEstoquePendenteEntrega() {
		return estoquePendenteEntrega;
	}

	public double getEstoqueReservado() {
		return estoqueReservado;
	}

	public double getEstoquePendenteEntrada() {
		return estoquePendenteEntrada;
	}

	public double getEstoqueFisico() {
		return estoqueFisico;
	}

	public double getEstoqueOutros() {
		return estoqueOutros;
	}

	public double getEstoqueMinimo() {
		return estoqueMinimo;
	}

	public double getEstoqueMaximo() {
		return estoqueMaximo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public int getLocalEstoqueCodigo() {
		return localEstoqueCodigo;
	}

	public void setLocalEstoqueCodigo(int localEstoqueCodigo) {
		this.localEstoqueCodigo = localEstoqueCodigo;
	}

	public Integer[] getLocaisEstoque() {
		return locaisEstoque;
	}

	public void setLocaisEstoque(Integer... locaisEstoque) {
		this.locaisEstoque = locaisEstoque;
	}

	public String getLote() {
		return lote;
	}

	public void setLote(String lote) {
		this.lote = lote;
	}

	public String[] getLotes() {
		return lotes;
	}

	public void setLotes(String... lotes) {
		this.lotes = lotes;
	}

	public int getFlagControlaLote() {
		return flagControlaLote;
	}

	public void setFlagControlaLote(int flagControlaLote) {
		this.flagControlaLote = flagControlaLote;
	}

	public FlagTipoEstoque getFlagTipoEstoque() {
		return flagTipoEstoque;
	}

	public void setFlagTipoEstoque(FlagTipoEstoque flagTipoEstoque) {
		this.flagTipoEstoque = flagTipoEstoque;
	}

	public GenericoDAO getDao() {
		return dao;
	}

	public void setDao(GenericoDAO dao) {
		this.dao = dao;
	}

	public List<TipoMovimentoEstoque> getTiposMovimentoEstoque() {
		return tiposMovimentoEstoque;
	}

	public String getSqlProdutoFilial() {
		return sqlProdutoFilial;
	}

	public void setSqlProdutoFilial(String sqlProdutoFilial) {
		this.sqlProdutoFilial = sqlProdutoFilial;
	}

	public String getSqlLocalProduto() {
		return sqlLocalProduto;
	}

	public void setSqlLocalProduto(String sqlLocalProduto) {
		this.sqlLocalProduto = sqlLocalProduto;
	}

	public String getSqlProdutoLote() {
		return sqlProdutoLote;
	}

	public void setSqlProdutoLote(String sqlProdutoLote) {
		this.sqlProdutoLote = sqlProdutoLote;
	}

	public TipoMovimentoEstoque getTipoMovimentoEstoque() {
		return tipoMovimentoEstoque;
	}

	public int getTipoMovimentoEstoqueCodigo() {
		return tipoMovimentoEstoqueCodigo;
	}

	public void setTipoMovimentoEstoqueCodigo(int tipoMovimentoEstoqueCodigo) {
		this.tipoMovimentoEstoqueCodigo = tipoMovimentoEstoqueCodigo;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public static void main(String[] args) {

		System.out.println("+-".indexOf("."));

		GenericoDAO daoAux = new GenericoDAO();

		EstoqueBkp est = new EstoqueBkp(daoAux, new Parametros(daoAux,
				Filial.recuperarUnico(daoAux, 1)));

		try {
			daoAux.beginTransaction();

			// est.setDao();

			est.limpar();
			est.setFilialCodigo(1);
			est.setProdutoCodigo(1002);

			est.setFlagTipoEstoque(FlagTipoEstoque.DisponivelVendaGeral);
			est.recuperarEstoqueDisponivel();
			double pcant = est.getEstoquePendenteConfirmacao();

			est.setTipoMovimentoEstoqueCodigo(7001);
			est.setQuantidade(6);
			est.gravarEstoque();

			// est.setLocaisEstoque(new Integer[] { 2 });

			/*
			 * System.err.println(est.recuperarFlagControleLoteProduto(1002));
			 * 
			 * System.err.println(est
			 * .recuperarFlagControleLoteCertificadoProduto(1002));
			 * 
			 * est.setFlagTipoEstoque(FlagTipoEstoque.Outros);
			 * System.out.println("O=" + est.recuperarEstoqueDisponivel());
			 * 
			 * est.setFlagTipoEstoque(FlagTipoEstoque.DisponivelVendaInterno);
			 * System.out.println("V=" + est.recuperarEstoqueDisponivel());
			 * 
			 * est.setFlagTipoEstoque(FlagTipoEstoque.DisponivelInterno);
			 * System.out.println("I=" + est.recuperarEstoqueDisponivel());
			 * 
			 * est.setFlagTipoEstoque(FlagTipoEstoque.DisponivelExterno);
			 * System.out.println("E=" + est.recuperarEstoqueDisponivel());
			 * 
			 * est.setFlagTipoEstoque(FlagTipoEstoque.DisponivelVendaGeral);
			 * System.out.println("C=" + est.recuperarEstoqueDisponivel());
			 */

			est.setFlagTipoEstoque(FlagTipoEstoque.DisponivelVendaGeral);
			est.recuperarEstoqueDisponivel();
			System.out.println("PCA=" + pcant);
			System.out.println("PCD=" + est.getEstoquePendenteConfirmacao());

			daoAux.commitTransaction();
		} catch (Exception e) {
			daoAux.rollBackTransaction();
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param filialCodigo
	 * @param as
	 *            Apelido o subSelect, NÃO PODE TER A TAG AS - SOMENTE O NOME DA
	 *            COLUNA. Caso null sera "pro_estoque"
	 * @return
	 */
	public static String getSubSelectEstoqueProduto(int filialCodigo, String as) {

		String subSelectEstoque = getSubSelectEstoqueProduto(filialCodigo)
				+ " as ";

		if (StringUtil.isValida(as))
			subSelectEstoque += as;
		else
			subSelectEstoque += " pro_estoque";

		return subSelectEstoque;
	}

	/**
	 * 
	 * @param filialCodigo
	 * @param as
	 *            Apelido o subSelect, NÃO PODE TER A TAG AS - SOMENTE O NOME DA
	 *            COLUNA. Caso null sera "pro_estoque"
	 * @return
	 */
	public static String getSubSelectEstoqueProduto(int filialCodigo) {

		String subSelectEstoqueLocal = "(select "
				+ " sum(coalesce(lpd_estfisico,0) - coalesce(lpd_estreserv,0)) - "
				+ " coalesce(pfi_estpenentre,0) - coalesce(pfi_estpenconfi,0) "
				+ " from localprod inner join localfilial on"
				+ " lpd_filcodigo = lcf_filcodigo and lpd_lcecodigo = lcf_lcecodigo "
				+ " and lcf_permiteven = 1 "
				+ " where lpd_filcodigo =  "
				+ filialCodigo
				+ " and lpd_procodigo = produto.pro_codigo"
				+ " having  sum(coalesce(lpd_estfisico,0) - coalesce(lpd_estreserv,0)) -"
				+ " coalesce(pfi_estpenentre,0) - coalesce(pfi_estpenconfi,0) > 0)";

		String subSelectEstoqueLote = "(select "
				+ " sum(coalesce(ple_estfisico,0) - coalesce(ple_estreserv,0)) - "
				+ " coalesce(pfi_estpenentre,0) - coalesce(pfi_estpenconfi,0)"
				+ " from produtolote inner join localfilial on"
				+ " ple_filcodigo = lcf_filcodigo and ple_lcecodigo = lcf_lcecodigo "
				+ " and lcf_permiteven = 1 "
				+ " where ple_filcodigo = "
				+ filialCodigo
				+ " and ple_procodigo = produto.pro_codigo"
				+ " having sum(coalesce(ple_estfisico,0) - coalesce(ple_estreserv,0)) - "
				+ " coalesce(pfi_estpenentre,0) - coalesce(pfi_estpenconfi,0) > 0)";

		String subSelectEstoque = "(select coalesce("
				+ " case when par_contlote in (0, null) or pro_contlote in (0, null) then "
				+ subSelectEstoqueLocal + " else " + subSelectEstoqueLote
				+ " end, 0)" + ")";

		return subSelectEstoque;
	}

	public static boolean algumProdutoEmBalanco(List<Produto> produtos,
			int codigoFilial, GenericoDAO dao) {

		String select = getSelectProdutosEmBalanco(FormaSelectBalanco.COUNT,
				produtos, codigoFilial, 0);

		long count = dao.count(select);

		return count > 0;
	}

	public boolean produtoEmBalanco(int codigoProduto) {

		return produtoEmBalanco(codigoProduto, filialCodigo, dao);
	}

	public static boolean produtoEmBalanco(int codigoProduto, int codigoFilial,
			GenericoDAO dao) {

		String select = getSelectProdutosEmBalanco(FormaSelectBalanco.COUNT,
				null, codigoFilial, codigoProduto);

		long count = dao.count(select);

		return count > 0;
	}

	public static List<Integer> recuperarCodigoProdutosEmBalanco(
			int codigoFilial, GenericoDAO dao, List<Produto> produtos) {

		String select = getSelectProdutosEmBalanco(FormaSelectBalanco.CODIGOS,
				produtos, codigoFilial, 0);

		return dao.listUniqueField(select, "ivi_procodigo");
	}

	public static String getSelectProdutosEmBalanco(
			FormaSelectBalanco formaSelect, List<Produto> produtos,
			int codigoFilial, int produtoCodigo) {

		String whereProduto = null;

		if (ListUtil.isValida(produtos)) {
			whereProduto = "in" + GenericDAO.createValuesArgumentIn(produtos);
		} else {
			whereProduto = " = " + produtoCodigo;
		}

		String select = "select {colunas} from inventarioitem inner join inventario on ivi_invcodigo = inv_codigo and inv_filcodigo = "
				+ codigoFilial
				+ " and inv_status = 'A' and ivi_procodigo "
				+ whereProduto
				+ " group by ivi_invcodigo, ivi_filcodigo, ivi_procodigo";

		String colunas = null;
		switch (formaSelect) {
		case CODIGOS:
			colunas = "ivi_procodigo";
			break;
		case COUNT:
			colunas = "count(*)";
			break;
		}

		return select.replace("{colunas}", colunas);
	}

	public enum FormaSelectBalanco {
		COUNT, CODIGOS
	}

}