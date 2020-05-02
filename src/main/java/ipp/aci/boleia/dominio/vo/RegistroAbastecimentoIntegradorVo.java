package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Representa uma entidade de registro de abastecimento para integração com o Integrador
 */
public class RegistroAbastecimentoIntegradorVo {

    private String codigoAbastecimento;

    private BigDecimal volume;

    private BigDecimal precoUnitario;

    private Date dataAbastecimento;

    private BicoIntegradorVo bico;

    public String getCodigoAbastecimento() {
        return codigoAbastecimento;
    }

    public void setCodigoAbastecimento(String codigoAbastecimento) {
        this.codigoAbastecimento = codigoAbastecimento;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public void setVolume(BigDecimal volume) {
        this.volume = volume;
    }

    public BigDecimal getPrecoUnitario() {
        return precoUnitario;
    }

    public void setPrecoUnitario(BigDecimal precoUnitario) {
        this.precoUnitario = precoUnitario;
    }

    public Date getDataAbastecimento() {
        return dataAbastecimento;
    }

    public void setDataAbastecimento(Date dataAbastecimento) {
        this.dataAbastecimento = dataAbastecimento;
    }

    public BicoIntegradorVo getBico() {
        return bico;
    }

    public void setBico(BicoIntegradorVo bico) {
        this.bico = bico;
    }
}
