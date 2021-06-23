package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IDistribuicaoAutomaticaDados;
import ipp.aci.boleia.dominio.beneficios.DistribuicaoAutomatica;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades DistribuicaoAutomatica
 */
@Repository
public class OracleDistribuicaoAutomaticaDados extends OracleRepositorioBoleiaDados<DistribuicaoAutomatica> implements IDistribuicaoAutomaticaDados {
    /**
     * Instancia o repositorio
     */
    public OracleDistribuicaoAutomaticaDados() {
        super(DistribuicaoAutomatica.class);
    }

    @Override
    public DistribuicaoAutomatica obterPorIdBeneficiario(Long idBeneficiario) {
        return pesquisarUnico(new ParametroPesquisaIgual("beneficiario", idBeneficiario));
    }
}
