package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;


@Embeddable
public class GrdsPK implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Column(name = Grds.COLUNA_CODIGO_FILIAL)
		int codigoFilial = 0;
	
	@Column(name = Grds.COLUNA_WAY)
		int way = 0;
	
	public GrdsPK(){
		super();
	}
	public GrdsPK(int codigoFilial, int way){
		super();
		this.codigoFilial = codigoFilial;
		this.way = way;
	}
	
	public int getCodigoFilial() {
		return codigoFilial;
	}

	public void setCodigoFilial(int codigoFilial) {
		this.codigoFilial = codigoFilial;
	}

	public int getWay() {
		return way;
	}

	public void setWay(int way) {
		this.way = way;
	}
	
}