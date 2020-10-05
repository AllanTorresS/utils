package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import com.fasterxml.jackson.annotation.JsonFormat;
import ipp.aci.boleia.dominio.agenciadorfrete.Pedido;
import ipp.aci.boleia.util.ConstantesFormatacao;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Classe com informações referentes do pedido de um extrato de motorista autonomo
 */
public class PedidoVo {

    private String numero;
    private String cpfMotorista;
    private String placa;
    private BigDecimal precoUnitario;
    @JsonFormat(pattern = ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS_E_TIMEZONE, timezone = ConstantesFormatacao.FORMATO_TIMEZONE_GMT_3)
    private Date dataPedido;
    @JsonFormat(pattern = ConstantesFormatacao.FORMATO_ISO_8601_COM_MILLIS_E_TIMEZONE, timezone = ConstantesFormatacao.FORMATO_TIMEZONE_GMT_3)
    private Date dataExpiracao;
    private PostoBasicoVo posto;
    private BigDecimal litragem;
    private CombustivelVo combustivel;


    public PedidoVo() {
        //Contrutor default
    }

    public PedidoVo(Pedido pedido) {
        this.numero = pedido.getNumero();
        this.cpfMotorista = pedido.getMotorista() != null ? pedido.getMotorista().getCpf() : null;
        this.placa = pedido.getPlaca();
        this.precoUnitario = pedido.getPrecoUnitario();
        this.dataPedido = pedido.getDataCriacao();
        this.dataExpiracao = pedido.getDataExpiracao();
        this.posto = pedido.getPosto() != null ? new PostoBasicoVo(pedido.getPosto()) : null;
        this.litragem = pedido.getLitragem();
        this.combustivel = pedido.getTipoCombustivel() != null ? new CombustivelVo(pedido.getTipoCombustivel(), pedido.getPrecoUnitario()) : null;
    }

    public String getCpfMotorista() {
        return cpfMotorista;
    }

    public void setCpfMotorista(String cpfMotorista) {
        this.cpfMotorista = cpfMotorista;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Date getDataPedido() {
        return dataPedido;
    }

    public void setDataPedido(Date dataPedido) {
        this.dataPedido = dataPedido;
    }

    public PostoBasicoVo getPosto() {
        return posto;
    }

    public void setPosto(PostoBasicoVo posto) {
        this.posto = posto;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public Date getDataExpiracao() {
        return dataExpiracao;
    }

    public void setDataExpiracao(Date dataExpiracao) {
        this.dataExpiracao = dataExpiracao;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }

    public CombustivelVo getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(CombustivelVo combustivel) {
        this.combustivel = combustivel;
    }
}
