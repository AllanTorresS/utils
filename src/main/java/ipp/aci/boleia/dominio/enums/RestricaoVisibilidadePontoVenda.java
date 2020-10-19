package ipp.aci.boleia.dominio.enums;

import java.util.Arrays;

/**
 * Representa o possíveis restrições de visibilidade de um {@link ipp.aci.boleia.dominio.PontoDeVenda}
 * 
 * @author Rodrigo Salvatore
 */
public enum RestricaoVisibilidadePontoVenda {
    SEM_RESTRICAO(0),
    VISIVEL_APENAS_PARA_FROTAS_COM_VINCULO_ATIVO(1);
    
    private final Integer value;

    RestricaoVisibilidadePontoVenda(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da restrição
     *
     * @return o valor da restrição
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
    public static RestricaoVisibilidadePontoVenda obterPorValor(Integer value) {
        return Arrays.stream(values())
                .filter(s -> s.getValue().equals(value))
                .findAny()
                .orElse(null);
    }
}
