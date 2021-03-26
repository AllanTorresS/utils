package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Vo com os valores totais de reembolso em um determinado período.
 */
public class ReembolsoTotalPeriodoVo {
    private BigDecimal totalReembolso;
    private BigDecimal totalAntecipado;

    /**
     * Construtor default.
     */
    public ReembolsoTotalPeriodoVo() {
    }

    /**
     * Construtor da classe.
     *
     * @param totalReembolso Valor de reembolso total.
     * @param totalAntecipado Valor antecipado total.
     */
    public ReembolsoTotalPeriodoVo(BigDecimal totalReembolso, BigDecimal totalAntecipado) {
        this.totalReembolso = totalReembolso;
        this.totalAntecipado = totalAntecipado;
    }

    public BigDecimal getTotalReembolso() {
        return totalReembolso;
    }

    public void setTotalReembolso(BigDecimal totalReembolso) {
        this.totalReembolso = totalReembolso;
    }

    public BigDecimal getTotalAntecipado() {
        return totalAntecipado;
    }

    public void setTotalAntecipado(BigDecimal totalAntecipado) {
        this.totalAntecipado = totalAntecipado;
    }
}
