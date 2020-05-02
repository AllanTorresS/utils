package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

/**
 * Classe que encapsula resposta do serviço de consulta de status de transação na base do POS/Abastece Aí
 */
public class RespostaPosAbasteceAiVo {

    @JsonProperty("ObjetoRetorno")
    private List<RequisicaoPosAbasteceAiVo> objetoRetorno;

    /**
     * Construtor default da classe.
     */
    public RespostaPosAbasteceAiVo() {
        //construtor default
    }

    public List<RequisicaoPosAbasteceAiVo> getObjetoRetorno() {
        return objetoRetorno;
    }

    public void setObjetoRetorno(List<RequisicaoPosAbasteceAiVo> objetoRetorno) {
        this.objetoRetorno = objetoRetorno;
    }
}
