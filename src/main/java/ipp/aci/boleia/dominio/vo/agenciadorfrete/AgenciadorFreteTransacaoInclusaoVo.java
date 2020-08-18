package ipp.aci.boleia.dominio.vo.agenciadorfrete;

import ipp.aci.boleia.util.anotacoes.Numeric;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.math.BigDecimal;

public class AgenciadorFreteTransacaoInclusaoVo {

    @NotNull
    private String numeroPedido;
    @NotNull
    private String codigoAbastecimento;
    @NotNull
    private String placaVeiculo;
    @NotNull
    private String cnpjPosto;
    @NotNull
    private BigDecimal litragem;
    @NotNull
    private Long combustivelId;

    private BigDecimal valorSolicitadoSaque;

    @Size(max=4)
    @Numeric
    private String codigoVip;


    public AgenciadorFreteTransacaoInclusaoVo() {
        //Construtor default
    }

    public String getCodigoAbastecimento() {
        return codigoAbastecimento;
    }

    public void setCodigoAbastecimento(String codigoAbastecimento) {
        this.codigoAbastecimento = codigoAbastecimento;
    }

    public String getPlacaVeiculo() {
        return placaVeiculo;
    }

    public void setPlacaVeiculo(String placaVeiculo) {
        this.placaVeiculo = placaVeiculo;
    }

    public String getCnpjPosto() {
        return cnpjPosto;
    }

    public void setCnpjPosto(String cnpjPosto) {
        this.cnpjPosto = cnpjPosto;
    }

    public BigDecimal getLitragem() {
        return litragem;
    }

    public void setLitragem(BigDecimal litragem) {
        this.litragem = litragem;
    }

    public Long getCombustivelId() {
        return combustivelId;
    }

    public void setCombustivelId(Long combustivelId) {
        this.combustivelId = combustivelId;
    }

    public BigDecimal getValorSolicitadoSaque() {
        return valorSolicitadoSaque;
    }

    public void setValorSolicitadoSaque(BigDecimal valorSolicitadoSaque) {
        this.valorSolicitadoSaque = valorSolicitadoSaque;
    }

    public String getCodigoVip() {
        return codigoVip;
    }

    public void setCodigoVip(String codigoVip) {
        this.codigoVip = codigoVip;
    }

    public String getNumeroPedido() {
        return numeroPedido;
    }

    public void setNumeroPedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
    }
}
