package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Size;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Usuario Motorista
 */
@Entity
@Audited
@Table(name = "USUARIO_MOTORISTA")
public class UsuarioMotorista implements IPersistente, IExclusaoLogica {

    private static final long serialVersionUID = 6272175572593493823L;

    @Id
    @Column(name="CD_USUARIO_MOTORISTA")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_USUARIO_MOTORISTA")
    @SequenceGenerator(name = "SEQ_USUARIO_MOTORISTA", sequenceName = "SEQ_USUARIO_MOTORISTA", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_USUARIO")
    private Usuario usuario;

    @Column(name="CD_TOKEN_ALLOWME")
    private String tokenAllowMe;

    @Column(name="CD_ID_ALLOWME")
    private String idAllowMe;

    @Column(name="CD_SEED_ALLOWME")
    private String seedAllowMe;

    @Size(max = 150)
    @Column(name = "DS_UDID")
    private String udid;

    @Column(name = "DT_EXP_SENHA_TEMP")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracaoSenhaTemporaria;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "usuarioMotorista")
    private List<Motorista> motoristas;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    public UsuarioMotorista() {
        //Construtor default
    }

    /**
     * Instancia o usuario motorista
     * @param usuario o usuario do motorista
     */
    public UsuarioMotorista(Usuario usuario) {
        this.usuario = usuario;
    }

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

    public String getTokenAllowMe() {
        return tokenAllowMe;
    }

    public void setTokenAllowMe(String tokenAllowMe) {
        this.tokenAllowMe = tokenAllowMe;
    }

    public String getIdAllowMe() {
        return idAllowMe;
    }

    public void setIdAllowMe(String idAllowMe) {
        this.idAllowMe = idAllowMe;
    }

    public String getSeedAllowMe() {
        return seedAllowMe;
    }

    public void setSeedAllowMe(String seedAllowMe) {
        this.seedAllowMe = seedAllowMe;
    }

    public String getUdid() {
        return udid;
    }

    public void setUdid(String udid) {
        this.udid = udid;
    }

    public Date getDataExpiracaoSenhaTemporaria() {
        return dataExpiracaoSenhaTemporaria;
    }

    public void setDataExpiracaoSenhaTemporaria(Date dataExpiracaoSenhaTemporaria) {
        this.dataExpiracaoSenhaTemporaria = dataExpiracaoSenhaTemporaria;
    }

    public List<Motorista> getMotoristas() {
        return motoristas;
    }

    public void setMotoristas(List<Motorista> motoristas) {
        this.motoristas = motoristas;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }
}
