package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
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
import java.util.Date;

@Entity
@Audited
@Table(name = "FREQUENCIA_TAREFA")
public class FrequenciaTarefa implements IPersistente {
    @Id
    @Column(name = "CD_FREQUENCIA_TAREFA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FREQUENCIA_TAREFA")
    @SequenceGenerator(name = "SEQ_FREQUENCIA_TAREFA", sequenceName = "SEQ_FREQUENCIA_TAREFA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TAREFA_AGENDADA")
    private TarefaAgendada tarefaAgendada;

    @Column(name = "ID_STATUS_FREQUENCIA")
    private Integer statusFrequencia;

    @Column(name = "ID_TIPO_FREQUENCIA")
    private Integer tipoFrequencia;

    @Column(name = "VR_FREQUENCIA")
    private Integer valorFrequencia;

    @Column(name = "ID_TIPO_REPETICAO")
    private Integer tipoRepeticao;

    @Column(name = "VR_REPETICAO")
    private Integer valorRepeticao;

    @Column(name = "DT_INICIO_FREQUENCIA")
    private Date dataInicio;

    @Column(name = "DT_PROX_EXECUCAO")
    private Date dataProximaExecucao;

    @Column(name = "DT_ULTIMA_EXECUCAO")
    private Date dataUltimaExecucao;

    @Column(name = "ID_APENAS_DIAS_DE_SEMANA")
    private Boolean apenasDiasDeSemana;

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }

    public Integer getStatusFrequencia() {
        return statusFrequencia;
    }

    public void setStatusFrequencia(Integer statusFrequencia) {
        this.statusFrequencia = statusFrequencia;
    }

    public Integer getValorFrequencia() {
        return valorFrequencia;
    }

    public void setValorFrequencia(Integer valorFrequencia) {
        this.valorFrequencia = valorFrequencia;
    }

    public Integer getValorRepeticao() {
        return valorRepeticao;
    }

    public void setValorRepeticao(Integer valorRepeticao) {
        this.valorRepeticao = valorRepeticao;
    }

    public Date getDataUltimaExecucao() {
        return dataUltimaExecucao;
    }

    public void setDataUltimaExecucao(Date dataUltimaExecucao) {
        this.dataUltimaExecucao = dataUltimaExecucao;
    }

    public TarefaAgendada getTarefaAgendada() {
        return tarefaAgendada;
    }

    public void setTarefaAgendada(TarefaAgendada tarefaAgendada) {
        this.tarefaAgendada = tarefaAgendada;
    }

    public Integer getTipoFrequencia() {
        return tipoFrequencia;
    }

    public void setTipoFrequencia(Integer tipoFrequencia) {
        this.tipoFrequencia = tipoFrequencia;
    }

    public Integer getTipoRepeticao() {
        return tipoRepeticao;
    }

    public void setTipoRepeticao(Integer tipoRepeticao) {
        this.tipoRepeticao = tipoRepeticao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataProximaExecucao() {
        return dataProximaExecucao;
    }

    public void setDataProximaExecucao(Date dataProximaExecucao) {
        this.dataProximaExecucao = dataProximaExecucao;
    }

    public Boolean getApenasDiasDeSemana() {
        return apenasDiasDeSemana;
    }

    public void setApenasDiasDeSemana(Boolean apenasDiasDeSemana) {
        this.apenasDiasDeSemana = apenasDiasDeSemana;
    }
}
