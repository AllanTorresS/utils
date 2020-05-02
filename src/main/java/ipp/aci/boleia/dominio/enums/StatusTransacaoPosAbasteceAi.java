package ipp.aci.boleia.dominio.enums;

import java.util.Arrays;

/**
 * Enum que representa os status possiveis de uma transacao POS/Abastece Aí
 */
public enum StatusTransacaoPosAbasteceAi {
    AUTORIZADA(1, true),
    CONFIRMACAO(2, true),
    DESFEITA(3, false);


    private final Integer value;
    private final Boolean consideraAutorizada;

    /**
     * Construtor
     * @param value identificador do status
     * @param consideraAutorizada se o status torna a transacao autorizada ou nao
     */
    StatusTransacaoPosAbasteceAi(Integer value, Boolean consideraAutorizada) {
        this.value = value;
        this.consideraAutorizada = consideraAutorizada;
    }

    /**
     * Obtem por nome
     *
     * @param nome value
     * @return Enum para o nome
     */
    public static StatusTransacaoPosAbasteceAi obterPorNome(String nome) {
        return Arrays.stream(values())
                .filter(e -> e.name().equals(nome))
                .findAny()
                .orElse(null);
    }

    /**
     * Verifica se o status da transacao deve considerar a transacao como autorizada baseada no status
     * @param valor o codigo do status
     * @return se o status considera a transacao autorizada ou nao
     */
    public static Boolean deveConsiderarAutorizada(Integer valor) {
        StatusTransacaoPosAbasteceAi transacao = Arrays.stream(values())
                .filter(e -> e.getValue().equals(valor))
                .findAny().orElse(null);
        return transacao != null ? transacao.getConsideraAutorizada() : false;
    }

    public Integer getValue() {
        return value;
    }

    public Boolean getConsideraAutorizada() {
        return consideraAutorizada;
    }
}
