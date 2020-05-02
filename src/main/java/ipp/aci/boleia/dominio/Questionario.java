package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.List;

/**
 * Representa a tabela de Questionario
 */
@Entity
@Audited
@Table(name = "QUESTIONARIO")
public class Questionario implements IPersistente {

    private static final long serialVersionUID = -5590268934461744328L;

    @Id
    @Column(name = "CD_QUESTIONARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QUESTIONARIO")
    @SequenceGenerator(name = "SEQ_QUESTIONARIO", sequenceName = "SEQ_QUESTIONARIO", allocationSize = 1)
    private Long id;

    @Column(name = "NM_QUESTIONARIO")
    private String nome;

    @Column(name = "ID_TIPO_QUEST")
    private Integer tipo;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "questionario")
    private List<QuestionarioGrupo> grupo;

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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public List<QuestionarioGrupo> getGrupo() {
        return grupo;
    }

    public void setGrupo(List<QuestionarioGrupo> grupo) {
        this.grupo = grupo;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
