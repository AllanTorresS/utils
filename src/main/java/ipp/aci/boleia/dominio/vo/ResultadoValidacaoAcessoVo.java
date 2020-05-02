package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.enums.StatusAcessoSistemaExterno;

/**
 * Contem mo resultado de uma validacao de acesso de um usuario ao sistema
 */
public class ResultadoValidacaoAcessoVo {

    private StatusAcessoSistemaExterno status;
    private String mensagem;

    public ResultadoValidacaoAcessoVo() {
        // serializacao json
    }

    /**
     * Construtor do resultado da validacao de acesso
     * @param status O status do acesso ao sistema externo
     * @param mensagem A mensagem resposta
     */
    public ResultadoValidacaoAcessoVo(StatusAcessoSistemaExterno status, String mensagem) {
        this.status = status;
        this.mensagem = mensagem;
    }

    /**
     * Construtor do resultado da validacao de acesso
     * @param status O status do acesso ao sistema externo
     */
    public ResultadoValidacaoAcessoVo(StatusAcessoSistemaExterno status) {
        this(status, null);
    }

    public StatusAcessoSistemaExterno getStatus() {
        return status;
    }

    public void setStatus(StatusAcessoSistemaExterno status) {
        this.status = status;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    /**
     * Informa se o status do resultado da validação é de sucesso.
     *
     * @return True, caso possua status de sucesso.
     */
    public boolean isSucesso() {
        return status != null && status.equals(StatusAcessoSistemaExterno.SUCESSO);
    }
}
