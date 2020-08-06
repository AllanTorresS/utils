package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Set;

@Entity
@Audited
@Table(name = "TAREFA_AGENDADA")
public class TarefaAgendada implements IPersistente {

    @Id
    @Column(name = "CD_TAREFA_AGENDADA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TAREFA_AGENDADA")
    @SequenceGenerator(name = "SEQ_TAREFA_AGENDADA", sequenceName = "SEQ_TAREFA_AGENDADA", allocationSize = 1)
    private Long id;

    @Column(name = "CD_ROTINA")
    private Integer idRotina;

    @Column(name = "ID_STATUS")
    private Integer status;

    @ManyToMany(fetch = FetchType.EAGER)
    @NotAudited
    @JoinTable(name = "CONCORRENCIA_TAREFA", joinColumns = {@JoinColumn(name = "CD_TAREFA")}, inverseJoinColumns = {@JoinColumn(name = "CD_TAREFA_CONCORRENTE")})
    private Set<TarefaAgendada> tarefasConcorrentes;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tarefaAgendada", fetch = FetchType.EAGER)
    private Set<FrequenciaTarefa> frequencias;

    public TarefaAgendada() {
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Integer getIdRotina() {
        return idRotina;
    }

    public void setIdRotina(Integer idRotina) {
        this.idRotina = idRotina;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Set<TarefaAgendada> getTarefasConcorrentes() {
        return tarefasConcorrentes;
    }

    public void setTarefasConcorrentes(Set<TarefaAgendada> tarefasConcorrentes) {
        this.tarefasConcorrentes = tarefasConcorrentes;
    }

    public Set<FrequenciaTarefa> getFrequencias() {
        return frequencias;
    }

    public void setFrequencias(Set<FrequenciaTarefa> frequencias) {
        this.frequencias = frequencias;
    }
}
