package ipp.aci.boleia.dominio;

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
import javax.validation.constraints.NotNull;
import java.util.Date;

/**
 * Entidade persistente que mantém as informações específicas de uma transação
 * realizada por um veículo frota leve.
 */
@Entity
@Audited
@Table(name = "TRANS_FROTA_LEVE")
public class TransacaoFrotaLeve implements IPersistente {

    private static final long serialVersionUID = -358998697314220745L;

    @Id
    @Column(name = "CD_TRANS_FROTA_LEVE")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SEQ_TRANS_FROTA_LEVE")
    @SequenceGenerator(name = "SEQ_TRANS_FROTA_LEVE", sequenceName = "SEQ_TRANS_FROTA_LEVE", allocationSize = 1)
    private Long id;

    @JoinColumn(name = "CD_PEDIDO")
    @ManyToOne(fetch = FetchType.LAZY)
    private DispositivoMotoristaPedido pedido;

    @NotNull
    @Column(name="DS_NSU_ZACC")
    private String nsuZacc;

    @Column(name="DS_NSU_ZACC_ORIG")
    private String nsuZaccOriginal;

    @Column(name="ID_TIPO_TRANS")
    private Integer tipoTransacao;

    @Column(name="CD_CHAVE_NUM_PEDIDO")
    private Integer chaveNumeroPedido;

    @Column(name="ID_STATUS_CONFIRMACAO")
    private Integer statusConfirmacao;

    @Column(name="DS_NSU_PROFROTAS")
    private String nsuProFrotas;

    @Column(name="DT_REQUISICAO")
    private Date dataRequisicao;

    @Column(name="DT_ATUALIZACAO")
    private Date dataAtualizacao;


    /**
     * Construtor padrao
     */
    public TransacaoFrotaLeve() {

    }

    /**
     * Constroi uma entidade de Transacao Frota Leve a partir dos parametros necessairios
     *
     * @param nsuZacc O numero serial enviado pela Zacc para essa transacao
     * @param pedido O pedido que gerou a transacao
     * @param tipoTransacao Informa se a transacao e de queima ou de confirmacao
     * @param chaveNumeroPedido O codigo de abastecimento complementando o numero do pedido
     * @param statusConfirmacaoTransacao O status da transacao (0 - FALHA, 1 - SUCESSO)
     * @param nsuProFrotas O serial da transacao gerado pelo backend
     * @param dataRequisicao A data e hora na qual a requisição foi feita
     */
    public TransacaoFrotaLeve(String nsuZacc, DispositivoMotoristaPedido pedido, Integer tipoTransacao, Integer chaveNumeroPedido, Integer statusConfirmacaoTransacao, String nsuProFrotas, Date dataRequisicao) {
        this.nsuZacc = nsuZacc;
        this.pedido = pedido;
        this.tipoTransacao = tipoTransacao;
        this.chaveNumeroPedido = chaveNumeroPedido;
        this.statusConfirmacao = statusConfirmacaoTransacao;
        this.nsuProFrotas = nsuProFrotas;
        this.dataRequisicao = dataRequisicao;
    }

    @Override
    public Long getId() {
        return this.id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public DispositivoMotoristaPedido getPedido() {
        return pedido;
    }

    public void setPedido(DispositivoMotoristaPedido pedido) {
        this.pedido = pedido;
    }

    public String getNsuZacc() {
        return nsuZacc;
    }

    public void setNsuZacc(String nsuZacc) {
        this.nsuZacc = nsuZacc;
    }

    public String getNsuZaccOriginal() {
        return nsuZaccOriginal;
    }

    public void setNsuZaccOriginal(String nsuZaccOriginal) {
        this.nsuZaccOriginal = nsuZaccOriginal;
    }

    public Integer getTipoTransacao() {
        return tipoTransacao;
    }

    public void setTipoTransacao(Integer tipoTransacao) {
        this.tipoTransacao = tipoTransacao;
    }

    public Integer getChaveNumeroPedido() {
        return chaveNumeroPedido;
    }

    public void setChaveNumeroPedido(Integer chaveNumeroPedido) {
        this.chaveNumeroPedido = chaveNumeroPedido;
    }

    public Integer getStatusConfirmacao() {
        return statusConfirmacao;
    }

    public void setStatusConfirmacao(Integer statusConfirmacao) {
        this.statusConfirmacao = statusConfirmacao;
    }

    public String getNsuProFrotas() {
        return nsuProFrotas;
    }

    public void setNsuProFrotas(String nsuProFrotas) {
        this.nsuProFrotas = nsuProFrotas;
    }

    public Date getDataRequisicao() {
        return dataRequisicao;
    }

    public void setDataRequisicao(Date dataRequisicao) {
        this.dataRequisicao = dataRequisicao;
    }

    public Date getDataAtualizacao() {
        return dataAtualizacao;
    }

    public void setDataAtualizacao(Date dataAtualizacao) {
        this.dataAtualizacao = dataAtualizacao;
    }
}
