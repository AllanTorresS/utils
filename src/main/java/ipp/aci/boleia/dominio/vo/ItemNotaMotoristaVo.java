package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Representa um item de uma nota de compra exibida no aplicativo do motorista
 *
 * @author pedro.silva
 */
public class ItemNotaMotoristaVo {
    private String produtoNome;
    private String produtoUnidade;
    private BigDecimal produtoQuant;
    private BigDecimal produtoValor;

    public String getProdutoNome() {
        return produtoNome;
    }

    public void setProdutoNome(String produtoNome) {
        this.produtoNome = produtoNome;
    }

    public String getProdutoUnidade() {
        return produtoUnidade;
    }

    public void setProdutoUnidade(String produtoUnidade) {
        this.produtoUnidade = produtoUnidade;
    }

    public BigDecimal getProdutoQuant() {
        return produtoQuant;
    }

    public void setProdutoQuant(BigDecimal produtoQuant) {
        this.produtoQuant = produtoQuant;
    }

    public BigDecimal getProdutoValor() {
        return produtoValor;
    }

    public void setProdutoValor(BigDecimal produtoValor) {
        this.produtoValor = produtoValor;
    }
}
