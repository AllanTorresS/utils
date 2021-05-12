package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Corpo da requisição para solicitar participação no programa de antecipação
 */
public class SolicitacaoInteresseAntecipacaoVo {

    @JsonProperty("ParticiparAntecipacaoRecebiveis__c")
    private Boolean participacaoAntecipacaoRecebiveis;

    /**
     * Construtor padrão
     */
    public SolicitacaoInteresseAntecipacaoVo() {}

    /**
     * Construtor que recebe o novo valor do campo
     * @param participacaoAntecipacaoRecebiveis o novo valor para o campo
     */
    public SolicitacaoInteresseAntecipacaoVo(Boolean participacaoAntecipacaoRecebiveis) {
        this.participacaoAntecipacaoRecebiveis = participacaoAntecipacaoRecebiveis;
    }

    public Boolean getParticipacaoAntecipacaoRecebiveis() {
        return participacaoAntecipacaoRecebiveis;
    }

    public void setParticipacaoAntecipacaoRecebiveis(Boolean participacaoAntecipacaoRecebiveis) {
        this.participacaoAntecipacaoRecebiveis = participacaoAntecipacaoRecebiveis;
    }
}
