package ipp.aci.boleia.dominio;


import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

/**
 * Representa a tabela Categoria Notificacao
 */
@Entity
@Audited
@Table(name = "CATEGORIA_NOTIFICACAO")
public class CategoriaNotificacao implements IPersistente {

    private static final long serialVersionUID = 8197878685848858554L;

    @Id
    @Column(name = "CD_CATEGORIA")
    private Long id;

    @Size(max=50)
    @Column(name = "NM_CATEGORIA")
    private String nome;

    @Size(max=50)
    @Column(name = "NM_CHAVE_CATEGORIA")
    private String chave;

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

    public String getChave() {
        return chave;
    }

    public void setChave(String chave) {
        this.chave = chave;
    }
}
