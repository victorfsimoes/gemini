package br.com.space.spacewebII.controle.endereco;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.endereco.LogBairro;
import br.com.space.spacewebII.modelo.dominio.endereco.LogCep;
import br.com.space.spacewebII.modelo.dominio.endereco.LogCpc;
import br.com.space.spacewebII.modelo.dominio.endereco.LogGrandeUsuario;
import br.com.space.spacewebII.modelo.dominio.endereco.LogLocalidade;
import br.com.space.spacewebII.modelo.dominio.endereco.LogLogradouro;
import br.com.space.spacewebII.modelo.dominio.endereco.LogUnidOper;

public class PesquisaCep {

	protected boolean achouLogradouro = false;

	private GenericoDAO<IPersistent> daoCep;

	private String cepCidade = null;
	private String cepLogradouro = null;
	private String cepBairro = null;
	private String cepUF = null;
	private String cepReferencia = null;
	private String cepNumero = null;

	public PesquisaCep() {
	}

	/**
	 * 
	 * @param daoCep
	 * @param cep
	 */
	public void pesquisarCEPII(GenericoDAO<IPersistent> daoCep, String cep) {
		this.daoCep = daoCep;

		LogCep logCep = LogCep.recuperarUnico(daoCep, cep);

		if (logCep != null) {
			int tabela = logCep.getLogCepPK().getLogInTabela();

			String filtroIdCep = Integer.toString(logCep.getLogCepPK()
					.getLogNuCep());

			if (tabela == 1) {
				pesquisarLocalidade(null, filtroIdCep);
			} else if (tabela == 2) {
				pesquisarLogradouro(null, filtroIdCep);
			} else if (tabela == 3) {
				pesquisarGrandeUsuario(null, filtroIdCep);
			} else if (tabela == 4) {
				pesquisarUnidadeOperacional(null, filtroIdCep);
			} else if (tabela == 5) {
				pesquisarCaixaPostalComunitaria(null, filtroIdCep);
			}

		}

	}

	/**
	 * 
	 * @param daoCep
	 * @param cep
	 */
	public void pesquisarCEP(GenericoDAO<IPersistent> daoCep, String cep) {
		this.daoCep = daoCep;
		achouLogradouro = false;

		int sufixo = 0;

		String cepDivido[] = cep.split("\\-");
		if (cepDivido.length > 1)
			sufixo = Integer.parseInt(cepDivido[1]);

		String cepAux = cep.trim();
		cepAux = cepAux.replace("-", "");
		cepAux = cepAux.replace(".", "");

		if (cepAux.length() == 8) {
			if (sufixo >= 900 && sufixo <= 959) {
				pesquisarGrandeUsuario(cepAux, null);
			} else if ((sufixo >= 970 && sufixo <= 989) || sufixo == 999) {
				pesquisarUnidadeOperacional(cepAux, null);
			} else if (sufixo >= 990 && sufixo <= 998) {
				pesquisarCaixaPostalComunitaria(cepAux, null);
			} else {
				pesquisarLogradouro(cepAux, null);

				if (achouLogradouro == false)
					pesquisarLocalidade(cepAux, null);
			}
		}
	}

	/**
	 * 
	 * @param cep
	 * @param numCaixaPostal
	 */
	public void pesquisarCaixaPostalComunitaria(String cep,
			String numCaixaPostal) {
		LogCpc logCaixaPostal = LogCpc.recuperaUnico(daoCep, null,
				numCaixaPostal);

		if (logCaixaPostal != null) {
			pesquisarLocalidade(null,
					Integer.toString(logCaixaPostal.getLocNuSequencial()));

			cepReferencia = logCaixaPostal.getCpcNo();
			cepUF = logCaixaPostal.getUfeSg();
			cepLogradouro = logCaixaPostal.getCpcEndereco();
		}
	}

	/**
	 * 
	 * @param cep
	 * @param numUniOperacional
	 */
	public void pesquisarUnidadeOperacional(String cep, String numUniOperacional) {
		LogUnidOper logUniOpe = LogUnidOper.recuperaUnico(daoCep, null,
				numUniOperacional);

		if (logUniOpe != null) {
			pesquisarLocalidade(null,
					Integer.toString(logUniOpe.getLocNuSequencial()));
			pesquisarBairro(Integer.toString(logUniOpe.getBaiNuSequencial()),
					null);

			cepReferencia = logUniOpe.getUopNo();
			cepUF = logUniOpe.getUfeSg();
			cepLogradouro = logUniOpe.getUopEndereco();
		}
	}

	/**
	 * 
	 * @param cep
	 * @param numGrandeUsuario
	 */
	public void pesquisarGrandeUsuario(String cep, String numGrandeUsuario) {
		LogGrandeUsuario logGrandeUsuario = LogGrandeUsuario.recuperaUnico(
				daoCep, cep, numGrandeUsuario);

		if (logGrandeUsuario != null) {
			pesquisarLocalidade(null,
					Integer.toString(logGrandeUsuario.getLocNuSequencial()));
			pesquisarBairro(
					Integer.toString(logGrandeUsuario.getBaiNuSequencial()),
					null);

			cepReferencia = logGrandeUsuario.getGruNo();
			cepUF = logGrandeUsuario.getUfeSg();
			cepLogradouro = logGrandeUsuario.getGruEndereco();
		}
	}

	/**
	 * 
	 * @param cep
	 * @param numLogradouro
	 */
	public void pesquisarLogradouro(String cep, String numLogradouro) {
		LogLogradouro logCepLogradouro = LogLogradouro.recuperaUnico(daoCep,
				cep, numLogradouro);

		if (logCepLogradouro != null) {
			pesquisarLocalidade(null,
					Integer.toString(logCepLogradouro.getLocNuSequencial()));
			pesquisarBairro(
					Integer.toString(logCepLogradouro.getBaiNuSequencialIni()),
					null);

			cepLogradouro = logCepLogradouro.getLogNome();
			cepUF = logCepLogradouro.getUfeSg();
			cepReferencia = logCepLogradouro.getLogComplemento();

			achouLogradouro = true;
		}
	}

	/**
	 * 
	 * @param filtroNumBairroIni
	 * @param filtroNumBairroFim
	 */
	public void pesquisarBairro(String filtroNumBairroIni,
			String filtroNumBairroFim) {
		LogBairro logCepBairro = null;

		if (filtroNumBairroIni != null && !filtroNumBairroIni.isEmpty()) {
			logCepBairro = LogBairro.recuperaUnicoBairroInicial(daoCep,
					filtroNumBairroIni);
		}
		if (filtroNumBairroFim != null && !filtroNumBairroFim.isEmpty()) {
			logCepBairro = LogBairro.recuperaUnicoBairroInicial(daoCep,
					filtroNumBairroFim);
		}

		if (logCepBairro != null) {
			cepUF = logCepBairro.getUfeSg();
			cepBairro = logCepBairro.getBaiNo();
		}
	}

	/**
	 * 
	 * @param cep
	 * @param numLocalidade
	 */
	public void pesquisarLocalidade(String cep, String numLocalidade) {
		LogLocalidade logCepLocalidade = LogLocalidade.recuperaUnico(daoCep,
				cep, numLocalidade);

		if (logCepLocalidade != null) {
			cepCidade = logCepLocalidade.getLocNo();
			cepUF = logCepLocalidade.getUfeSg();
		}
	}

	public String getCepCidade() {
		return cepCidade;
	}

	public void setCepCidade(String cepCidade) {
		this.cepCidade = cepCidade;
	}

	public String getCepLogradouro() {
		return cepLogradouro;
	}

	public void setCepLogradouro(String cepLogradouro) {
		this.cepLogradouro = cepLogradouro;
	}

	public String getCepBairro() {
		return cepBairro;
	}

	public void setCepBairro(String cepBairro) {
		this.cepBairro = cepBairro;
	}

	public String getCepUF() {
		return cepUF;
	}

	public void setCepUF(String cepUF) {
		this.cepUF = cepUF;
	}

	public String getCepReferencia() {
		return cepReferencia;
	}

	public void setCepReferencia(String cepReferencia) {
		this.cepReferencia = cepReferencia;
	}

	public String getCepNumero() {
		return cepNumero;
	}

	public void setCepNumero(String cepNumero) {
		this.cepNumero = cepNumero;
	}

}
