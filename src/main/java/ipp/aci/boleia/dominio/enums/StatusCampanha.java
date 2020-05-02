package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

import java.util.Arrays;

/**
 * Indica o status de um Campanha
 */
public enum StatusCampanha implements IEnumComLabel<StatusCampanha> {
    ENCERRADO(0),
    ATIVO(1),
    RASCUNHO(2),
    AGUARDANDO_APROVACAO(3),
    AGENDADO(4);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusCampanha(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     *
     * @param value value
     * @return Enum para o valor
     */
    public static StatusCampanha obterPorValor(Integer value) {
        return Arrays.stream(StatusCampanha.values())
                .filter(s -> s.getValue().equals(value))
                .findFirst().orElse(null);
    }

    /**
     * Obtem por nome
     *
     * @param nome value
     * @return Enum para o nome
     */
    public static StatusCampanha obterPorNome(String nome) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(nome))
                .findAny()
                .orElse(null);
    }
}
