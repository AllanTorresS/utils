package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica se a versão do aplicativo está atualizado, desatualizado ou se tem atualização pendente
 */
public enum StatusVersaoAplicativo implements IEnumComLabel<StatusVersaoAplicativo> {

    ATUALIZADO(0),
    ATUALIZACAO_PENDENTE(1),
    ATUALIZACAO_OBRIGATORIA(2);

    public static final String DECODE_FORMULA = "DECODE(ID_STATUS, 1, 'ATUALIZACAO_PENDENTE', 0, 'ATUALIZADO', 2, 'ATUALIZACAO_OBRIGATORIA')";

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusVersaoAplicativo(Integer value) {
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    /**
     * Obtem por valor
     * @param value value
     * @return Enum para o valor
     */
    public static StatusVersaoAplicativo obterPorValor(Integer value) {
        for(StatusVersaoAplicativo status : StatusVersaoAplicativo.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
