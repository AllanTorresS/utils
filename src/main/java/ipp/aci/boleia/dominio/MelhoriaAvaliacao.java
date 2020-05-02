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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Representa a tabela de Melhoria Avaliacao
 */
@Entity
@Audited
@Table(name = "MELHORIA_AVALIACAO")
public class MelhoriaAvaliacao implements IPersistente {

    private static final long serialVersionUID = 7120408226324110680L;

    @Id
    @Column(name = "CD_MELHORIA_AVALIACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MELHORIA_AVALIACAO")
    @SequenceGenerator(name = "SEQ_MELHORIA_AVALIACAO", sequenceName = "SEQ_MELHORIA_AVALIACAO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AVALIACAO_ABASTECIMENTO")
    private AvaliacaoAbastecimento avaliacaoAbastecimento;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_MELHORIA")
    private TipoMelhoria tipoMelhoria;

    @Column(name = "DS_MELHORIA")
    private String descricao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public AvaliacaoAbastecimento getAvaliacaoAbastecimento() {
        return avaliacaoAbastecimento;
    }

    public void setAvaliacaoAbastecimento(AvaliacaoAbastecimento avaliacaoAbastecimento) {
        this.avaliacaoAbastecimento = avaliacaoAbastecimento;
    }
    public TipoMelhoria getTipoMelhoria() {

        return tipoMelhoria;
    }

    public void setTipoMelhoria(TipoMelhoria tipoMelhoria) {
        this.tipoMelhoria = tipoMelhoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
