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
    private Integer codigo;
    private String mensagem;
    private Integer statusRequisicao;
    private List<String> trechos = new ArrayList<>();

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

    public List<String> getTrechos() {
        return trechos;
    }

    public void setTrechos(List<String> trechos) {
        this.trechos = trechos;
    }
}
