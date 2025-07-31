/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

/**
 * 
 * @author Desenvolvimento
 */
@Entity
@Table(name = "link")
@XmlRootElement
public class Link implements Serializable, IPersistent {
	private static final long serialVersionUID = 1L;
	
	protected static final String COLUNA_ATIVO = "lnk_ativo";

	@EmbeddedId
	protected LinkPK linkPK;

	@Lob
	@Column(name = "lnk_link")
	private String link;

	@Column(name = "lnk_ordem")
	private int ordemLink;
	
	@Column(name = "lnk_ordemgrupo")
	private int ordemGrupo;
	
	@Column(name = "lnk_tooltip")
	private String tooltip;

	@Column(name = COLUNA_ATIVO)
	private int flagAtivo;
	
	@Column(name = "lnk_pagcodigo")
	private Integer codigoPagina;

	public Link() {
	}

	public Link(LinkPK linkPK) {
		this.linkPK = linkPK;
	}

	public Link(String lnkGrupo, String lnkTitulo) {
		this.linkPK = new LinkPK(lnkGrupo, lnkTitulo);
	}

	public LinkPK getLinkPK() {
		return linkPK;
	}

	public void setLinkPK(LinkPK linkPK) {
		this.linkPK = linkPK;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public int getOrdemGrupo() {
		return ordemGrupo;
	}

	public void setOrdemGrupo(int ordemGrupo) {
		this.ordemGrupo = ordemGrupo;
	}
	
	public int getOrdemLink() {
		return ordemLink;
	}

	public void setOrdemLink(int ordemLink) {
		this.ordemLink = ordemLink;
	}
	
	public String getGrupo() {
		return getLinkPK().getGrupo();
	}

	public void setGrupo(String grupo) {
		getLinkPK().setGrupo(grupo);
	}

	public String getTitulo() {
		return getLinkPK().getTitulo();
	}

	public void setTitulo(String titulo) {
		getLinkPK().setTitulo(titulo);
	}

	public String getTooltip() {
		return tooltip;
	}

	public void setTooltip(String tooltip) {
		this.tooltip = tooltip;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public Integer getCodigoPagina() {
		return codigoPagina;
	}

	public void setCodigoPagina(Integer codigoPagina) {
		this.codigoPagina = codigoPagina;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		
	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		return super.clone();
	}
	
	public static List<Link> recuperarLinksAtivos(GenericoDAO dao){
		return dao.list(Link.class, COLUNA_ATIVO + " = 1", null, "ordemGrupo, ordemLink", null);
	}

}
