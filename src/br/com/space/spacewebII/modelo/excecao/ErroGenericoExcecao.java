package br.com.space.spacewebII.modelo.excecao;

public class ErroGenericoExcecao extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public ErroGenericoExcecao(String msg) {
		super(msg);
	}
}
