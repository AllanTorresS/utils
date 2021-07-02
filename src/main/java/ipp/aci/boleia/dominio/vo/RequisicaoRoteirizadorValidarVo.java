package ipp.aci.boleia.dominio.vo;

import java.util.ArrayList;
import java.util.List;

/**
 * Representa uma requisição para cálculo de uma Rota
 */
public class RequisicaoRoteirizadorValidarVo {

    private List<PontoRotaVo> pontosRota = new ArrayList<>();
    private VeiculoRotaVo infoVeiculo;
    private Long frota;
    private List<Integer> parametrosFrota;
    private List<PontoRotaPostoVo> postos;

    /**
     * Construtor padrão
     */
    public RequisicaoRoteirizadorValidarVo() { }

    /**
     * Constrói uma requisição para envio ao algoritmo de roteirização
     * @param pontosRota Lista de pontos da rota
     * @param infoVeiculo Informações do veículo
     * @param frota Informações da Frota
     * @param parametrosFrota Informações dos Parametros de Sistema da Frota
     * @param postos Lista de Postos a serem utilizados na Rota
     */
    public RequisicaoRoteirizadorValidarVo(List<PontoRotaVo> pontosRota, VeiculoRotaVo infoVeiculo, Long frota,
                                           List<Integer> parametrosFrota, List<PontoRotaPostoVo> postos) {
        this.pontosRota = pontosRota;
        this.infoVeiculo = infoVeiculo;
        this.frota = frota;
        this.parametrosFrota = parametrosFrota;
        this.postos = postos;
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

    public List<Integer> getParametrosFrota() {
        return parametrosFrota;
    }

    public void setParametrosFrota(List<Integer> parametrosFrota) {
        this.parametrosFrota = parametrosFrota;
    }

    public List<PontoRotaPostoVo> getPostos() {
        return postos;
    }

    public void setPostos(List<PontoRotaPostoVo> postos) {
        this.postos = postos;
    }
}
