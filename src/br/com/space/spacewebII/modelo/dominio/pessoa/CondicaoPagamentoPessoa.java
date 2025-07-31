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
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.ICondicaoPagamentoPessoa;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "condpgpessoa")
@XmlRootElement
public class CondicaoPagamentoPessoa implements Serializable, IPersistent,
		ICondicaoPagamentoPessoa {

	public static final String COLUNA_CONDICAO_PAGAMENTO_CODIGO = "cpe_cpgcodigo";
	public static final String COLUNA_PESSOA_CODIGO = "cpe_pescodigo";

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected CondicaoPagamentoPessoaPK condicaoPagamentoPessoaPK;

	/**
	 * 
	 */
	public CondicaoPagamentoPessoa() {
	}

	public CondicaoPagamentoPessoaPK getCondicaoPagamentoPessoaPK() {
		return condicaoPagamentoPessoaPK;
	}

	public void setCondicaoPagamentoPessoaPK(
			CondicaoPagamentoPessoaPK condicaoPagamentoPessoaPK) {
		this.condicaoPagamentoPessoaPK = condicaoPagamentoPessoaPK;
	}

	public int getPessoaCodigo() {
		return this.getCondicaoPagamentoPessoaPK().getPessoaCodigo();
	}

	public int getCondicaoPagamentoCodigo() {
		return this.getCondicaoPagamentoPessoaPK().getCondicaoPagamentoCodigo();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		// TODO Auto-generated method stub

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
	 * @param condicaoPagamentoCodigo
	 * @return
	 */
	public static CondicaoPagamentoPessoa recuperarUnico(GenericoDAO dao,
			int pessoaCodigo, int condicaoPagamentoCodigo) {

		return dao.uniqueResult(CondicaoPagamentoPessoa.class,
				COLUNA_PESSOA_CODIGO + " = " + pessoaCodigo + " and "
						+ COLUNA_CONDICAO_PAGAMENTO_CODIGO + "= '"
						+ condicaoPagamentoCodigo + "'", null);
	}
	
	/**
	 * 
	 * @param dao
	 * @param pessoaCodigo
	 * @return
	 */
	public static List<CondicaoPagamentoPessoa> recuperar(GenericoDAO dao,
			int pessoaCodigo) {

		return dao.list(CondicaoPagamentoPessoa.class,
				COLUNA_PESSOA_CODIGO + " = " + pessoaCodigo, null, null, null);
	}

}
