package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de clonagem de um perfil
 */
public enum MotivoClonagem implements IEnumComLabel<MotivoClonagem>, IEnumComValor {

    FALTA(0, false),
    LICENCA_MEDICA(1, true),
    LICENCA_MATERNIDADE(2, true),
    FERIAS(3, true),
    SUSPENSAO(4, false),
    PERFIL_TEMPORARIO(5, false),
    PERFIL_DEFINITIVO(6, false),
    OUTROS(7, true);

    private final Integer value;
    private final Boolean validoInterno;

    /**
     * Construtor
     *
     * @param value O value do status
     * @param validoInterno Indica se o motivo é válido para usuário interno
     */
    MotivoClonagem(Integer value, Boolean validoInterno) {
        this.value = value;
        this.validoInterno = validoInterno;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getValidoInterno() {
        return validoInterno;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static MotivoClonagem obterPorValor(Integer value) {
        for(MotivoClonagem status : MotivoClonagem.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
