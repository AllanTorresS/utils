package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica
 */
public enum MotivoPermissaoTemporaria implements IEnumComLabel<MotivoPermissaoTemporaria> {

    FALTA(0, true),
    LICENCA_MEDICA(1, true),
    LICENCA_MATERNIDADE(2, true),
    FERIAS(3, true),
    SUSPENSAO(4, true),
    PERFIL_TEMPORARIO(5 ,true),
    PERFIL_DEFINITIVO(6, true),
    OUTROS(7, true);

    private final Integer value;
    private final Boolean semAlteracao;

    /**
     * Construtor
     *
     * @param value O value do status
     * @param  semAlteracao verifica se existe alteracao
     */
    MotivoPermissaoTemporaria(Integer value, Boolean semAlteracao) {
        this.value = value;
        this.semAlteracao = semAlteracao;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getSemAlteracao() {
        return semAlteracao;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static MotivoPermissaoTemporaria obterPorValor(Integer value) {
        for(MotivoPermissaoTemporaria status : MotivoPermissaoTemporaria.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
