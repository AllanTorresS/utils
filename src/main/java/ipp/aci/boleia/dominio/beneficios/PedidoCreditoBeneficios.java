package ipp.aci.boleia.dominio.beneficios;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
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
import javax.persistence.Transient;
import javax.persistence.Version;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Entidade relacionada aos pedidos de crédito para benefícios
 * que uma frota realiza.
 *
 * @author pedro.silva
 */
@Audited
@Entity
@Table(name = "PEDIDO_CREDITO_BENEFICIOS")
public class PedidoCreditoBeneficios implements IPersistente, IPertenceFrota {

    @Id
    @Column(name = "CD_PEDIDO_CREDITO_BENEFICIOS")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO_CREDITO_BENEFICIOS")
    @SequenceGenerator(name = "SEQ_PEDIDO_CREDITO_BENEFICIOS", sequenceName = "SEQ_PEDIDO_CREDITO_BENEFICIOS", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_CONTA_BENEFICIOS_FROTA")
    private ContaBeneficiosFrota contaBeneficiosFrota;

    @NotNull
    @Column(name = "VA_PEDIDO")
    private BigDecimal valorPedido;

    @NotNull
    @Column(name = "NO_DOC_JDE")
    private Long numeroDocumento;

    @NotNull
    @Column(name = "NM_CIA_DOC")
    private String ciaDocumento;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotNull
    @Column(name = "DT_PEDIDO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataPedido;

    @NotNull
    @Column(name = "DT_VENCIMENTO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataVencimento;

    @NotNull
    @Column(name = "DT_ATUALIZACAO")
    @Temporal(TemporalType.TIMESTAMP)
    private Date dataAtualizacao;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public ContaBeneficiosFrota getContaBeneficiosFrota() {
        return contaBeneficiosFrota;
    }

    public void setContaBeneficiosFrota(ContaBeneficiosFrota contaBeneficiosFrota) {
        this.contaBeneficiosFrota = contaBeneficiosFrota;
    }

    public BigDecimal getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(BigDecimal valorPedido) {
        this.valorPedido = valorPedido;
    }

    public Long getNumeroDocumento() {
        return numeroDocumento;
    }

    public void setNumeroDocumento(Long numeroDocumento) {
        this.numeroDocumento = numeroDocumento;
    }

    public String getCiaDocumento() {
        return ciaDocumento;
    }

    public void setCiaDocumento(String ciaDocumento) {
        this.ciaDocumento = ciaDocumento;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public Date getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(Date dataVencimento) {
        this.dataVencimento = dataVencimento;
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

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(contaBeneficiosFrota.getFrota());
    }
}
