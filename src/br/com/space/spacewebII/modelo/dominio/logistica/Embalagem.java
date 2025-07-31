package br.com.space.spacewebII.modelo.dominio.logistica;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;

@Entity
@Table(name = "embalagem")
public class Embalagem implements IPersistent{

	@Id
	@Column(name = "emb_codigo")
	@GeneratedValue
	private int codigo;

	@Column(name = "emb_desc")
	private String descricao;

	@Column(name = "emb_tipo")
	private String tipo;

	@Column(name = "emb_valor")
	private double valor;

	@Column(name = "emb_peso")
	private double peso;

	@Column(name = "emb_largura")
	private double largura;

	@Column(name = "emb_altura")
	private double altura;

	@Column(name = "emb_comprimento")
	private double comprimento;

	@Column(name = "emb_diametro")
	private double diametro;

	@Column(name = "emb_cubagem")
	private double cubagem;

	@Column(name = "emb_ativo")
	private int flagAtivo;

	public Embalagem() {

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

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public double getValor() {
		return valor;
	}

	public void setValor(double valor) {
		this.valor = valor;
	}

	public double getPeso() {
		return peso;
	}

	public void setPeso(double peso) {
		this.peso = peso;
	}

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public double getAltura() {
		return altura;
	}

	public void setAltura(double altura) {
		this.altura = altura;
	}

	public double getComprimento() {
		return comprimento;
	}

	public void setComprimento(double comprimento) {
		this.comprimento = comprimento;
	}

	public double getDiametro() {
		return diametro;
	}

	public void setDiametro(double diametro) {
		this.diametro = diametro;
	}

	public double getCubagem() {
		return cubagem;
	}

	public void setCubagem(double cubagem) {
		this.cubagem = cubagem;
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
