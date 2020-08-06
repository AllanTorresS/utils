package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Lista os campos de abastecimento disponiveis para edição
 */
public enum CampoItemAbastecimento implements IEnumComLabel<CampoItemAbastecimento> {
    ARLA32GRANEL(1),
    LAVAGEM(2),
    TROCAOLEO(3),
    ALIMENTACAO(4),
    BORRACHARIA(5),
    MANUTENCAO(6),
    OUTROS(7),
    ARLA32BALDE(8),
    COMBUSTIVEL(9);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O valor do enum
     */
    CampoItemAbastecimento(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enum
     * @return O valor da enum
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static CampoItemAbastecimento obterPorValor(Integer value) {
        for(CampoItemAbastecimento status : CampoItemAbastecimento.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}