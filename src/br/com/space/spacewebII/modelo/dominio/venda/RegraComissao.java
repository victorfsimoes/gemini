package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Sandro
 */
@Entity
@Table(name = "regracomissao")
@XmlRootElement
public class RegraComissao implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Basic(optional = false)
	@Column(name = "rgc_codigo")
	private int codigo;

	@Column(name = "rgc_desc")
	private String descricao;

	@Column(name = "rgc_natcodigo")
	private String naturezaOperacaoCodigo;

	@Column(name = "rgc_clbcodigo")
	private Integer colaboradorCodigo;

	@Column(name = "rgc_ctpcodigo")
	private Integer categoriaProdutoCodigo;

	@Column(name = "rgc_grpcodigo")
	private Integer grupoProdutoCodigo;

	@Column(name = "rgc_sgpcodigo")
	private Integer subgrupoProdutoCodigo;

	@Column(name = "rgc_scpcodigo")
	private Integer subcategoriaProdutoCodigo;

	@Column(name = "rgc_procodigo")
	private Integer produtoCodigo;

	@Column(name = "rgc_tprcodigo")
	private Integer tabelaPrecoCodigo;

	@Column(name = "rgc_grccodigo")
	private Integer grupoClienteCodigo;

	@Column(name = "rgc_promocao")
	private String flagPromocao;

	@Column(name = "rgc_oferta")
	private String flagOferta;

	@Column(name = "rgc_filcodigo")
	private Integer filialCodigo;

	@Column(name = "rgc_gcocodigo")
	private Integer grupoComissaoCodigo;

	@Column(name = "rgc_ativo")
	private Integer flagAtivo;

	@Column(name = "rgc_dataini")
	private Date dataInicio;

	@Column(name = "rgc_datafim")
	private Date dataFim;

	@Column(name = "rgc_clccodigo")
	private Date classificacaoClienteCodigo;

	public RegraComissao() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getNaturezaOperacaoCodigo() {
		return naturezaOperacaoCodigo;
	}

	public void setNaturezaOperacaoCodigo(String naturezaOperacaoCodigo) {
		this.naturezaOperacaoCodigo = naturezaOperacaoCodigo;
	}

	public Integer getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(Integer colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public Integer getCategoriaProdutoCodigo() {
		return categoriaProdutoCodigo;
	}

	public void setCategoriaProdutoCodigo(Integer categoriaProdutoCodigo) {
		this.categoriaProdutoCodigo = categoriaProdutoCodigo;
	}

	public Integer getGrupoProdutoCodigo() {
		return grupoProdutoCodigo;
	}

	public void setGrupoProdutoCodigo(Integer grupoProdutoCodigo) {
		this.grupoProdutoCodigo = grupoProdutoCodigo;
	}

	public Integer getSubgrupoProdutoCodigo() {
		return subgrupoProdutoCodigo;
	}

	public void setSubgrupoProdutoCodigo(Integer subgrupoProdutoCodigo) {
		this.subgrupoProdutoCodigo = subgrupoProdutoCodigo;
	}

	public Integer getSubcategoriaProdutoCodigo() {
		return subcategoriaProdutoCodigo;
	}

	public void setSubcategoriaProdutoCodigo(Integer subcategoriaProdutoCodigo) {
		this.subcategoriaProdutoCodigo = subcategoriaProdutoCodigo;
	}

	public Integer getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(Integer produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public Integer getTabelaPrecoCodigo() {
		return tabelaPrecoCodigo;
	}

	public void setTabelaPrecoCodigo(Integer tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	public Integer getGrupoClienteCodigo() {
		return grupoClienteCodigo;
	}

	public void setGrupoClienteCodigo(Integer grupoClienteCodigo) {
		this.grupoClienteCodigo = grupoClienteCodigo;
	}

	public String getFlagPromocao() {
		return flagPromocao;
	}

	public void setFlagPromocao(String flagPromocao) {
		this.flagPromocao = flagPromocao;
	}

	public String getFlagOferta() {
		return flagOferta;
	}

	public void setFlagOferta(String flagOferta) {
		this.flagOferta = flagOferta;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Integer getGrupoComissaoCodigo() {
		return grupoComissaoCodigo;
	}

	public void setGrupoComissaoCodigo(Integer grupoComissaoCodigo) {
		this.grupoComissaoCodigo = grupoComissaoCodigo;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
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

	public Date getClassificacaoClienteCodigo() {
		return classificacaoClienteCodigo;
	}

	public void setClassificacaoClienteCodigo(Date classificacaoClienteCodigo) {
		this.classificacaoClienteCodigo = classificacaoClienteCodigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
