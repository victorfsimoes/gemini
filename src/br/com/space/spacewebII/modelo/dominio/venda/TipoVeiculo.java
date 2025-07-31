package br.com.space.spacewebII.modelo.dominio.venda;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "tipoveiculo")
public class TipoVeiculo implements IPersistent {

	public static final int ACAO_CARGA_MINIMA_ERRO = 1;
	public static final int ACAO_CARGA_MINIMA_ASSUMIR = 2;

	@Id
	@Column(name = "tve_codigo")
	private int codigo;

	@Column(name = "tve_cargamax")
	private double cargaMaxima = 0;

	@Column(name = "tve_cargamin")
	private double cargaMinima = 0;

	@Column(name = "tve_altura")
	private double altura = 0;

	@Column(name = "tve_comprimento")
	private double comprimento = 0;

	@Column(name = "tve_largura")
	private double largura = 0;

	@Column(name = "tve_alturamin")
	private double alturaMinima = 0;

	@Column(name = "tve_comprmin")
	private double comprimentoMinima = 0;

	@Column(name = "tve_larguramin")
	private double larguraMinima = 0;

	@Column(name = "tve_regcubabmin")
	private int acaoCargaMinima = 0;

	public TipoVeiculo() {
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public double getCargaMaxima() {
		return cargaMaxima;
	}

	public void setCargaMaxima(double cargaMaxima) {
		this.cargaMaxima = cargaMaxima;
	}

	public double getCargaMinima() {
		return cargaMinima;
	}

	public void setCargaMinima(double cargaMinima) {
		this.cargaMinima = cargaMinima;
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

	public double getLargura() {
		return largura;
	}

	public void setLargura(double largura) {
		this.largura = largura;
	}

	public double getAlturaMinima() {
		return alturaMinima;
	}

	public void setAlturaMinima(double alturaMinima) {
		this.alturaMinima = alturaMinima;
	}

	public double getComprimentoMinima() {
		return comprimentoMinima;
	}

	public void setComprimentoMinima(double comprimentoMinima) {
		this.comprimentoMinima = comprimentoMinima;
	}

	public double getLarguraMinima() {
		return larguraMinima;
	}

	public void setLarguraMinima(double larguraMinima) {
		this.larguraMinima = larguraMinima;
	}

	public int getAcaoCargaMinima() {
		return acaoCargaMinima;
	}

	public void setAcaoCargaMinima(int acaoCargaMinima) {
		this.acaoCargaMinima = acaoCargaMinima;
	}

	public boolean isLancaErroCargaMinimaInvalida() {
		return this.acaoCargaMinima == ACAO_CARGA_MINIMA_ERRO;
	}

	public boolean isLancaErroCargaMinimaInvalida(double altura,
			double comprimento, double largura, double peso) {

		return isLancaErroCargaMinimaInvalida()
				&& !isCargaValidaDimensoesMinima(altura, comprimento, largura,
						peso);
	}

	public boolean isLancaErroCargaMaximaInvalida(double altura,
			double comprimento, double largura, double peso) {

		return !isCargaValidaDimensoesMaxima(altura, comprimento, largura, peso);
	}

	public boolean isAssumeCargaMinimaInvalida() {
		return this.acaoCargaMinima == ACAO_CARGA_MINIMA_ASSUMIR;
	}

	public boolean isCargaValidaDimensoesMaxima(double altura,
			double comprimento, double largura, double peso) {

		return ((altura + comprimento + largura) <= (this.altura
				+ this.comprimento + this.largura))
				&& peso <= this.cargaMaxima;
	}

	public boolean isCargaValidaDimensoesMinima(double altura,
			double comprimento, double largura, double peso) {

		return ((altura + comprimento + largura) >= (this.alturaMinima
				+ this.comprimentoMinima + this.larguraMinima))
				&& peso >= this.cargaMinima;
	}

	public static TipoVeiculo recuperarTipoVeiculo(GenericoDAO dao,
			int tipoVeiculoCodigo) {

		return dao.uniqueResult(TipoVeiculo.class, "tve_codigo = "
				+ tipoVeiculoCodigo, null);

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
