package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.ITipoBeneficioConfiguracaoDados;
import ipp.aci.boleia.dominio.beneficios.TipoBeneficioConfiguracao;
import org.springframework.stereotype.Repository;

/**
 * Repositório de entidades TipoBeneficioConfiguracao.
 */
@Repository
public class OracleTipoBeneficioConfiguracaoDados extends OracleRepositorioBoleiaDados<TipoBeneficioConfiguracao> implements ITipoBeneficioConfiguracaoDados {

    /**
     * Instancia o repositório.
     */
    public OracleTipoBeneficioConfiguracaoDados() {
        super(TipoBeneficioConfiguracao.class);
    }
}
