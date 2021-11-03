package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Motivo Clonagem
 */
@Audited
@Entity
@Table(name = "MOTIVO_CLONAGEM")
public class MotivoClonagem implements IPersistente {

    private static final long serialVersionUID = -419724588512937598L;

    @Id
    @Column(name = "CD_MOTIVO_CLONAGEM")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTIVO_CLONAGEM")
    @SequenceGenerator(name = "SEQ_MOTIVO_CLONAGEM", sequenceName = "SEQ_MOTIVO_CLONAGEM", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "NM_MOTIVO_CLONAGEM")
    private String nome;

    @NotNull
    @Column(name = "ID_VALIDO_INTERNO")
    private Boolean validoInterno;

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

    public Boolean getValidoInterno() {
        return validoInterno;
    }

    public void setValidoInterno(Boolean validoInterno) {
        this.validoInterno = validoInterno;
    }
}
