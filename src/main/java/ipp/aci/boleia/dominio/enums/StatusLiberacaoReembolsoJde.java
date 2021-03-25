package ipp.aci.boleia.dominio.enums;

import java.util.stream.Stream;

/**
 * Status de liberação para pagamento de um reembolso no JDE.
 *
 * @author pedro.silva
 */
public enum StatusLiberacaoReembolsoJde {

    SUSPENSO_PAGAMENTO(0),
    APROVADO_PAGAMENTO(1);

    private Integer value;

    /**
     * Construtor privado do enum.
     *
     * @param value Valor do enum
     */
    StatusLiberacaoReembolsoJde(Integer value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    /**
     * Informa se o valor do enum é igual a APROVADO_PAGAMENTO.
     *
     * @return True, caso seja igual a APROVADO_PAGAMENTO.
     */
    public boolean isAprovadoPagamento() {
        return equals(APROVADO_PAGAMENTO);
    }

    /**
     * Retorna uma instância do enum a partir do valor.
     *
     * @param valor Valor utilizado na busca
     * @return Instância do enum encontrado ou null.
     */
    public static StatusLiberacaoReembolsoJde obterPorValor(int valor) {
        return Stream.of(values())
                .filter(s -> s.getValue() == valor)
                .findAny()
                .orElse(null);
    }
}
