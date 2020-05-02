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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Avaliacao Abastecimento
 */
@Entity
@Audited
@Table(name = "AVALIACAO_ABASTECIMENTO")
public class AvaliacaoAbastecimento implements IPersistente {

    private static final long serialVersionUID = 4395479161264162331L;

    @Id
    @Column(name = "CD_AVALIACAO_ABASTECIMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AVALIACAO_ABASTECIMENTO")
    @SequenceGenerator(name = "SEQ_AVALIACAO_ABASTECIMENTO", sequenceName = "SEQ_AVALIACAO_ABASTECIMENTO", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AUTORIZACAO_PAGAMENTO")
    private AutorizacaoPagamento nota;

    @Column(name = "VA_NOTA_AVALIACAO")
    private Integer notaAvaliacao;

    @Column(name= "DT_AVALIACAO")
    private Date dataAvaliacao;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "avaliacaoAbastecimento")
    private List<MelhoriaAvaliacao> melhoriaAvaliacao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public AutorizacaoPagamento getNota() {
        return nota;
    }

    public void setNota(AutorizacaoPagamento nota) {
        this.nota = nota;
    }

    public Integer getNotaAvaliacao() {
        return notaAvaliacao;
    }

    public void setNotaAvaliacao(Integer notaAvaliacao) {
        this.notaAvaliacao = notaAvaliacao;
    }

    public List<MelhoriaAvaliacao> getMelhoriaAvaliacao() {
        return melhoriaAvaliacao;
    }

    public void setMelhoriaAvaliacao(List<MelhoriaAvaliacao> melhoriaAvaliacao) {
        this.melhoriaAvaliacao = melhoriaAvaliacao;
    }

    public Date getDataAvaliacao() {
        return dataAvaliacao;
    }

    public void setDataAvaliacao(Date dataAvaliacao) {
        this.dataAvaliacao = dataAvaliacao;
    }
}
