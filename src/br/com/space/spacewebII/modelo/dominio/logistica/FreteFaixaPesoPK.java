package br.com.space.spacewebII.modelo.dominio.logistica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class FreteFaixaPesoPK implements Serializable {

	private static final long serialVersionUID = 1L;

	@Column(name = FreteFaixaPeso.COLUNA_FRETEFAIXACODIGO)
	private int freteFaixaCodigo;

	@Column(name = FreteFaixaPeso.COLUNA_PESOINI)
	private double pesoInicial;

	@Column(name = FreteFaixaPeso.COLUNA_PESOFINAL)
	private double pesoFinal;

	public FreteFaixaPesoPK() {

	}

	public int getFreteFaixaCodigo() {
		return freteFaixaCodigo;
	}

	public void setFreteFaixaCodigo(int freteFaixaCodigo) {
		this.freteFaixaCodigo = freteFaixaCodigo;
	}

	public double getPesoInicial() {
		return pesoInicial;
	}

	public void setPesoInicial(double pesoInicial) {
		this.pesoInicial = pesoInicial;
	}

	public double getPesoFinal() {
		return pesoFinal;
	}

	public void setPesoFinal(double pesoFinal) {
		this.pesoFinal = pesoFinal;
	}

}
