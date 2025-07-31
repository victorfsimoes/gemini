package br.com.space.spacewebII.controle.aplicacao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.inject.Inject;

import br.com.space.api.core.util.ListUtil;
import br.com.space.api.core.util.StringUtil;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.financeiro.FormaPagamento;
import br.com.space.spacewebII.modelo.dominio.sistema.Link;
import br.com.space.spacewebII.modelo.dominio.sistema.ParametroWeb;
import br.com.space.spacewebII.modelo.propriedade.Propriedade;
import br.com.space.spacewebII.modelo.util.Arquivo;
import br.com.space.spacewebII.modelo.util.FormatacaoMB;
import br.com.space.spacewebII.modelo.widget.GrupoLink;

@ManagedBeanSessionScoped
public class RodapeMB implements Serializable {
	private static final long serialVersionUID = 1L;

	@Inject
	private GenericoDAO dao;

	@Inject
	private ParametroWebMB parametroWebMB;

	@Inject
	private FormatacaoMB formatacao;

	@Inject
	GerenteLogin gerenteLogin;

	@Inject
	private Arquivo arquivo;

	private List<GrupoLink> grupoLinks = null;

	private List<FormaPagamento> formasPagamento = null;

	private ParametroWeb parametroWeb = null;

	public RodapeMB() {

	}

	@PostConstruct
	public void postConstruct() {
		try {

			if (grupoLinks == null)
				montarRodapeLinks();

			if (formasPagamento == null)
				montarRodapeFormasPagamento();

			parametroWeb = parametroWebMB.getParametroWeb();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Monta o rodapé de links do site quando a sessão é criada.
	 * 
	 * Possui uma lista com titulos dos grupos e listas com os links do grupos.
	 * 
	 */
	private void montarRodapeLinks() {

		grupoLinks = new ArrayList<GrupoLink>();

		try {

			List<Link> gruposLink = Link.recuperarLinksAtivos(dao);

			for (Link link : gruposLink) {
				if (link.getCodigoPagina() > 0)
					link.setLink(arquivo.getRootPath()
							+ "/paginas/"
							+ link.getCodigoPagina()
							+ "/"
							+ formatacao.formatarStringURL(link.getTitulo(),
									true, false, true));

				String grupo = link.getGrupo();

				GrupoLink grupoLink = GrupoLink.pesquisarGrupoLinkNaLista(
						grupoLinks, grupo);

				if (grupoLink == null) {
					grupoLink = new GrupoLink(grupo);
					grupoLinks.add(grupoLink);
				}

				grupoLink.addLink(link);
			}
		} catch (Exception ex) {

		}
	}

	/**
	 * 
	 */
	private void montarRodapeFormasPagamento() {
		formasPagamento = new ArrayList<FormaPagamento>();
		try {
			formasPagamento = FormaPagamento.recuperarVisiveisRodape(dao);
		} catch (Exception ex) {
		}
	}

	/**
	 * 
	 * @return
	 */
	public List<GrupoLink> getGrupoLinks() {
		return grupoLinks;
	}

	/**
	 * 
	 * @return
	 */
	public List<FormaPagamento> getFormasPagamento() {
		return formasPagamento;
	}

	/**
	 * 
	 * @return
	 */
	public boolean existeGrupoLinks() {
		return ListUtil.isValida(grupoLinks);
	}

	/**
	 * 
	 * @return
	 */
	public String getSiteInstitucionalTitulo() {

		if (parametroWeb != null
				&& StringUtil.isValida(parametroWeb
						.getSiteInstitucionalTitulo())) {

			return parametroWeb.getSiteInstitucionalTitulo();
		}

		return "Space Informatica";
	}

	/**
	 * 
	 * @return
	 */
	public String getSiteInstitucional() {

		if (parametroWeb != null
				&& StringUtil.isValida(parametroWeb.getSiteInstitucional())) {
			return parametroWeb.getSiteInstitucional();
		}

		return "http://www.spaceinformatica.com.br";
	}

	/**
	 * 
	 * @return
	 */
	public ParametroWeb getParametroWeb() {
		return parametroWeb;
	}

	/**
	 * 
	 * @return
	 */
	public String montarEndereco() {
		String endereco = "";

		try {
			endereco += gerenteLogin.getFilialLogada().getRazaoSocial()
					.toLowerCase()
					+ " / "
					+ Propriedade.getMensagemUtilizadoSessao("texto.cnpj")
					+ ": "
					+ gerenteLogin.getFilialLogada().getCnpj()
					+ " / "
					+ Propriedade
							.getMensagemUtilizadoSessao("texto.inscricaoEstadual")
					+ ": "
					+ gerenteLogin.getFilialLogada().getInscricaoEstadual()
					+ " <br/>"
					+ Propriedade.getMensagemUtilizadoSessao("titulo.endereco")
					+ ": "
					+ gerenteLogin.getFilialLogada().getLogradouro()
							.toLowerCase()
					+ ", "
					+ Propriedade
							.getMensagemUtilizadoSessao("texto.abreviacao.numero")
					+ ": "
					+ gerenteLogin.getFilialLogada().getNumeroLogradouro()
					+ " - "
					+ gerenteLogin.getFilialLogada().getBairro()
							.getDescricaoBairro().toLowerCase()
					+ " - "
					+ gerenteLogin.getFilialLogada().getCidade()
							.getDescricaoCidade().toLowerCase()
					+ " - "
					+ gerenteLogin.getFilialLogada().getUf().getDescricao()
							.toLowerCase()
					+ " - "
					+ Propriedade.getMensagemUtilizadoSessao("texto.cep")
					+ ": "
					+ gerenteLogin.getFilialLogada().getCep()
					+ " - "
					+ Propriedade.getMensagemUtilizadoSessao("texto.telefone")
					+ ": " + gerenteLogin.getFilialLogada().getTelefone1();
		} catch (Exception e) {
		}

		return endereco;
	}
}
