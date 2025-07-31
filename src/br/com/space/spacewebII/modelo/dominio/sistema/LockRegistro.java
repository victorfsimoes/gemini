package br.com.space.spacewebII.modelo.dominio.sistema;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;


@Entity(name="lockreg")
@Table(name="lockreg")
public class LockRegistro implements IPersistent, Comparable<LockRegistro>{

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="lcr_sequencial")
	private int codigo;
	
	@Column(name="lcr_sescodigo")
	private int codigoSessao;
	
	@Column(name="lcr_data")
	private Date data;
	
	@Column(name="lcr_hora")
	private String hora;
	
	@Column(name="lcr_programa")
	private String programa;
	
	@Column(name="lcr_valor")
	private String valorChave;
	
	@Column(name="lcr_tabela")
	private String nomeTabela;
	
	public LockRegistro() {
		
	}
	
	public LockRegistro(int codigo, int codigoSessao, Date data, String hora,
			String programa, String valorChave, String nomeTabela) {
		super();
		this.codigo = codigo;
		this.codigoSessao = codigoSessao;
		this.data = data;
		this.hora = hora;
		this.programa = programa;
		this.valorChave = valorChave;
		this.nomeTabela = nomeTabela;
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

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public int getSessaoCodigo() {
		return codigoSessao;
	}

	public void setCodigoSessao(int codigoSessao) {
		this.codigoSessao = codigoSessao;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getHora() {
		return hora;
	}

	public void setHora(String hora) {
		this.hora = hora;
	}

	public String getPrograma() {
		return programa;
	}

	public void setPrograma(String programa) {
		this.programa = programa;
	}

	public String getValorChave() {
		return valorChave;
	}

	public void setValorChave(String valorChave) {
		this.valorChave = valorChave;
	}

	public String getNomeTabela() {
		return nomeTabela;
	}

	public void setNomeTabela(String nomeTabela) {
		this.nomeTabela = nomeTabela;
	}

	@Override
	public int compareTo(LockRegistro o) {
		if(codigo > o.getCodigo()){
			return 1;
		}else if (codigo == o.getCodigo()){
			return 0;
		}else{
			return -1;
		}
	}
	
}
