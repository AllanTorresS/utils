package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de uma inclusão de abastecimento sem senha ou codigo
 */
public enum MotivoAbastecimentoSemSenhaOuCodigo implements IEnumComLabel<MotivoAbastecimentoSemSenhaOuCodigo> {

    PDV_SEM_INTERNET(1),
    SEM_CELULAR(2),
    SEM_CODIGO_ABASTECIMENTO(3),
    SEM_SENHA_CONTIGENCIA(4),
    NAO_RECEBE_SMS(5),
    SEM_APLICATIVO(6);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do motivo
     */
    MotivoAbastecimentoSemSenhaOuCodigo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
