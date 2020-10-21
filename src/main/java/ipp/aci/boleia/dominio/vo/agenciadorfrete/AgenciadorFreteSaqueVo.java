package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.dominio.agenciadorfrete.Saque;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Classe com informações referentes ao saque de um abastecimento de um motorista autonomo
 */
public class AgenciadorFreteSaqueVo {
    private Long id;
    @NotNull
    private BigDecimal valorSolicitado;
    private BigDecimal taxa;

    private BigDecimal valorRecebido;

    public AgenciadorFreteSaqueVo() {
        //Construtor default
    }

    public AgenciadorFreteSaqueVo(Saque saque) {
        if(saque != null) {
            this.id = saque.getId();
            this.valorSolicitado = saque.getValorSolicitado();
            this.valorRecebido = saque.getValorSolicitado().subtract(saque.getTaxa()).subtract(saque.getTaxaAgenciadorFrete());
            this.taxa = saque.getTaxa();
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
