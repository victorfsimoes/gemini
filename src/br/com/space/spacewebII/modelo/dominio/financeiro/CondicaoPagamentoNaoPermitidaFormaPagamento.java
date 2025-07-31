/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "condnforma")
@XmlRootElement
public class CondicaoPagamentoNaoPermitidaFormaPagamento implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected CondicaoPagamentoNaoPermitidaFormaPagamentoPK condicaoPagamentoNaoPermitidaFormaPagamentoPK;

	public CondicaoPagamentoNaoPermitidaFormaPagamento() {
	}

	public CondicaoPagamentoNaoPermitidaFormaPagamentoPK getCondicaoPagamentoNaoPermitidaFormaPagamentoPK() {
		return condicaoPagamentoNaoPermitidaFormaPagamentoPK;
	}

	public void setCondicaoPagamentoNaoPermitidaFormaPagamentoPK(
			CondicaoPagamentoNaoPermitidaFormaPagamentoPK condicaoPagamentoNaoPermitidaFormaPagamentoPK) {
		this.condicaoPagamentoNaoPermitidaFormaPagamentoPK = condicaoPagamentoNaoPermitidaFormaPagamentoPK;
	}

	public static CondicaoPagamentoNaoPermitidaFormaPagamento recuperar(GenericoDAO dao, String forma, int condicao) {

		CondicaoPagamentoNaoPermitidaFormaPagamento condicaoPagamentoNaoPermitidaFormaPagamento = dao.uniqueResult(CondicaoPagamentoNaoPermitidaFormaPagamento.class,
				"Select * from condnforma where cnf_fpgcodigo = '" + forma + "' and cnf_cpgcodigo = " + condicao);
		
		return condicaoPagamentoNaoPermitidaFormaPagamento;
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
}
