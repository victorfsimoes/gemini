package br.com.space.spacewebII.modelo.util.teste;

import java.io.Serializable;

import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;

@ManagedBeanSessionScoped
public class ParametrosTesteMB implements Serializable {
	private static final long serialVersionUID = 1L;

	private boolean permiteCompraEstoqueVazio = false;
	
	

	public boolean getPermiteCompraEstoqueVazio() {
		return permiteCompraEstoqueVazio;
	}

	public void setPermiteCompraEstoqueVazio(boolean permiteCompraEstoqueVazio) {
		this.permiteCompraEstoqueVazio = permiteCompraEstoqueVazio;
	}
	
	

}
