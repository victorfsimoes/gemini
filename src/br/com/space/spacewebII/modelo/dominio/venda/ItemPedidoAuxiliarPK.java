package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 * 
 * @author Sandro
 */
@Embeddable
public class ItemPedidoAuxiliarPK implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Basic(optional = false)
	@Column(name = "Ipx_SpvCodigo")
	private String serieCodigo;

	@Basic(optional = false)
	@Column(name = "Ipx_PedNumero")
	private int numero;

	@Basic(optional = false)
	@Column(name = "Ipx_NumItem")
	private int numeroItem;

	@Basic(optional = false)
	@Column(name = "Ipx_FilCodigo")
	private int filialCodigo;

	public ItemPedidoAuxiliarPK() {
	}

	public ItemPedidoAuxiliarPK(String ipxSpvCodigo, int ipxPedNumero,
			int ipxNumItem, int ipxFilCodigo) {
		this.serieCodigo = ipxSpvCodigo;
		this.numero = ipxPedNumero;
		this.numeroItem = ipxNumItem;
		this.filialCodigo = ipxFilCodigo;
	}

	public String getSerieCodigo() {
		return serieCodigo;
	}

	public void setSerieCodigo(String serieCodigo) {
		this.serieCodigo = serieCodigo;
	}

	public int getNumero() {
		return numero;
	}

	public void setNumero(int numero) {
		this.numero = numero;
	}

	public int getNumeroItem() {
		return numeroItem;
	}

	public void setNumeroItem(int numeroItem) {
		this.numeroItem = numeroItem;
	}

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

}
