package relatorio;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import br.com.space.spacewebII.modelo.dao.GenericoDAO;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.JasperRunManager;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Boleto
 */
@WebServlet("/Boleto")
public class Boleto extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Boleto() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		try {

			String select = "select * from finang where fng_codigoseq = 4341";

			GenericoDAO dao = new GenericoDAO();

			ResultSet resultSet = dao.listResultSet(select);

			URL url = getClass().getClassLoader().getResource(
					"relatorio/Boleta.jasper");

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(url);

			JRResultSetDataSource dataSource = new JRResultSetDataSource(
					resultSet);

			Map<String, Object> parametros = new HashMap<>();

			parametros
					.put("imagem_banco",
							"\\\\192.168.0.2\\space_vendasweb\\formapagamento\\boleto.png");

			byte[] bytes = JasperRunManager.runReportToPdf(jasperReport,
					parametros, dataSource);

			response.setContentType("application/pdf");
			response.setContentLength(bytes.length);

			try (ServletOutputStream ous = response.getOutputStream();) {

				ous.write(bytes, 0, bytes.length);

			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
