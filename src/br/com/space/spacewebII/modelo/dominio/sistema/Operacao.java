package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * Interface com o banco de dados responsável pelas operações realizadas pelo
 * usuário: Edição, Exclusão, Inclusão, Visualização, Vínculo e Desvínculo de
 * Clientes-Usuários.
 * 
 * @author Desenvolvimento
 * 
 */
@Entity(name = "operacao")
@Table(name = "operacao")
public class Operacao implements IPersistent, Serializable {
	private static final long serialVersionUID = 1L;

	public static final int NENHUMA = 0;
	public static final int INCLUIR = 1;
	public static final int EDITAR = 2;
	public static final int EXCLUIR = 3;
	public static final int VISUALIZAR = 4;
	public static final int VINCULAR_DESVINCULAR_CLIENTEUSUARIO = 5;

	public static final String COLUNA_CODIGO = "ope_codigo";
	public static final String COLUNA_OPCAO = "ope_opcao";

	@Id
	@Column(name = COLUNA_CODIGO)
	private int codigo = 0;

	@Column(name = COLUNA_OPCAO)
	private String opcao;

	public Operacao() {

	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getOpcao() {
		return opcao;
	}

	public void setOpcao(String opcao) {
		this.opcao = opcao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.spa.model.domain.IPersistent#getTable()
	 */
	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.api.spa.model.domain.IPersistent#setTable(br.com.space.api
	 * .spa.model.dao.db.Table)
	 */
	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Operacao clone() throws CloneNotSupportedException {
		return (Operacao) super.clone();
	}
}
