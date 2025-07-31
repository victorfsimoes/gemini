package br.com.space.spacewebII.modelo.dominio.interfaces;

import br.com.space.api.negocio.modelo.dominio.IItemPedido;
import br.com.space.spacewebII.modelo.dominio.estoque.ProdutoFilial;
import br.com.space.spacewebII.modelo.dominio.venda.Promocao;

public interface IItemPedidoWeb extends IItemPedido, IItemPedidoDescricao {

	public String getCodigoFiscalCodigo();

	public void setCodigoFiscalCodigo(String codigoFiscalCodigo);

	public double getPrecoVendaOriginal();

	public void setPrecoVendaOriginal(double precoVendaOriginal);

	public Integer getFlagAtivo();

	public void setFlagAtivo(Integer flagAtivo);

	public String getCodigoBarrasProdutoCodigo();

	public void setCodigoBarrasProdutoCodigo(String codigoBarrasProdutoCodigo);

	public double getPrecoTotal();

	public String getStatusBaixa();

	public void setStatusBaixa(String statusBaixa);

	public double getCusto1();

	public void setCusto1(double custo1);

	public double getCusto2();

	public void setCusto2(double custo2);

	public double getCusto3();

	public void setCusto3(double custo3);

	public double getCusto4();

	public void setCusto4(double custo4);

	public double getCusto5();

	public void setCusto5(double custo5);

	public double getCusto6();

	public void setCusto6(double custo6);

	public double getCusto7();

	public void setCusto7(double custo7);

	public double getCusto8();

	public void setCusto8(double custo8);

	public double getCusto9();

	public void setCusto9(double custo9);

	public double getCusto10();

	public void setCusto10(double custo10);

	public Double getPesoBrutoProduto();

	public void setPesoBrutoProduto(Double pesoBrutoProduto);

	public Double getPesoLiquidoProduto();

	public void setPesoLiquidoProduto(Double pesoLiquidoProduto);

	public Integer getGrupoComissaoCodigo();

	public void setGrupoComissaoCodigo(Integer grupoComissaoCodigo);

	public double getPercentualComissao1();

	public void setPercentualComissao1(double percentualComissao1);

	public double getPercentualComissao2();

	public void setPercentualComissao2(double percentualComissao2);

	public double getPercentualComissao3();

	public void setPercentualComissao3(double percentualComissao3);

	public double getPercentualComissao4();

	public void setPercentualComissao4(double percentualComissao4);

	public double getPercentualComissao5();

	public void setPercentualComissao5(double percentualComissao5);

	public Double getMetroCubico();

	public void setMetroCubico(Double metroCubico);

	public Double getQuantidadeVolumes();

	public void setQuantidadeVolumes(Double quantidadeVolumes);

	public void setKitCodigo(Integer kitCodigo);

	public Double getValorSubstituicao();

	public void setValorSubstituicao(Double valorSubstituicao);

	public Double getValorSubstituicaoAvulso();

	public void setValorSubstituicaoAvulso(Double valorSubstituicaoAvulso);

	public double getValorFundoEstadualCombatePobreza();

	public void setValorFundoEstadualCombatePobreza(double valorSubstituicaoAvulso);

	public Integer getLocalEstoqueCodigo();

	public void setLocalEstoqueCodigo(Integer localEstoqueCodigo);

	public String getLote();

	public void setLote(String lote);

	public String getCertificadoClassificacao();

	public void setCertificadoClassificacao(String certificadoClassificacao);

	public Integer getNegociacaoProdutoNumero();

	public void setNegociacaoProdutoNumero(Integer negociacaoProdutoNumero);

	public String getDescricaoNF1();

	public void setDescricaoNF1(String descricaoNF1);

	public String getDescricaoNF2();

	public void setDescricaoNF2(String descricaoNF2);

	public String getDescricaoNF3();

	public void setDescricaoNF3(String descricaoNF3);

	public String getDescricaoNF4();

	public void setDescricaoNF4(String descricaoNF4);

	public String getDescricaoNF5();

	public void setDescricaoNF5(String descricaoNF5);

	public Integer getParcelaOrdemServico();

	public void setParcelaOrdemServico(Integer parcelaOrdemServico);

	public void setPromocaoNumero(Integer promocaoNumero);

	public boolean isPermiteAlterarQuantidadeNoList();

	public Double getPercentualIpi();

	public void setPercentualIpi(Double percentualIpi);

	public Double getValorIpi();

	public void setValorIpi(Double valorIpi);

	public String getCstIpiCodigo();

	public void setCstIpiCodigo(String cstIpiCodigo);

	public int getPedidoNumero();

	public void setPedidoNumero(int pedidoNumero);

	public int getFilialCodigo();

	public void setFilialCodigo(int filialCodigo);

	public double getQuantidadeAnterior();

	public void setQuantidadeAnterior(double quantidadeAnterior);

	public double getQuantidadeCarrinho();

	public void setQuantidadeCarrinho(double quantidade);

	public ProdutoFilial getProdutoFilial();

	public void setProdutoFilial(ProdutoFilial produtoFilial);

	public void setPromocao(Promocao promocao);

	public void reiniciarValoresImpostos();

	public double calcularPrecoLiquido();

	public double getPrecoLiquidoTotalCalculado();

	public double getValorPis();

	public double getValorCofins();

	public double getValorIcms();

	public void setValorPis(double valorPis);

	public void setValorCofins(double valorCofins);

	public void setValorIcms(double valorIcms);

	public int getGrupoProdutoCodigo();

	public int getSubGrupoProdutoCodigo();

	public int getCategoriaProdutoCodigo();

	public int getSubCategoriaProdutoCodigo();

	public double getValorFrete();

	public void setValorFrete(double valorFrete);

}
