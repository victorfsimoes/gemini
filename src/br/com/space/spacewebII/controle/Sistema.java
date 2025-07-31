package br.com.space.spacewebII.controle;

import java.util.Date;

import br.com.space.api.core.sistema.Conversao;

public class Sistema {

	/**
	 * 
	 * @return
	 */
	public static Date obterData() {
		return new Date();

	}

	/**
	 * 
	 * @return
	 */
	public static String obterHora() {
		return Conversao.formatarDataHHMMSS(obterData());
	}

	/**
	 * 
	 * @return
	 */
	public static String getJavaVersion() {
		return System.getProperties().getProperty("java.version");
	}

	/**
	 * 
	 * @return
	 */
	public static String getJavaSpecificationVersion() {
		return System.getProperties().getProperty(
				"java.vm.specification.version");
	}

	/**
	 * 
	 * @return
	 */
	public static String getJavaVMName() {
		return System.getProperties().getProperty("java.vm.name");
	}

	/**
	 * 
	 * @return
	 */
	public static String getOsName() {
		return System.getProperties().getProperty("os.name");
	}

	/**
	 * 
	 * @return
	 */
	public static String getOsArchitecture() {
		return System.getProperties().getProperty("os.arch");
	}

	/**
	 * 
	 * @return
	 */
	public static String getOsVersion() {
		return System.getProperties().getProperty("os.version");
	}

}
