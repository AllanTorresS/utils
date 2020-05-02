package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * Representa um registro a ser selecionado no Couch DB
 */
public class SelectorVo {

    private Long pv;

    private String typeModel;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String bicoId;

    public Long getPv() {
        return pv;
    }

    public void setPv(Long pv) {
        this.pv = pv;
    }

    public String getTypeModel() {
        return typeModel;
    }

    public void setTypeModel(String typeModel) {
        this.typeModel = typeModel;
    }

    public String getBicoId() {
        return bicoId;
    }

    public void setBicoId(String bicoId) {
        this.bicoId = bicoId;
    }
}
