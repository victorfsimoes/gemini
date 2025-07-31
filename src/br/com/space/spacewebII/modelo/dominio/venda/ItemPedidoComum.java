package br.com.space.spacewebII.modelo.dominio.venda;

import br.com.space.spacewebII.modelo.dominio.interfaces.IItemPedidoWeb;

public abstract class ItemPedidoComum implements IItemPedidoWeb {

	@Override
	public double calcularPrecoLiquido() {
		return ((getPrecoVenda() * getQuantidade()) + getAcrescimoPedidoValor() - getDescontoPedidoValor())
				/ getQuantidade();
	}
	
	@Override
	public double getPrecoLiquidoTotalCalculado() {
		return calcularPrecoLiquido() * getQuantidade();
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

}
