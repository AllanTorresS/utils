package ipp.aci.boleia.dominio.vo;

/**
 * Representa uma entidade de tipo combustível para integração com o Integrador
 */
public class TipoCombustivelIntegradorVo {

    private String codigoTipoCombustivel;

    private String nomeTipoCombustivel;

    public String getCodigoTipoCombustivel() {
        return codigoTipoCombustivel;
    }

    public void setCodigoTipoCombustivel(String codigoTipoCombustivel) {
        this.codigoTipoCombustivel = codigoTipoCombustivel;
    }

    public String getNomeTipoCombustivel() {
        return nomeTipoCombustivel;
    }

    public void setNomeTipoCombustivel(String nomeTipoCombustivel) {
        this.nomeTipoCombustivel = nomeTipoCombustivel;
    }
}