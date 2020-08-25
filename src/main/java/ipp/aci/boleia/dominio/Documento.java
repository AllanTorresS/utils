package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IExclusaoLogicaComData;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.List;

/**
 * Representa um documento associado a uma permissão de um usuário
 */
@Audited
@Entity
@Table(name="DOCUMENTO")
public class Documento implements IPersistente, IExclusaoLogicaComData {

    @Id
    @Column(name = "CD_DOCUMENTO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_DOCUMENTO")
    @SequenceGenerator(name = "SEQ_DOCUMENTO", sequenceName = "SEQ_DOCUMENTO", allocationSize = 1)
    private Long id;

    /**
     * O número de versão de um documento
     */
    @Column(name="NM_VERSAO")
    private String versaoDocumento;

    /**
     * O link para acessar o documento
     */
    @Column(name="NM_LINK")
    private String link;

    /**
     * O valor de um DocumentoTipo
     */
    @Column(name="ID_DOC_TIPO")
    private Integer tipo;

    /**
     * O nome do autor do documento
     */
    @Column(name="NM_AUTOR")
    private String autor;

    /**
     * A data de inclusão do documento
     */
    @Column(name="DT_INCLUSAO")
    private Date dataInclusao;

    /**
     * A data de inicio da vigência do documento
     */
    @Column(name="DT_INICIO")
    private Date dataInicio;

    /**
     * A data de fim da vigência do documento
     */
    @Column(name="DT_FIM")
    private Date dataFim;

    /**
     * O valor de um DocumentoStatus
     */
    @Column(name="ID_DOC_STATUS")
    private Integer status;

    /**
     * Quantidade de total de usuarios que podem aceitar o documento.
     * Este atributo só é utilizado quando o prazo do documento foi encerrado.
     */
    @Column(name = "NO_QTD_TOTAL")
    private Long qtdTotalEsperado;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "documento")
    private List<DocumentoAceite> usuarios;


    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CONTEUDO")
    private DocumentoConteudo conteudo;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Column(name = "DT_EXCLUSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExclusao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_PERFIL")
    private TipoPerfil tipoPerfil;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id=id;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido=excluido;
    }

    @Override
    public Date getDataExclusao() {
        return dataExclusao;
    }

    @Override
    public void setDataExclusao(Date dataExclusao) {
        this.dataExclusao=dataExclusao;
    }

    public String getVersaoDocumento() {
        return versaoDocumento;
    }

    public void setVersaoDocumento(String versaoDocumento) {
        this.versaoDocumento = versaoDocumento;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public Date getDataInclusao() {
        return dataInclusao;
    }

    public void setDataInclusao(Date dataInclusao) {
        this.dataInclusao = dataInclusao;
    }

    public Date getDataInicio() {
        return dataInicio;
    }

    public void setDataInicio(Date dataInicio) {
        this.dataInicio = dataInicio;
    }

    public Date getDataFim() {
        return dataFim;
    }

    public void setDataFim(Date dataFim) {
        this.dataFim = dataFim;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public List<DocumentoAceite> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<DocumentoAceite> usuarios) {
        this.usuarios = usuarios;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Long getQtdTotalEsperado() {
        return qtdTotalEsperado;
    }

    public void setQtdTotalEsperado(Long qtdTotalEsperado) {
        this.qtdTotalEsperado = qtdTotalEsperado;
    }

    public DocumentoConteudo getConteudo() {
        return conteudo;
    }

    public void setConteudo(DocumentoConteudo conteudo) {
        this.conteudo = conteudo;
    }

    public TipoPerfil getTipoPerfil() { return tipoPerfil; }

    public void setTipoPerfil(TipoPerfil tipoPerfil) { this.tipoPerfil = tipoPerfil; }
}
