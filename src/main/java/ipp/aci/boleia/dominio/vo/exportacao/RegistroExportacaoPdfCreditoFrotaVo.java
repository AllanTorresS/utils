package ipp.aci.boleia.dominio.vo.exportacao;

/**
 * Representa um registro exibido na linha do relatório de PDF.
 */
public class RegistroExportacaoPdfCreditoFrotaVo extends RegistroExportacaoPdfBaseVo {
    private String frotaNome;
    private String frotaCnpj;
    private String modalidadeDePagamento;
    private String cidade;
    private String uf;
    private String assessorResponsavel;
    private String volumeDiesel;
    private String volumeOtto;
    private String cicloAbastecimento;
    private String prazoPagamento;
    private String status;
    private String limiteCredito;
    private String saldo;

    public RegistroExportacaoPdfCreditoFrotaVo(){
        // Serialização
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

    public String getModalidadeDePagamento() {
        return modalidadeDePagamento;
    }

    public void setModalidadeDePagamento(String modalidadeDePagamento) {
        this.modalidadeDePagamento = modalidadeDePagamento;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getUf() {
        return uf;
    }

    public void setUf(String uf) {
        this.uf = uf;
    }

    public String getAssessorResponsavel() {
        return assessorResponsavel;
    }

    public void setAssessorResponsavel(String assessorResponsavel) {
        this.assessorResponsavel = assessorResponsavel;
    }

    public String getVolumeDiesel() {
        return volumeDiesel;
    }

    public void setVolumeDiesel(String volumeDiesel) {
        this.volumeDiesel = volumeDiesel;
    }

    public String getVolumeOtto() {
        return volumeOtto;
    }

    public void setVolumeOtto(String volumeOtto) {
        this.volumeOtto = volumeOtto;
    }

    public String getCicloAbastecimento() {
        return cicloAbastecimento;
    }

    public void setCicloAbastecimento(String cicloAbastecimento) {
        this.cicloAbastecimento = cicloAbastecimento;
    }

    public String getPrazoPagamento() {
        return prazoPagamento;
    }

    public void setPrazoPagamento(String prazoPagamento) {
        this.prazoPagamento = prazoPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(String limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public String getSaldo() {
        return saldo;
    }

    public void setSaldo(String saldo) {
        this.saldo = saldo;
    }
}
