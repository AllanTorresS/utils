package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

/**
 * Representa a tabela de Campos Favoritos do relatório de Abastecimento
 */
@Entity
@Table(name = "FAVORITO_REL_ABASTECIMENTO")
public class FavoritoRelAbastecimento implements IPersistente {

    private static final long serialVersionUID = 8116772818767866017L;

    @Id
    @Column(name = "CD_FAVORITO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_FAV_REL_ABASTECIMENTO")
    @SequenceGenerator(name = "SEQ_FAV_REL_ABASTECIMENTO", sequenceName = "SEQ_FAV_REL_ABASTECIMENTO", allocationSize = 1)
    private Long id;

    @Column(name = "CD_CAMPOS")
    private String campos;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    /**
     * Construtor default
     */
    public FavoritoRelAbastecimento() {
        // construtor default
    }

    /**
     * Construtor.
     *
     * @param campos Os indices dos campos a serem salvo concatenados em uma string na ordem
     * @param usuario o usuário ao qual este campos pertence
     */
    public FavoritoRelAbastecimento(String campos, Usuario usuario) {
        this.campos = campos;
        this.usuario = usuario;
    }

    @Override
    public Long getId() { return id; }

    @Override
    public void setId(Long id) { this.id = id; }

    public String getCampos() { return campos; }

    public void setCampos(String campos) { this.campos = campos; }

    public Usuario getUsuario() { return usuario; }

    public void setUsuario(Usuario usuario) { this.usuario = usuario; }
}
