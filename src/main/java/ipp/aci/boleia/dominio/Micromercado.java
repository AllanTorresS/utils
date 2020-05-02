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
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Representa a tabela de Micromercado
 */
@Entity
@Audited
@Table(name = "MICROMERCADO")
public class Micromercado implements IPersistente {

    private static final long serialVersionUID = -687034162245256999L;

    @Id
    @Column(name = "CD_MICROMERCADO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MICROMERCADO")
    @SequenceGenerator(name = "SEQ_MICROMERCADO", sequenceName = "SEQ_MICROMERCADO", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "CD_CHAVE")
    private String chave;

    @OneToMany(mappedBy = "micromercado", fetch = FetchType.LAZY)
    private List<PontoDeVenda> pontosDeVenda;

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }

    public List<PontoDeVenda> getPontosDeVenda() {
        return pontosDeVenda;
    }

    public void setPontosDeVenda(List<PontoDeVenda> pontosDeVenda) {
        this.pontosDeVenda = pontosDeVenda;
    }
}
