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
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Histórico da Cota de Veículos dos Parametros do Sistema para a Frota
 */
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Entity
@Table(name = "HIST_FROTA_PARAM_SIS_COTA")
public class HistoricoFrotaParametroSistemaCota implements IPersistente, IPertenceFrota {

    private static final long serialVersionUID = 622042891226349536L;

    @Id
    @Column(name = "CD_HIST_FROTA_PARAM_SIS_COTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_HIST_FROTA_PARAM_SIS_COTA")
    @SequenceGenerator(name = "SEQ_HIST_FROTA_PARAM_SIS_COTA", sequenceName = "SEQ_HIST_FROTA_PARAM_SIS_COTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA_PARAM_SIS")
    private FrotaParametroSistema frotaParametroSistema;

    @Column(name = "ID_COTA_VEIC_VISIVEL_MOTORISTA")
    private Boolean cotaVeiculoVisivelMotorista;

    @Column(name = "ID_EM_LITROS")
    private Boolean emLitros;

    @Column(name = "ID_COTA_VEIC_POR_ABAS")
    private Boolean cotaVeiculoPorAbastecimento;

    @Column(name = "ID_COTA_VEIC_USAR_SUGESTAO")
    private Boolean cotaVeiculoUsarSugestao;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "DT_ALTERACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAlteracao;

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

    public Boolean getCotaVeiculoVisivelMotorista() {
        return cotaVeiculoVisivelMotorista;
    }

    public void setCotaVeiculoVisivelMotorista(Boolean cotaVeiculoVisivelMotorista) {
        this.cotaVeiculoVisivelMotorista = cotaVeiculoVisivelMotorista;
    }

    public Boolean getEmLitros() {
        return emLitros;
    }

    public void setEmLitros(Boolean emLitros) {
        this.emLitros = emLitros;
    }

    public Boolean getCotaVeiculoPorAbastecimento() {
        return cotaVeiculoPorAbastecimento;
    }

    public void setCotaVeiculoPorAbastecimento(Boolean cotaVeiculoPorAbastecimento) {
        this.cotaVeiculoPorAbastecimento = cotaVeiculoPorAbastecimento;
    }

    public Boolean getCotaVeiculoUsarSugestao() {
        return cotaVeiculoUsarSugestao;
    }

    public void setCotaVeiculoUsarSugestao(Boolean cotaVeiculoUsarSugestao) {
        this.cotaVeiculoUsarSugestao = cotaVeiculoUsarSugestao;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(Date dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    @Override
    public List<Frota> getFrotas() {
        return frotaParametroSistema.getFrota() != null ? frotaParametroSistema.getFrota().getFrotas() : null;
    }

}