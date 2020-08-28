package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Representa o conteúdo de um documento associado a uma permissão de um usuário
 */
@Audited
@Entity
@Table(name="DOCUMENTO_CONTEUDO")
public class DocumentoConteudo implements IPersistente {

    @Id
    @Column(name = "CD_DOC_CONTEUDO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DOC_CONTEUDO")
    @SequenceGenerator(name = "SEQ_DOC_CONTEUDO", sequenceName = "SEQ_DOC_CONTEUDO", allocationSize = 1)
    private Long id;

    @Lob
    @Column(name = "NM_TEXTO")
    private String texto;

    @OneToOne
    @JoinColumn(name = "CD_DOCUMENTO")
    private Documento documento;

    public DocumentoConteudo() { }

    public DocumentoConteudo(String texto) {
        this.texto = texto;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public Documento getDocumento() {
        return documento;
    }

    public void setDocumento(Documento documento) {
        this.documento = documento;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }
}
