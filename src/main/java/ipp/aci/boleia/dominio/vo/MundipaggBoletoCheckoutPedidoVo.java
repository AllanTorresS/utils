package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.util.Date;

/**
 * Classe com informacoes do boleto Mundipagg
 */
public class MundipaggBoletoCheckoutPedidoVo {

    private static final String CODIGO_ITAU_BANK = "341";
    private static final String LABEL_INSTRUCTION = "Pagar até o vencimento";

    private String bank;

    @JsonProperty("due_at")
    private String dueAt;

    private  String instructions;

    public MundipaggBoletoCheckoutPedidoVo() {

    }

    /**
     * Construtor do boleto Mundipagg
     * @param vencimentoBoleto A data de vencimento do boleto
     */
    public MundipaggBoletoCheckoutPedidoVo(Date vencimentoBoleto) {
        dueAt = UtilitarioFormatacaoData.formatarDataIso8601SemTimezone(vencimentoBoleto);
        bank = CODIGO_ITAU_BANK;
        instructions = LABEL_INSTRUCTION;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getDueAt() {
        return dueAt;
    }

    public void setDueAt(String dueAt) {
        this.dueAt = dueAt;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
 