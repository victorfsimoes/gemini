package br.com.space.spacewebII.modelo.dominio.endereco;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.space.spacewebII.modelo.dao.GenericoDAOCep;
import br.com.space.spacewebII.modelo.util.Fabrica;
import br.com.space.spacewebII.modelo.util.InscricaoEstadual;


@Named
@SessionScoped
public class EnderecoMB implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private GenericoDAOCep dao;

	private List<LogBairro> listaBairro = null;
	private List<LogLocalidade> listaCidade = null;

	public List<String> pesquisarBairro(Object sugest) {
		List<String> nomesBairros = new ArrayList<String>();

		String pref = (String) sugest;

		LogBairro bairro;
		for (int i = 0; i < getListaBairro().size(); i++) {

			bairro = getListaBairro().get(i);

			if ((bairro.getBaiNo() != null && bairro.getBaiNo().toLowerCase()
					.indexOf(pref.toLowerCase()) == 0)
					|| "".equalsIgnoreCase(pref))

				if (i > 0
						&& bairro.getBaiNo().equals(
								getListaBairro().get(i - 1).getBaiNo())) {
					continue;
				} else {
					nomesBairros.add(bairro.getBaiNo());
				}
		}

		for (int i = 0; i < nomesBairros.size(); i++) {
			for (int j = i + 1; j < nomesBairros.size(); j++) {
				if (nomesBairros.get(i).equals(nomesBairros.get(j))) {
					nomesBairros.remove(j);
				}
			}
		}
		return nomesBairros;
	}

	public List<LogBairro> getListaBairro() {
		if (listaBairro == null) {
			listaBairro = LogBairro.recuperaTodosBairros(dao, "baiNo");
		}
		return listaBairro;
	}

	public List<LogLocalidade> getListaCidade() {
		if (listaCidade == null) {
			listaCidade = LogLocalidade.recuperaTodasCidades(dao, "locNo");
		}
		return listaCidade;
	}

	public List<String> pesquisarCidade(Object sugest) {
		List<String> nomesCidades = new ArrayList<String>();

		String pref = (String) sugest;

		LogLocalidade cidade;
		for (int i = 0; i < getListaCidade().size(); i++) {

			cidade = getListaCidade().get(i);

			if ((cidade.getLocNo() != null && cidade.getLocNo().toLowerCase()
					.indexOf(pref.toLowerCase()) == 0)
					|| "".equalsIgnoreCase(pref))

				if (i > 0
						&& cidade.getLocNo().equals(
								getListaBairro().get(i - 1).getBaiNo())) {
					continue;
				} else {
					nomesCidades.add(cidade.getLocNo());
				}
		}

		for (int i = 0; i < nomesCidades.size(); i++) {
			for (int j = i + 1; j < nomesCidades.size(); j++) {
				if (nomesCidades.get(i).equals(nomesCidades.get(j))) {
					nomesCidades.remove(j);
				}
			}
		}
		return nomesCidades;
	}

	public List<InscricaoEstadual> pesquisarUF(Object sugest) {
		String[] ufs = Fabrica.ufs;
		List<InscricaoEstadual> ufsSelecionadas = new ArrayList<InscricaoEstadual>();

		String pref = (String) sugest;

		for (int i = 0; i < ufs.length; i++) {

			if ((ufs[i].indexOf(pref.toUpperCase()) == 0)
					|| "".equalsIgnoreCase(pref)) {
				for (InscricaoEstadual inscricaoEstadual : InscricaoEstadual
						.getListaInscricaoEstadual())
					if (ufs[i].equals(inscricaoEstadual.getUfSigla()))
						ufsSelecionadas.add(inscricaoEstadual);
			}
		}

		return ufsSelecionadas;
	}
}
