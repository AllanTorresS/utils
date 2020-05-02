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
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Grupo Economico
 */
@Entity
@Audited
@Table(name = "GRUPO_ECONOMICO")
public class GrupoEconomico implements IPersistente {

    private static final long serialVersionUID = 9057646579756150393L;

    @Id
    @Column(name = "CD_GRUPO_ECON")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GRUPO_ECONOMICO")
    @SequenceGenerator(name = "SEQ_GRUPO_ECONOMICO", sequenceName = "SEQ_GRUPO_ECONOMICO", allocationSize = 1)
    private Long id;

    @Max(99999)
    @Column(name = "CD_GRUPO_ECON_CORP")
    private Integer codigoCorporativo;

    @Size(max=150)
    @Column(name = "DS_GRUPO_ECON")
    private String descricao;

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

    public Integer getCodigoCorporativo() {
        return codigoCorporativo;
    }

    public void setCodigoCorporativo(Integer codigoCorporativo) {
        this.codigoCorporativo = codigoCorporativo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    /**
     * Monta o nome de apresentacao de um Grupo Econômico.
     * @return O nome de apresentacao do Grupo em questão
     */
    @Transient
    public String getNomeApresentacao() {
        return getDescricao();
    }
}
