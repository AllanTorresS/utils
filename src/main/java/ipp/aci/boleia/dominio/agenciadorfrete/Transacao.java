package ipp.aci.boleia.dominio.agenciadorfrete;

import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

/**
 * Representa a tabela de Transação financeira de um Abastecimento
 */
@Entity
@Audited
@Table(name = "AG_FRETE_TRANSACAO")
public class Transacao implements IPersistente {

    @Id
    @Column(name = "CD_TRANSACAO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_AG_FRETE_TRANS")
    @SequenceGenerator(name = "SEQ_AG_FRETE_TRANS", sequenceName = "SEQ_AG_FRETE_TRANS", allocationSize = 1)
    private Long id;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_MOTORISTA_AUTONOMO")
    private MotoristaAutonomo motorista;

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "CD_PTOV")
    private PontoDeVenda posto;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="CD_ABASTECIMENTO")
    private Abastecimento abastecimento;

    @OneToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="CD_SAQUE")
    private Saque saque;

    @ManyToOne
    @JoinColumn(name="CD_PEDIDO")
    private Pedido pedido;

    @Size(max=50)
    @Column(name = "CD_VIP")
    private String codigoVip;

    @NotNull
    @Size(max=8)
    @Column(name = "DS_PLACA_VEICULO")
    private String placaVeiculo;

    @Size(max=450)
    @Column(name = "DS_MOTIVO_RECUSA")
    private String motivoRecusa;

    @ManyToOne
    @JoinColumn(name="CD_CONSOLIDADO")
    private Consolidado consolidado;

    @NotNull
    @Column(name = "DT_CRIACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataCriacao;

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

    public PontoDeVenda getPosto() {
        return posto;
    }

    public void setPosto(PontoDeVenda posto) {
        this.posto = posto;
    }

    public Saque getSaque() {
        return saque;
    }

    public void setSaque(Saque saque) {
        this.saque = saque;
    }

    public Consolidado getConsolidado() {
        return consolidado;
    }

    public void setConsolidado(Consolidado consolidado) {
        this.consolidado = consolidado;
    }

    public Abastecimento getAbastecimento() {
        return abastecimento;
    }

    public void setAbastecimento(Abastecimento abastecimento) {
        this.abastecimento = abastecimento;
    }

    public Pedido getPedido() {
        return pedido;
    }

    public void setPedido(Pedido pedido) {
        this.pedido = pedido;
    }

    public Integer getStatus() {
        return status;
    }

    public String getMotivoRecusa() {
        return motivoRecusa;
    }

    public void setMotivoRecusa(String motivoRecusa) {
        this.motivoRecusa = motivoRecusa;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCodigoVip() {
        return codigoVip;
    }

    public void setCodigoVip(String codigoVip) {
        this.codigoVip = codigoVip;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public Boolean temSaque() {
        return true;
    }
}
