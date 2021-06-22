package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enum que representa possíveis status de vigência de uma
 * solicitação de alteração de status de uma frota
 */
public enum StatusVigenciaAlteracaoStatusFrota implements IEnumComLabel<StatusVigenciaAlteracaoStatusFrota> {
    NAO_VIGENTE(0),
    VIGENTE(1);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    StatusVigenciaAlteracaoStatusFrota(Integer value) {
        this.value = value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusVigenciaAlteracaoStatusFrota obterPorValor(Long value) {
        for (StatusVigenciaAlteracaoStatusFrota status : StatusVigenciaAlteracaoStatusFrota.values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

    /**
     * Obtém uma instância do enum com nome correspondente a string passada.
     *
     * @param nome Nome do enum.
     * @return Enum com o nome correspondente ou nulo.
     */
    public static StatusVigenciaAlteracaoStatusFrota obterPorNome(String nome) {
        for (StatusVigenciaAlteracaoStatusFrota status : StatusVigenciaAlteracaoStatusFrota.values()) {
            if (status.toString().equals(nome)) {
                return status;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }
}
