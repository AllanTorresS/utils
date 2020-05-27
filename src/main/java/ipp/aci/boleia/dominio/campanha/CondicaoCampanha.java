package ipp.aci.boleia.dominio.campanha;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.CascadeType;
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
import java.util.List;
import java.util.stream.Collectors;


/**
 * Representa a tabela de Condições de uma Campanha
 */
@Audited
@Entity
@Table(name = "CONDICAO_CAMPANHA", schema = "BOLEIA_CAMPANHA_SCHEMA")
public class CondicaoCampanha implements IPersistente, Cloneable {

    private static final long serialVersionUID = -3723808629907149757L;

    @Id
    @Column(name = "CD_CONDICAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CONDICAO_CAMPANHA")
    @SequenceGenerator(name = "SEQ_CONDICAO_CAMPANHA", sequenceName = "BOLEIA_CAMPANHA_SCHEMA.SEQ_CONDICAO_CAMPANHA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CAMPANHA")
    private Campanha campanha;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_CONDICAO")
    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    private TipoCondicaoCampanha tipoCondicaoCampanha;

    @Column(name = "ID_OPERACAO")
    private Integer tipoOperacao;

    //O mapeamento como EAGER se deve ao fato de os respectivos valores sempre serem necessários ao se buscar uma condição
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "condicao", fetch = FetchType.EAGER, orphanRemoval = true)
    private List<ValorCondicao> valores;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Campanha getCampanha() {
        return campanha;
    }

    public void setCampanha(Campanha campanha) {
        this.campanha = campanha;
    }

    public TipoCondicaoCampanha getTipoCondicaoCampanha() {
        return tipoCondicaoCampanha;
    }

    public void setTipoCondicaoCampanha(TipoCondicaoCampanha tipoCondicaoCampanha) {
        this.tipoCondicaoCampanha = tipoCondicaoCampanha;
    }

    public Integer getTipoOperacao() {
        return tipoOperacao;
    }

    public void setTipoOperacao(Integer tipoOperacao) {
        this.tipoOperacao = tipoOperacao;
    }

    public List<ValorCondicao> getValores() {
        return valores;
    }

    public void setValores(List<ValorCondicao> valores) {
        this.valores = valores;
    }

    /**
     * Método que compara duas condições de campanha, segundo a regra de negócio de equivalências entre condições
     * @param condicao objeto de condição a ser comparado com a instância atual do objeto
     * @return se os objetos são ou não iguais
     */
    public boolean comparar(CondicaoCampanha condicao) {
        if (this == condicao) return true;
        if (condicao == null) return false;

        Boolean tipoOperacao = this.getTipoOperacao().equals(condicao.getTipoOperacao());
        Boolean tipoCondicao = this.getTipoCondicaoCampanha().getId().equals(condicao.getTipoCondicaoCampanha().getId());
        Boolean valoresCorrespondem = this.getValores().stream().anyMatch(v -> condicao.getValores().stream().filter(v2 -> v.getValor().equals(v2.getValor())).count() > 0)
                || condicao.getValores().stream().anyMatch(v -> this.getValores().stream().filter(v2 -> v.getValor().equals(v2.getValor())).count() > 0);


        return tipoOperacao && tipoCondicao && valoresCorrespondem;
    }

    /**
     * Copia uma condição de campanha
     *
     * @return Uma cópia da condição
     */
    public CondicaoCampanha clone() {
        CondicaoCampanha copiaCondicao = new CondicaoCampanha();
        copiaCondicao.setTipoCondicaoCampanha(this.getTipoCondicaoCampanha());
        copiaCondicao.setTipoOperacao(this.getTipoOperacao());
        if (this.getValores() != null) {
            copiaCondicao.setValores(this.getValores().stream().map(v -> v.clone()).collect(Collectors.toList()));
            copiaCondicao.getValores().stream().forEach(v -> v.setCondicao(copiaCondicao));
        }
        return copiaCondicao;
    }
}
