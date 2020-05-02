package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Motivo Chamado
 */
@Entity
@Table(name = "MOTIVO_CHAMADO")
public class MotivoChamado implements IPersistente {

    private static final long serialVersionUID = -6532094120604784104L;

    @Id
    @Column(name = "CD_MOTIVO")
    private Long id;

    @Size(max=50)
    @Column(name="DS_MOTIVO")
    private String descricao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }
}
