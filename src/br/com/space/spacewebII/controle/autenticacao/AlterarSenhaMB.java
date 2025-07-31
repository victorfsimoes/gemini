package br.com.space.spacewebII.controle.autenticacao;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.space.api.core.sistema.Criptografia;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

@ManagedBeanSessionScoped
public class AlterarSenhaMB implements Serializable {

	private static final long serialVersionUID = 1L;

	private String senhaAtual;
	private String novaSenha;

	@Inject
	private Propriedade propriedade;

	@Inject
	private GenericoDAO dao;

	@Inject
	private GerenteLogin login;

	public AlterarSenhaMB() {
	}

	/**
	 * 
	 */
	public void alterarSenha() {

		String senhaCriptografada = login.getUsuarioLogado().getLogin();

		if (login.isPerfilCliente())
			senhaCriptografada += Criptografia.encriptarSenhaEMail(senhaAtual,
					login.getUsuarioLogado().getLogin());
		else
			senhaCriptografada += Criptografia.encriptarSenha(senhaAtual, login
					.getUsuarioLogado().getLogin());

		if (senhaCriptografada.equals(login.getUsuarioLogado().getKey())) {

			int codigo = login.isPerfilCliente() ? login.getClienteCodigo()
					: login.getColaboradorCodigo();

			Cliente usuario = Cliente.recuperarUnico(dao, codigo);

			if (login.isPerfilCliente())
				usuario.setSenhaWeb(Criptografia.encriptarSenhaEMail(novaSenha,
						login.getUsuarioLogado().getLogin()));
			else
				usuario.setSenhaWeb(Criptografia.encriptarSenha(novaSenha,
						login.getUsuarioLogado().getLogin()));

			try {
				dao.beginTransaction();
				dao.update(usuario);
				dao.commitTransaction();

				MensagemSistema.exibir(TipoMensagem.Info, null,
						propriedade.getMensagem("alerta.senhaAlterada"));

			} catch (Exception e) {
				dao.rollBackTransaction();
				MensagemSistema.exibir(TipoMensagem.Erro, propriedade
						.getMensagem("alerta.ocorreuUmErro"), propriedade
						.getMensagem("alerta.tenteNovamenteMaisTarde"));
				e.printStackTrace();
			}
		} else {
			MensagemSistema.exibir(TipoMensagem.Alerta, null,
					propriedade.getMensagem("alerta.senhaDiferenteAtual"));
		}
	}

	public String getSenhaAtual() {
		return senhaAtual;
	}

	public void setSenhaAtual(String senhaAtual) {
		this.senhaAtual = senhaAtual;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

}
