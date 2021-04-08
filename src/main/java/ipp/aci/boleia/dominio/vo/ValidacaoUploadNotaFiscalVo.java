package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.util.excecao.Erro;

import java.util.List;

public class ValidacaoUploadNotaFiscalVo {
    private String numero;
    private String valorTotal;
    private Erro erroValidacao;
    private List<String> mensagens;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<String> getMensagens() {
        return mensagens;
    }

    public void setMensagens(List<String> mensagens) {
        this.mensagens = mensagens;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Erro getErroValidacao() {
        return erroValidacao;
    }

    public void setErroValidacao(Erro erroValidacao) {
        this.erroValidacao = erroValidacao;
    }
}
