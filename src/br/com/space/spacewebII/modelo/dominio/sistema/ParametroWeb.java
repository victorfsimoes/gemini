/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Sandro
 */
/**
 * @author RenatoAlves-SpaceInf
 *
 */
@Entity
@Table(name = "parametroweb")
@XmlRootElement
public class ParametroWeb implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "paw_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = "paw_filcodigo")
	private int filialCodigo;

	@Column(name = "paw_tprcodigo")
	private int tabelaPrecoCodigo;

	@Column(name = "paw_cpgcodigo")
	private int condicaoPagamentoCodigo;

	@Column(name = "paw_fpgcodigo")
	private String FormaPagamentoCodigo;

	@Column(name = "paw_vencodpad")
	private int vendedorPadraoCodigo = 0;

	@Column(name = "paw_exiprcunit")
	private int flagExibePrecoUnitario = 0;

	@Column(name = "paw_exiprosest")
	private int flagExibeProdutoSemEstoque = 0;

	@Column(name = "paw_exilimcli")
	private int flagExibeLimiteCliente = 0;

	@Column(name = "paw_ordprosest")
	private int flagOrdenaProdutoSemEstoque = 0;

	@Column(name = "paw_pastaimg")
	private String pastaImagens;

	@Column(name = "paw_permvisit")
	private int flagPermiteVisitantes = 0;

	@Column(name = "paw_exibprecvis")
	private int flagExibePrecoVisitante = 0;

	@Column(name = "paw_exibfantcli")
	private int flagExibeFantasiaCliente = 0;

	@Column(name = "paw_sitetitulo")
	private String siteTitulo;

	@Lob
	@Column(name = "paw_siterodape")
	private String siteRodape;

	@Column(name = "paw_siterodtit")
	private String siteRodapeTitulo;

	@Column(name = "paw_siteinst")
	private String siteInstitucional;

	@Column(name = "paw_tituloinst")
	private String siteInstitucionalTitulo;

	@Column(name = "paw_facecomm")
	private int flagFacebookComentario;

	@Column(name = "paw_faceplugin")
	private int flagFacebookPlugin;

	@Column(name = "paw_faceuser")
	private String usuarioFacebook;

	@Column(name = "paw_youchan")
	private String youtubeCanal;

	@Column(name = "paw_twituser")
	private String usuarioTwitter;

	@Column(name = "paw_tmwnome")
	private String temaWebNome;

	@Column(name = "paw_natcodigo")
	private String naturezaOperacaoCodigo;

	@Column(name = "paw_laylstpro")
	private int layoutListaProduto;

	@Column(name = "paw_padlstgrdpr")
	private int listaOuGridProduto;

	@Column(name = "paw_exibnmaxclb")
	private int flagExibeMaxiBannerColaborador = 0;

	@Column(name = "paw_exibncenclb")
	private int flagExibeBannerCentralColaborador = 0;

	@Column(name = "paw_exibnminclb")
	private int flagExibeMiniBannerColaborador = 0;

	@Column(name = "paw_eximgproclb")
	private int flagExibeImagemProdutoColaborador = 0;

	@Column(name = "paw_emailaviest")
	private String emailHTMLAvisoEstoque;

	@Column(name = "paw_emailnovsen")
	private String emailHTMLNovaSenha;

	@Column(name = "paw_urlsite")
	private String urlSite;

	@Column(name = "paw_urlimagem")
	private String urlImagem;

	@Column(name = "paw_exibestcli")
	private int flagExibeEstoqueCliente = 0;

	@Column(name = "paw_msgpedconf")
	private String mensagemConfirmacaoPedido;

	@Column(name = "paw_msgpedcart")
	private String mensagemAguardandoCartaoPedido;

	@Column(name = "paw_redesocclb")
	private int flagExibeRedeSocialColaborador = 0;

	@Column(name = "paw_perUnComRap")
	private int flagPermiteSelecionarUnidadeCompraRapida = 0;

	@Column(name = "paw_exibundlst")
	private int flagExibeUnidadeProdutoLista = 0;

	@Column(name = "paw_cliespneg")
	private int clienteEspelhoNegociacaoCodigo;

	@Column(name = "paw_permconsfin")
	private int flagPermiteConsumidorFinal;

	@Column(name = "paw_maxtencanct")
	private int quantidadeMaximaTentativasCancelamento;

	@Column(name = "paw_emailavtran")
	private String emailAvisoTransacao = null;

	@Column(name = "paw_usravtran")
	private String usuarioAvisoTransacao = null;

	@Column(name = "paw_altDatEmiss")
	private int flagPermiteAlterarDataEmissaoModalidadeCliente = 0;

	@Column(name = "paw_exibSemPrec")
	private int flagExibeProdutoSemPreco = 1;

	@Column(name = "paw_exibpesoPro")
	private int flagExibePesoProduto = 1;

	@Column(name = "paw_diasconsped")
	private int diasConsultaPedidos = 90;

	@Column(name = "paw_recupped")
	private int flagRecuperaPedido;

	@Column(name = "paw_tprcodrev")
	private int tabelaPrecoRevenda;

	@Column(name = "paw_AdicDiaEmis")
	private int adicionarDiasDataEmissao;

	@Column(name = "paw_tprcodsugpf")
	private int tabelaPrecoCodigoSugeridaPessoaFisica;

	@Column(name = "paw_tprcodsugpj")
	private int tabelaPrecoCodigoSugeridaPessoaJuridica;

	@Column(name = "paw_intredahome")
	private int intervaloRedirecionamentoAutomaticoHome;

	@Column(name = "paw_reqidentcli")
	private int flagRequerIdentificacaoCliente;

	public ParametroWeb() {
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getTabelaPrecoCodigo() {
		return tabelaPrecoCodigo;
	}

	public void setTabelaPrecoCodigo(int tabelaPrecoCodigo) {
		this.tabelaPrecoCodigo = tabelaPrecoCodigo;
	}

	public int getCondicaoPagamentoCodigo() {
		return condicaoPagamentoCodigo;
	}

	public void setCondicaoPagamentoCodigo(int condicaoPagamentoCodigo) {
		this.condicaoPagamentoCodigo = condicaoPagamentoCodigo;
	}

	public String getFormaPagamentoCodigo() {
		return FormaPagamentoCodigo;
	}

	public void setFormaPagamentoCodigo(String formaPagamentoCodigo) {
		FormaPagamentoCodigo = formaPagamentoCodigo;
	}

	public int getVendedorPadraoCodigo() {
		return vendedorPadraoCodigo;
	}

	public void setVendedorPadraoCodigo(int vendedorPadraoCodigo) {
		this.vendedorPadraoCodigo = vendedorPadraoCodigo;
	}

	public boolean isExibePrecoUnitario() {
		return flagExibePrecoUnitario == 1;
	}

	public int getFlagExibePrecoUnitario() {
		return flagExibePrecoUnitario;
	}

	public void setFlagExibePrecoUnitario(int flagExibePrecoUnitario) {
		this.flagExibePrecoUnitario = flagExibePrecoUnitario;
	}

	public boolean isExibeProdutoSemEstoque() {
		return flagExibeProdutoSemEstoque == 1;
	}

	public boolean isOrdenaProdutoSemEstoque() {
		return flagOrdenaProdutoSemEstoque == 1;
	}

	public int getFlagOrdenaProdutoSemEstoque() {
		return flagOrdenaProdutoSemEstoque;
	}

	public void setFlagOrdenaProdutoSemEstoque(int flagOrdenaProdutoSemEstoque) {
		this.flagOrdenaProdutoSemEstoque = flagOrdenaProdutoSemEstoque;
	}

	public int getFlagExibeProdutoSemEstoque() {
		return flagExibeProdutoSemEstoque;
	}

	public void setFlagExibeProdutoSemEstoque(int flagExibeProdutoSemEstoque) {
		this.flagExibeProdutoSemEstoque = flagExibeProdutoSemEstoque;
	}

	public boolean isExibeLimiteCliente() {
		return flagExibeLimiteCliente == 1;
	}

	public int getFlagExibeLimiteCliente() {
		return flagExibeLimiteCliente;
	}

	public void setFlagExibeLimiteCliente(int flagExibeLimiteCliente) {
		this.flagExibeLimiteCliente = flagExibeLimiteCliente;
	}

	public String getPastaImagens() {
		return pastaImagens;
	}

	public void setPastaImagens(String pastaImagens) {
		this.pastaImagens = pastaImagens;
	}

	public boolean isPermiteVisitantes() {
		return flagPermiteVisitantes == 1;
	}

	public int getFlagPermiteVisitantes() {
		return flagPermiteVisitantes;
	}

	public void setFlagPermiteVisitantes(int flagPermiteVisitantes) {
		this.flagPermiteVisitantes = flagPermiteVisitantes;
	}

	public boolean isExibePrecoVisitante() {
		return flagExibePrecoVisitante == 1;
	}

	public int getFlagExibePrecoVisitante() {
		return flagExibePrecoVisitante;
	}

	public void setFlagExibePrecoVisitante(int flagExibePrecoVisitante) {
		this.flagExibePrecoVisitante = flagExibePrecoVisitante;
	}

	public boolean isFlagExibeFantasiaCliente() {
		return flagExibeFantasiaCliente == 1;
	}

	public int getFlagExibeFantasiaCliente() {
		return flagExibeFantasiaCliente;
	}

	public void setFlagExibeFantasiaCliente(int flagExibeFantasiaCliente) {
		this.flagExibeFantasiaCliente = flagExibeFantasiaCliente;
	}

	public String getSiteTitulo() {
		return siteTitulo;
	}

	public void setSiteTitulo(String siteTitulo) {
		this.siteTitulo = siteTitulo;
	}

	public String getSiteRodape() {
		return siteRodape;
	}

	public void setSiteRodape(String siteRodape) {
		this.siteRodape = siteRodape;
	}

	public String getSiteRodapeTitulo() {
		return siteRodapeTitulo;
	}

	public void setSiteRodapeTitulo(String siteRodapeTitulo) {
		this.siteRodapeTitulo = siteRodapeTitulo;
	}

	public boolean isFacebookComentario() {
		return flagFacebookComentario == 1;
	}

	public int getFlagFacebookComentario() {
		return flagFacebookComentario;
	}

	public void setFlagFacebookComentario(int flagFacebookComentario) {
		this.flagFacebookComentario = flagFacebookComentario;
	}

	public boolean isFacebookPlugin() {
		return flagFacebookPlugin == 1;
	}

	public int getFlagFacebookPlugin() {
		return flagFacebookPlugin;
	}

	public void setFlagFacebookPlugin(int flagFacebookPlugin) {
		this.flagFacebookPlugin = flagFacebookPlugin;
	}

	public String getYoutubeCanal() {
		return youtubeCanal;
	}

	public void setYoutubeCanal(String youtubeCanal) {
		this.youtubeCanal = youtubeCanal;
	}

	public String getUsuarioTwitter() {
		return usuarioTwitter;
	}

	public void setUsuarioTwitter(String usuarioTwitter) {
		this.usuarioTwitter = usuarioTwitter;
	}

	public String getUsuarioFacebook() {
		return usuarioFacebook;
	}

	public void setUsuarioFacebook(String usuarioFacebook) {
		this.usuarioFacebook = usuarioFacebook;
	}

	public String getTemaWebNome() {
		return temaWebNome;
	}

	public void setTemaWebNome(String temaWebNome) {
		this.temaWebNome = temaWebNome;
	}

	public String getNaturezaOperacaoCodigo() {
		return naturezaOperacaoCodigo;
	}

	public void setNaturezaOperacaoCodigo(String naturezaOperacaoCodigo) {
		this.naturezaOperacaoCodigo = naturezaOperacaoCodigo;
	}

	public String getSiteInstitucional() {
		return siteInstitucional;
	}

	public void setSiteInstitucional(String siteInstitucional) {
		this.siteInstitucional = siteInstitucional;
	}

	public String getSiteInstitucionalTitulo() {
		return siteInstitucionalTitulo;
	}

	public void setSiteInstitucionalTitulo(String siteInstitucionalTitulo) {
		this.siteInstitucionalTitulo = siteInstitucionalTitulo;
	}

	public int getLayoutListaProduto() {
		return layoutListaProduto;
	}

	public void setLayoutListaProduto(int layoutListaProduto) {
		this.layoutListaProduto = layoutListaProduto;
	}

	public int getListaOuGridProduto() {
		return listaOuGridProduto;
	}

	public void setListaOuGridProduto(int listaOuGridProduto) {
		this.listaOuGridProduto = listaOuGridProduto;
	}

	public boolean isExibeMaxiBannerColaborador() {
		return flagExibeMaxiBannerColaborador == 1;
	}

	public int getFlagExibeMaxiBannerColaborador() {
		return flagExibeMaxiBannerColaborador;
	}

	public void setFlagExibeMaxiBannerColaborador(int flagExibeMaxiBannerColaborador) {
		this.flagExibeMaxiBannerColaborador = flagExibeMaxiBannerColaborador;
	}

	public boolean isExibeBannerCentralColaborador() {
		return flagExibeBannerCentralColaborador == 1;
	}

	public int getFlagExibeBannerCentralColaborador() {
		return flagExibeBannerCentralColaborador;
	}

	public void setFlagExibeBannerCentralColaborador(int flagExibeBannerCentralColaborador) {
		this.flagExibeBannerCentralColaborador = flagExibeBannerCentralColaborador;
	}

	public boolean isExibeMiniBannerColaborador() {
		return flagExibeMiniBannerColaborador == 1;
	}

	public int getFlagExibeMiniBannerColaborador() {
		return flagExibeMiniBannerColaborador;
	}

	public void setFlagExibeMiniBannerColaborador(int flagExibeMiniBannerColaborador) {
		this.flagExibeMiniBannerColaborador = flagExibeMiniBannerColaborador;
	}

	public boolean isExibeImagemProdutoColaborador() {
		return flagExibeImagemProdutoColaborador == 1;

	}

	public int getFlagExibeImagemProdutoColaborador() {
		return flagExibeImagemProdutoColaborador;
	}

	public void setFlagExibeImagemProdutoColaborador(int flagExibeImagemProdutoColaborador) {
		this.flagExibeImagemProdutoColaborador = flagExibeImagemProdutoColaborador;
	}

	public int getFlagExibeUnidadeProdutoLista() {
		return flagExibeUnidadeProdutoLista;
	}

	public void setFlagExibeUnidadeProdutoLista(int flagExibeUnidadeProdutoLista) {
		this.flagExibeUnidadeProdutoLista = flagExibeUnidadeProdutoLista;
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
	 * @param filialCodigo
	 * @return
	 */
	public static ParametroWeb recuperarUnico(GenericoDAO dao, int filialCodigo) {
		return dao.uniqueResult(ParametroWeb.class, COLUNA_FILIAL + " = " + filialCodigo, null);

	}

	public String getTituloSiteRodape() {
		return null;
	}

	public String getEmailHTMLAvisoEstoque() {
		return emailHTMLAvisoEstoque;
	}

	public void setEmailHTMLAvisoEstoque(String emailHTMLAvisoEstoque) {
		this.emailHTMLAvisoEstoque = emailHTMLAvisoEstoque;
	}

	public String getUrlSite() {
		return urlSite;
	}

	public void setUrlSite(String urlSite) {
		this.urlSite = urlSite;
	}

	public String getUrlImagem() {
		return urlImagem;
	}

	public void setUrlImagem(String urlImagem) {
		this.urlImagem = urlImagem;
	}

	public String getEmailHTMLNovaSenha() {
		return emailHTMLNovaSenha;
	}

	public void setEmailHTMLNovaSenha(String emailHTMLNovaSenha) {
		this.emailHTMLNovaSenha = emailHTMLNovaSenha;
	}

	public boolean isExibeEstoqueCliente() {
		return flagExibeEstoqueCliente == 1;
	}

	public int getFlagExibeEstoqueCliente() {
		return flagExibeEstoqueCliente;
	}

	public void setFlagExibeEstoqueCliente(int flagExibeEstoqueCliente) {
		this.flagExibeEstoqueCliente = flagExibeEstoqueCliente;
	}

	public String getMensagemConfirmacaoPedido() {
		return mensagemConfirmacaoPedido;
	}

	public void setMensagemConfirmacaoPedido(String mensagemConfirmacaoPedido) {
		this.mensagemConfirmacaoPedido = mensagemConfirmacaoPedido;
	}

	public String getMensagemAguardandoCartaoPedido() {
		return mensagemAguardandoCartaoPedido;
	}

	public void setMensagemAguardandoCartaoPedido(String mensagemAguardandoCartaoPedido) {
		this.mensagemAguardandoCartaoPedido = mensagemAguardandoCartaoPedido;
	}

	public boolean isExibeRedeSocialColaborador() {
		return flagExibeRedeSocialColaborador == 1;
	}

	public int getFlagExibeRedeSocialColaborador() {
		return flagExibeRedeSocialColaborador;
	}

	public void setFlagExibeRedeSocialColaborador(int flagExibeRedeSocialColaborador) {
		this.flagExibeRedeSocialColaborador = flagExibeRedeSocialColaborador;
	}

	public int getFlagPermiteSelecionarUnidadeCompraRapida() {
		return flagPermiteSelecionarUnidadeCompraRapida;
	}

	public void setFlagPermiteSelecionarUnidadeCompraRapida(int flagPermiteSelecionarUnidadeCompraRapida) {
		this.flagPermiteSelecionarUnidadeCompraRapida = flagPermiteSelecionarUnidadeCompraRapida;
	}

	public int getClienteEspelhoNegociacaoCodigo() {
		return clienteEspelhoNegociacaoCodigo;
	}

	public void setClienteEspelhoNegociacaoCodigo(int clienteEspelhoNegociacaoCodigo) {
		this.clienteEspelhoNegociacaoCodigo = clienteEspelhoNegociacaoCodigo;
	}

	public int getFlagPermiteConsumidorFinal() {
		return flagPermiteConsumidorFinal;
	}

	public void setFlagPermiteConsumidorFinal(int flagPermiteConsumidorFinal) {
		this.flagPermiteConsumidorFinal = flagPermiteConsumidorFinal;
	}

	public int getQuantidadeMaximaTentativasCancelamento() {
		return quantidadeMaximaTentativasCancelamento;
	}

	public void setQuantidadeMaximaTentativasCancelamento(int quantidadeMaximaTentativasCancelamento) {
		this.quantidadeMaximaTentativasCancelamento = quantidadeMaximaTentativasCancelamento;
	}

	public String getEmailAvisoTransacao() {
		return emailAvisoTransacao;
	}

	public void setEmailAvisoTransacao(String emailAvisoTransacao) {
		this.emailAvisoTransacao = emailAvisoTransacao;
	}

	public String[] getUsuariosAvisoTransacao() {

		if (StringUtil.isValida(usuarioAvisoTransacao)) {
			return usuarioAvisoTransacao.split(";");
		}

		return null;
	}

	public String getUsuarioAvisoTransacao() {
		return usuarioAvisoTransacao;
	}

	public void setUsuarioAvisoTransacao(String usuarioAvisoTransacao) {
		this.usuarioAvisoTransacao = usuarioAvisoTransacao;
	}

	public int getFlagPermiteAlterarDataEmissaoModalidadeCliente() {
		return flagPermiteAlterarDataEmissaoModalidadeCliente;
	}

	public void setFlagPermiteAlterarDataEmissaoModalidadeCliente(int flagPermiteAlterarDataEmissaoModalidadeCliente) {
		this.flagPermiteAlterarDataEmissaoModalidadeCliente = flagPermiteAlterarDataEmissaoModalidadeCliente;
	}

	public boolean isFlagPermiteAlterarDataEmissaoModalidadeCliente() {
		return flagPermiteAlterarDataEmissaoModalidadeCliente == 1;
	}

	public int getFlagExibeProdutoSemPreco() {
		return flagExibeProdutoSemPreco;
	}

	public void setFlagExibeProdutoSemPreco(int flagExibeProdutoSemPreco) {
		this.flagExibeProdutoSemPreco = flagExibeProdutoSemPreco;
	}

	public boolean isFlagExibeProdutoSemPreco() {
		return flagExibeProdutoSemPreco == 1;
	}

	public int getFlagExibePesoProduto() {
		return flagExibePesoProduto;
	}

	public void setFlagExibePesoProduto(int flagExibePesoProduto) {
		this.flagExibePesoProduto = flagExibePesoProduto;
	}

	public boolean isFlagExibePesoProduto() {
		return flagExibePesoProduto == 1;
	}

	public int getDiasConsultaPedidos() {
		return diasConsultaPedidos;
	}

	public void setDiasConsultaPedidos(int diasConsultaPedidos) {
		this.diasConsultaPedidos = diasConsultaPedidos;
	}

	public int getFlagRecuperaPedido() {
		return flagRecuperaPedido;
	}

	public void setFlagRecuperaPedido(int flagRecuperaPedido) {
		this.flagRecuperaPedido = flagRecuperaPedido;
	}

	public boolean isFlagRecuperaPedido() {
		return this.flagRecuperaPedido == 1;
	}

	public int getTabelaPrecoRevenda() {
		return tabelaPrecoRevenda;
	}

	public void setTabelaPrecoRevenda(int tabelaPrecoRevenda) {
		this.tabelaPrecoRevenda = tabelaPrecoRevenda;
	}

	public int getAdicionarDiasDataEmissao() {
		return adicionarDiasDataEmissao;
	}

	public void setAdicionarDiasDataEmissao(int adicionarDiasDataEmissao) {
		this.adicionarDiasDataEmissao = adicionarDiasDataEmissao;
	}

	public int getTabelaPrecoCodigoSugeridaPessoaFisica() {
		return tabelaPrecoCodigoSugeridaPessoaFisica;
	}

	public void setTabelaPrecoCodigoSugeridaPessoaFisica(int tabelaPrecoCodigoSugeridaPessoaFisica) {
		this.tabelaPrecoCodigoSugeridaPessoaFisica = tabelaPrecoCodigoSugeridaPessoaFisica;
	}

	public int getTabelaPrecoCodigoSugeridaPessoaJuridica() {
		return tabelaPrecoCodigoSugeridaPessoaJuridica;
	}

	public void setTabelaPrecoCodigoSugeridaPessoaJuridica(int tabelaPrecoCodigoSugeridaPessoaJuridica) {
		this.tabelaPrecoCodigoSugeridaPessoaJuridica = tabelaPrecoCodigoSugeridaPessoaJuridica;
	}

	public int getIntervaloRedirecionamentoAutomaticoHome() {
		return intervaloRedirecionamentoAutomaticoHome;
	}

	public void setIntervaloRedirecionamentoAutomaticoHome(int intervaloRedirecionamentoAutomaticoHome) {
		this.intervaloRedirecionamentoAutomaticoHome = intervaloRedirecionamentoAutomaticoHome;
	}

	public boolean isRequerIdentificacaoCliente() {
		return flagRequerIdentificacaoCliente == 1;
	}
	
	public int getFlagRequerIdentificacaoCliente() {
		return flagRequerIdentificacaoCliente;
	}

	public void setFlagRequerIdentificacaoCliente(int flagRequerIdentificacaoCliente) {
		this.flagRequerIdentificacaoCliente = flagRequerIdentificacaoCliente;
	}
}