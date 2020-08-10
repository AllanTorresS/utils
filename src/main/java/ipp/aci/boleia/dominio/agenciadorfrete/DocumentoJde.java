package ipp.aci.boleia.dominio.agenciadorfrete;

import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;

@Entity
@Audited
@Table(name = "AG_DOC_JDE")
public class DocumentoJde {

    @Id
    @Column(name = "CD_DOCUMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AG_DOC_JDE")
    @SequenceGenerator(name = "SEQ_AG_DOC_JDE", sequenceName = "SEQ_AG_DOC_JDE", allocationSize = 1)
    private Long id;

    @Column(name = "NO_DOC")
    private Long numero;

    @Column(name = "ID_TIPO")
    private String tipo;

    @Column(name = "NM_CIA")
    private String cia;

    @Column(name = "ID_STATUS")
    private Integer status;

    @Column(name = "DS_MSG_ERRO")
    private String mensagemErro;

    @Column(name = "NO_TENTATIVAS_ENVIO")
    private Integer numeroTentativasEnvio;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    public Long getNumero() {
        return numero;
    }

    public void setNumeroDocumento(Long numero) {
        this.numero = numero;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getCia() {
        return cia;
    }

    public void setCia(String cia) {
        this.cia = cia;
    }
}
