package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.vo.EntidadeVo;

import java.math.BigDecimal;

/**
 * Classe de exibição que representa um Preço Frete
 */
public class PrecoFreteVo {

    private EntidadeVo pontoVenda;

    private BigDecimal preco;

    private EntidadeVo tipoCombustivel;

    public BigDecimal getPreco() {
        return preco;
    }

    public EntidadeVo getTipoCombustivel() {
        return tipoCombustivel;
    }

    public EntidadeVo getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(EntidadeVo pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public void setPreco(BigDecimal preco) {
        this.preco = preco;
    }

    public void setTipoCombustivel(EntidadeVo tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
}
