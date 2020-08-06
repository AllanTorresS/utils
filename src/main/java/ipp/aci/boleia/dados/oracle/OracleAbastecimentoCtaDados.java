package ipp.aci.boleia.dados.oracle;

import ipp.aci.boleia.dados.IAbastecimentoCtaDados;
import ipp.aci.boleia.dominio.AbastecimentoCta;
import ipp.aci.boleia.dominio.pesquisa.parametro.ParametroPesquisaIgual;
import org.springframework.stereotype.Repository;

/**
 * Respositorio de entidades de AbastecimentoCta
 */
@Repository
public class OracleAbastecimentoCtaDados extends OracleRepositorioBoleiaDados<AbastecimentoCta>  implements IAbastecimentoCtaDados {

    /**
     * Instancia o repositorio
     */
    public OracleAbastecimentoCtaDados() {
        super(AbastecimentoCta.class);
    }

    @Override
    public AbastecimentoCta buscarPorIdCTA(Long idCTA) {
        return pesquisarUnico(new ParametroPesquisaIgual("idCTA", idCTA));
    }
}
