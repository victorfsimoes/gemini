package br.com.space.spacewebII.modelo.aplicacao;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import com.itextpdf.text.pdf.PdfStructTreeController.returnType;

import br.com.space.api.core.sistema.ManipulaXMLJAXB;

@XmlRootElement()
@XmlAccessorType(XmlAccessType.FIELD)
public class AtributoConfiguracao implements Serializable {

	private static final long serialVersionUID = 1L;

	@XmlElement(name = "filialPadraoCodigo")
	private int filialPadraoCodigo = 0;

	@XmlElement(name = "intervaloVerificacaoTransacaoCartaoMinutos")
	private int intervaloVerificacaoTransacaoCartaoMinutos = 30;

	@XmlElement(name = "intervaloVerificacaoAvisoEstoqueMinutos")
	private int intervaloVerificacaoAvisoEstoqueMinutos = 60;

	@XmlElement
	private int realizaAvisoEstoque = 1;

	@XmlElement
	private int realizaVerificacaoPagamentoCartao = 1;

	@XmlElement
	private boolean demostracao = false;

	/**
	 * 
	 */
	public AtributoConfiguracao() {
	}

	/**
	 * 
	 * @return
	 */
	public int getFilialPadraoCodigo() {
		return filialPadraoCodigo;
	}

	/**
	 * 
	 * @param filialPadraoCodigo
	 */
	public void setFilialPadraoCodigo(int filialPadraoCodigo) {
		this.filialPadraoCodigo = filialPadraoCodigo;
	}

	public int getIntervaloVerificacaoTransacaoCartaoMinutos() {
		return intervaloVerificacaoTransacaoCartaoMinutos;
	}

	public void setIntervaloVerificacaoTransacaoCartaoMinutos(int intervaloVerificacaoTransacaoCartaoMinutos) {
		this.intervaloVerificacaoTransacaoCartaoMinutos = intervaloVerificacaoTransacaoCartaoMinutos;
	}

	public int getIntervaloVerificacaoAvisoEstoqueMinutos() {
		return intervaloVerificacaoAvisoEstoqueMinutos;
	}

	public void setIntervaloVerificacaoAvisoEstoqueMinutos(int intervaloVerificacaoAvisoEstoqueMinutos) {
		this.intervaloVerificacaoAvisoEstoqueMinutos = intervaloVerificacaoAvisoEstoqueMinutos;
	}

	public boolean isRealizaAvisoEstoque() {
		return getRealizaAvisoEstoque() == 1;
	}

	public int getRealizaAvisoEstoque() {
		return realizaAvisoEstoque;
	}

	public void setRealizaAvisoEstoque(int realizaAvisoEstoque) {
		this.realizaAvisoEstoque = realizaAvisoEstoque;
	}

	public boolean isRealizaVerificacaoPagamentoCartao() {
		return getRealizaVerificacaoPagamentoCartao() == 1;
	}

	public int getRealizaVerificacaoPagamentoCartao() {
		return realizaVerificacaoPagamentoCartao;
	}

	public void setRealizaVerificacaoPagamentoCartao(int realizaVerificacaoPagamentoCartao) {
		this.realizaVerificacaoPagamentoCartao = realizaVerificacaoPagamentoCartao;
	}
	
	public boolean isDemostracao() {
		return demostracao;
	}

	public void setDemostracao(boolean demostracao) {
		this.demostracao = demostracao;
	}

	/**
	 * 
	 * @param file
	 */
	public void gravarEmXml(File file) {
		ManipulaXMLJAXB as = ManipulaXMLJAXB.getInstance(AtributoConfiguracao.class);

		try {
			as.gravarXml(new AtributoConfiguracao(), file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param file
	 * @return
	 */
	public static AtributoConfiguracao carregarXml(File file) {

		AtributoConfiguracao configuracao = null;
		try {
			ManipulaXMLJAXB man = ManipulaXMLJAXB.getInstance(AtributoConfiguracao.class);
			Object obj = man.carregarXml(file);
			if (obj != null && obj instanceof AtributoConfiguracao)
				configuracao = (AtributoConfiguracao) obj;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return configuracao;
	}
}
