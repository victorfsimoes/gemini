package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Sandro
 */
@Embeddable
public class RegraPisCofinsPK implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "RPC_PISCOFINS")
	private String flagPisCofins;

	@Basic(optional = false)
	@Column(name = "RPC_CSTPISCOFO")
	private String cstPisCofinsOrigemCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_CDFCODIGO")
	private String cfop;

	@Basic(optional = false)
	@Column(name = "RPC_GPCCODIGO")
	private int grupoPisCofinsCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_NATCODIGO")
	private String naturezaOperacaoCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_SGMCODIGO")
	private int segmentoCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_GRPCODIGO")
	private int grupoProdutoCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_SGPCODIGO")
	private int subGrupoProdutoCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_CTPCODIGO")
	private int categoriaProdutoCodigo;

	@Basic(optional = false)
	@Column(name = "RPC_SCPCODIGO")
	private int subCategoriaProdutoCodigo;

	public RegraPisCofinsPK() {
	}

	public String getFlagPisCofins() {
		return flagPisCofins;
	}

	public void setFlagPisCofins(String flagPisCofins) {
		this.flagPisCofins = flagPisCofins;
	}

	public String getCstPisCofinsOrigemCodigo() {
		return cstPisCofinsOrigemCodigo;
	}

	public void setCstPisCofinsOrigemCodigo(String cstPisCofinsOrigemCodigo) {
		this.cstPisCofinsOrigemCodigo = cstPisCofinsOrigemCodigo;
	}

	public String getCfop() {
		return cfop;
	}

	public void setCfop(String cfop) {
		this.cfop = cfop;
	}

	public int getGrupoPisCofinsCodigo() {
		return grupoPisCofinsCodigo;
	}

	public void setGrupoPisCofinsCodigo(int grupoPisCofinsCodigo) {
		this.grupoPisCofinsCodigo = grupoPisCofinsCodigo;
	}

	public String getNaturezaOperacaoCodigo() {
		return naturezaOperacaoCodigo;
	}

	public void setNaturezaOperacaoCodigo(String naturezaOperacaoCodigo) {
		this.naturezaOperacaoCodigo = naturezaOperacaoCodigo;
	}

	public int getSegmentoCodigo() {
		return segmentoCodigo;
	}

	public void setSegmentoCodigo(int segmentoCodigo) {
		this.segmentoCodigo = segmentoCodigo;
	}

	public int getGrupoProdutoCodigo() {
		return grupoProdutoCodigo;
	}

	public void setGrupoProdutoCodigo(int grupoProdutoCodigo) {
		this.grupoProdutoCodigo = grupoProdutoCodigo;
	}

	public int getSubGrupoProdutoCodigo() {
		return subGrupoProdutoCodigo;
	}

	public void setSubGrupoProdutoCodigo(int subGrupoProdutoCodigo) {
		this.subGrupoProdutoCodigo = subGrupoProdutoCodigo;
	}

	public int getCategoriaProdutoCodigo() {
		return categoriaProdutoCodigo;
	}

	public void setCategoriaProdutoCodigo(int categoriaProdutoCodigo) {
		this.categoriaProdutoCodigo = categoriaProdutoCodigo;
	}

	public int getSubCategoriaProdutoCodigo() {
		return subCategoriaProdutoCodigo;
	}

	public void setSubCategoriaProdutoCodigo(int subCategoriaProdutoCodigo) {
		this.subCategoriaProdutoCodigo = subCategoriaProdutoCodigo;
	}

	@Override
	public int hashCode() {
		int hash = 0;
		hash += (flagPisCofins != null ? flagPisCofins.hashCode() : 0);
		hash += (cstPisCofinsOrigemCodigo != null ? cstPisCofinsOrigemCodigo.hashCode() : 0);
		hash += (cfop != null ? cfop.hashCode() : 0);
		hash += (int) grupoPisCofinsCodigo;
		hash += (naturezaOperacaoCodigo != null ? naturezaOperacaoCodigo.hashCode() : 0);
		hash += (int) segmentoCodigo;
		hash += (int) grupoProdutoCodigo;
		hash += (int) subGrupoProdutoCodigo;
		hash += (int) categoriaProdutoCodigo;
		hash += (int) subCategoriaProdutoCodigo;
		return hash;
	}

	@Override
	public boolean equals(Object object) {
		if (!(object instanceof RegraPisCofinsPK)) {
			return false;
		}
		RegraPisCofinsPK other = (RegraPisCofinsPK) object;
		if ((this.flagPisCofins == null && other.flagPisCofins != null)
				|| (this.flagPisCofins != null && !this.flagPisCofins.equals(other.flagPisCofins))) {
			return false;
		}

		if ((this.cstPisCofinsOrigemCodigo == null && other.cstPisCofinsOrigemCodigo != null)
				|| (this.cstPisCofinsOrigemCodigo != null
						&& !this.cstPisCofinsOrigemCodigo.equals(other.cstPisCofinsOrigemCodigo))) {
			return false;
		}
		if ((this.cfop == null && other.cfop != null) || (this.cfop != null && !this.cfop.equals(other.cfop))) {
			return false;
		}
		if (this.grupoPisCofinsCodigo != other.grupoPisCofinsCodigo) {
			return false;
		}
		if ((this.naturezaOperacaoCodigo == null && other.naturezaOperacaoCodigo != null)
				|| (this.naturezaOperacaoCodigo != null
						&& !this.naturezaOperacaoCodigo.equals(other.naturezaOperacaoCodigo))) {
			return false;
		}
		if (this.segmentoCodigo != other.segmentoCodigo) {
			return false;
		}
		if (this.grupoProdutoCodigo != other.grupoProdutoCodigo) {
			return false;
		}
		if (this.subGrupoProdutoCodigo != other.subGrupoProdutoCodigo) {
			return false;
		}
		if (this.categoriaProdutoCodigo != other.categoriaProdutoCodigo) {
			return false;
		}
		if (subCategoriaProdutoCodigo != other.subCategoriaProdutoCodigo) {
			return false;
		}
		return true;
	}

}
