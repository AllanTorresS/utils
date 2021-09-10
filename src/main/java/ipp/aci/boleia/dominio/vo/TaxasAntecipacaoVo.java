package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Representa um conjunto de taxas de antecipação
 */
public class TaxasAntecipacaoVo {
    private BigDecimal valorMdr;
    private BigDecimal valorTaxaXp;
    private BigDecimal valorTaxaProFrotas;
    private BigDecimal valorTaxaAntecipacao;

    /**
     * Construtor default
     */
    public TaxasAntecipacaoVo() {
        valorMdr = BigDecimal.ZERO;
        valorTaxaXp = BigDecimal.ZERO;
        valorTaxaProFrotas = BigDecimal.ZERO;
        valorTaxaAntecipacao = BigDecimal.ZERO;
    }

    /**
     * Constrói o VO a partir dos parâmetros necessários
     * @param valorMdr o valor de MDR cobrado
     * @param valorTaxaXp o valor cobrado do agente parceiro
     * @param valorTaxaProFrotas o valor retido pelo Pró-Frotas
     * @param valorTaxaAntecipacao o valor cobrado pela operação de antecipação
     */
    public TaxasAntecipacaoVo(BigDecimal valorMdr, BigDecimal valorTaxaXp, BigDecimal valorTaxaProFrotas, BigDecimal valorTaxaAntecipacao) {
        this.valorMdr = valorMdr;
        this.valorTaxaXp = valorTaxaXp;
        this.valorTaxaProFrotas = valorTaxaProFrotas;
        this.valorTaxaAntecipacao = valorTaxaAntecipacao;
    }

    public BigDecimal getValorMdr() {
        return valorMdr;
    }

    public void setValorMdr(BigDecimal valorMdr) {
        this.valorMdr = valorMdr;
    }

    public BigDecimal getValorTaxaXp() {
        return valorTaxaXp;
    }

    public void setValorTaxaXp(BigDecimal valorTaxaXp) {
        this.valorTaxaXp = valorTaxaXp;
    }

    public BigDecimal getValorTaxaProFrotas() {
        return valorTaxaProFrotas;
    }

    public void setValorTaxaProFrotas(BigDecimal valorTaxaProFrotas) {
        this.valorTaxaProFrotas = valorTaxaProFrotas;
    }

    public BigDecimal getValorTaxaAntecipacao() {
        return valorTaxaAntecipacao;
    }

    public void setValorTaxaAntecipacao(BigDecimal valorTaxaAntecipacao) {
        this.valorTaxaAntecipacao = valorTaxaAntecipacao;
    }
}
