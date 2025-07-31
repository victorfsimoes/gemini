package br.com.space.spacewebII.modelo.dominio.estoque;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.cliente.Cliente;

@Entity
@Table(name = "avisoestoque")
public class AvisoEstoque implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;
	private static final String COLUNA_ENVIAR = "aes_enviar";
	private static final String COLUNA_PRODUTO_CODIGO = "aes_procodigo";

	@Id
	@Column(name = "aes_codigo")
	@GeneratedValue
	private int codigo;

	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@NotFound(action = NotFoundAction.IGNORE)
	@JoinColumn(name = COLUNA_PRODUTO_CODIGO, updatable = false, insertable = false)
	private Produto produto;

	@Column(name = COLUNA_PRODUTO_CODIGO)
	private int produtoCodigo = 0;

	@ManyToOne(optional = true)
	@Fetch(FetchMode.JOIN)
	@JoinColumn(name = "aes_clicodigo", updatable = false, insertable = false)
	@NotFound(action = NotFoundAction.IGNORE)
	private Cliente cliente;

	@Column(name = "aes_clicodigo")
	private int clienteCodigo = 0;

	@Column(name = "aes_filcodigo")
	private int filialCodigo;

	@Column(name = "aes_dtreq")
	@Temporal(TemporalType.DATE)
	private Date dataRequisicao;

	@Column(name = "aes_dtaviso")
	@Temporal(TemporalType.DATE)
	private Date dataAviso;

	@Column(name = COLUNA_ENVIAR)
	private int flagEnviar;

	public AvisoEstoque() {

	}

	public AvisoEstoque(Produto produto, Cliente cliente, int filialCodigo,
			Date dataRequisicao, Date dataAviso, int flagEnviar) {
		super();
		this.produto = produto;
		this.cliente = cliente;
		this.filialCodigo = filialCodigo;
		this.dataRequisicao = dataRequisicao;
		this.dataAviso = dataAviso;
		this.flagEnviar = flagEnviar;
	}

	public AvisoEstoque(int produtoCodigo, int clienteCodigo, int filialCodigo,
			Date dataRequisicao, Date dataAviso, int flagEnviar) {
		super();

		this.produtoCodigo = produtoCodigo;
		this.clienteCodigo = clienteCodigo;
		this.filialCodigo = filialCodigo;
		this.dataRequisicao = dataRequisicao;
		this.dataAviso = dataAviso;
		this.flagEnviar = flagEnviar;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Produto getProduto() {
		return produto;
	}

	public void setProdutoCodigo(Produto produto) {
		this.produto = produto;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setClienteCodigo(Cliente cliente) {
		this.cliente = cliente;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public Date getDataRequisicao() {
		return dataRequisicao;
	}

	public void setDataRequisicao(Date dataRequisicao) {
		this.dataRequisicao = dataRequisicao;
	}

	public Date getDataAviso() {
		return dataAviso;
	}

	public void setDataAviso(Date dataAviso) {
		this.dataAviso = dataAviso;
	}

	public int getFlagEnviar() {
		return flagEnviar;
	}

	public void setFlagEnviar(int flagEnviar) {
		this.flagEnviar = flagEnviar;
	}

	public static List<AvisoEstoque> recuperarSolicitacoesAviso(
			GenericoDAO dao, int filialCodigo) {

		return dao.list(AvisoEstoque.class, COLUNA_ENVIAR
				+ " = 1 and aes_filcodigo = " + filialCodigo, null,
				"produtoCodigo", null);
	}

	public static List<AvisoEstoque> recuperarSolicitacaoEspecifica(
			GenericoDAO dao, int filialCodigo, int clienteCodigo,
			int produtoCodigo) {

		return dao.list(AvisoEstoque.class, COLUNA_ENVIAR
				+ " = 1 and aes_filcodigo = " + filialCodigo
				+ " and aes_clicodigo = " + clienteCodigo + " and "
				+ COLUNA_PRODUTO_CODIGO + " = " + produtoCodigo, null,
				null, null);
	}

	public int getProdutoCodigo() {
		return produtoCodigo;
	}

	public void setProdutoCodigo(int produtoCodigo) {
		this.produtoCodigo = produtoCodigo;
	}

	public int getClienteCodigo() {
		return clienteCodigo;
	}

	public void setClienteCodigo(int clienteCodigo) {
		this.clienteCodigo = clienteCodigo;
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
	}

}
