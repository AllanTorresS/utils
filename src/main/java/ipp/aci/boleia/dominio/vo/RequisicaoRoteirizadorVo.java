package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.visao.dto.RotaCalculoDTO;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma requisição para cálculo de uma Rota
 */
public class RequisicaoRoteirizadorVo {

    private List<PontoRotaVo> pontosRota = new ArrayList<>();
    private VeiculoRotaVo infoVeiculo;
    private Long frota;

    /**
     * Construtor padrão
     */
    public RequisicaoRoteirizadorVo() { }

    /**
     * Representa uma requisição criada a partir de {@link RotaCalculoDTO}
     * @param dto {@link RotaCalculoDTO} Utilizado para criar a requisição
     */
    public RequisicaoRoteirizadorVo(RotaCalculoDTO dto) {
        dto.getPontos()
                .stream()
                .map(pontoRotaDTO -> new PontoRotaVo(pontoRotaDTO))
                .forEach(this.pontosRota::add);
        this.infoVeiculo = new VeiculoRotaVo(dto.getVeiculo());
        this.frota = dto.getFrota().getId();
    }

    public RequisicaoRoteirizadorVo(List<PontoRotaVo> pontosRota, VeiculoRotaVo infoVeiculo, Long frota) {
        this.pontosRota = pontosRota;
        this.infoVeiculo = infoVeiculo;
        this.frota = frota;
    }

    public List<PontoRotaVo> getPontosRota() {
        return pontosRota;
    }

    public void setPontosRota(List<PontoRotaVo> pontosRota) {
        this.pontosRota = pontosRota;
    }

    public VeiculoRotaVo getInfoVeiculo() {
        return infoVeiculo;
    }

    public void setInfoVeiculo(VeiculoRotaVo infoVeiculo) {
        this.infoVeiculo = infoVeiculo;
    }

    public Long getFrota() {
        return frota;
    }

    public void setFrota(Long frota) {
        this.frota = frota;
    }

}
