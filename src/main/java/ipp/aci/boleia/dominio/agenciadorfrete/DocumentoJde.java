package ipp.aci.boleia.dominio.agenciadorfrete;

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
 * Representa a tabela de Documento do Jde
 */
@Entity
@Audited
@Table(name = "AG_DOC_JDE")
public class DocumentoJde implements IPersistente {

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

    @Column(name = "QT_PARCELAS")
    private Integer quantidadeParcelas;

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

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public Integer getNumeroTentativasEnvio() {
        return numeroTentativasEnvio;
    }

    public void setNumeroTentativasEnvio(Integer numeroTentativasEnvio) {
        this.numeroTentativasEnvio = numeroTentativasEnvio;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Long getId() {
        return null;
    }

    @Override
    public void setId(Long id) {

    }

    public Integer getQuantidadeParcelas() {
        return quantidadeParcelas;
    }

    public void setQuantidadeParcelas(Integer quantidadeParcelas) {
        this.quantidadeParcelas = quantidadeParcelas;
    }
}
