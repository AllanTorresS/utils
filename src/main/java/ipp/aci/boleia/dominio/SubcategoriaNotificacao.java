package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Subcategoria de Notificacao
 */
@Entity
@Audited
@Table(name = "SUBCATEGORIA_NOTIFICACAO")
public class SubcategoriaNotificacao implements IPersistente {

    private static final long serialVersionUID = 7072220831276371740L;

    @Id
    @Column(name = "CD_SUBCATEGORIA")
    private Long id;

    @Size(max=50)
    @Column(name = "NM_CHAVE_SUBCATEGORIA")
    private String chave;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CATEGORIA")
    private CategoriaNotificacao categoria;

    @Override
    public Long getId() {
        return id;
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

    public CategoriaNotificacao getCategoria() {
        return categoria;
    }

    public void setCategoria(CategoriaNotificacao categoria) {
        this.categoria = categoria;
    }
}
