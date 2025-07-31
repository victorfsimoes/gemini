package br.com.space.spacewebII.modelo.dominio.sistema;

import java.io.Serializable;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import br.com.space.api.negocio.modelo.dominio.parametro.IParametroVenda2;
import br.com.space.spacewebII.modelo.dao.GenericoDAO;

@Entity
@Table(name = "parametro2")
public class ParametroVenda2 implements Serializable, IParametroVenda2 {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String COLUNA_FILIAL = "pa2_filcodigo";

	@Id
	@Basic(optional = false)
	@Column(name = COLUNA_FILIAL)
	private int filialCodigo;

	@Column(name = "pa2_altprazoesp")
	private int permiteAlterarPrazoEspecial;

	@Column(name = "pa2_nsugfpgcpg")
	private int naoSugereFormaCondicaoCliente;

	@Column(name = "pa2_infacreped")
	private int permiteAcrescimoPedido;

	@Column(name = "pa2_infdescped")
	private int permiteDescontoPedido;

	@Column(name = "pa2_oftabaixocs")
	private int permiteOfertaAbaixoCusto;

	@Column(name = "pa2_comprecobr")
	private int flagComissaoPrecoBruto;

	@Column(name = "pa2_selcustonat")
	private int flagSelecionaCustoNaturezaOperacao;

	@Column(name = "pa2_comiprbruto")
	private int flagBuscarFaixaComissaoPrecoBruto;

	@Column(name = "pa2_calcstped")
	private int flagCalculaStPedido;

	@Column(name = "pa2_calcvolped")
	private int flagCalculaVolumePedido;

	@Column(name = "pa2_blqitemsp")
	private int flagBloqueiaItemSemPreco;

	@Column(name = "pa2_utilprzmax")
	private int flagUtilizaPrazoMaximo;

	@Column(name = "pa2_przmaxclpa")
	private String flagPrazoMaximoClienteParametro;

	@Column(name = "pa2_prazomax")
	private int prazoMaximo;

	@Column(name = "pa2_diaspromo")
	private String diasPromocaoVenda;

	@Column(name = "pa2_precokit")
	private String tipoPerecoKit = null;
	
	@Column(name = "pa2_psvolitem")
	private int flagLancaVolumeItemPedido;

	public int getFilialCodigo() {
		return filialCodigo;
	}

	public void setFilialCodigo(int filialCodigo) {
		this.filialCodigo = filialCodigo;
	}

	public int getPermiteAlterarPrazoEspecial() {
		return permiteAlterarPrazoEspecial;
	}

	public void setPermiteAlterarPrazoEspecial(int permiteAlterarPrazoEspecial) {
		this.permiteAlterarPrazoEspecial = permiteAlterarPrazoEspecial;
	}

	public int getNaoSugereFormaCondicaoCliente() {
		return naoSugereFormaCondicaoCliente;
	}

	public void setNaoSugereFormaCondicaoCliente(
			int naoSugereFormaCondicaoCliente) {
		this.naoSugereFormaCondicaoCliente = naoSugereFormaCondicaoCliente;
	}

	public int getPermiteAcrescimoPedido() {
		return permiteAcrescimoPedido;
	}

	public void setPermiteAcrescimoPedido(int permiteAcrescimoPedido) {
		this.permiteAcrescimoPedido = permiteAcrescimoPedido;
	}

	public int getPermiteDescontoPedido() {
		return permiteDescontoPedido;
	}

	public void setPermiteDescontoPedido(int permiteDescontoPedido) {
		this.permiteDescontoPedido = permiteDescontoPedido;
	}

	public int getPermiteOfertaAbaixoCusto() {
		return permiteOfertaAbaixoCusto;
	}

	public void setPermiteOfertaAbaixoCusto(int permiteOfertaAbaixoCusto) {
		this.permiteOfertaAbaixoCusto = permiteOfertaAbaixoCusto;
	}

	public int getFlagComissaoPrecoBruto() {
		return flagComissaoPrecoBruto;
	}

	public void setFlagComissaoPrecoBruto(int flagComissaoPrecoBruto) {
		this.flagComissaoPrecoBruto = flagComissaoPrecoBruto;
	}

	public int getFlagSelecionaCustoNaturezaOperacao() {
		return flagSelecionaCustoNaturezaOperacao;
	}

	public void setFlagSelecionaCustoNaturezaOperacao(
			int flagSelecionaCustoNaturezaOperacao) {
		this.flagSelecionaCustoNaturezaOperacao = flagSelecionaCustoNaturezaOperacao;
	}

	public int getFlagBuscarFaixaComissaoPrecoBruto() {
		return flagBuscarFaixaComissaoPrecoBruto;
	}

	public int getFlagCalculaStPedido() {
		return flagCalculaStPedido;
	}

	public void setFlagCalculaStPedido(int flagCalculaStPedido) {
		this.flagCalculaStPedido = flagCalculaStPedido;
	}

	public void setFlagBuscarFaixaComissaoPrecoBruto(
			int flagBuscarFaixaComissaoPrecoBruto) {
		this.flagBuscarFaixaComissaoPrecoBruto = flagBuscarFaixaComissaoPrecoBruto;
	}

	public int getFlagCalculaVolumePedido() {
		return flagCalculaVolumePedido;
	}

	public void setFlagCalculaVolumePedido(int flagCalculaVolumePedido) {
		this.flagCalculaVolumePedido = flagCalculaVolumePedido;
	}

	public int getFlagBloqueiaItemSemPreco() {
		return flagBloqueiaItemSemPreco;
	}

	public void setFlagBloqueiaItemSemPreco(int flagBloqueiaItemSemPreco) {
		this.flagBloqueiaItemSemPreco = flagBloqueiaItemSemPreco;
	}

	public int getFlagUtilizaPrazoMaximo() {
		return flagUtilizaPrazoMaximo;
	}

	public int getPrazoMaximo() {
		return prazoMaximo;
	}

	public void setPrazoMaximo(int prazoMaximo) {
		this.prazoMaximo = prazoMaximo;
	}

	@Override
	public String getDiasPromocaoVenda() {
		return diasPromocaoVenda;
	}

	public void setDiasPromocaoVenda(String diasPromocaoVenda) {
		this.diasPromocaoVenda = diasPromocaoVenda;
	}
	
	public int getFlagLancaVolumeItemPedido() {
		return flagLancaVolumeItemPedido;
	}

	public void setFlagLancaVolumeItemPedido(int flagLancaVolumeItemPedido) {
		this.flagLancaVolumeItemPedido = flagLancaVolumeItemPedido;
	}

	public void setFlagUtilizaPrazoMaximo(int flagUtilizaPrazoMaximo) {
		this.flagUtilizaPrazoMaximo = flagUtilizaPrazoMaximo;
	}

	@Override
	public br.com.space.api.spa.model.dao.db.Table getTable() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setTable(br.com.space.api.spa.model.dao.db.Table arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public Object clone() throws CloneNotSupportedException {
		// TODO Auto-generated method stub
		return super.clone();
	}

	public static ParametroVenda2 recuperarUnico(GenericoDAO dao, int codigo) {

		return dao.uniqueResult(ParametroVenda2.class, COLUNA_FILIAL + " = "
				+ codigo, null);
	}

	public String getFlagPrazoMaximoClienteParametro() {
		return flagPrazoMaximoClienteParametro;
	}

	public void setFlagPrazoMaximoClienteParametro(
			String flagPrazoMaximoClienteParametro) {
		this.flagPrazoMaximoClienteParametro = flagPrazoMaximoClienteParametro;
	}

	@Override
	public String getTipoPerecoKit() {
		return tipoPerecoKit;
	}

	public void setTipoPerecoKit(String tipoPerecoKit) {
		this.tipoPerecoKit = tipoPerecoKit;
	}
}
