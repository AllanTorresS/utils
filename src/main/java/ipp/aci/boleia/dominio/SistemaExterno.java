package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusTokenSistemaExterno;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Representa a tabela SISTEMA_EXTERNO.
 */

@Entity
@Audited
@Table(name = "SISTEMA_EXTERNO")
public class SistemaExterno implements IPersistente, IExclusaoLogica {
    private static final long serialVersionUID = 2897493392247832028L;

    @Id
    @Column(name = "CD_SISTEMA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_SISTEMA_EXTERNO")
    @SequenceGenerator(name = "SEQ_SISTEMA_EXTERNO", sequenceName = "SEQ_SISTEMA_EXTERNO", allocationSize = 1)
    private Long id;

    @AuditJoinTable(name = "SIS_EXTERNO_PERMISSAO_AUD")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "SIS_EXTERNO_PERMISSAO",
            joinColumns = @JoinColumn(name = "CD_SISTEMA", referencedColumnName = "CD_SISTEMA"),
            inverseJoinColumns = @JoinColumn(name = "CD_PERMISSAO", referencedColumnName = "CD_PERMISSAO"))
    private List<Permissao> permissoes;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_SISTEMA")
    private String nomeSistema;

    @NotNull
    @Max(99999999999999L)
    @Column(name = "CD_CNPJ")
    private Long cnpj;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_CLIENT")
    private String client;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_SECRET")
    private String secret;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_CONTATO")
    private String nomeContato;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_EMAIL")
    private String email;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(StatusTokenSistemaExterno.DECODE_FORMULA)
    private String statusConvertido;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;


    public String getNomeSistema() {
        return nomeSistema;
    }

    public void setNomeSistema(String nomeSistema) {
        this.nomeSistema = nomeSistema;
    }

    public String getClient() {
        return client;
    }

    public void setClient(String client) {
        this.client = client;
    }

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Long getCnpj() {
        return cnpj;
    }

    public void setCnpj(Long cnpj) {
        this.cnpj = cnpj;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getNomeContato() {
        return nomeContato;
    }

    public void setNomeContato(String nomeContato) {
        this.nomeContato = nomeContato;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public String getStatusConvertido() {
        return statusConvertido;
    }

    public void setStatusConvertido(String statusConvertido) {
        this.statusConvertido = statusConvertido;
    }
}