package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.TipoPerfilUsuario;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Representa a tabela de Tipo Perfil
 */
@Audited
@Entity
@Table(name = "TIPO_PERFIL")
public class TipoPerfil implements IPersistente {

    private static final long serialVersionUID = 6207556647840840856L;

    @Id
    @Column(name = "CD_TIPO_PERFIL")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TIPO_PERFIL")
    @SequenceGenerator(name = "SEQ_TIPO_PERFIL", sequenceName = "SEQ_TIPO_PERFIL", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 250)
    @Column(name = "NM_TIPO_PERFIL")
    private String nome;

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

    @Transient
    public boolean isInterno() {
        return TipoPerfilUsuario.INTERNO.getValue().equals(id);
    }

    @Transient
    public boolean isFrotista() {
        return TipoPerfilUsuario.FROTA.getValue().equals(id);
    }

    @Transient
    public boolean isRevendedor() {
        return TipoPerfilUsuario.REVENDA.getValue().equals(id);
    }

    @Transient
    public boolean isPrecos() {
        return TipoPerfilUsuario.PRECOS.getValue().equals(id);
    }

    @Transient
    public boolean isMotorista() {
        return TipoPerfilUsuario.MOTORISTA.getValue().equals(id);
    }

    @Transient
    public TipoPerfilUsuario getTipoPerfilUsuario(){ return TipoPerfilUsuario.obterPorValor(id);}
}