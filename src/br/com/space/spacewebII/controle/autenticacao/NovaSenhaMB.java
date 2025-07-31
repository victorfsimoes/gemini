package br.com.space.spacewebII.controle.autenticacao;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

import javax.inject.Inject;

import org.jboss.seam.security.Credentials;

import br.com.space.api.core.sistema.Criptografia;
import br.com.space.api.core.sistema.MD5;
import br.com.space.spacewebII.controle.aplicacao.GerenteEmail;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

import com.ocpsoft.pretty.faces.annotation.URLAction;
import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBeanSessionScoped
@URLBeanName(value = "novaSenhaMB")
@URLMapping(id = "novaSenha", pattern = "/novaSenha/#{novaSenhaMB.requisicao}/", viewId = "/pages/novaSenha.xhtml")
public class NovaSenhaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GenericoDAO dao;

	@Inject
	private Credentials credentials;

	@Inject
	private Parametros parametros;

	@Inject
	private Propriedade propriedade;

	private String requisicao = null;

	private Cliente usuario = null;
	private String novaSenha = null;

	public NovaSenhaMB() {
	}

	/**
	 * Verifica se a requisição: * Existe * É única
	 */
	@URLAction(mappingId = "novaSenha")
	public boolean verificarRequisicao() {
		if (requisicao != null) {

			List<Cliente> usuario = Cliente.recuperarClienteNovaSenha(dao,
					requisicao);

			if (usuario.size() == 1) {
				setUsuario(usuario.get(0));
				return true;
			}

		}

		setUsuario(null);
		return false;
	}

	/**
	 * Gera a requisição para nova senha e envia um email para o cliente.
	 */
	public void gerarRequisicaoNovaSenha() {

		if (credentials.getUsername() == null
				|| credentials.getUsername().isEmpty()) {
			MensagemSistema.exibir(TipoMensagem.Alerta, null,
					propriedade.getMensagem("alerta.digiteLogin"));
			return;
		}

		List<Cliente> cliLista = Cliente.recuperarCliente(dao,
				credentials.getUsername());
		if (cliLista == null || cliLista.isEmpty()) {
			MensagemSistema.exibir(TipoMensagem.Erro, null,
					propriedade.getMensagem("alerta.loginInvalido"));
			return;
		}

		Cliente usuario = cliLista.get(0);
		String chave = null;

		try {
			chave = MD5.getMd5String(usuario.getEmailLoginWeb()
					+ UUID.randomUUID().toString());
			usuario.setChaveNovaSenha(chave);
		} catch (Exception e1) {
			e1.printStackTrace();
			MensagemSistema.exibir(TipoMensagem.Erro,
					propriedade.getMensagem("alerta.ocorreuUmErro"),
					propriedade.getMensagem("alerta.tenteNovamenteMaisTarde"));
			chave = null;
		}

		if (chave != null) {
			try {
				dao.beginTransaction();
				dao.update(usuario);

				boolean emailEnviado = GerenteEmail.enviarEmailNovaSenha(chave,
						credentials.getUsername(), dao, parametros);

				if (emailEnviado) {
					dao.commitTransaction();

					MensagemSistema
							.exibir(TipoMensagem.Info,
									null,
									propriedade
											.getMensagem("alerta.solicitacaoSenhaEnviadaEmail"));
				} else {
					MensagemSistema
							.exibir(TipoMensagem.Erro,
									null,
									propriedade
											.getMensagem("alerta.falha.solicitacaoSenhaEnviadaEmail"));
				}

			} catch (ClassNotFoundException e) {
				MensagemSistema.exibir(TipoMensagem.Erro, propriedade
						.getMensagem("alerta.ocorreuUmErro"), propriedade
						.getMensagem("alerta.tenteNovamenteMaisTarde"));
				dao.rollBackTransaction();
				e.printStackTrace();
			} catch (SQLException e) {
				MensagemSistema.exibir(TipoMensagem.Erro, propriedade
						.getMensagem("alerta.ocorreuUmErro"), propriedade
						.getMensagem("alerta.tenteNovamenteMaisTarde"));
				dao.rollBackTransaction();
				e.printStackTrace();
			} catch (Exception e) {
				MensagemSistema.exibir(TipoMensagem.Erro, propriedade
						.getMensagem("alerta.ocorreuUmErro"), propriedade
						.getMensagem("alerta.tenteNovamenteMaisTarde"));

			}
		}
	}

	/**
	 * Atualiza a senha no banco.
	 * 
	 * @param dao
	 * @param novaSenha
	 * @return url da próxima página
	 */
	public void atualizarNovaSenha() {

		try {
			dao.beginTransaction();
			usuario.setSenhaWeb(Criptografia.encriptarSenhaEMail(novaSenha,
					usuario.getEmailLoginWeb()));
			usuario.setChaveNovaSenha(null);
			dao.update(usuario);
			dao.commitTransaction();

			MensagemSistema.exibir(TipoMensagem.Info, null,
					propriedade.getMensagem("alerta.senhaAlterada"));

			return;

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

		dao.rollBackTransaction();
		MensagemSistema.exibir(TipoMensagem.Erro,
				propriedade.getMensagem("alerta.ocorreuUmErro"),
				propriedade.getMensagem("alerta.tenteNovamenteMaisTarde"));

	}

	/* GETS E SETS */

	public String getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(String requisicao) {
		this.requisicao = requisicao;
	}

	public Cliente getUsuario() {
		return usuario;
	}

	public void setUsuario(Cliente usuario) {
		this.usuario = usuario;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}
}
