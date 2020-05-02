package ipp.aci.boleia.dominio.enums;

/**
 * Enumera os codigos telefonicos dos paises, para envio de SMS
 */
public enum CodigoTelefonePais {

    BRASIL(55);

    private Integer codigo;

    /**
     * Construtor privado
     * @param codigo O codigo do pais
     */
    CodigoTelefonePais(Integer codigo) {
        this.codigo = codigo;
    }

    /**
     * Obtem o codigo do pais
     * @return O codigo
     */
    public Integer getCodigo() {
        return codigo;
    }

    /**
     * Obtem o codigo prefixado com o sinal +
     * @return O codigo prefixado com o sinal +
     */
    public String getCodigoComPrefixo() {
        return "+" + codigo;
    }

}
