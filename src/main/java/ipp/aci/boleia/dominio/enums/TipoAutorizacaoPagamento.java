package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import static ipp.aci.boleia.dominio.enums.TipoTransacao.AUTORIZACAO_PAGAMENTO_ACD;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.AUTORIZACAO_PAGAMENTO_CTA_PLUS;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.AUTORIZACAO_PAGAMENTO_MANUAL;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.AUTORIZACAO_PAGAMENTO_PCC;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.AUTORIZACAO_PAGAMENTO_PDV;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.AUTORIZACAO_PAGAMENTO_PDV_WEB;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.AUTORIZACAO_PAGAMENTO_POS;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.AUTORIZACAO_PAGAMENTO_POS_FL;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.ESTORNO_AUTORIZACAO_PAGAMENTO_ACD;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.ESTORNO_AUTORIZACAO_PAGAMENTO_CTA_PLUS;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.ESTORNO_AUTORIZACAO_PAGAMENTO_MANUAL;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.ESTORNO_AUTORIZACAO_PAGAMENTO_PCC;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.ESTORNO_AUTORIZACAO_PAGAMENTO_PDV;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.ESTORNO_AUTORIZACAO_PAGAMENTO_PDV_WEB;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.ESTORNO_AUTORIZACAO_PAGAMENTO_POS;
import static ipp.aci.boleia.dominio.enums.TipoTransacao.ESTORNO_AUTORIZACAO_PAGAMENTO_POS_FL;

/**
 * Enumera os possíveis tipos de uma autorização de pagamento
 */
public enum TipoAutorizacaoPagamento implements IEnumComLabel<TipoAutorizacaoPagamento> {

    PDV(1,ESTORNO_AUTORIZACAO_PAGAMENTO_PDV,AUTORIZACAO_PAGAMENTO_PDV),
    PCC(2,ESTORNO_AUTORIZACAO_PAGAMENTO_PCC,AUTORIZACAO_PAGAMENTO_PCC),
    MAN(3,ESTORNO_AUTORIZACAO_PAGAMENTO_MANUAL,AUTORIZACAO_PAGAMENTO_MANUAL),
    ACPI(4,null,null),
    ACD(5,ESTORNO_AUTORIZACAO_PAGAMENTO_ACD,AUTORIZACAO_PAGAMENTO_ACD),
    PDV_WEB(6,ESTORNO_AUTORIZACAO_PAGAMENTO_PDV_WEB,AUTORIZACAO_PAGAMENTO_PDV_WEB),
    POS(7,ESTORNO_AUTORIZACAO_PAGAMENTO_POS,AUTORIZACAO_PAGAMENTO_POS),
    MAN_PI(8,null,null),
    CTA_PLUS(9,ESTORNO_AUTORIZACAO_PAGAMENTO_CTA_PLUS,AUTORIZACAO_PAGAMENTO_CTA_PLUS),
    POS_FL(10,ESTORNO_AUTORIZACAO_PAGAMENTO_POS_FL, AUTORIZACAO_PAGAMENTO_POS_FL);

    private final Integer value;
    private final TipoTransacao estorno;
    private final TipoTransacao autorizacao;


    TipoAutorizacaoPagamento(Integer value, TipoTransacao estorno, TipoTransacao autorizacao) {
        this.value = value;
        this.estorno = estorno;
        this.autorizacao = autorizacao;
    }

    public TipoTransacao getEstorno() {
        return estorno;
    }

    public TipoTransacao getAutorizacao() {
        return autorizacao;
    }

    /**
     * Obtem o valor da eum
     * @return O valor da enum
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoAutorizacaoPagamento obterPorValor(Integer value) {
        for(TipoAutorizacaoPagamento tipoFormaPagamento : TipoAutorizacaoPagamento.values()) {
            if(tipoFormaPagamento.value.equals(value)) {
                return tipoFormaPagamento;
            }
        }
        return null;
    }
}
