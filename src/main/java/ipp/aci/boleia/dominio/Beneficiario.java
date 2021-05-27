package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IExclusaoLogica;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Representa a tabela de beneficiário
 */
@Entity
@Table(name = "BENEFICIARIO")
public class Beneficiario implements IPersistente, IExclusaoLogica, IPertenceFrota {

    @Id
    @Column(name = "CD_BENEFICIARIO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_BENEFICIARIO")
    @SequenceGenerator(name = "SEQ_BENEFICIARIO", sequenceName = "SEQ_BENEFICIARIO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name="NM_BENEFICIARIO")
    private String nome;

    @NotNull
    @Column(name="CD_CPF")
    private Long cpf;

    @NotNull
    @Column(name="ID_STATUS")
    private Integer status;

    @Column(name = "ID_EXCLUIDO")
    private Boolean excluido;

    @NotNull
    @Column(name="ID_ORIGEM")
    private Integer origem;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @NotNull
    @Column(name="NO_VERSAO")
    private Long versao;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "beneficiario")
    private ContaBeneficioUsuario contaBeneficioUsuario;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

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

    public Long getCpf() {
        return cpf;
    }

    public void setCpf(Long cpf) {
        this.cpf = cpf;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public Boolean getExcluido() {
        return excluido;
    }

    @Override
    public void setExcluido(Boolean excluido) {
        this.excluido = excluido;
    }

    public Integer getOrigem() {
        return origem;
    }

    public void setOrigem(Integer origem) {
        this.origem = origem;
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

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public ContaBeneficioUsuario getContaBeneficioUsuario() {
        return contaBeneficioUsuario;
    }

    public void setContaBeneficioUsuario(ContaBeneficioUsuario contaBeneficioUsuario) {
        this.contaBeneficioUsuario = contaBeneficioUsuario;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }
}