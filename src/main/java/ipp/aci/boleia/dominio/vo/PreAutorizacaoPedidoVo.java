package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.Frota;
import ipp.aci.boleia.dominio.PontoDeVenda;
import ipp.aci.boleia.dominio.Veiculo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * Modela os dados de pre-autorizacao para execucao da validacao dos parametros de uso na pre-autorizacao.
 */
public class PreAutorizacaoPedidoVo extends PreAutorizacaoBasePedidoVo {

    private Frota frota;
    private Veiculo veiculo;
    private List<PontoDeVenda> postosDisponiveis;

    public PreAutorizacaoPedidoVo() {}

    public PreAutorizacaoPedidoVo(Frota frota, Veiculo veiculo, Date dataCriacao) {
        this.frota = frota;
        this.veiculo = veiculo;
        this.dataCriacao = dataCriacao;
    }

    public PreAutorizacaoPedidoVo(Frota frota, Date dataCriacao) {
        this.frota = frota;
        this.dataCriacao = dataCriacao;
    }

    public PreAutorizacaoPedidoVo(Frota frota, Veiculo veiculo, BigDecimal litragem, Long hodometro, BigDecimal horimetro, Date dataCriacao) {
        this(frota, veiculo, dataCriacao);
        this.litragem = litragem;
        this.hodometro = hodometro;
        this.horimetro = horimetro;
    }

    public PreAutorizacaoPedidoVo(Frota frota, Veiculo veiculo, Long hodometro, BigDecimal horimetro, Date dataCriacao) {
        this(frota, veiculo, dataCriacao);
        this.hodometro = hodometro;
        this.horimetro = horimetro;
        this.dataCriacao = dataCriacao;
    }

    public PreAutorizacaoPedidoVo(Frota frota, BigDecimal latitude, BigDecimal longitude, BigDecimal precisaoGPS, Date dataCriacao) {
        this(frota, dataCriacao);
        this.latitude = latitude;
        this.longitude = longitude;
        this.precisaoGPS = precisaoGPS;
        this.dataCriacao = dataCriacao;
    }

    public Frota getFrota() {
        return frota;
    }

    public void setFrota(Frota frota) {
        this.frota = frota;
    }

    public Veiculo getVeiculo() {
        return veiculo;
    }

    public void setVeiculo(Veiculo veiculo) {
        this.veiculo = veiculo;
    }

    public List<PontoDeVenda> getPostosDisponiveis() {
        return postosDisponiveis;
    }

    public void setPostosDisponiveis(List<PontoDeVenda> postosDisponiveis) {
        this.postosDisponiveis = postosDisponiveis;
    }
}
