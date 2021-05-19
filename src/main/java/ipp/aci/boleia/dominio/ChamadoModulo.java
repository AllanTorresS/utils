package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * Representa a tabela de Permissao
 */
@Entity
@Audited
@Table(name = "CHAMADO_MODULO")
public class ChamadoModulo implements IPersistente {

	private static final long serialVersionUID = -6226038120046024032L;

    @Id
    @Column(name = "CD_CHAMADO_MODULO")
    private Long id;

    @NotNull
    @Size(max=50)
    @Column(name = "NM_MODULO")
    private String nome;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    @JoinTable(name = "CHAMADO_SISTEMA_MODULO",
            joinColumns = @JoinColumn(name = "CD_CHAMADO_MODULO", referencedColumnName = "CD_CHAMADO_MODULO"),
            inverseJoinColumns = @JoinColumn(name = "CD_CHAMADO_SISTEMA", referencedColumnName = "CD_CHAMADO_SISTEMA"))
    private List<ChamadoSistema> sistemas;

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

    public List<ChamadoSistema> getSistemas() {
        return sistemas;
    }

    public void setSistemas(List<ChamadoSistema> sistemas) {
        this.sistemas = sistemas;
    }
}