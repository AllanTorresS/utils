package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.DocumentoSistema;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa a relação de aceite de um usuario a um documento
 */
@Audited
@Entity
@Table(name="DOCUMENTO_ACEITE")
public class DocumentoAceite implements IPersistente {

    @Id
    @Column(name = "CD_DOCUMENTO_ACEITE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DOCUMENTO_ACEITE")
    @SequenceGenerator(name = "SEQ_DOCUMENTO_ACEITE", sequenceName = "SEQ_DOCUMENTO_ACEITE", allocationSize = 1)
    private Long id;

    /**
     * Representa se o usuario aceitou o acordo do documento
     */
    @Column(name = "ID_USUARIO_ACEITOU")
    private Boolean usuarioAceitou;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_DOCUMENTO")
    private Documento documento;

    @NotNull
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Column(name = "DT_ACEITE")
    private Date dataAceite;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @NotNull
    @Column(name = "ID_DOC_SISTEMA")
    private Integer sistema;

    @NotAudited
    @Formula(DocumentoSistema.DECODE_FORMULA)
    private String sistemaConvertido;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    public Boolean getUsuarioAceitou() {
        return usuarioAceitou;
    }

    public void setUsuarioAceitou(Boolean usuarioAceitou) {
        this.usuarioAceitou = usuarioAceitou;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Date getDataAceite() {
        return dataAceite;
    }

    public void setDataAceite(Date dataAceite) {
        this.dataAceite = dataAceite;
    }

    public Integer getSistema() { return sistema; }

    public void setSistema(Integer sistema) { this.sistema = sistema; }

    public String getSistemaConvertido() { return sistemaConvertido; }

    public void setSistemaConvertido(String sistemaConvertido) { this.sistemaConvertido = sistemaConvertido; }
}
