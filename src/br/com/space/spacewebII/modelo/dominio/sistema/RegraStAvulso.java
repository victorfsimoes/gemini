package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Renato
 */
@Entity
@Table(name = "regrastav")
public class RegraStAvulso implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected RegraStAvulsoPK regraStAvulsoPK;

	@Column(name = "rsa_desc")
	private String descricao = null;

	@Column(name = "rsa_valorpauta")
	private double valorPauta;

	@Column(name = "rsa_ativo")
	private int flagAtivo = 0;

	@Column(name = "rsa_aliqmva")
	private double aliquotaMVA = 0;

	@Column(name = "rsa_redmva")
	private double reducaoMVA = 0;

	@Column(name = "rsa_aliqicms")
	private double aliquotaICMS;

	@Column(name = "rsa_aliqst")
	private double aliquotaST;
	
	@Column(name = "rsa_aliqfecp")
	private double aliquotaFundoEstadualCombatePobreza;

	public RegraStAvulso() {
	}

	public RegraStAvulsoPK getRegraStAvulsoPK() {
		return regraStAvulsoPK;
	}

	public void setRegraStAvulsoPK(RegraStAvulsoPK regraStAvulsoPK) {
		this.regraStAvulsoPK = regraStAvulsoPK;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public double getValorPauta() {
		return valorPauta;
	}

	public void setValorPauta(double valorPauta) {
		this.valorPauta = valorPauta;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public double getAliquotaMVA() {
		return aliquotaMVA;
	}

	public void setAliquotaMVA(double aliquotaMVA) {
		this.aliquotaMVA = aliquotaMVA;
	}

	public double getReducaoMVA() {
		return reducaoMVA;
	}

	public void setReducaoMVA(double reducaoMVA) {
		this.reducaoMVA = reducaoMVA;
	}

	public double getAliquotaICMS() {
		return aliquotaICMS;
	}

	public void setAliquotaICMS(double aliquotaICMS) {
		this.aliquotaICMS = aliquotaICMS;
	}

	public double getAliquotaST() {
		return aliquotaST;
	}

	public void setAliquotaST(double aliquotaST) {
		this.aliquotaST = aliquotaST;
	}
	
	public double getAliquotaFundoEstadualCombatePobreza() {
		return aliquotaFundoEstadualCombatePobreza;
	}

	public void setAliquotaFundoEstadualCombatePobreza(
			double aliquotaFundoEstadualCombatePobreza) {
		this.aliquotaFundoEstadualCombatePobreza = aliquotaFundoEstadualCombatePobreza;
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

	public static RegraStAvulso recuperarUnicoAtivo(GenericoDAO dao,
			Filial filial, String naturezaOperacaoCodigo, String tipoPessoa,
			int atividadeCodigo, int grupoTributacaoCodigo, String ufDestino) {

		List<String> whereFragmento = new ArrayList<>();

		whereFragmento.add("rsa_ativo = 1");
		whereFragmento.add("rsa_natcodigo = '" + naturezaOperacaoCodigo + "'");

		whereFragmento.add("rsa_tipopessoa like '%" + tipoPessoa + "%'");

		whereFragmento.add("(rsa_filcodigo = 0 or rsa_filcodigo ="
				+ filial.getCodigo() + ")");

		whereFragmento.add("(rsa_atvcodigo = 0 or rsa_atvcodigo ="
				+ atividadeCodigo + ")");

		whereFragmento.add("(rsa_grtcodigo = 0 or rsa_grtcodigo = "
				+ grupoTributacaoCodigo + ")");

		whereFragmento.add("(rsa_uforigem = '' or rsa_uforigem = '"
				+ filial.getUfSigla() + "')");

		String ufIgualOuDiferente = filial.getUfSigla().equals(ufDestino) ? "="
				: "#";
		whereFragmento.add("(rsa_ufdestino = '" + ufDestino
				+ "' or rsa_ufdestino = '" + ufIgualOuDiferente + "')");

		String where = GenericoDAO.criarWhere(whereFragmento);

		String select = "select * from regrastav where "
				+ where
				+ " order by rsa_grtcodigo desc, rsa_atvcodigo desc, rsa_filcodigo desc, rsa_uforigem desc, rsa_ufdestino desc";

		List<RegraStAvulso> grupoStAvulsos = dao.list(RegraStAvulso.class,
				select);

		if (ListUtil.isValida(grupoStAvulsos)) {
			return grupoStAvulsos.get(0);
		}
		return null;
	}

	@Override
	public String toString() {
		return "GrupoStAvulso [grupoStAvulsoPK=" + regraStAvulsoPK
				+ ", descricao=" + descricao + ", valorPauta=" + valorPauta
				+ ", flagAtivo=" + flagAtivo + ", aliquotaMVA=" + aliquotaMVA
				+ ", reducaoMVA=" + reducaoMVA + ", aliquotaICMS="
				+ aliquotaICMS + ", aliquotaST=" + aliquotaST + "]";
	}
}
