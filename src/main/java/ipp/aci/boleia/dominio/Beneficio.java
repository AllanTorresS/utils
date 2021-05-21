package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de benefícios
 */
@Entity
@Table(name = "BENEFICIO")
public class Beneficio implements IPersistente {

    @Id
    @Column(name = "CD_BENEFICIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BENEFICIOS")
    @SequenceGenerator(name = "SEQ_BENEFICIOS", sequenceName = "SEQ_BENEFICIOS", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "NM_BENEFICIO")
    private String nome;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @OneToMany(mappedBy = "beneficio", fetch = FetchType.LAZY)
    private List<ContaBeneficio> contasBeneficio;

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

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public List<ContaBeneficio> getContasBeneficio() {
        return contasBeneficio;
    }

    public void setContasBeneficio(List<ContaBeneficio> contasBeneficio) {
        this.contasBeneficio = contasBeneficio;
    }
}