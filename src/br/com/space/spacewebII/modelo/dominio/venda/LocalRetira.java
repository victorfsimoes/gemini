package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.core.util.StringUtil;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.endereco.Bairro;
import br.com.space.spacewebII.modelo.dominio.endereco.Cidade;
import br.com.space.spacewebII.modelo.dominio.endereco.EnderecoUtil;
import br.com.space.spacewebII.modelo.dominio.endereco.IEndereco;

@Entity
@Table(name = "localretira")
@XmlRootElement
public class LocalRetira implements Serializable, IPersistent, IEndereco {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue
	@Column(name = "lrr_codigo")
	private int codigo;

	@Column(name = "lrr_baicodigo")
	private Integer bairroCodigo;

	@Column(name = "lrr_cidcodigo")
	private Integer cidadeCodigo;

	@ManyToOne
	@JoinColumn(name = "lrr_cidcodigo", referencedColumnName = "cid_codigo", insertable = false, updatable = false)
	private Cidade cidade;

	@ManyToOne
	@JoinColumn(name = "lrr_baicodigo", referencedColumnName = "bai_codigo", insertable = false, updatable = false)
	private Bairro bairro;

	@Column(name = "lrr_desc", length = 45)
	private String descricao;

	@Column(name = "lrr_logradouro", length = 45)
	private String logradouro;

	@Column(name = "lrr_ufsigla", length = 2)
	private String ufsigla;
	@Column(name = "lrr_cep", length = 10)
	private String cep;

	@Column(name = "lrr_numero", length = 10)
	private String numero;
	@Column(name = "lrr_complemento", length = 20)
	private String complemento;

	@Column(name = "lrr_contato", length = 30)
	private String contato;

	@Column(name = "lrr_fone", length = 16)
	private String telefone;

	@Column(name = "lrr_ativo")
	private int flagAtivo;

	public LocalRetira() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	@Override
	public Integer getBairroCodigo() {
		return bairroCodigo;
	}

	public void setBairroCodigo(Integer bairroCodigo) {
		this.bairroCodigo = bairroCodigo;
	}

	@Override
	public Integer getCidadeCodigo() {
		return cidadeCodigo;
	}

	public void setCidadeCodigo(Integer cidadeCodigo) {
		this.cidadeCodigo = cidadeCodigo;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Bairro getBairro() {
		return bairro;
	}

	public void setBairro(Bairro bairro) {
		this.bairro = bairro;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getUfsigla() {
		return ufsigla;
	}

	public void setUfsigla(String ufsigla) {
		this.ufsigla = ufsigla;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getContato() {
		return contato;
	}

	public void setContato(String contato) {
		this.contato = contato;
	}

	public String getTelefone() {
		return telefone;
	}

	public void setTelefone(String telefone) {
		this.telefone = telefone;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
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

	@Override
	public Integer getPaisCodigo() {
		return 1058;
	}

	@Override
	public String toString() {

		StringBuilder sb = new StringBuilder();

		if (StringUtil.isValida(descricao)) {
			sb.append(descricao.trim()).append(" | ");
		}

		sb.append(EnderecoUtil.toString(this));

		if (StringUtil.isValida(telefone)) {
			sb.append(" | ").append(telefone.trim());

			if (StringUtil.isValida(contato)) {
				sb.append(" ").append(contato.trim());
			}
		}

		return sb.toString();
	}

	public static List<LocalRetira> recuperar(GenericoDAO dao) {
		return dao.list(LocalRetira.class, "lrr_ativo = 1", null, "descricao", null);
	}

	public static LocalRetira recuperar(GenericoDAO dao, int localRetiraCodigo) {

		return dao.uniqueResult(LocalRetira.class, "lrr_ativo = 1 AND lrr_codigo=" + localRetiraCodigo, null);
	}
}