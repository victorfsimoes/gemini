package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.pessoa.Pessoa;

/**
 * 
 * @author Marina
 */
@Entity
@Table(name = "transportadora")
public class Transportadora implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;

	private static final String COLUNA_CODIGO = "tra_pescodigo";

	@Id
	@Column(name = "tra_pescodigo")
	private int pessoaCodigo;

	@Column(name = "TRA_FILCODIGO")
	private Integer filialCodigo;

	// carteira motorista
	@Column(name = "tra_codmot")
	private String codigoMotorista;

	@Column(name = "tra_catmot")
	private String categoriaHabilitacao;

	@Column(name = "tra_dtemismot")
	@Temporal(TemporalType.DATE)
	private Date emissaoHabilitacao;

	@Column(name = "tra_inscpref")
	private String inscricaoMotorista;

	@Column(name = "tra_pis")
	private String pis;

	@Column(name = "tra_inss")
	private String inss;

	@Column(name = "tra_dtcadinss")
	@Temporal(TemporalType.DATE)
	private Date dataCadastroInss;

	@Column(name = "tra_ndepende")
	private Integer numDependentes;

	@Column(name = "tra_valpensao")
	private Double valorPensao;

	@Column(name = "tra_dtvenmot")
	@Temporal(TemporalType.DATE)
	private Date vencimentoHabilitacao;

	@Column(name = "tra_antt")
	private String antt;

	@Column(name = "tra_carneinss")
	private Double carneInss;

	@Column(name = "tra_rntrc")
	private Integer rntrc;

	@OneToOne(optional = false)
	@JoinColumn(name = "tra_pescodigo", referencedColumnName = "pes_codigo", insertable = false, updatable = false)
	private Pessoa pessoa;

	public Transportadora() {
	}

	public int getPessoaCodigo() {
		return pessoaCodigo;
	}

	public void setPessoaCodigo(int pessoaCodigo) {
		this.pessoaCodigo = pessoaCodigo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getCodigoMotorista() {
		return codigoMotorista;
	}

	public void setCodigoMotorista(String codigoMotorista) {
		this.codigoMotorista = codigoMotorista;
	}

	public String getCategoriaHabilitacao() {
		return categoriaHabilitacao;
	}

	public void setCategoriaHabilitacao(String categoriaHabilitacao) {
		this.categoriaHabilitacao = categoriaHabilitacao;
	}

	public Date getEmissaoHabilitacao() {
		return emissaoHabilitacao;
	}

	public void setEmissaoHabilitacao(Date emissaoHabilitacao) {
		this.emissaoHabilitacao = emissaoHabilitacao;
	}

	public String getInscricaoMotorista() {
		return inscricaoMotorista;
	}

	public void setInscricaoMotorista(String inscricaoMotorista) {
		this.inscricaoMotorista = inscricaoMotorista;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getInss() {
		return inss;
	}

	public void setInss(String inss) {
		this.inss = inss;
	}

	public Date getDataCadastroInss() {
		return dataCadastroInss;
	}

	public void setDataCadastroInss(Date dataCadastroInss) {
		this.dataCadastroInss = dataCadastroInss;
	}

	public Integer getNumDependentes() {
		return numDependentes;
	}

	public void setNumDependentes(Integer numDependentes) {
		this.numDependentes = numDependentes;
	}

	public Double getValorPensao() {
		return valorPensao;
	}

	public void setValorPensao(Double valorPensao) {
		this.valorPensao = valorPensao;
	}

	public Date getVencimentoHabilitacao() {
		return vencimentoHabilitacao;
	}

	public void setVencimentoHabilitacao(Date vencimentoHabilitacao) {
		this.vencimentoHabilitacao = vencimentoHabilitacao;
	}

	public String getAntt() {
		return antt;
	}

	public void setAntt(String antt) {
		this.antt = antt;
	}

	public Double getCarneInss() {
		return carneInss;
	}

	public void setCarneInss(Double carneInss) {
		this.carneInss = carneInss;
	}

	public Integer getRntrc() {
		return rntrc;
	}

	public void setRntrc(Integer rntrc) {
		this.rntrc = rntrc;
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

	public static Transportadora recuperarUnicoAtivo(GenericoDAO dao, int codigo) {

		String select = getSelectAtivo(COLUNA_CODIGO + " = " + codigo);

		return dao.uniqueResult(Transportadora.class, select);
	}

	public static Transportadora recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(Transportadora.class, COLUNA_CODIGO + " = "
				+ codigo, null);
	}

	private static String getSelectAtivo(String where) {

		String select = "select transportadora.* from transportadora inner join pessoa on tra_pescodigo = pes_codigo and pes_ativo = 1";

		if (StringUtil.isValida(where)) {

			select += " where " + where;

		}
		return select;
	}

}