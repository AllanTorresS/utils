package ipp.aci.boleia.dominio.beneficios;

import ipp.aci.boleia.dominio.AutorizacaoPagamento;
import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.Usuario;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de operação conta beneficiário
 */
@Audited
@Entity
@Table(name = "OPER_CONTA_BENEFICIARIO")
public class OperacaoContaBeneficiario implements IPersistente, IPertenceFrota {

    @Id
    @Column(name = "CD_OPER_CONTA_BENEFICIARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_OPER_CONTA_BENEFICIARIO")
    @SequenceGenerator(name = "SEQ_OPER_CONTA_BENEFICIARIO", sequenceName = "SEQ_OPER_CONTA_BENEFICIARIO", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CONTA_BENEFICIARIO")
    private ContaBeneficiario contaBeneficiario;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")
    private AutorizacaoPagamento autorizacaoPagamento;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO_DISTRIBUICAO")
    private Usuario autor;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ContaBeneficiario getContaBeneficiario() {
        return contaBeneficiario;
    }

    public void setContaBeneficiario(ContaBeneficiario contaBeneficiario) {
        this.contaBeneficiario = contaBeneficiario;
    }

    public AutorizacaoPagamento getAutorizacaoPagamento() {
        return autorizacaoPagamento;
    }

    public void setAutorizacaoPagamento(AutorizacaoPagamento autorizacaoPagamento) {
        this.autorizacaoPagamento = autorizacaoPagamento;
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

    public Usuario getAutor() {
        return autor;
    }

    public void setAutor(Usuario autor) {
        this.autor = autor;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return  Collections.singletonList(contaBeneficiario.getBeneficiario().getFrota());
    }
}