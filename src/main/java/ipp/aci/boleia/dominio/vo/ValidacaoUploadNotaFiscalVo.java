package ipp.aci.boleia.dominio.vo;

import java.util.List;

public class ValidacaoUploadNotaFiscalVo {
    private String numero;
    private String valorTotal;
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
}
