package br.com.space.spacewebII.modelo.pesquisa;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TabelaResultados implements Serializable {

	private static final long serialVersionUID = 1L;
	private List<ColumnModel> columns = new ArrayList<ColumnModel>();
	private List<String> resultados = new ArrayList<>();

	public TabelaResultados() {

	}

	public List<String> getResultados() {
		return resultados;
	}

	public void setResultados(List<String> resultados) {
		this.resultados = resultados;
	}

	static public class ColumnModel implements Serializable {
		private String header;
		private String property;

		public ColumnModel(String header, String property) {
			this.header = header;
			this.property = property;
		}

		public String getHeader() {
			return header;
		}

		public String getProperty() {
			return property;
		}
	}

	public void createDynamicColumns() {
		/*
		 * String[] columnKeys = columnTemplate.split(" "); columns.clear();
		 * 
		 * for(String columnKey : columnKeys) { String key = columnKey.trim();
		 * 
		 * if(VALID_COLUMN_KEYS.contains(key)) { columns.add(new
		 * ColumnModel(columnKey.toUpperCase(), columnKey)); } }
		 */
	}

}
