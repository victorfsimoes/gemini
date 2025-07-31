package br.com.space.spacewebII.controle.autenticacao;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;

@ManagedBeanSessionScoped
public class PerfilCadastroMB implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Inject
	GerenteLogin gerenteLogin;
	
	public PerfilCadastroMB() {
		// TODO Auto-generated constructor stub
	}
	
	public void preencherDadosUsuario(){
		//gerenteLogin.getUsuarioLogado()
	}
	

}
