package ipp.aci.boleia.dominio.enums;


import ipp.aci.boleia.util.i18n.IEnumComLabel;

/**
 * Indica so status do processamento em lote de arquivos para download
 */
public enum StatusDownloadArquivos implements IEnumComLabel<StatusDownloadArquivos> {

    EM_ANDAMENTO(0),
    CONCLUIDO(1),//verificar o default na migration
    CANCELADO(2),
    EXCLUIDO(3),
    ERRO(4),
    REGISTROS_NAO_ENCONTRADOS(5);

    private final Integer value;

    /**
     * Construtor
     *
     * @param value O value do status
     */
    StatusDownloadArquivos(Integer value) {
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
    public static StatusDownloadArquivos obterPorValor(Integer value) {
        for(StatusDownloadArquivos status : StatusDownloadArquivos.values()) {
            if(status.value.equals(value)) {
                return status;
            }
        }
        return null;
    }
}
