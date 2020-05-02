package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa a tabela de Vip.
 * <br>
 * Na nomenclatura Ipiranga, VIP é um <b>Vendedor Ipiranga de Pista</b>.
 */
@Entity
@Table(name = "VIP")
public class Vip implements IPersistente{

    private static final long serialVersionUID = 5341861254831347956L;

    @Id
    @Column(name = "CD_VIP")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_VIP")
    @SequenceGenerator(name = "SEQ_VIP", sequenceName = "SEQ_VIP", allocationSize = 1)
    private Long id;

    @NotNull
    @Size(max = 256)
    @Column(name = "DS_SENHA")
    private String senha;

    @NotNull
    @Column(name = "NO_CRACHA")
    private Long codigoVip;

    @NotNull
    @Column(name = "CD_PTOV_CORP")
    private Long codigoCorporativoPV;

    @Column(name = "NO_ERRO_AUTENTICACAO")
    private Integer numeroErrosAutenticacao;

    @Column(name = "DT_BLOQUEIO_TEMPORARIO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date bloqueioTemporario;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Long getCodigoVip() {
        return codigoVip;
    }

    public void setCodigoVip(Long codigoVip) {
        this.codigoVip = codigoVip;
    }

    public Long getCodigoCorporativoPV() {
        return codigoCorporativoPV;
    }

    public void setCodigoCorporativoPV(Long codigoCorporativoPV) {
        this.codigoCorporativoPV = codigoCorporativoPV;
    }

    public Integer getNumeroErrosAutenticacao() {
        return numeroErrosAutenticacao;
    }

    public void setNumeroErrosAutenticacao(Integer numeroErrosAutenticacao) {
        this.numeroErrosAutenticacao = numeroErrosAutenticacao;
    }

    public Date getBloqueioTemporario() {
        return bloqueioTemporario;
    }

    public void setBloqueioTemporario(Date bloqueioTemporario) {
        this.bloqueioTemporario = bloqueioTemporario;
    }
}
