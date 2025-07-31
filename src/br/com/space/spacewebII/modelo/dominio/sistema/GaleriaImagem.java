/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "galeriaimagem")
@XmlRootElement
public class GaleriaImagem implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;
	protected static final String COLUNA_GALERIACODIGO = "gim_galcodigo";

	private static Comparator<GaleriaImagem> comparadorOrdem = null;

	@EmbeddedId
	private GaleriaImagemPK galeriaImagemPK;

	@Column(name = "gim_descimagem")
	private String descricaoImagem;

	@Column(name = "gim_ordem")
	private int ordem;
	
	@Column(name = "gim_grpcodigo")
	private Integer grupoProdutoCodigo;
	
	@Column(name = "gim_sgpcodigo")
	private Integer subGrupoProdutoCodigo;
	
	@Column(name = "gim_procodigo")
	private Integer produtoCodigo;
	
	@Transient
	private Galeria galeria;

	public GaleriaImagem() {
	}

	public GaleriaImagemPK getGaleriaImagemPK() {
		if (galeriaImagemPK == null)
			galeriaImagemPK = new GaleriaImagemPK();
		return galeriaImagemPK;
	}

	public void setGaleriaImagemPK(GaleriaImagemPK galeriaImagemPK) {
		this.galeriaImagemPK = galeriaImagemPK;
	}

	public int getGaleriaCodigo() {
		return getGaleriaImagemPK().getGaleriaCodigo();
	}

	public void setGaleriaCodigo(int galeriaCodigo) {
		getGaleriaImagemPK().setGaleriaCodigo(galeriaCodigo);
	}

	public Galeria getGaleria() {
		return galeria;
	}

	public void setGaleria(Galeria galeria) {
		this.galeria = galeria;
	}

	public String getNomeImagem() {
		return getGaleriaImagemPK().getNomeImagem();
	}

	public void setNomeImagem(String nomeImagem) {
		getGaleriaImagemPK().setNomeImagem(nomeImagem);
	}

	public String getDescricaoImagem() {
		return descricaoImagem;
	}

	public void setDescricaoImagem(String descricaoImagem) {
		this.descricaoImagem = descricaoImagem;
	}

	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public String getUrl(){
		
		String url = "/produtos/";
		
		if(produtoCodigo > 0)
			url = "/produto/" + produtoCodigo + "/";
		
		else if(grupoProdutoCodigo > 0){
			url += grupoProdutoCodigo;
			
			if(subGrupoProdutoCodigo > 0)
				url += "/" + subGrupoProdutoCodigo;
		}
		
		return url;
	}

	/**
	 * 
	 * @param dao
	 * @param codigoGaleria
	 * @return
	 */
	public static List<GaleriaImagem> recuperarImagensGaleria(GenericoDAO dao,
			int codigoGaleria) {
		return dao.list(GaleriaImagem.class, COLUNA_GALERIACODIGO + " = "
				+ codigoGaleria, null, "ordem", null);
	}

	/**
	 * 
	 * @param dao
	 * @param tipoLeiaute
	 *            0 - Banner ou 1 - Mini Banner
	 * @return
	 */
	public static List<GaleriaImagem> recuperarImagensBannerAtivos(
			GenericoDAO dao, int tipoLeiaute) {

		String select = "select * from galeriaimagem inner join galeria "
				+ " on gim_galcodigo = gal_codigo and gal_leiaute = "
				+ tipoLeiaute + " and " + Galeria.COLUNA_ATIVO + " = 1";

		return dao.list(GaleriaImagem.class, select);
	}

	/**
	 * 
	 * @param galeriaImagemsPesquisa
	 * @param galerias
	 * @param galeriaImagemNaoPermitidas
	 * @return
	 */
	public static List<GaleriaImagem> pesquisarNaLista(
			List<GaleriaImagem> galeriaImagemsPesquisa, List<Galeria> galerias,
			List<GaleriaImagem> galeriaImagemNaoPermitidas) {

		String conteudo = Galeria.getStringCodigosSepadosPorVirgula(galerias);

		String conteudoImagemNaoPermitido = ListUtil
				.isValida(galeriaImagemNaoPermitidas) ? getStringNomeImagensSepadosPorVirgula(galeriaImagemNaoPermitidas)
				: null;

		boolean pesquisaNaoPermitidas = ListUtil
				.isValida(galeriaImagemNaoPermitidas)
				&& StringUtil.isValida(conteudoImagemNaoPermitido);

		if (StringUtil.isValida(conteudo)) {

			List<GaleriaImagem> result = new ArrayList<GaleriaImagem>();

			for (GaleriaImagem galeriaImagem : galeriaImagemsPesquisa) {

				if (StringUtil.contains(conteudo,
						Integer.valueOf(galeriaImagem.getGaleriaCodigo()))

						&& ((pesquisaNaoPermitidas && !StringUtil.contains(
								conteudoImagemNaoPermitido,
								galeriaImagem.getNomeImagem())) || !pesquisaNaoPermitidas)) {

					result.add(galeriaImagem);
				}
			}

			return result;
		}

		return null;
	}

	/**
	 * 
	 * @param galeriaImagems
	 * @return
	 */
	public static String getStringNomeImagensSepadosPorVirgula(
			List<GaleriaImagem> galeriaImagems) {

		if (!ListUtil.isValida(galeriaImagems)) {
			return null;
		}

		StringBuilder conteudo = new StringBuilder();

		for (GaleriaImagem galeriaImagem : galeriaImagems) {

			if (galeriaImagem.getNomeImagem() != null) {

				if (StringUtil.isValida(conteudo.toString())) {

					conteudo.append(", ");
				}
				conteudo.append(galeriaImagem.getNomeImagem());
			}

		}

		return conteudo.toString();
	}

	/**
	 * 
	 * @return
	 */
	public static Comparator<GaleriaImagem> getComparadorOrdem() {

		if (comparadorOrdem == null) {

			comparadorOrdem = new Comparator<GaleriaImagem>() {

				@Override
				public int compare(GaleriaImagem o1, GaleriaImagem o2) {

					return Integer.compare(o1.getOrdem(), o2.getOrdem());
				}
			};

		}
		return comparadorOrdem;
	}

	public Integer getGrupoProdutoCodigo() {
		return grupoProdutoCodigo;
	}

	public void setGrupoProdutoCodigo(Integer grupoProdutoCodigo) {
		this.grupoProdutoCodigo = grupoProdutoCodigo;
	}

	public Integer getSubGrupoProdutoCodigo() {
		return subGrupoProdutoCodigo;
	}

	public void setSubGrupoProdutoCodigo(Integer subGrupoProdutoCodigo) {
		this.subGrupoProdutoCodigo = subGrupoProdutoCodigo;
	}

	public Integer getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(Integer produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}
}
