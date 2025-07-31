package br.com.space.spacewebII.modelo.dominio.venda;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.spa.model.domain.IPersistent;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "tipoentrega")
public class TipoEntrega implements IPersistent, Serializable {

	public static int TIPO_CALCULO_PEDIDO = 1;
	public static int TIPO_CALCULO_VOLUME = 0;

	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "tpe_codigo")
	private String codigo;

	@Basic(optional = false)
	@Column(name = "tpe_perdesfrete")
	private double descontoFretePercentual;

	@Basic(optional = false)
	@Column(name = "tpe_infendent")
	private int flagInformaEnderecoEntrega;

	@Basic(optional = false)
	@Column(name = "tpe_ativo")
	private int flagAtivo;

	@Column(name = "tpe_wsintegra")
	private int flagIntegraWebService;

	@Column(name = "tpe_wscodserv")
	private String codigoServicoWebService;

	@Column(name = "tpe_wsmodelo")
	private int modeloWebService;

	@Column(name = "tpe_wsusuario")
	private String usuarioWebService;

	@Column(name = "tpe_wssenha")
	private String senhaWebService;

	@Column(name = "tpe_tvecodigo")
	private int tipoVeiculoCodigo;

	@Column(name = "tpe_prazomin")
	private int prazoMinimo;

	@Column(name = "tpe_prazomax")
	private int prazoMaximo;

	@Column(name = "tpe_fatpescub")
	private double fatorPesoCubico;

	@Column(name = "tpe_tipfretevol")
	private int tipoCalculoFreteVolume;

	@Column(name = "tpe_persegenc")
	private double percentualSeguroPrecoLiquido = 0;

	@Column(name = "tpe_avisorecb")
	private int flagAvisoRecebimento = 0;

	@Column(name = "tpe_maopropria")
	private int flagMaoPropria = 0;

	/**
	 * 
	 */
	public TipoEntrega() {

	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	public double getDescontoFretePercentual() {
		return descontoFretePercentual;
	}

	public void setDescontoFretePercentual(double descontoFretePercentual) {
		this.descontoFretePercentual = descontoFretePercentual;
	}

	public int getFlagInformaEnderecoEntrega() {
		return flagInformaEnderecoEntrega;
	}

	public void setFlagInformaEnderecoEntrega(int flagInformaEnderecoEntrega) {
		this.flagInformaEnderecoEntrega = flagInformaEnderecoEntrega;
	}

	public int getFlagAtivo() {
		return flagAtivo;
	}

	public void setFlagAtivo(int flagAtivo) {
		this.flagAtivo = flagAtivo;
	}

	public int getFlagIntegraWebService() {
		return flagIntegraWebService;
	}

	public void setFlagIntegraWebService(int flagIntegraWebService) {
		this.flagIntegraWebService = flagIntegraWebService;
	}

	public String getCodigoServicoWebService() {
		return codigoServicoWebService;
	}

	public void setCodigoServicoWebService(String codigoServicoWebService) {
		this.codigoServicoWebService = codigoServicoWebService;
	}

	public int getModeloWebService() {
		return modeloWebService;
	}

	public void setModeloWebService(int modeloWebService) {
		this.modeloWebService = modeloWebService;
	}

	public String getUsuarioWebService() {
		return usuarioWebService;
	}

	public void setUsuarioWebService(String usuarioWebService) {
		this.usuarioWebService = usuarioWebService;
	}

	public String getSenhaWebService() {
		return senhaWebService;
	}

	public void setSenhaWebService(String senhaWebService) {
		this.senhaWebService = senhaWebService;
	}

	public int getTipoVeiculoCodigo() {
		return tipoVeiculoCodigo;
	}

	public void setTipoVeiculoCodigo(int tipoVeiculoCodigo) {
		this.tipoVeiculoCodigo = tipoVeiculoCodigo;
	}

	public int getPrazoMinimo() {
		return prazoMinimo;
	}

	public void setPrazoMinimo(int prazoMinimo) {
		this.prazoMinimo = prazoMinimo;
	}

	public int getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(int prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	public double getFatorPesoCubico() {
		return fatorPesoCubico;
	}

	public void setFatorPesoCubico(double fatorPesoCubico) {
		this.fatorPesoCubico = fatorPesoCubico;
	}

	public boolean isTipoCalculoFretePorVolume() {
		return tipoCalculoFreteVolume == TIPO_CALCULO_VOLUME;
	}

	public int getTipoCalculoFreteVolume() {
		return tipoCalculoFreteVolume;
	}

	public void setTipoCalculoFreteVolume(int tipoCalculoFreteVolume) {
		this.tipoCalculoFreteVolume = tipoCalculoFreteVolume;
	}

	public double getPercentualSeguroPrecoLiquido() {
		return percentualSeguroPrecoLiquido;
	}

	public void setPercentualSeguroPrecoLiquido(
			double percentualSeguroPrecoLiquido) {
		this.percentualSeguroPrecoLiquido = percentualSeguroPrecoLiquido;
	}

	public boolean isAvisoRecebimento() {
		return flagAvisoRecebimento == 1;
	}

	public int getFlagAvisoRecebimento() {
		return flagAvisoRecebimento;
	}

	public void setFlagAvisoRecebimento(int flagAvisoRecebimento) {
		this.flagAvisoRecebimento = flagAvisoRecebimento;
	}

	public boolean isMaoPropria() {
		return flagMaoPropria == 1;
	}

	public int getFlagMaoPropria() {
		return flagMaoPropria;
	}

	public void setFlagMaoPropria(int flagMaoPropria) {
		this.flagMaoPropria = flagMaoPropria;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table table) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	@Override
	public String toString() {
		return this.getCodigo();
	}

	/**
	 * 
	 * @param dao
	 * @return
	 */
	public static List<TipoEntrega> recuperaAtivos(GenericoDAO dao) {
		String where = "tpe_ativo = 1";
		return dao.list(TipoEntrega.class, where, null, "codigo", null);
	}

	/**
	 * 
	 * @param dao
	 * @param tipoEntregaCodigo
	 * @return
	 */
	public static TipoEntrega recuperarCodigo(GenericoDAO dao,
			String tipoEntregaCodigo) {
		if (tipoEntregaCodigo == null || tipoEntregaCodigo.length() == 0)
			return null;
		String where = "tpe_codigo = '" + tipoEntregaCodigo + "'";
		return dao.uniqueResult(TipoEntrega.class, where, null);
	}

}
