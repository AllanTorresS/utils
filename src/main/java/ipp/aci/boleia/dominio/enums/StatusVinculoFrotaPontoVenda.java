package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;
import java.util.Arrays;

/**
 * Representa o possível status de um vinculo entre uma frota {@link ipp.aci.boleia.dominio.Frota} e um {@link ipp.aci.boleia.dominio.PontoDeVenda}
 * 
 * @author Rodrigo Salvatore
 */
public enum StatusVinculoFrotaPontoVenda implements IEnumComLabel<StatusBloqueio> {
    INATIVO(0),
    ATIVO(1);

    private final Integer value;

    StatusVinculoFrotaPontoVenda(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor do status
     *
     * @return o valor do status
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusVinculoFrotaPontoVenda obterPorValor(Integer value) {
        return Arrays.stream(values())
                .filter(s -> s.getValue().equals(value))
                .findAny()
                .orElse(null);
    }
}
