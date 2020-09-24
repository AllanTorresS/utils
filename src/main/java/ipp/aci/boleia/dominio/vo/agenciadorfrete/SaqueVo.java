package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Saque;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe com informações referentes ao saque de um abastecimento de um motorista autonomo
 */
public class SaqueVo {
    private Long id;
    private BigDecimal valorSolicitado;
    private BigDecimal taxa;
    private BigDecimal valorRecebido;

    public SaqueVo() {
        //Construtor default
    }

    public SaqueVo(Saque saque) {
        if(saque != null) {
            this.id = saque.getId();
            this.valorSolicitado = saque.getValorSolicitado();
            this.valorRecebido = saque.getValorSolicitado().subtract(saque.getTaxa()).subtract(saque.getTaxaAgenciadorFrete()).setScale(2, RoundingMode.HALF_UP);
            this.taxa = saque.getTaxa().add(saque.getTaxaAgenciadorFrete()).subtract(saque.getTaxaAgenciadorFrete()).setScale(2, RoundingMode.HALF_UP);
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getValorSolicitado() {
        return valorSolicitado;
    }

    public void setValorSolicitado(BigDecimal valorSolicitado) {
        this.valorSolicitado = valorSolicitado;
    }

    public BigDecimal getTaxa() {
        return taxa;
    }

    public void setTaxa(BigDecimal taxa) {
        this.taxa = taxa;
    }

    public BigDecimal getValorRecebido() {
        return valorRecebido;
    }

    public void setValorRecebido(BigDecimal valorRecebido) {
        this.valorRecebido = valorRecebido;
    }
}
