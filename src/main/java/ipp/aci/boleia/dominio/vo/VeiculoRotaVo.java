package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * VO para obtenção de informações de veículos para cálculo de Rotas
 */
public class VeiculoRotaVo {

    private BigDecimal kmlVeiculo;
    private Long combustivel;
    private String percentualTanque;
    private Integer volumeTotalTanque;
    private String placa;

    /**
     * Construtor padrão
     */
    public VeiculoRotaVo() { }

    /**
     * Cria uma representação de veículo para a roteirização
     * @param kmlVeiculo Consumo médio do veículo
     * @param combustivel Tipo de combustível usado
     * @param percentualTanque Percentual do tanque considerado para cálculo
     * @param volumeTotalTanque Volume total do tanque
     */
    public VeiculoRotaVo(BigDecimal kmlVeiculo, Long combustivel, String percentualTanque, Integer volumeTotalTanque, String placa) {
        this.kmlVeiculo = kmlVeiculo;
        this.combustivel = combustivel;
        this.percentualTanque = percentualTanque;
        this.volumeTotalTanque = volumeTotalTanque;
        this.placa = placa;
    }

    public BigDecimal getKmlVeiculo() {
        return kmlVeiculo;
    }

    public void setKmlVeiculo(BigDecimal kmlVeiculo) {
        this.kmlVeiculo = kmlVeiculo;
    }

    public Long getCombustivel() { return combustivel; }

    public void setCombustivel(Long combustivel) { this.combustivel = combustivel; }

    public String getPercentualTanque() {
        return percentualTanque;
    }

    public void setPercentualTanque(String percentualTanque) {
        this.percentualTanque = percentualTanque;
    }

    public Integer getVolumeTotalTanque() {
        return volumeTotalTanque;
    }

    public void setVolumeTotalTanque(Integer volumeTotalTanque) {
        this.volumeTotalTanque = volumeTotalTanque;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }
}
