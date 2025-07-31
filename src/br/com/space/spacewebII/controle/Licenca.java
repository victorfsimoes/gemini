package br.com.space.spacewebII.controle;

import br.com.space.api.core.licenca.LeitorLicenca;
import br.com.space.api.core.sistema.CodigoSistema;
import br.com.space.spacewebII.modelo.aplicacao.AtributoConfiguracao;
import br.com.space.spacewebII.modelo.aplicacao.Configuracao;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Filial;
import br.com.space.spacewebII.modelo.dominio.sistema.Grds;

public class Licenca {

	private static Filial filial;

	private static LeitorLicenca leitorLicenca;

	/**
	 * 
	 * @return
	 */
	private static LeitorLicenca getLeitorLicenca() {
		if (leitorLicenca == null) {
			leitorLicenca = new LeitorLicenca();
		}

		return leitorLicenca;
	}

	/**
	 * 
	 * @return
	 */
	private static Filial getFilial() {
		if (filial == null) {
			AtributoConfiguracao atributos = Configuracao
					.getAtributosConfiguracao();

			filial = Filial.recuperarUnico(GenericoDAO.getDaoLeitura(),
					atributos.getFilialPadraoCodigo());
		}

		if (filial == null) {
			filial = new Filial();
		}

		return filial;
	}

	/**
	 * 
	 */
	public static void carregarLicenca() {
		try {
			Grds grds = Grds.recuperarUnico(GenericoDAO.getDaoLeitura(),
					getFilial().getCodigo());

			getLeitorLicenca().setParamCodigoSistema(
					Integer.toString(CodigoSistema.VENDA_WEB));
			getLeitorLicenca().setParamCnpjCpf(getFilial().getCnpj());
			getLeitorLicenca().carregarLicencaString(grds.getLicenca());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 */
	public static boolean isLicencaValida() {
		return getLeitorLicenca() != null
				&& getLeitorLicenca().isLicencaValida();
	}

	/**
	 * 
	 * @param modulo
	 * @return
	 */
	public static boolean isModuloAtivo(String modulo) {
		return getLeitorLicenca() != null
				&& getLeitorLicenca().isModuloAtivo(modulo);
	}

	/**
	 * 
	 * 
	 * @return
	 * 
	 *         public static boolean isModuloMiniBannerAtivo() { return
	 *         isModuloAtivo("0000"); }
	 */

	/**
	 * Verificar se cliente possui módulo cliente
	 * 
	 * @return
	 */
	public static boolean isModuloClienteAtivo() {
		return isModuloAtivo("2");
	}

	/**
	 * Verificar se cliente possui módulo colaborador
	 * 
	 * @return
	 */
	public static boolean isModuloColaboradorAtivo() {
		return isModuloAtivo("3");
	}

	/**
	 * Verifica se o cliente possui o módulo Banner Ativo
	 * 
	 * @return
	 */
	public static boolean isModuloBannerAtivo() {
		return isModuloAtivo("4");
	}

	/**
	 * Verifica se o cliente possui o módulo de compra rapida ativa
	 * 
	 * @return
	 */
	public static boolean isModuloCompraRapidaAtiva() {
		return isModuloAtivo("5");
	}
}
