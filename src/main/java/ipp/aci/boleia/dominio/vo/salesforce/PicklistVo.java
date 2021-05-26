package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.Map;

/**
 * VO com as informações de um picklist do salesforce.
 *
 * @author pedro.silva
 */
public class PicklistVo {
    @JsonProperty("controllerValues")
    private Map<String, Integer> valoresEntrada;
    @JsonProperty("values")
    private List<ValorPicklistVo> valores;

    public Map<String, Integer> getValoresEntrada() {
        return valoresEntrada;
    }

    public void setValoresEntrada(Map<String, Integer> valoresEntrada) {
        this.valoresEntrada = valoresEntrada;
    }

    public List<ValorPicklistVo> getValores() {
        return valores;
    }

    public void setValores(List<ValorPicklistVo> valores) {
        this.valores = valores;
    }
}
