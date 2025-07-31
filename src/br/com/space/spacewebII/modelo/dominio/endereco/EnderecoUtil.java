package br.com.space.spacewebII.modelo.dominio.endereco;

import br.com.space.api.core.sistema.Conversao;
import br.com.space.api.core.util.StringUtil;

public class EnderecoUtil {

	public static String toString(IEndereco end) {
		StringBuilder endereco = new StringBuilder();

		if (StringUtil.isValida(end.getLogradouro()))
			endereco.append(end.getLogradouro().trim());

		if (StringUtil.isValida(end.getNumero()))
			endereco.append(",").append(end.getNumero().trim());

		if (StringUtil.isValida(end.getComplemento()))
			endereco.append(",").append(end.getComplemento().trim());
		// partesEndereco.add(complemento);

		if (end.getBairro() != null && StringUtil.isValida(end.getBairro().getDescricaoBairro()))
			endereco.append(",").append(end.getBairro().getDescricaoBairro().trim());
		// partesEndereco.add(bairro.getDescricaoBairro());

		if (end.getCidade() != null && StringUtil.isValida(end.getCidade().getDescricaoCidade()))
			endereco.append(",").append(end.getCidade().getDescricaoCidade().trim());
		// partesEndereco.add(cidade.getDescricaoCidade());

		if (StringUtil.isValida(end.getUfsigla()))
			endereco.append(",").append(end.getUfsigla().trim());
		// partesEndereco.add(ufsigla);

		if (StringUtil.isValida(end.getCep()))
			endereco.append(",").append(" (").append(Conversao.formatarCep(end.getCep())).append(") ");
		// partesEndereco.add(" (" + Conversao.formatarCep(cep) + ") ");

		/*
		 * String endereco = ""; for (int i = 0; i < partesEndereco.size(); i++) { if (i
		 * > 0 && i < partesEndereco.size() - 1) endereco += ", "; endereco +=
		 * partesEndereco.get(i); }
		 */
		return endereco.toString();
	}

}
