package ipp.aci.boleia.dominio;

import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.dominio.interfaces.IPersistente;
import ipp.aci.boleia.dominio.interfaces.IPertenceFrota;
import org.hibernate.annotations.Formula;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;

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
 * Representa a tabela de Pedido de Credito pre pago para Frota
 */
@Audited
@Entity
@Table(name = "PEDIDO_CREDITO_FROTA")
public class PedidoCreditoFrota implements IPersistente,IPertenceFrota {

    private static final long serialVersionUID = -8758810934145668866L;

    @Id
    @Column(name = "CD_PEDIDO_CREDITO")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_PEDIDO_CREDITO_FROTA")
    @SequenceGenerator(name = "SEQ_PEDIDO_CREDITO_FROTA", sequenceName = "SEQ_PEDIDO_CREDITO_FROTA", allocationSize = 1)
    private Long id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "CD_FROTA")
    private Frota frota;

    @NotNull
    @Column(name = "CD_PEDIDO")
    private String codigoPedido;

    @NotNull
    @Column(name = "CD_PEDIDO_MUNDIPAGG")
    private String codigoPedidoMundipagg;

    @NotNull
    @Column(name = "DT_PEDIDO")
    private Date dataPedido;

    @NotNull
    @Column(name = "VA_VALOR_PEDIDO")
    private BigDecimal valorPedido;

    @NotNull
    @Column(name = "ID_STATUS")
    private Integer status;

    @NotAudited
    @Formula(StatusPedidoCredito.DECODE_FORMULA_SOLUCAO)
    private String statusSolucao;

    @NotAudited
    @Formula(StatusPedidoCredito.DECODE_FORMULA_PROCESSAMENTO)
    private String statusProcessador;

    @Column(name = "VA_VALOR_PAGO")
    private BigDecimal valorPago;

    @NotAudited
    @Formula(StatusPedidoCredito.DECODE_FORMULA_VALOR_PAGO_ORDENACAO_STATUS_FROTISTA)
    private BigDecimal valorPagoOrdenacaoStatusFrotista;

    @NotAudited
    @Formula(StatusPedidoCredito.DECODE_FORMULA_VALOR_PAGO_ORDENACAO_STATUS_SOLUCAO)
    private BigDecimal valorPagoOrdenacaoStatusSolucao;

    @NotNull
    @Column(name = "VA_VALOR_CONSUMIDO")
    private BigDecimal valorConsumido;

    @NotAudited
    @Formula("NVL(VA_VALOR_PAGO, 0) - NVL(VA_VALOR_CONSUMIDO, 0)")
    private BigDecimal saldoPedido;

    @Temporal(TemporalType.DATE)
    @Column(name = "DT_VALIDADE")
    private Date validadePedido;

    @Version
    @Column(name = "NO_VERSAO")
    private Long versao;

    @Column(name = "ID_STATUS_JDE")
    private Integer statusJde;

    @Column(name = "DS_MSG_ERRO_JDE")
    private String mensagemErroJde;

    @Column(name = "NO_DOC_JDE")
    private Long numeroDocumentoJde;

    @Column(name = "NM_CIA_DOC_JDE")
    private String ciaDocumentoJde;

    @Column(name = "ID_TIPO_PAGAMENTO")
    private Integer tipoPagamento;

    @NotNull
    @Column(name = "ID_VERSAO_MUNDIPAGG")
    private Integer versaoMundipaggApi;

    @Transient
    private String idCobranca;

    @Transient
    private String mensagemErroMundiPagg;

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

    public String getCodigoPedido() {
        return codigoPedido;
    }

    public void setCodigoPedido(String codigoPedido) {
        this.codigoPedido = codigoPedido;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public BigDecimal getValorPedido() {
        return valorPedido;
    }

    public void setValorPedido(BigDecimal valorPedido) {
        this.valorPedido = valorPedido;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public BigDecimal getValorPago() {
        return valorPago;
    }

    public void setValorPago(BigDecimal valorPago) {
        this.valorPago = valorPago;
    }

    public Date getValidadePedido() {
        return validadePedido;
    }

    public void setValidadePedido(Date validadePedido) {
        this.validadePedido = validadePedido;
    }

    public Long getVersao() {
        return versao;
    }

    public void setVersao(Long versao) {
        this.versao = versao;
    }

    public String getCodigoPedidoMundipagg() {
        return codigoPedidoMundipagg;
    }

    public void setCodigoPedidoMundipagg(String codigoPedidoMundipagg) {
        this.codigoPedidoMundipagg = codigoPedidoMundipagg;
    }

    public String getStatusSolucao() {
        return statusSolucao;
    }

    public String getStatusProcessador() {
        return statusProcessador;
    }

    public void setStatusSolucao(String statusSolucao) {
        this.statusSolucao = statusSolucao;
    }

    public void setStatusProcessador(String statusProcessador) {
        this.statusProcessador = statusProcessador;
    }

    @Transient
    @Override
    public List<Frota> getFrotas() {
        return Collections.singletonList(frota);
    }

    public BigDecimal getValorConsumido() {
        return valorConsumido;
    }

    public void setValorConsumido(BigDecimal valorConsumido) {
        this.valorConsumido = valorConsumido;
    }

    public BigDecimal getSaldoPedido() {
        return saldoPedido;
    }

    public void setSaldoPedido(BigDecimal saldoPedido) {
        this.saldoPedido = saldoPedido;
    }

    public String getIdCobranca() {
        return idCobranca;
    }

    public void setIdCobranca(String idCobranca) {
        this.idCobranca = idCobranca;
    }

    @Transient
    public String getMensagemErroMundiPagg() {
        return mensagemErroMundiPagg;
    }

    @Transient
    public void setMensagemErroMundiPagg(String mensagemErroMundiPagg) {
        this.mensagemErroMundiPagg = mensagemErroMundiPagg;
    }

    public BigDecimal getValorPagoOrdenacaoStatusFrotista() {
        return valorPagoOrdenacaoStatusFrotista;
    }

    public void setValorPagoOrdenacaoStatusFrotista(BigDecimal valorPagoOrdenacaoStatusFrotista) {
        this.valorPagoOrdenacaoStatusFrotista = valorPagoOrdenacaoStatusFrotista;
    }

    public BigDecimal getValorPagoOrdenacaoStatusSolucao() {
        return valorPagoOrdenacaoStatusSolucao;
    }

    public void setValorPagoOrdenacaoStatusSolucao(BigDecimal valorPagoOrdenacaoStatusSolucao) {
        this.valorPagoOrdenacaoStatusSolucao = valorPagoOrdenacaoStatusSolucao;
    }

    public Integer getStatusJde() {
        return statusJde;
    }

    public void setStatusJde(Integer statusJde) {
        this.statusJde = statusJde;
    }

    public String getMensagemErroJde() {
        return mensagemErroJde;
    }

    public void setMensagemErroJde(String mensagemErroJde) {
        this.mensagemErroJde = mensagemErroJde;
    }

    public Long getNumeroDocumentoJde() {
        return numeroDocumentoJde;
    }

    public void setNumeroDocumentoJde(Long numeroDocumentoJde) {
        this.numeroDocumentoJde = numeroDocumentoJde;
    }

    public String getCiaDocumentoJde() {
        return ciaDocumentoJde;
    }

    public void setCiaDocumentoJde(String ciaDocumentoJde) {
        this.ciaDocumentoJde = ciaDocumentoJde;
    }

    public String getFrotaCnpjRazaoSocial(){
        return this.frota.getCnpj() + " - " + this.frota.getNomeRazaoFrota();
    }

    public Integer getTipoPagamento() {
        return tipoPagamento;
    }

    public void setTipoPagamento(Integer tipoPagamento) {
        this.tipoPagamento = tipoPagamento;
    }

    public Integer getVersaoMundipaggApi() {
        return versaoMundipaggApi;
    }

    public void setVersaoMundipaggApi(Integer versaoMundipaggApi) {
        this.versaoMundipaggApi = versaoMundipaggApi;
    }
}
