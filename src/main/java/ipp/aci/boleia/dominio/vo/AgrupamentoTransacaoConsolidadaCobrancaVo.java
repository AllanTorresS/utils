package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusIntegracaoJde;

import javax.persistence.Transient;
import java.math.BigDecimal;
import java.util.Date;

import static java.math.BigDecimal.ROUND_HALF_DOWN;

/**
 * VO que agrupa transações consolidadas por cobrança
 */
public class AgrupamentoTransacaoConsolidadaCobrancaVo {
    private Long idFrota;
    private String nomeEmpresa;
    private Long cnpjEmpresa;
    private Date dataInicioPeriodo;
    private Date dataFimPeriodo;
    private BigDecimal valorTotal;
    private BigDecimal valorDesconto;
    private BigDecimal valorCobrancaAjustado;
    private Integer statusConsolidacao;
    private Integer statusPagamento;
    private Long quantidadeAbastecimentos;
    private Date dataVencimento;
    private Date dataLimitePagamento;
    private Date dataPagamento;
    private Date dataLimiteEmissao;
    private Boolean exigeNota;
    private BigDecimal valorEmitidoNotaFiscal;
    private BigDecimal valorTotalNotaFiscal;
    private EnumVo statusIntegracao;
    private String mensagemErroIntegracao;
    private Long idCobranca;
    private boolean ultimaCobrancaFrota;
    private Long numeroDocumento;
    private String usuarioUltimoAjusteValor;
    private Date dataUltimoAjusteValor;
    private String usuarioUltimoAjusteVencimento;
    private Date dataUltimoAjusteVencimento;
    private Boolean totalmenteEmitida;
    private String ultimaJustificativaAjuste;

    /**
     * Construtor default
     */
    public AgrupamentoTransacaoConsolidadaCobrancaVo() {
    }

    /**
     * Construtor do VO
     * @param idFrota Identificador da frota
     * @param nomeEmpresa Nome da empresa para qual a cobrança é destinada
     * @param cnpjEmpresa CNPJ da empresa para qual a cobrança é destinada
     * @param dataInicioPeriodo Data de início do ciclo
     * @param dataFimPeriodo Data de fim do ciclo
     * @param valorTotal O valor total do ciclo
     * @param valorDesconto O valor dos descontos aplicados aos abastecimentos (campanha)
     * @param valorCobrancaAjustado O valor total da cobrança com os ajustes aplicados
     * @param statusConsolidacao O status do consolidado
     * @param statusPagamento O status de pagamento da cobrança
     * @param quantidadeAbastecimentos A quantidade de abastecimentos na cobrança
     * @param dataVencimento A data de vencimento da cobrança
     * @param statusIntegracao o status da integração da cobrança
     * @param idCobranca o id da cobrança
     * @param ultimaCobrancaFrota Flag informando se a cobrança é a ultima gerada para a frota
     * @param numeroDocumento o número de documento da cobrança
     * @param dataPagamento A data de pagamento da cobrança
     * @param dataLimiteEmissao A data limite para emissão de nota fiscal
     * @param exigeNota Se há exigência de nota para emissão da cobrança
     * @param valorEmitidoNotaFiscal Valor total emitido
     * @param valorTotalNotaFiscal Valor total a ser emitido
     * @param usuarioUltimoAjusteValor Nome do usuário que realizou o ultimo ajuste de valor na cobrança
     * @param dataUltimoAjusteValor Data do ultimo ajuste de valor na cobrança
     * @param usuarioUltimoAjusteVencimento Nome do usuário que realizou o ultimo ajuste na data de vencimento da cobrança
     * @param dataUltimoAjusteVencimento Data do ultimo ajuste na data de vencimento da cobrança
     * @param totalmenteEmitida Indica se todos os ciclos do agrupamento foram totalmente emitidos
     * @param ultimaJustificativaAjuste a Ultima justificativa do ajuste da cobrança
     */
    public AgrupamentoTransacaoConsolidadaCobrancaVo(Long idFrota, String nomeEmpresa, Long cnpjEmpresa, Date dataInicioPeriodo, Date dataFimPeriodo,
                                                     BigDecimal valorTotal, BigDecimal valorDesconto, BigDecimal valorCobrancaAjustado,
                                                     Integer statusConsolidacao, Integer statusPagamento, Long quantidadeAbastecimentos,
                                                     Date dataVencimento, Date dataLimitePagamento, Date dataPagamento,
                                                     Date dataLimiteEmissao, Integer statusIntegracao, String mensagemErroIntegracao,
                                                     Long idCobranca, Boolean ultimaCobrancaFrota, Long numeroDocumento,
                                                     Integer exigeNota, BigDecimal valorEmitidoNotaFiscal, BigDecimal valorTotalNotaFiscal,
                                                     String usuarioUltimoAjusteValor, Date dataUltimoAjusteValor, String usuarioUltimoAjusteVencimento, Date dataUltimoAjusteVencimento, Integer totalmenteEmitida, String ultimaJustificativaAjuste) {
        this.idFrota = idFrota;
        this.nomeEmpresa = nomeEmpresa;
        this.cnpjEmpresa = cnpjEmpresa;
        this.dataInicioPeriodo = dataInicioPeriodo;
        this.dataFimPeriodo = dataFimPeriodo;
        this.valorTotal = valorTotal;
        this.valorDesconto = valorDesconto;
        this.valorCobrancaAjustado = valorCobrancaAjustado;
        this.statusConsolidacao = statusConsolidacao;
        this.statusPagamento = statusPagamento;
        this.quantidadeAbastecimentos = quantidadeAbastecimentos;
        this.dataVencimento = dataVencimento;
        this.dataLimitePagamento = dataLimitePagamento;
        this.dataPagamento = dataPagamento;
        this.dataLimiteEmissao = dataLimiteEmissao;
        this.exigeNota = exigeNota == 1;
        this.valorEmitidoNotaFiscal = valorEmitidoNotaFiscal;
        this.valorTotalNotaFiscal = valorTotalNotaFiscal;
        this.idCobranca = idCobranca;
        this.ultimaCobrancaFrota = ultimaCobrancaFrota != null && ultimaCobrancaFrota;
        this.numeroDocumento = numeroDocumento;
        if(statusIntegracao != null) {
            this.statusIntegracao = new EnumVo(StatusIntegracaoJde.obterPorValor(statusIntegracao));
        }
        this.mensagemErroIntegracao = mensagemErroIntegracao;
        this.usuarioUltimoAjusteValor = usuarioUltimoAjusteValor;
        this.dataUltimoAjusteValor = dataUltimoAjusteValor;
        this.usuarioUltimoAjusteVencimento = usuarioUltimoAjusteVencimento;
        this.dataUltimoAjusteVencimento = dataUltimoAjusteVencimento;
        this.totalmenteEmitida = totalmenteEmitida == 1;
        this.ultimaJustificativaAjuste = ultimaJustificativaAjuste;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public String getNomeEmpresa() {
        return nomeEmpresa;
    }

    public void setNomeEmpresa(String nomeEmpresa) {
        this.nomeEmpresa = nomeEmpresa;
    }

    public Long getCnpjEmpresa() {
        return cnpjEmpresa;
    }

    public void setCnpjEmpresa(Long cnpjEmpresa) {
        this.cnpjEmpresa = cnpjEmpresa;
    }

    public Date getDataInicioPeriodo() {
        return dataInicioPeriodo;
    }

    public void setDataInicioPeriodo(Date dataInicioPeriodo) {
        this.dataInicioPeriodo = dataInicioPeriodo;
    }

    public Date getDataFimPeriodo() {
        return dataFimPeriodo;
    }

    public void setDataFimPeriodo(Date dataFimPeriodo) {
        this.dataFimPeriodo = dataFimPeriodo;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getValorDesconto() {
        return valorDesconto;
    }

    public void setValorDesconto(BigDecimal valorDesconto) {
        this.valorDesconto = valorDesconto;
    }

    public BigDecimal getValorCobrancaAjustado() {
        return valorCobrancaAjustado;
    }

    public void setValorCobrancaAjustado(BigDecimal valorCobrancaAjustado) {
        this.valorCobrancaAjustado = valorCobrancaAjustado;
    }

    public Integer getStatusConsolidacao() {
        return statusConsolidacao;
    }

    public void setStatusConsolidacao(Integer statusConsolidacao) {
        this.statusConsolidacao = statusConsolidacao;
    }

    public Integer getStatusPagamento() {
        return statusPagamento;
    }

    public void setStatusPagamento(Integer statusPagamento) {
        this.statusPagamento = statusPagamento;
    }

    public Long getQuantidadeAbastecimentos() {
        return quantidadeAbastecimentos;
    }

    public void setQuantidadeAbastecimentos(Long quantidadeAbastecimentos) {
        this.quantidadeAbastecimentos = quantidadeAbastecimentos;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public Date getDataLimitePagamento() {
        return dataLimitePagamento;
    }

    public void setDataLimitePagamento(Date dataLimitePagamento) {
        this.dataLimitePagamento = dataLimitePagamento;
    }

    public Boolean getExigeNota() {
        return exigeNota;
    }

    public void setExigeNota(Boolean exigeNota) {
        this.exigeNota = exigeNota;
    }

    public BigDecimal getValorEmitidoNotaFiscal() {
        return valorEmitidoNotaFiscal;
    }

    public void setValorEmitidoNotaFiscal(BigDecimal valorEmitidoNotaFiscal) {
        this.valorEmitidoNotaFiscal = valorEmitidoNotaFiscal;
    }

    public BigDecimal getValorTotalNotaFiscal() {
        return valorTotalNotaFiscal;
    }

    public void setValorTotalNotaFiscal(BigDecimal valorTotalNotaFiscal) {
        this.valorTotalNotaFiscal = valorTotalNotaFiscal;
    }

    public Date getDataPagamento() {
        return dataPagamento;
    }

    public void setDataPagamento(Date dataPagamento) {
        this.dataPagamento = dataPagamento;
    }

    public Date getDataLimiteEmissao() {
        return dataLimiteEmissao;
    }

    public void setDataLimiteEmissao(Date dataLimiteEmissao) {
        this.dataLimiteEmissao = dataLimiteEmissao;
    }

    public EnumVo getStatusIntegracao() {
        return statusIntegracao;
    }

    public void setStatusIntegracao(EnumVo statusIntegracao) {
        this.statusIntegracao = statusIntegracao;
    }

    public String getMensagemErroIntegracao() {
        return mensagemErroIntegracao;
    }

    public void setMensagemErroIntegracao(String mensagemErroIntegracao) {
        this.mensagemErroIntegracao = mensagemErroIntegracao;
    }

    public Long getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(Long idCobranca) {
        this.idCobranca = idCobranca;
    }

    public boolean isUltimaCobrancaFrota() {
        return ultimaCobrancaFrota;
    }

    public void setUltimaCobrancaFrota(boolean ultimaCobrancaFrota) {
        this.ultimaCobrancaFrota = ultimaCobrancaFrota;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getUsuarioUltimoAjusteValor() {
        return usuarioUltimoAjusteValor;
    }

    public void setUsuarioUltimoAjusteValor(String usuarioUltimoAjusteValor) {
        this.usuarioUltimoAjusteValor = usuarioUltimoAjusteValor;
    }

    public Date getDataUltimoAjusteValor() {
        return dataUltimoAjusteValor;
    }

    public void setDataUltimoAjusteValor(Date dataUltimoAjusteValor) {
        this.dataUltimoAjusteValor = dataUltimoAjusteValor;
    }

    public String getUsuarioUltimoAjusteVencimento() {
        return usuarioUltimoAjusteVencimento;
    }

    public void setUsuarioUltimoAjusteVencimento(String usuarioUltimoAjusteVencimento) {
        this.usuarioUltimoAjusteVencimento = usuarioUltimoAjusteVencimento;
    }

    public Date getDataUltimoAjusteVencimento() {
        return dataUltimoAjusteVencimento;
    }

    public void setDataUltimoAjusteVencimento(Date dataUltimoAjusteVencimento) {
        this.dataUltimoAjusteVencimento = dataUltimoAjusteVencimento;
    }

    public Boolean getTotalmenteEmitida() {
        return totalmenteEmitida;
    }

    public void setTotalmenteEmitida(Boolean totalmenteEmitida) {
        this.totalmenteEmitida = totalmenteEmitida;
    }
    
    public String getUltimaJustificativaAjuste() {
        return ultimaJustificativaAjuste;
    }

    public void setUltimaJustificativaAjuste(String ultimaJustificativaAjuste) {
        this.ultimaJustificativaAjuste = ultimaJustificativaAjuste;
    }

    @Transient
    public String getPercentualEmissao() {
        int casasDecimaisValores = 2;
        String percentualEmissao = null;
        if(getValorTotalNotaFiscal().compareTo(BigDecimal.ZERO) > 0){
            percentualEmissao = getValorEmitidoNotaFiscal()
                    .divide(getValorTotalNotaFiscal(), casasDecimaisValores, ROUND_HALF_DOWN)
                    .scaleByPowerOfTen(casasDecimaisValores)
                    .toString();
        }
        return percentualEmissao;
    }
}
