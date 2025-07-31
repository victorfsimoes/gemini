package br.com.space.spacewebII.modelo.dominio.logistica;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.GeneratedValue;

@Embeddable
public class FretePK implements Serializable {
	
	private static final long serialVersionUID = 1L;

	@GeneratedValue
	@Column(name = "frt_codigo")
	private int codigo;

	@Column(name = "frt_filcodigo")
	private int filialCodigo;
}
