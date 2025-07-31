/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "parametro3")
@XmlRootElement
public class Parametro3 implements IPersistent, Serializable {
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "pa3_filcodigo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private Integer filialCodigo;

	@Column(name = "pa3_condescst")
	private int flagConsideraDescontoCalculoSt;

	@Column(name = "pa3_stbasepcnf")
	private int flagConsideraStBaseCalculoPisCofins;

	@Column(name = "pa3_ipibasepcnf")
	private int flagConsideraIpiBaseCalculoPisCofins;

	@Column(name = "pa3_nsomaacrbpc")
	private int flagConsideraAcrescimoBaseCalculoPisCofins;

	@Column(name = "pa3_nsomadesbpc")
	private int flagConsideraDescontoBaseCalculoPisCofins;

	@Column(name = "pa3_sugtabprcli")
	private int flagSugereTabelaPrecoCliente;

	@Column(name = "pa3_tprcodigof ")
	private int tabelaPrecoCodigoPessoaFisica;

	@Column(name = "pa3_tprcodigoj")
	private int tabelaPrecoCodigoPessoaJuridica;

	public Parametro3() {
	}

	public Parametro3(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getFlagConsideraDescontoCalculoSt() {
		return flagConsideraDescontoCalculoSt;
	}

	public void setFlagConsideraDescontoCalculoSt(int flagConsideraDesacontoCalculoSt) {
		this.flagConsideraDescontoCalculoSt = flagConsideraDesacontoCalculoSt;
	}

	public int getFlagConsideraStBaseCalculoPisCofins() {
		return flagConsideraStBaseCalculoPisCofins;
	}

	public void setFlagConsideraStBaseCalculoPisCofins(int flagConsideraStBaseCalculoPisCofins) {
		this.flagConsideraStBaseCalculoPisCofins = flagConsideraStBaseCalculoPisCofins;
	}

	public int getFlagConsideraIpiBaseCalculoPisCofins() {
		return flagConsideraIpiBaseCalculoPisCofins;
	}

	public void setFlagConsideraIpiBaseCalculoPisCofins(int flagConsideraIpiBaseCalculoPisCofins) {
		this.flagConsideraIpiBaseCalculoPisCofins = flagConsideraIpiBaseCalculoPisCofins;
	}

	public int getFlagConsideraAcrescimoBaseCalculoPisCofins() {
		return flagConsideraAcrescimoBaseCalculoPisCofins;
	}

	public void setFlagConsideraAcrescimoBaseCalculoPisCofins(int flagConsideraAcrescimoBaseCalculoPisCofins) {
		this.flagConsideraAcrescimoBaseCalculoPisCofins = flagConsideraAcrescimoBaseCalculoPisCofins;
	}

	public int getFlagConsideraDescontoBaseCalculoPisCofins() {
		return flagConsideraDescontoBaseCalculoPisCofins;
	}

	public void setFlagConsideraDescontoBaseCalculoPisCofins(int flagConsideraDescontoBaseCalculoPisCofins) {
		this.flagConsideraDescontoBaseCalculoPisCofins = flagConsideraDescontoBaseCalculoPisCofins;
	}

	public boolean isSugereTabelaPrecoCliente() {
		return flagSugereTabelaPrecoCliente == 1;
	}

	public int getFlagSugereTabelaPrecoCliente() {
		return flagSugereTabelaPrecoCliente;
	}

	public void setFlagSugereTabelaPrecoCliente(int flagSugereTabelaPrecoCliente) {
		this.flagSugereTabelaPrecoCliente = flagSugereTabelaPrecoCliente;
	}

	public int getTabelaPrecoCodigoPessoaFisica() {
		return tabelaPrecoCodigoPessoaFisica;
	}

	public void setTabelaPrecoCodigoPessoaFisica(int tabelaPrecoCodigoPessoaFisica) {
		this.tabelaPrecoCodigoPessoaFisica = tabelaPrecoCodigoPessoaFisica;
	}

	public int getTabelaPrecoCodigoPessoaJuridica() {
		return tabelaPrecoCodigoPessoaJuridica;
	}

	public void setTabelaPrecoCodigoPessoaJuridica(int tabelaPrecoCodigoPessoaJuridica) {
		this.tabelaPrecoCodigoPessoaJuridica = tabelaPrecoCodigoPessoaJuridica;
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

	public static Parametro3 recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(Parametro3.class, COLUNA_FILIAL + " = " + codigo, null);
	}

	public boolean isConsideraStBaseCalculoPisCofins() {
		return (getFlagConsideraStBaseCalculoPisCofins() == 1);
	}

	public boolean isConsideraIpiBaseCalculoPisCofins() {
		return (getFlagConsideraIpiBaseCalculoPisCofins() == 1);
	}

	public boolean isConsideraAcrescimoBaseCalculoPisCofins() {
		return (getFlagConsideraAcrescimoBaseCalculoPisCofins() == 1);
	}

	public boolean isConsideraDescontoBaseCalculoPisCofins() {
		return (getFlagConsideraDescontoBaseCalculoPisCofins() == 1);
	}

}
