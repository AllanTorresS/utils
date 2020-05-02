package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.pesquisa.comum.BaseFiltroPaginado;

/**
 * Filtro para pesquisa de veiculos
 */
public class FiltroPesquisaVeiculoVo extends BaseFiltroPaginado {

    private String placa;
    private EntidadeVo tipoVeiculo;
    private EntidadeVo subtipoVeiculo;
    private EntidadeVo marca;
    private String anoFabricacao;
    private String anoModelo;
    private EntidadeVo modelo;
    private EntidadeVo tipoCombustivel;
    private EnumVo status;
    private EnumVo classificacao;
    private EntidadeVo unidade;
    private EntidadeVo grupoOperacional;
    private EntidadeVo empresaAgregada;
    private EntidadeVo frota;
    private Boolean semConsumo;

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

    public EntidadeVo getSubtipoVeiculo() {
        return subtipoVeiculo;
    }

    public void setSubtipoVeiculo(EntidadeVo subtipoVeiculo) {
        this.subtipoVeiculo = subtipoVeiculo;
    }

    public EntidadeVo getMarca() {
        return marca;
    }

    public void setMarca(EntidadeVo marca) {
        this.marca = marca;
    }

    public String getAnoFabricacao() {
        return anoFabricacao;
    }

    public void setAnoFabricacao(String anoFabricacao) {
        this.anoFabricacao = anoFabricacao;
    }

    public String getAnoModelo() {
        return anoModelo;
    }

    public void setAnoModelo(String anoModelo) {
        this.anoModelo = anoModelo;
    }

    public EntidadeVo getModelo() {
        return modelo;
    }

    public void setModelo(EntidadeVo modelo) {
        this.modelo = modelo;
    }

    public EntidadeVo getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(EntidadeVo tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }

    public EnumVo getStatus() {
        return status;
    }

    public void setStatus(EnumVo status) {
        this.status = status;
    }

    public EntidadeVo getUnidade() {
        return unidade;
    }

    public void setUnidade(EntidadeVo unidade) {
        this.unidade = unidade;
    }

    public EntidadeVo getGrupoOperacional() {
        return grupoOperacional;
    }

    public void setGrupoOperacional(EntidadeVo grupoOperacional) {
        this.grupoOperacional = grupoOperacional;
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

    public Boolean getSemConsumo() {
        return semConsumo;
    }

    public void setSemConsumo(Boolean semConsumo) {
        this.semConsumo = semConsumo;
    }
}
