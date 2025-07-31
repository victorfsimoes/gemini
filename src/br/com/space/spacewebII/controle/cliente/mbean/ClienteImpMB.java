package br.com.space.spacewebII.controle.cliente.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.space.api.core.modelo.validacao.CNPJ;
import br.com.space.api.core.modelo.validacao.CPF;
import br.com.space.api.core.modelo.validacao.Email;
import br.com.space.api.core.modelo.validacao.ie.IE;
import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.negocio.modelo.negocio.GerenteCliente;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.controle.aplicacao.SessaoMB;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.controle.endereco.PesquisaCep;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOCep;
import br.com.space.spacewebII.modelo.dominio.cliente.Atividade;
import br.com.space.spacewebII.modelo.dominio.cliente.ClienteImp;
import br.com.space.spacewebII.modelo.dominio.endereco.Bairro;
import br.com.space.spacewebII.modelo.dominio.endereco.Cidade;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroAcessoExclusivoExcecao;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroTravamentoExcecao;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.InscricaoEstadual;

/**
 * 
 * @author Desenvolvimento
 * 
 *         Classe responsavel por fazer a gerencia do cadastro de Clientes para
 *         pré-analise.
 * 
 */

@SessionScoped
@Named("clienteImpMB2")
public class ClienteImpMB implements Serializable {

	private static final long serialVersionUID = 1L;
	private static final String NOME_PROGRAMA = "FRMIMPCLIVIKING";

	@Inject
	GenericoDAOCep daoCep;

	@Inject
	GenericoDAO dao;

	@Inject
	SessaoMB sessaoMB;

	@Inject
	GerenteLogin gerenteLogin;

	@Inject
	Propriedade propriedade;

	private String acao = "";
	private ClienteImp clienteImp = new ClienteImp();
	private String ufInscricaoEstadual = "";
	private boolean flagVisualizacao = false;

	private List<Atividade> todasAtividades = null;

	private GerenteCliente<ClienteImp> gerenteCliente = null;

	public ClienteImpMB() {

	}

	@PostConstruct
	public void PostConstruct() {
		gerenteCliente = new GerenteCliente<ClienteImp>(propriedade);
	}

	public String validacoes() {
		StringBuilder mensagem = new StringBuilder();
		if (clienteImp.getInscricaoEstadual() != null
				&& ufInscricaoEstadual != null
				&& clienteImp.getTipoPessoa().equals("J")) {
			if (!IE.Validar(clienteImp.getInscricaoEstadual(),
					ufInscricaoEstadual))
				mensagem.append("<br/> Inscrição estadual inválida.");
		}

		if (clienteImp.getCnpjCpf() != null) {
			if (clienteImp.getTipoPessoa().equals("F")) {
				if (!CPF.Validar(clienteImp.getCnpjCpf()))
					mensagem.append("<br/> CPF inválido.");
			} else {
				if (!CNPJ.Validar(clienteImp.getCnpjCpf()))
					mensagem.append("<br/> CNPJ inválido.");
			}
		}

		if (clienteImp.getEmail() != null) {
			if (!Email.Validar(clienteImp.getEmail())) {
				mensagem.append("<br/> E-mail inválido.");
			}
		}

		return mensagem.toString();
	}

	public String voltar() {

		return sessaoMB.getUrlAnterior();
	}

	public String salvar() {

		String paginaSucesso = "/pages/login.xhtml";

		String mensagemValidacao = validacoes();

		carregarCodigos();

		// List<String> nomeTabelasTravadas = new ArrayList<String>();

		try {

			if (acao == null || acao.isEmpty()) {
				if (mensagemValidacao != "" && mensagemValidacao != null) {
					sessaoMB.mensagem(TipoMensagem.Erro, "Erro!",
							mensagemValidacao);
					return null;
				}

				clienteImp.setCodigo(dao.getkeyHierarchy(clienteImp.getClass(),
						"codigo"));

				if (gerenteLogin.getCodigoFilialSelecionada() != null)
					clienteImp.setCodigoFilial(gerenteLogin
							.getCodigoFilialSelecionada());

				if (gerenteLogin.getColaboradorCodigo() != null)
					clienteImp.setCodigoColaborador(gerenteLogin
							.getColaboradorCodigo());

				clienteImp.setStatus("N");

				Date dataPreCadastro = new Date();
				clienteImp.setDataPreCadastro(dataPreCadastro);
				clienteImp.setHoraPreCadastro(Conversao.formatarData(
						dataPreCadastro, Conversao.FORMATO_HORA));

				dao.beginTransaction();

				// gerenciaTabelasTravadas(nomeTabelasTravadas);
				gerenteCliente.incluir(clienteImp, dao);

				dao.commitTransaction();

				sessaoMB.mensagem(TipoMensagem.Info, "Cadastro Cliente Imp",
						"Pré-Cadastro realizado com sucesso!");

				clienteImp = new ClienteImp();
				// sessaoMB.setAcao("");
				// sessaoMB.setCodigoSelecionado(0);

				return sessaoMB.redirecionar(paginaSucesso);

			} else if ("alterar".equalsIgnoreCase(acao)) {

				sessaoMB.travarRegistro(clienteImp,
						sessaoMB.getCodigoSessaoAtiva(), NOME_PROGRAMA, true);

				dao.beginTransaction();

				// gerenciaTabelasTravadas(nomeTabelasTravadas);
				gerenteCliente.alterar(clienteImp, dao);

				dao.commitTransaction();

				sessaoMB.mensagem(TipoMensagem.Info, "Cadastro Cliente Imp",
						"Pré-Cadastro alterado com sucesso!");

				clienteImp = new ClienteImp();
				// sessaoMB.setAcao("");
				// sessaoMB.setCodigoSelecionado(0);

				return sessaoMB.redirecionar(paginaSucesso);

			} else if ("consultar".equalsIgnoreCase(acao)) {
				return voltar();
			}

		} catch (Exception e) {
			e.printStackTrace();

			erroCadastro();
		} finally {
			try {
				sessaoMB.destravarRegistro(clienteImp,
						sessaoMB.getCodigoSessaoAtiva(), NOME_PROGRAMA, true);
			} catch (RegistroAcessoExclusivoExcecao e) {
				e.printStackTrace();
			} catch (RegistroTravamentoExcecao e) {
				e.printStackTrace();
			}
		}

		return null;
	}

	public void erroCadastro() {
		dao.rollBackTransaction();
		sessaoMB.mensagem(TipoMensagem.Fatal, "Ocorreu um erro.",
				"O pré-cadastro não foi realizado.");
	}

	public void carregarCodigos() {
		if (clienteImp.getDescricaoAtividade() != null
				&& !clienteImp.getDescricaoAtividade().isEmpty()) {
			clienteImp.setCodigoAtividade(carregarCodigoAtividade());
		}

		if (clienteImp.getDescricaoBairro() != null
				&& !clienteImp.getDescricaoBairro().isEmpty()) {
			clienteImp.setCodigoBairro(carregarCodigoBairro());
		}

		if (clienteImp.getDescricaoCidade() != null
				&& !clienteImp.getDescricaoCidade().isEmpty()) {
			clienteImp.setCodigoCidade(carregarCodigoCidade());
		}
	}

	public String carregarNovo() {
		acao = null;
		clienteImp = new ClienteImp();
		ufInscricaoEstadual = "";
		flagVisualizacao = false;

		return sessaoMB.redirecionar("/pages/cadastroPassos.xhtml");
	}

	public String carregarVisualizacao(ClienteImp clienteImp) {
		acao = "consultar";
		this.clienteImp = clienteImp;
		flagVisualizacao = true;

		return sessaoMB.redirecionar("/pages/cadastroPassos.xhtml");
	}

	public String carregarEdicao(ClienteImp clienteImp) {

		acao = "alterar";
		this.clienteImp = clienteImp;
		flagVisualizacao = false;

		return sessaoMB.redirecionar("/pages/cadastroPassos.xhtml");
	}

	public boolean flagEditando() {
		return "alterar".equals(acao) || acao == null || acao.isEmpty();
	}

	public boolean flagVisualizando() {
		return "consultar".equals(acao);
	}

	/**
	 * Apaga os dados que envolvem pessoa física e jurídica do cliente
	 */
	public void apagarDadosTipoPessoa() {
		clienteImp.setCnpjCpf(null);
		clienteImp.setRg(null);
		clienteImp.setRazao(null);
		clienteImp.setNomeFantasia(null);
		clienteImp.setInscricaoEstadual(null);
		ufInscricaoEstadual = "";
	}

	/**
	 * Apaga os dados inseridos nos campos de endereço.
	 */
	public void apagarDadosCep() {
		clienteImp.setLogradouro(null);
		clienteImp.setDescricaoBairro(null);
		clienteImp.setDescricaoCidade(null);
		clienteImp.setUfSigla(null);
		clienteImp.setPontoreferencia(null);
		clienteImp.setNumero(null);
	}

	public void cpfCnpjChangeListener(ValueChangeEvent event) {
		apagarDadosTipoPessoa();
		this.clienteImp.setCnpjCpf(event.getNewValue().toString());
	}

	/**
	 * Pesquisa o cep e preenche os campos com os dados dele.
	 * 
	 * @return
	 */
	public String pesquisarCep() {
		PesquisaCep cep = new PesquisaCep();

		apagarDadosCep();

		if (clienteImp.getCep() != null && !clienteImp.getCep().isEmpty()) {

			cep.pesquisarCEP(daoCep, clienteImp.getCep());

			if (cep.getCepLogradouro() != null
					&& !cep.getCepLogradouro().isEmpty())
				clienteImp.setLogradouro(cep.getCepLogradouro());

			if (cep.getCepBairro() != null && !cep.getCepBairro().isEmpty())
				clienteImp.setDescricaoBairro(cep.getCepBairro());

			if (cep.getCepCidade() != null && !cep.getCepCidade().isEmpty())
				clienteImp.setDescricaoCidade(cep.getCepCidade());

			if (cep.getCepUF() != null && !cep.getCepUF().isEmpty())
				clienteImp.setUfSigla(cep.getCepUF());

			if (cep.getCepReferencia() != null
					&& !cep.getCepReferencia().isEmpty())
				clienteImp.setPontoreferencia(cep.getCepReferencia());

		}
		return null;
	}

	/* CÓDIGOS */
	/**
	 * Metodo responsável por localizar o codigo do bairro e preencher na
	 * entidade
	 * 
	 * @param event
	 */

	public int carregarCodigoBairro() {

		Bairro bairroSelecionado = Bairro.recuperaBairro(dao, clienteImp
				.getDescricaoBairro().toUpperCase());

		if (bairroSelecionado != null) {
			return bairroSelecionado.getCodigoBairro();
		}

		return 0;
	}

	/**
	 * Metodo responsável por localizar o codigo da Cidade e preencher na
	 * entidade
	 * 
	 * @param event
	 */
	public int carregarCodigoCidade() {

		Cidade cidadeSelecionada = Cidade.recuperaCidade(dao, clienteImp
				.getDescricaoCidade().toUpperCase());

		if (cidadeSelecionada != null) {
			return cidadeSelecionada.getCodigoCidade();
		}

		return 0;
	}

	/**
	 * Método responsável por localizar o código da Atividade e preencher na
	 * entidade
	 * 
	 * @param event
	 */
	public int carregarCodigoAtividade() {
		Atividade atividadeSelecionada = Atividade.recuperaAtividade(dao,
				clienteImp.getDescricaoAtividade());

		if (atividadeSelecionada != null) {
			return atividadeSelecionada.getCodigoAtividade();
		}

		return 0;
	}

	public InscricaoEstadual recuperaInscricaoPorUf(String uf) {
		List<InscricaoEstadual> inscricoes = InscricaoEstadual
				.getListaInscricaoEstadual();

		for (InscricaoEstadual inscricao : inscricoes) {
			if (inscricao.getUfSigla().toLowerCase().equals(uf.toLowerCase())) {
				clienteImp.setInscricaoEstadual("");
				return inscricao;
			}
		}

		return null;
	}

	/**
	 * UFs do método auto-complete
	 * 
	 * @param sugest
	 * @return
	 */
	public List<String> pesquisarUf(Object sugest) {
		List<String> ufs = new ArrayList<String>();

		String pref = (String) sugest;

		for (InscricaoEstadual inscricaoEstadual : InscricaoEstadual
				.getListaInscricaoEstadual()) {

			if ((inscricaoEstadual != null && inscricaoEstadual.getUfSigla()
					.toLowerCase().indexOf(pref.toLowerCase()) == 0)
					|| "".equalsIgnoreCase(pref))
				ufs.add(inscricaoEstadual.getUfSigla());
		}

		return ufs;
	}

	/**
	 * Inscrições estaduais do método auto-complete
	 * 
	 * @param sugest
	 * @return
	 */
	public List<InscricaoEstadual> pesquisarInscricaoEstadual(Object sugest) {
		List<InscricaoEstadual> inscricoes = new ArrayList<InscricaoEstadual>();

		String pref = (String) sugest;

		for (InscricaoEstadual inscricaoEstadual : InscricaoEstadual
				.getListaInscricaoEstadual()) {

			if ((inscricaoEstadual != null && inscricaoEstadual.getUfSigla()
					.toLowerCase().indexOf(pref.toLowerCase()) == 0)
					|| "".equalsIgnoreCase(pref))
				inscricoes.add(inscricaoEstadual);
		}

		return inscricoes;
	}

	/**
	 * Atividades do método auto-complete
	 * 
	 * @param suggest
	 * @return
	 */
	public List<String> pesquisarDescricaoAtividades(Object suggest) {

		List<String> atividades = new ArrayList<String>();

		String pref = (String) suggest;

		for (Atividade atividade : getTodasAtividades()) {
			if ((atividade.getDescricaoAtividade() != null && atividade
					.getDescricaoAtividade().toLowerCase()
					.indexOf(pref.toLowerCase()) == 0)
					|| "".equalsIgnoreCase(pref))
				atividades.add(atividade.getDescricaoAtividade());
		}

		return atividades;
	}

	public List<Atividade> getTodasAtividades() {
		if (todasAtividades == null || todasAtividades.isEmpty())
			todasAtividades = Atividade.recuperaTodasAtividades(dao,
					"descricaoAtividade");

		return todasAtividades;
	}

	/**
	 * Metodo que é executado no momento que a pagina e renderizada
	 */
	public String dadosPaginaClientes(String url) {
		// mensagem = null;
		return url;
	}

	/**
	 * Verifica se existe algum parametro para alteração de cliente. Caso exista
	 * localiza o cliente e a os dados para visualização.
	 */
	public void carregarDadosParametro() {
	}

	/**
	 * Limpa os dados das variaveis utilizadas na tela de cadastro de Cliente.
	 * 
	 */
	public void limparTelaCadastro() {

		this.clienteImp = new ClienteImp();
		// this.mensagem = "";
		// sessaoMB.setAcao("");
		// sessaoMB.setCodigoSelecionado(0);

	}

	/**
	 * Verifica se o registro que o usuario quer alterar está travado. Se
	 * estiver lança exceção para ser capturada pelo PAGES.XML
	 * 
	 * @throws RegistroTravamentoExcecao
	 * 
	 * @throws RegistroAcessoExclusivoExcecao
	 */
	public void travaRegistroAlteracao2() throws RegistroTravamentoExcecao {

		if ("alterar".equalsIgnoreCase(acao)) {
			try {
				sessaoMB.travarRegistro(clienteImp,
						sessaoMB.getCodigoSessaoAtiva(), NOME_PROGRAMA, true);
			} catch (RegistroAcessoExclusivoExcecao e) {
				e.printStackTrace();
			}

			System.out.println("Registro travado!");
		}
	}

	/**
	 * Verifica se o usuario logado tem permissao para acessar a Pagina
	 * 
	 * @return
	 */
	public boolean verificarPermissaoPrograma() {

		boolean retorno = false;

		return retorno;
	}

	public String getAcao() {
		return acao;
	}

	public void setAcao(String acao) {
		this.acao = acao;
	}

	public ClienteImp getClienteImp() {
		return clienteImp;

	}

	public void setClienteImp(ClienteImp clienteImp) {
		this.clienteImp = clienteImp;
	}

	public String getNome_Programa() {
		return NOME_PROGRAMA;
	}

	public String getUfInscricaoEstadual() {
		return ufInscricaoEstadual;
	}

	public void setUfInscricaoEstadual(String ufInscricaoEstadual) {
		this.ufInscricaoEstadual = ufInscricaoEstadual;
	}

	public GerenteCliente<ClienteImp> getGerenteCliente() {
		return gerenteCliente;
	}

	public void setGerenteCliente(GerenteCliente<ClienteImp> gerenteCliente) {
		this.gerenteCliente = gerenteCliente;
	}

	public boolean isFlagVisualizacao() {
		return flagVisualizacao;
	}

	public void setFlagVisualizacao(boolean flagVisualizacao) {
		this.flagVisualizacao = flagVisualizacao;
	}

	/*
	 * public void gerenciaTabelasTravadas(List<String> tabelas) { if (tabelas
	 * != null && !tabelas.isEmpty()) { sessaoMB.travarTabelas(tabelas);
	 * 
	 * for (String tabela : tabelas) { if (tabela.equals("bairro")) { Bairro
	 * bairro = cadastrarBairro(); if (bairro != null) { dao.insert(bairro);
	 * continue; } else { erroCadastro(); } }
	 * 
	 * if (tabela.equals("cidade")) { Cidade cidade = cadastrarCidade();
	 * 
	 * if (cidade != null) { dao.insert(cidade); continue; } else {
	 * erroCadastro(); } } } } }
	 */

	/**
	 * Adiciona novo bairro na base de dados da Space Informática
	 */
	/*
	 * public Bairro cadastrarBairro() {
	 * 
	 * // int novoCodigo = Bairro.maxCodigo(dao); int novoCodigo = 0; if
	 * (novoCodigo != 0) {
	 * 
	 * novoCodigo++; Bairro novoBairro = new Bairro();
	 * novoBairro.setBairroAtivo(1); novoBairro.setCodigoBairro(novoCodigo);
	 * novoBairro.setDescricaoBairro(clienteImp.getDescricaoBairro());
	 * 
	 * clienteImp.setCodigoBairro(novoCodigo);
	 * 
	 * return novoBairro; }
	 * 
	 * return null; }
	 */

	/**
	 * Adiciona nova cidade na base de dados da Space Informática
	 */
	/*
	 * public Cidade cadastrarCidade() { // int novoCodigo =
	 * Cidade.maxCodigo(dao); int novoCodigo = 0; if (novoCodigo != 0) {
	 * 
	 * novoCodigo++;
	 * 
	 * Cidade novaCidade = new Cidade(); novaCidade.setCidadeAtivo(1);
	 * novaCidade.setCodigoCidade(novoCodigo);
	 * novaCidade.setDescricaoCidade(clienteImp.getDescricaoCidade());
	 * 
	 * clienteImp.setCodigoCidade(novoCodigo);
	 * 
	 * return novaCidade; }
	 * 
	 * return null; }
	 */
}
