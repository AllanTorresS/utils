package ipp.aci.boleia.dominio;

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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Saldo da Frota
 */
@Audited
@Entity
@Table(name = "SALDO_FROTA")
public class SaldoFrota implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -2077625344449197658L;

    @Id
    @Column(name = "CD_SALDO_FROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SALDO_FROTA")
    @SequenceGenerator(name = "SEQ_SALDO_FROTA", sequenceName = "SEQ_SALDO_FROTA", allocationSize = 1)
    private Long id;

    @Column(name="VA_LIMITE_CREDITO")
    private BigDecimal limiteCredito;

    @Column(name="VA_SALDO_CORRENTE")
    private BigDecimal saldoCorrente;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public BigDecimal getLimiteCredito() {
        return limiteCredito;
    }

    public void setLimiteCredito(BigDecimal limiteCredito) {
        this.limiteCredito = limiteCredito;
    }

    public BigDecimal getSaldoCorrente() {
        return saldoCorrente;
    }

    public void setSaldoCorrente(BigDecimal saldoCorrente) {
        this.saldoCorrente = saldoCorrente;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }
}
