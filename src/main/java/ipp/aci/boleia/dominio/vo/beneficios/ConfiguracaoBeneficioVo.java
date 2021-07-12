package ipp.aci.boleia.dominio.vo.beneficios;

import ipp.aci.boleia.dominio.vo.EntidadeVo;

import java.util.List;

/**
 * VO com os dados de uma configuração de benefício.
 *
 * @author lucas.santiago
 */
public class ConfiguracaoBeneficioVo {
    private Long idBeneficiario;
    private List<EntidadeVo> beneficios;

    public Long getIdBeneficiario() {
        return idBeneficiario;
    }

    public void setIdBeneficiario(Long idBeneficiario) {
        this.idBeneficiario = idBeneficiario;
    }

    public List<EntidadeVo> getBeneficios() {
        return beneficios;
    }

    public void setBeneficios(List<EntidadeVo> beneficios) {
        this.beneficios = beneficios;
    }
}
