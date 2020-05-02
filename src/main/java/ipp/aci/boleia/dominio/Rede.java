package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Representa a tabela de Rede
 */
@Entity
@Audited
@Table(name = "REDE")
public class Rede implements IPersistente, IExclusaoLogica {

    private static final long serialVersionUID = -4064142785379385603L;

    @Id
    @Column(name = "CD_REDE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_REDE")
    @SequenceGenerator(name = "SEQ_REDE", sequenceName = "SEQ_REDE", allocationSize = 1)
    private Long id;

    @OneToMany(mappedBy = "rede", fetch = FetchType.LAZY)
    private List<Usuario> usuarios;

    @OneToMany(mappedBy = "rede", fetch = FetchType.LAZY)
    private List<PontoDeVenda> pontosDeVenda;

    @NotNull
    @Column(name = "NM_REDE")
    private String nomeRede;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeRede() {
        return nomeRede;
    }

    public void setNomeRede(String nomeRede) {
        this.nomeRede = nomeRede;
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

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public List<PontoDeVenda> getPontosDeVenda() {
        return pontosDeVenda;
    }

    public void setPontosDeVenda(List<PontoDeVenda> pontosDeVenda) {
        this.pontosDeVenda = pontosDeVenda;
    }


    /**
     * Monta o nome de apresentacao da Rede
     * @return O nome de apresentacao da Rede em questão
     */
    @Transient
    public String getNomeApresentacao() {
       String codigo = getId().toString();
       codigo += " ";
       String nome = getNomeRede();
       return codigo + nome;
    }
}
