package br.com.space.spacewebII.modelo.dominio.endereco;

public class EnderecoBase implements IEndereco {

	private String cep;
	private String logradouro;
	private String numero;
	private String ufSigla;
	private int paisCodigo;
	private int cidadeCodigo;
	private int bairroCodigo;

	/**
	 * 
	 * @param cep
	 * @param logradouro
	 * @param numero
	 * @param ufSigla
	 * @param paisCodigo
	 * @param cidadeCodigo
	 * @param bairroCodigo
	 */
	public EnderecoBase(String cep, String logradouro, String numero,
			String ufSigla, int paisCodigo, int cidadeCodigo, int bairroCodigo) {
		super();
		this.cep = cep;
		this.logradouro = logradouro;
		this.numero = numero;
		this.ufSigla = ufSigla;
		this.paisCodigo = paisCodigo;
		this.cidadeCodigo = cidadeCodigo;
		this.bairroCodigo = bairroCodigo;
	}

	@Override
	public String getCep() {
		return cep;
	}

	@Override
	public String getLogradouro() {
		return logradouro;
	}

	@Override
	public String getNumero() {
		return numero;
	}

	@Override
	public String getUfsigla() {
		return ufSigla;
	}

	@Override
	public Integer getPaisCodigo() {
		return paisCodigo;
	}

	@Override
	public Integer getCidadeCodigo() {
		return cidadeCodigo;
	}

	@Override
	public Integer getBairroCodigo() {
		return bairroCodigo;
	}

	@Override
	public String getComplemento() {
		return null;
	}

	@Override
	public Bairro getBairro() {
		return null;
	}

	@Override
	public Cidade getCidade() {
		return null;
	}
}