package ipp.aci.boleia.dominio.agenciadorfrete;


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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa a tabela de Motorista Autônomo
 */
@Entity
@Audited
@Table(name = "MOTORISTA_AUTONOMO")
public class MotoristaAutonomo implements IPersistente {

    @Id
    @Column(name = "CD_MOTORISTA_AUTONOMO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_MOTORISTA_AUTONOMO")
    @SequenceGenerator(name = "SEQ_MOTORISTA_AUTONOMO", sequenceName = "SEQ_MOTORISTA_AUTONOMO", allocationSize = 1)
    private Long id;

    @Size(max = 250)
    @NotNull
    @Column(name = "NM_MOTORISTA")
    private String nome;

    @NotNull
    @Column(name = "CD_CPF")
    private String cpf;

    @Column(name = "NO_DDD_TEL_CEL")
    private Integer dddTelefoneCelular;

    @Column(name = "NO_TEL_CEL")
    private Integer telefoneCelular;

    @Size(max = 250)
    @Column(name = "DS_EMAIL")
    private String email;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_AGENCIADOR_FRETE")
    private AgenciadorFrete agenciadorFrete;

    @Column(name = "DT_CRICAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public AgenciadorFrete getAgenciadorFrete() {
        return agenciadorFrete;
    }

    public void setAgenciadorFrete(AgenciadorFrete agenciadorFrete) {
        this.agenciadorFrete = agenciadorFrete;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
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

}
