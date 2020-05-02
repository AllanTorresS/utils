package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Representa a tabela de Tipo Melhoria
 */
@Audited
@Entity
@Table(name = "TIPO_MELHORIA")
public class TipoMelhoria implements IPersistente {

    private static final long serialVersionUID = 4225848308762927317L;

    private static final String OUTRO = "OUTRO";

    @Id
    @Column(name = "CD_TIPO_MELHORIA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_MELHORIA")
    @SequenceGenerator(name = "SEQ_TIPO_MELHORIA", sequenceName = "SEQ_TIPO_MELHORIA", allocationSize = 1)
    private Long id;

    @Column(name = "DS_TIPO_MELHORIA")
    private String descricao;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Column(name = "ID_DESCRICAO")
    private Boolean permiteDescricao;


    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Boolean getExcluido() {
        return excluido;
    }

    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Boolean getPermiteDescricao() {
        return permiteDescricao;
    }

    public void setPermiteDescricao(Boolean permiteDescricao) {
        this.permiteDescricao = permiteDescricao;
    }

    @Transient
    public boolean isOutro() {
        return this.descricao != null && OUTRO.equalsIgnoreCase(this.descricao);
    }
}
