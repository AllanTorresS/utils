package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import ipp.aci.boleia.dominio.interfaces.IPertenceRevendedor;
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
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.List;

/**
 * Representa a tabela de Gap Servico
 */
@Entity
@Audited
@Table(name = "GAP_SERVICO")
public class GapServico implements IPersistente, IPertenceFrota, IPertenceRevendedor {

    private static final long serialVersionUID = 5767205373332254085L;

    @Id
    @Column(name = "CD_GAP_SERVICO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_GAP_SERVICO")
    @SequenceGenerator(name = "SEQ_GAP_SERVICO", sequenceName = "SEQ_GAP_SERVICO", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda pontoVenda;

    @NotAudited
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "GAP_SERVICO_PONTO_VENDA",
            joinColumns = @JoinColumn(name = "CD_GAP_SERVICO", referencedColumnName = "CD_GAP_SERVICO"),
            inverseJoinColumns = @JoinColumn(name = "CD_SERVICO", referencedColumnName = "CD_SERVICO"))
    private List<Servico> servicos;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public PontoDeVenda getPontoVenda() {
        return pontoVenda;
    }

    public void setPontoVenda(PontoDeVenda pontoVenda) {
        this.pontoVenda = pontoVenda;
    }

    public List<Servico> getServicos() {
        return servicos;
    }

    public void setServicos(List<Servico> servicos) {
        this.servicos = servicos;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return usuario!=null ? usuario.getFrotas() : null;
    }

    @Transient
    @Override
    public List<PontoDeVenda> getPontosDeVenda() {
        return this.pontoVenda!=null ? Collections.singletonList(this.pontoVenda) : null;
    }
}
