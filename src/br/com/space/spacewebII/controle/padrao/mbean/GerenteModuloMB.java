package br.com.space.spacewebII.controle.padrao.mbean;

import java.util.List;

import javax.inject.Inject;

import br.com.space.spacewebII.modelo.padrao.interfaces.Listavel;
import br.com.space.spacewebII.modelo.padrao.interfaces.PesquisaPadrao;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;

/**
 * Classe padrão de Módulos
 * 
 * @author Desenvolvimento
 * 
 * @param <T>
 *            O tipo com que será realizada toda a interação
 */
public abstract class GerenteModuloMB<T> extends GerenteMB implements
		Listavel<T>, PesquisaPadrao {

	@Inject
	protected Propriedade propriedade = null;

	private static final long serialVersionUID = 1L;

	private List<T> listaResultadoPesquisa = null;

	private int paginaAtualScroller = 1;
	private boolean habilitarPesquisaAtivoEInativo = false;

	protected boolean valorCheckAtivo = true;
	protected boolean valorCheckInativo = true;

	/**
	 * Construtor vazio
	 */
	public GerenteModuloMB() {

	}

	/**
	 * Construtor que inicializa este Bean permitindo realizar a pesquisa padrão
	 * de ativos e inativos
	 * 
	 * @param habilitarPesquisaAtivoEInativo
	 *            Flag que permite habilitar a pesquisa por ativos e inativos.
	 *            Por padrão, a pesquisa está desativada.
	 */
	public GerenteModuloMB(boolean habilitarPesquisaAtivoEInativo) {
		this();
		this.habilitarPesquisaAtivoEInativo = habilitarPesquisaAtivoEInativo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Pesquisavel#executarPesquisa
	 * ()
	 */
	@Override
	public void executarPesquisa() {
		listaResultadoPesquisa = pesquisar();
	}

	/**
	 * Método responsável por retornar uma lista de objetos de acordo com os
	 * filtros implementados.
	 * 
	 * Método chamado pelo executar pesquisa
	 * 
	 * @return Lista contendo a pesquisa
	 */
	public abstract List<T> pesquisar();

	/**
	 * Recarrega a última pesquisa feita.
	 */
	public void recarregarPesquisa() {
		try {

			if (listaResultadoPesquisa != null
					&& listaResultadoPesquisa.size() > 0) {
				executarPesquisa();
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Listavel#getListaResultados
	 * ()
	 */
	@Override
	public List<T> getListaResultados() {
		return listaResultadoPesquisa;
	}

	/**
	 * Método que limpa a lista de pesquisa
	 */
	public void limparListaResultados() {
		if (listaResultadoPesquisa != null) {
			listaResultadoPesquisa.clear();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Listavel#setListaResultados
	 * (java.util.List)
	 */
	@Override
	public void setListaResultados(List<T> list) {
		this.listaResultadoPesquisa = list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.controle.padrao.mbean.GerenteMB#getPropriedade()
	 */
	@Override
	public Propriedade getPropriedade() {
		return propriedade;
	}



	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Listavel#getPaginaAtualScroller
	 * ()
	 */
	@Override
	public int getPaginaAtualScroller() {
		return paginaAtualScroller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Listavel#setPaginaAtualScroller
	 * (int)
	 */
	@Override
	public void setPaginaAtualScroller(int paginaAtualScroller) {
		this.paginaAtualScroller = paginaAtualScroller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.Pesquisavel#
	 * getHAbilitarPesquisaAtivoEInativo()
	 */
	@Override
	public boolean getHabilitarPesquisaAtivoEInativo() {
		return habilitarPesquisaAtivoEInativo;
	}

	/**
	 * 
	 * @param ativarPesquisaAtivoEInativo
	 *            TRUE para habilitar a pesquisa por ativo e inativo, FALSE para
	 *            desativar
	 */
	public void setHabilitarPesquisaAtivoEInativo(
			boolean ativarPesquisaAtivoEInativo) {
		this.habilitarPesquisaAtivoEInativo = ativarPesquisaAtivoEInativo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Pesquisavel#getValorCheckAtivo
	 * ()
	 */
	@Override
	public boolean getValorCheckAtivo() {
		return valorCheckAtivo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Pesquisavel#setValorCheckAtivo
	 * (boolean)
	 */
	@Override
	public void setValorCheckAtivo(boolean valorCheckAtivo) {
		this.valorCheckAtivo = valorCheckAtivo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.Pesquisavel#
	 * getValorCheckInativo()
	 */
	@Override
	public boolean getValorCheckInativo() {
		return valorCheckInativo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.Pesquisavel#
	 * setValorCheckInativo(boolean)
	 */
	@Override
	public void setValorCheckInativo(boolean valorCheckInativo) {
		this.valorCheckInativo = valorCheckInativo;
	}
}
