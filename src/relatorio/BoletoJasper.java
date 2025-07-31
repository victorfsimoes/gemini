package relatorio;

import java.io.Serializable;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;

import javax.faces.context.FacesContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import br.com.space.spacewebII.modelo.anotacao.stereotype.ManagedBeanSessionScoped;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

@ManagedBeanSessionScoped
public class BoletoJasper implements Serializable {

	private static final long serialVersionUID = 1L;

	public BoletoJasper() {

	}

	public static void main(String[] args) throws Exception {

		BoletoJasper boletoJasper = new BoletoJasper();

		boletoJasper.e();

		System.out.println("Acabou Acabou Acabou");

	}

	public void boletoResponse() throws Exception {

		String select = "select * from finang where fng_codigoseq = 4341";

		GenericoDAO dao = new GenericoDAO();

		ResultSet resultSet = dao.listResultSet(select);

		URL url = getClass().getClassLoader().getResource(
				"relatorio/Boleta.jasper");

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

		JRResultSetDataSource dataSource = new JRResultSetDataSource(resultSet);

		byte[] bytes = JasperRunManager.runReportToPdf(jasperReport,
				new HashMap(), dataSource);

		HttpServletResponse response = (HttpServletResponse) FacesContext
				.getCurrentInstance().getExternalContext().getResponse();

		response.setContentType("application/pdf");
		response.setContentLength(bytes.length);

		try (ServletOutputStream ous = response.getOutputStream()) {
			ous.write(bytes, 0, bytes.length);
		}
	}

	private void e() throws Exception {
		String select = "select * from finang where fng_codigoseq = 4341";

		GenericoDAO dao = new GenericoDAO();

		// List<NaturezaOperacao> list = dao.list(NaturezaOperacao.class);

		ResultSet resultSet = dao.listResultSet(select);

		URL url = getClass().getClassLoader().getResource(
				"relatorio/Boleta.jasper");

		JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

		JRResultSetDataSource dataSource = new JRResultSetDataSource(resultSet);

		/*
		 * byte[] bytes = JasperRunManager.runReportToPdf(jasperReport, new
		 * HashMap(), dataSource);
		 */

		JasperRunManager.runReportToPdfFile(url.getPath(),
				"c://temp//boletaAlex.pdf", new HashMap(), dao.getConnection());
	}
}
