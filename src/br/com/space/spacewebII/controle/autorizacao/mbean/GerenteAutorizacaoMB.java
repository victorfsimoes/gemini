package br.com.space.spacewebII.controle.autorizacao.mbean;

import java.io.Serializable;

import javax.annotation.PostConstruct;

import org.primefaces.context.RequestContext;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

import br.com.space.api.negocio.modelo.excecao.cliente.ClienteSituacaoIrregularExcecao;
import br.com.space.spacewebII.controle.autorizacao.GerenteAutorizacao;
import br.com.space.spacewebII.controle.padrao.mbean.GerenteMB;
import br.com.space.spacewebII.controle.pedido.mbean.GerentePedidoMB;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;
import br.com.space.spacewebII.modelo.dominio.sistema.Usuario;
import br.com.space.spacewebII.modelo.dominio.venda.Pedido;
import br.com.space.spacewebII.modelo.dominio.venda.PedidoPK;

@ManagedBeanSessionScoped
@URLMappings(mappings = {
		@URLMapping(id = "autorizacaoBarra", pattern = "/autorizacao/", viewId = "/pages/erroAutorizavel.xhtml", onPostback = false),
		@URLMapping(id = "autorizacao", pattern = "/autorizacao", viewId = "/pages/erroAutorizavel.xhtml", onPostback = false) })
public class GerenteAutorizacaoMB extends GerenteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private GerenteAutorizacao gerenteAutorizacao = null;

	private String observacaoSolicitacao = "";
	private String usuarioLiberou = "";

	/**
	 * 
	 */
	public GerenteAutorizacaoMB() {
	}

	/**
	 * @throws ClienteSituacaoIrregularExcecao
	 * 
	 */
	@PostConstruct
	public void inicializarGerente() throws ClienteSituacaoIrregularExcecao {

		if (this.gerenteAutorizacao == null)
			this.gerenteAutorizacao = new GerenteAutorizacao(this.dao, this.gerenteLogin, this.sessaoMB);
		this.observacaoSolicitacao = "";

		this.usuarioLiberou = gerenteLogin.getUserLogin();
	}

	/**
	 * Testes
	 * 
	 * @throws Exception
	 */
	public void forcarTesteValidacao() throws Exception {
		/*
		 * Testes
		 */

		Usuario usuario = new Usuario();
		Filial filial = new Filial();

		usuario.setLogin("MANUTMAC");
		filial.setCodigo(1);

		gerenteLogin.setFilialLogada(filial);
		gerenteLogin.setUsuarioLogado(usuario);

		// sessaoMB.setCodigoSessaoAtiva(123456);

		Pedido pedido = new Pedido(parametroWebMB.getParametroWeb().getAdicionarDiasDataEmissao());

		PedidoPK pedidoPK = new PedidoPK();
		pedidoPK.setFilialCodigo(1);
		pedidoPK.setNumero(123456);
		pedidoPK.setSerieCodigo("1");

		pedido.setPedidoPK(pedidoPK);
		pedido.setPessoaCodigo(221);
		pedido.setValor(10.22);

		Cliente cliente = Cliente.recuperarUnico(dao, 221);

		try {
			gerenteAutorizacao.verificarAutorizacao(new ClienteSituacaoIrregularExcecao(propriedade, pedido, cliente),
					sessaoMB.getCodigoSessaoAtiva());
		} catch (Exception e) {
			exibirMensagemErro("", e.getMessage());
			throw e;
		}
	}

	/**
	 * 
	 * @return
	 */
	public String autorizar() {
		try {
			if (gerenteAutorizacao.verificarPermissaoUsuario(this.usuarioLiberou)) {
				dao.beginTransaction();
				gerenteAutorizacao.confirmarAutorizacao(this.usuarioLiberou, this.observacaoSolicitacao);
				dao.commitTransaction();
			}
			// executarScript(sessaoMB.getComandoVoltarErro());

		} catch (Exception e) {
			dao.rollBackTransaction();
			exibirMensagemErro("", e.getMessage());
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @return
	 */
	public String solicitar() {
		try {
			// sessaoMB.mensagem(TipoMensagem.Info, "", getPropriedade()
			// .getMensagem("mensagem.regra.observacao.SI301"));
			dao.beginTransaction();
			gerenteAutorizacao.solicitarAutorizacao(this.observacaoSolicitacao);
			dao.commitTransaction();

			// executarScript(sessaoMB.getComandoVoltarErro());

		} catch (Exception e) {
			dao.rollBackTransaction();
			exibirMensagemErro("", e.getMessage());
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String cancelar() {
		return null;
	}

	/**
	 * 
	 * @param comando
	 */
	private void executarScript(String comando) {
		// oncomplete="if (args.validationFailed) {} else
		// {#{sessaoMB.comandoVoltarErro}}'}"
		RequestContext context = RequestContext.getCurrentInstance();
		// execute javascript oncomplete
		context.execute(comando);
	}

	/**
	 * 
	 */
	public void exibirPopupAutorizacao() {
		executarScript("pAutorizacao.show();");
	}

	public GerenteAutorizacao getGerenteAutorizacao() {
		return gerenteAutorizacao;
	}

	public void setGerenteAutorizacao(GerenteAutorizacao gerenteAutorizacao) {
		this.gerenteAutorizacao = gerenteAutorizacao;
	}

	public String getObservacao() {
		return this.getGerenteAutorizacao().getObservacao().replace("\n", "<br/>");
	}

	public void setObservacao(String observacao) {
		// Não faz nada.
	}

	public String getDescricao() {
		return this.getGerenteAutorizacao().getDescricao();
	}

	public void setDescricao(String descricao) {
		// Não faz nada;
	}

	public String getObservacaoSolicitacao() {
		return observacaoSolicitacao;
	}

	public void setObservacaoSolicitacao(String observacaoSolicitacao) {
		this.observacaoSolicitacao = observacaoSolicitacao;
	}

	/**
	 * 
	 * @return
	 */
	public boolean getFlagPermiteAutorizar() {
		return this.getGerenteAutorizacao().getFlagPermiteAutorizar();
	}

	/**
	 * 
	 * @return
	 */
	public boolean getFlagPermiteSolicitar() {
		return this.getGerenteAutorizacao().getFlagPermiteSolicitar();
	}

	@Override
	public String getNomePrograma() {

		return null;
	}

	@Override
	public String getUrlView() {
		return getUrlViewPattern();
	}

	@Override
	protected String getUrlViewPage() {
		return "/pages/erroAutorizavel.xhtml";
	}

	@Override
	protected String getUrlViewPattern() {
		return "/autorizacao";
	}

	@Override
	public boolean necessarioLogin() {
		return true;
	}

	@Override
	public boolean permiteAcessoDeCliente() {
		return false;
	}

	public boolean isPermiteAutorizar() {
		// Veriica se o a mensagem é nula e se tem o codigo da permissão
		boolean temCodigoPermissao = gerenteAutorizacao.getMensagem() != null
				&& gerenteAutorizacao.getMensagem().getPermissaoCodigo() != null
				&& gerenteAutorizacao.getMensagem().getPermissaoCodigo() != 0;

		return temCodigoPermissao && gerentePermissao.verificarPermissao(GerentePedidoMB.NOME_PROGRAMA,
				gerenteAutorizacao.getMensagem().getPermissaoCodigo());
	}
}
