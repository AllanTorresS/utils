package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

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
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa a tabela de Parametros do Sistema Consumo para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "FROTA_PARAM_SIS_CONSUMO")
public class FrotaParametroSistemaConsumo implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -8744864612379584028L;

    @Id
    @Column(name = "CD_FROTA_PARAM_SIS_CONSUMO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PARAM_SIS_CONSUMO")
    @SequenceGenerator(name = "SEQ_FROTA_PARAM_SIS_CONSUMO", sequenceName = "SEQ_FROTA_PARAM_SIS_CONSUMO", allocationSize = 1)
    private Long id;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

    @NotNull
    @Column(name= "VA_PORCENTAGEM_LIMITE_MINIMO")
    private BigDecimal porcentagemLimiteMinimo;

    @NotNull
    @Column(name= "VA_PORCENTAGEM_LIMITE_MAXIMO")
    private BigDecimal porcentagemLimiteMaximo;

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public FrotaParametroSistema getFrotaParametroSistema() {
        return frotaParametroSistema;
    }

    public void setFrotaParametroSistema(FrotaParametroSistema frotaParametroSistema) {
        this.frotaParametroSistema = frotaParametroSistema;
    }



    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistema != null ? frotaParametroSistema.getFrotas() : null;
    }

    public BigDecimal getPorcentagemLimiteMinimo() {
        return porcentagemLimiteMinimo;
    }

    public void setPorcentagemLimiteMinimo(BigDecimal porcentagemLimiteMinimo) {
        this.porcentagemLimiteMinimo = porcentagemLimiteMinimo;
    }

    public BigDecimal getPorcentagemLimiteMaximo() {
        return porcentagemLimiteMaximo;
    }

    public void setPorcentagemLimiteMaximo(BigDecimal porcentagemLimiteMaximo) {
        this.porcentagemLimiteMaximo = porcentagemLimiteMaximo;
    }
}