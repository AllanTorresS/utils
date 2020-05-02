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
import javax.persistence.Version;

/**
 * Representa a tabela de Banco
 */
@Entity
@Audited
@Table(name = "BANCO")
public class Banco implements IPersistente {

    private static final long serialVersionUID = 3178200191469588041L;

    @Id
    @Column(name = "CD_BANCO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BANCO")
    @SequenceGenerator(name = "SEQ_BANCO", sequenceName = "SEQ_BANCO", allocationSize = 1)
    private Long id;

    @Column(name = "CD_INSTITUICAO")
    private String codigoInstituicao;

    @Column(name = "NM_BANCO")
    private String nome;

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

    public String getCodigoInstituicao() {
        return codigoInstituicao;
    }

    public void setCodigoInstituicao(String codigoInstituicao) {
        this.codigoInstituicao = codigoInstituicao;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }
}