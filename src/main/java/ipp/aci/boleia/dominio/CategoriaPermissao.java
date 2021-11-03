package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Representa a tabela de categoria de permissão
 */
@Audited
@Entity
@Table(name = "CATEGORIA_PERMISSAO")
public class CategoriaPermissao implements IPersistente {

    private static final long serialVersionUID = -1051361534459041571L;

    @Id
    @Column(name = "CD_CATEGORIA_PERMISSAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CATEGORIA_PERMISSAO")
    @SequenceGenerator(name = "SEQ_CATEGORIA_PERMISSAO", sequenceName = "SEQ_CATEGORIA_PERMISSAO", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_CATEGORIA")
    private String nome;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "categoria")
    private List<Permissao> permissoes;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

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
}
