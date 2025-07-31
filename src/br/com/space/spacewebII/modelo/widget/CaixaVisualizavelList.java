package br.com.space.spacewebII.modelo.widget;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Classe que representa uma coleção de {@link CaixaProdutoVisualizavel}
 * 
 * 
 * @author Desemvolvedor
 */
public abstract class CaixaVisualizavelList extends
		ArrayList<CaixaProdutoVisualizavel> {

	private static final long serialVersionUID = 1L;
	private HashMap<String, Boolean> produtosConfigurados;

	public CaixaVisualizavelList() {
		produtosConfigurados = new HashMap<>();

	}

	/**
	 * Preenche os atributos do produto visualizavel por demanda
	 * 
	 */
	@Override
	public CaixaProdutoVisualizavel get(int index) {

		CaixaProdutoVisualizavel produtoVisualizavel = super.get(index);

		String keyHas = produtoVisualizavel.getCodigo()
				+ produtoVisualizavel.getDescricaoVisualizacao();

		boolean configurado = produtosConfigurados.containsKey(keyHas);

		if (!configurado) {
			try {

				aoRetornarUmObjeto(produtoVisualizavel);

				produtosConfigurados.put(keyHas, true);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return produtoVisualizavel;
	}

	public abstract void aoRetornarUmObjeto(
			CaixaProdutoVisualizavel produtoVisualizavel);

	/**
	 * Limpa tambem a lista que armazena qual produto ja foi configurado.
	 */
	@Override
	public void clear() {
		reconfigurarProdutos();
		super.clear();
	}

	public void reconfigurarProdutos() {
		produtosConfigurados.clear();
	}

}
