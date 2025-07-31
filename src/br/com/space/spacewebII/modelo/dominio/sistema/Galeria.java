/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

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

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "galeria")
@XmlRootElement
public class Galeria implements Serializable, IPersistent {

	public static final int BANNER_MAXI = 0;
	public static final int BANNER_CENTRAL = 1;
	public static final int BANNER_MINI = 2;

	private static final long serialVersionUID = 1L;
	public static final String COLUNA_CODIGO = "gal_codigo";
	public static final String COLUNA_ATIVO = "gal_ativo";

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Column(name = "gal_nome")
	private String nome;

	@Column(name = "gal_grpcodigo")
	private Integer grupoCodigo;

	@Column(name = "gal_sgpcodigo")
	private Integer subGrupoCodigo;

	@Column(name = "gal_ctpcodigo")
	private Integer categoriaProdutoCodigo;

	@Column(name = "gal_scpcodigo")
	private Integer subCategoriaProdutoCodigo;

	@Column(name = "gal_leiaute")
	private int tipoLeiaute;

	@Column(name = COLUNA_ATIVO)
	private int flagAtivo;

	@Column(name = "gal_altura")
	private Integer altura;
	
	@Column(name = "gal_bgimagem")
	private String imagemBackground;
	
	@Column(name = "gal_ordem")
	private int ordem;

	public Galeria() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public Integer getGrupoCodigo() {
		return grupoCodigo;
	}

	public void setGrupoCodigo(Integer grupoCodigo) {
		this.grupoCodigo = grupoCodigo;
	}

	public Integer getSubGrupoCodigo() {
		return subGrupoCodigo;
	}

	public void setSubGrupoCodigo(Integer subGrupoCodigo) {
		this.subGrupoCodigo = subGrupoCodigo;
	}

	public Integer getCategoriaProdutoCodigo() {
		return categoriaProdutoCodigo;
	}

	public void setCategoriaProdutoCodigo(Integer categoriaProdutoCodigo) {
		this.categoriaProdutoCodigo = categoriaProdutoCodigo;
	}

	public Integer getSubCategoriaProdutoCodigo() {
		return subCategoriaProdutoCodigo;
	}

	public void setSubCategoriaProdutoCodigo(Integer subCategoriaProdutoCodigo) {
		this.subCategoriaProdutoCodigo = subCategoriaProdutoCodigo;
	}

	public int getTipoLeiaute() {
		return tipoLeiaute;
	}

	public void setTipoLeiaute(int leiaute) {
		this.tipoLeiaute = leiaute;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getAltura() {
		return altura;
	}

	public void setAltura(Integer altura) {
		this.altura = altura;
	}

	public String getImagemBackground() {
		return imagemBackground;
	}

	public void setImagemBackground(String imagemBackground) {
		this.imagemBackground = imagemBackground;
	}
	
	public int getOrdem() {
		return ordem;
	}

	public void setOrdem(int ordem) {
		this.ordem = ordem;
	}

	public boolean eExclusiva() {
		return (grupoCodigo != null && grupoCodigo > 0)
				|| (subGrupoCodigo != null && subGrupoCodigo > 0)
				|| (categoriaProdutoCodigo != null && categoriaProdutoCodigo > 0)
				|| (subCategoriaProdutoCodigo != null && subCategoriaProdutoCodigo > 0);
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

	/**
	 * 
	 * @param dao
	 * @return
	 */
	public static List<Galeria> recuperarGaleriasAtivas(GenericoDAO dao) {
		return dao.list(Galeria.class, COLUNA_ATIVO + " = 1", null, "ordem", null);
	}

	/**
	 * Recupera todas as galerias ativas de acordo com seu tipo
	 * 
	 * @param dao
	 * @param tipo
	 *            0 - Maxi Banner 1 - Banner Central 2 - Mini Banner
	 * @return
	 */
	public static List<Galeria> recuperarGaleriasAtivasTipo(GenericoDAO dao,
			int tipo) {

		String where = COLUNA_ATIVO + " = 1 and gal_leiaute = " + tipo;

		return dao.list(Galeria.class, where, null, "ordem", null);
	}

	/**
	 * 
	 * @param galerias
	 * @param galeriaCodigo
	 * @return
	 */
	public static Galeria pesquisarNaLista(List<Galeria> galerias,
			int galeriaCodigo) {

		for (Galeria galeria : galerias) {
			if (galeria.getCodigo() == galeriaCodigo) {
				return galeria;
			}
		}
		return null;

	}

	/**
	 * 
	 * @param galerias
	 * @return
	 */
	public static String getStringCodigosSepadosPorVirgula(
			List<Galeria> galerias) {

		if (galerias == null || galerias.size() == 0) {
			return null;
		}

		StringBuilder conteudo = new StringBuilder();

		for (Galeria galeria : galerias) {

			if (!conteudo.toString().isEmpty()) {
				conteudo.append(", ");
			}

			conteudo.append(Integer.toString(galeria.getCodigo()));
		}
		return conteudo.toString();
	}

}
