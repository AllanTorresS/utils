package ipp.aci.boleia.dominio.vo;

/**
 * Representa uma entidade de produto combustível para integração com o Integrador
 */
public class ProdutoCombustivelIntegradorVo {

    private TipoCombustivelIntegradorVo tipoCombustivel;

    public TipoCombustivelIntegradorVo getTipoCombustivel() {
        return tipoCombustivel;
    }

    public void setTipoCombustivel(TipoCombustivelIntegradorVo tipoCombustivel) {
        this.tipoCombustivel = tipoCombustivel;
    }
}
