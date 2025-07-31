package br.com.space.spacewebII.controle.cliente.mbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;

import org.primefaces.event.FlowEvent;

import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.com.space.api.core.modelo.validacao.CNPJ;
import br.com.space.api.core.modelo.validacao.CPF;
import br.com.space.api.core.modelo.validacao.Email;
import br.com.space.api.core.propriedade.Propriedade;
import br.com.space.api.core.sistema.Criptografia;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.aplicacao.GerenteEmail;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.controle.endereco.PesquisaCep;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.aplicacao.Configuracao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOCep;
import br.com.space.spacewebII.modelo.dominio.cliente.Atividade;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.endereco.Bairro;
import br.com.space.spacewebII.modelo.dominio.endereco.Cidade;
import br.com.space.spacewebII.modelo.dominio.endereco.ClassificaEndereco;
import br.com.space.spacewebII.modelo.dominio.endereco.Enderecos;
import br.com.space.spacewebII.modelo.dominio.pessoa.CondicaoPagamentoPessoa;
import br.com.space.spacewebII.modelo.dominio.pessoa.FormaPagamentoPessoa;
import br.com.space.spacewebII.modelo.dominio.pessoa.GrupoVendaNaoPermitidoCliente;
import br.com.space.spacewebII.modelo.dominio.pessoa.Pessoa;
import br.com.space.spacewebII.modelo.dominio.sistema.DicionarioCampos;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametro3;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.util.InscricaoEstadual;

/**
 * 
 * @author Desenvolvimento
 * 
 *         Classe responsavel por fazer a gerencia do cadastro de Clientes para
 *         pré-analise.
 * 
 */
@ManagedBeanSessionScoped
@URLMappings(mappings = {
		@URLMapping(id = "meuPerfil", pattern = "/meuPerfil/", viewId = "/pages/meuPerfil.xhtml", onPostback = false),
		@URLMapping(id = "cadastroClienteBarra", pattern = "/cliente/", viewId = "/pages/cadastroPassos.xhtml", onPostback = false) })
@URLBeanName("gerenteClienteMB")
public class GerenteClienteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Configuracao config;

	@Inject
	private GenericoDAO dao;

	@Inject
	private GenericoDAOCep daoCep;

	@Inject
	private Parametros parametros;

	@Inject
	private Propriedade propriedade;

	@Inject
	private GerenteLogin gerenteLogin;

	private String ufInscricaoEstadual = "";
	private List<Atividade> todasAtividades = null;
	private List<ClassificaEndereco> classificacoesEndereco = null;
	private boolean inscricaoEstadualIsenta = false;
	private boolean inscricaoMunicipalIsenta = false;

	private Cliente clientePadrao;

	private Pessoa pessoa;
	private Cliente cliente;
	private Enderecos endereco;
	private String bairro, cidade;
	private List<Cidade> novasCidades;
	private List<Bairro> novosBairros;

	private boolean clienteExistente = false;
	private int numeroEnderecosRecuperadosEdicao = 0;

	private List<Enderecos> enderecos;

	private String msgSituacaoCadastro = null;
	private String msgSituacaoEnvioConfirmacao = null;

	private DicionarioCampos dicionarioCamposTelefone1;
	private DicionarioCampos dicionarioCamposCelular;

	public GerenteClienteMB(Propriedade propriedade) {

	}

	public GerenteClienteMB() {

	}

	/**
	 * 
	 * @param event
	 * @return
	 */
	public String handleFlow(FlowEvent event) {
		String currentStepId = event.getOldStep();
		String stepToGo = event.getNewStep();

		try {
			/*
			 * tab_login -> tab_dados -> tab_endereco -> tab_confirmacao
			 */
			if (currentStepId.equals("tab_login") && stepToGo.equals("tab_dados")) {
				/*
				 * Validar e-mail válido ou duplicado.
				 */
				this.validarEMail();
			} else if (currentStepId.equals("tab_dados") && stepToGo.equals("tab_endereco")) {
				/*
				 * Valida os Documentos
				 */
				this.validarCpfCnpj();
			} else if (currentStepId.equals("tab_endereco") && stepToGo.equals("tab_confirmacao")) {

				if (this.enderecos.size() <= 0) {
					throw new SpaceExcecao(propriedade.getMensagem("alerta.endereco.vazio"));
				}
			}

		} catch (Exception e) {
			e.printStackTrace();
			MensagemSistema.exibir(TipoMensagem.Erro, e.getMessage(), null);
			return currentStepId;
		}

		return event.getNewStep();
	}

	/**
	 * 
	 * @throws SpaceExcecao
	 */
	private void validarEMail() throws SpaceExcecao {
		/*
		 * Verifica se o e-mail está mal formado.
		 */
		if (!Email.Validar(cliente.getEmailLoginWeb())) {
			throw new SpaceExcecao(propriedade.getMensagem("alerta.email.invalido"));
		}

		/*
		 * Verifica se o e-mail já foi usado por outro cliente.
		 */

		if (Cliente.isExisteEmail(dao, cliente.getEmailLoginWeb())) {
			throw new SpaceExcecao(propriedade.getMensagem("alerta.email.jaCadastrado"));
		}

	}

	/**
	 * 
	 * @return
	 */
	public String carregarNovo() {
		clientePadrao = Cliente.recuperarUnico(dao, parametros.getParametroWeb().getClienteEspelhoNegociacaoCodigo());

		cliente = new Cliente();

		carregarNovaPessoa();

		/*
		 * Se encontrou o cliente padrão copia todos os dados necessários para o novo
		 * cliente.
		 */
		if (clientePadrao != null) {

			carregarPadraoRegrasNegociacao();
			carregarPadraoLogistica();
			carregarPadraoFinaceiro();
			carregarPadraoOutros();
		} else {
			MensagemSistema.exibir(TipoMensagem.Erro, propriedade.getMensagem("alerta.ocorreuUmErro"), null);

			return "/login/";
		}

		novasCidades = new ArrayList<>();
		novosBairros = new ArrayList<>();

		classificacoesEndereco = ClassificaEndereco.recuperarClassificacoesAtivas(dao,
				config.getAtributos().getFilialPadraoCodigo());

		enderecos = new ArrayList<Enderecos>();
		carregarNovoEndereco();

		ufInscricaoEstadual = "";

		return null;
	}

	private void carregarPadraoTabelasPorTipoPessoa() {

		ParametroWeb parametroWeb = parametros.getParametroWeb();

		Parametro3 parametro3 = parametros.getParametro3();

		boolean sugereTabelaGuardian = parametro3 != null && parametro3.isSugereTabelaPrecoCliente();
		boolean possuiParametroWeb = parametroWeb != null;

		if ("J".equalsIgnoreCase(cliente.getTipoPessoa())) {

			if (possuiParametroWeb && parametroWeb.getTabelaPrecoCodigoSugeridaPessoaJuridica() > 0) {

				cliente.setTabelaPrecoPadraoGemini(parametroWeb.getTabelaPrecoCodigoSugeridaPessoaJuridica());
			}

			if (sugereTabelaGuardian && parametro3.getTabelaPrecoCodigoPessoaJuridica() > 0) {
				cliente.setTabelaPrecoCodigoGuardian(parametro3.getTabelaPrecoCodigoPessoaJuridica());
			}

		} else if ("F".equalsIgnoreCase(cliente.getTipoPessoa())) {

			if (possuiParametroWeb && parametroWeb.getTabelaPrecoCodigoSugeridaPessoaFisica() > 0) {
				cliente.setTabelaPrecoPadraoGemini(parametroWeb.getTabelaPrecoCodigoSugeridaPessoaFisica());
			}

			if (sugereTabelaGuardian && parametro3.getTabelaPrecoCodigoPessoaFisica() > 0) {
				cliente.setTabelaPrecoCodigoGuardian(parametro3.getTabelaPrecoCodigoPessoaFisica());
			}
		}
	}

	private void carregarPadraoOutros() {
		/*
		 * Outros dados de clientes / Pessoa - setando para não cadastrar como NULL
		 */
		cliente.setSequenciaEntrega(0);
		cliente.setObservacaoEntrega("");
		cliente.setNacionalidade("");
		cliente.setNaturalidade("");
		cliente.setCodigoBarras("");
		cliente.setPontos(0.00);
		cliente.setNumeroDependentes(0);
		cliente.setNomePai("");
		cliente.setNomeMae("");
		cliente.setRazaoSocialEmpregador("");
		cliente.setEnderecoEmpregador("");
		cliente.setNumeroEmpregador("");
		cliente.setBairroEmpregadorCodigo(0);
		cliente.setCidadeEmpregadorCodigo(0);
		cliente.setUfEmpregador("");
		cliente.setCepEmpregador("");
		cliente.setCargoEmprego("");
		cliente.setSalarioEmprego(0.00);
		cliente.setFone1Empregador("");
		cliente.setFone2Empregador("");
		cliente.setNomeConjuge("");
		cliente.setRazaoSocialConjuge("");
		cliente.setEnderecoConjuge("");
		cliente.setNumeroConjuge("");
		cliente.setBairroConjugeCodigo(0);
		cliente.setCidadeConjugeCodigo(0);
		cliente.setUfConjuge("");
		cliente.setFone1Conjuge("");
		cliente.setFone2Conjuge("");
		cliente.setCelularConjuge("");
		cliente.setCargoConjuge("");
		cliente.setCepConjuge("");
		cliente.setSalarioConjuge(0.00);
		cliente.setFrequenciaVisitacao("");
		cliente.setFrequenciaSemanalVisitacao(0);
		cliente.setFlagVisitaDomingo(0);
		cliente.setFlagVisitaSegunda(0);
		cliente.setFlagVisitaDomingo(0);
		cliente.setFlagVisitaQuarta(0);
		cliente.setFlagVisitaQuinta(0);
		cliente.setFlagVisitaSexta(0);
		cliente.setFlagVisitaSabado(0);
		cliente.setHoraInicialVisita("");
		cliente.setHoraFinalVisita("");
		cliente.setObservacaoCobranca("");
		cliente.setDiaMesVisitacao(0);
		cliente.setSemanaMesVisitacao("");
		cliente.setSituacao1("");
		cliente.setSituacao2("");
		cliente.setSituacao3("");
		cliente.setSituacao4("");
		cliente.setPta("");
		cliente.setEmailFinanceiro("");

		pessoa.setFantasia("");
		pessoa.setInscricaoEstadual("");
		pessoa.setInscricaoMunicipal("");
		pessoa.setInscricaoProduto("");
		pessoa.setHomePage("");
		pessoa.setAtividadeCodigo(0);
		pessoa.setHistorico("");
		pessoa.setEstadoCivil("");
		pessoa.setClassificacaoClienteCodigo(0);
		pessoa.setCampoAuxiliar(0.00);
		pessoa.setCnaeCodigo(0);
		pessoa.setFlagImprimeFinanceiroNF(0);
		pessoa.setFlagInformaSTNF(0);
		pessoa.setCodigoContabil("");
		pessoa.setRg("");
		pessoa.setOrgaoExpedidorRg("");
		pessoa.setUfRg("");
	}

	/**
	 * Usuar o Gerente Cliente na APi Nefocio Impl
	 * 
	 * @deprecated
	 */
	private void carregarPadraoFinaceiro() {
		/*
		 * Financeiro
		 */
		cliente.setLimiteCredito(clientePadrao.getLimiteCredito());
		cliente.setContaCorrenteCodigo(clientePadrao.getContaCorrenteCodigo());
		cliente.setFlagTaxaBoleta(clientePadrao.getFlagTaxaBoleta());

	}

	/**
	 * Usuar o Gerente Cliente na APi Nefocio Impl
	 * 
	 * @deprecated
	 */
	private void carregarPadraoLogistica() {
		/*
		 * Logistica
		 */
		cliente.setCarteiraClienteInterna(clientePadrao.getCarteiraClienteInterna());
		cliente.setCarteiraClienteExterna(clientePadrao.getCarteiraClienteExterna());
		cliente.setGrupoClienteCodigo(clientePadrao.getGrupoClienteCodigo());
		cliente.setTransportadorCodigo(clientePadrao.getTransportadorCodigo());
		cliente.setRegiao(clientePadrao.getRegiao());
	}

	/**
	 * Usuar o Gerente Cliente na APi Nefocio Impl
	 * 
	 * @deprecated
	 */
	private void carregarPadraoRegrasNegociacao() {
		/*
		 * Regras
		 */
		cliente.setFlagDebitoCredito(clientePadrao.getFlagDebitoCredito());
		cliente.setFlagBloqueioPedidoPendente(clientePadrao.getFlagBloqueioPedidoPendente());
		cliente.setPrazoMaximo(clientePadrao.getPrazoMaximo());
		cliente.setPrazoEspecial(clientePadrao.getPrazoEspecial());
		cliente.setOpcaoEspecialCodigo(clientePadrao.getOpcaoEspecialCodigo());
		cliente.setFlagNaoCalculaIpi(clientePadrao.getFlagNaoCalculaIpi());
		cliente.setTabelaPrecoCodigoGuardian(clientePadrao.getTabelaPrecoCodigo());
		cliente.setTabelaPrecoPromocionalCodigoGuardian(clientePadrao.getTabelaPrecoPromocionalCodigo());
		cliente.setTabelaPrecoPadraoGemini(clientePadrao.getTabelaPrecoPadraoGemini());
		cliente.setNegociacaoPrecoCodigo(clientePadrao.getNegociacaoPrecoCodigo());
		cliente.setDescontoMaximo(clientePadrao.getDescontoMaximo());
		cliente.setAcrescicmoMaximo(clientePadrao.getAcrescimoMaximo());
		cliente.setDescontoEspecial(clientePadrao.getDescontoEspecial());
		cliente.setAcrescimoEspecial(clientePadrao.getAcrescimoEspecial());

		cliente.setCondicaoPagamento(clientePadrao.getCondicaoPagamento());
		cliente.setFormaPagamento(clientePadrao.getFormaPagamento());

		cliente.setCondicaoPagamentoModoCliente(clientePadrao.getCondicaoPagamentoModoCliente());
		cliente.setFormaPagamentoModoCliente(clientePadrao.getFormaPagamentoModoCliente());

		cliente.setFlagInformaOrigemVendaNoXml(clientePadrao.getFlagInformaOrigemVendaNoXml());

		pessoa.setFlagImprimeFinanceiroNF(clientePadrao.getPessoa().getFlagImprimeFinanceiroNF());

	}

	/**
	 * Somente pode carregar a pessoa logada quando for um cliente. Colaborador não
	 * é uma pessoa para o Guardian.
	 * 
	 * Usuar o Gerente Cliente na APi Nefocio Impl
	 * 
	 * @deprecated
	 */
	public void carregarPessoaLogada() {
		clienteExistente = true;
		/*
		 * if (gerenteLogin.isPerfilCliente()) pessoa = Pessoa.recuperar(dao,
		 * gerenteLogin.getClienteCodigo()); else pessoa = Pessoa.recuperar(dao,
		 * gerenteLogin.getColaboradorCodigo());
		 */
		pessoa = Pessoa.recuperar(dao, gerenteLogin.getClienteCodigo());
		cliente = Cliente.recuperarUnico(dao, pessoa.getCodigo());
		enderecos = Enderecos.recuperarEnderecosAtivosPessoa(dao, pessoa.getCodigo());
	}

	/**
	 * 
	 */
	public void confirmarSenha() {
		cliente.setAcessoWeb(1);
	}

	/**
	 * 
	 */
	public void cadastrar() {

		String mensagemCadastroSucesso = "mensagem.cadastro.sucesso";
		String mensagemCadastroFalha = "mensagem.cadastro.falha";

		try {

			boolean emailEnviado = false;

			if (clienteExistente) {
				mensagemCadastroSucesso += ".alteracao";
				mensagemCadastroFalha += ".alteracao";
				emailEnviado = editarCadastro();
			} else {
				emailEnviado = cadastrarNovo();
			}

			String mensagemEnvioEmail = "mensagem.confirmacaoEnviadaEmail";

			if (!emailEnviado) {
				mensagemEnvioEmail += ".falha";
			}

			msgSituacaoCadastro = propriedade.getMensagem(mensagemCadastroSucesso);

			msgSituacaoEnvioConfirmacao = propriedade.getMensagem(mensagemEnvioEmail);

		} catch (Exception e) {

			if (e instanceof SpaceExcecao) {
				MensagemSistema.exibir(TipoMensagem.Erro, propriedade.getMensagem("alerta.ocorreuProblema"),
						e.getMessage());
			} else {
				MensagemSistema.exibir(TipoMensagem.Erro, propriedade.getMensagem("alerta.ocorreuProblema"),
						propriedade.getMensagem(mensagemCadastroFalha));
			}

			msgSituacaoCadastro = null;
			msgSituacaoEnvioConfirmacao = null;
		}
	}

	/**
	 * @return
	 * @throws Exception
	 * 
	 */
	public boolean editarCadastro() throws Exception {

		confirmarEmail();

		try {
			dao.beginTransaction();

			dao.update(pessoa);

			for (int i = 0; i < enderecos.size(); i++) {

				if (i < numeroEnderecosRecuperadosEdicao) {
					dao.update(enderecos.get(i));
				} else {
					enderecos.get(i).setCodigo(
							dao.getRetNumero(Enderecos.class, config.getAtributos().getFilialPadraoCodigo()));
					enderecos.get(i).setPessoa(pessoa);
					enderecos.get(i).setPessoaCodigo(pessoa.getCodigo());
					dao.insert(enderecos.get(i));
				}
			}

			dao.update(cliente);

			dao.commitTransaction();

		} catch (Exception e) {

			dao.rollBackTransaction();

			throw e;
		}

		return enviarEmailConfirmacao();
	}

	/**
	 * @throws Exception
	 * 
	 */
	public boolean cadastrarNovo() throws Exception {

		pessoa.setCodigo(dao.getRetNumero(Pessoa.class, config.getAtributos().getFilialPadraoCodigo()));
		pessoa.setClienteCadastradoViaWeb(1);
		// pessoa.setFlagAtivo(1);
		pessoa.setDataCadastro(new Date());

		cliente.setPessoa(pessoa);
		cliente.setPessoaCodigo(pessoa.getCodigo());
		cliente.setAcessoWeb(0);
		cliente.setFlagClientePrePago(1);

		/*
		 * Valida os dados do cliente novamente para garantir que não houve cadastros
		 * simultâneos.
		 */
		this.validarEMail();
		this.validarCpfCnpj();

		carregarPadraoTabelasPorTipoPessoa();

		/*
		 * Gera a chave de confirmacao do email.
		 */
		confirmarEmail();

		List<FormaPagamentoPessoa> formasPgPessoa = criarFormasPagamentoPessoa();

		List<CondicaoPagamentoPessoa> condicoesPgPessoa = criarCodicaoPagamentoPessoa();

		List<GrupoVendaNaoPermitidoCliente> gruposVendaNaoPermitidos = criarGrupoVendaNaoPermitidos();

		/*
		 * Armazena a senha decriptografaca para caso der algum erro.
		 */
		String senhaDecrip = cliente.getSenhaWeb();

		try {
			cliente.setEmailLoginWeb(cliente.getEmailLoginWeb().toUpperCase());

			cliente.setSenhaWeb(Criptografia.encriptarSenhaEMail(cliente.getSenhaWeb(), cliente.getEmailLoginWeb()));

			dao.beginTransaction();

			dao.insert(pessoa);

			/*
			 * Bairro do cliente inexistente no cadastro.
			 */
			for (Bairro novoBairro : novosBairros) {
				novoBairro
						.setCodigoBairro(dao.getRetNumero(Bairro.class, config.getAtributos().getFilialPadraoCodigo()));
				dao.insert(novoBairro);
			}

			/*
			 * Cidade do cliente inexistente no cadastro
			 */
			for (Cidade novaCidade : novasCidades) {
				novaCidade
						.setCodigoCidade(dao.getRetNumero(Cidade.class, config.getAtributos().getFilialPadraoCodigo()));
				dao.insert(novaCidade);
			}

			for (int i = 0; i < enderecos.size(); i++) {
				enderecos.get(i).setCodigo(i + 1);
				enderecos.get(i).setPessoa(pessoa);
				enderecos.get(i).setPessoaCodigo(pessoa.getCodigo());
				dao.insert(enderecos.get(i));
			}

			for (FormaPagamentoPessoa formaPagamentoPessoa : formasPgPessoa) {
				dao.insert(formaPagamentoPessoa);
			}

			for (CondicaoPagamentoPessoa condicaoPagamentoPessoa : condicoesPgPessoa) {
				dao.insert(condicaoPagamentoPessoa);
			}

			dao.insert(cliente);

			for (GrupoVendaNaoPermitidoCliente grupoVendaNaoPermitidoCliente : gruposVendaNaoPermitidos) {
				dao.insert(grupoVendaNaoPermitidoCliente);
			}

			dao.commitTransaction();

		} catch (Exception e) {
			cliente.setSenhaWeb(senhaDecrip);

			e.printStackTrace();
			dao.rollBackTransaction();
			throw e;
		}

		boolean emailEnviado = false;

		/*
		 * Envia o email fora da transação para evitar da tela ficar aberta se o cliente
		 * tiver sido cadastrado com sucesso.
		 */
		try {
			emailEnviado = enviarEmailConfirmacao();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return emailEnviado;
	}

	private List<GrupoVendaNaoPermitidoCliente> criarGrupoVendaNaoPermitidos() {

		List<GrupoVendaNaoPermitidoCliente> gruposVendaNaoPermitidos = new ArrayList<>();

		List<GrupoVendaNaoPermitidoCliente> gruposVendaNCliente = GrupoVendaNaoPermitidoCliente.recuperar(dao,
				clientePadrao.getCodigo());

		for (GrupoVendaNaoPermitidoCliente grupoVendaNaoPermitidoCliente : gruposVendaNCliente) {
			GrupoVendaNaoPermitidoCliente grupo;
			try {
				grupo = (GrupoVendaNaoPermitidoCliente) grupoVendaNaoPermitidoCliente.clone();
				grupo.getGrupoVendaNaoPermitidoClientePK().setPessoaCodigo(pessoa.getCodigo());
				gruposVendaNaoPermitidos.add(grupo);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return gruposVendaNaoPermitidos;
	}

	private List<CondicaoPagamentoPessoa> criarCodicaoPagamentoPessoa() {

		List<CondicaoPagamentoPessoa> condicoesPgPessoa = new ArrayList<>();
		List<CondicaoPagamentoPessoa> condicoesPagamento = CondicaoPagamentoPessoa.recuperar(dao,
				clientePadrao.getCodigo());

		for (CondicaoPagamentoPessoa condicaoPagamentoPessoa : condicoesPagamento) {
			CondicaoPagamentoPessoa cpg;

			try {
				cpg = (CondicaoPagamentoPessoa) condicaoPagamentoPessoa.clone();
				cpg.getCondicaoPagamentoPessoaPK().setPessoaCodigo(pessoa.getCodigo());
				condicoesPgPessoa.add(cpg);
			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return condicoesPgPessoa;
	}

	private List<FormaPagamentoPessoa> criarFormasPagamentoPessoa() {

		List<FormaPagamentoPessoa> formasPgPessoa = new ArrayList<>();

		List<FormaPagamentoPessoa> formasPagamento = FormaPagamentoPessoa.recuperar(dao, clientePadrao.getCodigo());

		for (FormaPagamentoPessoa formaPagamentoPessoa : formasPagamento) {
			FormaPagamentoPessoa fpg;
			try {
				fpg = (FormaPagamentoPessoa) formaPagamentoPessoa.clone();
				fpg.setPessoaCodigo(pessoa.getCodigo());
				formasPgPessoa.add(fpg);

			} catch (CloneNotSupportedException e) {
				e.printStackTrace();
			}
		}
		return formasPgPessoa;
	}

	/**
	 * 
	 */
	public void confirmarEmail() {
		if (cliente.getAcessoWeb() == 0) {
			String chave = ConfirmacaoEmailMB.gerarConfirmacao(cliente);

			if (chave != null)
				cliente.setChaveConfirmacaoEmail(chave);
			else {
				MensagemSistema.exibir(TipoMensagem.Erro, propriedade.getMensagem("alerta.ocorreuUmErro"),
						propriedade.getMensagem("alerta.ocorreuUmErroConfirmacaoEmail"));
			}

		}
	}

	/**
	 * 
	 */
	public boolean enviarEmailConfirmacao() {
		return enviarEmailConfirmacao(cliente, dao, parametros);
	}

	public static boolean enviarEmailConfirmacao(Cliente cliente, GenericoDAO dao, Parametros parametros) {
		if (cliente.getAcessoWeb() == 0 && cliente.getChaveConfirmacaoEmail() != null) {

			return enviarEmailConfirmacao(cliente.getChaveConfirmacaoEmail(), cliente.getEmailLoginWeb(), dao,
					parametros);
		}
		return false;
	}

	public static boolean enviarEmailConfirmacao(String chaveConfirmacaoEmail, String emailLoginWeb, GenericoDAO dao,
			Parametros parametros) {

		return GerenteEmail.enviarEmailConfirmacao(chaveConfirmacaoEmail, emailLoginWeb, dao, parametros);

	}

	/**
	 * Carrega nova pessoa sem apagar CPF/CNPJ previamente preenchidos.
	 */
	public void carregarNovaPessoa() {
		Pessoa novaPessoa = new Pessoa();
		if (pessoa != null) {
			novaPessoa.setCnpjCpf(pessoa.getCnpjCpf());
			novaPessoa.setTipoPessoa(pessoa.getTipoPessoa());
		} else {
			novaPessoa.setTipoPessoa("F");
		}

		novaPessoa.setFlagAtivo(1);
		pessoa = novaPessoa;
	}

	/**
	 * Valida CPF/CNPJ
	 * 
	 * @return
	 * @throws SpaceExcecao
	 */
	public void validarCpfCnpj() throws SpaceExcecao {
		/*
		 * Valida se é um CPF/CNPJ válido
		 */
		if (pessoa.getTipoPessoa().equals("F")) {
			if (!CPF.Validar(pessoa.getCnpjCpf())) {
				throw new SpaceExcecao(propriedade.getMensagem("alerta.cpf.invalido"));
			}
		} else {
			if (!CNPJ.Validar(pessoa.getCnpjCpf())) {
				throw new SpaceExcecao(propriedade.getMensagem("alerta.cnpj.invalido"));
			}
		}

		/*
		 * Verifica se já existe alguma pessoa cadastrada
		 */
		boolean jaCadastrdoCpfCnpj = Pessoa.isExisteCpfCnpj(dao, pessoa.getCnpjCpf());
		if (jaCadastrdoCpfCnpj) {
			if (pessoa.getTipoPessoa().equals("F")) {
				throw new SpaceExcecao(propriedade.getMensagem("alerta.cpf.jaCadastrado"));
			} else {
				throw new SpaceExcecao(propriedade.getMensagem("alerta.cnpj.jaCadastrado"));
			}
		}

	}

	/**
	 * Apaga os dados que envolvem pessoa física e jurídica do cliente
	 */
	public void apagarDadosTipoPessoa() {
		pessoa.setRazao("");
		pessoa.setFantasia("");

		pessoa.setCnpjCpf("");
		pessoa.setInscricaoEstadual("");
		pessoa.setInscricaoMunicipal("");
		ufInscricaoEstadual = "";

		pessoa.setRg("");
		pessoa.setDataExpedicaoRg(null);
		pessoa.setOrgaoExpedidorRg("");
		pessoa.setUfRg("");

		pessoa.setSexo("");
		pessoa.setAtividadeCodigo(0);
	}

	/**
	 * Apaga os dados inseridos nos campos de endereço.
	 */
	private void carregarNovoEndereco() {
		endereco = new Enderecos();
		bairro = "";
		cidade = "";

		int codigoClassificacaoEnderecoPadrao = parametros.getParametro1().getClassificacaoEnderecoPadraoCodigo();
		int codigoClassificacaoEnderecoCobrancaPadrao = parametros.getParametro1()
				.getClassificacaoEnderecoPadraoCobrancaCodigo();

		ClassificaEndereco classificaEnderecoPadrao = pesquisarClassificaoEndereco(codigoClassificacaoEnderecoPadrao);

		if (classificaEnderecoPadrao != null) {

			endereco.setClassificacaoEnderecoCodigo(codigoClassificacaoEnderecoPadrao);

		} else if (codigoClassificacaoEnderecoCobrancaPadrao != codigoClassificacaoEnderecoPadrao) {

			ClassificaEndereco classificaEnderecoPadraoCobranca = pesquisarClassificaoEndereco(
					codigoClassificacaoEnderecoCobrancaPadrao);

			if (classificaEnderecoPadraoCobranca != null) {
				endereco.setClassificacaoEnderecoCodigo(codigoClassificacaoEnderecoCobrancaPadrao);
			}
		}

		if (endereco.getClassificacaoEnderecoCodigo() == 0 && ListUtil.isValida(classificacoesEndereco)) {
			endereco.setClassificacaoEnderecoCodigo(classificacoesEndereco.get(0).getCodigo());
		}

		/*
		 * Campos não utilizados na tela
		 */
		endereco.setCaixaPostal("");
		endereco.setPaisCodigo(0);
		endereco.setTipoContato("");
		endereco.setTelefone2("");
		endereco.setEmail("");
		endereco.setContato("");
		endereco.setTelefoneCelular("");
		endereco.setFax("");

	}

	/**
	 * 
	 */
	public void adicionarEndereco() {
		/*
		 * Valida os dados obrigatorios do endereco.
		 * 
		 * CEP, Logradouro, Numero, Bairro, Cidade, UF, Telefone
		 */
		try {
			if (!StringUtil.isValida(bairro) || !StringUtil.isValida(cidade) || !StringUtil.isValida(endereco.getCep())
					|| !StringUtil.isValida(endereco.getNumero()) || !StringUtil.isValida(endereco.getLogradouro())
					|| !StringUtil.isValida(endereco.getUfsigla())) {

				MensagemSistema.exibir(TipoMensagem.Erro, propriedade.getMensagem("alerta.ocorreuUmErro"),
						propriedade.getMensagem("alerta.endereco.dados"));
				return;
			}

		} catch (Exception e) {
			return;
		}

		try {

			if (!StringUtil.isValida(endereco.getTelefone1()) && !StringUtil.isValida(endereco.getTelefoneCelular())) {

				MensagemSistema.exibir(TipoMensagem.Erro, propriedade.getMensagem("alerta.ocorreuUmErro"),
						propriedade.getMensagem("alerta.endereco.dados.telefone"));

				return;
			}

		} catch (Exception e) {
			return;
		}

		if (!bairro.isEmpty() && bairro != null) {

			Bairro novoBairro = Bairro.recuperaBairro(dao, bairro);

			if (novoBairro == null) {

				novoBairro = new Bairro();
				novoBairro.setBairroAtivo(1);
				novoBairro.setDescricaoBairro(bairro.toUpperCase());
				novoBairro.setFilialBairro(config.getAtributos().getFilialPadraoCodigo());
				novoBairro.setCodigoBairro(0);
				novosBairros.add(novoBairro);
			}

			endereco.setBairro(novoBairro);
		}

		if (!cidade.isEmpty() && cidade != null) {

			Cidade novaCidade = Cidade.recuperaCidade(dao, cidade);

			if (novaCidade == null) {
				novaCidade = new Cidade();
				novaCidade.setCidadeAtivo(1);
				novaCidade.setDescricaoCidade(cidade.toUpperCase());
				novaCidade.setFilialCidade(config.getAtributos().getFilialPadraoCodigo());
				novaCidade.setCodigoCidade(0);
				novasCidades.add(novaCidade);
			}
			endereco.setCidade(novaCidade);
		}

		endereco.setFlagAtivo(1);
		endereco.setEmail(cliente.getEmailLoginWeb());

		ClassificaEndereco classificaEnderecoAtual = pesquisarClassificaoEndereco(
				endereco.getClassificacaoEnderecoCodigo());

		int codigoClassificacaoEnderecoPadrao = parametros.getParametro1().getClassificacaoEnderecoPadraoCodigo();

		int codigoClassificacaoEnderecoCobrancaPadrao = parametros.getParametro1()
				.getClassificacaoEnderecoPadraoCobrancaCodigo();

		if (classificaEnderecoAtual != null && (classificaEnderecoAtual.isUnico()
				|| (classificaEnderecoAtual.getCodigo() == codigoClassificacaoEnderecoPadrao
						|| classificaEnderecoAtual.getCodigo() == codigoClassificacaoEnderecoCobrancaPadrao))) {

			classificacoesEndereco.remove(classificaEnderecoAtual);
		}

		enderecos.add(endereco);

		carregarNovoEndereco();
	}

	private ClassificaEndereco pesquisarClassificaoEndereco(int classificacaoEnderecoCodigo) {

		for (ClassificaEndereco classificaEndereco : classificacoesEndereco) {
			if (classificaEndereco.getCodigo() == classificacaoEnderecoCodigo) {
				return classificaEndereco;
			}
		}
		return null;
	}

	public DicionarioCampos getDicionarioCamposTelefone1() {
		if (dicionarioCamposTelefone1 == null) {
			dicionarioCamposTelefone1 = DicionarioCampos.recuperarCampoTabela(dao, "enderecos", "end_fone1");
		}
		return dicionarioCamposTelefone1;
	}

	public DicionarioCampos getDicionarioCamposCelular() {

		if (dicionarioCamposCelular == null) {
			dicionarioCamposCelular = DicionarioCampos.recuperarCampoTabela(dao, "enderecos", "end_celular");
		}
		return dicionarioCamposCelular;
	}

	/**
	 * Ao alterar o tipo de pessoa, apaga os dados que pertencem a um tipo
	 * específico.
	 * 
	 * @param event
	 */
	public void cpfCnpjChangeListener(ValueChangeEvent event) {
		apagarDadosTipoPessoa();
		pessoa.setCnpjCpf(event.getNewValue().toString());
	}

	/**
	 * Pesquisa o cep e preenche os campos com os dados dele.
	 * 
	 * @return
	 */
	public String pesquisarCep() {
		PesquisaCep cep = new PesquisaCep();

		if (endereco.getCep() != null && !endereco.getCep().isEmpty()) {
			cep.pesquisarCEP(daoCep, endereco.getCep());

			// apagarDadosCep();

			if (cep.getCepLogradouro() != null)
				endereco.setLogradouro(cep.getCepLogradouro());
			else
				endereco.setLogradouro("");

			if (cep.getCepBairro() != null)
				setBairro(cep.getCepBairro());
			else
				setBairro("");

			if (cep.getCepCidade() != null)
				setCidade(cep.getCepCidade());
			else
				setCidade("");

			if (cep.getCepUF() != null)
				endereco.setUfsigla(cep.getCepUF());
			else
				endereco.setUfsigla("");

			if (cep.getCepReferencia() != null)
				endereco.setPontoReferencia(cep.getCepReferencia());
			else
				endereco.setPontoReferencia("");

		}
		return null;
	}

	/* CÓDIGOS */
	/**
	 * Método responsável por localizar o código do bairro. Caso não exista, retorna
	 * 0.
	 * 
	 * @param event
	 */
	public int carregarCodigoBairro(String descricao) {
		Bairro bairroSelecionado = Bairro.recuperaBairro(dao, descricao.toUpperCase());

		if (bairroSelecionado != null) {
			return bairroSelecionado.getCodigoBairro();
		}

		return 0;
	}

	/**
	 * Metodo responsável por localizar o código da cidade. Caso não exista, retorna
	 * 0.
	 * 
	 * @param event
	 */
	public int carregarCodigoCidade(String descricao) {
		Cidade cidadeSelecionada = Cidade.recuperaCidade(dao, descricao.toUpperCase());
		if (cidadeSelecionada != null) {
			return cidadeSelecionada.getCodigoCidade();
		}
		return 0;
	}

	/**
	 * Retorna os dados de uma inscrição estadual, de acordo com sua UF.
	 * 
	 * @param uf
	 * @return
	 */
	public InscricaoEstadual recuperaInscricaoPorUf(String uf) {
		List<InscricaoEstadual> inscricoes = InscricaoEstadual.getListaInscricaoEstadual();

		for (InscricaoEstadual inscricao : inscricoes) {
			if (inscricao.getUfSigla().toLowerCase().equals(uf.toLowerCase())) {
				pessoa.setInscricaoEstadual("");
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

		for (InscricaoEstadual inscricaoEstadual : InscricaoEstadual.getListaInscricaoEstadual()) {

			if ((inscricaoEstadual != null
					&& inscricaoEstadual.getUfSigla().toLowerCase().indexOf(pref.toLowerCase()) == 0)
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

		for (InscricaoEstadual inscricaoEstadual : InscricaoEstadual.getListaInscricaoEstadual()) {

			if ((inscricaoEstadual != null
					&& inscricaoEstadual.getUfSigla().toLowerCase().indexOf(pref.toLowerCase()) == 0)
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
			if ((atividade.getDescricaoAtividade() != null
					&& atividade.getDescricaoAtividade().toLowerCase().indexOf(pref.toLowerCase()) == 0)
					|| "".equalsIgnoreCase(pref))
				atividades.add(atividade.getDescricaoAtividade());
		}

		return atividades;
	}

	/**
	 * Retorna todas as atividades.
	 * 
	 * @return
	 */
	public List<Atividade> getTodasAtividades() {
		if (todasAtividades == null || todasAtividades.isEmpty())
			todasAtividades = Atividade.recuperaTodasAtividades(dao, "descricaoAtividade");

		return todasAtividades;
	}

	/**
	 * 
	 * @return
	 */
	public List<ClassificaEndereco> getClassificacoesEndereco() {
		return classificacoesEndereco;
	}

	/**
	 * 
	 * @return
	 */
	public String getUfInscricaoEstadual() {
		return ufInscricaoEstadual;
	}

	public void setUfInscricaoEstadual(String ufInscricaoEstadual) {
		this.ufInscricaoEstadual = ufInscricaoEstadual;
	}

	public boolean isInscricaoEstadualIsenta() {
		return inscricaoEstadualIsenta;
	}

	public void setInscricaoEstadualIsenta(boolean inscricaoEstadualIsenta) {
		this.inscricaoEstadualIsenta = inscricaoEstadualIsenta;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Enderecos getEndereco() {
		return endereco;
	}

	public void setEndereco(Enderecos endereco) {
		this.endereco = endereco;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public List<Enderecos> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Enderecos> enderecos) {
		this.enderecos = enderecos;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	public boolean isInscricaoMunicipalIsenta() {
		return inscricaoMunicipalIsenta;
	}

	public void setInscricaoMunicipalIsenta(boolean inscricaoMunicipalIsenta) {
		this.inscricaoMunicipalIsenta = inscricaoMunicipalIsenta;
	}

	public String getMsgSituacaoCadastro() {
		return msgSituacaoCadastro;
	}

	public void setMsgSituacaoCadastro(String msgSituacaoCadastro) {
		this.msgSituacaoCadastro = msgSituacaoCadastro;
	}

	public String getMsgSituacaoEnvioConfirmacao() {
		return msgSituacaoEnvioConfirmacao;
	}

	public void setMsgSituacaoEnvioConfirmacao(String msgSituacaoEnvioConfirmacao) {
		this.msgSituacaoEnvioConfirmacao = msgSituacaoEnvioConfirmacao;
	}

}
