package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de pedido Motorista autonomo
 */
@Entity
@Table(name = "OPERACAO_CONTA_BENEFICIO")
public class OperacaoContaBeneficio implements IPersistente {

    @Id
    @Column(name = "CD_OPERACAO_CONTA_BENEFICIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONTA_BENEFICIO_USUARIO")
    @SequenceGenerator(name = "SEQ_CONTA_BENEFICIO_USUARIO", sequenceName = "SEQ_CONTA_BENEFICIO_USUARIO", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CONTA_BENEFICIO_USUARIO")
    private ContaBeneficioUsuario contaBeneficioUsuario;

    @Column(name = "VA_TOTAL")
    private BigDecimal valorTotal;

    @Column(name = "VA_SALDO_RESULTANTE")
    private BigDecimal saldoResultante;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ContaBeneficioUsuario getContaBeneficioUsuario() {
        return contaBeneficioUsuario;
    }

    public void setContaBeneficioUsuario(ContaBeneficioUsuario contaBeneficioUsuario) {
        this.contaBeneficioUsuario = contaBeneficioUsuario;
    }

    public BigDecimal getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(BigDecimal valorTotal) {
        this.valorTotal = valorTotal;
    }

    public BigDecimal getSaldoResultante() {
        return saldoResultante;
    }

    public void setSaldoResultante(BigDecimal saldoResultante) {
        this.saldoResultante = saldoResultante;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }


    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}