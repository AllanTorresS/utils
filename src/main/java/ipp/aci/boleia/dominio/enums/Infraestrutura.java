package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Enumera os possíveis tipos de infraestrutura dos postos
 */
public enum Infraestrutura implements IEnumComLabel<Infraestrutura> {
    CONVENIENCIA(1, 1L),
    AM_PM(2, 3L),
    RESTAURANTE(3, 5L),
    BANHEIRO(4, 9L),
    INTERNET(5, 34L),
    WIFI(6, 36L),
    ESTACIONAMENTO(7, 13L),
    ESTACIONAMENTO_ABERTO(8, 15L),
    ESTACIONAMENTO_FECHADO(9, 16L),
    TROCA_OLEO(10, 26L),
    TROCA_OLEO_GRANEL(11, 28L),
    ARLA_32_GRANEL(12, 33L),
    ARLA_32_BALDE(13, 32L);

    private final Integer value;
    private final Long idOpcaoCorrespondente;

    /**
     * Construtor
     * @param value o valor do enum
     * @param idOpcaoCorrespondente
     */
    Infraestrutura(Integer value, Long idOpcaoCorrespondente) {
        this.value = value;
        this.idOpcaoCorrespondente = idOpcaoCorrespondente;
    }

    /**
     * Obtém o valor do enum
     * @return o valor do enum
     */
    public Integer getValue() {
        return value;
    }


    /**
     * Obtém o identificador da resposta da {@link ipp.aci.boleia.dominio.QuestionarioPerguntaOpcao}
     * da {@link ipp.aci.boleia.dominio.QuestionarioPergunta} correspondete ao item do enum
     *
     * @return O identificador da opção correspondente
     */
    public Long getIdOpcaoCorrespondente() {
        return idOpcaoCorrespondente;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static Infraestrutura obterPorValor(Integer value) {
        return Arrays.stream(Infraestrutura.values())
                .filter(i -> i.getValue().equals(value))
                .findFirst().orElse(null);
    }

    /**
     * Obtém o enumerado referente ao parâmetro fornecido
     *
     * @param nome nome da infraestrutura
     * @return enumerado ou nulo
     */
    public static Infraestrutura obterPorNome(String nome) {
        return Arrays.stream(Infraestrutura.values())
                .filter(t -> t.name().equalsIgnoreCase(nome))
                .findFirst().orElse(null);
    }
}
