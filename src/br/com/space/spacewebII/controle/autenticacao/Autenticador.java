package br.com.space.spacewebII.controle.autenticacao;

import java.io.Serializable;

import javax.inject.Inject;

import org.jboss.seam.security.Authenticator;
import org.jboss.seam.security.BaseAuthenticator;
import org.jboss.seam.security.Credentials;
import org.picketlink.idm.api.User;
import org.picketlink.idm.impl.api.PasswordCredential;

import br.com.space.api.core.sistema.Criptografia;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.controle.aplicacao.SessaoMB;
import br.com.space.spacewebII.controle.cliente.mbean.GerenteClienteMB;
import br.com.space.spacewebII.controle.permissao.GerentePermissao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Parametros;
import br.com.space.spacewebII.modelo.dominio.sistema.Usuario;
import br.com.space.spacewebII.modelo.dominio.sistema.UsuarioCliente;
import br.com.space.spacewebII.modelo.padrao.interfaces.UsuarioWeb;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

public class Autenticador extends BaseAuthenticator implements Serializable, Authenticator {

	/*
	 * <security:authenticatorClass>br.com.space.spacewebII.controle.
	 * autenticacao .Autenticador </security:authenticatorClass>
	 * <security:authenticatorName>autenticador</security:authenticatorName>
	 */

	private static final long serialVersionUID = 1L;

	@Inject
	private SessaoMB sessaoMB;

	@Inject
	private Credentials credentials;

	@Inject
	private GerenteLogin gerenteLogin;

	@Inject
	private GenericoDAO dao;

	@Inject
	private Propriedade propriedade;

	@Inject
	private GerentePermissao gerentePermissao;

	@Inject
	private Parametros parametros;

	public Autenticador() {
		super();
	}

	// colocar mensagem de usuário não encontrado no perfil.

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.seam.security.Authenticator#authenticate()
	 */
	@Override
	public void authenticate() {
		String mensagem = null;

		if (credentials != null) {
			setStatus(AuthenticationStatus.DEFERRED);

			String userLogin = credentials.getUsername().toUpperCase();

			String senha = ((PasswordCredential) credentials.getCredential()).getValue();

			UsuarioWeb usuarioLogado = null;
			/*
			 * Se for cliente, criptografa a senha diferente, pois usada parte
			 * do email.
			 */
			if (gerenteLogin.isPerfilSelecionadoCliente()) {
				senha = Criptografia.encriptarSenhaEMail(senha, userLogin);

				usuarioLogado = UsuarioCliente.recuperarUsuarioLogin(dao, userLogin, senha,
						parametros.getParametroWeb().isFlagExibeFantasiaCliente());
			} else {
				senha = Criptografia.encriptarSenha(senha, userLogin);

				usuarioLogado = Usuario.recuperarUsuarioLogin(dao, userLogin, senha);
			}

			if (usuarioLogado != null) {

				if (usuarioLogado instanceof UsuarioCliente) {

					UsuarioCliente uc = (UsuarioCliente) usuarioLogado;

					if (StringUtil.isValida(uc.getChaveConfirmacaoEmail()) || uc.getAcessoWeb() == 0) {

						try {

							boolean emailEnviado = GerenteClienteMB.enviarEmailConfirmacao(
									uc.getChaveConfirmacaoEmail(), uc.getLogin(), dao, parametros);

							if (emailEnviado) {
								mensagem = propriedade.getMensagem("alerta.email.pendenteConfirmacao");
							} else {
								mensagem = propriedade.getMensagem("alerta.email.pendenteConfirmacao.falhareenvio");
							}

						} catch (Exception e) {
							e.printStackTrace();

							mensagem = propriedade.getMensagem("alerta.email.pendenteConfirmacao.falhareenvio");
						}

						usuarioLogado = null;
					}
				}

				if (usuarioLogado == null) {

					setStatus(AuthenticationStatus.FAILURE);

				} else {
					setStatus(AuthenticationStatus.SUCCESS);

					setUser(usuarioLogado);

					gerentePermissao.carregarListaPermissoes();

					mensagem = propriedade.getMensagem("mensagem.bemvindo");
				}

			} else {

				mensagem = propriedade.getMensagem("mensagem.loginOuSenhaIncorretos");

				setStatus(AuthenticationStatus.FAILURE);
			}

		} else {
			mensagem = propriedade.getMensagem("mensagem.loginOuSenhaIncorretos");

			setStatus(AuthenticationStatus.FAILURE);
		}

		if (mensagem != null) {
			sessaoMB.mensagem(TipoMensagem.Info, mensagem, "");
		}
	}

	/*
	 * 
	 * (non-Javadoc)
	 * 
	 * @see org.jboss.seam.security.BaseAuthenticator#getUser()
	 */
	@Override
	public UsuarioWeb getUser() {
		return (UsuarioWeb) super.getUser();
	}

	@Override
	public void setUser(User user) {
		super.setUser(user);

		gerenteLogin.setUsuarioLogado((UsuarioWeb) user);
	}

	@Override
	public void setStatus(AuthenticationStatus status) {
		super.setStatus(status);
		gerenteLogin.setAuthenticationStatus(status);
	}
}
