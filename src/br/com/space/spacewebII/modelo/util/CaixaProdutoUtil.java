package br.com.space.spacewebII.modelo.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import br.com.space.spacewebII.modelo.widget.CaixaProdutoVisualizavel;

public class CaixaProdutoUtil implements Serializable {

	private static final long serialVersionUID = 1L;

	private static Comparator<CaixaProdutoVisualizavel> comparatorCodigo = null;
	private static Comparator<CaixaProdutoVisualizavel> comparatorDescricao = null;

	/**
	 * Pesquisa na lista os produtos desejados. Por código ou descrição.
	 * 
	 * @param filtroCodigoOuDescricao
	 * @param produtos
	 * @return
	 */
	public static List<CaixaProdutoVisualizavel> pesquisarNaLista(
			final String filtroCodigoOuDescricao,
			List<CaixaProdutoVisualizavel> produtos) {

		if (produtos == null || produtos.size() == 0) {
			return null;
		}

		List<CaixaProdutoVisualizavel> result = new ArrayList<CaixaProdutoVisualizavel>();

		boolean pesquisaPorCodigoOuDescricao = filtroCodigoOuDescricao != null
				&& !filtroCodigoOuDescricao.trim().isEmpty();

		boolean pesquisaPorCodigo = pesquisaPorCodigoOuDescricao
				&& filtroCodigoOuDescricao.matches("[0-9]+");

		boolean pesquisaPorDescricao = pesquisaPorCodigoOuDescricao
				&& !pesquisaPorCodigo;

		if (!pesquisaPorCodigoOuDescricao) {

			return produtos;

		} else {

			for (CaixaProdutoVisualizavel produto : produtos) {
				boolean candidato = false;

				if (pesquisaPorDescricao
						&& produto.getDescricaoVisualizacao() != null) {
					candidato = produto.getDescricaoVisualizacao()
							.toLowerCase()
							.startsWith(filtroCodigoOuDescricao.toLowerCase());
				} else {
					candidato = Integer.toString(produto.getCodigo()).equals(
							filtroCodigoOuDescricao);
				}

				if (candidato) {
					result.add(produto);
				}
			}

			return result;
		}
	}

	/**
	 * 
	 * @return
	 */
	@SuppressWarnings("unused")
	private static Comparator<CaixaProdutoVisualizavel> getComparatorCodigo() {

		if (comparatorCodigo == null) {

			comparatorCodigo = new Comparator<CaixaProdutoVisualizavel>() {

				@Override
				public int compare(CaixaProdutoVisualizavel o1,
						CaixaProdutoVisualizavel o2) {
					return Integer.compare(o1.getCodigo(), o2.getCodigo());
				}
			};
		}

		return null;
	}

	/**
	 * 
	 * @return
	 */
	public static Comparator<CaixaProdutoVisualizavel> getComparatorDescricao() {

		if (comparatorDescricao == null) {
			comparatorDescricao = new Comparator<CaixaProdutoVisualizavel>() {

				@Override
				public int compare(CaixaProdutoVisualizavel o1,
						CaixaProdutoVisualizavel o2) {

					if (o1.getDescricaoVisualizacao() == null) {
						return -1;
					}
					return o1.getDescricaoVisualizacao().compareTo(
							o2.getDescricaoVisualizacao());
				}
			};
		}
		return comparatorDescricao;
	}

	public static Comparator<CaixaProdutoVisualizavel> getNewComparatorDescricaoEstoque(
			final boolean ordenarSemEstoque) {

		return new Comparator<CaixaProdutoVisualizavel>() {

			@Override
			public int compare(CaixaProdutoVisualizavel o1,
					CaixaProdutoVisualizavel o2) {

				if ((o1.getEstoqueVisualizacao() > 0 && o2
						.getEstoqueVisualizacao() > 0)
						|| (o1.getEstoqueVisualizacao() <= 0 && o2
								.getEstoqueVisualizacao() <= 0)
						|| !ordenarSemEstoque) {

					return o1.getDescricaoVisualizacao().compareTo(
							o2.getDescricaoVisualizacao());

				} else if (o1.getEstoqueVisualizacao() > 0
						&& o2.getEstoqueVisualizacao() <= 0) {
					return -1;
				} else {
					return 1;
				}
			}
		};
	}

}
