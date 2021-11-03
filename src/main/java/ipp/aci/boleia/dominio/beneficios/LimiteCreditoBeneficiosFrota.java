package ipp.aci.boleia.dominio.beneficios;

import ipp.aci.boleia.dominio.Frota;
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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

/**
 * Entidade com a configuração do indice de limite de crédito para
 * benefícios de uma frota.
 *
 * @author pedro.silva
 */
@Audited
@Entity
@Table(name = "LIMITE_CREDITO_BENEF_FROTA")
public class LimiteCreditoBeneficiosFrota implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -2145052593207536187L;

    @Id
    @Column(name = "CD_LIMITE_CREDITO_BENEF_FROTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_LIMITE_CREDITO_BENEF_FROTA")
    @SequenceGenerator(name = "SEQ_LIMITE_CREDITO_BENEF_FROTA", sequenceName = "SEQ_LIMITE_CREDITO_BENEF_FROTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name = "VA_INDICE_LIMITE")
    private BigDecimal valorIndiceLimite;

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

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public BigDecimal getValorIndiceLimite() {
        return valorIndiceLimite;
    }

    public void setValorIndiceLimite(BigDecimal valorIndiceLimite) {
        this.valorIndiceLimite = valorIndiceLimite;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }
}
