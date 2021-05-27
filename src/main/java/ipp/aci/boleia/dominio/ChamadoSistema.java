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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import javax.persistence.CascadeType;

/**
 * Representa a tabela de Permissao
 */
@Entity
@Audited
@Table(name = "CHAMADO_SISTEMA")
public class ChamadoSistema implements IPersistente {
	private static final long serialVersionUID = -6226038120046024032L;

    @Id
    @Column(name = "CD_CHAMADO_SISTEMA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CHAMADO_SISTEMA")
    @SequenceGenerator(name = "SEQ_CHAMADO_SISTEMA", sequenceName = "SEQ_CHAMADO_SISTEMA", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_SISTEMA")
    private String nome;

    @NotNull
    @Column(name = "ID_FROTISTA")
    private boolean portalFrotista;

    @NotNull
    @Column(name = "ID_REVENDEDOR")
    private boolean portalRevendedor;

    @NotNull
    @Column(name = "ID_SOLUCAO")
    private boolean portalSolucao;

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

    public boolean isPortalFrotista() {
        return portalFrotista;
    }

    public void setPortalFrotista(boolean portalFrotista) {
        this.portalFrotista = portalFrotista;
    }

    public boolean isPortalRevendedor() {
        return portalRevendedor;
    }

    public void setPortalRevendedor(boolean portalRevendedor) {
        this.portalRevendedor = portalRevendedor;
    }

    public boolean isPortalSolucao() {
        return portalSolucao;
    }

    public void setPortalSolucao(boolean portalSolucao) {
        this.portalSolucao = portalSolucao;
    }

}