package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os Estados da Federação
 * <p>
 * O Enum está ordenado pelas siglas dos estados e deve ser mantida esta ordenação.
 */
public enum TransacaoStatus implements IEnumComLabel<TransacaoStatus> {

    NAO_AUTORIZADO(0),
    AUTORIZADO(1);

    private Integer valor;

    TransacaoStatus(Integer valor){
        this.valor = valor;
    }
    /**
     * Obtem por valor
     *
     * @param valor valor que representa o status
     * @return Enum para o valor
     */
    public static TransacaoStatus obterPorValor(Integer valor) {
        for (TransacaoStatus estado : TransacaoStatus.values()) {
            if (estado.getValor().equals(valor)) {
                return estado;
            }
        }
        return null;
    }

    public Integer getValor() {
        return valor;
    }
}