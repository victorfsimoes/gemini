package br.com.space.spacewebII.controle.pesquisa;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.persistence.Table;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.controle.aplicacao.SessaoMB;
import br.com.space.spacewebII.controle.padrao.mbean.GerenteMB;
import br.com.space.spacewebII.modelo.anotacao.ObjetoPesquisaSelecionado;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.estoque.NaturezaOperacao;
import br.com.space.spacewebII.modelo.dominio.financeiro.CondicaoPagamento;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.pessoa.Colaborador;
import br.com.space.spacewebII.modelo.dominio.sistema.DicionarioCampos;
import br.com.space.spacewebII.modelo.dominio.sistema.DicionarioTabelas;
import br.com.space.spacewebII.modelo.dominio.venda.OpcaoEspecial;
import br.com.space.spacewebII.modelo.dominio.venda.TabelaPreco;
import br.com.space.spacewebII.modelo.dominio.venda.Transportadora;
import br.com.space.spacewebII.modelo.dominio.venda.Vendedor;
import br.com.space.spacewebII.modelo.pesquisa.Filtro;
import br.com.space.spacewebII.modelo.util.Arquivo;

import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

/**
 * Classe responsável pela tela de pesquisa
 * 
 * @author Desenvolvimento
 * 
 */
@URLMappings(mappings = {
		@URLMapping(id = "pesquisaBarra", pattern = "/pesquisa/", viewId = "/pages/pesquisa.xhtml", onPostback = false),
		@URLMapping(id = "pesquisa", pattern = "/pesquisa", viewId = "/pages/pesquisa.xhtml", onPostback = false) })
@ManagedBeanSessionScoped
@URLBeanName("pesquisaMB")
public class PesquisaMB extends GerenteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Arquivo arquivo;

	@Inject
	@ObjetoPesquisaSelecionado
	private Event<Object> event;
	private Object objetoSelecionado = null;

	private List<Filtro> filtros = null;
	private DicionarioTabelas tabela = null;
	private List<DicionarioCampos> camposConsulta = null;
	private List<String> joins = null;
	private List<DicionarioCampos> dicCampos = null;
	private List<DicionarioCampos> dicCamposIndice = null;
	private List<Linha> resultadosString = null;
	private List<String> titulosColunas = null;

	private String select = "";
	private String orderBy = "";

	private String whereAdcional = null;

	private Class<? extends IPersistent> classeTabela;

	private boolean exibirAtivo = false;
	private String nomeColunaAtivo = new String();
	private String statusSelecionado = "1";

	private int linhasPaginacaoGrid = 10;
	private String urlRequsicao = null;

	public PesquisaMB() {
		tratarVariaveis();
	}

	/**
	 * Seleciona a classe que será pesquisada <br/>
	 * 
	 * Caso o usuario clique na seleção de um requistro ou cancelar será
	 * redirecionado para a URL de requisição que será preenchida
	 * automaticamnete.
	 * 
	 * 
	 * @param tipo
	 *            cliente / colaborador / tabelapreco / formapagamento /
	 *            condicaopagamento
	 * 
	 * 
	 * 
	 * @return {@link #pesquisa(String, String)}
	 * 
	 * @see PesquisaMB#pesquisa(String, String)
	 * 
	 */
	public String pesquisa(String tipo) {

		return pesquisa(tipo, null);

	}

	/**
	 * Seleciona a classe que será pesquisada
	 * 
	 * @param tipo
	 *            cliente / colaborador / tabelapreco / formapagamento /
	 *            condicaopagamento
	 * @param urlVoltar
	 *            Para onde o usuario será redirecionado caso clique na seleção
	 *            de um requistro ou cancelar
	 * 
	 * 
	 * 
	 * @return {@link #getUrlView()}
	 */
	public String pesquisa(String tipo, String urlVoltar) {
		if (tipo.toLowerCase().equals("cliente"))
			exibirPesquisa(Cliente.class, urlVoltar);

		if (tipo.toLowerCase().equals("colaborador"))
			exibirPesquisa(Colaborador.class, urlVoltar);

		if (tipo.toLowerCase().equals("tabelapreco"))
			exibirPesquisa(TabelaPreco.class, urlVoltar);

		if (tipo.toLowerCase().equals("formapagamento"))
			exibirPesquisa(FormaPagamento.class, urlVoltar);

		if (tipo.toLowerCase().equals("condicaopagamento"))
			exibirPesquisa(CondicaoPagamento.class, urlVoltar);

		if (tipo.toLowerCase().equals("vendedor"))
			exibirPesquisa(Vendedor.class, urlVoltar);

		if (tipo.toLowerCase().equals("naturezaoperacao"))
			exibirPesquisa(NaturezaOperacao.class, "nat_liberavenda=1 and nat_libweb = 1", urlVoltar);

		if (tipo.toLowerCase().equals("transportadora"))
			exibirPesquisa(Transportadora.class, urlVoltar);

		if (tipo.toLowerCase().equals("opcaoespecial"))
			exibirPesquisa(OpcaoEspecial.class, urlVoltar);

		return getUrlView();
	}

	/**
	 * Seleciona objeto pesquisado
	 * 
	 * @param id
	 * @return
	 */
	public String selecionar(String id) {
		String sql = "select * " + getFrom() + getJoins() + " where " + id;

		List<? extends IPersistent> registros = dao.list(classeTabela, sql);

		if (ListUtil.isValida(registros)) {
			objetoSelecionado = registros.get(0);

			dispararEvento(objetoSelecionado);

			return getUrlVoltar();
		}

		return null;
	}

	public String voltar() {
		return getUrlVoltar();
	}

	public String getUrlVoltar() {

		String url = urlRequsicao;

		return url;
	}

	@Override
	public String getUrlView() {
		return getUrlViewPattern();
	}

	@Override
	protected String getUrlViewPage() {
		return "pages/pesquisa.xhtml";
	}

	@Override
	protected String getUrlViewPattern() {
		return "/pesquisa";
	}

	private void dispararEvento(Object object) {
		event.fire(object);
	}

	/**
	 * Inicializa variáveis encapsuladas
	 * 
	 * public void inicializaVariaveis() {
	 * 
	 * filtros = new ArrayList<Filtro>(); camposConsulta = new
	 * ArrayList<DicionarioCampos>(); resultadosString = new ArrayList<Linha>();
	 * statusSelecionado = "1"; objetoSelecionado = null; exibirAtivo = false;
	 * select = ""; orderBy = ""; joins = new ArrayList<String>();
	 * titulosColunas = new ArrayList<String>(); }
	 */

	private void tratarVariaveis() {

		if (filtros == null) {
			filtros = new ArrayList<Filtro>();
		} else {
			filtros.clear();
		}

		if (camposConsulta == null) {
			camposConsulta = new ArrayList<DicionarioCampos>();
		} else {
			camposConsulta.clear();
		}

		if (resultadosString == null) {
			resultadosString = new ArrayList<Linha>();
		} else {
			resultadosString.clear();
		}

		if (joins == null) {
			joins = new ArrayList<String>();
		} else {
			joins.clear();
		}

		if (titulosColunas == null) {
			titulosColunas = new ArrayList<String>();
		} else {
			titulosColunas.clear();
		}

		whereAdcional = null;

		tabela = null;
		classeTabela = null;

		statusSelecionado = "1";
		objetoSelecionado = null;
		exibirAtivo = false;
		select = "";
		orderBy = "";
		linhasPaginacaoGrid = 10;

	}

	public void exibirPesquisa(Class<? extends IPersistent> classeTabela,
			String urlVoltar) {

		exibirPesquisa(classeTabela, null, urlVoltar);

	}

	public void exibirPesquisa(Class<? extends IPersistent> classeTabela,
			String filtoAdcional, String urlVoltar) {

		exibirPesquisa(classeTabela, null, filtoAdcional, urlVoltar);

	}

	/**
	 * Carrega os dados da pesquisa
	 * 
	 * @param classeTabela
	 */
	private void exibirPesquisa(Class<? extends IPersistent> classeTabela,
			String formAdicional, String filtoAdcional, String urlVoltar) {

		tratarVariaveis();

		this.whereAdcional = filtoAdcional;
		this.classeTabela = classeTabela;

		/*
		 * Obtem o nome da tabela anotada na classe.
		 */
		String nomeTabela = new String();
		if (classeTabela.isAnnotationPresent(Table.class)) {
			Table table = classeTabela.getAnnotation(Table.class);
			nomeTabela = table.name().toUpperCase();
		}
		tabela = DicionarioTabelas.recuperarUnico(dao, nomeTabela);

		dicCampos = DicionarioCampos
				.recuperaCamposGridConsulta(dao, nomeTabela);

		dicCamposIndice = DicionarioCampos
				.recuperaCamposIndice(dao, nomeTabela);

		DicionarioCampos.atualizarIndices(dicCampos, dicCamposIndice);

		for (DicionarioCampos campo : dicCampos) {
			/*
			 * RONIE - if (!campo.getTituloCampo().equals("CNPJ / CPF"))
			 * campo.setTituloCampo(campo.getTituloCampo().toLowerCase());
			 */

			if (campo.getFlagExibidoNoGrid() == 1 || campo.isFlagIndice()) {

				if (campo.getFlagExibidoNoGrid() == 1)
					titulosColunas.add(campo.getTituloCampo());

				if (!select.equals(""))
					select += ", ";
				select += campo.getDicCamposPK().getNomeCampo();

				if (campo.getTabelaPai() != null
						&& !campo.getTabelaPai().trim().isEmpty()) {
					montaJoin(campo);
				}

				if (campo.getFlagExibidoNoGrid() == 1
						&& campo.getOrdemPadraoGrid() == 1) {
					if (!orderBy.equals(""))
						orderBy += ", ";
					orderBy += campo.getDicCamposPK().getNomeCampo();
				}
			}

			if (campo.getTituloCampo().toLowerCase().equals("ativo")) {
				exibirAtivo = true;
				nomeColunaAtivo = campo.getDicCamposPK().getNomeCampo();
				continue;
			}

			if (campo.getFlagExibidoNoGrid() == 1) {
				// campo.getFlagConsulta() == 1) {

				/*
				 * if (campo.getTituloCampo().toLowerCase().equals("ativo")) {
				 * exibirAtivo = true; nomeColunaAtivo =
				 * campo.getDicCamposPK().getNomeCampo(); continue; }
				 */

				filtros.add(new Filtro(campo.getTituloCampo(), campo
						.getTamanhoGrid(), campo.getDicCamposPK()
						.getNomeCampo(), campo.getTipoCampo(), null));
			}

			linhasPaginacaoGrid = 10;
			if (!filtros.isEmpty())
				linhasPaginacaoGrid -= 2;

			if (filtros.size() >= 4)
				linhasPaginacaoGrid -= (filtros.size() / 4) * 2;

			if (exibirAtivo)
				linhasPaginacaoGrid -= 2;

			if (linhasPaginacaoGrid < 5)
				linhasPaginacaoGrid = 5;
		}

		this.atualizarTipoFiltros();

		// Caso não exista filtors pesquisa de uma vez
		if (filtros.isEmpty()) {
			try {
				pesquisar();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (StringUtil.isValida(urlVoltar)) {
			urlRequsicao = urlVoltar;
		} else {
			urlRequsicao = getURLRequisicaoSemRootPath();
		}
	}

	/**
	 * cria o from da query
	 * 
	 * @return
	 */
	private String getFrom() {
		return " from " + tabela.getDtbTabela().trim().toLowerCase();
	}

	/**
	 * retorna os joins da query
	 * 
	 * @return
	 */
	private String getJoins() {
		StringBuilder todosJoins = new StringBuilder();
		for (String join : joins) {
			todosJoins.append(join);
		}

		return todosJoins.toString();
	}

	/**
	 * Acrescenta join à query
	 * 
	 * @param campo
	 */
	private void montaJoin(DicionarioCampos campo) {
		String join = (" left join " + campo.getTabelaPai().trim() + " on "
				+ campo.getChavePai().trim() + " = " + campo.getChaveFilho()
				.trim()).toLowerCase();

		montaJoin(join);

	}

	private void montaJoin(String join) {

		boolean joinRepetido = false;
		if (joins.size() >= 1) {
			for (int i = 0; i < joins.size(); i++) {
				if (joins.get(i).equals(join)) {
					joinRepetido = true;
					break;
				}
			}
		}
		if (!joinRepetido)
			joins.add(join);

	}

	/**
	 * cria where da query
	 * 
	 * @return
	 */
	private String getWhere() {

		StringBuilder where = new StringBuilder(StringUtil.isValida(whereAdcional) ? whereAdcional : "");

		for (Filtro filtro : filtros) {
			if (StringUtil.isValida(filtro.getValue())) {

				if (!where.toString().equals(""))
					where.append(" and ");

				where.append(filtro.getCampo());

				if (filtro.getTipoCampo().toLowerCase().equals("n"))
					where.append(" = ").append(filtro.getValue());
				else if (filtro.getTipoCampo().toLowerCase().equals("d"))
					where.append(" = '").append(filtro.getValue()).append("'");
				else
					where.append(" like '").append(filtro.getValue()).append("%'");
			}
		}

		if (exibirAtivo && !statusSelecionado.equals("-1")) {
			if (!where.toString().equals(""))
				where.append(" and ");

			where.append(nomeColunaAtivo).append(" = ").append(statusSelecionado);
		}

		if (!where.toString().equals(""))
			return " where " + where.toString();
		else
			return "";
	}

	/**
	 * cria orderBy da where
	 * 
	 * @return
	 */
	private String getOrderBy() {
		String orderBy = "";

		if (!this.orderBy.equals(""))
			orderBy = " order by " + this.orderBy;

		return orderBy;
	}

	/**
	 * Atualiza o tipo dos campos dos filtros de acordo com o tipo do banco de
	 * dados. Evita erro de dicionario.
	 */
	private void atualizarTipoFiltros() {
		if (!ListUtil.isValida(filtros))
			return;

		String query = "select " + select + getFrom() + getJoins()
				+ " where null = null " + getOrderBy();

		ResultSet rs = null;
		try {
			rs = dao.listResultSet(query, null, null);

			for (Filtro filtro : filtros) {

				int tipoField = rs.getMetaData().getColumnType(
						rs.findColumn(filtro.getCampo()));
				String tipoString = filtro.getTipoCampo();

				switch (tipoField) {
				case java.sql.Types.VARCHAR:
				case java.sql.Types.CHAR:
					tipoString = "C";
					break;
				case java.sql.Types.DOUBLE:
				case java.sql.Types.FLOAT:
				case java.sql.Types.INTEGER:
				case java.sql.Types.DECIMAL:
				case java.sql.Types.NUMERIC:
					tipoString = "N";
					break;
				case java.sql.Types.DATE:
				case java.sql.Types.TIMESTAMP:
					tipoString = "D";
					break;
				}

				filtro.setTipoCampo(tipoString);
			}
		} catch (Exception ex) {
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (SQLException e) {
				}
		}
	}

	/**
	 * método de pesquisa por filtros
	 * 
	 * @throws Exception
	 */
	public void pesquisar() throws Exception {

		if (select.isEmpty() || select == null)
			select = "*";

		String query = "select " + select + getFrom() + getJoins() + getWhere()
				+ getOrderBy();

		ResultSet rs = null;
		try {
			rs = dao.listResultSet(query, null, null);

			resultadosString = new ArrayList<>();

			while (rs.next()) {

				List<Resultado> listColunas = new ArrayList<Resultado>();

				Linha linha = new Linha();

				List<String> whereFragmentos = new ArrayList<>();

				for (DicionarioCampos campos : dicCampos) {

					if (campos.getFlagExibidoNoGrid() == 0
							&& !campos.isFlagIndice()) {
						continue;
					}

					String campoBD = campos.getDicCamposPK().getNomeCampo()
							.trim();
					String valor = rs.getString(campoBD);

					/*
					 * Gera os valores das colunas a serem exibidas
					 */
					if (campos.getFlagExibidoNoGrid() == 1) {
						valor = valor == null ? valor : valor.trim();
						listColunas.add(new Resultado(campoBD, null, valor));
					}

					/*
					 * Gera o ID da classe a ser consultada quando selecionada
					 * pela chave primaria. Se não exisitir chave, pega todos os
					 * campos que estão sendo exibidos na pesquisa.
					 */
					if ((campos.getFlagExibidoNoGrid() == 1 && !ListUtil
							.isValida(dicCamposIndice))
							|| campos.isFlagIndice()) {

						StringBuilder whereID = new StringBuilder(campoBD);
						if (valor == null) {
							whereID.append(" is null ");
						} else if (campos.getTipoCampo().equalsIgnoreCase("N")) {
							whereID.append(" = ").append(valor);
						} else {
							whereID.append(" = '").append(valor).append("'");
						}

						whereFragmentos.add(whereID.toString());
					}
				}

				linha.setId(GenericoDAO.criarWhere(whereFragmentos));
				linha.setResultados(listColunas);

				resultadosString.add(linha);
			}
		} finally {
			if (rs != null)
				try {
					rs.close();
				} catch (Exception e) {
				}
		}
	}

	public DicionarioTabelas getTabela() {
		return tabela;
	}

	public void setTabela(DicionarioTabelas tabela) {
		this.tabela = tabela;
	}

	public List<DicionarioCampos> getCamposConsulta() {
		return camposConsulta;
	}

	public void setCamposConsulta(List<DicionarioCampos> camposConsulta) {
		this.camposConsulta = camposConsulta;
	}

	public GenericoDAO getDao() {
		return dao;
	}

	public void setDao(GenericoDAO dao) {
		this.dao = dao;
	}

	public SessaoMB getSessaoMB() {
		return sessaoMB;
	}

	public void setSessaoMB(SessaoMB sessaoMB) {
		this.sessaoMB = sessaoMB;
	}

	public boolean getExibirAtivo() {
		return exibirAtivo;
	}

	public void setExibirAtivo(boolean exibirAtivo) {
		this.exibirAtivo = exibirAtivo;
	}

	public Class<? extends IPersistent> getClasseTabela() {
		return classeTabela;
	}

	public void setClasseTabela(Class<? extends IPersistent> classeTabela) {
		this.classeTabela = classeTabela;
	}

	public String getStatusSelecionado() {
		return statusSelecionado;
	}

	public void setStatusSelecionado(String statusSelecionado) {
		this.statusSelecionado = statusSelecionado;
	}

	public List<Filtro> getFiltros() {
		return filtros;
	}

	public void setFiltros(List<Filtro> filtros) {
		this.filtros = filtros;
	}

	public int getLinhasPaginacaoGrid() {
		return linhasPaginacaoGrid;
	}

	public void setLinhasPaginacaoGrid(int linhasPaginacaoGrid) {
		this.linhasPaginacaoGrid = linhasPaginacaoGrid;
	}

	public class Resultado {

		private String nomeCampoBD;
		private String tituloColuna;
		private String valores;

		public Resultado(String nomeCampoBD, String tituloColuna, String valor) {
			this();
			this.nomeCampoBD = nomeCampoBD;
			this.tituloColuna = tituloColuna;
			this.valores = valor;
		}

		public Resultado() {
			super();

		}

		public String getNomeCampoBD() {
			return nomeCampoBD;
		}

		public void setNomeCampoBD(String nomeCampoBD) {
			this.nomeCampoBD = nomeCampoBD;
		}

		public String getTituloColuna() {
			return tituloColuna;
		}

		public void setTituloColuna(String tituloColuna) {
			this.tituloColuna = tituloColuna;
		}

	}

	public List<Linha> getResultadosString() {
		return resultadosString;
	}

	public void setResultadosString(List<Linha> resultadosString) {
		this.resultadosString = resultadosString;
	}

	public Object getObjetoSelecionado() {
		return objetoSelecionado;
	}

	public void setObjetoSelecionado(Object objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	public class Linha {

		private String id = null;
		private List<Resultado> resultados = null;

		public Linha() {
		}

		public Linha(String id, List<Resultado> resultados) {
			super();
			this.id = id;
			this.resultados = resultados;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String valor(int i) {

			return resultados.get(i).valores;

		}

		public List<Resultado> getResultados() {
			return resultados;
		}

		public void setResultados(List<Resultado> resultados) {
			this.resultados = resultados;
		}

	}

	public List<String> getTitulosColunas() {
		return titulosColunas;
	}

	public void setTitulosColunas(List<String> titulosColunas) {
		this.titulosColunas = titulosColunas;
	}

	@Override
	public String getNomePrograma() {
		return null;
	}

	@Override
	public boolean necessarioLogin() {

		return false;
	}

	@Override
	public boolean permiteAcessoDeCliente() {
		return true;
	}

}
