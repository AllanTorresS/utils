package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
import org.apache.commons.collections4.CollectionUtils;
import org.hibernate.envers.AuditJoinTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Perfil
 */
@Audited
@Entity
@Table(name = "PERFIL")
public class Perfil implements IPersistente, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = -1051361534459041571L;

    @Id
    @Column(name = "CD_PERFIL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PERFIL")
    @SequenceGenerator(name = "SEQ_PERFIL", sequenceName = "SEQ_PERFIL", allocationSize = 1)
    private Long id;

    @NotAudited
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_PERFIL")
    private TipoPerfil tipoPerfil;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_REDE")
    private Rede rede;

    @NotNull
    @Size(max=80)
    @Column(name = "NM_PERFIL")
    private String nome;

    @AuditJoinTable(name = "PERFIL_PERMISSAO_AUD")
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "PERFIL_PERMISSAO",
            joinColumns = @JoinColumn(name = "CD_PERFIL"),
            inverseJoinColumns = @JoinColumn(name = "CD_PERMISSAO"))
    private List<Permissao> permissoes;

    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "USUARIO_PERFIL",
            joinColumns = @JoinColumn(name = "CD_PERFIL", referencedColumnName = "CD_PERFIL"),
            inverseJoinColumns = @JoinColumn(name = "CD_USUARIO", referencedColumnName = "CD_USUARIO"))
    private List<Usuario> usuarios;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "ID_TEMPLATE")
    private Boolean template;

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Rede getRede() {
        return rede;
    }

    public void setRede(Rede rede) {
        this.rede = rede;
    }

    public Boolean getTemplate() {
        return template;
    }

    public void setTemplate(Boolean template) {
        this.template = template;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public TipoPerfil getTipoPerfil() {
        return tipoPerfil;
    }

    public void setTipoPerfil(TipoPerfil tipoPerfil) {
        this.tipoPerfil = tipoPerfil;
    }

    public List<Permissao> getPermissoes() {
        return permissoes;
    }

    public void setPermissoes(List<Permissao> permissoes) {
        this.permissoes = permissoes;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return rede.getPontosDeVenda();
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    /**
     * Cria uma instancia de perfil a partir de um template
     * @param template O template
     * @return O perfil instanciado
     */
    public static Perfil instanciarTemplate(Perfil template) {
        Perfil instancia = new Perfil();
        instancia.setNome(template.getNome());
        instancia.setTipoPerfil(template.getTipoPerfil());
        instancia.setPermissoes(instanciarPermissoesTemplate(template));
        instancia.setTemplate(false);
        return instancia;
    }

    /**
     * Instancia uma lista de permissoes de acordo com o template informado
     * @param template O template
     * @return A lista de permissoes
     */
    private static List<Permissao> instanciarPermissoesTemplate(Perfil template) {
        List<Permissao> instancias = new ArrayList<>();
        List<Permissao> permissoes = template.getPermissoes();
        if(!CollectionUtils.isEmpty(permissoes)) {
            instancias.addAll(permissoes);
        }
        return instancias;
    }
}
