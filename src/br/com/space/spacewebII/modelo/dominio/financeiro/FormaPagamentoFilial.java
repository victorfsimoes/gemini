package br.com.space.spacewebII.modelo.dominio.financeiro;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "formapgfilial")
public class FormaPagamentoFilial implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	public static final String COLUNA_FORMAPAGAMENTO_CODIGO = "fpf_fpgcodigo";
	public static final String COLUNA_FILIAL_CODIGO = "fpf_filcodigo";

	@EmbeddedId
	private FormaPagamentoFilialPK formaPagamentoFilialPK;

	@JoinColumns(@JoinColumn(name = "fpf_caccodigo", referencedColumnName = "cac_codigo"))
	@ManyToOne(optional = true)
	@NotFound(action = NotFoundAction.IGNORE)
	private CredencialAdministradoraCartao credencialAdministradoraCartao;

	@Column(name = "fpf_limcpgadm")
	private int flagLimitaCondicaoPagamentoAdministradora;

	@Column(name = "fpf_intpagadm")
	private int flagIntegraPagamentoAdministradora;

	public FormaPagamentoFilial() {

	}

	public FormaPagamentoFilialPK getFormaPagamentoFilialPK() {
		return formaPagamentoFilialPK;
	}

	public void setFormaPagamentoFilialPK(
			FormaPagamentoFilialPK formaPagamentoFilialPK) {
		this.formaPagamentoFilialPK = formaPagamentoFilialPK;
	}

	public CredencialAdministradoraCartao getCredencialAdministradoraCartao() {
		return credencialAdministradoraCartao;
	}

	public void setCredencialAdministradoraCartao(
			CredencialAdministradoraCartao credencialAdministradoraCartao) {
		this.credencialAdministradoraCartao = credencialAdministradoraCartao;
	}

	public int getFlagLimitaCondicaoPagamentoAdministradora() {
		return flagLimitaCondicaoPagamentoAdministradora;
	}

	public void setFlagLimitaCondicaoPagamentoAdministradora(
			int flagLimitaCondicaoPagamentoAdministradora) {
		this.flagLimitaCondicaoPagamentoAdministradora = flagLimitaCondicaoPagamentoAdministradora;
	}

	public int getFlagIntegraPagamentoAdministradora() {
		return flagIntegraPagamentoAdministradora;
	}

	public void setFlagIntegraPagamentoAdministradora(
			int flagIntegraPagamentoAdministradora) {
		this.flagIntegraPagamentoAdministradora = flagIntegraPagamentoAdministradora;
	}

	/**
	 * 
	 * @param dao
	 * @param formaPagamentoCodigo
	 * @param filialCodigo
	 * @return
	 */
	public static FormaPagamentoFilial recuperarUnico(GenericoDAO dao,
			String formaPagamentoCodigo, int filialCodigo) {

		return dao.uniqueResult(FormaPagamentoFilial.class,
				COLUNA_FORMAPAGAMENTO_CODIGO + " = '" + formaPagamentoCodigo
						+ "' and " + COLUNA_FILIAL_CODIGO + "=" + filialCodigo,
				null);
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
