package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.PedidoCreditoFrota;
import ipp.aci.boleia.dominio.enums.StatusPedidoCredito;
import ipp.aci.boleia.util.UtilitarioFormatacao;
import ipp.aci.boleia.util.UtilitarioFormatacaoData;

import java.math.RoundingMode;

/**
 * Classe com informacoes relacionadas a resposta do pedido de credito junto a Mundipagg
 */
public class MundipaggRespostaPedidoVo {

    private String id;
    private String token;
    private String codigo;
    private String valorCompra;
    private String dataVencimento;
    private String meioPagamento;
    private String status;
    private String erro;


    public MundipaggRespostaPedidoVo() {

    }

    /**
     * Construtor da resposta Mundipagg
     * @param pedidoCreditoFrota O pedido de credito
     */
    public MundipaggRespostaPedidoVo(PedidoCreditoFrota pedidoCreditoFrota) {
        this.id = pedidoCreditoFrota.getCodigoPedidoMundipagg();
        this.codigo = pedidoCreditoFrota.getCodigoPedido();
        this.valorCompra = UtilitarioFormatacao.formatarDecimal(pedidoCreditoFrota.getValorPedido().setScale(2, RoundingMode.HALF_UP));
        this.dataVencimento = UtilitarioFormatacaoData.formatarDataCurta(pedidoCreditoFrota.getValidadePedido());
        this.status = StatusPedidoCredito.obterPorValor(pedidoCreditoFrota.getStatus()).getLabel();
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getValorCompra() {
        return valorCompra;
    }

    public void setValorCompra(String valorCompra) {
        this.valorCompra = valorCompra;
    }

    public String getErro() {
        return erro;
    }

    public void setErro(String erro) {
        this.erro = erro;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDataVencimento() {
        return dataVencimento;
    }

    public void setDataVencimento(String dataVencimento) {
        this.dataVencimento = dataVencimento;
    }

    public String getMeioPagamento() {
        return meioPagamento;
    }

    public void setMeioPagamento(String meioPagamento) {
        this.meioPagamento = meioPagamento;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
 