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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa a tabela de Notificacao
 */
@Entity
@Audited
@Table(name = "NOTIFICACAO")
public class Notificacao implements IPersistente {

    private static final long serialVersionUID = 1350864176482436049L;

    @Id
    @Column(name = "CD_NOTIFICACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_NOTIFICACAO")
    @SequenceGenerator(name = "SEQ_NOTIFICACAO", sequenceName = "SEQ_NOTIFICACAO", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max=500)
    @Column(name = "DS_TITULO")
    private String titulo;

    @NotNull
    @Size(max=1000)
    @Column(name = "DS_LINK")
    private String link;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_SUBCATEGORIA")
    private SubcategoriaNotificacao subcategoria;

    @Column(name = "DT_ENVIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataEnvio;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "CD_AUTORIZACAO_PAGAMENTO")
    private Long idAutorizacaoPagamento;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public SubcategoriaNotificacao getSubcategoria() {
        return subcategoria;
    }

    public void setSubcategoria(SubcategoriaNotificacao subcategoria) {
        this.subcategoria = subcategoria;
    }

    public Date getDataEnvio() {
        return dataEnvio;
    }

    public void setDataEnvio(Date dataEnvio) {
        this.dataEnvio = dataEnvio;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Long getIdAutorizacaoPagamento() {
        return idAutorizacaoPagamento;
    }

    public void setIdAutorizacaoPagamento(Long idAutorizacaoPagamento) {
        this.idAutorizacaoPagamento = idAutorizacaoPagamento;
    }
}
