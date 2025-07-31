package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "paginaweb")
@XmlRootElement
public class PaginaPersonalizadaWeb implements Serializable, IPersistent {

	private static final long serialVersionUID = 1L;
	private static final String COLUNA_CODIGO = "pag_codigo";
	private static final String COLUNA_FILIAL_CODIGO = "pag_filcodigo";
	private static final String COLUNA_ATIVO = "pag_ativo";

	@Id
	@Column(name = COLUNA_CODIGO)
	private int codigo;

	@Column(name = COLUNA_FILIAL_CODIGO)
	private Integer filialCodigo;

	@Column(name = "pag_texto")
	private String conteudo;

	@Column(name = "pag_titulo")
	private String titulo;

	public PaginaPersonalizadaWeb() {

	}

	public static PaginaPersonalizadaWeb recuperarUnicoAtivo(GenericoDAO dao,
			int codigoPagina, Integer codigoFilial) {

		if (codigoPagina <= 0)
			return null;

		StringBuilder where = new StringBuilder(COLUNA_ATIVO)
				.append(" = 1 and ").append(COLUNA_CODIGO).append(" = ")
				.append(codigoPagina);

		if (codigoFilial != null && codigoFilial > 0)
			where.append(" ").append(COLUNA_FILIAL_CODIGO).append(" = ").append(codigoFilial);

		return dao.uniqueResult(PaginaPersonalizadaWeb.class, where.toString(), null);
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public Integer getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(Integer filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public String getConteudo() {
		return conteudo;
	}

	public void setConteudo(String conteudo) {
		this.conteudo = conteudo;
	}

	public String getTitulo() {
		return titulo;
	}

	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
}
