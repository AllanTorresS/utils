package ipp.aci.boleia.dominio.vo.exportacao;

/**
 * Representa um ciclo exibido na exportação em PDF do financeiro.
 */
public class RegistroExportacaoPdfCicloFinanceiroVo extends RegistroExportacaoPdfBaseVo {

    public String periodo;
    public String cicloFrota;
    public String frotaNome;
    public String frotaCnpj;
    public String postoNome;
    public String postoCnpj;
    public String faturamento;
    public String reembolso;
    public String taxa;
    public String numTransacoes;
    public String prazoReembolso;
    public String statusReembolso;
    public String prazoNotaFiscal;
    public String statusNotaFiscal;
    public String totalEmitido;
    public String totalNf;
    public Integer percentualEmitido;
    public Boolean atrasada;
    public Boolean exigeNf;
    public String observacao;

    public RegistroExportacaoPdfCicloFinanceiroVo(){
        // Serialização
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

    public String getNumTransacoes() {
        return numTransacoes;
    }

    public void setNumTransacoes(String numTransacoes) {
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
