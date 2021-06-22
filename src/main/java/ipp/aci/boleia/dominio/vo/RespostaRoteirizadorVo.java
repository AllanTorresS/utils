package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma determinada resposta ao invocar o serviço de roteirizador inteligente
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaRoteirizadorVo {

    private List<PontoRoteirizadorVo> pontos = new ArrayList<>();
    private Integer codigo;
    private String mensagem;
    private Integer statusRequisicao;

    /**
     * Construtor padrão
     */
    public RespostaRoteirizadorVo() {
        // Construtor padrão para serialização
    }

    public RespostaRoteirizadorVo(List<PontoRoteirizadorVo> pontos) {
        this.pontos = pontos;
    }

    public List<PontoRoteirizadorVo> getPontos() { return pontos; }

    public void setPontos(List<PontoRoteirizadorVo> pontos) { this.pontos = pontos; }

    public Integer getCodigo() {
        return codigo;
    }

    public void setCodigo(Integer codigo) {
        this.codigo = codigo;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Integer getStatusRequisicao() { return statusRequisicao; }

    public void setStatusRequisicao(Integer statusRequisicao) { this.statusRequisicao = statusRequisicao; }
}
