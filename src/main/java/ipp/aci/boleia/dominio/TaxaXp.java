package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

/**
 * Representa a tabela de taxas da XP
 */
@Entity
@Table(name = "TAXA_XP")
public class TaxaXp implements IPersistente, IExclusaoLogica {

    @Id
    @Column(name = "CD_TAXA_XP")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "NO_TABELA_XP")
    private String codigoTabela;

    @NotNull
    @Column(name = "VA_TAXA")
    private BigDecimal valorTaxa;

    @NotNull
    @Column(name = "ID_PERCENTUAL")
    private boolean percentual;

    @NotNull
    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public String getCodigoTabela() {
        return codigoTabela;
    }

    public void setCodigoTabela(String codigoTabela) {
        this.codigoTabela = codigoTabela;
    }

    public BigDecimal getValorTaxa() {
        return valorTaxa;
    }

    public void setValorTaxa(BigDecimal valorTaxa) {
        this.valorTaxa = valorTaxa;
    }

    public boolean isPercentual() {
        return percentual;
    }

    public void setPercentual(boolean percentual) {
        this.percentual = percentual;
    }
}
