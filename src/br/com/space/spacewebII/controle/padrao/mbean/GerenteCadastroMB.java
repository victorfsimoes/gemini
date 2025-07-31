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
 * Classe padrão de cadastro
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
	 * Construtor padrão
	 * 
	 * @param classObjeto
	 *            Corresponde a classe do objeto principal do cadastro
	 */
	public GerenteCadastroMB(Class<T> classObjeto) {
		this.classObjeto = classObjeto;
	}

	/**
	 * Método que será subscrito pelas classes específicas a fim de validar o
	 * objeto que será incluído ou editado
	 * 
	 * @param t
	 *            O objeto que será incluído ou editado
	 * @throws Exception
	 *             Caso exista alguma inconformidade com o objeto em parâmetro.
	 *             (O lançamento das inconformidades é responsabilidade da
	 *             subclasse)
	 */
	public abstract String validarObjetoIncluirEditar();

	/**
	 * Método que será subscrito pelas classes específicas a fim de validar o
	 * objeto que será excluído
	 * 
	 * @param t
	 *            O objeto que será excluído
	 * @throws Exception
	 *             Caso exista alguma inconformidade com o objeto em parâmetro.
	 *             (O lançamento das inconformidades é responsabilidade da
	 *             subclasse)
	 */
	public abstract void validarObjetoExcluir(T t) throws SpaceExcecao;

	/**
	 * Impede a alteração de um campo que está sendo editado no momento por
	 * outra pessoa.
	 */
	public boolean travarRegistro() {
		return false;
	}

	/**
	 * Método que destrava o campo quando ele não estiver sendo utilizado
	 */
	public void destravarRegistro() {
	}

	/**
	 * Método responsável pela criação de uma nova instância do Objeto tipado e
	 * de atribuir a operação realizada.
	 * 
	 * @return Url da view responsável pelo cadastro
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
	 * Método responsável por popular o objeto a ser alterado e atribuir a nova
	 * operação.
	 * 
	 * @param objetoSelecionado
	 *            O objeto tipado que será alterado
	 * @return Url da página onde o objeto foi alterado
	 */
	@Override
	public String carregarEdicao(T objetoSelecionado) {
		this.setObjetoSelecionado(objetoSelecionado);
		this.operacao = Operacao.EDITAR;

		return this.carregarView();
	}

	/**
	 * Método responsável por popular o objeto a ser excluído e atribuir a nova
	 * operação
	 * 
	 * @param objetoSelecionado
	 *            O objeto tipado que será excluído
	 * @return Url da view responsável pela exclusão
	 */
	@Override
	public String carregarExclusao(T objetoSelecionado) {
		this.setObjetoSelecionado(objetoSelecionado);
		this.operacao = Operacao.EXCLUIR;

		return this.carregarView();
	}

	/**
	 * Método responsável por popular o objeto a ser visualizado e atribuir a
	 * nova operação
	 * 
	 * @param objetoSelecionado
	 *            O objeto tipado que será visualizado
	 * @return Url da view responsável pela visualização
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

				msgErro = "Falha na operação " + getMensagemOperacao();

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
	 * Caso na subClasse exista alguma operação especial que o método 'confirma'
	 * da super classe não é suficiente, este método deve ser implementado. O
	 * método será chamado pelo método 'confirma' da super classe.
	 * 
	 * @param dao
	 *            O Genérico dao utilizado pelo confirma já com todo o controle
	 *            de transação
	 * 
	 * @return TRUE caso o método confirma da super classe não deva executar a
	 *         rotina normal, caso contrário, FALSE
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
	 * Valida o campo passado para o backbean. Método chamado pelo jboss.
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
	 * @return O ID da operação que está sendo executada
	 */
	public int getOperacao() {
		return operacao;
	}

	/**
	 * O objeto que está sendo manipulado
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
	 * @return TRUE caso a operação atual seja de edição ou inclusão, caso
	 *         contrário, FALSE
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
