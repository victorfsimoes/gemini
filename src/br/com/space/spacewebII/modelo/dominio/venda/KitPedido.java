/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.negocio.modelo.dominio.IItemPedido;
import br.com.space.api.negocio.modelo.dominio.IKitPedido;
import br.com.space.api.negocio.modelo.dominio.Ikit;
import br.com.space.api.spa.model.domain.IPersistent;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "kitpedido")
@XmlRootElement
public class KitPedido implements Serializable, IPersistent, IKitPedido {

	private static final long serialVersionUID = 1L;

	@EmbeddedId
	protected KitPedidoPK kitPedidoPK;

	@Basic(optional = false)
	@Column(name = "kpd_quantidade")
	private double quantidade;

	@Column(name = "kpd_precotab")
	private Double precoTabela;

	@Basic(optional = false)
	@Column(name = "kpd_preco")
	private double preco;

	@Column(name = "kpd_custo1")
	private double custo1;

	@Column(name = "kpd_custo2")
	private Double custo2;

	@Column(name = "kpd_custo3")
	private Double custo3;

	@Column(name = "kpd_custo4")
	private Double custo4;

	@Column(name = "kpd_custo5")
	private Double custo5;

	@Column(name = "kpd_custo6")
	private Double custo6;

	@Column(name = "kpd_custo7")
	private Double custo7;

	@Column(name = "kpd_custo8")
	private Double custo8;

	@Column(name = "kpd_custo9")
	private Double custo9;

	@Column(name = "kpd_custo10")
	private Double custo10;

	@Transient
	private Kit kit = null;

	@Transient
	private List<ItemPedido> itensPedido = null;

	public KitPedido() {

	}

	public KitPedido(Ikit kit) {
		setKit(kit);

	}

	public KitPedidoPK getKitPedidoPK() {

		if (kitPedidoPK == null) {
			kitPedidoPK = new KitPedidoPK();
		}

		return kitPedidoPK;
	}

	public void setKitPedidoPK(KitPedidoPK kitPedidoPK) {
		this.kitPedidoPK = kitPedidoPK;
	}

	public double getQuantidade() {
		return quantidade;
	}

	public void setQuantidade(double quantidade) {
		this.quantidade = quantidade;
	}

	public Double getPrecoTabela() {
		return precoTabela;
	}

	public void setPrecoTabela(Double precoTabela) {
		this.precoTabela = precoTabela;
	}

	public double getPreco() {
		return preco;
	}

	public void setPreco(double preco) {
		this.preco = preco;
	}

	public double getCusto1() {
		return custo1;
	}

	public void setCusto1(double custo1) {
		this.custo1 = custo1;
	}

	public double getCusto2() {
		if (custo2 == null) {
			return 0;
		}
		return custo2;
	}

	public void setCusto2(Double custo2) {
		this.custo2 = custo2;
	}

	public double getCusto3() {
		if (custo3 == null) {
			return 0;
		}
		return custo3;
	}

	public void setCusto3(Double custo3) {
		this.custo3 = custo3;
	}

	public double getCusto4() {
		if (custo4 == null) {
			return 0;
		}
		return custo4;
	}

	public void setCusto4(Double custo4) {
		this.custo4 = custo4;
	}

	public double getCusto5() {
		if (custo5 == null) {
			return 0;
		}
		return custo5;
	}

	public void setCusto5(Double custo5) {
		this.custo5 = custo5;
	}

	public double getCusto6() {
		if (custo6 == null) {
			return 0;
		}
		return custo6;
	}

	public void setCusto6(Double custo6) {
		this.custo6 = custo6;
	}

	public double getCusto7() {
		if (custo7 == null) {
			return 0;
		}
		return custo7;
	}

	public void setCusto7(Double custo7) {
		this.custo7 = custo7;
	}

	public double getCusto8() {
		if (custo8 == null) {
			return 0;
		}
		return custo8;
	}

	public void setCusto8(Double custo8) {
		this.custo8 = custo8;
	}

	public double getCusto9() {
		if (custo9 == null) {
			return 0;
		}
		return custo9;
	}

	public void setCusto9(Double custo9) {
		this.custo9 = custo9;
	}

	public double getCusto10() {
		if (custo10 == null) {
			return 0;
		}
		return custo10;
	}

	public void setCusto10(Double custo10) {
		this.custo10 = custo10;
	}

	@Override
	public int getKitCodigo() {
		return this.getKitPedidoPK().getKitCodigo();
	}

	@Override
	public void setKitCodigo(int kitCodigo) {
		this.getKitPedidoPK().setKitCodigo(kitCodigo);
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


	public int getFilialCodigo() {
		return getKitPedidoPK().getFilialCodigo();
	}


	public void setFilialCodigo(int filialCodigo) {
		getKitPedidoPK().setFilialCodigo(filialCodigo);
	}

	@Override
	public String getSeriePedidosCodigo() {
		return getKitPedidoPK().getSeriePedidosCodigo();
	}

	@Override
	public void setSeriePedidosCodigo(String seriePedidosCodigo) {

		getKitPedidoPK().setSeriePedidosCodigo(seriePedidosCodigo);

	}

	@Override
	public int getPedidoNumero() {
		return getKitPedidoPK().getPedidoNumero();
	}

	@Override
	public void setPedidoNumero(int pedidoNumero) {

		getKitPedidoPK().setPedidoNumero(pedidoNumero);

	}

	@Override
	public Ikit getKit() {
		return kit;
	}

	public void setKit(Ikit kit) {
		this.kit = (Kit) kit;

		if (kit != null) {
			setKitCodigo(kit.getCodigo());
		}

	}

	@Override
	public List<ItemPedido> getIItensPedido() {
		return itensPedido;
	}

	@SuppressWarnings("unchecked")
	@Override
	public void setIItensPedido(List<? extends IItemPedido> iItemPedidos) {
		this.itensPedido = (List<ItemPedido>) iItemPedidos;

	}

}
