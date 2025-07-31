package br.com.space.spacewebII.controle.autorizacao;

import java.io.Serializable;
import java.sql.ResultSet;

import br.com.space.api.negocio.modelo.dominio.IAutorizacao;
import br.com.space.api.negocio.modelo.dominio.IMensagem;
import br.com.space.spacewebII.controle.Sistema;
import br.com.space.spacewebII.controle.aplicacao.SessaoMB;
import br.com.space.spacewebII.controle.autenticacao.GerenteLogin;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dominio.sistema.Autorizacao;
import br.com.space.spacewebII.modelo.dominio.sistema.Mensagem;
import br.com.space.spacewebII.modelo.dominio.sistema.MensagemCorreio;

public class GerenteAutorizacao extends br.com.space.api.negocio.modelo.negocio.GerenteAutorizacao
		implements Serializable {

	private static final long serialVersionUID = 1L;

	private GerenteLogin gerenteLogin;
	private SessaoMB sessaoMB;

	/**
	 * 
	 * @param dao
	 * @param gerenteLogin
	 * @param sessaoMB
	 */
	public GerenteAutorizacao(GenericoDAO dao, GerenteLogin gerenteLogin, SessaoMB sessaoMB) {
		super(dao);

		// this.dao = dao;
		this.gerenteLogin = gerenteLogin;
		this.sessaoMB = sessaoMB;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerenteAutorizacao#
	 * recuperarAutorizacao(java.lang.String, java.lang.String,
	 * java.lang.Integer, java.lang.Integer)
	 */
	@Override
	public IAutorizacao recuperarAutorizacao(String chave, String nomePrograma, Integer permissaoCodigo,
			Integer sessaoCodigo) {

		// Autorizacao autorizacao;
		// this.chave = this.montarChave();
		// int sessao = this.sessaoMB.getCodigoSessaoAtiva();
		return (IAutorizacao) Autorizacao.recuperarAutorizacao((GenericoDAO) this.getDao(), chave, nomePrograma,
				permissaoCodigo, sessaoCodigo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerenteAutorizacao#
	 * recuperarMensagem (java.lang.String)
	 */
	@Override
	protected IMensagem recuperarMensagem(String mensagemCodigo) {
		return (IMensagem) Mensagem.recuperarUnico((GenericoDAO) this.getDao(), mensagemCodigo);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see br.com.space.api.negocio.modelo.negocio.GerenteAutorizacao#
	 * inicializarNovaAutorizacao(java.lang.String, java.lang.String)
	 */
	@Override
	protected void inicializarNovaAutorizacao(String status, String tipoLiberacao) {

		Autorizacao autorizacao = new Autorizacao();

		autorizacao.setChave(this.getExcecaoAutorizavel().getChave());
		autorizacao.setDataSolicitacao(Sistema.obterData());
		autorizacao.setFilialCodigo(this.gerenteLogin.getFilialLogada().getCodigo());
		autorizacao.setHoraSolicitacao(Sistema.obterHora());

		// Dados virão da exceção

		String obs = this.getExcecaoAutorizavel().getObservacao();
		String regraMsg = ((Exception) this.getExcecaoAutorizavel()).getMessage();

		obs = obs.replace("{regraDescricao}", this.getMensagem().getDescricao()).replace("{regraMensagem}", regraMsg)
				.replace("{usuarioLogin}", gerenteLogin.getUsuarioLogado().getLogin());

		autorizacao.setObservacao(obs);

		autorizacao.setPermissaoCodigo(this.getMensagem().getPermissaoCodigo());
		autorizacao.setProgramaCodigo(this.getExcecaoAutorizavel().getNomePrograma());
		autorizacao.setSessaoCodigoSolicitacao(sessaoMB.getCodigoSessaoAtiva());
		autorizacao.setStatus(status);
		autorizacao.setTipoLiberacao(tipoLiberacao);
		autorizacao.setValor(this.getExcecaoAutorizavel().getValor());

		this.setAutorizacao(autorizacao);
	}

	/**
	 * Método para enviar solicitação a usuários que possuem permissão para
	 * atender à solicitação
	 */
	@Override
	protected void enviarSolicitacao() {
		ResultSet resultUsuarios = null;
		String sql = new StringBuilder("select usr_login, usr_nome, usr_clbcodigo, usr_grucodigo, usr_email, ")
				.append(" usr_ativo, usr_bloq, upr_incexcl").append(" from usuario ")
				.append(" left join usrprog on usr_login = upr_usrlogin ").append(" and upr_percodigo = ")
				.append(this.getMensagem().getPermissaoCodigo()).append(" and upr_prgcodigo = '")
				.append(this.getExcecaoAutorizavel().getNomePrograma())
				.append("' and upr_incexcl = 'E' and upr_usrlogin <> 'MANUTMAC'")
				.append(" where usr_ativo = 1 and usr_bloq = 0 ")
				.append(" and (exists (select * from permisgrupo where usr_grucodigo = psg_grucodigo and psg_percodigo = ")
				.append(this.getMensagem().getPermissaoCodigo()).append(" and psg_prgcodigo = '")
				.append(this.getExcecaoAutorizavel().getNomePrograma()).append("') or ")
				.append(" exists (select * from usrprog where usr_login = upr_usrlogin ")
				.append(" and ((upr_percodigo = ").append(this.getMensagem().getPermissaoCodigo())
				.append(" and upr_prgcodigo = '").append(this.getExcecaoAutorizavel().getNomePrograma())
				.append("') or (upr_usrlogin = 'MANUTMAC')))) ")
				.append(" having upr_incexcl is null order by usr_grucodigo, usr_login ").toString();

		try {
			resultUsuarios = ((GenericoDAO) this.getDao()).listResultSet(sql);
			while (resultUsuarios.next()) {
				this.gravarMensagemCorreio(resultUsuarios.getString("usr_login"));
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			GenericoDAO.closeResultSet(resultUsuarios);
		}

	}

	/**
	 * Método para gravar mensagem de correio
	 * 
	 * @param usuarioDestino
	 */
	private void gravarMensagemCorreio(String usuarioDestino) {

		MensagemCorreio mensagemCorreio = new MensagemCorreio();

		mensagemCorreio.setAssunto(sessaoMB.getPropriedade().getMensagem("mensagem.msgcorreio.assunto.autorizacao"));
		mensagemCorreio.setDataEnvio(Sistema.obterData());
		mensagemCorreio.setUsuarioDestinoLogin(usuarioDestino);
		mensagemCorreio.setUsuarioEnvioLogin(this.gerenteLogin.getUsuarioLogado().getLogin());
		mensagemCorreio.setHoraEnvio(Sistema.obterHora());
		mensagemCorreio.setHoraLeitura("");
		mensagemCorreio.setMensagem(this.getAutorizacao().getObservacao());
		mensagemCorreio.setStatusMensagem("E");
		mensagemCorreio.setAutorizacaoCodigo(this.getAutorizacao().getNumero());
		mensagemCorreio.setTipoAutorizacao("A");

		this.getDao().insert(mensagemCorreio);
	}

	/**
	 * Função para verificar se determinado usuário possui permissão para
	 * liberar exceção
	 * 
	 * @param usuario
	 * @return
	 */
	public boolean verificarPermissaoUsuario(String usuario) {
		ResultSet resultUsuario = null;
		boolean resultado = false;

		String sql = new StringBuilder("select usr_login, usr_nome, usr_clbcodigo, usr_grucodigo, usr_email, ")
				.append(" usr_ativo, usr_bloq, upr_incexcl").append(" from usuario ")
				.append(" left join usrprog on usr_login = upr_usrlogin ").append(" and upr_percodigo = ")
				.append(this.getMensagem().getPermissaoCodigo()).append(" and upr_prgcodigo = '")
				.append(this.getExcecaoAutorizavel().getNomePrograma())
				.append("' and upr_incexcl = 'E' and upr_usrlogin <> 'MANUTMAC'")
				.append(" where usr_ativo = 1 and usr_bloq = 0 ")
				.append(" and (exists (select * from permisgrupo where usr_grucodigo = psg_grucodigo and psg_percodigo = ")
				.append(this.getMensagem().getPermissaoCodigo()).append(" and psg_prgcodigo = '")
				.append(this.getExcecaoAutorizavel().getNomePrograma()).append("') or ")
				.append(" exists (select * from usrprog where usr_login = upr_usrlogin ")
				.append(" and ((upr_percodigo = ").append(this.getMensagem().getPermissaoCodigo())
				.append(" and upr_prgcodigo = '").append(this.getExcecaoAutorizavel().getNomePrograma())
				.append("') or (upr_usrlogin = 'MANUTMAC')))) ").append(" and (usr_login = '").append(usuario)
				.append("' or usr_login = 'MANUTMAC')")
				.append(" having upr_incexcl is null order by usr_grucodigo, usr_login ").toString();

		try {
			resultUsuario = ((GenericoDAO) this.getDao()).listResultSet(sql);
			if (resultUsuario != null && resultUsuario.first()) {
				resultado = true;
			}

		} catch (Exception e) {
			e.printStackTrace();

		} finally {
			GenericoDAO.closeResultSet(resultUsuario);
		}

		return resultado;
	}

	@Override
	public void preencherAutorizacaoUtilizada() {
		this.getAutorizacao().setDataUtilizacao(Sistema.obterData());
		this.getAutorizacao().setHoraUtilizacao(Sistema.obterHora());

		super.preencherAutorizacaoUtilizada();
	}

	public GerenteLogin getGerenteLogin() {
		return gerenteLogin;
	}

	public void setGerenteLogin(GerenteLogin gerenteLogin) {
		this.gerenteLogin = gerenteLogin;
	}

	public SessaoMB getSessaoMB() {
		return sessaoMB;
	}

	public void setSessaoMB(SessaoMB sessaoMB) {
		this.sessaoMB = sessaoMB;
	}

}
