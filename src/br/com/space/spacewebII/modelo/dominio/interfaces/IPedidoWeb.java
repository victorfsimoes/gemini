package br.com.space.spacewebII.modelo.dominio.interfaces;

import java.util.Date;

import br.com.space.api.negocio.modelo.dominio.IPedido;
import br.com.space.spacewebII.modelo.padrao.interfaces.Travavel;

public interface IPedidoWeb extends IPedido, Travavel {

	public Integer getCarga();

	public void setCarga(Integer carga);

	public int getFlagDebitoCredito();

	public void setFlagDebitoCredito(int flagDebitoCredito);

	public Double getFrete();

	public void setFrete(Double frete);

	public Integer getQuantidadeImpressao();

	public void setQuantidadeImpressao(Integer quantidadeImpressao);

	public String getOrigem();

	public void setOrigem(String origem);

	public String getCodigoOrigem();

	public void setCodigoOrigem(String codigoOrigem);

	public int getEnderecoEntregaCodigo();

	public void setEnderecoEntregaCodigo(int enderecoEntregaCodigo);

	public Integer getEnderecoCobrancaCodigo();

	public void setEnderecoCobrancaCodigo(Integer enderecoCobrancaCodigo);

	public Integer getOrcamentoCodigo();

	public void setOrcamentoCodigo(Integer orcamentoCodigo);

	public Double getLucratividade();

	public void setLucratividade(Double lucratividade);

	public int getOrigemVendaCodigo();

	public void setOrigemVendaCodigo(int origemVendaCodigo);

	public Date getDataCadastro();

	public void setDataCadastro(Date dataCadastro);

	public int getFlagAtivo();

	public void setFlagAtivo(int flagAtivo);

	public int getPrecoBaseCodigo();

	public void setPrecoBaseCodigo(int precoBaseCodigo);

	public int getControleNumeroCodigo();

	public void setControleNumeroCodigo(int controleNumeroCodigo);

	public int getStatusPedidoCodigo();

	public void setStatusPedidoCodigo(int statusPedidoCodigo);

	public String getObservacao();

	public void setObservacao(String observacao);

	public int getLoteLancamentoFinanceiro();

	public void setLoteLancamentoFinanceiro(int loteLancamentoFinanceiro);

	public Double getPesoBruto();

	public void setPesoBruto(Double pesoBruto);

	public Integer getLoteNotaPedidoCodigo();

	public void setLoteNotaPedidoCodigo(Integer loteNotaPedidoCodigo);

	public String getObservacao1();

	public void setObservacao1(String observacao1);

	public String getObservacao2();

	public void setObservacao2(String observacao2);

	public Double getValorComissao();

	public void setValorComissao(Double valorComissao);

	public Integer getLotePedidoImportadoCodigo();

	public void setLotePedidoImportadoCodigo(Integer lotePedidoImportadoCodigo);

	public double getValorComissao2();

	public void setValorComissao2(double valorComissao2);

	public double getValorComissao3();

	public void setValorComissao3(double valorComissao3);

	public double getValorComissao4();

	public void setValorComissao4(double valorComissao4);

	public double getValorComissao5();

	public void setValorComissao5(double valorComissao5);

	public Integer getTransportadoraCodigo();

	public void setTransportadoraCodigo(Integer transportadoraCodigo);

	public String getCampoUsuario1();

	public void setCampoUsuario1(String campoUsuario1);

	public String getCampoUsuario2();

	public void setCampoUsuario2(String campoUsuario2);

	public String getCampoUsuario3();

	public void setCampoUsuario3(String campoUsuario3);

	public String getCampoUsuario4();

	public void setCampoUsuario4(String campoUsuario4);

	public String getCampoUsuario5();

	public void setCampoUsuario5(String campoUsuario5);

	public Double getQuantidadeVolumes();

	public void setQuantidadeVolumes(Double quantidadeVolumes);

	public String getTipoSeparacao();

	public void setTipoSeparacao(String tipoSeparacao);

	public Double getMetrosCubicos();

	public void setMetrosCubicos(Double metrosCubicos);

	public Integer getContaCorrenteCodigo();

	public void setContaCorrenteCodigo(Integer contaCorrenteCodigo);

	public Integer getNumeroSeparacoes();

	public void setNumeroSeparacoes(Integer numeroSequencial);

	public int getNumeroEntregas();

	public void setNumeroEntregas(int numeroEntregas);

	public Integer getOrdemEntrega();

	public void setOrdemEntrega(Integer ordemEntrega);

	public Integer getTipoVendaCodigo();

	public void setTipoVendaCodigo(Integer tipoVendaCodigo);

	public Integer getOrdemCliente();

	public void setOrdemCliente(Integer ordemCliente);

	public Double getValorSubstituicao();

	public void setValorSubstituicao(Double valorSubstituicao);

	public Double getValorSubstituicaoAvulso();

	public void setValorSubstituicaoAvulso(Double valorSubstituicaoAvulso);

	public double getValorFundoEstadualCombatePobreza();

	public void setValorFundoEstadualCombatePobreza(double valorSubstituicaoAvulso);

	public void setPromocaoNumero(Integer promocaoNumero);

	public int getFlagParticipaPromocao();

	public void setFlagParticipaPromocao(int flagParticipaPromocao);

	public Double getPesoLiquido();

	public void setPesoLiquido(Double pesoLiquido);

	public boolean isAtualizaEstoque();

	public int getFlagAtualizaEstoque();

	public void setFlagAtualizaEstoque(int flagAtualizaEstoque);

	public String getFlagTipoNaturezaOperacao();

	public void setFlagTipoNaturezaOperacao(String flagTipoNaturezaOperacao);

	public int getFlagDevolucao();

	public void setFlagDevolucao(int flagDevolucao);

	public int getFlagConsisteLimite();

	public void setFlagConsisteLimite(int flagConsisteLimite);

	public Integer getNumeroPedidoPromocao();

	public void setNumeroPedidoPromocao(Integer numeroPedidoPromocao);

	public String getSeriePedidoPromocao();

	public void setSeriePedidoPromocao(String seriePedidoPromocao);

	public Double getValorIpi();

	public void setValorIpi(Double valorIpi);

	public Integer getLoteComissaoNumero();

	public void setLoteComissaoNumero(Integer loteComissaoNumero);

	public void setFilialCodigo(int filialCodigo);

	public String getSerieENumeroPedido(String separador);

	public int getStatusPagamentoCartao();

	public void setStatusPagamentoCartao(int statusPagamentoCartao);

	public double getValorPis();

	public double getValorCofins();

	public double getValorIcms();

	public void setValorPis(double valorPis);

	public void setValorCofins(double valorCofins);

	public void setValorIcms(double valorIcms);
	
	public String getClienteIdentificacao();
}
