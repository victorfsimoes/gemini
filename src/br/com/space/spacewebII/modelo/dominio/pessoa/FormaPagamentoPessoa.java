/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.negocio.modelo.dominio.IFormaPagamentoPessoa;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity(name = "formapgpessoa")
@Table(name = "formapgpessoa")
@SpaceTable(name = "formapgpessoa")
public class FormaPagamentoPessoa implements IPersistent, Serializable,
		IFormaPagamentoPessoa {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static br.com.space.api.spa.model.dao.db.Table table = null;

	public static final String COLUNA_FORMA_PAGAMENTO_CODIGO = "fpe_fpgcodigo";
	public static final String COLUNA_PESSOA_CODIGO = "fpe_pescodigo";

	@EmbeddedId
	private FormaPagamentoPessoaPK formaPagamentoPessoaPK;

	/**
	 * 
	 */
	public FormaPagamentoPessoa() {
		super();
	}

	public FormaPagamentoPessoa(int pessoaCodigo, String formaPagamentoCodigo) {
		super();
		this.getFormaPagamentoPessoaPK().setPessoaCodigo(pessoaCodigo);
		this.getFormaPagamentoPessoaPK().setFormaPagamentoCodigo(
				formaPagamentoCodigo);

	}

	public int getPessoaCodigo() {
		return getFormaPagamentoPessoaPK().getPessoaCodigo();

	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.getFormaPagamentoPessoaPK().setPessoaCodigo(pessoaCodigo);
	}

	public String getFormaPagamentoCodigo() {
		return getFormaPagamentoPessoaPK().getFormaPagamentoCodigo();

	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		this.getFormaPagamentoPessoaPK().setFormaPagamentoCodigo(
				formaPagamentoCodigo);
	}

	/**
	 * 
	 * @return
	 */
	public FormaPagamentoPessoaPK getFormaPagamentoPessoaPK() {
		if (this.formaPagamentoPessoaPK == null) {
			this.formaPagamentoPessoaPK = new FormaPagamentoPessoaPK();
		}
		return formaPagamentoPessoaPK;
	}

	/**
	 * 
	 * @param formaPagamentoPessoaPK
	 */
	public void setformaPagamentoPessoaPK(
			FormaPagamentoPessoaPK formaPagamentoPessoaPK) {
		this.formaPagamentoPessoaPK = formaPagamentoPessoaPK;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return table;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		// FormaPagamentoPessoa.table = table;

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	/**
	 * 
	 * @param dao
	 * @param pessoaCodigo
	 * @param formaPagamentoCodigo
	 * @return
	 */
	public static FormaPagamentoPessoa recuperarUnico(GenericoDAO dao,
			int pessoaCodigo, String formaPagamentoCodigo) {

		return dao.uniqueResult(FormaPagamentoPessoa.class,
				COLUNA_PESSOA_CODIGO + " = " + pessoaCodigo + " and "
						+ COLUNA_FORMA_PAGAMENTO_CODIGO + "= '"
						+ formaPagamentoCodigo + "'", null);
	}

	/**
	 * 
	 * @param dao
	 * @param pessoaCodigo
	 * @return
	 */
	public static List<FormaPagamentoPessoa> recuperar(GenericoDAO dao,
			int pessoaCodigo) {

		return dao.list(FormaPagamentoPessoa.class, COLUNA_PESSOA_CODIGO
				+ " = " + pessoaCodigo, null, null, null);
	}

}
