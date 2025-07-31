package br.com.space.spacewebII.modelo.dominio.logistica;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;

@Entity
@Table(name = "frete")
public class Frete implements IPersistent {

	@EmbeddedId
	private FretePK fretePK;

	@Column(name = "frt_desc")
	private String descricao;

	@Column(name = "frt_uforigem")
	private String ufOrigem;

	@Column(name = "frt_origlocal")
	private int flagOrigemLocal;

	@Column(name = "frt_ufdestino")
	private String ufDestino;

	@Column(name = "frt_destlocal")
	private int flagDestinoLocal;

	@Column(name = "frt_tvecodigo")
	private int tipoVeiculoCodigo;

	@Column(name = "frt_tpecodigo")
	private String tipoEntregaCodigo;

	@Column(name = "frt_regcodigo")
	private int regiaoCodigo;

	@Column(name = "frt_usafaixapes")
	private int flagUsaFaixaPeso;

	@Column(name = "frt_frfcodigo")
	private int freteFaixaCodigo;

	@Column(name = "frt_valor")
	private double valor;

	@Column(name = "frt_somavalemb")
	private int flagSomaValorEmbalagem;

	@Column(name = "frt_isencao")
	private int flagIsencao;

	@Column(name = "frt_valorisento")
	private double valorIsencao;

	@Column(name = "frt_ativo")
	private int flagAtivo;

	public Frete() {

	}

	public FretePK getFretePK() {
		return fretePK;
	}

	public void setFretePK(FretePK fretePK) {
		this.fretePK = fretePK;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getUfOrigem() {
		return ufOrigem;
	}

	public void setUfOrigem(String ufOrigem) {
		this.ufOrigem = ufOrigem;
	}

	public int getFlagOrigemLocal() {
		return flagOrigemLocal;
	}

	public void setFlagOrigemLocal(int flagOrigemLocal) {
		this.flagOrigemLocal = flagOrigemLocal;
	}

	public String getUfDestino() {
		return ufDestino;
	}

	public void setUfDestino(String ufDestino) {
		this.ufDestino = ufDestino;
	}

	public int getFlagDestinoLocal() {
		return flagDestinoLocal;
	}

	public void setFlagDestinoLocal(int flagDestinoLocal) {
		this.flagDestinoLocal = flagDestinoLocal;
	}

	public int getTipoVeiculoCodigo() {
		return tipoVeiculoCodigo;
	}

	public void setTipoVeiculoCodigo(int tipoVeiculoCodigo) {
		this.tipoVeiculoCodigo = tipoVeiculoCodigo;
	}

	public String getTipoEntregaCodigo() {
		return tipoEntregaCodigo;
	}

	public void setTipoEntregaCodigo(String tipoEntregaCodigo) {
		this.tipoEntregaCodigo = tipoEntregaCodigo;
	}

	public int getRegiaoCodigo() {
		return regiaoCodigo;
	}

	public void setRegiaoCodigo(int regiaoCodigo) {
		this.regiaoCodigo = regiaoCodigo;
	}

	public int getFlagUsaFaixaPeso() {
		return flagUsaFaixaPeso;
	}

	public void setFlagUsaFaixaPeso(int flagUsaFaixaPeso) {
		this.flagUsaFaixaPeso = flagUsaFaixaPeso;
	}

	public int getFreteFaixaCodigo() {
		return freteFaixaCodigo;
	}

	public void setFreteFaixaCodigo(int freteFaixaCodigo) {
		this.freteFaixaCodigo = freteFaixaCodigo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public int getFlagSomaValorEmbalagem() {
		return flagSomaValorEmbalagem;
	}

	public void setFlagSomaValorEmbalagem(int flagSomaValorEmbalagem) {
		this.flagSomaValorEmbalagem = flagSomaValorEmbalagem;
	}

	public int getFlagIsencao() {
		return flagIsencao;
	}

	public void setFlagIsencao(int flagIsencao) {
		this.flagIsencao = flagIsencao;
	}

	public double getValorIsencao() {
		return valorIsencao;
	}

	public void setValorIsencao(double valorIsencao) {
		this.valorIsencao = valorIsencao;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

}
