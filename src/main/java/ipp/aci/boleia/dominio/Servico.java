package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Representa a tabela de Servico
 */
@Entity
@Audited
@Table(name = "SERVICO")
public class Servico implements IPersistente {

    private static final long serialVersionUID = -1700327962279089927L;

    @Id
    @Column(name = "CD_SERVICO")
    private Long id;

    @Column(name = "DS_SERVICO")
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
