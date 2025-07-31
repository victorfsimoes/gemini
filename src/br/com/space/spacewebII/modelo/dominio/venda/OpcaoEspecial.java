package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IOpcaoEspecial;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "opcaoesp")
@XmlRootElement
public class OpcaoEspecial implements Serializable, IOpcaoEspecial {
	private static final long serialVersionUID = 1L;
	private static final String COLUNA_FILIAL_CODIGO = "oep_filcodigo";
	private static final String COLUNA_CODIGO = "oep_codigo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "oep_codigo")
	private int codigo;

	@Column(name = "oep_descricao")
	private String descricao;

	@Column(name = "oep_opcao")
	private int opcao;

	@Column(name = "oep_hierarquia")
	private int hierarquia;

	@Column(name = "oep_tipo")
	private int tipo;

	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL_CODIGO)
	private int filialCodigo;

	@Column(name = "oep_pvenabcusto")
	private Integer flagPermiteVenderAbaixoCusto;

	@Column(name = "oep_cvenabcusto")
	private String flagCaracterIndicadorAbaixoCusto;

	@Column(name = "oep_dvenabcusto")
	private Integer flagDescontoVendaAbaixoCusto;

	@Basic(optional = false)
	@Column(name = "oep_vcusto")
	private int flagValorCusto;

	@Column(name = "oep_pdadoscad")
	private Integer oepPdadoscad;

	@Column(name = "oep_fraciona")
	private Integer flagFraciona;

	@Column(name = "oep_valminimo")
	private Double valorMinimoVenda;

	@Column(name = "oep_exigepermis")
	private Integer flagExigePermissao;

	@Column(name = "oep_grvcodigo")
	private Integer grupoVendaCodigo;

	public OpcaoEspecial() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getOpcao() {
		return opcao;
	}

	public void setOpcao(int opcao) {
		this.opcao = opcao;
	}

	public int getHierarquia() {
		return hierarquia;
	}

	public void setHierarquia(int hierarquia) {
		this.hierarquia = hierarquia;
	}

	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Integer getFlagPermiteVenderAbaixoCusto() {
		return flagPermiteVenderAbaixoCusto;
	}

	public void setFlagPermiteVenderAbaixoCusto(
			Integer flagPermiteVenderAbaixoCusto) {
		this.flagPermiteVenderAbaixoCusto = flagPermiteVenderAbaixoCusto;
	}

	public String getFlagCaracterIndicadorAbaixoCusto() {
		return flagCaracterIndicadorAbaixoCusto;
	}

	public void setFlagCaracterIndicadorAbaixoCusto(
			String flagCaracterIndicadorAbaixoCusto) {
		this.flagCaracterIndicadorAbaixoCusto = flagCaracterIndicadorAbaixoCusto;
	}

	public Integer getFlagDescontoVendaAbaixoCusto() {
		return flagDescontoVendaAbaixoCusto;
	}

	public void setFlagDescontoVendaAbaixoCusto(
			Integer flagDescontoVendaAbaixoCusto) {
		this.flagDescontoVendaAbaixoCusto = flagDescontoVendaAbaixoCusto;
	}

	public int getFlagValorCusto() {
		return flagValorCusto;
	}

	public void setFlagValorCusto(int flagValorCusto) {
		this.flagValorCusto = flagValorCusto;
	}

	public Integer getOepPdadoscad() {
		return oepPdadoscad;
	}

	public void setOepPdadoscad(Integer oepPdadoscad) {
		this.oepPdadoscad = oepPdadoscad;
	}

	public Integer getFlagFraciona() {
		return flagFraciona;
	}

	public void setFlagFraciona(Integer flagFraciona) {
		this.flagFraciona = flagFraciona;
	}

	public double getValorMinimoVenda() {
		if (valorMinimoVenda == null) {
			return 0;
		}
		return valorMinimoVenda.doubleValue();
	}

	public void setValorMinimoVenda(Double valorMinimoVenda) {
		this.valorMinimoVenda = valorMinimoVenda;
	}

	public Integer getFlagExigePermissao() {
		return flagExigePermissao;
	}

	public void setFlagExigePermissao(Integer flagExigePermissao) {
		this.flagExigePermissao = flagExigePermissao;
	}

	public Integer getGrupoVendaCodigo() {
		return grupoVendaCodigo;
	}

	public void setGrupoVendaCodigo(Integer grupoVendaCodigo) {
		this.grupoVendaCodigo = grupoVendaCodigo;
	}

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

	public static OpcaoEspecial recuperarUnico(GenericoDAO dao, int codigo) {
		return dao.uniqueResult(OpcaoEspecial.class, COLUNA_CODIGO + " = "
				+ codigo, null);
	}

	public static List<OpcaoEspecial> recuperaOpcoesEspeciais(GenericoDAO dao,
			Integer filialCodigo) {
		String where = null;
		if (filialCodigo != null)
			where = COLUNA_FILIAL_CODIGO + " = " + filialCodigo;
		return dao.list(OpcaoEspecial.class, where, null, "descricao", null);
	}
}
