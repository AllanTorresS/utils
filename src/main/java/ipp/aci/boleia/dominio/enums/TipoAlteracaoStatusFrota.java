package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enum que representa possíveis tipos de alteração de status de uma frota
 */
public enum TipoAlteracaoStatusFrota implements IEnumComLabel<TipoAlteracaoStatusFrota> {
    INATIVACAO(0),
    ATIVACAO(1);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoAlteracaoStatusFrota(Integer value) {
        this.value = value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static TipoAlteracaoStatusFrota obterPorValor(Long value) {
        for (TipoAlteracaoStatusFrota tipo : TipoAlteracaoStatusFrota.values()) {
            if (tipo.value.equals(value)) {
                return tipo;
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
    public static TipoAlteracaoStatusFrota obterPorNome(String nome) {
        for (TipoAlteracaoStatusFrota tipo : TipoAlteracaoStatusFrota.values()) {
            if (tipo.toString().equals(nome)) {
                return tipo;
            }
        }
        return null;
    }

    public Integer getValue() {
        return value;
    }
}
