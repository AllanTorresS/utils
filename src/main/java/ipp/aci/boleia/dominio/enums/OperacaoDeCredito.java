package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Representa os tipos de operação possíveis com créditos pré-pagos
 */
public enum OperacaoDeCredito implements IEnumComLabel<OperacaoDeCredito> {

    CONCESSAO_CREDITO_PRE_PAGO,
    USO_DE_CREDITO_PRE_PAGO,
    COMPRA_DE_CREDITO_PRE_PAGO,
    CREDITO_PRE_PAGO_EXPIRADO;

}
