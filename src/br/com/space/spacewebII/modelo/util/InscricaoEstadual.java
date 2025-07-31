package br.com.space.spacewebII.modelo.util;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import br.com.space.spacewebII.modelo.propriedade.Propriedade;

public class InscricaoEstadual {

	@Inject
	Propriedade propriedade;

	private String ufSigla;
	private String ufNome;
	private String mascara;

	public InscricaoEstadual(String ufSigla, String ufNome, String mascara) {
		super();
		this.ufSigla = ufSigla;
		this.ufNome = ufNome;
		this.mascara = mascara;
	}

	public static final List<InscricaoEstadual> getListaInscricaoEstadual() {

		List<InscricaoEstadual> inscricoesEstaduais = new ArrayList<InscricaoEstadual>();

		for (int i = 0; i < Fabrica.ufs.length; i++) {
			inscricoesEstaduais.add(new InscricaoEstadual(Fabrica.ufs[i],
					Fabrica.estados[i], Fabrica.mascarasUF[i]));
		}

		return inscricoesEstaduais;
	}

	public String obterMascara(String ufSigla) {
		return propriedade.getMensagem("mascara.inscricaoEstadual."
				+ ufSigla.toLowerCase());
	}

	public String getUfSigla() {
		return ufSigla;
	}

	public void setUfSigla(String ufSigla) {
		this.ufSigla = ufSigla;
	}

	public String getUfNome() {
		return ufNome;
	}

	public void setUfNome(String ufNome) {
		this.ufNome = ufNome;
	}

	public String getMascara() {
		return mascara;
	}

	public void setMascara(String mascara) {
		this.mascara = mascara;
	}

	@Override
	public String toString() {
		return ufSigla;
	}

}
