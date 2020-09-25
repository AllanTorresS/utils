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

import java.util.List;

/**
 * Representa a tabela de Plano de Viagem
 */
@Entity
@Audited
@Table(name = "PLANO_VIAGEM")
public class PlanoViagem implements IPersistente, IExclusaoLogica {

    private static final long serialVersionUID = -7307959267296169290L;

    @Id
    @Column(name = "CD_PLANO_VIAGEM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PLANO_VIAGEM")
    @SequenceGenerator(name = "SEQ_PLANO_VIAGEM", sequenceName = "SEQ_PLANO_VIAGEM", allocationSize = 1)
    private Long id;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @OneToMany(mappedBy = "planoViagem", fetch = FetchType.LAZY)
    private List<Rota> rotas;

    public PlanoViagem() { }

    public PlanoViagem(Long id, List<Rota> rotas) {
        this.id = id;
        this.rotas = rotas;
    }

    public PlanoViagem(Long id, Boolean excluido, List<Rota> rotas) {
        this.id = id;
        this.excluido = excluido;
        this.rotas = rotas;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<Rota> getRotas() {
        return rotas;
    }

    public void setRotas(List<Rota> rotas) {
        this.rotas = rotas;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }
}
