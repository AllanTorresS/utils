package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica a classificação de motivo de ativação ou inativação de uma frota
 */
public enum ClassificacaoStatusFrota implements IEnumComLabel<ClassificacaoStatusFrota> {

    OUTROS(-1),
    SUSPEITA_DE_FRAUDE(0),
    INADIMPLENCIA(1),
    SUSPENSAO_DE_ATIVIDADE(2),
    DEBITO_VENCIDO(3);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    ClassificacaoStatusFrota(Integer value) {
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
    public static ClassificacaoStatusFrota obterPorValor(Integer value) {
        for(ClassificacaoStatusFrota status : ClassificacaoStatusFrota.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
