package br.com.space.spacewebII.controle.aplicacao;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;

@ManagedBeanSessionScoped
public class ParametroWebMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GenericoDAO dao;

	@Inject
	private GerenteLogin gerenteLogin;

	private ParametroWeb parametroWeb = null;

	public ParametroWebMB() {
	}

	public ParametroWeb getParametroWeb() {

		if (parametroWeb == null) {

			parametroWeb = ParametroWeb.recuperarUnico(dao,
					gerenteLogin.getFilialCodigo());

		}

		return parametroWeb;
	}

	public boolean isExibeRedeSocial() {
		return gerenteLogin.isPerfilCliente()
				|| (gerenteLogin.isPerfilColaborador() && getParametroWeb()
						.isExibeRedeSocialColaborador());
	}

	public boolean isExibeFacebookPlugin() {
		return getParametroWeb().isFacebookPlugin()
				&& StringUtil.isValida(getParametroWeb().getUsuarioFacebook());
	}

	public boolean isExibeFacebookComentario() {
		return getParametroWeb().isFacebookComentario()
				&& StringUtil.isValida(getParametroWeb().getUsuarioFacebook());
	}

	public boolean isExibeTwitter() {
		return  StringUtil.isValida(getParametroWeb().getUsuarioTwitter());
	}
	
	public boolean isFlagExibePesoProdutoCliente(){
		
		if(gerenteLogin.isPerfilCliente()){
			return getParametroWeb().isFlagExibePesoProduto();
		}else {
			return true;
		}
	}
}
