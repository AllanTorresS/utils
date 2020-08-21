package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.visao.dto.VeiculoCalculoDTO;

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
     * Cria um VO a partir de um {@link VeiculoCalculoDTO}
     * @param dto {@link} Representação do veículo
     */
    public VeiculoRotaVo(VeiculoCalculoDTO dto) {
        this.kmlVeiculo = dto.getConsumo();
        this.percentualTanque = dto.getPercentual();
        this.volumeTotalTanque = dto.getCapacidade();
        dto.getCombustivelMotor()
                .getTipoCombustiveis()
                .stream()
                .map(tipoCombustivelDTO -> tipoCombustivelDTO.getId())
                .findFirst().ifPresent(this::setCombustivel);
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
