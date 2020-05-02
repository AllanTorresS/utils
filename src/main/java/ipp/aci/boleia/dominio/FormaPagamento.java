package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Forma Pagamento
 */
@Entity
@Table(name = "FORMA_PAGAMENTO")
public class FormaPagamento implements IPersistente {

    private static final long serialVersionUID = 5987987976517275422L;

    @Id
    @Column(name = "CD_FORMA_PAGAMENTO")
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "NM_FORMA_PAGAMENTO")
    private String nome;

    @NotNull
    @Column(name = "CD_TIPO_FORMA_PAGAMENTO")
    private Integer tipo;

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

    public Integer getTipo() {
        return tipo;
    }

    public void setTipo(Integer tipo) {
        this.tipo = tipo;
    }
}