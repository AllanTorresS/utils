package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.interfaces.IPersistente;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Representa a tabela de pedido Motorista autonomo
 */
@Entity
@Table(name = "CONTA_BENEFICIO")
public class ContaBeneficio implements IPersistente {

    @Id
    @Column(name = "CD_CONTA_BENEFICIO")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CONTA_BENEFICIO_USUARIO")
    private ContaBeneficioUsuario contaBeneficioUsuario;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_BENEFICIO")
    private Beneficio beneficio;

    @NotNull
    @Column(name="ID_CONFIGURADO")
    private Boolean beneficioConfigurado;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ContaBeneficioUsuario getContaBeneficioUsuario() {
        return contaBeneficioUsuario;
    }

    public void setContaBeneficioUsuario(ContaBeneficioUsuario contaBeneficioUsuario) {
        this.contaBeneficioUsuario = contaBeneficioUsuario;
    }

    public Beneficio getBeneficio() {
        return beneficio;
    }

    public void setBeneficio(Beneficio beneficio) {
        this.beneficio = beneficio;
    }

    public Boolean getBeneficioConfigurado() {
        return beneficioConfigurado;
    }

    public void setBeneficioConfigurado(Boolean beneficioConfigurado) {
        this.beneficioConfigurado = beneficioConfigurado;
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
}