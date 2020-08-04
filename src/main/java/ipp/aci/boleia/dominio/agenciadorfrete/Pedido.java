package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.TipoCombustivel;
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
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;
import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa a tabela de pedido Motorista autonomo
 */
@Entity
@Audited
@Table(name = "PEDIDO_MOTORISTA_AUTONOMO")
public class Pedido implements IPersistente {

    @Id
    @Column(name = "CD_PEDIDO_M_AUTONOMO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO_MO_AUTONOMO")
    @SequenceGenerator(name = "SEQ_PEDIDO_MO_AUTONOMO", sequenceName = "SEQ_PEDIDO_MO_AUTONOMO", allocationSize = 1)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA_AUTONOMO")
    private MotoristaAutonomo motorista;

    @Column(name = "NM_PEDIDO")
    private String numero;

    @Size(min=7, max=8)
    @Column(name = "DS_PLACA")
    private String placa;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda posto;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TIPO_COMBUSTIVEL")
    private TipoCombustivel tipoCombustivel;

    @Column(name = "VA_UNITARIO")
    private BigDecimal precoUnitario;

    @DecimalMax("1000.00")
    @DecimalMin("0.01")
    @Column(name = "QT_LITRAGEM")
    private BigDecimal litragem;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_TRANSACAO")
    private Transacao transacao;

    @NotNull
    @Column(name = "DT_EXPIRACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataExpiracao;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

    @Column(name = "DS_COD_ABAST")
    private String codigoAbastecimento; //TODO: refatorar

    @Column(name = "DT_EXP_COD_ABAST")
    private Date expiracaoCodigoAbastecimento; //TODO: refatorar

    @Column(name = "NO_VERSAO")
    @Version
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public MotoristaAutonomo getMotorista() {
        return motorista;
    }

    public void setMotorista(MotoristaAutonomo motorista) {
        this.motorista = motorista;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public PontoDeVenda getPosto() {
        return posto;
    }

    public void setPosto(PontoDeVenda posto) {
        this.posto = posto;
    }

    public TipoCombustivel getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivel tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public Date getDataCriacao() {
        return dataCriacao;
    }

    public void setDataCriacao(Date dataCriacao) {
        this.dataCriacao = dataCriacao;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Transacao getTransacao() {
        return transacao;
    }

    public void setTransacao(Transacao transacao) {
        this.transacao = transacao;
    }

    public String getCodigoAbastecimento() {
        return codigoAbastecimento;
    }

    public void setCodigoAbastecimento(String codigoAbastecimento) {
        this.codigoAbastecimento = codigoAbastecimento;
    }

    public Date getExpiracaoCodigoAbastecimento() {
        return expiracaoCodigoAbastecimento;
    }

    public void setExpiracaoCodigoAbastecimento(Date expiracaoCodigoAbastecimento) {
        this.expiracaoCodigoAbastecimento = expiracaoCodigoAbastecimento;
    }
}
