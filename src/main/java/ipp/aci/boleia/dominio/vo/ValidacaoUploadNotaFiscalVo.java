package ipp.aci.boleia.dominio.vo;

import java.util.List;

public class ValidacaoUploadNotaFiscalVo {
    private String numero;
    private String valorTotal;
    private List<ErroMessageVo> mensagensErro;
    private NotaFiscalVo notaFiscalVo;
    private Boolean isNotaFiscalDuplicada;

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public List<ErroMessageVo> getMensagensErro() {
        return mensagensErro;
    }

    public void setMensagensErro(List<ErroMessageVo> mensagensErro) {
        this.mensagensErro = mensagensErro;
    }

    public String getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(String valorTotal) {
        this.valorTotal = valorTotal;
    }

    public NotaFiscalVo getNotaFiscalVo() {
        return notaFiscalVo;
    }

    public void setNotaFiscalVo(NotaFiscalVo notaFiscalVo) {
        this.notaFiscalVo = notaFiscalVo;
    }

    public Boolean getNotaFiscalDuplicada() {
        return isNotaFiscalDuplicada;
    }

    public void setNotaFiscalDuplicada(Boolean notaFiscalDuplicada) {
        isNotaFiscalDuplicada = notaFiscalDuplicada;
    }
}
