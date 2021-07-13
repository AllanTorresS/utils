package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica os motivos de clonagem de um perfil
 */
public enum MotivoClonagem implements IEnumComLabel<MotivoClonagem>, IEnumComValor {

    FALTA(0),
    LICENCA_MEDICA(1),
    LICENCA_MATERNIDADE(2),
    FERIAS(3),
    SUSPENSAO(4),
    PERFIL_TEMPORARIO(5),
    PERFIL_DEFINITIVO(6),
    OUTROS(7);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    MotivoClonagem(Integer value) {
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
    public static MotivoClonagem obterPorValor(Integer value) {
        for(MotivoClonagem status : MotivoClonagem.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
