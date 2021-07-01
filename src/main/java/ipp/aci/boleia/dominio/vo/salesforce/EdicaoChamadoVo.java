package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

/**
 * VO com as informações necessárias para alterar um chamado do Salesforce.
 *
 */
public class EdicaoChamadoVo {
    
    @JsonProperty("Description")
    private String descricao;

    public EdicaoChamadoVo() {
        // serializacao JSON
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
