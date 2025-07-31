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

@Entity
@Table(name = "parametro4")
@XmlRootElement
public class Parametro4 implements IPersistent, Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "pa4_filcodigo")
	private Integer filialCodigo;

	@Column(name = "pa4_frbasepcnf")
	private int consideraFreteBaseCalculoPisCofins;

	@Column(name = "pa4_dicmspiscof")
	private int flagDeduzIcmsBasePisCofins;

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getConsideraFreteBaseCalculoPisCofins() {
		return consideraFreteBaseCalculoPisCofins;
	}

	public void setConsideraFreteBaseCalculoPisCofins(int consideraFreteBaseCalculoPisCofins) {
		this.consideraFreteBaseCalculoPisCofins = consideraFreteBaseCalculoPisCofins;
	}

	public boolean isDeduzValorIcmsPisCofins() {
		return flagDeduzIcmsBasePisCofins == 1;
	}

	public int getFlagDeduzIcmsBasePisCofins() {
		return flagDeduzIcmsBasePisCofins;
	}

	public void setFlagDeduzIcmsBasePisCofins(int flagDeduzIcmsBasePisCofins) {
		this.flagDeduzIcmsBasePisCofins = flagDeduzIcmsBasePisCofins;
	}

	public static Parametro4 recuperarUnico(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(Parametro4.class, "pa4_filcodigo = " + codigo, null);
	}

	public boolean isConsideraFreteBaseCalculoPisCofins() {
		return getConsideraFreteBaseCalculoPisCofins() == 1;
	}

}
