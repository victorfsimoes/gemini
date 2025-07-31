package br.com.space.spacewebII.modelo.util.teste;

import java.util.ArrayList;
import java.util.List;


public class BannerTeste {
	private List<String> imagensBannerPrincipal;

	
	public void init() {

		try {

			imagensBannerPrincipal = new ArrayList<String>();

			imagensBannerPrincipal.add("slider1.jpg");
			imagensBannerPrincipal.add("slider2.jpg");
			imagensBannerPrincipal.add("slider4.png");

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public List<String> getImagensBannerPrincipal() {
		return imagensBannerPrincipal;
	}

}
