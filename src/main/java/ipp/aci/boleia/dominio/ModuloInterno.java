package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusTokenSistemaExterno;
import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.SequenceGenerator;
import javax.persistence.ManyToMany;
import javax.persistence.FetchType;
import javax.persistence.CascadeType;
import javax.persistence.JoinTable;
import javax.persistence.JoinColumn;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Representa a tabela MODULO_INTERNO.
 */

@Entity
@Audited
@Table(name = "MODULO_INTERNO")
public class ModuloInterno implements IPersistente, IExclusaoLogica {
    private static final long serialVersionUID = 2897493392247832028L;

    @Id
    @Column(name = "CD_MODULO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MODULO_INTERNO")
    @SequenceGenerator(name = "SEQ_MODULO_INTERNO", sequenceName = "SEQ_MODULO_INTERNO", allocationSize = 1)
    private Long id;

    @AuditJoinTable(name = "MOD_INTERNO_PERMISSAO_AUD")
    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "MOD_INTERNO_PERMISSAO",
            joinColumns = @JoinColumn(name = "CD_MODULO", referencedColumnName = "CD_MODULO"),
            inverseJoinColumns = @JoinColumn(name = "CD_PERMISSAO", referencedColumnName = "CD_PERMISSAO"))
    private List<Permissao> permissoes;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_MODULO")
    private String nomeModulo;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_CLIENT")
    private String client;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_CLIENT_SALT")
    private String clientSalt;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_SECRET")
    private String secret;

    @NotNull
    @Size(max=250)
    @Column(name = "DS_SECRET_SALT")
    private String secretSalt;

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


    public String getNomeModulo() {
        return nomeModulo;
    }

    public void setNomeModulo(String nomeModulo) {
        this.nomeModulo = nomeModulo;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getClientSalt() {
        return clientSalt;
    }

    public void setClientSalt(String clientSalt) {
        this.clientSalt = clientSalt;
    }

    public String getSecretSalt() {
        return secretSalt;
    }

    public void setSecretSalt(String secretSalt) {
        this.secretSalt = secretSalt;
    }
}