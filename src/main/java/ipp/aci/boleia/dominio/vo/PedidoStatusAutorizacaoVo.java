package ipp.aci.boleia.dominio.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * Classe com informações referentes do pedido e seu status de autorização
 */
public class PedidoStatusAutorizacaoVo implements Serializable {

    private String numero;
    private Integer statusAutorizacao;
    private Date dataExpiracao;
    private Integer codigoErro;
    private String mensagemErro;

    /**
     * Identificação do mapa para obter objetos PedidoStatusAutorizacaoVo na base em memoria.
     */
    public static final String STATUS_NUMERO_PEDIDO_AUTORIZACAO = "STATUS_NUMERO_PEDIDO_AUTORIZACAO";

    /**
     * Construtor dos utilizado diretamente na consulta em:
     * ipp.aci.boleia.dados.oracle.OracleDispositivoMotoristaPedidoDados#QUERY_PEDIDO_COM_STATUS_AUTORIZACAO
     *
     * @param numero numero do pedido
     * @param statusAutorizacao status da autorização pagamento associada
     * @param mensagemErro erro relacionado ao pedido não autorizado
     * @param dataExpiracao data limite para utilizar o pedido
     */
    public PedidoStatusAutorizacaoVo(String numero, Integer statusAutorizacao, String mensagemErro, Date dataExpiracao) {
        this.numero = numero;
        this.statusAutorizacao = statusAutorizacao;
        this.mensagemErro = mensagemErro;
        this.dataExpiracao = dataExpiracao;
    }

    public PedidoStatusAutorizacaoVo() {
        //Contrutor default
    }

    /**
     * Cria um status de pedido que não possui assiciação com autorização pagamento
     *
     * @param numero numero do pedido
     */
    public PedidoStatusAutorizacaoVo(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Integer getStatusAutorizacao() {
        return statusAutorizacao;
    }

    public void setStatusAutorizacao(Integer statusAutorizacao) {
        this.statusAutorizacao = statusAutorizacao;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public String getMensagemErro() {
        return mensagemErro;
    }

    public void setMensagemErro(String mensagemErro) {
        this.mensagemErro = mensagemErro;
    }

    public Integer getCodigoErro() {
        return codigoErro;
    }

    public void setCodigoErro(Integer codigoErro) {
        this.codigoErro = codigoErro;
    }
}
