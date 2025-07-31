/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * 
 * @author Ronie
 */
@Entity
@Table(name = "parametrovik")
@XmlRootElement
public class ParametroViking implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "pav_filcodigo")
	private Integer filialCodigo;

	@Column(name = "pav_hostinter")
	private String hostConexaoInternet;

	@Column(name = "pav_portainter")
	private Integer portaConexaoInternet;

	@Column(name = "pav_hostlocal")
	private String hostConexaoLocal;

	@Column(name = "pav_portalocal")
	private Integer portaConexaoLocal;

	@Column(name = "pav_diasvalcarg")
	private Integer diasValidadeCarga;

	public ParametroViking() {
	}

	public ParametroViking(Integer pavFilcodigo) {
		this.filialCodigo = pavFilcodigo;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getHostConexaoInternet() {
		return hostConexaoInternet;
	}

	public void setHostConexaoInternet(String hostConexaoInternet) {
		this.hostConexaoInternet = hostConexaoInternet;
	}

	public Integer getPortaConexaoInternet() {
		return portaConexaoInternet;
	}

	public void setPortaConexaoInternet(Integer portaConexaoInternet) {
		this.portaConexaoInternet = portaConexaoInternet;
	}

	public String getHostConexaoLocal() {
		return hostConexaoLocal;
	}

	public void setHostConexaoLocal(String hostConexaoLocal) {
		this.hostConexaoLocal = hostConexaoLocal;
	}

	public Integer getPortaConexaoLocal() {
		return portaConexaoLocal;
	}

	public void setPortaConexaoLocal(Integer portaConexaoLocal) {
		this.portaConexaoLocal = portaConexaoLocal;
	}

	public Integer getDiasValidadeCarga() {
		return diasValidadeCarga;
	}

	public void setDiasValidadeCarga(Integer diasValidadeCarga) {
		this.diasValidadeCarga = diasValidadeCarga;
	}

}
