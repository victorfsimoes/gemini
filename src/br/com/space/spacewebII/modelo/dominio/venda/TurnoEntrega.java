package br.com.space.spacewebII.modelo.dominio.venda;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Formula;
import org.hibernate.validator.Length;

import br.com.space.api.negocio.modelo.dominio.ITurnoEntrega;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 * 
 */
@Entity()
@Table(name = "turnoentrega")
@XmlRootElement
public class TurnoEntrega implements ITurnoEntrega {

	private static br.com.space.api.spa.model.dao.db.Table table = null;

	public final static String COLUNA_CODIGO = "tre_codigo";
	public final static String CAMPO_CODIGO_HQL = "codigo";
	public final static String COLUNA_ATIVO = "tre_ativo";
	public final static String COLUNA_CODIGO_FILIAL = "tre_filcodigo";

	@Id
	@Column(name = COLUNA_CODIGO)
	private int codigo = 0;

	@Column(name = "tre_desc", length = 30)
	@Length(max = 30)
	private String descricao = null;

	@Column(name = "tre_horalim", length = 8)
	@Length(max = 8)
	private String horaLimite = null;

	@Column(name = "tre_diasant")
	private int diasAntecedencia = 0;

	@Column(name = COLUNA_ATIVO)
	@Formula("coalesce(" + COLUNA_ATIVO + ", 0)")
	private int flagAtivo = 0;

	@Column(name = COLUNA_CODIGO_FILIAL)
	@Formula("coalesce(" + COLUNA_CODIGO_FILIAL + ", 0)")
	private int filialCodigo = 0;

	public TurnoEntrega() {

	}

	public TurnoEntrega(String descricao, String horaLimitePedidos,
			int diaAntecedenciaPedidos) {
		super();

		this.descricao = descricao;
		this.horaLimite = horaLimitePedidos;
		this.diasAntecedencia = diaAntecedenciaPedidos;
	}

	@Override
	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	@Override
	public String toString() {
		return descricao;
	}

	@Override
	public String getHoraLimite() {
		return horaLimite;
	}

	public void setHoraLimite(String horaLimite) {
		this.horaLimite = horaLimite;

	}

	@Override
	public int getDiasAntecedencia() {
		return diasAntecedencia;
	}

	public void setDiasAntecedencia(int diasAntecedencia) {
		this.diasAntecedencia = diasAntecedencia;

	}

	public static String getColunaCodigoFilial() {
		return COLUNA_CODIGO_FILIAL;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return table;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		TurnoEntrega.table = table;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	public static List<TurnoEntrega> recuperaTurnosEntregaAtivos(
			GenericoDAO dao, Integer filialCodigo) {
		String where = COLUNA_ATIVO + " = 1";
		if (filialCodigo != null)
			where += " and " + COLUNA_CODIGO_FILIAL + " = " + filialCodigo;

		return dao.list(TurnoEntrega.class, where, null, "descricao", null);
	}

	public static TurnoEntrega pesquisaNaLista(
			List<TurnoEntrega> turnoEntregas, int turnoCodigo) {

		for (TurnoEntrega turnoEntrega : turnoEntregas) {

			if (turnoEntrega.getCodigo() == turnoCodigo) {
				return turnoEntrega;
			}
		}
		return null;
	}

}
