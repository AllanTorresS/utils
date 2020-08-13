package ipp.aci.boleia.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ipp.aci.boleia.dominio.enums.TipoRestricaoPostosPermitidos;
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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.List;

/**
 * Representa a tabela de Pontos de Venda autorizados pelo Parametro de sistema da frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "FROTA_PARAM_SIS_PV_AUTORIZ")
public class FrotaParametroSistemaPostoAutorizadoAbastecimento implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = -3372916190809838149L;

    @Id
    @Column(name = "CD_FROTA_PARAM_SIS_PV_AUTORIZ")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FROTA_PARAM_SIS_PV_AUTORIZ")
    @SequenceGenerator(name = "SEQ_FROTA_PARAM_SIS_PV_AUTORIZ", sequenceName = "SEQ_FROTA_PARAM_SIS_PV_AUTORIZ", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @Column(name = "VA_MAXIMO_LITROS")
    private BigDecimal maximoLitros;

    @Column(name = "VA_MAXIMO_VALOR")
    private BigDecimal maximoValor;

    @NotNull
    @Column(name = "ID_AUTORIZADO")
    private Boolean autorizado;

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

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public Boolean getAutorizado() {
        return autorizado;
    }

    public void setAutorizado(Boolean autorizado) {
        this.autorizado = autorizado;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    /**
     * Valida se o posto esta autorizado. Nao importa se ele esta habilitado.
     * @return true se o posto estiver autorizado.
     */
    @JsonIgnore
    public boolean isAutorizado() {
        return this.pontoVenda != null && autorizado != null && this.autorizado;
    }

    /**
     * Valida se o posto esta habilitado e autorizado.
     * @return true se o posto estiver habilitado e autorizado.
     */
    @JsonIgnore
    public boolean isHabilitadoEAutorizado() {
        return this.pontoVenda != null && this.pontoVenda.isHabilitado() && autorizado != null && this.autorizado;
    }

    @Override
    public List<Frota> getFrotas() {
        return getFrotaParametroSistema().getFrotas();
    }

    public BigDecimal getMaximoLitros() {
        return maximoLitros;
    }

    public void setMaximoLitros(BigDecimal maximoLitros) {
        this.maximoLitros = maximoLitros;
    }

    public BigDecimal getMaximoValor() {
        return maximoValor;
    }

    public void setMaximoValor(BigDecimal maximoValor) {
        this.maximoValor = maximoValor;
    }

    @Transient
    public TipoRestricaoPostosPermitidos getTipoRestricaoPostoPermitido() {
        boolean isEmLitros = frotaParametroSistema.getEmLitros() != null && frotaParametroSistema.getEmLitros();
        if(isEmLitros) {
            return TipoRestricaoPostosPermitidos.LITRAGEM;
        }
        return TipoRestricaoPostosPermitidos.VALOR;
    }
}