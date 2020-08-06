package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Lista os campos de abastecimento disponiveis para edição
 */
public enum CampoEdicaoAbastecimento implements IEnumComLabel<CampoEdicaoAbastecimento> {

    PONTO_VENDA,
    CODIGO_VIP,
    PLACA_VEICULO,
    FROTA,
    CPF_MOTORISTA,
    COMBUSTIVEL,
    QUANTIDADE,
    LITROS,
    VALOR_UNITARIO,
    VALOR_TOTAL;
}
