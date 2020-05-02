package ipp.aci.boleia.dominio.enums;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Enumera todos os possíveis tipos de transações monetárias do sistema
 */
public enum TipoTransacao {

    AUTORIZACAO_PAGAMENTO_PDV               (1,   true,     OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    AUTORIZACAO_PAGAMENTO_PCC               (2,   true,     OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    CONCESSAO_CREDITO_HABILITACAO_FROTA     (3,   false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    PAGAMENTO_CICLO                         (4,   false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    AUMENTO_SALDO_LIMITE_FROTA              (5,   false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    AUTORIZACAO_PAGAMENTO_MANUAL            (6,   true,     OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    AUTORIZACAO_PAGAMENTO_ACD               (7,   true,     OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    COMPRA_CREDITO_PREPAGO_FROTA            (8,   false,    OperacaoDeCredito.COMPRA_DE_CREDITO_PRE_PAGO),
    EXPIRACAO_CREDITO_PREPAGO_FROTA         (9,   true,     OperacaoDeCredito.CREDITO_PRE_PAGO_EXPIRADO),
    AUTORIZACAO_PAGAMENTO_POS               (10,  true,     OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    AUTORIZACAO_PAGAMENTO_CTA_PLUS          (11,  true,     OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    AUTORIZACAO_PAGAMENTO_POS_FL            (12, true,      OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    ESTORNO_AUTORIZACAO_PAGAMENTO_PDV       (101, false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    ESTORNO_AUTORIZACAO_PAGAMENTO_PCC       (102, false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    ESTORNO_AUTORIZACAO_PAGAMENTO_MANUAL    (103, false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    ESTORNO_AUTORIZACAO_PAGAMENTO_ACD       (104, false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    REDUCAO_SALDO_LIMITE_FROTA              (105, true,     OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    AUTORIZACAO_PAGAMENTO_PDV_WEB           (106, true,     OperacaoDeCredito.USO_DE_CREDITO_PRE_PAGO),
    ESTORNO_AUTORIZACAO_PAGAMENTO_PDV_WEB   (107, false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    ESTORNO_AUTORIZACAO_PAGAMENTO_POS       (108, false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    ESTORNO_AUTORIZACAO_PAGAMENTO_CTA_PLUS  (109, false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO),
    ESTORNO_AUTORIZACAO_PAGAMENTO_POS_FL    (110, false,    OperacaoDeCredito.CONCESSAO_CREDITO_PRE_PAGO);


    private final Integer value;
    private final boolean debito;
    private final OperacaoDeCredito tipoOperacao;

    TipoTransacao(Integer value, boolean debito, OperacaoDeCredito tipoOperacao) {
        this.value = value;
        this.debito = debito;
        this.tipoOperacao = tipoOperacao;
    }

    public Integer getValue() {
        return this.value;
    }

    public boolean isDebito() {
        return this.debito;
    }

    public OperacaoDeCredito getTipoOperacao() {
        return tipoOperacao;
    }

    /**
     * Dada um tipo de transacao, retornao o tipo de estorno correspondente
     * caso exista, ou NULL caso contratio.
     *
     * @return A transacao de estorno correspondente, caso exista
     */
    public TipoTransacao obterTipoEstorno() {
        if (this.equals(AUTORIZACAO_PAGAMENTO_PCC)) {
            return ESTORNO_AUTORIZACAO_PAGAMENTO_PCC;
        }
        if (this.equals(AUTORIZACAO_PAGAMENTO_PDV)) {
            return ESTORNO_AUTORIZACAO_PAGAMENTO_PDV;
        }
        if (this.equals(AUTORIZACAO_PAGAMENTO_MANUAL)) {
            return ESTORNO_AUTORIZACAO_PAGAMENTO_MANUAL;
        }
        if (this.equals(AUTORIZACAO_PAGAMENTO_ACD)) {
            return ESTORNO_AUTORIZACAO_PAGAMENTO_ACD;
        }
        if (this.equals(AUTORIZACAO_PAGAMENTO_PDV_WEB)) {
            return ESTORNO_AUTORIZACAO_PAGAMENTO_PDV_WEB;
        }
        if(this.equals(AUTORIZACAO_PAGAMENTO_POS)){
            return ESTORNO_AUTORIZACAO_PAGAMENTO_POS;
        }
        if(this.equals(AUTORIZACAO_PAGAMENTO_CTA_PLUS)){
            return ESTORNO_AUTORIZACAO_PAGAMENTO_CTA_PLUS;
        }
        if(this.equals(AUTORIZACAO_PAGAMENTO_POS_FL)){
            return ESTORNO_AUTORIZACAO_PAGAMENTO_POS_FL;
        }
        return null;
    }

    /**
     * Obtem por valor
     * @param value o valor numerico da enumeracao
     * @return Enum para o valor
     */
    public static TipoTransacao obterPorValor(Integer value) {
        for(TipoTransacao tipo : TipoTransacao.values()) {
            if(tipo.value.equals(value)) {
                return tipo;
            }
        }
        return null;
    }

    /**
     * Obtem os tipos de transacao possiveis para um dado tipo de operacao de credito
     * @param operacaoDeCredito selecionada
     * @return tipos de transacao da operacao
     */
    public static List<TipoTransacao> obterPorTipoOperacao(OperacaoDeCredito operacaoDeCredito) {
        return Arrays.asList(TipoTransacao.values()).stream().filter(t-> t.getTipoOperacao() != null && t.getTipoOperacao().equals(operacaoDeCredito)).collect(Collectors.toList());
    }

}
