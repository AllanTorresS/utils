package ipp.aci.boleia.dominio.enums;

import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica o tipo do relatório emitido pelo motor de geração de relatórios
 */
public enum TipoExtensaoArquivo implements IEnumComLabel<TipoExtensaoArquivo> {

    XLSX(0),
    TXT(1);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    TipoExtensaoArquivo(Integer value) {
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
    public static TipoExtensaoArquivo obterPorValor(Integer value) {
        for(TipoExtensaoArquivo status : TipoExtensaoArquivo.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}