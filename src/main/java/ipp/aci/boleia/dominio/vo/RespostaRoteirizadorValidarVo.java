package ipp.aci.boleia.dominio.vo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma determinada resposta ao invocar o serviço de roteirizador inteligente
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RespostaRoteirizadorValidarVo {

    private Integer codigo;
    private String mensagem;
    private Integer statusRequisicao;
    private Integer status;

    /**
     * Construtor padrão
     */
    public RespostaRoteirizadorValidarVo() {
        // Construtor padrão para serialização
    }

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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
