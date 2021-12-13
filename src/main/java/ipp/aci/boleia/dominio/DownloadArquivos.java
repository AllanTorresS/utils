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
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa a tabela de Download de arquivos em lote
 */
@Audited
@Entity
@Table(name = "DOWNLOAD_ARQUIVOS")
public class DownloadArquivos implements IPersistente {

    private static final long serialVersionUID = 3050921001310015191L;

    @Id
    @Column(name = "CD_DOWNLOAD_ARQUIVOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DOWNLOAD_ARQUIVOS")
    @SequenceGenerator(name = "SEQ_DOWNLOAD_ARQUIVOS", sequenceName = "SEQ_DOWNLOAD_ARQUIVOS", allocationSize = 1)
    private Long id;

    @Column(name = "CHAVE_IDENTIFICADORA")
    private String chaveIdentificadora;

    @Column(name = "DESCRICAO")
    private String descricao;

    @Column(name = "NOME_ARQUIVO")
    private String nomeArquivo;

    @Column(name = "TIPO_ARQUIVO")
    private String tipoArquivo;

    @Column(name = "FORMATO")
    private String formato;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Column(name = "DT_REQUISICAO")
    @NotNull
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataRequisicao;


    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getChaveIdentificadora() {
        return chaveIdentificadora;
    }

    public void setChaveIdentificadora(String chaveIdentificadora) {
        this.chaveIdentificadora = chaveIdentificadora;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getNomeArquivo() {
        return nomeArquivo;
    }

    public void setNomeArquivo(String nomeArquivo) {
        this.nomeArquivo = nomeArquivo;
    }

    public String getTipoArquivo() {
        return tipoArquivo;
    }

    public void setTipoArquivo(String tipoArquivo) {
        this.tipoArquivo = tipoArquivo;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public String getFormato() {
        return formato;
    }

    public void setFormato(String formato) {
        this.formato = formato;
    }
}
