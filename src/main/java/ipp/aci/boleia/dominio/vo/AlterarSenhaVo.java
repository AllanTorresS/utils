package ipp.aci.boleia.dominio.vo;

/**
 * Vo para obtenção de senhas a alterar do usuario
 */
public class AlterarSenhaVo {

    private String atual;
    private String nova;
    private String confirmacao;

    public String getAtual() {
        return atual;
    }

    public void setAtual(String atual) {
        this.atual = atual;
    }

    public String getNova() {
        return nova;
    }

    public void setNova(String nova) {
        this.nova = nova;
    }

    public String getConfirmacao() {
        return confirmacao;
    }

    public void setConfirmacao(String confirmacao) {
        this.confirmacao = confirmacao;
    }
}
