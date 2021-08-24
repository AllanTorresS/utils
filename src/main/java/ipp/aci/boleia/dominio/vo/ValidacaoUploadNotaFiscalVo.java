package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.util.excecao.Erro;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe que representa um erro de validação no upload da nota fiscal.
 */
public class ValidacaoUploadNotaFiscalVo {
    private String numero;
    private BigDecimal valorTotal;
    private Erro erro;
    private String mensagem;
    private List<Object> argumentos;

    /**
     * Construtor padrao
     */
    public ValidacaoUploadNotaFiscalVo() {
        argumentos = new ArrayList<>();
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public Erro getErro() {
        return erro;
    }

    public void setErro(Erro erro) {
        this.erro = erro;
    }

    public String getMensagem() {
        return mensagem;
    }

    public void setMensagem(String mensagem) {
        this.mensagem = mensagem;
    }

    public List<Object> getArgumentos() {
        return argumentos;
    }

    public void setArgumentos(List<Object> argumentos) {
        this.argumentos = argumentos;
    }
}
