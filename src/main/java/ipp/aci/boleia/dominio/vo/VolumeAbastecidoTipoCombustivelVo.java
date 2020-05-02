package ipp.aci.boleia.dominio.vo;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Classe abstrata para implementação do Volume Abastecido por Tipo de Combustível
 *
 */
public class VolumeAbastecidoTipoCombustivelVo {
    private BigDecimal vtps;
    private BigDecimal vtpa;
    private String combustivel;
    private BigDecimal percentual;

    public VolumeAbastecidoTipoCombustivelVo() {
        //Construtor default
    }

    /**
     * Construtor
     * @param combustivel A descrição do combustível referido
     * @param vtps o vtps
     * @param vtpa o vtpa
     */
    public VolumeAbastecidoTipoCombustivelVo(BigDecimal vtps, BigDecimal vtpa, String combustivel) {
        this.vtps = vtps;
        this.vtpa = vtpa;
        this.combustivel = combustivel;

        if (vtpa == null || vtps == null) {
            return;
        }

        this.percentual = vtps
                .subtract(vtpa)
                .divide(vtpa, 4, RoundingMode.HALF_UP)
                .multiply(new BigDecimal(100));
    }

    public String getCombustivel() {
        return combustivel;
    }

    public void setCombustivel(String combustivel) {
        this.combustivel = combustivel;
    }

    public BigDecimal getPercentual() {
        return percentual;
    }

    public void setPercentual(BigDecimal percentual) {
        this.percentual = percentual;
    }

    public BigDecimal getVtps() {
        return vtps;
    }

    public void setVtps(BigDecimal vtps) {
        this.vtps = vtps;
    }

    public BigDecimal getVtpa() {
        return vtpa;
    }

    public void setVtpa(BigDecimal vtpa) {
        this.vtpa = vtpa;
    }
}
