package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import br.com.space.api.negocio.modelo.dominio.parametro.IParametroCusto;
import br.com.space.api.negocio.modelo.dominio.parametro.IParametroVenda;
import br.com.space.api.negocio.modelo.dominio.parametro.IParametroVenda2;
import br.com.space.api.negocio.modelo.dominio.parametro.IParametroVenda3;
import br.com.space.api.negocio.modelo.dominio.parametro.IParametroVenda4;
import br.com.space.api.negocio.modelo.dominio.parametro.IParametros;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@ManagedBeanSessionScoped
public class Parametros implements Serializable, IParametros {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static Parametros instancia = null;

	Parametro parametro1;
	ParametroVenda parametroVenda1;

	Parametro2 parametro2;
	ParametroVenda2 parametroVenda2;

	Parametro3 parametro3;
	ParametroVenda3 parametroVenda3;

	Parametro4 parametro4;
	ParametroVenda4 parametroVenda4;

	ParametroFinanceiro parametroFinanceiro;

	ParametroCusto parametroCusto;

	ParametroWeb parametroWeb;

	private int filialCodigo = 0;

	/**
	 * 
	 * @param dao
	 * @param filial
	 * @return
	 */
	public static Parametros getInstancia(GenericoDAO dao, Filial filial) {
		if (instancia == null)
			instancia = new Parametros(dao, filial);
		else {
			if (dao != null && filial != null) {
				if (filial.getCodigo() != instancia.filialCodigo) {
					instancia.recuperarUnico(dao, filial.getCodigo());
				}
			}
		}
		return instancia;
	}

	/**
	 * 
	 */
	public Parametros() {
	}

	/**
	 * 
	 */
	public Parametros(GenericoDAO dao, Filial filial) {
		if (dao != null && filial != null) {
			this.recuperarUnico(dao, filial.getCodigo());
		}
	}

	/**
	 * 
	 */
	public Parametros(GenericoDAO dao, int filialCodigo) {
		if (dao != null && filialCodigo > 0) {
			this.recuperarUnico(dao, filialCodigo);
		}
	}

	/**
	 * 
	 * @param filial
	 */
	@Deprecated
	public Parametros(Filial filial) {
		this(null, filial);
	}

	/**
	 * 
	 */
	@Override
	public IParametroCusto getParametroCusto() {
		return this.parametroCusto;
	}

	@Override
	public IParametroVenda getParametroVenda() {
		return parametroVenda1;
	}

	@Override
	public IParametroVenda2 getParametroVenda2() {
		return parametroVenda2;
	}

	@Override
	public IParametroVenda3 getParametroVenda3() {
		return parametroVenda3;
	}

	public Parametro getParametro1() {
		return parametro1;
	}

	public Parametro2 getParametro2() {
		return parametro2;
	}

	public void setParametro2(Parametro2 parametro2) {
		this.parametro2 = parametro2;
	}

	public Parametro3 getParametro3() {
		return parametro3;
	}

	public void setParametro3(Parametro3 parametro3) {
		this.parametro3 = parametro3;
	}

	public void setParametro1(Parametro parametro1) {
		this.parametro1 = parametro1;
	}

	public ParametroVenda getParametroVenda1() {
		return parametroVenda1;
	}

	public void setParametroVenda1(ParametroVenda parametroVenda1) {
		this.parametroVenda1 = parametroVenda1;
	}

	public void setParametroVenda2(ParametroVenda2 parametroVenda2) {
		this.parametroVenda2 = parametroVenda2;
	}

	public void setParametroVenda3(ParametroVenda3 parametroVenda3) {
		this.parametroVenda3 = parametroVenda3;
	}

	@Override
	public IParametroVenda4 getParametroVenda4() {
		return parametroVenda4;
	}

	public ParametroWeb getParametroWeb() {
		return parametroWeb;
	}

	public void setParametroWeb(ParametroWeb parametroWeb) {
		this.parametroWeb = parametroWeb;
	}

	public ParametroFinanceiro getParametroFinanceiro() {
		return parametroFinanceiro;
	}

	public void setParametroFinanceiro(ParametroFinanceiro parametroFinanceiro) {
		this.parametroFinanceiro = parametroFinanceiro;
	}

	public void setParametroCusto(ParametroCusto parametroCusto) {
		this.parametroCusto = parametroCusto;
	}
	
	public Parametro4 getParametro4() {
		return parametro4;
	}

	public void setParametro4(Parametro4 parametro4) {
		this.parametro4 = parametro4;
	}

	public void setParametroVenda4(ParametroVenda4 parametroVenda4) {
		this.parametroVenda4 = parametroVenda4;
	}

	/**
	 * 
	 * @param dao
	 * @param filialCodigo
	 */
	public void recuperarUnico(GenericoDAO dao, int filialCodigo) {

		parametro1 = Parametro.recuperarUnico(dao, filialCodigo);
		if (parametro1 == null)
			parametro1 = new Parametro();

		parametro2 = Parametro2.recuperarUnico(dao, filialCodigo);
		if (parametro2 == null)
			parametro2 = new Parametro2();

		parametro3 = Parametro3.recuperarUnico(dao, filialCodigo);
		if (parametro3 == null)
			parametro3 = new Parametro3();

		parametro4 = Parametro4.recuperarUnico(dao, filialCodigo);
		if (parametro4 == null)
			parametro4 = new Parametro4();

		parametroVenda1 = ParametroVenda.recuperarUnico(dao, filialCodigo);
		if (parametroVenda1 == null)
			parametroVenda1 = new ParametroVenda();

		parametroVenda2 = ParametroVenda2.recuperarUnico(dao, filialCodigo);
		if (parametroVenda2 == null)
			parametroVenda2 = new ParametroVenda2();

		parametroVenda3 = ParametroVenda3.recuperarUnico(dao, filialCodigo);
		if (parametroVenda3 == null)
			parametroVenda3 = new ParametroVenda3();

		parametroVenda4 = ParametroVenda4.recuperarUnico(dao, filialCodigo);
		if (parametroVenda4 == null)
			parametroVenda4 = new ParametroVenda4();

		parametroWeb = ParametroWeb.recuperarUnico(dao, filialCodigo);
		if (parametroWeb == null)
			parametroWeb = new ParametroWeb();

		parametroCusto = ParametroCusto.recuperarUnico(dao, filialCodigo);
		if (parametroCusto == null)
			parametroCusto = new ParametroCusto();

		this.filialCodigo = filialCodigo;
	}
}
