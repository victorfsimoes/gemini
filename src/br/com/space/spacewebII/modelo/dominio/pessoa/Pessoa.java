/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.pessoa;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "pessoa")
@XmlRootElement
public class Pessoa implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;
	private static final String COLUNA_CNPJCPF = "pes_cnpjcpf";

	@Id
	@Basic(optional = false)
	@Column(name = "pes_codigo")
	private Integer codigo;

	@Column(name = "pes_fantasia")
	private String fantasia;

	@Column(name = COLUNA_CNPJCPF)
	private String cnpjCpf;

	@Column(name = "pes_rg")
	private String rg;

	@Column(name = "pes_insestadual")
	private String inscricaoEstadual;

	@Column(name = "pes_insmunicipa")
	private String inscricaoMunicipal;

	@Column(name = "pes_insprodutor")
	private String inscricaoProduto;

	@Column(name = "pes_tipopessoa")
	private String tipoPessoa;

	@Column(name = "pes_homepage")
	private String homePage;

	@Column(name = "pes_atvcodigo")
	private Integer atividadeCodigo;

	@Column(name = "pes_ativo")
	private Integer flagAtivo;

	@Lob
	@Column(name = "pes_historico")
	private String historico;

	@Column(name = "pes_razao")
	private String razao;

	@Column(name = "pes_dtcad")
	@Temporal(TemporalType.DATE)
	private Date dataCadastro;

	@Column(name = "pes_sexo")
	private String sexo;

	@Column(name = "pes_estadocivil")
	private String estadoCivil;

	@Column(name = "pes_orgaoexprg")
	private String orgaoExpedidorRg;

	@Column(name = "pes_dtexprg")
	@Temporal(TemporalType.DATE)
	private Date dataExpedicaoRg;

	@Column(name = "pes_clccodigo")
	private Integer classificacaoClienteCodigo;

	// @Max(value=?) @Min(value=?)//if you know range of your decimal fields
	// consider using these annotations to enforce field validation
	@Column(name = "pes_campoaux")
	private Double campoAuxiliar;

	@Column(name = "pes_cnacodigo")
	private Integer cnaeCodigo;

	@Basic(optional = false)
	@Column(name = "pes_tipoemp")
	private int tipoEmpresa;

	@Column(name = "pes_impfinnf")
	private Integer flagImprimeFinanceiroNF;

	@Column(name = "pes_infstnf")
	private Integer flagInformaSTNF;

	@Column(name = "pes_codcontabil")
	private String codigoContabil;

	@Column(name = "pes_ufrg")
	private String ufRg;

	@Column(name = "pes_cliweb")
	private Integer clienteCadastradoViaWeb;

	@Column(name = "pes_sgmcodigo")
	private Integer segmentoCodigo;

	public Pessoa() {
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public String getFantasia() {
		return fantasia;
	}

	public void setFantasia(String fantasia) {
		this.fantasia = fantasia;
	}

	public String getCnpjCpf() {
		return cnpjCpf;
	}

	public void setCnpjCpf(String cnpjCpf) {
		this.cnpjCpf = cnpjCpf;
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getInscricaoEstadual() {
		return inscricaoEstadual;
	}

	public void setInscricaoEstadual(String inscricaoEstadual) {
		this.inscricaoEstadual = inscricaoEstadual;
	}

	public String getInscricaoMunicipal() {
		return inscricaoMunicipal;
	}

	public void setInscricaoMunicipal(String inscricaoMunicipal) {
		this.inscricaoMunicipal = inscricaoMunicipal;
	}

	public String getInscricaoProduto() {
		return inscricaoProduto;
	}

	public void setInscricaoProduto(String inscricaoProduto) {
		this.inscricaoProduto = inscricaoProduto;
	}

	public String getTipoPessoa() {
		return tipoPessoa;
	}

	public void setTipoPessoa(String tipoPessoa) {
		this.tipoPessoa = tipoPessoa;
	}

	public String getHomePage() {
		return homePage;
	}

	public void setHomePage(String homePage) {
		this.homePage = homePage;
	}

	public Integer getAtividadeCodigo() {
		return atividadeCodigo;
	}

	public void setAtividadeCodigo(Integer atividadeCodigo) {
		this.atividadeCodigo = atividadeCodigo;
	}

	public Integer getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(Integer flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public String getHistorico() {
		return historico;
	}

	public void setHistorico(String historico) {
		this.historico = historico;
	}

	public String getRazao() {
		return razao;
	}

	public void setRazao(String razao) {
		this.razao = razao;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public String getEstadoCivil() {
		return estadoCivil;
	}

	public void setEstadoCivil(String estadoCivil) {
		this.estadoCivil = estadoCivil;
	}

	public String getOrgaoExpedidorRg() {
		return orgaoExpedidorRg;
	}

	public void setOrgaoExpedidorRg(String orgaoExpedidorRg) {
		this.orgaoExpedidorRg = orgaoExpedidorRg;
	}

	public Date getDataExpedicaoRg() {
		return dataExpedicaoRg;
	}

	public void setDataExpedicaoRg(Date dataExpedicaoRg) {
		this.dataExpedicaoRg = dataExpedicaoRg;
	}

	public Integer getClassificacaoClienteCodigo() {
		return classificacaoClienteCodigo;
	}

	public void setClassificacaoClienteCodigo(Integer classificacaoClienteCodigo) {
		this.classificacaoClienteCodigo = classificacaoClienteCodigo;
	}

	public Double getCampoAuxiliar() {
		return campoAuxiliar;
	}

	public void setCampoAuxiliar(Double campoAuxiliar) {
		this.campoAuxiliar = campoAuxiliar;
	}

	public Integer getCnaeCodigo() {
		return cnaeCodigo;
	}

	public void setCnaeCodigo(Integer cnaeCodigo) {
		this.cnaeCodigo = cnaeCodigo;
	}

	public int getTipoEmpresa() {
		return tipoEmpresa;
	}

	public void setTipoEmpresa(int tipoEmpresa) {
		this.tipoEmpresa = tipoEmpresa;
	}

	public Integer getFlagImprimeFinanceiroNF() {
		return flagImprimeFinanceiroNF;
	}

	public void setFlagImprimeFinanceiroNF(Integer flagImprimeFinanceiroNF) {
		this.flagImprimeFinanceiroNF = flagImprimeFinanceiroNF;
	}

	public Integer getFlagInformaSTNF() {
		return flagInformaSTNF;
	}

	public void setFlagInformaSTNF(Integer flagInformaSTNF) {
		this.flagInformaSTNF = flagInformaSTNF;
	}

	public String getCodigoContabil() {
		return codigoContabil;
	}

	public void setCodigoContabil(String codigoContabil) {
		this.codigoContabil = codigoContabil;
	}

	public String getUfRg() {
		return ufRg;
	}

	public void setUfRg(String ufRg) {
		this.ufRg = ufRg;
	}

	public Integer getSegmentoCodigo() {
		return segmentoCodigo;
	}

	public void setSegmentoCodigo(Integer segmentoCodigo) {
		this.segmentoCodigo = segmentoCodigo;
	}

	public Integer getClienteCadastradoViaWeb() {

		if (clienteCadastradoViaWeb == null)
			return 0;

		return clienteCadastradoViaWeb;
	}

	public void setClienteCadastradoViaWeb(Integer clienteCadastradoViaWeb) {
		this.clienteCadastradoViaWeb = clienteCadastradoViaWeb;
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

	public static Pessoa recuperar(GenericoDAO<IPersistent> dao, Integer pessoaCodigo) {

		if (pessoaCodigo == null) {
			return null;
		}

		return dao.uniqueResult(Pessoa.class, "pes_codigo = " + pessoaCodigo.toString(), null);
	}

	/**
	 * 
	 * @param dao
	 * @param cnpjcpf
	 * @return
	 */
	public static List<Pessoa> recuperarCnpjAtivos(GenericoDAO<IPersistent> dao, String cnpjcpf) {
		return dao.list(Pessoa.class, COLUNA_CNPJCPF + " = '" + cnpjcpf + "' and pes_ativo = 1", null, null, null);
	}

	/**
	 * 
	 * @param dao
	 * @param cnpjcpf
	 * @return
	 */
	public static List<Pessoa> recuperarCnpj(GenericoDAO<IPersistent> dao, String cnpjcpf) {
		return dao.list(Pessoa.class, COLUNA_CNPJCPF + " = '" + cnpjcpf + "'", null, null, null);
	}

	public static boolean isExisteCpfCnpj(GenericoDAO<IPersistent> dao, String cnpjcpf) {

		long ocorrencias = dao.count("pessoa", COLUNA_CNPJCPF + " = '" + cnpjcpf + "'");

		return ocorrencias > 0;

	}

	public static Map<String, Object> recuperarNome(GenericoDAO<IPersistent> dao, List<Integer> codigosCliente,
			boolean isNomeFantasia) {

		String select = "SELECT pes_codigo AS " + GenericoDAO.COLUNA_ID + ", "
				+ (isNomeFantasia ? "pes_fantasia" : "pes_razao") + " AS " + GenericoDAO.COLUNA_VALOR
				+ " FROM pessoa WHERE pes_codigo IN" + GenericoDAO.criarIN(codigosCliente) + " GROUP BY pes_codigo";

		try {
			return dao.list(select);

		} catch (ClassNotFoundException | SQLException e) {
			e.printStackTrace();
		}

		return null;
	}
}
