package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.negocio.modelo.dominio.IGrupoVenda;
import br.com.space.api.negocio.modelo.dominio.IPrecoVenda;
import br.com.space.api.negocio.modelo.dominio.IProduto;
import br.com.space.api.negocio.modelo.dominio.produto.Precificacao;
import br.com.space.api.negocio.modelo.dominio.produto.PrecoVenda;
import br.com.space.api.negocio.modelo.negocio.estoque.Estoque;
import br.com.space.api.negocio.modelo.negocio.estoque.FlagTipoEstoque;
import br.com.space.api.spa.annotations.SpaceColumn;
import br.com.space.api.spa.annotations.SpaceId;
import br.com.space.api.spa.annotations.SpaceTable;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.controle.pedido.GerentePedido;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.GrupoTributacao;
import br.com.space.spacewebII.modelo.dominio.venda.CampanhaItem;
import br.com.space.spacewebII.modelo.dominio.venda.GrupoVenda;
import br.com.space.spacewebII.modelo.dominio.venda.Oferta;
import br.com.space.spacewebII.modelo.dominio.venda.Promocao;
import br.com.space.spacewebII.modelo.util.Fabrica;
import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;
import br.com.space.spacewebII.modelo.widget.GrupoWidget;
import br.com.space.spacewebII.modelo.widget.SubGrupoWidget;

/**
 * 
 * @author Ronie
 */
@Entity
@SpaceTable(name = Produto.NOME_TABELA)
@Table(name = Produto.NOME_TABELA)
@XmlRootElement
public class Produto implements Serializable, IProduto, IPersistent, CaixaProdutoVisualizavel, Comparable<Produto> {

	private static final long serialVersionUID = 1L;
	public static final String NOME_TABELA = "produto";
	public static final String COLUNA_DESCRICAO = "pro_desc";
	public static final String COLUNA_DESCRICAO_DETALHADA = "pro_descdet";
	public static final String COLUNA_GRUPOPRODUTO_CODIGO = "pro_grpcodigo";
	public static final String COLUNA_SUBGRUPOPRODUTO_CODIGO = "pro_sgpcodigo";
	public static final String COLUNA_CODIGO = "pro_codigo";
	public static final String COLUNA_ATIVO = "pro_ativo";
	public static final String COLUNA_PRODUTOSIMILAR_CODIGO = "pro_sprcodigo";
	public static final String COLUNA_MARCA_CODIGO = "pro_mrccodigo";

	private static Comparator<Produto> comparatorCodigo = null;
	private static Comparator<Produto> comparatorDescricao = null;

	private static br.com.space.api.spa.model.dao.db.Table table;

	@Id
	@Column(name = COLUNA_CODIGO)
	@SpaceId
	@SpaceColumn(name = "pro_codigo")
	private int codigo;

	@Column(name = COLUNA_DESCRICAO)
	@SpaceColumn(name = COLUNA_DESCRICAO)
	private String descricao;

	@Column(name = "pro_descres")
	@SpaceColumn(name = "pro_descres")
	private String descricaoResumida;

	@Column(name = COLUNA_DESCRICAO_DETALHADA)
	@SpaceColumn(name = "pro_descdet")
	private String descricaoDetalhada;

	@Column(name = "pro_referencia")
	@SpaceColumn(name = "pro_referencia")
	private String referencia;

	@Column(name = "pro_servico")
	@SpaceColumn(name = "pro_servico")
	private Integer flagServico;

	@Column(name = "pro_datacad")
	@Temporal(TemporalType.DATE)
	@SpaceColumn(name = "pro_datacad")
	private Date dataCadastro;

	@Column(name = "pro_pliquido")
	@SpaceColumn(name = "pro_pliquido")
	private Double pesoLiquido;

	@Column(name = "pro_pbruto")
	@SpaceColumn(name = "pro_pbruto")
	private Double pesoBruto;

	@Column(name = "pro_fabcodigo")
	@SpaceColumn(name = "pro_fabcodigo")
	private Integer fabricanteCodigo;

	@Column(name = COLUNA_MARCA_CODIGO)
	@SpaceColumn(name = COLUNA_MARCA_CODIGO)
	private Integer marcaCodigo;

	@Column(name = "pro_ctpcodigo")
	@SpaceColumn(name = "pro_ctpcodigo")
	private Integer categoriaCodigo;

	@Column(name = "pro_scpcodigo")
	@SpaceColumn(name = "pro_scpcodigo")
	private Integer subCategoriaCodigo;

	@Column(name = "pro_lprcodigo")
	@SpaceColumn(name = "pro_lprcodigo")
	private Integer linhaCodigo;

	@Column(name = "pro_pesado")
	@SpaceColumn(name = "pro_pesado")
	private Integer flagPesado;

	@Column(name = COLUNA_ATIVO)
	@SpaceColumn(name = "pro_ativo")
	private Integer flagAtivo;

	@Column(name = "pro_filcodigo")
	@SpaceColumn(name = "pro_filcodigo")
	private int filialCodigo;

	@Column(name = "pro_dptcodigo")
	@SpaceColumn(name = "pro_dptcodigo")
	private Integer departamentoCodigo;

	@Column(name = "pro_perecivel")
	@SpaceColumn(name = "pro_perecivel")
	private Integer flagPerecivel;

	@Column(name = "pro_codfabric")
	@SpaceColumn(name = "pro_codfabric")
	private String codigoReferenciaFabricante;

	@Column(name = "pro_congelado")
	@SpaceColumn(name = "pro_congelado")
	private Integer flagCongelado;

	@Column(name = "pro_inflamavel")
	@SpaceColumn(name = "pro_inflamavel")
	private Integer flagInflamavel;

	@Column(name = "pro_toxico")
	@SpaceColumn(name = "pro_toxico")
	private Integer flagToxico;

	@Column(name = "pro_tribpis")
	@SpaceColumn(name = "pro_tribpis")
	private Integer flagTributaPis;

	@Column(name = "pro_tribcofins")
	@SpaceColumn(name = "pro_tribcofins")
	private Integer flagTributaCofins;

	@Column(name = "pro_aliqpis")
	@SpaceColumn(name = "pro_aliqpis")
	private Double aliquotaPis;

	@Column(name = "pro_aliqcofins")
	@SpaceColumn(name = "pro_aliqcofins")
	private Double aliquotaCofins;

	@Column(name = "pro_contlote")
	@SpaceColumn(name = "pro_contlote")
	private Integer flagControlaLote;

	@Column(name = "pro_contvalida")
	@SpaceColumn(name = "pro_contvalida")
	private Integer flagControlaValidade;

	@Column(name = "pro_aliqipi")
	@SpaceColumn(name = "pro_aliqipi")
	private Double aliquotaIpi;

	@Column(name = "pro_orgcodigo")
	@SpaceColumn(name = "pro_orgcodigo")
	private Integer origemCodigo;

	@Column(name = "pro_clfcodigo")
	@SpaceColumn(name = "pro_clfcodigo")
	private String classificacaoFiscalCodigo;

	@Column(name = "pro_dtultalt")
	@Temporal(TemporalType.DATE)
	@SpaceColumn(name = "pro_dtultalt")
	private Date dataUltimaAlteracao;

	@Column(name = "pro_hrultalt")
	@SpaceColumn(name = "pro_hrultalt")
	private String horaUltimaAlteracao;

	@Column(name = "pro_correlato")
	@SpaceColumn(name = "pro_correlato")
	private Integer correlato;

	@Column(name = "pro_gstcodigo")
	@SpaceColumn(name = "pro_gstcodigo")
	private Integer grupoStCodigo;

	@Column(name = "pro_valpauta")
	@SpaceColumn(name = "pro_valpauta")
	private Double valorPauta;

	@Column(name = "pro_aliqst")
	@SpaceColumn(name = "pro_aliqst")
	private Double aliquotaSt;

	@Column(name = "pro_eva")
	@SpaceColumn(name = "pro_eva")
	private Integer flagEva;

	@Column(name = "pro_diasval")
	@SpaceColumn(name = "pro_diasval")
	private Integer diasValidade;

	@Column(name = "pro_usacertclas")
	@SpaceColumn(name = "pro_usacertclas")
	private Integer flagUtilizaCertificadoClassificacao;

	@Column(name = "pro_certclass")
	@SpaceColumn(name = "pro_certclass")
	private String certificadoClassificacao;

	@Column(name = "pro_andarmin")
	@SpaceColumn(name = "pro_andarmin")
	private Integer andarMinimo;

	@Column(name = "pro_andarmax")
	@SpaceColumn(name = "pro_andarmax")
	private Integer andarMaximo;

	@Column(name = "pro_clacodigo")
	@SpaceColumn(name = "pro_clacodigo")
	private Integer classificacaoCodigo;

	@Column(name = "pro_encomenda")
	@SpaceColumn(name = "pro_encomenda")
	private Integer flagProdutoEncomenda;

	@Column(name = "pro_descnf1")
	@SpaceColumn(name = "pro_descnf1")
	private String descricaoNF1;

	@Column(name = "pro_descnf2")
	@SpaceColumn(name = "pro_descnf2")
	private String descricaoNF2;

	@Column(name = "pro_descnf3")
	@SpaceColumn(name = "pro_descnf3")
	private String descricaoNF3;

	@Column(name = "pro_descnf4")
	@SpaceColumn(name = "pro_descnf4")
	private String descricaoNF4;

	@Column(name = "pro_descnf5")
	@SpaceColumn(name = "pro_descnf5")
	private String descricaoNF5;

	@Column(name = "pro_claproducao")
	@SpaceColumn(name = "pro_claproducao")
	private Integer classificacaoProducao;

	@Column(name = "pro_nenviapalm")
	@SpaceColumn(name = "pro_nenviapalm")
	private Integer flagNaoEnviaPalm;

	@Column(name = "pro_impetq")
	@SpaceColumn(name = "pro_impetq")
	private Integer flagImprimeEtiqueta;

	@Column(name = "pro_pautaipi")
	@SpaceColumn(name = "pro_pautaipi")
	private Double valorPautaIPI;

	@Column(name = "pro_gstcodigoe")
	@SpaceColumn(name = "pro_gstcodigoe")
	private Integer grupoStEntradaCodigo;

	@Column(name = "pro_pautaste")
	@SpaceColumn(name = "pro_pautaste")
	private Double pautaStEntrada;

	@Lob
	@Column(name = "pro_obs")
	@SpaceColumn(name = "pro_obs")
	private String observacao;

	@Column(name = "pro_grvcodigo")
	@SpaceColumn(name = "pro_grvcodigo")
	private Integer grupoVendaCodigo;

	@Column(name = "pro_tpicodigo")
	@SpaceColumn(name = "pro_tpicodigo")
	private String tipoItemCodigo;

	@Column(name = "pro_csvcodigo")
	@SpaceColumn(name = "pro_csvcodigo")
	private String codigoServicoProduto;

	@Column(name = "pro_gpccodigo")
	@SpaceColumn(name = "pro_gpccodigo")
	private Integer grupoPisCofinsCodigo;

	@Column(name = "pro_teecodigo")
	@SpaceColumn(name = "pro_teecodigo")
	private Integer tipoEnderecoEstoqueCodigo;

	@Column(name = "pro_gticodigo")
	@SpaceColumn(name = "pro_gticodigo")
	private Integer grupoTributacaoIpiCodigo;

	@Column(name = "pro_grpcodigo")
	@SpaceColumn(name = "pro_grpcodigo")
	private int grupoCodigo = 0;

	@Column(name = "pro_sgpcodigo")
	@SpaceColumn(name = "pro_sgpcodigo")
	private Integer subGrupoCodigo = 0;

	@Column(name = "pro_grtcodigo")
	@SpaceColumn(name = "pro_grtcodigo")
	private int grupoTributacaoCodigo = 0;

	@Column(name = "pro_acrdespent")
	@SpaceColumn(name = "pro_acrdespent")
	private double valorAcrescimoDespesaEntrega;

	@Column(name = COLUNA_PRODUTOSIMILAR_CODIGO)
	@SpaceColumn(name = COLUNA_PRODUTOSIMILAR_CODIGO)
	private Integer similarCodigo;

	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "pro_grpcodigo", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private GrupoProduto grupoProduto;

	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "pro_sgpcodigo", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private SubGrupoProduto subgrupoProduto;

	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "pro_grtcodigo", referencedColumnName = "grt_codigo", insertable = false, updatable = false)
	private GrupoTributacao grupoTributacao;

	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "pro_grvcodigo", referencedColumnName = "grv_codigo", insertable = false, updatable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private GrupoVenda grupoVenda;

	@Transient
	private ProdutoUnidade produtoUnidadeVenda = null;

	@Transient
	private String produtoUnidadeString;

	@Transient
	private ProdutoFilial produtoFilial = null;

	@Transient
	private boolean emCampanha = false;

	@Transient
	private boolean emOferta = false;

	@Transient
	private boolean emPromocao = false;

	@Transient
	private PrecoVenda precoVenda = null;

	@Transient
	@SpaceColumn(name = "pro_estoque")
	private double quantidadeEstoque = 0;

	@Transient
	private boolean isAplicouFatorDeEstoque = false;

	@Transient
	private double quantidadeCompraRapida = 1;

	@Transient
	private ProdutoMidia imagemCapa = null;

	@Transient
	private boolean exibePrecoVisitante = false;

	@Transient
	private boolean emBalanco = false;

	@Transient
	List<ProdutoUnidade> unidades;

	public Produto() {

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

	public String getDescricaoResumida() {
		return descricaoResumida;
	}

	public void setDescricaoResumida(String descricaoResumida) {
		this.descricaoResumida = descricaoResumida;
	}

	public String getDescricaoDetalhada() {
		return descricaoDetalhada;
	}

	public void setDescricaoDetalhada(String descricaoDetalhada) {
		this.descricaoDetalhada = descricaoDetalhada;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public Integer getFlagServico() {
		return flagServico;
	}

	public void setFlagServico(Integer flagServico) {
		this.flagServico = flagServico;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public double getPesoLiquido() {
		return pesoLiquido;
	}

	public void setPesoLiquido(double pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public Double getPesoBruto() {
		return pesoBruto;
	}

	public void setPesoBruto(Double pesoBruto) {
		this.pesoBruto = pesoBruto;
	}

	public Integer getFabricanteCodigo() {
		return fabricanteCodigo;
	}

	public void setFabricanteCodigo(Integer fabricanteCodigo) {
		this.fabricanteCodigo = fabricanteCodigo;
	}

	public Integer getMarcaCodigo() {
		return marcaCodigo;
	}

	public void setMarcaCodigo(Integer marcaCodigo) {
		this.marcaCodigo = marcaCodigo;
	}

	public Integer getSubGrupoCodigo() {
		return subGrupoCodigo;
	}

	public void setSubGrupoCodigo(Integer subGrupoCodigo) {
		this.subGrupoCodigo = subGrupoCodigo;
	}

	@Override
	public Integer getCategoriaCodigo() {
		return categoriaCodigo;
	}

	public void setCategoriaCodigo(Integer categoriaCodigo) {
		this.categoriaCodigo = categoriaCodigo;
	}

	@Override
	public Integer getSubCategoriaCodigo() {
		return subCategoriaCodigo;
	}

	public void setSubCategoriaCodigo(Integer subcategoriaCodigo) {
		this.subCategoriaCodigo = subcategoriaCodigo;
	}

	public int getLinhaCodigo() {
		return linhaCodigo;
	}

	public void setLinhaCodigo(int linhaCodigo) {
		this.linhaCodigo = linhaCodigo;
	}

	public Integer getFlagPesado() {
		return flagPesado;
	}

	public void setFlagPesado(Integer flagPesado) {
		this.flagPesado = flagPesado;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Integer getDepartamentoCodigo() {
		return departamentoCodigo;
	}

	public void setDepartamentoCodigo(Integer departamentoCodigo) {
		this.departamentoCodigo = departamentoCodigo;
	}

	public Integer getFlagPerecivel() {
		return flagPerecivel;
	}

	public void setFlagPerecivel(Integer flagPerecivel) {
		this.flagPerecivel = flagPerecivel;
	}

	public String getCodigoReferenciaFabricante() {
		return codigoReferenciaFabricante;
	}

	public void setCodigoReferenciaFabricante(String codigoReferenciaFabricante) {
		this.codigoReferenciaFabricante = codigoReferenciaFabricante;
	}

	public Integer getFlagCongelado() {
		return flagCongelado;
	}

	public void setFlagCongelado(Integer flagCongelado) {
		this.flagCongelado = flagCongelado;
	}

	public Integer getFlagInflamavel() {
		return flagInflamavel;
	}

	public void setFlagInflamavel(Integer flagInflamavel) {
		this.flagInflamavel = flagInflamavel;
	}

	public Integer getFlagToxico() {
		return flagToxico;
	}

	public void setFlagToxico(Integer flagToxico) {
		this.flagToxico = flagToxico;
	}

	public Integer getFlagTributaPis() {
		return flagTributaPis;
	}

	public void setFlagTributaPis(Integer flagTributaPis) {
		this.flagTributaPis = flagTributaPis;
	}

	public Integer getFlagTributaCofins() {
		return flagTributaCofins;
	}

	public void setFlagTributaCofins(Integer flagTributaCofins) {
		this.flagTributaCofins = flagTributaCofins;
	}

	public Double getAliquotaPis() {
		return aliquotaPis;
	}

	public void setAliquotaPis(Double aliquotaPis) {
		this.aliquotaPis = aliquotaPis;
	}

	public Double getAliquotaCofins() {
		return aliquotaCofins;
	}

	public void setAliquotaCofins(Double aliquotaCofins) {
		this.aliquotaCofins = aliquotaCofins;
	}

	public Integer getFlagControlaLote() {
		return flagControlaLote;
	}

	public void setFlagControlaLote(Integer flagControlaLote) {
		this.flagControlaLote = flagControlaLote;
	}

	public Integer getFlagControlaValidade() {
		return flagControlaValidade;
	}

	public void setFlagControlaValidade(Integer flagControlaValidade) {
		this.flagControlaValidade = flagControlaValidade;
	}

	public Double getAliquotaIpi() {
		return aliquotaIpi;
	}

	public void setAliquotaIpi(Double aliquotaIpi) {
		this.aliquotaIpi = aliquotaIpi;
	}

	public Integer getOrigemCodigo() {
		return origemCodigo;
	}

	public void setOrigemCodigo(Integer origemCodigo) {
		this.origemCodigo = origemCodigo;
	}

	public String getClassificacaoFiscalCodigo() {
		return classificacaoFiscalCodigo;
	}

	public void setClassificacaoFiscalCodigo(String classificacaoFiscalCodigo) {
		this.classificacaoFiscalCodigo = classificacaoFiscalCodigo;
	}

	public Date getDataUltimaAlteracao() {
		return dataUltimaAlteracao;
	}

	public void setDataUltimaAlteracao(Date dataUltimaAlteracao) {
		this.dataUltimaAlteracao = dataUltimaAlteracao;
	}

	public String getHoraUltimaAlteracao() {
		return horaUltimaAlteracao;
	}

	public void setHoraUltimaAlteracao(String horaUltimaAlteracao) {
		this.horaUltimaAlteracao = horaUltimaAlteracao;
	}

	public Integer getCorrelato() {
		return correlato;
	}

	public void setCorrelato(Integer correlato) {
		this.correlato = correlato;
	}

	public Integer getGrupoStCodigo() {
		return grupoStCodigo;
	}

	public void setGrupoStCodigo(Integer grupoStCodigo) {
		this.grupoStCodigo = grupoStCodigo;
	}

	public Double getValorPauta() {
		return valorPauta;
	}

	public void setValorPauta(Double valorPauta) {
		this.valorPauta = valorPauta;
	}

	public Double getAliquotaSt() {
		return aliquotaSt;
	}

	public void setAliquotaSt(Double aliquotaSt) {
		this.aliquotaSt = aliquotaSt;
	}

	public Integer getFlagEva() {
		return flagEva;
	}

	public void setFlagEva(Integer flagEva) {
		this.flagEva = flagEva;
	}

	public Integer getDiasValidade() {
		return diasValidade;
	}

	public void setDiasValidade(Integer diasValidade) {
		this.diasValidade = diasValidade;
	}

	public Integer getFlagUtilizaCertificadoClassificacao() {
		return flagUtilizaCertificadoClassificacao;
	}

	public void setFlagUtilizaCertificadoClassificacao(Integer flagUtilizaCertificadoClassificacao) {
		this.flagUtilizaCertificadoClassificacao = flagUtilizaCertificadoClassificacao;
	}

	public String getCertificadoClassificacao() {
		return certificadoClassificacao;
	}

	public void setCertificadoClassificacao(String certificadoClassificacao) {
		this.certificadoClassificacao = certificadoClassificacao;
	}

	public Integer getAndarMinimo() {
		return andarMinimo;
	}

	public void setAndarMinimo(Integer andarMinimo) {
		this.andarMinimo = andarMinimo;
	}

	public Integer getAndarMaximo() {
		return andarMaximo;
	}

	public void setAndarMaximo(Integer andarMaximo) {
		this.andarMaximo = andarMaximo;
	}

	public Integer getClassificacaoCodigo() {
		return classificacaoCodigo;
	}

	public void setClassificacaoCodigo(Integer classificacaoCodigo) {
		this.classificacaoCodigo = classificacaoCodigo;
	}

	@Override
	public boolean isPermiteVenderSemEstoque() {
		return flagProdutoEncomenda != null && flagProdutoEncomenda == 1;
	}

	public Integer getFlagProdutoEncomenda() {
		return flagProdutoEncomenda;
	}

	public void setFlagProdutoEncomenda(Integer flagProdutoEncomenda) {
		this.flagProdutoEncomenda = flagProdutoEncomenda;
	}

	public String getDescricaoNF1() {
		return descricaoNF1;
	}

	public void setDescricaoNF1(String descricaoNF1) {
		this.descricaoNF1 = descricaoNF1;
	}

	public String getDescricaoNF2() {
		return descricaoNF2;
	}

	public void setDescricaoNF2(String descricaoNF2) {
		this.descricaoNF2 = descricaoNF2;
	}

	public String getDescricaoNF3() {
		return descricaoNF3;
	}

	public void setDescricaoNF3(String descricaoNF3) {
		this.descricaoNF3 = descricaoNF3;
	}

	public String getDescricaoNF4() {
		return descricaoNF4;
	}

	public void setDescricaoNF4(String descricaoNF4) {
		this.descricaoNF4 = descricaoNF4;
	}

	public String getDescricaoNF5() {
		return descricaoNF5;
	}

	public void setDescricaoNF5(String descricaoNF5) {
		this.descricaoNF5 = descricaoNF5;
	}

	public Integer getClassificacaoProducao() {
		return classificacaoProducao;
	}

	public void setClassificacaoProducao(Integer classificacaoProducao) {
		this.classificacaoProducao = classificacaoProducao;
	}

	public Integer getFlagNaoEnviaPalm() {
		return flagNaoEnviaPalm;
	}

	public void setFlagNaoEnviaPalm(Integer flagNaoEnviaPalm) {
		this.flagNaoEnviaPalm = flagNaoEnviaPalm;
	}

	public Integer getFlagImprimeEtiqueta() {
		return flagImprimeEtiqueta;
	}

	public void setFlagImprimeEtiqueta(Integer flagImprimeEtiqueta) {
		this.flagImprimeEtiqueta = flagImprimeEtiqueta;
	}

	public Double getValorPautaIPI() {
		return valorPautaIPI;
	}

	public void setValorPautaIPI(Double valorPautaIPI) {
		this.valorPautaIPI = valorPautaIPI;
	}

	public Integer getGrupoStEntradaCodigo() {
		return grupoStEntradaCodigo;
	}

	public void setGrupoStEntradaCodigo(Integer grupoStEntradaCodigo) {
		this.grupoStEntradaCodigo = grupoStEntradaCodigo;
	}

	public Double getPautaStEntrada() {
		return pautaStEntrada;
	}

	public void setPautaStEntrada(Double pautaStEntrada) {
		this.pautaStEntrada = pautaStEntrada;
	}

	public String getObservacao() {
		return observacao;
	}

	public void setObservacao(String observacao) {
		this.observacao = observacao;
	}

	public int getGrupoVendaCodigo() {
		if (grupoVendaCodigo == null)
			return 0;
		return grupoVendaCodigo;
	}

	public void setGrupoVendaCodigo(Integer grupoVendaCodigo) {
		this.grupoVendaCodigo = grupoVendaCodigo;
	}

	public String getTipoItemCodigo() {
		return tipoItemCodigo;
	}

	public void setTipoItemCodigo(String tipoItemCodigo) {
		this.tipoItemCodigo = tipoItemCodigo;
	}

	public String getCodigoServicoProduto() {
		return codigoServicoProduto;
	}

	public void setCodigoServicoProduto(String codigoServicoProduto) {
		this.codigoServicoProduto = codigoServicoProduto;
	}

	public Integer getGrupoPisCofinsCodigo() {
		return grupoPisCofinsCodigo;
	}

	public void setGrupoPisCofinsCodigo(Integer grupoPisCofinsCodigo) {
		this.grupoPisCofinsCodigo = grupoPisCofinsCodigo;
	}

	public Integer getTipoEnderecoEstoqueCodigo() {
		return tipoEnderecoEstoqueCodigo;
	}

	public void setTipoEnderecoEstoqueCodigo(Integer tipoEnderecoEstoqueCodigo) {
		this.tipoEnderecoEstoqueCodigo = tipoEnderecoEstoqueCodigo;
	}

	public Integer getGrupoTributacaoIpiCodigo() {
		return grupoTributacaoIpiCodigo;
	}

	public void setGrupoTributacaoIpiCodigo(Integer grupoTributacaoIpiCodigo) {
		this.grupoTributacaoIpiCodigo = grupoTributacaoIpiCodigo;
	}

	public GrupoTributacao getGrupoTributacao() {
		return grupoTributacao;
	}

	public void setGrupoTributacao(GrupoTributacao grupoTributacao) {
		this.grupoTributacao = grupoTributacao;
	}

	public ProdutoUnidade getProdutoUnidadeVenda() {
		return produtoUnidadeVenda;
	}

	public void setProdutoUnidadeVenda(ProdutoUnidade produtoUnidadeVenda) {
		this.produtoUnidadeVenda = produtoUnidadeVenda;

		if (produtoUnidadeVenda != null) {
			this.setUnidadeSelecionadaID(produtoUnidadeVenda.getProdutoUnidadePK().getID());
		} else {
			this.setUnidadeSelecionadaID(null);
		}
	}

	public ProdutoFilial getProdutoFilial() {
		return produtoFilial;
	}

	/**
	 * Setado no Itens de pedidos
	 * 
	 * @param produtoFilial
	 */
	public void setProdutoFilial(ProdutoFilial produtoFilial) {
		this.produtoFilial = produtoFilial;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return table;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		Produto.table = arg0;
	}

	public void setUnidadeSelecionadaID(String produtoUnidadeString) {

		this.produtoUnidadeString = produtoUnidadeString;
	}

	public String getUnidadeSelecionadaID() {

		return this.produtoUnidadeString;
	}

	/*
	 * (non-Javadoc) - ProdutoFilial
	 * 
	 * @see br.com.space.api.negocio.modelo.dominio.IProduto#getCusto()
	 */
	@Override
	public double getCusto() {
		return 0;
	}

	/*
	 * (non-Javadoc) - ProdutoFilial+LocalProduto+ProdutoLote
	 * 
	 * @see br.com.space.api.negocio.modelo.dominio.IProduto#getEstoque()
	 */
	@Override
	public double getEstoque() {
		return getQuantidadeEstoque();
	}

	@Override
	public double getEstoqueVisualizacao() {

		if (getProdutoUnidadeVenda() != null) {

			double estoque = getQuantidadeEstoque() / getProdutoUnidadeVenda().getFatorEstoque();

			return estoque;
		}

		return getQuantidadeEstoque();
	}

	public double getQuantidadeEstoque() {
		return quantidadeEstoque;
	}

	public void setQuantidadeEstoque(double quantidadeEstoque) {
		this.quantidadeEstoque = quantidadeEstoque;
	}

	public void setPesoLiquido(Double pesoLiquido) {
		this.pesoLiquido = pesoLiquido;
	}

	public void setLinhaCodigo(Integer linhaCodigo) {
		this.linhaCodigo = linhaCodigo;
	}

	public boolean getEmCampanha() {
		return emCampanha;
	}

	public void setEmCampanha(boolean emCampanha) {
		this.emCampanha = emCampanha;
	}

	public boolean getEmOferta() {
		return emOferta;
	}

	public void setEmOferta(boolean emOferta) {
		this.emOferta = emOferta;
	}

	public boolean getEmPromocao() {
		return emPromocao;
	}

	public void setEmPromocao(boolean emPromocao) {
		this.emPromocao = emPromocao;
	}

	@Override
	public IPrecoVenda getPrecoVenda() {
		return precoVenda;
	}

	@Override
	public void setPrecoVenda(IPrecoVenda precoVenda) {
		this.precoVenda = (PrecoVenda) precoVenda;
	}

	/*
	 * (non-Javadoc) - ProdutoFilial
	 * 
	 * @see
	 * br.com.space.api.negocio.modelo.dominio.IProduto#getFlagDebitoCredito()
	 */
	@Override
	public int getFlagDebitoCredito() {
		if (getProdutoFilial() != null) {
			return getProdutoFilial().getFlagDebitoCreditoProduto();
		}
		return 0;
	}

	/*
	 * (non-Javadoc) - ProdutoFilial
	 * 
	 * @see br.com.space.api.negocio.modelo.dominio.IProduto#getGrupoVenda()
	 */
	@Override
	public IGrupoVenda getGrupoVenda() {
		return grupoVenda;
	}

	/*
	 * (non-Javadoc) - ProdutoFilial
	 * 
	 * @see br.com.space.api.negocio.modelo.dominio.IProduto#getPrazoMaximo()
	 */
	@Override
	public int getPrazoMaximo() {
		if (produtoFilial != null && produtoFilial.isControlePrazoMaximo()) {
			return produtoFilial.getPrazoMaximo();
		}
		return 0;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public int getFlagControlaPrazoMaximo() {

		if (produtoFilial != null) {
			return produtoFilial.getControlePrazoMaximo();
		}

		return 0;
	}

	public static boolean isContemDescricao(Produto produto, String descricao) {
		return (produto.getDescricaoDetalhada() != null
				&& produto.getDescricaoDetalhada().toLowerCase().contains(descricao.toLowerCase()))
				|| (produto.getDescricao() != null
						&& produto.getDescricao().toLowerCase().contains(descricao.toLowerCase()));
	}

	/**
	 * 
	 * @param filtroCodigoOuDescricao
	 * @param filtroGrupoProdutoCodigo
	 * @param produtos
	 * @return
	 */
	public static List<Produto> pesquisarNaLista(String filtroCodigoOuDescricao, String filtroGrupoProdutoCodigo,
			List<Produto> produtos, boolean ignorarDuplicidade) {

		if (produtos == null || produtos.size() == 0) {
			return null;
		}

		HashMap<Integer, Boolean> produtosAdd = new HashMap<Integer, Boolean>();

		List<Produto> result = new ArrayList<Produto>();

		boolean pesquisaProCodigoOuDescricao = filtroCodigoOuDescricao != null
				&& !filtroCodigoOuDescricao.trim().isEmpty();

		boolean pesquisaPorCodigo = pesquisaProCodigoOuDescricao && filtroCodigoOuDescricao.matches("[0-9]+");

		boolean pesquisaPorDescricao = pesquisaProCodigoOuDescricao && !pesquisaPorCodigo;

		boolean pesquisaPorGrupo = filtroGrupoProdutoCodigo != null && !filtroGrupoProdutoCodigo.trim().isEmpty();

		if (!pesquisaProCodigoOuDescricao && !pesquisaPorGrupo) {

			return produtos;

		} else if (pesquisaPorCodigo) {

			Collections.sort(produtos, getComparatorCodigo());
			Produto key = new Produto();
			key.setCodigo(Integer.valueOf(filtroCodigoOuDescricao));

			int indice = Collections.binarySearch(produtos, key, getComparatorCodigo());

			if (indice >= 0) {
				result.add(produtos.get(indice));

				return result;
			}
		} else {

			for (Produto produto : produtos) {

				boolean candidato = false;

				if (pesquisaPorDescricao && pesquisaPorGrupo
						&& (produto.getDescricao() != null || produto.getDescricaoVisualizacao() != null)) {

					candidato = isContemDescricao(produto, filtroCodigoOuDescricao)
							&& Integer.toString(produto.getGrupoCodigo()).equals(filtroCodigoOuDescricao);

				} else if (pesquisaPorDescricao && produto.getDescricao() != null) {

					candidato = isContemDescricao(produto, filtroCodigoOuDescricao);

				} else if (pesquisaPorGrupo) {

					candidato = Integer.toString(produto.getGrupoCodigo()).equals(filtroGrupoProdutoCodigo);

				}

				Integer key = Integer.valueOf(produto.getCodigo());

				if (candidato && ((ignorarDuplicidade && !produtosAdd.containsKey(key)) || !ignorarDuplicidade)) {

					result.add(produto);
					produtosAdd.put(key, true);

				}
			}

			return result;
		}

		return null;
	}

	/**
	 * 
	 * @param dao
	 * @param filtroCodigoOuDescricao
	 * @param filtroGrupoCodigo
	 * @return
	 */
	public static List<Produto> recuperar(GenericoDAO dao, String filtroCodigoOuDescricao, Integer filtroGrupoCodigo) {

		boolean pesquisaPorCodigo = filtroCodigoOuDescricao.matches("[0-9]+");

		String filtoDesc = null;
		String filtoCodigo = null;

		if (pesquisaPorCodigo) {
			filtoCodigo = filtroCodigoOuDescricao;
		} else {
			filtoDesc = filtroCodigoOuDescricao;
		}

		return recuperar(dao, filtoCodigo, filtoDesc, filtroGrupoCodigo, null);
	}

	/**
	 * 
	 * @param dao
	 * @param codigo
	 * @return
	 */
	public static Produto recuperarCodigo(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(Produto.class, COLUNA_CODIGO + " = " + codigo, null);
	}

	/**
	 * 
	 * @param dao
	 * @param filtroCodigo
	 * @param filtroDescricao
	 * @param filtroGrupoCodigo
	 * @return
	 */
	public static List<Produto> recuperar(GenericoDAO dao, String filtroCodigo, String filtroDescricao,
			Integer filtroGrupoCodigo, String filtroSubGrupoCodigo) {

		List<String> whereFragmentos = new ArrayList<>();

		if (filtroCodigo != null && !filtroCodigo.trim().isEmpty()) {

			whereFragmentos.add(Produto.COLUNA_CODIGO + " = " + filtroCodigo);
		}

		if (filtroGrupoCodigo != null && filtroGrupoCodigo > 0) {

			whereFragmentos.add(Produto.COLUNA_GRUPOPRODUTO_CODIGO + " = " + filtroGrupoCodigo);
		}

		if (filtroSubGrupoCodigo != null && !filtroSubGrupoCodigo.trim().isEmpty()) {
			whereFragmentos.add(Produto.COLUNA_GRUPOPRODUTO_CODIGO + " = " + filtroSubGrupoCodigo);
		}

		if (filtroDescricao != null && !filtroDescricao.trim().isEmpty()) {

			whereFragmentos.add("(" + COLUNA_DESCRICAO + " like '%" + filtroDescricao + "%' or "
					+ COLUNA_DESCRICAO_DETALHADA + " like '%" + filtroDescricao + "%')");
		}

		String where = GenericoDAO.criarWhere(whereFragmentos);

		return dao.list(Produto.class, where, null, null, null);
	}

	public static List<Produto> recuperarProdutosDestaqueVendaUsandoResultSet(GenericoDAO dao, int filialCodigo,
			int precoBase, boolean permiteProdutoSemEstoque, GerentePedido gerentePedido, boolean preencherAtributos) {

		return recuperarProdutosDestaqueVendaUsandoResultSet(dao, filialCodigo, precoBase, null, null,
				permiteProdutoSemEstoque, gerentePedido, preencherAtributos);

	}

	public static List<Produto> recuperarProdutosDestaqueVendaUsandoResultSet(GenericoDAO dao, int filialCodigo,
			int precoBase, Integer grupoCodigo, Integer subGrupoCodigo, boolean permiteProdutoSemEstoque,
			GerentePedido gerentePedido, boolean preencherAtributos) {

		List<String> whereFragmentos = new ArrayList<>();

		if (grupoCodigo != null) {

			whereFragmentos.add(COLUNA_GRUPOPRODUTO_CODIGO + " = " + grupoCodigo);
		}

		if (subGrupoCodigo != null) {

			whereFragmentos.add(COLUNA_SUBGRUPOPRODUTO_CODIGO + " = " + subGrupoCodigo);
		}

		String whereAdicional = GenericoDAO.criarWhere(whereFragmentos);

		String fromAdicional = " inner join produtodestaque ON pdq_procodigo = pro_codigo and pdq_filcodigo = pfi_filcodigo ";

		String select = getSelectPadraoVenda(filialCodigo, precoBase, fromAdicional, whereAdicional, null,
				permiteProdutoSemEstoque);

		List<Produto> produtos = null;

		try {
			produtos = dao.list(Produto.class, select, null);

			if (preencherAtributos) {
				preencherAtributosProduto(dao, filialCodigo, gerentePedido, produtos);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return produtos;
	}

	public static List<Produto> recuperarProdutosOfertaVendaUsandoResultSet(GenericoDAO dao, int filialCodigo,
			int precoBase, boolean preencherAtributos, Date data, GerentePedido gerentePedido,
			boolean permiteProdutoSemEstoque) {

		String fromAdicional = " inner join oferta on oft_procodigo = pro_codigo and "
				+ Oferta.getWhereSemHora(filialCodigo, data, null);

		String select = getSelectPadraoVenda(filialCodigo, precoBase, fromAdicional, null, "oft_procodigo",
				permiteProdutoSemEstoque);

		List<Produto> produtos = null;

		try {
			produtos = dao.list(Produto.class, select, null);
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (preencherAtributos) {
			preencherAtributosProduto(dao, filialCodigo, gerentePedido, produtos);
		}

		return produtos;
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param produtos
	 */
	public static void preencherAtributosProduto(GenericoDAO dao, int filialCodigo, GerentePedido gerentePedido,
			List<Produto> produtos) {

		if (!ListUtil.isValida(produtos) || filialCodigo <= 0 || dao == null) {
			return;
		}

		List<Integer> produtosEmOferta = null;
		List<Integer> produtosEmCampanha = null;
		List<Integer> produtosEmPromocao = null;

		List<Integer> produtosEmBalanco = null;
		Date date = new Date();

		try {
			produtosEmOferta = Oferta.recuperarCodigoProdutosEmOferta(dao, filialCodigo, date, false);

			produtosEmCampanha = CampanhaItem.recuperarCodigoProdutosEmCampanha(dao, filialCodigo, date);

			produtosEmPromocao = Promocao.recuperarCodigoProdutosEmPromocao(dao, date, gerentePedido);

			produtosEmBalanco = br.com.space.api.negocio.modelo.negocio.estoque.Estoque
					.recuperarCodigoProdutosEmBalanco(filialCodigo, dao, produtos);

		} catch (Exception e) {
			e.printStackTrace();
		}

		List<GrupoProduto> grupoProdutos = GrupoProduto.recuperar(dao, produtos);

		List<SubGrupoProduto> subGrupoProdutos = SubGrupoProduto.recuperar(dao, produtos);

		List<GrupoVenda> grupoVendas = GrupoVenda.recuperar(dao, produtos);

		List<ProdutoMidia> imagensCapa = ProdutoMidia.recuperarImagensAtivasCapa(dao, produtos);

		if (imagensCapa != null)
			Collections.sort(imagensCapa);

		boolean existeGP = grupoProdutos != null && grupoProdutos.size() > 0;
		boolean existeSGP = subGrupoProdutos != null && subGrupoProdutos.size() > 0;

		boolean existeGV = ListUtil.isValida(grupoVendas);

		boolean existeIC = imagensCapa != null && imagensCapa.size() > 0;

		boolean existeOfertas = produtosEmOferta != null && produtosEmOferta.size() > 0;
		boolean existeCampanhas = produtosEmCampanha != null && produtosEmCampanha.size() > 0;
		boolean existePromocoes = produtosEmPromocao != null && produtosEmPromocao.size() > 0;

		boolean existeProdutoEmBalanco = ListUtil.isValida(produtosEmBalanco);

		GrupoProduto keyGP = new GrupoProduto();
		SubGrupoProduto keySGP = new SubGrupoProduto();

		GrupoVenda keyGV = new GrupoVenda();

		for (Produto produto : produtos) {

			if (existeIC) {

				montarImagemCapaProduto(produto, imagensCapa);

			}

			if (existeGP && produto.getGrupoCodigo() > 0) {

				keyGP.setCodigo(produto.getGrupoCodigo());

				int indice = Collections.binarySearch(grupoProdutos, keyGP);

				if (indice >= 0) {
					produto.setGrupoProduto(grupoProdutos.get(indice));
				}
			}

			if (existeSGP && produto.getSubGrupoCodigo() != null && produto.getSubGrupoCodigo() > 0) {

				keySGP.setCodigo(produto.getSubGrupoCodigo());

				int indice = Collections.binarySearch(subGrupoProdutos, keySGP);

				if (indice >= 0) {
					produto.setSubgrupoProduto(subGrupoProdutos.get(indice));
				}
			}

			if (existeGV && produto.getGrupoVendaCodigo() > 0) {

				keyGV.setCodigo(produto.getGrupoVendaCodigo());

				int indice = Collections.binarySearch(grupoVendas, keyGV);

				if (indice >= 0) {
					produto.setGrupoVenda(grupoVendas.get(indice));
				}
			}

			if (existeOfertas) {
				produto.setEmOferta(produtosEmOferta.contains(produto.getCodigo()));
			}

			if (existeCampanhas) {
				produto.setEmCampanha(Collections.binarySearch(produtosEmCampanha, produto.getCodigo()) >= 0);
			}

			if (existePromocoes) {
				produto.setEmPromocao(Collections.binarySearch(produtosEmPromocao, produto.getCodigo()) >= 0);
			}

			if (existeProdutoEmBalanco) {
				produto.setEmBalanco(produtosEmBalanco.contains(produto.getCodigo()));

			}

			produto.setExibePrecoVisitante(produto.exibirPrecoVisitante(produto.codigo, filialCodigo, dao));

			produto.setUnidades(ProdutoUnidade.recuperarUnidadesProdutoParaVenda(dao, produto.getCodigo()));
		}
	}

	public static void montarImagemCapaProduto(Produto produto, List<ProdutoMidia> imagensCapa) {

		ProdutoMidia keyIC = new ProdutoMidia();
		keyIC.setProdutoCodigo(produto.getCodigo());

		int indice = Collections.binarySearch(imagensCapa, keyIC);

		if (indice >= 0) {

			produto.setImagemCapa((ProdutoMidia) imagensCapa.get(indice).clone());

			imagensCapa.get(indice)
					.setEnderecoMidia(Fabrica.gerarEnderecoImagemSemPasta(imagensCapa.get(indice).getEnderecoMidia()));

			produto.setImagemCapa(imagensCapa.get(indice));
		}

	}

	/**
	 * Preenche todos os atributos do produto que não foram recuperados. Se
	 * estão em oferta, em campanha, em promoção, unidade de venda, Preço de
	 * Venda e Estoque.
	 */
	@Override
	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, boolean atualizarFlags,
			boolean forcarRefreshPrecos) throws Exception {

		preencherAtributos(dao, gerentePedido, null, atualizarFlags, forcarRefreshPrecos);

	}

	@Override
	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, ProdutoUnidade produtoUnidade,
			boolean atualizarFlags, boolean forcarRefreshPrecos) throws Exception {

		preencherAtributos(dao, gerentePedido, produtoUnidade, atualizarFlags, forcarRefreshPrecos, true);
	}

	@Override
	public void preencherAtributos(GenericoDAO dao, GerentePedido gerentePedido, ProdutoUnidade produtoUnidade,
			boolean atualizarFlags, boolean forcarRefreshPrecos, boolean recuperaEstoque) throws Exception {

		Date date = new Date();

		int filialCodigo = gerentePedido.getFilial().getCodigo();

		if (produtoUnidade == null) {
			setProdutoUnidadeVenda(ProdutoUnidade.recuperar(dao, codigo, 1));
		} else {
			setProdutoUnidadeVenda(produtoUnidade);
		}

		if (getGrupoVendaCodigo() > 0 && grupoVenda == null) {
			setGrupoVenda(GrupoVenda.recuperar(dao, getGrupoVendaCodigo()));
		}

		Precificacao precificacao = gerentePedido.getPrecificacao();
		/*
		 * Preço de Venda do Produto
		 */
		if (precificacao != null) {

			PrecoVenda pv = precificacao.obterPrecoVendaSugerido(this, getProdutoUnidadeVenda(), null,
					forcarRefreshPrecos);

			if (pv != null)
				setEmOferta(pv.getNumeroOferta() != 0);
		} else {
			/*
			 * Faz o mesmo teste que o de baixo pois se tiver precificação
			 */
			if (atualizarFlags) {
				setEmOferta(Oferta.isProdutoEmOferta(dao, filialCodigo, date, codigo, false));
			}
		}

		if (atualizarFlags) {
			setEmCampanha(CampanhaItem.isProdutoEmCampanha(dao, filialCodigo, date, codigo));

			setEmPromocao(Promocao.isProdutoEmPromocao(dao, date, gerentePedido, this));
		}

		Estoque estoque = gerentePedido.getEstoque();
		/*
		 * Estoque disponível para venda.
		 */
		if (estoque != null && recuperaEstoque) {

			try {
				quantidadeEstoque = recuperarEstoqueDisponivel(estoque, filialCodigo);

				isAplicouFatorDeEstoque = true;

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		setExibePrecoVisitante(exibirPrecoVisitante(codigo, filialCodigo, dao));

		atulizarEmbalanco(filialCodigo, dao);
	}

	public double recuperarEstoqueDisponivel(Estoque estoque, int filialCodigo) {

		double quantidadeEstoque = estoque.recuperarEstoqueDisponivel(filialCodigo, this, 0, null,
				FlagTipoEstoque.DisponivelVendaGeral);

		return quantidadeEstoque;
	}

	public SubGrupoProduto getSubgrupoProduto() {
		return this.subgrupoProduto;
	}

	public void setSubgrupoProduto(SubGrupoProduto subgrupoProduto) {
		this.subgrupoProduto = subgrupoProduto;
	}

	public GrupoProduto getGrupoProduto() {
		return grupoProduto;
	}

	public void setGrupoProduto(GrupoProduto grupoProduto) {
		this.grupoProduto = grupoProduto;
	}

	public void setGrupoVenda(GrupoVenda grupoVenda) {
		this.grupoVenda = grupoVenda;
	}

	public static Comparator<Produto> getComparatorCodigo() {

		if (comparatorCodigo == null) {

			comparatorCodigo = new Comparator<Produto>() {

				@Override
				public int compare(Produto arg0, Produto arg1) {
					return Integer.compare(arg0.getCodigo(), arg1.getCodigo());
				}
			};

		}

		return comparatorCodigo;
	}

	public static Comparator<Produto> getComparatorDescricao() {

		if (comparatorDescricao == null) {

			comparatorDescricao = new Comparator<Produto>() {

				@Override
				public int compare(Produto o1, Produto o2) {

					if (o1.getDescricaoVisualizacao() == null) {
						return -1;
					}

					return o1.getDescricaoVisualizacao().compareTo(o2.getDescricaoVisualizacao());
				}
			};
		}

		return comparatorDescricao;
	}

	@Override
	public int getGrupoCodigo() {

		if (grupoProduto != null) {
			return grupoProduto.getCodigo();
		}
		return grupoCodigo;
	}

	public void setGrupoCodigo(int grupoCodigo) {
		this.grupoCodigo = grupoCodigo;
	}

	public int getGrupoTributacaoCodigo() {
		return grupoTributacaoCodigo;
	}

	public void setGrupoTributacaoCodigo(int grupoTributacaoCodigo) {
		this.grupoTributacaoCodigo = grupoTributacaoCodigo;
	}

	public Integer getSimilarCodigo() {
		return similarCodigo;
	}

	public void setSimilarCodigo(Integer similarCodigo) {
		this.similarCodigo = similarCodigo;
	}

	@Override
	public String toString() {
		return "Produto [codigo=" + codigo + ", descricao=" + descricao + "]";
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param precoBase
	 * @param permiteProdutoSemEstoque
	 * @return
	 * @throws Exception
	 */
	public static List<Produto> recuperarTodosProdutosVendaUsandoResultSet(GenericoDAO dao, int filialCodigo,
			int precoBase, boolean permiteProdutoSemEstoque) throws Exception {

		String select = getSelectPadraoVenda(filialCodigo, precoBase, null, null, null, permiteProdutoSemEstoque);

		return dao.list(Produto.class, select, null);
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param precoBase
	 * @param produto
	 * @param permiteProdutoSemEstoque
	 * @return
	 * @throws Exception
	 */
	public static List<Produto> recuperarProdutosSimilaresVendaUsandoResultSet(GenericoDAO dao, int filialCodigo,
			int precoBase, Produto produto, boolean permiteProdutoSemEstoque) throws Exception {

		if (produto == null || (produto.getSimilarCodigo() == null || produto.getSimilarCodigo() == 0)) {
			return null;
		}

		String where = COLUNA_PRODUTOSIMILAR_CODIGO + " = " + produto.getSimilarCodigo() + " and " + COLUNA_CODIGO
				+ " <> " + produto.getCodigo();

		String select = getSelectPadraoVenda(filialCodigo, precoBase, null, where, null, permiteProdutoSemEstoque);

		return dao.list(Produto.class, select, null);
	}

	/**
	 * 
	 * @param dao
	 * @param produtoCodigo
	 * @param filialCodigo
	 * @param precoBase
	 * @param permiteProdutoSemEstoque
	 * @return
	 */
	public static Produto recuperarProdutoParaVenda(GenericoDAO dao, int produtoCodigo, int filialCodigo, int precoBase,
			boolean permiteProdutoSemEstoque) {

		String where = COLUNA_CODIGO + " = " + produtoCodigo;

		String select = getSelectPadraoVenda(filialCodigo, precoBase, null, where, null, permiteProdutoSemEstoque);

		return dao.uniqueResult(Produto.class, select);
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param precoBase
	 * @param grupoCodigo
	 * @param subGrupoCodigo
	 * @param descricao
	 * @param codigo
	 * @param pesquisarSubGrupoNull
	 * @param permiteProdutoSemEstoque
	 * @param preencherAtributos
	 * @return
	 * @throws Exception
	 */
	public static List<Produto> recuperarProdutosVendaUsandoResultSet(GenericoDAO dao, int filialCodigo, int precoBase,
			Integer grupoCodigo, Integer subGrupoCodigo, String descricao, Integer codigo, GerentePedido gerentePedido,
			boolean pesquisarSubGrupoNull, boolean permiteProdutoSemEstoque, boolean preencherAtributos)
			throws Exception {

		// List<String> whereFragmentos = new ArrayList<>();
		StringBuilder where = new StringBuilder();

		if (grupoCodigo != null && grupoCodigo > 0) {
			if (where.length() > 0)
				where.append(" and ");

			where.append(COLUNA_GRUPOPRODUTO_CODIGO).append(" = ").append(grupoCodigo);
		}

		if (subGrupoCodigo != null && subGrupoCodigo > 0 && !pesquisarSubGrupoNull) {
			if (where.length() > 0)
				where.append(" and ");

			where.append(COLUNA_SUBGRUPOPRODUTO_CODIGO).append(" = ").append(subGrupoCodigo);

		} else if (pesquisarSubGrupoNull) {
			if (where.length() > 0)
				where.append(" and ");

			where.append(COLUNA_SUBGRUPOPRODUTO_CODIGO).append(" = 0 or pro_sgpcodigo is null");
		}

		if (descricao != null && !descricao.trim().isEmpty()) {
			if (where.length() > 0)
				where.append(" and ");

			where.append("(" + COLUNA_DESCRICAO + " like '%").append(descricao).append("%' or ")
					.append(COLUNA_DESCRICAO_DETALHADA).append(" like '%").append(descricao).append("%')");
		}

		if (codigo != null && codigo > 0) {
			if (where.length() > 0)
				where.append(" and ");

			where.append(COLUNA_CODIGO).append(" = ").append(codigo);
		}

		// String where = GenericoDAO.criarWhere(whereFragmentos);

		String select = getSelectPadraoVenda(filialCodigo, precoBase, null, where.toString(), null,
				permiteProdutoSemEstoque);

		List<Produto> produtos = null;

		try {
			produtos = dao.list(Produto.class, select, null);

			if (preencherAtributos) {
				preencherAtributosProduto(dao, filialCodigo, gerentePedido, produtos);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return produtos;
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param precoBase
	 * @param grupoCodigo
	 * @param subGrupoCodigo
	 * @param pesquisarSubGrupoNull
	 * @param permiteProdutoSemEstoque
	 * @param preencherAtributos
	 * @return
	 * @throws Exception
	 */
	public static List<Produto> recuperarProdutosVendaUsandoResultSet(GenericoDAO dao, int filialCodigo, int precoBase,
			Integer grupoCodigo, Integer subGrupoCodigo, GerentePedido gerentePedido, boolean pesquisarSubGrupoNull,
			boolean permiteProdutoSemEstoque, boolean preencherAtributos) throws Exception {

		return recuperarProdutosVendaUsandoResultSet(dao, filialCodigo, precoBase, grupoCodigo, subGrupoCodigo, null,
				null, gerentePedido, pesquisarSubGrupoNull, permiteProdutoSemEstoque, preencherAtributos);
	}

	/**
	 * 
	 * @param filialCodigo
	 * @param precoBase
	 * @param fromAdicional
	 * @param whereAdicional
	 * @param orederBy
	 * @param permiteProdutoSemEstoque
	 * @return
	 */
	public static String getSelectPadraoVenda(int filialCodigo, int precoBase, String fromAdicional,
			String whereAdicional, String orederBy, boolean permiteProdutoSemEstoque) {

		return getSelectPadraoVenda(filialCodigo, precoBase, fromAdicional, whereAdicional, orederBy,
				permiteProdutoSemEstoque, true);
	}

	/**
	 * 
	 * @param filialCodigo
	 * @param precoBase
	 * @param fromAdicional
	 * @param whereAdicional
	 * @param orederBy
	 * @param permiteProdutoSemEstoque
	 * @param permiteProdutoSemPreco
	 * @return
	 */
	public static String getSelectPadraoVenda(int filialCodigo, int precoBase, String fromAdicional,
			String whereAdicional, String orederBy, boolean permiteProdutoSemEstoque, boolean permiteProdutoSemPreco) {

		String subSelectEstoque = Estoque.getSubSelectEstoqueProduto(filialCodigo, null);

		String fromProduto = getFromProdutoPadraoVenda(filialCodigo, precoBase, fromAdicional)
				+ " inner join parametro on par_filcodigo =  pfi_filcodigo ";

		StringBuilder select = new StringBuilder().append("select produto.*, ").append(subSelectEstoque)
				.append(" from ").append(fromProduto);

		if (StringUtil.isValida(whereAdicional))
			select.append(" where ").append(whereAdicional.replace("where", ""));

		if (!permiteProdutoSemEstoque)
			select.append(" having pro_estoque > 0");

		select.append(" order by ");

		if (StringUtil.isValida(orederBy))
			select.append(orederBy.replace("order by", ""));
		else
			select.append("pro_codigo");

		return select.toString();
	}

	/**
	 * 
	 * @param filialCodigo
	 * @param precoBase
	 * @param fromAdicional
	 * @return
	 */
	public static String getFromProdutoPadraoVenda(int filialCodigo, int precoBase, String fromAdicional) {

		StringBuilder fromProduto = new StringBuilder().append("produto inner join produtofilial")
				.append(" ON pro_codigo = pfi_procodigo and").append(" pfi_filcodigo = ").append(filialCodigo)
				.append(" and pfi_libvenda = 1 ").append(" and pfi_libvendaweb = 1 ").append(" and pfi_inativo = 0 ")
				.append(" and pro_ativo = 1 ");

		if (precoBase > 0)
			fromProduto.append(" inner join produtopreco on pro_codigo = ppr_procodigo")
					.append(" and ppr_filcodigo = pfi_filcodigo and ppr_prbcodigo = ").append(precoBase)
					.append(" and ppr_precovenda > 0");

		if (StringUtil.isValida(fromAdicional))
			fromProduto.append(" ").append(fromAdicional);

		return fromProduto.toString();

	}

	public static List<Produto> recuperar(GenericoDAO dao) {

		String select = "select * from "
				+ "produto this_ left outer join grupopro grupoprodu2_ on this_.pro_grpcodigo=grupoprodu2_.grp_codigo left outer join grupotrib grupotribu3_ on this_.pro_grtcodigo=grupotribu3_.grt_codigo left outer join cst cst4_ on grupotribu3_.grt_cstcodigo=cst4_.cst_codigo  left outer join subgrupopro subgrupopr5_ on this_.pro_sgpcodigo=subgrupopr5_.sgp_codigo order by this_.pro_desc asc";

		return dao.list(Produto.class, select);

	}

	@Override
	public double getPeso() {
		if (produtoUnidadeVenda != null && pesoBruto > 0) {
			return pesoBruto * produtoUnidadeVenda.getFatorEstoque();
		}

		return 0;
	}

	@Override
	public String getUnidadeDescricao() {

		if (produtoUnidadeVenda != null) {
			return produtoUnidadeVenda.getUnidadeDescricao();
		}

		return null;
	}

	@Override
	public List<Produto> getProdutosKit() {

		return null;
	}

	@Override
	public ProdutoMidia getImagemCapa() {
		return imagemCapa;
	}

	@Override
	public void setImagemCapa(ProdutoMidia imagemCapa) {
		this.imagemCapa = imagemCapa;
	}

	@Override
	public int compareTo(Produto o) {
		return Integer.compare(codigo, o.getCodigo());
	}

	/**
	 * Recupera os produtos similares de um produto de acordo com o código.
	 * 
	 * @param dao
	 * @param produtoSimilarCodigo
	 * @return
	 */
	public static List<Produto> recuperarProdutosSimilaresAtivos(GenericoDAO dao, int produtoSimilarCodigo) {
		if (produtoSimilarCodigo != 0)

			return dao.list(Produto.class,
					COLUNA_ATIVO + " = 1 AND " + COLUNA_PRODUTOSIMILAR_CODIGO + " = " + produtoSimilarCodigo, null,
					null, null);
		else
			return null;
	}

	private boolean exibirPrecoVisitante(int codigoProduto, int codigoFilial, GenericoDAO dao) {
		ProdutoFilial prod = ProdutoFilial.recuperarUnico(dao, codigoFilial, codigoProduto);
		return prod == null || prod.getExibePrecoVisitante() == 0 ? false : true;
	}

	/**
	 * Recupera todos os produtos de uma marca de acordo com seu código.
	 * 
	 * @param dao
	 * @param codigoMarca
	 * @return
	 */
	public static List<Produto> recuperarProdutosMarca(GenericoDAO dao, int codigoMarca) {
		return dao.list(Produto.class, COLUNA_MARCA_CODIGO + " = " + codigoMarca, null, null, null);
	}

	@Override
	public boolean eKit() {
		return false;
	}

	@Override
	public boolean eProduto() {
		return true;
	}

	@Override
	public GrupoWidget getGrupoWidget() {
		return grupoProduto;
	}

	@Override
	public SubGrupoWidget getSubGrupoWidget() {
		return subgrupoProduto;
	}

	@Override
	public String getPrecoVendaVisualizacao() {

		if (getPrecoVenda() != null) {
			return Conversao.formatarValor2(getPrecoVenda().getPrecoVenda());
		}

		return null;
	}

	@Override
	public String getPrecoTabelaVisualizacao() {

		if (getPrecoVenda() != null) {
			return Conversao.formatarValor2(getPrecoVenda().getPrecoTabela());
		}

		return null;
	}

	@Override
	public String getPrecoUnitarioVisualizacao() {

		if (produtoUnidadeVenda != null && getPrecoVenda() != null) {

			double valorUnitario = Conversao
					.arredondar(getPrecoVenda().getPrecoSugerido() / produtoUnidadeVenda.getQuantidade(), 3);
			return Conversao.formatarValor3(valorUnitario);
		}

		return null;
	}

	@Override
	public String getDescricaoVisualizacao() {

		if (StringUtil.isValida(descricaoDetalhada)) {

			return descricaoDetalhada;

		} else {

			return descricao;
		}
	}

	@Override
	public boolean exibirPrecoUnitario() {
		return produtoUnidadeVenda != null && produtoUnidadeVenda.getQuantidade() > 1;
	}

	@Override
	public String getValueArgument() {
		return Integer.toString(getCodigo());
	}

	@Override
	public double getQuantidadeCompraRapida() {
		return quantidadeCompraRapida;
	}

	@Override
	public void setQuantidadeCompraRapida(double quantidadeCompraRapida) {
		this.quantidadeCompraRapida = quantidadeCompraRapida;
	}

	@Override
	public boolean isExibePrecoVisitante() {
		return exibePrecoVisitante;
	}

	@Override
	public boolean isEmBalanco() {
		return emBalanco;
	}

	public void setEmBalanco(boolean emBalanco) {
		this.emBalanco = emBalanco;
	}

	public void setExibePrecoVisitante(boolean exibePrecoVisitante) {
		this.exibePrecoVisitante = exibePrecoVisitante;
	}

	@Override
	public boolean atulizarEmbalanco(int codigoFilial, GenericoDAO dao) {
		emBalanco = Estoque.produtoEmBalanco(codigo, codigoFilial, dao);
		return emBalanco;
	}

	@Override
	public double getValorAcrescimoDespesaEntrega() {
		return valorAcrescimoDespesaEntrega;
	}

	public void setValorAcrescimoDespesaEntrega(double valorAcrescimoDespesaEntrega) {
		this.valorAcrescimoDespesaEntrega = valorAcrescimoDespesaEntrega;
	}

	public void setUnidades(List<ProdutoUnidade> unidades) {
		this.unidades = unidades;
	}

	@Override
	public List<ProdutoUnidade> getUnidades() {
		return unidades;
	}
}
