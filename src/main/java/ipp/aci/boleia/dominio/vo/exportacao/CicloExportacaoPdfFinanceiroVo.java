package ipp.aci.boleia.dominio.vo.exportacao;

import ipp.aci.boleia.dominio.TransacaoConsolidada;
import ipp.aci.boleia.dominio.enums.StatusPagamentoReembolso;
import ipp.aci.boleia.dominio.enums.StatusTransacaoConsolidada;
import ipp.aci.boleia.util.UtilitarioCalculoData;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.math.BigDecimal;
import java.util.Date;

import static ipp.aci.boleia.util.UtilitarioLambda.verificarTodosNaoNulos;

/**
 * Representa um ciclo exibido na exportação em PDF do financeiro.
 */
public class CicloExportacaoPdfFinanceiroVo {

    private String periodo;
    private String cicloFrota;
    private String frotaNome;
    private String frotaCnpj;
    private String postoNome;
    private String postoCnpj;
    private String faturamento;
    private String reembolso;
    private String taxa;
    private Integer numTransacoes;
    private String prazoReembolso;
    private String statusReembolso;
    private String prazoNotaFiscal;
    private String statusNotaFiscal;
    private String totalEmitido;
    private String totalNf;
    private Integer percentualEmitido;
    private Boolean atrasada;
    private Boolean exigeNf;
    private String observacao;

    public CicloExportacaoPdfFinanceiroVo(){
        // Serialização
    }

    /** Construtor
     *
     * @param consolidado A transacao consolidada do ciclo
     * @param dataAtual a data no momento que a requisição foi feita.
     * @param observacao Mensagem para o parametro observação.
     */
    public CicloExportacaoPdfFinanceiroVo(TransacaoConsolidada consolidado, Date dataAtual, String observacao, boolean cicloTemApenasTransacoesNegativas){
        BigDecimal valorEmitidoNf = null;
        BigDecimal valorTotalNf = null;
        this.setTotalEmitido("-");
        this.setExigeNf(consolidado.getFrota().exigeNotaFiscal());
        if (verificarTodosNaoNulos(consolidado.getValorTotalNotaFiscal(), consolidado.getValorEmitidoNotaFiscal())
                && consolidado.getValorTotalNotaFiscal().compareTo(BigDecimal.ZERO) != 0) {
            valorEmitidoNf = consolidado.getValorEmitidoNotaFiscal();
            valorTotalNf = consolidado.getValorTotalNotaFiscal();
            this.setTotalEmitido(UtilitarioFormatacao.formatarDecimalMoedaReal(consolidado.getValorEmitidoNotaFiscal(), 2).replace(" ",""));
            this.setTotalNf(UtilitarioFormatacao.formatarDecimalMoedaReal(consolidado.getValorTotalNotaFiscal(), 2).replace(" ",""));

        }

        Integer percentualEmitido = 0;
        if(valorEmitidoNf != null && valorTotalNf != null){
            percentualEmitido = valorEmitidoNf.divide(valorTotalNf, 2, BigDecimal.ROUND_HALF_DOWN).multiply(new BigDecimal(100)).intValue();

        } else{
            percentualEmitido = null;
            this.setExigeNf(true);
        }

        String faturamento = consolidado.getValorFaturamento() != null ?
                UtilitarioFormatacao.formatarDecimalMoedaReal(consolidado.getValorFaturamento(), 2).replace(" ","") : "-";
        String taxa = consolidado.getValorDesconto() != null ?
                UtilitarioFormatacao.formatarDecimalMoedaReal(consolidado.getValorDesconto(), 2).replace(" ","") : "-";
        String reembolso =  consolidado.getValorReembolso() != null ?
                UtilitarioFormatacao.formatarDecimalMoedaReal(consolidado.getValorReembolso(), 2).replace(" ","") : "-";


        this.setPeriodo(UtilitarioFormatacaoData.formatarPeriodoDiasMes(consolidado.getDataInicioPeriodo(), consolidado.getDataFimPeriodo(), true));
        this.setCicloFrota((UtilitarioCalculoData.diferencaEmDias(consolidado.getDataInicioPeriodo(), consolidado.getDataFimPeriodo()) + 1) + "+" + consolidado.getPrazos().getPrazoPgto());
        this.setFrotaNome(consolidado.getFrota().getRazaoSocial());
        this.setFrotaCnpj(UtilitarioFormatacao.formatarCnpjApresentacao(consolidado.getFrota().getCnpj()));
        if(consolidado.getEmpresaAgregada() != null){
            this.setFrotaNome(consolidado.getEmpresaAgregada().getRazaoSocial());
            this.setFrotaCnpj(UtilitarioFormatacao.formatarCnpjApresentacao(consolidado.getEmpresaAgregada().getCnpj()));
            this.setExigeNf(consolidado.getEmpresaAgregada().getExigeNotaFiscal());
        } else if (consolidado.getUnidade() != null){
            this.setFrotaNome(consolidado.getUnidade().getNome());
            this.setFrotaCnpj(UtilitarioFormatacao.formatarCnpjApresentacao(consolidado.getUnidade().getCnpj()));
            this.setExigeNf(consolidado.getUnidade().getExigeNotaFiscal());
        }

        this.setPostoNome(consolidado.getPontoVenda().getNome());
        this.setPostoCnpj(UtilitarioFormatacao.formatarCnpjApresentacao(consolidado.getPontoVenda().getCnpj()));
        this.setFaturamento(faturamento);
        this.setTaxa(taxa);
        this.setReembolso(reembolso);
        this.setNumTransacoes(consolidado.getQuantidadeAbastecimentos().intValue());
        this.setPrazoReembolso(UtilitarioFormatacaoData.formatarDataCurta(
                UtilitarioCalculoData.adicionarDiasData(consolidado.getDataFimPeriodo(), consolidado.getPrazos().getPrazoReembolso().intValue())));
        this.setPrazoNotaFiscal(UtilitarioFormatacaoData.formatarDataCurta(consolidado.getPrazos().getDataLimiteEmissaoNfe()));
        if(percentualEmitido != null){
            if(consolidado.getStatusConsolidacao().equals(StatusTransacaoConsolidada.FECHADA.getValue()) && consolidado.pendenteNotaFiscal()){
                if(percentualEmitido > 0){
                    this.setStatusNotaFiscal(StatusTransacaoConsolidada.PARCIALMENTE_EMITIDA.getLabel());
                } else{
                    this.setStatusNotaFiscal(cicloTemApenasTransacoesNegativas ? "-" : StatusTransacaoConsolidada.SEM_EMISSAO.getLabel());
                }
            } else{
                this.setStatusNotaFiscal(consolidado.obterStatusNotaFiscal(dataAtual).getLabel());
            }
        } else{
            this.setStatusNotaFiscal("-");
        }
        this.setPercentualEmitido(percentualEmitido);
        if(consolidado.getReembolso() != null) {
            Integer statusReembolso = consolidado.getReembolso().getStatus();
            this.setPrazoReembolso(UtilitarioFormatacaoData.formatarDataCurta(consolidado.getReembolso().getDataVencimentoPgto()));
            if (statusReembolso.equals(StatusPagamentoReembolso.EM_ABERTO.getValue()) ||
                    statusReembolso.equals(StatusPagamentoReembolso.AGUARDANDO_NF.getValue()) ||
                    statusReembolso.equals(StatusPagamentoReembolso.NF_ATRASADA.getValue())) {
                this.setStatusReembolso(StatusPagamentoReembolso.PREVISTO.getLabel());
            } else {
                this.setStatusReembolso(StatusPagamentoReembolso.obterPorValor(consolidado.getReembolso().getStatus()).getLabel());
            }
        } else {
            this.setStatusReembolso(StatusPagamentoReembolso.PREVISTO.getLabel());
            this.setPrazoReembolso(UtilitarioFormatacaoData.formatarDataCurta(UtilitarioCalculoData.adicionarDiasData(consolidado.getDataFimPeriodo(),consolidado.getPrazos().getPrazoReembolso().intValue())));
        }
        this.setObservacao(observacao);
        this.setAtrasada(consolidado.getReembolso() != null && (consolidado.getReembolso().getStatus().equals(StatusPagamentoReembolso.NF_ATRASADA.getValue()) ||
                consolidado.getReembolso().getStatus().equals(StatusPagamentoReembolso.ATRASADO.getValue())));
    }

    public String getPeriodo() {
        return periodo;
    }

    public void setPeriodo(String periodo) {
        this.periodo = periodo;
    }

    public String getCicloFrota() {
        return cicloFrota;
    }

    public void setCicloFrota(String cicloFrota) {
        this.cicloFrota = cicloFrota;
    }

    public String getFrotaNome() {
        return frotaNome;
    }

    public void setFrotaNome(String frotaNome) {
        this.frotaNome = frotaNome;
    }

    public String getFrotaCnpj() {
        return frotaCnpj;
    }

    public void setFrotaCnpj(String frotaCnpj) {
        this.frotaCnpj = frotaCnpj;
    }

    public String getPostoNome() {
        return postoNome;
    }

    public void setPostoNome(String postoNome) {
        this.postoNome = postoNome;
    }

    public String getPostoCnpj() {
        return postoCnpj;
    }

    public void setPostoCnpj(String postoCnpj) {
        this.postoCnpj = postoCnpj;
    }

    public String getFaturamento() {
        return faturamento;
    }

    public void setFaturamento(String faturamento) {
        this.faturamento = faturamento;
    }

    public String getReembolso() {
        return reembolso;
    }

    public void setReembolso(String reembolso) {
        this.reembolso = reembolso;
    }

    public String getTaxa() {
        return taxa;
    }

    public void setTaxa(String taxa) {
        this.taxa = taxa;
    }

    public Integer getNumTransacoes() {
        return numTransacoes;
    }

    public void setNumTransacoes(Integer numTransacoes) {
        this.numTransacoes = numTransacoes;
    }

    public String getPrazoReembolso() {
        return prazoReembolso;
    }

    public void setPrazoReembolso(String prazoReembolso) {
        this.prazoReembolso = prazoReembolso;
    }

    public String getStatusReembolso() {
        return statusReembolso;
    }

    public void setStatusReembolso(String statusReembolso) {
        this.statusReembolso = statusReembolso;
    }

    public String getPrazoNotaFiscal() {
        return prazoNotaFiscal;
    }

    public void setPrazoNotaFiscal(String prazoNotaFiscal) {
        this.prazoNotaFiscal = prazoNotaFiscal;
    }

    public String getStatusNotaFiscal() {
        return statusNotaFiscal;
    }

    public void setStatusNotaFiscal(String statusNotaFiscal) {
        this.statusNotaFiscal = statusNotaFiscal;
    }

    public String getTotalEmitido() {
        return totalEmitido;
    }

    public void setTotalEmitido(String totalEmitido) {
        this.totalEmitido = totalEmitido;
    }

    public String getTotalNf() {
        return totalNf;
    }

    public void setTotalNf(String totalNf) {
        this.totalNf = totalNf;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Integer getPercentualEmitido() {
        return percentualEmitido;
    }

    public void setPercentualEmitido(Integer percentualEmitido) {
        this.percentualEmitido = percentualEmitido;
    }

    public Boolean getAtrasada() {
        return atrasada;
    }

    public void setAtrasada(Boolean atrasada) {
        this.atrasada = atrasada;
    }

    public Boolean getExigeNf() {
        return exigeNf;
    }

    public void setExigeNf(Boolean exigeNf) {
        this.exigeNf = exigeNf;
    }
}
