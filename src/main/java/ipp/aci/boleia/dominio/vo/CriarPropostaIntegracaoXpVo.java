package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Corpo da requisição para solicitar criação de uma proposta de empréstimo
 */
public class CriarPropostaIntegracaoXpVo {

    private String tableCode;
    private BigDecimal requestedAmount;
    private Integer numberOfInstallments;
    private String firstDueDate;
    private String externalId;
    private String disbursementDate;

    /**
     * Construtor padrão
     */
    public CriarPropostaIntegracaoXpVo() {}

    public String getTableCode() {
        return tableCode;
    }

    public void setTableCode(String tableCode) {
        this.tableCode = tableCode;
    }

    public BigDecimal getRequestedAmount() {
        return requestedAmount;
    }

    public void setRequestedAmount(BigDecimal requestedAmount) {
        this.requestedAmount = requestedAmount;
    }

    public Integer getNumberOfInstallments() {
        return numberOfInstallments;
    }

    public void setNumberOfInstallments(Integer numberOfInstallments) {
        this.numberOfInstallments = numberOfInstallments;
    }

    public String getFirstDueDate() {
        return firstDueDate;
    }

    public void setFirstDueDate(String firstDueDate) {
        this.firstDueDate = firstDueDate;
    }

    public String getExternalId() {
        return externalId;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public String getDisbursementDate() {
        return disbursementDate;
    }

    public void setDisbursementDate(String disbursementDate) {
        this.disbursementDate = disbursementDate;
    }
}
