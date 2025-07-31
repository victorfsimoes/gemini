package br.com.space.spacewebII.controle.aplicacao;

import java.io.Serializable;

import br.com.space.spacewebII.controle.Sistema;
import br.com.space.spacewebII.controle.padrao.mbean.GerenteMB;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.util.HttpUtil;

import com.ocpsoft.pretty.faces.annotation.URLBeanName;
import com.ocpsoft.pretty.faces.annotation.URLMapping;

@ManagedBeanSessionScoped
@URLMapping(id = "versao", pattern = "/versao", viewId = "/pages/versao.xhtml", onPostback = false)
@URLBeanName("versaoMB")
public class VersaoMB extends GerenteMB implements Serializable {

	private static final long serialVersionUID = 1L;

	/*
	 * Versão do Sistema.
	 */
	public static final String versaoSistema = "1.0.1.27";

	private String informacoes = null;

	public String montarInformacoes() {
		if (informacoes == null) {

			Sistema.getJavaVMName();
			Sistema.getJavaSpecificationVersion();

			informacoes = "Sistema<br/>" + versaoSistema + "<br/><br/>" + "Java<br/>" + "Versão:"
					+ Sistema.getJavaVersion() + "<br/>" + "Nome Máquina Virtual:" + Sistema.getJavaVMName()
					+ "<br/><br/>" + "Sistema Operacional" + "<br/>" + "Nome S.O.:" + Sistema.getOsName() + "("
					+ Sistema.getOsVersion() + ")" + "<br/>" + "Arquitetura S.O.:" + Sistema.getOsArchitecture()
					+ "<br/><br/>" + "Servidor:" + "<br/>" + HttpUtil.getServerInfo();
		}

		return informacoes;
	}

	@Override
	public boolean temPermissao() {
		return !gerenteLogin.isPerfilCliente();
	}

	@Override
	public String getNomePrograma() {
		return "";
	}

	@Override
	public String getUrlView() {
		return getUrlViewPattern();
	}

	@Override
	public boolean necessarioLogin() {
		return true;
	}

	@Override
	public boolean permiteAcessoDeCliente() {
		return false;
	}

	@Override
	protected String getUrlViewPage() {
		return "/pages/versao.xhtml";
	}

	@Override
	protected String getUrlViewPattern() {
		return "/pedido";
	}

	public static void main(String[] args) {
		// java.version Java Runtime Environment version
		// java.vendor Java Runtime Environment vendor
		// java.vendor.url Java vendor URL
		// java.home Java installation directory
		// java.vm.specification.version Java Virtual Machine specification
		// version
		// java.vm.specification.vendor Java Virtual Machine specification
		// vendor
		// java.vm.specification.name Java Virtual Machine specification name
		// java.vm.version Java Virtual Machine implementation version
		// java.vm.vendor Java Virtual Machine implementation vendor
		// java.vm.name Java Virtual Machine implementation name
		// java.specification.version Java Runtime Environment specification
		// version
		// java.specification.vendor Java Runtime Environment specification
		// vendor
		// java.specification.name Java Runtime Environment specification name
		// java.class.version Java class format version number
		// java.class.path Java class path
		// java.library.path List of paths to search when loading libraries
		// java.io.tmpdir Default temp file path
		// java.compiler Name of JIT compiler to use
		// java.ext.dirs Path of extension directory or directories
		// os.name Operating system name
		// os.arch Operating system architecture
		// os.version Operating system version
		// file.separator File separator ("/" on UNIX)
		// path.separator Path separator (":" on UNIX)
		// line.separator Line separator ("\n" on UNIX)
		// user.name User's account name
		// user.home User's home directory
		// user.dir User's current working directory

		System.out.println(System.getProperties().getProperty("java.version"));
		System.out.println(System.getProperties().getProperty("java.vm.specification.version"));
		System.out.println(System.getProperties().getProperty("java.vm.version"));
		System.out.println(System.getProperties().getProperty("java.vm.name"));
		System.out.println(System.getProperties().getProperty("java.class.version"));

		System.out.println("Sistema Operacional");
		System.out.println(
				System.getProperties().getProperty("os.name") + "/" + System.getProperties().getProperty("os.arch")
						+ "/" + System.getProperties().getProperty("os.version"));

	}
}
