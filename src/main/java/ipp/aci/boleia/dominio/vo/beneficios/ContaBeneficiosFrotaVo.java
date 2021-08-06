package ipp.aci.boleia.dominio.vo.beneficios;

import java.math.BigDecimal;

/**
 * Vo com os dados de uma conta de benefícios da frota.
 *
 * @author lucas.santiago
 */
public class ContaBeneficiosFrotaVo {
    private BigDecimal creditoDisponivelFrota;
    private BigDecimal saldoDisponivelFrota;
    private BigDecimal limiteDisponivel;
    private BigDecimal indiceLimiteDisponivel;
    private BigDecimal saldoBeneficio;
    private BigDecimal saldoDisponivel;
    private BigDecimal pagamentoPendente;

    public ContaBeneficiosFrotaVo() {
        //Construtor default
    }

    /**
     * Construtor da classe ContaBeneficiosFrotaVo.
     *
     * @param creditoDisponivelFrota O total de crédito que uma frota pode gastar dentro de um ciclo.
     * @param saldoDisponivelFrota O saldo corrente da frota.
     * @param limiteDisponivel O total de crédito que uma frota pode comprar para benefícios.
     * @param indiceLimiteDisponivel O percentual do crédito disponível usado para formar o limite disponível.
     * @param saldoBeneficio O saldo corrente da frota para compra de crédito.
     * @param saldoDisponivel O saldo corrente para distribuição de benefícios.
     * @param pagamentoPendente O pagamento pendente da compra de créditos.
     */
    public ContaBeneficiosFrotaVo(BigDecimal creditoDisponivelFrota, BigDecimal saldoDisponivelFrota, BigDecimal limiteDisponivel, BigDecimal indiceLimiteDisponivel, BigDecimal saldoBeneficio, BigDecimal saldoDisponivel, BigDecimal pagamentoPendente) {
        this.creditoDisponivelFrota = creditoDisponivelFrota;
        this.saldoDisponivelFrota = saldoDisponivelFrota;
        this.limiteDisponivel = limiteDisponivel;
        this.indiceLimiteDisponivel = indiceLimiteDisponivel;
        this.saldoBeneficio = saldoBeneficio;
        this.saldoDisponivel = saldoDisponivel;
        this.pagamentoPendente = pagamentoPendente;
    }

    public BigDecimal getCreditoDisponivelFrota() {
        return creditoDisponivelFrota;
    }

    public void setCreditoDisponivelFrota(BigDecimal creditoDisponivelFrota) {
        this.creditoDisponivelFrota = creditoDisponivelFrota;
    }

    public BigDecimal getSaldoDisponivelFrota() {
        return saldoDisponivelFrota;
    }

    public void setSaldoDisponivelFrota(BigDecimal saldoDisponivelFrota) {
        this.saldoDisponivelFrota = saldoDisponivelFrota;
    }

    public BigDecimal getLimiteDisponivel() {
        return limiteDisponivel;
    }

    public void setLimiteDisponivel(BigDecimal limiteDisponivel) {
        this.limiteDisponivel = limiteDisponivel;
    }

    public BigDecimal getIndiceLimiteDisponivel() {
        return indiceLimiteDisponivel;
    }

    public void setIndiceLimiteDisponivel(BigDecimal indiceLimiteDisponivel) {
        this.indiceLimiteDisponivel = indiceLimiteDisponivel;
    }

    public BigDecimal getSaldoBeneficio() {
        return saldoBeneficio;
    }

    public void setSaldoBeneficio(BigDecimal saldoBeneficio) {
        this.saldoBeneficio = saldoBeneficio;
    }

    public BigDecimal getSaldoDisponivel() {
        return saldoDisponivel;
    }

    public void setSaldoDisponivel(BigDecimal saldoDisponivel) {
        this.saldoDisponivel = saldoDisponivel;
    }

    public BigDecimal getPagamentoPendente() {
        return pagamentoPendente;
    }

    public void setPagamentoPendente(BigDecimal pagamentoPendente) {
        this.pagamentoPendente = pagamentoPendente;
    }
}
