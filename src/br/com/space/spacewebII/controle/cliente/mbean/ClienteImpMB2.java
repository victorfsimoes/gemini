package br.com.space.spacewebII.controle.cliente.mbean;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.faces.event.ValueChangeEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.space.api.core.modelo.validacao.CNPJ;
import br.com.space.api.core.modelo.validacao.CPF;
import br.com.space.api.core.modelo.validacao.Email;
import br.com.space.api.core.modelo.validacao.ie.IE;
import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.controle.endereco.PesquisaCep;
import br.com.space.spacewebII.controle.padrao.mbean.GerenteCadastroMB;
import br.com.space.spacewebII.modelo.dominio.cliente.Atividade;
import br.com.space.spacewebII.modelo.dominio.cliente.ClienteImp;
import br.com.space.spacewebII.modelo.dominio.endereco.Bairro;
import br.com.space.spacewebII.modelo.dominio.endereco.Cidade;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroAcessoExclusivoExcecao;
import br.com.space.spacewebII.modelo.excecao.travamento.RegistroTravamentoExcecao;
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
@Named("clienteImpMB")
public class ClienteImpMB2 extends GerenteCadastroMB<ClienteImp> {

	private static final long serialVersionUID = 1L;
	private static final String NOME_PROGRAMA = "FRMIMPCLIVIKING";

	@Inject
	GerenteLogin autenticador;

	private String ufInscricaoEstadual = "";
	private List<Atividade> todasAtividades = null;
	private boolean inscricaoEstadualIsenta = false;

	public ClienteImpMB2() {
		super(ClienteImp.class);
	}

	@Override
	public String carregarNovo() {
		ufInscricaoEstadual = "";

		/*
		 * operacao = Operacao.INCLUIR; objetoSelecionado = new ClienteImp();
		 * 
		 * return carregarView();
		 */
		return super.carregarNovo();
	}

	@Override
	public boolean travarRegistro() {
		try {
			sessaoMB.travarRegistro(objetoSelecionado,
					sessaoMB.getCodigoSessaoAtiva(), NOME_PROGRAMA, true);
		} catch (RegistroAcessoExclusivoExcecao e) {
			return false;
		} catch (RegistroTravamentoExcecao e) {
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public void destravarRegistro() {
		try {
			sessaoMB.destravarRegistro(objetoSelecionado,
					sessaoMB.getCodigoSessaoAtiva(), NOME_PROGRAMA, true);
		} catch (RegistroAcessoExclusivoExcecao e) {
			e.printStackTrace();
		} catch (RegistroTravamentoExcecao e) {
			e.printStackTrace();
		}
	}

	@Override
	public String confirmar() {

		if (inscricaoEstadualIsenta) {
			objetoSelecionado.setInscricaoEstadual("Isento");
			ufInscricaoEstadual = "";
		}

		if (objetoSelecionado.getDescricaoAtividade() != null
				&& !objetoSelecionado.getDescricaoAtividade().isEmpty()) {
			objetoSelecionado.setCodigoAtividade(carregarCodigoAtividade());
		}

		if (objetoSelecionado.getDescricaoBairro() != null
				&& !objetoSelecionado.getDescricaoBairro().isEmpty()) {
			objetoSelecionado.setCodigoBairro(carregarCodigoBairro());
		}

		if (objetoSelecionado.getDescricaoCidade() != null
				&& !objetoSelecionado.getDescricaoCidade().isEmpty()) {
			objetoSelecionado.setCodigoCidade(carregarCodigoCidade());
		}

		if (operacao == 1) {
			objetoSelecionado.setCodigo(dao.getkeyHierarchy(
					objetoSelecionado.getClass(), "codigo"));

			if (autenticador.getCodigoFilialSelecionada() != null)
				objetoSelecionado.setCodigoFilial(autenticador
						.getCodigoFilialSelecionada());

			if (autenticador.getColaboradorCodigo() != null)
				objetoSelecionado.setCodigoColaborador(autenticador
						.getColaboradorCodigo());

			objetoSelecionado.setStatus("N");

			Date dataPreCadastro = new Date();
			objetoSelecionado.setDataPreCadastro(dataPreCadastro);
			objetoSelecionado.setHoraPreCadastro(Conversao.formatarData(
					dataPreCadastro, Conversao.FORMATO_HORA));
		}

		return super.confirmar();
	}

	@Override
	public String validarObjetoIncluirEditar() {
		StringBuilder mensagem = new StringBuilder();
		if (objetoSelecionado.getInscricaoEstadual() != null
				&& ufInscricaoEstadual != null
				&& objetoSelecionado.getTipoPessoa().equals("J")) {

			if (!IE.Validar(objetoSelecionado.getInscricaoEstadual(),
					ufInscricaoEstadual))
				mensagem.append("<br/> Inscrição estadual inválida.");
		}

		if (objetoSelecionado.getCnpjCpf() != null) {
			if (objetoSelecionado.getTipoPessoa().equals("F")) {
				if (!CPF.Validar(objetoSelecionado.getCnpjCpf()))
					mensagem.append("<br/> CPF inválido.");
			} else {
				if (!CNPJ.Validar(objetoSelecionado.getCnpjCpf()))
					mensagem.append("<br/> CNPJ inválido.");
			}
		}

		if (objetoSelecionado.getEmail() != null) {
			if (!Email.Validar(objetoSelecionado.getEmail())) {
				mensagem.append("<br/> E-mail inválido.");
			}
		}

		return mensagem.toString();
	}

	/**
	 * Apaga os dados que envolvem pessoa física e jurídica do cliente
	 */
	public void apagarDadosTipoPessoa() {
		objetoSelecionado.setCnpjCpf(null);
		objetoSelecionado.setRg(null);
		objetoSelecionado.setRazao(null);
		objetoSelecionado.setNomeFantasia(null);
		objetoSelecionado.setInscricaoEstadual(null);
		ufInscricaoEstadual = "";
	}

	/**
	 * Apaga os dados inseridos nos campos de endereço.
	 */
	public void apagarDadosCep() {
		objetoSelecionado.setLogradouro(null);
		objetoSelecionado.setDescricaoBairro(null);
		objetoSelecionado.setDescricaoCidade(null);
		objetoSelecionado.setUfSigla(null);
		objetoSelecionado.setPontoreferencia(null);
		objetoSelecionado.setNumero(null);
	}

	/**
	 * Ao alterar o tipo de pessoa, apaga os dados que pertencem a um tipo
	 * específico.
	 * 
	 * @param event
	 */
	public void cpfCnpjChangeListener(ValueChangeEvent event) {
		apagarDadosTipoPessoa();
		this.objetoSelecionado.setCnpjCpf(event.getNewValue().toString());
	}

	/**
	 * Pesquisa o cep e preenche os campos com os dados dele.
	 * 
	 * @return
	 */
	public String pesquisarCep() {
		PesquisaCep cep = new PesquisaCep();

		apagarDadosCep();

		if (objetoSelecionado.getCep() != null
				&& !objetoSelecionado.getCep().isEmpty()) {

			cep.pesquisarCEP(daoCep, objetoSelecionado.getCep());

			if (cep.getCepLogradouro() != null
					&& !cep.getCepLogradouro().isEmpty())
				objetoSelecionado.setLogradouro(cep.getCepLogradouro());

			if (cep.getCepBairro() != null && !cep.getCepBairro().isEmpty())
				objetoSelecionado.setDescricaoBairro(cep.getCepBairro());

			if (cep.getCepCidade() != null && !cep.getCepCidade().isEmpty())
				objetoSelecionado.setDescricaoCidade(cep.getCepCidade());

			if (cep.getCepUF() != null && !cep.getCepUF().isEmpty())
				objetoSelecionado.setUfSigla(cep.getCepUF());

			if (cep.getCepReferencia() != null
					&& !cep.getCepReferencia().isEmpty())
				objetoSelecionado.setPontoreferencia(cep.getCepReferencia());

		}
		return null;
	}

	/* CÓDIGOS */
	/**
	 * Método responsável por localizar o código do bairro. Caso não exista,
	 * retorna 0.
	 * 
	 * @param event
	 */

	public int carregarCodigoBairro() {

		Bairro bairroSelecionado = Bairro.recuperaBairro(dao, objetoSelecionado
				.getDescricaoBairro().toUpperCase());

		if (bairroSelecionado != null) {
			return bairroSelecionado.getCodigoBairro();
		}

		return 0;

	}

	/**
	 * Metodo responsável por localizar o código da cidade. Caso não exista,
	 * retorna 0.
	 * 
	 * @param event
	 */
	public int carregarCodigoCidade() {

		Cidade cidadeSelecionada = Cidade.recuperaCidade(dao, objetoSelecionado
				.getDescricaoCidade().toUpperCase());

		if (cidadeSelecionada != null) {
			return cidadeSelecionada.getCodigoCidade();
		}

		return 0;

	}

	/**
	 * Método responsável por localizar o código da atividade. Caso não exista,
	 * retorna 0.
	 * 
	 * @param event
	 */
	public Integer carregarCodigoAtividade() {

		Atividade atividadeSelecionada = Atividade.recuperaAtividade(dao,
				objetoSelecionado.getDescricaoAtividade());

		if (atividadeSelecionada != null) {
			return atividadeSelecionada.getCodigoAtividade();
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
		List<InscricaoEstadual> inscricoes = InscricaoEstadual
				.getListaInscricaoEstadual();

		for (InscricaoEstadual inscricao : inscricoes) {
			if (inscricao.getUfSigla().toLowerCase().equals(uf.toLowerCase())) {
				objetoSelecionado.setInscricaoEstadual("");
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

	/**
	 * Retorna todas as atividades.
	 * 
	 * @return
	 */
	public List<Atividade> getTodasAtividades() {
		if (todasAtividades == null || todasAtividades.isEmpty())
			todasAtividades = Atividade.recuperaTodasAtividades(dao,
					"descricaoAtividade");

		return todasAtividades;
	}

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

	@Override
	public String getNomePrograma() {
		return NOME_PROGRAMA;
	}

	@Override
	public String getUrlView() {
		return getUrlViewPage();
	}

	@Override
	protected String getUrlViewPage() {

		return "/pages/cadastroPassos.xhtml";
	}

	@Override
	protected String getUrlViewPattern() {

		return null;
	}

	public String getTituloView() {
		return "Pré-cadastro Cliente Imp";
	}

	@Override
	public void validarObjetoExcluir(ClienteImp t) throws SpaceExcecao {
	}

	@Override
	public boolean necessarioLogin() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean permiteAcessoDeCliente() {
		// TODO Auto-generated method stub
		return false;
	}
}
