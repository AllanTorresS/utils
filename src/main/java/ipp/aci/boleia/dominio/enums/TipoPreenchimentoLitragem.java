package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Enumera os póssíveis tipos de Preenchimento de Litragem
 */
public enum TipoPreenchimentoLitragem implements IEnumComLabel<TipoPreenchimentoLitragem> {

    AUTO(1),
    MANUAL(2);

    private final Integer value;

    /**
     * Construtor
     * @param value O valor da enum
     */
    TipoPreenchimentoLitragem(Integer value) {
        this.value = value;
    }

    /**
     * Obtem o valor da enumercao
     * @return o valor da enumeracao
     */
    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static TipoPreenchimentoLitragem obterPorValor(Integer value) {
        for(TipoPreenchimentoLitragem tipoPreenchimentoLitragem  : TipoPreenchimentoLitragem.values()) {
            if(tipoPreenchimentoLitragem.value.equals(value)) {
                return tipoPreenchimentoLitragem;
            }
        }
        return null;
    }
}