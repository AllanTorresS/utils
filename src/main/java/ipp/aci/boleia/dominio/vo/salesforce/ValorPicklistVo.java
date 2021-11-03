package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * VO com as informações de valor de um picklist.
 *
 * @author pedro.silva
 */
public class ValorPicklistVo {
    private String label;
    @JsonProperty("validFor")
    private Integer[] valoresEntradaValidos;
    @JsonProperty("value")
    private String valor;

    /**
     * Construtor default
     */
    public ValorPicklistVo() {
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Integer[] getValoresEntradaValidos() {
        return valoresEntradaValidos;
    }

    public void setValoresEntradaValidos(Integer[] valoresEntradaValidos) {
        this.valoresEntradaValidos = valoresEntradaValidos;
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }
}
