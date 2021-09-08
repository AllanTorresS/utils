package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Guarda o total antecipável em uma data
 */
public class ValorAntecipavelPorDataVo {

    private Date dataLimitePagamento;
    private BigDecimal valorAntecipavel;

    /**
     * Construtor default
     */
    public ValorAntecipavelPorDataVo() {
    }

    /**
     * Constrói o VO com os dados necessários.
     *
     * @param dataLimitePagamento Data limite para pagamento
     * @param valorAntecipavel valor disponível para antecipação
     */
    public ValorAntecipavelPorDataVo(Date dataLimitePagamento, BigDecimal valorAntecipavel) {
        this.dataLimitePagamento = dataLimitePagamento;
        this.valorAntecipavel = valorAntecipavel;
    }

    public Date getDataLimitePagamento() {
        return dataLimitePagamento;
    }

    public void setDataLimitePagamento(Date dataLimitePagamento) {
        this.dataLimitePagamento = dataLimitePagamento;
    }

    public BigDecimal getValorAntecipavel() {
        return valorAntecipavel;
    }

    public void setValorAntecipavel(BigDecimal valorAntecipavel) {
        this.valorAntecipavel = valorAntecipavel;
    }
}
