package br.com.space.spacewebII.modelo.dominio.logistica;

import java.sql.ResultSet;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "fretefaixapeso")
public class FreteFaixaPeso implements IPersistent {

	public static final String NOME_TABELA = "fretefaixapeso";
	public static final String COLUNA_FRETEFAIXACODIGO = "ffp_frfcodigo";
	public static final String COLUNA_PESOINI = "ffp_pesoini";
	public static final String COLUNA_PESOFINAL = "ffp_pesofim";

	@EmbeddedId
	private FreteFaixaPesoPK freteFaixaPesoPK;

	@Column(name = "ffp_valorfrete")
	private double valorFrete;

	public FreteFaixaPesoPK getFreteFaixaPesoPK() {
		return freteFaixaPesoPK;
	}

	public void setFreteFaixaPesoPK(FreteFaixaPesoPK freteFaixaPesoPK) {
		this.freteFaixaPesoPK = freteFaixaPesoPK;
	}

	public double getValorFrete() {
		return valorFrete;
	}

	public void setValorFrete(double valorFrete) {
		this.valorFrete = valorFrete;
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

	/**
	 * 
	 * @param dao
	 * @param freteFaixaCodigo
	 * @param peso
	 * @return
	 */
	public static FreteFaixaPeso recuperarPeso(GenericoDAO dao,
			int freteFaixaCodigo, double peso) {

		String where = COLUNA_FRETEFAIXACODIGO + " = " + freteFaixaCodigo
				+ " and " + peso + " between " + COLUNA_PESOINI + " and "
				+ COLUNA_PESOFINAL;
		return dao.uniqueResult(FreteFaixaPeso.class, where, null);
	}

	/**
	 * 
	 * @param dao
	 * @param freteFaixaCodigo
	 * @return
	 */
	public static FreteFaixaPeso recuperarMaiorPeso(GenericoDAO dao,
			int freteFaixaCodigo) {

		String subSelect = "(select max(" + COLUNA_PESOFINAL + ") as maiorpeso"
				+ " from " + NOME_TABELA + " where " + COLUNA_FRETEFAIXACODIGO
				+ " = " + freteFaixaCodigo + ")";

		String where = COLUNA_FRETEFAIXACODIGO + " = " + freteFaixaCodigo
				+ " and " + COLUNA_PESOFINAL + " = " + subSelect;
		return dao.uniqueResult(FreteFaixaPeso.class, where, null);
	}

	public static double recuperarMaiorPesoValor(GenericoDAO dao,
			int freteFaixaCodigo) {

		double maiorPeso = 0;
		ResultSet rs = null;
		try {
			String sql = "select max(" + COLUNA_PESOFINAL + ") as maiorpeso"
					+ " from " + NOME_TABELA + " where "
					+ COLUNA_FRETEFAIXACODIGO + " = " + freteFaixaCodigo;

			rs = dao.listResultSet(sql);

			if (rs.first()) {
				maiorPeso = rs.getDouble("maiorpeso");
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			GenericoDAO.closeResultSet(rs);
		}

		return maiorPeso;
	}
}
