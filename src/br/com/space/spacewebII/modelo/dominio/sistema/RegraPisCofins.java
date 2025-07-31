package br.com.space.spacewebII.modelo.dominio.sistema;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;

/**
 *
 * @author Sandro
 */
@Entity
@Table(name = "regrapiscofins")
@XmlRootElement
public class RegraPisCofins implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected RegraPisCofinsPK regraPisCofinsPK;

	@Basic(optional = false)
	@Column(name = "RPC_CSTPISCOFD")
	private String cstPisCofinsDestinoCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_NPCCODIGO")
	private String naturezaPisCofinsCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_ALIQPIS")
	private double aliquotaPis;

	@Basic(optional = false)
	@Column(name = "RPC_ALIQCOFINS")
	private double aliquotaCofins;

	@Basic(optional = false)
	@Column(name = "RPC_OBFCODIGO")
	private int observacaoFiscalCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_CODIGO")
	private int regraPisCofinsCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_DESC")
	private String descricao;

	@Transient
	private String tipoTributacaoPis;

	@Transient
	private String tipoTributacaoCofins;

	public RegraPisCofins() {
	}

	public RegraPisCofins(RegraPisCofinsPK regraPisCofinsPK) {
		this.regraPisCofinsPK = regraPisCofinsPK;
	}

	public RegraPisCofinsPK getRegraPisCofinsPK() {
		return regraPisCofinsPK;
	}

	public void setRegraPisCofinsPK(RegraPisCofinsPK regraPisCofinsPK) {
		this.regraPisCofinsPK = regraPisCofinsPK;
	}

	public String getCstPisCofinsDestinoCodigo() {
		return cstPisCofinsDestinoCodigo;
	}

	public void setCstPisCofinsDestinoCodigo(String cstPisCofinsDestinoCodigo) {
		this.cstPisCofinsDestinoCodigo = cstPisCofinsDestinoCodigo;
	}

	public String getNaturezaPisCofinsCodigo() {
		return naturezaPisCofinsCodigo;
	}

	public void setNaturezaPisCofinsCodigo(String naturezaPisCofinsCodigo) {
		this.naturezaPisCofinsCodigo = naturezaPisCofinsCodigo;
	}

	public double getAliquotaPis() {
		return aliquotaPis;
	}

	public void setAliquotaPis(double aliquotaPis) {
		this.aliquotaPis = aliquotaPis;
	}

	public double getAliquotaCofins() {
		return aliquotaCofins;
	}

	public void setAliquotaCofins(double aliquotaCofins) {
		this.aliquotaCofins = aliquotaCofins;
	}

	public int getObservacaoFiscalCodigo() {
		return observacaoFiscalCodigo;
	}

	public void setObservacaoFiscalCodigo(int observacaoFiscalCodigo) {
		this.observacaoFiscalCodigo = observacaoFiscalCodigo;
	}

	public int getRegraPisCofinsCodigo() {
		return regraPisCofinsCodigo;
	}

	public void setRegraPisCofinsCodigo(int regraPisCofinsCodigo) {
		this.regraPisCofinsCodigo = regraPisCofinsCodigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getTipoTributacaoPis() {
		return tipoTributacaoPis;
	}

	public void setTipoTributacaoPis(String tipoTributacaoPis) {
		this.tipoTributacaoPis = tipoTributacaoPis;
	}

	public String getTipoTributacaoCofins() {
		return tipoTributacaoCofins;
	}

	public void setTipoTributacaoCofins(String tipoTributacaoCofins) {
		this.tipoTributacaoCofins = tipoTributacaoCofins;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (regraPisCofinsPK != null ? regraPisCofinsPK.hashCode() : 0);
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		// not set
		if (!(object instanceof RegraPisCofins)) {
			return false;
		}
		RegraPisCofins other = (RegraPisCofins) object;
		if ((this.regraPisCofinsPK == null && other.regraPisCofinsPK != null)
				|| (this.regraPisCofinsPK != null && !this.regraPisCofinsPK.equals(other.regraPisCofinsPK))) {
			return false;
		}
		return true;
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

	public static RegraPisCofins obterTipoTributacaoPisCofins(GenericoDAO dao, RegraPisCofins regra) {
		String sql = "select csc_tipotrib, csp_tipotrib from cstpis, cstcofins " + " where csp_codigo = '"
				+ regra.getCstPisCofinsDestinoCodigo() + "' and csc_codigo = '" + regra.getCstPisCofinsDestinoCodigo()
				+ "'";

		ResultSet result = null;

		try {
			result = dao.listResultSet(sql);
			if (result.first()) {
				regra.setTipoTributacaoPis(result.getString("csp_tipotrib"));
				regra.setTipoTributacaoCofins(result.getString("csc_tipotrib"));
			}
		} catch (Exception e) {

		} finally {
			try {
				result.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return regra;
	}

	public int getCodigo() {
		return getRegraPisCofinsCodigo();
	}

	public String getCstPisCofins() {
		return getCstPisCofinsDestinoCodigo();
	}

	public static RegraPisCofins recuperarRegraPisCofins(GenericoDAO dao, String codigoFiscalOperacao,
			String cstPisCofins, int grupoPisCofinsCodigo, String naturezaOperacaoCodigo, Integer segmentoCodigo,
			IItemPedidoWeb item, String flagPisCofins) {

		String condicao = null;
		condicao = "(rpc_piscofins = '" + flagPisCofins + "' or rpc_piscofins = 'A')";
		condicao += " and (rpc_grpcodigo = " + item.getGrupoProdutoCodigo()
				+ " or rpc_grpcodigo = 0) and (rpc_sgpcodigo = " + item.getSubGrupoProdutoCodigo()
				+ " or rpc_sgpcodigo = 0) and (rpc_ctpcodigo = " + item.getCategoriaProdutoCodigo()
				+ " or rpc_ctpcodigo = 0) and (rpc_scpcodigo = " + item.getSubCategoriaProdutoCodigo()
				+ " or rpc_scpcodigo = 0) and rpc_cdfcodigo = '" + codigoFiscalOperacao + "' and rpc_cstpiscofo = '"
				+ cstPisCofins + "' and (rpc_gpccodigo = " + grupoPisCofinsCodigo
				+ " or rpc_gpccodigo = 0) and (rpc_natcodigo = '" + naturezaOperacaoCodigo
				+ "' or rpc_natcodigo= '') and (rpc_sgmcodigo = " + segmentoCodigo
				+ " or rpc_sgmcodigo = 0) and (rpc_filial = " + item.getFilialCodigo() + " or rpc_filial = 0)";

		RegraPisCofins regra = null;

		List<RegraPisCofins> regras = dao.list(RegraPisCofins.class, condicao, null, null, null);

		if (regras != null && regras.size() > 0)
			regra = regras.get(0);

		return regra;
	}

}
