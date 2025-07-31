/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Renato
 */
@Entity
@Table(name = "campanhaitem")
@XmlRootElement
public class CampanhaItem implements Serializable {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected CampanhaItemPK campanhaitemPK;

	@Column(name = "cmi_procodigo")
	private int produtoCodigo = 0;

	@Column(name = "cmi_qtdemincmp")
	private double quantidadeMinimaCampanha = 0;

	@Column(name = "cmi_pontos")
	private double pontos = 0;

	@Column(name = "cmi_pontosdsc")
	private double pontosDesconto = 0;

	@Column(name = "cmi_pontosnovo")
	private double pontosNovoCliente = 0;

	@Column(name = "cmi_pazomax")
	private int prazoMaximo = 0;

	@Column(name = "cmi_qtdeminped")
	private double quantidadeMinimaPorPedido;

	@Column(name = "cmi_pontoclipos")
	private Double pontoClientesPositivados;

	@Column(name = "cmi_dataini")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(name = "cmi_datafim")
	@Temporal(TemporalType.DATE)
	private Date dataFim;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "campanhaitem")
	private List<CampanhaItemPeriodo> campanhaitemperList;

	@JoinColumns({
			@JoinColumn(name = "CMI_CMPCODIGO", referencedColumnName = "CMP_CODIGO", insertable = false, updatable = false),
			@JoinColumn(name = "CMI_FILCODIGO", referencedColumnName = "CMP_FILCODIGO", insertable = false, updatable = false) })
	@ManyToOne(optional = false)
	private Campanha campanha;

	public CampanhaItem() {
	}

	public CampanhaItemPK getCampanhaitemPK() {
		return campanhaitemPK;
	}

	public void setCampanhaitemPK(CampanhaItemPK campanhaitemPK) {
		this.campanhaitemPK = campanhaitemPK;
	}

	public double getQuantidadeMinimaCampanha() {
		return quantidadeMinimaCampanha;
	}

	public void setQuantidadeMinimaCampanha(double quantidadeMinimaCampanha) {
		this.quantidadeMinimaCampanha = quantidadeMinimaCampanha;
	}

	public double getPontos() {
		return pontos;
	}

	public void setPontos(double pontos) {
		this.pontos = pontos;
	}

	public double getPontosDesconto() {
		return pontosDesconto;
	}

	public void setPontosDesconto(double pontosDesconto) {
		this.pontosDesconto = pontosDesconto;
	}

	public double getPontosNovoCliente() {
		return pontosNovoCliente;
	}

	public void setPontosNovoCliente(double pontosNovoCliente) {
		this.pontosNovoCliente = pontosNovoCliente;
	}

	public int getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(int prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	public double getQuantidadeMinimaPorPedido() {
		return quantidadeMinimaPorPedido;
	}

	public void setQuantidadeMinimaPorPedido(double quantidadeMinimaPorPedido) {
		this.quantidadeMinimaPorPedido = quantidadeMinimaPorPedido;
	}

	public Double getPontoClientesPositivados() {
		return pontoClientesPositivados;
	}

	public void setPontoClientesPositivados(Double pontoClientesPositivados) {
		this.pontoClientesPositivados = pontoClientesPositivados;
	}

	public Date getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(Date dataInicio) {
		this.dataInicio = dataInicio;
	}

	public Date getDataFim() {
		return dataFim;
	}

	public void setDataFim(Date dataFim) {
		this.dataFim = dataFim;
	}

	public List<CampanhaItemPeriodo> getCampanhaitemperList() {
		return campanhaitemperList;
	}

	public void setCampanhaitemperList(
			List<CampanhaItemPeriodo> campanhaitemperList) {
		this.campanhaitemperList = campanhaitemperList;
	}

	public Campanha getCampanha() {
		return campanha;
	}

	public void setCampanha(Campanha campanha) {
		this.campanha = campanha;
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	/**
	 * Verifica se o produto está em alguma campanha.
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param data
	 * @param produtoCodigo
	 * @return
	 * @throws Exception
	 */
	public static boolean isProdutoEmCampanha(GenericoDAO dao,
			int filialCodigo, Date data, int produtoCodigo) throws Exception {

		ResultSet resultSet = null;
		long count = 0;
		try {
			String dataStr = Conversao.formatarDataAAAAMMDD(data);

			String selectCampanha = "select  count(*)"
					+ " from campanhaitem "
					+ " inner join campanha ON cmi_cmpcodigo = cmp_codigo "
					+ " and cmi_filcodigo = cmp_filcodigo and cmp_situacao <> 'C'"
					+ " where cmi_filcodigo = "
					+ filialCodigo
					+ " and cmi_procodigo = "
					+ produtoCodigo
					+ " and '"
					+ dataStr
					+ "' between cmi_dataini and cmi_datafim order by cmi_procodigo";

			resultSet = dao.listResultSet(selectCampanha, null, null);

			if (resultSet.first()) {
				count = resultSet.getLong("count(*)");
			}

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (resultSet != null) {
				try {
					resultSet.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}

		return count > 0;
	}

	/**
	 * Retorna uma lista com todos os códigos dos produtos que estão em
	 * campanha.
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static List<Integer> recuperarCodigoProdutosEmCampanha(
			GenericoDAO dao, int filialCodigo, Date data) throws Exception {

		String dataStr = Conversao.formatarDataAAAAMMDD(data);

		String selectCampanha = "select  cmi_procodigo" + " from campanhaitem "
				+ " inner join campanha ON cmi_cmpcodigo = cmp_codigo "
				+ " and cmi_filcodigo = cmp_filcodigo and cmp_situacao <> 'C'"
				+ " where cmi_filcodigo = " + filialCodigo + " and '" + dataStr
				+ "' between cmi_dataini and cmi_datafim "
				+ " group by cmi_procodigo order by cmi_procodigo";

		List<Integer> produtosCampanha = new ArrayList<Integer>();
		ResultSet rs = null;

		try {

			rs = dao.listResultSet(selectCampanha, null, null);

			while (rs.next()) {
				int proCodigo = rs.getInt("cmi_procodigo");

				produtosCampanha.add(Integer.valueOf(proCodigo));
			}

			return produtosCampanha;

		} finally {
			try {
				if (rs != null) {
					rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
}
