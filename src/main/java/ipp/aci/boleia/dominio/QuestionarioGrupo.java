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
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.List;

/**
 * Representa a tabela de Grupo de Questionario
 */
@Entity
@Audited
@Table(name = "QUEST_GRUPO")
public class QuestionarioGrupo implements IPersistente {

    private static final long serialVersionUID = -1624384173116243353L;

    @Id
    @Column(name = "CD_QUEST_GRUPO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QUEST_GRUPO")
    @SequenceGenerator(name = "SEQ_QUEST_GRUPO", sequenceName = "SEQ_QUEST_GRUPO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_QUESTIONARIO")
    private Questionario questionario;

    @Column(name = "NM_QUEST_GRUPO")
    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "grupo")
    private List<QuestionarioPergunta> pergunta;

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

    public Questionario getQuestionario() {
        return questionario;
    }

    public void setQuestionario(Questionario questionario) {
        this.questionario = questionario;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<QuestionarioPergunta> getPergunta() {
        return pergunta;
    }

    public void setPergunta(List<QuestionarioPergunta> pergunta) {
        this.pergunta = pergunta;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
