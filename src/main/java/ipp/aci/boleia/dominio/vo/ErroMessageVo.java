package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.util.excecao.Erro;

public class ErroMessageVo {

    private Erro erro;
    private String mensagem;

    public ErroMessageVo(Erro erro, String mensagem) {
        this.erro = erro;
        this.mensagem = mensagem;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public Erro getErro() {
        return erro;
    }

    public void setErro(Erro erro) {
        this.erro = erro;
    }
}
