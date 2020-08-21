package ipp.aci.boleia.dominio.vo;

/**
 * VO para obtenção de informações de veículos para cálculo de Rotas
 */
public class VeiculoRotaVo {

    private String kmlVeiculo;
    private Long combustivel;
    private String percentualTanque;
    private Integer volumeTotalTanque;

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
    public VeiculoRotaVo(String kmlVeiculo, Long combustivel, String percentualTanque, Integer volumeTotalTanque) {
        this.kmlVeiculo = kmlVeiculo;
        this.combustivel = combustivel;
        this.percentualTanque = percentualTanque;
        this.volumeTotalTanque = volumeTotalTanque;
    }

    public String getKmlVeiculo() {
        return kmlVeiculo;
    }

    public void setKmlVeiculo(String kmlVeiculo) {
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

}
