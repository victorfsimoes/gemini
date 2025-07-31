package br.com.space.spacewebII.controle.aplicacao;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ImageServlet extends com.jsos.image.ImageServlet{
	private static final long serialVersionUID = 1L;
	
	public ImageServlet(){
		
	}
	
	@Override
	public void doPost(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		//this.getServletContext().setInitParameter("dir", "C:/Users/Marina/Desktop/Imagens/Space Web II");
		super.doPost(arg0, arg1);
	}
	
	@Override
	public void doGet(HttpServletRequest arg0, HttpServletResponse arg1)
			throws ServletException, IOException {
		//this.getServletContext().setInitParameter("dir", "C:/Users/Marina/Desktop/Imagens/Space Web II");
		super.doGet(arg0, arg1);
	}
	

}
