package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * Representa uma entidade Pedido do Dispositivo Motorista para apresentacao em tela
 */
public class PedidoInclusaoVo {

    @ApiModelProperty(value = "${docs.dominio.vo.PedidoInclusaoVo.placa}", required = true)
    @NotNull
    private String placa;

    @ApiModelProperty(value = "${docs.dominio.vo.PedidoInclusaoVo.cnpjPosto}")
    private String cnpjPosto;

    @ApiModelProperty(value = "${docs.dominio.vo.PedidoInclusaoVo.combustivel}")
    private Long combustivel;

    @ApiModelProperty(value = "${docs.dominio.vo.PedidoInclusaoVo.litragem}")
    private BigDecimal litragem;

    public PedidoInclusaoVo() {
        // serializacao json
    }


    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getCnpjPosto() {
        return cnpjPosto;
    }

    public void setCnpjPosto(String cnpjPosto) {
        this.cnpjPosto = cnpjPosto;
    }

    public Long getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(Long combustivel) {
        this.combustivel = combustivel;
    }

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }
}
