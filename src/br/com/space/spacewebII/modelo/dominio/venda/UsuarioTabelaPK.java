package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class UsuarioTabelaPK implements Serializable {

		private static final long serialVersionUID = 1L;
		public final static String COLUNA_USUARIO_LOGIN = "utp_usrlogin";
		public static final String COLUNA_TABELA_CODIGO = "utp_tprcodigo";
		
		@Column(name = COLUNA_TABELA_CODIGO)
		private int codigoTabela = 0;

		@Column(name = COLUNA_USUARIO_LOGIN, length = 3)
		private String usuarioLogin = null;

		public int getCodigoTabela() {
			return codigoTabela;
		}

		public void setCodigoTabela(int codigoTabela) {
			this.codigoTabela = codigoTabela;
		}

		public String getUsuarioLogin() {
			return usuarioLogin;
		}

		public void setUsuarioLogin(String usuarioLogin) {
			this.usuarioLogin = usuarioLogin;
		}

}
	

