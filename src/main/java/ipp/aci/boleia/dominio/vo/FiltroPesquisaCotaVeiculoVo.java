package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;
/**
 * Filtro para pesquisa de cota do veiculo
 */
public class FiltroPesquisaCotaVeiculoVo extends BaseFiltroPaginado {

    private String placa;
    private EntidadeVo tipoVeiculo;
    private EnumVo classificacao;
    private EntidadeVo empresaAgregada;
    private EntidadeVo frota;
    private EntidadeVo unidade;

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public EntidadeVo getTipoVeiculo() {
        return tipoVeiculo;
    }

    public void setTipoVeiculo(EntidadeVo tipoVeiculo) {
        this.tipoVeiculo = tipoVeiculo;
    }

    public EnumVo getClassificacao() {
        return classificacao;
    }

    public void setClassificacao(EnumVo classificacao) {
        this.classificacao = classificacao;
    }

    public EntidadeVo getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(EntidadeVo empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public EntidadeVo getFrota() {
        return frota;
    }

    public void setFrota(EntidadeVo frota) {
        this.frota = frota;
    }

    public EntidadeVo getUnidade() {
        return unidade;
    }

    public void setUnidade(EntidadeVo unidade) {
        this.unidade = unidade;
    }
}
