package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o status de alteração do preco por Frota/PV do revendedor
 */
public enum StatusPreco implements IEnumComLabel<StatusPreco> {

    VIGENTE(1),
    NOVO(2),
    PENDENTE(3),
    REJEITADO(4),
    ACEITO(5),
    HISTORICO(6);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'VIG', 2, 'NOV', 3, 'AC_PEND', 4, 'REJ', 5, 'AC', 6, 'HIST')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O label do status
     */
    StatusPreco(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor do status
     * @return O valor do status
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusPreco obterPorValor(Integer value) {
        for(StatusPreco status : StatusPreco.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }

}
