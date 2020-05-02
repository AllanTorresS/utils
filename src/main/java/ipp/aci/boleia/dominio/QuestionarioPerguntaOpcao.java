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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import java.util.List;

/**
 * Representa a tabela de Opcao de Pergunta do Questionario
 */
@Entity
@Audited
@Table(name = "QUEST_PERG_OPCAO")
public class QuestionarioPerguntaOpcao implements IPersistente {

    private static final long serialVersionUID = 5012154520843562530L;

    @Id
    @Column(name = "CD_QUEST_PERG_OPCAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_QUEST_PERG_OPCAO")
    @SequenceGenerator(name = "SEQ_QUEST_PERG_OPCAO", sequenceName = "SEQ_QUEST_PERG_OPCAO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_QUEST_PERGUNTA")
    private QuestionarioPergunta pergunta;

    @Column(name = "NO_ORDEM")
    private Integer ordem;

    @Column(name = "DS_OPCAO")
    private String descricao;

    @Column(name = "ID_DEFAULT")
    private Boolean opcaoDefault;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_SERVICO")
    private Servico servico;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "QUEST_RESP_PTOV", joinColumns = {@JoinColumn(name = "CD_QUEST_PERG_OPCAO")}, inverseJoinColumns = {@JoinColumn(name = "CD_PTOV")})
    private List<PontoDeVenda> pontosDeVenda;

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

    public QuestionarioPergunta getPergunta() {
        return pergunta;
    }

    public void setPergunta(QuestionarioPergunta pergunta) {
        this.pergunta = pergunta;
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

    public Boolean getOpcaoDefault() {
        return opcaoDefault;
    }

    public void setOpcaoDefault(Boolean opcaoDefault) {
        this.opcaoDefault = opcaoDefault;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Servico getServico() {
        return servico;
    }

    public void setServico(Servico servico) {
        this.servico = servico;
    }

    public List<PontoDeVenda> getPontosDeVenda() {
        return pontosDeVenda;
    }

    public void setPontosDeVenda(List<PontoDeVenda> pontosDeVenda) {
        this.pontosDeVenda = pontosDeVenda;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (this.getClass() != obj.getClass()) {
            return false;
        }
        QuestionarioPerguntaOpcao op = (QuestionarioPerguntaOpcao) obj;
        return this.id.equals(op.getId());
    }

    @Override
    public int hashCode() {
        return (this.id == null ? 0 : this.id.hashCode());
    }
}
