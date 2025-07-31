package br.com.space.spacewebII.controle;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import br.com.space.spacewebII.modelo.dao.GenericoDAOLog;

public class MonitorSessao implements ActionListener, Runnable {

	private GenericoDAO dao = null;
	private GenericoDAOLog daoLog = null;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		new Thread(this).start();
	}

	/**
	 * 
	 * @return
	 */
	public GenericoDAO getDao() {
		if (dao == null) {
			dao = new GenericoDAO();
		}
		return dao;
	}

	/**
	 * 
	 * @return
	 */
	public GenericoDAOLog getDaoLog() {
		if (daoLog == null) {
			daoLog = new GenericoDAOLog();
		}

		return daoLog;
	}

	@Override
	public void run() {
		
		GerenteSessao.fecharSessoesInativa(getDao(), getDaoLog(),
				"Log Gerado pela rotina automática de Inativação de sessão.");
		Licenca.carregarLicenca();
	}
}
