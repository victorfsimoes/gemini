package br.com.space.spacewebII.controle.aplicacao;

import java.io.Serializable;

import javax.inject.Inject;

import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Link;
import br.com.space.spacewebII.modelo.dominio.sistema.PaginaPersonalizadaWeb;

import com.ocpsoft.pretty.faces.annotation.URLMapping;
import com.ocpsoft.pretty.faces.annotation.URLMappings;

@ManagedBeanSessionScoped
@URLMappings(mappings = {
		@URLMapping(id = "mapa", pattern = "/paginas/", viewId = "/pages/paginas/mapaPaginas.xhtml", onPostback = false),
		@URLMapping(id = "mapa2", pattern = "/paginas", viewId = "/pages/paginas/mapaPaginas.xhtml", onPostback = false),
		@URLMapping(id = "paginaCodigo", pattern = "/paginas/#{paginaPersonalizadaWebMB.codigoPagURL}", viewId = "/pages/paginas/paginaPersonalizada.xhtml", onPostback = false),
		@URLMapping(id = "paginaCodigoTitulo", pattern = "/paginas/#{paginaPersonalizadaWebMB.codigoPagURL}/#{paginaPersonalizadaWebMB.tituloPagURL}", viewId = "/pages/paginas/paginaPersonalizada.xhtml", onPostback = false) })
public class PaginaPersonalizadaWebMB implements Serializable{

	private static final long serialVersionUID = 1L;

	private String codigoPagURL;
	private String tituloPagURL;
	
	private PaginaPersonalizadaWeb pagina;

	private Link link;

	@Inject
	private GenericoDAO dao;
	
	public PaginaPersonalizadaWebMB(){
		
	}
	
	public void aoCarregar(){
		carregarPaginaPelaURL(codigoPagURL);
	}
	
	public void carregarPaginaPelaURL(String codigoURL){
		if(codigoURL != null)
			carregarPagina(Integer.valueOf(codigoURL));
	}

	public String carregarPagina(int codigo) {
		
		String url = "/paginas/";
		
		pagina = PaginaPersonalizadaWeb.recuperarUnicoAtivo(dao, codigo, 0);
		
		if(pagina != null)
			url += codigo + "/";
		
		return url;
	}
	
	public PaginaPersonalizadaWeb getPagina() {
		return pagina;
	}

	public void setPagina(PaginaPersonalizadaWeb pagina) {
		this.pagina = pagina;
	}

	public Link getLink() {
		return link;
	}

	public void setLink(Link link) {
		this.link = link;
	}

	public String getCodigoPagURL() {
		return codigoPagURL;
	}

	public void setCodigoPagURL(String codigoPagURL) {
		this.codigoPagURL = codigoPagURL;
	}

	public String getTituloPagURL() {
		return tituloPagURL;
	}

	public void setTituloPagURL(String tituloPagURL) {
		this.tituloPagURL = tituloPagURL;
	}
}
