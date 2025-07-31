/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.negocio.modelo.dominio.IPromocao;
import br.com.space.api.negocio.modelo.negocio.GerentePedido;
import br.com.space.api.negocio.modelo.negocio.PromocaoVenda;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.estoque.Produto;
import br.com.space.spacewebII.modelo.padrao.interfaces.GeradorMensagem;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

@Entity
@Table(name = "promocao")
@XmlRootElement
@SuppressWarnings("rawtypes")
public class Promocao implements IPromocao, Serializable, IPersistent, GeradorMensagem {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected PromocaoPK promocaoPK;

	@ManyToOne
	@Fetch(FetchMode.JOIN)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = "prm_procodbnf", insertable = false, updatable = false)
	private Produto produtoBonificado;

	@Column(name = "prm_faixaini")
	private Double faixaValorInicio;

	@Column(name = "prm_faixafim")
	private Double faixaValorFim;

	@Column(name = "prm_przprom")
	private Integer prazoPromocao;

	@Column(name = "prm_vencfixo")
	@Temporal(TemporalType.DATE)
	private Date dataVencimentoFixo;

	@Column(name = "prm_data")
	@Temporal(TemporalType.DATE)
	private Date data;

	@Column(name = "prm_situacao")
	private String situacao;

	@Column(name = "prm_tipoprom")
	private String tipoPromocao;

	@Column(name = "prm_dataini")
	@Temporal(TemporalType.DATE)
	private Date dataInicio;

	@Column(name = "prm_datafim")
	@Temporal(TemporalType.DATE)
	private Date dataFim;

	@Column(name = "prm_fisico")
	private Integer flagPessoaFisica;

	@Column(name = "prm_juridico")
	private Integer flagPessoaJuridica;

	@Column(name = "prm_tppeditem")
	private Integer tipoPromocaoPedidoItem;

	@Column(name = "prm_tipoitem")
	private Integer tipoPromocaoItem;

	@Column(name = "prm_procodigo")
	private Integer produtoCodigo;

	@Column(name = "prm_lprcodigo")
	private Integer linhaProdutoCodigo;

	@Column(name = "prm_mrccodigo")
	private Integer marcaProdutoCodigo;

	@Column(name = "prm_fabcodigo")
	private Integer fabricanteCodigo;

	@Column(name = "prm_ctpcodigo")
	private Integer categoriaCodigo;

	@Column(name = "prm_scpcodigo")
	private Integer subCategoriaCodigo;

	@Column(name = "prm_grpcodigo")
	private int grupoCodigo;

	@Column(name = "prm_sgpcodigo")
	private int subGrupoCodigo;

	@Column(name = "prm_idnormal")
	private Double indiceNormal;

	@Column(name = "prm_idoferta")
	private Double indiceOferta;

	@Column(name = "prm_descmaxnor")
	private Double descontoMaximoNormal;

	@Column(name = "prm_acremaxnor")
	private Double acrescimoMaximoNormal;

	@Column(name = "prm_descmaxofe")
	private Double descontoMaximoOferta;

	@Column(name = "prm_acremaxofe")
	private Double acrescimoMaximoOferta;

	@Column(name = "prm_qtdeini")
	private Double quantidadeInicio;

	@Column(name = "prm_qtdefin")
	private Double quantidadeFim;

	@Column(name = "prm_prodtotal")
	private Integer flagQuantidadeProdutoTotal;

	@Column(name = "prm_sescodigo")
	private Integer sessaoCodigo;

	@Column(name = "prm_datacanc")
	@Temporal(TemporalType.DATE)
	private Date dataCancelamento;

	@Column(name = "prm_horacanc")
	private String horaCancelamento;

	@Column(name = "prm_usrcanc")
	private String usuarioCancelamento;

	@Column(name = "prm_clbcodigo")
	private Integer colaboradorCodigo;

	@Column(name = "prm_clbcodigos")
	private Integer colaboradorCodigoSupervisor;

	@Column(name = "prm_clbcodigog")
	private Integer colaboradorCodigoGerente;

	@Column(name = "prm_pescodigo")
	private Integer pessoaCodigo;

	@Column(name = "prm_desc")
	private String descricao;

	@Column(name = "prm_qtdemax")
	private Double quantidadeMaxima;

	@Column(name = "prm_qtdevendida")
	private Double quantidadeVendida;

	@Column(name = "prm_qtdebnf")
	private Double quantidadeBonificada;

	@Column(name = "prm_natcodbnf")
	private String naturezaOperacaoBonificacao;

	@Column(name = "prm_fpgcodbnf")
	private String formaPagamentoBonificacao;

	@Column(name = "prm_cpgcodbnf")
	private Integer condicaoPagamentoBonificacao;

	@Column(name = "prm_interna")
	private Integer flagInterna;

	@Column(name = "prm_externa")
	private Integer flagExterna;

	@Column(name = "prm_gcocodigoi")
	private Integer grupoComissaoNormalInterna;

	@Column(name = "prm_gcocodigoe")
	private Integer grupoComissaoNormalExterna;

	@Column(name = "prm_gcocodigooi")
	private Integer grupoComissaoOfertaInterna;

	@Column(name = "prm_gcocodigooe")
	private Integer grupoComissaoOfertaExterna;

	@Column(name = "prm_opcaoqtde")
	private Integer flagOpcaoQuantidade;

	@Column(name = "prm_tprcodigo")
	private Integer tabelaPrecoCodigo;
	
	@Column(name = "prm_tprcodigoq")
	private Integer tabelaPrecoQuantidadeCodigo;
	
	@Column(name = "prm_prbcodigoq")
	private Integer precoBaseQuantidadeCodigo;

	@Column(name = "prm_perdescit")
	private Double percentualDescontoItem;

	@Column(name = "prm_atvcodigo")
	private int atividadeCodigo;

	@Column(name = "prm_unppadrao")
	private Integer unidadePadrao;

	public Promocao() {
	}

	public Promocao(PromocaoPK promocaoPK) {
		this.promocaoPK = promocaoPK;
	}

	public Promocao(int prmFilcodigo, int prmNumero) {
		this.promocaoPK = new PromocaoPK(prmFilcodigo, prmNumero);
	}

	@Override
	public int getNumero() {
		return this.promocaoPK.getNumero();
	}

	public PromocaoPK getPromocaoPK() {
		return promocaoPK;
	}

	public void setPromocaoPK(PromocaoPK promocaoPK) {
		this.promocaoPK = promocaoPK;
	}

	public Double getFaixaValorInicio() {
		return faixaValorInicio;
	}

	public void setFaixaValorInicio(Double faixaValorInicio) {
		this.faixaValorInicio = faixaValorInicio;
	}

	public Double getFaixaValorFim() {
		return faixaValorFim;
	}

	public void setFaixaValorFim(Double faixaValorFim) {
		this.faixaValorFim = faixaValorFim;
	}

	public Integer getPrazoPromocao() {
		return prazoPromocao;
	}

	public void setPrazoPromocao(Integer prazoPromocao) {
		this.prazoPromocao = prazoPromocao;
	}

	public Date getDataVencimentoFixo() {
		return dataVencimentoFixo;
	}

	public void setDataVencimentoFixo(Date dataVencimentoFixo) {
		this.dataVencimentoFixo = dataVencimentoFixo;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getSituacao() {
		return situacao;
	}

	public void setSituacao(String situacao) {
		this.situacao = situacao;
	}

	public String getTipoPromocao() {
		return tipoPromocao;
	}

	public void setTipoPromocao(String tipoPromocao) {
		this.tipoPromocao = tipoPromocao;
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

	public Integer getFlagPessoaFisica() {
		return flagPessoaFisica;
	}

	public void setFlagPessoaFisica(Integer flagPessoaFisica) {
		this.flagPessoaFisica = flagPessoaFisica;
	}

	public Integer getFlagPessoaJuridica() {
		return flagPessoaJuridica;
	}

	public void setFlagPessoaJuridica(Integer flagPessoaJuridica) {
		this.flagPessoaJuridica = flagPessoaJuridica;
	}

	public Integer getTipoPromocaoPedidoItem() {
		return tipoPromocaoPedidoItem;
	}

	public void setTipoPromocaoPedidoItem(Integer tipoPromocaoPedidoItem) {
		this.tipoPromocaoPedidoItem = tipoPromocaoPedidoItem;
	}

	public Integer getTipoPromocaoItem() {
		return tipoPromocaoItem;
	}

	public void setTipoPromocaoItem(Integer tipoPromocaoItem) {
		this.tipoPromocaoItem = tipoPromocaoItem;
	}

	public Integer getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(Integer produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public Integer getLinhaProdutoCodigo() {
		return linhaProdutoCodigo;
	}

	public void setLinhaProdutoCodigo(Integer linhaProdutoCodigo) {
		this.linhaProdutoCodigo = linhaProdutoCodigo;
	}

	public Integer getMarcaProdutoCodigo() {
		return marcaProdutoCodigo;
	}

	public void setMarcaProdutoCodigo(Integer marcaProdutoCodigo) {
		this.marcaProdutoCodigo = marcaProdutoCodigo;
	}

	public Integer getFabricanteCodigo() {
		return fabricanteCodigo;
	}

	public void setFabricanteCodigo(Integer fabricanteCodigo) {
		this.fabricanteCodigo = fabricanteCodigo;
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

	public void setSubCategoriaCodigo(Integer subCategoriaCodigo) {
		this.subCategoriaCodigo = subCategoriaCodigo;
	}

	@Override
	public int getGrupoCodigo() {
		return grupoCodigo;
	}

	public void setGrupoCodigo(int grupoCodigo) {
		this.grupoCodigo = grupoCodigo;
	}

	@Override
	public int getSubGrupoCodigo() {
		return subGrupoCodigo;
	}

	public void setSubGrupoCodigo(int subGrupoCodigo) {
		this.subGrupoCodigo = subGrupoCodigo;
	}

	public Double getIndiceNormal() {
		return indiceNormal;
	}

	public void setIndiceNormal(Double indiceNormal) {
		this.indiceNormal = indiceNormal;
	}

	public Double getIndiceOferta() {
		return indiceOferta;
	}

	public void setIndiceOferta(Double indiceOferta) {
		this.indiceOferta = indiceOferta;
	}

	public Double getDescontoMaximoNormal() {
		return descontoMaximoNormal;
	}

	public void setDescontoMaximoNormal(Double descontoMaximoNormal) {
		this.descontoMaximoNormal = descontoMaximoNormal;
	}

	public Double getAcrescimoMaximoNormal() {
		return acrescimoMaximoNormal;
	}

	public void setAcrescimoMaximoNormal(Double acrescimoMaximoNormal) {
		this.acrescimoMaximoNormal = acrescimoMaximoNormal;
	}

	public Double getDescontoMaximoOferta() {
		return descontoMaximoOferta;
	}

	public void setDescontoMaximoOferta(Double descontoMaximoOferta) {
		this.descontoMaximoOferta = descontoMaximoOferta;
	}

	public Double getAcrescimoMaximoOferta() {
		return acrescimoMaximoOferta;
	}

	public void setAcrescimoMaximoOferta(Double acrescimoMaximoOferta) {
		this.acrescimoMaximoOferta = acrescimoMaximoOferta;
	}

	public Double getQuantidadeInicio() {
		return quantidadeInicio;
	}

	public void setQuantidadeInicio(Double quantidadeInicio) {
		this.quantidadeInicio = quantidadeInicio;
	}

	public Double getQuantidadeFim() {
		return quantidadeFim;
	}

	public void setQuantidadeFim(Double quantidadeFim) {
		this.quantidadeFim = quantidadeFim;
	}

	public Integer getFlagQuantidadeProdutoTotal() {
		return flagQuantidadeProdutoTotal;
	}

	public void setFlagQuantidadeProdutoTotal(Integer flagQuantidadeProdutoTotal) {
		this.flagQuantidadeProdutoTotal = flagQuantidadeProdutoTotal;
	}

	public Integer getSessaoCodigo() {
		return sessaoCodigo;
	}

	public void setSessaoCodigo(Integer sessaoCodigo) {
		this.sessaoCodigo = sessaoCodigo;
	}

	public Date getDataCancelamento() {
		return dataCancelamento;
	}

	public void setDataCancelamento(Date dataCancelamento) {
		this.dataCancelamento = dataCancelamento;
	}

	public String getHoraCancelamento() {
		return horaCancelamento;
	}

	public void setHoraCancelamento(String horaCancelamento) {
		this.horaCancelamento = horaCancelamento;
	}

	public String getUsuarioCancelamento() {
		return usuarioCancelamento;
	}

	public void setUsuarioCancelamento(String usuarioCancelamento) {
		this.usuarioCancelamento = usuarioCancelamento;
	}

	public Integer getColaboradorCodigo() {
		return colaboradorCodigo;
	}

	public void setColaboradorCodigo(Integer colaboradorCodigo) {
		this.colaboradorCodigo = colaboradorCodigo;
	}

	public Integer getColaboradorCodigoSupervisor() {
		return colaboradorCodigoSupervisor;
	}

	public void setColaboradorCodigoSupervisor(Integer colaboradorCodigoSupervisor) {
		this.colaboradorCodigoSupervisor = colaboradorCodigoSupervisor;
	}

	public Integer getColaboradorCodigoGerente() {
		return colaboradorCodigoGerente;
	}

	public void setColaboradorCodigoGerente(Integer colaboradorCodigoGerente) {
		this.colaboradorCodigoGerente = colaboradorCodigoGerente;
	}

	public Integer getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(Integer pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Double getQuantidadeMaxima() {
		return quantidadeMaxima;
	}

	public void setQuantidadeMaxima(Double quantidadeMaxima) {
		this.quantidadeMaxima = quantidadeMaxima;
	}

	public Double getQuantidadeVendida() {
		return quantidadeVendida;
	}

	public void setQuantidadeVendida(Double quantidadeVendida) {
		this.quantidadeVendida = quantidadeVendida;
	}

	public Double getQuantidadeBonificada() {
		return quantidadeBonificada;
	}

	public void setQuantidadeBonificada(Double quantidadeBonificada) {
		this.quantidadeBonificada = quantidadeBonificada;
	}

	public String getNaturezaOperacaoBonificacao() {
		return naturezaOperacaoBonificacao;
	}

	public void setNaturezaOperacaoBonificacao(String naturezaOperacaoBonificacao) {
		this.naturezaOperacaoBonificacao = naturezaOperacaoBonificacao;
	}

	public String getFormaPagamentoBonificacao() {
		return formaPagamentoBonificacao;
	}

	public void setFormaPagamentoBonificacao(String formaPagamentoBonificacao) {
		this.formaPagamentoBonificacao = formaPagamentoBonificacao;
	}

	public Integer getCondicaoPagamentoBonificacao() {
		return condicaoPagamentoBonificacao;
	}

	public void setCondicaoPagamentoBonificacao(Integer condicaoPagamentoBonificacao) {
		this.condicaoPagamentoBonificacao = condicaoPagamentoBonificacao;
	}

	public Integer getFlagInterna() {
		return flagInterna;
	}

	public void setFlagInterna(Integer flagInterna) {
		this.flagInterna = flagInterna;
	}

	public Integer getFlagExterna() {
		return flagExterna;
	}

	public void setFlagExterna(Integer flagExterna) {
		this.flagExterna = flagExterna;
	}

	public Integer getGrupoComissaoNormalInterna() {
		return grupoComissaoNormalInterna;
	}

	public void setGrupoComissaoNormalInterna(Integer grupoComissaoNormalInterna) {
		this.grupoComissaoNormalInterna = grupoComissaoNormalInterna;
	}

	public Integer getGrupoComissaoNormalExterna() {
		return grupoComissaoNormalExterna;
	}

	public void setGrupoComissaoNormalExterna(Integer grupoComissaoNormalExterna) {
		this.grupoComissaoNormalExterna = grupoComissaoNormalExterna;
	}

	public Integer getGrupoComissaoOfertaInterna() {
		return grupoComissaoOfertaInterna;
	}

	public void setGrupoComissaoOfertaInterna(Integer grupoComissaoOfertaInterna) {
		this.grupoComissaoOfertaInterna = grupoComissaoOfertaInterna;
	}

	public Integer getGrupoComissaoOfertaExterna() {
		return grupoComissaoOfertaExterna;
	}

	public void setGrupoComissaoOfertaExterna(Integer grupoComissaoOfertaExterna) {
		this.grupoComissaoOfertaExterna = grupoComissaoOfertaExterna;
	}

	public Integer getFlagOpcaoQuantidade() {
		return flagOpcaoQuantidade;
	}

	public void setFlagOpcaoQuantidade(Integer flagOpcaoQuantidade) {
		this.flagOpcaoQuantidade = flagOpcaoQuantidade;
	}

	public Integer getTabelaPrecoCodigo() {
		return tabelaPrecoCodigo;
	}

	public void setTabelaPrecoCodigo(Integer tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	@Override
	public Integer getTabelaPrecoQuantidadeCodigo() {
		return tabelaPrecoQuantidadeCodigo;
	}

	public void setTabelaPrecoQuantidadeCodigo(Integer tabelaPrecoQuantidadeCodigo) {
		this.tabelaPrecoQuantidadeCodigo = tabelaPrecoQuantidadeCodigo;
	}

	@Override
	public Integer getPrecoBaseQuantidadeCodigo() {
		return precoBaseQuantidadeCodigo;
	}

	public void setPrecoBaseQuantidadeCodigo(Integer precoBaseQuantidadeCodigo) {
		this.precoBaseQuantidadeCodigo = precoBaseQuantidadeCodigo;
	}

	public Double getPercentualDescontoItem() {
		return percentualDescontoItem;
	}

	public void setPercentualDescontoItem(Double percentualDescontoItem) {
		this.percentualDescontoItem = percentualDescontoItem;
	}

	public Produto getProdutoBonificado() {
		return produtoBonificado;
	}

	public void setProdutoBonificado(Produto produtoBonificado) {
		this.produtoBonificado = produtoBonificado;
	}

	@Override
	public int getAtividadeCodigo() {
		return atividadeCodigo;
	}

	public void setAtividadeCodigo(int atividadeCodigo) {
		this.atividadeCodigo = atividadeCodigo;
	}

	@Override
	public Integer getUnidadePadrao() {
		return unidadePadrao;
	}

	public void setUnidadePadrao(Integer unidadePadrao) {
		this.unidadePadrao = unidadePadrao;
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

	/**
	 * Verifica se o produto está em promoção.
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param data
	 * @param produtoCodigo
	 * @return
	 * @throws Exception
	 */
	public static boolean isProdutoEmPromocao(GenericoDAO dao, Date data, GerentePedido gerentePedido, Produto produto)
			throws Exception {

		long count = 0;

		try {

			if (gerentePedido.getPromocaoVenda().isNegociacaoPermitePromocao()) {

				String where = PromocaoVenda.getWhere(data, gerentePedido, produto, false) + " and prm_tipoitem = 1 ";

				count = dao.count("promocao", where);
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return count > 0;
	}

	public static Promocao recuperar(GenericoDAO dao, int filialCodigo, int numeroPromocao) {

		String where = " prm_filcodigo = " + filialCodigo + " and prm_numero = " + numeroPromocao;

		return dao.uniqueResult(Promocao.class, where, null);

	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param data
	 * @param produtoCodigo
	 * @param iCliente
	 * @return
	 * 
	 * @Deprecated public static List<Promocao> recuperar(GenericoDAO dao, int
	 *             filialCodigo, Date data, GerentePedido gerentePedido, Produto
	 *             produto) {
	 * 
	 *             String wherePadrao = getWhere(filialCodigo, data,
	 *             gerentePedido, produto, false);
	 * 
	 *             List<Promocao> promocoes = dao.list(Promocao.class,
	 *             wherePadrao, null, null, null);
	 * 
	 *             return promocoes; }
	 */

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param data
	 * @param produtoCodigo
	 * @return
	 */
	public static List<Promocao> recuperarPromocoesItem(GenericoDAO dao, Date data, GerentePedido gerentePedido,
			Produto produto) {

		String wherePadrao = PromocaoVenda.getWhere(data, gerentePedido, produto, false) + " and prm_tipoItem = 1 ";

		List<Promocao> promocoes = dao.list(Promocao.class, wherePadrao, null, "quantidadeInicio", null);

		return promocoes;
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param data
	 * @param produtoCodigo
	 * @return
	 */
	public static List<Promocao> recuperarPromocoesPedido(GenericoDAO dao, Date data, GerentePedido gerentePedido) {

		StringBuilder where = PromocaoVenda.getwherePadrao(data, gerentePedido);

		where.append(" and prm_tppeditem = 1 ");

		List<Promocao> promocoes = dao.list(Promocao.class, where.toString(), null, null, null);

		return promocoes;
	}

	/**
	 * Recupera uma lista contendo todos os códigos dos produsot que estão em
	 * promoção.
	 * 
	 * @param dao
	 * @param filialCodigo
	 * @param data
	 * @return
	 * @throws Exception
	 */
	public static List<Integer> recuperarCodigoProdutosEmPromocao(GenericoDAO dao, Date data,
			GerentePedido gerentePedido) throws Exception {

		if (gerentePedido.getPromocaoVenda().isNegociacaoPermitePromocao()) {

			StringBuilder where = PromocaoVenda.getwherePadrao(data, gerentePedido);

			PromocaoVenda.adicionarWhereCliente(gerentePedido, where);

			PromocaoVenda.adicionarWhereVendedor(gerentePedido, where);

			PromocaoVenda.adicionarWhereProduto(where);

			where.append(" AND prm_tipoitem = 1");

			StringBuilder select = new StringBuilder(
					"SELECT pro_codigo FROM promocao INNER JOIN produto ON prm_tppeditem = 2 AND ");

			select.append(where.toString());

			select.append(" GROUP BY pro_codigo ORDER BY pro_codigo");

			List<Integer> produtosPromocao = new ArrayList<Integer>();

			ResultSet rs = null;

			try {

				rs = dao.listResultSet(select.toString(), null, null);

				while (rs.next()) {
					int proCodigo = rs.getInt("pro_codigo");

					produtosPromocao.add(Integer.valueOf(proCodigo));
				}

				return produtosPromocao;

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
		return null;
	}

	/**
	 * 
	 * @param propriedade
	 * @param promocao
	 * @return
	 */
	@Override
	public String gerarMensagem(Propriedade propriedade) {

		String mensagem = "";

		if (this.getTipoPromocao().toLowerCase().trim().equals("a")) {
			mensagem = propriedade.getMensagem("mensagem.promocao.prazoAdicional",
					Conversao.formatarValor2(this.faixaValorInicio), this.prazoPromocao.toString());

		} else if (this.getTipoPromocao().toLowerCase().trim().equals("p")) {
			mensagem = propriedade.getMensagem("mensagem.promocao.prazoFinal",
					Conversao.formatarValor2(this.faixaValorInicio), this.prazoPromocao.toString());

		} else if (this.getTipoPromocao().toLowerCase().trim().equals("v")) {
			mensagem = propriedade.getMensagem("mensagem.promocao.vencimentoFixo",
					Conversao.formatarValor2(this.faixaValorInicio),
					Conversao.formatarDataDDMMAAAA(this.dataVencimentoFixo));

		} else if (this.getTipoPromocao().toLowerCase().trim().equals("m")) {
			mensagem = propriedade.getMensagem("mensagem.promocao.descontoMaximo",
					Conversao.formatarValor2(this.faixaValorInicio),
					Conversao.formatarValor2(this.percentualDescontoItem));

		}

		return mensagem;

	}
}
