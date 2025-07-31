/**
 * 
 */
package br.com.space.spacewebII.controle.padrao.mbean;

import java.io.Serializable;
import java.sql.SQLException;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import org.hibernate.HibernateException;

import br.com.space.api.core.sistema.excecao.SpaceExcecao;
import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.api.web.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.controle.aplicacao.MensagemSistema.TipoMensagem;
import br.com.space.spacewebII.modelo.dominio.sistema.Operacao;
import br.com.space.spacewebII.modelo.padrao.interfaces.CadastroPadrao;

/**
 * Classe padr�o de cadastro
 * 
 * @author Desenvolvimento
 * 
 */
public abstract class GerenteCadastroMB<T extends IPersistent> extends
		GerenteMB implements CadastroPadrao<T> {

	private static final long serialVersionUID = 1L;

	protected int operacao = Operacao.NENHUMA;

	protected T objetoSelecionado;

	private Class<T> classObjeto = null;

	public GerenteCadastroMB() {

	}

	/**
	 * Construtor padr�o
	 * 
	 * @param classObjeto
	 *            Corresponde a classe do objeto principal do cadastro
	 */
	public GerenteCadastroMB(Class<T> classObjeto) {
		this.classObjeto = classObjeto;
	}

	/**
	 * M�todo que ser� subscrito pelas classes espec�ficas a fim de validar o
	 * objeto que ser� inclu�do ou editado
	 * 
	 * @param t
	 *            O objeto que ser� inclu�do ou editado
	 * @throws Exception
	 *             Caso exista alguma inconformidade com o objeto em par�metro.
	 *             (O lan�amento das inconformidades � responsabilidade da
	 *             subclasse)
	 */
	public abstract String validarObjetoIncluirEditar();

	/**
	 * M�todo que ser� subscrito pelas classes espec�ficas a fim de validar o
	 * objeto que ser� exclu�do
	 * 
	 * @param t
	 *            O objeto que ser� exclu�do
	 * @throws Exception
	 *             Caso exista alguma inconformidade com o objeto em par�metro.
	 *             (O lan�amento das inconformidades � responsabilidade da
	 *             subclasse)
	 */
	public abstract void validarObjetoExcluir(T t) throws SpaceExcecao;

	/**
	 * Impede a altera��o de um campo que est� sendo editado no momento por
	 * outra pessoa.
	 */
	public boolean travarRegistro() {
		return false;
	}

	/**
	 * M�todo que destrava o campo quando ele n�o estiver sendo utilizado
	 */
	public void destravarRegistro() {
	}

	/**
	 * M�todo respons�vel pela cria��o de uma nova inst�ncia do Objeto tipado e
	 * de atribuir a opera��o realizada.
	 * 
	 * @return Url da view respons�vel pelo cadastro
	 */
	public String carregarNovo() {
		this.operacao = Operacao.INCLUIR;

		try {
			objetoSelecionado = this.classObjeto.newInstance();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return this.carregarView();
	}

	/**
	 * M�todo respons�vel por popular o objeto a ser alterado e atribuir a nova
	 * opera��o.
	 * 
	 * @param objetoSelecionado
	 *            O objeto tipado que ser� alterado
	 * @return Url da p�gina onde o objeto foi alterado
	 */
	@Override
	public String carregarEdicao(T objetoSelecionado) {
		this.setObjetoSelecionado(objetoSelecionado);
		this.operacao = Operacao.EDITAR;

		return this.carregarView();
	}

	/**
	 * M�todo respons�vel por popular o objeto a ser exclu�do e atribuir a nova
	 * opera��o
	 * 
	 * @param objetoSelecionado
	 *            O objeto tipado que ser� exclu�do
	 * @return Url da view respons�vel pela exclus�o
	 */
	@Override
	public String carregarExclusao(T objetoSelecionado) {
		this.setObjetoSelecionado(objetoSelecionado);
		this.operacao = Operacao.EXCLUIR;

		return this.carregarView();
	}

	/**
	 * M�todo respons�vel por popular o objeto a ser visualizado e atribuir a
	 * nova opera��o
	 * 
	 * @param objetoSelecionado
	 *            O objeto tipado que ser� visualizado
	 * @return Url da view respons�vel pela visualiza��o
	 */
	@Override
	public String carregarVisualizacao(T objetoSelecionado) {
		this.setObjetoSelecionado(objetoSelecionado);
		this.operacao = Operacao.VISUALIZAR;

		return this.carregarView();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.AcaoCadastroPadrao#confirmar
	 * ()
	 */
	@Override
	public String confirmar() {
		if (operacao == Operacao.NENHUMA) {
			return null;
		}

		String mensagemValidacao = validarObjetoIncluirEditar();

		if (mensagemValidacao != "" && mensagemValidacao != null) {
			sessaoMB.mensagem(TipoMensagem.Erro, "Erro!", mensagemValidacao);
			return null;
		}

		GenericoDAO<IPersistent> dao = getDaoObjetoSelecionado();

		try {
			dao.beginTransaction();

			if (operacao == Operacao.EDITAR) {
				if (!travarRegistro()) {
					dao.rollBackTransaction();
					sessaoMB.mensagem(TipoMensagem.Fatal, "Registro travado",
							"");
					return null;
				}
			}

			boolean executarMetodoConfirma = confirmarSubClasse(dao);

			if (!executarMetodoConfirma) {
				confirmar(dao);
			}

			dao.commitTransaction();

			if (operacao == Operacao.EDITAR) {
				try {
					dao.beginTransaction();
					destravarRegistro();
					dao.commitTransaction();
				} catch (Exception e) {
					e.printStackTrace();
					dao.rollBackTransaction();
					sessaoMB.mensagem(TipoMensagem.Fatal,
							propriedade.getMensagem("alerta.ocorreuUmErro"),
							propriedade.getMensagem("alerta.destravarregistro"));
					return null;
				}
			}

			sessaoMB.mensagem(TipoMensagem.Info, "",
					propriedade.getMensagem("mensagem.cadastro.sucesso"));

			operacao = Operacao.NENHUMA;

			return getUrlViewVoltar();

		} catch (Exception ex) {
			ex.printStackTrace();
			dao.rollBackTransaction();

			String msgErro = "";

			if (ex instanceof HibernateException || ex instanceof SQLException) {

				msgErro = "Falha na opera��o " + getMensagemOperacao();

			} else if (ex instanceof SpaceExcecao) {

				msgErro = ex.getMessage();

			} else {
				msgErro = "Falha interna.";
			}

			sessaoMB.mensagem(TipoMensagem.Fatal,
					propriedade.getMensagem("alerta.cadastro.naorealizado"),
					msgErro);

			return null;
		}
		/*
		 * finally{ try { dao.beginTransaction(); destravarRegistro();
		 * dao.commitTransaction(); } catch (ClassNotFoundException e) {
		 * dao.rollBackTransaction(); e.printStackTrace(); } catch (SQLException
		 * e) { dao.rollBackTransaction(); e.printStackTrace(); }
		 * 
		 * }
		 */
	}

	private void confirmar(GenericoDAO<IPersistent> dao) throws Exception {

		switch (operacao) {
		case Operacao.INCLUIR:

			if (!verificarPermissaoIncluir()) {
				throw new SpaceExcecao(
						propriedade.getMensagem("alerta.operacaonaopermitida")
								+ " " + getMensagemOperacao());
			}

			incluir(objetoSelecionado, dao);

			break;

		case Operacao.EDITAR:

			if (!verificarPermissaoAlterar()) {
				throw new SpaceExcecao(
						propriedade.getMensagem("alerta.operacaonaopermitida")
								+ " " + getMensagemOperacao());
			}

			editar(objetoSelecionado, dao);

			break;

		case Operacao.EXCLUIR:

			if (!verificarPermissaoExcluir()) {
				throw new SpaceExcecao(
						propriedade.getMensagem("alerta.operacaonaopermitida")
								+ " " + getMensagemOperacao());
			}

			apagar(objetoSelecionado, dao);

			break;
		}
	}

	/**
	 * Caso na subClasse exista alguma opera��o especial que o m�todo 'confirma'
	 * da super classe n�o � suficiente, este m�todo deve ser implementado. O
	 * m�todo ser� chamado pelo m�todo 'confirma' da super classe.
	 * 
	 * @param dao
	 *            O Gen�rico dao utilizado pelo confirma j� com todo o controle
	 *            de transa��o
	 * 
	 * @return TRUE caso o m�todo confirma da super classe n�o deva executar a
	 *         rotina normal, caso contr�rio, FALSE
	 * 
	 */
	protected boolean confirmarSubClasse(
			br.com.space.api.web.modelo.dao.GenericoDAO<IPersistent> dao) {

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.AcaoCadastroPadrao#voltar()
	 */
	@Override
	public String voltar() {
		operacao = Operacao.NENHUMA;
		return getUrlViewVoltar();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.Incluivel#
	 * verificarPermissaoIncluir()
	 */
	@Override
	public boolean verificarPermissaoIncluir() {
		/*
		 * return gerentePermissao.verificarPermissao(getNomePrograma(),
		 * Integer.toString(Operacao.INCLUIR));
		 */
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.Editavel#
	 * verificarPermissaoAlterar()
	 */
	@Override
	public boolean verificarPermissaoAlterar() {
		/*
		 * return gerentePermissao.verificarPermissao(getNomePrograma(),
		 * Integer.toString(Operacao.EDITAR));
		 */
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.Apagavel#
	 * verificarPermissaoExcluir()
	 */
	@Override
	public boolean verificarPermissaoExcluir() {
		/*
		 * return gerentePermissao.verificarPermissao(getNomePrograma(),
		 * Integer.toString(Operacao.EXCLUIR));
		 */
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.Visualizavel#
	 * verificarPermissaoVisualizar()
	 */
	@Override
	public boolean verificarPermissaoVisualizar() {
		/*
		 * return gerentePermissao.verificarPermissao(getNomePrograma(),
		 * Integer.toString(Operacao.VISUALIZAR));
		 */
		return true;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Incluivel#incluir(java.
	 * lang.Object, br.com.space.api.web.modelo.dao.GenericoDAO)
	 */
	@Override
	public Serializable incluir(T t, GenericoDAO<IPersistent> dao) {
		return dao.insertObject(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Editavel#editar(java.lang
	 * .Object, br.com.space.api.web.modelo.dao.GenericoDAO)
	 */
	@Override
	public void editar(T t, GenericoDAO<IPersistent> dao) {
		dao.update(t);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.Apagavel#apagar(java.lang
	 * .Object, br.com.space.api.web.modelo.dao.GenericoDAO)
	 */
	@Override
	public void apagar(T t, GenericoDAO<IPersistent> dao) {
		dao.delete(t);
	}

	/**
	 * Valida o campo passado para o backbean. M�todo chamado pelo jboss.
	 * 
	 * @param context
	 * @param component
	 * @param object
	 */
	public void validarCampo(FacesContext context, UIComponent component,
			Object object) {
	}

	/**
	 * 
	 * @return O ID da opera��o que est� sendo executada
	 */
	public int getOperacao() {
		return operacao;
	}

	/**
	 * O objeto que est� sendo manipulado
	 * 
	 * @return
	 */
	public T getObjetoSelecionado() {
		return objetoSelecionado;
	}

	/**
	 * @param objetoSelecionado
	 */
	public void setObjetoSelecionado(T objetoSelecionado) {
		this.objetoSelecionado = objetoSelecionado;
	}

	/**
	 * @return TRUE caso a opera��o atual seja de edi��o ou inclus�o, caso
	 *         contr�rio, FALSE
	 */
	public boolean getFlagEditando() {

		return getFlagNovo() || getFlagEdicao();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.CadastroPadrao#getFlagExcluindo
	 * ()
	 */
	@Override
	public boolean getFlagExcluindo() {
		return operacao == Operacao.EXCLUIR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.CadastroPadrao#getFlagNovo
	 * ()
	 */
	@Override
	public boolean getFlagNovo() {
		return operacao == Operacao.INCLUIR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * br.com.space.myspace.modelo.padrao.interfaces.CadastroPadrao#getFlagEdicao
	 * ()
	 */
	@Override
	public boolean getFlagEdicao() {
		return operacao == Operacao.EDITAR;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.CadastroPadrao#
	 * getFlagVisualizacao()
	 */
	@Override
	public boolean getFlagVisualizacao() {
		return operacao == Operacao.VISUALIZAR;
	}

	public boolean getFlagExibirPopupCadastro() {
		return getFlagEditando() || getFlagExcluindo() || getFlagVisualizacao();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.myspace.modelo.padrao.interfaces.CadastroPadrao#
	 * getMensagemOperacao()
	 */
	public String getMensagemOperacao() {

		if (operacao == Operacao.NENHUMA) {
			return null;
		}

		switch (operacao) {

		case Operacao.INCLUIR:
			return propriedade.getMensagem("mensagem.inclusao");
		case Operacao.EDITAR:
			return propriedade.getMensagem("mensagem.altercao");
		case Operacao.EXCLUIR:
			return propriedade.getMensagem("mensagem.exclusao");
		case Operacao.VISUALIZAR:
			return propriedade.getMensagem("mensagem.visualizacao");
		default:
			return null;
		}
	}

	/**
	 * @return DAO
	 */
	public GenericoDAO<IPersistent> getDaoObjetoSelecionado() {
		return dao;
	}
}
