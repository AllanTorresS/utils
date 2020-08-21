package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma determinada resposta ao invocar o serviço de roteirizador inteligente
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaRoteirizadorVo {

    private List<PontoRoteirizadorVo> postos = new ArrayList<>();
    private String message;
    private Integer statusRequisicao;

    /**
     * Construtor padrão
     */
    public RespostaRoteirizadorVo() {
        // Construtor padrão para serialização
    }

    public RespostaRoteirizadorVo(List<PontoRoteirizadorVo> postos) {
        this.postos = postos;
    }

    public List<PontoRoteirizadorVo> getPostos() { return postos; }

    public void setPostos(List<PontoRoteirizadorVo> postos) { this.postos = postos; }

    public String getMessage() { return message; }

    public void setMessage(String message) { this.message = message; }

    public Integer getStatusRequisicao() { return statusRequisicao; }

    public void setStatusRequisicao(Integer statusRequisicao) { this.statusRequisicao = statusRequisicao; }

}
