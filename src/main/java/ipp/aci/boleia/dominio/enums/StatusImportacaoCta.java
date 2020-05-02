package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status da integração de um abastecimento obtido da integração com o Ipiranga Connect(CTA)
 */
public enum StatusImportacaoCta implements IEnumComLabel<StatusImportacaoCta> {

    NAO_IMPORTADO(0),
    IMPORTADO(1),
    REJEITADO(2),
    ERRO(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusImportacaoCta(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }
}
