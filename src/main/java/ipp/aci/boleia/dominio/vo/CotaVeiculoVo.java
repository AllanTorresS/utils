package ipp.aci.boleia.dominio.vo;

import ipp.aci.boleia.dominio.EmpresaAgregada;
import ipp.aci.boleia.dominio.SubTipoVeiculo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa classe auxiliar para cotas veiculos.
 */
public class CotaVeiculoVo {

    private Long idVeiculo;
    private String frota;
    private String  placa;
    private String empresaAgregada;
    private Integer  agregado;
    private String subtipoVeiculo;
    private BigDecimal cota;
    private BigDecimal saldo;
    private String nomeMotorista;

    /**
     *
     */
    public CotaVeiculoVo(){

    }

    /**
     *
     * @param idVeiculo
     * @param frota
     * @param placa
     * @param empresaAgregada
     * @param agregado
     * @param subtipoVeiculo
     * @param cota
     * @param saldo
     * @param nomeMotorista
     */
    public CotaVeiculoVo(Long idVeiculo, String frota, String placa, String empresaAgregada, Integer agregado, String subtipoVeiculo, BigDecimal cota, BigDecimal saldo, String nomeMotorista) {
        this.idVeiculo = idVeiculo;
        this.frota = frota;
        this.placa = placa;
        this.empresaAgregada = empresaAgregada;
        this.agregado = agregado;
        this.subtipoVeiculo = subtipoVeiculo;
        this.cota = cota;
        this.saldo = saldo;
        this.nomeMotorista = nomeMotorista;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getFrota() {
        return frota;
    }

    public void setFrota(String frota) {
        this.frota = frota;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public String getEmpresaAgregada() {
        return empresaAgregada;
    }

    public void setEmpresaAgregada(String empresaAgregada) {
        this.empresaAgregada = empresaAgregada;
    }

    public Integer getAgregado() {
        return agregado;
    }

    public void setAgregado(Integer agregado) {
        this.agregado = agregado;
    }

    public String getSubtipoVeiculo() {
        return subtipoVeiculo;
    }

    public void setSubtipoVeiculo(String subtipoVeiculo) {
        this.subtipoVeiculo = subtipoVeiculo;
    }

    public BigDecimal getCota() {
        return cota;
    }

    public void setCota(BigDecimal cota) {
        this.cota = cota;
    }

    public BigDecimal getSaldo() {
        return saldo;
    }

    public void setSaldo(BigDecimal saldo) {
        this.saldo = saldo;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

}
