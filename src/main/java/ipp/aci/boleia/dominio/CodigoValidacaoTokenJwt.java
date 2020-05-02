package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

/**
 * Entidade com o código de validação de um token  jwt de um usuário do sistema.
 * Utilizado para forçar expiração de tokens JWT quando um usuário efetua uma nova autenticação.
 *
 * @author pedro.silva
 */
@Entity
@Audited
@Table(name = "CODIGO_VALIDACAO_TOKEN_JWT")
public class CodigoValidacaoTokenJwt implements IPersistente {

    private static final long serialVersionUID = 1454975520075271665L;

    @Id
    @Column(name = "CD_CODIGO_VALIDACAO_TOKEN_JWT")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_CODIGO_VALIDACAO_TOKEN_JWT")
    @SequenceGenerator(name = "SEQ_CODIGO_VALIDACAO_TOKEN_JWT", sequenceName = "SEQ_CODIGO_VALIDACAO_TOKEN_JWT", allocationSize = 1)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @NotNull
    @Column(name = "CD_TOKEN_JWT")
    private Long codigoValidacao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Long getCodigoValidacao() {
        return codigoValidacao;
    }

    public void setCodigoValidacao(Long codigoValidacao) {
        this.codigoValidacao = codigoValidacao;
    }
}
