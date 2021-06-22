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
    private List<Integer> parametrosFrota;
    private List<Long> postosDesconsiderados;
    private Integer postoUrbano;

    /**
     * Construtor padrão
     */
    public RequisicaoRoteirizadorVo() { }

    /**
     * Constrói uma requisição para envio ao algoritmo de roteirização
     * @param pontosRota Lista de pontos da rota
     * @param infoVeiculo Informações do veículo
     * @param frota Informações da Frota
     * @param parametrosFrota Informações dos Parametros de Sistema da Frota
     * @param postosDesconsiderados Lista de Postos a serem desconsiderados na Rota
     * @param postoUrbano Flag que sinaliza a utilizacao de postos urbanos
     */
    public RequisicaoRoteirizadorVo(List<PontoRotaVo> pontosRota, VeiculoRotaVo infoVeiculo, Long frota,
                                 List<Integer> parametrosFrota, List<Long> postosDesconsiderados, Integer postoUrbano  ) {
        this.pontosRota = pontosRota;
        this.infoVeiculo = infoVeiculo;
        this.frota = frota;
        this.parametrosFrota = parametrosFrota;
        this.postosDesconsiderados = postosDesconsiderados;
        this.postoUrbano = postoUrbano;
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

    public List<Long> getPostosDesconsiderados() {
        return postosDesconsiderados;
    }

    public void setPostosDesconsiderados(List<Long> postosDesconsiderados) {
        this.postosDesconsiderados = postosDesconsiderados;
    }

    public Integer getPostoUrbano() {
        return postoUrbano;
    }

    public void setPostoUrbano(Integer postoUrbano) {
        this.postoUrbano = postoUrbano;
    }
}
