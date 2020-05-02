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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.List;

/**
 * Representa a tabela de Perguntas de Questionario
 */
@Entity
@Audited
@Table(name = "QUEST_PERGUNTA")
public class QuestionarioPergunta implements IPersistente {

    private static final long serialVersionUID = 7570458316574707989L;

    @Id
    @Column(name = "CD_QUEST_PERGUNTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QUEST_PERGUNTA")
    @SequenceGenerator(name = "SEQ_QUEST_PERGUNTA", sequenceName = "SEQ_QUEST_PERGUNTA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_QUEST_GRUPO")
    private QuestionarioGrupo grupo;

    @Column(name = "NO_ORDEM")
    private Integer ordem;

    @Column(name = "DS_PERGUNTA")
    private String descricao;

    @Column(name = "ID_TIPO_RESPOSTA")
    private Integer tipoResposta;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "pergunta")
    private List<QuestionarioPerguntaOpcao> opcao;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name="QUEST_OPCAO_HABL_PERG", joinColumns={@JoinColumn(name="CD_QUEST_PERGUNTA")}, inverseJoinColumns={@JoinColumn(name="CD_QUEST_PERG_OPCAO")})
    private List<QuestionarioPerguntaOpcao> opcaoHabilita;

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

    public QuestionarioGrupo getGrupo() {
        return grupo;
    }

    public void setGrupo(QuestionarioGrupo grupo) {
        this.grupo = grupo;
    }

    public Integer getOrdem() {
        return ordem;
    }

    public void setOrdem(Integer ordem) {
        this.ordem = ordem;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getTipoResposta() {
        return tipoResposta;
    }

    public void setTipoResposta(Integer tipoResposta) {
        this.tipoResposta = tipoResposta;
    }

    public List<QuestionarioPerguntaOpcao> getOpcao() {
        return opcao;
    }

    public void setOpcao(List<QuestionarioPerguntaOpcao> opcao) {
        this.opcao = opcao;
    }

    public List<QuestionarioPerguntaOpcao> getOpcaoHabilita() {
        return opcaoHabilita;
    }

    public void setOpcaoHabilita(List<QuestionarioPerguntaOpcao> opcaoHabilita) {
        this.opcaoHabilita = opcaoHabilita;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}
