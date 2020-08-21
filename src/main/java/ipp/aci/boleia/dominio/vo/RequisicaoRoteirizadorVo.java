package ipp.aci.boleia.dominio.vo;

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
     * Constrói uma requisição para envio ao algoritmo de roteirização
     * @param pontosRota Lista de pontos da rota
     * @param infoVeiculo Informações do veículo
     * @param frota Informações da Frota
     */
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
