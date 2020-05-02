package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

public enum StatusConciliacaoNotaFiscal implements IEnumComLabel<StatusConciliacaoNotaFiscal> {
    UPLOAD(0),
    IMPORTADO(1),
    CONCILIADO_AUTOMATICAMENTE(2),
    NAO_CONCILIADO(3),
    CONCILIACAO_MANUAL(4);

    private final Integer value;

    /**
     * Construtor padrão
     * @param value O valor do status
     */
    StatusConciliacaoNotaFiscal(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusConciliacaoNotaFiscal obterPorValor(Integer value) {
        for(StatusConciliacaoNotaFiscal status : StatusConciliacaoNotaFiscal.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
