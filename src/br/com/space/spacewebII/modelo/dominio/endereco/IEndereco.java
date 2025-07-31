package br.com.space.spacewebII.modelo.dominio.endereco;

public interface IEndereco {

	public String getCep();

	public String getComplemento();

	public String getLogradouro();

	public String getNumero();

	public String getUfsigla();

	public Bairro getBairro();

	public Cidade getCidade();

	public Integer getPaisCodigo();

	public Integer getCidadeCodigo();

	public Integer getBairroCodigo();

}
