package br.com.space.spacewebII.controle.cliente.mbean;

import java.io.Serializable;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import br.com.space.api.core.sistema.MD5;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

/**
 * 
 * @author Marina
 * 
 */
@ManagedBeanSessionScoped
@URLBeanName(value = "confirmacaoEmailMB")
@URLMapping(id = "confirmacaoEmail", pattern = "/cliente/#{confirmacaoEmailMB.codigoConfirmacao}/", viewId = "/pages/login.xhtml")
public class ConfirmacaoEmailMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GenericoDAO dao;

	@Inject
	private Propriedade propriedade;

	private String codigoConfirmacao = null;

	/*
	 * private Cliente usuario = null; private String novaSenha = null;
	 */

	public ConfirmacaoEmailMB() {
	}

	/**
	 * Verifica se a requisição: * Existe * É única
	 */
	@URLAction(mappingId = "confirmacaoEmail")
	public void verificarRequisicao() {
		if (codigoConfirmacao != null) {

			List<Cliente> clientesChave = Cliente
					.recuperarChaveConfirmacaoEmailCliente(dao,
							codigoConfirmacao);

			if (clientesChave != null) {
				confirmarEmail(clientesChave.get(0));
			}
		}
	}

	/**
	 * Gera a requisição para nova senha e envia um email para o cliente.
	 */
	public static String gerarConfirmacao(Cliente cliente) {

		String chave = null;

		try {
			chave = MD5.getMd5String(cliente.getEmailLoginWeb()
					+ UUID.randomUUID().toString());
			return chave;
		} catch (Exception e1) {
			e1.printStackTrace();
			return null;
		}
	}

	/**
	 * Atualiza a senha no banco.
	 * 
	 * @param dao
	 * @param novaSenha
	 * @return url da próxima página
	 */
	public void confirmarEmail(Cliente cliente) {

		try {
			dao.beginTransaction();

			cliente.setAcessoWeb(1);
			cliente.setChaveConfirmacaoEmail(null);

			dao.update(cliente);
			dao.commitTransaction();

			MensagemSistema.exibir(TipoMensagem.Info, "Bem-vindo!",
					"Realize login com o email " + cliente.getEmailLoginWeb());

		} catch (Exception e) {
			dao.rollBackTransaction();
			MensagemSistema.exibir(TipoMensagem.Erro,
					propriedade.getMensagem("alerta.ocorreuUmErro"),
					propriedade.getMensagem("alerta.tenteNovamenteMaisTarde"));
		}

	}

	/* GETS E SETS */

	public String getCodigoConfirmacao() {
		return codigoConfirmacao;
	}

	public void setCodigoConfirmacao(String codigoConfirmacao) {
		this.codigoConfirmacao = codigoConfirmacao;
	}
}
