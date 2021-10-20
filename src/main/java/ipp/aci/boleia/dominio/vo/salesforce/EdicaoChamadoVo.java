package ipp.aci.boleia.dominio.vo.salesforce;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    /**
     * Construtor da classe.
     *
     * @param descricao A nova descrição do chamado 
     */
    public EdicaoChamadoVo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
