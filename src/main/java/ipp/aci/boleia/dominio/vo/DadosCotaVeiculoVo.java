package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;

/**
 * Representa classe auxiliar para cotas veiculos.
 */
public class DadosCotaVeiculoVo {
    private Long idVeiculo;
    private String placa;
    private Long idEmpresaAgregada;
    private Long cnpjEmpresaAgregada;
    private String razaoSocialEmpresaAgregada;
    private String nomeFantasiaEmpresaAgregada;
    private Integer agregado;
    private BigDecimal cotaVeiculo;
    private BigDecimal saldoVeiculo;
    private String nomeMotorista;
    private Long idFrota;
    private Long cnpjFrota;
    private String razaoSocialFrota;
    private Long idTipoVeiculo;
    private String descricaoTipoVeiculo;
    private Long idSubTipoVeiculo;
    private String descricaoSubTipoVeiculo;
    private Long idUnidade;
    private Long cnpjUnidade;
    private Long inscricaoEstadualUnidade;
    private String nomeUnidade;

    /**
     *
     */
    public DadosCotaVeiculoVo() {
    }

    /**
     *
     * @param idVeiculo
     * @param placa
     * @param idEmpresaAgregada
     * @param cnpjEmpresaAgregada
     * @param razaoSocialEmpresaAgregada
     * @param nomeFantasiaEmpresaAgregada
     * @param agregado
     * @param cotaVeiculo
     * @param saldoVeiculo
     * @param nomeMotorista
     * @param idFrota
     * @param cnpjFrota
     * @param razaoSocialFrota
     * @param idTipoVeiculo
     * @param descricaoTipoVeiculo
     * @param idSubTipoVeiculo
     * @param descricaoSubTipoVeiculo
     * @param idUnidade
     * @param cnpjUnidade
     * @param inscricaoEstadualUnidade
     * @param nomeUnidade
     */
    public DadosCotaVeiculoVo(Long idVeiculo, String placa, Long idEmpresaAgregada, Long cnpjEmpresaAgregada, String razaoSocialEmpresaAgregada, String nomeFantasiaEmpresaAgregada, Integer agregado, BigDecimal cotaVeiculo, BigDecimal saldoVeiculo, String nomeMotorista, Long idFrota, Long cnpjFrota, String razaoSocialFrota, Long idTipoVeiculo, String descricaoTipoVeiculo, Long idSubTipoVeiculo, String descricaoSubTipoVeiculo, Long idUnidade, Long cnpjUnidade, Long inscricaoEstadualUnidade, String nomeUnidade) {
        this.idVeiculo = idVeiculo;
        this.placa = placa;
        this.idEmpresaAgregada = idEmpresaAgregada;
        this.cnpjEmpresaAgregada = cnpjEmpresaAgregada;
        this.razaoSocialEmpresaAgregada = razaoSocialEmpresaAgregada;
        this.nomeFantasiaEmpresaAgregada = nomeFantasiaEmpresaAgregada;
        this.agregado = agregado;
        this.cotaVeiculo = cotaVeiculo;
        this.saldoVeiculo = saldoVeiculo;
        this.nomeMotorista = nomeMotorista;
        this.idFrota = idFrota;
        this.cnpjFrota = cnpjFrota;
        this.razaoSocialFrota = razaoSocialFrota;
        this.idTipoVeiculo = idTipoVeiculo;
        this.descricaoTipoVeiculo = descricaoTipoVeiculo;
        this.idSubTipoVeiculo = idSubTipoVeiculo;
        this.descricaoSubTipoVeiculo = descricaoSubTipoVeiculo;
        this.idUnidade = idUnidade;
        this.cnpjUnidade = cnpjUnidade;
        this.inscricaoEstadualUnidade = inscricaoEstadualUnidade;
        this.nomeUnidade = nomeUnidade;
    }

    public Long getIdVeiculo() {
        return idVeiculo;
    }

    public void setIdVeiculo(Long idVeiculo) {
        this.idVeiculo = idVeiculo;
    }

    public String getPlaca() {
        return placa;
    }

    public void setPlaca(String placa) {
        this.placa = placa;
    }

    public Long getIdEmpresaAgregada() {
        return idEmpresaAgregada;
    }

    public void setIdEmpresaAgregada(Long idEmpresaAgregada) {
        this.idEmpresaAgregada = idEmpresaAgregada;
    }

    public Long getCnpjEmpresaAgregada() {
        return cnpjEmpresaAgregada;
    }

    public void setCnpjEmpresaAgregada(Long cnpjEmpresaAgregada) {
        this.cnpjEmpresaAgregada = cnpjEmpresaAgregada;
    }

    public String getRazaoSocialEmpresaAgregada() {
        return razaoSocialEmpresaAgregada;
    }

    public void setRazaoSocialEmpresaAgregada(String razaoSocialEmpresaAgregada) {
        this.razaoSocialEmpresaAgregada = razaoSocialEmpresaAgregada;
    }

    public String getNomeFantasiaEmpresaAgregada() {
        return nomeFantasiaEmpresaAgregada;
    }

    public void setNomeFantasiaEmpresaAgregada(String nomeFantasiaEmpresaAgregada) {
        this.nomeFantasiaEmpresaAgregada = nomeFantasiaEmpresaAgregada;
    }

    public Integer getAgregado() {
        return agregado;
    }

    public void setAgregado(Integer agregado) {
        this.agregado = agregado;
    }

    public BigDecimal getCotaVeiculo() {
        return cotaVeiculo;
    }

    public void setCotaVeiculo(BigDecimal cotaVeiculo) {
        this.cotaVeiculo = cotaVeiculo;
    }

    public BigDecimal getSaldoVeiculo() {
        return saldoVeiculo;
    }

    public void setSaldoVeiculo(BigDecimal saldoVeiculo) {
        this.saldoVeiculo = saldoVeiculo;
    }

    public String getNomeMotorista() {
        return nomeMotorista;
    }

    public void setNomeMotorista(String nomeMotorista) {
        this.nomeMotorista = nomeMotorista;
    }

    public Long getIdFrota() {
        return idFrota;
    }

    public void setIdFrota(Long idFrota) {
        this.idFrota = idFrota;
    }

    public Long getCnpjFrota() {
        return cnpjFrota;
    }

    public void setCnpjFrota(Long cnpjFrota) {
        this.cnpjFrota = cnpjFrota;
    }

    public String getRazaoSocialFrota() {
        return razaoSocialFrota;
    }

    public void setRazaoSocialFrota(String razaoSocialFrota) {
        this.razaoSocialFrota = razaoSocialFrota;
    }

    public Long getIdTipoVeiculo() {
        return idTipoVeiculo;
    }

    public void setIdTipoVeiculo(Long idTipoVeiculo) {
        this.idTipoVeiculo = idTipoVeiculo;
    }

    public String getDescricaoTipoVeiculo() {
        return descricaoTipoVeiculo;
    }

    public void setDescricaoTipoVeiculo(String descricaoTipoVeiculo) {
        this.descricaoTipoVeiculo = descricaoTipoVeiculo;
    }

    public Long getIdSubTipoVeiculo() {
        return idSubTipoVeiculo;
    }

    public void setIdSubTipoVeiculo(Long idSubTipoVeiculo) {
        this.idSubTipoVeiculo = idSubTipoVeiculo;
    }

    public String getDescricaoSubTipoVeiculo() {
        return descricaoSubTipoVeiculo;
    }

    public void setDescricaoSubTipoVeiculo(String descricaoSubTipoVeiculo) {
        this.descricaoSubTipoVeiculo = descricaoSubTipoVeiculo;
    }

    public Long getIdUnidade() {
        return idUnidade;
    }

    public void setIdUnidade(Long idUnidade) {
        this.idUnidade = idUnidade;
    }

    public Long getCnpjUnidade() {
        return cnpjUnidade;
    }

    public void setCnpjUnidade(Long cnpjUnidade) {
        this.cnpjUnidade = cnpjUnidade;
    }

    public Long getInscricaoEstadualUnidade() {
        return inscricaoEstadualUnidade;
    }

    public void setInscricaoEstadualUnidade(Long inscricaoEstadualUnidade) {
        this.inscricaoEstadualUnidade = inscricaoEstadualUnidade;
    }

    public String getNomeUnidade() {
        return nomeUnidade;
    }

    public void setNomeUnidade(String nomeUnidade) {
        this.nomeUnidade = nomeUnidade;
    }
}