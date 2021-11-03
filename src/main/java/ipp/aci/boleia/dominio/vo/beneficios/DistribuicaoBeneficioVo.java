package ipp.aci.boleia.dominio.vo.beneficios;

import java.math.BigDecimal;

/**
 * VO com os dados de uma distribuição de benefício.
 *
 * @author pedro.silva
 */
public class DistribuicaoBeneficioVo {
    private Long idBeneficiario;
    private BigDecimal valorDistribuicao;

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public BigDecimal getValorDistribuicao() {
        return valorDistribuicao;
    }

    public void setValorDistribuicao(BigDecimal valorDistribuicao) {
        this.valorDistribuicao = valorDistribuicao;
    }
}
