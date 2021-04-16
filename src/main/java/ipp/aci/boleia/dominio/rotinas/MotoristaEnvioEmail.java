package ipp.aci.boleia.dominio.rotinas;

import ipp.aci.boleia.dominio.*;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Size;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de Motorista Envio Email
 */
@Entity
@Audited
@Table(name = "MOTORISTA_ENVIO_EMAIL")
public class MotoristaEnvioEmail implements IPersistente /*, IExclusaoLogica, IPertenceFrota, IPertenceMotorista */ {

    private static final long serialVersionUID = -5710886542823346375L;

    @Id
    @Column(name = "CD_MOTORISTA")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @Size(max = 250)
    @Column(name = "NM_MOTORISTA")
    private String nome;

    @Min(10L)
    @Max(99L)
    @Column(name = "CD_DDD_TEL_CEL")
    private Integer dddTelefoneCelular;

    @Min(10000000L)
    @Max(999999999L)
    @Column(name = "NO_TEL_CEL")
    private Integer telefoneCelular;

    @Column(name = "DS_EMAIL")
    private String email;

    @Column(name="DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "DT_EXCLUSAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExclusao;

    /**
     * Construtor padrão da entidade.
     */
    public MotoristaEnvioEmail() {
        // nada a fazer
    }

    /**
     * Construtor da classe
     *
     * @param id Código identificador do motorista
     * @param nome Nome do motorista
     * @param frota Frota do motorista
     * @param dddTelefoneCelular Número DDD do telefone
     * @param telefoneCelular Número do telefone
     * @param dataCriacao Data de criação
     * @param dataExclusao Data de exclusão
     */
    public MotoristaEnvioEmail(Long id, String nome, Frota frota, Integer dddTelefoneCelular, Integer telefoneCelular, Date dataCriacao, Date dataExclusao) {
        this.id = id;
        this.nome = nome;
        this.frota = frota;
        this.dddTelefoneCelular = dddTelefoneCelular;
        this.telefoneCelular = telefoneCelular;
        this.dataCriacao = dataCriacao;
        this.dataExclusao = dataExclusao;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) { this.id = id; }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Integer getDddTelefoneCelular() {
        return dddTelefoneCelular;
    }

    public void setDddTelefoneCelular(Integer dddTelefoneCelular) {
        this.dddTelefoneCelular = dddTelefoneCelular;
    }

    public Integer getTelefoneCelular() {
        return telefoneCelular;
    }

    public void setTelefoneCelular(Integer telefoneCelular) {
        this.telefoneCelular = telefoneCelular;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Transient
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataExclusao() {
        return dataExclusao;
    }

    public void setDataExclusao(Date dataExclusao) {
        this.dataExclusao = dataExclusao;
    }
}
