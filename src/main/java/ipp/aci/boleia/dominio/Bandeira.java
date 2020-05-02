package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.enums.TipoBandeira;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa a tabela Bandeira
 */
@Entity
@Audited
@Table(name = "BANDEIRA")
public class Bandeira implements IPersistente {

    private static final long serialVersionUID = -5699371537382188052L;

    @Id
    @Column(name = "CD_BAND")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BANDEIRA")
    @SequenceGenerator(name = "SEQ_BANDEIRA", sequenceName = "SEQ_BANDEIRA", allocationSize = 1)
    private Long id;

    @Max(999)
    @Column(name = "CD_BAND_CORP")
    private Integer codigoCorporativo;

    @Size(max=30)
    @Column(name = "DS_BAND")
    private String descricao;

    @NotNull
    @Column(name = "ID_PROPR_BAND")
    private Boolean propria;

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

    public Boolean getPropria() {
        return propria;
    }

    public void setPropria(Boolean propria) {
        this.propria = propria;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }


    /**
     * Monta o nome de apresentacao de uma Bandeira.
     * @return O nome de apresentacao da Bandeira em questão
     */
    @Transient
    public String getNomeApresentacao() {
        return getDescricao();
    }

    @Transient
    public String getTipoBandeira() {
        return propria != null && propria ? TipoBandeira.PROPRIA.name() : TipoBandeira.NAO_PROPRIA.name();
    }
}
