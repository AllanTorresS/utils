package ipp.aci.boleia.dominio;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ipp.aci.boleia.dominio.enums.StatusAtivacao;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Representa a tabela de Coordenadoria
 */
@Entity
@Audited
@Table(name = "COORDENADORIA")
public class Coordenadoria implements IPersistente {

    private static final long serialVersionUID = 3527677385895776333L;

    @Id
    @Column(name = "CD_COORDENADORIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_COORDENADORIA")
    @SequenceGenerator(name = "SEQ_COORDENADORIA", sequenceName = "SEQ_COORDENADORIA", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "NM_COORDENADORIA")
    private String nome;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(StatusAtivacao.DECODE_FORMULA)
    private String statusConvertido;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario coordenador;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "coordenadoria")
    private List<Usuario> assessores;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusConvertido() {
        return statusConvertido;
    }

    public void setStatusConvertido(String statusConvertido) {
        this.statusConvertido = statusConvertido;
    }

    public Usuario getCoordenador() {
        return coordenador;
    }

    public void setCoordenador(Usuario coordenador) {
        this.coordenador = coordenador;
    }

    public List<Usuario> getAssessores() {
        return assessores;
    }

    public void setAssessores(List<Usuario> assessores) {
        this.assessores = assessores;
    }

    /**
     * Lista os ids das frotas assessoradas pelos assessores da coordenadoria.
     * @return lista com  os ids das frotas assessoradas pelos assessores da coordenadoria.
     */
    @JsonIgnore
    public List<Long> listarFrotas() {
        if (this.assessores == null) {
            return Collections.emptyList();
        }
        return this.assessores.stream().map(Usuario::listarIdsFrotasAssessoradas).flatMap(List::stream).collect(Collectors.toList());
    }
}
