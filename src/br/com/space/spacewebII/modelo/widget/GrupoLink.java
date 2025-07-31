package br.com.space.spacewebII.modelo.widget;

import java.util.ArrayList;
import java.util.List;

import br.com.space.api.core.util.ListUtil;
import br.com.space.spacewebII.modelo.dominio.sistema.Link;

/**
 * Classe que padroniza os link que são exibidos no rodapé
 * 
 * @author Renato
 * 
 */
public class GrupoLink implements GrupoWidget {

	private static final long serialVersionUID = 1L;
	private String descricao = null;
	private List<Link> links = null;

	public GrupoLink(String descricao) {
		this.descricao = descricao;

	}

	@Override
	public String getDescricao() {
		return descricao;
	}

	@Override
	public int getCodigo() {
		return 0;
	}

	public void addLink(Link link) {

		if (links == null) {
			links = new ArrayList<Link>();
		}

		links.add(link);
	}

	public List<Link> getLinks() {
		return links;
	}

	public boolean existeLinks() {
		return ListUtil.isValida(links);
	}

	/**
	 * Pesquisa dentro da lista um GrupoLink que tenha sua descrição iqual ao
	 * grupoDescricao em parametro
	 * 
	 * @param grupoLinks
	 * @param grupoDescricao
	 * @return
	 */
	public static GrupoLink pesquisarGrupoLinkNaLista(
			List<GrupoLink> grupoLinks, String grupoDescricao) {

		if (ListUtil.isValida(grupoLinks)) {

			for (GrupoLink grupoLink : grupoLinks) {
				if (grupoLink.getDescricao().equalsIgnoreCase(grupoDescricao)) {
					return grupoLink;
				}
			}
		}

		return null;
	}
}
